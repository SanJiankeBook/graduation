<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" language="javascript"
	src="../../js/jquery-1.9.1.js"></script>
<link rel="stylesheet" type="text/css"
	href="../../jslib/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../../jslib/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/demo.css">
<script type="text/javascript" src="../../jslib/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../jslib/locale/easyui-lang-zh_CN.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />


  <style>
 #allClassNoticeDiv  table{
    float: left;
    border: 1px solid #000000;
    text-align: center;
   
}

</style>
<title>注意事项信息维护</title>
</head>
<body>
	<div>
		<form action="classNotice.action" method="post">

			<div style="width: 200px; height: auto; border: 1px solid #ccc; float: left;">
				<ul class="easyui-tree" id="classNotice_selectSubconditionSub"
					state="closed">
				
				</ul>
			</div>
			<div style="float: left; margin-left:20px;">
				<label id="selectedClass"></label> <label>  注意事项:</label><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<textarea rows="10" cols="50" id="noticeText"></textarea>
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="inp_L1" value="修改" onclick="addNotice()" />
			</div>
			
			<div style="float: left; margin-left:20px;" id="allClassNoticeDiv">
				
				<table id="allClassNotice">
					<tr>
						<td style="font-size: 13px;width:90px;  border: 1px solid #000000;">班级</td>
						<td style=" font-size: 13px;width:220px;  border: 1px solid #000000;">注意事项</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">
	var id;
	$(function() {
		$('#classNotice_selectSubconditionSub').tree('append', {
			data : [ {
				id : 'classNotice_selectSubcondition',
				text : '班级'
			} ]
		});

		var nodeTemp = $('#classNotice_selectSubconditionSub').tree('getRoot',
				"classNotice_selectSubcondition");
		$.ajax({
			url : "findClass.action",
			type : "POST",
			dataType : "JSON",
			success : function(data) {
				for (var i = 0; i < data.obj.length; i++) {
					$('#classNotice_selectSubconditionSub').tree('append', {
						parent : nodeTemp.target,
						data : [ {
							id : data.obj[i].id,
							text : data.obj[i].className,

						} ]
					});
				}
				//折叠所有节点
				$('#classNotice_selectSubconditionSub').tree('collapseAll');
			}
		});

		$('#classNotice_selectSubconditionSub').tree({
			onClick : function(node) {
				/* if (!$('#classNotice_selectSubcondition').tree("isLeaf", node.target)) {//如果不是叶子结点
					return;
				} */
				id = node.id;
				$.post("examineeclass_showClassNotice.action", {
					id : node.id
				}, function(data) {
					$("#noticeText").text('');
					$("#noticeText").val('');
					if (data.responseCode == 1) {
						alert("错误,请与管理员联系");
						return;
					} else {
						//data = data.obj.substring(1, data.obj.length - 1);
						$("#noticeText").val(data.obj);
					}
				});

				$("#selectedClass").text(node.text);
			}
		});
		
		showAllClassNotice();
		

	});

	//修改注意事项 
	function addNotice() {
		var content = $("#noticeText").val();
		$.post("examineeclass_addClassNotice.action", {
			remark : content,
			id : id
		}, function(data) {
			if (data.responseCode == 1) {
				alert("班级注意事项添加失败，请与管理员联系!");
				return;
			} else {
				alert("班级注意事项添加成功!");
				showAllClassNotice();
				return;
			}
		});
	}
	
	function showAllClassNotice(){
		$.ajax({
			url : "examineeclass_findAllClassNotice.action",
			type : "POST",
			dataType : "JSON",
			success : function(data) {
				$("#allClassNotice").text('');
				 for (var i = 0; i < data.obj.length; i++) {
					$("#allClassNotice").append("<tr><td style='font-size: 13px;width:90px;  border: 1px solid #000000;'>"+data.obj[i][1]+"</td><td style=' font-size: 13px;width:220px;  border: 1px solid #000000;'>"+data.obj[i][2]+"</td></tr>");
				} 
			}
		});
	}
</script>

