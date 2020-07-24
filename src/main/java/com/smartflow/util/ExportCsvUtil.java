package com.smartflow.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.csvreader.CsvWriter;
import com.smartflow.dto.MyTaskOutputDTO;
import com.smartflow.dto.TaskListOutputDTO;

public class ExportCsvUtil {
	public static String listToStr(List<MyTaskOutputDTO> list){
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
		return jsonArray.toString();
	}

	public static String[] listToStringArray(List<MyTaskOutputDTO> dataList){
		String[] strArray = new String[dataList.size()];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < dataList.size(); i++) {
			MyTaskOutputDTO myTaskOutputDTO = dataList.get(i);
			strArray[i] = myTaskOutputDTO.getTaskId().toString();		
			strArray[i] = sdf.format(myTaskOutputDTO.getCreateDateTime()); 
			strArray[i] = myTaskOutputDTO.getState(); 			
			strArray[i] = myTaskOutputDTO.getAssignmentTaskSheet(); 
			strArray[i] = myTaskOutputDTO.getTargetFacility(); 
			strArray[i] = myTaskOutputDTO.getItemName(); 
			strArray[i] = myTaskOutputDTO.getPosition(); 
			strArray[i] = myTaskOutputDTO.getRoleInCharge(); 
			strArray[i] = myTaskOutputDTO.getStaff(); 
			strArray[i] = myTaskOutputDTO.getPredictTaskLength().toString();
		}
		return strArray;
	}
	
	/**
	 * 判断是否是IE浏览器
	 * @param request
	 * @return
	 */
	public static boolean isMSBrowser(HttpServletRequest request) {
		String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};
		String userAgent = request.getHeader("User-Agent");
		for (String signal : IEBrowserSignals) {
			if (userAgent.contains(signal)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 导出csv文件
	 * @param request
	 * @param response
	 * @param dataList
	 * @param filePath
	 * @param fileName
	 * @return boolean
	 */
	public static String exportCsv(HttpServletRequest request, HttpServletResponse response, List<TaskListOutputDTO> dataList, String filePath, String fileName) {
		try{
			//可以指定某个路径创建
			File file = new File(filePath);
			if(!file.exists()) {
				file.mkdir();
			}
			File csvFile = File.createTempFile(fileName, ".csv", file);
			//也可以直接创建在AppData\Local\Temp临时文件
//			File csvFile = File.createTempFile(fileName, ".csv");
//			FileOutputStream out = new FileOutputStream(csvFile);
			CsvWriter csvWriter = new CsvWriter(csvFile.getCanonicalPath(), ',', Charset.forName("UTF-8"));
			//CsvWriter csvWriter = new CsvWriter(csvFile.getCanonicalPath(), ',', Charset.forName("UTF-8"));
			//写入表头信息
			String[] header = {"Id", "创建日期", "状态", "派工任务单", "目标设备", "项目名", "位置", "负责角色", "人员", "预估任务时长"};
			csvWriter.writeRecord(header);
			for (String[] str : listToString(dataList)) {
				csvWriter.writeRecord(str);
			}
			csvWriter.endRecord();
			//关闭写入的流
			csvWriter.close();
			System.out.println("csv文件导出成功");
			/*
			java.io.OutputStream out = response.getOutputStream();
            byte[] b = new byte[1024];
            java.io.File fileLoad = new java.io.File(csvFile.getCanonicalPath());
            response.reset();
            response.setContentType("application/csv");//设置文件ContentType类型，这样设置，下载时会自动判断文件类型
            String trueCSVName="我的任务清单.csv";
            //如果是IE浏览器，则用URFEncode解析
            if(isMSBrowser(request)){
            	trueCSVName = URLEncoder.encode(trueCSVName, "UTF-8");
            }else{//如果是谷歌、火狐则解析为ISO-8859-1
            	trueCSVName = new String(trueCSVName.getBytes("GBK"), "ISO-8859-1");//GBK UTF-8都可以
            }
            response.setHeader("Content-Disposition", "attachment;  filename="+ trueCSVName); 
            long fileLength = fileLoad.length();
            String length1 = String.valueOf(fileLength);
            response.setHeader("Content_Length", length1);
            java.io.FileInputStream in = new java.io.FileInputStream(fileLoad);

            out.write(new byte []{( byte ) 0xEF ,( byte ) 0xBB ,( byte ) 0xBF });//在写文件之前加上bom头，避免中文乱码
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n); // 每次写入out1024字节
            }
            in.close();
            out.close();
            */
            //下载完成后删除临时文件
            //csvFile.delete();
			System.out.println(csvFile.getName());
//			System.out.println(csvFile.getCanonicalPath());
			return csvFile.getName();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 将数据导出为CSV文件
	 * @param exportData 要导出的数据
	 * @param map 头部
	 * @param path 路径
	 * @param fileName 文件名
	 * @return
	 */
	public static boolean createCSVFile(List exportData, LinkedHashMap<String, String> map, String path, String fileName){
		File csvFile = null;
		BufferedWriter writer = null;
		try{
			File file = new File(path);
			if(!file.exists()){
				file.mkdir();
			}
			//定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv", file);
			System.out.println("csvFile:"+csvFile);
			//UTF-8使正确读取分隔符","
			//如果产生的文件乱码，windows下用GBK,linux下用UTF-8
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
			System.out.println("writer:"+writer);
			//写入前端字节流，防止乱码
			//			writer.write(getBOM());
			//写入文件头部
			for (Iterator it = map.entrySet().iterator(); it.hasNext();) {  
				Map.Entry header = (java.util.Map.Entry) it.next();  
				writer.write((String) header.getValue() != null ? (String) header.getValue() : "" );  
				if (it.hasNext()) {  
					writer.write(",");  
				}  
			}  
			//writer.write("\r\n");
			writer.newLine();
			//写入文件内容
			//实体数据写入
			for (Iterator it = exportData.iterator(); it.hasNext();) {  
				Object row = (Object) it.next();  
				for (Iterator headerIt = map.entrySet().iterator(); headerIt.hasNext();) {  
					Map.Entry header = (Entry) headerIt.next();  
					String str=row!=null?((String)((Map)row).get(header.getKey())):"";
					if(StringUtils.isEmpty(str)){
						str="";
					}else{
						str=str.replaceAll("\"","\"\"");
						if(str.indexOf(",")>=0){
							str="\""+str+"\"";
						}
					}
					writer.write(str);  
					if (headerIt.hasNext()) {  
						writer.write(",");  
					}  
				}  
				if (it.hasNext()) {  
					writer.newLine();  
				}  
			}  
			writer.flush();  
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 导出并在浏览器端下载csv文件
	 * @param response
	 * @param csvFilePath
	 * @param fileName
	 * @throws IOException 
	 */
	public static void exportCSVFile(HttpServletResponse response, String csvFilePath, String fileName) throws IOException {
		//		response.setHeader("Content-Type", "application-download");
		FileInputStream in = null;
		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		response.setContentType("text/csv;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ URLEncoder.encode(fileName, "UTF-8"));
		response.setCharacterEncoding("UTF-8");
		try {
			in = new FileInputStream(csvFilePath);
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("获取文件错误");
		} finally {
			if (in != null) {
				try {
					in.close();
					out.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static List<Map<String,Object>> listToMapObject(List<TaskListOutputDTO> dataList){
		List<Map<String, Object>> exportData = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (TaskListOutputDTO outputDTO : dataList) {
			Map<String, Object> rowData = new HashMap<>();
			rowData.put("TaskId", outputDTO.getTaskId().toString());
			rowData.put("CreateDateTime", sdf.format(outputDTO.getCreateDateTime()));
			rowData.put("State", outputDTO.getState());
			rowData.put("AssignmentTaskSheet", outputDTO.getAssignmentTaskSheet());
			rowData.put("TargetFacility", outputDTO.getTargetFacility());
			rowData.put("ItemName", outputDTO.getItemName());
			rowData.put("Position", outputDTO.getPosition());
			rowData.put("RoleInCharge", outputDTO.getRoleInCharge());
			rowData.put("Staff", outputDTO.getStaff());
			rowData.put("PredictTaskLength", outputDTO.getPredictTaskLength().toString());
			exportData.add(rowData);
		}
		return exportData;
	} 
	
	public static List<Map<String,String>> listToMap(List<TaskListOutputDTO> dataList){
		List<Map<String, String>> exportData = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (TaskListOutputDTO outputDTO : dataList) {
			Map<String, String> rowData = new HashMap<>();
			rowData.put("TaskId", outputDTO.getTaskId().toString());
			rowData.put("CreateDateTime", sdf.format(outputDTO.getCreateDateTime()));
			rowData.put("State", outputDTO.getState());
			rowData.put("AssignmentTaskSheet", outputDTO.getAssignmentTaskSheet());
			rowData.put("TargetFacility", outputDTO.getTargetFacility());
			rowData.put("ItemName", outputDTO.getItemName());
			rowData.put("Position", outputDTO.getPosition());
			rowData.put("RoleInCharge", outputDTO.getRoleInCharge());
			rowData.put("Staff", outputDTO.getStaff());
			rowData.put("PredictTaskLength", outputDTO.getPredictTaskLength().toString());
			exportData.add(rowData);
		}
		return exportData;
	}

	public static LinkedHashMap<String, String> getHeaders(){
		LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();
		headerMap.put("TaskId", "Id");
		headerMap.put("CreateDateTime", "创建日期");
		headerMap.put("State", "状态");
		headerMap.put("AssignmentTaskSheet", "派工任务单");
		headerMap.put("TargetFacility", "目标设备");
		headerMap.put("ItemName", "项目名");
		headerMap.put("Position", "位置");
		headerMap.put("RoleInCharge", "负责角色");
		headerMap.put("Staff", "人员");
		headerMap.put("PredictTaskLength", "预估任务时长");
		return headerMap;
	}


	public static List<String[]> listToString(List<TaskListOutputDTO> dataList){
		List<String[]> strList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (TaskListOutputDTO outputDTO : dataList) {
			String[] str = new String[10];
			str[0] = outputDTO.getTaskId().toString();
			str[1] = sdf.format(outputDTO.getCreateDateTime());
			str[2] = outputDTO.getState();
			str[3] = outputDTO.getAssignmentTaskSheet();
			str[4] = outputDTO.getTargetFacility();
			str[5] = outputDTO.getItemName();
			str[6] = outputDTO.getPosition();
			str[7] = outputDTO.getRoleInCharge();
			str[8] = outputDTO.getStaff();
			str[9] = outputDTO.getPredictTaskLength().toString();
			strList.add(str);			
		}
		return strList;
	}
	public static void main(String[] args) {

		List<MyTaskOutputDTO> dataList = new ArrayList<MyTaskOutputDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		MyTaskOutputDTO myTaskOutputDTO1;
		try {
			myTaskOutputDTO1 = new MyTaskOutputDTO(1, sdf.parse("2019-06-01 8:00:00"), "已分配", "PM001", "锡膏印刷机01", "点检", "喷嘴", "维护小组1", "张三", new BigDecimal(2.5));

			MyTaskOutputDTO myTaskOutputDTO2 = new MyTaskOutputDTO(2, sdf.parse("2019-06-05 8:00:00"), "已完成", "PM002", "锡膏印刷机02", "点检", "喷嘴", "维保小组2", "李四", new BigDecimal(3));

			dataList.add(myTaskOutputDTO1);
			dataList.add(myTaskOutputDTO2);
			/*
			String path = "C:\\Users\\smartflow\\Desktop\\我的任务清单";
			String fileName = "MyTaskList";
			createCSVFile(listToMap(dataList), getHeaders(), path, fileName);
			 */
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String filePath = "C:\\Users\\smartflow\\Desktop\\我的任务清单";
			//exportCsv(dataList, filePath, "MyTaskList_"+dateFormat.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}
}
