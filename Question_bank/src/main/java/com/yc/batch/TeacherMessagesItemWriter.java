package com.yc.batch;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.WorkBiz;
import com.yc.utils.CsvUtils;
import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;

import net.sf.ehcache.util.PropertyUtil;



//写
@Component("teacherMessagesItemWriter")
public class TeacherMessagesItemWriter implements ItemWriter<TeacherWorkdetail>{

	@Resource(name = "workBiz")
	private WorkBiz workBiz;
	@Resource(name = "examineeClassBiz")
	private ExamineeClassBiz examineeClassBiz;



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

	public void write(List<? extends TeacherWorkdetail> messages) throws Exception {
		//this.workBiz.updateWorkCheckcount();
		
		Properties props = new Properties();
		InputStream in= PropertyUtil.class.getClassLoader().getResourceAsStream("connectionConfig.properties");
		props.load(in);
		String startime=props.getProperty("detail_startime");
		String endtime=props.getProperty("detail_endtime");
		String time=props.getProperty("detail_time");
		List<Object> works=new ArrayList<Object>();
		for(TeacherWorkdetail twd:messages){
			String teachername=twd.getCheckcreator();
			int checkcount=this.workBiz.getTeacherchekcount(teachername, startime, endtime);
			TeacherWorkdetail teacherWorkdetail=new TeacherWorkdetail();
			teacherWorkdetail.setCheckcreator(teachername);
			teacherWorkdetail.setCheckcount(checkcount);
			works.add(teacherWorkdetail);
		}
		CsvUtils cu=new CsvUtils();
		String path=this.getClass().getResource("/").getPath();
        //String path=Class.class.getClass().getResource("/").getPath();
        path=path.substring(0,path.lastIndexOf("/"));
        path=path.substring(0,path.lastIndexOf("/"));
        path=path.substring(0,path.lastIndexOf("/"));
        path=path.substring(0,path.lastIndexOf("/"));
        cu.writeCsv(path+"/csv/teacher_"+time+".csv",works );
	}
}