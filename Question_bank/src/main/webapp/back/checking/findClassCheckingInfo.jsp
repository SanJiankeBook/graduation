<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!--
<%@ taglib uri="http://www.hyycinfo.com" prefix="yc"%>  -->
<link rel="stylesheet" href="../../css/szindex.css" type="text/css"></link>
<!-- <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.js"></script> -->
<script type="text/javascript">
	!window.jQuery
			&& document
					.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" src="../../js/dateChoose.js"></script>
<script type="text/javascript" src="../../js/findcheckinginfo.js"></script>
<style type="text/css">
.show_table_detail {
	font-size: 18px;
	font-weight: bold;
}

.find_table_slelct {
	border: 1px solid #F8D880;
	border-radius: 5px;
	width: 100%;
	margin-top: 10px;
	background-color: #F3F3F3;
	padding-bottom: 10px;
}

.show_table_detail_leave {
	font-size: 15px;
	font-weight: bold;
	color: green;
}

.show_table_detail_late {
	font-size: 15px;
	font-weight: bold;
	color: red;
}

.show_table {
	border: 1px solid #FFE48D;
	background-color: #D6E3F2;
	border-radius: 6px;
}

.show_table_detail_default {
	font-size: 15px;
	font-weight: bold;
}
</style>
<div align="center" id="bigbox">
	<div class='find_table_slelct'>
		<table width="1000px" border="0px" cellpadding="0px" cellspacing="0px">
			<tr height="43">
				<td width="10%" class="fontColor linefontsize" align="right">学期编号：&nbsp;</td>
				<td width="5%"><input id="semesterS1" name='semester'
					type='radio' onClick="checkSemesters('S1')" value='S1' checked />S1</td>
				<td width="5%"><input id="semesterS2" name='semester'
					type='radio' onClick="checkSemesters('S2')" value='S2' />S2</td>
				<td width="10%"><input id="semesterS3" type='radio'
					name='semester' value='S3' onClick="checkSemesters('S3')" />S3</td>
				<td width="25%" class="fontColor linefontsize">考勤班级：&nbsp; <select
					name='examineeClass' class='selectClass' id='examineeClass'>
						<option value="0" selected="selected">--请选择--</option>
				</select> <label style="color: red; font-size: 15px;">*</label>
				</td>

			</tr>
			</tr>
			<tr height="43">
				<td width="10%" class="fontColor linefontsize" align="right">考勤日期：&nbsp;</td>
				<td align="left" class="fontColor linefontsize" colspan="5"><input
					type="text" id="startDate" name="startDate" readonly="readonly"
					onclick="fPopCalendar(event,this,this)" onfocus="this.select()" />&nbsp;
					到 &nbsp;<input type="text" id="endDate" name="endDate"
					readonly="readonly" onclick="fPopCalendar(event,this,this)"
					onfocus="this.select()" />&nbsp;<select name="dateTime" id="dateTime" class='select2'>
						<option value="0" selected="selected">时段</option>
						<option value="上午">上午</option>
						<option value="下午">下午</option>
						<option value="晚上">晚上</option>
				</select>
						</td>
				<td class="fontColor linefontsize"><input name="btnView"
					type="button" onClick="findClassCheckingInfoByAll()" class="inp_L1"
					id="btnView" onMouseOver="this.className='inp_L2'"
					onMouseOut="this.className='inp_L1'" value="查询" style="margin-left: 30px" />&nbsp;&nbsp;&nbsp;
					<input name="btnView" type="button" onClick="reset()"
					class="inp_L1" id="btnView" onMouseOver="this.className='inp_L2'"
					onMouseOut="this.className='inp_L1'" value="重置" /></td>
			</tr>
		</table>
	</div>

	<br />
	<div style="width: 95%; text-align: center;">

		<div id="findCheckingResultInfo_findClassCheckingInfo"
			style="width: 60%; float: left; text-align: center;height:70%;overflow: auto;"></div>

		<div id="warnInfo_findClassCheckingInfo"
			style="width: 35%; float: right; text-align: center; margin-left: 40px; margin-top: 50px;"></div>

	</div>
	<div
		style="padding-bottom: 50px; position: absolute; border: 1px solid #666; background: #DDD; width: 300px; padding: 10px; display: none"
		id="tip"></div>


</div>
