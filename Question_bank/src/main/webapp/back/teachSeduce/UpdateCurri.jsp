<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" language="javascript" src="../../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../../js/dateChoose.js"></script>
<style type="text/css">
#col {
	background: #FF99CC;
}
</style>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />

 <style>
#updateclassCurriFind_form table{
    width: 85%;
    float: left;
    margin-left: 5%;
    margin-top:50px;
    height: 850px;
    border: 1px solid #000000;
    text-align: center;
    margin-bottom: 20px;
}
#updateclassCurriFind_form table tr{
    border: 1px solid #000000;
    height: 8%;
}
#updateclassCurriFind_form table td{
    border: 1px solid #000000;
    width: 12.5%;
    font-size: 13px;
}
#updateclassCurriFind_form table td p{
    text-align: center;
    line-height: 50%;
}
li{
 	list-style: none;
}
.updatechoose{
   background: #333;
    position: fixed;
    top: 110px;
    right: -250px;
    width: 250px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    text-align: inherit;
    overflow-y:auto;
}
.accordion {
    width: 80%;
    background: none;
    /*max-width: 360px;*/
    margin: 15px auto 20px;
    /*background: #FFF;*/
    z-index:2;
}

.accordion .link {
    cursor: pointer;
    display: block;
    padding: 12px 15px 15px 42px;
    color: #f0efee;
    font-size: 14px;
    font-weight: 700;
    border-bottom: 1px solid #CCC;
   /* position: relative;*/
    -webkit-transition: all 0.4s ease;
    -o-transition: all 0.4s ease;
    transition: all 0.4s ease;
	margin-left:-20px; 
}
.accordion li.open .link {
    color: #b63b4d;
}
.accordion li.open i {
    color: #b63b4d;
}
/**
 * Submenu
 -----------------------------*/
.submenu {
	 z-index:2;
    display: none;
    background: #444359;
    font-size: 14px;
    height: 200px;
    overflow-y:auto;
    border-radius: 0 0 5px 5px;
    margin-left:-20px; 
}

.submenu li {
    border-bottom: 1px solid #4b4a5e;
    margin-left:-20px; 
}
.submenu li:last-child{
    border: none;
}
.submenu label{
    width: 100%;
}
.submenu li i{
    width:20px;
    height: 20px;
    border: 1px solid #f0f0f0;
    float: left;
    margin-left: 12px;
    margin-top: 5px;
    margin-right:15px;
}
.submenu li a {
    display: block;
    text-decoration: none;
    color: #d9d9d9;
    padding: 6px;
    padding-left: 42px;
    -webkit-transition: all 0.25s ease;
    -o-transition: all 0.25s ease;
    transition: all 0.25s ease;
}
.submenu li input{
    float: left;
    width: 20px;
    height:20px;
    margin-left: 10px;
    /*padding-top: 6px;*/
}
.submenu a:hover {
    background: #b63b4d;
    color: #FFF;
}
.updatefinish{
    display: inline-block;
    padding-left: 30%;
    color: #f0f0f0;
    font-size: 16px;
    margin-top: 20px;
    cursor: pointer;
}
.cancel{
	display: inline-block;
    color: #f0f0f0;
    margin-top:30px;
    float:right;
    cursor: pointer;
    font-size: 14px;
}
.delete{
	display: inline-block;
    color: #f0f0f0;
    margin-top:30px;
    float:right;
    cursor: pointer;
    margin-right:30px;
    font-size: 14px;
}
    </style>
  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班级课表查询</title>
</head>
<body>
<div id="classCurriFind_conditionDiv" >
	查询日期：<input type="text" id="startDate" name="startDate" readonly="readonly" onclick="fPopCalendar(event,this,this)"onfocus="this.select()" />
		<input style="visibility:hidden" type="text" id="endDate" name="endDate" readonly="readonly" onclick="fPopCalendar(event,this,this)" onfocus="this.select()" />
		
	班级：<select id="classCurriFind_selectSubcondition" >
		<option value="-1"  selected="selected">--请选择--</option>
		</select> 
		
	<input type="button"  onClick="classCurriInfo()" value="查询" /> 
	
</div>
<div id="classCurriFind_showDiv" >
	<form id="updateclassCurriFind_form"  action="classCurriFind.action" method="post">
		<!--   <input type="text" name="notautosubmit"  style="display:none"/>  -->
	</form>
</div>
    <div class="updatechoose">
        <ul id="accordion" class="accordion">
            <li>
                <span class="link">老师</span>
                <ul class="submenu" id="teacherList">
                </ul>
            </li>
            <li>
                <span class="link">课程</span>
                <ul class="submenu" >
                    <li>
                        <label>
                            <input type="radio" name="classes">
                            <a href="javascript:void(0)"  id="-1">放假</a>
                        </label>
                    </li>
                    <li>
                        <label>
                            <input type="radio" name="classes">
                            <a href="javascript:void(0)" id="-2">自习</a>
                        </label>
                    </li>
                    <li>
                        <label>
                            <input type="radio" name="classes">
                            <a href="javascript:void(0)" id="-3">复习</a>
                        </label>
                    </li>
                    <li>
                        <label>
                            <input type="radio" name="classes">
                            <a href="javascript:void(0)" id="-4">补课</a>
                        </label>
                    </li>
                    <li>
                        <label>
                            <input type="radio" name="classes">
                            <a href="javascript:void(0)" id="-5">就业指导</a>
                        </label>
                    </li>
                    <li>
                        <label>
                            <input type="radio" name="classes">
                            <a href="javascript:void(0)" id="-6">测试</a>
                        </label>
                    </li>
                    <li>
                        <ul class="classes" id="classList"></ul>
                        <ul class="classes" id="chapterList"></ul>
                    </li>
                </ul>
            </li>
            <li>
                <span class="link">教室</span>
                <ul class="submenu" id="classRoomList">
              <!--   教室信息 -->
                </ul>
            </li>
            <li>
                 <span class="updatefinish">完成</span><span class="cancel">取消</span>&nbsp;<span class="delete">删除</span> 
            </li>
        </ul>
    </div>
</body>
 <script>

 var allClassName =new Array();

        $(function() {
            var Accordion = function(el, multiple) {
                this.el = el || {};
                this.multiple = multiple || false;

                // Variables privadas
                var links = this.el.find('.link');
                // Evento
                links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
            };

            Accordion.prototype.dropdown = function(e) {
                var $el = e.data.el;
                $this = $(this),
                $next = $this.next();

                $next.slideToggle();
                $this.parent().toggleClass('open');

                if (!e.data.multiple) {
                    $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
                };
            }

            var accordion = new Accordion($('#accordion'), false);
        	$.post("findClass.action", function(data) {
       		 if(data.responseCode==1){ 
       			alert("班级查询错误,请与管理员联系");
       			return;
       		}else{ 
       			data.obj.sort(createComparisonFunction('className'));
       			 for(var i=0;i<data.obj.length;i++){
       					$("#classCurriFind_selectSubcondition").append("<option value="+data.obj[i].id+">"+data.obj[i].className+"</option>");
       					allClassName[i]=[data.obj[i].className,data.obj[i].id];	
       			 } 										
       		}
       	});
        });
    </script>
<script type="text/javascript">
var count=1;var b; var innerhtml;
var time,date,column;//课程表 的id号
$("#startDate").change(function(){
	//alert($("#startDate").val());
	
	    var  date=$('#startDate').val();
	    var  d= new Date(date);//这里日期是传递过来的，可以自己改
	    d.setDate(d.getDate()+7);
	    date=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//新日期
	    
});

function classCurriInfo(){
	$(".updatechoose").animate({right:'-250px'});
	var startDate = $("#startDate").val();

	if(startDate==""){
		alert("请选择日期");
		return;
	}
	var endDate ;
	var classid=$("#classCurriFind_selectSubcondition").find("option:selected").val();
	var date;
    endDate= new Date(startDate);//这里日期是传递过来的，可以自己改
    endDate.setDate(endDate.getDate()+6);
    if(endDate.getDate()<10){
    	if(endDate.getMonth()+1<10){
    		endDate=endDate.getFullYear()+"-0"+(endDate.getMonth()+1)+"-0"+endDate.getDate();//新日期	
    	}else{
    		endDate=endDate.getFullYear()+"-"+(endDate.getMonth()+1)+"-0"+endDate.getDate();//新日期
    	}
    }else{
    	if(endDate.getMonth()+1<10){
    		endDate=endDate.getFullYear()+"-0"+(endDate.getMonth()+1)+"-"+endDate.getDate();//新日期	
    	}else{
    		endDate=endDate.getFullYear()+"-"+(endDate.getMonth()+1)+"-"+endDate.getDate();//新日期
    	}
    }
	 $.post("classCurriFind_getAllTeacherCurriInfo.action", {
		 startDate : startDate,
		 endDate:endDate,
		 classid:classid,
 	}, function(data) {
 		
 		if(data.obj.length<=0){
 			alert("暂无课程");
 		}
 		 if(data.responseCode==1){ 
				alert("查询有误，请与管理员联系");
				
				return;
			}else{
				$("#updateclassCurriFind_form").html("");
				gettheday();
				
				for(var i=0;i<allClassName.length;i++){
					var temp=new Array();//同一老师的数据
					var flag=0;
					for(var j=0;j<data.obj.length;j++){
						if(data.obj[j].classname==allClassName[i][0]){
							temp[flag]=data.obj[j];
							flag++;
						}
					}
					
					if(temp.length>0){
						CreateTable(allClassName[i][0],allClassName[i][1]);
						makea(temp,allClassName[i][0],allClassName[i][1]);	
						 };
				}
				$("#updateclassCurriFind_form  td").dblclick(function() {
					 //得到班级ID
					 that=$(this);
					 bid = $(this).parent().parent().parent().attr('id');
				  	 bid=$.trim(bid);
				     time="";
				     date="";
				     b="";
				     classes="";
				     classroom="";
				     teacher="";
				     var row = that.parent().index() + 1; // 行位置
				     var col = that.index() + 1; // 列位置
				     column=col-1;
				     var id=that.attr("id");
				     //有数据时插入
				     if ((row > 1) && (col > 1) ) {//&& id
				    	 $(".updatechoose").animate({right:'0px'});
				   	  time = that.parent().find('td').eq(0).text();time=$.trim(time);
				         date = $('#updateclassCurriFind_form tr').eq(0).find('td').eq(column).text();date=$.trim(date);
				   	  //插入课表数据
						   	 $.post("toUpdateCurri.action",{
									bid : bid,
									date:date,
									timeperiods:time
								}, function(data) {
										 if(data.responseCode==1){ 
											alert("查询错误,请与管理员联系");
											$(".updatechoose").animate({right:'-250px'});
											return;
										}else{
											var curriculum=data.obj[0];//已上课程
											var chapter=data.obj[1];//未上课程
											var teacherList=data.obj[2];//教师
											var classroomList=data.obj[3];//教室
								
											//拼接教师
								 			$("#teacherList").html("");
								 			for(var i=0;i<teacherList.length;i++){
									 			$("#teacherList").append("<li style='margin-left:-30px'><label><input type='radio' name='teacher'><i class='color"+i+"' style='background-color: "+teacherList[i].bgcolor+";border-color:"+teacherList[i].fontcolor+"'></i><a href='javascript:void(0)'  id='"+teacherList[i].id+"'>"+teacherList[i].userName+"</a></label></li>")
										 	}
										//拼接教室
								 		$("#classRoomList").html("");
								 		$("#classRoomList").append("<li style='margin-left:-30px'><label><a href='javascript:void(0)'>"+classroomList[0].local+":</a></label><ul id='"+classroomList[0].local+"'><li style='margin-left:-30px'><label><input type='radio' name='classroom'><a href='javascript:void(0)' id='"+classroomList[0].classroomid+"'>"+classroomList[0].classroomname+"_"+classroomList[0].machineNumber+"</a></label></li></ul></li>");
								 		for(var i=1;i<classroomList.length;i++){
											 if(classroomList[i].local==classroomList[i-1].local){
										 		$("#"+classroomList[i].local+"").append("<li style='margin-left:-30px'><label><input type='radio' name='classroom'><a href='javascript:void(0)' id='"+classroomList[i].classroomid+"'>"+classroomList[i].classroomname+"_"+classroomList[i].machineNumber+"</a></label></li>")
									 		}else{
										 		$("#classRoomList").append("<li style='margin-left:-30px'><label><a href='javascript:void(0)'>"+classroomList[i].local+":</a></label><ul id='"+classroomList[i].local+"'><li style='margin-left:-30px'><label><input type='radio' name='classroom'><a href='javascript:void(0)' id='"+classroomList[i].classroomid+"'>"+classroomList[i].classroomname+"_"+classroomList[i].machineNumber+"</a></label></li></ul></li>");
											}
								 		}
										//拼接已上课程
				   					$("#classList").html("");
				   					if(curriculum.length>0){ 
				   						$("#classList").append("<p style='color: #FFFFFF;margin-left:-30px'>已上课程:</p>");
				   						var count=curriculum.length;
				   						if(count>5){//防止多出
				   							count=5;
				   						}
				   						for(var i=0;i<count;i++){//已经上的五节课
				   							$("#classList").append("<li style='margin-left:-30px'><label><input type='radio' name='classes' onchange='changed()'><a href='javascript:void(0)' id='"+curriculum[i].chapterid+"'>"+curriculum[i].chapterName+"</a></label></li>")
				   						}
				   					}
				   					//拼接未上课程
				   					$("#classList").append("<p style='color: #FFFFFF;margin-left:-30px'>应上课程:</p>"); 
				   					for(var i=0;i<chapter.length;i++){
											$("#classList").append("<li style='margin-left:-30px'><label><input type='radio' name='classes' onchange='changed()'><a href='javascript:void(0)' id='"+chapter[i].id+"'>"+chapter[i].chapterName+"</a></label></li>")
				   					}
				   				//更多课程
			        				$("#chapterList").append("<p style='color: #FFFFFF;margin-left:-30px;'><a href='javascript:void(0)' onclick='getAllChapter("+bid+")'>更多课程</a></p>");
			        				
				   					 $("input[name='teacher']").change(function() {
								              teacher=$.trim($("input[name='teacher']:checked").parent().text());
								              teacherid=$.trim($("input[name='teacher']:checked").parent().find("a").attr("id"));
								          })
								          $("input[name='classes']").change(function() {
								              classes=$.trim($("input[name='classes']:checked").parent().text());
								              classesid=$.trim($("input[name='classes']:checked").parent().find("a").attr("id"));
								              count=2;
								          })
								          $("input[name='classroom']").change(function() {
								              classroom=$.trim( $("input[name='classroom']:checked").parent().text());
								              classroomid=$.trim($("input[name='classroom']:checked").parent().find("a").attr("id"));
								          })
									}
							});
				         $(".updatechoose input:radio").removeAttr('checked');
				         innerhtml=String($.trim(that.text()));
				         $(".cancel").click(function() {
					    	  that.html(innerhtml);
					    	  $(".updatechoose").animate({right:'-250px'});
					    	  $("#chapterList").html("");
						})
				         obj_text = $("table").find("input:text");    // 判断单元格下是否有文本框
				         if (!obj_text.length) {
				             //如果没有文本框，则添加文本框使之可以编辑
				         	that.html("<input type='text' value='" + innerhtml + "' autofocus='autofocus'>");
				         }
				     }
				     	$(".updatefinish").off();//解绑事件
				     	$(".updatefinish").one("click",(function () {
				     		$("#chapterList").html("");
				     		b=[];
				             //文本框编辑
				           var id=that.attr("id");
				           if (!teacher&&!classroom ||( teacher=="null" &&  classroom=="null" )){
				                 inner = classes;
				                 teacherid="-1";
				                 classroomid="-1";
				           }else if(!classroom || classroom=="null"){
				                 inner = classes+ '<br>[' + teacher + ']';
				                 classroomid="-1";
				           }else if(!teacher || teacher=="null"){
				                 inner = classes+ '<br><' + classroom + '>';
				                 teacherid="-1";
				             }else{
				                 inner = classes+ '<br>[' + teacher + ']<' +classroom+ '>';
				             }
				             if (classes!="" && classes!="null"){
				            	 $("table td input").parent().css("color", $("input[name='teacher']:checked").parent().find("i").css("borderColor"));
					              	if (navigator.userAgent.indexOf("Firefox")>-1) {
					 						$("table td input").parent().css("color", $("input[name='teacher']:checked").parent().find("i").css("borderLeftColor"));
					 					}
					              	
					              	$("table td input").parent().css("backgroundColor", $("input[name='teacher']:checked").parent().find("i").css("backgroundColor"));
					              	if (classes=="自习") {
					              		$("table td input").parent().css("color", "#000");
					                      $("table td input").parent().css("backgroundColor", "#FFFFCD");
					 					} else if (classes=="补课"){
					 						$("table td input").parent().css("color", "#000");
					                      $("table td input").parent().css("backgroundColor", "#FFFFCD");
					 					} else if (classes=="放假"){
					 						$("table td input").parent().css("color", "#FE4044");
					                      $("table td input").parent().css("backgroundColor", "#fff");
					 					} else if (classes=="测试"){
					 						$("table td input").parent().css("color", "#000");
					                      $("table td input").parent().css("backgroundColor", "#B3BDCD");
					 					}
			                		//banji="'"+banji+"'";
			                		if(id==null){//是插入的操作
			                			id="-1";
			                		}
				                 b = [id,teacherid,classesid,classroomid,bid,time,date];
				                 //console.info(b);
				                 //后台更新数据
				                    	 $.post("doUpdateCurri.action",{
				                    		 CurrInfo : JSON.stringify(b),
				                     	}, function(data) {
										 if(data.responseCode==1){ 
											alert("更新错误,请与管理员联系");
											$(".updatechoose").animate({right:'-250px'});
											return;
										}else{
											//alert("更新课程成功！！！");
											  inner = $.trim(inner);
							                  $("#updateclassCurriFind_form td input").parent().html(inner);
							                  $(".updatechoose").animate({right:'-250px'});
							                  return;
										}
								});
				             }else{
				                 alert("更新课程不成功");
				                 $("#updateclassCurriFind_form td input").parent().html(innerhtml);
				                 $(".updatechoose").animate({right:'-250px'});
				                  return;
				             }
				         })
				         
				     )
				 });
				}
			});
}

var a=[];//所有满足条件的数组
var obj=[];//满足坐标的数组
var d1,d2,d3,d4,d5,d6,d7,d;

function gettheday() {
    d1=$('#startDate').val();
    d= new Date(d1);//这里日期是传递过来的，可以自己改
    d.setDate(d.getDate());
    d1=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//新日期
    d.setDate(d.getDate() + 1);//天数+1
    d2=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//新日期
    d.setDate(d.getDate() + 1);//天数+1
    d3=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
    d.setDate(d.getDate() + 1);//天数+1
    d4=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
    d.setDate(d.getDate() + 1);//天数+1
    d5=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
    d.setDate(d.getDate() + 1);//天数+1
    d6=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
    d.setDate(d.getDate() + 1);//天数+1
    d7=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
}


//得到相对应的班级的纪录
function makea(arr,teachername,id) {
   	 for(var i=0;i<arr.length;i++){
   		if(arr[i].classname==teachername){
   			var ll=[];
   			for(var j in arr[i]){
   				ll.push(arr[i][j]);
   			}
               a.push(ll);
   		}
    }
   	 
    for(var i=0;i<a.length;i++){
        obj=maketable(a[i]);
        //console.info(obj);
         if(a[i][1]=='无'){
        	a[i][1]='';
        }
        if(a[i][4]=='无'){
        	a[i][4]='';
        }
        if(a[i][9]=='无'){
        	a[i][9]='';
        }
        $('#'+id+'').find("tr").eq(obj.a).find("td").eq(obj.b).append(a[i][1]+"<br>"+a[i][4]+"<br>"+a[i][9])
        	.attr("id",a[i][7]).css({"background-color": a[i][0],"color":a[i][6]});
    }
    a=[];
}
//得到每个的坐标
function maketable(arr) {
    var a,b;
    for(var i=0;i<arr.length;i++){
        var time=arr[8];
        switch (time){
            case "8:30-10:30":
                a=1;
                break;
            case "10:30-12:00":
                a=2;
                break;
            case "14:00-15:30":
                a=3;
                break;
            case "15:30-17:30":
                a=4;
                break;
            case "19:00-20:30":
                a=5;
                break;
            case "20:30-22:00":
                a=6;
                break;

        }
        var date=arr[5];
       
        switch (date){
            case d1:
                b=1;
                break;
            case d2:
                b=2;
                break;
            case d3:
                b=3;
                break;
            case d4:
                b=4;
                break;
            case d5:
                b=5;
                break;
            case d6:
                b=6;
                break;
            case d7:
                b=7;
                break;
        }
    }
    return {a:a,b:b};
}


//动态生成表格
function CreateTable(teachername,id)
{
 var table=$("<table cellpadding='0' cellspacing='0'  id='"+id+"' summary='"+teachername+"'><tbody>");
    table.appendTo($("#updateclassCurriFind_form"));
    var tr1=$("<tr><td id='col'>"+teachername+"</td> <td id='col'>"+d1+"</td> <td id='col'>"+d2+"</td> <td id='col'>"+d3+"</td> <td id='col'>"+d4+"</td> <td id='col'>"+d5+"</td> <td id='col'>"+d6+"</td> <td id='col'>"+d7+"</td></tr>");
    var tr2=$("<tr><td id='col'>8:30-10:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr3=$("<tr><td id='col'>10:30-12:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr4=$("<tr><td id='col'>14:00-15:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr5=$("<tr><td id='col'>15:30-17:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr6=$("<tr><td id='col'>19:00-20:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr7=$("<tr><td id='col'>20:30-22:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    tr1.appendTo(table);tr2.appendTo(table);tr3.appendTo(table);tr4.appendTo(table);tr5.appendTo(table);tr6.appendTo(table);tr7.appendTo(table);
    $("#updateclassCurriFind_form").append("</tbody></table>");
}

function getAllChapter( bid){
	$.post("getAllChapter.action",{
		bid : bid,
	}, function(data) {
			 if(data.responseCode==1){ 
				alert("查询错误,请与管理员联系");
				return;
			}else{
				var chapter=data.obj;//未上课程
				$("#chapterList").html("");
				$("#chapterList").append("<p style='color:#fff;margin-left:-30px'>更多课程</p>")
				for(var i=0;i<chapter.length;i++){
					$("#chapterList").append("<li><label><input type='radio' name='classes' onchange='changed()'><a href='javascript:void(0)' id='"+chapter[i].id+"'>"+chapter[i].chapterName+"</a></label></li>")
				}
				$("#chapterList").append("<li><label><a href='javascript:void(0)' id='nomore'>隐藏更多</a></label></li>")
				$("#nomore").click(function(){
					$("#chapterList").html("");
					$("#chapterList").append("<p style='color: #FFFFFF;margin-left:-30px;'><a href='javascript:void(0)' onclick='getAllChapter("+bid+")'>更多课程</a></p>");
				})
			}
	});
	}
	  function changed(){
            classes=$.trim($("input[name='classes']:checked").parent().text());
            classesid=$.trim($("input[name='classes']:checked").parent().find("a").attr("id"));
            
	} 
	 
	//根据对象数组中的某一个字段排序
	 function createComparisonFunction(propertyName){
	     return function(object1,object2){
	         var value1 = object1[propertyName];
	         var value2 = object2[propertyName];

	         if(value1 < value2){
	             return -1;
	         }else if(value1 > value2){
	             return 1;
	         }else{
	             return 0;
	         }
	     };
	 };
	 
	 //课表删除
	  $(".delete").click(function() {
		  var id=that.attr("id");
		   if(id==undefined){
			  alert("没有课程删除!!!!");
		 	  that.html("");
			  that.css("backgroundColor", "#FFFFFF");
	   	  $(".updatechoose").animate({right:'-250px'});
			  return;
		  } 
		   $.post("deleteCurri.action",{
      		currId : id, 
      	}, function(data) {
      		if(data.responseCode==0){
      	  that.html("");
		  that.css("backgroundColor", "#FFFFFF");
   	  $(".updatechoose").animate({right:'-250px'});
   	  $("#chapterList").html("");
   		
      		}else{
      			alert(data.errorMessag);
      		}
      	});
   	
	});

	 
</script>
<script type="text/javascript">
//禁用Enter键表单自动提交  
       document.onkeydown = function(event) {  
           var target, code, tag;  
           if (!event) {  
               event = window.event; //针对ie浏览器  
               target = event.srcElement;  
               code = event.keyCode;  
               if (code == 13) {  
                   tag = target.tagName;  
                   if (tag == "TEXTAREA") { return true; }  
                   else { return false; }  
               }  
           }  
           else {  
               target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
               code = event.keyCode;  
               if (code == 13) {  
                   tag = target.tagName;  
                   if (tag == "INPUT") { return false; }  
                   else { return true; }  
               }  
           }  
       };  
</script>
</html>