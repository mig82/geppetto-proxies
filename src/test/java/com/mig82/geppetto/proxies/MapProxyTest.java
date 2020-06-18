package com.mig82.geppetto.proxies;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class MapProxyTest {

	@Test
	public void testContainsKey() throws Exception {
		MapProxy map = new MapProxy();
		map.put("LoRemIpSum", "foo-1");
		assertEquals(map.getString("LOREMIPSUM"), "foo-1");
		assertEquals(map.getString("loremipsum"), "foo-1");
	}

	@Test
	public void testContainsUnexistingKey() throws Exception {
		MapProxy map = new MapProxy();
		assertNull(map.getString("foo"));
	}

	@Test
	public void testGetRealKey() throws Exception {
		MapProxy map = new MapProxy();
		map.put("LoRemIpSum", "foo-1");
		assertEquals(map.getRealKey("LOREMIPSUM"), "LoRemIpSum");
		assertEquals(map.getRealKey("loremipsum"), "LoRemIpSum");
	}

	@Test
	public void testGetString() throws Exception {
		MapProxy map = new MapProxy();
		map.put("foO", "foo-1");
		assertEquals(map.getString("foo"), "foo-1");
	}

	@Test
	public void testGetStringOrDefault() throws Exception {
		MapProxy map = new MapProxy();
		assertEquals(map.getString("foo", "foo-1"), "foo-1");
	}

	@Test
	public void testGetBoolean() throws Exception {
		MapProxy map = new MapProxy();
		map.put("foO", true);
		map.put("bAr", false);
		assertTrue(map.getBoolean("foo"));
		assertFalse(map.getBoolean("bar"));
	}

	@Test
	public void testGetBooleanOrDefault() throws Exception {
		MapProxy map = new MapProxy();
		assertTrue(map.getBoolean("foo", true));
		assertFalse(map.getBoolean("bar", false));
	}

	@Test
	public void testGetInteger() throws Exception {
		MapProxy map = new MapProxy();
		map.put("foO", 12345);
		assertEquals(map.getInteger("foo"), new Integer(12345));
	}

	@Test
	public void testGetIntegerOrDefault() throws Exception {
		MapProxy map = new MapProxy();
		assertEquals(map.getInteger("foo", -1), new Integer(-1));
	}

	@Test
	public void testGetFloat() throws Exception {
		MapProxy map = new MapProxy();
		map.put("foO", 1.2345f);
		assertEquals(map.getFloat("foo"), new Float(1.2345f));
	}

	@Test
	public void testGetFloatOrDefault() throws Exception {
		MapProxy map = new MapProxy();
		assertEquals(map.getFloat("foo", 1.2345f), new Float(1.2345f));
	}

	@Test
	public void testParseMapFromJson() throws Exception {

		MapProxy map = new MapProxy();
		map.put("nestedObj", "{'foo': 123, 'bar': 'bar-1'}");

		Map<String, Object> nestedObj = map.parseMapFromJson("nestedObj");
		assertEquals((Double) (nestedObj.get("foo")), 123.0);
		assertEquals((String)(map.parseMapFromJson("nestedObj").get("bar")), "bar-1");
	}

	@Test
	public void testParseStringCollectionFromJson() throws Exception {
		MapProxy map = new MapProxy();
		map.put("nestedArray", "['foo', 'bar', 'qux']");

		Collection<String> nestedArray = map.parseStringCollectionFromJson("nestedArray");
		assertEquals(nestedArray.size(), 3);
		assertEquals(nestedArray.toArray()[0], "foo");
		assertEquals(nestedArray.toArray()[1], "bar");
		assertEquals(nestedArray.toArray()[2], "qux");
	}

	@Test
	public void testParseIntCollectionFromJson() throws Exception {
		MapProxy map = new MapProxy();
		map.put("nestedArray", "[7, 999999999, -999999999]");

		Collection<Integer> nestedArray = map.parseIntCollectionFromJson("nestedArray");
		assertEquals(nestedArray.size(), 3);
		assertEquals(nestedArray.toArray()[0], 7);
		assertEquals(nestedArray.toArray()[1], 999999999);
		assertEquals(nestedArray.toArray()[2], -999999999);
	}

	@Test
	public void testParseMapCollectionFromJson() throws Exception {

		MapProxy map = new MapProxy();
		map.put("nestedArray", "[{'a':7, 'b': 'foo'}, {'c':-3}]");

		Map<String, Object>[] nestedArray = new Map[2];
		nestedArray = map.parseMapCollectionFromJson("nestedArray").toArray(nestedArray);

		assertEquals(nestedArray.length, 2);
		assertEquals(nestedArray[0].get("a"), 7.0);
		assertEquals(nestedArray[0].get("b"), "foo");
		assertEquals(nestedArray[1].get("c"), -3.0);
	}

	@Test(enabled = false)
	public void testGetClassName() throws Exception {
	}

	@Test(enabled = false)
	public void testToString() throws Exception {
	}
}
