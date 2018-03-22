<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 
<%@ taglib uri="http://www.hyycinfo.com" prefix="yc"%> -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="jslib/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jslib/themes/icon.css">
<script src="student/js/lib/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="jslib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jslib/jeasyui.common.js"></script>
<script type="text/javascript" src="student/js/lib/raphael-min.js"></script>
<script type="text/javascript" src="student/js/res/chinaMapConfig.js"></script>
<script type="text/javascript" src="student/js/map-min.js"></script>
<style>
* {
	margin: 0;
	padding: 0;
	border: none;
}

body {
	text-align: center;
	background-color: #dcf5ed;
}

.mapTipText {
	width: 212px;
	height: 130px;
	background-color: #ffffff;
}

.mapTipText .mapTipImg {
	height: 66px;
	width: 66px;
	float: left;
	border: 2px solid #ffffff;
	border-radius: 50%;
	overflow: hidden;
	margin: -12px 5px 0 -12px;
}

.mapTipText .mapTipImg img {
	width: 100%;
	height: 100%;
}

.mapTipText .mapTipList {
	float: left;
	margin-left: 4px;
}

.mapTipText .mapTipList h2 {
	text-align: left;
}

.mapTipText .mapTipList h2 a {
	font-size: 22px;
	color: #262626;
	text-decoration: none;
}

.mapTipText .mapTipList h2 a:hover {
	color: #0085d2;
}

.mapTipText .mapTipList h2 a span {
	font-size: 16px;
	margin-left: 3px;
}

.mapTipText .mapTipList ul {
	width: 180px;
	padding-right: 10px;
}

.mapTipText .mapTipList ul li {
	list-style: none;
	float: left;
	height: 24px;
	line-height: 20px;
	padding: 7px 3px 0 3px;
}

.mapTipText .mapTipList ul li a {
	color: #262626;
	text-decoration: none;
}

.mapTipText .mapTipList ul li a:hover {
	background-color: #2ebcfe;
	color: #ffffff;
}

.body_left {
	width: 100%;
	float: left;
	height: 100%;
}

#table_page td ul {
	list-style-type: none;
}

#table_page td ul li {
	display: inline-block;
	height: 24px;
	line-height: 20px;
	padding: 2px 5px;
}

#table_page td ul li a {
	text-decoration: none;
}

.body_rigth {
	width: 100%;
}

.salary_rank {
	width: 45%;
	height: 300px; background : #7CBFB6;
	position: absolute;
	top: 80px;
	left: 530px;
	padding: 2px 10px 10px 10px;
	border-radius: 10px;
	border: 1px solid #B76610;
	background: #7CBFB6;
}

.salary_year {
	width: 90%;
	margin: 20 auto;
	background: #7CBFB6;
	padding: 2px 10px 10px 10px;
	border-radius: 10px;
	border: 1px solid #B76610;
}

.salary_year_this {
	width: 100%;
}

.map {
	width: 800px;
	height: 800px;
}

#show_province_job_detail {
	display: none; width : 90%;
	margin: 20px auto;
	background: #7CBFB6;
	padding: 2px 10px 10px 10px;
	border-radius: 10px;
	border: 1px solid #B76610;
	width: 90%;
}

.title_span {
	display: block;
	height: 20px;
	line-height: 20px;
	text-align: center;
	font-size: 16px;
	border: 1px solid #B76610;
	border-radius: 5px;
	margin: 5px auto;
	margin-top: 10px; padding : 4px 0px;
	font-weight: 800;
	background: #713B81;
	padding: 4px 0px;
}

#map_div1{
            width:300px;
            height:200px;
            background: #DE8336;
            position: fixed;
            z-index: 40;
            top:100px;
            left:300px;
            overflow: hidden;
            opacity: 0.5;
            display: none;
        }
#map_div2{
            position: fixed;
            z-index: 50;
            width:300px;
            height:200px;
            top:100px;
            left:300px;
            background:;
            display: none;
}

</style>
<script type="text/javascript">
	$(function() {
		$('#ChinaMap').SVGMap({
			mapWidth : 500,
			mapHeight : 400
		});
	});
</script>
<div class="wrap" style="margin-top: 50px;">
	<div class="itemCon" style="margin: 0 auto">
		<div id="ChinaMap"></div>
		<div id="stateTip"
			style="width: 200px; height: 100px; overflow: hidden; position: absolute; left: 100%; text-align: left; display: inline;"></div>
	</div>
	<div id="mapTipContent"
		style="width: 900px; margin: 0 auto; display: none"></div>

</div>
<div id="map_div2">
		<table  id="table_show_examinee_info" style="margin-top:20px;;width: 100%; "  border="0" cellspacing="0" cellpadding="0" >
		
		</table>
		<button style="margin: 0 auto; margin-left: 100px;margin-top: 20px;"  onclick="close_map_detail()">关闭</button>
</div>
<div class="salary_rank">
	<span class="title_span">薪水排行榜</span>
	<table style="width: 100%;">
		<thead>
			<tr>
				<th align="center">编号</th>
				<th align="center">学员名称</th>
				<th align="center">入职公司</th>
				<th align="center">入职工资</th>
				<th align="center">入职年份</th>
				<th align="center">操作</th>
			</tr>
		</thead>
		<tbody id="table_rank_salary">

		</tbody>
	</table>
</div>

<div class='body_rigth' style="display: block">
	<div class="salary_year">
		<div class="salary_year_this">
			<span class="title_span"><yc:yearSelect id="sel_year"></yc:yearSelect>
				年薪资排行</span>
			<table style="width: 100%;">
				<thead>
					<tr>
						<th align="center">编号</th>
						<th align="center">学员名称</th>
						<th align="center">入职公司</th>
						<th align="center">入职工资</th>
						<th align="center">入职年份</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody id="table_this_year_salary">

				</tbody>
			</table>
		</div>
		<!-- <div class="salary_last_year">
			<span class="title_span">2016年薪资排行</span>
			<table style="width: 100%;">
				<thead>
					<tr>
						<th align="center">编号</th>
						<th align="center">学员名称</th>
						<th align="center">入职公司</th>
						<th align="center">入职工资</th>
						<th align="center">入职年份</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody id="table_last_year_salary">

				</tbody>
			</table>
		</div>
		<div class="salary_year_before">
			<span class="title_span">2015年薪资排行</span>
			<table style="width: 100%;">
				<thead>
					<tr>
						<th align="center">编号</th>
						<th align="center">学员名称</th>
						<th align="center">入职公司</th>
						<th align="center">入职工资</th>
						<th align="center">入职年份</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody id="table_before_year_salary">

				</tbody>
			</table>
		</div>
		<div class="salary_year_this">
			<span class="title_span">2017年薪资排行</span>
			<table style="width: 100%;">
				<thead>
					<tr>
						<th align="center">编号</th>
						<th align="center">学员名称</th>
						<th align="center">入职公司</th>
						<th align="center">入职工资</th>
						<th align="center">入职年份</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody id="table_this_year_salary">

				</tbody>
			</table>
		</div> -->
	</div>
</div>

<!-- <div id="detail_dialog" class="easyui-dialog" title="学员信息"
	style="width: 300px; height: 200px; background: #EDECEB;"
	data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false">
	<table id="table_show_examinee_info"
		style="margin: 0 auto; width: 100%;">

	</table>
</div> -->


<div id="show_province_job_detail">

	<span class="title_span">省薪资排行</span>
	<table style="width: 100%;">
		<thead>
			<tr>
				<th align="center">编号</th>
				<th align="center">学员名称</th>
				<th align="center">入职公司</th>
				<th align="center">入职工资</th>
				<th align="center">入职年份</th>
				<th align="center">操作</th>
			</tr>
		</thead>
		<tbody id="table_province_salary">

		</tbody>
		<tr id="table_page">
			<td colspan="6"><span>
					<ul>
						<li id="table_province_total">总条数
						<li>
						<li id="table_province_page">当前页数
						<li>
						<li id="table_province_pages">总页数
						<li>
						<li id="table_province_firstage">首页
						<li>
						<li id="table_province_propage">上一页
						<li>
						<li id="table_province_nextpage">下一页
						<li>
						<li id="table_province_lastpage">尾页
						<li>
					</ul>
			</span></td>
		</tr>
	</table>


</div>

<script type="text/javascript">
		$
				.post(
						'job_findsalarystar.action',
						{},
						function(data) {
							$("#table_rank_salary").html("");
							if (data.responseCode == 1) {
								$("#table_rank_salary")
										.html(
												"<tr><td colspan='6' align='center'><h2>暂无数据</h2></td></tr>");
								return;
							}
							data = data.obj;
							var str = "";
							var count = 0;
							for ( var i in data) {
								if (data[i].examineename == undefined) {
									continue;
								}
								count++;
								str += "<tr bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'>"
								str += '<td align="center">' + count + '</td>'
								str += '<td align="center">'
										+ data[i].examineename + '</td>'
								str += '<td align="center">'
										+ data[i].province + '</td>'		
								str += '<td align="center">' + data[i].salary
										+ '</td>'
								str += '<td align="center">'
										+ data[i].entrydate + '</td>'
								str += '<td align="center"><a href="javascript:find_examinee_detail(\''
										+ data[i].examineeid
										+ '\')">查看详情</a></td>'
								str += "</tr>";
							}
							$("#table_rank_salary").html(str)
						}, 'json')

		function find_examinee_detail(examineeid) {
			$
					.post(
							'job_findexamineedetail.action',
							{
								examineeid : examineeid
							},
							function(data) {
								if (data.responseCode == 1) {
									alert(data.errorMessage);
									return;
								}
								data = data.obj;
								var str = "";
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">名字:</td><td>'
										+ data.name + '</td></tr>';
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">电话:</td><td>'
										+ changenull(data.tel) + '</td></tr>';
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">qq:</td><td>'
										+ changenull(data.qq) + '</td></tr>';
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">邮箱:</td><td>'
										+ changenull(data.email) + '</td></tr>';
								str += '<tr bgcolor="#EDECEB" height="25"><td align="right">微信:</td><td>'
										+ changenull(data.weixin)
										+ '</td></tr>';
								$("#table_show_examinee_info").html(str)
								$("#map_div1").show(1000);
					            $("#map_div2").show(1000);
							}, 'json')
		}
$(function(){
	
	var sel= $("#sel_year");
	sel.on("change",function(){
		 select_year();
     });
})
		
		function select_year(){
			showdata("table_this_year_salary", $("#sel_year").val())
		}
		
		showdata("table_this_year_salary", 2017)

		function showdata(id, year) {
			$("#" + id).html("")
			$
					.post(
							'job_findsalarybyyear.action',
							{
								year : year
							},
							function(data) {
								$("#" + id).html("");
								if (data.responseCode == 1) {
									$("#" + id)
											.html(
													"<tr><td colspan='6' align='center'><h2>暂无数据</h2></td></tr>");
									return;
								}
								data = data.obj;
								var str = "";
								var count = 0;
								for ( var i in data) {
									if (data[i].examineename == undefined) {
										continue;
									}
									count++;
									str += "<tr  onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'>"
									str += '<td align="center">' + count
											+ '</td>'
									str += '<td align="center">'
											+ data[i].examineename + '</td>'
									str += '<td align="center">'
											+ data[i].province + '</td>'
									str += '<td align="center">'
											+ data[i].salary + '</td>'
									str += '<td align="center">'
											+ data[i].entrydate + '</td>'
									str += '<td align="center"><a href="javascript:find_examinee_detail(\''
											+ data[i].examineeid
											+ '\')">查看详情</a></td>'
									str += "</tr>";
								}
								$("#" + id).html(str)
							}, 'json')
		}

		function changenull(data) {
			if (data == null || data == "" || data == undefined) {
				return "无";
			}
			return data;
		}
		
	</script>

<script type="text/javascript">
	
	$
	.post(
			'job_findJobInfo.action',
			{},
			function(data) {
				if (data.responseCode == 1) {
					alert(data.errorMessage);
					return;
				}
				data = data.obj;
				var str = ""
				for ( var i in data) {
					str += '<div class="mapTipText mapTipText'+data[i].id+'">';
					//str += ' <div class="mapTipImg"><img src="img/'+data[i].id+'.png" alt=""/></div>';
					str += ' <div class="mapTipList">';
					str += ' <h2><a href="javascript:void()">'
							+ data[i].name + '</a></h2>';
					str += ' <ul>';
					str += ' <li><a href="javascript:click_detail_province(\''+data[i].name+'\',\''+data[i].worknum+'\')" ><label>就业人数 : '
							+ data[i].worknum + '</label></a></li>';
					str += ' <li><a href="javascript:click_detail_province(\''+data[i].name+'\',\''+data[i].worknum+'\')"><label>平均工资 : '
							+ data[i].salary + '</label></a></li>';
					str += ' </ul>';
					str += ' </div>';
					str += ' </div>';
				}
				$("#mapTipContent").html(str);

			}, 'json');
	
		
			function click_detail_province(province,num){
				if(num==0){
					alert('暂无数据')
					return 
				}
				
		/* $('#table_province_salary').datagrid({ 
			        pageNumber: 1, 
			       	url: "job_findJobByProvince.action", 
			        columns: [[ 
			         { field: 'Id', title: 'id', width: 100 }, 
			         { field: 'examineename', title: 'name', width: 100 }, 
			         { field: 'province', title: 'name', width: 100 }, 
			         { field: 'salary', title: 'name', width: 100 }, 
			         { field: 'entrydate', title: 'name', width: 100 }, 
			         { field: 'entrydate', title: 'name', width: 100 }, 
			        ]], 
			        pagination: true, 
			        rownumbers: true,
			        pageList: [5,10, 20] ,
			        fit : true,
					border : true,
					queryParams: {
						province: province,
					}
			   }); */

		$.post('job_findJobByProvince.action',{province:province},function(data){
			if (data.responseCode == 1) {
				alert(data.errorMessage);
				return;
			}
			$.ajaxSettings.async = false;//设置为同步加载
			$("#show_province_job_detail").css("display","block");
			total=data.obj.total
			data = data.obj.rows;
			var count =0;
			var str = ""
			for ( var i in data) {
				if (data[i].examineename == undefined) {
					continue;
				}
				count++;
				str += "<tr  onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'>"
				str += '<td align="center">' + count
						+ '</td>'
				str += '<td align="center">'
						+ data[i].examineename + '</td>'
				str += '<td align="center">'
						+ data[i].province + '</td>'
				str += '<td align="center">'
						+ data[i].salary + '</td>'
				str += '<td align="center">'
						+ data[i].entrydate + '</td>'
				str += '<td align="center"><a href="javascript:find_examinee_detail(\''
						+ data[i].examineeid
						+ '\')">查看详情</a></td>'
				str += "</tr>";
			}
			$.post('job_findJobByProvinceTotal.action',{province:province},function(data){
				provincetotal=data.obj;
			},'json')
			$("#table_province_total").html('<span>总条数:'+total+'</span>');
			$("#table_province_page").html('<span>当前页数:1</span>');
			$("#table_province_pages").html('<span>总页数:'+Math.ceil(total/10)+'</span>');
			$("#table_province_firstage").html('<span><a href="javascript:click_detail_province_bypage(\''+province+'\',1)">首页</a></span>');
			$("#table_province_propage").html('<span><a href="javascript:click_detail_province_bypage(\''+province+'\',\''+(1)+'\')">上一页</a></span>');
			$("#table_province_nextpage").html('<span><a href="javascript:click_detail_province_bypage(\''+province+'\',\''+(2)+'\')">下一页</a></span>');
			$("#table_province_lastpage").html('<span><a href="javascript:click_detail_province_bypage(\''+province+'\',\''+Math.ceil(total/10)+'\')">尾页</a></span>');
			$("#table_province_salary").html(str);
		},'json')
		$.ajaxSettings.async = true;
		scrollwindow()
	}
var provincetotal;			
			function close_map_detail(){
				$("#map_div1").hide(1000);
	            $("#map_div2").hide(1000);
			}		
			
	function click_detail_province_bypage(province,page){
		if(page==0){
			alert('已经是首页')
			return;
		}
		if(page>Math.ceil(provincetotal/10)){
			alert('已经是最后一页')
			return;
		}
		$.post('job_findJobByProvince.action',{province:province,page:page,row:10},function(data){
			if (data.responseCode == 1) {
				alert(data.errorMessage);
				return;
			}
			
			$("#show_province_job_detail").css("display","block");
			total=data.obj.total
			data = data.obj.rows;
			var count =(page-1)*10;
			var str = ""
			for ( var i in data) {
					if (data[i].examineename == undefined) {
						continue;
					}
					count++;
					str += "<tr bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'>"
					str += '<td align="center">' + count
							+ '</td>'
					str += '<td align="center">'
							+ data[i].examineename + '</td>'
					str += '<td align="center">'
							+ data[i].province + '</td>'
					str += '<td align="center">'
							+ data[i].salary + '</td>'
					str += '<td align="center">'
							+ data[i].entrydate + '</td>'
					str += '<td align="center"><a href="javascript:find_examinee_detail(\''
							+ data[i].examineeid
							+ '\')">查看详情</a></td>'
					str += "</tr>";
				}
			$("#table_province_total").html('<span>总条数:'+total+'</span>');
			$("#table_province_page").html('<span>当前页数:'+page+'</span>');
			$("#table_province_pages").html('<span>总页数:'+Math.ceil(total/10)+'</span>');
			$("#table_province_firstage").html('<span><a href="javascript:click_detail_province_bypage(\''+province+'\',1)">首页</a></span>');
			$("#table_province_propage").html('<span><a href="javascript:click_detail_province_bypage(\''+province+'\',\''+(parseInt(page)-1)+'\')">上一页</a></span>');
			$("#table_province_nextpage").html('<span><a href="javascript:click_detail_province_bypage(\''+province+'\',\''+(parseInt(page)+1)+'\')">下一页</a></span>');
			$("#table_province_lastpage").html('<span><a href="javascript:click_detail_province_bypage(\''+province+'\',\''+Math.ceil(total/10)+'\')">尾页</a></span>');
			$("#table_province_salary").html(str);
			
		},'json')
	}
	
	
	function scrollwindow() 
	{ 
            $('html, body').animate({  
                scrollTop: $("#table_province_salary").offset().top  
            }, 200);  
	} 
	</script>
