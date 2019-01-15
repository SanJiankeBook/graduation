package com.yc.biz.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.yc.bean.Novel;
import com.yc.bean.NovelType;
import com.yc.biz.Novelbiz;
import com.yc.dao.BaseDao;

@Service
public class NovelbizImpl implements Novelbiz {
	private BaseDao bd;

	@Resource(name = "baseDaoMybatisImpl")
	public void setBd(BaseDao bd) {
		this.bd = bd;
	}

	@Override
	public List<Novel> FindAllNovel() {
		List<Novel> list = this.bd.findAll(new Novel(), "findAllNovel");
		return list;
	}
	


	@Override
	public void delNovel(Integer nid) {
		Novel novel=new Novel();
		novel.setNid(nid);
		this.bd.delete(novel, "delNovel");
	}

	@Override
	public List<Novel> FindNovelByPage(Integer start, Integer end) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);

		List<Novel> list=this.bd.findAll(new Novel(), map, "findNovelByPage");

		return list;
	}

	@Override
	public List ShowNovel() {
		List list = bd.findAll(new Novel(), "getAllnname");
		return list;
	}

	// 根据小说id获取小说信息
	@Override
	public List ShowNovel_id(int nid) {
		Novel novel = new Novel();
		novel.setNid(nid);
		List list = bd.findAll(novel, "idgetAllnname");
		return list;
	}

	// 根据类型查询小说名字
	@Override
	public List<Novel> TypeNovel(String tname) {
		Novel novel = new Novel();
		NovelType ntype = new NovelType();
		ntype.setTname(tname);
		novel.setNovelType(ntype);
		List<Novel> list = bd.findAll(novel, "typegetAllnname");
		return list;
	}


	//插入书本信息
		@Override
		public Integer InsertNovel(Novel novel) {
			this.bd.add(novel, "addNovel");
			return null;
		}

		//根据小说名查询小说
		@Override
		public List<Novel> findNovelByName(Novel novel) {
			return this.bd.findAll(novel, "findNovleByName");
			
		}

		////根据小说名查询小说并且分页
		@Override
		public List<Novel> FindNovelByNameFenYe(String nname, int start, int end) {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("start", start);
			map.put("end", end);
			map.put("ssnname", nname);
			List<Novel> list=this.bd.findAll(new Novel(), map, "findNovelByPageName");
			return list;
		}


	//根据小说名查询所有小说信息
	@Override
	public List<Novel> NameFindNovel(String nname) {
		Novel novel=new Novel();
		novel.setNname(nname);
		List<Novel> list=bd.findAll(novel, "nnamegetAll");
		return list;
	}


		/**
		 * 查询待审核的小说
		 */
		@Override
		public List<Novel> UncheckNovel(Integer start,Integer end) {
			Map<String,Integer> map=new HashMap<String,Integer>();
			map.put("start", start);
			map.put("end", end);
			List<Novel> list=this.bd.findAll(new Novel(),map, "uncheckNovel");
			return list;
		}

	@Override
	public List<Novel> FindNovelByaid(int aid, int start, int end) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("aaid", aid);
		List<Novel> list=bd.findAll(new Novel(),map,"NovelByaid");
		return list;
	}

	
	@Override
	public List<Novel> ShowTNovel(int nid) {
		Novel novel=new Novel();
		novel.setNid(nid);
		List<Novel> list=this.bd.findAll(novel, "TNovelBynid");
		return list;
	}

	@Override
	public void UpdateNovel(String name, String npicture, String nstatus, int nid, int tid) {
		Novel novel=new Novel();
		novel.setNname(name);
		novel.setNpicture(npicture);
		novel.setNstatus(nstatus);
		novel.setNid(nid);
		novel.setTid(tid);
		this.bd.update(novel, "updateNovel");
	}



		/**
		 * 通过待审查的小说
		 */
		@Override
		public void passNovel(Integer nid) {
			Novel novel=new Novel();
			novel.setNid(nid);
			this.bd.update(novel, "passNovel");
		}

		/**
		 * 不通过待审核的小说
		 */
		@Override
		public void UnpassNovel(Integer nid) {
			Novel novel=new Novel();
			novel.setNid(nid);
			this.bd.update(novel, "unpassNovel");
		}


		//查询所有待审核的小说
		@Override
		public List<Novel> count() {
			List<Novel> list=this.bd.findAll(new Novel(), "findAllUncheckNovel");
			return list;
		}

		//作品推荐
		@Override
		public List<Novel> NovelRecommend() {
			List<Novel> list=this.bd.findAll(new Novel(), "novelrecommand");
			return list;
		}

		
		public List<Novel> Recommand(){
			List<Novel> list=this.bd.findAll(new Novel(), "recommand");
			return list;
		}

		@Override
		public List<Novel> FindNovelBytid(int tid, int start, int end) {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("start", start);
			map.put("end", end);
			map.put("tid", tid);
			List<Novel> list=bd.findAll(new Novel(),map,"TNovelBytname");

			return list;
		}
}
