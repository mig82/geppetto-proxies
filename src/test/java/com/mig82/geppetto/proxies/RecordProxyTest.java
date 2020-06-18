package com.mig82.geppetto.proxies;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RecordProxyTest {
	@BeforeMethod
	public void setUp() throws Exception {
	}

	@AfterMethod
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddStringParam() throws Exception {

		RecordProxy record = new RecordProxy().addParam("foo", "foo-1");
		assertEquals(record.getRecord().getParam("foo").getType(), ParamProxy.STRING);
		assertEquals(record.getRecord().getParam("foo").getValue(), "foo-1");
	}

	@Test
	public void testAddBooleanParam() throws Exception {

		Boolean myBool = true;
		RecordProxy record = new RecordProxy().addParam("foo", myBool);
		assertEquals(record.getRecord().getParam("foo").getType(), ParamProxy.BOOLEAN);
		assertEquals(record.getRecord().getParam("foo").getValue(), myBool.toString());
	}

	@Test
	public void testAddIntegerParam() throws Exception {

		Integer myInt = 999999999;
		RecordProxy record = new RecordProxy().addParam("foo", myInt);
		assertEquals(record.getRecord().getParam("foo").getType(), ParamProxy.NUMBER);
		assertEquals(record.getRecord().getParam("foo").getValue(), myInt.toString());
	}

	@Test
	public void testAddFloatParam() throws Exception {

		Float myFloat = 3.141516f;
		RecordProxy record = new RecordProxy().addParam("foo", myFloat);
		assertEquals(record.getRecord().getParam("foo").getType(), ParamProxy.NUMBER);
		assertEquals(record.getRecord().getParam("foo").getValue(), myFloat.toString());
	}

	@Test
	public void getUnexistingParam() throws Exception {

		RecordProxy record = new RecordProxy();

		assertNull(record.getString("foo"));
		assertNull(record.getFloat("foo"));
		assertNull(record.getInteger("foo"));
		assertNull(record.getBoolean("foo"));
	}

	@Test
	public void testRemoveParam() throws Exception {

		RecordProxy record = new RecordProxy().addParam("foo", "foo-1");
		record.removeParam("foo");
		assertNull(record.getRecord().getParam("foo"));
	}

	@Test
	public void testRemoveUnexistingParam() throws Exception {

		RecordProxy record = new RecordProxy();
		record.removeParam("foo");
		assertNull(record.getRecord().getParam("foo"));
	}

	@Test
	public void testPopParam() throws Exception {
		RecordProxy record = new RecordProxy()
				.addParam("foo", "foo-1");
		ParamProxy param = record.popParam("foo");

		assertEquals(param.getParam().getValue(), "foo-1");
		assertNull(record.getParam("foo").getParam());
	}
}
