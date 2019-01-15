<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%
	String path=request.getContextPath();  //douban
								//http					localhost				8080               douban
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<base href="<%=basePath %>">
<head>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作家创作区</title>
</head>
<body style="text-align:center; margin:auto auto">
	<input type="text" id="nid"  value="${novel[0].nid}" style="display: none"/> 
	章节名：<input type="text" id="cname"><br/><hr/><br/><br/>
	<script type="text/javascript" id="editor" style="width:98%;height:300px;"></script>
	<input type="button" onclick="addGoods()" value="上传章节"/> 
	
	<script type="text/javascript">
	var ue=UE.getEditor('editor');
	function addGoods(){
		var nid=$("#nid").val();
		var cname=$("#cname").val();
		var des=ue.getContent();
		//alert(des);
			$.ajax({//插入章节
				url:"insertNovlChapter",
				type:"post",
				data:{"nid":nid,"cname":cname,"des":des},
				dataType:"JSON",
				success:function( data ){
					if(data==1){
						alert("上传成功，等待管理员审核");
					}else{
						alert("上传失败，请与管理员联系");
					}				
				}
			});
	}
	</script>
</body>

</html>