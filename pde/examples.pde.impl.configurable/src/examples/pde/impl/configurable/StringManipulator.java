package examples.pde.impl.configurable;

import java.util.Map;
import org.osgi.service.component.annotations.*;
import examples.service.api.StringModifier;

@Component(
	configurationPid = "manipulator", 
	configurationPolicy = ConfigurationPolicy.REQUIRE
)
public class StringManipulator implements StringModifier {

	Map<String, Object> config;

	@Activate
	void activate(Map<String, Object> config) {
		System.out.println(this.getClass() + " activated");
		this.config = config;
	}

	@Modified
	void modified(Map<String, Object> config) {
		this.config = config;
	}

	public void deactivate() {
		System.out.println(this.getClass() + " de-activated");
	}

	@Override
	public String modify(String input) {
		String prefix = (String) config.getOrDefault(Constants.PREFIX, "");
		String suffix = (String) config.getOrDefault(Constants.SUFFIX, "");
		Integer iteration = (Integer) config.getOrDefault(Constants.ITERATION, Integer.valueOf(1));
		Boolean upperCase = (Boolean) config.getOrDefault(Constants.UPPER_CASE, Boolean.FALSE);

		StringBuilder builder = new StringBuilder();
		builder.append(prefix);

		for (int i = 0; i < iteration; i++) {
			builder.append(upperCase ? input.toUpperCase() : input);
		}
		builder.append(suffix);
		return builder.toString();
	}
}