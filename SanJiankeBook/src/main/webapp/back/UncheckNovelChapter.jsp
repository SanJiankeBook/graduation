<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- 获取敏感字，传到前台时显示成* -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>检查小说上传章节</title>
	<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>
</head>

<body>
	<table id="show_novelChapter_info" data-options="fit:true"></table>
	
	<div id="novelChapter_manager_search" style="height: 28px">
		<div class="datagrid-btn-separator"></div>
		
		<a href="javascript:passChapter()" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true" style="float:left">通过</a>
		<div class="datagrid-btn-separator"></div>
		
		<a href="javascript:unpassChapter()" class="easyui-linkbutton"
		data-options="iconCls:'icon-remove',plain:true" style="float:left">不通过</a>
	</div>
	
	
	
	<div id="shownvoel_check_info">
		<p>
			<div id="info"><label>章节编号：</label><input type="text" id="info1" name="cid" style="border:0;background:transparent;" readOnly="true"/> </div>
		</p>
		<p>
			<div id="info"><label>小说编号：</label><input type="text" id="info2" name="nid" style="border:0;background:transparent;" readOnly="true"/></div>
		</p>
		<p>
			<div id="info"><label>小说类型：</label><input type="text" id="info4" name="tname" style="border:0;background:transparent;" readOnly="true"/></div>
		</p>
		<p>
			<div id="info"><label>小说名：</label><input type="text" id="info5" name="nname" style="border:0;background:transparent;" readOnly="true"/></div>
		</p>
		<p>
			<div id="info"><label>小说章节名：</label><input type="text" id="info6" name="cname" style="border:0;background:transparent;" readOnly="true"/> </div>
		</p>
		<p>
			<div id="info"><label>审核状态：</label><input type="text" id="info7" name="standby_1" style="border:0;background:transparent;" readOnly="true"/> </div>
		</p>
		<!-- 小说章节地址 -->
		小说章节地址：
		<p>
			<div id="info"><a href="${novelChapter.caddress } "><input type="text" id="info8" name="caddress" style="border:0;background:transparent;" readOnly="true"/></a></div>
		</p>
		章节内容：
		<div>
			<script type="text/javascript" id="editor" style="width:90%;height:200px;"></script>
		</div>
	</div>
	
	<script type="text/javascript">
	var datagridObj;
	var editRow = undefined;	//当前正在被编辑的行的索引
	var op;
	var flag;

	datagridObj=$('#show_novelChapter_info').datagrid({
		url:'uncheckNovelChapter',   
		fitColumns:true,
		loadMsg:'数据加载中...',
		striped:true,		//斑马线效果
		pagination : true, //显示分页栏
		nowrap:true,		//超出宽度自动截取
		rownumber:true,		//显示行数
		sortName:'cid',		//排序的
		remoteSort:false,	//前段排序而非服务器的排序，自己的排序
		pageSize:5,
		 pageList:[5,10,15,20,25,30],
		columns:[[
			{field : 'cids',title : '全选',width : 20,align : 'center',sortable : true,checkbox:true},
			{field : 'cid',title : '章节编号',width : 20,align : 'center',sortable : true},
			{field : 'nid',title : '小说编号',width : 20,align : 'center',sortable : true},
			{field : 'tname',title : '小说类型',width : 30,align : 'center'},
			{field : 'nname',title : '小说名',width : 50,align : 'center'}, 
			{field : 'cname',title : '小说章节名',width : 50,align : 'center'}, 
			{field : 'caddress',title : '章节地址',width : 100,align : 'center',formatter: function(val,row,index){
				 	if(val){
						 return "<a href='tocaddress_id/"+row.cid+"'>"+val+"</a>";  
					} 
				 	
				}
			}, 
			{field : 'standby_1',title : '审核状态',width : 50,align : 'center'}, 
		]],
		toolbar : "#novelChapter_manager_search"
	});
	
	var ue=UE.getEditor('editor');
	
	function checkNovelInfo(){
		var rows=$('#show_novelChapter_info').datagrid("getChecked");
		if(rows.length<=0){
			$.messager.alert("温馨提示", "请选中一行数据进行查看", "error");
		}else{
			var cid="";
			
			for(var i=0;i<rows.length-1;i++){
				 cid=rows[i].cid;
			}
			cid=rows[i].cid;
			$.ajax({
				url:'showDetails',
				type:"post",
				data:{"id":cid},
				dataType:"json",
				success:function(data){
					data=$.trim(data);
					if(data=="0"){
						alert("获取失败");
					}else{
						rows=rows[0];
						$("#info1").val(rows.cid);
						$("#info2").val(rows.nid);
						$("#info4").val(rows.tname);
						$("#info5").val(rows.nname);
						$("#info6").val(rows.cname);
						$("#info7").val(rows.standby_1);
						$("#info8").val(rows.caddress);
						//$("#editor").val(rows.caddress);
						//ue.setContent("<p><br>ddd</p>");
						//$(function(){
					     //   var path =$('#info8').val();
					        
					     //   ue.addListener("ready", function () {
					      //  	ue.setContent(path);
					       // });
					    //});
					}
				}
			})
		
			$("#shownvoel_check_info").dialog({
				title : '查看小说章节详细信息',
				closed : false,
			iconCls : 'icon-remove'
			});
		}
	}
	
	//通过待审核的章节
	function passChapter(){
		var rows=$('#show_novelChapter_info').datagrid("getChecked");
		if(rows.length<=0){
			$.messager.alert("温馨提示", "请选中一行数据进行查看", "error");
		}else{
			var cids="";
			
			for(var i=0;i<rows.length-1;i++){
				 cids+=rows[i].cid+",";
			}
			cids+=rows[i].cid;
			$.ajax({
				url:'passChapter',
				type:"post",
				data:{"id":cids},
				dataType:"json",
				success:function(data){
					data=$.trim(data);
					if(data=="0"){
						$.messager.alert("温馨提示", "没有数据要通过，请查看", "error");
					}else{
						$.messager.show({title:'成功提示',msg:'通过成功...',timeout:2000,showType:'slide'});
						//重新加载数据
						$("#show_novelChapter_info").datagrid("reload");
					}
				}
			})
		}
	}
	
	//不通过待审核的章节
	function unpassChapter(){
		var rows=$('#show_novelChapter_info').datagrid("getChecked");
		if(rows.length<=0){
			$.messager.alert("温馨提示", "请选中一行数据进行查看", "error");
		}else{
			var cids="";
			
			for(var i=0;i<rows.length-1;i++){
				 cids+=rows[i].cid+",";
			}
			cids+=rows[i].cid;
			$.ajax({
				url:'unpassChapter',
				type:"post",
				data:{"id":cids},
				dataType:"json",
				success:function(data){
					data=$.trim(data);
					if(data=="0"){
						$.messager.alert("温馨提示", "没有数据要不通过，请查看", "error");
					}else{
						$.messager.show({title:'成功提示',msg:'不通过成功...',timeout:2000,showType:'slide'});
						//重新加载数据
						$("#show_novelChapter_info").datagrid("reload");
					}
				}
			})
		}
	}
	
	</script>
</body>
</html>