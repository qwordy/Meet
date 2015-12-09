package sjtu.se;

/**
 * Created by qwordy on 12/9/15.
 * UbmaTest
 */

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.ActivityInstrumentationTestCase2;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.click;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import sjtu.se.Meet.R;
import sjtu.se.Ubma.UbmaDrawerActivity;

@RunWith(AndroidJUnit4.class)
public class UbmaTest
		extends ActivityInstrumentationTestCase2<UbmaDrawerActivity> {

	private AppCompatActivity mActivity;

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

	//@Test
	public void typeOperandsAndPerformAddOperation() {
		// Call the CalculatorActivity add() method and pass in some operand values, then
		// check that the expected value is returned.
		assertEquals(1, 1);
		assertNotNull(null);
		assertEquals(4, 4);
	}

	private String getString(int id) {
		return getInstrumentation().getTargetContext().getString(id);
	}

	@Test
	public void test0() {
		ActionBar toolbar = mActivity.getSupportActionBar();
		assertEquals(toolbar.getTitle(), getString(R.string.title_section1));
		//onView(withId(android.R.id.home)).perform(click());
		onView(withContentDescription(getString(R.string.navigation_drawer_open))).
				perform(click());
		assertEquals(toolbar.getTitle(), getString(R.string.ubma_title));
		onView(withContentDescription(getString(R.string.navigation_drawer_close))).
				perform(click());
		assertEquals(toolbar.getTitle(), getString(R.string.title_section1));
		onView(withContentDescription(getString(R.string.navigation_drawer_open))).
				perform(click());
		//onData(withText(getString(R.string.title_section2))).perform(click());
		onData(is(getString(R.string.title_section2))).perform(click());

		//try {Thread.sleep(1000);}catch (Exception e) {}
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
