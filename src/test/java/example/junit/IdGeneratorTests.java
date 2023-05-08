package example.junit;

import internal.IDGenerator;
import internal.Løbenummer;

public class IdGeneratorTests {
	IDGenerator idGenerator;

	@org.junit.Before()
	public void testConstructor() {
		idGenerator = new IDGenerator();
	}

	@org.junit.Test()
	public void testGettersAndSetters() {
		idGenerator.getLøbenummer();
		idGenerator.setLøbenummer(new Løbenummer());
	}
}
