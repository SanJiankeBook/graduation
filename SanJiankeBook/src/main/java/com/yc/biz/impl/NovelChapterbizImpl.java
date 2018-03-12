package com.yc.biz.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yc.bean.Novel;
import com.yc.bean.NovelChapter;
import com.yc.biz.NovelChapterbiz;
import com.yc.dao.BaseDao;
import com.yc.bean.NovelChapter;
import com.yc.biz.NovelChapterbiz;
import com.yc.dao.BaseDao;

@Service
public class NovelChapterbizImpl implements NovelChapterbiz {
private  BaseDao bd;


	
	@Resource(name="baseDaoMybatisImpl")
	public void setBd(BaseDao bd) {
		this.bd = bd;
	}


	@Override
	public List NewChapter(int nid) {
		Novel novel=new Novel();
		novel.setNid(nid);
		List list=bd.findAll(novel, "newChapter");
		return list;

	}

	@Override
	public List<NovelChapter> ShowAllChapter(int nid) {
		NovelChapter nchpater=new NovelChapter();
		nchpater.setNid(nid);
		List<NovelChapter> list=bd.findAll(nchpater, "allChapter");
		return list;

	}
	//插入小说章节详细信息
	@Override
	public void insertNovelChapter(NovelChapter novelchapter) {
		this.bd.add(novelchapter, "insertNovel");

	}

	//查询待审核小说章节
	@Override
	public List<NovelChapter> UncheckNovelChapter(Integer start,Integer end) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("start", start);
		map.put("end", end);
		List<NovelChapter> list=this.bd.findAll(new NovelChapter(),map, "checkNovelChapter");
		return list;
		
	}
	
	//显示所有的审核小说
	@Override
	public List<NovelChapter> UncheckNovelChapter() {
		List<NovelChapter> list=this.bd.findAll(new NovelChapter(), "findAllcheckNovelChapter");
		return list;
	}

	//显示待审查小说章节的详情
	@Override
	public NovelChapter ShowDetails(Integer id) {
		NovelChapter chapter=new NovelChapter();
		chapter.setCid(id);
		List<NovelChapter> list=this.bd.findAll(chapter, "showDetails");
		return list != null && list.size() >0 ? list.get(0) : null ;
	}

	/**
	 * 通过待审核的章节
	 */
	@Override
	public void PassChapter(Integer cid) {
		NovelChapter chapter=new NovelChapter();
		chapter.setCid(cid);
		this.bd.update(chapter, "passChapter");
	}

	/**
	 * 不通过待审核的章节
	 */
	@Override
	public void UnpassChapter(Integer cid) {
		NovelChapter chapter=new NovelChapter();
		chapter.setCid(cid);
		this.bd.update(chapter, "unpassChapter");
	}


	//根据小说id获取章节id
	@Override
	public Double getNovleChapterId(NovelChapter novelchapter) {
		Map map=new HashMap<>();
		map.put("nid", novelchapter.getNid());
		Double nc =this.bd.fingFunc(novelchapter, map, "getNovelChapterId");
		return nc;
	}


	@Override
	public List<NovelChapter> ShowContent(Integer cid) {
		NovelChapter chapter=new NovelChapter();
		chapter.setCid(cid);
		List<NovelChapter> list=this.bd.findAll(chapter, "showContent");
		return list;
	}

}
