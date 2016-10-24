package examples.pde.command;

import java.util.Map;

import org.osgi.service.component.annotations.*;
import examples.service.api.StringModifier;

@Component(
	property = { 
		"osgi.command.scope"    + "=zExampleModifier",
		"osgi.command.function" + "=modify" }, 
	service = StringModifierCommand.class
)
public class StringModifierCommand {

	private StringModifier modifier;

	@Reference
	void bindStringModifier(StringModifier modifier) {
	    this.modifier = modifier;
	}

	void updatedStringModifier(StringModifier modifier, Map<String, ?> properties) {
	    properties.forEach((k,v) -> {System.out.println(k + " : " + v);} );
	}

	void unbindStringModifier(StringModifier modifier) {
	    this.modifier = null;
	}

	public void modify(String message) {
	    System.out.println(modifier.modify(message));
	}
}