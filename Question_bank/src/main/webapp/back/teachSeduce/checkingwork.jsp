<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
 
<script type="text/javascript" src="../../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../../js/CheckingWork.js"></script>
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
width: 100%;
margin-top: 10px;
background-color: #E8F2FE;
}

#show_check_left{
width: 20%;
float: left;
}

</style>
</head>
<body style="margin: 0 auto ">
		<div id="check_show_select" >
			<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr height="30">
					<td width="10%" align="right">学期编号：&nbsp;</td>
					<td width="5%"><input name='semester' id="check_semesterS1"
						type='radio' onClick="checkSemesters('S1')" value='S1'
						checked="checked" />S1</td>
					<td width="5%"><input name='semester' id="check_semesterS2"
						type='radio' onClick="checkSemesters('S2')" value='S2' />S2</td>
					<td width="5%"><input name='semester' id="check_semesterS3"
						type='radio' onClick="checkSemesters('S3')" value='S3' />S3</td>
					<td width="20%">检查班级：&nbsp; <select name='check_examineeClass'
						class='select2' id='check_examineeClass'
						onchange="check_findclassinfo()">
							<option value="0" selected="selected">--请选择--</option>
					</select> <span class="must_red"> *</span>
					</td>
					<td width="12%" id="systemUser_userName">检查教员：</td>
					<td width="25%">布置日期：&nbsp; <input type="text" value=""
						id="check_checkdate" name="check_checkdate"
						onclick="fPopCalendar(event,this,this)" />&nbsp;之前
					</td>
					<td width="8%" align="left"><input type="button" value="查询"
						onclick="check_findclassinfo()"
						style="display: inline-block; width: 40px; height: 30px"></td>
				</tr>
			</table>
		</div><br><hr>
	<div style="width: 100%">
		<div id="show_check_left" style="display: none">
			<fieldset
				style="border: 2px solid #95b8e7; border-radius: 5px; float: left; width: 9%;">
				<legend>作业名称</legend>
				<ul id="checkingwork_tree" class="easyui-tree"></ul>
			</fieldset>
		</div>
		<table id="showlist_work" width="70%" border="1" cellpadding="1"
			bordercolor="#FFFFFF" cellspacing="1" height="200px"
			style="float: right;">
		</table>
	</div>
	
	<div id="show_dictation" style="display: none">
		<textarea  rows="20" cols="100" id="show_dictation_detail" readonly="readonly"></textarea>
	</div>
</body>
</html>