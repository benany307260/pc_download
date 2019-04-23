package com.montnets.pc_service.util;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成自增ID
 * 
 * @功能概要：
 * @项目名称： mwytx
 * @初创作者： chentingsheng <cts314@163.com>
 * @公司名称： ShenZhen Montnets Technology CO.,LTD.
 * @创建时间： 2016年12月14日 下午5:01:22
 *        <p>
 *        修改记录1：
 *        </p>
 * 
 *        <pre>
 *      修改日期：
 *      修改人：
 *      修改内容：
 * </pre>
 */
public class GetIncrementId
{
	private static GetIncrementId	single		= new GetIncrementId();
	
	private GetIncrementId()
	{
	}

	private static Logger log = LoggerFactory.getLogger(GetIncrementId.class);
	
	private static Integer			count		= 0;
	//区域节点
	//private static int				areaNode	= Integer.parseInt(StaticValue.MWYTX_PTCODE); // StaticValue.MWYTX_PTCODE
	//服务器节点
	//private static int				serverNode	= Integer.parseInt(StaticValue.SERVER_NUMBER.substring(2));

	public static GetIncrementId getInstance()
	{
		return single;
	}

	synchronized public long getCount()
	{
		return getCount(1, 1);
	}
	
	/**
	 * 生成自增ID
	 * 
	 * @description
	 * @return
	 * @author chentingsheng <cts314@163.com>
	 * @datetime 2016年12月14日 下午5:10:34
	 */
	synchronized public long getCount(int serverNode, int areaNode)
	{
		try
		{
			Calendar current = Calendar.getInstance();
			// 取后两位
			int year = Integer.parseInt(String.valueOf(current.get(Calendar.YEAR)).substring(2));
			int month = current.get(Calendar.MONTH) + 1;
			int day = current.get(Calendar.DATE);
			int hour = current.get(Calendar.HOUR_OF_DAY);
			int minute = current.get(Calendar.MINUTE);
			int second = current.get(Calendar.SECOND);
			if(count == 65530)
			{
				count = 0;
			}
			count++;
			long messageId = 0;
			messageId |= (long) year << 56L;
			messageId |= (long) month << 52L;
			messageId |= (long) day << 47L;
			messageId |= (long) hour << 42L;
			messageId |= (long) minute << 36L;
			messageId |= (long) second << 30L;
			messageId |= (long) areaNode << 23L;
			messageId |= (long) serverNode << 16L;
			messageId |= (long) count;
			return messageId;
		}
		catch (Exception e)
		{
			log.error("生成自增ID异常。", e);
			return 0;
		}
	}
}
