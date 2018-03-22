package com.yc.webexam.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.http.HttpRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.yc.biz.EditionBiz;
import com.yc.biz.ResourceTypeBiz;
import com.yc.biz.SubjectBiz;
import com.yc.biz.TempBiz;
import com.yc.po.Book;
import com.yc.po.Category;
import com.yc.po.Edition;
import com.yc.po.ExaminInterviewRecord;
import com.yc.po.ResourceType;
import com.yc.po.Subject;
import com.yc.po.XSubject;
import com.yc.utils.JsonUtil;
import com.yc.utils.UploadFileUtil;
import com.yc.vo.Books;
import com.yc.vo.Page;


public class ResourceTypeAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ResourceTypeAction.class);
	private String jsonStr = null;
	
	@Resource(name="resourceTypeBizImpl")
	private ResourceTypeBiz resourceTypeBiz;
	@Resource(name = "editionBiz")
	private EditionBiz editionBiz;
	
	@Resource(name = "subjectBiz")
	private SubjectBiz subjectBiz;
	public ResourceTypeBiz getResourceTypeBiz() {
		return resourceTypeBiz;
	}
	public void setResourceTypeBiz(ResourceTypeBiz resourceTypeBiz) {
		this.resourceTypeBiz = resourceTypeBiz;
	}
	private Integer pageNume;//当前页面
	private Integer displayRows;//每页获取多少条
	private Integer bid; // 唯一标志号
	private Integer edtionId;//版本id
	private String semeter;//学期编号
	private Integer subjectid;//课程id
	private Integer id; // 唯一标志号
	private String cid; // 书籍类型名
	private String author; // 作者
	private String isbn;//书籍编号
	private float price; //书籍价格
	private String title;//书籍名称
	private String description;//描述
	private String imagesUrl;//图片地址
	private String pdfsUrl;//pdf地址
	private String  codesUrl;//源码地址
	private Integer resourceTypeId;//书籍？源码？工具类
	
	private String pdfsUrlFileName;
	private String codesUrlFileName;
   // <!--获取上传文件名,命名格式：表单file控件名+FileName(固定)-->    
    private String imagesUrlFileName;

    //获取上传文件类型,命名格式：表单file控件名+ContentType(固定) 
    private String imagesUrlContentType; 
    

	public Integer getPageNume() {
		return pageNume;
	}
	public void setPageNume(Integer pageNume) {
		this.pageNume = pageNume;
	}
	public Integer getDisplayRows() {
		return displayRows;
	}
	public void setDisplayRows(Integer displayRows) {
		this.displayRows = displayRows;
	}
	public Integer getEdtionId() {
		return edtionId;
	}
	public void setEdtionId(Integer edtionId) {
		this.edtionId = edtionId;
	}
	public String getSemeter() {
		return semeter;
	}
	public void setSemeter(String semeter) {
		this.semeter = semeter;
	}
	public Integer getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPdfsUrlFileName() {
		return pdfsUrlFileName;
	}
	public void setPdfsUrlFileName(String pdfsUrlFileName) {
		this.pdfsUrlFileName = pdfsUrlFileName;
	}
	public String getCodesUrlFileName() {
		return codesUrlFileName;
	}
	public void setCodesUrlFileName(String codesUrlFileName) {
		this.codesUrlFileName = codesUrlFileName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public Integer getResourceTypeId() {
		return resourceTypeId;
	}
	public void setResourceTypeId(Integer resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	public String getImagesUrl() {
		return imagesUrl;
	}
	public void setImagesUrl(String imagesUrl) {
		this.imagesUrl = imagesUrl;
	}
	public String getPdfsUrl() {
		return pdfsUrl;
	}
	public void setPdfsUrl(String pdfsUrl) {
		this.pdfsUrl = pdfsUrl;
	}
	public String getCodesUrl() {
		return codesUrl;
	}
	public void setCodesUrl(String codesUrl) {
		this.codesUrl = codesUrl;
	}
	
	
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getImagesUrlFileName() {
		return imagesUrlFileName;
	}
	public void setImagesUrlFileName(String imagesUrlFileName) {
		this.imagesUrlFileName = imagesUrlFileName;
	}
	public String getImagesUrlContentType() {
		return imagesUrlContentType;
	}
	public void setImagesUrlContentType(String imagesUrlContentType) {
		this.imagesUrlContentType = imagesUrlContentType;
	}
	public void uploadFile() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name=request.getParameter("pdfsUrl");
		//如果是书籍
		String imagesUrlname=null;
		String pdfsUrlname=null;
		String codesUrlname=null;
		if(this.imagesUrl!=null && this.imagesUrl!=""){
			if(resourceTypeId==1){//如果是书籍
				imagesUrlname=UploadFileUtil.uploadFile(request,new File(this.imagesUrl),this.imagesUrlFileName, "updloadBook");
			}else if(resourceTypeId==2){//如果是源码
				imagesUrlname=UploadFileUtil.uploadFile(request,new File(this.imagesUrl),this.imagesUrlFileName, "updloadResourceCode");
			}else if(resourceTypeId==3){//如果是课堂资料
				imagesUrlname=UploadFileUtil.uploadFile(request,new File(this.imagesUrl),this.imagesUrlFileName, "updloadClassMaterials");
			}else if(resourceTypeId==4){//如果是工具类
				imagesUrlname=UploadFileUtil.uploadFile(request,new File(this.imagesUrl),this.imagesUrlFileName, "updloadUtility");
			}else if(resourceTypeId==5){//如果是面试资料interview
				imagesUrlname=UploadFileUtil.uploadFile(request,new File(this.imagesUrl),this.imagesUrlFileName, "updloadinterview");
			}
		}
		if(this.pdfsUrl!=null && this.pdfsUrl!=""){
			if(resourceTypeId==1){//如果是书籍
				pdfsUrlname=UploadFileUtil.uploadFile(request,new File(this.pdfsUrl),this.pdfsUrlFileName, "updloadBook");
			}else if(resourceTypeId==2){//如果是源码
				pdfsUrlname=UploadFileUtil.uploadFile(request,new File(this.pdfsUrl),this.pdfsUrlFileName, "updloadResourceCode");
			}else if(resourceTypeId==3){//如果是课堂资料
				pdfsUrlname=UploadFileUtil.uploadFile(request,new File(this.pdfsUrl),this.pdfsUrlFileName, "updloadClassMaterials");
			}else if(resourceTypeId==4){//如果是工具类
				pdfsUrlname=UploadFileUtil.uploadFile(request,new File(this.pdfsUrl),this.pdfsUrlFileName, "updloadUtility");
			}else if(resourceTypeId==5){//如果是面试资料interview
				pdfsUrlname=UploadFileUtil.uploadFile(request,new File(this.pdfsUrl),this.pdfsUrlFileName, "updloadinterview");
			}
		}
		if(this.codesUrl!=null && this.codesUrl!=""){
			if(resourceTypeId==1){//如果是书籍
				codesUrlname=UploadFileUtil.uploadFile(request,new File(this.codesUrl),this.codesUrlFileName, "updloadBook");
			}else if(resourceTypeId==2){//如果是源码
				codesUrlname=UploadFileUtil.uploadFile(request,new File(this.codesUrl),this.codesUrlFileName, "updloadResourceCode");
			}else if(resourceTypeId==3){//如果是课堂资料
				codesUrlname=UploadFileUtil.uploadFile(request,new File(this.codesUrl),this.codesUrlFileName, "updloadClassMaterials");
			}else if(resourceTypeId==4){//如果是工具类
				codesUrlname=UploadFileUtil.uploadFile(request,new File(this.codesUrl),this.codesUrlFileName, "updloadUtility");
			}else if(resourceTypeId==5){//如果是面试资料interview
				codesUrlname=UploadFileUtil.uploadFile(request,new File(this.codesUrl),this.codesUrlFileName, "updloadinterview");
			}
		}
		Book book=new Book();
		book.setAuthor(author);
		book.setCid(cid);
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setPrice(price);
		book.setDescription(description);
		book.setPdfsUrl(pdfsUrlname);
		book.setImagesUrl(imagesUrlname);
		book.setCodesUrl(codesUrlname);
		book.setResourceTypeId(resourceTypeId);
		book.setEdtionId(edtionId);
		book.setSubjectid(subjectid);
		book.setSemeter(semeter);
		this.resourceTypeBiz.insertInfo(book);
		jsonStr = super.writeJson(0, "");
		try {
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}
	
	public void getResourceType() throws IOException {
		List<ResourceType> list=this.resourceTypeBiz.getAllResourceType();
		jsonStr = super.writeJson(0, list);
		try {
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}
	/**
	 * 书籍搜索
	 * @throws Exception 
	 */
	public void search() throws Exception{
		Book book=new Book();
		book.setCid(cid);
		book.setTitle(title);
		book.setResourceTypeId(resourceTypeId);
		
		Page<Book> page = new Page<Book>();
		page.setPageSize(displayRows);
		page.setCurrentPage(pageNume);
		page=this.resourceTypeBiz.searchBookInfo(book,page);
		List<Book> list=page.getResult();
		List<Category> list1=this.resourceTypeBiz.getAllType();
		
		Map<Integer,Object> map=new HashMap<Integer,Object>();
		for(Category l1:list1){
			map.put(l1.getCid(), l1.getName());
		}
		List<Book> listBook=new ArrayList<Book>();
		for(Book books:list){
			if(map.containsKey(Integer.parseInt(books.getCid()))){
				books.setCid(""+map.get(Integer.parseInt(books.getCid())));
			}
		}
		page.setResult(list);
		jsonStr = super.writeJson(0, page);
		try {
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}
	/**
	 * 源码搜索
	 * @throws IOException 
	 */
	public void searchCode() throws IOException{
		Book book=new Book();
		book.setTitle(title);
		book.setResourceTypeId(resourceTypeId);
		
		Page<Book> page = new Page<Book>();
		page.setPageSize(displayRows);
		page.setCurrentPage(pageNume);
		page=this.resourceTypeBiz.searchBookInfo(book,page);
		jsonStr = super.writeJson(0, page);
		try {
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}
	/**
	 * 课堂资料搜索
	 * @throws IOException
	 */
	public void searchClass() throws IOException{
	
		Book book=new Book();
		book.setEdtionId(edtionId);
		book.setSubjectid(subjectid);
		book.setSemeter(semeter);
		book.setTitle(title);
		book.setResourceTypeId(resourceTypeId);
		
		
		Page<Book> page = new Page<Book>();
		page.setPageSize(displayRows);
		page.setCurrentPage(pageNume);
		page=this.resourceTypeBiz.searchBookInfo(book,page);
		List<Book> list=page.getResult();
		List<Books> listbook=new ArrayList<Books>();
		List<XSubject> subjects = subjectBiz.getSubjects();
		//List<Edition>  currentUse = editionBiz.searchAllEdition();
		
		Map<Integer,Object> map=new HashMap<Integer,Object>();
		for(XSubject l1:subjects){
			map.put(l1.getId(), l1.getSubjectName());
		}
		for(Book books:list){
			Books bk=new Books();
			bk.setBid(books.getBid());
			bk.setTitle(books.getTitle());
			bk.setSemeter(books.getSemeter());
			if(map.containsKey(books.getSubjectid())){
				bk.setSubjectName(String.valueOf(map.get(books.getSubjectid())));
			}
			listbook.add(bk);
		}
		page.setResult(list);
		Page<Books> pageInfo=new Page<Books>();
		pageInfo.setCurrentPage(page.getCurrentPage());
		pageInfo.setPageSize(page.getPageSize());
		pageInfo.setResult(listbook);
		pageInfo.setTotalsCount(page.getTotalsCount());
		pageInfo.setTotalsPage(page.getTotalsPage());
		jsonStr = super.writeJson(0, pageInfo);
		try {
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}
	/**
	 * 通过id查询课堂资料信息
	 * @throws IOException 
	 */
	public void searchClassInfo() throws IOException{
		Book book=new Book();
		book.setBid(bid);
		Page<Book> page=this.resourceTypeBiz.searchBookInfo(book,new Page<Book>());
		List<Book> list=page.getResult();
		List<Books> listbook=new ArrayList<Books>();
		List<XSubject> subjects = subjectBiz.getSubjects();
		List<Edition>  currentUse = editionBiz.searchAllEdition();
		Map<Integer,Object> map=new HashMap<Integer,Object>();
		for(XSubject l1:subjects){
			map.put(l1.getId(), l1.getSubjectName());
		}
		for(Book books:list){
			Books bk=new Books();
			bk.setBid(books.getBid());
			bk.setTitle(books.getTitle());
			bk.setSemeter(books.getSemeter());
			bk.setPdfsUrl(books.getPdfsUrl());
			bk.setDescription(books.getDescription());
			bk.setEdtionId(books.getEdtionId());
			if(map.containsKey(books.getSubjectid())){
				bk.setSubjectName(String.valueOf(map.get(books.getSubjectid())));
			}
			listbook.add(bk);
		}
		map.clear();
		for(Edition l1:currentUse){
			map.put(l1.getId(), l1.getEditionName());
		}
		for(Books bk:listbook){
			if(map.containsKey(bk.getEdtionId())){
				bk.setEdtionName(String.valueOf(map.get(bk.getEdtionId())));
			}
		}
		
		page.setResult(list);
		Page<Books> pageInfo=new Page<Books>();
		pageInfo.setCurrentPage(page.getCurrentPage());
		pageInfo.setPageSize(page.getPageSize());
		pageInfo.setResult(listbook);
		pageInfo.setTotalsCount(page.getTotalsCount());
		pageInfo.setTotalsPage(page.getTotalsPage());
		jsonStr = super.writeJson(0, pageInfo);
		try {
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}
	/**
	 * 通过id查询书本信息
	 * @throws Exception 
	 */
	public void searchBookInfo() throws Exception{
		Book book=new Book();
		book.setBid(bid);
		//分页代码
		Page<Book> page = new Page<Book>();
		page.setPageSize(displayRows);
		page.setCurrentPage(pageNume);
		page=this.resourceTypeBiz.searchBookInfo(book,page);
		List<Book> list=page.getResult();
		
		List<Category> list1=this.resourceTypeBiz.getAllType();
		Map<Integer,Object> map=new HashMap<Integer,Object>();
		for(Category l1:list1){
			map.put(l1.getCid(), l1.getName());
		}
		List<Book> listBook=new ArrayList<Book>();
		for(Book books:list){
			if(map.containsKey(Integer.parseInt(books.getCid()))){
				books.setCid(""+map.get(Integer.parseInt(books.getCid())));
				//listBook.add(book);
			}
		}
		
		page.setResult(list);
		jsonStr = super.writeJson(0, page);
		try {
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
		
	}
	
	/**
	 * 获取书籍的所有类型
	 * @throws IOException
	 */
	public void getAllType() throws IOException {
		
		List<Category> list=this.resourceTypeBiz.getAllType();
		jsonStr = super.writeJson(0, list);
		try {
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}
	/**
	 *通过isbn来 获取书籍
	 * @throws IOException
	 */
	public void searchIsbn() throws IOException {
		Book book=new Book();
		book.setIsbn(isbn);
		Page<Book> list=this.resourceTypeBiz.searchBookInfo(book,new Page<Book>());
		if(list.getResult().isEmpty()){
			jsonStr = super.writeJson(0, "该isbn不重复");
		} else{
			jsonStr = super.writeJson(1, "该isbn重复");
		}
		try {
		} catch (Exception e) {
			jsonStr = super.writeJson(1, "查询失败");
			logger.error(e);
		}
		JsonUtil.jsonOut(jsonStr);
	}
}
