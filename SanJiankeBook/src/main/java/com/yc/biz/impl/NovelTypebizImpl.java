package com.yc.biz.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.yc.bean.NovelType;
import com.yc.biz.NovelTypebiz;
import com.yc.dao.BaseDao;

@Service
public class NovelTypebizImpl implements NovelTypebiz {

	private  BaseDao bd;
	
	@Resource(name="baseDaoMybatisImpl")
	public void setBd(BaseDao bd) {
		this.bd = bd;
	}

	public BaseDao get(){
		return this.bd;
	}
	
	
	@Override
	public List<NovelType> showType( Object object) {
		List<NovelType> list=bd.findAll(object, "getAllType");
		return list;
	}
	
	//	//获取所有的书本类型，包括id，名字
	@Override
	public List<Object> AllNovelType(Object obj) {
		List<Object> list=bd.findAll(obj, "getAllNovelType");
		return list;
	}

	@Override
	public List<NovelType> TnameByType(String tname) {
		NovelType type=new NovelType();
		type.setTname(tname);
		List<NovelType> list=this.bd.findAll(type, "getTid");
		return list;
	}

}
