<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path=request.getContextPath();
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<base href="<%=basePath %>">
<title>管理员登录</title>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
</head>
<body>
	<c:if test="${errmsg!='' }">
		<font style="color:red"><c:out value="${errmsg}"></c:out></font>
	</c:if>
	 <form action="adminLogin" method="post"> 
		<fieldset>
			<legend>管理后台登录</legend>
			<p>管理员ID：<input type="text"  name="adnumber"></p>
			<p>密码：<input type="password"  name="adpassword"></p>
			<p><input type="submit"  value="登录"></p>
		</fieldset>
	 </form> 
	
</body>
</html>