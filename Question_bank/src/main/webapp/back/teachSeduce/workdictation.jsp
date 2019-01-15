<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>布置默写</title>
<script type="text/javascript">
	!window.jQuery
			&& document
					.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<link rel="stylesheet" href="../../css/checking.css"
	type="text/css"></link>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->
<script type="text/javascript" src="../../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../../js/workdictation.js"></script>
<script type="text/javascript" src="../../jslib/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../jslib/locale/easyui-lang-zh_CN.js"></script>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
</head>

<style type="text/css">
.must_red {
	color: red;
	font: bold;
}
.select_table{
border: solid blue 2px;
}
#check_show_select{
border: 1px solid #F8D880 ;
border-radius:5px;
width: 90%;
margin:0 auto;
margin-top: 10px;
background-color: #E8F2FE;
}

#showlist_dictationwork tr th{
background: #3FABD6;
}

</style>
<body style="margin: 0 auto">
	<div id="check_show_select">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr height="30">
				<td  width="15%" align="right">学期编号：&nbsp;</td>
				<td width="5%"><input name='semester' id="dictation_semesterS1"
					type='radio' onClick="checkSemesters('S1')" value='S1'
					checked="checked" />S1</td>
				<td width="5%"><input name='semester' id="dictation_semesterS2"
					type='radio' onClick="checkSemesters('S2')" value='S2' />S2</td>
				<td width="5%"><input name='semester' id="dictation_semesterS3"
					type='radio' onClick="checkSemesters('S3')" value='S3' />S3</td>
				<td width="30%">检查班级：&nbsp; <select name='dictation_examineeClass'
					class='select2' id='dictation_examineeClass'
					onchange="dictation_findclassinfo()">
						<option value="0" selected="selected">--请选择--</option>
				</select> <span class="must_red"> *</span>
				</td>
				<td width="50%" align="left"><input type="button" value="查询"
					onclick="dictation_findclassinfo()"
					style="display: inline-block; width: 40px; height: 30px"></td>
			</tr>
		</table>
	</div>
	<br>
	<hr>
	<br/>
		<table id="showlist_dictationwork" width="80%" border="1" cellpadding="1"
			bordercolor="#FFFFFF" cellspacing="1"  style="margin:0 auto"
			>
		</table>
</body>
</html>