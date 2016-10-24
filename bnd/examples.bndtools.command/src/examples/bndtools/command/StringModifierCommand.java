package examples.bndtools.command;

import org.osgi.service.component.annotations.*;
import examples.service.api.StringModifier;

@Component(property = { "osgi.command.scope" + "=zExampleModifier",
		"osgi.command.function" + "=modify" }, service = StringModifierCommand.class)
public class StringModifierCommand {

	@Reference
	private StringModifier modifier;

	public void modify(String message) {
		System.out.println(modifier.modify(message));
	}

	@Activate
	void activate() {
		System.out.println(this.getClass() + " activated");
	}

	@Deactivate
	void deactivate() {
		System.out.println(this.getClass() + " deactivated");
	}
}