package com.mig82.geppetto.proxies;

import com.konylabs.middleware.dataobject.Dataset;
import com.konylabs.middleware.dataobject.Param;
import com.konylabs.middleware.dataobject.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatasetProxy {

	private final Dataset dataset;

	public DatasetProxy(String id) {
		dataset = new Dataset();
		dataset.setId(id);
	}

	public DatasetProxy(Dataset dataset) {
		this.dataset = dataset;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public DatasetProxy addRecord(RecordProxy record){
		dataset.addRecord(
			record.getRecord()
		);
		return this;
	}

	public RecordProxy getRecord(int index){
		return new RecordProxy(dataset.getAllRecords().get(index));
	}

	public DatasetProxy removeRecord(int index){
		dataset.getRecords().remove(index);
		return this;
	}

	public static DatasetProxy getDataset(ResultProxy result, String id){
		return new DatasetProxy(
				result.getResult().getDatasetById(id)
		);
	}

	public int size(){
		return dataset.getAllRecords().size();
	}
}
