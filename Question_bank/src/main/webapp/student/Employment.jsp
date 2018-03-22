<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
!window.jQuery && document.write('<script src=../../js/jquery-1.9.1.js><\/script>');
</script>
<script type="text/javascript" language="javascript"
	src="../../js/jquery-1.9.1.js"></script>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />

<style type="text/css">
.body_left{
width: 400px;
float: left;
height: 800px;
}

body{
width: 100%;
}
.body_right{
width: auto;
float: left;
height: 800px;
}
.map{
width: 100%;
height: 800px;

}

</style>

</head>
<body>

<div class='body_left'>
	<iframe class='map' src="map.html" height="500px" width="400px"></iframe>
</div>
</body>


</html>