package com.yc.utils;

public class JsonProtocol
{
	private int responseCode; // 响应码,0成功, 1失败
	private String errorMessage; // 失败信息
	private Object obj;// 响应信息

	public JsonProtocol()
	{
	}

	public JsonProtocol(int responseCode, String errorMessage, Object obj)
	{
		this.responseCode = responseCode;
		this.errorMessage = errorMessage;
		this.obj = obj;
	}

	public int getResponseCode()
	{
		return responseCode;
	}

	public void setResponseCode(int responseCode)
	{
		this.responseCode = responseCode;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public Object getObj()
	{
		return obj;
	}

	public void setObj(Object obj)
	{
		this.obj = obj;
	}

}
