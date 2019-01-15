package com.yc.webexam.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.ChapterBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.impl.StudentCheckTotalBizImpl;
import com.yc.po.Chapter;
import com.yc.po.Edition;
import com.yc.po.Subject;
import com.yc.utils.JedisUtils;
import com.yc.utils.JsonUtil;
import com.yc.vo.ChapterPage;
import com.yc.vo.RedisTotal;

import redis.clients.jedis.Jedis;

public class ChapterAction extends BaseAction implements ServletResponseAware, ModelDriven<ChapterPage> {
	private static final Logger logger = Logger.getLogger(ChapterAction.class);
	private HttpServletResponse response;
	private Jedis jedis=new Jedis(JedisUtils.REDIS_URL,JedisUtils.REDIS_PORT);
	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;

	@Resource(name = "chapterBiz")
	private ChapterBiz chapterBiz;

	private String jsonStr;

	private PrintWriter out;

	private String id;
	
	@Resource(name="studentCheckTotalBizImpl")
	private StudentCheckTotalBizImpl sctbi;
	

	private ChapterPage chapterPage = new ChapterPage();

	
	public void setSctbi(StudentCheckTotalBizImpl sctbi) {
		this.sctbi = sctbi;
	}


	public ChapterPage getChapterPage() {
		return chapterPage;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	HttpServletRequest req = ServletActionContext.getRequest();

	public void showAllChapter() {
		Integer page = Integer.parseInt(req.getParameter("page"));
		Integer rows = Integer.parseInt(req.getParameter("rows"));

		//System.out.println(chapterPage.getRows());

		List<Chapter> chapter;
		List<ChapterPage> chapters;
		try {
			chapter = chapterBiz.getChapterList(chapterPage, page, rows);
			Map<String, Object> map = new HashMap<String, Object>();
			chapters = new ArrayList<ChapterPage>();
			if (chapter != null && chapter.size() > 0) {
				for (int i = 0,len=chapter.size(); i < len; i++) {
					ChapterPage chapterPage = new ChapterPage();
					chapterPage.setId(chapter.get(i).getId());
					chapterPage.setEditionName(chapter.get(i).getSubject().getEdition().getEditionName());
					chapterPage.setSemester(chapter.get(i).getSubject().getSemester());
					chapterPage.setSubjectName(chapter.get(i).getSubject().getSubjectName());
					chapterPage.setRemark(chapter.get(i).getRemark());
					chapterPage.setChapterName(chapter.get(i).getChapterName());
					chapterPage.setSeq(chapter.get(i).getSeq());
					chapters.add(chapterPage);
				}

				int total = chapterBiz.getChapterList(chapterPage, null, null).size();
				map.put("total", total);
				map.put("rows", chapters);
			} else {
				map = new HashMap<String, Object>();
				map.put("total", 0);
				map.put("rows", chapter);
			}

			jsonStr = JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "出现异常");
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}

	}

	public void subjectName() throws IOException {
		List<Subject> subject=null;
		id=req.getParameter("id");
		if(id==null || id==""){
			subject = (List<Subject>) subjectBiz.getSubjectsByedit("1");
		}else{
			subject = (List<Subject>) subjectBiz.getSubjectsByedit(id);
		}

		Edition edition = new Edition();
		if (subject != null && subject.size() > 0) {
			for (int i = 0,len=subject.size(); i <len ; i++) {
				//edition.setEditionName(subject.get(i).getEdition().getEditionName());
				subject.get(i).setPointInfos(null);
				subject.get(i).setChapters(null);
				//subject.get(i).setEdition(edition);
				subject.get(i).setEdition(null);
				subject.get(i).setPointPapers(null);
			}
		}try {
			if (subject != null && subject.size() > 0) { // SerializerFeature.DisableCircularReferenceDetect
				// 不能循环引用
				jsonStr = super.writeJson(0,  subject);
			}else{
				jsonStr=super.writeJson(2,  "没有数据");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}

	public void updateChapter() throws IOException {
		int cid = chapterPage.getId();
		/*String newChapterName = new String(chapterPage.getChapterName().getBytes("iso-8859-1"), "UTF-8");
		String chapterRemark = new String(chapterPage.getRemark().getBytes("iso-8859-1"), "UTF-8");*/
		HttpServletRequest request=ServletActionContext.getRequest();
		String newChapterName =request.getParameter("chapterName");
		String chapterRemark = request.getParameter("remark");
		Chapter chapter = chapterBiz.findChapterById(cid);
		chapter.setId(cid);
		chapter.setChapterName(newChapterName);
		chapter.setRemark(chapterRemark);

		try {
			int result = chapterBiz.updateChapter(chapter);
			if (result > 0) {
				jsonStr = super.writeJson(0, "修改成功");
			} else {
				jsonStr = super.writeJson(1, "修改失败");
			}
		} catch (Exception e) {
			logger.error(e);
			jsonStr = super.writeJson(1, "修改出现异常：" + e.toString());
		}
		JsonUtil.jsonOut(jsonStr);

	}

	public void deleteChapter() {
		int cid = chapterPage.getId();
		Chapter chapter = chapterBiz.findChapterById(cid);
		int subjectId = chapter.getSubject().getId();
		try {
			int rs = chapterBiz.deleteChapter(cid);
			if (rs > 0) {
				int count = chapterBiz.getChapterCount(subjectId); // 根据科目id获取章节数
				int res = subjectBiz.updateChapterCount(count, subjectId);
				if (res > 0) {
					jsonStr = super.writeJson(0, null);
				} else {
					jsonStr = super.writeJson(1, "删除失败");
				}
			} else {
				jsonStr = super.writeJson(1, "删除失败,或因为该章节不能删除");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			jsonStr = super.writeJson(1, "删除出现异常" + e.toString());
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void addChapter() throws IOException {
		Integer subjectName = Integer.parseInt(req.getParameter("subjectName"));
		String chapterName = req.getParameter("chapterName");
		String remark = req.getParameter("remark");
		String seq=req.getParameter("seq");
		//int subjectId = subjectBiz.getSubjectId(subjectName);
		int subjectId = subjectName;
		Subject subject = new Subject();
		subject.setId(subjectName);

		Chapter chapter = new Chapter();
		chapter.setSubject(subject);
		//chapter.setChapterName(chapterName);
		chapter.setChapterName(chapterName);
		chapter.setRemark(remark);
		chapter.setSeq(Integer.valueOf(seq));
		try {
			int result = chapterBiz.addChapter(chapter);

			if (result > 0) {
				int count = chapterBiz.getChapterCount(subjectId); // 根据科目id获取章节数
				int rs = subjectBiz.updateChapterCount(count, subjectId);
				if (rs > 0) {
					jsonStr = super.writeJson(0, null);
				} else {
					jsonStr = super.writeJson(1, "添加失败");
				}

			} else {
				jsonStr = super.writeJson(1, "添加失败");
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "添加出现异常，请联系管理员");
			e.printStackTrace();
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);

	}
	/**
	 * 获取redis中的数据,已经封装好了
	 */
	public void getTotal(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String name=request.getParameter("name");
		String semester=request.getParameter("semester");
		String cid=request.getParameter("cid");
		//this.jedis.get()
		RedisTotal rt=new RedisTotal();
		try {
			jedis.connect();
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error(e1);
			try {
				Runtime.getRuntime().exec("redis-server");
				jsonStr = super.writeJson(1, "数据正在获取中，请稍候");
				JsonUtil.writeJson(jsonStr);
				return;
			} catch (IOException e) {
				logger.error("redis自启动失败，请和管理员联系");
				jsonStr = super.writeJson(1, "redis自启动失败 ,请和管理员联系");
				JsonUtil.writeJson(jsonStr);
				return ;
			}
		}
		String str1=jedis.get(semester+"_"+cid+"_checkOut");
		String str2=jedis.get(semester+"_"+cid+"_checkOut_LastWeek");
		String str3=jedis.get((semester+"_"+cid+"_"+name).trim());
		String str4=jedis.get((semester+"_"+cid+"_"+name+"_LastWeek").trim());
		
		if(str1==null){
			String strS1=jedis.get("S1"+"_"+cid+"_checkOut");
			String strS2=jedis.get("S2"+"_"+cid+"_checkOut");
			String strS3=jedis.get("S3"+"_"+cid+"_checkOut");
			if(strS1==null){
				strS1="0";
			}
			if(strS2==null){
				strS2="0";
			}
			if(strS3==null){
				strS3="0";
			}
			if(Integer.parseInt(strS1)+Integer.parseInt(strS2)+Integer.parseInt(strS3)==0){
				this.sctbi.setTotalInfo();
				return;
			}
			str1="0";
		}
		if(str2==null){
			str2="0";
		}
		if(str3==null){
			str3="0";
		}
		if(str4==null){
			str4="0";
		}
		rt.setCheckCount(Integer.parseInt(str1));
		rt.setCheckCountLastM(Integer.parseInt(str2));
		rt.setCheckGood(Integer.parseInt(str3));
		rt.setCheckGoodLastM(Integer.parseInt(str4));

		 str1=jedis.get(semester+"_"+cid+"_checkOut_work");
		 str2=jedis.get(semester+"_"+cid+"_checkOut_LastWeek_work");
		 str3=jedis.get(semester+"_"+cid+"_"+name+"_work");
		 str4=jedis.get(semester+"_"+cid+"_"+name+"_LastWeek_work");
		if(str1==null){
			str1="0";
		}
		if(str2==null){
			str2="0";
		}
		if(str3==null){
			str3="0";
		}
		if(str4==null){
			str4="0";
		}
		rt.setWorkCount(Integer.parseInt(str1));
		rt.setWorkCountLastM(Integer.parseInt(str2));
		rt.setWorkGood(Integer.parseInt(str3));
		rt.setWorkGoodLastM(Integer.parseInt(str4));
		String names=cid+"_"+name;

		str1=jedis.get(names+"_ADailyTalk");
		str2=jedis.get(names+"_LastMonth_ADailyTalk");
		if(str1==null){
			str1="0";
		}
		if(str2==null){
			str2="0";
		}
		rt.setaDailyTalkCount(Integer.parseInt(str1));
		rt.setaDailyTalkCountLastM(Integer.parseInt(str2));

		str1=jedis.get(cid+"_WritingPaper");
		str2=jedis.get(cid+"_LastMonth_WritingPaper");
		str3=jedis.get(names+"_WritingAnswer");
		str4=jedis.get(names+"_LastMonth_WritingAnswer");
		if(str1==null){
			str1="0";
		}
		if(str2==null){
			str2="0";
		}
		if(str3==null){
			str3="0";
		}
		if(str4==null){
			str4="0";
		}
		rt.setWritingPaperCount(Integer.parseInt(str1));
		rt.setWritingPaperCountLastM(Integer.parseInt(str2));
		rt.setWritingPaperGood(Integer.parseInt(str3));
		rt.setWritingPaperGoodLastM(Integer.parseInt(str4));
		
		String strs="<h3>详情统计--"+name+"</h3><br/><table width='100%'>";
		strs+="<tr height='25'> <th bordercolor='#7EA6DC' width='50%'>该学期考勤次数</th>";
		strs+="<th bordercolor='#7EA6DC' width='50%'>该学期(已到)出勤次数</th></tr>";
		strs+="<tr style='text-align:center' bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\"><td>"+rt.getCheckCount()+"</td>"+"<td>"+rt.getCheckGood()+"</td></tr>";
		strs+="</table>";
		
		strs+="<br/><table width='100%'>";
		strs+="<tr height='25'><th bordercolor='#7EA6DC' width='50%'>上月总考勤次数</th> <th bordercolor='#7EA6DC' width='50%'>上月(已到)出勤次数</th></tr>";
		strs+="<tr style='text-align:center' bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\">";
		strs+="<td>"+rt.getCheckCountLastM()+"</td>"+"<td>"+rt.getCheckGoodLastM()+"</td></tr>";
		strs+="</table>";
		
		
		strs+="<br/><table width='100%'>";
		strs+="<tr height='25'> <th bordercolor='#7EA6DC' width='50%'>该学期作业布置次数</th>";
		strs+="<th bordercolor='#7EA6DC' width='50%'>该学期作业完成次数</th></tr> ";
		strs+="<tr style='text-align:center' bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\"><td>"+rt.getWorkCount()+"</td>"+"<td>"+rt.getWorkGood()+"</td>";
		strs+="</table>";
		
		strs+="<br/><table width='100%'>";
		strs+="<tr height='25'><th bordercolor='#7EA6DC' width='50%'>上月作业布置次数</th> <th bordercolor='#7EA6DC' width='50%'>上月作业完成次数</th></tr>";
		strs+="<tr style='text-align:center' bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\">";
		strs+="<td>"+rt.getWorkCountLastM()+"</td>"+"<td>"+rt.getWorkGoodLastM()+"</td></tr>";
		strs+="</table>";
		
		strs+="<br/><table width='100%'>";
		strs+="<tr height='25'> <th bordercolor='#7EA6DC' width='50%'>每日一讲次数</th>";
		strs+="<th bordercolor='#7EA6DC' width='50%'>上月每日一讲次数</th> </tr>";
		strs+="<tr style='text-align:center' bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\"><td>"+rt.getaDailyTalkCount()+"</td>"+"<td>"+rt.getaDailyTalkCountLastM()+"</td></tr>";
		strs+="</table>";
		
		strs+="<br/><table width='100%'>";
		strs+="<tr height='25'> <th bordercolor='#7EA6DC' width='25%'>考试次数</th>";
		strs+="<th bordercolor='#7EA6DC' width='25%'>完成考试次数</th> <th bordercolor='#7EA6DC' width='25%'>上月考试次数</th>";
		strs+="<th bordercolor='#7EA6DC' width='25%'>上月完成考试次数</th></tr>";
		strs+="<tr style='text-align:center' bgcolor=\"#EDECEB\" onmouseover=\"this.bgColor='#93BBDF';\" onmouseout=\"this.bgColor='#EDECEB';\"><td>"+rt.getWritingPaperCount()+"</td>"+"<td>"+rt.getWritingPaperGood()+"</td>";
		strs+="<td>"+rt.getWritingPaperCountLastM()+"</td>"+"<td>"+rt.getWritingPaperGoodLastM()+"</td></tr>";
		strs+="</table>";
		
		jsonStr = super.writeJson(0, strs);
		JsonUtil.writeJson(jsonStr);

	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	@Override
	public ChapterPage getModel() {
		return chapterPage;
	}

	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}

	public void setChapterBiz(ChapterBiz chapterBiz) {
		this.chapterBiz = chapterBiz;
	}

}
