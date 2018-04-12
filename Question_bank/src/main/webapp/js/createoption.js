		var version="";   //版本
  		var semester="";	//学期
  		var cid="";			//课程编号	
  		//生成班级列表
  		function createClassOption(){
  			semester=$('input[name="semester"]:checked').val();
  			$.ajax({
  				url:"/Question_bank/initData_examClass.action",
  				type:"post",
  				data:{"semester":semester},
  				datatype:"json",
  				success:function(examineeClassList){
  					var optionstr="";	
  					if(examineeClassList.responseCode!=1){
  						optionstr+="<option value='0'  name='className'>请选择班级</option>";
  						for(var i=0,len=examineeClassList.obj.length;i<len;i++){
  							optionstr+="<option value='"+examineeClassList.obj[i].id+"' name='className'>"+examineeClassList.obj[i].className+"</option>";
  							if(examineeClassList.obj[i].currentUse==1){
  								currentUse=examineeClassList.obj[i].id;
  							}
  						}
  					}else{
  						optionstr+="<option value=0 >没有数据</option>"
  					}	
  					$('#classes').html(optionstr);
  				}
  			});
  		}
  		function createClassOption2(semester){
  			/*semester=$('input[name="semester2"]:checked').val();*/
  			$.ajax({
  				url:"/Question_bank/initData_examClass.action",
  				type:"post",
  				data:{"semester":semester},
  				datatype:"json",
  				success:function(examineeClassList){
  					var optionstr="";	
  					if(examineeClassList.responseCode!=1){
  						optionstr+="<option value='0'  name='className'>请选择班级</option>";
  						for(var i=0,len=examineeClassList.obj.length;i<len;i++){
  							optionstr+="<option value='"+examineeClassList.obj[i].id+"' name='className2'>"+examineeClassList.obj[i].className+"</option>";
  							if(examineeClassList.obj[i].currentUse==1){
  								currentUse=examineeClassList.obj[i].id;
  							}
  						}
  					}else{
  						optionstr+="<option value=0 >没有数据</option>"
  					}	
  					$('#exam_show_anotherClass_class').html(optionstr);
  				}
  			});
  		}
  		//生成版本列表
  		function createVersionOption(){
  		var currentUse="";
  		 $.ajaxSettings.async = false; 
  			$.ajax({
  				url:"/Question_bank/initData_version.action",
  				type:"post",
  				datatype:"json",
  				success:function(examineeClassList){
  					var optionstr="";	
  					if(examineeClassList.responseCode!=1){
  						for(var i=0,len=examineeClassList.obj.length;i<len;i++){
  							optionstr+="<option value='"+examineeClassList.obj[i].id+"' name='className'>"+examineeClassList.obj[i].editionName+"</option>";
  							if(examineeClassList.obj[i].currentUse==1){
  								currentUse=examineeClassList.obj[i].id;
  							}
  						}
  					}else{
  						optionstr+="<option value=0 >没有数据</option>"
  					}	
  					$("#version").html(optionstr);
  					$("#version option[value="+currentUse+"]").attr("selected",true)
  					
  				}
  				
  			});
  			
  		}
  		//生成科目列表
  		function createSelectOption(){
  		 $.ajaxSettings.async = false; 
  			$.ajax({
  				url:"/Question_bank/initData_subject.action",
  				type:"post",
  				//data:{"editionId":version,"semester":semester},
  				datatype:"json",
  				success:function(examineeClassList){
  					var optionstr="";
  					if(examineeClassList.responseCode!=1){
  						for(var i=0;i<examineeClassList.obj.length;i++){
  						optionstr+="<option value='"+examineeClassList.obj[i].id+"'>"+examineeClassList.obj[i].subjectName+"</option>";
  						}
  					}else{
  						optionstr+="<option value=0 >没有数据</option>"
  					}
  					
  					$("#subject").html(optionstr);
  				}
  			});
  		}
  		//生成章节列表   毕设版
  		function createChapterOption(subjectId){
  	  		 $.ajaxSettings.async = false; 
  	  			$.ajax({
  	  				url:"/Question_bank/assessment_initPointInfo.action",
  	  				type:"post",
  	  				//data:{"subjectId":subjectId},
  	  				data:{"cid":subjectId},
  	  				datatype:"json",
  	  				success:function(examineeClassList){
  	  					var optionstr="";
  	  					if(examineeClassList.responseCode!=1 &&  examineeClassList.total!=0){
  	  						for(var i=0,len=examineeClassList.rows.length;i<len;i++){
  	  						optionstr+="<option value='"+examineeClassList.rows[i].pid+"'>"+examineeClassList.rows[i].pcontent+"</option>";
  	  						}
  	  					}else{
  	  						optionstr+="<option value=0 >没有数据</option>"
  	  					}
  	  					
  	  					$("#chapter").html(optionstr);
  	  				}
  	  			});
  	  		}
  		
  		//生成所有科目列表
  		function findAllSubject(){
  			$.ajaxSettings.async = false; 
  			$.ajax({
  				url:"/Question_bank/initData_subject.action",
  				type:"post",
  				data:{"editionId":version,"semester":semester},
  				datatype:"json",
  				success:function(examineeClassList){
  					var optionstr="";
  					if(examineeClassList.responseCode!=1){
  						
  						for(var i=0,len=examineeClassList.obj.length;i<len;i++){
  						optionstr+="<option value='"+examineeClassList.obj[i].id+"'>"+examineeClassList.obj[i].subjectName+"</option>";
  						}
  					}else{
  						optionstr+="<option value=0 >没有数据</option>"
  					}
  					
  					$("#subject").html(optionstr);
  				}
  			});
  			
  		}
  		//查出所有班级信息
  		function findexamineeClass(){
  	  		semester=$('input[name="semester"]:checked').val();
  	  		$.ajaxSettings.async = false; 
  	  			$.ajax({
  	  				url:"/Question_bank/initData_examClass.action",
  	  				type:"post",
  	  				datatype:"json",
  	  				success:function(examineeClassList){
  	  					var optionstr="";	
  	  					if(examineeClassList.responseCode!=1){
  	  						for(var i=0,len=examineeClassList.obj.length;i<len;i++){
  	  							optionstr+="<option value='"+examineeClassList.obj[i].id+"' name='className'>"+examineeClassList.obj[i].className+"</option>";
  	  							if(examineeClassList.obj[i].currentUse==1){
  	  								currentUse=examineeClassList.obj[i].id;
  	  							}
  	  						}
  	  					}else{
  	  						optionstr+="<option value=0 >没有数据</option>"
  	  					}	
  	  					$('#classes').html(optionstr);
  	  				}
  	  			});
  	  	}
  		