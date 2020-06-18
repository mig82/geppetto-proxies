package com.mig82.geppetto.proxies;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ResultProxyTest {

	@Test
	public void testAddStringParam(){

		ResultProxy result = new ResultProxy()
				.addParam("foo", "foo-1");

		assertEquals(result.getResult().getParamByName("foo").getType(), ParamProxy.STRING);
		assertEquals(result.getResult().getParamByName("foo").getValue(), "foo-1");
		assertEquals(result.getString("foo"), "foo-1");
	}

	@Test
	public void testAddIntParam(){

		ResultProxy result = new ResultProxy()
			.addParam("foo", 123);

		assertEquals(result.getResult().getParamByName("foo").getType(), ParamProxy.NUMBER);
		assertEquals(Integer.parseInt(result.getResult().getParamByName("foo").getValue()), 123);
		assertEquals(result.getInteger("foo"), new Integer(123));
	}

	@Test
	public void testAddFloatParam(){

		ResultProxy result = new ResultProxy()
				.addParam("foo", 1.23f);

		assertEquals(result.getResult().getParamByName("foo").getType(), ParamProxy.NUMBER);
		assertEquals(Float.parseFloat(result.getResult().getParamByName("foo").getValue()), 1.23f);
		assertEquals(result.getFloat("foo"), new Float(1.23f));
	}

	@Test
	public void testAddBooleanParam(){

		ResultProxy result = new ResultProxy()
				.addParam("foo", true);

		assertEquals(result.getResult().getParamByName("foo").getType(), ParamProxy.BOOLEAN);
		assertEquals(Boolean.parseBoolean(result.getResult().getParamByName("foo").getValue()), true);
		assertEquals(result.getBoolean("foo"), new Boolean(true));
	}

	@Test
	public void testRemoveParam(){
		ResultProxy result = new ResultProxy()
				.addParam("foo", "foo-1")
				.removeParam("foo");

		assertNull(result.getResult().getParamByName("foo"));
	}

	@Test
	public void testAddRecord(){
		ResultProxy result = new ResultProxy()
			.addRecord(new RecordProxy("r1")
				.addParam("foo", "foo-1")
				.addParam("bar", 1.23f)
			);
		assertEquals(result.getResult().getRecordById("r1").getParam("foo").getValue(), "foo-1");
		assertEquals(Float.parseFloat(result.getResult().getRecordById("r1").getParam("bar").getValue()), 1.23f);
	}

	@Test
	public void getUnexistingParam() throws Exception {

		ResultProxy result = new ResultProxy();

		assertNull(result.getString("foo"));
		assertNull(result.getFloat("foo"));
		assertNull(result.getInteger("foo"));
		assertNull(result.getBoolean("foo"));
	}

	@Test
	public void testRemoveRecord(){
		ResultProxy result = new ResultProxy()
				.addRecord(new RecordProxy("r1")
						.addParam("foo", "foo-1")
						.addParam("bar", 1.23f)
				)
				.removeRecord("r1");
		assertNull(result.getResult().getRecordById("r1"));
		assertNull(result.getRecord("r1").getRecord());
	}

	@Test
	public void testAddDataset(){
		ResultProxy result = new ResultProxy()
			.addDataset(new DatasetProxy("d1")
				.addRecord(new RecordProxy()
						.addParam("foo", "foo-1")
						.addParam("bar", 1.13f)
				)
				.addRecord(new RecordProxy()
						.addParam("foo", "foo-2")
						.addParam("bar", 1.23f)
				)
			);
		assertEquals(result.getResult().getDatasetById("d1").getAllRecords().get(1).getParam("foo").getValue(), "foo-2");
	}

	@Test
	public void testRemoveDataset(){
		ResultProxy result = new ResultProxy()
				.addDataset(new DatasetProxy("d1")
						.addRecord(new RecordProxy()
								.addParam("foo", "foo-1")
								.addParam("bar", 1.13f)
						)
						.addRecord(new RecordProxy()
								.addParam("foo", "foo-2")
								.addParam("bar", 1.23f)
						)
				)
				.removeDataset("d1");
		assertNull(result.getResult().getDatasetById("d1"));
		assertNull(result.getDataset("d1").getDataset());
	}

	@Test
	public void testAddParamToRecord(){

		ResultProxy result = new ResultProxy()
				.addParamToRecord("new", new ParamProxy("foo", "foo-1"));

		assertNotNull(result.getResult().getRecordById("new"));
	}

	@Test
	public void testAddRecordToDataset(){

		ResultProxy result = new ResultProxy()
			.addRecordToDataset("new", new RecordProxy()
					.addParam("foo", "foo-1")
			);

		assertNotNull(result.getResult().getDatasetById("new"));
	}

	@Test
	public void testAddException(){

		ResultProxy result = new ResultProxy();

		try{
			//Throw a first exception.
			throw new Exception("test exception 1");
		}
		catch (Exception e){

			//Add the first exception.
			result.addException(e);

			try {
				//Throw a second exception.
				throw new Exception("test exception 2");
			}
			catch (Exception e1) {
				//Add the second exception.
				result.addException(e1);
			}
		}
		finally {
			assertEquals(result.getExceptions().size(), 2);
			assertEquals(result.getExceptions().getRecord(1).getString("opstatus"), "10500");
			assertEquals(result.getExceptions().getRecord(1).getString("httpStatusCode"), "500");
			assertEquals(result.getExceptions().getRecord(1).getString("message"), "test exception 2");
			assertNotNull(result.getExceptions().getRecord(1).getString("class"));
			assertNotNull(result.getExceptions().getRecord(1).getString("stack"));
		}
	}

	@Test
	public void testPopParam() throws Exception {
		ResultProxy result = new ResultProxy()
				.addParam("foo", "foo-1");
		ParamProxy param = result.popParam("foo");

		assertEquals(param.getParam().getValue(), "foo-1");
		assertNull(result.getParam("foo").getParam());
	}
}
