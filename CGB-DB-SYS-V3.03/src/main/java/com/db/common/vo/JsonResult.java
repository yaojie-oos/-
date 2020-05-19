package com.db.common.vo;
/**
 * 借助此对象封装控制层数据
 * 1.业务层返回的数据
 * 2.状态码
 * 3.状态信息
 */
import java.io.Serializable;

public class JsonResult implements Serializable{
	/**信息的状态码：6000表示ok，0表示error*/
	private int state=6000;
	/**状态码对应的状态信息*/
	private String message="ok";
	/**正确的数据(输出到客户端)*/
	private Object data;
	public JsonResult() {}
	
	public JsonResult(String message) {
		this.message=message;
	}
	public JsonResult(Object data) {
		this.data=data;
	}
	/**通过此构造方法初始化错误信息*/
	public JsonResult(Throwable e) {
		this.state=0;
		this.message=e.getMessage();
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
