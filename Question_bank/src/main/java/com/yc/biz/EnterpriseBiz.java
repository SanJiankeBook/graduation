package com.yc.biz;

import java.util.List;

import com.yc.po.EnterprisePage;
import com.yc.po.Job;

public interface EnterpriseBiz {
	
	public List<EnterprisePage> findEnterprise(String addr,String name);
	
	public int  findstudentcount(int eid);
	
	public EnterprisePage findEnterprisebyeid(int eid);
	
	public List<Job> findStudentSetail(int eid);

}
