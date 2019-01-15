<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();   //   /douban
	//                   http               ：//         localhost             :        8080                  /douban  /          
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath %>">

<script type="text/javascript" src="jslib/jquery.min.js"></script>
<script type="text/javascript" src="jslib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="back/ckeditor/ckeditor.js"></script>

<title>资源上传</title>
</head>
<body style="text-align: center;margin: 0 auto;">

<style type="text/css">
.find_table_slelct{
border: 1px solid #F8D880 ;
border-radius:5px;
width: 100%;
margin-top: 10px;
background-color: #F3F3F3;
padding-bottom: 10px;
}
.inp_L1 {
    background-image: url(images/bg_x.jpg);
    background-repeat: no-repeat;
    BORDER-TOP-WIDTH: 0px;
    BACKGROUND-POSITION: -4px -55px;
    BORDER-LEFT-WIDTH: 0px;
    BORDER-BOTTOM-WIDTH: 0px;
    WIDTH: 82px;
    COLOR: #464646;
    LINE-HEIGHT: 23px;
    HEIGHT: 23px;
    BORDER-RIGHT-WIDTH: 0px;
}
.inp_L2 {
	background-image: url(images/bg_x.jpg);
	background-repeat: no-repeat;
	BORDER-TOP-WIDTH: 0px;
	BACKGROUND-POSITION: -4px -80px;
	BORDER-LEFT-WIDTH: 0px;
	BORDER-BOTTOM-WIDTH: 0px;
	WIDTH: 82px;
	COLOR: #464646;
	LINE-HEIGHT: 23px;
	HEIGHT: 23px;
	BORDER-RIGHT-WIDTH: 0px
}
.prompt {
    font-size: 10.5pt;
    color: #FF0000;
}
</style>
<script type="text/javascript">

$(function(){ 
	bool=true;
	editr1=CKEDITOR.replace( 'editor01' );
	editr2=CKEDITOR.replace( 'editor02' );
	editr3=CKEDITOR.replace( 'editor03' );
	editr4=CKEDITOR.replace( 'editor04' );
	editr5=CKEDITOR.replace( 'editor05' );
	$("#book").hide();
	$("#resourceCode").hide();
	$("#classMaterials").hide();
	$("#utility").hide();
	$("#interview").hide();
	
	$.post("ResourceTypeAction_getResourceType.action", {
	}, function(data) {
		if(data.responseCode==0){
			var classNameInfos =data.obj ;
			var optionstr="<option>--请选择--</option>";
				for(var i=0;i<classNameInfos.length;i++){
					optionstr+="<option value='"+classNameInfos[i].id+"'>"+classNameInfos[i].typename+"</option>";	  
				}
			$("#resourcetype").html(optionstr);
		}
	});
	});
function toVaild1(){
	var bool =true;
    var val = document.getElementById("category").value;
    
    if(val == "--请选择--"){
        alert("请选择一个类别");
        return false;
  }
    val = document.getElementById("isbn").value;
    if(val == ""||val==null){
        alert("请填写isbn");
        return false;
  	}else{
  		$.ajax({
  			url : 'ResourceTypeAction_searchIsbn.action',
  			data : {
  				isbn : val
  			},
  			type:"post",
  			async:false,//取消ajax异步加载，因为这里不能异步加载
  			dataType : 'json',
  			success : function (data) {
				if(data.responseCode==1){
					alert("该isbn已经存在")
					bool=false;
					return false;
					
			 	}
	         }
  		});
  	}
    
   if(!bool){
	  return false; 
   }
  val = document.getElementById("title").value;
    if(val == "" ||val==null){
        alert("请填写书名");
        return false;
  }
    val = document.getElementById("price").value;
    if(val != "" ||val!=null){
    	 if(isNaN(val)){
    		    alert('请填写一个纯数字的价格，可以包含小数点');
    		    return false;
    	}
  }
   
   /*  val = document.getElementById("author").value;
    if(val == "" ||val==null){
        alert("请填写作者名");
        return false;
  } */
    val = document.getElementById("pdfsUrl1").value;
    if(val == "" ||val==null){
        alert("请选择书本pdf");
        return false;
  }
}

function toVaild2(){
var  val = document.getElementById("title2").value;
    if(val == "" ||val==null){
        alert("请填写名称");
        return false;
  }
    val = document.getElementById("pdfsUrl2").value;
    if(val == "" ||val==null){
        alert("请选择上传资料");
        return false;
  }
   /*  $('#editor02').val(editr2.getData());
    val=$.trim($("#editor02").val());
    if(val == "" ||val==null){
        alert("请编写描述");
        return false;
  } */
}

function toVaild3(){
	 
	 var val = document.getElementById("version").value;
      if(val == "0"||val==0){
          alert("请选择版本");
          return false;
    }
	   val = document.getElementById("chapter_semester").value;
     if(val == "0"||val==0){
         alert("请选择学期");
         return false;
   }
     
    val = document.getElementById("subject").value;
    if(val == "0"||val==0){
        alert("请选择科目");
        return false;
  }
 
  
  val = document.getElementById("title3").value;
    if(val == "" ||val==null){
        alert("请填写名字");
        return false;
  }
    val = document.getElementById("pdfsUrl3").value;
    if(val == "" ||val==null){
        alert("请选择资料");
        return false;
  }
}

function toVaild4(){
	 var  val = document.getElementById("title4").value;
    if(val == "" ||val==null){
        alert("请填写名称");
        return false;
  }
    val = document.getElementById("pdfsUrl4").value;
    if(val == "" ||val==null){
        alert("请选择上传资料");
        return false;
  }
   /*  $('#editor04').val(editr4.getData());
    val=$.trim($("#editor04").val());
    if(val == "" ||val==null){
        alert("请编写描述");
        return false;
    } */
}

function toVaild5(){
	 var  val = document.getElementById("title5").value;
    if(val == "" ||val==null){
        alert("请填写名称");
        return false;
  }
    val = document.getElementById("pdfsUrl5").value;
    if(val == "" ||val==null){
        alert("请选择上传资料");
        return false;
  }
   /*  $('#editor05').val(editr5.getData());
    val=$.trim($("#editor05").val());
    if(val == "" ||val==null){
        alert("请编写描述");
        return false;
    } */
}	
function getAllType() {
	$.post("ResourceTypeAction_getAllType.action", {
	}, function(data) {
		if(data.responseCode==0){
			var classNameInfos =data.obj ;
			var optionstr="<option>--请选择--</option>";
				for(var i=0;i<classNameInfos.length;i++){
					optionstr+="<option value='"+classNameInfos[i].cid+"'>"+classNameInfos[i].name+"</option>";	  
				}
			$("#category").html(optionstr);
		}
	});
}
//显示不同资源上传的界面
function showExamineeNames(value) {
	if(value=="1"){//书籍上传
		$("#book").show();
		$("#resourceCode").hide();
		$("#classMaterials").hide();
		$("#utility").hide();
		$("#interview").hide();
		$("#resourceTypeId1").val(value);
		getAllType();
		return;
	}else if(value=="2"){
		$("#book").hide();
		$("#resourceCode").show();
		$("#classMaterials").hide();
		$("#utility").hide();
		$("#interview").hide();
		$("#resourceTypeId2").val(value);
		return;
	}else if(value=="3"){//课堂资料上传
		$("#book").hide();
		$("#resourceCode").hide();
		$("#classMaterials").show();
		$("#utility").hide();
		$("#interview").hide();
		$("#resourceTypeId3").val(value);
		showEditiontName();
		return;
	}else if(value=="4"){
		$("#book").hide();
		$("#resourceCode").hide();
		$("#classMaterials").hide();
		$("#utility").show();
		$("#interview").hide();
		$("#resourceTypeId4").val(value);
		return;
	}else if(value=="5"){
		$("#book").hide();
		$("#resourceCode").hide();
		$("#classMaterials").hide();
		$("#utility").hide();
		$("#interview").show();
		$("#resourceTypeId5").val(value);
		return;
	}
	$("#book").hide();
	$("#resourceCode").hide();
	$("#classMaterials").hide();
	$("#utility").hide();
	$("#interview").hide();
}

//一加载就显示版本号
function showEditiontName() {
	$.post("/Examination2.0/course_edition.action",
		function(obj) {
						$("#version").html("<option value='0'>--请选择--</option>");
						for (var i = 0; i < obj.length; i++) {
							$("#version").append(
									"<option value='" + obj[i].id+ "'>"
											+ obj[i].editionName
											+ "</option>");
						}
						$("#version").get(0).selectedIndex = 0;
	});
}
//更改版本
function changeVersion(){
	semester=$('#chapter_semester').val();
	version=$('#version').val();
	createSubjectOption(version,semester);
}
//更改学期
function cretsubject() {
	semester=$('#chapter_semester').val();
	version = $('#version').val();
	createSubjectOption(version, semester);
}
//加载课程
function createSubjectOption(version, semester) {
	$.ajaxSettings.async = false;
	$
			.ajax({
				url : "/Examination2.0/initData_subject.action",
				type : "post",
				data : {
					"editionId" : version,
					"semester" : semester
				},
				datatype : "json",
				success : function(data) {
					var examineeClassList =data;
					var optionstr = "<option value='0'>--请选择--</option>";
					if (semester == null || semester == "") {
						$("#subject").html(optionstr);
						return;
					}
					if (examineeClassList.responseCode != 1) {
						for ( var i = 0,len=examineeClassList.obj.length; i <len ; i++) {
							optionstr += "<option value='"+examineeClassList.obj[i].id+"'>"
									+ examineeClassList.obj[i].subjectName
									+ "</option>";
						}
					} 
					$("#subject").html(optionstr);
				}
			});
}

</script>
	<div class="find_table_slelct" style="text-align: center;margin-left: 25%;width: 50%;margin-top: 25px"><br/>请选择上传类型 :
			<select name='resourcetype' class='select2' id='resourcetype' onChange="showExamineeNames(this.value)">
							<option value="0" >--请选择--</option>
	</select><br/></div>
		<!-- 	<hr style="border: 1px dashed blue;" /> -->


<div id="book" style="display:none; background-color: #F3F3F3;text-align: center;margin-left: 15%;width: 80%;;border: 1;border-radius:15px ">
	<h3>书籍上传</h3>
	<form  action="ResourceTypeAction_uploadFile.action" target="target" method="post" enctype="multipart/form-data" onsubmit="return toVaild1()">
	<input type="hidden" name="resourceTypeId" id="resourceTypeId1">
	<table style="text-align: center;width: 100%">
		<tr height="35px">
			<td width="33%" style="text-align: right;">类别:&nbsp;&nbsp;&nbsp;</td>
			<td style="text-align: left;" colspan="2"><select id="category" class='select2' name='cid'>
							<option value="0">--请选择--</option></select>  <span class="prompt">*</span></td>
		</tr>
		<tr height="35px">
			<td width="33%" style="text-align: center;" >ISBN: <input type="text" name="isbn" id="isbn"/><span class="prompt">*</span> </td>
			<td width="33%" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;书名: <input type="text" name="title" id="title"/> <span class="prompt">*</span> </td>
			<td width="33%" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价格: $<input type="text" name="price" id="price"/>  </td>
		
		</tr>
		<tr height="35px">
			<td width="33%" style="text-align: center;">作者: <input type="text" name="author" id="author"/> </td>
			<td width="33%" style="text-align: center;">书籍图片: <input type="file" name="imagesUrl" id="imagesUrl" accept="image/*"/> </td>
			<td width="33%" style="text-align: center;">书籍PDF:<input type="file" name="pdfsUrl" id="pdfsUrl1"/> <span class="prompt">*</span></td>
		</tr>
		<tr height="35px">
			<td width="33%" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;书籍源码:<input type="file" name="codesUrl" id="codesUrl1"/> </td>
			<td width="33%" ><input height="35px" type="submit" value="上传" class="inp_L1" onMouseOver="this.className='inp_L2'" onMouseOut="this.className='inp_L1'"> </td>
		</tr>
		<tr>
		<td colspan="3" style="text-align: left;"><br>书籍描述:</br><textarea rows="30" cols="50" id="editor01" name="description"></textarea></td></tr>
	</table>
 	</form>
 	<iframe name="target" id="target" frameborder="0" width="0" height="0"></iframe>
</div>


<div id="resourceCode" style="display:none; background-color: #F3F3F3;text-align: center;margin-left: 15%;width: 80%;;border: 1;border-radius:15px ">
	<h3>源码上传</h3>
	<form  action="ResourceTypeAction_uploadFile.action" target="target" method="post" enctype="multipart/form-data" onsubmit="return toVaild2()">
	<input type="hidden" name="resourceTypeId" id="resourceTypeId2">
	<table style="text-align: center;width: 100%">
		<tr height="35px">
			<td width="30%" style="text-align: left;">名称: <input type="text" name="title" id="title2"/> <span class="prompt">*</span> </td>
			<td width="40%" style="text-align: left;">资料上传:<input type="file" name="pdfsUrl" id="pdfsUrl2"/> <span class="prompt">*</span></td>
			<td width="30%" ><input height="35px" type="submit" value="上传" class="inp_L1" onMouseOver="this.className='inp_L2'" onMouseOut="this.className='inp_L1'"> </td>
		</tr>
		<tr>
		<td colspan="3" style="text-align: left;"><br>源码描述: </br><textarea rows="30" cols="50" id="editor02" name="description"></textarea></td></tr>
	</table>
 	</form>
 	<iframe name="target" id="target" frameborder="0" width="0" height="0"></iframe>
</div>


<div id="classMaterials" style=" display:none;  background-color: #F3F3F3;text-align: center;margin-left: 15%;width: 80%;;border: 1;border-radius:15px ">
	<h3>课堂资料上传</h3>
	<form  action="ResourceTypeAction_uploadFile.action" target="target" method="post" enctype="multipart/form-data" onsubmit="return toVaild3()">
	<input type="hidden" name="resourceTypeId" id="resourceTypeId3">
	<table style="text-align: center;width: 100%">
		<tr height="35px">
			<td width="30%" style="text-align: left;">版本：<select id="version" name="edtionId" onChange="changeVersion()"><option value="0">--请选择--</option></select><span class="prompt">*</span></td>
			<td width="40%" style="text-align: left;">学期：<select id="chapter_semester" name="semeter" onchange="cretsubject()"><option value="0">--请选择--</option>
								<option value="S1">S1</option>
								<option value="S2">S2</option>
								<option value="S3">S3</option>
						</select> <span class="prompt">*</span></td>
			<td width="30%" >科目名：<select id="subject" name="subjectid"  ><option
									value="0">--请选择--</option>
						</select> <span class="prompt">*</span></td>
			
		</tr>
		 <tr height="35px">
			<td width="30%" style="text-align: left;">名称: <input type="text" name="title" id="title3"/> <span class="prompt">*</span> </td>
			<td width="40%" style="text-align: left;">课堂资料选择:<input type="file" name="pdfsUrl" id="pdfsUrl3"/> <span class="prompt">*</span></td>
			<td width="30%" ><input height="35px" type="submit" value="上传" class="inp_L1" onMouseOver="this.className='inp_L2'" onMouseOut="this.className='inp_L1'"> </td>
		</tr>
		<tr height="35px">
		</tr>
		<tr> 
		<td colspan="4" style="text-align: left;"><br>课堂资料描述:</br><textarea rows="30" cols="50" id="editor03" name="description"></textarea></td></tr>
	</table>
 	</form>
 	<iframe name="target" id="target" frameborder="0" width="0" height="0"></iframe>
</div>


<div id="utility" style="display:none;  background-color: #F3F3F3;text-align: center;margin-left: 15%;width: 80%;;border: 1;border-radius:15px ">
	<h3>工具类上传</h3>
	<form  action="ResourceTypeAction_uploadFile.action" target="target" method="post" enctype="multipart/form-data" onsubmit="return toVaild4()">
	<input type="hidden" name="resourceTypeId" id="resourceTypeId4">
	<table style="text-align: center;width: 100%">
		<tr height="35px">
			<td width="30%" style="text-align: left;">名称: <input type="text" name="title" id="title4"/> <span class="prompt">*</span> </td>
			<td width="40%" style="text-align: left;">工具类资料上传:<input type="file" name="pdfsUrl" id="pdfsUrl4"/> <span class="prompt">*</span></td>
			<td width="30%" ><input height="35px" type="submit" value="上传" class="inp_L1" onMouseOver="this.className='inp_L2'" onMouseOut="this.className='inp_L1'"> </td>
		
		</tr>
		<tr>
		<td colspan="3" style="text-align: left;"><br>工具类描述:</br><textarea rows="30" cols="50" id="editor04" name="description"></textarea></td></tr>
	</table>
 	</form>
 	<iframe name="target" id="target" frameborder="0" width="0" height="0"></iframe>
</div>
<div id="interview" style="display:none;  background-color: #F3F3F3;text-align: center;margin-left: 15%;width: 80%;;border: 1;border-radius:15px ">
	<h3>面试资料上传</h3>
	<form  action="ResourceTypeAction_uploadFile.action" target="target" method="post" enctype="multipart/form-data" onsubmit="return toVaild5()">
	<input type="hidden" name="resourceTypeId" id="resourceTypeId5" />
	<table style="text-align: center;width: 100%">
		<tr height="35px">
			<td width="30%" style="text-align: left;">名称: <input type="text" name="title" id="title5"/> <span class="prompt">*</span> </td>
			<td width="40%" style="text-align: left;">面试资料上传:<input type="file" name="pdfsUrl" id="pdfsUrl5"/> <span class="prompt">*</span></td>
			<td width="30%" ><input height="35px" type="submit" value="上传" class="inp_L1" onMouseOver="this.className='inp_L2'" onMouseOut="this.className='inp_L1'"> </td>
		
		</tr>
		<tr>
		<td colspan="3" style="text-align: left;"><br>面试资料描述: </br><textarea rows="30" cols="50" id="editor05" name="description"></textarea></td></tr>
	</table>
 	</form>
 	<iframe name="target" id="target" frameborder="0" width="0" height="0"></iframe>
</div>
<input type="hidden" id="bool" value="1">



</body>
</html>
<script>
//通过这个js实现无刷新文件上传
    var iframe = document.getElementById("target");
    var uploadCallback = function () {
        var data=iframe.contentDocument.body.innerHTML;
       // "<pre style="word-wrap: break-word; white-space: pre-wrap;">{"obj":"","responseCode":0}</pre>"
       var start=data.indexOf("responseCode");//
        var num=data.substring(start+14,start+15);//获取响应码
        if(num==0||num=="0"){
        	$("#imagesUrl").val("");
        	$("#pdfsUrl1").val("");
        	$("#isbn").val("");
        	$("#title").val("");
        	$("#price").val("");
        	$("#author").val("");
        	$("#codesUrl1").val("");
        	
        	$("#pdfsUrl2").val("");
        	$("#title2").val("");
        	
        	$("#pdfsUrl3").val("");
        	$("#title3").val("");
        	
        	$("#pdfsUrl4").val("");
        	$("#title4").val("");
        	
        	$("#pdfsUrl5").val("");
        	$("#title5").val("");
        	
        	editr1.setData("");
        	editr2.setData("");
        	editr3.setData("");
        	editr4.setData("");
        	editr5.setData("");
			/* if($("#bool").val()=="1"){
				$("#bool").val("2");
				return;
			} */
			alert("成功");
        	
        }else{
        	alert("失败")
        }
    };
    iframe.onload = uploadCallback;
</script>
