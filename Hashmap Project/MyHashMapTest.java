import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;

public class MyHashMapTest {
	
	private DefaultMap<String, String> testMap; // use this for basic tests
	private DefaultMap<String, String> mapWithCap; // use for testing proper rehashing
	public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
	
	@Before
	public void setUp() {
		testMap = new MyHashMap<>();
		mapWithCap = new MyHashMap<>(4, MyHashMap.DEFAULT_LOAD_FACTOR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPut_nullKey() {
		testMap.put(null, TEST_VAL);
	}

	@Test
	public void testKeys_nonEmptyMap() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(resultKeys);
		assertEquals(expectedKeys, resultKeys);
	}
	
	/* Add more of your tests below */
	@Test
	public void putTest() {
		List<String> expectedKeys = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			mapWithCap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		assertEquals(false, mapWithCap.put(TEST_KEY + 3, TEST_VAL));
		List<String> resultKeys = testMap.keys();
		assertEquals(expectedKeys, resultKeys);
	}
	
	@Test 
	public void replaceTest() {
		List<String> expectedKeys = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			mapWithCap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		assertEquals(true, mapWithCap.replace(TEST_KEY + 1, TEST_VAL + 5));
		assertEquals(false, mapWithCap.replace(TEST_KEY + 5, TEST_VAL + 5));
		List<String> resultKeys = testMap.keys();
		assertEquals(expectedKeys, resultKeys);
	}
	
	@Test
	public void removeTest() {
		List<String> expectedKeys = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			mapWithCap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		assertEquals(true, mapWithCap.remove(TEST_KEY + 4));
		assertEquals(false, mapWithCap.remove(TEST_KEY + 5));
		List<String> resultKeys = testMap.keys();
		assertEquals(expectedKeys, resultKeys);
	}
	
	@Test
	public void getTest() {
		List<String> expectedKeys = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			mapWithCap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		assertEquals(true, mapWithCap.get(TEST_KEY + 3));
		assertEquals(false, mapWithCap.get(TEST_KEY + 5));
		List<String> resultKeys = testMap.keys();
		assertEquals(expectedKeys, resultKeys);
	}
}
