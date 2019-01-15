function clearCookies(id)
{
	var bookidstr=getCookie("bookid");
	var chapteridstr=getCookie("chapterid");
	var chapternamestr=getCookie("chaptername");
	
	var cookiesbookid=new Array();
	if(bookidstr!=null&&bookidstr!=undefined)
	    cookiesbookid=bookidstr.split(",");
	
	var cookieschapterid=new Array();
	if(chapteridstr!=null&&chapteridstr!=undefined)
	    cookieschapterid=chapteridstr.split(",");
	
	var cookieschaptername=new Array();
	if(chapternamestr!=null&&chapternamestr!=undefined)
	    cookieschaptername=chapternamestr.split(",");
	
	bookidstr="";
	chapteridstr=""
	chapternamestr="";
	
	for (i=0;i<cookiesbookid.length;i++)
	{
		if(id!=cookiesbookid[i])
		{
			if(bookidstr=="")
			{
				bookidstr=cookiesbookid[i];
				chapteridstr=cookieschapterid[i];
				chapternamestr=cookieschaptername[i];
			}
			else
			{
				bookidstr=bookidstr + "," +cookiesbookid[i];
				if(cookieschapterid[i]!=null&&cookieschapterid[i]!=undefined)
				    chapteridstr=chapteridstr +","+ cookieschapterid[i];
				else
				    chapteridstr=chapteridstr +",";
				    
				if(cookieschaptername[i]!=null&&cookieschaptername[i]!=undefined)
				    chapternamestr=chapternamestr +","+ cookieschaptername[i];
				else
				     chapternamestr=chapternamestr +",";
			}
		}
	}
    setCookie("bookid",bookidstr);
    setCookie("chapterid",chapteridstr);
    setCookie("chaptername",chapternamestr);
   location.reload();
}

function setCookie(c_name,value,expiredays)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+365)
    document.cookie=c_name+ "=" +escape(value)+";expires="+exdate.toGMTString()+";path=/";
}

function getCookie(c_name)
{
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
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

function addBookMarkByJs(chapterId,bookId,chapterName){
	var bookidstr=getCookie("bookid");
	var chapteridstr=getCookie("chapterid");
	var chapternamestr=getCookie("chaptername");
	
	var cookiesbookid=new Array();
	if(bookidstr!=null&&bookidstr!=undefined&&bookidstr!="")
	    cookiesbookid=bookidstr.split(",");
	else
	    bookidstr="";

	var cookieschapterid=new Array();
	if(chapteridstr!=null&&chapteridstr!=undefined&&chapteridstr!="")
	    cookieschapterid=chapteridstr.split(",");
	else
	    chapteridstr="";

	var cookieschaptername=new Array();
	if(chapternamestr!=null&&chapternamestr!=undefined&&chapternamestr!="")
	    cookieschaptername=chapternamestr.split(",");
	else
	    chapternamestr="";

	var isfind=0,k=0,i=0;
	
        
    for (i=0;i<cookiesbookid.length;i++){
	    if(bookId==cookiesbookid[i]){
	        k=i;
		    isfind=1;
		    break;
	    }
    }
    
    if(isfind==0){
	    if(bookidstr==""||bookidstr==undefined||bookidstr==null){
		    bookidstr=bookId;
		    chapteridstr=chapterId;
		    chapternamestr=escape(chapterName);
		}
	    else{
                    if(cookiesbookid.length==30){
		    	bookidstr=bookidstr.substring(bookidstr.indexOf(","));
			chapteridstr=chapteridstr.substring(chapteridstr.indexOf(","));
			chapternamestr=chapternamestr.substring(chapternamestr.indexOf(","));
                    }
                    bookidstr=bookidstr+ "," +bookId;
		    chapteridstr=chapteridstr+ "," +chapterId;
		    chapternamestr=chapternamestr+ "," +escape(chapterName);
		}
	}
	else if(isfind==1){
	    chapteridstr="";
	    chapternamestr="";
	    
            
	    for(i=0;i<cookieschapterid.length;i++){
			if(chapteridstr=="")
			{
				if(i==k){
				    chapteridstr=chapterId;
				    chapternamestr=escape(chapterName);
				}
				else{
				    chapteridstr=cookieschapterid[i];
				    chapternamestr=cookieschaptername[i];
				}
			}
			else
			{
				if(i==k){
				    chapteridstr=chapteridstr +	"," + chapterId;
				   	chapternamestr=chapternamestr +	"," + escape(chapterName);
				}
				else{
					chapteridstr=chapteridstr +	"," + cookieschapterid[i];
					chapternamestr=chapternamestr +	"," + cookieschaptername[i];
				}
			}
		}
	}
    setCookie("bookid",bookidstr);
    setCookie("chapterid",chapteridstr);
    setCookie("chaptername",chapternamestr);
}

$(document).ready(function(){
    var chapterList= $("a[bookid]");
    if(chapterList==undefined||chapterList==null||chapterList.length==0) return;
    var bookidstr=getCookie("bookid");
	var chapteridstr=getCookie("chapterid");
	var chapternamestr=getCookie("chaptername");
	
	var cookiesbookid=new Array();
	if(bookidstr!=null&&bookidstr!=undefined)
	    cookiesbookid=bookidstr.split(",");
	
	var cookieschapterid=new Array();
	if(chapteridstr!=null&&chapteridstr!=undefined)
	    cookieschapterid=chapteridstr.split(",");
	
	var cookieschaptername=new Array();
	if(chapternamestr!=null&&chapternamestr!=undefined)
	    cookieschaptername=chapternamestr.split(",");
	    
	var bookCaseInfo=new Array();
	var i=0;
    for (i=0;i<cookiesbookid.length;i++)
    {
        if(cookieschaptername[i]!=undefined&&cookieschaptername[i]!=null)
	        bookCaseInfo[cookiesbookid[i]]=cookieschapterid[i]+","+cookieschaptername[i];
	    else
	         bookCaseInfo[cookiesbookid[i]]=cookieschapterid[i]+",";
    }

    chapterList.each(function(){
        var currentChapterNode=$(this);
        var bookId=parseInt(currentChapterNode.attr("bookid"));
        var chapterInfo=bookCaseInfo[bookId];
        if(chapterInfo!=null&&chapterInfo!=undefined){
            chapterInfo=chapterInfo.split(",");
            var chapterId=chapterInfo[0];
            var chapterName=unescape(chapterInfo[1]);
            currentChapterNode.attr("href",currentChapterNode.attr("href")+chapterId+".html");
            currentChapterNode.attr("title",chapterName);
            currentChapterNode.text(chapterName);
        }
    });
});