//登录函数
	function submitForm() {
		/*var identity = $("input[name='identity']:checked").val();
		//判断登录者身份，如果是教员则不显示班级，如果是学员则显示
		if(identity == 1) {
			var examClass = $("#examClass").val().trim();
		}*/
		var uname = $("#uname").val().trim();
		var password = $("#password").val().trim();
	/*	var validateCode = $("#validateCode").val().trim();*/
		$.post("http://localhost:8080/Question_bank/login_login.action", {
			uname : uname,
			password : password,
		}, function(data) {
			 if(data==2){
				alert("用户名或密码错误");
			}else if(data==3){
				location.href="back/teacher.jsp";
			}
		});
	}
	