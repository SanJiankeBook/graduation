<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/search.css" />

<title>显示所有用户</title>
</head>
<body>
	<table id="type_showuser_info" data-options="fit:true"></table> 
	
	<script type="text/javascript">
	var datagridObj;
	var editRow = undefined;	//当前正在被编辑的行的索引
	var op;
	var flag;

	datagridObj=$('#type_showuser_info').datagrid({
		url:'findAllUserByPage',   
		fitColumns:true,
		loadMsg:'数据加载中...',
		striped:true,		//斑马线效果
		pagination : true, //显示分页栏
		nowrap:true,		//超出宽度自动截取
		rownumber:true,		//显示行数
		sortName:'uid',		//排序的咧
		remoteSort:false,	//前段排序而非服务器的排序，自己的排序
		pageSize:5,
        pageList:[5,10,15,20,25,30],
		columns:[[
			{field:'uid',title:'用户ID',width:100,align:'center',sortable:true},
			{field:'uname',title:'用户姓名',width:100,align:'center'},
 			{field:'u_number',title:'用户账号',width:100,align:'center',sortable:true},
 			{field:'upassword',title:'用户密码',width:100,align:'center',sortable:true},
 			{field:'usex',title:'用户性别',width:100,align:'center',sortable:true}
		]],
		toolbar:[{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				//获取当前用户选中的行
				var rows = datagridObj.datagrid("getChecked");
				if(rows.length<=0){	//表示没有选中数据
					$.messager.show({title:'温馨提示',msg:"请选择您要删除的用户...",timeout:2000,showType:'slide'});
				}else{
					$.messager.confirm('删除确认','您确定要删除这些数据吗?',function(result){
						if( result ){
							var uids = "";
							for ( var i=0 ;i<rows.length-1;i++ ){
								uids+=rows[i].uid+",";
							}
							//在for循环中少加了一次
							uids += rows[i].uid;
							
													//json
							$.post("delUser",{uid:uids},function(data){
								if( data == "0" ){
									$.messager.alert('失败提示','用户删除失败!','error');
								}else{
									$.messager.show({title:'成功提示',msg:'用户删除成功...',timeout:2000,showType:'slide'});
									//重新加载数据
									datagridObj.datagrid("reload");
								}
							});
						}
					});
				}
			}
		},'-',{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				datagridObj.datagrid("endEdit",editRow);//结束编辑
				//要获取被编辑的数据
				var rows = datagridObj.datagrid("getChanges")[0];
				
				//发请求到数据库更新
				if( rows ==  undefined ){ //说明用户没有进行任何的操作
					datagridObj.datagrid("rejectChanges");
					datagridObj.datagrid("unselectAll");	//取消所有选中的行
					editRow = undefined;
				}else {
					rows["aj"]=aj;
					$.post("../goodsTypeServlet",rows,function (data){
						data = $.trim(data);
						if( data == "1" ){
							$.messager.show({title:flag+'提示',msg:flag+'用户成功...',timeout:2000,showType:'slide'});
							
							datagridObj.datagrid("rejectChanges");
							datagridObj.datagrid("unselectAll");   //取消所有的选中的行
							editRow = undefined;
							rows == undefined;
							//充新加载数据
							datagridObj.datagrid("reload");
						}else{
							$.messager.alert('失败提示',flag+'添加商品类型失败!','error');
						}
					});
				}
			}
		},'-',{
			text:'撤销',
			iconCls:'icon-undo',
			handler:function(){
				datagridObj.datagrid("rejectChanges");
				datagridObj.datagrid("endEdit",editRow);   //取消所有的选中的行
				datagridObj.datagrid("unselectAll");
				editRow = undefined;
			}
		}]
	});
		
	</script>
</body>
</html>