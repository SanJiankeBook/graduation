<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!--  
<%@ taglib uri="http://www.hyycinfo.com" prefix="yc" %>	-->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<link rel="stylesheet" href="../../css/szindex.css" type="text/css"></link>
<link rel="stylesheet" href="../../css/checking.css" type="text/css"></link>
<script type="text/javascript" src="../../js/dateChoose.js"></script>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->
<script type="text/javascript">

	!window.jQuery
			&& document
					.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" src="../../js/checking.js"></script>
<style type="text/css">
div {
	white-space: nowrap;
}
.find_table_slelct{
border: 1px solid #F8D880 ;
border-radius:5px;
width: 100%;
margin-top: 10px;
background-color: #F3F3F3;
padding-bottom: 10px;
}
</style>


<form name="myform" width="100%">
	<div id="bigbox" width="100%" style="text-align:center;">
		<div style="width: 100%;" class="find_table_slelct">
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
			<tr height="43">
				<td width="10%" align="right">学期编号：&nbsp;</td>
				<td width="5%"><input name='semester' id="semesterS1" type='radio' onClick="checkSemesters('S1')" value='S1' checked="checked" />S1</td>
				<td width="5%"><input name='semester' id="semesterS2" type='radio' onClick="checkSemesters('S2')" value='S2' />S2</td>
				<td width="7%"><input name='semester' id="semesterS3" type='radio' onClick="checkSemesters('S3')" value='S3' />S3</td>
				<td width="20%">考勤班级：&nbsp; <select name='examineeClass' class='select2' id='examineeClass' onchange="changeClazz()">
						<option value="0" selected="selected">--请选择--</option>
				</select>
				</td>
				<td width="18%" id="systemUser_userName">考勤教员：</td>
				<td>考勤日期：&nbsp; <input type="text" value="" id="checkDate" name="checkDate" onclick="fPopCalendar(event,this,this)" onblur="changeClasses()" readonly="readonly" />&nbsp; 
				<yc:dayPartSelect name="dateTime" id="dateTime" className="select2"></yc:dayPartSelect>
				</td>
			</tr>
		</table>
		</div>
		<hr style="border: 1px dashed blue;" />
		<br />
		<div style="width: 100%">
			<div style="width: 58%;float: left;">
				<table width="100%"  border="1" cellpadding="1" bordercolor="#FFFFFF" cellspacing="1">
					<tr height="25">
						<th bordercolor="#7EA6DC" width="8%">编号</th>
						<th bordercolor="#7EA6DC" width="12%">学员姓名</th>
						<th bordercolor="#7EA6DC" width="50%">到勤状态</th>
						<th bordercolor="#7EA6DC">备注</th>
						<th bordercolor="#7EA6DC">操作</th>
					</tr>
				</table>
				<div id="checkClassInfo" style="height: 400px;overflow: auto;">
					<table  border="1" cellpadding="1" bordercolor="#FFFFFF" cellspacing="1">
						<tr height="30">
							<td align="center" colspan="4"><span class="fontColor" style="color: red; font-weight: bold; font-size: 20px;">请在上面选择要考勤的班级</span></td>
						</tr>
				</table>
			</div>
			</div>
			<div id="totalDate" style= "width: 40%; float:right">
			</div>
		</div>
		
		
		<div id="beizhu">
			<div id="remarkinfo">
			<br/>
			 <hr style="border: 1px dashed blue;" /> <br/>
					<h3>教课备注：</h3><br/>
				<textarea cols="150" rows="3" id="remarkInfo"></textarea>
			</div>
			
			<div id="remarkcommit">
			<br/>
				 <input name="btnView" type="button" onClick="CheckingResultInfo()" class="inp_L1" id="btnView" onMouseOver="this.className='inp_L2'" onMouseOut="this.className='inp_L1'" value="提交"
					disabled="disabled">
			</div>
			<br />
		</div>
	</div>

</html>