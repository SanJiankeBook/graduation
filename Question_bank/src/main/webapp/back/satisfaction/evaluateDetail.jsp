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
<link rel="stylesheet" type="text/css"
	href="../../jslib/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../../jslib/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/demo.css">
<script type="text/javascript" src="../../jslib/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../jslib/locale/easyui-lang-zh_CN.js"></script>
	
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
</head>
<body>

<div style="padding-left:150px;">
	<label style="font-size:15px;">最满意：&nbsp;&nbsp; </label><br>
	&nbsp;&nbsp; &nbsp;&nbsp; <textarea rows="10" cols="80" id="evaluateSatis"></textarea><br><br>
	<label style="font-size:15px;">最不满意：</label><br>
	&nbsp;&nbsp; &nbsp;&nbsp; <textarea rows="10" cols="80" id="evaluateUnsatis"></textarea><br><br>
	<a href="back/satisfaction/summarySatisfaction.jsp" style=" text-decoration:none;font-size:15px;padding-left:400px; " >回    退</a>
</div>

</body>
<script type="text/javascript">
$(function(){
	var result=decodeURI(getQueryString());
	 var arr=result.toString().split(",,");
		console.info(result);
	    var month=arr[0].split("=")[1];
		var year=arr[1].split("=")[1];
		var id=arr[2].split("=")[1];
		
		
		$.post("findEvaluateDetail.action", {
			id:id,
			month:month,
			year:year,
		}, function(data) {
			
			if (data.responseCode == 1) {
				alert("暂无汇总！");
				return;
			} else {
				$("#evaluateSatis").val(data.obj.satisfactionSummary);
				$("#evaluateUnsatis").val(data.obj.unsatisfactedSummary);
			
				
				
			}
		});
		
		
		
		
		
		
		
		
});


function getQueryString(){
    var result = location.search.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+","g")); 
	
    if(result == null){

        return "";

    }

    for(var i = 0; i < result.length; i++){

        result[i] = result[i].substring(1);

    }

    return result;

}

</script>
</html>