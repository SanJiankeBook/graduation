<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!--上传的小说显示在后台进行审核，审核分为待审核，通过，未通过，审核通过后才能显示在前台  -->
    <!-- 将待审核的单独放在一个页面中，添加一个通过按钮， -->
   
    <!-- select nname
		from novel n
		inner join novel_type novelType
		on n.tid=novelType.tid
		where
		novelType.tname=#{novelType.tname} and standby_1='通过' -->
<table id="type_shownovels_info" data-options="fit:true"></table> 
	
	<script type="text/javascript">
	var datagridObj;
	var editRow = undefined;	//当前正在被编辑的行的索引
	var op;
	var flag;

	datagridObj=$('#type_shownovels_info').datagrid({
		url:'findNovelByPage',   
		fitColumns:true,
		loadMsg:'数据加载中...',
		striped:true,		//斑马线效果
		pagination : true, //显示分页栏
		nowrap:true,		//超出宽度自动截取
		rownumber:true,		//显示行数
		sortName:'nid',		//排序的咧
		remoteSort:false,	//前段排序而非服务器的排序，自己的排序
		pageSize:5,
		 pageList:[5,10,15,20,25,30],
		columns:[[
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
		]]
	});
		
	</script>