package com.yc.biz;

import java.util.List;
import com.yc.bean.NovelType;

public interface NovelTypebiz {
	
	//查询所有类型
	public List<NovelType> showType(Object object);

	//获取所有的书本类型，包括id，名字
	public List<Object> AllNovelType(Object obj);
	
	//根据类型名查询类型id
	public List<NovelType> TnameByType(String tname);
}
