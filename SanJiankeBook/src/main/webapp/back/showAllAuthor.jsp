<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>显示所有作者</title>
</head>
<body>
	<table id="type_showAuthor_info" data-options="fit:true"></table> 
	
	<script type="text/javascript"> 
	var datagridObj;
	var editRow = undefined;	//当前正在被编辑的行的索引
	var op;
	var flag;

	datagridObj=$('#type_showAuthor_info').datagrid({
		url:'findAuthorByPage',   
		fitColumns:true,
		loadMsg:'数据加载中...',
		pagination : true, //显示分页栏
		striped:true,		//斑马线效果
		nowrap:true,		//超出宽度自动截取
		rownumber:true,		//显示行数
		sortName:'aid',		//排序的咧
		remoteSort:false,	//前段排序而非服务器的排序，自己的排序
		pageSize:5,
		pageList:[5,10,15,20,25,30],
		columns:[[
			{field:'aid',title:'作家编号',width:100,align:'center',sortable:true},
			{field:'uid',title:'用户身份编号',width:100,align:'center',sortable:true},
			{field:'aname',title:'作家姓名',width:100,align:'center',editor:{type:"text",options:{request:true}}},
 			{field:'pan_name',title:'作家笔名',width:100,align:'center',sortable:true},
			{field:'aage',title:'年龄',width:100,align:'center'},
			{field:'agrade',title:'作者等级',width:100,align:'center'},
			{field:'acard',title:'身份证号',width:100,align:'center'},
			{field:'atel',title:'电话',width:100,align:'center'}

		]],
		toolbar:[{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				//获取当前用户选中的行
				var rows = datagridObj.datagrid("getChecked");
				if(rows.length<=0){	//表示没有选中数据
					$.messager.show({title:'温馨提示',msg:"请选择您要删除的作家...",timeout:2000,showType:'slide'});
				}else{
					$.messager.confirm('删除确认','您确定要删除这些数据吗?',function(result){
						if( result ){
							var aids = "";
							for ( var i=0 ;i<rows.length-1;i++ ){
								aids+=rows[i].aid+",";
							}
							//在for循环中少加了一次
							aids += rows[i].aid;
							
							$.post("delAuthor",{aid:aids},function(data){
								if( data == "0" ){
									$.messager.alert('失败提示','作家删除失败!','error');
								}else{
									$.messager.show({title:'成功提示',msg:'作家删除成功...',timeout:2000,showType:'slide'});
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
							$.messager.show({title:flag+'提示',msg:flag+'作家成功...',timeout:2000,showType:'slide'});
							
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

		}
		]
	});
	
	
</script>
</body>
</html>