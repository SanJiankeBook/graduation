	//检测源密码
	var userNameFlag=false;
	function checkUserName(){
		var txtUser=$.trim($("#txtUser").val());
		var txtUserReg=/^[\u4e00-\u9fa5_a-zA-Z0-9]{1,}$/;
		if(txtUser==""){
			$("#user_Span").html("用户名不能为空");
			userNameFlag=false;
		}else{
			if(txtUserReg.test(txtUser)){
				$.ajax({
	 				url:"/Examination2.0/systemUser_checkUserNameIsExist.action",
	 				type:"post",
	 				datatype:"json",
	 				data:{"userName":txtUser},
	 				success:showTxtUserSuccess
	 			});
			}else{
				$("#user_Span").html("用户名必须为汉字或字母");
				userNameFlag=false;
			}
			
		}
		return false;
	}
	//从数据库中验证该原密码与用户名是否匹配
	function showTxtUserSuccess(datainfo){
		if(datainfo.responseCode==0){
			$("#user_Span").html("√");
			userNameFlag=true;
		}else{
			$("#user_Span").html("用户名已存在，请更换");
			userNameFlag=false;
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
	function addUserInfo() {
		var txtConfirm=$.trim($("#txtConfirm").val());
		var userName=$.trim($("#txtUser").val());
		var urole=$.trim($("input[name='userRole']:checked").val());
		var remark="";
		if(urole=='1'){
			remark="教员";
		}else{
			remark="管理员";
		}
		
		//检查通过为真
		 
		checkUserName();
		var flag = userNameFlag && checkPassword() && checkConfirm();
		
		if (flag) {
			$.ajax({
 				url:"/Examination2.0/systemUser_addUserInfo.action",
 				type:"post",
 				datatype:"json",
 				data:{"userName":userName,"newPassword":txtConfirm,"authorities":urole,"remark":remark},
 				success:showAddSuccess
 			});
		} else {
			$("#span_info").text("用户信息添加失败");
			alert("验证没通过");
		}
	}
	//显示修改成功
	function showAddSuccess(datainfo){
		if(datainfo.responseCode==0){
			$("#span_info").text("用户信息添加成功");
			$("#txtConfirm").val("");
			$("#txtPass").val("");
			$("#txtUser").val("");
			$("#confirm_Span").html("");
			$("#pass_Span").html("");
			$("#user_Span").html("");
		}else{
			$("#span_info").text("用户信息添加失败");
		}
	}
	//点击取消时清空
	function resetForm(){
		$("#txtConfirm").val("");
		$("#txtPass").val("");
		$("#txtUser").val("");
		$("#confirm_Span").html("密码不能为空");
		$("#pass_Span").html("密码不能为空");
		$("#user_Span").html("用户名不能为空");
		$("#span_info").text("");
	}

