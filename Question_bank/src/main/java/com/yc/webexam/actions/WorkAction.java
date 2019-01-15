package com.yc.webexam.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.DictationBiz;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.WorkBiz;
import com.yc.po.Dictation;
import com.yc.po.Examinee;
import com.yc.po.Work;
import com.yc.utils.JsonUtil;
import com.yc.vo.ListClassPage;
import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;


@SuppressWarnings("serial")
public class WorkAction extends BaseAction implements ServletRequestAware, ServletResponseAware, ModelDriven<Work>{

	private static final Logger logger = Logger.getLogger(WorkAction.class);

	@Resource(name = "workBiz")
	private WorkBiz workBiz;

	@Resource(name = "dictationBiz")
	private DictationBiz dictationBiz;

	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;

	@Resource(name = "examineeBiz")
	private ExamineeBiz examineeBiz;

	private Work work=new Work();

	private String jsonStr;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	private HttpSession mysession = request.getSession();


	public void setServletResponse(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			this.request = request;
			this.mysession = this.request.getSession();
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	public void setWorkBiz(WorkBiz workBiz) {
		this.workBiz = workBiz;
	}

	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}

	@Override
	public Work getModel() {
		return work;
	}



	public void addWork() throws IOException{

		int classcount =examineeBiz.getExamineeCount(work.getExamineeclassid());

		String creator=(String) request.getSession().getAttribute("userName");
		work.setClasscount(classcount);
		work.setCheckcreator(creator);
		int result=(int) this.workBiz.addWork(work);
		try {
			if(result>0){
				jsonStr = super.writeJson(0, null);
			}else{
				jsonStr = super.writeJson(1, "添加失败");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "添加出现异常，请联系管理员");
			e.printStackTrace();
			logger.error(e);
		}

		JsonUtil.jsonOut(jsonStr);
	}

	

	public void adddictation() throws IOException{
		String path=this.getClass().getResource("/").getPath();
		String  date=request.getParameter("data");
		date=date.replace("<p>","\t\t\t");
		date=date.replace("</p>","");
		date=date.replace("&nbsp;"," ");
		date=date.replace("&quot;","\"");
		date=date.replace("&lt;","<");
		date=date.replace("&gt;",">");
		date=date.replace("&amp;","&");
		Integer wid=Integer.valueOf(request.getParameter("wid"));
		HttpSession mysession = request.getSession();
		String userName=String.valueOf(mysession.getAttribute("userName"));
		String examClass=String.valueOf(mysession.getAttribute("examClass"));
		for(int i=0;i<4;i++){
			path=path.substring(0,path.lastIndexOf("/"));
		}
		File file=new File(path+File.separator+examClass+File.separator+wid);
		if(!file.exists()){
			file.mkdirs();
		}
		
		path+=File.separator+examClass+File.separator+wid+File.separator+userName+".txt";
		File filedate=new File(path);
		if(!filedate.exists()){
			filedate.createNewFile();
		}
		FileOutputStream fos=new FileOutputStream(filedate);
		fos.write(date.getBytes(), 0, date.getBytes().length);
		fos.flush();
		fos.close();
		Dictation dictation=new Dictation();
		dictation.setWid(wid);
		dictation.setWorkinfo(path);
		dictation.setExamineename("");
		int result=this.dictationBiz.addDictation(dictation);
		try {
			if(result>0){
				jsonStr = super.writeJson(0, null);
			}else{
				jsonStr = super.writeJson(1, "添加失败");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "添加出现异常，请联系管理员");
			e.printStackTrace();
			logger.error(e);
		}

		JsonUtil.jsonOut(jsonStr);
	}

	public void findWorkdictation() throws IOException{
		String semester=request.getParameter("semester");
		Integer classid=Integer.valueOf(request.getParameter("classname"));
		List<Work> works=this.workBiz.findWorkbydictation(classid, semester);
		if(works.size()>0&&works!=null){
			jsonStr = super.writeJson(0, works);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);
	}

	public void findWork() throws IOException{
		String semester=request.getParameter("semester");
		Integer classid=Integer.valueOf(request.getParameter("check_examineeClass"));
		String checkDate=request.getParameter("checkDate");
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		List<Work> works=this.workBiz.findWork(classid, semester, checkDate,page,rows);
		//int total=workBiz.findworknum(classid,null,null,null,checkDate);
		if(works.size()>0&&works!=null){
			jsonStr = super.writeJson(0, works);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		/*Map<String,Object> map=new HashMap<String,Object>();
		map.put("rows", works);
		map.put("total", total);
		jsonStr=JSON.toJSONString(map);*/
		JsonUtil.jsonOut(jsonStr);
	}


	public void finddictationwork() throws IOException{
		HttpSession mysession = request.getSession();
		String examClass= (String) mysession.getAttribute("examClass");

		int classid=this.examineeClassBiz.findExamineeClassByName(examClass).getId();

		List<Work> works=this.workBiz.findDictation(classid);
		if(works.size()>0&&works!=null){
			jsonStr = super.writeJson(0, works);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);
	}

	public void findteacherWorkpool() throws IOException{
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		List<TeacherWorkdetail> listwork=this.workBiz.getteacherWorkdetail(year, month);
		if(listwork!=null){
			jsonStr = super.writeJson(0, listwork);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);

	}

	public void findteacherWorkdetail() throws IOException{
		String teachername =request.getParameter("teachername");
		int year=Integer.valueOf(request.getParameter("year").trim());
		int month=Integer.valueOf(request.getParameter("month").trim());
		List<Work> list=this.workBiz.getTeacherchekwork(teachername, year, month);
		List<Workdetail> works=new ArrayList<Workdetail>();
		if(list!=null){
			for(Work work:list){
				Workdetail wt=new Workdetail();
				int classid=work.getExamineeclassid();
				wt.setExamineeclassid(classid);
				String classname=this.examineeClassBiz.findExamineeClassById(classid).getClassName();
				wt.setCheckcount(work.getCheckcount());
				wt.setWorkname(work.getWname());
				wt.setClassName(classname);
				wt.setClasscount(this.examineeBiz.getExamineeCount(classid));
				if(work.getCheckcount()==null||work.getCheckcount()==0){
					wt.setCompletionrate("0.0");
				}else{
					wt.setCompletionrate(String.valueOf((work.getCheckcount()/work.getCheckcount())));
				}
				works.add(wt);
			}
			jsonStr = super.writeJson(0, works);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}

		JsonUtil.jsonOut(jsonStr);
	}

	public void findClassWorkdetail() throws IOException{
		int year=Integer.valueOf(request.getParameter("year"));
		int month=Integer.valueOf(request.getParameter("month"));
		int classid=Integer.valueOf(request.getParameter("classid"));

		List<Work> listwork=this.workBiz.findWorkdetail(classid, year, month);
		List<Work> listworks=new ArrayList<Work>();
		for(Work work:listwork){
			work.setClasscount(this.examineeBiz.getExamineeCount(classid));
			listworks.add(work);
		}
		if(listworks!=null){
			jsonStr = super.writeJson(0, listworks);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);
	}

	public void findClassWorkpool() throws IOException{
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		List<Workdetail> listwork=this.workBiz.getclassWorkdetail(year, month,null);

		if(listwork!=null){
			jsonStr = super.writeJson(0, listwork);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);
	}

	public void adddicationwork() throws IOException{
		Integer wid=Integer.valueOf(request.getParameter("wid"));
		int result=this.workBiz.updateworkstatus(wid);
		if(result>0){
			jsonStr = super.writeJson(0, "成功");
		}else{
			jsonStr = super.writeJson(1, "无修改失败");
		}

		JsonUtil.jsonOut(jsonStr);
	}

	public void findWorkbyid() throws IOException{
		Integer wid=Integer.valueOf(request.getParameter("wid"));

		Work works=this.workBiz.findWorkbyid(wid);

		if(works!=null){
			jsonStr = super.writeJson(0, works);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}

		JsonUtil.jsonOut(jsonStr);
	}

	public void checkWork() throws IOException{
		Integer wid=work.getWid();
		Work w=this.workBiz.findWorkbyid(wid);

		Integer classid=w.getExamineeclassid();
		String className=this.examineeClassBiz.getClassNameById(classid);
		List<Examinee> examinee = examineeBiz.findAllStuNameByClassName(className);
		List<ListClassPage> list = new ArrayList<ListClassPage>();
		if(examinee.size()>0 &&examinee!=null){
			for (int i = 0,len=examinee.size(); i < len; i++) {
				ListClassPage listClassPage = new ListClassPage();
				listClassPage.setName(examinee.get(i).getName());
				listClassPage.setClassName(examinee.get(i).getExamineeClass().getEditionId().toString());
				//传回去的实际是学生状态  （修改结果）
				listClassPage.setIdCard(examinee.get(i).getStudentStatus().toString());
				listClassPage.setId(examinee.get(i).getId());
				listClassPage.setTel(examinee.get(i).getTel());
				list.add(i, listClassPage);
			}
			jsonStr = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
			jsonStr=super.writeJson(0, list);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}

		JsonUtil.jsonOut(jsonStr);

	}


	public void findstudentWorkresult() throws Exception{
		String semester=request.getParameter("semester");
		String startDate=request.getParameter("startdate");
		String endDate=request.getParameter("enddate");
		if (startDate == null || "".equals(startDate)) {
			startDate = "1911-1-1";
		}
		if (endDate == null || "".equals(endDate)) {
			endDate = "2050-12-30";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date a = sdf.parse(startDate);
		Date b = sdf.parse(endDate);
		boolean flag = a.before(b);
		if (!flag) {
			String c = "";
			c = startDate;
			startDate = endDate;
			endDate = c;
		}
		String userName=(String) mysession.getAttribute("userName");
		String classname=(String) mysession.getAttribute("examClass");
		int classid=this.examineeClassBiz.getClassIdOfname(classname);
		List<Work> list=this.workBiz.getstudentwork(classid, semester,startDate, endDate);
		List<Work> listwork=new ArrayList<Work>();
		for(Work work:list){
			String result=work.getResult();
			if(result==null||"".equals(result)){
				work.setResult("null");
			}else{
				String[] str=result.split("\\|");
				for(String st:str){
					if(st.contains(userName)){
						if(st.contains("1")){
							work.setResult("true");
						}else{
							work.setResult("false");
						}
						break;
					}
				}
			}

			listwork.add(work);
		}
		if(listwork.size()>0){
			jsonStr = super.writeJson(0, listwork);
		}else{
			jsonStr = super.writeJson(1, "无数据");
		}

		JsonUtil.jsonOut(jsonStr);

	}


	public void addCheckResult() throws IOException{
		Integer wid=Integer.valueOf(request.getParameter("wid"));
		String resultInfo=request.getParameter("resultInfo");
		String checkcount=request.getParameter("checkcount");
		int result=this.workBiz.updateWorkresult(wid, resultInfo,checkcount);
		if(result>0){
			jsonStr = super.writeJson(0, "提交成功");
		}else{
			jsonStr = super.writeJson(1, "提交失败");
		}

		JsonUtil.jsonOut(jsonStr);
	}


	public void findictation_detail() throws IOException{
		Integer wid=Integer.valueOf(request.getParameter("wid"));
		String name=request.getParameter("name");
		Integer examineeclassid=Integer.valueOf(request.getParameter("examineeclassid"));
		String classname=this.examineeClassBiz.findExamineeClassById(examineeclassid).getClassName();
		String path=this.getClass().getResource("/").getPath();
		for(int i=0;i<4;i++){
			path=path.substring(0,path.lastIndexOf("/"));
		}
		path+=File.separator+classname+File.separator+wid+File.separator+name+".txt";
		
		try {
			StringBuffer sb=new StringBuffer();
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	sb.append(System.lineSeparator()+s);
            }
            jsonStr = super.writeJson(0, sb);
            br.close(); 
		} catch (IOException e) {
			jsonStr = super.writeJson(1, "无结果");
		}finally {
			JsonUtil.jsonOut(jsonStr);
		}
		
	}
	
	
	public void findWorkresult() throws IOException, ParseException{

		/*if(works!=null){
			jsonStr = super.writeJson(0, works);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}*/
		List<Work> works=this.workBiz.findWorkbyclass(work,null,null);
		/*	int total=workBiz.findworknum(work.getExamineeclassid(),String.valueOf(work.getEditionid()),String.valueOf(work.getSubjectid()),String.valueOf(work.getChapterid()),null);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rows", works);
		map.put("total", total);   //用于easyui格式的拼接
		jsonStr=JSON.toJSONString(map);*/
		if(works.size()>0&&works!=null){
			jsonStr = super.writeJson(0, works);
		}else{
			jsonStr = super.writeJson(1, "无结果");
		}
		JsonUtil.jsonOut(jsonStr);

	}

}
