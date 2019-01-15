var semester;
$(function() {
	semester = $("#check_semesterS1").attr('value');
	//var dateTime = $("#dateTime").find("option:selected").text().trim();

	$("#check_semesterS1").prop('checked', true);
	showClassName(semester);
	userName = localStorage.getItem("systemUser_userName");
	$("#systemUser_userName").html("检查教员：" + userName);
	//$("#checkDate").val(changeTime(new Date()));
})


function checkSemesters(semester) {
	showClassName(semester);
}

function showClassName(semester) {
	$.post("examineeclass_showClassList.action", {
		semester : semester
	}, function(obj) {
		$("#check_examineeClass").html("<option value='0'>--请选择--</option>");
		if (obj == null) {
			return;
		}
		var len=obj.length;
		for (var i = 0; i < len; i++) {
			$("#check_examineeClass").append(
					"<option value='" + obj[i].id + "'>" + obj[i].className
					+ "</option>");
		}
	});
}

function check_findclassinfo(){
	var classname=$("#check_examineeClass").val();
	if(classname==0){
		alert("请选择班级");
		return;
	}

	$("#showlist_work").html("");
	$("#checkingwork_tree").html("");
	var semester  = $('input[name="semester"]:checked').val(); //获取被选中Radio的Value值
	var checkDate=$("#check_checkdate").val();
	//document.getElementById("check_form").submit();
	$.ajax({
		url : "work_findWork.action",
		data:{
			semester:semester,
			checkDate:checkDate,
			check_examineeClass:classname
		},
		type : "POST",
		dataType : "JSON",
		success : function(data) {
			if(data.responseCode==1){
				alert('无数据')
				return ;
			}
			$('#show_check_left').css("display","block");
			var data=data.obj;
			for (var i in data) {
				if(data[i].wname==undefined){
					continue;
				}
				$('#checkingwork_tree').tree('append', {
					data : [ {
						id : data[i].wid,
						text : data[i].wname
					} ]
				});
				node = $('#checkingwork_tree').tree('find',
						data[i].wid);
				/*for(var j in JSON.parse(data[i])){

				}*/
			}
			$('#checkingwork_tree').tree('collapseAll');
		}
	});

	$('#checkingwork_tree').tree({
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
						$("#showlist_work").html($("#showlist_work").html()+str);
					return ;
				}
				if(data.obj.result==undefined){
					showclass(wid,data)
				}else{
					showcheckinfo(data);
				}


			},'json')
		}
	})
}


function showcheckinfo(data){
	var str='';
	str+='<tr height="25"><th bordercolor="#7EA6DC" width="8%" >编号</th>';
	str+='<th bordercolor="#7EA6DC" width="12%">学生姓名</th>';
	str+='<th bordercolor="#7EA6DC" width="30%">作业检查情况</th>';
	str+='<th bordercolor="#7EA6DC" width="20%" >备注</th>';
	str+='<th bordercolor="#7EA6DC">操作</th>';
	$("#showlist_work").html(str)
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
		if(stuStatus==undefined){
			continue;
		}
		var temp=stuStatus.split("-");
		status = temp[0];
		remark = temp[1];

		var tempstr="";
		if(status==0){
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
		var status="无";
		if(data.obj.status==1){
			status='<a href="javascript:finddectation(\''+data.obj.wid+'\',\''+stuName+'\',\''+data.obj.examineeclassid+'\')">查看默写情况</a>';
		}
		$("#showlist_work")
		.append(
				"<tr height='23' id='student"
				+ i
				+ "' bgcolor='#EDECEB' onmouseover=this.bgColor='#93BBDF'; onmouseout=this.bgColor='#EDECEB'; align='left'>"
				+ "<td align='center' width='8%'>"
				+ (i + 1)
				+ "</td>"
				+ "<td align='center' width='10%' id='stuName"
				+ (i + 1)
				+ "' >"
				+ stuName
				+ "</td>"
				+ "<td width='40%' align='center' id='radio"
				+ i
				+ "'>"
				+tempstr
				+ "</td>"
				+ "<td width='20%'><input type='text'  id='remarkInfo_work"
				+ (i + 1)
				+ "' value='"
				+ remark
				+ "' /></td>"
				+"<td align='center'>"+status+"</td>"
				+ "</tr>")
	}
	var s='<tr><td></td><td></td><td><input type="button" value="提交" id="btn_check_work" onclick="click_btn_work()"/></td></tr>';
	$("#showlist_work").html($("#showlist_work").html()+s)
}



function click_btn_work(){
	var resultInfo = "";
	var count = 0; 
	var sturemark = ""; 
	var checkcount=0;
	$("#showlist_work input[type=radio]").each(function() {
		if (this.checked) {
			count += 1;
			sturemark = "#remarkInfo_work" + count;
			sturemark = $(sturemark).val();
			var s=$(this).val().split(",")

			if(s[1]==1){
				checkcount++;
			}
			if (sturemark == null || sturemark == "") {
				resultInfo += $(this).val() + "- |";
			} else {
				resultInfo += $(this).val() + "-" + sturemark + "|";
			}
		}
	});
	$.post("work_addCheckResult.action", {wid:wid,resultInfo:resultInfo,checkcount:checkcount},function(data){
		if(data.responseCode==0){
			alert("提交成功")
		}else{
			alert("提交失败")
		}

	},'json')
}




function showclass(wid,date){
	$.post("work_checkWork.action",{wid:wid},
			function(data){
		var str='';
		str+='<tr height="25"><th bordercolor="#7EA6DC" width="8%" >编号</th>';
		str+='<th bordercolor="#7EA6DC" width="12%">学生姓名</th>';
		str+='<th bordercolor="#7EA6DC" width="30%">作业检查情况</th>';
		str+='<th bordercolor="#7EA6DC" width="20%">备注</th>';
		str+='<th bordercolor="#7EA6DC">操作</th>';
		var i=1;
		for(var j in data){
			for(var k in data[j]){
				if(data[j][k].name==''){
					continue;
				}
				if(date.obj.status==1){
					status='<a href=\'javascript:finddectation(\''+wid+'\',\''+data[j][k].name+'\',\''+date.obj.examineeclassid+'\')\'>查看默写情况</a>';
				}else{
					status='无';
				}

				//if(data[j][k].name=)
				str+='<tr><td bgcolor="#EDECEB" onmouseover=this.bgColor="#93BBDF" onmouseout=this.bgColor="#EDECEB" align="center">'+i+'</td>';
				str+='<td bgcolor="#EDECEB" onmouseover=this.bgColor="#93BBDF" onmouseout=this.bgColor="#EDECEB" align="center">'+data[j][k].name+'</td>';
				str+='<td bgcolor="#EDECEB" onmouseover=this.bgColor="#93BBDF" onmouseout=this.bgColor="#EDECEB" align="center">';
				str+='&nbsp;&nbsp;&nbsp;&nbsp;<input  type="radio" name="work_status'+i+'"  value="'+data[j][k].name+',1"  />&nbsp;&nbsp;检查&nbsp;&nbsp;';
				str+='&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="work_status'+i+'"  value="'+data[j][k].name+',0" checked="checked" />&nbsp;&nbsp未检查&nbsp;&nbsp;';
				str+='</td>'
					str+='<td  align="center"><input type="text" id="remark_check'+i+'"/></td>'
					str+="<td align='center' bgcolor='#EDECEB'>"+status+"</td></tr>"
					i++;
			}
		}
		str+='<tr><td align="center" colspan="4"><input type="button" value="提交" id="btn_check_work" onclick="click_btn_work()"/></tr>'
			$("#showlist_work").html(str)
	},'json')
}


function change(data) {
	var birthday = new Date(data);
	var time=birthday.toLocaleString()
	time=time.replace(/上午(\w|:)*/,"");
	//time=time.replace(/\s$/,"");
	return time;
}



function finddectation(wid,name,examineeclassid){
	$.post('work_findictation_detail.action',
			{
		wid:wid,
		name:name,
		examineeclassid:examineeclassid
			},function(data){
				if(data.responseCode==1){
					alert('未找到该学员的默写文档')
					return ;
				}
				show_dictation(data)
			},'json');
}

function show_dictation(data){
	$("#show_dictation").css("display","block");
	$("#show_dictation_detail").val(data.obj)
}

