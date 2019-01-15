var xmlRequest;

function createXmlRequest() {
  var ajaxObject;
  try {
    request = new XMLHttpRequest();
  }
  catch (trymicrosoft) {
    try {
      request = new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch (othermicrosoft) {
      try {
        request = new ActiveXObject("Microsoft.XMLHTTP");
      }
      catch (failed) {
        request = false;
      }
    }
  }
  return request;
}

//sendPostRequest(url,参数，回调函数的名称)，再自己在外面重写callBack()方法，不用问号
function sendPostRequest(url,param,callBack){
  xmlRequest = createXmlRequest();
  //将请求发送到Servlet中去
  param = encodeURI(encodeURI(param));
  
  //alert(param);
  
  xmlRequest.open("POST", url, true);
  xmlRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  //判断服务器的状态是否为正常的
  xmlRequest.onreadystatechange = callBack;
  xmlRequest.send(param);
}

//转成全角
function parseSpecialChar(str) {
	var val = "";
  for(var i=0;i<str.length;i++){
    var ch = str.substring(i, i+1);
    if(ch=='+'){
      val += '＋';
    }
    else if(ch=='&') {
			val += '＆';    	
    }
    else if(ch=="."){
      val += "．";
    }
    else if(ch=="?") {
    	val += "？";
    }
    else{
      val += ch;
    }
  }
  return val;
	
}
//模板，在外面重写
//function callBack() {
//  if (xmlRequest.readyState == 4) {
//    if (xmlRequest.status == 200) {
//			不一定要返回东西       
//      var response = xmlRequest.responseText;
//    }
//    else {
//      alert("服务器出现错误，错误代码为:" + request.status);
//    }
//  } else {
//     "数据读取中……";
//  }
//}

//参数是form ID，返回一个包含所有参数的字符串查询对象，不包含url
function pairFormData(formId) {
  //定义数组
  var pairs = [];
  for (var i=0; i< formId.length; i++) {
    var e = formId.elements[i];
    if(e.tagName.toLowerCase()=="input") {
      //忽略
      if(e.type.toLowerCase()=="button")
        continue;
      if((e.type.toLowerCase()=="radio" || e.type.toLowerCase() == "checkbox") && e.checked ==false)
        continue;
    }
    //push函数方法是将新元素添加到一个数组中，并返回数组的新长度值，所有的&符号要进行处理换成ASCII码
    e.value = e.value.replace("&","＆");
    pairs.push(e.name+"="+e.value);
  }
  //join函数方法是返回字符串值，其中包含了连接到一起的数组的所有元素，元素由指定的分隔符分隔开来
  return pairs.join("&");
}

//后台验证输入信息，url为发送的地址,spanOjbect为显示信息的对象,txtObject为验证的文本框对象
function sendInput3(url,spanObject,txtObject){
  xmlRequest = createXmlRequest();
  //将请求发送到Servlet中去
  url = encodeURI(encodeURI(url));
  xmlRequest.open("POST",url,true);
  //判断服务器的状态是否为正常的
  xmlRequest.onreadystatechange = function()
  {
    if(xmlRequest.readyState == 4)
    {
      if(xmlRequest.status == 200)
      {
        var strText = xmlRequest.responseText;
        if(strText=="true"){
          spanObject.innerHTML=strText;
          txtObject.focus();
          txtObject.value="";
        }else{
          spanObject.innerHTML="";
        }
      }
	    else {
	      alert("Error: status code is " + request.status);
	    }
    }
    else {
    	spanObject.innerHTML="请稍候...";
    }
  }
  xmlRequest.send(null);
}

//后台验证输入信息，url为发送的地址,spanOjbect为显示信息的对象
function sendInput2(url,spanObject){
  xmlRequest = createXmlRequest();
  //将请求发送到Servlet中去
  url = encodeURI(encodeURI(url));
  xmlRequest.open("POST",url,true);
  //判断服务器的状态是否为正常的
  xmlRequest.onreadystatechange = function()
  {
    if(xmlRequest.readyState == 4)
    {
      if(xmlRequest.status == 200)
      {
        var strText = xmlRequest.responseText;
        spanObject.innerHTML=strText;
      }
	    else {
	      alert("Error: status code is " + request.status);
	    }
    }
    else {
    	spanObject.innerHTML="请稍候...";
    }
  }
  xmlRequest.send(null);
}

//模板：后台验证输入信息，url为发送的地址
function sendInput1(url){
  xmlRequest = createXmlRequest();
  //将请求发送到Servlet中去
  url = encodeURI(encodeURI(url));
  xmlRequest.open("POST",url,true);
  //判断服务器的状态是否为正常的
  xmlRequest.onreadystatechange = function()
  {
    if(xmlRequest.readyState == 4)
    {
      if(xmlRequest.status == 200)
      {
        var strText = xmlRequest.responseText;
      }
	    else {
	      alert("Error: status code is " + request.status);
	    }
    }
  }
  xmlRequest.send(null);
}

//  ------去掉字符串中前后的空格---------
function JTrim(str)
{
  var i = 0;
  var len = str.length;

  if(str == "")
  {
    return(str);
  }

  j = len -1;
  flagbegin = true;
  flagend = true;

  while((flagbegin == true) && (i< len))
  {
    if(str.charAt(i) == " ")
    {
      i = i + 1;
      flagbegin = true;
    }
    else
    {
      flagbegin = false;
    }
  }

  while((flagend == true) && (j >= 0))
  {
    if(str.charAt(j) == " ")
    {
      j = j - 1;
      flagend = true;
    }
    else
    {
      flagend = false;
    }
  }

  if(i > j)
  {
    return ("");
  }

  trimstr = str.substring(i, j+1);
  return trimstr;
}
//------判断浏览器---------
function getOs()
{
    var OsObject = "";
   if(navigator.userAgent.indexOf("MSIE")>0) {
        return "MSIE";
   }
   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
        return "Firefox";
   }
   if(isSafari=navigator.userAgent.indexOf("Safari")>0) {
        return "Safari";
   } 
   if(isCamino=navigator.userAgent.indexOf("Camino")>0){
        return "Camino";
   }
   if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){
        return "Gecko";
   }
  
} 
//------火狐---------
function getTextFieldSelection(e){ 
    //var oEvent=arguments.callee.caller.arguments[0]; 
    if(e.selectionStart != undefined && e.selectionEnd != undefined) 
    	
        return e.value.substring(e.selectionStart,e.selectionEnd); 
    else return ""; 
} 

