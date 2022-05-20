package opennms.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class SNMPTrapFilter {

	private List<String> prefixes = new ArrayList<>();
	
	public SNMPTrapFilter(String filename) throws FileNotFoundException {
		this.prefixes = (retrieveListFromYamlFile(filename));
	}

	private List<String> retrieveListFromYamlFile(String filename) throws FileNotFoundException{
		Yaml yaml = new Yaml();
		InputStream inputStream = null;
		try {
			File file = new File(filename);
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(String.format("File name %s not found, please restart with a valid file", filename));
		}
		
		Map<String, List<String>> obj = yaml.load(inputStream);

		if(obj == null) {
			return Collections.emptyList();
		} else {
			String yamlFileObjName = (String) obj.keySet().toArray()[0];
			return obj.get(yamlFileObjName);
		}
		
	}
	
	public boolean isOIDValid(String oid) {
		if(prefixes.isEmpty()) {
			return false;
		} else {
			return doesAnyPrefixExistInString(oid);
		}
	}
	
	protected boolean doesAnyPrefixExistInString(String oid) {
		return prefixes.parallelStream().anyMatch(prefix -> oid.startsWith(prefix) || prefix.startsWith(oid));
	}

	public List<String> getPrefixes() {
		return prefixes;
	}
}
