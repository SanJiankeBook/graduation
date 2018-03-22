<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- 
<%@ taglib uri="http://www.hyycinfo.com" prefix="yc"%> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../jslib/themes/default/easyui.css"
	type="text/css"></link>
<link rel="stylesheet" href="../../css/szindex.css" type="text/css"></link>
<link rel="stylesheet" href="../../css/checking.css" type="text/css"></link>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->
<script type="text/javascript">
	!window.jQuery
			&& document
					.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" src="../../js/teacherInterviewdetail.js"></script>
<script type="text/javascript" src="../../js/dateChoose.js"></script>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<body>
<body>
	<div
		style="border: 1px solid #0777D9; background-color: #E8FFFA; width: 300px; height: 25px; float: left; border-radius: 5px; margin-top: 10px; text-align: center; line-height: 25px;">
		年份 :
		<yc:yearSelect id="Interview_year"></yc:yearSelect>
		月份 :
		<yc:monthSelect id="Interview_month"></yc:monthSelect>
		&nbsp;&nbsp; <input type="button" id="btn_teacherworkpool"
			onclick="finddata_tescherInterview()" value=" 查询 ">
	</div>
	<br />
	<br />
	<br />
	<br />
	<hr>
	<br />
	<table width="80%" id="showteacherInterview_pool" border="1"
		cellpadding="0" cellspacing="0">
	</table>

	<br>
	<br>
	<br>
	
	<hr />
	<div style="width: 100%; margin-top: 20px;">
	
		<div id="page_teacherInterview_show" style="display: none; margin-top: -20px;float: left;width: 45%;height:500px; overflow: auto;">
			<br>
			<br>
			<table width="95%" id="showteacher_workdetail" border="1"
				cellpadding="0" cellspacing="0" bordercolor="#FFFFFF"></table>
		</div>
		<div style="width: 43%; float: left; margin-left: 30px">
			<div id="showInterviewRecordTo" name="showInterviewRecordTo">
				<div id="showInterviewRecord" name="showInterviewRecord"
					style="display: none;">
					<div
						style="background-color: #E8F2FE; border-radius: 20px; text-align: center;">访谈记录详细信息显示</div>
					<table width="460px" border="1" cellspacing="0"
						style="text-align: center; border-radius: 15px">
						<tr height="43">
							<td width="15%" class="titlestyle"
								style="border-left: none; border-top: none">班级：<span
								id="className2"></span></td>
							<td width="20%" class="titlestyle">学生名：<span
								id="studentName2"></span></td>
							<td width="20%" class="titlestyle">教师名：<span
								id="teacherName2"></span></td>
							<td width="20%" class="titlestyle"
								style="border-right: none; border-top: none">日期：<span
								id="pdate2"></span></td>
						</tr>

						<tr height="43">
							<td class="titlestyle">地址：<span id="interviewAddress2"></span></td>
							<td colspan="3" class="titlestyle">备注：<span id="remark2"></span></td>

						</tr>
						<tr>
							<td colspan="4"
								style="border-bottom: none; border-left: none; border-right: none"><span
								style="width: 200px; height: 30px; font-size: 20px">访谈记录内容显示</span>
								<hr style="width: 300px" />

								<div name='content2' class="content" id="content2"
									style="border: none"></div> <br /> <br /> <br /> <!-- <input type="text" name='question'  class="content" onBlur="resetInfo()"
							id="question"></input> --></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		</div>
</body>