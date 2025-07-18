package examples.bndtools.impl.configurable;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import examples.service.api.StringModifier;

@ObjectClassDefinition(name = "StringManipulator Configuration")
@interface StringManipulatorConfig {
	String prefix() default "";

	String suffix() default "";

	int repeat() default 1;

	boolean uppercase() default false;
}

@Component(configurationPid = "manipulator", configurationPolicy = ConfigurationPolicy.OPTIONAL)
@Designate(ocd = StringManipulatorConfig.class)
public class StringManipulator implements StringModifier {

	StringManipulatorConfig config;

	@Activate
	void activate(StringManipulatorConfig config) {
		System.out.println(this.getClass() + " activated");
		this.config = config;
	}

	@Modified
	void modified(StringManipulatorConfig config) {
		this.config = config;
	}

	public void deactivate() {
		System.out.println(this.getClass() + " de-activated");
	}

	@Override
	public String modify(String input) {
		String prefix = config.prefix();
		String suffix = config.suffix();
		Integer iteration = config.repeat();
		Boolean upperCase = config.uppercase();

		StringBuilder builder = new StringBuilder();
		builder.append(prefix);

		for (int i = 0; i < iteration; i++) {
			builder.append(upperCase ? input.toUpperCase() : input);
		}

		builder.append(suffix);

		return builder.toString();
	}

}