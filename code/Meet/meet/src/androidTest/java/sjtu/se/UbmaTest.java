package sjtu.se;

/**
 * Created by qwordy on 12/9/15.
 * Ubma
 */

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.ActivityInstrumentationTestCase2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import sjtu.se.Ubma.UbmaDrawerActivity;

@RunWith(AndroidJUnit4.class)
public class UbmaTest
		extends ActivityInstrumentationTestCase2<UbmaDrawerActivity> {

	private Activity mActivity;

	public UbmaTest() {
		super(UbmaDrawerActivity.class);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		// Injecting the Instrumentation instance is required
		// for your test to run with AndroidJUnitRunner.
		injectInstrumentation(InstrumentationRegistry.getInstrumentation());
		mActivity = getActivity();
	}

	@Test
	public void typeOperandsAndPerformAddOperation() {
		// Call the CalculatorActivity add() method and pass in some operand values, then
		// check that the expected value is returned.
		assertEquals(1, 1);
		assertNotNull(null);
		assertEquals(4, 4);
	}

	@Test
	public void test0() {
		assertTrue(2 > 1);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
