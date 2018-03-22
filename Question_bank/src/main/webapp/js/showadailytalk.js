function findKonwledgeInfoByAll() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var stuname = $("#stuname").val();
	$('#back_yhgl_datagrid').datagrid({
		url : '/Examination2.0/checking_find.action?t=' + new Date()+'&startDate='+startDate+'&endDate='+endDate+'&stuname='+stuname,
		fit : true,
		border : true,
		pagination : true,
		singleSelect : true,
		sortName : 'name', //排序方式
		sortOrder : 'asc', //排序规则
		idField : 'id', //标识列
		pageList : [ 5, 10, 15, 20, 30, 50, 100 ], //每页多少条
		pageSize : 5, //默认每页多少条
		rownumbers : true, //排序ID
		columns : [ [ {
			field : 'id',
			title : '编号',
			width : 150,
			align : 'center',
			checkbox : true
		}, {
			field : 'name',
			title : '讲解学生',
			width : 150,
			align : 'center',
			sortable : true, //可排序
		}, {
			field : 'knowledge',
			title : '讲解内容',
			width : 150,
			align : 'center',
		}, {
			field : 'assessment',
			title : '教员评价',
			width : 150,
			align : 'center',
		}, {
			field : 'speakdate',
			title : '开讲日期',
			width : 150,
			align : 'center',
		}, {
			field : 'status',
			title : '操作',
			width : 150,
			align : 'center',
			hidden : true,
		}, {
			field : 'sta',
			title : '操作',
			width : 150,
			align : 'center',
		}, {
			field : 'uname',
			title : '操作',
			width : 150,
			align : 'center',
			hidden : true,
		} ] ],

		toolbar : [ {
			text : '上传',
			iconCls : 'icon-arrow-up',
			handler : function() {
				upload();
			}
		}, '-', {
			text : '下载',
			iconCls : 'icon-arrow-down',
			handler : function() {
				down();
			}
		}]
	});
	
	$.post("/Examination2.0/checking_talkByFpc.action", function(data) {
		var data=eval("("+data+")");
		$.each(data,function(i,item){
			var uname=item[0].uname;
			var a='';
			a+='<option value="0" selected="selected" id="opentionName">--请选择--</option>';
			for(var i=0;i<item[0].stuNameList.length;i++){
				a+='<option value="'+item[0].stuNameList[i]+'" id="opentionName">'+item[0].stuNameList[i]+'</option>';
			}
			$("#stuname").html(a);
		});
	});
	
	clearForm();
	
}



function readyok(id) {
	var infoPath = $("#knowledgeinfo").val();
	//alert(infoPath);
	/*$("#bb").attr("action",
			"/Examination2.0/upload_uploadfile_hwh.action?filename="+infoPath+"&id=" + id);*/
	
	$("#bb").attr("action",
			"/Examination2.0/upload_uploadfile.action?id="+id);
	if (infoPath == "") {
		$("#errorInfo").html("请选择您要上传的文件!");
		$("#errorInfo").attr("class", "error");
		return false;
	}
	var fileInfo = $("#knowledgeinfo").val();
	var filetype = fileInfo.substring(fileInfo.lastIndexOf(".") + 1);

	if (filetype != "rar" && filetype != "zip" && filetype != "RAR"
			&& filetype != "ZIP") {
		$("#errorInfo").html("您上传的文件格式有误，请上传后缀名为.rar或.zip的文件！");
		$("#errorInfo").attr("class", "error");
		return false;
	}
	var fileName = fileInfo.substring(fileInfo.lastIndexOf("\\") + 1);
	var reg = /^[a-zA-Z0-1][\w.]{1,30}$/;
	if (!reg.test(fileName)) {
		$("#errorInfo").html("您上传的文件的文件名有误，请将上传的文件的文件名改为英文！");
		$("#errorInfo").attr("class", "error");
		return false;
	}
	document.myform.submit();
	/*$.ajax({
		url:"/Examination2.0/upload_uploadfile.action",
		data:{"filename":infoPath,"id":id},
		type:"POST",
		dataType:"JSON",
		success:function(data){
			alert("go  go  go")
		}
	});*/
	//window.close();
	//location.href="/Examination2.0/student/student.html";
}


//清空
function clearForm() {
	$('#back_yhgl_searchFrom input[name=startDate]').val('');
	$('#back_yhgl_searchFrom input[name=endDate]').val('');
	$('#back_yhgl_datagrid').datagrid('load', {});
}
