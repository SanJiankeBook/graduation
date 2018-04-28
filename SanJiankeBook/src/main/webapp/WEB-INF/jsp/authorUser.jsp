<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/post.js"></script>
<script type="text/javascript" src="js/wap.js"></script>
<script type="text/javascript" src="js/xiaoshuo.js"></script>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作家注册专区</title>
</head>
<body style="text-align:center; margin:auto auto">
	<h1>IT类专业书籍管理作家注册专区</h1>
	<form id="form1" action="#" method="post" style="margin-top:100px">
		作家笔名&nbsp;&nbsp;&nbsp;<input type="text" id="uname" name="uname"> <label id="uname_1"></label></label><br/><br/>
		作家真实姓名<input type="text" name="aname" id="aname"> <label id="aname_1"></label><br/><br/>
		作家年龄&nbsp;&nbsp;<input type="text" name="aage" id="aage"> <label id="aage_1"></label><br/><br/>
		作家身份证号<input type="text" name="acard" id="acard"> <label id="acard_1"></label><br/><br/>
		<input type="reset" value="重置">
		<input type="button" value="注册" onclick="registauthor()"/>
	</form>
	<script type="text/javascript">  
	function registauthor(){
		var uname = $("#uname").val(); 
        var aname = $("#aname").val();  
        var aage = $("#aage").val();  
        var acard = $("#acard").val();   
        $("#uname_1").text(""); 
        $("#aname_1").text(""); 
        $("#aage_1").text(""); 
        $("#acard_1").text(""); 
        if(uname.length == 0)  
        {  
            $("#uname_1").text("作家笔名不能不能为空！");  
            $("#uname_1").css({"color":"red"});  
           return false;
        }  
        if(aname.length == 0)  
        {  
            $("#aname_1").text("作家名字不能不能为空！");  
            $("#aname_1").css({"color":"red"});  
           return false;
        }  
        if(aage.length == 0)  
        {  
            $("#aage_1").text("年龄不能不能为空！");  
            $("#aage_1").css({"color":"red"});  
           return false;
        }else{
        	 if(!isNaN(aage)){
        	       
        	    }else{
        	    	$("#aage_1").text("年龄非法");  
        	    	 $("#aage_1").css({"color":"red"});  
        	        return false;
        	    }
        }  
        if(acard.length == 0)  
        {  
            $("#acard_1").text("身份证号不能不能为空！");  
            $("#acard_1").css({"color":"red"});  
           return false;
        }else{
        	var sId=acard;
        	var bool=true;
        	var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"} 
        		 var iSum=0 ;
        		 var info="" ;
        		 if(!/^\d{17}(\d|x)$/i.test(sId)){ $("#acard_1").text("你输入的身份证长度或格式错误！");$("#acard_1").css({"color":"red"});    return false; }
        		 sId=sId.replace(/x$/i,"a");
        		 if(aCity[parseInt(sId.substr(0,2))]==null){ $("#acard_1").text("你的身份证地区非法");$("#acard_1").css({"color":"red"});   return false;}
        		 sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
        		 var d=new Date(sBirthday.replace(/-/g,"/")) ;
        		 if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){$("#acard_1").text("身份证上的出生日期非法"); $("#acard_1").css({"color":"red"});  return;}
        		 for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
        		 if(iSum%11!=1){
        			  $("#acard_1").text("你的身份证号非法");$("#acard_1").css({"color":"red"});  
        			  return false;
        		 }
        		 //aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女");//此次还可以判断出输入的身份证号的人性别
        		
        }  
        $.ajax({
			url:"registauthorUser",
			type:"post",
			data:{"uname":uname,"aname":aname,"aage":aage,"acard":acard},
			dataType:"JSON",
			success:function( data ){
				if(data=="1"){
					alert('注册成功...');
					window.location="toindex_zpd";
				}else{
					alert("注册失败");
					window.location="toauthor";
				}
				
				
			}
		});
	} 
</script> 
</body>
</html>