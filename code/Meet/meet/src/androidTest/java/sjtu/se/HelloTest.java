package sjtu.se;

/**
 * Created by qwordy on 12/9/15.
 * HelloTest
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelloTest {
	@Rule
	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
			MainActivity.class);
}
