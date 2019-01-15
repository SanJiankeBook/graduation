package com.yc.biz.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.yc.bean.Novel;
import com.yc.bean.User;
import com.yc.bean.UserBook;
import com.yc.biz.UserBookbiz;
import com.yc.dao.BaseDao;

@Service
public class UserBookbizImpl implements UserBookbiz {
private  BaseDao bd;
	@Resource(name="baseDaoMybatisImpl")

	public void setBd(BaseDao bd) {
		this.bd = bd;
	}
	
	//根据用户id查询书架已经有多少本书了
	@Override
	public List finduserbook(UserBook ub) {
		return this.bd.findAll(ub, "finduserbookByUid");
	}
	//查询这本书是否存在于书架
	@Override
	public List getUserbook(UserBook userbook) {
		
		return this.bd.findAll(userbook, "getUserbookPT");
	}
	//添加这本书到书架
	@Override
	public void addUserBook(UserBook userbook) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		userbook.setUbdate(dateString);
		this.bd.add(userbook, "addUserBookPT");
	}
	
	//根据用户查询书架信息
	@Override
	public List finduserbookInfo(UserBook ub, int start, int end) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		map.put("uid", ub.getUid());
		List<UserBook> listinfo1=this.bd.findAll(new UserBook(), map,"finduserbookInfo1");
		List<Novel> listinfo2=this.bd.findAll(new UserBook(), map, "finduserbookInfo2");
		for(int i=0;i<listinfo1.size();i++){
			for(int j=0;j<listinfo2.size();j++){
				if(listinfo1.get(i).getNid()==listinfo2.get(j).getNid()){
					listinfo2.get(j).setPan_name(listinfo1.get(i).getPan_name());
					listinfo2.get(j).setUbdate(listinfo1.get(i).getUbdate());
					break;
				}
			}
		}
		return listinfo2;
	}
	
	//用户删除书架的书
	@Override
	public void delUserbook(UserBook userbook) {
		this.bd.delete(userbook, "delUserbookInfo");
	}
}
