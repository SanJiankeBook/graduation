$(function(){
		// 从浏览器中取id（从localstorage或cookie中取）
		var userName= window.localStorage? localStorage.getItem("systemUser_userName"): Cookie.read("systemUser_userName");
		var uId= window.localStorage? localStorage.getItem("systemUser_uId"): Cookie.read("systemUser_uId");
		var remark= window.localStorage? localStorage.getItem("systemUser_remark"): Cookie.read("systemUser_remark");
		var uRole= window.localStorage? localStorage.getItem("systemUser_uRole"): Cookie.read("systemUser_uRole");
		/*if(uRole=="1"){
			$("#urole1").attr('checked','checked');
		}else if(uRole=="2"){
			$("#urole2").attr('checked','checked');
		}
		$("#userId").val(uId);
		$("#txtUser").val(userName);*/
		$("#urole").val(uRole)
		$("#uid").val(uId)
		$("#username").html(userName)
		/*$.ajax({
				url:"/Examination2.0/systemUser_showSystemUserInfo.action",
				type:"post",
				datatype:"json",
				success:showUserNameInfo
		});*/
	});
	
	//显示用户名下拉框的页面实现
	function showUserNameInfo(datainfos){
		var uId= window.localStorage? localStorage.getItem("systemUser_uId"): Cookie.read("systemUser_uId");
		var optionstr="";
		if(datainfos.responseCode==0){
			if(datainfos.obj!=null){
				$.each(datainfos.obj, function(i,datainfo){
					if(uId==datainfo[0]){
						optionstr+="<option value='"+datainfo[0]+"' name='authorities' checked='checked'>"+datainfo[1]+"("+datainfo[2]+")</option>";
					}else{
						optionstr+="<option value='"+datainfo[0]+"' name='authorities'>"+datainfo[1]+"("+datainfo[2]+")</option>";	
					}
					  														
				});	
			}
			$("#userNameId").html(optionstr);
		}else{
			alert("查询用户名失败！");
		}
	}
	
	//验证密码
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
		var oldPassword=$.trim($("#oldpassword").val());
		var userId=$.trim($("#uid").val());
		var urole=$.trim($("#urole").val());
		var remark="";
		if(urole=='1'){
			remark="教员";
		}else{
			remark="管理员";
		}
		checkPassword();
		checkConfirm()
		//检查通过为真
		var flag =  checkPassword() && checkConfirm();
		if (flag) {
			$.ajax({
 				url:"/Examination2.0/systemUser_updateSystemUserInfo.action",
 				type:"post",
 				datatype:"json",
 				data:{"userId":userId,"newPassword":txtConfirm,"authorities":urole,"remark":remark,"oldPassword":oldPassword},
 				success:showUpdateSuccess
 			});
		} else {
			$("#span_info").text("用户信息修改失败");
			alert("验证没通过");
		}
	}
	//显示修改成功
	function showUpdateSuccess(datainfo){
		if(datainfo.responseCode==0){
			showUserNameOption();
			$("#span_info").text("用户信息修改成功");
			$("#txtConfirm").val("");
			$("#txtPass").val("");
			$("#oldpassword").val("");
		}else{
			$("#span_info").text(datainfo.errorMessage);
		}
	}
	
	//显示用户名下拉框
	function showUserNameOption(){
		$.ajax({
				url:"/Examination2.0/systemUser_showSystemUserInfo.action",
				type:"post",
				datatype:"json",
				success:showUserNameInfo
			});
	}
	
	//点击取消时清空
	function resetForm(){
		$("#txtConfirm").val("");
		$("#txtPass").val("");
		$("#confirm_Span").html("密码不能为空");
		$("#pass_Span").html("密码不能为空");
		$("#span_info").text("");
	}