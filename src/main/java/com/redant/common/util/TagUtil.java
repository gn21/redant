package com.redant.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录两个标签之间所耗的时间
 * @author gris.wang
 *
 */
public class TagUtil {
	
	private static Log logger = LogFactory.getLog(TagUtil.class);
	
	private static class GHandle{
		public static Map<String,Long> tags = Collections.synchronizedMap(new HashMap<String,Long>());
	}
	
	/**
	 * 新增标签点
	 * @param tag 标签名称
	 */
	public static void addTag(String tag){
		if(StringUtils.isEmpty(tag)){
			throw new RuntimeException("标签名称不可以为空");
		}
		GHandle.tags.put(tag, System.currentTimeMillis());
	}
	
	/**
	 * 计算开始标签和结束标签之间的耗时
	 * @param startTag 开始标签名称
	 * @param endTag 结束标签名称，如果为空，以当前调用代码所在行设置默认标签名称并计算耗时
	 */
	public static void showCost(String startTag,String endTag){
		if(StringUtils.isEmpty(startTag)){
			throw new RuntimeException("开始标签名称不可以为空");
		}
		if(StringUtils.isEmpty(endTag)){
			String tempTag= "cur_"+System.currentTimeMillis();
			addTag(tempTag);
			endTag=tempTag;
		}else if(!GHandle.tags.containsKey(endTag)){
			addTag(endTag);
		}
		Long start= GHandle.tags.get(startTag);
		Long end= GHandle.tags.get(endTag);
		if(start==null){
			throw new RuntimeException("获取标签["+startTag+"]信息失败!");
		}
		if(end==null){
			throw new RuntimeException("获取标签["+endTag+"]信息失败!");
		}
		long cost = end-start;
		logger.info("from ["+startTag+"] to ["+endTag+"] cost ["+cost+"ms]");
	}





}
