package com.yc.utils;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.yc.bean.Alllist;
import com.yc.bean.Novel;
import com.yc.bean.NovelType;
import com.yc.biz.Authorbiz;
import com.yc.biz.NovelTypebiz;
import com.yc.biz.Novelbiz;
import com.yc.biz.impl.NovelTypebizImpl;
import com.yc.biz.impl.NovelbizImpl;

@Component(value="tNovelUtils")
public class TNovelUtils {
	 private Novelbiz novelbiz;
	 private NovelType noveltype;
	 private NovelTypebiz noveltypebiz;
	 private NovelbizImpl novelbizImpl;
	 private NovelTypebizImpl novelTypebizImpl;
	 private Authorbiz authorbiz;
	 

	@Resource(name="authorbizImpl") 
	public void setAuthorbiz(Authorbiz authorbiz) {
		this.authorbiz = authorbiz;
	}

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
	
	//Alllist allist=new Alllist();
	public List<Alllist> ListNovel( String tyname){
		//List<NovelType> list=novelTypebizImpl.showType(noveltype); //小说类型
		List<Novel> TypeNovel=novelbiz.TypeNovel(tyname);
		List<Alllist> Alllist=new ArrayList<Alllist>();  //整合小说id-小说名 -作者名
		
		for(int a=0;a<TypeNovel.size();a++){
			Alllist allist1=new Alllist();
			int nid=TypeNovel.get(a).getNid(); //获取小说id
	  		String snid=String.valueOf(nid);
	  		String nname=TypeNovel.get(a).getNname();
	      	List<Novel> author=authorbiz.Show_Author(nid);
	      	String aname=author.get(0).getAuthor().getPan_name();
	      	allist1.setNid(snid);
	      	allist1.setNname(nname);
	      	allist1.setAname(aname);
	      	allist1.setNpicture(TypeNovel.get(a).getNpicture());
	      	Alllist.add(allist1);
	      	
	      	 
		}
		return Alllist;
	}
	
}
