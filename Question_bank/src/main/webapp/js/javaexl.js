function uploadExcel() {
	var location = $("input[name='file']").val();
	var point = location.lastIndexOf(".");
	var type = location.substr(point);
	if (type != ".xls" && type != ".xlsx") {
		alert("请确认您要上传的文件类型！！！");
		return;
	}
	var formData = new FormData(document.getElementById('myExcelForm'));
	$.ajax({
		type : "POST",
		url : "/Examination2.0/uploadExcel_uploadExcel.action",
		data : formData,
		processData : false,
		contentType : false,
		dataType : "json",
		success : function(data) {
			alert(data.obj);
		}
	})
}