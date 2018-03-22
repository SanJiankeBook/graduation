var val;
$(function() {
	var ide;
	$("#commitAdailytalk").hide();
	$("#showAdailytalk").hide();
	$("#speak").hide();
	showinfo();
	var semester = $("#semesterS1").attr('value');
	$("#semesterS1").prop('checked', true);
	showClassName(semester);
	val="S1";
	 $("#semesterS1").click(function(){
		 	val=$('input:radio[name="semester"]:checked').val();
		 });
	  
	  $("#semesterS2").click(function(){
		 	val=$('input:radio[name="semester"]:checked').val();
		 });
	  
	  $("#semesterS3").click(function(){
		 	val=$('input:radio[name="semester"]:checked').val();
		 });
})

function checkSemesters(semester) {
	showClassName(semester);
}
// 显示讲解班级
function showClassName(semester) {
	$.post("/Examination2.0/examineeclass_showClassList.action", {
		semester : semester
	}, function(json) {
		$("#examineeClass").html("<option>--请选择--</option>");
		$("#examineeName").html("<option>--请选择--</option>");
		if (json == null) {
			$("#examineeClass").append("<option>--请选择--</option>");
		}
		for ( var i = 0; i < json.length; i++) {
			$("#examineeClass").append(
					"<option value='" + json[i].id + "'>" + json[i].className
							+ "</option>");
		}
	});
}
// 改变讲解班级显示学员姓名
function changeExamineeClasses(value) {
	$("#nospeak").show();
	$("#speak").hide();
	$("#commitAdailytalk").hide();
	var className = $("#examineeClass").find("option:selected").text().trim();
	if(className==null  ||  className=="--请选择--"){
		//$("#historyInfobtn").attr("disabled",true);
		//$("#historyInfobtn").attr("disabled",false);
		return;
	}
	$("#historyInfobtn").removeAttr("disabled");
	$.post("/Examination2.0/checking_showExamineeList.action", {
		className : className
	}, function(json) {
		$("#examineeName").html("");
		for ( var i = 0; i < json.length; i++) {
			$("#examineeName").append(
					"<option value='" + i + "'>" + json[i].name
							+ "&nbsp;&nbsp;&nbsp;&nbsp;</option>");
		}
	});
	showAdailyTalk();
}

function replaceHTML(str){
	  str = str.replace(/<[^>].*?>/g,"");
	  str = str.replace(/ /g,"");
	  return str;
	}
// 添加
function addADailyTalkInfo() {
	var className = $("#examineeClass").find("option:selected").text().trim(); // 班级编号
	var name = $("#examineeName").find("option:selected").text().trim(); // 学生姓名
	var num=name.lastIndexOf("_");
	name=name.substring(0, num);
	var knowledge = $("#knowledgeInfo").val().trim(); // 新技术内容
	if (className == "--请选择--") {
		alert("请选择要添加的班级");
		return;
	}
		
	if (name == "--请选择--") {
		alert("请选择讲解的学生");
		return;
	}
	
	if (knowledge == "" || knowledge == null || knowledge == "null") {
		alert("请输入要添加的新技术内容");
		return;
	}
	$("#historyInfobtn").removeAttr("disabled");

	$.post("/Examination2.0/checking_dailyTalk.action", {
		className : className,
		name : name,
		knowledge : knowledge
	}, function(data) {
		//data=eval( data );
		//alert( data.responseCode );
		if (data.responseCode == 0) {
			alert("添加成功");
			changeExamineeClasses(null);
		}
	});
	
	
}

function resetInfo() {

}
//显示每日一讲没讲的人
function showAdailyTalk() {
	
	$("#commitAdailytalk").hide();
	$("#showAdailytalk").hide();
	var str="";
	$.ajaxSettings.async = false;
	var className = $("#examineeClass").find("option:selected").text().trim(); // 班级编号
	if(className==null || className=="" || className=="--请选择--"){
		alert("请选择班级");
		return;
	}
	str = "<table id='mytable' width='90%' align='center' border='1' cellpadding='1'  bgcolor='#EDECEB' bordercolor='#FFFFFF' cellspacing='0'>";
	var status = 1;
	$.post("/Examination2.0/checking_showDailyTalk.action",{status:status,className:className},function(data) {
		if(data.responseCode==0){
			for(var i=0;i<data.obj.length;i++){
				str+="<tr height='23' id='"+(i+1)+"' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB';>";
				str+="<td width=10% align='center'>"+(i+1)+"</td>";
				str+="<td width=15% align='center'>"+data.obj[i].name+"</td>";
				str+="<td width=60%>"+decodeURIComponent(data.obj[i].knowledge)+"</td>";
				str+="<td align='center'><a href=javascript:speakKnowledgeInfo('"+data.obj[i].name+"',"+data.obj[i].id+")>&nbsp;[开讲]</a>";
				str+="<a href=javascript:delKnowledgeInfo1("+(i+1)+","+data.obj[i].id+")>&nbsp;[删除]</a>";
				str+="</td></tr>";
			}
			
		}else{
			str+="<tr height='23' id='"+(i+1)+"' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB';>";
			str+="<td align='center' colspan='4'> 暂无数据"
			str+="</td></tr>";
		}
	});
	str += "</table>";

	$("#showKnowledgeInfoDiv").html(str);
	$("#knowledgeInfo").val("");
}
function showinfo() {
	str = "<table id='mytable' width='90%' align='center' border='1' cellpadding='1'  bgcolor='#EDECEB' bordercolor='#FFFFFF' cellspacing='0'>";
	str+="<tr height='23' id='"+(1)+"' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB';>";
	str+="<td align='center' colspan='4'> 暂无数据"
	str+="</td></tr>";
	str += "</table>";
	$("#showKnowledgeInfoDiv").html(str);
}
//删除
function delKnowledgeInfo1(flag,ids){
	if(confirm("您确定要删除此安排吗?")){
		$.post("/Examination2.0/checking_delSpeak.action",{id:ids},function(data) {
			if(data.responseCode==0){
				alert("操作成功");
				showAdailyTalk();
				changeExamineeClasses(null);
			}
		});
	}
	
}

//开讲
function speakKnowledgeInfo(name,id){
	ide=id;
	$("#commitAdailytalk").show();
	$.post("/Examination2.0/checking_getKnow.action", {
		id : id,
	}, function(obj) {
		$("#StuName").html(decodeURIComponent(name));
		$("#StuContent").html(obj.knowledge);
		var time = changeTime();
		$("#StuDate").html(time);
	});

}

//点击提交
function addInfoById() {
	
	var assessment = $("#knowledgeInfoAssess").val();
	if (confirm("您确定要提交?")) {
		$.post("/Examination2.0/checking_startSpeak.action", {
			id : ide,
			assessment : assessment
		}, function(data) {
			if (data.responseCode == 0) {
				alert("操作成功");
				$("#commitAdailytalk").hide();
				$("#knowledgeInfoAssess").val("");
				showAdailyTalk();
			}
		});
	}
}
//改变时间
function changeTime(time) {
	if (time == null) {
		var now = new Date();
	} else {
		var now = new Date(time);
	}

	var year = now.getFullYear(); //年
	var month = now.getMonth() + 1; //月
	var day = now.getDate(); //日

	//    var hh = now.getHours();            //时
	//    var mm = now.getMinutes();          //分

	var clock = year + "-";

	if (month < 10)
		clock += "0";

	clock += month + "-";

	if (day < 10)
		clock += "0";

	clock += day + " ";

	//    if(hh < 10)
	//        clock += "0";

	//    clock += hh + ":";
	//    if (mm < 10) clock += '0'; 
	//    clock += mm; 
	return (clock);
}

//显示历史记录
function showHistoryInfoInfo() {
	$("#speak").show();
	$("#nospeak").hide();
	var cid=$("#examineeClass").val();  //班级编号
	var className = $("#examineeClass").find("option:selected").text().trim(); // 班级编号
	if(cid==0||cid=="0"||cid=="--请选择--"){
		alert("请选择查询的班级");
		str = '<tr height="23"  bgcolor="#EDECEB" onmouseover=this.bgColor="#93BBDF" onmouseout=this.bgColor="#EDECEB";><td align="center" colspan="6"> 暂无数据</td></tr>';
		$("#showResultListInfoTeacher1").html(str);
		return;
	}
	var status=1;
	var name=null;
	var startdate=null;
	var enddate=null;
	var str=null;
	$.getJSON("/Examination2.0/checking_showHistory.action",
					{
						status : status,
						className : className,
						name : name,
						startdate : startdate,
						enddate : enddate,
					},
					function(data) {
						
						if (data.responseCode == 0) {
								for (var i = 0; i < data.obj.length; i++) {
									str += "<tr height='23' id='"
											+ (i + 1)
											+ "' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB';>";
									str += "<td width=10% align='center'>"
											+ (i + 1) + "</td>";
									str += "<td width=12% align='center'>"
											+ data.obj[i].name + "</td>";
									str += "<td width=37%>" + data.obj[i].knowledge
											+ "</td>";
									str += "<td align='center' width='15%'>"
											+ data.obj[i].speakdate.substr(0, 10)
											+ "</td>";
									
									
									if (data.obj[i].status == 2) {
										str += "<td align='center' width='20%'><font style='color:red;font-size:12px'>[未传]</font><a href='javascript:delKnowledgeInfo("
												+ data.obj[i].id
												+ ")'>[删除]</a><a  href='javascript:void(0)' onClick=detailAdailytalk(\'"
												+ data.obj[i].name+"','"+data.obj[i].id
												+ "\') title='查看详细内容'>[详细]</a></td>"
									} else if (data.obj[i].status == 3) {
										str += "<td align='center' width='20%'><font style='color:blue;font-size:12px'>[下载]</font><a href='javascript:delKnowledgeInfo("
												+ data.obj[i].id
												+ ")'>[删除]</a><a style='font-size:12px' title='"
												+ data.obj[i].assessment
												+ "'>[详细]</a></td>"
									}
	
									str += "</td></tr>";
								}
						} else {
							str = '<tr height="23"  bgcolor="#EDECEB" onmouseover=this.bgColor="#93BBDF" onmouseout=this.bgColor="#EDECEB";><td align="center" colspan="6"> 暂无数据</td></tr>';
						}
					});
	$("#showResultListInfoTeacher1").html(str);
	$("#knowledgeInfo").val("");
}
function detailAdailytalk (name,id){
	$("#showAdailytalk").show();
	$.post("/Examination2.0/checking_getKnow.action", {
		id : id,
	}, function(obj) {
		$("#StuName1").html(decodeURIComponent(name));
		$("#StuContent1").html(decodeURIComponent(obj.knowledge));
		$("#knowledgeInfoAssess1").html(obj.assessment);
		var time = changeTime();
		$("#StuDate1").html(time);
	});
}
//删除记录
function delKnowledgeInfo(ids) {
	if (confirm("您确定要删除吗?")) {
		$.post("/Examination2.0/checking_delSpeak.action", {
			id : ids
		}, function(data) {
			if (data.responseCode == 0) {
				showHistoryInfoInfo();
			}
		});
	}
}
//重置按钮
function reset() {
	$("#StuName1").html("");
	$("#StuContent1").html("");
	$("#knowledgeInfoAssess1").html("");
	$("#StuDate1").html("");
	
	$("#StuName").html("");
	$("#StuContent").html("");
	$("#knowledgeInfoAssess").html("");
	$("#StuDate").html("");
	
}
function showadatalkInfoInfo() {
	$("#nospeak").show();
	$("#speak").hide();
	showAdailyTalk();
}