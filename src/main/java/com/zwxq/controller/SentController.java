package com.zwxq.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.zwxq.bean.MessageInfo;
import com.zwxq.bean.ana.AnaDetail;
import com.zwxq.queryVo.MessageInfoVo;
import com.zwxq.service.MessageInfoServise;
import com.zwxq.utils.ExcelUtil;
import com.zwxq.utils.FileUtil;
import com.zwxq.utils.JacksonUtil;
import com.zwxq.utils.SentMassageTxUtil;

@Controller
@RequestMapping("/admin")
public class SentController {


    @Autowired
    private MessageInfoServise messageInfoServise;

    @Value("${tengxun.appid}")
    private int appid;
    
    @Value("${tengxun.appkey}")
    private String appkey;
    
  /*  @Value("${tengxun.smsSign}")
    private String smsSign;
    */
    @Value("${tengxun.templateId}")
    private int templateId;
    
    @Value("${tengxun.templateId.thanks}")
    private int templateIdthanks;
    
    
    @RequestMapping("/sentMessage")
    public String sentMessage(){

        return "sentMessage";
    }

    @RequestMapping("/pastMessage")
    public String pastMessage(){

        return "pastMessage";
    }
    
    @RequestMapping("/sentThankMessage")
    public String sentThankMessage(){
    	
    	return "sentThankMessage";
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String pastMessage(MultipartFile file,String message,String senter,String senterPhone){

        //下载文件 将文件保存到服务器上
            try {
                String fileName = file.getOriginalFilename();
        		// 获取文件的后缀名称
        		String fileNameExtention = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
                List<Map<String, Object>> exportListFromExcel = ExcelUtil.exportListFromExcel(file.getInputStream(),fileNameExtention, 0);
                int num=2;
                List<String> photoNums = new ArrayList<>();
                for (Map<String, Object> m:exportListFromExcel) {
                    //m.get("$A$"+(num++));
                    photoNums.add(m.get("$A$"+(num++)).toString());
                }
                //电话号码
                String[] photoNumStr = photoNums.toArray(new String[photoNums.size()]);
                //发送的消息
                String [] messages= {message,senter,senterPhone};
                //产生的结果
                SmsMultiSenderResult result = SentMassageTxUtil.massMessaging(photoNumStr, messages,appid,appkey,templateId,"宁大校园服务");
                //String result="{\"result\":0,\"errmsg\":\"OK\",\"ext\":\"\",\"detail\":[{\"result\":0,\"errmsg\":\"OK\",\"mobile\":\"18195555504\",\"nationcode\":\"86\",\"sid\":\"8:kLqAtxpbPxom4rSRMXK20181014\",\"fee\":1}]}";
                System.out.println(result);
                //读取返回的JSON数据
                //String r ="{\"result\":0,\"errmsg\":\"OK\",\"ext\":\"\",\"detail\":[{\"result\":0,\"errmsg\":\"OK\",\"mobile\":\"18141905504\",\"nationcode\":\"86\",\"sid\":\"8:l9y7di9eEQOF3IfbXOR20181012\",\"fee\":1},{\"result\":0,\"errmsg\":\"OK\",\"mobile\":\"18195555504\",\"nationcode\":\"86\",\"sid\":\"8:VDxXAZOomlFo97zDgZz20181012\",\"fee\":1}]}\n";
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(result.toString()); //
                JsonNode detailNode = rootNode.path("detail");
                //获取发送结果
                JsonNode errmsgNode = rootNode.path("errmsg");
                String errmsg = errmsgNode.textValue();
                Iterator<JsonNode> elements = detailNode.elements();
                //创建一个集合用于储存短信信息
                List<MessageInfo> messageList = new ArrayList<>();

                while(elements.hasNext()){
                    AnaDetail anaDetail = JacksonUtil.readValue(elements.next().toString(), AnaDetail.class);
                    MessageInfo messageInfo = new MessageInfo();
                    messageInfo.setResult(anaDetail.getResult());
                    messageInfo.setMobile(anaDetail.getMobile());
                    messageInfo.setErrmsg(anaDetail.getErrmsg());
                    messageList.add(messageInfo);
                }

                //保存数据到数据库
                messageInfoServise.insertMessage(messageList);

               // System.out.println(result);
                System.out.println("===========删除文件============");
                return "{\"errmsg\": \""+errmsg+"\"}";
                //return "{\"errmsg\": \"OK\"}";
            } catch (Exception e) {
                e.printStackTrace();
                return "{\"errmsg\": \"fasle\"}";
            }
    }
    
    @RequestMapping("/uploadThankFile")
    @ResponseBody
    public String pastThankMessage(MultipartFile file,String senter,String senterPhone){
    	
    	//下载文件 将文件保存到服务器上
    		try {
    			String fileName = file.getOriginalFilename();
        		// 获取文件的后缀名称
        		String fileNameExtention = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
                List<Map<String, Object>> exportListFromExcel = ExcelUtil.exportListFromExcel(file.getInputStream(),fileNameExtention, 0);
    			int num=2;
    			List<String> photoNums = new ArrayList<>();
    			for (Map<String, Object> m:exportListFromExcel) {
    				//m.get("$A$"+(num++));
    				photoNums.add(m.get("$A$"+(num++)).toString());
    			}
    			//电话号码
    			String[] photoNumStr = photoNums.toArray(new String[photoNums.size()]);
    			//发送的消息
    			String [] messages= {senter,senterPhone};
    			//产生的结果
    			SmsMultiSenderResult result = SentMassageTxUtil.massMessaging(photoNumStr, messages,appid,appkey,templateIdthanks,"百世汇通");
    			//String result="{\"result\":0,\"errmsg\":\"OK\",\"ext\":\"\",\"detail\":[{\"result\":0,\"errmsg\":\"OK\",\"mobile\":\"18195555504\",\"nationcode\":\"86\",\"sid\":\"8:kLqAtxpbPxom4rSRMXK20181014\",\"fee\":1}]}";
    			System.out.println(result);
    			//读取返回的JSON数据
    			//String r ="{\"result\":0,\"errmsg\":\"OK\",\"ext\":\"\",\"detail\":[{\"result\":0,\"errmsg\":\"OK\",\"mobile\":\"18141905504\",\"nationcode\":\"86\",\"sid\":\"8:l9y7di9eEQOF3IfbXOR20181012\",\"fee\":1},{\"result\":0,\"errmsg\":\"OK\",\"mobile\":\"18195555504\",\"nationcode\":\"86\",\"sid\":\"8:VDxXAZOomlFo97zDgZz20181012\",\"fee\":1}]}\n";
    			ObjectMapper mapper = new ObjectMapper();
    			JsonNode rootNode = mapper.readTree(result.toString()); //
    			JsonNode detailNode = rootNode.path("detail");
    			//获取发送结果
    			JsonNode errmsgNode = rootNode.path("errmsg");
    			String errmsg = errmsgNode.textValue();
    			Iterator<JsonNode> elements = detailNode.elements();
    			//创建一个集合用于储存短信信息
    			List<MessageInfo> messageList = new ArrayList<>();
    			
    			while(elements.hasNext()){
    				AnaDetail anaDetail = JacksonUtil.readValue(elements.next().toString(), AnaDetail.class);
    				MessageInfo messageInfo = new MessageInfo();
    				messageInfo.setResult(anaDetail.getResult());
    				messageInfo.setMobile(anaDetail.getMobile());
    				messageInfo.setErrmsg(anaDetail.getErrmsg());
    				messageList.add(messageInfo);
    			}
    			
    			//保存数据到数据库
    			messageInfoServise.insertMessage(messageList);
    			// System.out.println(result);
    			System.out.println("===========删除文件============");
    			return "{\"errmsg\": \""+errmsg+"\"}";
    			//return "{\"errmsg\": \"OK\"}";
    		} catch (Exception e) {
    			e.printStackTrace();
    			return "{\"errmsg\": \"fasle\"}";
    		}
    	}
    

    @RequestMapping("/messageInfo")
    @ResponseBody
    public String messageInfo(Integer page,Integer rows,String errmsg){
        MessageInfoVo mesVo = new MessageInfoVo(page,rows);
        List<MessageInfo> list = messageInfoServise.findall(mesVo,errmsg);
        int total=messageInfoServise.totalCount();
        String jSon = JacksonUtil.toJSon(list);
        return "{\"rows\": "+jSon+"," +"\"total\":"+total+"}";
    }





}
