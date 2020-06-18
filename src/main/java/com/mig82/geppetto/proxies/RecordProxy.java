package com.mig82.geppetto.proxies;

import com.konylabs.middleware.dataobject.Param;
import com.konylabs.middleware.dataobject.Record;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class RecordProxy {

	private final Record record;

	public RecordProxy(){
		record = new Record();
	}

	public RecordProxy(String id) {
		this();
		record.setId(id);
	}

	public RecordProxy(Record record){
		this.record = record;
	}

	public Record getRecord() {
		return record;
	}

	public RecordProxy addParam(ParamProxy param){
		String type = param.getParam().getType();
		String name = param.getParam().getName();
		String value = param.getParam().getValue();

		if(type.equals(ParamProxy.STRING)){
			addParam(name, value);
		}
		else if(type.equals(ParamProxy.BOOLEAN)){
			addParam(name, Boolean.parseBoolean(value));
		}
		else if(type.equals(ParamProxy.NUMBER)){

			if(value.contains(".")){
				addParam(name, Float.parseFloat(value));
			}
			else{
				addParam(name, Integer.parseInt(value));
			}
		}

		return this;
	}

	public RecordProxy addParam(String name, String value){
		record.addParam(new Param(name, value, ParamProxy.STRING));
		return this;
	}

	public RecordProxy addParam(String name, Boolean value){
		record.addParam(new Param(name, String.valueOf(value), ParamProxy.BOOLEAN));
		return this;
	}

	public RecordProxy addParam(String name, Integer value){
		record.addParam(new Param(name, String.valueOf(value), ParamProxy.NUMBER));
		return this;
	}

	public RecordProxy addParam(String name, Float value){
		record.addParam(new Param(name, String.valueOf(value), ParamProxy.NUMBER));
		return this;
	}

	public RecordProxy removeParam(String name){
		record.removeParamByName(name);
		return this;
	}

	public ParamProxy getParam(String name){
		return new ParamProxy(record.getParam(name));
	}

	public String getString(String name){
		return record.getParam(name) != null ? record.getParam(name).getValue() : null;
	}

	public Boolean getBoolean(String name){
		return record.getParam(name) != null ? Boolean.parseBoolean(record.getParam(name).getValue()) : null;
	}

	public Integer getInteger(String name){
		return record.getParam(name) != null ? Integer.parseInt(record.getParam(name).getValue()) : null;
	}

	public Float getFloat(String name){
		return record.getParam(name) != null ? Float.parseFloat(record.getParam(name).getValue()) : null;
	}
}
