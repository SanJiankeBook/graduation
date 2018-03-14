<!-- 书架 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>
	小说搜索
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/style.css" />
<!--  <script type="text/javascript" src="../js/xiaoshuo.js"></script> -->
<script type="text/javascript" src="js/xiaoshuo.js"></script>
<link rel="stylesheet" type="text/css"
	href="easyui/css/easyui.css">
<link rel="stylesheet" type="text/css"
	href="easyui/css/icon.css">
<link rel="stylesheet" type="text/css"
	href="easyui/css/demo.css">
<script type="text/javascript"
	src="js/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="easyui/js/index.js"></script>
<script type="text/javascript"
	src="easyui/js/easyui-lang-zh_CN.js"></script>
</head>
<body style="text-align: center; margin: 0 auto">
	<h1 style="font-size: 20px">小说搜索</h1>
    <div id="wrapper">
<div class="header">
    <div class="header_logo"><a href="toindex_zpd">三剑客文学</a></div>
    <div class="header_search"><script>search();</script></div>
</div>
<div class="clear"></div>
<div class="nav">
			<ul>
				<li><a href="toindex_zpd">首页</a></li>
					<li><a rel="nofollow" href="mybook">我的书架</a></li>
					<li><a href="toindex_Type/${list[0].tname}">${list[0].tname}</a></li>
					<li><a href="toindex_Type/${list[1].tname}">${list[1].tname}</a></li>
					<li><a href="toindex_Type/${list[2].tname}">${list[2].tname}</a></li>
					<li><a href="toindex_Type/${list[3].tname}">${list[3].tname}</a></li>
					<li><a href="toindex_Type/${list[4].tname}">${list[4].tname}</a></li>
					<li><a href="toindex_Type/${list[5].tname}">${list[5].tname}</a></li>
					<li><a href="authorPrefectrue1">作者专区</a></li>
					<li><a href="toindex_type">排行榜单</a></li>
					<li><a href="quanben">全本小说</a></li>
					<li><a rel="nofollow" href="jsp/readRecord.jsp">阅读记录</a></li>
			</ul>
		</div>
       <br />

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
//最后一个标题后面也必须加一个逗号，不然火狐浏览器不能显示出标题名
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
								picStr += "<img src='images/nopic2.jpg' width='100px' height='100px' />";
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
		
					{field : 'nstatus',title : '小说状态',width : 50,align : 'center'} 
				]]
	});
	
	
	}
</script>



        
<div class="footer">
    <div class="footer_link">
    </div>
    <div class="footer_cont">
        <p>
            本站所有小说为转载作品，所有章节均由网友上传，转载至本站只是为了宣传本书让更多读者欣赏。</p>
        <p>
            Copyright © 2016
            笔下文学</p>
       <script>footer();</script>
    </div>
</div>
    </div>
</body>
</html>
