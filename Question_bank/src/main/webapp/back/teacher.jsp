<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset='UTF-8'>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<title>主界面</title>
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="jslib/themes/default/easyui.css">
<link rel="stylesheet" href="css/extreme.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="jslib/themes/icon.css">
<link rel="stylesheet" href="css/szindex.css" type="text/css"></link>


<script type="text/javascript" src="jslib/jquery.min.js"></script>
<script type="text/javascript" src="jslib/jquery.cookie.js"></script>
<script type="text/javascript" src="jslib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jslib/jeasyui.common.js"></script>

<script type="text/javascript" src="js/createoption.js"></script>
<script type="text/javascript" src="js/ajax.js"></script>
<script type="text/javascript" src="jslib/locale/easyui-lang-zh_CN.js"></script>


<script type="text/javascript" src="js/creatXML.js"></script>
<script type="text/javascript" src="js/sendrequest.js"></script>
<script type="text/javascript" src="js/check.js"></script>


<script type="text/javascript">
	$(function() {
		$("#class_panel").layout('collapse','east');
	     //绑定右键菜单事件
	          $(".easyui-tabs").bind('contextmenu',function(e){         
	         e.preventDefault();  
	            $('#rcmenu').menu('show', {  
	                left: e.pageX,  
	                top: e.pageY  
	            });  
	        });  
	        //关闭所有标签页  
	         $("#closeall").bind("click",function(){  
	            var tablist =$('#back_mainTab').tabs('tabs');
	             console.log(tablist);
	          //  return; 
	            for(var i=tablist.length-1;i>=1;i--){  
	                $('#back_mainTab').tabs('close',i);  
	            }  
	        });   
	        //关闭其他页面（先关闭右侧，再关闭左侧）  
	        $("#closeother").bind("click",function(){  
	            var tablist = $('#back_mainTab').tabs('tabs');  
	            var tab = $('#back_mainTab').tabs('getSelected');  
	            var index = $('#back_mainTab').tabs('getTabIndex',tab);  
	            for(var i=tablist.length-1;i>index;i--){  
	                $('#back_mainTab').tabs('close',i);  
	            }  
	            var num = index-1;  
	            if(num < 1){  
	                return;  
	            }else{  
	                for(var i=num;i>=1;i--){  
	                    $('#back_mainTab').tabs('close',i);  
	                }  
	                $("#back_mainTab").tabs("select", 1);  
	            }  
	        });  
	        //关闭右边的选项卡
	        $("#closeright").bind("click",function(){  
	            var tablist = $('#back_mainTab').tabs('tabs');  
	            var tab = $('#back_mainTab').tabs('getSelected');  
	            var index = $('#back_mainTab').tabs('getTabIndex',tab);  
	            for(var i=tablist.length-1;i>index;i--){  
	                $('#back_mainTab').tabs('close',i);  
	            }  
	        });  
	        //关闭右边的选项卡
	        $("#closeleft").bind("click",function(){  
	            var tablist = $('#back_mainTab').tabs('tabs');  
	            var tab = $('#back_mainTab').tabs('getSelected');  
	            var index = $('#back_mainTab').tabs('getTabIndex',tab);  
	            var num = index-1;  
	            if(num < 1){  
	                return;  
	            }else{  
	                for(var i=num;i>=1;i--){  
	                    $('#back_mainTab').tabs('close',i);  
	                }  
	                $("#back_mainTab").tabs("select", 1);  
	            }   
	        });  
		
			//TODO:由于该系统不需要登陆，所以这个登陆验证需要注释掉
		<%-- $.getJSON("login_isLogin.action", function(data) {
			//修改:  张影
			var uname=data.obj[0].username;
			var flag=data.obj[0].flag;
			if(  flag=="true"){
				var unameStr = "";
				unameStr += '教员：[<strong>' + uname
						+ '</strong>]，欢迎您进入源辰在线考试系统！';
				$("#sessionInfoDiv").html(unameStr);
			}else{
				alert("请先登录！！！");
				location.href = "<%=basePath%>";
			}
			/*
			$.each(data, function(i, item) {
				var flag = item[0].flag;
				var uname = item[0].username;
				if (flag == "true") {
					var unameStr = "";
					unameStr += '教员：[<strong>' + uname
							+ '</strong>]，欢迎您进入源辰在线考试系统！';
					$("#sessionInfoDiv").html(unameStr);
				} else {
					
				}
			});
			 */
		}); --%>
		
		$("#back_mainTab").tabs('add', {
			title : '主界面',
			closable : false,
			href : 'back/welcome/welcome.html'
		});
		//该系统不需要权限管理
		/* $.post("login_showUserInfo.action", function(data) {
			var systemUser = data.obj[0];
			var a = '';
			//FIXME   : 修改改成权限控制模块    张影
			//TODO:
			if (systemUser.uRole == '1') {
				document.getElementById("_easyui_tree_36").style.display = " ";
				document.getElementById("_easyui_tree_37").style.display = " ";
				document.getElementById("_easyui_tree_38").style.display = " ";
				document.getElementById("_easyui_tree_39").style.display = " ";
				document.getElementById("_easyui_tree_40").style.display = " ";
			} else if (systemUser.uRole == '2') {
				document.getElementById("_easyui_tree_36").style.display = "";
				document.getElementById("_easyui_tree_37").style.display = "";
				document.getElementById("_easyui_tree_38").style.display = "";
				document.getElementById("_easyui_tree_39").style.display = "";
				document.getElementById("_easyui_tree_40").style.display = "";
			}
			if (window.localStorage) {
				localStorage
						.setItem("systemUser_userName", systemUser.userName);
				localStorage.setItem("systemUser_uRole", systemUser.uRole);
				localStorage.setItem("systemUser_uId", systemUser.uId);
				localStorage.setItem("systemUser_remark", systemUser.remark);
			} else {
				Cookie.write("systemUser_userName", systemUser.userName);
				Cookie.write("systemUser_uRole", systemUser.uRole);
				Cookie.write("systemUser_uId", systemUser.uId);
				Cookie.write("systemUser_remark", systemUser.remark);
			}
			/*
			$
					.each(
							data,
							function(i, item) {
								//var systemUser= eval("("+data+")");
								var systemUser = item[0];
								var a = '';
								if (systemUser.uRole == '1') {
									document
											.getElementById("_easyui_tree_36").style.display = "none";
									document
											.getElementById("_easyui_tree_37").style.display = "none";
									document
											.getElementById("_easyui_tree_38").style.display = "none";
									document
											.getElementById("_easyui_tree_39").style.display = "";
									document
											.getElementById("_easyui_tree_40").style.display = "";
								} else if (systemUser.uRole == '2') {
									document
											.getElementById("_easyui_tree_36").style.display = "";
									document
											.getElementById("_easyui_tree_37").style.display = "";
									document
											.getElementById("_easyui_tree_38").style.display = "";
									document
											.getElementById("_easyui_tree_39").style.display = "none";
									document
											.getElementById("_easyui_tree_40").style.display = "none";
								}
								if (window.localStorage) {
									localStorage
											.setItem(
													"systemUser_userName",
													systemUser.userName);
									localStorage.setItem(
											"systemUser_uRole",
											systemUser.uRole);
									localStorage.setItem(
											"systemUser_uId",
											systemUser.uId);
									localStorage
											.setItem(
													"systemUser_remark",
													systemUser.remark);
								} else {
									Cookie
											.write(
													"systemUser_userName",
													systemUser.userName);
									Cookie.write(
											"systemUser_uRole",
											systemUser.uRole);
									Cookie.write(
											"systemUser_uId",
											systemUser.uId);
									Cookie
											.write(
													"systemUser_remark",
													systemUser.remark);
								}
							});*/
		//}); */

	});
	
	

	//格式化日期
	$.fn.datebox.defaults.formatter = function(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
	}

	$.extend($.fn.layout.methods, {
		full : function(jq) {
			return jq.each(function() {
				var layout = $(this);
				var center = layout.layout('panel', 'center');
				center.panel('maximize');
				center.parent().css('z-index', 10);

				$(window).on('resize.full', function() {
					layout.layout('unFull').layout('resize');
				});
			});
		},
		unFull : function(jq) {
			return jq.each(function() {
				var center = $(this).layout('panel', 'center');
				center.parent().css('z-index', 'inherit');
				center.panel('restore');
				$(window).off('resize.full');
			});
		}
	});

	//全屏显示中间部分
	function full() {
		$("body").layout("full");

	}
	function unFull() {
		$("body").layout("unFull");

	}

	function addTab(title, iconCls, closable, href, content) {
		var tab = $("#back_mainTab");
		if (tab.tabs('exists', title)) {
			tab.tabs('select', title);
		} else {
			if (content != null || href != "") {
				tab.tabs('add', {
					title : title,
					iconCls : iconCls,
					closable : closable,
					content : content,
				});
			} else if (href != null || href != "") {
				tab.tabs('add', {
					title : title,
					iconCls : iconCls,
					closable : closable,//设置面板的关闭
					href : href
				});
			}
		}
		var str="录入笔试题";
		var str2="出笔试卷";
		if(title==str || str2==title){
			full();
		}
		/*
		if (href == null || href == "") {
			//判断这个obj中的title是否已经在tab中存在
			if (tab.tabs('exists', title)) {
				tab.tabs('select', title);
			} else {
				tab.tabs('add', {
					title : title,
					iconCls : iconCls,
					closable : closable,
					content : content,
				});
			}
		} else if (content == null || content == "") {
			if (tab.tabs('exists', title)) {
				tab.tabs('select', title);
			} else {
				tab.tabs('add', {
					title : title,
					iconCls : iconCls,
					closable : closable,
					href : href
				});
			}
		}
		 */
	}
</script>
</head>
<body class="easyui-layout" id="class_panel">
	<!-- 头部 -->
	<div data-options="region:'north',border:false" style="height: 83px;">
		
		 	<div><p><table cellSpacing=0 cellPadding=0 align=center 
		 	background="">
<tbody> <tr> <td style="FILTER: chroma(color=#336699)">
<table align=center background=""><tbody><tr>
<td align=middle><font style="FONT-WEIGHT: normal; FONT-SIZE: 40pt; LINE-HEIGHT: normal; FONT-STYLE: normal; 
FONT-VARIANT: normal" face=华文行楷 color=#336699><strong>试卷生成系统</strong></font></td></tr></tbody></table></td></tr>
</tbody></table></p></div>
		
	</div>
	<!-- <div data-options="region:'north',border:false" style="height: 83px;">
		<img style="padding-left: 20px;" height="80px" width="337px"
			class="mainpage_head_img" alt="公司logo" src="images/logo.png" />

		<div id="sessionInfoDiv"
			style="position: absolute; right: 5px; top: 25px;">
			登录界面
		</div>
		<div style="position: absolute; right: 0px; bottom: 5px;">
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">信息中心</a>
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_zxMenu',iconCls:'icon-back'">注销</a>
		</div>
		<div id="layout_north_pfMenu" style="width: 120px; display: none;">
			<div onclick="changeTheme('default');">默认皮肤</div>
			<div onclick="changeTheme('gray');">灰色惬意</div>
			<div onclick="changeTheme('metro-green');">现代绿色</div>
			<div onclick="changeTheme('metro-orange');">现代橙色</div>
			<div onclick="changeTheme('metro-red');">深红沐浴</div>
		</div>
		<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
			<div onclick="userInfoFun();">修改密码</div>
			<div class="menu-sep"></div>
			<div onclick="aboutUs();">联系我们</div>
		</div>
		<div id="personalInfo" class="easyui-dialog" title="修改密码"
			data-options="modal:true,closed:true,buttons:[{
				text:'确认',
				handler:function(){
					//确认修改密码
					var updateUname=$('#updateUname').val();
					var oldPassword=$('#oldPassword').val();
					var newPassword=$('#newPassword').val();
					var newPasswordAgain=$('#newPasswordAgain').val();
					if(newPassword==newPasswordAgain){
						$.getJSON('login_updateTeacherPassWord.action', {examineeName:updateUname,oldPwd:oldPassword,newPwd:newPassword}, function(data) {
							if(data==0){
								//data为0，表示修改密码不成功，原密码不正确
								//清空密码框
								$('#oldPassword').val('');
								$('#newPassword').val('');
								$('#newPasswordAgain').val('');
								alert('修改密码不成功，原密码不正确');
								return;
							}else if(data==1){
								//data为1，表示修改密码成功
								//清空密码框
								$('#oldPassword').val('');
								$('#newPassword').val('');
								$('#newPasswordAgain').val('');
								alert('修改密码成功');
								$('#personalInfo').window('close');
							}else if(data==2){
								//data为2，表示修改密码失败
								//清空密码框
								$('#oldPassword').val('');
								$('#newPassword').val('');
								$('#newPasswordAgain').val('');
								alert('修改密码失败');
								$('#personalInfo').window('close');
							}
						});
					}else{
						alert('两次输入密码不一致，请重新输入');
						//清空密码框
						$('#newPassword').val('');
						$('#newPasswordAgain').val('');
						return;
					}
				}
			},{
				text:'取消',
				handler:function(){
					//取消修改密码
					//实现方法就是清空所有的文本框
					$('#oldPassword').val('');
					$('#newPassword').val('');
					$('#newPasswordAgain').val('');
				}
			}]"
			style="width: 400px; height: 260px; padding: 20px;">
			<table cellpadding="5" align="center">
				<tr>
					<td>用户名:</td>
					<td><input class="easyui-validatebox textbox" type="text"
						id="updateUname" style="background: #F5F5F5;" disabled="disabled"></input></td>
				</tr>
				<tr>
					<td>原密码:</td>
					<td><input class="easyui-validatebox textbox" type="password"
						id="oldPassword" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>新密码:</td>
					<td><input class="easyui-validatebox textbox" type="password"
						id="newPassword" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>确认新密码:</td>
					<td><input class="easyui-validatebox textbox" type="password"
						id="newPasswordAgain" data-options="required:true"></input></td>
				</tr>
			</table>
		</div>
		<div id="aboutUsInfo" class="easyui-dialog" title="联系我们"
			style="width: 800px; height: 370px;"
			data-options="modal:true,closed:true">
			<p
				style="line-height: 30px; margin-top: 30px; font-size: 24px; font-family: 华文行楷; text-align: center;">开发人员联系表</p>
			<table border=0px; cellpadding=0px; align="center">
				<tr>
					<td height="160px"><img src="images/aboutUs/Tane.jpg"
						width="150px" height="160px" title="谭振" /></td>
					<td><img src="images/aboutUs/fpc.jpg" width="150px"
						height="160px" title="付鹏程" /></td>
					<td><img src="images/aboutUs/fanhao.jpg" width="150px"
						height="160px" title="范浩" /></td>
					<td><img src="images/aboutUs/sunfei.jpg" width="150px"
						height="160px" title="孙飞" /></td>
					<td><img src="images/aboutUs/renzewen.jpg" width="150px"
						height="160px" title="任泽文" /></td>
				</tr>
				<tr>
					<td height="30px" align="center" style="line-height: 30px;">组长：谭振</td>
					<td height="30px" align="center" style="line-height: 30px;">组员：付鹏程</td>
					<td height="30px" align="center" style="line-height: 30px;">组员：范浩浩</td>
					<td height="30px" align="center" style="line-height: 30px;">组员：孙飞</td>
					<td height="30px" align="center" style="line-height: 30px;">组员：任泽文</td>
				</tr>
				<tr>
					<td align="center">QQ:632497078</td>
					<td align="center">QQ:377513462</td>
					<td align="center">QQ:1453028282</td>
					<td align="center">QQ:714278754</td>
					<td align="center">QQ:544145729</td>
				</tr>
			</table>
		</div>
		<div id="layout_north_zxMenu" style="width: 100px; display: none;">
			<div onclick="ReLogin();">重新登录</div>
			<div class="menu-sep"></div>
			<div onclick="logoutFun();">退出系统</div>
		</div>
		<div id="reLoginInfo" class="easyui-dialog" title="提示"
			style="width: 400px; height: 200px;"
			data-options="modal:true,closed:true,closable:false,
			buttons:[{
				text:'确定',
				handler:function(){
					//确定退出
					location.href='login.jsp';
				}
			},{
				text:'取消',
				handler:function(){
					//取消退出
					$('#reLoginInfo').window('close');
				}
			}]">
			<p
				style="line-height: 70px; color: red; font-size: 24px; font-family: 华文行楷; text-align: center;">您确定要重新登录吗？</p>
		</div>
		<div id="logoutInfo" class="easyui-dialog" title="提示"
			style="width: 400px; height: 200px;"
			data-options="modal:true,closed:true,closable:false,
			buttons:[{
				text:'确定',
				handler:function(){
					//确定退出
					$.getJSON('login_exit.action', function(data) {
						if (data==1) {
							location.replace('login.jsp');
						} else {
							$('#sessionInfoDiv').html('');
						}
					});
				}
			},{
				text:'取消',
				handler:function(){
					//取消退出
					$('#logoutInfo').window('close');
				}
			}]">
			<p
				style="line-height: 70px; color: red; font-size: 24px; font-family: 华文行楷; text-align: center;">您确定要退出系统吗？</p>
		</div>
	</div> -->

	<!-- 西边 -->
	<div data-options="region:'west',split:true,title:'导航菜单'"
		style="width: 150px;">
		<div id="aa" class="easyui-accordion"
			data-options="fit:true,border:false">
			<%-- <div title="学员信息"
				data-options="iconCls:'icon-mini-add',selected:true"
				style="padding: 10px;">
				<ul id="tt" class="easyui-tree">
					<li><span>考勤管理</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('学员考勤','icon-mini-add',true,'','<iframe src=back/checking/checking.jsp  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">学员考勤</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('考勤记录','icon-mini-add',true,'','<iframe src=back/checking/showallcheckinginfo.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">考勤记录</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('个人考勤统计','icon-mini-add',true,'','<iframe src=back/checking/findcheckinginfo.html  frameborder=0 style=border:0;width:100%;height:100%;></iframe>')">个人考勤统计</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('班级考勤统计','icon-mini-add',true,'','<iframe src=back/checking/findClassCheckingInfo.jsp  frameborder=0 style=border:0;width:100%;height:100%;></iframe>')">班级考勤统计</a>
							</li>

						</ul></li>

					<li><span>每日一讲</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('每日一讲','icon-mini-add',true,'','<iframe src=back/checking/adailytalk.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">每日一讲</a>
							</li>

						</ul></li>

					<li><span>班级管理</span>
						<ul>
							<li><a href="javascript:void(0);" onclick="javascript:addTab('新增班级','icon-mini-add',true,'','<iframe src=back/student/newclass.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">新增班级</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('班级列表','icon-mini-add',true,'','<iframe src=back/student/listclass.jsp  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">班级列表</a></li>
						</ul></li>

					<li><span>学员管理</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('学员密码','icon-mini-add',true,'','<iframe src=back/student/updatepassword.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">学员密码</a>
							</li>
							<li><a href="javascript:void(0);" onclick="javascript:addTab('录入考生','icon-mini-add',true,'','<iframe src=back/student/newexaminee.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">录入考生</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('学员列表','icon-mini-add',true,'','<iframe src=back/student/listexaminee.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">学员列表</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('学员转班','icon-mini-add',true,'','<iframe src=back/student/updateexaminee.jsp  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">学员转班</a>
							</li>
						</ul></li>
					<li><span>导入excel班级文件</span>
						<ul>
							<li><a href="javascript:void(0);" onclick="javascript:addTab('导入excel班级文件','icon-mini-add',true,'','<iframe src=back/student/javaexl.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">导入excel班级文件</a>
							</li>
						</ul></li>
				</ul>
			</div>

			<div title="教学质量监控"
				data-options="iconCls:'icon-mini-add',selected:true"
				style="padding: 10px;">
				<ul id="tt" class="easyui-tree">
				<li><span>满意度管理</span><ul>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('开启月度满意度调查','icon-mini-add',true,'','<iframe src=back/satisfaction/openSatisfaction.jsp  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">开启月度满意度调查</a></li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('查看老师满意度','icon-mini-add',true,'','<iframe src=back/satisfaction/showTeacherSatisfaction.jsp  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">查看老师满意度</a>
					</li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('查看班级满意度','icon-mini-add',true,'','<iframe src=back/satisfaction/showClassSatisfaction.jsp  frameborder=0 style=border:0;width:100%;height:100%;></iframe>')">查看班级满意度</a>
					</li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('满意度汇总','icon-mini-add',true,'','<iframe src=back/satisfaction/summarySatisfaction.jsp  frameborder=0 style=border:0;width:100%;height:100%;></iframe>')">满意度汇总</a>
					</li>
					</ul></li>

					<li><span>作业管理</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('添加作业','icon-mini-add',true,'','<iframe src=back/teachSeduce/AddCheckingWork.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">添加作业</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('默写作业','icon-mini-add',true,'','<iframe src=back/teachSeduce/workdictation.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">默写作业</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('检查作业','icon-mini-add',true,'','<iframe src=back/teachSeduce/checkingwork.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">检查作业</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('查看作业','icon-mini-add',true,'','<iframe src=back/teachSeduce/findwork.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">查看作业</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('班级作业情况汇总','icon-mini-add',true,'','<iframe src=back/teachSeduce/classworkdetail.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">班级作业情况汇总</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('老师作业情况汇总','icon-mini-add',true,'','<iframe src=back/teachSeduce/teacherworkdetail.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">老师作业情况汇总</a></li>
						</ul></li>
					<li><span>访谈记录</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('访谈记录添加','icon-mini-add',true,'','<iframe src=back/teachSeduce/addInterviewRecord.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">访谈记录添加</a></li>
						
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('访谈记录查询','icon-mini-add',true,'','<iframe src=back/teachSeduce/interviewRecord.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">访谈记录查询</a></li>
								
							<li><a href="javascript:void(0);"
										onclick="javascript:addTab('班级访谈记录统计','icon-mini-add',true,'','<iframe src=back/teachSeduce/showClassInterview.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">班级访谈记录统计</a></li>
							<li><a href="javascript:void(0);"
										onclick="javascript:addTab('教师访谈记录统计','icon-mini-add',true,'','<iframe src=back/teachSeduce/showTeacherInterview.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">教师访谈记录统计</a></li>
						</ul></li>
						
						
				</ul>
			</div>

			<div title="排课维护"
				data-options="iconCls:'icon-mini-add',selected:false"
				style="padding: 10px;">
				<ul id="tt" class="easyui-tree">
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('排课管理','icon-mini-add',true,'','<iframe src=back/teachSeduce/index.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">排课管理</a>
					</li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('月度课时统计','icon-mini-add',true,'','<iframe src=back/teachSeduce/teachSeduceCount.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">月度课时统计</a>
					</li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('课表更正','icon-mini-add',true,'','<iframe src=back/teachSeduce/UpdateCurri.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">课表更正</a>
					</li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('班级注意事项信息维护','icon-mini-add',true,'','<iframe src=back/teachSeduce/classNotice.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">班级注意事项信息维护</a>
					</li>4444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444’
				</ul>

				<ul id="tt" class="easyui-tree">
					<li><span>查询管理</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('老师课表查询','icon-mini-add',true,'','<iframe src=back/teachSeduce/teacherCurriFind.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">老师课表查询</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('班级课表查询','icon-mini-add',true,'','<iframe src=back/teachSeduce/classCurriFind.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">班级课表查询</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('教室占用情况查询','icon-mini-add',true,'','<iframe src=back/teachSeduce/classroomCurriFind.jsp frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">教室占用情况查询</a></li>
						</ul></li>
				</ul>
			</div>
 --%>
			<div title="题库维护"
				data-options="iconCls:'icon-mini-add',selected:false"
				style="overflow: auto; padding: 10px;">
				<ul id="tt" class="easyui-tree">
					<li><span>题库维护</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('知识点管理','icon-mini-add',true,'','<iframe src=back/admin/addpoint.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">知识点管理</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('录入笔试题','icon-mini-add',true,'','<iframe src=back/backoperation/newwritingquestion.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">录入笔试题</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('浏览笔试题','icon-mini-add',true,'','<iframe src=back/backoperation/listwritquestion.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">浏览笔试题</a></li>
						</ul></li>
					<li><span>试卷管理</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('出笔试卷','icon-mini-add',true,'','<iframe src=back/backoperation/newwritingpaper.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">出笔试卷</a></li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('出笔试模板卷','icon-mini-add',true,'','<iframe src=back/backoperation/newwritingpapertemplate.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">出笔试模板卷</a></li>

							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('浏览笔试卷','icon-mini-add',true,'','<iframe src=back/grade/listwritingpaper2.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">浏览笔试卷</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('浏览笔试模板卷','icon-mini-add',true,'','<iframe src=back/grade/listwritingpaper2Template.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">浏览笔试模板卷</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('出自评卷','icon-mini-add',true,'','<iframe src=back/admin/newpointpaper.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">出自评卷</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('出自评模板卷','icon-mini-add',true,'','<iframe src=back/admin/newpointpapertemplate.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">出自评模板卷</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('浏览自评卷','icon-mini-add',true,'','<iframe src=back/admin/showpointpaper.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">浏览自评卷</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('浏览自评模板卷','icon-mini-add',true,'','<iframe src=back/admin/showpointpapertemplate.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">浏览自评模板卷</a>
							</li>
						</ul></li>
					<li><span>考试安排</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('考试安排','icon-mini-add',true,'','<iframe src=back/backoperation/examarrange.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">考试安排</a>
							</li>
						</ul></li>
				</ul>
			</div>
			<div title="测评结果管理"
				data-options="iconCls:'icon-mini-add',selected:false"
				style="padding: 10px;">
				<ul id="tt" class="easyui-tree">
					<li><span> 测试结果管理</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('笔试卷查询','icon-mini-add',true,'','<iframe src=back/grade/listwritingpaper.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">笔试卷查询</a>
							</li>

						</ul></li>
					<%-- <li><span>成绩查询</span>
						<ul>
						<li><a href="javascript:void(0);"

							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('班级成绩查询','icon-mini-add',true,'','<iframe src=back/grade/searchclassscore.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">班级成绩查询</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('考生成绩查询','icon-mini-add',true,'','<iframe src=back/grade/searchexaminee.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">考生成绩查询</a>
							</li>
						</ul></li> --%>
					<li><span>评价结果管理</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('自评成绩查询','icon-mini-add',true,'','<iframe src=back/grade/pointtotal.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')"> 自评成绩查询</a>
							</li>
							<%-- <li><a href="javascript:void(0);"
								onclick="javascript:addTab('测评成绩查询','icon-mini-add',true,'','<iframe src=back/grade/showstudentanswer.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">测评成绩查询</a>
							</li> --%>
						</ul></li>
					<%-- <li><span>知识点管理</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('知识点统计','icon-mini-add',true,'','<iframe src=back/grade/pointtotal.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">知识点统计</a>
							</li>
						</ul></li>
					<li><span>测评管理</span>
						<ul>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('课程测评统计','icon-mini-add',true,'','<iframe src=back/grade/showsubjecttotal.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">课程测评统计</a>
							</li>
							<li><a href="javascript:void(0);"
								onclick="javascript:addTab('查看测评留言','icon-mini-add',true,'','<iframe src=back/grade/showmessageinfo.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">查看测评留言</a>
							</li>
						</ul></li> --%>
				</ul>
			</div>
			<%-- <div title="用户管理"
				data-options="iconCls:'icon-mini-add',selected:false"
				style="padding: 10px;">
				<ul id="tt" class="easyui-tree">
					<li><a href="javascript:void(0);" onclick="javascript:addTab('添加新用户','icon-mini-add',true,'','<iframe src=back/userInfo/admin/newuser.html  frameborder=0 style=border:0;width:100%;height:100%;></iframe>')">添加新用户</a>
					</li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('修改用户信息','icon-mini-add',true,'','<iframe src=back/userInfo/admin/updateuser.html  frameborder=0 style=border:0;width:100%;height:100%;></iframe>')">修改用户信息</a>
					</li>
					<li><a href="javascript:void(0);" onclick="javascript:addTab('查询现有用户','icon-mini-add',true,'','<iframe src=back/userInfo/admin/listuser.html  frameborder=0 style=border:0;width:100%;height:100%;></iframe>')">查询现有用户</a>
					</li>

				</ul>
			</div>
			<div title="版本控制"
				data-options="iconCls:'icon-mini-add',selected:false"
				style="padding: 10px;">
				<ul id="tt" class="easyui-tree">
					<li><a href="javascript:void(0);" onclick="javascript:addTab('版本维护','icon-mini-add',true,'','<iframe src=back/edition/edition.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">版本维护</a></li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('课程维护','icon-mini-add',true,'','<iframe src=back/edition/subject.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">课程维护</a></li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('章节维护','icon-mini-add',true,'','<iframe src=back/edition/chapter.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">章节维护</a></li>
				</ul>
			</div>
			
			<div title="学习资源管理"
				data-options="iconCls:'icon-mini-add',selected:false"
				style="padding: 10px;">
				<ul id="tt" class="easyui-tree">
					<li><a href="javascript:void(0);" onclick="javascript:addTab('版本维护','icon-mini-add',true,'','<iframe src=back/edition/edition.html  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">版本维护</a></li>
					<li><a href="javascript:void(0);"
						onclick="javascript:addTab('资源上传','icon-mini-add',true,'','<iframe src=back/resource/addResource.jsp  frameborder=0 style=border:0;width:100%;height:99.5%;></iframe>')">资源上传</a></li>
				</ul>
			</div> --%>

		</div>
	</div>
 
  <div id="rcmenu" class="easyui-menu" style="">  
    <div id="closeall">关闭全部</div>  
    <div id="closeother">关闭其他</div> 
    <div id="closeright">当前页右侧全部关闭</div>
     <div id="closeleft">当前页左侧全部关闭</div> 
  </div>
 <!-- 东边 -->
	<div data-options="region:'east',split:true,title:'工具箱' ,selected:false"
		style="width: 170px; padding: 10px;">
		<div class="easyui-calendar" style="width: 150px; height: 150px;"></div>
	</div>
	<!-- 南边 -->
	<div data-options="region:'south',border:false"
		style="height: 30px; padding: 10px;">
		<center>©2018 邹真试卷生成系统</center>
	</div>
	<!-- 中间 -->
	<div id="cc"
		data-options="region:'center',title:'主操作区',
			 tools: [{   
        		iconCls:'icon-full',
       			handler:function(){full()}   
    		},{   
        		iconCls:'icon-unfull',
       			handler:function(){unFull()}   
    		}]">
		<div id="back_mainTab" class="easyui-tabs" data-options="fit:true"></div>

	</div>
	<div id="div1" style="margin: 0 auto;text-align: center;width: 70%;margin-left: 15%" >
 	</div>
    <div id="div2" style="margin: 0 auto;width: 50%;text-align: center;margin-top: 5%">
	</div>
	
</body>



</html>