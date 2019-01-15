var val;
$(function(){
	var result=getQueryString();
	var arr=result.toString().split(",,");
	var id=arr[0].split("=")[1];
	var semester=arr[2].split("=")[1];
	var createDate=arr[4].split("=")[1];
	var endDat=arr[5].split("=")[1];
	var className=arr[6].split("=")[1];
	var remark=arr[1].split("=")[1];
	semester=semester.substring(1,2);
	//alert(semester);
	$("#idInfo").val(id);
	$("#semesterS"+semester).attr("checked",true);
	$("#className").val(className);
	$("#openTime").val(createDate);
	$("#overTime").val(endDat);
	$("#textarea").val(decodeURIComponent(remark));
	
	  $("#semesterS1").click(function(){
		 	val=$('input:radio[name="semester"]:checked').val();
		 });
	  
	  $("#semesterS2").click(function(){
		 	val=$('input:radio[name="semester"]:checked').val();
		 });
	  
	  $("#semesterS3").click(function(){
		 	val=$('input:radio[name="semester"]:checked').val();
		 });
	
});
   
   
   function getQueryString(){

     var result = location.search.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+","g")); 

     if(result == null){

         return "";

     }

     for(var i = 0; i < result.length; i++){

         result[i] = result[i].substring(1);

     }

     return result;

}
   
   function submitForm(){
	var semester=$('input:radio[name="semester"]:checked').val();
	var id=$("#idInfo").val();
	var className=$("#className").val();
	var createDate=$("#openTime").val();
	var overDate=$("#overTime").val();
	var remark=$("#textarea").val();
	
	$.post("/Examination2.0/examineeclass_updateClass.action",{id:id,semester:semester,className:className,
		createDate:createDate,overDate:overDate,remark:remark},
			function(obj){
			if(obj.responseCode==0){
				alert("班级信息更新成功");
				javascript:history.go(-1);
			}
			else if(obj.responseCode==1){
				$("#strPromp").val(obj.errorMessage);
			}
	});
	
   }