import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class BooleamTestCase {
	Boolean b;
	@Before
	public void setUp(){
		b= new Boolean();
	}

	@Test
	public void test() {
		assertTrue(b.isTrue(5));
	}

}
