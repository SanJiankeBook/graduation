 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>待审查小说</title>
</head>
<body>

	<table id="show_novel_info" data-options="fit:true"></table>
	<script type="text/javascript">
	var datagridObj;
	var editRow = undefined;	//当前正在被编辑的行的索引
	var op;
	var flag;

	datagridObj=$('#show_novel_info').datagrid({
		url:'uncheckNovel',   
		fitColumns:true,
		loadMsg:'数据加载中...',
		striped:true,		//斑马线效果
		pagination : true, //显示分页栏
		nowrap:true,		//超出宽度自动截取
		rownumber:true,		//显示行数
		sortName:'nid',		//排序的
		remoteSort:false,	//前段排序而非服务器的排序，自己的排序
		pageSize:5,
		 pageList:[5,10,15,20,25,30],
		columns:[[
			{field : 'nids',title : '全选',width : 20,align : 'center',sortable : true,checkbox:true},
			{field : 'nid',title : '小说编号',width : 20,align : 'center',sortable : true},
			{field : 'npicture',title : '小说封面',width : 50,align : 'center',formatter : function(value, row, index) {
					var picStr = "";
					if (value.indexOf(",")) {
						value = value.split(",");
						for (var i = 0; i < value.length; i++) {
							if (value[i] == null || value[i] == "") {
								picStr += "<img src='images/zanwu.jpg' width='100px' height='100px' />";
							} else {
								var patt1 = new RegExp("../");
								if (patt1.test(value[i])) {
									picStr += "<img src='"+value[i]+"' width='100px' height='100px' />";
								} else {
									picStr += "<img src='images/"+value[i]+"' width='100px' height='100px' />";
								}
							}
						}
					} else if (value != "") {
						picStr += "<img src='"+value+"' width='100px' height='100px' />";
					} else {
						picStr += "<img src='images/zanwu.jpg' width='100px' height='100px' />";
					} 
						return picStr;
					}
				},
			{field : 'tname',title : '小说类型',width : 30,align : 'center'},
			{field : 'aname',title : '小说作者',width : 30,align : 'center'},
			{field : 'pan_name',title : '作者笔名',width : 30,align : 'center'},
			{field : 'nname',title : '小说名',width : 50,align : 'center'}, 
			{field : 'ndescription',title : '小说简介',width : 100,align : 'center'},
			{field : 'nstatus',title : '小说状态',width : 50,align : 'center'}, 
			{field : 'standby_1',title : '审核状态',width : 50,align : 'center'}, 
		]],
		toolbar:[{
			text:'通过',
			iconCls:'icon-add',
			handler:function(){
				//获取当前用户选中的行
				var rows = datagridObj.datagrid("getChecked");
				if(rows.length<=0){	//表示没有选中数据
					$.messager.show({title:'温馨提示',msg:"请选择您要通过的小说...",timeout:2000,showType:'slide'});
				}else{
					$.messager.confirm('通过确认','您确定要通过这些小说吗?',function(result){
						if( result ){
							var nids = "";
							for ( var i=0 ;i<rows.length-1;i++ ){
								nids+=rows[i].nid+",";
							}
							//在for循环中少加了一次
							nids += rows[i].nid;
							
							$.post("passNovel",{nid:nids},function(data){
								if( data == "0" ){
									$.messager.alert('失败提示','小说通过失败!','error');
								}else{
									$.messager.show({title:'成功提示',msg:'小说通过成功...',timeout:2000,showType:'slide'});
									//重新加载数据
									datagridObj.datagrid("reload");
								}
							});
						}
					});
				}
			}
		},'-',{
			text:'不通过',
			iconCls:'icon-remove',
			handler:function(){
				//获取当前用户选中的行
				var rows = datagridObj.datagrid("getChecked");
				if(rows.length<=0){	//表示没有选中数据
					$.messager.show({title:'温馨提示',msg:"请选择您不通过的小说...",timeout:2000,showType:'slide'});
				}else{
					$.messager.confirm('删除确认','您确定该小说不通过?',function(result){
						if( result ){
							var nids = "";
							for ( var i=0 ;i<rows.length-1;i++ ){
								nids+=rows[i].nid+",";
							}
							//在for循环中少加了一次
							nids += rows[i].nid;
							
							$.post("unpassNovel",{nid:nids},function(data){
								if( data == "0" ){
									$.messager.alert('失败提示','小说不通过失败!','error');
								}else{
									$.messager.show({title:'成功提示',msg:'小说不通过成功...',timeout:2000,showType:'slide'});
									//重新加载数据
									datagridObj.datagrid("reload");
								}
							});
						}
					});
				}
			}
		}]
		
	});
		
	</script>
	
</body>
</html>