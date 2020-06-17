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

	public ResultProxy addDataset(DatasetProxy dataset){
		result.addDataset(dataset.getDataSet());
		return this;
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

	public ResultProxy addOk(){
		addParam(new ParamProxy("httpStatusCode", 200));
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
			.addParam("opstatus", 10500)
			.addParam("httpStatusCode", 500)
			.addParam("message", e.getMessage())
			.addParam("class", e.getClass().getCanonicalName())
			.addParam("stack", ExceptionUtils.getStackTrace(e))
		);

		return this;
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
}
