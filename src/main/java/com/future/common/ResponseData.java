package com.future.common;
/**
 * @description  返回返回消息码且带有数据
 * @author hely
 * @date 2018/4/13
 * @param
 */
public class ResponseData extends Response{
    private Object data;

    public ResponseData(Object data) {
        this.data = data;
    }
    
    public ResponseData(ResultMsg msg) {
    	  super(msg);
    }
    
    public ResponseData(String rspCode, String rspMsg) {
        super(rspCode, rspMsg);
    }

    public ResponseData(String rspCode, String rspMsg, Object data) {
        super(rspCode, rspMsg);
        this.data = data;
    }

    public ResponseData(ResultMsg msg, Object data) {
        super(msg);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "data=" + data +
                "} " + super.toString();
    }
}
