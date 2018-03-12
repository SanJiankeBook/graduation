package com.yc.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.yc.bean.Admin;
import com.yc.bean.Author;
import com.yc.bean.EasyuiFindByPage;
import com.yc.bean.Novel;
import com.yc.bean.NovelChapter;
import com.yc.bean.NovelType;
import com.yc.bean.User;
import com.yc.biz.Adminbiz;
import com.yc.biz.Authorbiz;
import com.yc.biz.NovelChapterbiz;
import com.yc.biz.NovelTypebiz;
import com.yc.biz.Novelbiz;
import com.yc.biz.Userbiz;
import com.yc.biz.impl.NovelChapterbizImpl;
import com.yc.utils.RankUtils;

@Controller
public class ZLNovelController {
    private static final Log logger=LogFactory.getLog(ZLNovelController.class);

    private Userbiz userbiz;
    private Adminbiz adminbiz;
    private Authorbiz authorbiz;
    private Novelbiz novelbiz;
    private NovelChapterbiz chapter;
    private NovelChapter novelchapter;
    private NovelTypebiz novelTypebiz;
    private RankUtils rankUtils;
    private NovelType noveltype;
    
    
    @Resource(name = "novelType")
	public void setNoveltype(NovelType noveltype) {
		this.noveltype = noveltype;
	}
    @Resource(name = "rankUtils")
	public void setRankUtils(RankUtils rankUtils) {
		this.rankUtils = rankUtils;
	}
    @Resource(name="novelTypebizImpl")
    public void setNovelTypebiz(NovelTypebiz novelTypebiz) {
		this.novelTypebiz = novelTypebiz;
	}

	@Resource(name="novelChapter")
    public void setNovelchapter(NovelChapter novelchapter) {
		this.novelchapter = novelchapter;
	}

	@Resource(name="novelChapterbizImpl")
    public void setChapter(NovelChapterbiz chapter) {
		this.chapter = chapter;
	}

	@Resource(name="novelbizImpl")
    public void setNovelbiz(Novelbiz novelbiz) {
		this.novelbiz = novelbiz;
	}

	@Resource(name="userbizImpl")
    public void setUserbiz(Userbiz userbiz) {
		this.userbiz = userbiz;
	}
    
    @Resource(name="adminbizImpl")
    public void setAdminbiz(Adminbiz adminbiz) {
		this.adminbiz = adminbiz;
	}
    
    @Resource(name="authorbizImpl")
	public void setAuthorbiz(Authorbiz authorbiz) {
		this.authorbiz = authorbiz;
	}
    

	//用户注册
    @RequestMapping(value="/toSave")
    public String addUser(User use){
    	logger.info("addUser called...");
    	this.userbiz.save(use);
		return "index";
    }
   
    
    //用户登录
//    @RequestMapping(value="/userLogin")
//    public String userLogin(@RequestParam(value="uname") String uname,@RequestParam(value="upassword") String upassword,HttpServletRequest req,HttpServletResponse resp){
//    	logger.info("this is the userLogin.....");
//    	
//    	List<User> list= this.userbiz.userLogin(uname, upassword);
//    	
//    	// TODO:将404页面显示成弹出提示框"用户名或密码错误",再完善一下,
//    	if(!list.isEmpty()){
//    		//若用户存在,存到session中
//    		req.getSession().setAttribute("uname",uname);
//    		return "index";
//    	}else if(list.isEmpty()){
//    		return "404";
//    	}else{
//    		return "";
//    	}
//    	
//    }
    
    
    //主页作品推荐专区显示数据
//    @RequestMapping(value="toindex")
//    public String ToIndex(Model model,HttpServletRequest request){
//    	//取到nid,tid,tname,nname,pan_name,可以用novel
//    	List<Novel> novel2=this.novelbiz.NovelRecommend();
//    	List<Novel> novel1=this.novelbiz.Recommand();
//    	model.addAttribute("novel2",novel2);
//    	model.addAttribute("novel1",novel1);
//		return "index";
//    	
//    }
    
    //全本小说
    @RequestMapping(value="quanben")
    public String ToQuanBen(Model model){
    List<NovelType> Tlist = this.novelTypebiz.showType(noveltype); // 小说类型
		
		logger.info("toIndex.....");
		//根据类型来排行榜
		for (int r = 0; r < Tlist.size(); r++) {
			String tname=Tlist.get(r).getTname();
			List<Object> listAll = new ArrayList<Object>();
			List<Object> listAll1 = new ArrayList<Object>();
			List<Object> listAll2 = new ArrayList<Object>();
			List<Object> listAll3 = new ArrayList<Object>();
			List<Object> listAll4 = new ArrayList<Object>();
			List<Object> listAll5 = new ArrayList<Object>();
			List<Object> listAll6 = new ArrayList<Object>();
			switch (r) {
			case 0:
				listAll = rankUtils.RankType(tname);
				if (rankUtils.RankType(tname) != null) {
					model.addAttribute("listAll", listAll);
					break;
				}
			case 1:
				listAll1 = rankUtils.RankType(tname);
				if (rankUtils.RankType(tname) != null) {
					model.addAttribute("listAll1", listAll1);
					break;
				}
			case 2:
				listAll2 = rankUtils.RankType(tname);
				if (rankUtils.RankType(tname) != null) {
					model.addAttribute("listAll2", listAll2);
					break;
				}
			case 3:
				listAll3 = rankUtils.RankType(tname);
				if (rankUtils.RankType(tname) != null) {
					model.addAttribute("listAll3", listAll3);
					break;
				}
			case 4:
				listAll4 = rankUtils.RankType(tname);
				if (rankUtils.RankType(tname) != null) {
					model.addAttribute("listAll4", listAll4);
					break;
				}
			case 5:
				listAll5 = rankUtils.RankType(tname);
				if (rankUtils.RankType(tname) != null) {
					model.addAttribute("listAll5", listAll5);
					break;
				}
			case 6:
				listAll6 = rankUtils.RankAll();
				if(rankUtils.RankAll() != null){
					model.addAttribute("listAll6",listAll6);
					break;
				}
			}
		}
		
		model.addAttribute("list",Tlist);
    	return "quanben";
    }
    
    
//后台管理员模块------------------------------------------------------------------------------
   /**
    * 管理员登录
    * @param adnumber    编号
    * @param adpassword   密码
    * @param req
    * @return
    */
    @RequestMapping(value="adminLogin")
    public String BackIndex(@RequestParam String adnumber,@RequestParam String adpassword,HttpSession session){
		logger.info("this is backLogin .......");
		
		List<Admin> list=this.adminbiz.adminLogin(adnumber, adpassword);
		
		if(!list.isEmpty() ){
			return "../../back/BackIndex";
		}else if(list.isEmpty()){
			session.removeAttribute("errmsg");
			session.setAttribute("errmsg", "用户名或密码错误");
			return "../../back/Index";
		}else{
			return "404";
		}
		
    }
    
    
    /**
     * 查找所有用户
     * 因为在controller中返回json用了@ResponseBody，而spring源码中@ResponseBody 的实现类发现其默认的编码是 iso-8859-1，
	 * 而项目用的编码为utf-8,所以传中文会出现乱码。
	 * 加produces = {"application/text;charset=UTF-8"}解决中文乱码的问题
     */
    @RequestMapping(value="/findAllUser",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String FindUser() throws UnsupportedEncodingException{
    	logger.info("this is findAllUser.....");
    	List<User> list=this.userbiz.findUser();
    	Gson gson=new Gson();
    	return gson.toJson(list);
    }
    
    
    /**
     * 用户分页查询
     */
    @RequestMapping(value="/findAllUserByPage",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String FindUserByPage2(HttpServletRequest request){
    	logger.info("this is use map findAllUser.....");
    	String page=request.getParameter("page");    
    	String rows=request.getParameter("rows");
    	int currentPage=Integer.parseInt(page);     //当前的页数
    	int end=Integer.parseInt(rows);           //每页的条数
    	int start=0;
    	start=(currentPage-1)*end;
    	
    	List<User> lists=this.userbiz.findUser();
    	List<User> list=this.userbiz.findUserByPage(start, end);
    	EasyuiFindByPage ebp=new EasyuiFindByPage();
    	ebp.setTotal(lists.size());
    	ebp.setRows(list);
    	Gson gson=new Gson();
		return gson.toJson(ebp);
    }
    
    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value="/delUser")
    public @ResponseBody void DelUser(String uid ){
    	logger.info("this is easyUI delUser.....");
    	String[] uids=uid.split(",");
    	for(String u:uids){
    		Integer a=Integer.parseInt(u);
    		this.userbiz.DelUser(a);
    	}
    	
    } 
    
    /**
     * 查找作者
     */
    @RequestMapping(value="/findAllAuthor",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String FindAuthor(){
    	logger.info("this is findAllAuthor.....");
    	List<Author> list=this.authorbiz.FindAuthor();
    	Gson gson=new Gson();
		return gson.toJson(list);
    	
    }
    
    /**
     * 分页查询作者
     */
    @RequestMapping(value="/findAuthorByPage",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String FindAuthorByPage(HttpServletRequest request){
    	logger.info("this is findAuthorByPage.....");
    	String page=request.getParameter("page");    
    	String rows=request.getParameter("rows");
    	int currentPage=Integer.parseInt(page);     //当前的页数
    	int end=Integer.parseInt(rows);           //每页的条数
    	int start=0;
    	start=(currentPage-1)*end;
    	
    	List<Author> lists=this.authorbiz.FindAuthor();
    	List<Author> list=this.authorbiz.FindAuthorByPage(start, end);
    	
    	EasyuiFindByPage ebp=new EasyuiFindByPage();
    	ebp.setTotal(lists.size());
    	ebp.setRows(list);
    	Gson gson=new Gson();
		return gson.toJson(ebp);
    	
    }
    /**
     * 删除作者
     */
    @RequestMapping(value="/delAuthor")
    public @ResponseBody void DelAuthor(String aid){
    	logger.info("this is DelAuthor.....");
    	String[] aids=aid.split(",");
    	for(String a:aids){
    		Integer n=Integer.parseInt(a);
    		this.authorbiz.DelAuthor(n);
    	}
    	
    }
    
    /**
     * 查找小说
     */
    @RequestMapping(value="/findAllNovel",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String FindNovel(){
    	logger.info("this is findAllNovel.....");
    	List<Novel> list=this.novelbiz.FindAllNovel();
    	Gson gson=new Gson();
    	return gson.toJson(list);
    } 
    
    /**
     * 分页查询小说
     */
    @RequestMapping(value="/findNovelByPage",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String FindNovelByPage(HttpServletRequest request){
    	logger.info("this is use map findNovelByPage.....");
    	String page=request.getParameter("page");    
    	String rows=request.getParameter("rows");
    	int currentPage=Integer.parseInt(page);     //当前的页数
    	int end=Integer.parseInt(rows);           //每页的条数
    	int start=0;
    	start=(currentPage-1)*end;
    	
    	List<Novel> lists=this.novelbiz.FindAllNovel();
    	List<Novel> list=this.novelbiz.FindNovelByPage(start, end);
    	EasyuiFindByPage ebp=new EasyuiFindByPage();
    	ebp.setTotal(lists.size());
    	ebp.setRows(list);
    	Gson gson=new Gson();
		return gson.toJson(ebp);
    } 
    
    /**
     * 删除小说
     * @param nid
     */
    @RequestMapping(value="/delNovel")
    public @ResponseBody void DelNovel(String nid){
    	logger.info("this is delNovel.....");
    	String[] nids=nid.split(",");
    	for(String n:nids){
    		Integer a=Integer.parseInt(n);
    		this.novelbiz.delNovel(a);
    	}
    	
    }

    /**
     * 显示所有待审查的小说
     * @param request
     * @return
     */
    @RequestMapping(value="/uncheckNovel",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String FindUncheckNovel(HttpServletRequest request){
    	logger.info("this is use map FindUncheckNovel.....");
    	String page=request.getParameter("page");    
    	String rows=request.getParameter("rows");
    	int currentPage=Integer.parseInt(page);     //当前的页数
    	int end=Integer.parseInt(rows);           //每页的条数
    	int start=0;
    	start=(currentPage-1)*end;
    	
    	List<Novel> lists=this.novelbiz.count();
    	List<Novel> list=this.novelbiz.UncheckNovel(start, end);
    	EasyuiFindByPage ebp=new EasyuiFindByPage();
    	ebp.setTotal(lists.size());
    	ebp.setRows(list);
    	Gson gson=new Gson();
		return gson.toJson(ebp);
    }
    
    /**
     * 通过待审查的小说
     */
    @RequestMapping(value="/passNovel",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody void PassNovel(@RequestParam(value="nid") String nid){
    	logger.info("this is passNovel.....");
    	String[] nids=nid.split(",");
    	for(String n:nids){
    		Integer a=Integer.parseInt(n);
    		this.novelbiz.passNovel(a);
    	}
    	
    }
    
    /**
     * 不通过待审查的小说
     */
    @RequestMapping(value="/unpassNovel",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody void UnpassNovel(@RequestParam(value="nid") String nid){
    	logger.info("this is unpassNovel.....");
    	String[] nids=nid.split(",");
    	for(String n:nids){
    		Integer a=Integer.parseInt(n);
    		this.novelbiz.UnpassNovel(a);
    	}
    	
    }
    
    /**
     *  显示所有待审查的小说章节
     */
    @RequestMapping(value="/uncheckNovelChapter",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String FindUncheckNovelChapter(HttpServletRequest request){
    	logger.info("this is use map FindUncheckNovelChapter.....");
    	String page=request.getParameter("page");    
    	String rows=request.getParameter("rows");
    	int currentPage=Integer.parseInt(page);     //当前的页数
    	int end=Integer.parseInt(rows);           //每页的条数
    	int start=0;
    	start=(currentPage-1)*end;
    	
    	List<NovelChapter> lists=this.chapter.UncheckNovelChapter();
    	List<NovelChapter> list=this.chapter.UncheckNovelChapter(start, end);
    	EasyuiFindByPage ebp=new EasyuiFindByPage();
    	ebp.setTotal(lists.size());
    	ebp.setRows(list);
    	Gson gson=new Gson();
		return gson.toJson(ebp);
    }
    
    /**
     * 通过待审查章节
     */
    @RequestMapping(value="/passChapter",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String PassNovelChapter(@RequestParam(value="id") String cid){
    	logger.info("this is PassNovelChapter...");
    	String[] cids=cid.split(",");
    	for(String c:cids){
    		Integer a=Integer.parseInt(c);
    		this.chapter.PassChapter(a);
    	}
    	//this.chapter.PassChapter(cid);
    	return "1";
    }
    
    /**
     * 不通过待审核的小说章节
     */
    @RequestMapping(value="/unpassChapter",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String UnpassNovelChapter(@RequestParam(value="id") String cid){
    	logger.info("this is UnpassNovelChapter...");
    	String[] cids=cid.split(",");
    	for(String c:cids){
    		Integer a=Integer.parseInt(c);
    		this.chapter.UnpassChapter(a);
    	}
    	return "1";
    	
    }
    /**
     * 显示小说详情
     */
    @RequestMapping(value="/showDetails",produces = {"application/text;charset=UTF-8"})
    public @ResponseBody String ShowDetails(@RequestParam(value="id") Integer cid){
    	logger.info("this is ShowDetails...");
    	NovelChapter list=this.chapter.ShowDetails(cid);
    	if(list!=null){
    		return "1";
    	}else{
    		return "0";
    	}
    }
    
    //查看详情
    @RequestMapping(value="/tocaddress_id/{cid}")
    public String ToCaddress(@PathVariable Integer cid, Model model,HttpServletRequest request){
    	logger.info("this is chapterContent");
    	List<NovelChapter> novelChapter=this.chapter.ShowContent(cid);
    	System.out.println(novelChapter);
    	model.addAttribute("novelChapter", novelChapter);
    	return "FindChapter";
    } 
    
}
