package com.zwxq.queryVo;

public class MessageInfoVo {

    private Integer page ; // 第几页
    private Integer rows; // 每页记录数
    private Integer start;  // 起始页

    public MessageInfoVo(Integer page, Integer rows) {
        super();
        this.page = page;
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getRows() {
        return rows;
    }
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    public Integer getStart() {
        return (page-1)*rows;
    }
}
