var semester;
$(function() {
	semester = $("#dictation_semesterS1").attr('value');
	//var dateTime = $("#dateTime").find("option:selected").text().trim();

	$("#dictation_semesterS1").prop('checked', true);
	showClassName(semester);
	userName = localStorage.getItem("systemUser_userName");
})

function checkSemesters(semester) {
	showClassName(semester);
}

function showClassName(semester) {
	$.post("examineeclass_showClassList.action", {
		semester : semester
	}, function(obj) {
		$("#dictation_examineeClass").html("<option value='0'>--请选择--</option>");
		if (obj == null) {
			return;
		}
		var len=obj.length;
		for (var i = 0; i < len; i++) {
			$("#dictation_examineeClass").append(
					"<option value='" + obj[i].id + "'>" + obj[i].className
					+ "</option>");
		}
	});
}



function dictation_findclassinfo(){
	var classname=$("#dictation_examineeClass").val();
	if(classname==0){
		alert("请选择班级");
		return;
	}
	
	var semester  = $('input[name="semester"]:checked').val();
	
	$.post('work_findWorkdictation.action',{semester:semester,classname:classname},function(data){
		if(data.responseCode==1){
			alert('无数据')
			return ;
		}
		
		var data=data.obj;
		for (var i in data) {
			if(data[i].wname==undefined){
				continue;
			}
			var str="";
			str+="<tr  height='20'><th>编号</th><th>作业名称</th><th>作业描述</th><th>备注</th><th>操作</th></tr>"
			str+="<tr  height='25'><td align='center' id='work_"+data[i].wid+"'>"+data[i].wid+"</td>";
			str+="<td align='center'>"+data[i].wname+"</td>"
			str+="<td align='center'>"+changenull(data[i].description)+"</td>"
			str+="<td align='center'>"+changenull(data[i].remark)+"</td>"
			str+="<td align='center'><a href='javascript:adddication("+data[i].wid+")'>开始默写</a></td></tr>"
			/*$('#dictationwork_tree').tree('append', {
				data : [ {
					id : data[i].wid,
					text : data[i].wname
				} ]
			});
			node = $('#dictationwork_tree').tree('find',
					data[i].wid);
			for(var j in JSON.parse(data[i])){

			}*/
		}
		$("#showlist_dictationwork").html(str);
		/*$('#dictationwork_tree').tree('collapseAll');
*/	},'json');
	
/*	$('#dictationwork_tree').tree({
		onClick : function(node) {
			wid = node.id;
			if (!$('#enterprise_tree').tree("isLeaf", node.target)) {//如果不是叶子结点
				return;
			}
			$.post("work_findWorkbyid.action",{wid:wid},
					function(data){
				var str='';
				if(data.responseCode==1){
					str+="<tr><td></td><td></td><td align='center'>'<h2>错误,无数据</h2>'</td> </tr>"
					$("#showlist_dictationwork").html($("#showlist_dictationwork").html()+str);
					return ;
				}
				showlist_dictationwork
				var obj=data.obj;
				var str="";
				str+="<tr  height='20'></th>编号</th><th>作业名称</th><th>作业描述</th><th>备注</th><th>操作</th></tr>"
					str+="<tr  height='25'><td id='work_"+obj.wid+"'>"+obj.wid+"</td>";
					str+="<td>"+obj.wname+"</td>"
					str+="<td>"+obj.description+"</td>"
					str+="<td>"+obj.remark+"</td>"
					str+="<td><a href='javascript:adddication("+obj.wid+")'></a></td>"
				$("#showlist_dictationwork").html(str);
			},'json')
		}
	})*/
}

function changenull(data){
	if(data==null||data==''){
		return "无"
	}
	return data
}


function adddication(wid){
	$.post('work_adddicationwork.action',{wid:wid},function(data){
		if(data.responseCode==0){
			alert('添加成功')
			var a=$("#work_"+wid).parent()
			a.remove();
		}
	},'json')
	
}
