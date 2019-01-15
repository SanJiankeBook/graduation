//建立requesthttp对象，这样只需导入这个js就可以了
var xmlRequest;

function createXml()
{
  var ajaxObject;
  try
  {
    request = new XMLHttpRequest();
  }
  catch (trymicrosoft)
  {
    try
    {
      request = new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch (othermicrosoft)
    {
      try
      {
        request = new ActiveXObject("Microsoft.XMLHTTP");
      }
      catch (failed)
      {
        request = false;
      }
    }
  }
  return request;
}

//方法一：后台验证输入的用户名, 密码, 班级是否正确spanObject表示显示信息的地方，也可以加txtObject表示要验证的文本框
function sendRequest(url, spanObject){
  xmlRequest = createXml();
  //将请求发送到Servlet中去
  url = encodeURI(encodeURI(url));
  xmlRequest.open("POST", url, true);
  //判断服务器的状态是否为正常的
  xmlRequest.onreadystatechange = function()
  {
    if(xmlRequest.readyState == 4)
    {
      if(xmlRequest.status == 200)
      {
        var strText = xmlRequest.responseText;
        //不为0表示有错误提示
        if (strText!='0') {
        	spanObject.innerHTML=strText;
        }
        else {
        	spanObject.innerHTML="√";
        } 
      }
    } 
    else {
    	spanObject.innerHTML="请稍候...";
    }
  };
  xmlRequest.send(null);
}

//方法二：先调用sendRequest(url,回调函数的名称)，再自己在外面重写callBack()方法
function httpRequest(url,callBack){
  xmlRequest = createXml();
  //将请求发送到Servlet中去
  url = encodeURI(encodeURI(url));
  xmlRequest.open("POST", url, false);
  //判断服务器的状态是否为正常的
  xmlRequest.onreadystatechange = callBack;
  xmlRequest.send(null);
}

//模板，在外面重写
//function callBack()
//{
//    if(xmlRequest.readyState == 4)
//    {
//      if(xmlRequest.status == 200)
//      {
//        var strText = xmlRequest.responseText;
//        //不为0表示有错误提示
//        return strText;
//      }
//    }
//    else {
//    	//显示稍候之类的
//    } 
//}