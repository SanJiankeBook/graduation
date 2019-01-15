var semester;
$(function() {
	createVersionOption();
	semester = $("#find_work_semesterS1").attr('value');
	var dateTime = $("#find_dateTime").find("option:selected").text().trim();

	$("#find_work_semesterS1").prop('checked', true);
	showClassName(semester);

	//$("#checkDate").val(changeTime(new Date()));
})

function find_checkwork(){
	
	var semester = $('input[name="semester"]:checked').val();
	var classname=$("#find_work_examineeClass").val();
	var checkDate=$("#find_checkDate").val();
	var editionid=$("#find_work_version").val();
	var subjectid=$("#find_work_subject").val();
	var chapterid=$("#find_work_chapter").val();

	if(classname==0){
		alert('请选择班级')
		return;
	}
	
	$.ajax({
		url : "work_findWorkresult.action",
		data:{
			examineeclassid:classname,
			checkdate:checkDate,
			semester:semester,
			editionid:editionid,
			subjectid:subjectid,
			chapterid:chapterid,
		},
		type : "POST",
		dataType : "JSON",
		success : function(data) {
			if(data.responseCode==1){
				$("#show_find_left").css("display","none");
				$("#show_work_checkcount").css("display","none");
				$('#findwork_tree').html("");
				return ;
			}
			
			$('#show_work_checkcount').css("display","none");
			$('#show_find_left').css("display","block");
			$('#findwork_tree').html("");
			$('#show_find_checkwork').html("");
			
			var data=data.obj;
			for (var i in data) {
				if(data[i].wname==undefined){
					continue;
				}
				$('#findwork_tree').tree('append', {
					data : [ {
						id : data[i].wid,
						text : data[i].wname
					} ]
				});
				node = $('#findwork_tree').tree('find',
						data[i].wid);
				/*for(var j in JSON.parse(data[i])){

				}*/
			}
			$('#findwork_tree').tree('collapseAll');
		}
	});
	
	$('#findwork_tree').tree({
		onClick : function(node) {
			wid = node.id;
			if (!$('#enterprise_tree').tree("isLeaf", node.target)) {//如果不是叶子结点
				return;
			}
			$.post("work_findWorkbyid.action",{wid:wid},
					function(data){
				var str='';
				if(data.responseCode==1){
					str+="<tr><td></td><td></td><td align='center'>'<h2>无数据</h2>'</td> </tr>"
					$("#show_find_checkwork").html($("#work_table_check").html()+str);
					return ;
				}
				if(data.obj.result==undefined){
					alert('该作业还未检查')
					$("#show_find_checkwork").html("");
					$('#show_work_checkcount').css("display","none");
					/*showNoCheck();*/
				}else{
					showcheckinfo(data);
				}
				
				
			},'json')
		}
	})

	/*datagrid=$("#show_find_checkwork").datagrid({  
		url:"/Examination2.0/work_findWorkresult.action",//加载的URL  
		pagination:true,//显示分页  
		pageSize:10,//分页大小  
		pageList:[5,10,15,20],//每页的个数  
		fit:true,//自动补全  
		toolbar : '#find_table_slelct',
		fitColumns:true,  
		queryParams : {
			examineeclassid:classname,
			chackdate:checkDate,
			semester:semester,
			editionid:editionid,
			subjectid:subjectid,
			chapterid:chapterid,
		},
		iconCls:"icon-save",//图标  
		columns:[[      //每个列具体内容  
		                {  
		                	field:'wid',  
		                	title:'编号',  
		                	width:50,  
		                },     
		                {field:'wname',title:'作业名',align :'center',width:100},     
		                {field:'checkdate',title:'检查时间',align : 'center',width:100,
		                	formatter : function(value, row, index) {
		                		return change(row.checkdate)
		                	}}, 
		                {field:'description',title:'作业描述',align : 'center',width:200},
		                {field:'remark',title:'备注',align : 'center',width:100} ,
		                {field:'result',title:'操作',align : 'center',width:100,
		                	formatter : function(value, row, index) {
		                		if(row.result!=null&&row.result!=""&&row.result!=undefined){
		                			return '<a href=\"changefindwork.jsp?wid='+row.wid+'">查看</a>';
		                		}else{
		                			return '<label>未检查</label>';
		                		}
		                		
		                	}} 
		                ]]  
	}) */


	/*$.post("/Examination2.0/work_findWorkresult.action",{
		examineeclassid:classname,
		chackdate:checkDate,
		semester:semester,
		editionid:editionid,
		subjectid:subjectid,
		chapterid:chapterid,
	},function(data){
		var str="";
		str+='<tr height="25"><th bordercolor="#7EA6DC" width="8%" >编号</th>';
		str+='<th bordercolor="#7EA6DC" width="12%">作业名</th>';
		str+='<th bordercolor="#7EA6DC" width="30%">检查时间</th>';
		str+='<th bordercolor="#7EA6DC">作业描述</th>';
		str+='<th bordercolor="#7EA6DC">操作</th></tr>';
		if(data.responseCode==1){
			str+="<tr><td></td><td></td><td align='center'>'<h2>暂无数据</h2>'</td> </tr>"
				$("#showlist_work").html(str);
			return ;
		}
		var obj=data.obj;
		for(var i in obj){
			if(obj[i].wid==undefined){
				continue
			}
			var flag=false;
			if(obj[i].result==null||obj[i].result==""){
				flag=true;
			}
			var description=obj[i].description;
			if(description==undefined||description==""){
				description="无";
			}
			str+='<tr height="25"><td width="8%" align="center">'+obj[i].wid+'</td>';
			str+='<td width="12%" align="center">'+obj[i].wname+'</td>';
			str+='<td width="30%" align="center">'+obj[i].chackdate+'</td>';
			str+='<td align="center">'+description+'</td>';
			str+='<td align="center"><a href=\"changefindwork.jsp?wid='+obj[i].wid+'">查看</a></td></tr>';
		}		
		$("#show_find_checkwork").html(str);
	},'json')
	 */

}

function showcheckinfo(data){
	var str='';
	str+='<tr height="25"><th bordercolor="#7EA6DC" width="18%" >编号</th>';
	str+='<th bordercolor="#7EA6DC" width="12%">学生姓名</th>';
	str+='<th bordercolor="#7EA6DC" width="40%">作业检查情况</th>';
	str+='<th bordercolor="#7EA6DC" width="30%">备注</th>';
	$("#show_work_checkcount").css("display","block");
	$("#show_find_checkwork").html(str)
	var checkcount=data.obj.checkcount;
	var classcount=data.obj.classcount;
	
	var str="<lable class='show_label_count'>班级人数："+classcount+"</label><br><br><lable class='show_label_count'>已检查人数："+checkcount+"</label>";
	$("#show_work_checkcount").html(str);
	var resultinfo=data.obj.result;
	crs = resultinfo.split("|");
	var len=crs.length;
	for(var i=0;i<len;i++){
		index = i + 1;
		var stu = crs[i].split(",");
		var stuName = stu[0];
		var stuStatus = stu[1];
		var status = 0;
		var remark = '';
		var temp=stuStatus.split("-");
		status = temp[0];
		remark = temp[1];
		var classname="";
		var tempstr="";
		if(status==0){
			classname+="<td align='center' style='background-color:red;'  id='stuName"
			+ (i + 1)
			+ "' >"
			+ stuName
			+ "</td>";
			
			tempstr+= "<input type='radio' name='status"
				tempstr+= i
				tempstr+= "'  id='status1' value='"
				tempstr+= stuName
				tempstr+= ",1' />已检查&nbsp;&nbsp;&nbsp;"
				tempstr+= "<input type='radio' name='status"
				tempstr+= i
				tempstr+= "'  id='status2' value='"
				tempstr+= stuName
				tempstr+= ",0' checked='checked'/>未检查&nbsp;&nbsp;&nbsp;"
		}else{
			classname+="<td align='center'   id='stuName"
				+ (i + 1)
				+ "' >"
				+ stuName
				+ "</td>";
			
			
			tempstr+= "<input type='radio' name='status"
				tempstr+= i
				tempstr+= "'  id='status1' value='"
				tempstr+= stuName
				tempstr+= ",1'checked='checked' />已检查&nbsp;&nbsp;&nbsp;"
				tempstr+= "<input type='radio' name='status"
				tempstr+= i
				tempstr+= "'  id='status2' value='"
				tempstr+= stuName
				tempstr+= ",0' />未检查&nbsp;&nbsp;&nbsp;"
		}
		
		$("#show_find_checkwork")
		.append(
				"<tr height='23' id='student"
				+ i
				+ "' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'; align='left'>"
				+ "<td align='center' >"
				+ (i + 1)
				+ "</td>"
				+classname
				+ "<td  align='center' id='radio"
				+ i
				+ "'>"
				+tempstr
				+ "</td>"
				+ "<td ><input type='text' size='30' id='remarkInfo_work"
				+ (i + 1)
				+ "' value='"
				+ remark
				+ "' /></td>"
				+ "</tr>")
				$("#radio" + i + "  #status" + status)
				.attr("checked", true);
	}
}

function showNoCheck(){
	$("#show_find_checkwork").html("<tr ><td>该作业还未检查</td></tr>")
}

function change(data) {
	var birthday = new Date(data);
	var time=birthday.toLocaleString()
	time=time.replace(/上午(\w|:)*/,"");
	//time=time.replace(/\s$/,"");
	return time;
}


function checkSemesters(semester) {
	showClassName(semester);
}

function showClassName(semester) {
	changeVersion()
	changeSubject()
	$.post("examineeclass_showClassList.action", {
		semester : semester
	}, function(obj) {
		$("#find_work_examineeClass").html("<option value='0'>--请选择--</option>");
		if (obj == null) {
			return;
		}
		var len=obj.length;
		for (var i = 0; i < len; i++) {
			$("#find_work_examineeClass").append(
					"<option value='" + obj[i].id + "'>" + obj[i].className
					+ "</option>");
		}
	});
}

function changeVersion() {
	semester = $('input[name="semester"]:checked').val();
	version = $('#find_work_version').val();
	createSelectOption(version, semester);
}
//生成科目列表
function createSelectOption(version,semester){
	$.ajaxSettings.async = false; 
	$.ajax({
		url:"initData_subject.action",
		type:"post",
		data:{"editionId":version,"semester":semester},
		datatype:"json",
		success:function(examineeClassList){
			var optionstr="";
			if(examineeClassList.responseCode!=1){
				optionstr+="<option value='0'>请选择</option>";
				for(var i=0;i<examineeClassList.obj.length;i++){
					optionstr+="<option value='"+examineeClassList.obj[i].id+"'>"+examineeClassList.obj[i].subjectName+"</option>";
				}
			}else{
				optionstr+="<option value=0 >没有数据</option>"
			}

			$("#find_work_subject").html(optionstr);
		}
	});
}

function changeSubject() {
	subjectId = $('#find_work_subject').val();
	createChapterOption(subjectId);
}

//生成章节列表
function createChapterOption(subjectId){
	$.ajaxSettings.async = false; 
	$.ajax({
		url:"initData_chapter.action",
		type:"post",
		data:{"subjectId":subjectId},
		datatype:"json",
		success:function(examineeClassList){
			var optionstr="";
			if(examineeClassList.responseCode!=1){
				optionstr+="<option value='0'>请选择</option>";
				for(var i=0;i<examineeClassList.obj.length;i++){
					optionstr+="<option value='"+examineeClassList.obj[i].id+"'>"+examineeClassList.obj[i].chapterName+"</option>";
				}
			}else{
				optionstr+="<option value=0 >没有数据</option>"
			}

			$("#find_work_chapter").html(optionstr);
		}
	});
}

//生成版本列表
function createVersionOption(){
	var currentUse="";
	$.ajaxSettings.async = false; 
	$.ajax({
		url:"initData_version.action",
		type:"post",
		datatype:"json",
		success:function(examineeClassList){
			var optionstr="<option value=0 >--请选择--</option>";	
			if(examineeClassList.responseCode!=1){
				for(var i=0;i<examineeClassList.obj.length;i++){
					optionstr+="<option value='"+examineeClassList.obj[i].id+"' name='className'>"+examineeClassList.obj[i].editionName+"</option>";
					if(examineeClassList.obj[i].currentUse==1){
						currentUse=examineeClassList.obj[i].id;
					}
				}
			}else{
				optionstr+="<option value=-1 >没有数据</option>"
			}	
			$("#find_work_version").html(optionstr);
			$("#find_work_versionversion option[value="+currentUse+"]").attr("selected",true)

		}

	});

}



