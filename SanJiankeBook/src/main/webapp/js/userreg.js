/*
 ** 判断是否是电子邮箱
 */
 String.prototype.isEmail = function(){
    try{
        var pattern=/^(([a-z A-Z \- _ 0-9]+)@[a-z A-Z \- _ 0-9]+\.([a-z A-Z]+(\.)?)?[a-z A-Z]+)$/;
	    return pattern.test(this.Trim());
    }
    catch(e){
        return false;
    }
};
String.prototype.Trim  =  function(){
	return  this.replace(/(^\s*)  |(\s*$)/g,  "");
};
$(document).ready(function(){
    $("#regusername").focus();
    $("#regusername").blur(function(){
        var username=$(this).val();
        if($.trim(username).length<5){
            $("#usermsg").html("用户名长度不能小于5");
            return;
        }
        
         $.ajax({
            url:'/checkmember.php?action=checkusername&username='+username,
            success:function(data){
                if(data == 1){//如果用户存在
                    $("#usermsg").html("该用户名已经存在!");
                    return;
                }//end if
                else{
                    $("#usermsg").html("");
                }
            },
            error:function(){alert('验证用户出错!');}
        });//end ajax
    });
     $("#regpassword").blur(function(){
        var userpwd=$(this).val();
        if($.trim(userpwd).length<=5){
            $("#passmsg").html("密码长度不能小于6位!");
            return;
        }
        else
        {
             $("#passmsg").html("");
        }
        
    });
    
     $("#regrepassword").blur(function(){
        var userpwd2=$(this).val();
        if(userpwd2!=$("#regpassword").val()){
            $("#repassmsg").html("两次输入的密码不一致!");
            return;
        }
        else
        {
             $("#repassmsg").html("");
        }
        
    });
    
    $("#regemail").blur(function(){
        var email=$(this).val();
        if($.trim(email).length==0){
            $("#mailmsg").html("邮件不能为空!");
            return;
        }
        if(!email.isEmail()){
            $("#mailmsg").html("邮件格式不正确!");
            return;
        }
        $("#mailmsg").html("");
    });
});

function checkUserReg()
{
    if($("#usermsg").html()!=""||$("#passmsg").html()!=""||$("#repassmsg").html()!=""||$("#mailmsg").html()!="")
    {
        return false;
    }
}