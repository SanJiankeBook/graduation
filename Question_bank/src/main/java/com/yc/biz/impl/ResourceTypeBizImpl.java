package com.yc.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.biz.ResourceTypeBiz;
import com.yc.dao.BaseDao;
import com.yc.po.Book;
import com.yc.po.Category;
import com.yc.po.ChangeClass;
import com.yc.po.ExaminInterviewRecord;
import com.yc.po.ResourceType;
import com.yc.vo.Page;
@Service("resourceTypeBizImpl")
public class ResourceTypeBizImpl implements ResourceTypeBiz {

	private BaseDao baseDao;

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public List<ResourceType> getAllResourceType() {
		String sql="select * from ResourceType";
		return this.baseDao.excuteSelectSql(sql, new ResourceType());
	}
	
	@Override
	public List<Category> getAllType() {
		String sql="select * from Category";
		return this.baseDao.excuteSelectSql(sql, new Category());
	}
	@Override
	public void insertInfo(Book book) {
		baseDao.add(book);
	}
	@Override
	public Page<Book> searchBookInfo(Book book,Page<Book> page) {
		
		String sql="select * from Book where ";
		Map<String,Object> params=new HashMap<String,Object>();
		
		if(book.getResourceTypeId()!=null && book.getResourceTypeId()!=0){
			sql+=" resourceTypeId=:resourceTypeId and ";
			params.put("resourceTypeId", book.getResourceTypeId());
		}
		
		if(book.getCid()!=null && book.getCid()!="" && !book.getCid().equals("")){
			sql+=" cid=:cid and ";
			params.put("cid", book.getCid());
		}
		if(book.getTitle()!=null && book.getTitle()!="" && !book.getTitle().equals("")){
			sql+=" title like :title and ";
			book.setTitle("%"+book.getTitle()+"%");
			params.put("title", book.getTitle());
		}
		
		if(book.getBid()!=null && book.getBid()!=0){
			sql+=" bid= :bid and ";
			params.put("bid", book.getBid());
		}
		
		if(book.getEdtionId()!=null && book.getEdtionId()!=0){
			sql+=" edtionId= :edtionId and ";
			params.put("edtionId", book.getEdtionId());
		}
		if(book.getSubjectid()!=null && book.getSubjectid()!=0){
			sql+=" subjectid= :subjectid and ";
			params.put("subjectid", book.getSubjectid());
		}
		if(book.getSemeter()!=null && book.getSemeter()!="" && !book.getSemeter().equals("")){
			sql+=" semeter= :semeter and ";
			params.put("semeter", book.getSemeter());
		}
		if(book.getIsbn()!=null && book.getIsbn()!="" &&!book.getIsbn().equals("")){
			sql+=" isbn= :isbn and ";
			params.put("isbn", book.getIsbn());
		}
		sql+=" 1=1";
		
		if( page.getCurrentPage()==null||page.getCurrentPage()==0){
			List listSum=baseDao.findDatabyhql(sql, params, new Book(), null,null);
			page.setResult(listSum);
		}else{
			//下面这块是分页
			Integer startPage=page.getCurrentPage();
			//计算在这种条件下总共有多少条结果
			List listSum=baseDao.findDatabyhql(sql, params, new Book(), null,null);
			Integer totalsPage=0;
			if(listSum.size()%10!=0){
				totalsPage=listSum.size()/10+1;
			}else{
				totalsPage=listSum.size()/10;
			}
			//设置显示数据的总页面
			page.setTotalsPage(totalsPage);
			//设置数据的总条数
			page.setTotalsCount(listSum.size());
			//分页查询
			List<Book> list=baseDao.findDatabyhql(sql, params, new Book(), String.valueOf(startPage), String.valueOf(page.getPageSize()));
			page.setResult(list);
		}
		return page;
	}
}
