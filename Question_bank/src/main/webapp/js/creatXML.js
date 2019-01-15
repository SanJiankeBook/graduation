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

//从右往左去空格
function rtrim(stringObj)
{
  while (stringObj.charCodeAt(stringObj.length - 1) == 32)
  {
    stringObj = stringObj.substring(0, stringObj.length - 1);
  }
  return stringObj;
}

//从左往右去空格
function ltrim(stringObj)
{
  while (stringObj.charCodeAt(0) == 32)
  {
    stringObj = stringObj.substring(1, stringObj.length);
  }
  return stringObj;
}
//去字符串左右两边的空格(中间的空格不能去)
function trim(stringObj)
{
  return(ltrim(rtrim(stringObj)));
}



//输完后, 输入的类型不对, 自动去除最后一个不是数字的字符
function inputIsNaN(val){
  if(isNaN(val.value)){
    val.value = val.value.substr(0, val.value.length - 1);
    val.focus();
  }
}

//输完后, 失去蕉点时, 输入的类型不对, 自动清空
function valueIsNaN(val){
  if(isNaN(val.value)){
    val.value="";
    val.focus();
  }
}

//判断是否提交下面转页的表单
function isSubmitForm1(){
  try{
    var numPagev = document.getElementById("pageNum").value;
    var sumPagev = document.getElementById("sumPage").value;
    if(numPagev=="" || sumPagev==""){
      return false;
    }
    if(parseInt(numPagev)<=parseInt(sumPagev) && numPagev>0){
      form1.submit();
    }
  }
  catch(ex){
    alert(numPage);
  }
}
//对 "&" 字符串进行转换成 "与符号"
function htmlCode(str){
  var val = "";
  for(var i=0; i<str.length; i++){
    var ch = str.substring(i, i+1);
    if(ch=="&"){
      val += "与符号";
    }
    //      else if(ch=="."){
    //        val += "点符号";
    //      }
    else{
      val += ch;
    }
  }
  return val;
}

var i=0;
//倒计时，剩余时间

//获得系统当前时间
function disptime(){
  var now=new Date();
  var minute=now.getMinutes();
  var second=now.getSeconds();

  if(minute<10){
    minute="0"+minute;
  }
  if(second<10)
  {
    second="0"+second;
  }
  nowtime.innerHTML=now.getHours()+":"+minute+":"+second;
  //设置时间跳动
  var myTime=setTimeout("disptime()", 1000);
}

//点击交卷退出页面
function openwindow(){
  var anwer=confirm("确定交卷吗?");
  if(anwer==true)
  {
    window.close();
  }
}

//屏蔽鼠标右键、Ctrl+n、shift+F10、F5刷新、退格键、屏蔽Alt+F4
function KeyDown(){
  if ((window.event.altKey)&&((window.event.keyCode==37)|| //屏蔽 Alt+ 方向键 ←
  (window.event.keyCode==39))){
    //屏蔽 Alt+ 方向键 →
    alert("不准你使用ALT+方向键前进或后退网页！");
    event.returnValue=false;
  }

  if ((event.keyCode==8) || //屏蔽退格删除键
  (event.keyCode==116)|| //屏蔽 F5 刷新键
  (event.ctrlKey && event.keyCode==82)){
    //Ctrl + R
    event.keyCode=0;
    event.returnValue=false;
  }
  if ((event.ctrlKey)&&(event.keyCode==78)) //屏蔽 Ctrl+n
     event.returnValue=false;
  if ((event.shiftKey)&&(event.keyCode==121)) //屏蔽 shift+F10
      event.returnValue=false;
  if (window.event.srcElement.tagName == "A" && window.event.shiftKey)
    window.event.returnValue = false;
  //屏蔽 shift 加鼠标左键新开一网页
  if ((window.event.altKey)&&(window.event.keyCode==115)){
    //屏蔽Alt+F4
    window.showModelessDialog("about:blank", "", "dialogWidth:1px;dialogheight:1px");
    return false;
  }
}

//返回字符串的长度，一个汉字占两个字节
function strlength(str){
  return str.replace(/[^\x00-\xff]/gi, 'xx').length;
}