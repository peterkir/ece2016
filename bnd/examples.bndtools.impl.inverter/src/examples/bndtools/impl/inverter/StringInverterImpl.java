package examples.bndtools.impl.inverter;

import org.osgi.service.component.annotations.*;
import examples.service.api.StringModifier;

@Component
public class StringInverterImpl implements StringModifier {
	@Override
	public String modify(String input) {
		return new StringBuilder(input).reverse().toString();
	}
}