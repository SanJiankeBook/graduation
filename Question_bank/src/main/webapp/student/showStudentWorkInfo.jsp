<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--  
<%@ taglib uri="http://www.hyycinfo.com" prefix="yc"%>-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


</head>


<body style="margin: 0 auto">
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
	<base href="<%=basePath%>" />

	<style type="text/css">
.body_left {
	width: 100%;
	float: left;
	height: 100%;
}

body {
	width: 100%;
}

.body_rigth {
	width: 100%;
	float: left;
}

.salary_rank {
	width: 100%;
}

.salary_year {
	width: 100%;
}

.salary_last_year {
	width: 50%;
	float: left;
}

.salary_year_before {
	width: 50%;
	float: left;
}

.salary_year_this {
	width: 100%;
	float: left;
}

.map {
	width: 800px;
	height: 800px;
}

#show_province_job_detail {
	width: 100%;
	float: left;
}

.title_span {
	display: block;
	width: 60%;
	height: 20px;
	line-height: 20px;
	text-align: center;
	font-size: 16px;
	border: 1px solid #B76610;
	border-radius: 5px;
	margin: 0 auto;
	padding: 4px 0px;
	background: #DCF5ED;
	font-weight: 800;
	background: #DCF5ED;
}
</style>
	<div class='body_left'>
		<iframe src="student/map.jsp" width="100%" height="100%" claas="map"></iframe>
	</div>
	<!-- <div class='body_rigth' style="display: block">
		<div class="salary_rank">
			<span class="title_span">薪水排行榜</span>
			<table style="width: 100%;">
				<thead>
					<tr>
						<th align="center">编号</th>
						<th align="center">学员名称</th>
						<th align="center">入职工资</th>
						<th align="center">入职年份</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody id="table_rank_salary">

				</tbody>
			</table>
		</div>
		<div class="salary_year">
			<div class="salary_last_year">
				<span class="title_span">2016年薪资排行</span>
				<table style="width: 100%;">
					<thead>
						<tr>
							<th align="center">编号</th>
							<th align="center">学员名称</th>
							<th align="center">入职公司</th>
							<th align="center">入职工资</th>
							<th align="center">入职年份</th>
							<th align="center">操作</th>
						</tr>
					</thead>
					<tbody id="table_last_year_salary">

					</tbody>
				</table>
			</div>
			<div class="salary_year_before">
				<span class="title_span">2015年薪资排行</span>
				<table style="width: 100%;">
					<thead>
						<tr>
							<th align="center">编号</th>
							<th align="center">学员名称</th>
							<th align="center">入职公司</th>
							<th align="center">入职工资</th>
							<th align="center">入职年份</th>
							<th align="center">操作</th>
						</tr>
					</thead>
					<tbody id="table_before_year_salary">

					</tbody>
				</table>
			</div>
			<div class="salary_year_this">
				<span class="title_span">2017年薪资排行</span>
				<table style="width: 100%;">
					<thead>
						<tr>
							<th align="center">编号</th>
							<th align="center">学员名称</th>
							<th align="center">入职公司</th>
							<th align="center">入职工资</th>
							<th align="center">入职年份</th>
							<th align="center">操作</th>
						</tr>
					</thead>
					<tbody id="table_this_year_salary">

					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div id="detail_dialog" class="easyui-dialog" title="学员信息"
		style="width: 300px; height: 200px; background: #EDECEB;"
		data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false">
		<table id="table_show_examinee_info"
			style="margin: 0 auto; width: 100%;">

		</table>
	</div>



	<div id="show_province_job_detail">

		<span class="title_span">省薪资排行</span>
		<table style="width: 100%;">
			<thead>
				<tr>
					<th align="center">编号</th>
					<th align="center">学员名称</th>
					<th align="center">入职公司</th>
					<th align="center">入职工资</th>
					<th align="center">入职年份</th>
					<th align="center">操作</th>
				</tr>
			</thead>
			<tbody id="table_province_salary">

			</tbody>
			<tr>
				<td colspan="6"><span>
						<ul>
							<li id="table_province_total">总条数
							<li>
							<li id="table_province_page">当前页数
							<li>
							<li id="table_province_pages">总页数
							<li>
							<li id="table_province_firstage">首页
							<li>
							<li id="table_province_propage">上一页
							<li>
							<li id="table_province_nextpage">下一页
							<li>
							<li id="table_province_lastpage">尾页
							<li>
						</ul>
				</span></td>
			</tr>
		</table>


	</div>

	<script type="text/javascript">
		$
				.post(
						'job_findsalarystar.action',
						{},
						function(data) {
							$("#table_rank_salary").html("");
							if (data.responseCode == 1) {
								$("#table_rank_salary")
										.html(
												"<tr><td colspan='6' align='center'><h2>暂无数据</h2></td></tr>");
								return;
							}
							data = data.obj;
							var str = "";
							var count = 0;
							for ( var i in data) {
								if (data[i].examineename == undefined) {
									continue;
								}
								count++;
								str += "<tr bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'>"
								str += '<td align="center">' + count + '</td>'
								str += '<td align="center">'
										+ data[i].examineename + '</td>'
								str += '<td align="center">' + data[i].salary
										+ '</td>'
								str += '<td align="center">'
										+ data[i].entrydate + '</td>'
								str += '<td align="center"><a href="javascript:find_examinee_detail(\''
										+ data[i].examineeid
										+ '\')">查看详情</a></td>'
								str += "</tr>";
							}
							$("#table_rank_salary").html(str)
						}, 'json')

		function find_examinee_detail(examineeid) {
			$
					.post(
							'job_findexamineedetail.action',
							{
								examineeid : examineeid
							},
							function(data) {
								if (data.responseCode == 1) {
									alert(data.errorMessage);
									return;
								}
								data = data.obj;
								var str = "";
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">名字:</td><td>'
										+ data.name + '</td></tr>';
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">电话:</td><td>'
										+ changenull(data.tel) + '</td></tr>';
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">qq:</td><td>'
										+ changenull(data.qq) + '</td></tr>';
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">邮箱:</td><td>'
										+ changenull(data.email) + '</td></tr>';
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">微信:</td><td>'
										+ changenull(data.weixin)
										+ '</td></tr>';
								$("#table_show_examinee_info").html(str)
								$('#detail_dialog').dialog('open');
							}, 'json')
		}

		showdata("table_this_year_salary", 0)
		showdata("table_before_year_salary", 2)
		showdata("table_last_year_salary", 1)

		function showdata(id, year) {
			$
					.post(
							'job_findsalarybyyear.action',
							{
								year : year
							},
							function(data) {
								$("#" + id).html("");
								if (data.responseCode == 1) {
									$("#" + id)
											.html(
													"<tr><td colspan='6' align='center'><h2>暂无数据</h2></td></tr>");
									return;
								}
								data = data.obj;
								var str = "";
								var count = 0;
								for ( var i in data) {
									if (data[i].examineename == undefined) {
										continue;
									}
									count++;
									str += "<tr bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'>"
									str += '<td align="center">' + count
											+ '</td>'
									str += '<td align="center">'
											+ data[i].examineename + '</td>'
									str += '<td align="center">'
											+ data[i].province + '</td>'
									str += '<td align="center">'
											+ data[i].salary + '</td>'
									str += '<td align="center">'
											+ data[i].entrydate + '</td>'
									str += '<td align="center"><a href="javascript:find_examinee_detail(\''
											+ data[i].examineeid
											+ '\')">查看详情</a></td>'
									str += "</tr>";
								}
								$("#" + id).html(str)
							}, 'json')
		}

		function changenull(data) {
			if (data == null || data == "" || data == undefined) {
				return "无";
			}
			return data;
		}
		
	</script> -->
</body>
</html>