package sjtu.se;

/**
 * Created by qwordy on 12/9/15.
 * UbmaTest
 */

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
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
	public void testUbma() {
		ActionBar toolbar = mActivity.getSupportActionBar();

		// Check title 1
		assertEquals(toolbar.getTitle(), getString(R.string.preferenced_app));
		//onView(withId(android.R.id.home)).perform(click());

		// Open drawer
		onView(withContentDescription(getString(R.string.navigation_drawer_open)))
				.perform(click());

		// Check title ubma
		assertEquals(toolbar.getTitle(), getString(R.string.ubma_title));

		// Close drawer
		onView(withContentDescription(getString(R.string.navigation_drawer_close)))
				.perform(click());

		// Check title 1
		assertEquals(toolbar.getTitle(), getString(R.string.preferenced_app));

		// Open drawer
		onView(withContentDescription(getString(R.string.navigation_drawer_open)))
				.perform(click());

		// Click title 1
		onData(allOf(is(instanceOf(String.class)),
				is(getString(R.string.preferenced_app))))
				.perform(click());

		// Check title 1
		assertEquals(toolbar.getTitle(), getString(R.string.preferenced_app));

		// Open drawer
		onView(withContentDescription(getString(R.string.navigation_drawer_open)))
				.perform(click());

		// Click title 2
		onData(allOf(is(instanceOf(String.class)),
				is(getString(R.string.active_time))))
				.perform(click());

		// Swipe
		onView(withId(R.id.appListView)).perform(swipeUp());
		onView(withId(R.id.appListView)).perform(swipeUp());
		onView(withId(R.id.appListView)).perform(swipeDown());

		// Click spinner
		onView(withId(R.id.spinner)).perform(click());

		// Select user apps
		onData(allOf(is(instanceOf(String.class)), is("用户应用")))
				.perform(click());

		// Swipe
		onView(withId(R.id.appListView)).perform(swipeUp());
		onView(withId(R.id.appListView)).perform(swipeUp());
		onView(withId(R.id.appListView)).perform(swipeDown());

		// Open drawer
		onView(withContentDescription(getString(R.string.navigation_drawer_open)))
				.perform(click());

		// Click title 4
		onData(allOf(is(instanceOf(String.class)),
				is(getString(R.string.ubma_about))))
				.inAdapterView(withId(R.id.navigation_drawer))
				.perform(click());

		// Open drawer
		onView(withContentDescription(getString(R.string.navigation_drawer_open)))
				.perform(click());

		// Click title 2
		onData(allOf(is(instanceOf(String.class)),
				is(getString(R.string.active_time))))
				.perform(click());

		//try {Thread.sleep(1000);}catch (Exception e) {}
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
