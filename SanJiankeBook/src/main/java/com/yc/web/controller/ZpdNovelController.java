package com.yc.web.controller;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.yc.bean.Alllist;
import com.yc.bean.Author;
import com.yc.bean.EasyuiFindByPage;
import com.yc.bean.Novel;
import com.yc.bean.NovelChapter;
import com.yc.bean.NovelType;
import com.yc.bean.Rank;
import com.yc.bean.User;
import com.yc.biz.Authorbiz;
import com.yc.biz.NovelChapterbiz;
import com.yc.biz.NovelTypebiz;
import com.yc.biz.Novelbiz;
import com.yc.biz.Userbiz;
import com.yc.biz.impl.AuthorbizImpl;
import com.yc.biz.impl.NovelChapterbizImpl;
import com.yc.biz.impl.NovelTypebizImpl;
import com.yc.biz.impl.NovelbizImpl;

import com.yc.duanxin.DuanxinJudge;
import com.yc.help.StaticContain;
import com.yc.utils.JsoupUtils;
import com.yc.utils.RandomUtils;
import com.yc.utils.RankUtils;
import com.yc.utils.TopUtils;
import com.yc.web.upload.UploadFileUtil;
import com.yc.web.upload.UploadFileUtil.UploadFile;
import com.yc.utils.RedisUtils;
import com.yc.utils.TNovelUtils;

@Controller
public class ZpdNovelController {

	private static final Log logger = LogFactory.getLog(NovelController.class);

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
	private TopUtils rutils;
	private TNovelUtils tNovelUtils;
	private RandomUtils randomUtils;
	private RankUtils rankUtils;
	private JsoupUtils jsoupUtils;

	@Resource(name = "jsoupUtils")
	public void setJsoupUtils(JsoupUtils jsoupUtils) {
		this.jsoupUtils = jsoupUtils;
	}
	@Resource(name = "rankUtils")
	public void setRankUtils(RankUtils rankUtils) {
		this.rankUtils = rankUtils;
	}
	@Resource(name = "tNovelUtils")
	public void settNovelUtils(TNovelUtils tNovelUtils) {
		//System.out.println("注入了tNovelUtils");
		this.tNovelUtils = tNovelUtils;
	}
	@Resource(name = "randomUtils")
	public void setRandomUtils(RandomUtils randomUtils) {
		this.randomUtils = randomUtils;
	}

	@Resource(name = "topUtils")
	public void setRk(TopUtils rutils) {
		this.rutils = rutils;
	}

	@Resource(name = "novelChapterbizImpl")
	public void setNovelchapterbiz(NovelChapterbiz novelchapterbiz) {
		this.novelchapterbiz = novelchapterbiz;
	}

	@Resource(name = "novelbizImpl")
	public void setNovelbiz(Novelbiz novelbiz) {
		this.novelbiz = novelbiz;
	}

	@Resource(name = "novelTypebizImpl")
	public void setNoveltypebiz(NovelTypebiz noveltypebiz) {
		this.noveltypebiz = noveltypebiz;
	}

	@Resource(name = "authorbizImpl")
	public void setAuthorbiz(Authorbiz authorbiz) {
		this.authorbiz = authorbiz;
	}

	@Resource(name = "userbizImpl")
	public void setUser(Userbiz userbiz) {
		this.userbiz = userbiz;
	}

	@Resource(name = "novel")
	public void setNovel(Novel novel) {
		this.novel = novel;
	}

	@Resource(name = "novelType")
	public void setNoveltype(NovelType noveltype) {
		this.noveltype = noveltype;
	}

	@Resource(name = "novelbizImpl")
	public void setNovelbizImpl(NovelbizImpl novelbizImpl) {
		this.novelbizImpl = novelbizImpl;
	}

	@Resource(name = "novelTypebizImpl")
	public void setNovelTypebizImpl(NovelTypebizImpl novelTypebizImpl) {
		this.novelTypebizImpl = novelTypebizImpl;
	}

	@Resource(name = "authorbizImpl")
	public void setAuthorbizImpl(AuthorbizImpl authorbizImpl) {
		this.authorbizImpl = authorbizImpl;
	}

	@Resource(name = "novelChapterbizImpl")
	public void setNovelChapterbizImpl(NovelChapterbizImpl novelChapterbizImpl) {
		this.novelChapterbizImpl = novelChapterbizImpl;
	}
	//跳转到注册页面
	@RequestMapping(value="/showregister")
	public String showregister(){
		return "register";
	}

	// 主页面显示数据
	@RequestMapping(value = "/toindex_zpd")
	public String Index(Model model,HttpServletRequest request) {
		logger.info("toIndex.....");
		List<NovelType> list = novelTypebizImpl.showType(noveltype); // 小说类型
		List<Novel> novel = novelbizImpl.ShowNovel(); // 小说信息

		// 根据类型显示所有的小说信息
		for (int tnum = 0; tnum < list.size(); tnum++) {
			String tyname = list.get(tnum).getTname(); // 每种类型下的小说名
			List<Alllist> Alllist = new ArrayList<Alllist>(); // 整合小说id-小说名 -作者名
			List<Alllist> Alllist1 = new ArrayList<Alllist>();
			List<Alllist> Alllist2 = new ArrayList<Alllist>();
			List<Alllist> Alllist3 = new ArrayList<Alllist>();
			List<Alllist> Alllist4 = new ArrayList<Alllist>();
			List<Alllist> Alllist5 = new ArrayList<Alllist>();
			switch (tnum) {
			case 0:
				Alllist = tNovelUtils.ListNovel(tyname);

				model.addAttribute("Alllist", Alllist);
				break;

			case 1:
				Alllist1 = tNovelUtils.ListNovel(tyname);
				model.addAttribute("Alllist1", Alllist1);
				break;

			case 2:
				Alllist2 = tNovelUtils.ListNovel(tyname);

				model.addAttribute("Alllist2", Alllist2);
				break;

			case 3:
				Alllist3 = tNovelUtils.ListNovel(tyname);

				model.addAttribute("Alllist3", Alllist3);
				break;

			case 4:
				Alllist4 = tNovelUtils.ListNovel(tyname);

				model.addAttribute("Alllist4", Alllist4);
				break;

			case 5:
				Alllist5 = tNovelUtils.ListNovel(tyname);

				model.addAttribute("Alllist5", Alllist5);
				break;

			}
		}

		model.addAttribute("list", list);
		model.addAttribute("novel", novel);

//		// 根据点击数最高的显示小说信息
//		for (int r = 0; r < list.size(); r++) {
//			List<Novel> n = new ArrayList<Novel>();
//			List<Novel> n1 = new ArrayList<Novel>();
//			List<Novel> n2 = new ArrayList<Novel>();
//			List<Novel> n3 = new ArrayList<Novel>();
//			List<Novel> n4 = new ArrayList<Novel>();
//			List<Novel> n5 = new ArrayList<Novel>();
//			List<Author> author = authorbizImpl.Show_Author(r);
//			switch (r) {
//			case 0:
//				n = rutils.RankInformation(r);
//				if (rutils.RankInformation(r) != null) {
//					model.addAttribute("author", author);
//					model.addAttribute("n", n);
//					break;
//				}
//			case 1:
//				n1 = rutils.RankInformation(r);
//				if (rutils.RankInformation(r) != null) {
//					model.addAttribute("author", author);
//					model.addAttribute("n1", n1);
//					break;
//				}
//			case 2:
//				n2 = rutils.RankInformation(r);
//				if (rutils.RankInformation(r) != null) {
//					model.addAttribute("author", author);
//					model.addAttribute("n2", n2);
//					break;
//				}
//			case 3:
//				n3 = rutils.RankInformation(r);
//				if (rutils.RankInformation(r) != null) {
//					model.addAttribute("author", author);
//					model.addAttribute("n3", n3);
//					break;
//				}
//			case 4:
//				n4 = rutils.RankInformation(r);
//				if (rutils.RankInformation(r) != null) {
//					model.addAttribute("author", author);
//					model.addAttribute("n4", n4);
//					break;
//				}
//			case 5:
//				n5 = rutils.RankInformation(r);
//				if (rutils.RankInformation(r) != null) {
//					model.addAttribute("author", author);
//					model.addAttribute("n5", n5);
//					break;
//				}
//			}
//		}
		
		//作品推荐
		List<Novel> novel2=this.novelbiz.NovelRecommend();
    	List<Novel> novel1=this.novelbiz.Recommand();
    	model.addAttribute("novel2",novel2);
    	model.addAttribute("novel1",novel1);
		
		return "index";
	}
	
	// 点击，显示小说的页面
	@RequestMapping(value = "/toindex_id/{nid}")
	public String Index_id(@PathVariable int nid, Model model) {
		List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
		List list = new ArrayList();
		logger.info("toIndex.....");
		list = novelbizImpl.ShowNovel_id(nid);
		List author = authorbizImpl.Show_Author(nid);
		List chapter = novelChapterbizImpl.NewChapter(nid);
		List<NovelChapter> nchapter = novelChapterbizImpl.ShowAllChapter(nid);
		if(nchapter.size()>1){
			for(int i=(nchapter.size()-1);i<nchapter.size();i++){
				String caddress=nchapter.get(i).getCaddress();
				String cname=nchapter.get(i).getCname();
				model.addAttribute("caddress", caddress);
				model.addAttribute("cname",cname);
			}
		}
		model.addAttribute("nchapter", nchapter);
		model.addAttribute("novel_id", list);
		model.addAttribute("author", author);
		model.addAttribute("chapter", chapter);
		model.addAttribute("list",list1);
		// System.out.println(list);

		/*
		 * 记录数据
		 */
		Novel nname = (Novel) list.get(0);
		String name = nname.getNname();
//		RedisUtils redis = new RedisUtils();
//		redis.Ranking(name);

		return "Novel2";
	}
	
	
	//在Novel2显示某个类型的全部小说信息
		@RequestMapping(value="/TypeNovel",produces = {"application/text;charset=UTF-8"})
	    @ResponseBody
	    public String quearyNovel1(@RequestParam int tid,HttpServletRequest request){
	    	logger.info("quearyNovel.....");
	    	
	    	String page=request.getParameter("page");    
	    	String rows=request.getParameter("rows");
	    	int currentPage=Integer.parseInt(page);     //当前的页数
	    	int end=Integer.parseInt(rows);           //每页的条数
	    	int start=0;
	    	start=(currentPage-1)*end;

	    	List<Novel> list=this.novelbiz.FindNovelBytid(tid, start, end);
	    	EasyuiFindByPage ebp=new EasyuiFindByPage();
	    	ebp.setTotal(list.size());
	    	ebp.setRows(list);
	    	Gson gson=new Gson();
			return gson.toJson(ebp);
	    	//这个功能并没有从数据库中拿数据
	    }
	

	//检查是否登陆
	@RequestMapping(value="/checkloging",produces = {"application/text;charset=UTF-8"})
	@ResponseBody
	public String checkloging(HttpServletRequest request){
		Gson gson=new Gson();
		if(request.getSession().getAttribute("users")!=null){
			User user=(User) request.getSession().getAttribute("users");
			user.setStatus("1");
			return gson.toJson(user);
		}else{
			User user=new User();
			user.setStatus("-1");
			return gson.toJson(user);
		}
	}
		
	//登录验证
	@RequestMapping(value="/logger",produces = {"application/text;charset=UTF-8"})
	@ResponseBody
	public String logger(HttpServletRequest request,Model model) {
		logger.info("AuthorPrefecture.......");
		User user=new User();
		Gson gson=new Gson();
		HttpSession session = request.getSession();
		String validateCode=request.getParameter("validateCode");
		if(validateCode!=null &&validateCode!=""){
			String randCode=(String) session.getAttribute("rand");
			if(!validateCode.equals(randCode)){
				session.setAttribute("errmsg", "验证码错误");
				user.setStatus("-2");
				return gson.toJson(user);
			}
		}
			//Map<String,Object> map = new HashMap<String,Object>(); 
			String uname=request.getParameter("uname");
			String upassword=request.getParameter("upassword");
			List<User> list=userbiz.findUserName(uname);
			
			if(uname!="" || uname!=null && upassword!="" || upassword!=null){
				if(!list.isEmpty()){
					if(uname.equals(list.get(0).getU_number()) && upassword.equals(list.get(0).getUpassword())){
						Integer uuid=list.get(0).getUid();
						
						user.setUname(list.get(0).getUname());
						user.setUid(list.get(0).getUid());
						user.setU_number(list.get(0).getU_number());
						user.setUpassword(upassword);
						user.setStatus("1");
						session.setAttribute("users",user);
						return gson.toJson(user);	
					}
					user.setStatus("-1");
					return gson.toJson(user);
				}else{
					user.setStatus("-1");
					return  gson.toJson(user);
				}		
				//return "index";
			}else{
				user.setStatus("-1");
				return  gson.toJson(user);
			}
//		}else{
//			user.setStatus("-2");
//			return gson.toJson(user);
//		}
	
			
	}
	
	//前往用户信息页面
	@RequestMapping(value="/showUser")
	public String showUser(Model model,HttpServletRequest request){
		List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
		model.addAttribute("list",list1);
		if(request.getSession().getAttribute("users")!=null){
			User user=(User) request.getSession().getAttribute("users");
			List<User> listuser=this.userbiz.findUserInfo(user);
			model.addAttribute("listuser",listuser);
		}else{
			return "userlogin";
		}
		return "showuserinfo";
	}
	
	
	//用户忘记密码
	@RequestMapping(value="/forgivepasswordUname")
	@ResponseBody
	public String forgivepassword(HttpServletRequest request){
		String uname=request.getParameter("uname");
		request.getSession().setAttribute("forgiveUser", uname);
		//System.out.println(uname);
		return "1";
		
	}
	//发送验证码
	@RequestMapping(value="sendcode")
	@ResponseBody
	public String sendcode(HttpServletRequest request){
		logger.info("sendcode.....");
		String standby_1=request.getParameter("standby_1");
		if(request.getSession().getAttribute("forgiveUser")==null){
			return "toindex_zpd";
		}else{
			String val=(String) request.getSession().getAttribute("forgiveUser");
			List<User> list=userbiz.findUserName(val);
			if(list.get(0).getStandby_1().equals(standby_1)){
				Integer value=null;
				try {
					value = DuanxinJudge.sendJudgeCode(standby_1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.getSession().setAttribute("code", value+"");
				return "1";
			}else{
				return "-2";
			}
			
		
	}
}
	//判断用户忘记密码的验证码是否正确
	@RequestMapping(value="/judgecode")
	@ResponseBody
	public String judgecode(HttpServletRequest request){
		logger.info("judgecode....");
		String number=request.getParameter("number");
		if(request.getSession().getAttribute("code")==null){
			return "-1";
		}else{
			if(number.equals(request.getSession().getAttribute("code"))){
				return "1";
			}else{
				return "0";
			}
		}
			
	}
	//用户信息修改
	@RequestMapping(value="/updateUserInfo")
	@ResponseBody
	public String updateUserInfo(HttpServletRequest request){
		logger.info("updateUserInfo....");
		String uname=request.getParameter("uname");
		String usex_2=request.getParameter("usex_2");
		String standby_1=request.getParameter("standby_1");
		if(request.getSession().getAttribute("users")!=null){
			User user=(User) request.getSession().getAttribute("users");
			user.setUname(uname);
			List<User> list_uname=this.userbiz.findUserInfo(user);
			if(!list_uname.isEmpty()){
				int num1=list_uname.get(0).getUid();
				int num2=user.getUid();
				if(num1!=num2){
					return "0";
				}
			}
			user.setStandby_1(standby_1);
			user.setUsex(usex_2);
			this.userbiz.updateUserInfo(user);
			return "1";
		}else{
			return "-1";
		}
		
	}
	//修改用户密码已经有原密码
	@RequestMapping(value="/updatePasswordInfo")
	@ResponseBody
	public String updatePasswordInfo(HttpServletRequest request){
		logger.info("updatePasswordInfo....");
		String xinupassword=null;
		String upassword=null;
		try {
			xinupassword = request.getParameter("xinupassword");
			upassword = request.getParameter("upassword");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(request.getSession().getAttribute("users")!=null){
			User user=(User) request.getSession().getAttribute("users");
			if(user.getUpassword().equals(upassword)){//如果输入的原密码和数据库的密码一样
				user.setUpassword(xinupassword);
				this.userbiz.updateUser(user);
				return "1";
			}else{
				return "0";
			}
			
		}else{
			return "-1";
		}
		
	}
	//修改用户密码
	@RequestMapping(value="/updatepassword")
	@ResponseBody
	public String updatepassword(HttpServletRequest request){
		logger.info("updatepassword....");
		try {
			String xinpassword=request.getParameter("xinpassword");
			String val=(String) request.getSession().getAttribute("forgiveUser");
			request.getSession().removeAttribute("forgiveUser");
			request.getSession().removeAttribute("code");
			User user=new User();
			user.setUpassword(xinpassword);
			user.setU_number(val);
			this.userbiz.updateUser(user);
			return "1";
		} catch (Exception e) {
			
			e.printStackTrace();
			return "0";
		}
		
		
		
	}
	//用户注册
		@RequestMapping(value="/register",produces = {"application/text;charset=UTF-8"})
		@ResponseBody
		public String register(HttpServletRequest request,Model model) {
			logger.info("register.......");
			User user=new User();
			Gson gson=new Gson();
			HttpSession session = request.getSession();
			String validateCode=request.getParameter("validateCode");
			if(validateCode!=null &&validateCode!=""){
				String randCode=(String) session.getAttribute("rand");
				if(!validateCode.equals(randCode)){
					session.setAttribute("errmsg", "验证码错误");
					user.setStatus("-2");
					return gson.toJson(user);
				}
			}
				//Map<String,Object> map = new HashMap<String,Object>(); 
				String uname=request.getParameter("uname");
				String upassword=request.getParameter("upassword");
				String u_number=request.getParameter("u_number");
				String standby_1=request.getParameter("standby_1");
				User userlist=new User();
				userlist.setU_number(u_number);
				
				List<User> list_uname=this.userbiz.findUserInfo(userlist);
				if(!list_uname.isEmpty()){
					user.setStatus("0");
					return gson.toJson(user);
				}
				userlist.setUname(uname);
				userlist.setU_number(null);
				list_uname=this.userbiz.findUserInfo(userlist);
				if(!list_uname.isEmpty()){
					user.setStatus("-1");
					return gson.toJson(user);
				}
				userlist.setUname(uname);
				userlist.setU_number(u_number);
				userlist.setUpassword(upassword);
				userlist.setStandby_1(standby_1);
				this.userbiz.addUser(userlist);
				user.setStatus("1");
				return gson.toJson(user);
				
		}

			
		
		
				//作家登录
				@RequestMapping(value="/Alogger",produces = {"application/text;charset=UTF-8"})
				@ResponseBody
				public String Alogger(HttpServletRequest request,Model model) {
					logger.info("Alogger.......");
					User user=new User();
					Gson gson=new Gson();
					HttpSession session = request.getSession();
					String validateCode=request.getParameter("validateCode");
					if(validateCode!=null &&validateCode!=""){
						String randCode=(String) session.getAttribute("rand");
						if(!validateCode.equals(randCode)){
							session.setAttribute("errmsg", "验证码错误");
							user.setStatus("-3");
							return gson.toJson(user);
						}
					}
					String u_number=request.getParameter("u_number");
					String upassword=request.getParameter("upassword");
					
					
					if(u_number!="" && u_number!=null && upassword!="" && upassword!=null){
						List<User> list=authorbiz.inforByu_number(u_number);
						if(!list.isEmpty()){
								if(list.get(0).getUpassword().equals(upassword)){
									user.setUname(list.get(0).getUname());
									
									user.setUid(list.get(0).getUid());
									user.setU_number(list.get(0).getU_number());
									user.setUpassword(upassword);
									user.setStatus("1");
									session.setAttribute("users",user);
									StaticContain.USERID=user.getAid();
									return gson.toJson(user);
								}else{
									user.setStatus("0");//密码错误
									return gson.toJson(user);
								}
						}else{
							user.setStatus("-2");//还不是作家
							return gson.toJson(user);
						}		
					}else{
						user.setStatus("-1");//用户名或密码为空
						return  gson.toJson(user);
					}	
				}	
		
	
	//注销登陆
	@RequestMapping(value="/uploging")
	@ResponseBody
	public String uploging(HttpServletRequest request){
		logger.info("注销登陆.....");
			request.getSession().setAttribute("users", null);
			request.getSession().removeAttribute("users");
			return "1";
	}
	
	// 排行榜，按类型显示数据
	@RequestMapping(value = "/toindex_type")
	public String Index_type(Model model) {
		List<NovelType> Tlist = novelTypebizImpl.showType(noveltype); // 小说类型
		
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
			}
		}
		
		model.addAttribute("list",Tlist);
		return "rank";
	}
	
	
	//作家专区
	@RequestMapping(value ="/authorPrefectrue1")
	public String authorPrefectrue(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		logger.info("authorPrefectrue.....");
		List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
		model.addAttribute("list",list1);
		List<Author> list=new ArrayList<Author>();	
		HttpSession session = request.getSession(); //session==null的话会报错，所以判断的时候要信判断session能不能为空
		User uuser=(User)(session.getAttribute("users"));
		//Object uuid=session.getAttribute("uuid");
		if(uuser!=null){//表示没有登入不能进
				Integer uid=Integer.parseInt(String.valueOf(uuser.getUid()));
				list=authorbiz.inforByunumber(uid);
				if(!list.isEmpty()){
				Integer aid=list.get(0).getAid();
				//List<Novel> novel=authorbiz.inforByaid(aid); 根据作家id得到自己的书籍信息
				model.addAttribute("author",list);
				StaticContain.USERID=aid;
				return "AuthorPrefecture";		
				}else{
					System.out.println("调到作家注册页面2");
					return "authorUser";//调到作家注册页面2
				}
			
		}else{
	    	return "Authorlogger";
		}	
		
	
	}
	
	//作家信息编辑
	@RequestMapping(value = "/editor")
	public String editor(HttpServletRequest request,Model model){
		
		HttpSession session = request.getSession();
		User uuser=(User)(session.getAttribute("users"));
		Integer uid=Integer.parseInt(String.valueOf(uuser.getUid()));
		List<Author> list=authorbiz.inforByunumber(uid);
//		Integer aid=list.get(0).getAid();
//		List<Novel> novel=authorbiz.inforByaid(aid);
		
		model.addAttribute("author",list);
	return "EditorAuthor";		
	}
	
	
	//保存作家信息
	@RequestMapping(value = "/saveAuthor")
	public String save(HttpServletRequest request,Model model){
		String Said=request.getParameter("aid");
		int aid=Integer.parseInt(Said);
		String pan_name=request.getParameter("pan_name");
		String Saage=request.getParameter("aage");
		int aage=Integer.parseInt(Saage);
		String acard=request.getParameter("acard");
		String atel=request.getParameter("atel");
		authorbiz.updataAuthor(pan_name, aage, acard, atel, aid);
		
		List<Author> list=authorbiz.FindAuthorByaid(aid);	
		model.addAttribute("author",list);
	return "AuthorPrefecture";		
	}
	
	
	//作者信息显示小说的信息
	@RequestMapping(value="/AuthorNovel",produces = {"application/text;charset=UTF-8"})
    @ResponseBody
    public String quearyNovel(@RequestParam int aid,HttpServletRequest request){
    	logger.info("quearyNovel.....");
    	//Novel novel=new Novel();
    	//String nname=text.substring(0,1)+"%";
//    	novel.setNname(nname);
//    	novel.setPan_name(nname);
    	String page=request.getParameter("page");    
    	String rows=request.getParameter("rows");
    	int currentPage=Integer.parseInt(page);     //当前的页数
    	int end=Integer.parseInt(rows);           //每页的条数
    	int start=0;
    	start=(currentPage-1)*end;
    	//TODO:这里的分页由于某些原因，暂时通过Java实现
    	//List<Novel> lists=this.novelbiz.FindAllNovel();
    	//    List<Novel> lists=this.novelbiz.findNovelByName(novel);
    	List<Novel> lists=authorbiz.inforByaid(aid);
    	//List<Novel> list=this.novelbiz.FindNovelByPage(start, end);
    	//   List<Novel> list=this.novelbiz.FindNovelByaid(aid, start, end);
    	List<Novel> list=new ArrayList<Novel>();//通过java代码实现分页
    	Integer st=(Integer.valueOf(page)-1)*Integer.valueOf(rows);//获取应取得第一个值得索引
    	Integer len=st+Integer.valueOf(rows);
    	for(int i=st;i<len;i++){
    		if((lists.size())<=i){//防止数组溢出
    			break;
    		}
    		list.add(lists.get(i));
    	}
    	EasyuiFindByPage ebp=new EasyuiFindByPage();
    	ebp.setTotal(lists.size());
    	ebp.setRows(list);
    	Gson gson=new Gson();
		return gson.toJson(ebp);
    	//这个功能并没有从数据库中拿数据
    }
	
	//作家专区小说信息编辑
	@RequestMapping(value="/editNovel/{nid}")
    public String editNovel(@PathVariable String nid,Model model){
    	logger.info("editNovel.....");
    	
    	int num=Integer.parseInt(nid);
    	
    	List<Novel> list=novelbiz.ShowTNovel(num);
    	model.addAttribute("novel",list);
		return "ENovel";
    }
	
	//作家专区小说信息编辑
		@RequestMapping(value="/writeNovel/{nid}")
	    public String writeNovel(@PathVariable String nid,Model model){
	    	logger.info("editNovel.....");	    	
	    	int num=Integer.parseInt(nid);	
	    	List<Novel> list=novelbiz.ShowTNovel(num);
	    	model.addAttribute("novel",list);
			return "writenovel";
	    }
	
	
	//作家专区部分显示小说类型
	@RequestMapping(value="/ShowType",produces = {"application/text;charset=UTF-8"})
	@ResponseBody
    public String ShowType(@RequestParam int nid,Model model){
    	logger.info("ShowType.....");
    	List<Novel> list=novelbiz.ShowTNovel(nid);
    	Gson gson=new Gson();
		return gson.toJson(list);
    }
	
	//作家专区部分显示小说状态
	@RequestMapping(value="/Shownstatus",produces = {"application/text;charset=UTF-8"})
	@ResponseBody
    public String ShowNstatus(@RequestParam int nid,HttpServletRequest request,Model model){
    	logger.info("Shownstatus.....");
    	List<Novel> list=novelbiz.ShowNovel_id(nid);
    	String status=list.get(0).getNstatus();
    	Gson gson=new Gson();
		return gson.toJson(status);
    }
	
	private String pdfRootName="uploadPdfs";
	//作家专区小说信息编辑页面保存
		@RequestMapping(value="/saveedit")
	    public String Saveedit(@ModelAttribute Novel novel,HttpServletRequest request,Model model) throws IOException{
	    	logger.info("editNovel.....");
	    	//String npicture="";
	    	String npicture1=" ";

	    	int nid=novel.getNid();
	    	String nname=novel.getNname();
	    	int tid=novel.getTid();
	    	String nstatus=novel.getNstatus();
	   	
	    	if(nname!=null ){
				Map<String,UploadFile> map= UploadFileUtil.uploadFile(request, novel.getPdfsUrl(), pdfRootName);
				for(Entry<String,UploadFile> entry:map.entrySet()){
					UploadFile uploadFile=entry.getValue();
					npicture1+=uploadFile.getNewFileUrl();
				}
	    	}
	    	    	
	    	novelbiz.UpdateNovel(nname, npicture1,nstatus,nid,tid);
	    	//model.addAttribute("novel",list);
			return "redirect:authorPrefectrue1";
	    }
		
	
	//首页标题的类型分类显示
	@RequestMapping(value = "/toindex_Type/{tname}")
	public String Index_Type(@PathVariable String tname, Model model) throws SQLException, UnsupportedEncodingException {
		/*byte[] t_byte = tname.getBytes("ISO-8859-1");//把Windows默认的编码集解码
		String str = new String(t_byte, "utf-8"); //组装成utf-8
		*/
	    String str=tname;
	    if(tname=="c" || tname.equals("c")){
	        str="c#/c/c++";
	    }
	    List<NovelType> list = novelTypebizImpl.showType(noveltype); // 小说类型
		List<Alllist> list1=randomUtils.Type_infor(str);
		List<NovelType> Tlist=novelTypebizImpl.TnameByType(str);
		model.addAttribute("list",list);
		model.addAttribute("tname",str);
		model.addAttribute("list1",list1);
		model.addAttribute("Tlist",Tlist);
		return "TypeAll";
	}

	//前往注册页面
	@RequestMapping(value="/toregister")
	public String register(Model model){
		List<NovelType> list1 = novelTypebizImpl.showType(noveltype); // 小说类型
		model.addAttribute("list",list1);
		return "register";
	}
	
	
	//TXT下载
	@RequestMapping(value = "/txt_id/{nid}")
	public ResponseEntity<byte[]> Download_txt(@PathVariable int nid, Model model,HttpServletResponse response) throws IOException {
			List<Novel> list=novelbiz.ShowNovel_id(nid);
			String nname=list.get(0).getNname()+".txt";
			byte[] content=jsoupUtils.Chapter(nid);
			HttpHeaders headers = new HttpHeaders();    
			String fileName=new String(nname.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
			headers.setContentDispositionFormData("attachment", fileName);   
			headers.setContentType(MediaType.TEXT_PLAIN);
			return new ResponseEntity<byte[]>(content,headers, HttpStatus.CREATED);
		   
	}
	//第二次大改    创造书本按钮  
	//前往创造书籍界面
	@RequestMapping(value="/nove-create")
	public String noveCreate(){
	    return "forward:creatnovel";
	}
	

}
