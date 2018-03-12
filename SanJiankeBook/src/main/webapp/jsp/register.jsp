<!-- 新用户注册 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%
	String path=request.getContextPath();
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户注册</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="../css/style.css" />
<script type="text/javascript" src="../js/xiaoshuo.js"></script>
<script type="text/javascript" src="../js/jquery.min.js"></script>

</head>
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
		<br />
		<!-- toSave有特么传不过去 -->
		<form action="<%=basePath %>toSave" method="post">
			<table class="grid" width="580" align="center">
				<caption>新用户注册</caption>
				<tr>
					<td class="odd" width="25%">用户名<span class="hottext">(必填)</span></td>
					<td class="even"><input type="text" class="text"
						name="uname" id="uname" size="25" maxlength="30"
						style="width: 160px" /></td>
				</tr>
				<tr>
					<td class="odd" width="25%">用户账号<span class="hottext">(必填)</span></td>
					<td class="even"><input type="text" class="text"
						name="u_number" id="u_number" size="25" maxlength="20"
						style="width: 160px" /> </td>
				</tr>
				<tr>
					<td class="odd" width="25%">密码<span class="hottext">(必填)</span></td>
					<td class="even"><input type="password" class="text"
						name="upassword" id="upassword" size="25" maxlength="20"
						style="width: 160px" /> </td>
				</tr>
				<tr>
					<td class="odd" width="25%">用户性别<span class="hottext">(必填)</span></td>
					<td class="even">
							<input type="radio" name="usex" id="usex" checked="yes" value="男"/>男
							<input type="radio" name="usex" id="usex"  value="女"/>女
						
					</td>
				</tr>

				<tr>
					<!--  <td class="odd" width="25%">&nbsp;
						<input type="hidden" name="action" id="action" value="toSave" />
					</td> -->
					<td class="even">
						<input type="submit" class="button" name="submit"  id="submit" value="提 交" />
					</td>
				</tr>
			</table>
		</form>


		<div class="footer">
			<div class="footer_link"></div>
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
