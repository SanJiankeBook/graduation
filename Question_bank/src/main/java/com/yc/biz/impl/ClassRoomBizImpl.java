package com.yc.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.ClassRoomBiz;
import com.yc.dao.BaseDao;
import com.yc.po.ClassRoom;

@Service("classRoomBiz")
public class ClassRoomBizImpl implements ClassRoomBiz{
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public List<ClassRoom> getAllClassRoom(Integer status) {
			String hql="from ClassRoom c where c.status=? order by local";
			String[] params=new String[]{status+""};
			List<ClassRoom> list=(List<ClassRoom>) this.baseDao.search(hql,params);
			if( list!=null&&list.size()>0 ){
				return list;
			}	
			return null;
		}

	
	public String getClassRoomNameById(Integer id) {
		String[] params=new String[]{id+""};
		String sql = "select classroomname from ClassRoom where classroomid=?";
		List<String> list=this.baseDao.search(sql,params);
		return list.get(0);
	}

}
