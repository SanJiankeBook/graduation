function setCookie(c_name,value,expiredays){
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+365)
    document.cookie=c_name+ "=" +escape(value)+";expires="+exdate.toGMTString()+";path=/";
}

function getCookie(c_name){
    if (document.cookie.length>0){
        c_start=document.cookie.indexOf(c_name + "=");
        if (c_start!=-1){ 
            c_start=c_start + c_name.length+1;
            c_end=document.cookie.indexOf(";",c_start);
            if (c_end==-1) c_end=document.cookie.length;
            return unescape(document.cookie.substring(c_start,c_end));
        } 
    }
    return "";
}

function delCookie(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    document.cookie= name + "=;expires="+exp.toGMTString();
}
var bookUserName=getCookie("member_username");
function login(){

//document.writeln("<div style=\"display:none\" >");
//document.writeln("<script src=\"http://s11.cnzz.com/stat.php?id=1261165914&web_id=1261165914\" language=\"JavaScript\"></script>");
//document.writeln("</div>");
//document.writeln("<div class=\"ywtop\"><div class=\"ywtop_con\"><div class=\"ywtop_sethome\"><a onClick=\"this.style.behavior='url(#default#homepage)';this.setHomePage('http://www.bixia.org');\" href=\"#\">将本站设为首页</a></div>");
//document.writeln("		<div class=\"ywtop_addfavorite\"><a href=\"javascript:window.external.addFavorite(\'http://www.bixia.org\',\'三剑客文学\')\">三剑客文学</a></div>");
//document.write('<div class="nri">');
if(bookUserName != ''){
  document.write('Hi,<a href="/userdetail.php" target="_top">'+bookUserName+'</a>&nbsp;&nbsp;<a href="/modules/article/bookcase.php" target="_top">我的书架</a>');
  document.write(' | <a href="/userdetail.php" target="_top">查看资料</a> | <a href="/logout.php" target="_top">退出登录</a>&nbsp;');
}
else{
  var jumpurl="";
  if(location.href.indexOf("jumpurl") == -1){
    jumpurl=location.href;
  }
//  document.write('<form name="mylogin" id="mylogin" method="post" action="/Login.php?action=login&usecookie=1&jumpurl="'+jumpurl+'>');
//  document.write('<div class="cc"><div class="txt">账号：</div><div class="inp"><input type="text" name="username" id="username" /></div></div>');
//  document.write('<div class="cc"><div class="txt">密码：</div><div class="inp"><input type="password" name="password" id="password" /></div></div>');
//  document.write('<div class="frii"><input type="submit" class="int" value=" " /></div><div class="ccc"><div class="txtt"></div><div class="txtt"><a href="/register.php">用户注册</a></div></div></form>');
  }
// document.write('</div></div></div>');
}
function search(){
	document.writeln("<script type=\"text/javascript\">(function(){document.write(unescape(\'%3Cdiv id=\"bdcs\"%3E%3C/div%3E\'));var bdcs = document.createElement(\'script\');bdcs.type = \'text/javascript\';bdcs.async = true;bdcs.src = \'http://znsv.baidu.com/customer_search/api/js?sid=3677118700255927857\' + \'&plate_url=\' + encodeURIComponent(window.location.href) + \'&t=\' + Math.ceil(new Date()/3600000);var s = document.getElementsByTagName(\'script\')[0];s.parentNode.insertBefore(bdcs, s);})();</script>");
}
function banner(){
//	document.writeln("<!-- Baidu Button BEGIN -->");
//	document.writeln("<div id=\"bdshare\" class=\"bdshare_t bds_tools_32 get-codes-bdshare\">");
//	document.writeln("<a class=\"bds_tsina\"><\/a>");
//	document.writeln("<a class=\"bds_renren\"><\/a>");
//	document.writeln("<span class=\"bds_more\"><\/span>");
//	document.writeln("<a class=\"shareCount\"><\/a>");
//	document.writeln("<\/div>");
//	document.writeln("<script type=\"text\/javascript\" id=\"bdshare_js\" data=\"type=tools&amp;uid=0\" ><\/script>");
//	document.writeln("<script type=\"text\/javascript\" id=\"bdshell_js\"><\/script>");
//	document.writeln("<script type=\"text\/javascript\">");
//	document.writeln("document.getElementById(\"bdshell_js\").src = \"http:\/\/bdimg.share.baidu.com\/static\/js\/shell_v2.js?cdnversion=\" + Math.ceil(new Date()\/3600000)");
//	document.writeln("<\/script>");
//	document.writeln("<!-- Baidu Button END -->")
}
function list1(){
}
function read1(){
}
function read2(){
}
function read3(){}

function read4(){}

function chaptererror(){
	document.writeln("<div align=\"center\" ><a href=\"javascript:postError();\" style=\"text-align:center;color:red;\">章节错误,点此举报(免注册)</a>,举报后维护人员会在两分钟内校正章节内容,请耐心等待,并刷新页面。</div>");
}
function footer(){

}
var prevpage="";
var nextpage="";
var index_page = "./";
function bookOperate(bookid,lchapter,chapterid,nchapter,chaptername){
	document.onkeydown=keypage;
 prevpage=lchapter;
 nextpage=nchapter;
 index_page = "./";

 if(typeof addHit!= 'undefined' &&addHit instanceof Function)
	addHit(bookid);

 if(typeof addBookMarkByJs!= 'undefined' &&addBookMarkByJs instanceof Function)
       addBookMarkByJs(chapterid,bookid,chaptername);

}

function keypage() {
if (event.keyCode==37) location=prevpage
if (event.keyCode==39) location=nextpage
if (event.keyCode == 13) document.location = index_page
}
