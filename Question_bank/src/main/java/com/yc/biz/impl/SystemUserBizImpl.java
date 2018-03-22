package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.SystemUserBiz;
import com.yc.dao.BaseDao;
import com.yc.po.SystemUser;
import com.yc.vo.Page;

@Service("systemUserBiz")
public class SystemUserBizImpl implements SystemUserBiz
{
	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao)
	{
		this.baseDao = baseDao;
	}

	@Override
	public boolean isExist(String userName)
	{
		String[] params = new String[] { userName };
		boolean rs = false;
		String sql = "select s.id from SystemUser s where s.userName=?";
		List list = (List) this.baseDao.search(sql, params);
		if (list != null && list.size() > 0)
		{
			rs = true;
		}
		return rs;
	}

	@Override@SuppressWarnings("rawtypes")
	public boolean isExist(String userName, String pwd)
	{
		String[] params = new String[] { userName };
		boolean flag = false;
		// 去除bug，先根据name和id找password，再比较password
		String sql = "select s.password from SystemUser s where s.userName=? and status=1";
		String pass = "";
		List list = (List) this.baseDao.search(sql, params);
		if (list != null && list.size() > 0)
		{
			pass = (String) list.get(0);
		}
		if (pwd.equals(pass))
		{
			flag = true;
		}
		return flag;
	}

	@Override
	public String getRole(String name, String password)
	{
		String sql = "select s.authorities from SystemUser s where s.userName=? and s.password=? and status=1";
		String params[] = { name, password };
		List<String> roles = (List<String>) baseDao.search(sql, params);
		return roles.get(1);
	}

	@Override
	public int deleteUser(SystemUser systemUser)
	{
		return 0;
	}

	@Override
	public int updateUserPassword(SystemUser systemUser)
	{	
		SystemUser su=findSystemUserByName(systemUser.getId()).get(0);
		su.setPassword(systemUser.getPassword());
		baseDao.update(su);
		return 0;
	}

	@Override
	public int updateSystemUser(SystemUser systemUser)
	{
		SystemUser su=findSystemUserByName(systemUser.getId()).get(0);
		su.setPassword(systemUser.getPassword());
		su.setAuthorities(systemUser.getAuthorities());
		su.setRemark(systemUser.getRemark());
		baseDao.update(su);
		return 0;
	}

	@Override
	public int addUser(SystemUser systemUser)
	{
		this.baseDao.add(systemUser);
		return 0;
	}
	
	@Override
	public List<SystemUser> searchUser(Integer status) {
		String hql="from  SystemUser s where s.status=? and bgcolor is not null";
		String[] params=new String[]{status+""};
		List<SystemUser> list=(List<SystemUser>) this.baseDao.search(hql,params);
		if( list!=null&&list.size()>0 ){
			return list;
		}	
		return null;
	}

	@Override
	public ArrayList<SystemUser> searchUser(){
		String hql="from  SystemUser s where s.status='1' ";
		List<SystemUser> list=(List<SystemUser>) this.baseDao.search(hql);
		if( list!=null&&list.size()>0 ){
			return (ArrayList<SystemUser>) list;
		}	
		return null;
	}

	@Override
	public List searchUser(String userName)
	{
		//String hql="from  SystemUser s where s.status=? and bgcolor is not null";
		return null;
	}

	@Override
	public List<SystemUser> findSystemUserByName(String uname)
	{
		String hql="from SystemUser s where s.userName=?";
		String[] params=new String[]{uname};
		List<SystemUser> list=(List<SystemUser>) this.baseDao.search(hql,params);
		if( list!=null&&list.size()>0 ){
			return list;
		}		
		return null;
	}

	@Override
	public int checkOldPassword(Integer userId, String userName,
			String oldPassword) {
		String hql="from SystemUser s where s.userName=? and s.id=? and s.password=?";
		String[] params=new String[]{userName,userId+"",oldPassword};
		List<SystemUser> list=(List<SystemUser>) this.baseDao.search(hql,params);
		if( list!=null&&list.size()>0 ){
			return 0;
		}		
		return 1;
	}

	@Override
	public List<SystemUser> findSystemUserByName(Integer id) {
		String hql="from SystemUser s where s.id=?";
		String[] params=new String[]{id+""};
		List<SystemUser> list=(List<SystemUser>) this.baseDao.search(hql,params);
		if( list!=null&&list.size()>0 ){
			return list;
		}		
		return null;
	}
	
	@Override
	public String findSystemUserById(Integer id) {
		String hql="select userName  from SystemUser s where s.id=?";
		String[] params=new String[]{id+""};
		List<String> list= this.baseDao.search(hql,params);
		if( list!=null&&list.size()>0 ){
			return list.get(0);
		}		
		return null;
	}

	@Override
	public List<String> searchAllAuthorities() {
		String sql="select distinct s.authorities,s.remark from SystemUser s";
		List<String> list=(List<String>) this.baseDao.search(sql);
		if( list!=null&&list.size()>0 ){
			return list;
		}		
		return null;
	}

	@Override
	public void searchSystemUserPageByRemark(Page<SystemUser> page, SystemUser su) {
		if (su.getRemark() != null && !"".equals(su.getRemark())) {
			String hqlString = "from SystemUser su  where su.remark like '%"+su.getRemark()+"%'";
			String queryCountHql = "select count(su) from SystemUser su  where  su.remark like '%"+su.getRemark()+"%'";
			baseDao.showPage(hqlString, queryCountHql, page);
		} else {
			String hqlString = "from SystemUser su ";
			String queryCountHql = "select count(su) from SystemUser su";
			baseDao.showPage(hqlString, queryCountHql, page);
		}
		
	}

	@Override
	public List<String> searchSystemUserInfo() {
		String hqlString = "select su.id,su.userName,su.remark from SystemUser su ";
		List<String> list=(List<String>) this.baseDao.search(hqlString);
		if( list!=null&&list.size()>0 ){
			return list;
		}
		return null;
	}

	

}
