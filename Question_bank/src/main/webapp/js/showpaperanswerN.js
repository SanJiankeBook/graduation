$(function(){
		var answerIdeaArr;
					// 从浏览器中取id（从localstorage或cookie中取）
					var opid= window.localStorage? localStorage.getItem("opid"): Cookie.read("opid");
					// alert("opid:"+opid);
					// 根据id查找试卷信息
					$.ajax({
						url:"/Examination2.0/pointAnswer_findPoinAnswerInfoByOpid.action",
						type:"post",
						datatype:"json",
						data:{"opid":opid},
						success:showPointAnswerInfo
						});
					
				});
	
		function showPointAnswerInfo(data){
			var paperinfos = eval("(" + data + ")");
			if(paperinfos.responseCode==0){
				$.each(paperinfos.obj,function(idx,paperinfo){
					var className=$.trim(paperinfo.examinee.examineeClass.className);
					var subName=$.trim(paperinfo.pointPaper.subject.subjectName);
					var studentName=$.trim(paperinfo.examinee.name);
					var pdate=$.trim(paperinfo.pointPaper.pdate);
					var pname=$.trim(paperinfo.pointPaper.pname);
					var descript=$.trim(paperinfo.pointPaper.descript);
					var ptitle=$.trim(paperinfo.pointPaper.ptitle);
					var idea=$.trim(paperinfo.idea);
					var answer=$.trim(paperinfo.answer);
					if( answer.charAt(answer.length-1)==","){
						answer=answer.substring(0,answer.length-1)
						}else{
							answer=answer;
						}
					var answerArr=answer.split(',');
					answerIdeaArr=new Array();
					for(var j=0;j<answerArr.length;j++){
						answerIdeaArr[j]=answerArr[j].split('-')[1];
					}
					if( ptitle.charAt(ptitle.length-1)==","){
						ptitle=ptitle.substring(0,ptitle.length-1)
						}else{
							ptitle=ptitle;
						}
					if(ptitle!=""){
						ptitle="["+ptitle+"]";
						$.ajax({
							url:"/Examination2.0/pointAnswer_findAllPoinInfoByPids.action",
							type:"post",
							datatype:"json",
							data:{"ptitle":ptitle},
							success:showAllPointInfo
							});	
					}
					
					if(descript==""){
						descript="无";
					}
					$("#className").text(className);
					$("#subName").text(subName);
					$("#studentName").text(studentName);
					$("#pdate").text(pdate);
					$("#pname").text(pname);
					$("#descript").text(descript);
					$("#messageinfo").text(idea);
				});
			}else{
				alert("失败");
			}
		}
	function showAllPointInfo(data){
		var pointInfos = eval("(" + data + ")");
		var pointInfoStr='<tr bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left" ><td height="60" colspan="3"><span class="fontColor">暂无测评知识点信息</span></td></tr>';
		if(pointInfos.responseCode==0){
			if(pointInfos.obj!=null&&pointInfos.obj.length>0){
				pointInfoStr='';
				$.each(pointInfos.obj,function(i,pointInfo){
					var index=i+1;
					pointInfoStr+='<tr id="'+index+'" bgcolor="#EDECEB" onmouseover="this.bgColor=\'#93BBDF\';" onmouseout="this.bgColor=\'#EDECEB\';" align="left">';
					pointInfoStr+='<td align="center" width="10%">'+index+'</td>';
					pointInfoStr+='<td width="80%">&nbsp;'+pointInfo.pcontent+'</td>';
					pointInfoStr+='<td align="center" width="10%">'+answerIdeaArr[i]+'分</td></tr>';
				});
				$("#pointInfoMsg").html(pointInfoStr);
			}else{
				$("#pointInfoMsg").html(pointInfoStr);
			}	
		}else{
			$("#pointInfoMsg").html(pointInfoStr);
		}
	}	
	
	function toShowStudentAnswer(){
		window.location.href='/Examination2.0/back/grade/showstudentanswer.html';
	}