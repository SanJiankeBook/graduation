<!-- 书架 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>
	我的书架
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/style.css" />
<!--  <script type="text/javascript" src="../js/xiaoshuo.js"></script> -->
<script type="text/javascript" src="js/xiaoshuo.js"></script>
<link rel="stylesheet" type="text/css"
	href="easyui/css/easyui.css">
<link rel="stylesheet" type="text/css"
	href="easyui/css/icon.css">
<link rel="stylesheet" type="text/css"
	href="easyui/css/demo.css">
<script type="text/javascript"
	src="js/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="easyui/js/index.js"></script>
<script type="text/javascript"
	src="easyui/js/easyui-lang-zh_CN.js"></script>
</head>
<body style="text-align:center; margin:auto auto">
    <h1 style="font-size: 20px">用户书架</h1>
    <div id="wrapper">
<div class="header">
    <div class="header_logo"><a href="toindex_zpd">三剑客文学</a></div>
    <div class="header_search"><script>search();</script></div>
</div>
<div class="clear"></div>
<div class="nav">
			<ul>
				<li><a href="toindex_zpd">首页</a></li>
					<li><a rel="nofollow" href="mybook">我的书架</a></li>
					<li><a href="toindex_Type/${list[0].tname}">${list[0].tname}</a></li>
					<li><a href="toindex_Type/${list[1].tname}">${list[1].tname}</a></li>
					<li><a href="toindex_Type/${list[2].tname}">${list[2].tname}</a></li>
					<li><a href="toindex_Type/${list[3].tname}">${list[3].tname}</a></li>
					<li><a href="toindex_Type/${list[4].tname}">${list[4].tname}</a></li>
					<li><a href="toindex_Type/${list[5].tname}">${list[5].tname}</a></li>
					<li><a href="authorPrefectrue1">作者专区</a></li>
					<li><a href="toindex_type">排行榜单</a></li>
					<li><a href="quanben">全本小说</a></li>
					<li><a  href="toReadRecord">阅读记录</a></li>
			</ul>
		</div>
       <br />

<table id="type_shownovel_info" data-options="fit:false" style="height: 500px"></table> 
	<script type="text/javascript">
	
	$(function(){
		searchNovel();
	});
function searchNovel() {
	
	var datagridObj;
	var editRow = undefined;	//当前正在被编辑的行的索引
	var op;
	var flag;
	datagridObj=$('#type_shownovel_info').datagrid({
		url:'userbooknovel',   
		fitColumns:true,
		//queryParams: {uid:easyui},
		loadMsg:'数据加载中...',
		striped:true,		//斑马线效果
		pagination : true, //显示分页栏
		nowrap:true,		//超出宽度自动截取
		rownumbers:true,		//显示行数
		sortName:'nid',		//排序的咧
		remoteSort:false,	//前段排序而非服务器的排序，自己的排序
		pageSize:5,
		 pageList:[5,10,15,20,25,30],
		columns:[[
			{field : 'nids',title : '全选',width : 100,align : 'center',checkbox : true},
			{field : 'nid',title : '小说编号',width : 20,align : 'center',sortable : true,hidden:true},
			{field : 'npicture',title : '小说封面',width : 50,align : 'center',formatter : function(value, row, index) {
					var picStr = "";
					if (value.indexOf(",")) {
						value = value.split(",");
						for (var i = 0; i < value.length; i++) {
							if (value[i] == null || value[i] == "") {
								picStr += "<img src='../images/zanwu.jpg' width='100px' height='100px' />";
							} else {
								var patt1 = new RegExp("../");
								if (patt1.test(value[i])) {
									picStr += "<img src='"+value[i]+"' width='100px' height='100px' />";
								} else {
									picStr += "<img src='../images/"+value[i]+"' width='100px' height='100px' />";
								}
							}
						}
					} else if (value != "") {
						picStr += "<img src='"+value+"' width='100px' height='100px' />";
					} else {
						picStr += "<img src='images/nopic2.jpg' width='100px' height='100px' />";
					} 
						return picStr;
					}
				},
			{field : 'tname',title : '类型',width : 30,align : 'center'},
			
			{field : 'pan_name',title : '作者名字',width : 30,align : 'center'},
			{field : 'nname',title : '小说名',width : 50,align : 'center',formatter: function(val,row,index){
				 if(val){
					 return "<a href='toindex_id/"+row.nid +"' >"+val+"</a>";
				} 
			}}, 
			{field : 'ndescription',title : '小说简介',width : 100,align : 'center'},
			{field : 'nstatus',title : '小说状态',width : 50,align : 'center'}, 
			{field : 'ubdate',title : '添加日期',width : 50,align : 'center'} 
		]],
		toolbar:[{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				//获取当前用户选中的行
				var rows = datagridObj.datagrid("getChecked");
				if(rows.length<=0){	//表示没有选中数据
					$.messager.show({title:'温馨提示',msg:"请选择您要删除的书籍...",timeout:2000,showType:'slide'});
				}else{
					$.messager.confirm('删除确认','您确定要删除这些数据吗?',function(result){
						if( result ){
							var nids = "";
							for ( var i=0 ;i<rows.length-1;i++ ){
								nids+=rows[i].nid+",";
							}
							//在for循环中少加了一次
							nids += rows[i].nid;
							
													//json
							$.post("delUserbook",{nid:nids},function(data){
								if( data == "0" ){
									$.messager.alert('失败提示','小说删除失败!','error');
								}else{
									$.messager.show({title:'成功提示',msg:'小说删除成功...',timeout:2000,showType:'slide'});
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
	}
		
	</script>
        
<div class="footer">
    <div class="footer_link">
    </div>
    <div class="footer_cont">
        <p>
            本站所有小说为转载作品，所有章节均由网友上传，转载至本站只是为了宣传本书让更多读者欣赏。</p>
        <p>
            Copyright © 2016
            笔下文学</p>
       <script>footer();</script>
    </div>
</div>
    </div>
</body>
</html>
