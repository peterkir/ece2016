package examples.bndtools.impl.inverter;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import examples.service.api.StringModifier;

@Component
public class StringInverterImpl implements StringModifier {
	@Override
	public String modify(String input) {
		return new StringBuilder(input).reverse().toString();
	}

	@Activate
	void activate() {
		System.out.println("modifications " + this.getClass() + " activated");
	}

	@Deactivate
	void deactivate() {
		System.out.println(this.getClass() + " deactivated");
	}
}