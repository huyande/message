package com.zwxq.service;

import com.zwxq.bean.MessageInfo;
import java.util.List;

import com.zwxq.queryVo.MessageInfoVo;

public interface MessageInfoServise {

    public void insertMessage(List<MessageInfo> messageList);
    public List<MessageInfo> findall(MessageInfoVo mesVo, String errmsg);
    public int totalCount();


}
