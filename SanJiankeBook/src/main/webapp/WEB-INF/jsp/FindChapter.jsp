<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>小说章节</title>
</head>
<body>
	<fieldset>
		<legend>章节详情</legend>
		<div id="shownvoel_check_info">
		<c:forEach items="${novelChapter }" var="novelChapter">
		<p>
			<div id="info"><label>章节编号：</label><input type="text" id="info1" name="cid" value="${novelChapter.cid }" style="border:0;background:transparent;" readOnly="true"/> </div>
		</p>
		<p>
			<div id="info"><label>小说编号：</label><input type="text" id="info2" name="nid" value="${novelChapter.nid }" style="border:0;background:transparent;" readOnly="true"/></div>
		</p>
		<p>
			<div id="info"><label>小说类型：</label><input type="text" id="info4" name="tname" value="${novelChapter.tname }" style="border:0;background:transparent;" readOnly="true"/></div>
		</p>
		<p>
			<div id="info"><label>小说名：</label><input type="text" id="info5" name="nname" value="${novelChapter.nname }" style="border:0;background:transparent;" readOnly="true"/></div>
		</p>
		<p>
			<div id="info"><label>小说章节名：</label><input type="text" id="info6" name="cname" value="${novelChapter.cname }" style="border:0;background:transparent;" readOnly="true"/> </div>
		</p>
		<p>
			<div id="info"><label>审核状态：</label><input type="text" id="info7" name="standby_1" value="${novelChapter.standby_1 }" style="border:0;background:transparent;" readOnly="true"/> </div>
		</p>
		<!-- 小说章节地址 -->
		小说章节地址：
		<p>
			<div id="info"><a href="${novelChapter.caddress } "><input type="text" id="info8" value="${novelChapter.caddress }" name="caddress" style="border:0;background:transparent;" readOnly="true"/></a></div>
		</p>
		</c:forEach>
	</div>
	</fieldset>
</body>
</html>