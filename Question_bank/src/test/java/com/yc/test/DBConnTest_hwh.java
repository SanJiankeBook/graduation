package com.yc.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yc.biz.ADailyTalkBiz;
import com.yc.biz.CheckingBiz;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.SystemUserBiz;
import com.yc.biz.TempBiz;
import com.yc.po.ADailyTalk;
import com.yc.po.Checking;
import com.yc.po.Examinee;
import com.yc.po.ExamineeClass;
import com.yc.po.SystemUser;
import com.yc.po.Temp;
import com.yc.utils.Encrypt;
import com.yc.utils.YcConstants;
import com.yc.vo.ListClassPage;
import com.yc.vo.Page;
/**
 * 测试数据库连接
 * @author jp
 * @param <T>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-actions.xml" })  
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)  
public class DBConnTest_hwh {


	private ExamineeClassBiz examineeClassBiz;
	private ExamineeBiz examineeBiz;
	private CheckingBiz checkingBiz;
	private TempBiz tempBiz;
	private ADailyTalkBiz aDailyTalkBiz;
	private SystemUserBiz systemUserBiz;
	@Autowired
	public void setTempBiz(TempBiz tempBiz) {
		this.tempBiz = tempBiz;
	}

	@Autowired
	public void setaDailyTalkBiz(ADailyTalkBiz aDailyTalkBiz) {
		this.aDailyTalkBiz = aDailyTalkBiz;
	}

	@Autowired 
	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	@Autowired 
	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}

	@Autowired 
	public void setCheckingBiz(CheckingBiz checkingBiz) {
		this.checkingBiz = checkingBiz;
	}

	@Autowired 
	public void setSystemUserBiz(SystemUserBiz systemUserBiz) {
		this.systemUserBiz = systemUserBiz;
	}


	@Test //测试hibernate框架   测试班级查询
	public void testClass() {
		List<ExamineeClass> examineeClass = examineeClassBiz.findExamineeClassBySemester("s3");
		assert examineeClass.size()>0:"程序出错";
		for(ExamineeClass e:examineeClass){
			System.out.println(e.getClassName());
		}

	}
	
	@Test //测试班级用班级id查询所有学生
	public void testFindExameeByclassId(){
		List<Examinee> Examinees=this.examineeBiz.findAllStudent(28);
	}
	
	
	@Test //测试hibernate框架   测试班级remester修改
	public void testUpdateClass() {
		String id = "53";
		String semester ="S2";
		String className = "YC_27";
		String createDate ="2017-07-11";
		String overDate = "2018-07-05";
		String remark = "test";
		ExamineeClass examineeClass = examineeClassBiz.findExamineeClassById(Integer.parseInt(id));
		examineeClass.setClassName(className);
		examineeClass.setCreateDate(createDate);
		examineeClass.setOverDate(overDate);
		examineeClass.setRemark(remark);
		examineeClass.setSemester(semester);
		examineeClassBiz.updateExamineeClass(examineeClass);

	}

	@Test //测试hibernate框架   测试查询是否存在考勤记录
	public void testchecking() {
		Date date=new Date(116, 6, 13);
		Checking  c=checkingBiz.findCheckingByClassIdAndDateTime(28, date, "下午");
		System.out.println(c.getCheckTime());
		assert c!=null:"程序出错";

	}

	@Test //测试hibernate框架   测试查询用户  分组
	public void testFindSystemUser() throws Exception {
		SystemUser su = new SystemUser();
		su.setRemark("管理员");
		Page<SystemUser> page = new Page<SystemUser>();
		page.setPageSize(10);
		page.setCurrentPage(1);
		systemUserBiz.searchSystemUserPageByRemark(page, su);
		List<SystemUser>  l=page.getResult();

		assert l.size()>0:"程序出错";
		for(SystemUser s:l){
			System.out.println(s.getUserName());
		}
	}

	@Test //测试hibernate框架   测试插入考勤保存记录
	public void testchecking1() {
		Date date=new Date(117, 6, 12);
		int uid=1;
		Checking checking = new Checking();
		String str="汤格,1- |曹慧,1- |郑笑,1- |李先来,1- |欧阳孝俊,1- |谭琳琪,1- |卓怀渊,1- "
				+ "|于再选,1- |杨旺,1- |林哲达,1- |大彭涛,1- |黄迪豪,1- |胡遵盟,1- |周品东,1- "
				+ "|许佳能,1- |徐有全,1- |罗玉华,1- |陈铭,1- |胡伟豪,1- |赵亮,1- |肖宇军,1- |小彭涛,1- "
				+ "|金振威,1- |杨卓,1- |袁湘云,1- |李光耀,1- |丁翰,1- |唐宇,1- |张磊,1- |周洁颖,1- |黄鑫,1- "
				+ "|";
		checking.setSemester("S3");
		checking.setCheckDate(date);
		checking.setCheckTime("下午");
		checking.setCheckRemark("无");
		checking.setCheckResult(str);
		checking.setCheckFlag(null);
		checking.setCheckStatus(1);
		checking.setCheckFlag(null);
		checking.setCheckDescript(null);
		SystemUser systemUser = new SystemUser();
		systemUser.setId(uid);
		checking.setSystemUser(systemUser);
		ExamineeClass examineeClass = new ExamineeClass();
		examineeClass.setId(28);
		checking.setExamineeClass(examineeClass);
		int result = checkingBiz.addCheckingInfo(checking);

		assert result>0:"程序出错";
		//System.out.println("result========>"+result);
	}

	@Test 
	public void getSys(){
		System.out.println(System.getProperties());;
	}

	@Test //测试hibernate框架   测试查询考勤记录后点击详细信息查询
	public void testchecking3() {
		int cid=1074;
		List<Checking> ch=checkingBiz.findCheckByCheckid(cid);

		assert ch.size()>0:"程序出错";
		for(Checking c:ch){
			System.out.println(c.getCheckDate());
			System.out.println(c.getCheckTime());
			System.out.println(c.getCheckId());
		}

	}


	@Test //测试hibernate框架   测试查询考勤记录
	public void testchecking4() {
		Date date=new Date(116, 6, 13);
		System.out.println(date);
		Checking  c=checkingBiz.findCheckingByClassIdAndDateTime(28, date, "下午");
		assert c!=null:"程序出错";

	}

	@Test //测试hibernate框架   测试每日一讲  历史记录
	public void testadailytalk() {
		int status =1;
		String className ="YC_28";
		String startdate = "1911-1-1"; // 查询开始时间
		String enddate = "2050-12-30"; // 查询结束时间
		List<ADailyTalk> aDailyTalk = null;
		int cid = aDailyTalkBiz.getCid(className);
		aDailyTalk = aDailyTalkBiz.findHistoryADailyTalkByCid(cid, status, null, null);
		assert aDailyTalk.size()>0:"程序出错";

		for(ADailyTalk adt:aDailyTalk){
			System.out.println(adt.getKnowledge());
		}
	}

	@Test //测试hibernate框架   测试每日一讲  教师点评
	public void testadailytalk3() {
		int id = 104;
		String assessment = "sssssss";
		int result = aDailyTalkBiz.updateADailyTalkStatus(id, assessment, 2);

		assert result>0:"程序出错";
		System.out.println(result);
	}


	@Test //测试hibernate框架   测试添加班级
	public void testaddclass() {
		ExamineeClass examineeClass = new ExamineeClass();
		examineeClass.setSemester("S1");
		examineeClass.setClassName("YC_57");
		examineeClass.setCreateDate("2017-07-11");
		examineeClass.setOverDate("2018-07-05");
		examineeClass.setRemark("test");

		examineeClassBiz.addExamineeClass(examineeClass);
	}


	@Test //测试hibernate框架   测试查看班级是否存在
	public void testfindclass() {
		String className = "YC_27";
		Integer id = examineeClassBiz.getClassIdOfname(className);

		assert id>0:"程序出错";

		System.out.println(id);
	}

	@Test //测试hibernate框架   测试查看班级列表
	public void testfindListClass() {
		String semester = "S3";
		List<ExamineeClass> examineeClass = examineeClassBiz.findExamineeClassBySemester(semester);

		assert examineeClass.size()>0:"程序出错";
		System.out.println(examineeClass.size());
	}

	@Test //测试hibernate框架   测试查看班级班级列表
	public void testShowClassStudent() {
		String className = "YC_27";
		List<Examinee> examinee = examineeBiz.findAllStuNameByClassName(className);

		assert examinee.size()>0:"程序出错";
		System.out.println(examinee.size());
	}


	/*@Test //测试hibernate框架   测试添加考生
	public void testaddStudent() {
		String pwd = Encrypt.md5AndSha("a");
		String exaimineeNames = "聂1诚"; //不能重名
		String idCard = "1111111111";
		String tel = "12345678901";
		int classId = 28;
		int count = examineeBiz.addExaminee(exaimineeNames, pwd, classId, idCard, tel);

		//assert count>0:"程序出错";
		System.out.println(count);
	}*/



	@Test //测试hibernate框架   测试修改考生密码
	public void testPasswordStudent() {
		String className = "YC_33";
		String newpwd = Encrypt.md5AndSha(  "aaaa" );
		String examineeName = "杨鸿宇";
		Examinee examinee = examineeBiz.getExaminee(examineeName, null, className);
		examinee.setPassword(newpwd);
		int result = examineeBiz.updateExaminee(examinee);

		assert result>0:"程序出错";
		System.out.println(result);
	}

	@Test //测试hibernate框架   测试修改考生密码_全班
	public void testPasswordStudent_Class() {
		String className = "YC_27";
		String pwd = Encrypt.md5AndSha(  "qdSscsz" );
		List<Examinee> examinees = examineeBiz.getAllExaminee(className);

		assert examinees.size()>0:"程序出错";
		for (Examinee examinee : examinees) {
			examinee.setPassword(pwd);
			int result = examineeBiz.updateExaminee(examinee);
		}

	}

	@Test //测试hibernate框架   测试修改用户信息
	public void testUser() {
		String newPassword = Encrypt.md5AndSha("a");
		SystemUser su = new SystemUser();
		su.setId(1);
		su.setPassword(newPassword);
		su.setAuthorities("1");
		su.setRemark("管理员");
		systemUserBiz.updateSystemUser(su);
	}


	@Test //测试hibernate框架   测试修改学生信息
	public void testUpdateClassStudent() {
		String name = "陈铭";
		String className = "YC_27";
		String oldname = "陈铭";
		String tel = "1212112135";
		String idCard = null;
		Examinee examinee = examineeBiz.getExaminee(oldname, null, className);
		examinee.setName(name);
		examinee.setIdCard(idCard);
		examinee.setTel(tel);
		int result = examineeBiz.updateExaminee(examinee);
		assert result>0:"程序出错";
		System.out.println(result);
	}

	@Test //测试hibernate框架   测试每日一讲  未开讲
	public void testadailytalk1() {
		int status =1;
		String className ="YC_27";
		int cid = aDailyTalkBiz.getCid(className);
		List<ADailyTalk> aDailyTalk = aDailyTalkBiz.findADailyTalkByCid(cid, status);

		assert aDailyTalk!=null:"程序出错";
		System.out.println(aDailyTalk.size());
		for(ADailyTalk adt:aDailyTalk){
			System.out.println(adt.getKnowledge());
		}
	}


	@Test
	public void testMap(){
		HashMap<String, String> hm=new HashMap<String,String>();  
		hm.put("zs", "beijing");  
		hm.put("ls", "nanjing");  
		hm.put("was", "beijing");  
		hm.put("zdds", "shenzhen");  
		hm.put("ls", "tieling"); // 键相同时，存入后存的值  
		System.out.println("==============================");
		System.out.println("==============================");
		System.out.println(hm.get(1));
	}

	@Test //测试hibernate框架   测试每日一讲  插入数据
	public void testadailytalk2() throws ParseException {
		String name = "聂诚";
		String className ="YC_33";
		String knowledge = "测试插入";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String time = df.format(new java.util.Date());// new Date()为获取当前系统时间
		int cid = aDailyTalkBiz.getCid(className);
		ADailyTalk adilyTalk = new ADailyTalk();
		adilyTalk.setName(name);
		adilyTalk.setKnowledge(knowledge);
		adilyTalk.setStatus(1);
		adilyTalk.setSpeakdate(df.parse(time));
		adilyTalk.setAssessment( YcConstants.ADILYTALK_ACCESSMENT_START);
		ExamineeClass examineeClass = new ExamineeClass();
		examineeClass.setId(cid);
		adilyTalk.setExamineeClass(examineeClass);
		int result = (Integer) aDailyTalkBiz.addADailyTalk(adilyTalk);

		assert result>0:"程序出错";
		System.out.println("result====>"+result);




	}


	@Test //测试优化sql语句
	public void testSql(){

		String cid = "34"; // 班级编号
		String startdate = "1911-1-1"; // 查询开始时间
		String enddate = "2050-12-30"; // 查询结束时间
		String datetime = ""; // 查询时间段
		List<Checking> rightList=checkingBiz.findCheckByClassId(Integer.parseInt(cid), startdate, enddate,datetime);

		assert rightList.size()>0:"程序出错";
	}


	@Test //测试hibernate框架   测试查询考勤记录--班级记录
	public void testchecking6() {
		String semester = "S2"; // 学期编号
		String cid = "34"; // 班级编号
		String startdate = "1911-1-1"; // 查询开始时间
		String enddate = "2050-12-30"; // 查询结束时间
		String datetime = ""; // 查询时间段
		List<String> listName=examineeBiz.findAllStuNameByClassId(Integer.parseInt(cid));//获取 班级学生数据
		List<Integer> checkides = null;
		List<Temp> listTemp = new ArrayList<Temp>();
		//queqName 缺勤  subname 迟到  jianame 请假  ppid 考勤总数   kuangkCount 旷课人数  lateCount 迟到人数    qingjCount 请假人数 className
		String sname="";
		String queqName="";//缺勤人名
		String subname="";String jianame="";Integer count=0;
		Integer kuangkCount=0;Integer lateCount=0;Integer qingjCount=0;
		Integer status=0;
		Integer normalCount=0;
		int result = tempBiz.exectueDelTemp(); // 清空临时表中的记录
		List<Checking> rightList=checkingBiz.findCheckByClassId(Integer.parseInt(cid), startdate, enddate,datetime);
		if (result >= 0) {
			for (Checking list : rightList) {
				Date checkDate=(Date) list.getCheckDate();
				sname="";
				queqName="";//缺勤人名
				subname=""; jianame=""; count=0;
				kuangkCount=0; lateCount=0; qingjCount=0;
				normalCount=0;
				String checkTime = list.getCheckTime();
				String checkResult = list.getCheckResult();

				int flags = 1;
				while (flags > 0) {
					int tempNum=0;//排除退学的学生
					sname = checkResult.substring(0, checkResult.indexOf(",", 0));
					for(int i=0;i<listName.size();i++){
						if(sname.equals(listName.get(i))){
							tempNum=1;
							break;
						}
					}

					if(tempNum!=1){
						if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
								.indexOf("|", 0) > 1) {
							checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
									checkResult.length());
						} else {
							flags = 0;
						}
					}

					status = Integer.parseInt(checkResult.substring(checkResult.indexOf(",", 0) + 1,
							checkResult.indexOf("-", 0)));
					// 1 已到   2 迟到 3 病假 4 请假 5 旷课 6 早退 
					if(status==1){
						normalCount++;
					}

					if(status==2){
						subname+=" "+sname;
						lateCount++;
					}

					if(status==4){
						jianame+=" "+sname;
						qingjCount++;
					}

					if(status==5){
						queqName+=" "+sname;
						kuangkCount++;
					}
					count++;

					if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
							.indexOf("|", 0) > 1) {
						checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
								checkResult.length());
					} else {
						flags = 0;
					}
				}

				Temp t = new Temp();
				t.setPpid(count);
				t.setSname(queqName);
				t.setClassid(qingjCount);
				t.setClassName(checkDate.toString()+" "+checkTime);
				t.setSubid(lateCount);
				t.setSubname(subname);
				t.setPointid(kuangkCount);
				t.setPcontent(jianame);
				t.setGrade(Float.parseFloat(normalCount + ""));
				listTemp.add(t);

			}
		}
		assert listTemp.size()>0:"程序出错";
		System.out.println(listTemp.size());
	}


	@Test //测试hibernate框架   测试查询考勤记录--详情
	public void testchecking5() {
		Date date=new Date(116, 6, 13);
		System.out.println(date);
		String semester = "S3"; // 学期编号
		String cid = "28"; // 班级编号
		String stuname = "曹慧"; // 学生姓名
		String startdate = "1911-1-1"; // 查询开始时间
		String enddate = "2050-12-30"; // 查询结束时间
		String datetime = ""; // 查询时间段
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);


		List<Integer> checkides = null;
		List<Temp> listTemp = new ArrayList<Temp>();
		checkides = checkingBiz.findCheckingAllCheckIdExcuteSql(semester, Integer.parseInt(cid), stuname, startdate, enddate,
				datetime);
		System.out.println("checkid  length ====================>"+checkides.size());
		if (checkides != null && checkides.size() > 0) {
			List<Checking> lists = checkingBiz.findCheckInCheckid(checkides);
			int result = tempBiz.exectueDelTemp(); // 清空临时表中的记录
			if (result >= 0) {
				for (Checking list : lists) {
					int ctime = 0; // 考勤时间段 1.上午 2.下午 3.晚上
					String sname = ""; // 学生姓名
					int status = 0; // 考勤状态
					String remark = ""; // 备注
					//List<Checking> list = checkingBiz.findCheckByCheckid(str);

					Date checkDate = (Date) list.getCheckDate();
					String checkTime = list.getCheckTime();
					String checkResult = list.getCheckResult();
					Integer ccid = list.getExamineeClass().getId();
					// list.get(0).getExamineeClass().getClassName();
					String className = list.getExamineeClass().getClassName();
					int flags = 1;
					while (flags > 0) {
						if ("上午".equals(checkTime.trim())) {
							ctime = 1;
						} else if ("下午".equals(checkTime.trim())) {
							ctime = 2;
						} else {
							ctime = 3;
						}
						sname = checkResult.substring(0, checkResult.indexOf(",", 0));
						if (!sname.equals(stuname)) {
							if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
									.indexOf("|", 0) > 1) {
								checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
										checkResult.length());
							} else {
								flags = 0;
							}
							continue;
						}
						status = Integer.parseInt(checkResult.substring(checkResult.indexOf(",", 0) + 1,
								checkResult.indexOf("-", 0)));
						remark = checkResult.substring(checkResult.indexOf("-", 0) + 1,
								checkResult.indexOf("|", 0));
						Temp t = new Temp();
						t.setPpid(list.getCheckId());
						t.setSname(sname);
						t.setClassid(ccid);
						t.setClassName(className);
						t.setSubid(ctime);
						t.setSubname(remark);
						t.setPointid(status);
						t.setPcontent(sdf.format(checkDate));
						t.setGrade(Float.parseFloat(0 + ""));

						listTemp.add(t);
						if (checkResult.substring(checkResult.indexOf("|", 0) + 1, checkResult.length())
								.indexOf("|", 0) > 1) {
							checkResult = checkResult.substring(checkResult.indexOf("|", 0) + 1,
									checkResult.length());
						} else {
							flags = 0;
						}
					}

				}
			}
		}
		assert listTemp.size()>0:"程序出错";
		System.out.println("temp length ====="+listTemp.size());

	}

	@Test //测试hibernate框架   测试修改考勤记录
	public void testchecking_Update() {
		Date date=new Date(116, 6, 13);

		Checking checking = checkingBiz.findCheckingInfoById(15);
		String str="汤格,1- |曹慧,1- |郑笑,3- |李先来,1- "
				+ "|欧阳孝俊,1- |谭琳琪,1- |卓怀渊,1- |于再选,1- "
				+ "|杨旺,1- |林哲达,1- |大彭涛,1- |黄迪豪,1- |胡遵盟,1- "
				+ "|周品东,1- |许佳能,1- |徐有全,2- |罗玉华,1- |陈铭,1- |胡伟豪,1- "
				+ "|赵亮,1- |肖宇军,1- |小彭涛,1- |金振威,1- |杨卓,1- |袁湘云,5- |李光耀,1- "
				+ "|丁翰,1- |唐宇,1- |张磊,1- |周洁颖,1- |黄鑫,1- |";
		checking.setCheckId(15);
		checking.setCheckDate(date);
		checking.setCheckTime("上午");
		checking.setCheckRemark("");
		checking.setCheckResult(str);
		int result = checkingBiz.updateCheckingInfo(checking);

		assert result>0:"程序出错";
		System.out.println(result);
	}

	@Test //测试hibernate框架   测试学生查询
	public void testshudent() {
		List<Examinee> examinee = examineeBiz.findAllStuNameByClassName("YC_33");
		List<ListClassPage> list = new ArrayList<ListClassPage>();
		if (examinee != null && examinee.size() > 0) {
			for (int i = 0; i < examinee.size(); i++) {
				ListClassPage listClassPage = new ListClassPage();
				listClassPage.setName(examinee.get(i).getName());
				listClassPage.setClassName(examinee.get(i).getExamineeClass().getEditionId().toString());
				//传回去的实际是学生状态  （修改结果）
				listClassPage.setIdCard(examinee.get(i).getStudentStatus().toString());
				listClassPage.setId(examinee.get(i).getId());
				listClassPage.setTel(examinee.get(i).getTel());
				list.add(i, listClassPage);
			}
		}
		assert list.size()>0:"程序出错";
		for(ListClassPage l:  list){
			System.out.println(l.getName());

		}
	}

}
