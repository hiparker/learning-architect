/**
 * Copyright &copy; Edwin All rights reserved.
 */
package com.edwin.practive.springbootmvcframe.common.json;

import com.edwin.practive.springbootmvcframe.core.mapper.JsonMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.LinkedHashMap;


/**
 * $.ajax后需要接受的JSON
 * 
 * @author Edwin
 * 
 */
@ApiModel(value = "统一数据格式")
public class AjaxJson implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否成功", name = "success",dataType = "boolean")
	private boolean success = true;// 是否成功
    @ApiModelProperty(value = "错误代码", name = "errorCode",dataType = "String")
	private String errorCode = "-1";//错误代码
    @ApiModelProperty(value = "提示信息", name = "msg",dataType = "String")
	private String msg = "操作成功";// 提示信息
    @ApiModelProperty(value = "封装json的map", name = "body",dataType = "LinkedHashMap<String, Object>")
	private LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();//封装json的map

	public LinkedHashMap<String, Object> getBody() {
		return body;
	}

	public void setBody(LinkedHashMap<String, Object> body) {
		this.body = body;
	}

	public void put(String key, Object value){//向json中添加属性，在js中访问，请调用data.map.key
		body.put(key, value);
	}
	
	public void remove(String key){
		body.remove(key);
	}
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {//向json中添加属性，在js中访问，请调用data.msg
		this.msg = msg;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@JsonIgnore//返回对象时忽略此属性
	public String getJsonStr() {//返回json字符串数组，将访问msg和key的方式统一化，都使用data.key的方式直接访问。

		String json = JsonMapper.getInstance().toJson(this);
		return json;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
