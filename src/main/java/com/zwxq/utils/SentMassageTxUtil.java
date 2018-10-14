package com.zwxq.utils;

import java.io.IOException;

import org.json.JSONException;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

public class SentMassageTxUtil {

	/*private static int appid = 1400147984; // 1400开头
	private static  String appkey = "6ba8c8727d2a179abb4ae49fd47f99c3";
	private static int templateId = 209956; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
	private static String smsSign = "百世汇通";*/
	
	/**
	 * 群发
	 * @param phoneNumbers
	 * @return
	 */
	public static SmsMultiSenderResult massMessaging(String[] phoneNumbers,String[] messages,int appid,String appkey,int templateId,String smsSign){
		try {
			//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
		    SmsMultiSender msender = new SmsMultiSender(appid, appkey);
		    SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
		        templateId, messages, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
		    return result;
		} catch (HTTPException e) {
		    // HTTP响应码错误
		    e.printStackTrace();
		    return null;
		} catch (JSONException e) {
		    // json解析错误
		    e.printStackTrace();
		    return null;
		} catch (IOException e) {
		    // 网络IO错误
		    e.printStackTrace();
		    return null;
		}
	}
	
	/**
	 * 单条发
	 */
	
	public static SmsSingleSenderResult singleMessaging(String phoneNumber,int appid,String appkey,int templateId,String smsSign){
		try {
		    String[] params = {"5678"};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		    SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
		        templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
		    return result;
		} catch (HTTPException e) {
		    // HTTP响应码错误
		    e.printStackTrace();
		    return null;
		} catch (JSONException e) {
		    // json解析错误
		    e.printStackTrace();
		    return null;
		} catch (IOException e) {
		    // 网络IO错误
		    e.printStackTrace();
		    return null;
		}
	}
}
