$(function(){
	$('#index_content').tabs('add',{
		title:'三剑客文学',
		href:'back/sanJianKe.html',
		fit:true,
	});

	$('#novel_menu1').tree({
		onClick:function(node){
			var id = node.id; //获取点击的节点
			var title= "三剑客文学";
			var href = "back/sanJianKe.html";
			var tabObj = $('#index_content' );

			if( id == "authorManage"){	//信息管理
				if( tabObj.tabs("exists","作家管理")){
					//选中
					return ;
				}else{
					return;
				}
			}else if(id=="showAllAuthor"){ //添加商品
				if( tabObj.tabs("exists","所有作家")){
					//选中
					tabObj.tabs("select","所有作家");
					return ;
				}else{
					title="所有作家";
					href="back/showAllAuthor.jsp";
				}
			}

			tabObj.tabs('add',{
				title:title,
				href:href,
				fit:true,
				closable:true
			});
		}
	});
	$('#novel_menu2').tree({
		onClick:function(node){
			var id = node.id; //获取点击的节点
			var title= "三剑客文学";
			var href = "back/sanJianKe.html";
			var tabObj = $('#index_content' );
			console.info(id);

			if( id=="showAllNovel" ){	
				if( tabObj.tabs("exists","所有小说")){
					//选中
					tabObj.tabs("select","所有小说");
					return ;
				}else{
					title="所有小说";
					href="back/showAllNovel.jsp";
				}
			}

			tabObj.tabs('add',{
				title:title,
				href:href,
				fit:true,
				closable:true
			});
		}
	});
	
	
	$('#novel_menu3').tree({
		onClick:function(node){
			var id = node.id; //获取点击的节点
			var title= "三剑客文学";
			var href = "back/sanJianKe.html";
			var tabObj = $('#index_content' );
			console.info(id);

			if( id=="showAllUser" ){	
				if( tabObj.tabs("exists","所有用户")){
					//选中
					tabObj.tabs("select","所有用户");
					return ;
				}else{
					title="所有用户";
					href="back/showAllUser.jsp";
				}
			}

			tabObj.tabs('add',{
				title:title,
				href:href,
				fit:true,
				closable:true
			});
		}
	});
	
	
	$('#novel_menu4').tree({
		onClick:function(node){
			var id = node.id; //获取点击的节点
			var title= "三剑客文学";
			var href = "back/sanJianKe.html";
			var tabObj = $('#index_content' );
			console.info(id);

			if( id=="checkNovel_manage" ){	
				if( tabObj.tabs("exists","审查上传小说信息")){
					//选中
					tabObj.tabs("select","审查上传小说信息");
					return ;
				}else{
					title="审查上传小说信息";
					href="back/CheckAuthorNovel.jsp";
				}
			}else if( id=="uncheckNovel_manage" ){	
				if( tabObj.tabs("exists","待审查小说")){
					//选中
					tabObj.tabs("select","待审查小说");
					return ;
				}else{
					title="待审查小说";
					href="back/UncheckNovel.jsp";
				}
			}else if( id=="uncheckNovelChapter_manage" ){	
				if( tabObj.tabs("exists","待审查小说章节")){
					//选中
					tabObj.tabs("select","待审查小说章节");
					return ;
				}else{
					title="待审查小说章节";
					href="back/UncheckNovelChapter.jsp";
				}
			}

			tabObj.tabs('add',{
				title:title,
				href:href,
				fit:true,
				closable:true
			});
		}
	});


	/*$("#btn_foods").click(function(){
		$.ajax({
			url:"addfoods.action",
			data:"op=addfoods",
			type:"POST",
			dataType:"JSON",
			success:function(data){
				if( data.code == 1 ){
					alert(1);
				}
			}
		});
	});*/

});





