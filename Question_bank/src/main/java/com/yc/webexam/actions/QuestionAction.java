package com.yc.webexam.actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ModelDriven;
import com.yc.biz.ConvertQuestionEditionBiz;
import com.yc.biz.EditionBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.WritingQuestionBiz;
import com.yc.po.ConvertQuestionEdition;
import com.yc.po.WritingQuestion;
import com.yc.utils.JsonProtocol;
import com.yc.utils.JsonUtil;
import com.yc.utils.ValueUtil;
import com.yc.vo.QuestionModel;

@SuppressWarnings("serial")
@Scope("prototype")
public class QuestionAction extends BaseAction implements ModelDriven<QuestionModel> {

	@Resource(name = "writingQuestionBiz")
	private WritingQuestionBiz writingQuestionBiz;
	@Resource(name = "editionBiz")
	private EditionBiz editionBiz;
	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;
	@Resource(name = "convertQuestionEditionBiz")
	private ConvertQuestionEditionBiz convertQuestionEditionBiz;

	private QuestionModel questionModel = new QuestionModel();
	private HttpServletResponse response = ServletActionContext.getResponse();
	private static final Logger logger = Logger.getLogger(QuestionAction.class);
	private String question;// 试题内容
	private File[] filePath;// 上传的文件
	private String[] filePathFileName;// 上传文件的原文件名
	private String[] filePathContentType;// 上传文件的文件类型
	private String savePath;// 用于指定服务器的保存位置

	private String optionA;// 选项A的内容
	private String optionB;// 选项B的内容
	private String optionC;// 选项C的内容
	private String optionD;// 选项D的内容
	private Integer editionId;// 版本号
	private String semester;// 学期
	private Integer subjectId;// 课程编号
	private Integer chapterId;// 章节编号
	private String questionType;// 试题类型
	private String remark;// 标记 提示
	private String answer;// 答案
	private Integer difficulty;// 难度
	private Integer id;
	private String personid;
	private Integer oldEditionId;
	
	public Integer getOldEditionId() {
		return oldEditionId;
	}

	public void setOldEditionId(Integer oldEditionId) {
		this.oldEditionId = oldEditionId;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public Integer getEditionId() {
		return editionId;
	}

	public void setEditionId(Integer editionId) {
		this.editionId = editionId;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public EditionBiz getEditionBiz() {
		return editionBiz;
	}

	public File[] getFilePath() {
		return filePath;
	}

	public void setFilePath(File[] filePath) {
		this.filePath = filePath;
	}

	public String[] getFilePathFileName() {
		return filePathFileName;
	}

	public void setFilePathFileName(String[] filePathFileName) {
		this.filePathFileName = filePathFileName;
	}

	public String[] getFilePathContentType() {
		return filePathContentType;
	}

	public void setFilePathContentType(String[] filePathContentType) {
		this.filePathContentType = filePathContentType;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	@Override
	public QuestionModel getModel() {
		return this.questionModel;
	}

	public void setWritingQuestionBiz(WritingQuestionBiz writingQuestionBiz) {
		this.writingQuestionBiz = writingQuestionBiz;
	}

	public void setEditionBiz(EditionBiz editionBiz) {
		this.editionBiz = editionBiz;
	}

	public void setSubjectBiz(SubjectBiz subjectBiz) {
		this.subjectBiz = subjectBiz;
	}
	
	

	public ConvertQuestionEditionBiz getConvertQuestionEditionBiz() {
		return convertQuestionEditionBiz;
	}

	public void setConvertQuestionEditionBiz(ConvertQuestionEditionBiz convertQuestionEditionBiz) {
		this.convertQuestionEditionBiz = convertQuestionEditionBiz;
	}

	// 查找是否有相同的题目
	public void findQuestbyText() {
		String jsonStr = "";
		try {
			List<WritingQuestion> list = writingQuestionBiz.searchQuesExist(question);
			if (list.size() <= 5) {
				jsonStr = super.writeJson(0, list);
			} else {
				jsonStr = super.writeJson(0, list.size());
			}
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查找失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}

	// 显示相同的题目
	public void showQuestbyText() {
		String jsonStr = "";
		try {
			List<Map<String, String>> Object = new ArrayList<Map<String, String>>();
			List<WritingQuestion> list = writingQuestionBiz.searchQuesExist(question);
			for (int i = 0,len=list.size(); i < len; i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", list.get(i).getId() + "");
				map.put("question", list.get(i).getQuestion());
				map.put("edition", editionBiz.searchEditionName(list.get(i).getEditionId()));
				map.put("subjectName", subjectBiz.findSubjectName(list.get(i).getSubjectId()));
				Object.add(map);
			}
			JsonProtocol jsonProtocol = null;
			jsonProtocol = new JsonProtocol(0, null, Object);
			jsonStr = JSON.toJSONString(jsonProtocol, SerializerFeature.DisableCircularReferenceDetect);
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查找失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	
	// 上传图片//添加
		public void upload() throws IOException {
			PrintWriter out = response.getWriter();
			try {
				File[] srcFiles = this.getFilePath();// 取出上传的源文件
				String path = "";
				// 循环源文件，写到服务器 savePath路径
				if (srcFiles != null) {
					for (int i = 0,len=srcFiles.length; i < len; i++) {
						int j = this.getFilePathFileName()[i].lastIndexOf(".");
						String ext = this.getFilePathFileName()[i].substring(j);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSS");
						String filename = sdf.format(new Date()) + ext;
						// 取出服务器的保存路径
						String destPath = ServletActionContext.getServletContext().getRealPath(this.getSavePath()) + "\\"
								+ filename;
						// 创建文件对象用于保存
						File destFile = new File(destPath);
						copy(srcFiles[i], destFile);

						path += destPath + ";";
					}
				}
				if ("".equals(path) || path == null) {
					path = "无图片";
				}
				WritingQuestion wq = new WritingQuestion();
				wq.setAnswer(questionModel.getUanswer());
				wq.setChapterId(questionModel.getUchapterId());
				wq.setDifficulty(questionModel.getUdifficulty());
				wq.setEditionId(questionModel.getUeditionId());
				wq.setOptionA(questionModel.getUoptionA());
				wq.setOptionB(questionModel.getUoptionB());
				wq.setOptionC(questionModel.getUoptionC());
				wq.setOptionD(questionModel.getUoptionD());
				wq.setQuestion(questionModel.getUquestion());
				wq.setQuestionType(questionModel.getUquestionType());
				wq.setRemark(questionModel.getUremark());
				wq.setSemester(questionModel.getUsemester());
				wq.setSubjectId(questionModel.getUsubjectId());
				wq.setImage(path);
				writingQuestionBiz.addWritingQuestion(wq);
				out.print("<script>alert('添加成功');</script>");
			} catch (Exception e) {
				out.print("<script>alert('添加失败');</script>");
				logger.error(e);
			} finally {
				out.flush();
				out.close();
			}

		}

	// 更新
	public void updateQuestion() {
		String jsonStr = "";
		try {
			WritingQuestion wq = new WritingQuestion();
			wq = writingQuestionBiz.searchWQuestion(id);
			wq.setAnswer(answer);
			wq.setChapterId(chapterId);
			wq.setDifficulty(difficulty);
			wq.setEditionId(editionId);
			wq.setOptionA(optionA);
			wq.setOptionB(optionB);
			wq.setOptionC(optionC);
			wq.setOptionD(optionD);
			wq.setQuestion(question);
			wq.setQuestionType(questionType);
			wq.setRemark(remark);
			wq.setSemester(semester);
			wq.setSubjectId(subjectId);
			writingQuestionBiz.updateQuestion(wq);
			jsonStr = super.writeJson(0, "修改成功");
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "修改失败！");
			logger.error(e);
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	// 上传图片
	private void copy(File srcFile, File destFile) {
		InputStream iis = null;
		OutputStream oos = null;

		try {
			iis = new BufferedInputStream(new FileInputStream(srcFile));
			oos = new BufferedOutputStream(new FileOutputStream(destFile));

			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = iis.read(buffer, 0, buffer.length)) != -1) {
				oos.write(buffer, 0, length);
			}
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 *@author  pt
	 * @throws IOException 
	 */
	
	// 复制题目
	public void addQuestion() throws IOException {
		String jsonStr=null;
		try {
			File[] srcFiles = this.getFilePath();// 取出上传的源文件
			String path = "";
			// 循环源文件，写到服务器 savePath路径
			if (srcFiles != null) {
				for (int i = 0,len=srcFiles.length; i < len; i++) {
					int j = this.getFilePathFileName()[i].lastIndexOf(".");
					String ext = this.getFilePathFileName()[i].substring(j);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSS");
					String filename = sdf.format(new Date()) + ext;
					// 取出服务器的保存路径
					String destPath = ServletActionContext.getServletContext().getRealPath(this.getSavePath()) + "\\"
							+ filename;
					// 创建文件对象用于保存
					File destFile = new File(destPath);
					copy(srcFiles[i], destFile);

					path += destPath + ";";
				}
			}
			//TODO:空图片的处理有问题 郑笑
			if ("".equals(path) || path == null) {
				path = "无图片";
			}
			//TODO:  beanutils工具
			WritingQuestion wq = new WritingQuestion();
			wq.setAnswer(this.answer);
			wq.setChapterId(this.chapterId);
			wq.setDifficulty(this.difficulty);
			wq.setEditionId(this.editionId);
			wq.setOptionA(this.optionA);
			wq.setOptionB(this.optionB);
			wq.setOptionC(this.optionC);
			wq.setOptionD(this.optionD);
			wq.setQuestion(this.question);
			wq.setQuestionType(this.questionType);
			wq.setRemark(this.remark);
			wq.setSemester(this.semester);
			wq.setSubjectId(this.subjectId);
			wq.setImage(path);
			
			ConvertQuestionEdition cqe=new ConvertQuestionEdition();
			
			cqe.setOldQuestionId(id);
			cqe.setNewEditionId(this.getEditionId());
			cqe.setOldEditionId(this.oldEditionId);
			
		   /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		    String dateNowStr = sdf.format(new Date()); */ 
		    cqe.setTime(ValueUtil.getNowDate());
			writingQuestionBiz.addWritingQuestion(wq);
			if(this.getPersonid()!=null && !"".equals(this.getPersonid()) ){
				cqe.setConvertpersonid(Integer.parseInt(this.getPersonid()));
			}
			
			WritingQuestion	 record=this.writingQuestionBiz.sreachLastRecord();
			cqe.setNewQuestionId(record.getId());
			//插入到题目追溯表中
			this.convertQuestionEditionBiz.insertConvertInfo(cqe);
			jsonStr = super.writeJson(0, "复制成功！");
			
		} catch (Exception e) {
			logger.error(e);
			 jsonStr = super.writeJson(1, "复制失败！");
			
		} finally {
			try {
				JsonUtil.jsonOut(jsonStr);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

}
