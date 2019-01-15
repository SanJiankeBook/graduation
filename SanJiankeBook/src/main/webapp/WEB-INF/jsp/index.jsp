<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	//					http				://		localhost			:	8081				/SpringMvc_Book/
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html >
<head id="Head1">



<title>IT类专业书籍</title>

<script type="text/javascript" src="js/xiaoshuo.js"></script>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript" async="" src="js/js.js"></script>
<script type="text/javascript" src="js/wap.js"></script>
<script type="text/javascript" src="js/logger.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/bdsstyle.css" />
<link rel="stylesheet" type="text/css" href="css/index.css" />
</head>
<base href="<%=basePath%>">
<body>
	<iframe style="display: none;" frameborder="0"></iframe>
	<div id="bdshare_s" style="display: block;">
		<iframe id="bdsIfr"
			style="position: absolute; display: none; z-index: 9999; top: 84px; left: 1044.08px; height: 314px; width: 212px;"
			frameborder="0"></iframe>

		<div id="bdshare_l" style="display: none; left: 1044.08px; top: 84px;">
			<div id="bdshare_l_c" style="visibility: hidden;">
				<h6>分享到</h6>
				<ul>
					<li><a href="#" class="bds_mshare mshare">一键分享</a></li>
					<li><a href="#" class="bds_qzone qqkj">QQ空间</a></li>
					<li><a href="#" class="bds_tsina xlwb">新浪微博</a></li>
					<li><a href="#" class="bds_bdysc bdysc">百度云收藏</a></li>
					<li><a href="#" class="bds_renren rrw">人人网</a></li>
					<li><a href="#" class="bds_tqq txwb">腾讯微博</a></li>
					<li><a href="#" class="bds_bdxc bdxc">百度相册</a></li>
					<li><a href="#" class="bds_kaixin001 kxw">开心网</a></li>
					<li><a href="#" class="bds_tqf txpy">腾讯朋友</a></li>
					<li><a href="#" class="bds_tieba bdtb">百度贴吧</a></li>
					<li><a href="#" class="bds_douban db">豆瓣网</a></li>
					<li><a href="#" class="bds_tsohu shwb">搜狐微博</a></li>
					<li><a href="#" class="bds_bdhome bdhome">百度新首页</a></li>
					<li><a href="#" class="bds_sqq sqq">QQ好友</a></li>
					<li><a href="#" class="bds_thx thx">和讯微博</a></li>
					<li><a href="#" class="bds_more">更多...</a></li>
				</ul>
				<p>
					<a href="#" class="goWebsite">百度分享</a>
				</p>
			</div>
		</div>
	</div>
	<div id="wrapper">


		<div class="ywtop">
			<div class="ywtop_con" id="top">
				<div class="ywtop_sethome">
					
				</div>
				<div class="ywtop_addfavorite">
					<a href="#">IT类专业书籍</a>
				</div>

				<div class="nri" id="loger">

					<!-- <form name="mylogin" id="mylogin" method="post"
						action="userLogin"
						> -->
					<div class="cc" style="display: none;">
						<div class="txt">账号：</div>
						<div class="inp">
							<input name="uname" id="uname" type="text" />

						</div>
					</div>
					<div class="cc" style="display:none; ">
						<div class="txt">密码：</div>
						<div class="inp">

							<input name="upassword" id="upassword" type="password" />

						</div>
					</div>
					<div class="frii">
						<!--  <button id="log">登录</button>-->
						<a href="userlogininfo"><span>登陆</span></a>
<!-- 						<input type="button" id="log" onClick="logger()" value="登录" />
 -->					</div>

					<div class="ccc">
						<div class="txtt"></div>
						<div class="txtt">

							<a href="toregister">用户注册</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="toauthor">作家注册</a>
						</div>
					</div>
					<!-- </form> -->



				</div>
				<div id="nimabi" style="display: none">
					
				</div>
			</div>
		</div>



		<div class="header">
			<div class="header_logo">
				<a href="toindex_zpd">IT类专业书籍</a>
			</div>
			<div class="header_search">
				<script>
					search();
				</script>
				<script type="text/javascript">
					(function() {
						document
								.write(unescape('%3Cdiv id="bdcs"%3E%3C/div%3E'));
						var bdcs = document.createElement('script');
						bdcs.type = 'text/javascript';
						bdcs.async = true;
						bdcs.src = 'http://znsv.baidu.com/customer_search/api/js?sid=3677118700255927857'
								+ '&plate_url='
								+ encodeURIComponent(window.location.href)
								+ '&t=' + Math.ceil(new Date() / 3600000);
						var s = document.getElementsByTagName('script')[0];
						s.parentNode.insertBefore(bdcs, s);
					})();
				</script>
				<div id="bdcs">
					<div class="bdcs-container">
						<meta http-equiv="x-ua-compatible" content="IE=9" />
						<!-- 嵌入式 -->
						<div class="bdcs-main bdcs-clearfix" id="default-searchbox">
							<div class="bdcs-search bdcs-clearfix" id="bdcs-search-inline">

								<form action="tosousuo" method="post" target="_blank"
									class="bdcs-search-form" autocomplete="off"
									id="bdcs-search-form">
									<input name="s" value="3677118700255927857" type="hidden" /> <input
										name="entry" value="1" type="hidden" /> <input name="nname"
										class="bdcs-search-form-input" id="bdcs-search-form-input"
										placeholder="请输入书名或作者" autocomplete="off"
										style="height: 36px; line-height: 36px;" type="text" /> <input
										class="bdcs-search-form-submit " id="bdcs-search-form-submit"
										value="搜索" type="submit" />
								</form>
							</div>
							<div class="bdcs-search-sug" id="bdcs-search-sug"
								style="top: 85px; width: 319px;">
								<ul class="bdcs-search-sug-list" id="bdcs-search-sug-list"></ul>
							</div>


						</div>
					</div>
				</div>


<!-- 
				<div class="userpanel">
					<script>
						banner();
					</script>
					<div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare">
						<a class="bds_tsina" title="分享到新浪微博" href="#"></a> <a
							class="bds_renren" title="分享到人人网" href="#"></a> <span
							class="bds_more"></span> <a class="shareCount" href="#"
							title="累计分享57次">57</a>
					</div>
					<script type="text/javascript" id="bdshare_js"
						data="type=tools&amp;uid=0" src="js/bds_s_v2.js"></script>

					<script type="text/javascript">
						document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion="
								+ Math.ceil(new Date() / 3600000)
					</script>
				</div> -->

			</div>
			<div class="clear"></div>
			<div class="nav">
				<ul>
					<li><a href="toindex_zpd">首页</a></li>
					<li><a rel="nofollow" href="mybook">我的书架</a></li>
					<li><a href="toindex_Type/${list[0].tname}">${list[0].tname}</a></li>
					<li><a href="toindex_Type/${list[1].tname}">${list[1].tname}</a></li>
					<li><a href="toindex_Type/${list[2].tname}">${list[2].tname}</a></li>
					<li><a href="toindex_Type/${list[3].tname}">${list[3].tname}</a></li>
					<li><a href="toindex_Type/${list[4].tname}">${list[4].tname}</a></li>
					<li><a href="toindex_Type/${list[5].tname}">${list[5].tname}</a></li>
					<li><a href="authorPrefectrue1">作者专区</a></li>
					<li><a href="toindex_type">排行榜单</a></li>
					<li><a href="quanben">全本小说</a></li>
					<li><a  href="toReadRecord">阅读记录</a></li>
				</ul>
			</div>
			<div id="main">
				<div id="hotcontent">
					<script type="text/javascript">
						list();
					</script>
					<div class="l">
					<c:forEach items="${novel1}" var="novel1">
						<div class="item">
                                <div class="image">
                                    <a href="toindex_id/${novel1.nid}">
                                        <img src="${novel1.npicture}" alt="${novel1.nname }" width="120" height="150"></a></div>
                                <dl>
                                    <dt><span>
                                      ${novel1.pan_name }
                                    </span><a href="toindex_id/${novel1.nid}">
                                       ${novel1.nname }
                                    </a></dt>
                                    <dd>
                                    ${novel1.ndescription }
                                    </dd>
                                </dl>
                                <div class="clear">
                                </div>
                            </div>
					</c:forEach>

					</div>
					<div class="r">
						<h2>经典推荐</h2>
						<ul>
						<c:forEach items="${novel2 }" var="novel2">
								<li><span class="s1">${novel2.tname }</span><span class="s2"><a href="toindex_id/${novel2.nid}">${novel2.nname }</a></span><span class="s5">${novel2.pan_name }</span></li>
						</c:forEach>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<div class="dahengfu">
					<script>
						list1();
					</script>
				</div>



				<!-- 显示页面 -->
				<div id="novelslist1" class="novelslist">

					<!-- 玄幻   -->
					<div class="content" >
						<h2>${list[0].tname}</h2>
						<div class="top">
						<c:forEach items="${Alllist}" var="Alllist">
							<div style="height: 82;width: 318px ;float: left;" >
							<div class="image">
								<img src="${Alllist.npicture}" alt="${Alllist.nname}" width="67"
									height="82" />
							</div>
							<dl>
								<dt>
									<a href="toindex_id/${Alllist.nid}"> ${Alllist.nname}</a>
								</dt>
								<dd>${Alllist.aname}</dd>
								

							</dl>
							</div>
							</c:forEach>
							<div class="clear"></div>
						</div>
						<ul>
							<c:forEach items="${Alllist}" var="Alllist">
								<li><a href="toindex_id/${Alllist.nid}">${Alllist.nname}
								</a>/${Alllist.aname }</li>
							</c:forEach>
						</ul>
					</div>

					<!-- 修仙 -->
					<div class="content">
						<h2>${list[1].tname}</h2>
						<div class="top">
						
							<c:forEach items="${Alllist1}" var="Alllist1">
							<div style="height: 82;width: 318px ;float: left;" >
							<div class="image">
								<img src="${Alllist1.npicture}" alt="${Alllist1.nname}" width="67"
									height="82" />
							</div>
							<dl>
								<dt>
									<a href="toindex_id/${Alllist1.nid}"> ${Alllist1.nname}</a>
								</dt>
								<dd>${Alllist1.aname}</dd>
								

							</dl>
							</div>
							</c:forEach>
							
							
							<div class="clear"></div>
						</div>
						<ul>
							<c:forEach items="${Alllist1}" var="Alllist1">
								<li><a href="toindex_id/${Alllist1.nid}">${Alllist1.nname}
								</a>/${Alllist1.aname }</li>
							</c:forEach>
						</ul>
					</div>
					<div class="content">
						<h2>${list[2].tname}</h2>
						<div class="top">
						
						<c:forEach items="${Alllist2}" var="Alllist2">
							<div style="height: 82;width: 318px ;float: left;" >
							<div class="image">
								<img src="${Alllist2.npicture}" alt="${Alllist2.nname}" width="67"
									height="82" />
							</div>
							<dl>
								<dt>
									<a href="toindex_id/${Alllist2.nid}"> ${Alllist2.nname}</a>
								</dt>
								<dd>${Alllist2.aname}</dd>
								

							</dl>
							</div>
							</c:forEach>
							
							<div class="clear"></div>
						</div>

						<ul>

							<c:forEach items="${Alllist2}" var="Alllist2">
								<li><a href="toindex_id/${Alllist2.nid}">${Alllist2.nname}
								</a>/${Alllist2.aname }</li>
							</c:forEach>

						</ul>
					</div>
					<div class="clear"></div>
				</div>

				<div id="novelslist2" class="novelslist">
					<div class="content">
						<h2>${list[3].tname}</h2>


						<div class="top">
						
						<c:forEach items="${Alllist3}" var="Alllist3">
							<div style="height: 82;width: 318px ;float: left;" >
							<div class="image">
								<img src="${Alllist3.npicture}" alt="${Alllist3.nname}" width="67"
									height="82" />
							</div>
							<dl>
								<dt>
									<a href="toindex_id/${Alllist3.nid}"> ${Alllist3.nname}</a>
								</dt>
								<dd>${Alllist3.aname}</dd>
								

							</dl>
							</div>
							</c:forEach>
							
							<div class="clear"></div>
						</div>

						<ul>

							<c:forEach items="${Alllist3}" var="Alllist3">
								<li><a href="toindex_id/${Alllist3.nid}">${Alllist3.nname}
								</a>/${Alllist3.aname }</li>
							</c:forEach>

						</ul>
					</div>
					<div class="content">

						<h2>${list[4].tname}</h2>


						<div class="top">
						
						<c:forEach items="${Alllist4}" var="Alllist4">
							<div style="height: 82;width: 318px ;float: left;" >
							<div class="image">
								<img src="${Alllist4.npicture}" alt="${Alllist4.nname}" width="67"
									height="82" />
							</div>
							<dl>
								<dt>
									<a href="toindex_id/${Alllist4.nid}"> ${Alllist4.nname}</a>
								</dt>
								<dd>${Alllist4.aname}</dd>
								

							</dl>
							</div>
							</c:forEach>
							
							<div class="clear"></div>
						</div>

						<ul>

							<c:forEach items="${Alllist4}" var="Alllist4">
								<li><a href="toindex_id/${Alllist4.nid}">${Alllist4.nname}
								</a>/${Alllist4.aname }</li>
							</c:forEach>

						</ul>
					</div>
					<div class="content border">
						<h2>${list[5].tname}</h2>


						<div class="top">
						
						<c:forEach items="${Alllist5}" var="Alllist5">
							<div style="height: 82;width: 318px ;float: left;" >
							<div class="image">
								<img src="${Alllist5.npicture}" alt="${Alllist5.nname}" width="67"
									height="82" />
							</div>
							<dl>
								<dt>
									<a href="toindex_id/${Alllist5.nid}"> ${Alllist5.nname}</a>
								</dt>
								<dd>${Alllist5.aname}</dd>
								

							</dl>
							</div>
							</c:forEach>
							<div class="clear"></div>
						</div>

						<ul>

							<c:forEach items="${Alllist5}" var="Alllist5">
								<li><a href="toindex_id/${Alllist5.nid}">${Alllist5.nname}
								</a>/${Alllist5.aname }</li>
							</c:forEach>

						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<div id="newscontent">
					<!-- <div class="l">
						<h2>最近更新小说列表</h2>
						<ul>

							<li><span class="s1">[武侠仙侠]</span><span class="s2"><a
									href="http://www.bixia.org/40_40600/" target="_blank">一言通天</a></span><span
								class="s3"><a
									href="http://www.bixia.org/40_40600/2418830.html"
									target="_blank">第725章 神木峡之行</a></span><span class="s4">黑弦</span><span
								class="s5">03-03</span></li>



						</ul>
					</div> -->
					<!-- <div class="r">
						<h2>最新入库小说</h2>
						<ul>

							<li><span class="s1">[都市言情]</span><span class="s2"><a
									href="http://www.bixia.org/51_51392/">善良的小姨子</a></span><span
								class="s5">苦哈哈</span></li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
			</div>

			<div id="firendlink">友情连接：</div>
 -->

			<div class="footer">
				<div class="footer_link"></div>
				<div class="footer_cont">
					<p>Copyright © 2018 IT类专业书籍</p>
					<script>
						footer();
					</script>
				</div>
			</div>
		</div>
</body>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url : "checkloging",
			type : "POST",
			dataType : "JSON",//客户端返回过来的数据类型
			data : {},
			success : function(data) {
				if(data.status=="1"){
					$("#loger").hide(); //隐藏
					$("#nimabi").show();
					$("#nimabi").html('<a href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hi,&nbsp;'+data.uname +'</a> | <a href="showUser" target="_top">查看资料</a> | <a href="#" onClick="uploging()">退出登录</a>&nbsp;');
				}
			}

		});
		
	})
						function logger() {
						if($("#uname").val()==null | $("#uname").val()==""){
							alert("请输入用户名");
							return ;
						}
						if($("#upassword").val()==null | $("#upassword").val()==""){
							alert("请输入密码");
							return ;
						}
							$.ajax({
										url : "logger",
										type : "POST",
										dataType : "JSON",//客户端返回过来的数据类型
										data : {
											'uname' : $("#uname").val(),
											'upassword' : $("#upassword").val(),
										},
										success : function(data) {
											if (data.status == -2) {
												//response.sendRedirect("500.jsp");
												alert("请登录");
											} else if (data.status == 1) {
												$("#nimabi").show();
												$("#loger").hide() //隐藏
												$("#nimabi").html('<a href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hi,&nbsp;'+data.uname +'</a> | <a href="showUser" target="_top">查看资料</a> | <a href="#" onClick="uploging()">退出登录</a>&nbsp;');
											} else {
												alert("用户名或密码错误");
											}
										}

									});
						}
					</script>
			
</html>