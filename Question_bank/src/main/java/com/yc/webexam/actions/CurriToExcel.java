package com.yc.webexam.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import org.apache.struts2.interceptor.ServletResponseAware;

import com.yc.biz.ChapterBiz;
import com.yc.biz.ClassRoomBiz;
import com.yc.biz.CurriculumBiz;
import com.yc.biz.ExamineeBiz;
import com.yc.biz.ExamineeClassBiz;
import com.yc.biz.SystemUserBiz;
import com.yc.po.Chapter;
import com.yc.po.ClassRoom;
import com.yc.po.Curriculum;
import com.yc.po.ExamineeClass;
import com.yc.po.SystemUser;
import com.yc.po.TeacherCurriModel;
import com.yc.utils.JsonUtil;
import com.yc.utils.ValueUtil;
import com.yc.vo.CurriculumPage;

/**
 * 
 * @author lyh
 * @version 创建时间：2017年5月22日 下午7:56:30
 * @ClassName 导出excel
 * @Description 类处理界面传过来的所有的请求
 */
public class CurriToExcel extends BaseAction {

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
	public String ExcelExport() throws Exception {
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String classid = req.getParameter("classid");
		int status = 1;// 0 老师 1 班级 2 教室
		List list = this.curriculumBiz.getCurriInfo(startDate, endDate, classid, status);
		List<ExamineeClass> classList = new ArrayList<>();
		if ("-1".equals(classid)) {
			classList = this.examineeClassBiz.findExamineeClassByTime(ValueUtil.getNowDate());
		} else {
			Integer classid2 = Integer.parseInt(classid);
			ExamineeClass e = new ExamineeClass();
			e = this.examineeClassBiz.findExamineeClassById(classid2);
			classList.add(e);
		}
		// 查询各个老师颜色
		List<SystemUser> sList = this.systemUserBiz.searchUser(1);
		short colorIndex = 9;
		Map<String, Short> map = new HashMap<String, Short>();
		for (SystemUser sUser : sList) {// 将颜色的索引存进去
			map.put(sUser.getBgcolor(), colorIndex);
			colorIndex++;
		}
		//
		/**
		 * 按照List中的某个String类型的属性进行排序(给班级排序)
		 * 
		 * @param list
		 */

		Collections.sort(classList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				ExamineeClass e1 = (ExamineeClass) o1;
				ExamineeClass e2 = (ExamineeClass) o2;
				return e1.getClassName().compareTo(e2.getClassName());
			}
		});

		List<TeacherCurriModel> SignList = new ArrayList<TeacherCurriModel>();
		SignList = list;

		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		for (int j = 0,len=classList.size(); j <len ; j++) {// 循环数据
			System.out.println(classList.get(j).getClassName());
			ExamineeClass eClass = (ExamineeClass) classList.get(j);
			HSSFCellStyle style = workbook.createCellStyle(); // 设置表头的类型
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容左右居中
			HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容左右居中
			HSSFFont font = workbook.createFont(); // 设置字体
			HSSFSheet sheet = workbook.createSheet(eClass.getClassName()); // 创建一个sheet
			HSSFHeader header = sheet.getHeader();// 设置sheet的头
			HSSFCell cell = null; // Excel的列
			HSSFRow row = null; // Excel的行

			/*
			 * 设置表头：对Excel每列取名 (必须根据你取的数据编写)
			 */
			String[] tableHeader = new String[8];
			tableHeader[0] = eClass.getClassName();//列的第一行命名
			String a[] = startDate.split("-");//把日期年月日分开
			int year = Integer.parseInt(a[0]);
			int month = Integer.parseInt(a[1]);
			int day = Integer.parseInt(a[2]);
			tableHeader[1] = getDate(year, month - 1, day);
			for (int k = 1; k < 7; k++) {//把各个日期命名为列名
				day += 1;
				tableHeader[k + 1] = getDate(year, month - 1, day);
			}

			/*
			 * 下面的都可以拷贝不用编写
			 */
			short cellNumber = (short) tableHeader.length;// 表的列数
			/**
			 * 根据是否取出数据，设置header信息
			 *
			 */
			if (SignList.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter("学生课表");
				row = sheet.createRow(0);// 第0行
				row.setHeight((short) 400);//设置行高
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					sheet.setColumnWidth(k, 6000);// 设置列的宽度
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					style1.setFont(font);// 设置字体风格
					cell.setCellStyle(style1);//设置样式
					style.setFillForegroundColor(HSSFColor.ROSE.index);   // 前景色的设定 
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式  
					cell.setCellStyle(style);
				}
				String times[] = { "8:30-10:30", "10:30-12:00", "14:00-15:30", "15:30-17:30", "19:00-20:30",
						"20:30-22:00" };
				for (int h = 0; h < 6; h++) {// 设置第一列
					row = sheet.createRow(h + 1);
					row.setHeight((short) 400);
					cell = row.createCell(0);
					cell.setCellValue(times[h]);// 设置第1列0行
					sheet.setColumnWidth(0, 6000);// 设置列的宽度
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					style1.setFont(font);// 设置字体风格
					cell.setCellStyle(style1);
					style.setFillForegroundColor(HSSFColor.ROSE.index);
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cell.setCellStyle(style);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 * 
				 */
				for (int i = 0,len1=SignList.size(); i <len1 ; i++) {// 循环所有的数据
					TeacherCurriModel sign = (TeacherCurriModel) SignList.get(i);// 获取sign对象
					row = sheet.getRow(0);// 0行

					if (sign.getClassname().equals(row.getCell(0).getStringCellValue())) {
						String date = sign.getDate();
						String tString = sign.getTimeperiods();
						int rownum = 0;
						int cellnum = 0;
						for (int k = 1; k < cellNumber; k++) {// 列
							row = sheet.getRow(0);// 行
							String date1 = row.getCell(k).getStringCellValue();
							if (date1.equals(date)) {
								cellnum = k;// 找到列id
								break;
							}
						}
						for (int c = 1; c < 7; c++) {
							row = sheet.getRow(c);
							String time = row.getCell(0).getStringCellValue();
							if (time.equals(tString)) {
								rownum = c;// 找到行id
								break;
							}
						}
						row = sheet.getRow((short) rownum);
						row.setHeight((short) 400);
						cell = row.createCell(cellnum);
						HSSFCellStyle style2 = workbook.createCellStyle();
						style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
						HSSFPalette palette = workbook.getCustomPalette();
						HSSFFont font1 = workbook.createFont(); // 设置字体
						if ("自习".equals(sign.getChapterName())) {
							style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);//设置前景色
						}
						if ("放假".equals(sign.getChapterName())) {
							font1.setColor(IndexedColors.RED.getIndex());
							style2.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
						}
						if ("测试".equals(sign.getChapterName())) {
							style2.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
						}
						if ("补课".equals(sign.getChapterName())) {
							style2.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
						}if("就业指导".equals(sign.getChapterName())){
							style2.setFillForegroundColor(HSSFColor.LAVENDER.index);
						}if("复习".equals(sign.getChapterName())){
							style2.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
						}
						if (sign.getChapterid() > 0) {
							String bgcolor = sign.getBgcolor();
							short colorNum = 0;
							Iterator keys = map.keySet().iterator();
							while (keys.hasNext()) {
								String key = (String) keys.next();
								if (bgcolor.equals(key)) {
									colorNum = map.get(bgcolor);
									break;
								}
							}
							String color = bgcolor.substring(bgcolor.lastIndexOf("#") + 1); // 此处得到的color为16进制的字符串
							// 转为RGB码
							int r = Integer.parseInt((color.substring(0, 2)), 16); // 转为16进制
							int g = Integer.parseInt((color.substring(2, 4)), 16);
							int b = Integer.parseInt((color.substring(4, 6)), 16);
							// 自定义cell颜色
							// 这里的9是索引用来确定的设置颜色的索引只能是 8 ~ 64，在此之外的索引无效，也不会报错。
							palette.setColorAtIndex(colorNum, (byte) r, (byte) g, (byte) b);
							style2.setFillForegroundColor(colorNum);
						}
						style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
						style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
						style2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
						style2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
						font1.setFontHeightInPoints((short) 7);// 设置字体大小
						style2.setFont(font1);
						cell.setCellStyle(style2);// 设置风格
						if ("无".equals(sign.getClassroomname())) {// 赋值
							cell.setCellValue(sign.getChapterName());
						} else {
							cell.setCellValue(sign.getChapterName() + "_" + sign.getClassroomname());
						}
					}

				}
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		excelFile = new ByteArrayInputStream(baos.toByteArray());
		baos.close();
		return "excel";
	}

	//
	public String getDate(int year, int month, int day) {
		// 制作日期 26-25 eg:3.26-4.25
		Calendar ca = Calendar.getInstance();
		// int year = ca.get(Calendar.YEAR);
		// int month = ca.get(Calendar.MONTH);
		ca.set(year, month, day);// 制作日期
		Date resultDate = ca.getTime(); // 结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		return sdf.format(resultDate);

	}

}
