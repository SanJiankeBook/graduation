<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<title>月度课时统计</title>
<script src="js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	var allTeahcer = new Array();
	var dataTemp;//临时存储传过来的数据
	var MonthTotal = 0;//月度课时总和
	function initTeacher() {//加载所有的老师
		$.post("findTeacher.action", function(data) {
			if (data.responseCode == 1) {
				alert("老师查询错误,请与管理员联系");
				return;
			} else {
				var year = new Date();
				$("#year").val(year.getFullYear());
				var month=year.getMonth()+1;
				$("#month").val(month); 
				$("#teacher").html("");
				$("#teacher").append("<option value='0'>请选择老师的名字</option>");
				for (var i = 0; i < data.obj.length; i++) {
					$("#teacher").append(
							"<option value='"+data.obj[i].id+"'>"
									+ data.obj[i].userName + "</option>");
					allTeahcer[i] = data.obj[i].userName;
				}
			}
		});

	}

	//发送老师编号查询课程信息
	function findTeacherScheduleInfo() {
		MonthTotal = 0;//清空课时总计
		var month = $("#month").val();
		var year = $("#year").val();
		var tid = $("#teacher").val();
		var teacher = $("#teacher").find("option:selected").text();
	 
		var regMycard = /^[1-9]\d{3}$/;
		if (!regMycard.test(year)) {
			alert("年份填写错误,必须为数字而且长度只能4位。。。");
			return false;
		}
		$.post("findTeacherScheduleInfo.action", {
			id : tid,
			month : month,
			year : year
		}, function(data) {
			if (data.responseCode == 1) {
				alert("月度课时统计信息出错，暂无数据");
				return;
			} else {
				$("#count_table").text('');
				//月份课时总和

				/* 
				drawTable("姜平", "1");
				$("totalCount").html("");
				$("#teachSeduce").html("");//表清空
				$("#teacherName").html("");//授课人清空
				$("#totalSeduce").html("");//课时总和清空
				$("#teacherName1").html(teacher); */

				if (data.obj == null) {
					alert("暂无数据!!!");
					return;
				} else {
					var tempCount=0; //  如果大于1 则表示查全部  等于1表示查单个老师   月份总计不一样 
					 Array.prototype.removeRepeatAttr = function() {//进行数据查重（查询出每个班每个老师的唯一标志去除重复）
						var tmp = {} ,a = this.slice(); //从何处开始选取
						for (var i = j = 0; i < a.length; i++) {
							if (!tmp[a[i].uniqueName]) {
								tmp[a[i].uniqueName] = !0;
								j++;
							} else {
								this.splice(j, 1);//移除
							}
						}
					}  
					 
					var arr = data.obj;
					var arr2 = $.extend(true, [], arr);
					arr.removeRepeatAttr();
					//arr.unique();
					//console.info(arr.length);
					//////////////////////////////////////////////////////////////
					for (var i = 0; i < arr.length; i++) {//结算课时
						var num = 0;
						for (var j = 0; j < arr2.length; j++) {
							if (arr[i].className == arr2[j].className&&arr[i].teacherName == arr2[j].teacherName) {
								num++;
							}
							continue;
						}
						arr[i].teachCount *= num;
						
					}
					
					dataTemp=arr;

					//画图
					drawCount();
					for (var j = 0; j < allTeahcer.length; j++) {
						var flag = 0;
					var temp = new Array();//同一老师的数据
					//console.info(arr);
					for (var i = 0; i < arr.length; i++) {
						//console.info(arr[i]);
						//console.info( allTeahcer[j]);
						if (arr[i].teacherName == allTeahcer[j]) {
							temp[flag] = arr[i];
							flag++;
						}
					}
					if(temp.length > 0){
						tempCount++;
						drawTable(allTeahcer[j],"1");
						$("#teacherName"+allTeahcer[j]).html(allTeahcer[j]); 
						var total = 0;//课时总和
						for (var i = 0; i < temp.length; i++) {
							$("#teachSeduce"+temp[i].teacherName).append(
									"<tr><td>" + temp[i].className + "</td><td>"
											+ temp[i].teachCount + "</td><td>"
											+ temp[i].studentCount + "</td><td>"
											+ temp[i].studentCount2 + "</td></tr>")
							total += temp[i].teachCount;
							
						}
						$("#totalSeduce"+allTeahcer[j]).html(total);
						//  
						MonthTotal += total;
						
					}
					//console.info(tempCount);
					if(tempCount>1){
						$("#totalCount").html(MonthTotal);
					}
					if(tempCount==1){
						$("#totalCount").html(total);
					}
					}
				}
			}
		});
	}
	
	function drawCount(){

		var count="<p >月度课时总和: <font style='color: red;' id='totalCount'></font></p>";
		$("#count_table").append($(count));
	}

	function drawTable(teachername, id) {
		
		var table = "<table id="+teachername+" border='1px' width='800px' cellpadding='0' cellspacing='0'>";
		table += "<thead><tr><th>授课人：</th><th colspan='3' id='teacherName"+teachername+"'></th></tr>";
		table += "<tr><th>班级</th><th>课时</th><th>班级人数</th><th>班级流失人数</th></tr></thead>";
		table += "<tbody id='teachSeduce"+teachername+"'>  </tbody>";
		table += "<tr bgcolor='yellow'><td>课时总计</td> <td align='right' colspan='3' id='totalSeduce"+teachername+"'> </td> </tr>";
		table += "</table> <br/><br/>";
		$("#count_table").append($(table));

	}
	 
</script>
<script type="text/javascript">
	$(function(){
		$("#teachSed_excel").click(function(){
			if(confirm("确认导出数据吗？")){

				 dataTemp=JSON.stringify(dataTemp);
				/* console.info("dataTemp:"+dataTemp);
				dataTemp=JSON.stringify(dataTemp);
				//console.info("dataTemp:"+dataTemp);
				//document.forms[0].action="teachSeduceExportToExcel.action?dataTemp="+dataTemp+"";

				document.forms[0].submit();   */
				 $.ajax({
				        type: "post",
				        url: 'teachSeduceExportToExcel.action',
				        data: {"dataTemp":dataTemp},
				        success: function (data) {
				            $("#toExcel").hide();
				            console.info(data.obj);
				            $("#MainContent").append('<a href='+data.obj+' >请下载</a>')
				        }
				    });
			}
		})
		
	});	


</script>

</head>
<body onload="initTeacher()">
	<form action="" method="post">
		<div  id="MainContent">
			<center>
				<p>月度课时统计</p>
				请输入年份 <input type="text" name="year" id="year" size="4"
					maxlength="4">&nbsp;&nbsp; 请选择月份:<select id="month">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
				</select>&nbsp;&nbsp; 请选择老师名字：<select id="teacher"></select> <input
					type="button" value="查询" onclick="findTeacherScheduleInfo()">
			
			<span title="导出为Excel"  id="toExcel"><a href="javascript:" id="teachSed_excel"><img
						border="0" src="images/excel.png" /></a></span>
			</center>
		</div>

		<div id="count_table">
		
		</div>
	</form>


</body>
</html>