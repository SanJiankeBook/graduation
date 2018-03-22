
	
 		//柱状图	
 		$(function(){
 			var wpid= window.localStorage? localStorage.getItem("writtingPaper_wpid"): Cookie.read("writtingPaper_wpid");
 			$.ajax({
 				url:"/Examination2.0/writingPaper_getCorrectRate.action",
 				type:"post",
 				datatype:"json",
 				data:{"wpid":wpid},
 				success:showCorrectRateOfChapterOrSubject
 			});
 		});
 		
 		//饼状图	
 		$(function(){
 			var scorePercent= window.localStorage? localStorage.getItem("writtingPaper_scorePercent"): Cookie.read("writtingPaper_scorePercent");
 			if( scorePercent.charAt(scorePercent.length-1)==";"){
 				scorePercent=scorePercent.substring(0,scorePercent.length-1)
 				}else{
 					scorePercent=scorePercent;
 				}
 			var scorePercentArr=scorePercent.split(";");
 			var sumScorePercent=0;
 			for(var i=0;i<scorePercentArr.length;i++){
 				sumScorePercent=accAdd(scorePercentArr[i],sumScorePercent);
 			}
 			var examCount=sumScorePercent;
 			var passExamRate=fomatFloat(Subtr(1,accDiv(scorePercentArr[0],sumScorePercent)),3);
 			var  passExamCount=Subtr(sumScorePercent,scorePercentArr[0]);
 			//alert("passExamCount:"+passExamCount);
 			$("#examCount").val(examCount);
 			$("#passExamRate").text(passExamRate);
 			$("#passExamCount").text(passExamCount);
 			
 			var myData = new Array(['60分以下 '+scorePercentArr[0]+'人',accMul(fomatFloat(accDiv(scorePercentArr[0],sumScorePercent),3),100)], ['60-69分 '+scorePercentArr[1]+' 人', accMul(fomatFloat(accDiv(scorePercentArr[1],sumScorePercent),3),100)], ['70-79分 '+scorePercentArr[2]+' 人', accMul(fomatFloat(accDiv(scorePercentArr[2],sumScorePercent),3),100)], ['80-89分 '+scorePercentArr[3]+' 人', accMul(fomatFloat(accDiv(scorePercentArr[3],sumScorePercent),3),100)], ['90-100分 '+scorePercentArr[4]+' 人', accMul(fomatFloat(accDiv(scorePercentArr[4],sumScorePercent),3),100)]);
 			var colors = ['#C40000', '#750303', '#F9ECA2', '#FA9000', '#FA5400'];
 			var myChart = new JSChart('piegraph', 'pie');
 			myChart.setDataArray(myData);
 			myChart.colorizePie(colors);
 			myChart.setTitle('每分数段的考生比例 (%)');
 			myChart.setTitleColor('#8E8E8E');
 			myChart.setTitleFontSize(11);
 			myChart.setTextPaddingTop(30);
 			myChart.setSize(616, 321);
 			myChart.setPiePosition(308, 170);
 			myChart.setPieRadius(85);
 			myChart.setPieUnitsColor('#555');
 			myChart.setBackgroundImage('/Examination2.0/images/chart_bg.jpg');
 			myChart.draw();	
 		});

//
function showCorrectRateOfChapterOrSubject(data){
	var returnInfos =  data ;
	var examSubject="";
	if(returnInfos.responseCode==0){
		if(returnInfos.obj!=null){
				var myDataArr=new Array();
				var colorsArr=new Array();
				var colors=new Array('#F13714','#C13714','#AF0202', '#EC7A00', '#FCD200', '#81C714', '#81a714', '#813714','#913714','#A13714','#B13714','#D13714','#F13714','#C13714','#AF0202', '#EC7A00', '#FCD200', '#81C714', '#81a714', '#813714','#913714','#A13714','#B13714','#D13714');
				for(var i=0;i<parseInt(returnInfos.obj.length/3);i++){
					myDataArr[i]=new Array();
					colorsArr[i]=colors[i];
						//alert(returnInfos.obj[j]);
						for(var j=i*3;j<returnInfos.obj.length;j++){
							if(j%3==1){
								myDataArr[i][0]=returnInfos.obj[(i*3+1)];
							}else if(j%3==2){
								myDataArr[i][1]=parseFloat(returnInfos.obj[i*3+2]);
							}else{
								examSubject=returnInfos.obj[i*3];
							}
						}
				}
				
				var myData=myDataArr;
				var myChart = new JSChart('bargraph', 'bar');		
				myChart.setDataArray(myData);
				myChart.colorizeBars(colorsArr)
				myChart.setTitle('科目《'+examSubject+'》及其章节的考生答对率');
				myChart.setTitleColor('#8E8E8E');
				myChart.setAxisNameX('所在章节');
				myChart.setAxisNameY('答对人数比例');
				myChart.setAxisColor('#C4C4C4');
				myChart.setAxisNameFontSize(11);
				myChart.setAxisNameColor('#999');
				myChart.setAxisValuesColor('#7E7E7E');
				myChart.setBarValuesColor('#7E7E7E');
				myChart.setAxisPaddingTop(50);
				myChart.setAxisPaddingRight(40);
				myChart.setAxisPaddingLeft(50);
				myChart.setAxisPaddingBottom(100);
				myChart.setTextPaddingLeft(10);
				myChart.setTextPaddingBottom(10);
				myChart.setAxisValuesAngle(30);
				myChart.setTitleFontSize(11);
				myChart.setBarBorderWidth(1);
				myChart.setBarBorderColor('#C4C4C4');
				myChart.setBarSpacingRatio(50);
				//myChart.setGrid(false);
				myChart.setSize(616, 321);
				myChart.setBackgroundImage('/Examination2.0/images/chart_bg.jpg');
				myChart.draw();		
			}	
		}else{
		var examSubject= window.localStorage? localStorage.getItem("writtingPaper_examSubject"): Cookie.read("writtingPaper_examSubject");
		//alert("获取数据失败！");
		var colors=new Array('#F13714');
		var myData=new Array(['章节不存在',0]);
		var myChart = new JSChart('bargraph', 'bar');		
		myChart.setDataArray(myData);
		myChart.colorizeBars(colors)
		myChart.setTitle('科目《'+examSubject+'》及其章节的考生答对率');
		myChart.setTitleColor('#8E8E8E');
		myChart.setAxisNameX('所在章节');
		myChart.setAxisNameY('答对人数比例%');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisNameFontSize(11);
		myChart.setAxisNameColor('#999');
		myChart.setAxisValuesColor('#7E7E7E');
		myChart.setBarValuesColor('#7E7E7E');
		myChart.setAxisPaddingTop(50);
		myChart.setAxisPaddingRight(40);
		myChart.setAxisPaddingLeft(50);
		myChart.setAxisPaddingBottom(100);
		myChart.setTextPaddingLeft(10);
		myChart.setTextPaddingBottom(10);
		myChart.setAxisValuesAngle(30);
		myChart.setTitleFontSize(11);
		myChart.setBarBorderWidth(1);
		myChart.setBarBorderColor('#C4C4C4');
		myChart.setBarSpacingRatio(50);
		//myChart.setGrid(false);
		myChart.setSize(616, 321);
		myChart.setBackgroundImage('/Examination2.0/images/chart_bg.jpg');
		myChart.draw();		
	}
}

	


$(function(){
	var wpid= window.localStorage? localStorage.getItem("writtingPaper_wpid"): Cookie.read("writtingPaper_wpid");
	var paperName= window.localStorage? localStorage.getItem("writtingPaper_paperName"): Cookie.read("writtingPaper_paperName");
	var examineeClass= window.localStorage? localStorage.getItem("writtingPaper_examineeClass"): Cookie.read("writtingPaper_examineeClass");
	var examSubject= window.localStorage? localStorage.getItem("writtingPaper_examSubject"): Cookie.read("writtingPaper_examSubject");
	var examDate= window.localStorage? localStorage.getItem("writtingPaper_examDate"): Cookie.read("writtingPaper_examDate");
	var scorePercent= window.localStorage? localStorage.getItem("writtingPaper_scorePercent"): Cookie.read("writtingPaper_scorePercent");
	var avgScore= window.localStorage? localStorage.getItem("writtingPaper_avgScore"): Cookie.read("writtingPaper_avgScore");
	var maxScore= window.localStorage? localStorage.getItem("writtingPaper_maxScore"): Cookie.read("writtingPaper_maxScore");
	var minScore= window.localStorage? localStorage.getItem("writtingPaper_minScore"): Cookie.read("writtingPaper_minScore");
	
	$("#examClassId").val(wpid);
	//alert(wpid);
	$("#paperName").val(paperName);
	$("#examineeClass").val(examineeClass);
	$("#examSubject").val(examSubject);
	$("#examDate").val(examDate);
	$("#avgScore").text(avgScore);
	$("#maxScore").text(maxScore);
	$("#minScore").text(minScore);
	$.ajax({
		url:"/Examination2.0/writingAnswer_getCountOfExamPerson.action",
		type:"post",
		datatype:"json",
		data:{"wpid":wpid},
		success:showCountOfExamPerson
	});
	
});
function showCountOfExamPerson(data){
	
	//alert("sumScorePercent:"+sumScorePercent);
	var writingAnswerInfos =   data ;
	var questionStr='<tr><th bordercolor="#7EA6DC">编号</th><th bordercolor="#7EA6DC">姓名</th><th bordercolor="#7EA6DC">分数</th> <th bordercolor="#7EA6DC" colspan="2">信息查看</th></tr>';
	if(writingAnswerInfos.responseCode==0){
		if(writingAnswerInfos.obj!=null){
			$.each(writingAnswerInfos.obj, function(i,writingAnswerInfo){
				questionStr+='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="center" id="'+(i+1)+'">';
				questionStr+='<td align="left">'+(i+1)+'';
				questionStr+='<td align="left">'+writingAnswerInfo.examineeName+'';
				questionStr+='<td align="left">'+writingAnswerInfo.score+'';
				questionStr+='<td align="center"> <a href="#" title="查看评卷后的详细结果" onclick="javascript:toShowExamineeWritinggrade(\''+writingAnswerInfo.writingPaper.id+'\',\''+writingAnswerInfo.examineeName+'\',\''+writingAnswerInfo.writingPaper.examineeClass+'\',\''+writingAnswerInfo.score+'\',\''+writingAnswerInfo.id+'\')">考试信息</a> &nbsp; /<a href="#" onClick="javascript:toShowWritingAnswerPage(\''+writingAnswerInfo.writingPaper.id+'\','+writingAnswerInfo.id+')" title="查看考生的答卷">查看答卷</a> </td> </tr>';
			});	
		}else{
			questionStr+='<tr><td colspan="4">暂无数据...</td></tr>';
		}
		
	$("#showWritingAnswerInfo").html(questionStr);
		
		
		
	}
	
}
//把id存到localstorage或cookie中
	function toShowWritingAnswerPage(wpid,waid){
		if (window.localStorage) {
			 localStorage.setItem("wpid", wpid);
			localStorage.setItem("waid", waid);
		} else {
 		 Cookie.write("wpid", wpid);	
 		 Cookie.write("waid", waid);
		}
		window.open("/Examination2.0/back/backoperation/examineewritpaper.html");
	}
	//把id存到localstorage或cookie中（跳到ShowExamineeWritinggrade。html中）
		
		function toShowExamineeWritinggrade(wpid,examineeName,examineeClass,score,waid){
			if (window.localStorage) {
   			 localStorage.setItem("toExamineeWritinggrad_wpid", wpid);
   			 localStorage.setItem("toExamineeWritinggrad_examineeName", examineeName);
   			 localStorage.setItem("toExamineeWritinggrad_examineeClass", examineeClass);
   			 localStorage.setItem("toExamineeWritinggrad_score", score);
   			 localStorage.setItem("toExamineeWritinggrad_waid", waid);
		} else {
    		 Cookie.write("toExamineeWritinggrad_wpid", wpid);                      
    		 Cookie.write("toExamineeWritinggrad_examineeName", examineeName);      
    		 Cookie.write("toExamineeWritinggrad_examineeClass", examineeClass);    
    		 Cookie.write("toExamineeWritinggrad_score", score); 
    		 Cookie.write("toExamineeWritinggrad_waid", waid);
		}
			
		var content = '<iframe src="/Examination2.0/back/grade/showexamineewritinggrade.html" width="700px" height="95%" frameborder="0" scrolling="yes" ></iframe>';  
	    var boarddiv = '<div id="msgwindow2" title="浏览考生评卷结果"    width="1000px" height="100%"></div>'//style="overflow:hidden;"可以去掉滚动条  
	    $(document.body).append(boarddiv);  
	    var win = $('#msgwindow2').dialog({  
	        content: content,  
	        width: "100%",  
	        height: "100%",  
	        modal: "true",  
	        title: "浏览考生评卷结果",  
	        onClose: function () {  
	            $(this).dialog('destroy');//后面可以关闭后的事件  
	        }  
	    });  
	    win.dialog('open'); 
			
		}
		