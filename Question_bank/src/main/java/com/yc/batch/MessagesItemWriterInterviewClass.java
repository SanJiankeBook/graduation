package com.yc.batch;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.yc.biz.ExaminInterviewRecordBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.WorkBiz;
import com.yc.po.ExamineeClass;
import com.yc.utils.CsvUtils;
import com.yc.vo.TeacherWorkdetailInterview;
import com.yc.vo.Workdetail;

import net.sf.ehcache.util.PropertyUtil;



//写 访谈记录班级统计
@Component("messagesItemWriterInterview")
public class MessagesItemWriterInterviewClass implements ItemWriter<TeacherWorkdetailInterview>{
	
	@Resource(name = "examinInterviewRecordBiz")
	private ExaminInterviewRecordBiz examinInterviewRecordBiz ;

	
	public void setExaminInterviewRecordBiz(ExaminInterviewRecordBiz examinInterviewRecordBiz) {
		this.examinInterviewRecordBiz = examinInterviewRecordBiz;
	}
	public void write(List<? extends TeacherWorkdetailInterview> messages) throws Exception {
		//this.workBiz.updateWorkCheckcount();
		Properties props = new Properties();
		InputStream in= PropertyUtil.class.getClassLoader().getResourceAsStream("connectionConfig.properties");
		props.load(in);
		String startime=props.getProperty("detail_startime");
		String endtime=props.getProperty("detail_endtime");
		String time=props.getProperty("detail_time");
		 List<Object> works=new ArrayList<Object>();
		 
		for(TeacherWorkdetailInterview work:messages){
			String className=work.getClassName();
			List<ExamineeClass> list= this.examinInterviewRecordBiz.findinfo(className);
			Workdetail workdetail=new Workdetail();
			workdetail.setExamineeclassid(list.get(0).getId());
			workdetail.setClassName(className);
			workdetail.setClasscount(list.get(0).getStudentcount());
			workdetail.setCheckcount(work.getTotal());
			DecimalFormat    df   = new DecimalFormat("######0.00");   
			Double p=(double)((float)workdetail.getCheckcount()/(float)(workdetail.getClasscount()))*100;
			String completionrate=df.format(p);
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
	        cu.writeCsv(path+"/csv/classInterview_"+time+".csv",works );
    }
}