package cn.fastmc.core.utils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.cglib.beans.BeanMap;
import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.fastmc.core.DateConvert;
import cn.fastmc.core.EnumConverter;
import cn.fastmc.core.annotation.MetaData;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 常用函数管理类
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("rawtypes")
public class FunctionUtils {
	static {
        ConvertUtils.register(new DateConvert(), java.util.Date.class);
		ConvertUtils.register(new DateConvert(), java.sql.Date.class);
	}
	private static final List<String> ext_arr = Arrays.asList(new String[]{"doc","docx","ppt","xls","txt","pdf","mdb","jpg","gif","png","bmp","jpeg","rar","zip","swf","flv"});
	private static MessageDigest digest = null;
	public static final String JSON_ROOT = "rows";
	public static final String JSON_TOTAL = "total";
	public static final String SESSION_KEY = "session_key";
	public static final String FORMSUCCESS = "success";
	public static final String FORMDATA = "data";
	public static final String ICON_IMG = "icon";

	public static Date String2Date(String Strdate) {
		if (Strdate == null || Strdate.trim().length() == 0) {
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(Strdate.trim());
		} catch (Exception e) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(Strdate.trim());
			} catch (ParseException ex) {
				try {
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					return df.parse(Strdate.trim());
				} catch (Exception ex2) {
					return null;
				}
			}
		}
	}

	public static String encodeData2Json(Object datas)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		JSONObject json = new JSONObject();
		if (datas == null) {
			return "{" + FORMSUCCESS + ":true,data:{}}";
		}
		JSONObject rowData = encodeData2JsonObj(datas);
		json.put(FORMSUCCESS, "true");
		json.put(FORMDATA, rowData);
		return json.toString();
	}

	public static JSONObject encodeData2JsonObj(Object datas) {
		JSONObject rowData = new JSONObject();
		BeanMap map = BeanMap.create(datas);
		for (Object key : map.keySet()) {
			Object value = map.get(key);
			if (null == value) {
				value = "";
			}
			if (null != value && value instanceof Date) {
				value = formatDate2((Date) value);
			}
			rowData.put(key.toString(), value.toString());
		}
		return rowData;
	}
	
	public static JSONArray encodeListJson(List datas)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		JSONArray jsonData = new JSONArray();

		for (Object orig : datas) {
			JSONObject rowData = new JSONObject();
			if (orig instanceof Map) {
				Iterator names = ((Map) orig).keySet().iterator();
				while (names.hasNext()) {
					String name = (String) names.next();
					Object value = ((Map) orig).get(name);
					if (value == null)
						value = "";
					if (value instanceof Date) {
						value = formatDateTime((Date) value);
					}
					rowData.put(name, value);
				}
			} else {
				BeanMap map = BeanMap.create(orig);// 杩樻槸灏肩帥蹇�
				for (Object key : map.keySet()) {

					Object value = map.get(key);
				
					if (null == value) {
						value = "";
					}
					if (value instanceof Date) {
						value = formatDateTime((Date) value);
					}
					rowData.put(key.toString(), value.toString());
				}
			}
			jsonData.add(rowData);
		}
		return jsonData;

	}

	public static String encodeData2Json(List datas, long total)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException, SecurityException, NoSuchFieldException, OgnlException {
		JSONObject json = new JSONObject();
		JSONArray jsonData = new JSONArray();
		String[] listFiled = null;
		for (Object orig : datas) {
			JSONObject rowData = new JSONObject();
			if (orig instanceof Map) {
				Iterator names = ((Map) orig).keySet().iterator();
				while (names.hasNext()) {
					String name = (String) names.next();
					Object value = ((Map) orig).get(name);
					if (value == null)
						value = "";
					if (value instanceof Date) {
						value = formatDate2((Date) value);
					}
					rowData.put(name, value);
				}
			} else {
				if(null == listFiled){
					MetaData metaMata =  AnnotationUtils.findAnnotation(orig.getClass(), MetaData.class);
					if(null != metaMata)listFiled = metaMata.listField();
				}
				BeanMap map = BeanMap.create(orig);// 
				Collection keys = (listFiled==null || 0 == listFiled.length? map.keySet():Arrays.asList(listFiled));
				for (Object key : keys) {
					Object value = null;
					   if(StringUtils.indexOf((String)key, ".")>0){//ognl表达式情况
						   value = Ognl.getValue("#root."+(String)key, orig) ;
					   }else{
						   value =  map.get(key);  
					   }
						if ("_parentId".equals(key)) {
							if ("0".equals(value)) {
								continue;
							}
						}
						if (null == value) {
							value = "";
						}
						if (value instanceof Date) {
							value = formatDate2((Date) value);
						}
						if(value instanceof Double || double.class.isAssignableFrom(value.getClass())){
							DecimalFormat df = new DecimalFormat("###0.0#");
							value = df.format(value);
						}
						rowData.put(key.toString(), value.toString());
					}
					
			
			}
			jsonData.add(rowData);
		}
		json.put(JSON_ROOT, jsonData);
		json.put(JSON_TOTAL, total);

		return json.toString();

	}

	/**
	 * Formats a Date object to return a date using the global locale.
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
		return outFormat.format(date);
	}

	/**
	 * Formats a Date object to return a date and time using the global locale.
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return outFormat.format(date);
	}

	public static String formatDate2(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate3(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate4(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate5(Date myDate) {
		String strDate = getYear(myDate) + "-" + getMonth(myDate) + "-"
				+ getDay(myDate);
		return strDate;
	}

	public static String formatDate6(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate(Date myDate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static long Date2Long(int year, int month, int date) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date);
		return cld.getTime().getTime();
	}

	public static long Time2Long(int year, int month, int date, int hour,
			int minute, int second) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date, hour, minute, second);
		return cld.getTime().getTime();
	}

	public static int getYear(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MINUTE);
	}

	public static int getSecond(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.SECOND);
	}

	public static int getYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MINUTE);
	}

	public static int getSecond(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.SECOND);
	}

	public static int getYear() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static String getDayOfWeek() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		int w = cld.get(Calendar.DAY_OF_WEEK);
		if (2 == w) {
			return "星期一";
		} else if (3 == w) {
			return "星期二";
		} else if (4 == w) {
			return "星期三";
		} else if (5 == w) {
			return "星期四";
		} else if (6 == w) {
			return "星期五";
		} else if (7 == w) {
			return "星期六";
		} else {
			return "星期日";
		}
	}

	public static Class applicationClass(String className)
			throws ClassNotFoundException {
		return applicationClass(className, null);
	}

	public static Class applicationClass(String className,
			ClassLoader classLoader) throws ClassNotFoundException {
		if (classLoader == null) {
			classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = FunctionUtils.class.getClassLoader();
			}
		}
		return (classLoader.loadClass(className));
	}

	public static Object applicationInstance(String className)
			throws ClassNotFoundException, IllegalAccessException,
			InstantiationException {
		return applicationInstance(className, null);
	}

	public static Object applicationInstance(String className,
			ClassLoader classLoader) throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		return (applicationClass(className, classLoader).newInstance());
	}

	@SuppressWarnings("unchecked")
	public static void populateAndNull(Object bean,Map<String, String> properties) {
		try {
			if ((bean == null) || (properties == null)) {
				return;
			}
			//BeanMap map = BeanMap.create(bean);
			PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass()); 
			for (PropertyDescriptor pd : pds) {
				   if(properties.containsKey(pd.getName()) && StringUtils.isNotBlank(properties.get(pd.getName()))){
					    Method writeMethod = pd.getWriteMethod();
					    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
					    String value = properties.get(pd.getName());
					    Class clazz = PropertyUtils.getPropertyType(bean, pd.getName());
					    if ( clazz != null) {
							if (Collection.class.isAssignableFrom(clazz)) {// 参数为list时的情况
								writeMethod.invoke(bean, Arrays.asList(value.split(",")));
							} else if (Boolean.class.isAssignableFrom(clazz)) {
								writeMethod.invoke(bean, Boolean.parseBoolean(value));
							} else if (Date.class.isAssignableFrom(clazz)) {
								writeMethod.invoke(bean, FunctionUtils.String2Date(value));
							} else if (Integer.class.isAssignableFrom(clazz)) {
								writeMethod.invoke(bean, NumberUtils.toInt(value, 0));
							} else if (Double.class.isAssignableFrom(clazz)) {
								writeMethod.invoke(bean, NumberUtils.toDouble(value, 0));
							} else if (int.class.isAssignableFrom(clazz)) {
								writeMethod.invoke(bean, NumberUtils.toInt(value, 0));
							} else if (double.class.isAssignableFrom(clazz)) {
								writeMethod.invoke(bean, NumberUtils.toDouble(value, 0));
							} else if (long.class.isAssignableFrom(clazz)) {
								writeMethod.invoke(bean, NumberUtils.toLong(value, 0));
							} else if (Long.class.isAssignableFrom(clazz)) {
								writeMethod.invoke(bean, NumberUtils.toLong(value, 0));
							} else if(Enum.class.isAssignableFrom(clazz)){
								writeMethod.invoke(bean,  Enum.valueOf(clazz, value));
							}else {
								writeMethod.invoke(bean, value);
							}
				   }
				  }
			}
			/*
			Iterator entries = properties.entrySet().iterator();
			for (PropertyDescriptor targetPd : pds) { {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (name == null) {
					continue;
				}
				String value = (String) entry.getValue();
				
				Class clazz = PropertyUtils.getPropertyType(bean, name);
				if (StringUtils.isNotBlank(value) && clazz != null) {
					if (Collection.class.isAssignableFrom(clazz)) {// 参数为list时的情况
						map.put(name, Arrays.asList(value.split(",")));
					} else if (Boolean.class.isAssignableFrom(clazz)) {
						map.put(name, Boolean.parseBoolean(value));
					} else if (Date.class.isAssignableFrom(clazz)) {
						map.put(name, FunctionUtils.String2Date(value));
					} else if (Integer.class.isAssignableFrom(clazz)) {
						map.put(name, NumberUtils.toInt(value, 0));
					} else if (Double.class.isAssignableFrom(clazz)) {
						map.put(name, NumberUtils.toDouble(value, 0));
					} else if (int.class.isAssignableFrom(clazz)) {
						map.put(name, NumberUtils.toInt(value, 0));
					} else if (double.class.isAssignableFrom(clazz)) {
						map.put(name, NumberUtils.toDouble(value, 0));
					} else if (long.class.isAssignableFrom(clazz)) {
						map.put(name, NumberUtils.toLong(value, 0));
					} else if (Long.class.isAssignableFrom(clazz)) {
						map.put(name, NumberUtils.toLong(value, 0));
					} else {
						map.put(name, value);
					}
				}
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static Object findObjFormSpringContext(ServletContext sc,String beanId) {
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(sc);
		return wc.getBean(beanId);

	}

	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
		return false;
	}
	/**
	 * 文件重命名
	 * @param path
	 * @param oldname
	 * @param newname
	 */
	public static void renameFile(String path,String oldname,String newname){    
	       if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名    
	           File oldfile=new File(path+"/"+oldname);    
		          File newfile=new File(path+"/"+newname);    
		           if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名    
		               System.out.println(newname+"已经存在！");    
	           else{    
	               oldfile.renameTo(newfile);    
		          }    
		       }  
	}


	public static String getExtension(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return defExt;
	}

	public static String getPainFileName(String filename, String defName) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return filename.substring(0, i + 1);
			}
		}
		return defName;
	}

	public static String getUrlFileName(String url, String defName) {
		if ((url != null) && (url.length() > 0)) {
			int i = url.lastIndexOf('/');

			if ((i > 0) && (i < (url.length() - 1))) {
				return url.substring(i + 1);
			}
		}
		return defName;
	}

	public static File[] getDirFileList(String dir) {
		File file = new File(dir);
		File[] contents = null;
		if (file.exists()) {
			if (file.isDirectory()) {
				contents = file.listFiles();
			} else {
				contents = new File[] { file };
			}
		}
		return contents;
	}
	
	  public static String getFileIcon(String file ) {
  		String ext = getExtension(file,"do");
  			if("zip".equals(ext) || "rar".equals(ext)) ext = "rar";
  			else if( "doc".equals(ext) || "docx".equals(ext)) ext = "doc";
  			else if("xls".equals(ext) ||  "xlsx".equals(ext)) ext = "xls";
  			else if("ppt".equals(ext) ||  "pptx".equals(ext)) ext = "ppt";
  			else if ("flv".equals(ext) || "swf".equals(ext) || "rm".equals(ext) || "rmvb".equals(ext)) ext = "flv";
  		if(ext_arr.contains(ext.toLowerCase())) return "/resource/images/ext/"+ext+".gif";
  		else return "/resource/images/ext/blank.png";
  	}
	
	public static boolean upload(String uploadFileName, String savePath, File uploadFile) {
		boolean flag = false;
		try {
			uploadForName(uploadFileName, savePath, uploadFile);
			flag = true;
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	public static final int BUFFER_SIZE = 4096;
	public static String upload(String uploadFileName,String savePath,InputStream in)throws IOException{
		String newFileName = checkFileName(uploadFileName, savePath);
		FileOutputStream fos = null;
		try {
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			fos = new FileOutputStream(savePath +File.separator +newFileName);
			while ((bytesRead = in.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			fos.flush();
		    return newFileName;
		}
		finally {
			try {
				in.close();
			}
			catch (IOException ex) {
			}
			try {
				fos.close();
			}
			catch (IOException ex) {
				
			}
		}
	}

	public static String uploadForName(String uploadFileName, String savePath,
			File uploadFile) throws IOException {
		String newFileName = checkFileName(uploadFileName, savePath);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(savePath + newFileName);
			fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return newFileName;
	}

	public static boolean isFileExist(String fileName, String dir) {
		File files = new File(dir + fileName);
		return (files.exists()) ? true : false;
	}
	public static boolean isFileExist(String fullPath){
		File files = new File(fullPath);
		return (files.exists()) ? true : false;
	}
	  public static Map<String, String> getQueryMap(String query) {
			String[] params = query.split("&");
			Map<String, String> map = new HashMap<String, String>();
			for (String param : params) {
				String[] values = param.split("=");
				if("start".equals(values[0]) && values.length==1){
					map.put(param.split("=")[0], "0");
					continue;
				}
				if("limit".equals(values[0])&& values.length==1){
					map.put(param.split("=")[0], "20");
					continue;
				}
				String name = param.split("=")[0];
				String value = param.split("=")[1];
				map.put(name, value);
			}
			return map;
		}
	   

	public static String checkFileName(String fileName, String dir) {
		boolean isDirectory = new File(dir + fileName).isDirectory();
		if (FunctionUtils.isFileExist(fileName, dir)) {
			int index = fileName.lastIndexOf(".");
			StringBuffer newFileName = new StringBuffer();
			String name = isDirectory ? fileName : fileName.substring(0, index);
			String extendName = isDirectory ? "" : fileName.substring(index);
			int nameNum = 1;
			while (true) {
				newFileName.append(name).append("(").append(nameNum)
						.append(")");
				if (!isDirectory) {
					newFileName.append(extendName);
				}
				if (FunctionUtils.isFileExist(newFileName.toString(), dir)) {
					nameNum++;
					newFileName = new StringBuffer();
					continue;
				}
				return newFileName.toString();
			}
		}
		return fileName;
	}
	
	public static Object getPropertyToEmpty(Object bean,String name){
		try{
			Object obj = PropertyUtils.getNestedProperty(bean, name);
			if(null != obj  ){
				if(obj instanceof Date){
					obj = formatDate2((Date)obj);	
				}else if(obj instanceof Double || double.class.isAssignableFrom(obj.getClass())){
					DecimalFormat df = new DecimalFormat("###0.0#");
					obj = df.format(obj);
				}else{
					obj = String.valueOf(obj);	
				}
			}
			return obj;
		}catch(Exception ex){
			return null;
		}
	}
	
	public static String getUrlParamsByMap(Map<String, String> map) {  
	    if (map == null) {  
	        return "";  
	    }  
	    StringBuffer sb = new StringBuffer();  
	    for (Map.Entry<String, String> entry : map.entrySet()) {  
	        sb.append(entry.getKey() + "=" + entry.getValue());  
	        sb.append("&");  
	    }  
	    String s = sb.toString();  
	    if (s.endsWith("&")) {  
	        s = StringUtils.substringBeforeLast(s, "&");  
	    }  
	    return s;  
	}  

	/*
	 * MD5生成
	 */
	public synchronized static final String hash(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest. "
						+ "We will be unable to function normally.");
				nsae.printStackTrace();
			}
		}
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}
	
	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}
	
	

}
