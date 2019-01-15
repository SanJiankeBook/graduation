<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%
	String path=request.getContextPath();  //douban
								//http					localhost				8080               douban
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<base href="<%=basePath %>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>作品创作区</title>
	<link rel="stylesheet" type="text/css" href="easyui/css/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="easyui/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/index.js"></script>
	<script type="text/javascript" src="easyui/js/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>
 
</head>
<body class="easyui-layout" style="text-align:center; margin:auto auto">
		<h1>创作之旅开始</h1><br/><hr/>
		<form action="InsertNovel" method="post" enctype="multipart/form-data">
		类型：<select id="tid"  name="tid">
		</select><br/>
		书籍名字：<input type="text" name="nname" id="nname"/><br/>
		书籍描述：<textarea cols=60 rows=4 name="ndescription" id="ndescription" style="font-size:9pt;line-height:100%"></textarea>
		<!-- style="border:2px dashed red;" -->
		<div >
            <p>
                书籍封面图片选取：<input type="file" name="pdfsUrl" id="xdaTanFileImg" onchange="xmTanUploadImg(this)" />
                <input type="file" name="pdfsUrl" id="xdaTanFileImg" onchange="xmTanUploadImg(this)"  style="display:none"/>
                <input type="button" value="隐藏图片" onclick="document.getElementById('xmTanImg').style.display = 'none';"/>
                <input type="button" value="显示图片" onclick="document.getElementById('xmTanImg').style.display = 'block';"/>
            </p>
            <img id="xmTanImg"  height="200" width="200" style="margin-left:300px"/>
            <div id="xmTanDiv"></div>
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
		<input type="submit" value="开始创作"/>   
	</form>
	
	
	<script type="text/javascript">
	$(function(){
		$.ajax({//显示书本类型
			url:"showNovelTypes",
			type:"post",
			data:"",
			dataType:"JSON",
			success:function( data ){
				var art=document.getElementById("usex");
				$("#tid").html("");
				var value="";
				 $.each(data, function(index, element) {
					 value+="<option value='"+element.tid+"'>"+element.tname+"</option>";
		            });
				 $("#tid").html(value);
			}
		});
	});
	//添加书籍信息及章节
	function addGoods(){
		//var npicture= $("#xmTanImg").html();
		alert($("#xdaTanFileImg").val());
	/*	var des=ue.getContent();
		var tid=$("#tid").val();
		var nname=$("#nname").val();
		
		var ndescription=$("#ndescription").val();
			 $.ajax({//存入书本信息及章节信息
				url:"InsertNovel",
				type:"post",
				data:"des="+des+"&tid="+$("#tid")+"&",
				data:{"des":des,"tid":tid,"ndescription":ndescription,"nname":nname},
				dataType:"JSON",
				success:function( data ){
				}
			});
			*/
	}
	
	</script>
</body>
</html>