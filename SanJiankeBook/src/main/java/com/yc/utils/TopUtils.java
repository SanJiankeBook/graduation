package com.yc.utils;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.yc.bean.Novel;
import com.yc.bean.NovelType;
import com.yc.bean.Rank;
import com.yc.biz.NovelTypebiz;
import com.yc.biz.Novelbiz;
import com.yc.biz.impl.NovelTypebizImpl;
import com.yc.biz.impl.NovelbizImpl;
@Component
public class TopUtils {
	 private Novelbiz novelbiz;
	 private NovelType noveltype;
	 private NovelTypebiz noveltypebiz;
	 private NovelbizImpl novelbizImpl;
	 private NovelTypebizImpl novelTypebizImpl;
	 
	@Resource(name="novelbizImpl") 
	public void setNovelbiz(Novelbiz novelbiz) {
		this.novelbiz = novelbiz;
	}
	
	@Resource(name="novelTypebizImpl")
	public void setNoveltypebiz(NovelTypebiz noveltypebiz) {
		this.noveltypebiz = noveltypebiz;
	}
	
	@Resource(name="novelTypebizImpl")
	public void setNovelTypebizImpl(NovelTypebizImpl novelTypebizImpl) {
		this.novelTypebizImpl = novelTypebizImpl;
	}

	public List<Novel> RankInformation(int id){
		List<NovelType> list=noveltypebiz.showType(new NovelType()); //小说类型
		
		//显示点击量最高的小说
	  	List<Rank> listNovel=new ArrayList<Rank>();
		List<String> dlist=new ArrayList<String>();//点击量
		List<String> Ranklist=new ArrayList<String>();//排名
		RedisUtils redis=new RedisUtils();
		
		//第一个类型的
		List<Novel> list1=novelbiz.TypeNovel(list.get(id).getTname());
		for(int i=0;i<list1.size();i++){
			String rname=  list1.get(i).getNname();//小说名
			Double num=redis.ShowRank(rname);//点击量 Double
			String Stnum=String.valueOf(num); //点击量 String
			String inum=String.valueOf(i+1);//排名
			dlist.add(Stnum);
			Ranklist.add(inum);
		} 
		
		for(int j=0;j<list1.size();j++){
			Rank rank=new Rank();
			rank.setNovelname(list1.get(j).getNname());
			rank.setRanknum(dlist.get(j));
			rank.setDoll(Ranklist.get(j));
			listNovel.add(rank);
				if(j==0){
					//System.out.println("ListNovel===="+listNovel);
					String nname=rank.getNovelname();
					//System.out.println(rank.getNovelname()+".......");
			    	List<Novel> nameNovel=novelbiz.NameFindNovel(nname);
			    	return nameNovel;
				}
		}
		return null;
		
	}
}
