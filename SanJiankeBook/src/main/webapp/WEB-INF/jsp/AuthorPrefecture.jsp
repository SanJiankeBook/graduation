<!-- 女生频道 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	//					http				://		localhost			:	8081				/SpringMvc_Book/
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IT类专业书籍</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/xiaoshuo.js"></script>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
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
<style type="text/css">  
     .item   table  
        {  
            border-collapse: collapse;  
            margin : 0 auto;  
            text-align: center;  
        }  
      .item   table td, table th  
        {  
            border: 1px solid #cad9ea;  
            color: #666;  
            height: 30px; 
          
        }  
       .item  table thead th  
        {  
            background-color: #CCE8EB;  
            width: 100px;  
        }  
       .item  table tr:nth-child(odd)  
        {  
            background: #fff;  
        }  
       .item  table tr:nth-child(even)  
        {  
            background: #F5FAFA;  
        }  
    </style>  
</head>
	<!-- 显示作者写的书籍 -->
	<script  type="text/javascript">
	$(function(){
		var datagridObj;
		/* var editRow = undefined;	//当前正在被编辑的行的索引
		var op;
		var flag; */
		var aid=${author[0].aid};
		datagridObj=$('#type_showAuthor_info').datagrid({
			url:'AuthorNovel?aid='+aid,   
			fitColumns:true,
			loadMsg:'数据加载中...',
			striped:true,		//斑马线效果
			pagination : true, //显示分页栏
			nowrap:true,		//超出宽度自动截取
			rownumber:true,		//显示行数
			sortName:'nid',		//排序的
			remoteSort:false,	//前段排序而非服务器的排序，自己的排序
			pageSize:5,
			 pageList:[5,10,15,20,25,30],
			columns:[[
				{field : 'nids',title : '全选',width : 20,align : 'center',sortable : true,checkbox:true},
				{field : 'nid',title : '书籍编号',width : 20,align : 'center',sortable : true},
				{field : 'npicture',title : '书籍封面',width : 50,align : 'center',formatter : function(value, row, index) {
						var picStr = "";
						if (value.indexOf(",")) {
							value = value.split(",");
							for (var i = 0; i < value.length; i++) {
								if (value[i] == null || value[i] == "") {
									picStr += "<img src='images/zanwu.jpg' width='100px' height='100px' />";
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
			/* 	{field : 'tname',title : '书籍类型',width : 30,align : 'center'},
				{field : 'aname',title : '书籍作者',width : 30,align : 'center'}, */
				/* {field : 'pan_name',title : '作者笔名',width : 30,align : 'center'}, */
				{field : 'nname',title : '书籍名',width : 50,align : 'center'}, 
				{field : 'ndescription',title : '书籍简介',width : 100,align : 'center'},
				{field : 'nstatus',title : '书籍状态',width : 50,align : 'center'}, 
				{field : 'standby_1',title : '审核状态',width : 50,align : 'center'}, 
			]]
		});
/* 		datagridObj=$('#type_showAuthor_info').datagrid({
			url:'AuthorNovel',   
			queryParams: {aid:aid},
			fit:true,
			fitColumns:true,
			singleSelect:true,
			border:true,  //显示边框
			loadMsg:'数据加载中...',
			pageSize:5,
			pageList:[5,10,15,20,25],
			pagination:true, //显示分页栏
			striped:true,		//斑马线效果
			nowrap:true,		//超出宽度自动截取
			rownumbers:true,	//显示行数
			sortName:'nid',		//排序的咧
			//remoteSort:false,	//前段排序而非服务器的排序，自己的排序
			columns:[[	
						{field : 'nnid',checkbox: true },
						{field : 'nid',title : '书籍编号',width : 20,align : 'center',sortable : true,hidden:true},
						{field : 'npicture',title : '书籍封面',width : 20,align : 'center',hidden:false,formatter : function(value, row, index) {
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
						{field : 'tname',title : '类型',hidden:false,width : 20,align : 'center'},
						/* {field : 'pan_name',title : '作者',width : 30,align : 'center'},
						{field : 'nname',title : '名字',hidden:false,width : 20,align : 'center',formatter: function(val,row,index){
							 if(val){
								 return "<a href='toindex_id/"+row.nid +"' >"+val+"</a>";
							} 
						}
						}, 
						{field : 'nstatus',title : '书籍状态',width : 20,align : 'center'},
						{field : 'standby_1',title : '书籍审核状态',width : 20,align : 'center'} 
					]] 
                    });
					 */
		$('#edit').linkbutton({
			plain:true,
			text:"书籍信息编辑",
		});  
	
	});
	</script>
	<script  type="text/javascript">
	function editauthorjsp(){
			var row = $('#type_showAuthor_info').datagrid('getSelected');
			
		    /* var rows = $('#type_showAuthor_info').datagrid('getSelections'); */
		    if (row){
		    	if(row.nid!=null){
		    		window.location="editNovel/"+row.nid;
				}else{
					alert("请选择其中一本书籍");
				}
		    }
		}
	</script>
<body>
	<div id="wrapper">

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
					<li><a href="quanben">全本书籍</a></li>
					<li><a  href="toReadRecord">阅读记录</a></li>
			</ul>
		</div>
			

					<div class="item" style="text-align: center;width: 90%">
						<form action="editor" method="post">
						<table style="text-align: center;">
						<tr style="height: 28px"></tr>
						<tr style="width: 200px">
							<td style="text-align: right;">尊敬的${author[0].aname },您好!</td><td style="text-align: left;">您的信息如下</td>
						</tr>
						<tr>
							<td style="text-align: right;">您的笔名：</td><td style="text-align: left;"><span  id="pan_name">${author[0].pan_name }</td>
						</tr>
						<tr>
							<td style="text-align: right;">您的身份证号：</td><td style="text-align: left;"> <span id="acard">${author[0].acard }</span></td>
						</tr>
						<tr>
							<td style="text-align: right;">您的联系方式：</td><td style="text-align: left;"><span id="atel">${author[0].atel }</span></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;"><input type="submit" name="editor" value="作者信息编辑" /></td>
						</tr>
						</table>
						</form>
						<a href="creat" style="font-size:40px;color:#FF0000">创作新书籍</a>
					</div>
					

		<table id="type_showAuthor_info" ></table>
		<input type="button" onclick="editauthorjsp()" value="编辑书籍信息"/>
		<input type="button" onclick="writenovel()" value="添加书籍章节内容"/>
		
		
		
	<script  type="text/javascript">	
		function writenovel(){
			var row = $('#type_showAuthor_info').datagrid('getSelected');
		    if (row){
		    	if(row.nid!=null){
		    		var nid=row.nid;
		    		window.location="writeNovel/"+nid;
				}else{
					alert("请选择其中一本书籍");
				}
		    }		
		}
	</script>
		
		
		
		
		<div class="footer">
			
			<div class="footer_cont">
				<p>本站所有书籍归it专业书籍管理网站所有</p>
				<p>Copyright © 2018 it类专业书籍管理</p>
				<script>
					footer();
				</script>
			</div>
		</div>
	</div>
</body>
</html>
