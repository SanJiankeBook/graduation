package com.yc.biz;

import java.util.List;

import com.yc.bean.UserTalk;

public interface UserTalkbiz {
	
	//	根据书本id获取这本书所有的评论
	List<UserTalk> findAllTalk(UserTalk usertalk);
	// 根据书本id获取这本书所有的评论分页
	List<UserTalk> findLimitTalk(UserTalk usertalk, int start, int end);
	//添加书本评论
	public void addtalk(UserTalk usertalk);

}
