<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%
	String path=request.getContextPath();
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>管理员登录</title>
</head>
<body>
	
	<form action="<%=basePath %>adminLogin" method="post">
		<fieldset>
			<legend>管理后台登录</legend>
			<p>管理员ID：<input type="text" name="adnumber"></p>
			<p>密码：<input type="password" name="adpassword"></p>
			<p><input type="submit" value="登录"></p>
		</fieldset>
	</form>
</body>
</html>