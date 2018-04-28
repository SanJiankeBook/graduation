<!-- 书架 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>
	发送验证码
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<!--  <script type="text/javascript" src="../js/xiaoshuo.js"></script> -->

</head>
<body>
    <div id="wrapper">
        

<div class="header">
    <div class="header_logo"><a href="http://www.bixia.org">笔下文学</a></div>
    <div class="header_search"><script>search();</script></div>
    <div class="userpanel"><script>banner();</script></div>
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
       <br />
<form name="frmLogin" id="frmLogin" action="logger" method="post">
<table class="grid" width="580" align="center">
<caption>密码修改</caption>
<tr>
  <td class="odd" width="25%">输入绑定账号的手机号码</td>
  <td class="even"><input type="text" class="text" name="standby_1" id="standby_1" size="25" maxlength="30" value="" style="width:160px"/></td>
</tr>
<tr id="yanzhenma" style="display: none">
  <td class="odd" width="25%">验证码</td>
  <td class="even"><input type="text" class="text" name="number" id="number" size="25" maxlength="30" value="" style="width:160px"/></td>
</tr>
<tr id="xinpassword" style="display: none">
  <td class="odd" width="25%">新密码</td>
  <td class="even"><input type="password" class="text" name="xinpassword1" id="xinpassword1" size="25" maxlength="20" style="width:160px"/></td>
</tr>
<tr id="fasong">
  <td class="odd" width="25%">&nbsp;<input type="hidden" name="action" id="action" value="login" /></td>
  <td class="even"><input type="button" class="button" name="submit" onclick="logger()"  id="submit" value="发送" /></td>
</tr>
<tr id="queren" style="display: none;">
  <td class="odd" width="25%">&nbsp;<input type="hidden" name="action" id="action" value="login" /></td>
  <td class="even"><input type="button" class="button" name="submit" onclick="loggerInfo()"  id="submit" value="确认" /></td>
</tr>

<tr id="querenupdate" style="display: none;">
  <td class="odd" width="25%">&nbsp;<input type="hidden" name="action" id="action" value="login" /></td>
  <td class="even"><input type="button" class="button" name="submit" onclick="updatepassword()"  id="submit" value="确认修改" /></td>
</tr>
</table>
</form>
<script type="text/javascript">
//刷新验证码
function loadImage(){
	var img=document.getElementById("randImg");
	img.src="imageCode.jsp?rb="+Math.random();
	//加了个随机数后，不会再从缓存拿数据了
}

function logger() {
	if(document.getElementById("standby_1").value=="")
    {
        alert("请输入手机号!");
        return false;
    }else{
   	 var tel = document.getElementById("standby_1").value;
   	 var reg = /^0?1[3|4|5|8|7][0-9]\d{8}$/;
   	 if (reg.test(tel)) {
   		$.ajax({
			url : "sendcode",
			type : "POST",
			dataType : "JSON",//客户端返回过来的数据类型
			data : {
				'standby_1' : $("#standby_1").val()
			},
			success : function(data) {
				if (data == -2) {
					alert("该手机号码与用户不匹配");
					$("#standby_1").html("");
					$("#standby_1").val("");
				} else if (data== 1) {
					  alert("号码正确~");
			   	      $("#yanzhenma").show();
			   	      $("#fasong").hide();
			   	      $("#queren").show();
			   	   document.getElementById("standby_1").disabled=true;
				}
			}
		});
   	 }else{
   	      alert("号码有误~");
   	 };
    }			
	}
function loggerInfo(){
	if(document.getElementById("number").value=="")
    {
        alert("验证码不能为空!");
        return false;
    }else{
    	$.ajax({
			url : "judgecode",
			type : "POST",
			dataType : "JSON",//客户端返回过来的数据类型
			data : {
				'number' : $("#number").val()
			},
			success : function(data) {
				 if (data== 1) {
					 $("#xinpassword").show();
					 $("#querenupdate").show();
					 $("#yanzhenma").hide();
					 $("#queren").hide();
				} else if(data==0){
					alert("验证码错误");
				}else{
					window.location="toindex_zpd";
				}
			}
		});
    }
}
//修改密码
function updatepassword(){
	
	if(document.getElementById("xinpassword1").value=="")
    {
        alert("新密码不能为空!");
        return false;
    }else{
    	$.ajax({
			url : "updatepassword",
			type : "POST",
			dataType : "JSON",//客户端返回过来的数据类型
			data : {
				'xinpassword' : $("#xinpassword1").val()
			},
			success : function(data) {
				 if (data== 1) {
					alert("修改成功");
					window.location="userlogininfo";
				} else {
					alert("修改失败");
				}
			}
		});
    }
}
</script>
        
<div class="footer">
    <div class="footer_link">
    </div>
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
