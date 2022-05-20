package opennms.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

class SNMPTrapFilterTest {

	SNMPTrapFilter filter;
	
	@Test
	void testYamlFileNotFoundThrowsError() throws FileNotFoundException {
		try {
			filter = new SNMPTrapFilter("src/test/resources/fail.yaml");
		} catch (FileNotFoundException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	void testYamlFileIsParsedCorrectly() throws FileNotFoundException {
		filter = new SNMPTrapFilter("src/test/resources/test.yaml");
		List<String> prefixes = filter.getPrefixes();
		assertEquals(".1.3.6.1.6.3.1.1.5", prefixes.get(0));
	}
	
	@Test
	void testPerformanceOnFileRead() throws FileNotFoundException {
		filter = new SNMPTrapFilter("src/test/resources/test2.yaml");
		List<String> prefixes = filter.getPrefixes();
		assertEquals(".1.3.6.1.4.1.9.10.137.0.1", prefixes.get(prefixes.size() -1));
	}
	
	@Test
	void testDifferentObjName() throws FileNotFoundException {
		filter = new SNMPTrapFilter("src/test/resources/test3.yaml");
		List<String> prefixes = filter.getPrefixes();
		assertEquals(".1.3.6.1.4.1.9.10.137.0.1", prefixes.get(prefixes.size() -1));
	}
	
	@Test
	void testYamlFileEmpty() throws FileNotFoundException {
		filter = new SNMPTrapFilter("src/test/resources/test5.yaml");
		List<String> prefixes = filter.getPrefixes();
		assertEquals(0, prefixes.size());
	}
	
	@Test
	void testIsOIDValidReturnsCorrectlyForGivenExamples() throws FileNotFoundException {
		filter = new SNMPTrapFilter("src/test/resources/test.yaml");
		assertEquals(true, filter.isOIDValid(".1.3.6.1.4.1.9.9.117.2.0.1"));
		assertEquals(true, filter.isOIDValid(".1.3.6.1.4.1.9.9.117"));
		assertEquals(false, filter.isOIDValid(".1.3.6.1.4.1.9.9.118.2.0.1"));
	}
	
	@Test
	void testIsOIDWhenYamlHasALargeAmount() throws FileNotFoundException {
		filter = new SNMPTrapFilter("src/test/resources/test4.yaml");
		assertEquals(true, filter.isOIDValid(".1.3.6.1.4.1.9.9.117.2.0.1"));
		assertEquals(true, filter.isOIDValid(".1.3.6.1.4.1.9.9.117"));
	}
	
	@Test
	void testIsOIDValidWhenFileIsEmpty() throws FileNotFoundException {
		filter = new SNMPTrapFilter("src/test/resources/test5.yaml");
		assertEquals(false, filter.isOIDValid("fail"));
	}
	
}
