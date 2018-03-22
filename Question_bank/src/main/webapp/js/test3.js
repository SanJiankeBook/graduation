    //初始化一些值
    var a,obj_text,that;
    var objJson={};
    var time,date,column,b;
    var banji,inner,classes,teacher,classroom,classesid,teacherid,classroomid,machinecount;
    var count=1;
    var count1=1;
    var d,d1,d2,d3,d4,d5,d6,d7,week;
    var teacherarr=[],classesarr=[],classroomarr=[];
    var bgcolor,fontcolor;
    //显示班级显示
    $(".classSel .select").click(function () {
        //获取班级
        $(".classche").css("display","block");
    })
if(count1=1){
	$(".classche a").click(
        function () {
            $(this).parent().css("display","none");
            a=[];
            $('.classche input:checked').each(function () {
                a.push($(this).next().html());
            });
           
        } 
    )
    count1=2;
}
    
    

    //生成后几天的信息
    function gettheday() {
    	d1=$('.timeSel input').val();
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
    
    //生成表格
    $('#makeTable').click(function () {
    	if($('.timeSel input').val()!="" && count1==2 && $('.weekche option:selected').text()!="周选择"){
        gettheday();
        week=$('.weekche option:selected').text();
        $("#table").html("");
        //发送请求
        var checkboxTest = document.forms['classche'].elements['sel'];
        var bid=getCheckboxValue(checkboxTest) ;
        $.post("getTable.action",{bid:bid},function(data){
	 		 if(data.responseCode==1){ 
				alert("获取表格信息出错，请与管理员联系");
				return;
			}else{ 
				var obj=data.obj[0];	
				//给班级根据名字排序
				//createComparisonFunction("className");
				obj.sort(createComparisonFunction('className'));
			 for(var i=0;i<obj.length;i++){
				 CreateTable(obj[i],week);
			 }
			 //拼接教师信息
			 var teacherList=data.obj[1];
			 $("#teacherList").html("");
			 for(var i=0;i<teacherList.length;i++){
				 c=teacherList[i].id+","+teacherList[i].userName+","+teacherList[i].bgcolor+","+teacherList[i].fontcolor;
				 teacherarr.push(c.split(","));
				 $("#teacherList").append("<li><label><input type='radio' name='teacher'><i class='color"+i+"' style='background-color: "+teacherList[i].bgcolor+";border-color:"+teacherList[i].fontcolor+"'></i><a href='javascript:void(0)' id='"+teacherList[i].id+"'>"+teacherList[i].userName+"</a></label></li>")
			 }
			 //拼接教室信息
			 var classroomList =data.obj[2];
			 $("#classRoomList").html("");
			 $("#classRoomList").append("<li><label><a href='javascript:void(0)'>"+classroomList[0].local+":</a></label><ul id='"+classroomList[0].local+"'><li><label><input type='radio' name='classroom'><a href='javascript:void(0)' id='"+classroomList[0].classroomid+"'>"+classroomList[0].classroomname+"_"+classroomList[0].machineNumber+"</a></label></li></ul></li>");
			 c=classroomList[0].classroomid+","+classroomList[0].classroomname+","+classroomList[0].machineNumber;
			 classroomarr.push(c.split(","));
			 for(var i=1;i<classroomList.length;i++){
				 c=classroomList[i].classroomid+","+classroomList[i].classroomname+","+classroomList[i].machineNumber;
				 classroomarr.push(c.split(","));
				 if(classroomList[i].local==classroomList[i-1].local){
					 $("#"+classroomList[i].local+"").append("<li><label><input type='radio' name='classroom'><a href='javascript:void(0)' id='"+classroomList[i].classroomid+"'>"+classroomList[i].classroomname+"_"+classroomList[i].machineNumber+"</a></label></li>")
				 }else{
					 $("#classRoomList").append("<li><label><a href='javascript:void(0)'>"+classroomList[i].local+":</a></label><ul id='"+classroomList[i].local+"'><li><label><input type='radio' name='classroom'><a href='javascript:void(0)' id='"+classroomList[i].classroomid+"'>"+classroomList[i].classroomname+"_"+classroomList[i].machineNumber+"</a></label></li></ul></li>");
				 }
			 }
			 //表格双击效果
			 $("#table td").dblclick(function() {
				 //得到班级ID
				 that=$(this);
				 bid = $(this).parent().parent().parent().attr('summary');
                 bid=$.trim(bid);
                 $.post("getClass.action",{bid:bid},function(data){//发送请求
        	 		 if(data.responseCode==1){ 
        				alert("出错，请与管理员联系");
        				return;
        			}else{
        				//拼接课程信息
        				$("#classList").html("");
        				var curriculum=data.obj[0];
        				var chapter=data.obj[1];
        				$("#classList").html("");
        				$("#chapterList").html("");
        				if(curriculum.length>0){
        					$("#classList").append("<p style='color: #FFFFFF;'>已上课程:</p>");
        					var count=curriculum.length;
        					if(count>3){//防止超过情况
        						count=2;
        					}
        					for(var i=0;i<count;i++){
        						classesarr.push("["+curriculum[i].chapterid+","+curriculum[i].chapterName+"]");
        						$("#classList").append("<li><label><input type='radio' name='classes' onchange='changed()'><a href='javascript:void(0)' id='"+curriculum[i].chapterid+"'>"+curriculum[i].chapterName+"</a></label></li>")
        					}
        				}
        				if(chapter.length>0){
        				$("#classList").append("<p style='color: #FFFFFF;'>未上课程:</p>"); 
        				for(var i=0;i<chapter.length;i++){
        					c=chapter[i].id+","+chapter[i].chapterName;
        					classesarr.push(c.split(","));
    						$("#classList").append("<li><label><input type='radio' name='classes' onchange='changed()'><a href='javascript:void(0)' id='"+chapter[i].id+"'>"+chapter[i].chapterName+"</a></label></li>")
        				}
        				}
        				//更多课程
        				$("#chapterList").append("<p style='color: #FFFFFF'><a href='javascript:void(0)' onclick='getAllChapter("+bid+")'>更多课程</a></p>");
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
		            time="";
		            date="";
		            b="";
		            classes="";
		            classroom="";
		            teacher="";
		            var row = that.parent().index() + 1; // 行位置
		            var col = that.index() + 1; // 列位置
		            if ((row > 3) && (col > 1)) {
		            	$(".insertchoose").animate({right:'0px'});
		                $(".insertchoose input:radio").removeAttr('checked');
		                $(".insertcancel").off();
		                $(".insertcancel").click(function() {
					    	  that.html($("table td input").val());
					    	  $(".insertchoose").animate({right:'-250px'});
					    	  $("#chapterList").html("");
						})
		                obj_text = $("table").find("input:text");    // 判断单元格下是否有文本框
		                if (!obj_text.length) {
		                    //如果没有文本框，则添加文本框使之可以编辑
		                	that.html("<input type='text' value='" + $.trim(that.text()) + "' autofocus='autofocus'>");
		                }
		            }
		            column=col-1;
		            that.on("keypress", (function (e) {
		            	$(".insertchoose").animate({right:'-250px'});
		                var currKey=e.which;
		                if(currKey==13){
			                	inner = $("table td input").val();
			                	if (inner!="") {
			                		teacher = (/\[[\u4e00-\u9fa5\s]+\]/).exec(inner);
			                		teacher = (/[\u4e00-\u9fa5]+/).exec(teacher);
			                		teacher=String(teacher);
			                		classroom =(/\<[\u4e00-\u9fa50-9\s]+/).exec(inner);
			                		classroom =(/[\u4e00-\u9fa50-9]+/).exec(classroom);
			                		classroom=String(classroom);
			                		if (!teacher&&!classroom ||( teacher=="null" &&  classroom=="null" )){
			                			classes=$.trim(inner);
			                			classesid=findId(classes,classesarr,classesid);
			                			teacherid="-1";
			                			classroomid="-1";
			                		}else if(!classroom || classroom=="null"){
			                			classroomid="-1";	 
			                			classes = (/[a-zA-Z\u4e00-\u9fa5\uff08\uff09\uff0c_0-9+\s\.]*\[/).exec(inner);
			                			classes = (/[a-zA-Z\u4e00-\u9fa5\uff08\uff09\uff0c_0-9+\.]*/).exec(classes);
			                					classes=String(classes);
			                					classesid=findId(classes,classesarr,classesid);
			                					teacherid=findId(teacher,teacherarr,teacherid);
			                					inner = classes+ '<br>[' + teacher + ']';
			                		}else if(!teacher || teacher=="null"){
			                			teacherid="-1";
			                			classes = (/[a-zA-Z\u4e00-\u9fa5\uff08\uff09\uff0c_0-9+\s\.]*\</).exec(inner);
					                	classes = (/[a-zA-Z\u4e00-\u9fa5\uff08\uff09\uff0c_0-9+\.]*/).exec(classes);
			                		    classes=String(classes);
			                			classroomid=findId(classroom,classroomarr,classroomid);
			                			machinecount=findCount(classroomid,classroomarr,machinecount);
			                			classesid=findId(classes,classesarr,classesid);
			                			inner = classes+ '<br><'+classroom+'_'+machinecount+ '>';
			                		}else{
			                					classes = (/[a-zA-Z\u4e00-\u9fa5\uff08\uff09\uff0c_0-9+\s\.]*\[/).exec(inner);
					                			classes = (/[a-zA-Z\u4e00-\u9fa5\uff08\uff09\uff0c_0-9+\.]*/).exec(classes);
			                					classes=String(classes);
			                					classesid=findId(classes,classesarr,classesid);
			                					classroomid=findId(classroom,classroomarr,classroomid);
			                					machinecount=findCount(classroomid,classroomarr,machinecount);
			                					teacherid=findId(teacher,teacherarr,teacherid);
			                					inner = classes+ '<br>[' + teacher + ']<' +classroom+'_'+machinecount+ '>';
			                		}
			                		var d= findColor(teacherid, teacherarr, bgcolor, fontcolor);
			                		$("table td input").parent().css("color", d.fontcolor);
			                		$("table td input").parent().css("backgroundColor", d.bgcolor);
			                		banji = that.parent().parent().parent().attr('summary');
			                		banji=$.trim(banji);
			                		banji="'"+banji+"'";
			                		time = that.parent().find('td').eq(0).text();time=$.trim(time);
			                		date = $('table tr').eq(1).find('td').eq(column).text();date=$.trim(date);
			                		if (classes!=""&& classes!="null") {
			                				if (classes=="自习") {
			                					classesid="-2";
			                					$("table td input").parent().css("color", "#000");
			                					$("table td input").parent().css("backgroundColor", "#FFFFCD");
			                				} else if (classes=="补课"){
			                					classesid="-4";
			                					$("table td input").parent().css("color", "#fff");
			                					$("table td input").parent().css("backgroundColor", "#FFFFFF");
			                				} else if (classes=="放假"){
			                					classesid="-1";
			                					$("table td input").parent().css("color", "#FE4044");
			                					$("table td input").parent().css("backgroundColor", "#FFFFFF");
			                				} else if (classes=="复习"){
			                					classesid="-3";
			                				}else if (classes=="测试"){
			                					classesid="-6";
			                					$("table td input").parent().css("color", "#000");
			                					$("table td input").parent().css("backgroundColor", "#B3BDCD");
			                				}else if (classes=="就业指导"){
			                					classesid="-5";
			                				}
			                				b = [teacherid,classesid,classroomid ,time, date];
			                				if(typeof objJson[banji]=="undefined"){
			                					objJson[banji]=[];
			                				}
			                				isRepeatTime(time,date,objJson[banji]);
			                				inner = $.trim(inner);
			                				//console.log(objJson);
			                				//判断在这个阶段是否有课程
			                                $.get("isHaveCurri.action",{
			                            		bid : banji,date :date,time:time,
			                            	},function(data) {
			                            			 if(data.responseCode==1){ 
			                            				 $("table td input").parent().css("backgroundColor", "#FFFFFF");
			                              				$("table td input").parent().html("");
			                              				alert("此班级在该时间段已有课程,填写课程不成功！");
			                            				return;
			                            			}else{
			                            				objJson[banji].push(b);
			                            				$("table td input").parent().html(inner);
			                            				that.off("keypress");//关闭事件
			                            			}
			                            	});
			                		}else{
			                			$("table td input").parent().css("backgroundColor", "#FFFFFF");
			                			$("table td input").parent().html("");
			                			alert('填写课程不成功！！');
			                		}
			                	}else{
			                		$("input:radio").attr("checked", false);
			                        $("table td input").parent().html($("table td input").val());
			                	}
		                }
		            }))
                  
		          }
		        });
			   });
		            }
    	}); }
    })
       
    //动态生成表格
    function CreateTable(banji,week)
    {
		 var table=$("<table cellpadding='0' cellspacing='0' summary="+banji.id+"><tbody>");
	        table.appendTo($("#table"));
	        var tr1=$("<tr><td>人数</td><td>"+banji.studentcount+"人</td><td>注意</td><td colspan='5'>"+banji.notice+"</td></tr>");
	        var tr2=$("<tr><td rowspan='2'>"+week+"("+banji.className+")</td> <td>"+d1+"</td> <td>"+d2+"</td> <td>"+d3+"</td> <td>"+d4+"</td> <td>"+d5+"</td> <td>"+d6+"</td> <td>"+d7+"</td></tr>");
	        var tr3=$("<tr><td>星期一</td> <td>星期二</td> <td>星期三</td><td>星期四</td> <td>星期五</td> <td>星期六</td> <td>星期七</td></tr>");
	        var tr4=$("<tr><td>8:30-10:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
	        var tr5=$("<tr><td>10:30-12:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
	        var tr6=$("<tr><td>14:00-15:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
	        var tr7=$("<tr><td>15:30-17:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
	        var tr8=$("<tr><td>19:00-20:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
	        var tr9=$("<tr><td>20:30-22:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
	        tr1.appendTo(table);tr2.appendTo(table);tr3.appendTo(table);tr4.appendTo(table);tr5.appendTo(table);tr6.appendTo(table);tr7.appendTo(table);tr8.appendTo(table);tr9.appendTo(table);
	        $("#table").append("</tbody></table>");

    }
    //表格双击编辑
//查找编号
 function findId(name,arr,id){
		for(var i in arr){
			 if(name==arr[i][1]){
					id=arr[i][0];
					break;
			}
	   }
		return id;
 }
 function findName(name,arr,id){
		for(var i in arr){
			 if(id==arr[i][0]){
				 name=arr[i][1];
					break;
			}
	   }
		return name;
}
 function findCount(id,arr,count){
				 for(var i in arr){
				 if(id==arr[i][0]){
					 count=arr[i][2];
					 break;
				 }
				 }
				 return count;
 }
			//查找颜色
 function findColor(id,arr,bgcolor,fontcolor){
	 for(var i in arr){
		 if(id==arr[i][0]){
			  bgcolor=arr[i][2];
			  fontcolor=arr[i][3];
			  break;
		}
   }
	 return {bgcolor:bgcolor,fontcolor:fontcolor};
 }
//json查重
    function isRepeatTime(time,date,arr) {
        for(var i in arr){
            if(time==arr[i][3]&&date==arr[i][4]){
                delete arr[i];
            }
        }
    }
    //老师表操作
    $(".deleteIcon").click(function() {
    $(".black").css("display","none")
})
//教室表操作
$("#playBlack2").click(function() {
	 $(".chooseSel #roomsel").html("");
	for(var i=0;i<classroomarr.length;i++){
   	   $(".chooseSel #roomsel").append("<option value="+classroomarr[i][0]+">"+classroomarr[i][1]+"</option>")
      }
    $("#black2").css("display","block");
    $("#teaTable2").click(function() {
        $("#roomSel").css("display","none");
        //老师选择结果
        var roomsel=$('#roomsel option:selected').text();
		var classroomid=$('#roomsel option:selected').attr("value");
        //生成表格
        createTeatable(roomsel,"b");
        if(objJson){
        	makeb(objJson,classroomid);
        }
        $("#b").css("display","block");
        $("#b #toback").click(function(){
        	$("#b").css("display","none");
        	$("#roomSel").css("display","block");
        })
    })
})

  //老师表操作
$("#playBlack1").click(function() {
	 $(".chooseSel #teasel").html("");
	 for(var i=0;i<teacherarr.length;i++){
  	   $(".chooseSel #teasel").append("<option value="+teacherarr[i][0]+">"+teacherarr[i][1]+"</option>")
     }
    $("#black1").css("display","block");
    $("#teaTable1").click(function() {
        $("#teaSel").css("display","none");
        //老师选择结果
        var teasel=$('#teasel option:selected').text();
        var teacherId=$('#teasel option:selected').attr("value");
        //生成表格
        createTeatable(teasel,"a");
        //
        if(objJson){
        	makea(objJson,teacherId);
        }
        $("#a").css("display","block");
        $("#a #toback").click(function(){
        	$("#a").css("display","none");
        	$("#teaSel").css("display","block");
        })
        
    })
})
function createTeatable(teasel,id) {
    $("#"+id+"").html("");
    var table=$("<table cellpadding='0' cellspacing='0' summary="+teasel+"><tbody>");
    table.appendTo($("#"+id+""));
    var tr2=$("<tr><td rowspan='2'>"+teasel+"</td> <td>"+d1+"</td> <td>"+d2+"</td> <td>"+d3+"</td> <td>"+d4+"</td> <td>"+d5+"</td> <td>"+d6+"</td> <td>"+d7+"</td></tr>");
    var tr3=$("<tr><td>星期一</td> <td>星期二</td> <td>星期三</td><td>星期四</td> <td>星期五</td> <td>星期六</td> <td>星期七</td></tr>");
    var tr4=$("<tr><td>8:30-10:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr5=$("<tr><td>10:30-12:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr6=$("<tr><td>14:00-15:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr7=$("<tr><td>15:30-17:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr8=$("<tr><td>19:00-20:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    var tr9=$("<tr><td>20:30-22:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
    tr2.appendTo(table);tr3.appendTo(table);tr4.appendTo(table);tr5.appendTo(table);tr6.appendTo(table);tr7.appendTo(table);tr8.appendTo(table);tr9.appendTo(table);
    $("#table").append("</tbody></table>");
    $("#"+id+"").append("<p id='toback'>返回</p>")
    
}
    var aa=[];
//得到相对应的老师的纪录
function makea(arr,id) {
	aa=[];
    for(var i in arr){
    	if(id){
    		 for(var j=0;j<Object.getOwnPropertyNames(arr[i]).length+1;j++){
    			 if(arr[i][j]){
    				 console.info(arr[i][j][0]);
    				 if(arr[i][j][0]==id){
                         var ll=$.extend(true ,[],arr[i][j]);//强制值传递
                         ll.push(/[0-9]+/.exec(i));
                         aa.push(ll);
                     }
    			 }
    		 }
    	}  
    }
    //得到后填入表格
     for(var i=0;i<aa.length;i++){
         obj=maketable(aa[i]);
         var className=findName(name,classesarr,aa[i][1]);
         var roomName=findName(name,classroomarr,aa[i][2]);
         if(aa[i][1]==-1){
        	 className="放假";
         }else if(aa[i][1]==-2){
        	 className="自习";
         }else if(aa[i][1]==-3){
        	 className="复习";
         }else if(aa[i][1]==-4){
        	 className="补课";
         }else if(aa[i][1]==-5){
        	 className="就业指导";
         }else if(aa[i][1]==-6){
        	 className="测试";
         }
         $("#black_content #a").find("tr").eq(obj.a).find("td").eq(obj.b).append(className+"<"+roomName+">("+aa[i][5]+")");
     }
}
function makeb(arr,id) {
	aa=[];
    for(var i in arr){
    	if(id){
    		 for(var j=0;j<Object.getOwnPropertyNames(arr[i]).length+1;j++){
    			 if(arr[i][j]){
    				 if(arr[i][j][2]==id){
    					 var ll=$.extend(true ,[],arr[i][j]);//强制值传递
    					 ll.push(/[0-9]+/.exec(i));
    					 aa.push(ll);
    				 }
    			 }
    		 }
    	}  
    }
    //得到后填入表格
     for(var i=0;i<aa.length;i++){
         obj=maketable(aa[i]);
         var className=findName(name,classesarr,aa[i][1]);
         var teaName=findName(name,teacherarr,aa[i][0]);
         if(aa[i][1]==-1){
        	 className="放假";
         }else if(aa[i][1]==-2){
        	 className="自习";
         }else if(aa[i][1]==-3){
        	 className="复习";
         }else if(aa[i][1]==-4){
        	 className="补课";
         }else if(aa[i][1]==-5){
        	 className="就业指导";
         }else if(aa[i][1]==-6){
        	 className="测试";
         }
         $("#b").find("tr").eq(obj.a).find("td").eq(obj.b).append(className+"<"+teaName+">("+aa[i][5]+")");
     }
}
//得到每个的坐标
function maketable(arr) {
    var a,b;
    for(var i=0;i<arr.length;i++){
        var time=arr[3];
        switch (time){
            case "8:30-10:30":
                a=2;
                break;
            case "10:30-12:00":
                a=3;
                break;
            case "14:00-15:30":
                a=4;
                break;
            case "15:30-17:30":
                a=5;
                break;
            case "19:00-20:30":
                a=6;
                break;
            case "20:30-22:00":
                a=7;
                break;

        }
        var date=arr[4];
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

    
//得到CheckBox中的班级id
	function getCheckboxValue(checkbox) 
	{ 
	    if (!checkbox.length && checkbox.type.toLowerCase() == 'checkbox') 
	    { return (checkbox.checked)?checkbox.value:'';  } 
	     
	    if (checkbox[0].tagName.toLowerCase() != 'input' ||  
	        checkbox[0].type.toLowerCase() != 'checkbox') 
	    { return ''; } 
	 
	    var val = ""; 
	    var len = checkbox.length; 
	    for(i=0; i<len; i++) 
	    { 
	        if (checkbox[i].checked) 
	        { 
	            val += checkbox[i].value+","; 
	        } 
	    } 
	    return (val.length)?val:''; 
	}
    
 function finishedAll(){
	
      $.post("teachSchedule_insertCurri.action", {
        	objJson : JSON.stringify(objJson),
    	}, function(data) {
    		//alert(data.responseCode);
    		 if(data.responseCode==1){ 
 				alert("课表添加失败，请与管理员联系");
 				return;
 			}else{
 				alert("课表添加成功！！");
 			}
    		
    	});
      objJson={};
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
					$("#chapterList").append("<p style='color:#fff;margin-left:30px'>更多课程</p>")
					for(var i=0;i<chapter.length;i++){
						c=chapter[i].id+","+chapter[i].chapterName;
    					classesarr.push(c.split(","));
						$("#chapterList").append("<li><label><input type='radio' name='classes' onchange='changed()'><a href='javascript:void(0)' id='"+chapter[i].id+"'>"+chapter[i].chapterName+"</a></label></li>")
					}
					$("#chapterList").append("<li><label><a href='javascript:void(0)' id='nomore'>隐藏更多</a></label></li>")
					$("#nomore").click(function(){
						$("#chapterList").html("");
						$("#chapterList").append("<p style='color: #FFFFFF;margin-left:-30px'><a href='javascript:void(0)' onclick='getAllChapter("+bid+")'>更多课程</a></p>");
					})
				}
		});
		}
 function changed(){
	 
	 classes=$.trim($("input[name='classes']:checked").parent().text());
     classesid=$.trim($("input[name='classes']:checked").parent().find("a").attr("id"));
     $(".insertfinish").off();
    	 $(".insertfinish").one("click",(function () {
     		$(".insertchoose").animate({right:'-250px'});
             //文本框编辑
             if (!teacher && !classroom) {
                 inner = classes;
                 teacherid="-1";
                 classroomid="-1";
             }else if(classroom==""){
                 inner = classes+ '<br>[' + teacher + ']';
                 classroomid="-1";
             }else if(teacher==""){
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
						$("table td input").parent().css("color", "#fff");
                     $("table td input").parent().css("backgroundColor", "#000");
					} else if (classes=="放假"){
						$("table td input").parent().css("color", "#FE4044");
                     $("table td input").parent().css("backgroundColor", "#fff");
					} else if (classes=="测试"){
						$("table td input").parent().css("color", "#000");
                     $("table td input").parent().css("backgroundColor", "#B3BDCD");
					}
             	 banji = that.parent().parent().parent().attr('summary');
                 banji=$.trim(banji);
                 banji="'"+banji+"'";
                 time = that.parent().find('td').eq(0).text();time=$.trim(time);
                 date = $('table tr').eq(1).find('td').eq(column).text();date=$.trim(date);
                 b = [teacherid,classesid,classroomid ,time, date];
                 if(typeof objJson[banji]=="undefined"){
                     objJson[banji]=[];
                 }
                 isRepeatTime(time,date,objJson[banji]);
                 inner = $.trim(inner);
                 //objJson[banji].push(b);
                 //判断在这个阶段是否有课程
                 $.post("isHaveCurri.action",{
             		bid : banji,date :date,time:time,
             	}, function(data) {
             			 if(data.responseCode==1){ 
            				 $("table td input").parent().css("backgroundColor", "#FFFFFF");
             				$("table td input").parent().html("");
             				alert("此班级在该时间段已有课程,填写课程不成功！");
             				return;
             			}else{
             				objJson[banji].push(b);
             				$("table td input").parent().html(inner);
             			}
             	});
             }else{
              
                 $("table td input").parent().css("backgroundColor", "#FFFFFF");
     			$("table td input").parent().html("");
     		   alert("填写课程不成功");
             }
         }));
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
