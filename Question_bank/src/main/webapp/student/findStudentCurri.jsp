<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" language="javascript"
	src="../js/jquery-1.9.1.js"></script>

<script type="text/javascript" src="../js/dateChoose.js"></script>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" /> 
<style type="text/css">
#col {
	background: #FF99CC;
}
</style>
<style>
#classCurriFind_form table {
	width: 90%;
	float: left;
	margin-left: 5%;
	margin-top: 50px;
	height: 850px;
	border: 1px solid #000000;
	text-align: center;
	margin-bottom: 20px;
}

#classCurriFind_form table tr {
	border: 1px solid #000000;
	height: 10%;
}

#classCurriFind_form table td {
	border: 1px solid #000000;
	width: 12.5%;
	font-size: 13px;
}

#classCurriFind_form table th {
	border: 1px solid #000000;
	width: 12.5%;
}

#classCurriFind_form table td p {
	text-align: center;
	line-height: 50%;
}
</style>

	<div id="classCurriFind_conditionDiv">
		<form action="" method="post">
			查询日期：<input type="text" id="startDate" name="startDate"
				readonly="readonly" onclick="fPopCalendar(event,this,this)"
				onfocus="this.select()" /> <input style="visibility: hidden"
				type="text" id="endDate" name="endDate" readonly="readonly"
				onclick="fPopCalendar(event,this,this)" onfocus="this.select()" />
				<!-- <input type="text" value="" id="checkDate" name="checkDate"
											onclick="fPopCalendar(event,this,this)" /> -->

			班级：<select id="classCurriFind_selectSubcondition">
				
			</select> <input type="button" onClick="classCurriInfo()" value="查询" />
			<!-- <input type='button'  onClick='exportExcel()' value='导出Excel' /> -->
		<!-- 		<span title="导出为Excel"><a href="javascript:" id="excel"><img
						border="0" src="images/excel.png" /></a></span> -->
			 
		</form>
	</div>
	<div id="classCurriFind_showDiv">
		<form id="classCurriFind_form" action="classCurriFind.action"
			method="post"></form>
	</div>

<script type="text/javascript">
	$("#startDate").change(function() {
		alert($("#startDate").val());

		var date = $('#startDate').val();
		var d = new Date(date);//这里日期是传递过来的，可以自己改
		d.setDate(d.getDate() + 7);
		date = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();//新日期
	});

	var allClassName = new Array();
	$(function() {
		var test='<%=basePath%>';
		var className=localStorage.getItem("student_className");
		
		$.post(test +"examineeclass_findClassById.action", {
			examClass : className,
		},function(data) {
			 if (data.responseCode == 1) {
				alert("班级查询错误,请与管理员联系");
				return;
			} else { 
				var obj=data.obj;
				$("#classCurriFind_selectSubcondition").append(
						"<option value="+obj[0][0]+">"
								+ className + "</option>");
				allClassName[0] = className;
				
			}
		});
		
	});

	function classCurriInfo() {
		var startDate = $("#startDate").val();
		//alert(startDate);
		if (startDate == "") {
			alert("请选择日期");
			return;
		}
		var endDate;
		var classid = $("#classCurriFind_selectSubcondition").find(
				"option:selected").val();
		var date;
		endDate = new Date(startDate);//这里日期是传递过来的，可以自己改
		endDate.setDate(endDate.getDate() + 6);
		if (endDate.getDate() < 10) {
			if (endDate.getMonth() + 1 < 10) {
				endDate = endDate.getFullYear() + "-0"
						+ (endDate.getMonth() + 1) + "-0" + endDate.getDate();//新日期	
			} else {
				endDate = endDate.getFullYear() + "-"
						+ (endDate.getMonth() + 1) + "-0" + endDate.getDate();//新日期
			}

		} else {
			if (endDate.getMonth() + 1 < 10) {
				endDate = endDate.getFullYear() + "-0"
						+ (endDate.getMonth() + 1) + "-" + endDate.getDate();//新日期	
			} else {
				endDate = endDate.getFullYear() + "-"
						+ (endDate.getMonth() + 1) + "-" + endDate.getDate();//新日期
			}
		}

		$.post("classCurriFind_getAllTeacherCurriInfo.action", {
			startDate : startDate,
			endDate : endDate,
			classid : classid,
		}, function(data) {
			if (data.obj.length <= 0) {
				alert("暂无课程");
			}
			if (data.responseCode == 1) {
				alert("查询有误，请与管理员联系");
				return;
			} else {
				//$("#classCurriFind_conditionDiv").append("<input type='button'  onClick='exportExcel()' value='导出Excel' />");
				$("#classCurriFind_form").html("");

				gettheday();
				var id = 0;//课程表 的id号
				for (var i = 0; i < allClassName.length; i++) {
					var temp = new Array();//同一老师的数据
					var flag = 0;
					for (var j = 0; j < data.obj.length; j++) {
						if (data.obj[j].classname == allClassName[i]) {

							temp[flag] = data.obj[j];
							flag++;
						}
					}

					if (temp.length > 0) {
						CreateTable(allClassName[i], id);

						makea(temp, allClassName[i], id);
						id++;
					}
				}
			}
		});
	}

	var a = [];//所有满足条件的数组
	var obj = [];//满足坐标的数组
	var d1, d2, d3, d4, d5, d6, d7, d;

	function gettheday() {
		d1 = $('#startDate').val();
		d = new Date(d1);//这里日期是传递过来的，可以自己改
		d.setDate(d.getDate());
		d1 = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();//新日期
		d.setDate(d.getDate() + 1);//天数+1
		d2 = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();//新日期
		d.setDate(d.getDate() + 1);//天数+1
		d3 = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
		d.setDate(d.getDate() + 1);//天数+1
		d4 = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
		d.setDate(d.getDate() + 1);//天数+1
		d5 = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
		d.setDate(d.getDate() + 1);//天数+1
		d6 = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
		d.setDate(d.getDate() + 1);//天数+1
		d7 = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
		// console.info(d1+","+d2+","+d3+","+d4+","+d7);
	}

	//得到相对应的老师的纪录
	function makea(arr, teachername, id) {
		for (var i = 0; i < arr.length; i++) {
			if (arr[i].classname == teachername) {
				var ll = [];
				for ( var j in arr[i]) {
					ll.push(arr[i][j]);
				}
				a.push(ll);
			}
		}
		for (var i = 0; i < a.length; i++) {
			obj = maketable(a[i]);
			//console.info(obj);
			if (a[i][4] == '无') {
				a[i][4] = '';
			}
			if (a[i][9] == '无') {
				a[i][9] = '';
			}
			if (a[i][1] == '无') {
				a[i][1] = '';
			}
			// console.info(obj);
			// console.info(a[1][1]);
			$('#' + id + '').find("tr").eq(obj.a).find("td").eq(obj.b).append(
					a[i][1] + "<br>" + a[i][4] + "<br>" + a[i][9]).css({
				"background-color" : a[i][0],
				"color" : a[i][6]
			});
		}
		a = [];
	}
	//得到每个的坐标
	function maketable(arr) {
		var a, b;
		for (var i = 0; i < arr.length; i++) {
			var time = arr[8];
			switch (time) {
			case "8:30-10:30":
				a = 1;
				break;
			case "10:30-12:00":
				a = 2;
				break;
			case "14:00-15:30":
				a = 3;
				break;
			case "15:30-17:30":
				a = 4;
				break;
			case "19:00-20:30":
				a = 5;
				break;
			case "20:30-22:00":
				a = 6;
				break;

			}
			var date = arr[5];

			switch (date) {
			case d1:
				b = 1;
				break;
			case d2:
				b = 2;
				break;
			case d3:
				b = 3;
				break;
			case d4:
				b = 4;
				break;
			case d5:
				b = 5;
				break;
			case d6:
				b = 6;
				break;
			case d7:
				b = 7;
				break;
			}
		}
		return {
			a : a,
			b : b
		};
	}

	//动态生成表格
	function CreateTable(teachername, id) {
		var table = $("<table cellpadding='0' cellspacing='0'  id="+id+"><tbody>");
		table.appendTo($("#classCurriFind_form"));
		var tr1 = $("<tr><td id='col'>" + teachername + "</td> <td id='col'>"
				+ d1 + "</td> <td id='col'>" + d2 + "</td> <td id='col'>" + d3
				+ "</td> <td id='col'>" + d4 + "</td> <td id='col'>" + d5
				+ "</td> <td id='col'>" + d6 + "</td> <td id='col'>" + d7
				+ "</td></tr>");
		var tr2 = $("<tr><td id='col'>8:30-10:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
		var tr3 = $("<tr><td id='col'>10:30-12:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
		var tr4 = $("<tr><td id='col'>14:00-15:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
		var tr5 = $("<tr><td id='col'>15:30-17:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
		var tr6 = $("<tr><td id='col'>19:00-20:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
		var tr7 = $("<tr><td id='col'>20:30-22:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
		tr1.appendTo(table);
		tr2.appendTo(table);
		tr3.appendTo(table);
		tr4.appendTo(table);
		tr5.appendTo(table);
		tr6.appendTo(table);
		tr7.appendTo(table);
		$("#classCurriFind_form").append("</tbody></table>");
	}
	
	//根据对象数组中的某一个字段排序
	 function createComparisonFunction(propertyName){
	     return function(object1,object2){
	         var value1 = object1[propertyName];
	         var value2 = object2[propertyName];

	         if(value1 < value2){
	             return -1;
	         }else if(value1 > value2){
	             return 1;
	         }else{
	             return 0;
	         }
	     };
	 };
</script>