    //初始化一些值
    var a,obj_text,that;
    var objJson=[];
    var time,date,column,b;
    var classroom,classes,teacher,banji,inner;
    var count=1;

    //显示班级显示
    $(".classSel .select").click(function () {
        $(".classche").css("display","block");
    })

    //得到选择的班级
    $(".classche a").click(
        function () {
            $(this).parent().css("display","none");
            a=[];
            $('.classche input:checked').each(function () {
                a.push($(this).next().html());
            });
            console.log(a);
        }
    )

    //获取第几周
    $('.weekche option:selected').text();

    //生成后几天的信息
    function gettheday() {
        var d1=$('.timeSel input').val();
        var d= new Date(d1);//这里日期是传递过来的，可以自己改
        d.setDate(d.getDate() + 1);//天数+1
        var d2=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//新日期
        d.setDate(d.getDate() + 1);//天数+1
        var d3=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
        d.setDate(d.getDate() + 1);//天数+1
        var d4=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
        d.setDate(d.getDate() + 1);//天数+1
        var d5=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
        d.setDate(d.getDate() + 1);//天数+1
        var d6=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
        d.setDate(d.getDate() + 1);//天数+1
        var d7=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
    }

    //生成表格
    $('#makeTable').click(function () {
        for(var key in objJson){
            
        }
    })

    //表格双击编辑
    $("table td").dblclick(function() {
        $(".choose").animate({right:'0px'});
        time="";
        date="";
        b="";
        classroom="";
        classes="";
        teacher="";
        var row = $(this).parent().index() + 1; // 行位置
        var col = $(this).index() + 1; // 列位置
        if ((row > 3) && (col > 1)) {
            $(".choose input:radio").removeAttr('checked');
            obj_text = $("table").find("input:text");    // 判断单元格下是否有文本框
            if (!obj_text.length) {
                //如果没有文本框，则添加文本框使之可以编辑
                $(this).html("<input type='text' value='" + $.trim($(this).text()) + "' autofocus='autofocus'>");
            }
        }
        that=$(this);
        column=col-1;
        $(this).one("keypress", (function () {
            if (window.event.keyCode == 13) {
                inner = $("table td input").val();
                if (inner == "") {
                    $("input:radio").attr("checked", false);
                    $("table td input").parent().html($("table td input").val());
                }else{
                    teacher = (/\[[\u4e00-\u9fa5]+\]/).exec(inner);
                    teacher = (/[\u4e00-\u9fa5]+/).exec(teacher);
                    classes = (/[a-zA-Z\u4e00-\u9fa5 ]*\[/).exec(inner);
                    classes = (/[a-zA-Z\u4e00-\u9fa5 ]*/).exec(classes);
                    classroom =(/\d+/).exec(inner);
                    inner = classes+ '<br>[' + teacher + ']<' +classroom+ '>';
                }
                if ((/姜平/).exec(inner)) {
                    $("table td input").parent()
                        .css("backgroundColor", 'saddlebrown');
                } else if ((/张影/).exec(inner)) {
                    $("table td input").parent()
                        .css("backgroundColor", 'blue');
                } else if ((/周海军/).exec(inner)) {
                    $("table td input").parent()
                        .css("backgroundColor", 'orange');
                } else if ((/付鹏程/).exec(inner)) {
                    $("table td input").parent()
                        .css("backgroundColor", 'red');
                } else if ((/罗路/).exec(inner)) {
                    $("table td input").parent()
                        .css("backgroundColor", 'darkorange');
                } else {
                    $("table td input").parent()
                        .css("backgroundColor", 'white');
                }
                banji = that.parent().parent().parent().attr('summary');
                time = that.parent().find('td').eq(0).text();
                date = $('table tr').eq(1).find('td').eq(column).text();
                if (classes!="" && classroom!="") {
                    b = '{"banji":' + $.trim(banji) + ',"teacher":' + $.trim(teacher)+ ',"classes":' +$.trim(classes) + ',"classroom":' +$.trim(classroom) + ',"time":' + $.trim(time) + ',"date":' + $.trim(date) + '}';
                    if(classes!="null"){
                        objJson.push(b);
                    }
                    inner = $.trim(inner);
                    $("table td input").parent().html(inner);
                    console.log(objJson);
                }else{
                    // alert('没有填写教室或课程');
                }
                if(isRepeat(objJson)==true){
                    objJson.splice(objJson.length-1,1);
                    console.log(objJson);
                }

            }

        }))
        if(count==1){
            $("input[name='teacher']").change(function() {
                teacher=$.trim($("input[name='teacher']:checked").parent().text());
            })
            $("input[name='classes']").change(function() {
                classes=$.trim($("input[name='classes']:checked").parent().text());
            })
            $("input[name='classroom']").change(function() {
                classroom=$.trim( $("input[name='classroom']:checked").parent().text());
            })
            count=2;
        }
        if(count==2){
            change();
            count=1;
        }
        function change() {
            $(".finish").one("click",(function () {
                    //文本框编辑
                    inner = classes+ '<br>[' + teacher + ']<' +classroom+ '>';
                    if (!$("input[name='teacher']:checked").parent().text()) {
                        inner = classes + '<br><' + classroom + '>';
                    }
                    if (!classes || !classroom) {
                        return;
                    }
                    $("table td input").parent().css("backgroundColor", $("input[name='teacher']:checked").parent().find("i").css("backgroundColor"));
                    banji = that.parent().parent().parent().attr('summary');
                    time = that.parent().find('td').eq(0).text();
                    date = $('table tr').eq(1).find('td').eq(column).text();
                    if (classes!="" && classroom!="") {
                        b = '{"banji":' + $.trim(banji) + ',"teacher":' + $.trim(teacher)+ ',"classes":' +$.trim(classes) + ',"classroom":' +$.trim(classroom) + ',"time":' + $.trim(time) + ',"date":' + $.trim(date) + '}';
                        if(classes!="null"){
                            objJson.push(b);
                        }
                        inner = $.trim(inner);
                        $("table td input").parent().html(inner);
                        console.log(objJson);
                    }else{
                        // alert('没有填写教室或课程');
                    }
                    if(isRepeat(objJson)==true){
                        objJson.splice(objJson.length-1,1);
                        console.log(objJson);
                    }
                })
            )
        }
    });
    //json查重
    function isRepeat(arr) {
        var hash = {};
        for(var i in arr) {
            if(hash[arr[i]])
            {
                return true;
            }
            // 不存在该元素，则赋值为true，可以赋任意值，相应的修改if判断条件即可
            hash[arr[i]] = true;
        }
        return false;
    }

    //老师表操作
    $("#playBlack").click(function () {
        $(".black").css("display","block")
    })
    $(".deleteIcon").click(function () {
        $(".black").css("display","none")
    })
    //生成表格
    $(".teaTable").click(function () {
        //老师选择结果
        var teasel=$('.teasel option:selected').text();

        //生成表格
    })
