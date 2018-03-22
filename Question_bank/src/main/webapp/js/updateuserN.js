//在text中显示用户名和隐藏的用户id
	$(function(){
		// 从浏览器中取id（从localstorage或cookie中取）
		var userName= window.localStorage? localStorage.getItem("systemUser_userName"): Cookie.read("systemUser_userName");
		var uId= window.localStorage? localStorage.getItem("systemUser_uId"): Cookie.read("systemUser_uId");
		$("#userId").val(uId);
		$("#txtUser").val(userName);
		});
	
	//检测源密码
	var oldPasswordFlag=false;
	function checkOldPassword(){
		var txtOldPassword=$.trim($("#oldPassword").val());
		var userId=$.trim($("#userId").val());
		var userName=$.trim($("#txtUser").val());
		var txtOldPasswordReg=/^[a-zA-Z0-9_]{8,}$/;
		if(txtOldPassword==""){
			$("#oldpass_span").html("密码不能为空");
			oldPasswordFlag=false;
		}else{
			if(txtOldPasswordReg.test(txtOldPassword)){
				$.ajax({
	 				url:"/Examination2.0/systemUser_checkOldPassword.action",
	 				type:"post",
	 				datatype:"json",
	 				data:{"userId":userId,"userName":userName,"oldPassword":txtOldPassword},
	 				success:showOldPasswordSuccess
	 			});
			}else{
				$("#oldpass_span").html("密码长度不能小于8位");
				oldPasswordFlag=false;
			}
			
		}
		return false;
	}
	//从数据库中验证该原密码与用户名是否匹配
	function showOldPasswordSuccess(datainfo){
		//var datainfo= eval("(" + data + ")");
		if(datainfo.responseCode==0){
			$("#oldpass_span").html("√");
			oldPasswordFlag=true;
		}else{
			$("#oldpass_span").html("原密码错误，请重新输入");
			oldPasswordFlag=false;
		}
	}
	//验证新密码
	function checkPassword() {
		var txtPassword=$.trim($("#txtPass").val());
		var txtPasswordReg=/^[a-zA-Z0-9_]{8,}$/;
		if(txtPassword==""){
			$("#pass_Span").html("密码不能为空");
		}else{
			if(txtPasswordReg.test(txtPassword)){
				$("#pass_Span").html("√");
				return true;
			}else{
				$("#pass_Span").html("密码长度不能小于8位");
			}
			
		}
		return false;
	}
	//确认新密码
	function checkConfirm(){
		var txtConfirm=$.trim($("#txtConfirm").val());
		var txtPassword=$.trim($("#txtPass").val());
		var txtConfirmReg=/^[a-zA-Z0-9_]{8,}$/;
		if(txtConfirm==""){
			$("#confirm_Span").html("密码不能为空");
		}else{
			if(txtConfirmReg.test(txtConfirm)){
				if(txtConfirm==txtPassword){
					$("#confirm_Span").html("√");
					return true;
				}else{
					$("#confirm_Span").html("请与新密码一致");
				}
			}else{
				$("#confirm_Span").html("密码长度不能小于8位");
			}
			
		}
		return false;
	}

	//最后进行验证是否进行提交
	function updateUser() {
		var txtConfirm=$.trim($("#txtConfirm").val());
		var userId=$.trim($("#userId").val());
		//检查通过为真
		var flag = oldPasswordFlag && checkPassword() && checkConfirm();
		checkOldPassword();
		if (flag) {
			$.ajax({
 				url:"/Examination2.0/systemUser_updatePasswordById.action",
 				type:"post",
 				datatype:"json",
 				data:{"userId":userId,"newPassword":txtConfirm},
 				success:showUpdateSuccess
 			});
		} else {
			alert("验证没通过");
		}
	}
	//显示修改成功
	function showUpdateSuccess(datainfo){
		//var datainfo= eval("(" + data + ")");
		if(datainfo.responseCode==0){
			$("#span_info").text("用户信息修改成功");
		}else{
			$("#span_info").text("用户信息修改失败");
		}
	}
	//点击取消时清空
	function resetForm(){
		$("#txtConfirm").val("");
		$("#txtPass").val("");
		$("#oldPassword").val("");
		$("#confirm_Span").html("密码不能为空");
		$("#pass_Span").html("密码不能为空");
		$("#oldpass_span").html("密码不能为空");
	}

	