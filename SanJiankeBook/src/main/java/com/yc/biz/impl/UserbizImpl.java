package com.yc.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yc.biz.Userbiz;
import com.yc.dao.BaseDao;
import com.yc.bean.User;
import com.yc.biz.Userbiz;
import com.yc.dao.BaseDao;

@Service
public class UserbizImpl implements Userbiz {
private  BaseDao bd;
	
	@Resource(name="baseDaoMybatisImpl")
	public void setBd(BaseDao bd) {
		this.bd = bd;
	}

	//注册成为用户
	@Override
	public Integer InsertUser(Object obj) {
		this.bd.add(obj, "addUser");
		return null;
	}
	@Override
	public void save(User use) {
		this.bd.add(use, "addUser");
		
	}
	
	@Override
	public List<User> userLogin(String uname, String upassword) {
		User use=new User();
		use.setUname(uname);
		use.setUpassword(upassword);
		List<User> list=this.bd.findAll(use, "userLogin");
		return list;
	}
	
	/**
	 * 查询所有用户
	 */
	@Override
	public List<User> findUser() {
		List<User> list=this.bd.findAll(new User(), "findAllUser");
		return list;
	}
	
	/**
	 * 根据id查询用户
	 */
	@Override
	public List<User> findUserById(int id) {
		return null;
	}
	
	/**
	 * 分页查询用户
	 */
	@Override
	public List<User> findUserByPage(Integer start,Integer end) {
		
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("start", start);
		map.put("end", end);
		List<User> list=this.bd.findAll(new User(), map, "findUserByPage");
		return list;
	}
	
	/**
	 * 删除用户
	 */
	@Override
	public void DelUser(Integer uid) {
		User use=new User();
		use.setUid(uid);
		this.bd.delete(use, "delUser");
	}

	/*
	 * 根据用户名查询用户信息
	 */
	@Override
	public List<User> findUserName(String u_number) {
		User user=new User();
		user.setU_number(u_number);
		List<User> list=this.bd.findAll(user, "findUserByName");
		return list;
	}

	//账号注册前的验证
	@Override
	public List<User> findUserInfo(User userlist) {
		return this.bd.findAll(userlist, "finduserinfo");
	}

	@Override
	public void addUser(User userlist) {
		this.bd.add(userlist, "addUserInfo");
	}

	//密码修改
	@Override
	public void updateUser(User user) {
		this.bd.update(user, "updatepassword");
	}
	
	//用户信息修改
	@Override
	public void updateUserInfo(User user) {
		this.bd.update(user, "updateUserInfo");
	}


}
