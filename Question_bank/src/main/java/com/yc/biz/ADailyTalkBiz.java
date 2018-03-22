package com.yc.biz;

import java.io.Serializable;
import java.util.List;

import com.yc.po.ADailyTalk;

public interface ADailyTalkBiz {

	/**
	 * 根据班级查询新知识点讲解安排
	 * @param cid：要查询的班级编号
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkByCid(int cid, int status);

	/**
	 * 根据班级查询新知识点讲解安排
	 * 
	 * @param cid：要查询的班级编号
	 * @return
	 */
	public List<ADailyTalk> findHistoryADailyTalkByCid(int cid, int status, Integer offset, Integer length);

	/**
	 * 根据班级查询新知识点讲解历史记录学生端
	 * 
	 * @param cid：要查询的班级编号
	 * @param uname：学生姓名
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkByCidHistoryStudent(int cid, String uname);

	/**
	 * 根据班级查询新知识点讲解历史记录教师端
	 * 
	 * @param cid：要查询的班级编号
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkByCidHistoryTeacher(int cid);

	/**
	 * 分页查询
	 * 
	 * @param pageNo：查找的页面
	 * @param pageSize：每页显示的信息条数
	 * @param cid：要查询的班级编号
	 * @param uname：学生姓名
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkBypages(int cid, String uname, int pageNo, int pageSize);

	/**
	 * 教师端多条件查询
	 * 
	 * @param cid：查询班级
	 * @param uname：查询的学生姓名
	 * @param startDate：查询开始时间
	 * @param endDate：查询结束时间
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkByAllToTeacher(int cid, String uname, String startDate, String endDate);

	/**
	 * 根据条件查询
	 * 
	 * @param cid：查询班级
	 * @param uname：查询的学生姓名
	 * @param startDate：查询开始时间
	 * @param endDate：查询结束时间
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkByPageAll(int cid, String uname, String startDate, String endDate);

	/**
	 * 查询自己的
	 * 
	 * @param cid
	 * @param uname
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkByMePageAll(int cid, String uname, String startDate, String endDate);

	/**
	 * 根据班级查询已讲学员姓名（学生端）
	 * 
	 * @param cid:要查询的班级
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkStudentName(int cid);

	/**
	 * 根据班级查询已讲学员姓名（教师端）
	 * 
	 * @param cid:要查询的班级
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkStudentNameToTeacher(int cid);

	/**
	 * 添加新知识讲解安排
	 * 
	 * @param adt：要添加的新知识点对象
	 * @return：添加成功返回一个大于0的数
	 */
	public Serializable addADailyTalk(ADailyTalk adt);

	/**
	 * 根据编号删除新技术点讲解安排
	 * 
	 * @param id
	 * @return
	 */
	public int delADailyTalk(int id);

	/**
	 * 修改新知识点状态
	 * 
	 * @param id：要修改的新知识点编号
	 * @param status：要修改的状态
	 * @return
	 */
	public int updateADailyTalkStatus(int id, String assessment, int status);

	/**
	 * 根据编号查询内容
	 * 
	 * @param id
	 * @return
	 */
	public ADailyTalk findADailyTalkDetail(int id);

	/**
	 * 根据编号查询内容
	 * 
	 * @param id
	 * @return
	 */
	public ADailyTalk findADailyTalkDetailInfo(int id);

	/**
	 * 添加教员评价
	 * 
	 * @param assessInfo：教员评价内容
	 * @param id：要修改的知识点编号
	 * @return
	 */
	public int addADailyTalkAssess(String assessInfo, int id);

	public int getCid(String examName);

	/**
	 * 上传讲解的资料
	 * 
	 * @param f：上传的文件
	 * @param id：对应的知识点编号
	 * @param id：文件名
	 * @return
	 */
	public int updateUploadKnowledgeInfos(byte[] bt, int id, String fileName);

	/**
	 * 上传讲解的资料
	 * 
	 * @param f：上传的文件
	 * @param id：对应的知识点编号
	 * @return
	 */
	public List<ADailyTalk> downloadKnowledgeInfos(int id);

	public List<ADailyTalk> findSize(int cid);

	/**
	 * 根据时间和名字查询每日一讲
	 * 
	 * @return
	 */
	public List<ADailyTalk> findADailyTalkBytime(int cid, String uname, String startDate, String endDate,
			String stuname, int pageNo, int pageSize);

	public List<ADailyTalk> findADailyTalkSize(int cid, String uname, String startDate, String endDate, String stuname);
	
	/**
	 * 通过班级名来获取讲过每日一讲的名单
	 * @param className
	 * @return
	 */
	public List<ADailyTalk> findClassName(String className);
}
