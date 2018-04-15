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
</head>
	<!-- 显示作者写的小说 -->
	<script  type="text/javascript">
	$(function(){
		var datagridObj;
		/* var editRow = undefined;	//当前正在被编辑的行的索引
		var op;
		var flag; */
		var aid=${author[0].aid};
		datagridObj=$('#type_showAuthor_info').datagrid({
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
						{field : 'nid',title : '小说编号',width : 20,align : 'center',sortable : true,hidden:true},
						{field : 'npicture',title : '小说封面',width : 20,align : 'center',formatter : function(value, row, index) {
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
						{field : 'tname',title : '类型',width : 20,align : 'center'},
						/* {field : 'pan_name',title : '作者',width : 30,align : 'center'}, */
						{field : 'nname',title : '名字',width : 20,align : 'center',formatter: function(val,row,index){
							 if(val){
								 return "<a href='toindex_id/"+row.nid +"' >"+val+"</a>";
							} 
						}
						}, 
						{field : 'nstatus',title : '小说状态',width : 20,align : 'center'} 
					]]
		});
		
		$('#edit').linkbutton({
			plain:true,
			text:"小说信息编辑",
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
					alert("请选择其中一本书");
				}
		    }
		}
	</script>
<body>
	<div id="wrapper">

		<script>
			login();
		</script>
		<div class="header">
			<div class="header_logo">
				<a href="http://www.bixia.org">笔下文学</a>
			</div>
			<div class="header_search">
				<script>
					search();
				</script>
			</div>
			<div class="userpanel">
				<script>
					banner();
				</script>
			</div>
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
					<li><a  href="toReadRecord">阅读记录</a></li>
			</ul>
		</div>
		

					<div class="item">
						<form action="editor" method="post">
							<h1>尊敬的${author[0].aname },你好!</h1>
							<h1>你的信息</h1>
							<h2 id="pan_name">你的笔名：${author[0].pan_name }</h2>
							<h2 id="agrade">你的称号：${author[0].agrade }</h2>
							<h2 id="acard">你的身份证号：${author[0].acard }</h2>
							<h2 id="atel">你的联系方式：${author[0].atel }</h2>
							<input type="submit" name="editor" value="作者信息编辑" />
						</form>
						<a href="creat" style="font-size:40px;color:#FF0000">创作新小说</a>
					</div>
					

		<table id="type_showAuthor_info"  style="width: 90%; height: 200px;"></table>
		<input type="button" onclick="editauthorjsp()" value="编辑小说信息"/>
		<input type="button" onclick="writenovel()" value="添加小说章节内容"/>
		
		
		
	<script  type="text/javascript">	
		function writenovel(){
			var row = $('#type_showAuthor_info').datagrid('getSelected');
		    if (row){
		    	if(row.nid!=null){
		    		var nid=row.nid;
		    		window.location="writeNovel/"+nid;
				}else{
					alert("请选择其中一天信息");
				}
		    }		
		}
	</script>
		
		
		
		
		<div class="footer">
			
			<div class="footer_cont">
				<p>本站所有小说为转载作品，所有章节均由网友上传，转载至本站只是为了宣传本书让更多读者欣赏。</p>
				<p>Copyright © 2016 笔下文学</p>
				<script>
					footer();
				</script>
			</div>
		</div>
	</div>
</body>
</html>
