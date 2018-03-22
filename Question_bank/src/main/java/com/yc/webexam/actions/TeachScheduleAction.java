package com.yc.webexam.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.yc.biz.ChapterBiz;
import com.yc.biz.ClassRoomBiz;
import com.yc.biz.CurriculumBiz;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.SystemUserBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Chapter;
import com.yc.po.ClassRoom;
import com.yc.po.Curriculum;
import com.yc.po.ExamineeClass;
import com.yc.po.SystemUser;
import com.yc.po.TeacherCurriModel;
import com.yc.utils.JsonUtil;
import com.yc.utils.ValueUtil;
import com.yc.utils.YcConstants;
import com.yc.vo.CurriculumPage;


/**
 * 
 * @author lyh
 * @version 创建时间：2017年4月22日 下午7:56:30
 * @ClassName 课表管理处理类
 * @Description 类处理界面传过来的所有的请求
 */
public class TeachScheduleAction extends BaseAction implements ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1107107308023089084L;
	private static final Logger logger = Logger.getLogger(TeachScheduleAction.class);
	private HttpServletResponse response;
	private PrintWriter out;
	HttpServletRequest request = ServletActionContext.getRequest();
	private ExamineeClassBiz examineeClassBiz;
	private SystemUserBiz systemUserBiz;
	private ClassRoomBiz classRoomBiz;
	private CurriculumBiz curriculumBiz;
	private ChapterBiz chapterBiz;
	private ExamineeBiz examineeBiz;
	private String bid;//班级编号
	
	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	@Resource(name = "examineeBiz")
	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}

	@Resource(name = "examineeClassBiz")
	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	@Resource(name = "systemUserBiz")
	public void setSystemUserBiz(SystemUserBiz systemUserBiz) {
		this.systemUserBiz = systemUserBiz;
	}

	@Resource(name="classRoomBiz")
	public void setClassRoomBiz(ClassRoomBiz classRoomBiz) {
		this.classRoomBiz = classRoomBiz;
	}

	@Resource(name="curriculumBiz")
	public void setCurriculumBiz(CurriculumBiz curriculumBiz) {
		this.curriculumBiz = curriculumBiz;
	}


	@Resource(name="chapterBiz")
	public void setChapterBiz(ChapterBiz chapterBiz) {
		this.chapterBiz = chapterBiz;
	}

	@Override
	public void setServletResponse(HttpServletResponse resp) {
		resp.setCharacterEncoding("utf-8");
		this.response = resp;

		try {
			this.out = this.response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//查询所有的班级
	public void examClass() {
		String jsonStr = "";
		try {
			List<ExamineeClass> AllexamineeClass = this.examineeClassBiz.findExamineeClassByTime(ValueUtil.getNowDate());
			List<ExamineeClass> getAllexamineeClass=new ArrayList<ExamineeClass>();
			for(ExamineeClass eClass:AllexamineeClass){
				ExamineeClass e=new ExamineeClass();
				e.setClassName(eClass.getClassName());
				e.setId(eClass.getId());
				getAllexamineeClass.add(e);
			}
			jsonStr = super.writeJson(0, getAllexamineeClass);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	//制作表格
	public String getTable(){
		String jsonStr = "";
		List<List<Object>> list=new ArrayList<List<Object>>();
		String a[]=this.bid.split(",");

		//Arrays.sort(a); 

		List<Object> list2=new ArrayList<Object>();//存储班级
		List<Object> list3=new ArrayList<Object>();//存储教师
		List<Object> list4=new ArrayList<Object>();//存储教室
		try {
			for(int i=0,len=a.length;i<len;i++){
				//全选
				if(a[i]=="-1" || a[i].equals("-1")){
					//findExamineeClassByTime1 如果有选择的查询 无法 cast成 examineeclass 
					List<ExamineeClass> AllexamineeClass = this.examineeClassBiz.findExamineeClassByTime(ValueUtil.getNowDate());

					for(ExamineeClass eClass:AllexamineeClass){
						ExamineeClass e=new ExamineeClass();
						if(eClass.getNotice()==null||"".equals(eClass.getNotice())){
							eClass.setNotice("暂无");
						}
						e.setId(eClass.getId());
						e.setClassName(eClass.getClassName());
						e.setNotice(eClass.getNotice());
						e.setStudentcount(eClass.getStudentcount());
						list2.add(e);
					}

					break;
				}else{
					ExamineeClass e=new ExamineeClass();
					e=this.examineeClassBiz.findExamineeClassById(Integer.parseInt(a[i]));
					ExamineeClass e2=new ExamineeClass();
					if(e.getNotice()==null||"".equals(e.getNotice())){
						e.setNotice("暂无");
					}
					e2.setNotice(e.getNotice());
					e2.setId(e.getId());
					e2.setClassName(e.getClassName());
					e2.setStudentcount(e.getStudentcount());
					list2.add(e2);
				}
			}
			//查询出所有的教师
			List<SystemUser> sList=this.systemUserBiz.searchUser(1);
			for(SystemUser sUser:sList){
				SystemUser sUser2=new SystemUser();
				sUser2.setBgcolor(sUser.getBgcolor());
				sUser2.setId(sUser.getId());
				sUser2.setFontcolor(sUser.getFontcolor());
				sUser2.setUserName(sUser.getUserName());
				list3.add(sUser2);
			}
			//查询出所有的教室
			List<ClassRoom> cList=this.classRoomBiz.getAllClassRoom(1);
			for(ClassRoom cRoom:cList){
				list4.add(cRoom);
			}
			list.add(list2);//顺序不能更改
			list.add(list3);
			list.add(list4);
			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return SUCCESS;
	}

	/**
	 * 得到课程信息
	 * @return
	 */
	public String getClassInfo(){
		String jsonStr = "";
		List<List<Object>> list=new ArrayList<List<Object>>();
		List<Object> list2=new ArrayList<Object>();//已经上的两节课
		List<Object> list3=new ArrayList<Object>();//未上的八节课
		try {
			List<Curriculum> cList=curriculumBiz.getCurriculum(Integer.parseInt(bid),null,null,8);//查询已经上的两节课
			List<Chapter> chapters=null;
			if(cList==null){//表示还没开始上过课
				chapters=chapterBiz.getEightChapter(0,Integer.parseInt(bid),8);
			}else{ 
				Chapter chapter= chapterBiz.findChapterById(cList.get(0).getChapterid());//得到seq
				chapters= chapterBiz.getEightChapter(chapter.getSeq(),Integer.parseInt(bid),8);
			}
			if(chapters!=null){
				for(Chapter c:chapters){
					Chapter chapter=new Chapter();
					chapter.setId(c.getId());
					chapter.setChapterName(c.getChapterName());
				list3.add(chapter);
			}
			}
			if(cList!=null){
				for(Curriculum curri:cList){
					Curriculum curriculum=new Curriculum();
					curriculum.setChapterid(curri.getChapterid());
					curriculum.setChapterName(curri.getChapterName());
					list2.add(curriculum);
				}
			}
			list.add(list2);
			list.add(list3);
			jsonStr = super.writeJson(0, list);

		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return SUCCESS;
	}
	HttpServletRequest req = ServletActionContext.getRequest();
	/*
	 * 插入课程
	 * classArr 一个班级的课程 ; 
	 * tempArr 获取 班级id
	 * middleArr 一个班级的所有课程
	 * curriArr 一节课的数据
	 */
	public String insertCurri(){
		String jsonStr = "";
		//一个班级的课程
		String[] classArr;
		//一个班的一节课程
		List<String> curriArr; 
		String [] middleArr;
		String [] tempArr;//获取班级
		int classid;
		try {
			String array=req.getParameter("objJson");
			//System.out.println("objJson:"+array);
			if(array.contains("null,")){
				array=array.replaceAll("null,", "").trim();
			}
			if(array.length()>0){
				classArr=array.split("]],");
				for(int i=0,len=classArr.length;i<len;i++){
					tempArr=classArr[i].split(":");
					List<String> temp=splitArray(tempArr[0]);
					if(temp.get(0).contains("'")){
						classid=Integer.parseInt(temp.get(0).substring(1,temp.get(0).length()-1));
					}else{
						classid=Integer.parseInt(temp.get(0));
					}
					middleArr=new String[classArr.length];
					if(classArr[i].contains("],")){
						middleArr=classArr[i].split("],");
					}else{
						middleArr[0]=classArr[i];
					}
					try {
						List<Curriculum> list=new ArrayList<Curriculum>();
						for(int j=0,len1=middleArr.length;j<len1;j++){
							Curriculum cur=new Curriculum();
							cur.setClassid(classid);
							//获取 字符串中的 所有 双引号中的 内容
							curriArr=splitArray(middleArr[j]);
							//如果有班级id（该班只有一节课）那么 size=6 要从第1个开始取  如果没有班级id 那么size=5 从第0个开始取
							if(curriArr.size()==6){
								cur.setTeacherid(Integer.parseInt(curriArr.get(1)));
								cur.setChapterid(Integer.parseInt(curriArr.get(2)));
								cur.setClassroomid(Integer.parseInt(curriArr.get(3)));
								cur.setTimeperiods(curriArr.get(4));
								cur.setDate(curriArr.get(5));
							}

							if(curriArr.size()==5){
								cur.setTeacherid(Integer.parseInt(curriArr.get(0)));
								cur.setChapterid(Integer.parseInt(curriArr.get(1)));
								cur.setClassroomid(Integer.parseInt(curriArr.get(2)));
								cur.setTimeperiods(curriArr.get(3));
								cur.setDate(curriArr.get(4));
							}
							list.add(   cur );
						}
						this.curriculumBiz.insertIntoCurriculum(list);
						jsonStr = super.writeJson(0, "插入成功");
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e);
						jsonStr = super.writeJson(1, e);
					}
				}
			}
		}finally{
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return SUCCESS;
	}

	/**
	 * status 0 老师  1 班级  2 教室
	 */

	//获取所有老师 课程信息
	public void getAllTeacherCurriInfo(){
		String startDate=req.getParameter("startDate");
		String endDate=req.getParameter("endDate");
		String teacherid=req.getParameter("teacherid");
		String jsonStr = "";
		Integer status=0;// 0 老师  1 班级  2 教室
		List<TeacherCurriModel> returnList=new ArrayList<TeacherCurriModel>();
		try {
			//获取正常数据
			List list=this.curriculumBiz.getCurriInfo(startDate, endDate, teacherid,status);
		
			//合班上课
			if(list.size()>0){
				returnList=this.combineClass(list);
			}
			
			jsonStr = super.writeJson(0, returnList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			jsonStr = super.writeJson(1, "查询失败");
		}finally{
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}  
	}

	//获取所有 班级  课程信息
	public String getAllClassCurriInfo(){
		String startDate=req.getParameter("startDate");
		String endDate=req.getParameter("endDate");
		String classid=req.getParameter("classid");
		String jsonStr = "";
		int status=1;// 0 老师  1 班级  2 教室

		try {
			//获取正常数据
			List list=this.curriculumBiz.getCurriInfo(startDate, endDate, classid,status);

			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			jsonStr = super.writeJson(1, "查询失败");
		}finally{
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}  
		return SUCCESS;
	}


	//获取所有教室 课程信息
	public String getAllClassroomCurriInfo(){
		String startDate=req.getParameter("startDate");
		String endDate=req.getParameter("endDate");
		String classroomid=req.getParameter("classroomid");
		String jsonStr = "";
		Integer status=2;
		List<TeacherCurriModel> returnList=new ArrayList<TeacherCurriModel>();

		try {
			//获取正常数据
			List list=this.curriculumBiz.getCurriInfo(startDate, endDate, classroomid,status);
			//合班上课
			if(list.size()>0){
				returnList=this.combineClass(list);
			}
			jsonStr = super.writeJson(0, returnList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			jsonStr = super.writeJson(1, "查询失败");
		}finally{
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}  
		return SUCCESS;
	}

	//获得所有的教室信息
	public String getAllClassroom(){
		String jsonStr = "";
		try {
			List list=this.classRoomBiz.getAllClassRoom(1);
			jsonStr = super.writeJson(0, list);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			jsonStr = super.writeJson(1, "查询失败");
		}finally{
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		} 
		return SUCCESS;
	}


	//合班上课
	public List<TeacherCurriModel> combineClass(List list){
		List<TeacherCurriModel> returnList=new ArrayList<TeacherCurriModel>();

		for(int i=0,len=list.size();i<len;i++){
			TeacherCurriModel obj = (TeacherCurriModel) list.get(i); 

			int temp=0;
			for(int k=0,len1=returnList.size();k<len1;k++){
				/*
					if(obj.getDate().equals(returnList.get(k).getDate()) && obj.getTimeperiods().equals(returnList.get(k).getTimeperiods()) && obj.getClassroomname().equals(returnList.get(k).getClassroomname()) &&obj.getClassname().equals(returnList.get(k).getClassname()) ){
						break;
					}
				 */
				//同一date 同一periods 同一教室的不同班级 同一老师
				if(obj.getUsername().equals(returnList.get(k).getUsername() )&& obj.getDate().equals(returnList.get(k).getDate()) && obj.getTimeperiods().equals(returnList.get(k).getTimeperiods()) && obj.getClassroomname().equals(returnList.get(k).getClassroomname()) &&!obj.getClassname().equals(returnList.get(k).getClassname()) ){
					returnList.get(k).setClassname(returnList.get(k).getClassname()+"/"+obj.getClassname());
					break;
				}
				temp++;
			}

			if(temp==returnList.size()){
				returnList.add(obj);
			}
		}
		return returnList;
	}

	//获取 字符串中的 所有 双引号中的 内容
	public List<String> splitArray(String str){
		int count=0;//双引号个数
		for(int i=0,len=str.length();i<len;i++){
			String s=str.substring(i,i+1);
			if("\"".equals(s)){
				count++;
			}
		}

		List<String> list=new ArrayList<String>();
		for(int i=0;i<count;i=i+2){
			str=str.substring(str.indexOf("\"")+1,str.length());
			String strTemp=str.substring(0,str.indexOf("\""));
			str=str.substring(strTemp.length()+1) ;
			list.add(strTemp);
		}
		return list;
	}

	/*
	 * 加载所有的老师信息
	 */
	public void getAllTeacher() {
		String jsonStr = "";
		try {
			// 查询出所有的教师
			List<SystemUser> list = new ArrayList<SystemUser>();
			List<SystemUser> sList = this.systemUserBiz.searchUser(1);
			for (SystemUser sUser : sList) {
				SystemUser user = new SystemUser();
				user.setId(sUser.getId());
				user.setUserName(sUser.getUserName());
				list.add(user);
			}
			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	
	/*
	 * 加载所有的访谈老师的信息
	 */
	public void getAllTeacherInterview() {
		String jsonStr = "";
		try {
			// 查询出所有的教师
			List<SystemUser> list = new ArrayList<SystemUser>();
			List<SystemUser> sList = this.systemUserBiz.searchUser();
			for (SystemUser sUser : sList) {
				SystemUser user = new SystemUser();
				user.setId(sUser.getId());
				user.setUserName(sUser.getUserName());
				list.add(user);
			}
			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 加载老师的月度课时统计
	 */
	@SuppressWarnings("unchecked")
	public void getTeacherSchedule() {
		String jsonStr = "";
		String tid = req.getParameter("id");//得到指定老师的id
		int month=Integer.parseInt(req.getParameter("month"));//得到月份
		int year=Integer.parseInt(req.getParameter("year"));//得到年份
		String startTime = ValueUtil.getDate(year,month-2, 26);// 得到上上一个月
		String endTime = ValueUtil.getDate(year,month-1, 25);// 得到上一个月
		List<TeacherCurriModel> returnList = new ArrayList<TeacherCurriModel>();//接收从数据库查出的数据
		List<CurriculumPage> curriculumPages = new ArrayList<CurriculumPage>();//存储发送前台页面的数据
		List list =new ArrayList<>(); 
		try {
			//int totalSeduce=this.curriculumBiz.getTotalCount(startTime, endTime);//查询出月份总的课时
			list=this.curriculumBiz.getAllTeacherCurriInfo(startTime, endTime, tid);//查询出指定老师的课程情况

			// 判断合班上课
			if (list.size() > 0 && list != null) {
				for (int i = 0,len=list.size(); i < len; i++) {
					TeacherCurriModel obj = (TeacherCurriModel) list.get(i);//映射到实体类
					int temp = 0;
					for (int k = 0,len1=returnList.size(); k < len1; k++) {
						// 同一老师 同一date 同一periods 同一教室的不同班级
						if (obj.getUsername().equals(returnList.get(k).getUsername()) && obj.getDate().equals(returnList.get(k).getDate())
								&& obj.getTimeperiods().equals(returnList.get(k).getTimeperiods())
								&& obj.getClassroomname().equals(returnList.get(k).getClassroomname())
								&& !obj.getClassname().equals(returnList.get(k).getClassname())) {
							returnList.get(k)
							.setClassname(returnList.get(k).getClassname() + "/" + obj.getClassname());
							break;
						}
						temp++; 
					}
					if (temp == returnList.size()) {//说明循环完毕
						returnList.add(obj);
					}
				}
				 
				for (TeacherCurriModel tModel : returnList) {// 进行课时统计
					CurriculumPage curriculumPage = new CurriculumPage();
					if (tModel.getClassname().contains("/")) {//合班的操作
						int studentCount2=0;
						int studentCount=0;
						String className=tModel.getClassname();//学生人数统计
						String[] aStrings=className.split("/");
						//班级排序
						List<String> list1 = Arrays.asList(aStrings);
						Collections.sort(list1,new Comparator<String>() {
							@Override
							public int compare(String o1, String o2) {
								return Integer.parseInt(o1.replaceAll("[^0-9]*", "")) - Integer.parseInt(o2.replaceAll("[^0-9]*", ""));
							}
						});
						for(int i=0,len=list1.size();i<len;i++){
							int number=this.examineeBiz.getStudentCount(aStrings[i], 4);
							studentCount2+=number;//查询流失人数
							ExamineeClass examineeClass=this.examineeClassBiz.findExamineeClassByName(aStrings[i]);//查询总人数
							studentCount+=examineeClass.getStudentcount();//查询总人数
					}
						curriculumPage.setStudentCount(studentCount);
						curriculumPage.setStudentCount2(studentCount2);
						String className1="";
						for(int i=0,len=list1.size()-1;i<len;i++){
							className1+=list1.get(i)+"/";
						}
						className1+=list1.get(list1.size()-1);
						curriculumPage.setClassName(className1);
						curriculumPage.setTeacherName(tModel.getUsername());
						curriculumPage.setTotalSedule(null);
						curriculumPage.setUniqueName(className1+"-"+tModel.getUsername());
						curriculumPage.setTeachCount(YcConstants.SeduceNum);
					} else {
						String className=tModel.getClassname();//通过班名查学生人数统计
						int studentCount2=0;
						int studentCount=0;
						int number=this.examineeBiz.getStudentCount(className, 4);
						studentCount2=studentCount2+number;//查询流失人数
						ExamineeClass examineeClass=this.examineeClassBiz.findExamineeClassByName(className);//查询总人数
						studentCount+=examineeClass.getStudentcount();//查询总人数
						curriculumPage.setStudentCount(studentCount-studentCount2);
						curriculumPage.setStudentCount2(studentCount2);
						curriculumPage.setClassName(tModel.getClassname());
						curriculumPage.setTeacherName(tModel.getUsername());
						curriculumPage.setTotalSedule(null);
						curriculumPage.setUniqueName(tModel.getClassname()+"-"+tModel.getUsername());
						curriculumPage.setTeachCount(YcConstants.SeduceNum);
						
					}
					curriculumPage.setMonthTotalSedule(null);
					//System.out.println(curriculumPage);
					curriculumPages.add(curriculumPage);
				}
				jsonStr = super.writeJson(0, curriculumPages);
			} else{
				jsonStr = super.writeJson(0, null);
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
 
	/*
	 * 更新时生成表格的数据
	 * 教师信息
	 *教室信息
	 *课程信息
	 */
	public  String updateToTable(){
		String jsonStr = "";
		List<List<Object>> list=new ArrayList<List<Object>>();
		List<Object> list2=new ArrayList<Object>();//已经上的五节课
		List<Object> list3=new ArrayList<Object>();//未上的五节课
		List<Object> list4=new ArrayList<Object>();//存储教师
		List<Object> list5=new ArrayList<Object>();//存储教室
		//Integer currid=Integer.parseInt(req.getParameter("id"));//当前课表id；
		String date=req.getParameter("date");//日期
		String timeperiods=req.getParameter("timeperiods");//时间
		try {
			List<Curriculum> cList=this.curriculumBiz.getCurriculum(Integer.parseInt(this.bid),date,timeperiods,10);//查询已经上的五节课
			List<Chapter> chapters=null;
			if(cList==null){
				chapters=this.chapterBiz.getEightChapter(0,Integer.parseInt(this.bid),5);//得到未上的五节课
			}else{
				Chapter chapter= chapterBiz.findChapterById(cList.get(0).getChapterid());//得到seq
				chapters= chapterBiz.getEightChapter(chapter.getSeq(),Integer.parseInt(bid),5);
			}
			if(chapters!=null){
				for(Chapter c:chapters){
					Chapter chapter=new Chapter();
					chapter.setId(c.getId());
					chapter.setChapterName(c.getChapterName());
				list3.add(chapter);
			}
			}
			if(cList!=null){
				for(Curriculum curri:cList){
					Curriculum curriculum=new Curriculum();
					curriculum.setChapterid(curri.getChapterid());
					curriculum.setChapterName(curri.getChapterName());
					list2.add(curriculum);
				}
			}
			//查询出所有的教师
			List<SystemUser> sList=this.systemUserBiz.searchUser(1);
			for(SystemUser sUser:sList){
				SystemUser sUser2=new SystemUser();
				sUser2.setBgcolor(sUser.getBgcolor());
				sUser2.setId(sUser.getId());
				sUser2.setFontcolor(sUser.getFontcolor());
				sUser2.setUserName(sUser.getUserName());
				list4.add(sUser2);
			}
			//查询出所有的教室
			List<ClassRoom> clList=this.classRoomBiz.getAllClassRoom(1);
			for(ClassRoom cRoom:clList){
				list5.add(cRoom);
			}
			list.add(list2);//已上课程
			list.add(list3);//未上课程
			list.add(list4);//教师信息
			list.add(list5);//教室信息

			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return SUCCESS;

	}
	/**
	 * 课表更新操作
	 */
	public String updateCurriInfo(){
		String jsonStr = "";
		try {
			//String array=req.getParameter("CurrInfo");
			 String strings=req.getParameter("CurrInfo");
			 List<String> list=null;
			 list= splitArray(strings);
			
			 int result=0;
			 if(!"-1".equals(list.get(0))){
				 Curriculum curriculum=new Curriculum();
				 curriculum.setId(Integer.parseInt(list.get(0)));
				 curriculum.setTeacherid(Integer.parseInt(list.get(1)));
				 curriculum.setChapterid(Integer.parseInt(list.get(2)));
				 curriculum.setClassroomid(Integer.parseInt(list.get(3)));
				 result=this.curriculumBiz.updateCurri(curriculum);
			 }else{//进行插入
					List<Curriculum> list1=new ArrayList<Curriculum>();
					 Curriculum curriculum=new Curriculum();
					 curriculum.setTeacherid(Integer.parseInt(list.get(1)));
					 curriculum.setChapterid(Integer.parseInt(list.get(2)));
					 curriculum.setClassroomid(Integer.parseInt(list.get(3)));
					 curriculum.setClassid(Integer.parseInt(list.get(4)));
					 curriculum.setDate(list.get(6));
					 curriculum.setTimeperiods(list.get(5));
					 list1.add(curriculum);
				result= this.curriculumBiz.insertIntoCurriculum(list1);
			 }
			 if(result==1){
				jsonStr = super.writeJson(0, "操作成功");
			 }else{
				 jsonStr = super.writeJson(1, "操作失败");
			 }
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "插入失败");
			logger.error(e);
		} finally{
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return SUCCESS;
	}
	/**
	 * 课表删除
	 * @return
	 */
	public String deleteCurriInfo(){
		String jsonStr = "";
		try {
			//String array=req.getParameter("CurrInfo");
			Integer CurrId=Integer.parseInt(req.getParameter("currId"));
			int result=0;
			 result=this.curriculumBiz.deleteCurri(CurrId);
				jsonStr = super.writeJson(0, "删除成功");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "删除失败");
			logger.error(e);
		} finally{
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return SUCCESS;
	}
	/**
	 * 查询出所有的课程章节
	 */
	public void getAllChapter() {
		String jsonStr = "";
		try {
		//	ExamineeClass eClass=this.examineeClassBiz.findExamineeClassById(Integer.parseInt(this.bid));//得到班级所属类型
			List<Chapter> list=null;
			list=this.chapterBiz.getAllChapter1(Integer.parseInt(this.bid));
			for(Chapter chapter:list){
				chapter.setRemark(null);
				chapter.setSubject(null);
				chapter.setSeq(null);
			}
			jsonStr = super.writeJson(0, list);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	
	/**
	 * 查询出所有的课程章节
	 */
	public void isHaveCurri() {
		String jsonStr = "";
		String classid=request.getParameter("bid");
		String date=request.getParameter("date");
		String timeperiods=request.getParameter("time");
		classid=classid.substring(1, classid.length()-1);
		System.out.println("classidclassidclassid:"+classid);
		Curriculum curriculum=new Curriculum();
		curriculum.setClassid(Integer.valueOf(classid));
		curriculum.setDate(date);
		curriculum.setTimeperiods(timeperiods);
		try {
			List<Curriculum> list=this.curriculumBiz.isHaveCurris(curriculum);
			if(list.size()>0){
				jsonStr = super.writeJson(1, "1");
			}else{
				jsonStr = super.writeJson(0, "0");
			}
		}finally{
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	
	
	
}
