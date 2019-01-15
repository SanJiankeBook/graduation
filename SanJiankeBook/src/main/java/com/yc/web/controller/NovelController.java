package com.yc.web.controller;


import java.util.List;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.yc.bean.Novel;
import com.yc.bean.NovelType;
import com.yc.biz.impl.AuthorbizImpl;
import com.yc.biz.impl.NovelChapterbizImpl;
import com.yc.biz.impl.NovelTypebizImpl;
import com.yc.biz.impl.NovelbizImpl;
import com.yc.biz.impl.UserTalkbizImpl;
import com.yc.bean.Author;
import com.yc.bean.EasyuiFindByPage;
import com.yc.bean.NovelChapter;
import com.yc.bean.User;
import com.yc.bean.UserBook;
import com.yc.bean.UserTalk;
import com.yc.biz.Authorbiz;
import com.yc.biz.NovelChapterbiz;
import com.yc.biz.NovelTypebiz;
import com.yc.biz.Novelbiz;
import com.yc.biz.UserBookbiz;
import com.yc.biz.UserTalkbiz;
import com.yc.biz.Userbiz;
import com.yc.biz.impl.NovelTypebizImpl;
import com.yc.dao.BaseDao;
import com.yc.help.StaticContain;
import com.yc.web.upload.ForFile;
import com.yc.web.upload.UploadFileUtil;
import com.yc.web.upload.UploadFileUtil.UploadFile;

@Controller
public class NovelController {
    private static final Log logger=LogFactory.getLog(NovelController.class);

    private Authorbiz authorbiz;
    private Userbiz userbiz;
    private NovelTypebiz noveltypebiz;
    private Novelbiz novelbiz;
    private NovelChapterbiz novelchapterbiz;
    private NovelType noveltype;
    private Novel novel;
    private NovelTypebizImpl novelTypebizImpl;
    private NovelbizImpl novelbizImpl;
    private AuthorbizImpl authorbizImpl;
    private NovelChapterbizImpl novelChapterbizImpl;
    private UserBookbiz userbookbiz;
    private UserTalkbiz usertalbiz;
    
    
    @Resource(name="userTalkbizImpl")
    public void setUsertalbiz(UserTalkbiz usertalbiz) {
		this.usertalbiz = usertalbiz;
	}
	@Resource(name="userBookbizImpl")
    public void setUserbookbiz(UserBookbiz userbookbiz) {
		this.userbookbiz = userbookbiz;
	}
	@Resource(name="novelChapterbizImpl")
    public void setNovelchapterbiz(NovelChapterbiz novelchapterbiz) {
		this.novelchapterbiz = novelchapterbiz;
	}
	@Resource(name="novelbizImpl")
    public void setNovelbiz(Novelbiz novelbiz) {
		this.novelbiz = novelbiz;
	}
	@Resource(name="novelTypebizImpl")
    public void setNoveltypebiz(NovelTypebiz noveltypebiz) {
		this.noveltypebiz = noveltypebiz;
	}
	@Resource(name="authorbizImpl")
    public void setAuthorbiz(Authorbiz authorbiz) {
		this.authorbiz = authorbiz;
	}
    @Resource(name="userbizImpl")
	public void setUser(Userbiz userbiz) {
		this.userbiz = userbiz;
	}
    
    
    @Resource(name="novel")
    public void setNovel(Novel novel) {
		this.novel = novel;
	}

	@Resource(name="novelType")
    public void setNoveltype(NovelType noveltype) {
		this.noveltype = noveltype;
	}

	@Resource(name="novelbizImpl")
    public void setNovelbizImpl(NovelbizImpl novelbizImpl) {
		this.novelbizImpl = novelbizImpl;
	}

	@Resource(name="novelTypebizImpl")
	public void setNovelTypebizImpl(NovelTypebizImpl novelTypebizImpl) {
		this.novelTypebizImpl = novelTypebizImpl;
	}
	
	@Resource(name="authorbizImpl")
	public void setAuthorbizImpl(AuthorbizImpl authorbizImpl) {
		this.authorbizImpl = authorbizImpl;
	}
	
	
	@Resource(name="novelChapterbizImpl")
	public void setNovelChapterbizImpl(NovelChapterbizImpl novelChapterbizImpl) {
		this.novelChapterbizImpl = novelChapterbizImpl;
	}
	
	//前往我的书架业务
	@RequestMapping(value="/mybook")
	public String mybook(HttpServletRequest request,Model model){
		List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
		logger.info("mybook....");
		if(request.getSession().getAttribute("users")!=null){
			//List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
			model.addAttribute("list",list1);
			return "userbook";
		}else{
			model.addAttribute("list",list1);
			return "userlogin";
		}
	}

	//页面登陆界面
	@RequestMapping(value="/userlogininfo")
	public String userlogininfo(Model model){
		logger.info("页面登陆界面........");
		List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
		model.addAttribute("list",list1);
		return "userlogin";
	}
    //搜索页面
    @RequestMapping(value="/tosousuo")
    public String sousuo(Model model,Novel novel){
    	logger.info("tosousuo.....");
    	model.addAttribute("novel",novel.getNname());
    	List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
		model.addAttribute("list",list1);
    	return "findnovel";
    }
    //搜索
    @RequestMapping(value="/quearyNovel",produces = {"application/text;charset=UTF-8"})
    @ResponseBody
    public String quearyNovel(@RequestParam String text,HttpServletRequest request){
    	logger.info("quearyNovel.....");
    	Novel novel=new Novel();
    	String nname=null;
    	if(text!=null && text!=""){
    	     nname=text.substring(0,1)+"%";
    	}
    	novel.setNname(nname);
    	novel.setPan_name(nname);
    	String page=request.getParameter("page");    
    	String rows=request.getParameter("rows");
    	int currentPage=Integer.parseInt(page);     //当前的页数
    	int end=Integer.parseInt(rows);           //每页的条数
    	int start=0;
    	start=(currentPage-1)*end;
    	
    	//List<Novel> lists=this.novelbiz.FindAllNovel();
    	List<Novel> lists=this.novelbiz.findNovelByName(novel);
    	//List<Novel> list=this.novelbiz.FindNovelByPage(start, end);
    	List<Novel> list=this.novelbiz.FindNovelByNameFenYe(nname,start, end);
    	EasyuiFindByPage ebp=new EasyuiFindByPage();
    	ebp.setTotal(lists.size());
    	ebp.setRows(list);
    	Gson gson=new Gson();
		return gson.toJson(ebp);
    	//这个功能并没有从数据库中拿数据
    }
    
    //添加到用户书架
    @RequestMapping(value = "/adduserbook/{nid}")
    @ResponseBody
	public String adduserbook(@PathVariable int nid, Model model,HttpServletRequest request) {
    	logger.info(nid);
    	//判断是否登入
    	if(request.getSession().getAttribute("users")!=null){
	    	UserBook userbook=new UserBook();
	    	userbook.setNid(nid);
	    	User users=(User) request.getSession().getAttribute("users");
	    	if(users.getUid()!=null){
	    		userbook.setUid(users.getUid());
	    		List list=this.userbookbiz.finduserbook(userbook);
	    		if(list.size()<2){
	    			//查询是否有这本书
	    				List listUserbook=this.userbookbiz.getUserbook(userbook);
	    				//如果是空
	    				if(listUserbook.isEmpty()){
	    					//添加这本书
	    					this.userbookbiz.addUserBook(userbook);
	    					return "2";
	    				}else{
	    					return "1";
	    				}
	    		}else{
	    			return "0";
	    		}
	    	}else{
	    		return "-1";
	    	}
    	}else{
    		return "-1";
    	}
    	
    }
    //从用户书架删除书本
    @RequestMapping(value ="/delUserbook")
    @ResponseBody
    public String delUserbook(@RequestParam String nid, Model model,HttpServletRequest request) {
    	logger.info("delUserbook......");
    	//判断是否登入
    	if(request.getSession().getAttribute("users")!=null){
    	User users=(User) request.getSession().getAttribute("users");
	    	UserBook userbook=new UserBook();
	    	String[] listNid=nid.split(",");
	    	userbook.setList1(listNid);
	    	if(users.getUid()!=null){
	    		userbook.setUid(users.getUid());
	    		this.userbookbiz.delUserbook(userbook);
	    		return "1";
	    	}else{
	    		return "0";
	    	}
    	}else{
    		return "0";
    	}
    }
    //用户书架
    @RequestMapping(value="/userbooknovel",produces = {"application/text;charset=UTF-8"})
    @ResponseBody
    public String userbooknovel(HttpServletRequest request){
    	logger.info("userbooknovel.....");
    	UserBook ub=new UserBook();
    	if(request.getSession().getAttribute("users")!=null){
    		User users=(User) request.getSession().getAttribute("users");
    		ub.setUid(users.getUid());
	    	String page=request.getParameter("page");    
	    	String rows=request.getParameter("rows");
	    	int currentPage=Integer.parseInt(page);     //当前的页数
	    	int end=Integer.parseInt(rows);           //每页的条数
	    	int start=0;
	    	start=(currentPage-1)*end;
	    	
	    	//List<Novel> lists=this.novelbiz.findNovelByName(novel);
	    	List list=this.userbookbiz.finduserbook(ub);
	    	//List<Novel> list=this.novelbiz.FindNovelByPage(start, end);
	    	//List<Novel> list=this.novelbiz.FindNovelByNameFenYe(nname,start, end);
	    	List list1=this.userbookbiz.finduserbookInfo(ub,start,end);
	    	EasyuiFindByPage ebp=new EasyuiFindByPage();
	    	ebp.setTotal(list.size());
	    	ebp.setRows(list1);
	    	Gson gson=new Gson();
	    	return gson.toJson(ebp);
    	}else{
    		return "-1";
    	}
    	
    }
    //显示评论
    @RequestMapping(value="/showusertalk",produces = {"application/text;charset=UTF-8"})
    @ResponseBody
    public String showusertalk(HttpServletRequest request){
    	logger.info("showusertalk....");
    	String nid=request.getParameter("text");
    	UserTalk usertalk=new UserTalk();
    	usertalk.setNid(Integer.parseInt(nid));
    	String page=request.getParameter("page");    
    	String rows=request.getParameter("rows");
    	int currentPage=Integer.parseInt(page);     //当前的页数
    	int end=Integer.parseInt(rows);           //每页的条数
    	int start=0;
    	start=(currentPage-1)*end;
    	List<UserTalk> list=this.usertalbiz.findAllTalk(usertalk);
    	List<UserTalk> list2=this.usertalbiz.findLimitTalk(usertalk,start,end);
    	EasyuiFindByPage ebp=new EasyuiFindByPage();
    	ebp.setTotal(list.size());
    	ebp.setRows(list2);
    	Gson gson=new Gson();
    	return gson.toJson(ebp);
    }
    //评论发表
    @RequestMapping(value="/publishTalk")
    @ResponseBody
    public String publishTalk(HttpServletRequest request){
    	HttpSession session=null;
    	User user=null;
		try {
			session = request.getSession();
			 user=(User) session.getAttribute("users");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	UserTalk usertalk=new UserTalk();
    	if(user!=null){
    		String nid=request.getParameter("nid");
    		String utcontent=request.getParameter("des");
	    		Integer uid=user.getUid();
	    		usertalk.setUid(uid);
	    		usertalk.setNid(Integer.parseInt(nid));
	    		usertalk.setUtcontent(utcontent);
	    		this.usertalbiz.addtalk(usertalk);
	    		return "1";
    	}else{
    		return "-1";
    	}
    }
    //前往作家注册页面2，已登入在注册
    @RequestMapping(value="/toauthorUser")
    public String toauthorUser(){
    	logger.info("toauthorUser....");
    	return "authorUser";
    }
    //前往作家注册页面
    @RequestMapping(value="/toauthor")
    public String author(){
    	logger.info("toauthor.....");
    	return "author";
    }
    //前往作家登入页面
    @RequestMapping(value="/toAuthorlogger")
    public String toAuthorlogger(Model model){
    	logger.info("toAuthorlogger...");
    	List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
		model.addAttribute("list",list1);
    	return "Authorlogger";
    }
    //前往阅读记录界面  毕设
    @RequestMapping(value="/toReadRecord")
    public String toReadRecord(Model model){
    	logger.info("toReadRecord...");
    	List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
    	model.addAttribute("list",list1);
    	return "readRecord";
    }
    //已有账号注册成为作家
    @RequestMapping(value="/registauthorUser")
    @ResponseBody
    public String registauthorUser(Author author,HttpServletRequest request){
    	logger.info("registauthorUser.....");
    	HttpSession session=request.getSession();
    	User uuser=(User)(session.getAttribute("users"));
    	if(uuser!=null){
	    	author.setUid(uuser.getUid());
	    	//在注册成为一个作家
	    	author.setPan_name(request.getParameter("uname"));
	    	author.setAtel(uuser.getStandby_1());
	    	this.authorbiz.insertAuthor(author);
	    
	    	return "1";
    	}else{
    		return "-1";
    	}
    }
    //注册成为作家
    @RequestMapping(value="/registauthor")
    @ResponseBody
    public String registauthor(Author author,User user){
    	
    	logger.info("registauthor.....");
    	author.setPan_name(user.getUname());
    	//需要先注册成为一个用户
    	//注册之前需要验证一拨
    	User userlist=new User();
    	userlist.setU_number(user.getU_number());
		
		List<User> list_uname=this.userbiz.findUserInfo(userlist);
		if(!list_uname.isEmpty()){
			return "-1";//该账号已被注册
		}
		userlist.setUname(user.getUname());
		userlist.setU_number(null);
		list_uname=this.userbiz.findUserInfo(userlist);
		if(!list_uname.isEmpty()){
			return "0" ;//该用户已被注册
		}
    	this.userbiz.InsertUser(user);
    	author.setUid(user.getUid());
    	//在注册成为一个作家
    	this.authorbiz.insertAuthor(author);
    	
    	StaticContain.USERID=author.getAid();
    	return "1";
    }
    @RequestMapping(value="/test")
    public String test(){
    	logger.info("test....");
    	//return "userbook";
    	return "forgivepassword";
    }
    //前往手机验证码页面
    @RequestMapping(value="/forgivepassword")
    public String forgivepassword(Model model){
    	List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
		model.addAttribute("list",list1);
    	return "forgivepassword";
    }
    
    private String pdfRootName="uploadPdfs";
    //插入书籍信息
    @RequestMapping(value="/InsertNovel")
    public String InsertNovel(@ModelAttribute Novel novel,@ModelAttribute NovelType noveltype,Model model,HttpServletRequest request) throws IOException{
    	logger.info("InsertNovel....");
    	String npicture="";
    	if(novel.getNname()!=null ){
			Map<String,UploadFile> map= UploadFileUtil.uploadFile(request, novel.getPdfsUrl(), pdfRootName);
			for(Entry<String,UploadFile> entry:map.entrySet()){
				UploadFile uploadFile=entry.getValue();
				npicture+=uploadFile.getNewFileUrl();
			}
			novel.setNpicture(npicture);
			novel.setAid(StaticContain.USERID);
			//novel.setAid(1);
			novel.setNstatus("更新");
			novel.setStandby_1("待审核");
			this.novelbiz.InsertNovel(novel);
			model.addAttribute("novel",novel);
			return  "forward:toindex_zpd";
    	}else{
    		return "forward:toindex_zpd";
    	}
    	
    }
    
   
    //插入书籍章节
    @RequestMapping(value="/insertNovlChapter",produces = {"application/text;charset=UTF-8"})
    @ResponseBody
    public String insertNovlChapter(@ModelAttribute NovelChapter novelchapter,@RequestParam String des,HttpServletRequest request,Model model) throws IOException{
    	logger.info("insertNovlChapter....");
    	String caddress=ForFile.createFile(request, des,novelchapter.getCname(),novelchapter,this.novelchapterbiz);
    	novelchapter.setCaddress(caddress);
    	novelchapter.setStandby_1("待审核");
    	int value=0;
    	String num="";
    	try {
			this.novelchapterbiz.insertNovelChapter(novelchapter);
			value= 1;
			num=String.valueOf(value);
		} catch (Exception e) {
			e.printStackTrace();
			value= 0;
			num=String.valueOf(value);
		}
    	return num;
    }
    
    //produces告诉浏览器我是用utf8格式编码
    //得到书籍类型
    @RequestMapping(value="/showNovelTypes",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String showNovelTypes(Model model){
    	logger.info("showNovelTypes....");
    	List list=this.noveltypebiz.AllNovelType(new NovelType());
    	Gson gson=new Gson();
		return gson.toJson(list);
		
		
    }

	    /*	model.addAttribute("listAll",listAll);
	    		
			List<Object> listAll=new ArrayList<Object>();
			List<String> dlist=new ArrayList<String>();//点击量
			List<String> Ranklist=new ArrayList<String>();//排名
			RedisUtil redis=new RedisUtil();
			List typelist=novelTypebizImpl.showType(noveltype);
			//System.out.println(typelist.get(1)+"=======你还不是修仙");  
			
				//System.out.println("进来没？");
				List<Novel> list=novelbizImpl.TypeNovel("玄幻");
				for(int i=0;i<list.size();i++){
		    		String rname=  list.get(i).getNname();
		    		Double num=redis.ShowRank(rname);
		    		String Stnum=String.valueOf(num);
		    		String inum=String.valueOf(i+1);
		    		//System.out.println("num=====  "+num);	
		    		dlist.add(Stnum);
		    		Ranklist.add(inum);
		    	} 
		    	
		    	for(int j=0;j<list.size();j++){
		    		Rank rank=new Rank();
		    		rank.setNovelname(list.get(j).getNname());
		    		rank.setRanknum(dlist.get(j));
		    		rank.setDoll(Ranklist.get(j));
		    		listAll.add(rank);	
		    	}
		    	model.addAttribute("listAll",listAll);
		    	
			return "rank";
	    }
	*/
}

