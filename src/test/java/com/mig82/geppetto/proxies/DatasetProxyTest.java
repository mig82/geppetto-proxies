package com.mig82.geppetto.proxies;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DatasetProxyTest {

	DatasetProxy d;

	@BeforeMethod
	public void setUp() {

		d = new DatasetProxy("d1")
				.addRecord(new RecordProxy()
						.addParam("foo", "foo-1")
						.addParam("bar", 1.1f)
				)
				.addRecord(new RecordProxy()
						.addParam("foo", "foo-2")
						.addParam("bar", 1.2f)
				);
	}

	@AfterMethod
	public void tearDown(){
		d = null;
	}

	@Test
	public void testAddRecord() {

		d.addRecord(new RecordProxy()
				.addParam("foo", "foo-3")
				.addParam("bar", 1.3f)
		);

		assertEquals(d.getDataset().getAllRecords().size(), 3);
		assertEquals(d.getRecord(2).getFloat("bar"), 1.3f);
	}

	@Test
	public void testRemoveRecord() {
		d.removeRecord(0);
		assertEquals("record count: " + d.getDataset().getAllRecords().size(), "record count: " + 1);
		assertEquals(d.getRecord(0).getFloat("bar"), 1.2f);
	}
}
