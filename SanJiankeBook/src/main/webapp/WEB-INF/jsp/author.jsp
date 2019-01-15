<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/post.js"></script>
<script type="text/javascript" src="js/wap.js"></script>
<script type="text/javascript" src="js/xiaoshuo.js"></script>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作家注册专区</title>
</head>
<body style="text-align:center; margin:auto auto">
	<h1>三剑客作家注册专区</h1>
	<form id="form1" action="#" method="post" style="margin-top:100px">
		作家笔名&nbsp;&nbsp;&nbsp;<input type="text" id="uname" name="uname"> <label id="uname_1"></label></label><br/><br/>
		作家真实姓名<input type="text" name="aname" id="aname"> <label id="aname_1"></label><br/><br/>
		作家年龄&nbsp;&nbsp;<input type="text" name="aage" id="aage"> <label id="aage_1"></label><br/><br/>
		作家身份证号<input type="text" name="acard" id="acard"> <label id="acard_1"></label><br/><br/>
		作家联系方式<input type="text" name="atel" id="atel"> <label id="atel_1"></label><br/><br/>
		账号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="u_number" id="u_number"> <label id="u_number_1"></label><br/><br/>
		密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" name="upassword" id="upassword"> <label id="upassword_1"></label><br/><br/>
		性别<select id="usex" name="usex" style="margin-left:100px">
			<option value="男">男</option>
			<option value="女">女</option>
		</select><br/>
		<input type="reset" value="重置">
		<input type="button" value="注册" onclick="registauthor()"/>
	</form>
	<script type="text/javascript">  
	function registauthor(){
		var uname = $("#uname").val(); 
        var aname = $("#aname").val();  
        var aage = $("#aage").val();  
        var acard = $("#acard").val();   
        var atel = $("#atel").val();   
        var u_number = $("#u_number").val();  
        var upassword = $("#upassword").val();   
        $("#uname_1").text(""); 
        $("#aname_1").text(""); 
        $("#aage_1").text(""); 
        $("#acard_1").text(""); 
        $("#atel_1").text("");  
        $("#u_number_1").text("");  
        $("#upassword_1").text("");  
        if(uname.length == 0)  
        {  
            $("#uname_1").text("作家笔名不能不能为空！");  
            $("#uname_1").css({"color":"red"});  
           return false;
        }  
        if(aname.length == 0)  
        {  
            $("#aname_1").text("作家名字不能不能为空！");  
            $("#aname_1").css({"color":"red"});  
           return false;
        }  
        if(aage.length == 0)  
        {  
            $("#aage_1").text("年龄不能不能为空！");  
            $("#aage_1").css({"color":"red"});  
           return false;
        }  
        if(acard.length == 0)  
        {  
            $("#acard_1").text("身份证号不能不能为空！");  
            $("#acard_1").css({"color":"red"});  
           return false;
        }  
        if(atel.length == 0)  
        {  
            $("#atel_1").text("联系方式不能为空！");  
            $("#atel_1").css({"color":"red"});  
            
           return false;
        }  
        if(u_number.length == 0)  
        {  
            $("#u_number_1").text("账号不能为空！");  
            $("#u_number_1").css({"color":"red"});  
           return false;
        }  
        if(upassword.length == 0)  
        {  
            $("#upassword_1").text("密码不能为空！");  
            $("#upassword_1").css({"color":"red"});  
           return false;
        } 
        $.ajax({
			url:"registauthor",
			type:"post",
			data:{"uname":uname,"aname":aname,"aage":aage,"acard":acard,"atel":atel,"u_number":u_number,"upassword":upassword},
			dataType:"JSON",
			success:function( data ){
				if(data=="1"){
					alert('注册成功...');
					window.location="toAuthorlogger";
				}else if(data=="0"){
					alert('失败提示，注册失败，用户名已存在');
				}else if(data=="-1"){
					alert('失败提示，注册失败，账号已存在');
				}
				
				
			}
		});
	} 
</script> 
</body>
</html>