package com.yc.batch;

import java.io.InputStream;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.WorkBiz;
import com.yc.utils.CsvUtils;
import com.yc.vo.TeacherInterviewdetail;
import com.yc.vo.TeacherWorkdetail;
import com.yc.vo.Workdetail;
import net.sf.ehcache.util.PropertyUtil;



//写
@Component("teacherInterviewItemWriter")
public class TeacherInterviewItemWriter implements ItemWriter<TeacherInterviewdetail>{

	@Override
	public void write(List<? extends TeacherInterviewdetail> teacherInterviewdetails) throws Exception {
		Properties props = new Properties();
		InputStream in= PropertyUtil.class.getClassLoader().getResourceAsStream("connectionConfig.properties");
		props.load(in);
		String time=props.getProperty("detail_time");
		CsvUtils cu=new CsvUtils();
		 List<Object> works=new ArrayList<Object>();
		 for(TeacherInterviewdetail t:teacherInterviewdetails){
			 works.add(t);
		 }
		 
		String path=this.getClass().getResource("/").getPath();
		path=path.substring(0,path.lastIndexOf("/"));
        path=path.substring(0,path.lastIndexOf("/"));
        path=path.substring(0,path.lastIndexOf("/"));
        path=path.substring(0,path.lastIndexOf("/"));
        cu.writeCsv(path+"/csv/teacherInterview_"+time+".csv",works );
		
	}

	 
}