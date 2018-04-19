<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<%
	String path = request.getContextPath();
	//					http				://		localhost			:	8081				/SpringMvc_Book/
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>书籍信息修改</title>
<base href="<%=basePath%>">	
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
</head>

	<script type="text/javascript">
	$(function(){
		$.ajax({//显示书本类型
			url:"ShowType",
			type:"post",
			dataType:"JSON",
			data:{'nid' :$("#nid").val()},
			success:function( data ){
				//var art=document.getElementById("usex");
				$("#t").html("");
				$("#tname").html("");
				var value="";
				 $.each(data, function(index, element) {
					 value+="<option value='"+element.tid+"'>"+element.tname+"</option>";
		            });
				 $("#t").html(value);
				 $("#tid").html(value);
			}
		});
		
		$.ajax({//显示小说状态
			url:"Shownstatus",
			type:"post",
			dataType:"JSON",
			data: {'nid' :$("#nid").val()},
			success:function( data ){
				$("#nstatus").append("<option value="+data+" selected = 'selected'>"+data+"</option>");
			}
		});
	});	
	</script>

<body>
	<h1>书籍信息编辑</h1>
	<form action="saveedit" method="post" enctype="multipart/form-data">
	<input id="nid" name="nid" value="${novel[0].nid}" type="hidden"/>
	书籍名：<input type="text" name="nname" value="${novel[0].nname}"><br />
	书籍类型：<select id="t"  name="t" disabled="disabled">
	</select>
	<div id="div" style="display: none" >
	<select id="tid"  name="tid" >
	</select></div>
	<br />
	<hr />
	 书籍封面: <img id="xmTanImg" name="npicture" height="200" width="200" style="margin-left:300px" src="${novel[0].npicture}"/>
	 书籍封面图片选取：<input type="file" name="pdfsUrl" id="xdaTanFileImg" onchange="xmTanUploadImg(this)" />
                <input type="file" name="pdfsUrl" id="xdaTanFileImg" onchange="xmTanUploadImg(this)"  style="display:none"/>
                <input type="button" value="隐藏图片" onclick="document.getElementById('xmTanImg').style.display = 'none';"/>
                <input type="button" value="显示图片" onclick="document.getElementById('xmTanImg').style.display = 'block';"/>
            
        </div>
        <hr />
        <script type="text/javascript">            
            //判断浏览器是否支持FileReader接口
            if (typeof FileReader == 'undefined') {
                document.getElementById("xmTanDiv").InnerHTML = "<h1>当前浏览器不支持FileReader接口</h1>";
                //使选择控件不可操作
                document.getElementById("xdaTanFileImg").setAttribute("disabled", "disabled");
            }

            //选择图片，马上预览
            function xmTanUploadImg(obj) {
                var file = obj.files[0];
                
                console.log(obj);console.log(file);
                console.log("file.size = " + file.size);  //file.size 单位为byte

                var reader = new FileReader();

                //读取文件过程方法
                reader.onloadstart = function (e) {
                    console.log("开始读取....");
                }
                reader.onprogress = function (e) {
                    console.log("正在读取中....");
                }
                reader.onabort = function (e) {
                    console.log("中断读取....");
                }
                reader.onerror = function (e) {
                    console.log("读取异常....");
                }
                reader.onload = function (e) {
                    console.log("成功读取....");

                    var img = document.getElementById("xmTanImg");
                    img.src = e.target.result;
                    //或者 img.src = this.result;  //e.target == this
                }

                reader.readAsDataURL(file)
            }
        </script>
		小说状态：<select id="nstatus" name="nstatus">		
			<option value="完结">完结</option>
			<option value="未完结">待更新</option>
		</select><br/>
		<input type="submit" value="保存" />
		<input type="reset" value="重置 " /> 
		</form>

</body>
</html>