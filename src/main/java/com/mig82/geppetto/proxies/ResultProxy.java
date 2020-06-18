package com.mig82.geppetto.proxies;

import com.konylabs.middleware.dataobject.Result;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class ResultProxy {

	private final Result result;

	public ResultProxy() {
		result = new Result();
	}

	public Result getResult() {
		return result;
	}

	public ResultProxy addParam(ParamProxy param){
		result.addParam(param.getParam());
		return this;
	}

	public ResultProxy addRecord(RecordProxy record){
		result.addRecord(record.getRecord());
		return this;
	}

	public RecordProxy getRecord(String name){
		return new RecordProxy(result.getRecordById(name));
	}

	public ResultProxy addDataset(DatasetProxy dataset){
		result.addDataset(dataset.getDataset());
		return this;
	}

	public DatasetProxy getDataset(String name){
		return new DatasetProxy(result.getDatasetById(name));
	}

	public ResultProxy addParam(String name, String value){
		addParam(new ParamProxy(name, value));
		return this;
	}

	public ResultProxy addParam(String name, Boolean value){
		addParam(new ParamProxy(name, value));
		return this;
	}

	public ResultProxy addParam(String name, Integer value){
		addParam(new ParamProxy(name, value));
		return this;
	}

	public ResultProxy addParam(String name, Float value){
		addParam(new ParamProxy(name, value));
		return this;
	}

	public ParamProxy getParam(String name){
		return new ParamProxy(result.getParamByName(name));
	}

	public String getString(String name){
		return result.getParamByName(name) != null ? result.getParamByName(name).getValue() : null;
	}

	public Boolean getBoolean(String name){
		return result.getParamByName(name) != null ? Boolean.parseBoolean(result.getParamByName(name).getValue()) : null;
	}

	public Integer getInteger(String name){
		return result.getParamByName(name) != null ? Integer.parseInt(result.getParamByName(name).getValue()) : null;
	}

	public Float getFloat(String name){
		return result.getParamByName(name) != null ? Float.parseFloat(result.getParamByName(name).getValue()) : null;
	}

	public ResultProxy removeParam(String name){
		result.removeParamByName(name);
		return this;
	}

	public ResultProxy removeRecord(String name){
		result.removeRecordById(name);
		return this;
	}

	public ResultProxy removeDataset(String name){
		result.removeDatasetById(name);
		return this;
	}

	public ResultProxy addHttpStatusCode(String code){
		addParam(new ParamProxy("httpStatusCode", code));
		return this;
	}

	public ResultProxy addHttpOk(){
		addHttpStatusCode("200");
		return this;
	}

	public ResultProxy addParamToRecord(String recordName, ParamProxy param){
		if(result.getRecordById(recordName) == null){
			addRecord(new RecordProxy(recordName));
		}
		getRecord(recordName).addParam(param);
		return this;
	}
	/**
	 * Adds a record to a dataset by a given name. If no dataset exists by this name, a new dataset is created
	 * and the record is added to it.
	 * */
	public ResultProxy addRecordToDataset(String datasetName, RecordProxy record){

		if(result.getDatasetById(datasetName) == null){
			addDataset(new DatasetProxy(datasetName));
		}
		result.getDatasetById(datasetName)
			.addRecord(record.getRecord());

		return this;
	}

	public ResultProxy addException(Exception e){

		addRecordToDataset("exceptions", new RecordProxy()
			.addParam("opstatus", "10500")
			.addParam("httpStatusCode", "500")
			.addParam("message", e.getMessage())
			.addParam("class", e.getClass().getCanonicalName())
			.addParam("stack", ExceptionUtils.getStackTrace(e))
		);

		return this;
	}

	public DatasetProxy getExceptions() {
		return getDataset("exceptions");
	}

	public ResultProxy addDebugStuff(MapProxy cfgMap, MapProxy inMap, MapProxy outMap, MapProxy headers){

		if(inMap.getBoolean("debug", false)){

			addRecord(new RecordProxy("debug")
				.addParam("cfgMap", cfgMap.toString())
				.addParam("inMap", cfgMap.toString())
				.addParam("outMap", cfgMap.toString())
				.addParam("headers", headers.toString())
			);
		}
		return this;
	}

	public ParamProxy popParam(String name){
		ParamProxy param = getParam(name);
		removeParam(name);
		return param;
	}
}
