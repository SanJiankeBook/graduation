<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../../jslib/themes/default/easyui.css"></link>
<link rel="stylesheet" href="../../css/szindex.css" type="text/css"></link>
<link rel="stylesheet" href="../../css/checking.css" type="text/css"></link>
<script type="text/javascript">
	!window.jQuery
			&& document
					.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->
<script type="text/javascript" src="../../js/findwork.js"></script>
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
#find_table_slelct{
border: 1px solid #F8D880 ;
border-radius:5px;
width: 100%;
margin-top: 10px;
background-color: #F3F3F3;
padding-bottom: 10px;
}
.must_red {
	color: red;
	font: bold;
}
#show_find_left{
float: left;
width: 12%;
margin: 10px 20px;
}

#show_work_checkcount{
float: left;
width: 18%;
height:100px;
margin-top:40px;
margin-left:10px;
border: 1px solid #E8F2FE;
border-radius: 5px;
text-align: center;
background-color: #A5EEFC;
}


#show_find_checkwork{
float: left;
width: 60%;
margin: 10px 20px;
}
.show_label_count{
font: 20px bold;

}

</style>
</head>
<body style="margin: 0 auto;">
	<form action="" name="find_work_form" id="find_work_form">
		<div id="find_table_slelct">
			<table width="90%" border="0" cellpadding="0" cellspacing="0">
				<tr height="43">
					<td width="10%"></td>
					<td width="25%" class="fontColor">学期编号：&nbsp;<input
						name='semester' id="find_work_semesterS1" type='radio'
						onClick="checkSemesters('S1')" value='S1' checked="checked" />S1<input
						name='semester' id="find_work_semesterS2" type='radio'
						onClick="checkSemesters('S2')" value='S2' />S2<input
						name='semester' id="find_work_semesterS3" type='radio'
						onClick="checkSemesters('S3')" value='S3' />S3 
						<span class="must_red"> *</span>
					</td>
					<td width="30%" class="fontColor">选择班级：&nbsp; <select
						name='worlexamineeClass' class='select2'
						id='find_work_examineeClass'>
							<option value="0" selected="selected">--请选择--</option>
					</select><span class="must_red"> *</span>
					</td>
					<td width="30%" class="fontColor">布置日期：&nbsp; <input
						type="text" id="find_checkDate" name="checkDate"
						readonly="readonly" onclick="fPopCalendar(event,this,this)"
						onfocus="this.select()" />之前
					</td>
					<td width="15%"></td>
				</tr>
				<tr>
					<td width="10%"></td>
					<td width="25%" class="fontColor">版本号 : <select name="version"
						id="find_work_version" onChange="changeVersion()">
					</select>
					</td>
					<td width="30%" class="fontColor">科目 : <select name='subject'
						class='select2' id='find_work_subject' onChange="changeSubject()">
							<option value="0">--请选择--</option>
					</select>
					</td>
					<td width="25%" class="fontColor">章节: <select name='chapter'
						class='select2' id='find_work_chapter'>
							<option value="0">--请选择--</option>
					</select> &nbsp;&nbsp;
					</td>
					<td width="10" align="left">
					<input type="button" value="查询" onclick="find_checkwork()"
						style="display: inline-block; width: 40px; height: 20px">
					</td>
				</tr>
			</table>
		</div>
	</form><br>
	<hr>
	<div id="show_find_left" style="display: none">
		<fieldset  style="border: 2px solid #95b8e7; border-radius: 5px; float: left; width: 9%;">
		<legend>作业名称</legend>
		<ul id="findwork_tree" class="easyui-tree"></ul>
		</fieldset>
	</div>
	<div id="show_work_checkcount" style="display: none;">
		
	</div>
	<table id="show_find_checkwork"  border="1" cellpadding="1"
		bordercolor="#FFFFFF" cellspacing="1">
	</table>
</body>
</html>