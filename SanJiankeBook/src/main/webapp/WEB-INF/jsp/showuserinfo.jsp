<!-- 书架 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	//					http				://		localhost			:	8081				/SpringMvc_Book/
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>
	用户信息
</title>
<base href="<%= basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<!--  <script type="text/javascript" src="../js/xiaoshuo.js"></script> -->
<script type="text/javascript" src="js/xiaoshuo.js"></script>
</head>
<body style="text-align: center; margin: 0 auto">
    <div id="wrapper">
        
<div class="header">
    <div class="header_logo"><a href="toindex_zpd">三剑客文学</a></div>
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
       <br />
       <input id="userUid" type="text"  value="${listuser[0].uid }" style="display: none"/>
<form name="frmLogin" id="frmLogin" action="logger" method="post">
<table class="grid" width="580" align="center">
<caption>用户信息</caption>
<tr id="oldupassword_1" style="display: none">
  <td class="odd" width="25%">用户原密码</td>
  <td class="even"><input type="password" class="text" name="upassword" id="upassword" size="25" maxlength="30" value="" style="width:160px;"/></td>
</tr>
<tr id="xinupassword_1" style="display: none">
  <td class="odd" width="25%">用户新密码</td>
  <td class="even"><input type="password" class="text" name="xinupassword" id="xinupassword" size="25" maxlength="30" value="" style="width:160px;"/></td>
</tr>
<tr id="info_1">
  <td class="odd" width="25%">用户名</td>
  <td class="even"><input type="text" class="text" name="uname" id="uname" size="25" maxlength="30" value="${listuser[0].uname }" style="width:160px;"/></td>
</tr>
<tr id="info_2">
  <td class="odd" width="25%">用户账号</td>
  <td class="even"><input type="text" class="text" name="u_number" id="u_number" size="25" maxlength="30" value="${listuser[0].u_number }" style="width:160px;"/></td>
</tr>
<tr id="usersex">
  <td class="odd" width="25%">用户性别</td>
  <td class="even"><input type="text" class="text" name="usex" id="usex" size="25" maxlength="30" value="${listuser[0].usex }" style="width:160px;"/></td>
</tr>
<tr id="usersex_2" style="display: none">
  <td class="odd" width="25%">用户性别</td>
  <td class="even"><select id="usex_2"><option>男</option><option>女</option></select></td>
</tr>
<tr id="info_3">
  <td class="odd" width="25%">用户绑定的手机号码</td>
  <td class="even"><input type="text" class="text" name="standby_1" id="standby_1" size="25" maxlength="30" value="${listuser[0].standby_1 }" style="width:160px;"/></td>
</tr>
<tr>
	<td></td>
	<td><input id="fanghui_1" type="button" value="返回" onclick="fanghui()" style="display: none" > <input id="info_4" type="button" value="确认修改密码" onclick="passwordUpdateInfo()" style="display: none" > <input id="userInfoupdate1" type="button" value="修改" onclick="userInfoupdate_1()" style="display: none" >	<input id="querenupdate" type="button" value="用户基本信息修改" onclick="updateUserInfo()" ></input><input id="passwordUpdate" type="button" onclick="passwordInfoUpdate()" value="密码修改" style="margin-left: 70px"></input></td>
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
$(function(){
	document.getElementById("uname").disabled=true;
	document.getElementById("u_number").disabled=true;
	document.getElementById("usex").disabled=true;
	document.getElementById("standby_1").disabled=true;
})
//用户密码修改
function passwordUpdateInfo(){
	$.ajax({
		url : "updatePasswordInfo",
		type : "POST",
		dataType : "JSON",//客户端返回过来的数据类型
		data : {
			'upassword' : $("#upassword").val(),
			'xinupassword' : $("#xinupassword").val()
		},
		success : function(data) {
			if (data=="0") {
				alert("原密码错误");
			} else if (data =="1") {
				alert("成功");
				window.location="showUser";
			}else{
				alert("请先登录");
			}
		}
	});
}
//返回到初始界面
function fanghui(){
	document.getElementById("uname").disabled=true;
	document.getElementById("u_number").disabled=true;
	document.getElementById("usex").disabled=true;
	document.getElementById("standby_1").disabled=true;
	$("#oldupassword_1").hide();
	$("#xinupassword_1").hide();
	$("#info_1").show();
	$("#info_2").show();
	$("#usersex").show();
	$("#usersex_2").hide();
	$("#info_3").show();
	$("#querenupdate").show();
	$("#passwordUpdate").show();
	$("#fanghui_1").hide();
	$("#info_4").hide();
	$("#userInfoupdate1").hide();
}
//用户信息修改
function userInfoupdate_1(){
	$.ajax({
		url : "updateUserInfo",
		type : "POST",
		dataType : "JSON",//客户端返回过来的数据类型
		data : {
			'uname' : $("#uname").val(),
			'usex_2' : $("#usex_2").val(),
			'standby_1' : $("#standby_1").val()
		},
		success : function(data) {
			if (data=="0") {
				alert("该用户名已经存在，请换一个用户名");
			} else if (data =="1") {
				alert("成功修改");
				window.location="showUser";
			}else{
				alert("请先登录");
			}
		}
	});
}
//打开用户密码编辑界面
function passwordInfoUpdate(){
	$("#info_3").hide();
	$("#info_2").hide();
	$("#info_1").hide();
	$("#info_4").show();//打开密码确认修改按钮
	$("#usersex").hide();
	$("#oldupassword_1").show();
	$("#xinupassword_1").show();
	$("#fanghui_1").show();
	 $("#querenupdate").hide();//确认修改
	 $("#passwordUpdate").hide();//修改密码
}
//打开用户信息编辑
function updateUserInfo(){
	document.getElementById("uname").disabled=false;
	document.getElementById("u_number").disabled=false;
	 $("#usersex").hide();//性别
	 $("#usersex_2").show();//性别修改
	 $("#querenupdate").hide();//确认修改
	 $("#passwordUpdate").hide();//修改密码
	 $("#userInfoupdate1").show();//用户信息修改按钮
	 $("#fanghui_1").show(); //返回按钮
	 $("#info_2").hide(); //返回按钮
	 
	document.getElementById("standby_1").disabled=false;
}
//忘记密码
function forgivepassword(){
	 if(document.getElementById("uname").value=="")
     {
         alert("请输入账号!1");
         return false;
     }else{
     $.post("forgivepasswordUname",
    	       { uname: $("#uname").val()},function(data){
    	    		   window.location="forgivepassword";
    	       }
    	    ); 
     }
}
function logger() {
						$.ajax({
										url : "logger",
										type : "POST",
										dataType : "JSON",//客户端返回过来的数据类型
										data : {
											'uname' : $("#uname").val(),
											'upassword' : $("#upassword").val(),
											'validateCode' : $("#validateCode").val()
										},
										success : function(data) {
											if (data.status == -2) {
												//response.sendRedirect("500.jsp");
												alert("验证码错误");
											} else if (data.status == 1) {
												window.location="toindex_zpd";
											} else {
												alert("用户名或密码错误");
											}
										}
									});
						}
</script>
        
<div class="footer">
    <div class="footer_link">
    </div>
    <div class="footer_cont">
        <p>
            本站所有小说为转载作品，所有章节均由网友上传，转载至本站只是为了宣传本书让更多读者欣赏。</p>
        <p>
            Copyright © 2016
            笔下文学</p>
       <script>footer();</script>
    </div>
</div>
    </div>
</body>
</html>
