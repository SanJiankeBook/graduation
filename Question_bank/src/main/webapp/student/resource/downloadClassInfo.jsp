<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();   //   /douban
	//                   http               ：//         localhost             :        8080                  /douban  /          
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath %>">
<script type="text/javascript">
!window.jQuery && document.write('<script src=js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" src="jslib/jquery.min.js"></script>
<script type="text/javascript" src="jslib/jquery.easyui.min.js"></script>
<link href="css/resource.css" type=text/css rel=stylesheet>
<html>
<head>
<title>课堂资料下载</title>

<style type="text/css">
.find_table_slelct{
border: 1px solid #F8D880 ;
border-radius:15px;
width: 100%;
margin-top: 10px;
background-color: #F3F3F3;
padding-bottom: 10px;
}

.find_table_slelct1{
border: 1px solid #F8D880 ;
border-radius:15px;
width: 100%;
margin-top: 10px;
background-color: #E8F2FE;
padding-bottom: 10px;
}
.inp_L1 {
    background-image: url(images/bg_x.jpg);
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
.inp_L2 {
	background-image: url(images/bg_x.jpg);
	background-repeat: no-repeat;
	BORDER-TOP-WIDTH: 0px;
	BACKGROUND-POSITION: -4px -80px;
	BORDER-LEFT-WIDTH: 0px;
	BORDER-BOTTOM-WIDTH: 0px;
	WIDTH: 82px;
	COLOR: #464646;
	LINE-HEIGHT: 23px;
	HEIGHT: 23px;
	BORDER-RIGHT-WIDTH: 0px
}
.prompt {
    font-size: 10.5pt;
    color: #FF0000;
}
body{
text-align: center;
margin:  0 auto;
}
th{
    background-color: #0099CC;
    font-size: 10.5pt;
    font-weight: bold;
    color: #FFFFFF;
}


 table {  
    width:100%;  
    table-layout:fixed;  
    word-wrap:break-word;
 }  
    td {  
       white-space:nowrap;  
       overflow:hidden;  
	   text-overflow: ellipsis;
			
    } 
 .content {
    width: 100%;  
    height: auto;  
    word-wrap:break-word;  
    word-break:break-all;  
    overflow: hidden;  
}
div{word-break:break-all;}
div p{word-break:break-all;}
</style>
<script type="text/javascript">
$(function(){ 
	showEditiontName();
})
//一加载就显示版本号
function showEditiontName() {
	$.post("/Examination2.0/course_edition.action",
		function(obj) {
						$("#version").html("<option value='0'>--请选择--</option>");
						for (var i = 0; i < obj.length; i++) {
							$("#version").append(
									"<option value='" + obj[i].id+ "'>"
											+ obj[i].editionName
											+ "</option>");
						}
						$("#version").get(0).selectedIndex = 1;
	});
}
//更改版本
function changeVersion(){
	semester=$('#chapter_semester').val();
	version=$('#version').val();
	createSubjectOption(version,semester);
}
//更改学期
function cretsubject() {
	semester=$('#chapter_semester').val();
	version = $('#version').val();
	createSubjectOption(version, semester);
}
//加载课程
function createSubjectOption(version, semester) {
	$.ajaxSettings.async = false;
	$
			.ajax({
				url : "/Examination2.0/initData_subject.action",
				type : "post",
				data : {
					"editionId" : version,
					"semester" : semester
				},
				datatype : "json",
				success : function(data) {
					var examineeClassList =data;
					var optionstr = "<option value='0'>--请选择--</option>";
					if (semester == null || semester == "") {
						$("#subject").html(optionstr);
						return;
					}
					if (examineeClassList.responseCode != 1) {
						for ( var i = 0,len=examineeClassList.obj.length; i <len ; i++) {
							optionstr += "<option value='"+examineeClassList.obj[i].id+"'>"
									+ examineeClassList.obj[i].subjectName
									+ "</option>";
						}
					} 
					$("#subject").html(optionstr);
				}
			});
}
//搜索
function search() {
	$("#bookinfobyId").html("");
	var edtionId = $("#version").find("option:selected").val().trim();
	var subjectid = $("#subject").find("option:selected").val().trim();
	var semeter = $("#chapter_semester").find("option:selected").val().trim();
	var resourceTypeId=3;
	var title=$("#title").val().trim();
	if(title=="名称可只写一部分"){
		title=null;
	}
	if(edtionId=="0" ||edtionId==0){
		edtionId=null;
	}
	if(subjectid=="0" ||subjectid==0){
		subjectid=null;
	}
	if(semeter=="0" ||semeter==0){
		semeter=null;
	}
	//下面这段是分页所用到的参数
	//总页数
		var totalPage = parseInt($.trim($("#tabTotalPage").text()));
		if (totalPage == "") {
			displayRows = 0;
		}
		//每页显示几条
		var displayRows = $.trim($("#displayRows").val());
		if (displayRows == "") {
			displayRows = 10;
			$("#displayRows").val(displayRows);
		}
		//第几页
		var pageNume = $.trim($("#pageNume").val());
		if (pageNume == "") {
			pageNume = 1;
			$("#pageNume").val(pageNume);
		} else if (pageNume == 0) {
			pageNume = 1;
		} else if (pageNume >= totalPage) {
			pageNume = totalPage;
			$("#pageNume").val(totalPage);
		}
	$.ajax({
		url : 'ResourceTypeAction_searchClass.action',
		data : {
			edtionId : edtionId,
			subjectid : subjectid,
			semeter:semeter,
			title:title,
			resourceTypeId:resourceTypeId,
			pageNume : pageNume,
			displayRows : displayRows
		},
		type:"post",
		dataType : 'json',
		success : showBookInfo
	});
}
//显示数据
function showBookInfo(data) {
	if(data.responseCode==0){
		var obj=data.obj.result;
		var currentPage = data.obj.currentPage;
		var totalsCount = data.obj.totalsCount;
		var totalsPage = data.obj.totalsPage;
		length=obj.length;
		if (obj != null && length> 0) {
			str = "<table id='mytable' class='mytable' style='float: left;' width='100%' border='1' cellpadding='1'  bgcolor='#EDECEB' bordercolor='#FFFFFF' cellspacing='0'>";
			for (var i = 0; i < length; i++) {
				
				str += "<tr height='23' id='"
					+ (i + 1)
					+ "' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB';>";
				str += "<td width=10% align='center'>"
					+ obj[i].bid + "</td>";
				str += "<td width=40% align='center'>"
					+ obj[i].title
					+ "</td>";
				str += "<td width=10% align='center'>"
					+ obj[i].semeter+ "</td>";
				str += "<td align='center' width='30%'>"
					+ obj[i].subjectName
					+ "</td>";
				str += "<td align='center' width='10%'><a href=\"javascript:showBookInfoById('"+obj[i].bid+"')\">&nbsp;详情</a></td></tr>";
				
			}
			str += "</table>";
			$("#tabCurrentPage").html(currentPage);
			$("#tabTotalPage").html(totalsPage);
			$("#findCheckingResultInfo").html(str);
		} else {
			$("#findCheckingResultInfo").html("");
			$("#findCheckingResultInfo").append(
					"<table><tr height='30'><td align='center' colspan='5'><span class='fontColor' style='color:red;font-weight:bold;font-size:20px;'>暂无数据</span></td></tr></table>")
		}
	}
}
//查看课堂资料详情
function showBookInfoById(bid) {
	$("#bookinfobyId").offset({top:($(window).height()*0.2),left:($(window).width()*0.53)});
	$.ajax({
		url : 'ResourceTypeAction_searchClassInfo.action',
		data : {
			bid : bid,
		},
		type:"post",
		dataType : 'json',
		success : showInfoByBid
	});
}
//拼详细资料
function showInfoByBid(data) {
	if(data.responseCode==0){
		var obj=data.obj.result;
			var str = "<div class='find_table_slelct1' align='center'><table id='mytable' class='mytable' style='float: left;' width='80%' border='1' cellpadding='1'  bgcolor='#EDECEB' bordercolor='#FFFFFF' cellspacing='0'>";
				str += "<tr height='35'  bgcolor='#E8F2FE' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#E8F2FE';>";
				str += "<td width=50% align='center'>学期："
					+ obj[0].semeter + "</td>";
				str += "<td width=50% align='center'>资料名："
					+ obj[0].title
					+ "</td></tr>";
				str += "<tr height='35'  bgcolor='#E8F2FE' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#E8F2FE';>";
				str += "<td width=50% align='center'>版本类型："
					+ obj[0].edtionName + "</td>";
				str += "<td width=50% align='center' >科目类型："
					+ obj[0].subjectName+ "</td>";
					+ "</tr>";
				str += "<tr height='35'  bgcolor='#E8F2FE' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#E8F2FE';>";	
					str+="<td colspan='2' align='center' ><a download target='_blank' href='"+obj[0].pdfsUrl+"'>下载课堂资料</a></td><tr>";
				str += "</table><div id='miaoshu' align='center'></div></div>";
			$("#bookinfobyId").html(str);
			if(obj[0].description==null || obj[0].description==""){
				
			}else{
				$("#miaoshu").html("<h3>课堂资料描述</h3>"+obj[0].description);
			}
		}
	}
//首页，上一页，下一页，尾页，点击时触发该函数
function skipToPageNum(status) {
	$("#page").show();
	$("#bookinfobyId").html("");
	var edtionId = $("#version").find("option:selected").val().trim();
	var subjectid = $("#subject").find("option:selected").val().trim();
	var semeter = $("#chapter_semester").find("option:selected").val().trim();
	var resourceTypeId=3;
	var title=$("#title").val().trim();
	if(title=="名称可只写一部分"){
		title=null;
	}
	if(edtionId=="0" ||edtionId==0){
		edtionId=null;
	}
	if(subjectid=="0" ||subjectid==0){
		subjectid=null;
	}
	if(semeter=="0" ||semeter==0){
		semeter=null;
	}
	
	//总页数
	var totalPage = parseInt($.trim($("#tabTotalPage").text()));
	//alert(totalPage);
	if (totalPage == "") {
		displayRows = 0;
	}
	//每页显示几条	
	var displayRows = $.trim($("#displayRows").val());
	if (displayRows == "") {
		displayRows = 10;
		$("#displayRows").val(displayRows);
	}
	//第几页		
	var pageNume = $.trim($("#pageNume").val());
	if (pageNume == "") {
		pageNume = 1;
		$("#pageNume").val(pageNume);
	} else if (pageNume == 0) {
		pageNume = 1;
	}
	//当前第几页	
	var currentNume = $.trim($("#tabCurrentPage").text());
	if (currentNume == "") {
		currentNume = 1;
		$("#tabCurrentPage").val(currentNume);
	} else if (currentNume == 0) {
		currentNume = 1;
	}
	if (status == "up") { //上一页
		if (currentNume == 1) {
			pageNume = currentNume;
			return;
		} else {
			//$("#upPage").animate({color:'red'});
			pageNume--;
			$("#pageNume").val(pageNume);
		}
	} else if (status == "down") {
		if (currentNume == totalPage || currentNume + "" == totalPage + "") {
			pageNume = currentNume;
			return;
		} else {
			//$("#downPage").animate({color:'red'});
			pageNume++;
			$("#pageNume").val(pageNume);
		}
	} else if (status == "first") {
		if (currentNume == 1) {
			pageNume = currentNume;
			return;
		} else {
			//overChangeColor("firstPage","blue")
			pageNume = 1;
			$("#pageNume").val(pageNume);
		}
	} else if (status == "last") {
		if (currentNume == totalPage) {
			pageNume = currentNume;
			return;
		} else {
			//overChangeColor("lastPage","blue")
			pageNume = totalPage;
			$("#pageNume").val(pageNume);
		}
	}

	if (pageNume >= 1 && pageNume <= totalPage) {
		$.ajax({
			url : 'ResourceTypeAction_searchClass.action',
			data : {
				edtionId : edtionId,
				subjectid : subjectid,
				semeter:semeter,
				title:title,
				resourceTypeId:resourceTypeId,
				pageNume : pageNume,
				displayRows : displayRows,
			},
			type:"post",
			dataType : 'json',
			success : showBookInfo
		});
	}
}
</script>

</head>
<body>
<div  style="text-align: center;margin: 0 auto;width:100%;height:100%">
	<div class="find_table_slelct" style="width: 70%;margin-left: 15%">
		<table style="text-align: center;width: 100%;table-layout: auto">
			<tr height="15px"></tr>
			<tr height="35px">
			<td width="20%" style="text-align: left;">版本：<select id="version" name="edtionId" onChange="changeVersion()"></select></td>
			<td width="5%" style="text-align: left;">学期：<select id="chapter_semester" name="semeter" onchange="cretsubject()"><option value="0">--请选择--</option>
								<option value="S1">S1</option>
								<option value="S2">S2</option>
								<option value="S3">S3</option>
						</select></td>
			<td width="25%" style="text-align: center;margin-left: 0px">科目名：<select width="100%" id="subject" name="subjectid"  "><option
									value="0">--请选择--</option>
						</select> </td>
			<td width="30%" align="right">资料名: <input type="text"  id="title" value="名称可只写一部分" onfocus="if (value =='名称可只写一部分'){value =''}" onblur="if (value ==''){value='名称可只写一部分'}"  /> </td>
			<td width="20%" ><input height="35px" type="button" onclick="search()" value="搜索" class="inp_L1" onMouseOver="this.className='inp_L2'" onMouseOut="this.className='inp_L1'"> </td>
			</tr>
		</table>
	 </div>
	
	<div   style="text-align: center;margin: 0 auto;width:100%;height:100%;margin-top: 35px">
		<div  style="width: 45%;text-align:center  ;margin-left: 25px">
			<table id="change_check_info" width="95%" border="1" cellpadding="1" bordercolor="#FFFFFF" cellspacing="1">
				<tr height="30">
					<th bordercolor="#7EA6DC" width="10%">编号</th>
					<th bordercolor="#7EA6DC" width="40%">资料名</th>
					<th bordercolor="#7EA6DC" width="10%">学期</th>
					<th bordercolor="#7EA6DC" width="30%">科目类型</th>
					<th bordercolor="#7EA6DC" width="10%">操作</th>
				</tr>
			</table>
			<div id="findCheckingResultInfo" class="corll_div" style="width: 100%;float: left;"></div>
			<table style="table-layout:auto;" width="100%" height="40" border="0" cellpadding="0"
					cellspacing="0">
					<tr height="40">
						<td>
							<table style="table-layout:auto;" width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><br> 第 <span id="tabCurrentPage"></span> 页 &nbsp;
										共<span id="tabTotalPage"></span> 页 &nbsp; <input
										id="firstPage" type="button" value="首页"
										onClick="skipToPageNum('first')"></input> &nbsp; <input
										id="upPage" type="button" value="上一页"
										onClick="skipToPageNum('up')"></input> &nbsp; <input
										id="downPage" type="button" value="下一页"
										onClick="skipToPageNum('down')"></input> &nbsp;<input
										value="末页" type="button" id="lastPage"
										onClick="skipToPageNum('last')"></input> &nbsp;<input
										name="pageNum" type="hidden" class="text4" id="pageNume"
										onkeyup="" size="5"><input type="hidden" value=""
										name="sumPage" /></td>

								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr align="center">
									<td><font color="red"> <span id="gradePrompt"></span>
									</font></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="center"><font color="red"> <span
								id="gradePaperPrompt">&nbsp;</span>
						</font></td>
					</tr>
				</table>
		</div>
		<div style="width: 45%;float: right;margin-right: 25px" id="bookinfobyId">
			<div></div>
		</div>
	</div>
</div>


</body>
</html>