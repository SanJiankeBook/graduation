<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" /> 
<script type="text/javascript" language="javascript" src="js/jquery-1.9.1.js"></script>

<script type="text/javascript" src="back/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/dictation.js"></script>
<title>满意度调查表</title>

<style type="text/css">
#show_table_dictation {
	width: 80%;
	margin: 0 auto;
	margin-top: 30px;
}
</style>
</head>
	<table id="show_table_dictation">
		<tr bgcolor="#715DE5" height="25">
			<th align="center">编号</th>
			<th align="center">作业名称</th>
			<th align="center">布置者</th>
			<th align="center">备注</th>
			<th align="center">操作</th>
		</tr>
	</table>



	<div id="show_ckeditor" style="display: none">
		<textarea rows="30" cols="50" id="editor01" name="editor01""></textarea>
	</div>
	<div id="show_button" style="display: none">
		<input type="button" value="提交" onclick="click_dictation_up()">
	</div>
	<script type="text/javascript">
		var wid = 0;
		$(function() {
			editor = CKEDITOR.replace('editor01');

			$.post('work_finddictationwork.action',{},
							function(data) {
								if (data.responseCode == 1) {
									var str = "<tr><td colspan='5' align='center'><h2>暂无默写作业</h2></td></tr>"
									$("#show_table_dictation").append(str)
									return;
								}
								var str = "";
								data = data.obj;
								for ( var i in data) {
									if (data[i].wname == undefined) {
										continue;
									}
									str += '<tr><td align="center" id="dectation_'+data[i].wid+'">'
											+ data[i].wid + '</td>'
									str += '<td align="center">'
											+ data[i].wname + '</td>';
									str += '<td align="center">'
											+ data[i].checkcreator + '</td>';
									str += '<td align="center">'
											+ changenull(data[i].remark)
											+ '</td>';
									str += '<td align="center"><a href="javascript:todictation('
											+ data[i].wid + ')">开始默写</a></td>';
								}
								$("#show_table_dictation").append(str)
							})

		})

		function changenull(data) {
			if (data == null || data == "") {
				return "无"
			}
			return data
		}

		function todictation(data) {
			wid = data;
			editor.setData("")
			$('#show_ckeditor').css("display", "block");
			$('#show_button').css("display", "block");
		}

		function click_dictation_up() {
			if (confirm("确定提交？")) {
				var data = editor.getData();
				$.post('work_adddictation.action', {
					data : data,
					wid : wid
				}, function(data) {
					if (data.responseCode == 0) {
						alert('添加成功')
						var a = $("#dectation_" + wid).parent()
						a.remove();
						$('#show_ckeditor').css("display", "none");
						$('#show_button').css("display", "none");
					} else {
						alert('添加失败')
					}
				})
			}
		}
	</script>
