package com.zwxq.service.impl;

import com.zwxq.bean.MessageInfo;
import com.zwxq.mapper.MessageinfoMapper;
import com.zwxq.queryVo.MessageInfoVo;
import com.zwxq.service.MessageInfoServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageInfoServiceImpl implements MessageInfoServise{

    @Autowired
    private MessageinfoMapper messageinfoMapper;

    /**
     * 添加
     * @param messageList
     */
    @Override
    public void insertMessage(List<MessageInfo> messageList) {
        for (MessageInfo message:messageList) {
            messageinfoMapper.insert(message);
        }
    }

    @Override
    public int totalCount() {

        return messageinfoMapper.totalCount();
    }

    @Override
    public List<MessageInfo> findall(MessageInfoVo mesVo, String errmsg) {
        List<MessageInfo> list = messageinfoMapper.findAllLimit(mesVo,errmsg);
        return list;
    }
}
