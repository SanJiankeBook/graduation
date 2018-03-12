package com.yc.biz.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.yc.bean.UserTalk;
import com.yc.biz.UserTalkbiz;
import com.yc.dao.BaseDao;
import org.springframework.stereotype.Service;
import com.yc.biz.UserTalkbiz;


@Service
public class UserTalkbizImpl implements UserTalkbiz {
	private  BaseDao bd;
	@Resource(name="baseDaoMybatisImpl")
	public void setBd(BaseDao bd) {
		this.bd = bd;
	}
	
	//	根据书本id获取这本书所有的评论
	@Override
	public List<UserTalk> findAllTalk(UserTalk usertalk) {
		return this.bd.findAll(usertalk, "findAllTalk");
	}
	
//	根据书本id获取这本书所有的评论分页
	@Override
	public List<UserTalk> findLimitTalk(UserTalk usertalk, int start, int end) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		map.put("nid", usertalk.getNid());
		return this.bd.findAll(usertalk, map, "findUserByPage");
	}

	//根据书本添加评论
	@Override
	public void addtalk(UserTalk usertalk) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		usertalk.setUtdate(dateString);
		this.bd.add(usertalk, "adduserTalk");
		
	}
}
