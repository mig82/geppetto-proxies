package com.mig82.geppetto.proxies;

import com.konylabs.middleware.dataobject.Param;

public class ParamProxy {

	public static final String STRING = "string";
	public static final String NUMBER = "number";
	public static final String BOOLEAN = "boolean";

	private final Param param;

	public ParamProxy(){
		param = new Param();
	}

	public ParamProxy(String name, String value){
		param = new Param(name, value, STRING);
	}

	public ParamProxy(String name, Boolean value){
		param = new Param(name, String.valueOf(value).toLowerCase(), BOOLEAN);
	}

	public ParamProxy(String name, Integer value){
		param = new Param(name, String.valueOf(value), NUMBER);
	}

	public ParamProxy(String name, Float value){
		param = new Param(name, String.valueOf(value), NUMBER);
	}

	public ParamProxy setName(String name){
		param.setName(name);
		return this;
	}

	public ParamProxy setValue(String value){
		param.setType(STRING);
		param.setValue(value);
		return this;
	}

	public ParamProxy setValue(Boolean value){
		param.setType(BOOLEAN);
		param.setValue(String.valueOf(value).toLowerCase());
		return this;
	}

	public ParamProxy setValue(Integer value){
		param.setType(NUMBER);
		param.setValue(String.valueOf(value));
		return this;
	}

	public ParamProxy setValue(Float value){
		param.setType(NUMBER);
		param.setValue(String.valueOf(value));
		return this;
	}

	public Param getParam(){
		return param;
	}

}
