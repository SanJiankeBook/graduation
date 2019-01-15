<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath(); //douban
	//http					localhost				8080               douban
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath %>">
<style id="znBdcsStyle" type="text/css">
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Cache-Control" content="no-siteapp">
<meta http-equiv="Cache-Control" content="no-transform">
<meta http-equiv="mobile-agent"
	content="format=html5; url=http://m.bixia.org/2_2476/">
<meta http-equiv="mobile-agent"
	content="format=xhtml; url=http://m.bixia.org/2_2476/">
<title>三剑客文学</title>
<meta name="keywords" content="儒道至圣,儒道至圣最新章节">
<meta name="description"
	content="儒道至圣最新章节由网友提供，《儒道至圣》情节跌宕起伏、扣人心弦，是一本情节与文笔俱佳的玄幻奇幻小说，笔下文学免费提供儒道至圣最新清爽干净的文字章节在线阅读。">
<meta property="og:type" content="novel">
<meta property="og:title" content="儒道至圣">
<meta property="og:description"
	content="这是一个读书人掌握天地之力的世界。&lt;br/&gt;&lt;br/&gt;&nbsp;&nbsp;&nbsp;&nbsp;才气在身，诗可杀敌，词能灭军，文章安天下。&lt;br/&gt;&lt;br/&gt;&nbsp;&nbsp;&nbsp;&nbsp;秀才提笔，纸上谈兵；举人杀敌，出口成章；进士一怒，唇枪舌剑。&lt;br/&gt;&lt;br/&gt;&nbsp;&nbsp;&nbsp;&nbsp;圣人驾临，口诛笔伐，可诛人，可判天子无道，以一敌国。&lt;br/&gt;&lt;br/&gt;&nbsp;&nbsp;&nbsp;&nbsp;此时，圣院把持文位，国君掌官位，十国相争，蛮族虎视，群妖作乱。&lt;br/&gt;&lt;br/&gt;&nbsp;&nbsp;&nbsp;&nbsp;...">
<meta property="og:image"
	content="http://www.bixia.org/BookFiles/BookImages/rudaozhisheng.jpg">
<meta property="og:novel:category" content="玄幻奇幻">
<meta property="og:novel:author" content="永恒之火">
<meta property="og:novel:book_name" content="儒道至圣">
<meta property="og:novel:read_url"
	content="http://www.bixia.org/2_2476/">
<meta property="og:url" content="http://www.bixia.org/2_2476/">
<meta property="og:novel:status" content="连载">
<meta property="og:novel:update_time" content="2017/3/5 0:40:57">
<meta property="og:novel:latest_chapter_name" content="第2031章 孤星伴月">
<meta property="og:novel:latest_chapter_url"
	content="http://www.bixia.org/2_2476/3433725.html">
	
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript" async=""
	src="http://znsv.baidu.com/customer_search/api/js?sid=3677118700255927857&amp;plate_url=http%3A%2F%2Fwww.bixia.org%2F2_2476%2F&amp;t=413530"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/post.js"></script>
<script type="text/javascript" src="js/wap.js"></script>
<script type="text/javascript" src="js/xiaoshuo.js"></script>
<script
	src="http://bdimg.share.baidu.com/static/js/logger.js?cdnversion=413530"></script>
<link
	href="http://bdimg.share.baidu.com/static/css/bdsstyle.css?cdnversion=20131219"
	rel="stylesheet" type="text/css">
</head>
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
	
<body>
	<iframe style="display: none;" frameborder="0"></iframe>
	<div id="bdshare_s" style="display: block;">
		<iframe id="bdsIfr"
			style="position: absolute; display: none; z-index: 9999; top: 84px; left: 1044.08px; height: 314px; width: 212px;"
			frameborder="0"></iframe>
		<div id="bdshare_l" style="display: none; left: 1044.08px; top: 84px;">
			<div id="bdshare_l_c">
				<h6>分享到</h6>
				<ul>
					<li><a href="#" class="bds_mshare mshare">一键分享</a></li>
					<li><a href="#" class="bds_qzone qqkj">QQ空间</a></li>
					<li><a href="#" class="bds_tsina xlwb">新浪微博</a></li>
					<li><a href="#" class="bds_bdysc bdysc">百度云收藏</a></li>
					<li><a href="#" class="bds_renren rrw">人人网</a></li>
					<li><a href="#" class="bds_tqq txwb">腾讯微博</a></li>
					<li><a href="#" class="bds_bdxc bdxc">百度相册</a></li>
					<li><a href="#" class="bds_kaixin001 kxw">开心网</a></li>
					<li><a href="#" class="bds_tqf txpy">腾讯朋友</a></li>
					<li><a href="#" class="bds_tieba bdtb">百度贴吧</a></li>
					<li><a href="#" class="bds_douban db">豆瓣网</a></li>
					<li><a href="#" class="bds_tsohu shwb">搜狐微博</a></li>
					<li><a href="#" class="bds_bdhome bdhome">百度新首页</a></li>
					<li><a href="#" class="bds_sqq sqq">QQ好友</a></li>
					<li><a href="#" class="bds_thx thx">和讯微博</a></li>
					<li><a href="#" class="bds_more">更多...</a></li>
				</ul>
				<p>
					<a href="#" class="goWebsite">百度分享</a>
				</p>
			</div>
		</div>
	</div>
	<div id="wrapper">
		<script>
			login();
		</script>
		<div style="display: none">
			<script
				src="http://s11.cnzz.com/stat.php?id=1261165914&amp;web_id=1261165914"
				language="JavaScript"></script>
			<script src="http://c.cnzz.com/core.php?web_id=1261165914&amp;t=z"
				charset="utf-8" type="text/javascript"></script>
			<a href="http://www.cnzz.com/stat/website.php?web_id=1261165914"
				target="_blank" title="站长统计">站长统计</a>
		</div>

		<div class="header">
			<div class="header_logo">
				<a href="http://www.bixia.org">笔下文学</a>
			</div>
			<div class="header_search">
				<script>
					search();
				</script>
				<script type="text/javascript">
					(function() {
						document
								.write(unescape('%3Cdiv id="bdcs"%3E%3C/div%3E'));
						var bdcs = document.createElement('script');
						bdcs.type = 'text/javascript';
						bdcs.async = true;
						bdcs.src = 'http://znsv.baidu.com/customer_search/api/js?sid=3677118700255927857'
								+ '&amp;plate_url='
								+ encodeURIComponent(window.location.href)
								+ '&amp;t=' + Math.ceil(new Date() / 3600000);
						var s = document.getElementsByTagName('script')[0];
						s.parentNode.insertBefore(bdcs, s);
					})();
				</script>
				<div id="bdcs">
					<div class="bdcs-container">
						<meta http-equiv="x-ua-compatible" content="IE=9">
						<!-- 嵌入式 -->
						<div class="bdcs-main bdcs-clearfix" id="default-searchbox">
							<div class="bdcs-search bdcs-clearfix" id="bdcs-search-inline">
								<form action="tosousuo" method="post" target="_blank"
									class="bdcs-search-form" autocomplete="off"
									id="bdcs-search-form">
									<input name="s" value="3677118700255927857" type="hidden" /> <input
										name="entry" value="1" type="hidden" /> <input name="nname"
										class="bdcs-search-form-input" id="bdcs-search-form-input"
										placeholder="请输入书名或作者" autocomplete="off"
										style="height: 36px; line-height: 36px;" type="text" /> <input
										class="bdcs-search-form-submit " id="bdcs-search-form-submit"
										value="搜索" type="submit" />
								</form>
							</div>
							<div class="bdcs-search-sug" id="bdcs-search-sug"
								style="top: 85px; width: 319px;">
								<ul class="bdcs-search-sug-list" id="bdcs-search-sug-list"></ul>
							</div>
						</div>
					</div>
				</div>
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
		<div class="dahengfu">
			<script type="text/javascript">
				list1();
			</script>
		</div>
		<div class="box_con">
			<div class="con_top">
				<a href="http://localhost:8080/SanJianKeBook/">IT类专业书籍管理网站</a> &gt; ${novel_id[0].nname}最新章节列表
			</div>
			<div id="maininfo">

				<div id="info">
					<h1>${novel_id[0].nname}</h1>
					<p>作&nbsp;&nbsp;者：${author[0].author.aname}</p>
					<p>
						动&nbsp;&nbsp;作：<a rel="nofollow" href="javascript:;"
							onclick="addBookCase('${novel_id[0].nid }');">加入书架</a>、
							<a target="_blank" href="txt_id/${novel_id[0].nid }" style="color: red;">TXT下载</a>
					</p>
					<p>
						最新更新：<a href="${caddress}">${cname}</a>
					</p>
				</div>

				<div id="intro">简介：&nbsp;&nbsp;&nbsp;${novel_id[0].ndescription}</div>

			</div>
			<div id="sidebar">
				<!-- 小说封面 -->
				<div id="fmimg">
					<img alt="${novel_id[0].nname}" src="${novel_id[0].npicture}"
						width="120" height="150"><span class="b"></span>
				</div>
			</div>
			<div id="listtj"></div>
		</div>
		<div class="box_con">
			<div id="list">
				<dl>




					<dt>《${novel_id[0].nname}》相关的章节</dt>


					<c:forEach items="${nchapter}" var="nchapter">
						<dd>
							<a style="" href="${nchapter.caddress}">第${nchapter.standby_2}章&nbsp;&nbsp;&nbsp;${nchapter.cname}</a>
						</dd>
					</c:forEach>

				</dl>
			</div>
		</div>
		</div>
		<div style="margin-top: 50px;margin-bottom: 50px;text-align:center; margin:auto auto">
		<h1 style="font-size: 20px">评论发表</h1>
		<textarea cols=60 rows=4 name="ndescription" id="ndescription" style="font-size:9pt;line-height:100%"></textarea><br/>
		<button onclick="publishTalk()" >点击发表</button>
	</div>
	<div style="margin-top: 80px;margin-bottom: 50px;text-align:center; margin:auto auto">
		<h1 style="font-size: 20px;margin-top: 50px" >大神评论区</h1>
		<input type="text" id="searchNovel" style="display: none;">
		<table id="type_showAuthor_info" data-options="fit:false" style="height:400px;margin-top: 150px"></table> 
	</div>
	<script type="text/javascript"> 

$(function(){
	$("#searchNovel").val("${novel_id[0].nid}");
	searchNovel();
})
//插入评论
function publishTalk(){
				var des=$("#ndescription").val();
				var nid=$("#searchNovel").val();
				if(document.getElementById("ndescription").value=="" | des==null){
			        alert("评论的类容不能为空!");
			        return false;
			    }
					$.ajax({
						url:"publishTalk",
						type:"post",
						data:{"des":des,"nid":nid},
						dataType:"JSON",
						success:function( data ){
							if(data=="1"){
								$("#ndescription").val("");
								searchNovel();
								$.messager.show({title:'成功提示',msg:'评论发表成功...',timeout:2000,showType:'slide'});
							}else{
								window.location="userlogininfo";
								$.messager.alert('失败提示','评论发表失败!请先登录','error');
							}		
						}
					});
}

//查询评论					
function searchNovel() {
	var datagridObj;
	var editRow = undefined;	//当前正在被编辑的行的索引
	var op;
	var flag;
	var easyui=$("#searchNovel").val();
	datagridObj=$('#type_showAuthor_info').datagrid({
		url:'showusertalk',   
		queryParams: {text: easyui},
		fitColumns:true,
		loadMsg:'数据加载中...',
		pagination : true, //显示分页栏
		striped:true,		//斑马线效果,显示条纹
		nowrap:true,		//超出宽度自动截取
		rownumbers:true,		//显示行数
		sortName:'utdate',		//排序的咧
		sortOrder:'desc',//排序的方式
		remoteSort:false,	//前段排序而非服务器的排序，自己的排序
		pageSize:5,
		 pageList:[5,10,15,20,25,30],
		columns:[[
					{field : 'uname',title : '用户名',width : 30,align : 'center'},
					{field : 'utcontent',title : '内容',width : 150, align : 'center'},
					{field : 'utdate',title : '日期',width : 30,align : 'center'}, 
				]]
	});
}





</script>
		
		
	
		<div>
		<div id="footer" name="footer">
			<div class="footer_link"></div>
			<div class="footer_cont">
				<p>Copyright ? 2018IT类专业书籍管理网站</p>
			</div>
		</div>
		<script>
			footer();
		</script>
	</div>

</body>
</html>