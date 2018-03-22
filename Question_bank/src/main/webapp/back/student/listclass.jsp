<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../head.jsp"%>



<title>班级列表</title>


<script type="text/javascript" language="javascript"
	src="js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="css/szindex.css" type="text/css"></link>

<script type="text/javascript" src="js/listclass.js"></script>
<script type="text/javascript" src="js/layer.js"></script>
<script type="text/javascript" src="js/dateChoose.js"></script>


<style type="text/css">
.prompt {
    font-size: 10.5pt;
    color: #FF0000;
}
th {
    background-color: #0099CC;
    font-size: 10.5pt;
    font-weight: bold;
    color: #FFFFFF;
}
.find_table_slelct{
border: 1px solid #F8D880 ;
border-radius:5px;
width: 100%;
margin-top: 10px;
background-color: #F3F3F3;
padding-bottom: 10px;
}
a{
text-decoration:none;
}
</style>


</head>

<body style="margin: 0 auto;text-align: center;">
	<img src="back/student/images/t03.gif" width="595" height="33" alt="" style="margin-left: 12%" />
<div style="margin: 0 auto;text-align: center;width: 100%">
		<form id="frmClass" name="frmClass" method="post">
			<table width="90%" align="center" border="0" cellpadding="0"
				cellspacing="0">
				
				<tr>
					<td>
						<table width="90%" align="center" border="0px" cellspacing="0"
							cellpadding="0">
							<tr>
								<td>
								<div class="find_table_slelct">
									<table width="90%" align="center" height="36" border="0px"
										cellpadding="0" cellspacing="0">
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

											<td width="36%"><span id="message" class="fontColor">查询到
													个班级</span></td>
										</tr>
									</table>
									</div>
								</td>
							</tr>
						</table> <br>
						<table width="90%" align="center" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td id="delete_text">
									<div
										style=" width: 100%; height: 100%">
										<table width="90%" align="center" border="1" align="center"
											cellpadding="1" bordercolor="#FFFFFF" cellspacing="0"  >
											<thead>
												<tr>
													<th bordercolor="#7EA6DC">班级名称</th>
													<th bordercolor="#7EA6DC">班级描述</th>
													<th bordercolor="#7EA6DC">学生数量</th>
													<th bordercolor="#7EA6DC">开班日期</th>
													<th bordercolor="#7EA6DC">结束日期</th>
													<th bordercolor="#7EA6DC">信息</th>
													<th bordercolor="#7EA6DC">操作</th>
												</tr>

											</thead>

											<tbody id="showlist">
											</tbody>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<br/>
		<hr/>
	<div style="margin-top: 10px; text-align: center;">
		<div id="showStudentInfo" style="width: 100%;">
			<h3>学生信息详情</h3>

			<table width="80%" align="center" id="listexaminee" border="1"
				align="center" cellpadding="1" bordercolor="#FFFFFF" cellspacing="0">
				<thead>
					<tr style="background-color: #E8F2FE">
						<th bordercolor="#7EA6DC">编号</th>
						<th bordercolor="#7EA6DC">考生姓名</th>
						<th bordercolor="#7EA6DC">考生状态</th>
						<th bordercolor="#7EA6DC">联系电话</th>
					</tr>
				</thead>
				<tbody id="examineerows">
				</tbody>
			</table>
		</div>

		<div id="updateStudentInfo" style="width: 80%; border: 1px;border-radius: 15px;background-color:#F4F4F4;text-align: center;margin: 0 auto; ">
			<h3>班级信息修改</h3>
						<table width="100%"  border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="15%" height="29" class="fontColor" style="text-align: right;">学期编号:</td>
								<td width="5%" style="margin-left: 20px"><input id="semesterS1" name="semester2" type="radio"
									value="S1"> S1 <input id="semesterS2" name="semester2"
									type="radio" value="S2"> S2 <input id="semesterS3"
									name="semester2" type="radio" value="S3"> S3</td>
								 <td width="3%" id="className_text"><input type="hidden"
									name="idInfo" id="idInfo" value=""></td> 
									
								<td width="5%" height="26" class="fontColor" style="text-align: left;">班级名:</td>
								<td width="5%" class="STYLE4"><input name="className" type="text"
									class="text4" id="className" size="20" value=""
									read-only="true" disabled="disabled" maxlength="4" style="width: 50px"/> <span
									class="prompt"> * </span></td>
								<td width="15%" id="className_text" style="text-align: left;"><span class="yellow" id="classSpan">(格式:YC_1..)</span><span
									id="classTip" class="yellow"></span></td>	
									
							</tr>
							<tr>
								
							</tr>
							<tr>
								<td style="text-align: right;" width="15%" height="26" class="fontColor">开班时间:</td>
								<td  style=" margin-left: 20px" width="5%" class="STYLE4"><label> <!-- <input
										type="text" id="openTime" readonly="readonly"
										onclick="fPopCalendar(event,this,this)"
										onfocus="this.select()" /> --> 
										<input type="text" value=""
										id="openTime" name="openTime" 
										onclick="fPopCalendar(event,this,this)" />
										<span class="prompt">* </span>
								</label></td>
								<td  colspan="3" style="text-align: left;" width="8%" class="yellow" id="openTime_text">(格式:2007-05-09)</td>
							</tr>
							<tr>
								<td style="text-align: right;"  width="5%" height="26"><span class="fontColor">结束时间:</span></td>
								<td  width="15%" style="margin-left: 20px"><!-- <input type="text"
										id="overTime" readonly="readonly"
										onclick="fPopCalendar(event,this,this)"
										onfocus="this.select()" /></input> -->
										<input type="text" value=""
										id="overTime" name="overTime" 
										onclick="fPopCalendar(event,this,this)" />
								
								<span class="prompt">* </span>
								</label></td>
								<td  colspan="3" style="text-align: left;" width="8%" class="yellow" id="openTime_text">(格式:2007-05-09)</td>
							</tr>
							<tr>
								<td style="text-align: right;" height="55" valign="middle" class="fontColor">班级描述:</td>
								<td height="55" colspan="3" ><textarea name="remark"
										cols="50" rows="4" class="texta" id="textarea" style="margin-top: 5px"></textarea></td>
								<td align="center" valign="top" style="text-align: left"> 
							</tr>
							<tr><td  colspan="6" style="text-align: center;margin-top: 50px"><input name="update" type="button" class="inp_L1" id="add3"
												onMouseOver="this.className='inp_L2'"
												onMouseOut="this.className='inp_L1'" value=" 修 改  "
												onClick="submitForm()" /></td></tr>
							<tr>
								<label style="color: red; font-size: 15px;">
									温馨提示：点击修改，修改班级信息的同时会更新班级在读人数信息 </label>
							</tr>
							<tr valign="top">
								<td colspan="3"><table width="100%" height="111" border="0"
										cellpadding="0" cellspacing="0" >
										<tr>
											<td width="62%" valign="top" id="addclass_text"><span
												class="fontColor" id="strPromp"></span></td>
											
										</tr>
									</table></td>
							</tr>
						</table>
		</div>
	</div>
	<div id="changeClass" style="text-align: center;margin: auto ;">
		<form method="post">

			<div align="center" >
				<label>转班班级:</label> &nbsp;&nbsp;&nbsp;<label> <select
					name="classNameChoose" id="classNameChoose">
				</select>
				</label>
			</div>
			<br>
			<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
				onclick="changeClass();" value="确定">

		</form>
	</div>
</div>
</body>