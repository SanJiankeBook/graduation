<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String	username=String.valueOf(request.getSession().getAttribute("userName"));
	String	userid=String.valueOf(request.getSession().getAttribute("userid"));
	String	examClass=String.valueOf(request.getSession().getAttribute("examClass"));
%>    
<style>
.must{
color:red;
}


#strSpan{
color: red;
}


.body_c{
width:100%;
height:100%;
margin:0 auto;
padding-top:100px;
background: #ccc;
}

.update_student_password_info{
width: 50%;
margin: 0 auto;
padding:20px 30px;
background: #0FB9F5;
}
</style>
<div class="body_c">
<div class="update_student_password_info">
	<form  id="update_form">
		<table  style="width: 70%;margin: 0 auto; " >
		<tr height="43">
			<td align="right">姓名:</td>
			<td><%=  username%></td>
		</tr>
		<tr height="43">
			<td align="right">原密码:</td>
			<td><input type="password" id="update_password_old"><span class="must"> * </span><span  class="must"  id="password_old_info"></span></td>
		</tr>
		<tr height="43">
			<td align="right">新密码:</td>
			<td><input type="password" id="update_password_new"><span class="must"> * </span><span  class="must"  id="password_new_info"></span></td>
		</tr>
		<tr height="43">
			<td align="right">确认密码:</td>
			<td><input type="password" id="update_password_confirm"><span class="must"> * </span><span  class="must"  id="password_confirm_info"></span></td>
		</tr>
		<tr height="36">
			<td colspan="2" align="center"><input type="button" value="修改密码" onclick="update_password('<%= examClass%>')"></td>
		</tr>
		<tr height="30">
			<td colspan="2" align="center"><span id="strSpan"></span></td>
		</tr>
	</table>
	</form>
</div>
</div>
<script type="text/javascript">

function update_password(examClass){
	var oldpwd=$("#update_password_old").val().trim()
	if(oldpwd==null|oldpwd==""){
		$("#password_old_info").html("请输入原密码")
		return ;
	}
	var flag=false;
	$.post('examineeclass_findPassword.action',{pwd:oldpwd,id:"<%= userid %>"},function(data){
		if(data.responseCode==0){
			flag=true;
			$("#password_old_info").html("")
		}else{
			flag=false;
			$("#password_old_info").html("原密码不正确")
		}
	},'json')
	if(flag){
		var pwd=$("#update_password_new").val().trim();
		var confirmpwd=$("#update_password_confirm").val().trim()
		
		if(pwd==null|pwd==""){
			$("#password_new_info").html("请输入新密码")
			return ;
		}
		
		if(confirmpwd==null|confirmpwd==""){
			$("#password_confirm_info").html("请输入确认密码")
			return ;
		}
		
		if(pwd==confirmpwd){
			flag=true;
			$("#password_confirm_info").html("")
		}else{
			flag=false;
			$("#password_confirm_info").html("两次密码不统一")
		}
		if(flag){
			
			$.post("/Examination2.0/examineeclass_updatepassword.action",{className:examClass,name:'<%=  username%>',pwd:pwd},function(obj){
				if(obj.responseCode==0){
					document.getElementById("update_form").reset()
					$("#strSpan").text("修改密码成功");
				}else{
					$("#strSpan").text(obj.errorMessage);
				}
			});
		}
	}
	
	
}

</script>
