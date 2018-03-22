<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" href="../../css/szindex.css" type="text/css"></link>
<link rel="stylesheet" type="text/css"
	href="../../jslib/themes/default/easyui.css">
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->
<script type="text/javascript">
	!window.jQuery
			&& document
					.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>

<script type="text/javascript" src="../../jslib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/layer.js"></script>
<script type="text/javascript" src="../../js/updataeexamine.js"></script>

<style>
#changeClass {
	margin-left: 130px;
	margin-top: 60px;
}

.find_table_slelct {
	border: 1px solid #F8D880;
	border-radius: 5px;
	width: 100%;
	margin-top: 10px;
	background-color: #F3F3F3;
	padding-bottom: 10px;
}

.div_1{
margin: 0 auto;
width: 80%;
}
</style>

</head>

<body>
	<form name="frmListExaminee">
		<table width="90%" align="center" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td valign="middle" height="65" align="center"><img
					src="images/t04.gif" width="595" height="33" alt="考生列表"></td>
			</tr>
			<tr>
				<td align="center">
					<table width="100%" align="center" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<td>
								<div class="find_table_slelct">
									<table width="80%" align="center" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="15%" height="36"><label align="left"
												class="fontColor">学期名称：</label></td>
											<td width="30%"><input id="semesterS1" name="semester"
												type="radio" value="S1" checked
												onClick="changeSemester(this.value);">S1 <input
												id="semesterS2" name="semester" type="radio" value="S2"
												onClick="changeSemester(this.value);">S2 <input
												id="semesterS3" name="semester" type="radio" value="S3"
												onClick="changeSemester(this.value);">S3</td>

											<td width="36%"><span id="message" class="fontColor">
											</span></td>
											<td width="20%"><span id="back"
												style="display: none; cursor: pointer; color: green;"
												onclick="backPage()">返回</span></td>
										</tr>
										<tr>
											<td>
												<div align="left" class="fontColor">
													班级编号: <br> <br>
												</div>
											</td>
											<td>
												<div align="left">
													<label> <select name="className" id="className">

													</select> <br> <br>
													</label>
												</div>
											</td>
											<td><input name="search" id="search" type="button"
												class="inp_L1" value="查 询" onclick="serchExaminee()"
												onMouseOver="this.className='inp_L2'"
												onMouseOut="this.className='inp_L1'" /></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" align="center" border="0" cellspacing="0"
									cellpadding="0">
									<tr>
										<td height="15" class="style3" id="edit_text"></td>
									</tr>
									<tr>
										<td height="15" width="60%"></td>
										<td width="40%"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	<div class="div_1">
		<table width="60%" align="center" id="listexaminee" border="1"
			align="center" cellpadding="1" style="display: none; float: left;padding-left: 20px;"
			bordercolor="#FFFFFF" cellspacing="0">
			<thead>
				<tr>
					<th bordercolor="#7EA6DC">编号</th>
					<th bordercolor="#7EA6DC">考生姓名</th>
					<th bordercolor="#7EA6DC">考生状态</th>
					<th bordercolor="#7EA6DC">操作考生</th>
				</tr>
			</thead>
			<tbody id="examineerows">

			</tbody>
		</table>

		<table width="40%" id="update_table"
			style="float: left; display: none">
			<tr>
				<td>
					<div style="width: 200px; float: left; border: 1px solid #ccc;">
						<ul class="easyui-tree" id="update_class_tree" state="closed">
						</ul>
					</div>
					<div style="float: right; width: auto;padding-right: 20px">
						<div style="height: 50px;margin-top: 50px;">
							<span>班级:</span><label id="show_update_class"></label><span id="classid" style="display: none;"></span>
						</div>
						<div >
							<input type="button" value="确认转班" onclick="changeClass()"  size="15"/>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>


</body>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
</html>
