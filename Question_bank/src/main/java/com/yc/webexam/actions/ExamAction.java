package com.yc.webexam.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;

import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.LabAnswerBiz;
import com.yc.biz.LabPaperBiz;
import com.yc.biz.PointAnswerBiz;
import com.yc.biz.PointInfoBiz;
import com.yc.biz.PointPaperBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.WritingAnswerBiz;
import com.yc.biz.WritingPaperBiz;
import com.yc.po.ExamineeClass;
import com.yc.po.PointAnswer;
import com.yc.po.PointInfo;
import com.yc.po.PointPaper;
import com.yc.po.WritingAnswer;
import com.yc.po.WritingPaper;
import com.yc.po.WritingQuestion;
import com.yc.utils.ExamUtil;
import com.yc.utils.JedisUtils;
import com.yc.utils.JsonUtil;
import com.yc.utils.UTFUtil;
import com.yc.utils.ValueUtil;


@SuppressWarnings("serial")
@Scope("prototype")
public class ExamAction extends BaseAction implements ServletRequestAware, ServletResponseAware {
	private static final Logger logger = Logger.getLogger(ExamAction.class);

	ArrayList arr = new ArrayList();

	private HttpSession mysession;

	public void setServletResponse(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		this.response = response;
		try {
			this.out = this.response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setServletRequest(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			this.request = request;
			this.mysession = this.request.getSession();
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	private PrintWriter out;

	private String paperId; // 试卷编号
	private String textPassword; // 试卷密码
	private String userName; // 考生姓名
	private String examClass; // 考生班级

	private WritingPaper wp = new WritingPaper();
	private WritingAnswer wa = new WritingAnswer();

	List<PointPaper> openPointPaper = null;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();



	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getTextPassword() {
		return textPassword;
	}

	public void setTextPassword(String textPassword) {
		this.textPassword = textPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getExamClass() {
		return examClass;
	}

	public void setExamClass(String examClass) {
		this.examClass = examClass;
	}

	@Resource(name = "writingPaperBiz")
	private WritingPaperBiz writingPaperBiz;

	@Resource(name = "labPaperBiz")
	private LabPaperBiz labPaperBiz;

	@Resource(name = "writingAnswerBiz")
	private WritingAnswerBiz writingAnswerBiz;

	@Resource(name = "labAnswerBiz")
	private LabAnswerBiz labAnswerBiz;

	@Resource(name = "aDailyTalkBiz")
	private ADailyTalkBiz aDailyTalkBiz;

	@Resource(name = "pointPaperBiz")
	private PointPaperBiz pointPaperBiz;

	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;

	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;

	@Resource(name = "pointInfoBiz")
	private PointInfoBiz pointInfoBiz;

	@Resource(name = "pointAnswerBiz")
	private PointAnswerBiz pointAnswerBiz;

	/**
	 * 开始考试前的考卷编号和密码的验证
	 * 
	 * @author fpc
	 */
	public void loginPassWord() {
		String jsonStr = "";
		paperId = request.getParameter("paperId");
		examClass = writingPaperBiz.getExamClass(paperId);
		textPassword = request.getParameter("password");
		String examineeName = (String) mysession.getAttribute("userName");
		wp.setId(paperId);
		wp.setPaperPwd(textPassword);

		try {
			// 判断是否存在所在班级的考卷编号
			/*boolean boo = writingPaperBiz.isExistsPaperByClass(paperId, examClass);
			if (!boo) {
				out.print(3);
				return;
			}*/
			// 判断此考生是否已参加了此次考试。“您已参加了本次考试，并已交了卷，不可以再次进入考试!”
			boolean flag = writingAnswerBiz.isHandInPaper(paperId, examineeName);
			if (flag) {
				out.print("2");
				return;
			}
			// 判断是否存在此编号和状态的试卷，此编号的试卷还没到考试时间，不可以考试！
			boolean boo1 = writingPaperBiz.isExistsPaperByState(paperId, ExamUtil.PAPER_STATUS_IS_TESTING);
			if (!boo1) {
				out.print(4);
				return;
			}
			//若死机 进来判断一下 时间
			WritingAnswer wa = this.writingAnswerBiz.searchWritingAnswer(paperId, examineeName);
			
			if (wa != null && wa.getSpareTime()!=null) {
				// 先要判断时间是否用完
				if (wa.getSpareTime() <= 0) {
					out.print("6");
				}
				// 有可能是异常、死机或点关闭按钮退出的，可以再次进入
				else {
					out.print("5");
				}
				return;
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "错误");
			logger.error(e);
		} 
		// 如果文本框无输入,输出用"0"表示假,重定向到原来页面
		if (textPassword == null || "".equals(textPassword)) {
			out.print("0");
			// try {
			// ((HttpServletResponse) response)
			// .sendRedirect("Jsp/examinee/writpaperpassword.jsp");
			// } catch (IOException ex) {
			// ex.printStackTrace();
			// }
		}
		// 否则判断密码输入是否正确
		else {
			try {
				// 如果正确输出"1",以表示真。成功登录
				if (writingPaperBiz.validateWritingPaperPassword(wp)) {
					// out.print("1");
					JsonUtil.jsonOut("1");
				}
				// 否则输出"0",表示假。编号或密码不正确
				else {
					out.print("0");
				}
			} catch (Exception e) {
				out.print(e);
				logger.error(e);
			} 
		}
	}

	/**
	 * 显示笔试卷
	 * 
	 * @author fpc
	 * @return
	 */
	public void beginExam() {
		String jsonStr="";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		HttpSession mysession = request.getSession();
		// 打开考试页面时，添加答卷信息
		String paperId = request.getParameter("paperId");
		String examineeName = (String) mysession.getAttribute("userName");
		String examClass = (String) mysession.getAttribute("examClass");
		try {
			WritingPaper wp = writingPaperBiz.getWritingPaper(paperId);
			// 将本试卷的时间放入会话中
			map.put("examTime", wp.getTimesConsume());
			// 获得试卷的所有题目内容,放在集合中
			/*ArrayList<WritingQuestion> arr = (ArrayList<WritingQuestion>) writingPaperBiz
					.getWritingPaperQuestions(paperId);*/
			ArrayList<WritingQuestion> arr = (ArrayList<WritingQuestion>) writingPaperBiz
					.getWritingPaperQuestions(wp.getQuestionId());
			//将问题用一定的规则开始排版
			for(int i=0,len=arr.size();i<len;i++){
				String question=arr.get(i).getQuestion();
				String str=null;
				str=question.replaceAll("<br/>", "");
				str=str.replaceAll("\\r\\n", "<br/>");
				str=str.replaceAll("\\{", "{<br/>");
				str=str.replaceAll("\\}", "}<br/>");
				str=str.replaceAll("\\}", "<br/>}");
				str=str.replaceAll(";", ";<br/>");
				//str=question.replaceAll(";", ";<br/>");
				arr.get(i).setQuestion(str);
			}
			// 设置所有题目内容的属性
			map.put("questionList", arr);
			// 将所有题目内容的数量大小，转为字符串类型
			String questionCount = Integer.toString(arr.size());
			
			// 设置题目内容的数量大小
			map.put("questionCount", questionCount);
			// 这里不要用公共的对象，不然会出现两个人用同一个
			wa = this.writingAnswerBiz.searchWritingAnswer(paperId, examineeName);
			// wa为空表示没有本考生的答卷   (改)
			if (wa.getId() == null ) {
			//if (wa == null ) {
				// 进入以后往表WritingAnswer中加入一套试卷
				wa = new WritingAnswer();
				wa.setWritingPaper(wp);
				wa.setExamineeName(examineeName);
				wa.setSpareTime(wp.getTimesConsume());
				// 这里出现INSERT 语句与 COLUMN FOREIGN KEY 约束
				// 'FK_WritingAnswer_WritingPaper'
				// 冲突
				// 所以进入开始考试卷时有白屏的现像，再次进入就可以了
				writingAnswerBiz.addWritingAnswer(wa);
				map.put("spareTime", wp.getTimesConsume());
				logger.info(examClass.toUpperCase() + "班 学员：" + examineeName + " 开始考试，试卷编号为：" + paperId);
			}
			// 已经存在，表示再次进入
			else {
				// 得到考生已有的答案和剩余时间
				map.put("spareTime", wa.getSpareTime());
				String answer=writingAnswerBiz.getAnswer(paperId, examineeName);
				map.put("answer",answer);
				logger.info(examClass.toUpperCase() + "班 学员：" + examineeName + " 再次进入考试，试卷编号为：" + paperId + "，剩余时间："
						+ wa.getSpareTime() + "分钟,  答案: "+answer);
			}
			// 设置试卷的属性
			map.put("paper", wa);
			list.add(map);
			// 写入日制文件中
			String json = super.writeJson(0, list);
			out.print(json);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "错误");
			logger.error(e);
		} 
		
	}
	/**
	 * 浏览试卷
	 * 
	 */
	
	public void studentCheckAnswer(){
		String jsonStr="";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		HttpSession mysession = request.getSession();
		// 打开考试页面时，添加答卷信息
		String paperId = request.getParameter("paperId");
		String examineeName = (String) mysession.getAttribute("userName");
		String examClass = (String) mysession.getAttribute("examClass");
		try {
			WritingPaper wp = writingPaperBiz.getWritingPaper(paperId);
			// 将本试卷的时间放入会话中
			map.put("examTime", wp.getTimesConsume());
			// 获得试卷的所有题目内容,放在集合中
			ArrayList<WritingQuestion> arr = (ArrayList<WritingQuestion>) writingPaperBiz
					.getWritingPaperQuestions(paperId);
			// 设置所有题目内容的属性
			map.put("questionList", arr);
			// 将所有题目内容的数量大小，转为字符串类型
			String questionCount = Integer.toString(arr.size());
			// 设置题目内容的数量大小
			map.put("questionCount", questionCount);
			// 这里不要用公共的对象，不然会出现两个人用同一个
			wa = this.writingAnswerBiz.searchWritingAnswer(paperId, examineeName);
			// wa为空表示没有本考生的答卷   (改)
			if (wa.getId() == null ) {
				// 进入以后往表WritingAnswer中加入一套试卷
				wa = new WritingAnswer();
				wa.setWritingPaper(wp);
				wa.setExamineeName(examineeName);
				wa.setSpareTime(wp.getTimesConsume());
				// 这里出现INSERT 语句与 COLUMN FOREIGN KEY 约束
				// 'FK_WritingAnswer_WritingPaper'
				// 冲突
				// 所以进入开始考试卷时有白屏的现像，再次进入就可以了
				//writingAnswerBiz.addWritingAnswer(wa);
				map.put("spareTime", wp.getTimesConsume());
			}
			// 已经存在，表示再次进入
			else {
				// 得到考生已有的答案和剩余时间
				map.put("spareTime", wa.getSpareTime());
				map.put("answer", wa.getAnswer());

			}
			// 设置试卷的属性
			map.put("paper", wa);
			// 找现有的答案
			String strA = writingAnswerBiz.searchAnswer(paperId, examineeName);
			map.put("answer", strA);
			list.add(map);
			// 写入日制文件中
			
			String json = super.writeJson(0, list);
			out.print(json);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "错误");
			logger.error(e);
		} 
		
	}
	
	public void updateSpareTime() {
		String paperId = request.getParameter("paperId");
		String examineeName = request.getParameter("examineeName");
		String spareTime = request.getParameter("spareTime");
		// String examineeClass = request.getParameter("examineeClass");
		writingAnswerBiz.updateSpareTime(paperId, examineeName, spareTime);
	}
	
	
	/**
	 * 笔试答题时,每答一题保存一题 选从答卷表中答案找出，拆分出已答过的题目答案， 如果有现答的题目编号和已答的相同就更新答案，否则添加
	 * saveWritAnswer
	 */
	public void examAnswer() {
		String jsonStr="";
		String paperId = request.getParameter("paperId");
		//FIXED:ZX
		//String examineeName = UTFUtil.Utf8Util(request.getParameter("examineeName").trim());
		String examineeName = request.getParameter("examineeName");
		String answer = request.getParameter("answer");
		try {
			// 找现有的答案
			String strA = writingAnswerBiz.searchAnswer(paperId, examineeName);
			// 更新或添加过后的答案
			String strAnswer = "";
			if (strA == null || strA.equals("")) {
				// 第一题
				strAnswer = answer + ";";
			} else {
				String an[] = strA.split(";"); // 原有的答案
				boolean flag = true;
				// 如果有现答的题目编号和已答的相同就更新答案，否则添加
				String a[] = answer.split(","); // 现在的
				for (int i = 0,len=an.length; i < len; i++) {
					String s[] = an[i].split(","); // 原有的
					//String a[] = answer.split(","); // 现在的
					if (s[0].equals(a[0])) {
						an[i] = answer;
						flag = false;
					}
					strAnswer += an[i] + ";";
				}
				if (flag) {
					strAnswer += answer + ";";
				}
			}
			int n = writingAnswerBiz.updateAnswer(strAnswer, paperId, examineeName);
			if (n > 0) {
				JsonUtil.jsonOut("1");
				out.print("1");
				out.flush();
				out.close();
			}
			
		}catch (Exception e) {
			jsonStr = super.writeJson(1, "错误");
			logger.error(e);
		} 
	}

	
	//交卷
	public void handInPaper() {
		String userName = request.getParameter("examineeName");
		try {
			String paperId = request.getParameter("paperId");
			String examineeClass = request.getParameter("examineeClass");
			// 通过试卷编号得到试卷的正确答案
			WritingPaper wp = (writingPaperBiz.searchWritingPaper(paperId)).get(0);
			// 得到正确答案
			String rightAnswer = wp.getQuestionCorrect();
			// 得到共有多少道题
			int countQuestion = wp.getCountOfQuestion();
			// 计算出考生成绩，考生姓名从session中得到
			int score = writingAnswerBiz.computeScore(paperId, userName, rightAnswer, countQuestion);
			// 更新考生答卷表
			wa = writingAnswerBiz.searchWritingAnswer(paperId, userName);
			
			// wa.setScore(Float.parseFloat(score + ""));
			// wa.setScore(Float.parseFloat(score+""==null?Float.NaN:score));
			wa.setScore((float)score);
			
			writingAnswerBiz.exeUpdateGrade((float)score,wa.getId(),paperId, userName);
			
			// 只要学员交了试卷，试卷状态变为未评状态
			//TODO
			/*if (wp.getPaperStatus() == ExamUtil.PAPER_STATUS_IS_TESTING) {
				int n = writingPaperBiz.updateWritingPaper(paperId, ExamUtil.PAPER_STATUS_NOT_VIEW);
				if (n == 0) {
					logger.info("试卷：" + wp.getId() + " 第1位学员交卷,更新试卷状态失败！");
					// 更改状态失败，返回标识0
					JsonUtil.jsonOut("0");
				} else {
					logger.info("试卷： " + wp.getId() + " 第1位学员交卷，更改试卷状态为“未评”。");
				}
			}*/
			
			// 交卷写入日制文件
			logger.info(examineeClass + "班 学员：" + userName + " 正常交卷，试卷编号为：" + paperId + "，考试成绩：" + score + "，剩余时间："
					+ wa.getSpareTime() + "分钟");
			// 交卷成功，返回标识1
			JsonUtil.jsonOut("1");
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				JsonUtil.jsonOut("0");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询成绩
	 * 
	 * @throws IOException
	 */
	public void findGrade() throws IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String userName = ValueUtil.formatRequestStr(mysession.getAttribute("userName"));
		String examClass = ValueUtil.formatRequestStr(mysession.getAttribute("examClass"));
		String paperType = ValueUtil.formatRequestStr(request.getParameter("type"));
		// paperId = "1S16220130829071300";
		// 只显示老师评卷后的成绩，评完的才显示出来
		// 当请求的是查询笔试成绩时
		if (paperType.equals("writ")) {
			paperType = "writ";
			arr = (ArrayList) writingPaperBiz.searchExaminneWritAchievement(paperId, examClass, userName,
					ExamUtil.PAPER_STATUS_VIEWED);
		}
		// 当请求的是查询机试成绩时
		else if (paperType.equals("lab")) {
			arr = (ArrayList) labPaperBiz.searchExaminneLabAchievement(paperId, examClass, userName);
		}
		// 把查询到的信息存入请求对象中，再转向到页面
		map.put("paperType", paperType);
		map.put("arr", arr);
		map.put("userName", userName);
		map.put("paperId", paperId);
		list.add(map);
		JsonUtil.jsonOut(writeJson(0, list));
	}

	/**
	 * 查看试卷
	 * 
	 * @throws IOException
	 */
	public String lookPaper() throws IOException {

		String jsonStr="";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// 得到考生姓名,班级,所选答案。如果在窗体中用Post提交后，此处的汉字还是乱码，所以都用get
		try {
			String strExaminee = new String(request.getParameter("examineeName").getBytes("iso-8859-1"), "UTF-8");
			String strExamClass = request.getParameter("examClass");
			String answer = writingAnswerBiz.searchAnswer(paperId, strExaminee);
			// 通过试卷编号,得到此卷的信息
			wp = writingPaperBiz.getWritingPaper(paperId);
			ArrayList<WritingQuestion> arr = new ArrayList<WritingQuestion>();
			// 获得试卷的所有题目内容,放在集合中
			arr = (ArrayList<WritingQuestion>) writingPaperBiz.getWritingPaperQuestions(paperId);
			// 设置试卷的属性
			map.put("paper", wp);
			map.put("examinee", strExaminee);
			map.put("examClass", strExamClass);
			// 设置所有题目内容的属性
			map.put("questionList", arr);
			// 将所有题目内容的数量大小，转为字符串类型
			String questionCount = Integer.toString(arr.size());
			map.put("answer", ExamUtil.fillSpaceAnswer(answer, arr.size()));
			// 设置题目内容的数量大小
			map.put("questionCount", questionCount);
			list.add(map);
			//JsonUtil.jsonOut(writeJson(0, list));
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

		return "exam_lookPaper";
	}

	/**
	 * 自评测试
	 */
	public void myselfexam() {
		String jsonStr="";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String name = (String) mysession.getAttribute("userName"); // 获取登录考试的姓名
		String examClass = (String) mysession.getAttribute("examClass"); // 获取登录考生的班级
		try {
			int classid = aDailyTalkBiz.getCid(examClass);

			// 判断该生是否已经自评
			openPointPaper = pointPaperBiz.findOpenPointPaper(name, classid + ""); // 测评
			ExamineeClass examineeClass = new ExamineeClass();
			if (openPointPaper != null && openPointPaper.size() > 0) {
				// for(PointPaper pp:openPointPaper){
				// for(ExamineeClass ec:initClasses){
				// if(pp.getExamineeClass().getId()==ec.getId()){
				// pp.setRemark(ec.getClassName());
				//
				// }
				// }
				// }

				for (int i = 0,len=openPointPaper.size(); i < len; i++) {
					openPointPaper.get(i).setSubject(null);
					openPointPaper.get(i).setPointAnswers(null);
					examineeClass.setRemark(openPointPaper.get(i).getExamineeClass().getRemark());
					examineeClass.setaDailyTalks(null);
					openPointPaper.get(i).setExamineeClass(examineeClass);
					 
				}
			}

			map.put("openPointPaper", openPointPaper);
			list.add(map);
			// 遇到$refxxxx的循环引用，先用下面语句取消循环引用，在传json前传一次json，然后正常使用
			// JSON.toJSONString(openPointPaper,
			// SerializerFeature.DisableCircularReferenceDetect);
			String json = this.writeJson(0, list);
			//jsonStr = super.writeJson(0, json);
			out.write(json);
		} catch (Exception e) {
			//jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
		/*out.write(json);
		out.flush();
		out.close();*/
	}

	/**
	 * 查看自评
	 */
	public void myselfexam2() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String name = (String) mysession.getAttribute("userName"); // 获取登录考试的姓名
		String examClass = (String) mysession.getAttribute("examClass"); // 获取登录考生的班级
		int classid = aDailyTalkBiz.getCid(examClass);

		openPointPaper = pointPaperBiz.findEndPointPaper(name, classid + ""); // 查看结果
		
		
		logger.info("自评信息=====>"+openPointPaper);
		ExamineeClass examineeClass = new ExamineeClass();
		if (openPointPaper != null && openPointPaper.size() > 0) {
			for (int i = 0,len=openPointPaper.size(); i < len; i++) {
				openPointPaper.get(i).setSubject(null);
				openPointPaper.get(i).setPointAnswers(null);
				openPointPaper.get(i).setExamineeClass(null);
				openPointPaper.get(i).setDescript(null);
				openPointPaper.get(i).setPstatus(null);
				openPointPaper.get(i).setPaperPwd(null);
				openPointPaper.get(i).setPtitle(null);
				/*examineeClass.setRemark(openPointPaper.get(i).getExamineeClass().getRemark());
				openPointPaper.get(i).setExamineeClass(examineeClass);*/
			}
		}

		map.put("openPointPaper", openPointPaper);
		list.add(map);
		// 遇到$refxxxx的循环引用，先用下面语句取消循环引用，在传json前传一次json，然后正常使用
		// JSON.toJSONString(openPointPaper,
		// SerializerFeature.DisableCircularReferenceDetect);
		String json = this.writeJson(0, list);
		out.write(json);
		out.flush();
		out.close();
	}

	/**
	 * 自评试卷的密码检测
	 */
	public void checkPaperPassword() {
		String mypid = request.getParameter("myid").toString().trim(); // 测评试卷编号
		String mypwd = request.getParameter("mypwd").toString().trim(); // 测评试卷密码
		String pid = request.getParameter("pid").toString().trim(); // 测评试卷密码
		String pwd = request.getParameter("pwd").toString().trim(); // 测评试卷密码
		if (mypid.equals(pid) && mypwd.equals(pwd)) {
			out.print(3);
		} else {
			out.print(0);
		}
		out.flush();
		out.close();
	}

	/**
	 * 获取自评的信息
	 */
	public void getPoint() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		PointPaper pointPaper = null;
		String subjectName = null;
		String cid = null;
		List<PointInfo> pointContent = null;
		String pid = request.getParameter("pid").toString().trim(); // 测评试卷编号
		pointPaper = pointPaperBiz.findPointPaperById(Integer.parseInt(pid));
		if (pointPaper != null) {
			subjectName = subjectBiz.findSubjectName(pointPaper.getSubject().getId());// 查出课程名称
			cid = pointPaper.getPtitle();
			cid = cid.substring(0, cid.lastIndexOf(","));

			pointContent = pointInfoBiz.findPointInfo(cid); // 查出题目

			// 根据测评班级编号，查处测评班级
			pointPaper.setRemark(examineeClassBiz.getClassNameById(pointPaper.getExamineeClass().getId()));
		}

		PointPaper a = new PointPaper();
		a.setSubject(null);
		a.setDescript(pointPaper.getDescript());
		a.setExamineeClass(null);
		a.setFlag(null);
		a.setPaperPwd(null);
		a.setPdate(pointPaper.getPdate());
		a.setPid(pointPaper.getPid());
		a.setPname(pointPaper.getPname());
		a.setPointAnswers(null);
		a.setPstatus(null);
		a.setPtitle(null);
		a.setRemark(pointPaper.getRemark());

		map.put("subjectName", subjectName); // 自评课程名
		map.put("findPointPaperInfo", a); // 试卷信息
		map.put("pointContent", pointContent); // 知识点信息
		list.add(map);
		String json = this.writeJson(0, list);
		out.print(json);
		out.flush();
		out.close();
	}

	/**
	 * 自评交卷
	 */
	public void handInfpc() {
		String stuanswer = request.getParameter("stuanswer");
		String messageinfo = request.getParameter("messageInfo");
		String lengtha = request.getParameter("lengtha");
		String pid = request.getParameter("pid");

		List<ExamineeClass> initClasses = null;
		initClasses = examineeClassBiz.findAllClass();
		if (stuanswer != null && stuanswer != "" && stuanswer.split(",").length < Integer.parseInt(lengtha)) {
			out.println(2); // 未评完
		} else {
			String name = (String) mysession.getAttribute("userName");// 登录名
			String examClass = (String) mysession.getAttribute("examClass");// 考生班级
			int classid = 1;
			if (initClasses != null && initClasses.size() > 0 && examClass != null) {
				for (ExamineeClass ec : initClasses) {
					if (examClass.equals(ec.getClassName())) {
						classid = ec.getId();
						break;
					}
				}
			}
			PointAnswer pa = new PointAnswer();
			PointPaper pp = new PointPaper();
			pp.setPid(Integer.parseInt(pid));
			pp.setPaperPwd("a");
			pp.setPname("a");
			pp.setPtitle("a");
			pa.setCladdid(classid);
			pa.setName(name);
			pa.setPointPaper(pp);
			pa.setAnswer(stuanswer);
			pa.setIdea(messageinfo);
			pa.setRemark("");
			pa.setStatus("");
			int result = pointAnswerBiz.addPointAnswer(pa);
			if (result > 0) { // 修改考卷状态
				// pointPaperBiz.updatePointPaperStatus(Integer.parseInt(pid),
				// 3);
				out.println(1);
			} else {
				out.println(0);
			}
		}

		out.flush();
		out.close();
	}

	/**
	 * 查看自评结果
	 */
	public void lookPaperAnswer() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		String pid = request.getParameter("pid"); // 试卷编号
		String name = (String) mysession.getAttribute("userName"); // 获取登录考试的姓名
		String examClass = (String) mysession.getAttribute("examClass"); // 获取登录考生的班级
		int classid = aDailyTalkBiz.getCid(examClass);

		PointAnswer lookanswer = new PointAnswer();
		PointPaper lookpaper = new PointPaper();
		String sname = null;
		List<PointInfo> lookPointInfo = new ArrayList<PointInfo>();
		List<ExamineeClass> initClasses = null;
		initClasses = examineeClassBiz.findAllClass();

		// 通过pid查出此套试卷的相关信息
		if (pid != null && pid != "") {
			lookanswer = pointAnswerBiz.findPaperByPidAndSname(pid, name, classid);// 查出对应考生的答卷信息
			lookpaper = pointPaperBiz.findPointPaperById(Integer.parseInt(pid));// 查出对应的考卷
			if (lookanswer != null && lookpaper != null) {
				// 获取考生答案
				String sanswer = lookanswer.getAnswer();
				sanswer = sanswer.substring(0, sanswer.lastIndexOf(","));
				String[] answer = sanswer.split(",");

				String ptitle = lookpaper.getPtitle(); // 知识点字符串
				ptitle = ptitle.substring(0, ptitle.lastIndexOf(","));
				String[] content = ptitle.split(",");// 知识点编号数组
				lookPointInfo = pointInfoBiz.findPointInfo(ptitle);// 查出对应的知识点

				if (answer != null && answer.length > 0 && content != null && content.length > 0
						&& lookPointInfo != null && lookPointInfo.size() > 0) { // 说明有答案
					sname = subjectBiz.findSubjectName(lookpaper.getSubject().getId());// 查出课程名称

					// 查询班级名
					lookpaper.setRemark(examineeClassBiz.getClassNameById(lookpaper.getExamineeClass().getId()));

					if (initClasses != null && initClasses.size() > 0) { // 将课程编号转为课程名
						for (ExamineeClass ec : initClasses) {
							if (lookpaper.getExamineeClass().getId() == ec.getId()) {
								lookpaper.setRemark(ec.getClassName());
							}
						}
					}

					// 从试卷中取出知识点编号并将试题中的知识点编号改为该学生所评定的分数，以便在界面现实
					for (String ct : content) {
						for (String as : answer) {
							String psid = as.substring(0, as.indexOf("-")); // 知识点编号
							String psw = as.substring(as.indexOf("-") + 1); // 评分
							if (psid.trim().equals(ct.trim())) {
								for (PointInfo pi : lookPointInfo) {
									if (psid.trim().equals(pi.getPid() + "")) {
										pi.setFlag(psw);
										break;
									}
								}
								break;
							}
						}
					}
				}
			}
			lookpaper.setSubject(null);
			lookpaper.setExamineeClass(null);
			lookpaper.setPointAnswers(null);
			lookanswer.setPointPaper(null);

			map.put("subName", sname); // 自评课程名
			map.put("lookpaper", lookpaper); // 试卷信息
			map.put("lookanswer", lookanswer); // 试卷信息
			map.put("lookPointInfo", lookPointInfo); // 知识点信息
			list.add(map);
			String json = this.writeJson(0, list);
			out.print(json);
			out.flush();
			out.close();
		}
	}

	public void setWritingPaperBiz(WritingPaperBiz writingPaperBiz) {
		this.writingPaperBiz = writingPaperBiz;
	}

	public void setLabPaperBiz(LabPaperBiz labPaperBiz) {
		this.labPaperBiz = labPaperBiz;
	}

	public void setWritingAnswerBiz(WritingAnswerBiz writingAnswerBiz) {
		this.writingAnswerBiz = writingAnswerBiz;
	}

	public void setLabAnswerBiz(LabAnswerBiz labAnswerBiz) {
		this.labAnswerBiz = labAnswerBiz;
	}

	public void setaDailyTalkBiz(ADailyTalkBiz aDailyTalkBiz) {
		this.aDailyTalkBiz = aDailyTalkBiz;
	}

	public void setPointPaperBiz(PointPaperBiz pointPaperBiz) {
		this.pointPaperBiz = pointPaperBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}

	public void setPointInfoBiz(PointInfoBiz pointInfoBiz) {
		this.pointInfoBiz = pointInfoBiz;
	}

	public void setPointAnswerBiz(PointAnswerBiz pointAnswerBiz) {
		this.pointAnswerBiz = pointAnswerBiz;
	}

}
