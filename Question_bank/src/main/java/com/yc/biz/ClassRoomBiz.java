package com.yc.biz;

import java.util.List;

import com.yc.po.ClassRoom;

public interface ClassRoomBiz {
	/**
	 * 查询所有的教室
	 * @return
	 */
	public List<ClassRoom> getAllClassRoom(Integer status);
	/**
	 * 根据id查教室名
	 * @param id
	 * @return
	 */
	public String getClassRoomNameById(Integer id) ;

}
