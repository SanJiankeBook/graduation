<!-- 全本小说 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <meta http-equiv="Cache-Control" content="no-transform" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>经典全本小说排行榜_三剑客文学</title>
     <link href="css/style.css"      type="text/css" rel="stylesheet" />
     <link href="css/imageStyle.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="js/xiaoshuo.js"></script>
</head>
<body>
    
<script>login();</script>
<div class="header">
    <div class="header_logo"><a href="http://www.bixia.org">笔下文学</a></div>
    <div class="header_search"><script>search();</script></div>
    <div class="userpanel"><script>banner();</script></div>
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

        
        <div class="index_toplist mright mbottom">
            <div class="toptab" id="top_all_1">
                <span>${list[0].tname}</span>
            </div>
            <div id="tabData_1">
                
                <div class="topbooks" id="con_o1g_1" style="display: block;">
                    <ul>
						<c:forEach items="${listAll }" var="listAll" begin="0" end="14" step="1">
							<li><span class="hits">${ listAll.panname}</span> 
							<span class="num">${listAll.ranknum}. </span> <a href="toindex_id/${listAll.nid}"
								title="儒道至圣" target="_blank">${listAll.novelname}</a></li>
						</c:forEach>                        
                    </ul>
                </div>
                
               
            </div>
            <div class="clearfix"></div>
        </div>
        
        <div class="index_toplist mright mbottom">
            <div class="toptab" id="top_all_2">
                <span>${list[1].tname}</span>
            </div>
            <div id="tabData_2">
                
                <div class="topbooks" id="con_o2g_1" style="display: block;">
                    <ul>
                        
						<c:forEach items="${listAll1 }" var="listAll1" begin="0" end="14" step="1">
							<li><span class="hits">${listAll1.panname}</span> <span
								class="num">${listAll1.ranknum}. </span> <a href="toindex_id/${listAll1.nid}"
								title="儒道至圣" target="_blank">${listAll1.novelname}</a></li>
						</c:forEach>                          
                    </ul>
                </div>
                
              
                
            </div>
            <div class="clearfix"></div>
        </div>
        
        <div class="index_toplist mright mbottom">
            <div class="toptab" id="top_all_3">
                <span>${list[2].tname }</span>
            </div>
            <div id="tabData_3">
                
                <div class="topbooks" id="con_o3g_1" style="display: block;">
                    <ul>
                        <c:forEach items="${listAll2 }" var="listAll2" begin="0" end="14" step="1">
							<li><span class="hits">${ listAll2.panname}</span> <span
								class="num">${listAll2.ranknum}. </span> <a href="toindex_id/${listAll2.nid}"
								title="儒道至圣" target="_blank">${listAll2.novelname}</a></li>
						</c:forEach>  
                        
                    </ul>
                </div>
                
                
            </div>
            <div class="clearfix"></div>
        </div>
        
        <div class="index_toplist  mbottom">
            <div class="toptab" id="top_all_4">
                <span>${list[3].tname}</span>
            </div>
            <div id="tabData_4">
                
                <div class="topbooks" id="con_o4g_1" style="display: block;">
                    <ul>
                         <c:forEach items="${listAll3 }" var="listAll3" begin="0" end="14" step="1">
							<li><span class="hits">${ listAll3.panname}</span>
							<span class="num">${listAll3.ranknum}. </span> <a href="toindex_id/${listAll3.nid}"
								title="儒道至圣" target="_blank">${listAll3.novelname}</a></li>
						</c:forEach>
                        
                    </ul>
                </div>
                
            </div>
            <div class="clearfix"></div>
        </div>
        
        <div class="index_toplist mright mbottom">
            <div class="toptab" id="top_all_5">
                <span>${list[4].tname }</span>
            </div>
            <div id="tabData_5">
                
                <div class="topbooks" id="con_o5g_1" style="display: block;">
                    <ul>
                        <c:forEach items="${listAll4 }" var="listAll4" begin="0" end="14" step="1">
							<li><span class="hits">${ listAll4.panname}</span> <span
								class="num">${listAll4.ranknum}. </span> <a href="toindex_id/${listAll4.nid}"
								title="儒道至圣" target="_blank">${listAll4.novelname}</a></li>
						</c:forEach>
                        
                    </ul>
                </div>
                
            </div>
            <div class="clearfix"></div>
        </div>
        
        <div class="index_toplist mright mbottom">
            <div class="toptab" id="top_all_6">
                <span>${list[5].tname }</span>
            </div>
            <div id="tabData_6">
                
                <div class="topbooks" id="con_o6g_1" style="display: block;">
                    <ul>
                        <c:forEach items="${listAll5 }" var="listAll5" begin="0" end="14" step="1">
							<li><span class="hits">${ listAll5.panname}</span> <span
								class="num">${listAll5.ranknum}. </span> <a href="toindex_id/${listAll5.nid}"
								title="儒道至圣" target="_blank">${listAll5.novelname}</a></li>
						</c:forEach>
                        
                    </ul>
                </div>
                
                
            </div>
            <div class="clearfix"></div>
        </div>
        
        <div class="index_toplist mbottom">
            <div class="toptab" id="top_all_8">
                <span>完本小说总排行</span>
            </div>
            <div id="tabData_8">

                <div class="topbooks" id="con_hng_1" style="display: block;">
                    <ul>
                        <c:forEach items="${listAll6 }" var="listAll6" begin="0" end="14" step="1">
							<li><span class="hits">${ listAll6.panname}</span> <span
								class="num">${listAll6.ranknum}. </span> <a href="toindex_id/${listAll6.nid}"
								title="儒道至圣" target="_blank">${listAll6.novelname}</a></li>
						</c:forEach>
                        
                    </ul>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>

    </div>
    
<div class="footer">
    <div class="footer_link">
    </div>
    <div class="footer_cont">
        <p>
            本站所有小说为转载作品，所有章节均由网友上传，转载至本站只是为了宣传本书让更多读者欣赏。</p>
        <p>
            Copyright © 2016
            笔下文学</p>
       <script>footer();</script>
    </div>
</div>
    <script type="text/javascript">
        //滑动门
        function setTab(name, cursel, n, currentClass, otherClass) {
            for (i = 1; i <= n; i++) {
                var menu = document.getElementById(name + i);
                var con = document.getElementById("con_" + name + "_" + i);
                menu.className = i == cursel ? currentClass : otherClass;
                con.style.display = i == cursel ? "block" : "none";
            }
        }
    </script>
</body>
</html>
