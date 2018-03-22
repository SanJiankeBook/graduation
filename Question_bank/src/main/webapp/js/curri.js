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
    

	//得到相对应的老师的纪录
    function makea(arr,teachername,id) {
       	 for(var i=0;i<arr.length;i++){
       		if(arr[i].username==teachername){
       			var ll=[];
       			for(var j in arr[i]){
       				ll.push(arr[i][j]);
       			}
                a.push(ll);
       		}
        }
       	 
        for(var i=0;i<a.length;i++){
            obj=maketable(a[i]);
            if(a[i][1]=='无'){
            	a[i][1]='';
            }
            if(a[i][3]=='无'){
            	a[i][3]='';
            }
            if(a[i][4]=='无'){
            	a[i][4]='';
            }
            console.info(obj);
            $('#'+id+'').find("tr").eq(obj.a).find("td").eq(obj.b).append(a[i][1]+"<br>"+a[i][3]+"<br>"+a[i][4]).css({"background-color": a[i][0],"color":a[i][6]});
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
            
            console.info(date);
           
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
  /* function CreateTable(temp,teachername,id)
    {
	 var table=$("<table cellpadding='0' cellspacing='0'  id="+id+"><tbody>");
        table.appendTo($("#curriFind_form"));
        var tr1=$("<tr><td>"+teachername+"</td> <td>"+d1+"</td> <td>"+d2+"</td> <td>"+d3+"</td> <td>"+d4+"</td> <td>"+d5+"</td> <td>"+d6+"</td> <td>"+d7+"</td></tr>");
        var tr2=$("<tr><td>8:30-10:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
        var tr3=$("<tr><td>10:30-12:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
        var tr4=$("<tr><td>14:00-15:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
        var tr5=$("<tr><td>15:30-17:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
        var tr6=$("<tr><td>19:00-20:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
        var tr7=$("<tr><td>20:30-22:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>");
        tr1.appendTo(table);tr2.appendTo(table);tr3.appendTo(table);tr4.appendTo(table);tr5.appendTo(table);tr6.appendTo(table);tr7.appendTo(table);
        $("#curriFind_form").append("</tbody></table>");
        
        //makea(temp,allTeacherName[i],id);	
    }*/
    
    function CreateTable(temp,teachername,id){
	 var table="<table cellpadding='0' cellspacing='0'  id="+id+"><tbody>";
	 table+="<tr><td id='col'>"+teachername+"</td> <td id='col'>"+d1+"</td> <td id='col'>"+d2+"</td> <td id='col'>"+d3+"</td> <td id='col'>"+d4+"</td> <td id='col'>"+d5+"</td> <td id='col'>"+d6+"</td> <td id='col'>"+d7+"</td></tr>";
	 table+="<tr><td id='col'>8:30-10:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>";
	 table+="<tr><td id='col'>10:30-12:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>";
	 table+="<tr><td id='col'>14:00-15:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>";
	 table+="<tr><td id='col'>15:30-17:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>";
	 table+="<tr><td id='col'>19:00-20:30</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr>";
	 table+="<tr><td id='col'>20:30-22:00</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td></tr></tbody></table>";
       $("#curriFind_form").append($(table));
        makea(temp,teachername,id);	
    }