package com.yc.biz;


import java.util.List;
import com.yc.bean.NovelChapter;

public interface NovelChapterbiz {
	
	//插入小说章节信息
	void insertNovelChapter(NovelChapter novelchapter);
	
	//查询小说最新章节
	public List NewChapter(int nid);
	
	//根据小说ID查询小说所有章节
	public List<NovelChapter> ShowAllChapter(int nid);
	
	//根据小说id获取章节id
	public Double getNovleChapterId(NovelChapter novelchapter);
	//显示所有待审查的小说章节
	List<NovelChapter> UncheckNovelChapter(Integer start,Integer end);
	List<NovelChapter> UncheckNovelChapter();
	
	//显示待审查小说章节的详情
	NovelChapter ShowDetails(Integer id);
	
	//通过待审查的章节
	void PassChapter(Integer id);
	
	//不通过待审核的章节
	void UnpassChapter(Integer id);
	
	//查看小说章节内容
	List<NovelChapter> ShowContent(Integer cid);
}
