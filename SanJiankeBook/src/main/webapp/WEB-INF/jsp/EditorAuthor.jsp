<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑作者信息</title>
<style type="text/css">
*{
			padding:100;
			margin:100 auto;
		}

</style>
</head>
<body>
	<div style="margin: 0 auto;text-align: center;">
	<h1>作家信息编辑</h1>
	<form action="saveAuthor" method="post">
	<input name="aid" value="${author[0].aid }" type="hidden" />
	作家笔名:<input type="text" name="pan_name" value="${author[0].pan_name }"><br/>
	作家年龄:<input type="text" name="aage" value="${author[0].aage }"><br/>
	作家称号:<input type="text" name="aage" value="${author[0].agrade}" disabled="disabled" /><br/>
	身份证号:<input type="text" name="acard" value="${author[0].acard}"><br/>
	联系方式:<input type="text" name="atel" value="${author[0].atel}"><br/>
	<input type="submit" value="保存">
	<input type="reset" value="重置">
	</form>
	</div>
</body>
</html>