<link href="../../css/szindex.css" type=text/css rel=stylesheet>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- <script type="text/javascript" language="javascript" src="../../js/ExamUtils.js"></script> -->
<script language="javascript" type="text/javascript"
	src="../../js/ajax.js"></script>
<script language="javascript" type="text/javascript"
	src="../../js/creatXML.js"></script>
<script language="javascript" type="text/javascript"
	src="../../js/sendrequest.js"></script>
<!-- <script type="text/javascript"
	src="http://lib.sinaapp.com/js/jquery/2.0/jquery.min.js"></script> -->
	<script type="text/javascript" src="../../jslib/jquery.min.js"></script>
	<script type="text/javascript" src="../../jslib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../jslib/jeasyui.common.js"></script>
<script type="text/javascript" src="../../js/searchexamineeN.js"></script>


<script type="text/javascript" src="../../js/checking.js"></script>
<script type="text/javascript" src="../../js/dateChoose.js"></script>



<style type="text/css">
#firstPage, #upPage, #downPage, #lastPage {
	background-image: url(../../images/bg_x.jpg);
	background-repeat: no-repeat;
	BORDER-TOP-WIDTH: 0px;
	BACKGROUND-POSITION: -4px -4px;
	BORDER-LEFT-WIDTH: 0px;
	BORDER-BOTTOM-WIDTH: 0px;
	COLOR: #464646;
	WIDTH: 68px;
	LINE-HEIGHT: 23px;
	HEIGHT: 23px;
	BORDER-RIGHT-WIDTH: 0px
}

#firstPage:hover, #upPage:hover, #downPage:hover, #lastPage:hover {
	background-image: url(../../images/bg_x.jpg);
	background-repeat: no-repeat;
	BORDER-TOP-WIDTH: 0px;
	BACKGROUND-POSITION: -4px -30px;
	BORDER-LEFT-WIDTH: 0px;
	BORDER-BOTTOM-WIDTH: 0px;
	COLOR: #464646;
	WIDTH: 68px;
	LINE-HEIGHT: 23px;
	HEIGHT: 23px;
	BORDER-RIGHT-WIDTH: 0px
}
.inp_L1 {
    background-image: url(../images/bg_x.jpg);
    background-repeat: no-repeat;
    BORDER-TOP-WIDTH: 0px;
    BACKGROUND-POSITION: -4px -55px;
    BORDER-LEFT-WIDTH: 0px;
    BORDER-BOTTOM-WIDTH: 0px;
    WIDTH: 82px;
    COLOR: #464646;
    LINE-HEIGHT: 23px;
    HEIGHT: 23px;
    BORDER-RIGHT-WIDTH: 0px;
}
</style>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<script type="text/javascript" src="back/ckeditor/ckeditor.js"></script>


<body style="text-align: center;margin:0 auto;>
<div id="1"  style="text-align: center;margin: 0 auto;width:1000px;height:500px">
<div  style="text-align: center;margin: 0 auto;width:1000px;height:500px">
	<form name="frmList">
		<table width="1000" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<table width="100%" border="0" align="center" cellspacing="0">
						<tr>
							<td>
							<div style="background-color: #E8F2FE;width: 1000px;border: 1;border-radius:15px ">
								<table width="100%" border="0" cellspacing="0">
									
									<tr>
										<td width="6%" height="46">
											<div align="left">
												<label class="fontColor1"> 学期编号: </label>
											</div>
										</td>
										
										
										<td width="18%"><input name="semester" type="radio"
											value="S1" checked="checked"
											onClick="createSelectOption('S1')"> S1
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio"
											name="semester" value="S2" onClick="createSelectOption('S2')">
											S2 &nbsp;&nbsp;&nbsp;&nbsp; <input type="radio"
											name="semester" value="S3" onClick="createSelectOption('S3')">
											S3</td>
											<td width="6%" height="41">
											<div align="right" class="fontColor1">班级名称:</div>
										</td>
										
										<td width="13%">
											<div align="left">
												<select name="examClass" id="examClassName" class="text4"
													onChange="showExamineeNames(this.value)">
													<option>--请选择--</option>
												</select>
												<span class="prompt">*</span>
											</div>
											
										</td>
										<td height="27" width="6%" class="fontColor1">
										<div align="right">
												<label class="fontColor1"> 学生姓名: </label>
											</div>
										</td>
										<td width="5%"><span class="prompt"> <select
												name="examineeName" id="examineeName" class="text4">
													<option selected>--请选择--</option>
											</select>
										</span> <span class="prompt">*</span></td>
										<td height=57" width="6%"  class="fontColor1">
										<div align="right">
												<label class="fontColor1"> 访谈日期： </label>
											</div>
										</td>
										<td width="11%"> <input type="text" value=""
											id="checkDate" name="checkDate"
											onclick="fPopCalendar(event,this,this)" />&nbsp;
											<lable class="prompt">*</lable>
										</td>
									</tr>
									<tr>
									
									<td height="47" width="5%" class="fontColor1">
										<div align="right">
												<label class="fontColor1"> 访谈地址: </label>
											</div></td>
										<td>
										<!--  <input type="text" id="interviewAddress" name="interviewAddress"/>-->
										<select id="classroomCurriFind_selectSubcondition" >
										<option value="-1"  selected="selected">--请选择--</option>
										<option value="0"  >2513</option>
										</select> <span class="prompt">*</span></td>
										
										<td height="37" class="fontColor1">
										<div align="right">
												<label class="fontColor1">备注: </label>
											</div></td>
										<td><input type="text" id="remark" style="width: 100px" name="remark"/></td>
										<td colspan="2" style="text-align: center;"><input type="button" onclick="addInterviewRecordInfo()" value="添加访谈记录" class="inp_L1" id="btnView" onMouseOver="this.className='inp_L2'" onMouseOut="this.className='inp_L1'"/>
										</td>
									</tr>
								</table>
								</div>
							</td>
						</tr>
					</table>
	</form>
</div>
<div style="width: 1000px;margin-top: 20px" >
<div style="background-color: #E8F2FE;border-radius: 20px;text-align: center;">
<label >访谈内容：</label><span class="fontColor1">请在下面方框中输入访谈内容</span><span
											class="prompt"> * </span>
</div>
								
<textarea rows="30" cols="50" id="editor01" name="editor01"></textarea>
</div>
</div>


<input id="showTeacherSatis_teacherName" hidden="true"/>
<script type="text/javascript">
var allClassRoomName =new Array();
$(function(){ 
	editor=CKEDITOR.replace( 'editor01' );
	//从session中取出用户名
	 var user = '<%=session.getAttribute("userName")%>';
	 $("#showTeacherSatis_teacherName").val(user);
	 $.ajax({
			url:"findAllClassroom.action",
			type:"post",
			datatype:"json",
			success:showClassroom
		});
	 
	/*  $.post("findAllTeacher2.action", function(data) {
			if (data.responseCode == 1) {
				alert("老师查询错误,请与管理员联系");
				return;
			} else {
				for (var i = 0; i < data.obj.length; i++) {
					$("#showTeacherSatis_teacherName").append(
							"<option value="+data.obj[i].id+">"
									+ data.obj[i].userName + "</option>");
				}
			}
		}); */
	
	

}); 
function showClassroom(data) {
	
	 if(data.responseCode==1){ 
			alert("教室查询错误,请与管理员联系");
			return;
		}else{ 
			 for(var i=0;i<data.obj.length;i++){
					$("#classroomCurriFind_selectSubcondition").append("<option value="+data.obj[i].classroomid+">"+data.obj[i].classroomname+"</option>");
					allClassRoomName[i]=data.obj[i].classroomname;
			 } 
			}
}
function addInterviewRecordInfo() {
	/*//总页数
		var totalPage=parseInt($.trim($("#tabTotalPage").text()));
		if(totalPage==""){
			displayRows=0;
		}
		//每页显示几条
		var displayRows=$.trim($("#displayRows").val());
		if(displayRows==""){
			displayRows=10;
			$("#displayRows").val(displayRows);
		}
		//第几页
		var pageNume=$.trim($("#pageNume").val());
		if(pageNume==""){
			pageNume=1;
			$("#pageNume").val(pageNume);
		}else if(pageNume==0){
			pageNume=1;
		}else if(pageNume>=totalPage){
			pageNume=totalPage;
			$("#pageNume").val(totalPage);
		}*/
		
		//班级名称	
		var examClassName=$.trim($("#examClassName").val());
		if(examClassName=="--请选择--"){
			alert("请选择一个班级");
			return;
		}
		//alert(examClassName+"取班级名");
		//取学生姓名
		var stuName=$.trim($("#examineeName").val());
		//alert(stuName+"取学生名");
		if(stuName=="--请选择--"){
			alert("请选择一个学生姓名");
			return;
		}
		//取教师名	var teacherName=$("#showTeacherSatis_teacherName").find("option:selected").text();;
		var teacherName=$("#showTeacherSatis_teacherName").val().trim();
		//alert(teacherName+"取教师名");
	/* 	if(teacherName==null || teacherName=="" || teacherName=="--请选择--"){
			alert("请选择教师名");
			return;
		} */
		//取内容
		
		 $('#editor01').val(editor.getData());
		var content=$.trim($("#editor01").val());
		//alert(content+"取内容名");
		if(content==null || content==""){
			alert("请填写内容");
			return;
		}
		
		//取地址	var classroomid=$("#classroomCurriFind_selectSubcondition").find("option:selected").val();
		var interviewAddress=$("#classroomCurriFind_selectSubcondition").find("option:selected").text().trim();
		//alert(interviewAddress+"取地址名");
		if(interviewAddress==null || interviewAddress=="" ||interviewAddress=="--请选择--"){
			alert("请选择地址");
			return;
		}
		//取日期
		var pdate=$.trim($("#checkDate").val());
		//alert(pdate+"取日期名");
		if(pdate==null || pdate==""){
			alert("请填写时间");
			return;
		}
		
		//取备注
		var remark=$.trim($("#remark").val());
	//	alert(remark+"备注");
		
		$.ajax({
			url:"examinInterviewRecord_addInfo.action",
			type:"post",
			datatype:"json",
			data:{"className":examClassName,"studentName":stuName,"teacherName":teacherName,"remark":remark,"content":content,"interviewAddress":interviewAddress,"pdate":pdate},
			success:showPageInfo
		});
		}
	function showPageInfo(data) {
		
		if(data.responseCode==0){
		     $("#examineeName").val("--请选择--");
			alert("添加成功");
			reset();
		}else{
		     $("#examineeName").val("--请选择--");
			alert("添加失败")
		}
	}
	//重置每个文本框
	function reset() {
		//$("#teachername").val("");
		editor.setData("");
	//	$("#interviewAddress").val("");
		$("#remark").val("");
	}
</script>
</body>
