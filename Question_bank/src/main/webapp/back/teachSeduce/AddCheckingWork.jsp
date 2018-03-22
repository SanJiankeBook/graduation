<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="../../css/szindex.css" type="text/css"></link>
<link rel="stylesheet" href="../../css/checking.css" type="text/css"></link>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->
<script type="text/javascript">
	!window.jQuery
			&& document
					.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" src="../../js/AddCheckingWork.js"></script>
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

.menu_table{
border: 1px solid #F8D880 ;
border-radius:5px;
background-color: #E8F2FE;
padding-bottom: 10px;
width: 100%;
margin: 10px 5px 5px 10px;
}
</style>
<form name="work_myform" width="100%" id="work_myform">
	<div id="bigbox" width="2000px">
		<div class="menu_table">
		<table width="90%" border="0" cellpadding="0" cellspacing="0">
			<tr height="43">
				<td width="5%"></td>
				<td width="30%">学期编号：&nbsp;<input name='semester'
					id="work_semesterS1" type='radio' onClick="checkSemesters('S1')"
					value='S1' checked="checked" />S1 <input name='semester'
					id="work_semesterS2" type='radio' onClick="checkSemesters('S2')"
					value='S2' />S2 <input name='semester' id="work_semesterS3"
					type='radio' onClick="checkSemesters('S3')" value='S3' />S3
				</td>
				<td width="30%" >选择班级：&nbsp; <select name='worlexamineeClass'
					class='select2' id='work_examineeClass'>
						<option value="0" selected="selected">--请选择--</option>
				</select><span class="must_red"> * </span>
				</td>
				<td width="35%" >布置日期：&nbsp; <input type="text" id="checkDate"
					name="checkDate" readonly="readonly" style="width: 100px;"
					  />
					<span class="must_red"> * </span>
				</td>
			</tr>

			<tr >
				<td width="5%"></td>
				<td width="30%" class="fontColor">版本号 : <select name="version"
					id="work_version" onChange="changeVersion()">
				</select><span class="must_red"> * </span>
				</td>

				<td width="30%"  class="fontColor">科目 : <select name='subject'
					class='select2' id='work_subject' onChange="changeSubject()">
						<option value="0">--请选择--</option>
				</select><span class="must_red"> * </span>
				</td>
				<td width="35%"  class="fontColor">章节 : <select name='chapter'
					class='select2' id='work_chapter'>
						<option value="0">--请选择--</option>
				</select><span class="must_red"> * </span>
				</td>
				<td></td>
				<td></td>
			</tr>
			</table>
</div>			
			<br />
			<table>
				<tr height="43">
					<td>请设置要检查的作业名称：<br /></td>
					<td><input type="text" name="CheckingName" id="CheckingName"><span
						class="must_red"> * </span><br /></td>

				</tr>

				<tr height="90">
					<td>请填写作业描述:<br /></td>
					<td><textarea rows="5" cols="100" id="Checkingdescription"></textarea><br /></td>

				</tr>

				<tr height="80">
					<td>备注：<br /></td>
					<td><textarea rows="5" cols="100" id="CheckingRemark"></textarea><br /></td>
				</tr>
				<tr height="43">
					<td></td>
					<td align="center"><input type="button"
						onclick="submit_check()" value="提交" style="display: inline-block;width: 40px;height: 30px"></td>
				</tr>
				<tr height="43">
					<td></td>
					<td align="center"><label id="work_error" name="error"></label></td>
				</tr>
			</table>
			</div>

			</form>