package com.yc.biz.impl;



import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yc.biz.Adminbiz;
import com.yc.dao.BaseDao;
import com.yc.bean.Admin;


@Service
public class AdminbizImpl implements Adminbiz {
	private  BaseDao bd;
	
	@Resource(name="baseDaoMybatisImpl")
	public void setBd(BaseDao bd) {
		this.bd = bd;
	}


	
	//管理员登录
	@Override
	public List<Admin> adminLogin(String adnumber, String adpassword) {
		Admin admin=new Admin();
		admin.setAdnumber(adnumber);
		admin.setAdpassword(adpassword);
		List<Admin> list=this.bd.findAll(admin, "adminLogin");
		return list;
	}

}
