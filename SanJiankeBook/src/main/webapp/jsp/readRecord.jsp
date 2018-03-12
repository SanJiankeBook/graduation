<!-- 阅读记录 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title>临时书架只记录最新100部浏览过的小说</title>

<link rel="stylesheet" href="../css/style.css" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script language="javascript" type="text/javascript" src="../js/index.js"></script>
<!--  <script type="text/javascript" src="../js/xiaoshuo.js"></script> -->
<script type="text/javascript" src="../js/xiaoshuo.js"></script>

</head>
<body>
	<div id="wrapper">

		<script>login();</script>
		<div class="header">
			<div class="header_logo">
				<a href="http://www.bixia.org">笔下文学</a>
			</div>
			<div class="header_search">
				<script>search();</script>
			</div>
			<div class="userpanel">
				<script>banner();</script>
			</div>
		</div>
		<div class="clear"></div>
		<div class="nav">
			<ul>
				<li><a href="../index.jsp">首页</a></li>
				<li><a rel="nofollow" href="bookcase.jsp">我的书架</a></li>
				<li><a href="1-1.jsp">玄幻奇幻</a></li>
				<li><a href="2-1.jsp">武侠仙侠</a></li>
				<li><a href="3-1.jsp">都市言情</a></li>
				<li><a href="4-1.jsp">历史军事</a></li>
				<li><a href="5-1.jsp">科幻灵异</a></li>
				<li><a href="6-1.jsp">网游竞技</a></li>
				<li><a href="7-1.jsp">女频频道</a></li>
				<li><a href="rank.jsp">排行榜单</a></li>
				<li><a href="quanben.jsp">全本小说</a></li>
				<li><a rel="nofollow" href="readRecord.jsp">阅读记录</a></li>
			</ul>
		</div>
		<div id="main">
			<div class="novelslist2">
				<h2>临时书架 - 用户浏览过的小说会自动保存到书架中（只限同一电脑）</h2>
				<ul>
					<li><span class="s1"><b>序号</b></span> <span class="s2"><b>小说名称</b></span>
						<span class="s4"> <b>作者</b></span> <span class="s3"><b>最新章节</b></span>
						<span class="s5"><b>上次阅读章节</b></span> <span class="s6"><b>更新时间</b></span>
						<span class="s7"><b>操作</b></span></li>

				</ul>
			</div>
			<div class="clear"></div>
		</div>

		<div class="footer">
			<div class="footer_link"></div>
			<div class="footer_cont">
				<p>本站所有小说为转载作品，所有章节均由网友上传，转载至本站只是为了宣传本书让更多读者欣赏。</p>
				<p>Copyright © 2016 笔下文学</p>
				<script>footer();</script>
			</div>
		</div>
	</div>
</body>
</html>
