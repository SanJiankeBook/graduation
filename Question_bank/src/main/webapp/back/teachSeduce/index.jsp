<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<title>排课系统首页</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css" href="css/datedropper.css">
<script type="text/javascript">
	function initClass() {//加载所有的班级
		$.post("findClass.action", function(data) {
			if (data.responseCode == 1) {
				alert("班级查询错误,请与管理员联系");
				return;
			} else {
				for (var i = 0; i < data.obj.length; i++) {
					$("#allClass").append(
							" <input type='checkbox' name='sel' value='"+data.obj[i].id+"'><span>"
									+ data.obj[i].className + "</span>");
				}
			}
		});

	}
</script>

</head>
<body onload="initClass()">
	<nav>
		<div class="nav_content">
			<div class="classSel">
				<a href="javascript:void(0)" class="select">班级选择</a>
				<form class="classche" id="classche">
					<b class="trangle"></b>
					<div id="allClass">
						<input type="checkbox" name="sel" class="allcheck" value="-1"><span>全选</span>
					</div>
					<a href="javascript:void(0)">确定</a>
				</form>
			</div>
			<div class="weekSel">
				<select class="weekche" id="weekche">
					<option value="">周选择</option>
					<option value="1">第一周</option>
					<option value="2">第二周</option>
					<option value="3">第三周</option>
					<option value="4">第四周</option>
					<option value="5">第五周</option>
					<option value="6">第六周</option>
					<option value="7">第七周</option>
					<option value="8">第八周</option>
					<option value="9">第九周</option>
					<option value="2">第十周</option>
					<option value="3">第十一周</option>
					<option value="4">第十二周</option>
					<option value="5">第十三周</option>
					<option value="6">第十四周</option>
					<option value="7">第十五周</option>
					<option value="8">第十六周</option>
					<option value="9">第十七周</option>
					<option value="7">第十八周</option>
					<option value="8">第十九周</option>
					<option value="9">第二十周</option>
				</select>
			</div>
			<!---->
			<div class="timeSel">
				<input type="text" class="input" id="pickdate" placeholder="请输入日期"
					style="padding-left: 5px; color: #fff;" />
			</div>
			<button id="makeTable">生成表格</button>
			<button id="finished" onclick="finishedAll()">完成</button>
		</div>
	</nav>
	<article id="table"></article>
	<div class="insertchoose">
		<ul id="accordion" class="accordion">
			<li><span class="link">老师</span>
				<ul class="submenu" id="teacherList">
					<!-- 教师信息 -->
				</ul></li>
			<li><span class="link">课程</span>
				<ul class="submenu">
					<li><label> <input type="radio" name="classes" onchange='changed()'>
							<a href="javascript:void(0)" id="-1">放假</a>
					</label></li>
					<li><label> <input type="radio" name="classes" onchange='changed()'>
							<a href="javascript:void(0)" id="-2">自习</a>
					</label></li>
					<li><label> <input type="radio" name="classes" onchange='changed()'>
							<a href="javascript:void(0)" id="-3">复习</a>
					</label></li>
					<li><label> <input type="radio" name="classes" onchange='changed()'>
							<a href="javascript:void(0)" id="-4">补课</a>
					</label></li>
					<li><label> <input type="radio" name="classes" onchange='changed()'>
							<a href="javascript:void(0)" id="-5">就业指导</a>
					</label></li>
					<li><label> <input type="radio" name="classes" onchange='changed()'>
							<a href="javascript:void(0)" id="-6">测试</a>
					</label></li>
					<li>
						<ul class="classes" id="classList"></ul>
						<ul class="classes" id="chapterList"></ul>
					</li>
				</ul></li>
			<li><span class="link">教室</span>
				<ul class="submenu" id="classRoomList">
					<!--   教室信息 -->

				</ul></li>
			<li><span class="insertfinish">完成</span><span class="insertcancel">取消</span></li>
		</ul>
	</div>
	<div class="statistics">
		<ul class="accordion">
			<li><span class="link" id="playBlack1">获取老师课时信息</span></li>
			<li><span class="link" id="playBlack2">获取教室使用情况</span></li>
		</ul>
	</div>
	<div class="black" id="black1">
		<div class="black_content" id="black_content">
			<div class="chooseSel" id="teaSel">
				<span class="teaChoose">选择老师:</span> <select class="teasel"
					id="teasel">
				</select><br> <span class="teaTable" id="teaTable1">生成表格</span>
			</div>
			<div class="table_content" id="a" style="display: none;"></div>
		</div>
		<i class="deleteIcon"></i>
	</div>
	<div class="black" id="black2">
		<div class="black_content">
			<div class="chooseSel" id="roomSel">
				<span class="teaChoose">选择教室:</span> <select class="teasel"
					id="roomsel">
				</select><br> <span class="teaTable" id="teaTable2">生成表格</span>
			</div>
			<div class="table_content" id="b" style="display: none;"></div>
		</div>

		<i class="deleteIcon"></i>
	</div>

	<script src="js/jquery-1.9.1.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/test3.js" type="text/javascript"></script>
	<script src="js/datedropper.min.js"></script>
	<script>
		$("#pickdate").dateDropper({
			animate : false,
			format : 'Y-m-d',
			maxYear : '2030'
		});
	</script>
	<script>
		$(function() {
			var Accordion = function(el, multiple) {
				this.el = el || {};
				this.multiple = multiple || false;

				// Variables privadas
				var links = this.el.find('.link');
				// Evento
				links.on('click', {
					el : this.el,
					multiple : this.multiple
				}, this.dropdown)
			}

			Accordion.prototype.dropdown = function(e) {
				var $el = e.data.el;
				$this = $(this), $next = $this.next();

				$next.slideToggle();
				$this.parent().toggleClass('open');

				if (!e.data.multiple) {
					$el.find('.submenu').not($next).slideUp().parent()
							.removeClass('open');
				}
				;
			}

			var accordion = new Accordion($('#accordion'), false);
		});
	</script>
</body>
</html>