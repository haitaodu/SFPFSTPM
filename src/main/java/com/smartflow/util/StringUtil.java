package com.smartflow.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.mchange.v1.util.ArrayUtils;
import com.smartflow.model.UserModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean IsNotBlank(String str){
		if(str != null && !"".equals(str)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 修改日期格式
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDateTime(String datestr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");//注意格式化的表达式
		if (IsNotBlank(datestr)) {
			datestr = datestr.replace("Z", " UTC");//注意是空格+UTC
			Date date = sdf.parse(datestr);
			return date;
		}else{
			return null;
		}
	}
	/**
	 * 修改日期格式
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDate(String datestr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//注意格式化的表达式
		if (IsNotBlank(datestr)) {
			Date date = sdf.parse(datestr);
			return date;
		}else{
			return null;
		}
	}
	
	
	public static String parseEndDateToEndDateTime(String dateStr) throws ParseException{
		SimpleDateFormat dateSDF = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateTimeSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(IsNotBlank(dateStr)){
			Date date = dateSDF.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			Date dateTime = calendar.getTime();
			return dateTimeSDF.format(dateTime);
		}else{
			return null;
		}
	}

	/**
	 * 计算两个时间相差几小时
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static BigDecimal getHourApart(Date startDate,Date endDate){
//		long nd =  1000 * 24 * 60 * 60;//每天毫秒数
//		long nh = 1000 * 60 * 60;//每小时毫秒数
		long diff = endDate.getTime() - startDate.getTime();
		//long hour = diff % nd / nh; // 计算差多少小时
		double hour = diff/1000/60/60.0;//得到小数类型的小时
		DecimalFormat df = new DecimalFormat("##.#");
        BigDecimal dffHour=new BigDecimal(df.format(hour));
		return dffHour;
	}
	
	public static String formatDateFromYMDToYMDHMS(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d = sdf.parse(dateStr);
			Calendar calendar =Calendar.getInstance();
			calendar.setTime(d);
			System.out.println(calendar.getTime());
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static BigDecimal decimalFormatMinuteToHour(double workLengthMin){
		DecimalFormat df = new DecimalFormat("##.#");
		BigDecimal dffHour = new BigDecimal(df.format(workLengthMin));
		return dffHour;
	}
	
//	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
//		BigDecimal  hour = getHourApart(sdf.parse("2019-07-05 09:00:00"), sdf.parse("2019-07-05 10:40:00"));
//		System.out.println(hour);
//	}
	
	public static void getList(){
		UserModel user1 = new UserModel();//删除的
		user1.setId(1);
		user1.setUserName("张三");
		user1.setAccount("aaa");
		UserModel user2 = new UserModel();//修改的
		user2.setId(2);
		user2.setUserName("李四");
		user2.setAccount("bbb");
		UserModel user3 = new UserModel();
		user3.setId(3);
		user3.setUserName("王五");
		user3.setAccount("ccc");
		List<UserModel> userList1 = new ArrayList<>();
		userList1.add(user1);
		userList1.add(user2);	
		userList1.add(user3);
	
		UserModel user4 = new UserModel();
		user4.setId(2);
		user4.setUserName("李四02");
		user4.setAccount("bbb02");
		UserModel user5 = new UserModel();
		user5.setId(3);
		user5.setUserName("王五");
		user5.setAccount("ccc");
		UserModel user6 = new UserModel();//新增的
		user6.setId(4);
		user6.setUserName("赵六");
		user6.setAccount("ddd");
		List<UserModel> userList2 = new ArrayList<>();
		userList2.add(user4);
		userList2.add(user5);
		userList2.add(user6);
//		List<UserModel> remove = new ArrayList<>();
//		List<UserModel> add = new ArrayList<>();
		 List<UserModel> uList = userList2.stream().filter(u2 -> {
		        List<Boolean> result = userList1.stream().map(u1 -> u2.getId() == u1.getId()).collect(Collectors.toList());
		        if (result.indexOf(true) > -1)
		            return true;
		        return false;
		    }).collect(Collectors.toList());
		 uList.stream().forEach(System.out::println);
//		Collection A = new ArrayList<UserModel>(userList1);
//		Collection B = new ArrayList<UserModel>(userList2);
//		//求交集
//		A.retainAll(B);
//		System.out.println("交集结果："+A);
//		Set result = new HashSet<>();
//		//求全集
//		result.addAll(A);
//		result.addAll(B);
//		System.out.println("全集结果："+result);
//		//求差集：结果
//		Collection C = new ArrayList<UserModel>(result);
//		C.removeAll(A);
//		System.out.println("差集结果："+C);
		
//		for (UserModel u1 : userList1) {
//			for (UserModel u2 : userList2) {
//				if(u1.getId() == u2.getId()){
//					System.out.println("相等"+u1.getId());
//					u1.setUserName(u2.getUserName());
//					u1.setAccount(u2.getAccount());
//				}else{
//					System.out.println("id不相等:"+u1.getId()+"=="+u2.getId());
//					remove.add(u1);
//					add.add(u2);
//					break;
//				}
//			}
//		}
//		System.out.println("================");
//		userList1.removeAll(remove);
//		userList1.removeAll(add);
//		System.out.println(userList1.size());
//		for (UserModel list1 : userList1) {
//			System.out.println(userList1.size());
//			System.out.println(list1.toString());
//		}
	}
	public static class User{
		private Integer Id;
		private String UserName;
		private String Account;
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public String getUserName() {
			return UserName;
		}
		public void setUserName(String userName) {
			UserName = userName;
		}
		public String getAccount() {
			return Account;
		}
		public void setAccount(String account) {
			Account = account;
		}
		@Override
		public String toString() {
			return "User [Id=" + Id + ", UserName=" + UserName + ", Account=" + Account + "]";
		}
		
		
	}
	
	public static void addUserList(List<User> userList2){
		User user1 = new User();//删除的
		user1.setId(1);
		user1.setUserName("张三");
		user1.setAccount("aaa");
		User user2 = new User();//修改的
		user2.setId(2);
		user2.setUserName("李四");
		user2.setAccount("bbb");
		User user3 = new User();
		user3.setId(3);
		user3.setUserName("王五");
		user3.setAccount("ccc");
		List<User> userList1 = new ArrayList<>();
		userList1.add(user1);
		userList1.add(user2);
		userList1.add(user3);
		List<User> contain = new ArrayList<>();
		List<User> update = new ArrayList<>();
		List<User> add = new ArrayList<>();
		List<User> del = new ArrayList<>();
		for (User u2 : userList2) {
			for (User u1 : userList1) {
				if(u1.getId() == u2.getId()){
					System.out.println("包含");
					contain.add(u1);
					contain.add(u2);
					update.add(u2);
					break;
				}
			}
		}
		userList1.removeAll(contain);
		userList2.removeAll(contain);
		del.addAll(userList1);
		add.addAll(userList2);
		System.out.println("删除的"+del);
		System.out.println("新增的"+add);
		System.out.println("修改的"+update);
		userList1.removeAll(del);
		userList1.addAll(update);
		userList1.addAll(add);
		System.out.println(userList1);
	}


	public static void main(String[] args) throws ParseException {
		/**
		User user4 = new User();
		user4.setId(2);
		user4.setUserName("李四02");
		user4.setAccount("bbb02");
		User user5 = new User();
		user5.setId(3);
		user5.setUserName("王五");
		user5.setAccount("ccc");
		User user6 = new User();//新增的
		user6.setId(4);
		user6.setUserName("赵六");
		user6.setAccount("ddd");
		List<User> userList2 = new ArrayList<>();
		userList2.add(user4);
		userList2.add(user5);
		userList2.add(user6);
		
		addUserList(userList2);
		*/
		//System.out.println(parseEndDateToEndDateTime("2019-08-13"));
		String[] str1 = new String[]{"71-1", "71-2"};
		String[] str2 = new String[]{"71-2","71-3"};
		List<String> a = Arrays.asList(str1);
		List<String> b = Arrays.asList(str2);
		//并集
		Collection<String> union = CollectionUtils.union(a,b);
		//交集
		Collection<String> intersection = CollectionUtils.intersection(a, b);
		//交集的补集
		Collection<String> disjunction = CollectionUtils.disjunction(a, b);
		//集合相减
		Collection<String> subtract = CollectionUtils.subtract(a,b);

		Collections.sort((List<String>)union);
		Collections.sort((List<String>)intersection);
		Collections.sort((List<String>)disjunction);
		Collections.sort((List<String>)subtract);

		System.out.println(ArrayUtils.toString(a.toArray()));
		System.out.println(ArrayUtils.toString(b.toArray()));

		System.out.println("--------------------------------");
		System.out.println("并集"+ArrayUtils.toString(union.toArray()));
		System.out.println("交集"+ArrayUtils.toString(intersection.toArray()));
		System.out.println("交集的补集"+ArrayUtils.toString(disjunction.toArray()));
		System.out.println("集合相减"+ArrayUtils.toString(subtract.toArray()));
	}
}
