<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath(); //   /douban
	//                   http               ：//         localhost             :        8080                  /douban  /          
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<script type="text/javascript">
!window.jQuery && document.write('<script src=js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" src="jslib/jquery.min.js"></script>
<script type="text/javascript" src="jslib/jquery.easyui.min.js"></script>
<link href="css/resource.css" type=text/css rel=stylesheet>
<html>
<head>
<title>书籍下载</title>

<style type="text/css">
.find_table_slelct {
	border: 1px solid #F8D880;
	border-radius: 15px;
	width: 100%;
	margin-top: 10px;
	background-color: #F3F3F3;
	padding-bottom: 10px;
}

.find_table_slelct1 {
	border: 1px solid #F8D880;
	border-radius: 15px;
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

body {
	text-align: center;
	margin: 0 auto;
}

th {
	background-color: #0099CC;
	font-size: 10.5pt;
	font-weight: bold;
	color: #FFFFFF;
}

table {
	width: 100%;
	table-layout: fixed;
	word-wrap: break-word;
}

td {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.content {
	width: 100%;
	height: auto;
	word-wrap: break-word;
	word-break: break-all;
	overflow: hidden;
}

div {
	word-break: break-all;
}

div p {
	word-break: break-all;
}
</style>
<script type="text/javascript">

$(function(){ 
	getAllType();
})

function getAllType() {
	$.post("ResourceTypeAction_getAllType.action", {
	}, function(data) {
		if(data.responseCode==0){
			var classNameInfos =data.obj ;
			var optionstr="<option>--请选择--</option>";
				for(var i=0;i<classNameInfos.length;i++){
					optionstr+="<option value='"+classNameInfos[i].cid+"'>"+classNameInfos[i].name+"</option>";	  
				}
			$("#category").html(optionstr);
		}
	});
}
function search() {
	$("#bookinfobyId").html("");
	$("#page").show();
	var cid = $("#category").find("option:selected").val().trim();
	var title=$("#title").val().trim();
	var resourceTypeId=1;
	if(title=="可只输入书名的一部分"){
		title=null;
	}
	if(cid=="--请选择--"){
		cid=null;
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
		url : 'ResourceTypeAction_search.action',
		data : {
			cid : cid,
			title : title,
			resourceTypeId:resourceTypeId,
			pageNume : pageNume,
			displayRows : displayRows,
		},
		type:"post",
		dataType : 'json',
		success : showBookInfo
	});
}
function showBookInfo(data) {
	if(data.responseCode==0){
		var obj=data.obj.result;
		var currentPage = data.obj.currentPage;
		var totalsCount = data.obj.totalsCount;
		var totalsPage = data.obj.totalsPage;
		length=obj.length;
		if (obj != null && length > 0) {
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
				str += "<td width=20% align='center'>"
					+ obj[i].cid+ "</td>";
				str += "<td align='center' width='20%'>"
					+ obj[i].author
					+ "</td>";
				str += "<td align='center' width='10%'><a href=\"javascript:showBookInfoById('"+obj[i].bid+"')\">&nbsp;详情</a></td></tr>";
			}
			str += "</table>";
			$("#tabCurrentPage").html(currentPage);
			$("#tabTotalPage").html(totalsPage);
			$("#findCheckingResultInfo").html(str);
		} else {
			$("#page").hide();
			$("#findCheckingResultInfo").html("");
			$("#findCheckingResultInfo").append(
					"<table><tr height='30'><td align='center' colspan='5'><span class='fontColor' style='color:red;font-weight:bold;font-size:20px;'>暂无数据</span></td></tr></table>")
		}
	}
}
//查看书籍详情
function showBookInfoById(bid) {
	//设置它距离左边和顶部的距离
	$("#bookinfobyId").offset({top:($(window).height()*0.2),left:($(window).width()*0.53)});
	$.ajax({
		url : 'ResourceTypeAction_searchBookInfo.action',
		data : {
			bid : bid,
		},
		type:"post",
		dataType : 'json',
		success : showInfoByBid
	});
}

//首页，上一页，下一页，尾页，点击时触发该函数
function skipToPageNum(status) {
	$("#bookinfobyId").html("");
	$("#page").show();
	var cid = $("#category").find("option:selected").val().trim();
	var title=$("#title").val().trim();
	var resourceTypeId=1;
	if(title=="可只输入书名的一部分"){
		title=null;
	}
	if(cid=="--请选择--"){
		cid=null;
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
			url : 'ResourceTypeAction_search.action',
			data : {
				cid : cid,
				title : title,
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
function showInfoByBid(data) {
	if(data.responseCode==0){
		var obj=data.obj.result;
			var str = "<div class='find_table_slelct1' align='center'><table id='mytable' class='mytable' style='float: left;' width='80%' border='1' cellpadding='1'  bgcolor='#EDECEB' bordercolor='#FFFFFF' cellspacing='0'>";
				str += "<tr height='35'  bgcolor='#E8F2FE' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#E8F2FE';>";
				str += "<td width=50% align='center'>作者："
					+ obj[0].author + "</td>";
				str += "<td width=50% align='center'>书名："
					+ obj[0].title
					+ "</td></tr>";
				str += "<tr height='35'  bgcolor='#E8F2FE' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#E8F2FE';>";
				str += "<td width=50% align='center'>类型："
					+ obj[0].cid+ "</td>";
				str += "<td align='center' width='50%'>价格："
					+ obj[0].price
					+ "</td></tr>";
				str += "<tr height='100'  bgcolor='#E8F2FE' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#E8F2FE';>";	
				
				if(obj[0].imagesUrl==null ||obj[0].imagesUrl==""){
					str+="<td colspan='2' align='center'> <img width='300px' height='100px' src='student/img/zanwu.gif'> </td></tr>"
				}else{
					str+="<td colspan='2' align='center'> <img width='300px' height='100px' src='"+obj[0].imagesUrl+"'> </td></tr>"
				}
				str += "<tr height='35'  bgcolor='#E8F2FE' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#E8F2FE';>";	
				if(obj[0].codesUrl==null ||obj[0].codesUrl==""){
					str+="<td colspan='2' align='center' ><a target='_blank' href='"+obj[0].pdfsUrl+"'>查看书籍</a></td><tr>";
				}else{
					str+="<td  align='center'> <a download target='_blank' href='"+obj[0].codesUrl+"'>下载相关源码</a> </td>";
					str+="<td  align='center'><a target='_blank' href='"+obj[0].pdfsUrl+"'>查看书籍</a></td><tr>"
				}
				str += "</table><div id='miaoshu' align='center'></div></div>";
			$("#bookinfobyId").html(str);
			if(obj[0].description==null || obj[0].description==""){
				
			}else{
				$("#miaoshu").html("<h3>书籍描述</h3>"+obj[0].description);
			}
		}
	}
function page() {
	var str='<table id="page" width="95%" height="40" border="0" cellpadding="0"'
		str+='cellspacing="0">'
		str+='<tr height="40"><td><table width="100%" border="0" cellspacing="0" cellpadding="0">'
			str+='<tr><td><br> 第 <span id="tabCurrentPage"></span> 页 &nbsp;'
				str+='共<span id="tabTotalPage"></span> 页 &nbsp; <input '
				str+='id="firstPage" type="button" value="首页" '
				str+='onClick="skipToPageNum(first)"></input> &nbsp; <input'
				str+='id="upPage" type="button" value="上一页"'
				str+='onClick="skipToPageNum(up)"></input> &nbsp; <input'
				str+=' id="downPage" type="button" value="下一页"'
				str+='onClick="skipToPageNum(down)"></input> &nbsp;<input '
				str+='value="末页" type="button" id="lastPage"'
				str+='onClick="skipToPageNum(last)"></input> &nbsp;<input'
				str+='name="pageNum" type="hidden" class="text4" id="pageNume"'
				str+='onkeyup="" size="5"><input type="hidden" value=""'
				str+='name="sumPage" /></td>'
				str+='</tr><tr><td>&nbsp;</td></tr><tr align="center"><td><font color="red"> <span id="gradePrompt"></span>'	
				str+='</font></td></tr>'	
				str+='</table></td></tr><tr><td align="center"><font color="red"> <span id="gradePaperPrompt">&nbsp;</span></font></td></tr></table>'	
return str;
}
</script>

</head>
<body>
	<div
		style="text-align: center; margin: 0 auto; width: 100%; height: 100%">
		<div class="find_table_slelct" style="width: 70%; margin-left: 15%">
			<table style="text-align: center; width: 100%; table-layout: auto">
				<tr height="15px"></tr>
				<tr height="35px">
					<td width="10%" style="text-align: right;">类别:&nbsp;&nbsp;&nbsp;</td>
					<td width="40%" style="text-align: left;"><select
						id="category" class='select2' name='cid'>
							<option value="0">--请选择--</option>
					</select></td>
					<td width="30%">书名:<input type="text" id="title"
						value="可只输入书名的一部分" onfocus="if (value =='可只输入书名的一部分'){value =''}"
						onblur="if (value ==''){value='可只输入书名的一部分'}" />
					</td>
					<td width="20%"><input height="35px" type="button"
						onclick="search()" value="搜索" class="inp_L1"
						onMouseOver="this.className='inp_L2'"
						onMouseOut="this.className='inp_L1'"></td>
				</tr>
			</table>
		</div>

		<div
			style="text-align: center; margin: 0 auto; width: 100%; height: 100%; margin-top: 35px">
			<div style="width: 45%; text-align: center; margin-left: 25px">
				<table id="change_check_info" width="95%" border="1" cellpadding="1"
					bordercolor="#FFFFFF" cellspacing="1">
					<tr height="30">
						<th bordercolor="#7EA6DC" width="10%">编号</th>
						<th bordercolor="#7EA6DC" width="40%">书籍名</th>
						<th bordercolor="#7EA6DC" width="20%">类型</th>
						<th bordercolor="#7EA6DC" width="20%">作者名</th>
						<th bordercolor="#7EA6DC" width="10%">操作</th>
					</tr>
				</table>
				<div id="findCheckingResultInfo" class="corll_div"
					style="width: 100%; float: left;"></div>
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
			<div style="width: 45%; float: right; margin-right: 25px; margin-top: -320px"
				id="bookinfobyId" >
				<div></div>
			</div>
		</div>
	</div>


</body>
</html>