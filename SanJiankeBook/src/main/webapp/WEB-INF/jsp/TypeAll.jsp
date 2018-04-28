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
<base href="<%=basePath%>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${tname}</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
<!--   <script type="text/javascript" src="../js/xiaoshuo.js"></script> -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/xiaoshuo.js"></script>

<link rel="stylesheet" type="text/css" href="easyui/css/easyui.css">
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
<!-- 根据类型显示全部小说信息 -->
<script  type="text/javascript">
	$(function(){
		var datagridObj;
		var tid=${Tlist[0].tid};
		datagridObj=$('#type_showAuthor_info').datagrid({
			url:'TypeNovel',   
			queryParams: {tid:tid},
			fit:true,
			fitColumns:true,
			loadMsg:'数据加载中...',
			pageSize:5,
			pageList:[5,10,15,20,25,30],
			pagination : true, //显示分页栏
			striped:true,		//斑马线效果
			nowrap:true,		//超出宽度自动截取
			rownumbers:true,		//显示行数
			sortName:'nid',		//排序的咧
			remoteSort:false,	//前段排序而非服务器的排序，自己的排序
			columns:[[	
						{field : 'nid',title : '小说编号',width : 20,align : 'center',sortable : true,hidden:true},
						{field : 'tname',title : '类型',width : 20,align : 'center'},
						/* {field : 'pan_name',title : '作者',width : 30,align : 'center'}, */
						{field : 'nname',title : '名字',width : 20,align : 'center',formatter: function(val,row,index){
							 if(val){
								 return "<a href='toindex_id/"+row.nid +"' >"+val+"</a>";
							} 
						}}, 
						{field : 'pan_name',title : '作者',width : 20,align : 'center'},
						{field : 'nstatus',title : '小说状态',width : 20,align : 'center'}
					]]
		});	
	});
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
		<div id="main">
			<div id="hotcontent">
				<div class="ll">

					<c:forEach items="${list1}" var="list1">
						<div class="item">
							<div class="image">
								<a href="${list1.npicture}"> <img src="${list1.npicture}"
									alt="${list1.aname}" width="120" height="150" /></a>

							</div>
							<dl>
								<dt>
									<span> ${list1.aname}</span> <a href="toindex_id/${list1.nid}">${list1.nname}</a>
								</dt>
								<dd>${list1.ndescription}</dd>
							</dl>
							<div class="clear"></div>
						</div>
					</c:forEach>
				</div>
			</div>
			<table id="type_showAuthor_info"  style="width: 100%; height: 200px; $(this).width() * 0.2"></table>
			<div id="newscontent">
				
			</div>
		</div>

		<div class="footer">
			<div class="footer_link"></div>
			<div class="footer_cont">
				<p>Copyright © 2018 IT类专业书籍</p>
					<script>
						footer();
					</script>
			</div>
		</div>
	</div>
</body>
</html>
