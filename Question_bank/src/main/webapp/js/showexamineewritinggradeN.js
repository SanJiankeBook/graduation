//柱状图	
$(function(){
	var wpid= window.localStorage? localStorage.getItem("toExamineeWritinggrad_wpid"): Cookie.read("toExamineeWritinggrad_wpid");
	var examineeName= window.localStorage? localStorage.getItem("toExamineeWritinggrad_examineeName"): Cookie.read("toExamineeWritinggrad_examineeName");
	var examineeClass= window.localStorage? localStorage.getItem("toExamineeWritinggrad_examineeClass"): Cookie.read("toExamineeWritinggrad_examineeClass");
	var score= window.localStorage? localStorage.getItem("toExamineeWritinggrad_score"): Cookie.read("toExamineeWritinggrad_score");
	$("#paperId").val(wpid);
	$("#examineeName").val(examineeName);
	$("#examineeClass").val(examineeClass);
	$("#score").val(score);
});


$(function(){
	var wpid= window.localStorage? localStorage.getItem("toExamineeWritinggrad_wpid"): Cookie.read("toExamineeWritinggrad_wpid");
	var examineeName= window.localStorage? localStorage.getItem("toExamineeWritinggrad_examineeName"): Cookie.read("toExamineeWritinggrad_examineeName");
	$.ajax({
		url:"/Examination2.0/writingPaper_getExamineeCorrectRate.action",
		type:"post",
		datatype:"json",
		data:{"wpid":wpid,"examineeName":examineeName},
		success:showExamineeCorrectRate
	});
});
//
function showExamineeCorrectRate(data){
	//alert(data);
	var returnInfos =  data ;
	var examSubject="";
	if(returnInfos.responseCode==0){
		if(returnInfos.obj!=null){
				var myDataArr=new Array();
				for(var i=0;i<parseInt(returnInfos.obj.length/3);i++){
					myDataArr[i]=new Array();
						//alert(returnInfos.obj[j]);
						for(var j=i*3;j<returnInfos.obj.length;j++){
							if(j%3==0){
								myDataArr[i][0]=returnInfos.obj[(i*3)];
							}else if(j%3==1){
								myDataArr[i][1]=parseFloat(returnInfos.obj[i*3+1]);
							}else if(j%3==2){
								myDataArr[i][2]=parseFloat(returnInfos.obj[i*3+2]);
							}
						}
				}
				
				var myData=myDataArr;
				var myChart = new JSChart('bargraph', 'bar');		
				myChart.setDataArray(myData);
				myChart.setTitle('各科目及其章节的考生答对题数');
				myChart.setTitleColor('#8E8E8E');
				myChart.setAxisNameX('所在章节');
				myChart.setAxisNameY('答对题目数量');
				myChart.setAxisColor('#C4C4C4');
				myChart.setAxisNameFontSize(11);
				myChart.setAxisNameColor('#999');
				myChart.setAxisValuesColor('#7E7E7E');
				myChart.setBarValuesColor('#7E7E7E');
				myChart.setBarColor('#2D6B96', 1);
				myChart.setBarColor('#F13714', 2);
				myChart.setAxisPaddingTop(50);
				myChart.setAxisPaddingRight(10);
				myChart.setAxisPaddingLeft(50);
				myChart.setAxisPaddingBottom(100);
				myChart.setTextPaddingLeft(10);
				myChart.setTextPaddingBottom(10);
				myChart.setAxisValuesAngle(30);
				myChart.setTitleFontSize(11);
				myChart.setBarBorderWidth(1);
				myChart.setBarBorderColor('#C4C4C4');
				myChart.setBarSpacingRatio(50);
				myChart.setLegendShow(true);
				myChart.setLegendPosition('right top');
				myChart.setLegendForBar(1, '答对题数');
				myChart.setLegendForBar(2, '总题数量');
				myChart.setSize(616, 321);
				myChart.setBackgroundImage('/Examination2.0/images/chart_bg.jpg');
				myChart.draw();		
			}	
		}else{
		var examSubject= window.localStorage? localStorage.getItem("writtingPaper_examSubject"): Cookie.read("writtingPaper_examSubject");
		//alert("获取数据失败！");
		var colors=new Array('#F13714');
		var myData=new Array(['章节不存在',0,0]);
		var myChart = new JSChart('bargraph', 'bar');		
		myChart.setDataArray(myData);
		//myChart.colorizeBars(colors)
		myChart.setTitle('各科目及其章节的考生答对题数');
		myChart.setTitleColor('#8E8E8E');
		myChart.setAxisNameX('所在章节');
		myChart.setAxisNameY('答对题目数量');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisNameFontSize(11);
		myChart.setAxisNameColor('#999');
		myChart.setAxisValuesColor('#7E7E7E');
		myChart.setBarValuesColor('#7E7E7E');
		myChart.setBarColor('#2D6B96', 1);
		myChart.setBarColor('#F13714', 2);
		myChart.setAxisPaddingTop(50);
		myChart.setAxisPaddingRight(40);
		myChart.setAxisPaddingLeft(50);
		myChart.setAxisPaddingBottom(100);
		myChart.setTextPaddingLeft(10);
		myChart.setTextPaddingBottom(10);
		myChart.setAxisValuesAngle(30);
		myChart.setTitleFontSize(11);
		myChart.setBarBorderWidth(1);
		myChart.setLegendShow(true);
		myChart.setLegendPosition('right top');
		myChart.setLegendForBar(1, '答对题数');
		myChart.setLegendForBar(2, '总题数量');
		myChart.setBarBorderColor('#C4C4C4');
		myChart.setBarSpacingRatio(50);
		//myChart.setGrid(false);
		myChart.setSize(616, 321);
		myChart.setBackgroundImage('/Examination2.0/images/chart_bg.jpg');
		myChart.draw();		
	}
}
//把id存到localstorage或cookie中(跳到examineewritpaper.html页面中)
	function toShowWritingAnswerPage(){
		var wpid= window.localStorage? localStorage.getItem("toExamineeWritinggrad_wpid"): Cookie.read("toExamineeWritinggrad_wpid");
		var waid= window.localStorage? localStorage.getItem("toExamineeWritinggrad_waid"): Cookie.read("toExamineeWritinggrad_waid");
		if (window.localStorage) {
			 localStorage.setItem("wpid", wpid);
			 localStorage.setItem("waid", waid);
		} else {
 		 Cookie.write("wpid", wpid);	
 		 Cookie.write("waid", waid);
		}
		window.open("../../back/backoperation/examineewritpaper.html");
	}