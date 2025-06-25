package examples.pde.impl.configurable;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		property = { 
				"osgi.command.scope:String=zExamplesModifier",
				"osgi.command.function:String=config" 
		}, 
		service = ConfigCommand.class
)
public class ConfigCommand {

	ConfigurationAdmin cm;
	
	@Reference
	void setConfigAdmin(ConfigurationAdmin cm) {
		this.cm = cm;
	}
	
	public void config(String key, String value) throws IOException {
		if (Constants.PREFIX.equals(key) || Constants.SUFFIX.equals(key)) {
			Configuration config = cm.getConfiguration("manipulator");
			Dictionary<String, Object> props = null;
			if (config != null && config.getProperties() != null) {
				props = config.getProperties();
			} else {
				props = new Hashtable<>();
			}
			props.put(key, value);
			config.update(props);
		} else {
			System.err.println("invalid key to value mapping");
		}
	}
	
	public void config(String key, Integer value) throws IOException {
		if (Constants.ITERATION.equals(key)) {
			Configuration config = cm.getConfiguration("manipulator");
			Dictionary<String, Object> props = null;
			if (config != null && config.getProperties() != null) {
				props = config.getProperties();
			} else {
				props = new Hashtable<>();
			}
			props.put(key, value);
			config.update(props);
		} else {
			System.err.println("invalid key to value mapping");
		}
	}
	
	public void config(String key, Boolean value) throws IOException {
		if (Constants.UPPER_CASE.equals(key)) {
			Configuration config = cm.getConfiguration("manipulator");
			Dictionary<String, Object> props = null;
			if (config != null && config.getProperties() != null) {
				props = config.getProperties();
			} else {
				props = new Hashtable<>();
			}
			props.put(key, value);
			config.update(props);
		} else {
			System.err.println("invalid key to value mapping");
		}
	}
}