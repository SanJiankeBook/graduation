<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@include file="head.jsp" %>


<title>登陆界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="jslib/themes/default/easyui.css"
	type="text/css"></link>
<link rel="stylesheet" href="jslib/themes/icon.css" type="text/css"></link>

<script type="text/javascript" src="jslib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jslib/locale/easyui-lang-zh_CN.js"></script>
<%-- <%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath1%>" /> --%>

<style type="text/css">
body{
	background: url("images/loginbg.gif");
}
</style>
<script type="text/javascript">

	$(function() {
		/*
		 *prop与 attr的区别: http://www.tuicool.com/articles/VFnIve7
		 *   张影
		*/
		$("#teacher").prop("checked",false);//单选框，false表明开始界面不会选这个框 
		$("#student").prop("checked",true);
		
		 var top = getTopWinow(); //获取当前页面的顶层窗口对象
		if(top != window){
		    top.location.href = location.href; //跳转到登陆页面
		 	//document.parent.ReLogin();
		}  
		chooseStudent();
	});
	/*
	*这个方法用来获取当前页面的最顶层对象
	*/
	function getTopWinow(){  
	    var p = window;  
	    while(p != p.parent){  
	        p = p.parent;  
	    }  
	    return p;  
	}
	
	//刷新验证码
	 function loadImage(){
		 var img = document.getElementById("randImg");
		 img.src="imageCode.jsp?r="+ Math.random();
	 }
	 //教师登录
	 function chooseTeacher(){
		$("#uname").val("");
		$("#password").val("");
		$("#validateCode").val("");
		var str="";
		str+="<td colspan='2'></td>";
		$("#examClassTr").html(str);
	 }
	//学生登录
	function chooseStudent(){
		$("#uname").val("");
		$("#password").val("");
		$("#validateCode").val("");
		$.post("login_findClass.action", function(data) {
			//加载学生端信息
			var str="";
			str+="<td>班级:</td>";
			str+="<td><select id='examClass' style='width: 160px;'></select>";
			str+="</td>"
			var examClass = $("#examClassTr").html(str);
			//加载下拉列表
			var className="";
			$.each(data.obj,function(i,item){
				className+='<option value ="'+item+'">'+item+'</option>';
			});
			var examClass = $("#examClass").html(className);
		});
	}
	//登录函数
	function submitForm() {
		var identity = $("input[name='identity']:checked").val();
		//判断登录者身份，如果是教员则不显示班级，如果是学员则显示
		if(identity == 1) {
			var examClass = $("#examClass").val().trim();
		}
		var uname = $("#uname").val().trim();
		var password = $("#password").val().trim();
		var validateCode = $("#validateCode").val().trim();
		$.post("login_login.action", {
			identity : identity,
			examClass : examClass,
			uname : uname,
			password : password,
			validateCode : validateCode
		}, function(data) {
			if(data==0){
				alert("验证码错误");
			}else if(data==1){
				location.href="student/student.jsp";
			}else if(data==2){
				alert("密码错误或姓名与班级不匹配");
			}else if(data==3){
				location.href="back/teacher.jsp";
			}
		});
	}
	
	document.onkeydown = function(e){
	    var e = window.event   ?   window.event   :   e; 
	    if(e.keyCode == 13){
	    	submitForm()
	    }
	}
	
	
	/* $(document).ready(function () {    
	    if(window != top) {    
	        top.location.href = location.href;    
	    }    
	});  */ 
	//注册函数
	function regForm() {
		
	}
</script>
</head>

<body>
	<div id="dlg" class="easyui-dialog" title="用户登录" style="width:400px;height:390px;padding:20px 70px 10px 70px;" data-options="closable:false">
		<table cellpadding="5">
			<tr>
				<td colspan="2"><img src="images/logo.png" width="260px"></img></td>
			</tr>
			<tr>
				<td width="80px">身份:</td>
				<td><input type="radio" id="student" name="identity" value="1" checked="checked" onclick="chooseStudent()"/>学员
					<input type="radio" id="teacher" name="identity" value="2" onclick="chooseTeacher()"/>教员
				</td>
			</tr>
			<tr id="examClassTr">
				<td>班级:</td>
				<td>
					<!-- <input class="easyui-validatebox textbox" type="text" id="examClass" name="examClass" data-options="required:true"></input> -->
					<select id="examClass" style="width: 160px;" value="YC_7"></select>
				</td>
			</tr>
			<tr>
				<td>姓名:</td>
				<td><input class="easyui-validatebox textbox" type="text"
					id="uname" name="uname" data-options="required:true" ></input>
				</td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input class="easyui-validatebox textbox" type="password"
					id="password" name="password" data-options="required:true" ></input>
				</td>
			</tr>
			<tr>
				<td>验证码:</td>
				<td><input class="easyui-validatebox textbox" type="text"
					id="validateCode" name="validateCode" data-options="required:true"></input>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<img id="randImg" border=0 src="imageCode.jsp">
   					<a href="javascript:loadImage();">换一张</a>
				</td>
			</tr>
		</table>
		<div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width: 260px;">登录</a>&nbsp;&nbsp;&nbsp;&nbsp; 
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" onclick="regForm()">注册</a> -->
		</div>
	</div>
</body>
</html>

