<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 
<%@ taglib uri="http://www.hyycinfo.com" prefix="yc" %>	 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
<link rel="stylesheet" href="../../jslib/themes/default/easyui.css"
	type="text/css"></link>
<link rel="stylesheet" href="../../css/szindex.css" type="text/css"></link>
<link rel="stylesheet" href="../../css/checking.css" type="text/css"></link>
<script type="text/javascript">
	!window.jQuery
			&& document
					.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->
<script type="text/javascript" src="../../js/teacherworkdetail.js"></script>

<script type="text/javascript" src="../../jslib/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../jslib/locale/easyui-lang-zh_CN.js"></script>
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
		<yc:yearSelect id="classwork_year"></yc:yearSelect>
		月份 :
		<yc:monthSelect id="classwork_month"></yc:monthSelect> &nbsp;&nbsp;
		<input type="button" id="btn_teacherworkpool"  onclick="finddata_tescherworkpool()" value=" 查询 ">
	</div>
	<br />
	<br />
	<br />
	<br />
	<hr>
	<br />
	<table width="80%" id="showteacher_pool" border="1" cellpadding="0"
		cellspacing="0">
	</table>
	
	<br><br><br>
	
	<div id="page_teacher_show" style="display: none" >
	<hr/><br><br>
		<table width="90%" id="showteacher_workdetail" border="1"
			cellpadding="0" cellspacing="0"  bordercolor="#FFFFFF"></table>
	</div>

</body>