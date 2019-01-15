package com.yc.batch;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.WorkBiz;
import com.yc.utils.CsvUtils;
import com.yc.vo.Workdetail;
import com.yc.webexam.actions.ChapterAction;

import net.sf.ehcache.util.PropertyUtil;



//写
@Component("messagesItemWriter")
public class MessagesItemWriter implements ItemWriter<Workdetail>{
	private static final Logger logger = Logger.getLogger(MessagesItemWriter.class);
	
	@Resource(name = "workBiz")
	private WorkBiz workBiz;
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;
	@Resource(name = "examineeBiz")
	private ExamineeBiz examineeBiz;


	public ExamineeBiz getExamineeBiz() {
		return examineeBiz;
	}

	public void setExamineeBiz(ExamineeBiz examineeBiz) {
		this.examineeBiz = examineeBiz;
	}

	public ExamineeClassBiz getExamineeClassBiz() {
		return examineeClassBiz;
	}

	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	public WorkBiz getWorkBiz() {
		return workBiz;
	}

	public void setWorkBiz(WorkBiz workBiz) {
		this.workBiz = workBiz;
	}

	public void write(List<? extends Workdetail> messages) throws Exception {
		//this.workBiz.updateWorkCheckcount();
		Properties props = new Properties();
		InputStream in= PropertyUtil.class.getClassLoader().getResourceAsStream("connectionConfig.properties");
		props.load(in);
		String startime=props.getProperty("detail_startime");
		String endtime=props.getProperty("detail_endtime");
		String time=props.getProperty("detail_time");
		List<Object> works=new ArrayList<Object>();
		for(Workdetail work:messages){
			Workdetail workdetail=new Workdetail();
			Integer classid=work.getExamineeclassid();
			int num=this.workBiz.getClassWorkNum(classid,startime,endtime);
			String classname=this.examineeClassBiz.findExamineeClassById(classid).getClassName();
			int checkcount=this.workBiz.getWorkCheckcount(classid, startime, endtime);
			workdetail.setExamineeclassid(classid);
			workdetail.setWorkcount(num);
			int studentcount=this.examineeBiz.getExamineeCount(classid);
			workdetail.setClasscount(studentcount);
			DecimalFormat    df   = new DecimalFormat("######0.00");   
			Double p=(double)((float)checkcount/(float)(num*studentcount))*100;
			String completionrate=df.format(p);
			workdetail.setClassName(classname);
			workdetail.setCheckcount(checkcount);
			workdetail.setCompletionrate(completionrate);
			works.add(workdetail);
		}
		CsvUtils cu=new CsvUtils();
		String path=this.getClass().getResource("/").getPath();
		//String path=Class.class.getClass().getResource("/").getPath();
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		path=path.substring(0,path.lastIndexOf("/"));
		cu.writeCsv(path+"/csv/class_"+time+".csv",works );
	}
}