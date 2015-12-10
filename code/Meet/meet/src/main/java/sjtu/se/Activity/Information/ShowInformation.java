package sjtu.se.Activity.Information;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Activity.Setting.SystemSettings;
import sjtu.se.Activity.Want.WantSettings;
import sjtu.se.UserInformation.Information;

import sjtu.se.Meet.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowInformation extends AppCompatActivity {

	private Information info;

	private void setBaseInfo(){
		((TextView) findViewById(R.id.show_nick)).setText(info.baseinfo.Nick);
		String str;
		str = info.baseinfo.Name;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_name)).setText("-");
		else
			((TextView) findViewById(R.id.show_name)).setText(str);

		str = info.baseinfo.Gender;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_gender)).setText("-");
		else
			((TextView) findViewById(R.id.show_gender)).setText(ActivityControlCenter.genders[Integer.parseInt(str)]);

		str = info.baseinfo.BirthDay;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_birthday)).setText("-");
		else
			((TextView) findViewById(R.id.show_birthday)).setText(str);

		str = info.baseinfo.Homeland;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_homeland)).setText("-");
		else
			((TextView) findViewById(R.id.show_homeland)).setText(str);

		str = info.baseinfo.Location;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_location)).setText("-");
		else
			((TextView) findViewById(R.id.show_location)).setText(str);

		str = info.keywords;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_keywords)).setText("-");
		else
			((TextView) findViewById(R.id.show_keywords)).setText(str);
	}

	private void setContactInfo(){
		String str;
		str = info.contactinfo.Phone;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_phone)).setText("-");
		else
			((TextView) findViewById(R.id.show_phone)).setText(str);

		str = info.contactinfo.QQ;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_qq)).setText("-");
		else
			((TextView) findViewById(R.id.show_qq)).setText(str);

		str = info.contactinfo.E_Mail;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_email)).setText("-");
		else
			((TextView) findViewById(R.id.show_email)).setText(str);

		str = info.contactinfo.Weibo;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_weibo)).setText("-");
		else
			((TextView) findViewById(R.id.show_weibo)).setText(str);

		str = info.contactinfo.Social_Network;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_socialnet)).setText("-");
		else
			((TextView) findViewById(R.id.show_socialnet)).setText(str);
	}

	private void setEducationInfo(){
		String str;
		str = info.edu.College;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_college)).setText("-");
		else
			((TextView) findViewById(R.id.show_college)).setText(str);

		str = info.edu.High_School;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_high)).setText("-");
		else
			((TextView) findViewById(R.id.show_high)).setText(str);

		str = info.edu.Middle_School;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_middle)).setText("-");
		else
			((TextView) findViewById(R.id.show_middle)).setText(str);

		str = info.edu.Primary_School;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_primary)).setText("-");
		else
			((TextView) findViewById(R.id.show_primary)).setText(str);
	}

	private void setHobbyInfo(){
		String str;
		str = info.hobby.Game;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_game)).setText("-");
		else
			((TextView) findViewById(R.id.show_game)).setText(str);

		str = info.hobby.Sport;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_sport)).setText("-");
		else
			((TextView) findViewById(R.id.show_sport)).setText(str);

		str = info.hobby.Comic;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_comic)).setText("-");
		else
			((TextView) findViewById(R.id.show_comic)).setText(str);

		str = info.hobby.Music;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_music)).setText("-");
		else
			((TextView) findViewById(R.id.show_music)).setText(str);

		str = info.hobby.Books;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_books)).setText("-");
		else
			((TextView) findViewById(R.id.show_books)).setText(str);

		str = info.hobby.Movie;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_movie)).setText("-");
		else
			((TextView) findViewById(R.id.show_movie)).setText(str);

		str = info.hobby.Other;
		if (str.equals(""))
			((TextView) findViewById(R.id.show_other)).setText("-");
		else
			((TextView) findViewById(R.id.show_other)).setText(str);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_information);

		Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(myToolbar);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		Parcelable parcelable = this.getIntent().getParcelableExtra("information");
		info = (Information) parcelable;
		System.out.println("info : " + info.keywords);

		setBaseInfo();
		setContactInfo();
		setEducationInfo();
		setHobbyInfo();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_information, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			this.startActivity(new Intent(ShowInformation.this, SystemSettings.class));
			return true;
		}
		if (id == android.R.id.home){
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}*/
}
