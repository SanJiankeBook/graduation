package com.yc.biz;

import java.util.List;
import com.yc.bean.Admin;

public interface Adminbiz {
	
	//管理员登录
	List<Admin> adminLogin(String adnumber,String adpassword);
}
