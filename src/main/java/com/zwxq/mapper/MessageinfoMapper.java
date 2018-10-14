package com.zwxq.mapper;

import com.zwxq.bean.MessageInfo;
import com.zwxq.bean.MessageinfoExample;
import com.zwxq.queryVo.MessageInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageinfoMapper {
    int countByExample(MessageinfoExample example);

    int deleteByExample(MessageinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessageInfo record);

    int insertSelective(MessageInfo record);

    List<MessageInfo> selectByExample(MessageinfoExample example);

    MessageInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessageInfo record, @Param("example") MessageinfoExample example);

    int updateByExample(@Param("record") MessageInfo record, @Param("example") MessageinfoExample example);

    int updateByPrimaryKeySelective(MessageInfo record);

    int updateByPrimaryKey(MessageInfo record);

    public List<MessageInfo> findAllLimit(@Param("mesVo")MessageInfoVo mesVo, @Param("msg")String errmsg);
    public int totalCount();
}