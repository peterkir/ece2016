package examples.bndtools.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import examples.service.api.StringModifier;

@Component(property = { "osgi.command.scope:String=zExampleModifier",
		"osgi.command.function:String=modify" }, service = StringModifierCommand.class)
public class StringModifierCommand {

	private List<StringModifier> modifier = new ArrayList<>();

	@Activate
	void activate() {
		System.out.println(this.getClass() + " activated");
	}

	@Deactivate
	void deactivate() {
		System.out.println(this.getClass() + " deactivated");
	}

	@Reference(cardinality = ReferenceCardinality.AT_LEAST_ONE)
	void bindStringModifier(StringModifier modifier) {
		this.modifier.add(modifier);
	}

	void unbindStringModifier(StringModifier modifier) {
		this.modifier.remove(modifier);
	}

	void updatedStringModifier(StringModifier modifier, Map<String, Object> properties) {
		System.out.println(modifier.getClass() + " configuration updated");
		properties.forEach((key, value) -> System.out.println(key + " = " + value));
	}

	public void modify(String input) {
		modifier.forEach(m -> System.out.println(m.modify(input)));
	}
}