package com.yc.webexam.actions;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.yc.biz.CurriculumBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.SystemUserBiz;
import com.yc.po.SystemUser;
import com.yc.po.TeacherCurriModel;
import com.yc.utils.JsonUtil;
import com.yc.vo.CurriculumPage;


public class teachSeduceToExcel extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1107107308023089084L;
	private static final Logger logger = Logger.getLogger(CurriToExcel.class);
	private HttpServletResponse response;
	HttpServletRequest request = ServletActionContext.getRequest();
	private ExamineeClassBiz examineeClassBiz;
	private CurriculumBiz curriculumBiz;
	HttpServletRequest req = ServletActionContext.getRequest();
	private InputStream excelFile;
	private SystemUserBiz systemUserBiz;

	public InputStream getExcelFile() {
		return excelFile;
	}

	@Resource(name = "systemUserBiz")
	public void setSystemUserBiz(SystemUserBiz systemUserBiz) {
		this.systemUserBiz = systemUserBiz;
	}

	@Resource(name = "examineeClassBiz")
	public void setExamineeClassBiz(ExamineeClassBiz examineeClassBiz) {
		this.examineeClassBiz = examineeClassBiz;
	}

	@Resource(name = "curriculumBiz")
	public void setCurriculumBiz(CurriculumBiz curriculumBiz) {
		this.curriculumBiz = curriculumBiz;
	}



	@SuppressWarnings("unchecked")
	public void ExcelExport() throws Exception {
		String data = req.getParameter("dataTemp");

		String jsonStr="";
		try{

		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel

		List<SystemUser> allTeacherName=this.systemUserBiz.searchUser(1);
		//System.out.println("data:"+data);
		
		//System.out.println("single:"+data[1].);
		List<CurriculumPage> obj = (List<CurriculumPage>) JSONObject.parseArray(data, (new CurriculumPage()).getClass());

		for(int i=0,len=allTeacherName.size();i<len;i++){
			List<CurriculumPage> singleTeacherList=new ArrayList<CurriculumPage>();
			for(int j=0,len1=obj.size();j<len1;j++){
				if(allTeacherName.get(i).getUserName().equals(obj.get(j).getTeacherName())){
					singleTeacherList.add(obj.get(j));
				}
			}
			if(singleTeacherList.size()==0){
				continue;
			}
			HSSFCellStyle style = workbook.createCellStyle(); // 设置表头的类型
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont font = workbook.createFont(); // 设置字体
			HSSFSheet sheet = workbook.createSheet(singleTeacherList.get(0).getTeacherName()); // 创建一个sheet
			HSSFHeader header = sheet.getHeader();// 设置sheet的头
			HSSFCell cell = null; // Excel的列
			HSSFRow row = null; // Excel的行

			/*
			 * 设置表头：对Excel每列取名 (必须根据你取的数据编写)
			 */
			String[] frist=new String[2];
			frist[0]="授课人";
			frist[1]=singleTeacherList.get(0).getTeacherName();	

			String[] tableHeader = new String[4];
			tableHeader[0] ="班级";
			tableHeader[1] ="课时";
			tableHeader[2] ="班级人数";
			tableHeader[3] ="班级流失人数";

			short cellNumber = (short) tableHeader.length;// 表的列数

			/**
			 * 根据是否取出数据，设置header信息
			 *
			 */

			if (singleTeacherList.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter("月度课时统计");
				row = sheet.createRow(0);// 第0行
				row.setHeight((short) 400);

				for(int n=0;n<2;n++){
					cell = row.createCell(n);// 创建第0行第k列
					cell.setCellValue(frist[n]);// 设置第0行第k列的值
					
					sheet.setColumnWidth(n, 6000);// 设置列的宽度
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					style1.setFont(font);// 设置字体风格
					cell.setCellStyle(style1);
					style.setFillForegroundColor(HSSFColor.WHITE.index);
					
				}
				row = sheet.createRow(1);// 第1行
				row.setHeight((short) 400);
				for (int b = 0; b< cellNumber; b++) {
					cell = row.createCell(b);// 创建第0行第k列
					cell.setCellValue(tableHeader[b]);// 设置第0行第k列的值
					sheet.setColumnWidth(b, 6000);// 设置列的宽度
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					style1.setFont(font);// 设置字体风格
					cell.setCellStyle(style1);
					
				}


				/*
				 * // 给excel填充数据这里需要编写
				 * 
				 */
				int total=0;
				for(int k=0,len1=singleTeacherList.size();k<len1;k++){
					row = sheet.createRow(k + 2);
					row.setHeight((short) 400);
					sheet.setColumnWidth(0, 6000);// 设置列的宽度
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short) 7);// 设置字体大小
					style1.setFont(font);// 设置字体风格
					cell.setCellStyle(style1);

					CurriculumPage curriData=singleTeacherList.get(k);

					row = sheet.getRow((short) k+2);
					cell = row.createCell(0);
					cell.setCellValue(curriData.getClassName());
					cell = row.createCell(1);
					cell.setCellValue(curriData.getTeachCount()/2);
					cell = row.createCell(2);
					cell.setCellValue(curriData.getStudentCount());
					cell = row.createCell(3);
					cell.setCellValue(curriData.getStudentCount2());
					total+=curriData.getTeachCount()/2;
				}
				
				String[] last=new String[2];
				last[0]="课时总计";
				last[1]=String.valueOf(total);	
				row = sheet.createRow(singleTeacherList.size()+2);// 第1行
				row.setHeight((short) 400);
				for (int k = 0; k < 2; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(last[k]);// 设置第0行第k列的值
					sheet.setColumnWidth(k, 6000);// 设置列的宽度
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					style1.setFont(font);// 设置字体风格
					cell.setCellStyle(style1);
					
				}

			}
		}

		/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		excelFile = new ByteArrayInputStream(baos.toByteArray());
		baos.close();*/
		//写进文件  
		File webappsfile=new File( request.getSession().getServletContext().getRealPath(  File.separator )).getParentFile();
		File picFile=new File(  webappsfile, "excel");
		String picBasePath =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/excel" ;
		String newFileUrl= picBasePath+File.separator +"teachSeduceExcel.xls";
		if (!picFile.exists()) {
			picFile.mkdirs();
		} 
		//创建工作簿
		FileOutputStream fos=new FileOutputStream(picFile+File.separator +"teachSeduceExcel.xls");
		workbook.write(fos);
		jsonStr = super.writeJson(0, newFileUrl);
	} catch (Exception e) {
		jsonStr = super.writeJson(1, "导出失败");
		logger.error(e);
	} finally {
		try {
			JsonUtil.jsonOut(jsonStr);
		} catch (IOException e) {
			logger.error(e);
		}
	}
		 
	}

	


}
