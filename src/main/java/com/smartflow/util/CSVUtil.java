package com.smartflow.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;

import com.csvreader.CsvWriter;
import com.smartflow.dto.MyTaskOutputDTO;
import com.smartflow.dto.TaskListOutputDTO;

public class CSVUtil {
	public static void main(String[] args) {
		/*
		String filePath = "C:\\Users\\smartflow\\Desktop\\study.csv";
		CsvWriter csvWriter = new CsvWriter(filePath, ',', Charset.forName("GBK"));
		// 写表头
		String[] headers = {"编号", "姓名", "年龄"};
		String[] content = {"12365", "张山", "34"};
		try {
			csvWriter.writeRecord(headers);
			csvWriter.writeRecord(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		csvWriter.close();
		*/
//		CSVUtil.writeData(fileName, ExportCsvUtil.getHeaders(), ExportCsvUtil.listToMapObject(taskListOutputDTOList), response);
	}

	/**
	 * 导出.csv文件在浏览器端下载(可选择保存路径)
	 *
	 * @param fileName     文件名
	 * @param columnKeyMap 列名-属性,这里用LinkedHashMap是为了保证导出去的Excel列名顺序不会乱
	 * @param dataList     数据源
	 * @param response     HttpServletResponse
	 */
	public static void writeData(String fileName, LinkedHashMap<String, String> columnKeyMap, List<Map<String, Object>> dataList, HttpServletResponse response) {
		if (Objects.isNull(columnKeyMap) || columnKeyMap.isEmpty()) {
			throw new RuntimeException("Excel没有设置列名和对应的属性!");
		}

		if (CollectionUtils.isNotEmpty(dataList)) {
			//写入临时文件
			File tempFile = null;
			List<String> recordData = null;
			try {
				tempFile = File.createTempFile(fileName, ".csv");
				// 创建CSV写对象
				CsvWriter csvWriter = new CsvWriter(tempFile.getCanonicalPath(), ',', Charset.forName("UTF-8"));
				// 写入表头
				csvWriter.writeRecord(columnKeyMap.values().toArray(new String[columnKeyMap.values().size()]));

				//第一种写入方式
				/*for (Map<String,Object> data:dataList) {
                    for (String key:columnKeyMap.keySet()){
                        csvWriter.write(Objects.isNull(data.get(key)) ? "":data.get(key).toString());
                    }
                    csvWriter.endRecord();
                }*/

				//第二种写入方式
				for (Map<String, Object> data : dataList) {
					//先将每一行record的数据准备好,封装成一个String[]
					recordData = new ArrayList<>(data.size());
					for (String key : columnKeyMap.keySet()) {
						recordData.add(Objects.isNull(data.get(key)) ? "" : data.get(key).toString());
					}
					csvWriter.writeRecord(recordData.toArray(new String[recordData.size()]));
				}

				csvWriter.close();

				/**
				 * 写入csv结束，写出流,设置响应头直接在浏览器端下载
				 * 因为csvWriter不提供类似Poi的写入流workbook.write(out);
				 * 故在csv写入数据完之后,要手动把文件写入流；
				 */
				response.reset();
				response.setContentType("application/csv");
				response.setHeader("content-disposition", "attachment; filename=" + fileName + ".csv");
				File fileLoad = new File(tempFile.getCanonicalPath());
				String fileLength = String.valueOf(fileLoad.length());
				response.setHeader("Content_Length", fileLength);
				java.io.FileInputStream in = new java.io.FileInputStream(fileLoad);
				java.io.OutputStream out = response.getOutputStream();
				int eof;
				byte[] b = new byte[2048];
				while ((eof = in.read(b)) != -1) {
					out.write(b, 0, eof); //每次写入out 2048字节
				}
				in.close();
				out.close();

				//下载完成,删除临时文件
				tempFile.delete();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 除了可以查询出所有要的数据一次性导出外,
	 * 还可以边查边导,循环写入,只不过这种方式没法做到通用性,
	 * 对于数据量特别大的可以考虑
	 */
	public static void batchWriteData(String fileName, LinkedHashMap<String, String> columnKeyMap, HttpServletResponse response) {
		if (Objects.isNull(columnKeyMap) || columnKeyMap.isEmpty()) {
			throw new RuntimeException("Excel没有设置列名和对应的属性!");
		}
		//伪代码
		int totalRows = getList().size();
		if (totalRows > 0) {
			//写入临时文件
			File tempFile = null;
			List<String> recordData = null;
			try {
				tempFile = File.createTempFile(fileName, ".csv");
				// 创建CSV写对象
				CsvWriter csvWriter = new CsvWriter(tempFile.getCanonicalPath(), ',', Charset.forName("UTF-8"));
				// 写入表头
				csvWriter.writeRecord(columnKeyMap.values().toArray(new String[columnKeyMap.values().size()]));

				int loop = 0;
				int limit = 2000;//每次循环写入2000条数据
				while (totalRows > 0){
					int startRow = loop * limit;
					List<TaskListOutputDTO> taskListOutputDTOList = getList();
					/**
					 * 这里可以把list中的POJO转换成Map,因为我们并不总是需要所有属性,
					 * 还有可以需要对属性值做一些转换,如时间,金钱格式的转换,或者枚举价值的中文描述
					 */
					List<Map<String, String>> dataMap = ExportCsvUtil.listToMap(taskListOutputDTOList);
					for (Map<String,String> data:dataMap){
						//先将每一行record的数据准备好,封装成一个String[]
						recordData = new ArrayList<>(data.size());
						for (String key : columnKeyMap.keySet()) {
							recordData.add(Objects.isNull(data.get(key)) ? "" : data.get(key).toString());
						}
						csvWriter.writeRecord(recordData.toArray(new String[recordData.size()]));
					}

					loop++;
					totalRows = totalRows - limit;
				}

				csvWriter.close();

				/**
				 * 写入csv结束，写出流,设置响应头直接在浏览器端下载
				 * 因为csvWriter不提供类似Poi的写入流workbook.write(out);
				 * 故在csv写入数据完之后,要手动把文件写入流；
				 */
				response.reset();
				response.setContentType("application/csv");
				response.setHeader("content-disposition", "attachment; filename=" + fileName + ".csv");
				File fileLoad = new File(tempFile.getCanonicalPath());
				String fileLength = String.valueOf(fileLoad.length());
				response.setHeader("Content_Length", fileLength);
				java.io.FileInputStream in = new java.io.FileInputStream(fileLoad);
				java.io.OutputStream out = response.getOutputStream();
				int eof;
				byte[] b = new byte[2048];
				while ((eof = in.read(b)) != -1) {
					out.write(b, 0, eof); //每次写入out 2048字节
				}
				in.close();
				out.close();

				//下载完成,删除临时文件
				tempFile.delete();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	public static List<TaskListOutputDTO> getList() {
		List<TaskListOutputDTO> dataList = new ArrayList<TaskListOutputDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		TaskListOutputDTO myTaskOutputDTO1;
		try {
			myTaskOutputDTO1 = new TaskListOutputDTO(1, sdf.parse("2019-06-01 8:00:00"), "已分配", "PM001", "锡膏印刷机01", "点检", "喷嘴", "维护小组1", "张三", new BigDecimal(2.5));

			TaskListOutputDTO myTaskOutputDTO2 = new TaskListOutputDTO(2, sdf.parse("2019-06-05 8:00:00"), "已完成", "PM002", "锡膏印刷机02", "点检", "喷嘴", "维保小组2", "李四", new BigDecimal(3));

			dataList.add(myTaskOutputDTO1);
			dataList.add(myTaskOutputDTO2);
			return dataList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
