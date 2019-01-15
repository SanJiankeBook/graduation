<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="http://localhost:8080/SanJiankeBook/easyui/css/easyui.css">
<link rel="stylesheet" type="text/css"
	href="http://localhost:8080/SanJiankeBook/easyui/css/icon.css">
<link rel="stylesheet" type="text/css"
	href="http://localhost:8080/SanJiankeBook/easyui/css/demo.css">
<script type="text/javascript"
	src="http://localhost:8080/SanJiankeBook/js/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="http://localhost:8080/SanJiankeBook/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="http://localhost:8080/SanJiankeBook/easyui/js/index.js"></script>
<script type="text/javascript"
	src="http://localhost:8080/SanJiankeBook/easyui/js/easyui-lang-zh_CN.js"></script>

<title>小说搜索</title>
</head>
<body style="text-align: center; margin: 0 auto">
<div style="margin-bottom: 30px">
	<input id="searchNovel" type="text" style="width: 300px; height: 30px">
	&nbsp;&nbsp;&nbsp;
	<input type="button" onclick="searchNovel()" value="搜本站"
		style="width: 100px; height: 30px" />
	
	<input type="button" onclick="baidusearch()" value="搜全网"
		style="width: 100px; height: 30px" id="baidu"/>
	<br />
	</div>
	<table id="type_showAuthor_info" data-options="fit:false" style="height:400px;margin-top: 50px"></table> 
	
<script type="text/javascript"> 

$(function(){
	$("#searchNovel").val("${novel}");
	searchNovel();
})

//百度搜索
function baidusearch(id){
				var url = "http://www.baidu.com/s?ie=utf-8&cus_sid=3677118700255927857&tn=SE_pscse_053x7tyx&wd=" + 
encodeURIComponent($("#searchNovel").val());
				window.open(url,"_blank");
}
function searchNovel() {
	var datagridObj;
	var editRow = undefined;	//当前正在被编辑的行的索引
	var op;
	var flag;
	var easyui=$("#searchNovel").val();
	datagridObj=$('#type_showAuthor_info').datagrid({
		url:'quearyNovel',   
		queryParams: {text: easyui},
		fitColumns:true,
		loadMsg:'数据加载中...',
		pagination : true, //显示分页栏
		striped:true,		//斑马线效果
		nowrap:true,		//超出宽度自动截取
		rownumbers:true,		//显示行数
		sortName:'nid',		//排序的咧
		remoteSort:false,	//前段排序而非服务器的排序，自己的排序
		columns:[[
					{field : 'nid',title : '小说编号',width : 20,align : 'center',sortable : true,hidden:true},
					{field : 'npicture',title : '小说封面',width : 50,align : 'center',formatter : function(value, row, index) {
							var picStr = "";
							if (value.indexOf(",")) {
								value = value.split(",");
								for (var i = 0; i < value.length; i++) {
									if (value[i] == null || value[i] == "") {
										picStr += "<img src='../images/zanwu.jpg' width='100px' height='100px' />";
									} else {
										var patt1 = new RegExp("../");
										if (patt1.test(value[i])) {
											picStr += "<img src='"+value[i]+"' width='100px' height='100px' />";
										} else {
											picStr += "<img src='images/"+value[i]+"' width='100px' height='100px' />";
										}
									}
								}
							} else if (value != "") {
								picStr += "<img src='"+value+"' width='100px' height='100px' />";
							} else {
								picStr += "<img src='images/zanwu.jpg' width='100px' height='100px' />";
							} 
								return picStr;
							}
						},
					{field : 'tname',title : '类型',width : 30,align : 'center'},
					{field : 'pan_name',title : '作者',width : 30,align : 'center'},
					{field : 'nname',title : '名字',width : 50,align : 'center',formatter: function(val,row,index){
						 if(val){
							 return "<a href='toindex_id/"+row.nid +"' >"+val+"</a>";
						} 
					}
}, 
					{field : 'ndescription',title : '小说简介',width : 100,align : 'center'},
					{field : 'nstatus',title : '小说状态',width : 50,align : 'center'}, 
				]]
	});
	
	
	}
</script>
</html>