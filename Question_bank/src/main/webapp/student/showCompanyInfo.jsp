<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


</head>

<body>
	<style type="text/css">
.sel_addr {
	border: 1px solid #FFC63F;
	width: 80%;
	border-radius: 5px;
	padding: 10px 5px;
	margin: 10px 20px;
	padding-left: 50px;
	background: #E8F2FE;
}

.black_5 {
	display: inline-block;
	width: 5px;
}

.black_10 {
	display: inline-block;
	width: 10px;
}

#div_show_enterprise_detail {
	border: 1px solid #88B76F;
	padding: 10px 30px;
	padding-left: 50px;
	border-radius: 10px;
	background: #83A2B2;
}

#div1{
            width:300px;
            height:200px;
            background: #ccc;
            position: absolute;
            z-index: 40;
            top:100px;
            left:300px;
            overflow: hidden;
            opacity: 0.5;
            display: none;
        }
#div2{
            position: absolute;
            z-index: 50;
            width:300px;
            height:200px;
            top:100px;
            left:300px;
            display: none;
}
</style>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
	<base href="<%=basePath%>" />
 	<link id="easyuiTheme" rel="stylesheet" type="text/css" href="jslib/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="jslib/themes/icon.css"> 
	<script language="javascript" src="js/PCASClass.js"></script>
	<script language="javascript" src="student/js/distpicker.data.js"></script>
	<script language="javascript" src="student/js/distpicker.js"></script>
	<script language="javascript" src="student/js/main.js"></script>
	<script type="text/javascript" src="jslib/jeasyui.common.js"></script> 
	

	<form class="form-inline">
		<div id="distpicker5" class="sel_addr">
			<label class="sr-only" for="province10">省</label> <select
				class="form-control" id="province10"></select> <label
				class="sr-only" for="city10">市</label> <select class="form-control"
				id="city10"></select> <label class="sr-only" for="district10">区</label>
			<select class="form-control" id="district10"></select> <span
				class="black_10"></span><span class="black_10"></span> 请输入公司名称: <input
				id="enterprisename" type="text" /> <span class="black_10"></span><span
				class="black_10"></span> <input type="button" value="查询"
				onclick="click_find_entreprise()" style="width: 70px;" />
		</div>
	</form>

	<!-- <div class="sel_addr">
		<select name="province"></select> <span class="black_5"></span> <select
			name="city">
		</select><span class="black_5"></span> <select name="area">
		</select><span class="black_10"></span> 请输入公司名称: <input id="enterprisename" type="text" /> <span
			class="black_10"></span><span class="black_10"></span> <input
			type="button" value="查询" onclick="click_find_entreprise()"
			style="width: 70px;" />
	</div> -->
	<br />
	<hr />
	<br />
	<table width="50%" style="float: left; margin-left: 10px;">
		<thead>
			<tr>
				<th align="center">编号</th>
				<th align="center">公司名称</th>
				<th align="center">公司地址</th>
				<th align="center">公司类型</th>
				<th align="center">就职人数</th>
				<th align="center">操作</th>
			</tr>
		</thead>
		<tbody id="show_enterpriseinfo">

		</tbody>
	</table>

	<div>
		<div id="div_show_examinee_job"
			style="width: 40%; float: left; display: none; margin-left: 50px;">
			<table style="width: 80%">
				<thead>
					<tr>
						<th align="center">编号</th>
						<th align="center">学员名称</th>
						<th align="center">入职工资</th>
						<th align="center">入职年份</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody id="tbody_show_examinee_detail">

				</tbody>
			</table>
		</div>
		<div id="div_show_enterprise_detail"
			style="float: left; display: none; margin-left: 50px;">
			<table id="table_show_enterprise_detail">
			</table>
		</div>
	</div>
	
	<div id="div1"></div>
	
	<div id="div2">
		<table  id="table_show_examinee_info" style="margin-top:20px;;width: 100%; "  border="0" cellspacing="0" cellpadding="0" >
		
		</table>
		
		<button style="margin: 0 auto; margin-left: 100px;margin-top: 20px;"  onclick="close_detail()">关闭</button>
	</div>
	
	<script language="javascript" defer>
		/* new PCAS("province", "city", "area", "--请选择省份--", "--请选择城市--",
				"--请选择地区--"); */

		function click_find_entreprise() {
			var name = $("#enterprisename").val().trim();
			var province = $("#province10").val().trim();
			var city = $("#city10").val().trim();
			var area = $("#district10").val().trim();
			var addr = "";
			if (province != "—— 省 ——") {
				addr += province
			}

			if (city != "—— 市 ——") {
				addr += city
			}

			if (area != "—— 区 ——") {
				addr += area;
			}

			$
					.post(
							'enterprise_findenterprise.action',
							{
								addr : addr,
								name : name
							},
							function(data) {
								$("#show_enterpriseinfo").html("<tr><td colspan'6'><h2>无数据</h2></td></tr>")
								if (data.responseCode == 1) {
									$("#show_enterpriseinfo").html(str)
									return;
								}

								data = data.obj;
								
								var str = "";
								var count = 0;
								for ( var i in data) {
									if (data[i].ename == undefined) {
										continue;
									}
									count++;
									str += "<tr bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'>"
									str += '<td align="center">' + count
											+ '</td>'
									str += '<td align="center">'
											+ data[i].ename + '</td>'
									str += '<td align="center">'
											+ data[i].address + '</td>'
									str += '<td align="center">' + data[i].type
											+ '</td>'
									str += '<td align="center"><a href="javascript:find_enterprise_num(\''
											+ data[i].eid
											+ '\')">'
											+ data[i].worknum + '</a></td>'
									str += '<td align="center"><a href="javascript:find_enterprise_detail(\''
											+ data[i].eid
											+ '\')">查看详情</a></td>'
									str += "</tr>";
								}
								$("#show_enterpriseinfo").html(str)
							}, 'json')

		}

		function find_enterprise_num(eid) {
			$("#tbody_show_examinee_detail").html("")
			$("#div_show_enterprise_detail").css("display", "none");
			$("#div_show_examinee_job").css("display", "block");

			$
					.post(
							'job_findexamineejob.action',
							{
								eid : eid
							},
							function(data) {
								if (data.responseCode == 1) {
									$("#tbody_show_examinee_detail")
											.html(
													"<tr><td colspan'5'><h2>无数据</h2></td></tr>")
									alert(data.errorMessage);
									return;
								}
								var num = 0;
								data = data.obj;
								var str = "";
								for ( var i in data) {
									if (data[i].examineename == undefined) {
										continue;
									}
									num++;
									str += "<tr bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'>"
									str += '<td align="center">' + num
											+ '</td>';
									str += '<td align="center">'
											+ data[i].examineename + '</td>';
									str += '<td align="center">'
											+ data[i].salary + '</td>';
									str += '<td align="center">'
											+ data[i].entrydate + '</td>';
									str += '<td align="center"><a href="javascript:findexamineedateail(\''
											+ data[i].examineeid
											+ '\')">查看学员信息</a></td>';
									str += '</tr>'
								}
								$("#tbody_show_examinee_detail").html(str)
							}, 'json')
		}

		
		
		
		function findexamineedateail(examineeid) {
			$.post('job_findexamineedetail.action',{examineeid:examineeid},function(data){
				if (data.responseCode == 1) {
					alert(data.errorMessage);
					return;
				}
				data=data.obj;
				var str="";
				str+='<tr bgcolor="#EDECEB" height="25"><td align="right">名字:</td><td>'+data.name+'</td></tr>';
				str+='<tr bgcolor="#EDECEB" height="25"><td align="right">电话:</td><td>'+changenull(data.tel)+'</td></tr>';
				str+='<tr bgcolor="#EDECEB" height="25"><td align="right">qq:</td><td>'+changenull(data.qq)+'</td></tr>';
				str+='<tr bgcolor="#EDECEB" height="25"><td align="right">邮箱:</td><td>'+changenull(data.email)+'</td></tr>';
				str+='<tr bgcolor="#EDECEB" height="25"><td align="right">微信:</td><td>'+changenull(data.weixin)+'</td></tr>';
				$("#table_show_examinee_info").html(str)
				$("#div1").show(1000);
	            $("#div2").show(1000);
			},'json')
		}

		function close_detail(){
			$("#div1").hide(1000);
            $("#div2").hide(1000);
		}
		
		
		
		function find_enterprise_detail(eid) {

			$.post('enterprise_findenterprisedetail.action', {
				eid : eid
			}, function(data) {
				if (data.responseCode == 1) {
					alert(data.errorMessage);
					return;
				}
				data = data.obj;
				$("#table_show_enterprise_detail").html("");
				$("#div_show_examinee_job").css("display", "none");
				$("#div_show_enterprise_detail").css("display", "block");
				var str = "";
				str += '<tr height="25"><td>公司名称:</td><td>' + data.ename
						+ '</td></tr>';
				str += '<tr height="25"><td>公司类型:</td><td>' + data.type
						+ '</td></tr>';
				str += '<tr height="25"><td>公司地址:</td><td>'
						+ changenull(data.address) + '</td></tr>';
				str += '<tr height="25"><td>公司名称:</td><td>' + data.ename
						+ '</td></tr>';
				str += '<tr height="25"><td>公司信息:</td><td>'
						+ changenull(data.etelephone) + '</td></tr>';
				str += '<tr height="25"><td>公司信息:</td><td>'
						+ changenull(data.eFax) + '</td></tr>';
				str += '<tr height="25"><td>公司信息:</td><td>'
						+ changenull(data.ePost) + '</td></tr>';
				str += '<tr height="25"><td>公司信息:</td><td>'
						+ changenull(data.eURL) + '</td></tr>';
				str += '<tr height="25"><td>公司信息:</td><td>'
						+ changenull(data.eDescription) + '</td></tr>';
				$("#table_show_enterprise_detail").html(str);
			}, 'json')

		}

		function changenull(data) {
			if (data == null || data == "" || data == undefined) {
				return "无";
			}
			return data;
		}
	</script>
</body>


</html>