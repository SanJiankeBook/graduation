package com.yc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.yc.bean.Alllist;
import com.yc.bean.Novel;
import com.yc.biz.Authorbiz;
import com.yc.biz.Novelbiz;

@Component
public class RandomUtils {
	private Novelbiz novelbiz;
	private Authorbiz authorbiz;
	
	@Resource(name="novelbizImpl") 
	public void setNovelbiz(Novelbiz novelbiz) {
		this.novelbiz = novelbiz;
	}
	
	
	@Resource(name="authorbizImpl")
	public void setAuthorbiz(Authorbiz authorbiz) {
		this.authorbiz = authorbiz;
	}

	//根据类型随机显示小说信息
	public List<Alllist> Type_infor(String tname){
		List<Novel> list=novelbiz.TypeNovel(tname);
		List<Alllist> Alllist=new ArrayList<Alllist>();  //整合小说id-小说名 -作者名-小说描述-图片地址
		
		/*Integer[] count=new Integer[list.size()];
		for(int i=0;i<list.size();i++){
			count[i]=list.get(i).getNid(); // count[0],count[1]
		}
		
		Integer len=list.size();  //如果=2
*/		for(int i=0;i<list.size();i++){
			Alllist alllist=new Alllist();
			/*
			Random random=new Random();
			int num=random.nextInt(list.size()-i); //小说id
			Integer count1=count[len-i-1]; //交换  倒数最后一个数不取       count[2]
			count[len-i-1]=count[num];
			count[num]=count1;
			
			Integer nid=count[num];*/
			int nid=list.get(i).getNid();
			String Snid=String.valueOf(nid);
			List<Novel> list1=authorbiz.Show_Author(nid); //查出作者信息
			String aname=list1.get(0).getAuthor().getPan_name(); //作者笔名
			List<Novel> nlist=novelbiz.ShowNovel_id(nid); //根据小说id取小说信息
			String nname=nlist.get(0).getNname(); //小说名字
			String ndescription=nlist.get(0).getNdescription(); //小说描述
			String npicture=nlist.get(0).getNpicture();//小说图片地址
			
			alllist.setNname(nname);
			alllist.setAname(aname);
			alllist.setNdescription(ndescription);
			alllist.setNid(Snid);
			alllist.setNpicture(npicture);
			Alllist.add(alllist);		
		}
		return Alllist;			
		
	}
	
}
