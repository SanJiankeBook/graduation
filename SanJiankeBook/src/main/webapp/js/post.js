function addHit(bookid){
	$.post("/post.php",
       { action: "addhit", bookid: bookid }
    ); 
}
function addBookCase(bookid){
	$.post("adduserbook/"+bookid,
        function(data){
            var msg="加入书架出错！";
            if(data==-1)
            {
               msg="您还没有登录，请登录后加入该书到书架中！";
               alert(msg);
               window.location="userlogininfo";
            }
            else if(data==0)
            {
                msg="书架已满，加入书架出错！";
            }
            else if(data==1)
            {
                msg="该书已在书架中！";
            }
            else if(data==2)
            {
                msg="加入书架成功！";
            }
            alert(msg);
        }
    );
}

function addBookCommend(bookid){
	$.post("/post.php",
       { action: "addbookcommend", bookid: bookid },
       function(data){
           var msg="投推荐票出错！";
            if(data==-1)
            {
               msg="您还没有登录，请登录后再投推荐票！";
            }
            else if(data==0)
            {
                msg="您今天的推荐票已用完，谢谢您的参与！";
            }
            else if(data>0)
            {
                msg="投推荐票成功，您今天还有"+data+"张推荐票！";
            }
            alert(msg);
       }
    );
}

function addBookMark(chapterId,bookId,chapterName){
    $.post("/post.php",
       { action: "addbookmark",chapterid:chapterId, bookid: bookId, chaptername: chapterName },
       function(data){
            var msg="加入书签出错！";
            if(data==-1)
            {
               msg="您还没有登录，请登录后再加入书签！";
            }
            else if(data==1)
            {
                msg="加入书签成功！";
            }
            alert(msg);
        }
    );
}


function postErrorChapter(chapterId,bookId){
    
    if($("#content").html().indexOf("手打中")>0||$("#content").html().indexOf("@@")>0){
        alert("本章节正在处理中，不需要举报！");
    	return;
    };
    $.post("/post.php",
       { action: "error",chapterid:chapterId, bookid: bookId },
       function(data){
            var msg="报送错误章节失败！";
            if(data==-1)
            {
               msg="报送错误章节过于频繁，请休息30秒后再执行此操作！";
            }
            else if(data==1)
            {
                msg="报送错误章节成功，我们会尽快处理！请您稍后10分钟刷新查看。";
            }
            alert(msg);
        }
    );
}

