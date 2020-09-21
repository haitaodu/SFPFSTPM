package com.smartflow.util;

import com.smartflow.dto.MyTaskOutputDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.smartflow.dto.MyTaskOutputDTO;

public class ExportExcelUtil {
	//    private static final String URL = "jdbc:mysql://192.168.101.217:3306/test";
	//  private static final String NAME = "dev";
	//  private static final String PASSWORD = "lJZx2Ik5eqX3xBDp";
	private static final String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8";
	private static final String NAME = "root";
	private static final String PASSWORD = "123456";


	public static boolean exportExcel(List<MyTaskOutputDTO> dataList){
		try{
			// TODO 创建HSSFWorkbook对象(excel的文档对象)
			HSSFWorkbook wb = new HSSFWorkbook();
			// TODO 设置字体格式大小
			HSSFFont font = wb.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 14);//设置字体大小
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 居中
			cellStyle.setFont(font);

			

			//建立新的sheet对象（excel的表单）
			HSSFSheet sheet = wb.createSheet("我的任务列表");
			//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
			HSSFRow row1 = sheet.createRow(0);
			row1.setHeight((short)500);
			//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
			HSSFCell cell = row1.createCell(0);

			//设置单元格内容
			cell.setCellStyle(cellStyle);
			cell.setCellValue("我的任务列表");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, dataList.size()-1));

			//在sheet里创建第二行
			HSSFRow row2 = sheet.createRow(1);
			row2.setHeight((short) 500);

			//设置表头
			String[] headers = {"Id", "创建日期", "状态", "派工任务单", "目标设备", "项目名", "位置", "负责角色", "人员", "预估任务时长"};
			List<String> headerList = Arrays.asList(headers);
	
//			Iterator<Map<String, Object>> it = dataList.iterator();
//			Object[] headerList = null;
//			List<String> valueList = new ArrayList<>();
//			while (it.hasNext()) {
//				Map<String,Object> map = it.next();
//				headerList = map.keySet().toArray();
//				for (Object key : headerList) {
//					String value = map.get(key).toString();
//					valueList.add(value);
//				}
//				
//			}
			//获取列数
			int columnCount = headerList.size();
			//创建单元格并设置单元格内容
			for (int i = 0; i < headerList.size(); i++) {
				sheet.setColumnWidth(i,30*256);
				HSSFCell cell1 = row2.createCell(i);
				cell1.setCellStyle(cellStyle);
				cell1.setCellValue(headerList.get(i).toString());
			}


			int b = 2;
			//数据导入单元格
		
			if(dataList != null && !dataList.isEmpty()) {
//				for (int i = 0; i < dataList.size(); i++) {
//					HSSFRow row = sheet.createRow(b);
//					row.setHeight((short) 500);				
//					//List valueList = (List) dataList.get(i);
//					//String value = (String) valueList.get(j);
//					HSSFCell cell1 = row.createCell(i);
//					cell1.setCellStyle(cellStyle);
//					//cell1.setCellValue(value);	
//				}
				
				for (MyTaskOutputDTO myTaskOutputDTO : dataList) {
					HSSFRow row = sheet.createRow(b);
					row.setRowStyle(cellStyle);
					row.setHeight((short) 500);			
					row.createCell(0).setCellValue(myTaskOutputDTO.getTaskId());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String createDateTime = sdf.format(myTaskOutputDTO.getCreateDateTime());
					row.createCell(1).setCellValue(createDateTime);
					row.createCell(2).setCellValue(myTaskOutputDTO.getState());
					row.createCell(3).setCellValue(myTaskOutputDTO.getAssignmentTaskSheet());
					row.createCell(4).setCellValue(myTaskOutputDTO.getTargetFacility());
					row.createCell(5).setCellValue(myTaskOutputDTO.getItemName());
					row.createCell(6).setCellValue(myTaskOutputDTO.getPosition());
					row.createCell(7).setCellValue(myTaskOutputDTO.getRoleInCharge());
					row.createCell(8).setCellValue(myTaskOutputDTO.getStaff());
					row.createCell(9).setCellValue(myTaskOutputDTO.getPredictTaskLength().toString());
					b++;
				}
				
			}
			

		//输出Excel文件
		try {
			File file = new File("C:\\Users\\smartflow\\Desktop\\detail.xls");

			FileOutputStream output = new FileOutputStream(file);
			wb.write(output);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		System.out.println("文件成功导出");
		return true;
	}catch(Exception e){
		e.printStackTrace();
		return false;
	}

}

public static void main(String[] args) {
	List<MyTaskOutputDTO> list = new ArrayList<MyTaskOutputDTO>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	MyTaskOutputDTO myTaskOutputDTO1;
	try {
		myTaskOutputDTO1 = new MyTaskOutputDTO(1, sdf.parse("2019-06-01 8:00:00"), "已分配", "PM001", "锡膏印刷机01", "点检", "喷嘴", "维护小组1", "张三", new BigDecimal(2.5));

		MyTaskOutputDTO myTaskOutputDTO2 = new MyTaskOutputDTO(2, sdf.parse("2019-06-05 8:00:00"), "已完成", "PM002", "锡膏印刷机02", "点检", "喷嘴", "维保小组2", "李四", new BigDecimal(3));

		list.add(myTaskOutputDTO1);
		list.add(myTaskOutputDTO2);
//		List<Map<String,Object>> dataList = new ArrayList<>();
//		for (MyTaskOutputDTO myTaskOutputDTO : list) {
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("Id", myTaskOutputDTO.getTaskId());
//			map.put("创建日期", myTaskOutputDTO.getCreateDateTime());
//			map.put("状态", myTaskOutputDTO.getState());
//			map.put("派工任务单", myTaskOutputDTO.getAssignmentTaskSheet());
//			map.put("目标设备", myTaskOutputDTO.getTargetFacility());
//			map.put("项目名", myTaskOutputDTO.getItemName());
//			map.put("位置", myTaskOutputDTO.getPosition());
//			map.put("负责角色", myTaskOutputDTO.getRoleInCharge());
//			map.put("人员", myTaskOutputDTO.getStaff());
//			map.put("预估任务时长", myTaskOutputDTO.getPredictTaskLength());
//			dataList.add(map);
//		}
		System.out.println(exportExcel(list));
	} catch (Exception e) {
		e.printStackTrace();
	}
}

}
