package sjtu.se.Activity.Information;

import android.view.*;
import sjtu.se.Meet.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Activity.Setting.SystemSettings;
import sjtu.se.Activity.Want.WantSettings;

public class HobbyInfoSettings extends Activity {

	private Context ctx = this;

	private SharedPreferences hobbyInfo;
	private TextView item_game;
	private TextView item_sport;
	private TextView item_comic;
	private TextView item_music;
	private TextView item_books;
	private TextView item_movie;
	private TextView item_other;

	private Switch item_game_overt;
	private Switch item_sport_overt;
	private Switch item_comic_overt;
	private Switch item_music_overt;
	private Switch item_books_overt;
	private Switch item_movie_overt;
	private Switch item_other_overt;

	public void hobbyInfoGameEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(hobbyInfo.getString(ActivityControlCenter.KEY_GAME, ""));

		new AlertDialog.Builder(ctx).setTitle("游戏").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putString(ActivityControlCenter.KEY_GAME, str);
				editor.commit();
				item_game.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void hobbyInfoSportEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(hobbyInfo.getString(ActivityControlCenter.KEY_SPORT, ""));

		new AlertDialog.Builder(ctx).setTitle("运动").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putString(ActivityControlCenter.KEY_SPORT, str);
				editor.commit();
				item_sport.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void hobbyInfoComicEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(hobbyInfo.getString(ActivityControlCenter.KEY_COMIC, ""));

		new AlertDialog.Builder(ctx).setTitle("动漫").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putString(ActivityControlCenter.KEY_COMIC, str);
				editor.commit();
				item_comic.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void hobbyInfoMusicEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(hobbyInfo.getString(ActivityControlCenter.KEY_MUSIC, ""));

		new AlertDialog.Builder(ctx).setTitle("音乐").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putString(ActivityControlCenter.KEY_MUSIC, str);
				editor.commit();
				item_music.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void hobbyInfoBooksEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(hobbyInfo.getString(ActivityControlCenter.KEY_BOOKS, ""));

		new AlertDialog.Builder(ctx).setTitle("书籍").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putString(ActivityControlCenter.KEY_BOOKS, str);
				editor.commit();
				item_books.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void hobbyInfoMovieEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(hobbyInfo.getString(ActivityControlCenter.KEY_MOVIE, ""));

		new AlertDialog.Builder(ctx).setTitle("电影").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putString(ActivityControlCenter.KEY_MOVIE, str);
				editor.commit();
				item_movie.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void hobbyInfoOtherEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(hobbyInfo.getString(ActivityControlCenter.KEY_OTHER, ""));

		new AlertDialog.Builder(ctx).setTitle("其他").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putString(ActivityControlCenter.KEY_OTHER, str);
				editor.commit();
				item_other.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void setHobbyInfoGameOvertListener(){
		item_game_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_GAME_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setHobbyInfoSportOvertListener(){
		item_sport_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_SPORT_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setHobbyInfoComicOvertListener(){
		item_comic_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_COMIC_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setHobbyInfoMusicOvertListener(){
		item_music_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_MUSIC_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setHobbyInfoBooksOvertListener(){
		item_books_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_BOOKS_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setHobbyInfoMovieOvertListener(){
		item_movie_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_MOVIE_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setHobbyInfoOtherOvertListener(){
		item_other_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = hobbyInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_OTHER_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hobby_info);

		/*this.getActionBar().setDisplayHomeAsUpEnabled(true);*/

		ActivityControlCenter.PERSONAL_INFO_MAY_CHANGED = true;

		hobbyInfo = getSharedPreferences(ActivityControlCenter.PERSONAL_HOBBY_INFO, 0);

		item_game = (TextView) findViewById(R.id.hobby_info_game);
		item_game.setText(hobbyInfo.getString(ActivityControlCenter.KEY_GAME, ""));
		item_sport = (TextView) findViewById(R.id.hobby_info_sport);
		item_sport.setText(hobbyInfo.getString(ActivityControlCenter.KEY_SPORT, ""));
		item_comic = (TextView) findViewById(R.id.hobby_info_comic);
		item_comic.setText(hobbyInfo.getString(ActivityControlCenter.KEY_COMIC, ""));
		item_music = (TextView) findViewById(R.id.hobby_info_music);
		item_music.setText(hobbyInfo.getString(ActivityControlCenter.KEY_MUSIC, ""));
		item_books = (TextView) findViewById(R.id.hobby_info_books);
		item_books.setText(hobbyInfo.getString(ActivityControlCenter.KEY_BOOKS, ""));
		item_movie = (TextView) findViewById(R.id.hobby_info_movie);
		item_movie.setText(hobbyInfo.getString(ActivityControlCenter.KEY_MOVIE, ""));
		item_other = (TextView) findViewById(R.id.hobby_info_other);
		item_other.setText(hobbyInfo.getString(ActivityControlCenter.KEY_OTHER, ""));

		item_game_overt = (Switch) findViewById(R.id.hobby_info_game_overt);
		item_game_overt.setChecked(hobbyInfo.getBoolean(ActivityControlCenter.KEY_GAME_OVERT, false));
		setHobbyInfoGameOvertListener();

		item_sport_overt = (Switch) findViewById(R.id.hobby_info_sport_overt);
		item_sport_overt.setChecked(hobbyInfo.getBoolean(ActivityControlCenter.KEY_SPORT_OVERT, false));
		setHobbyInfoSportOvertListener();

		item_comic_overt = (Switch) findViewById(R.id.hobby_info_comic_overt);
		item_comic_overt.setChecked(hobbyInfo.getBoolean(ActivityControlCenter.KEY_COMIC_OVERT, false));
		setHobbyInfoComicOvertListener();

		item_music_overt = (Switch) findViewById(R.id.hobby_info_music_overt);
		item_music_overt.setChecked(hobbyInfo.getBoolean(ActivityControlCenter.KEY_MUSIC_OVERT, false));
		setHobbyInfoMusicOvertListener();

		item_books_overt = (Switch) findViewById(R.id.hobby_info_books_overt);
		item_books_overt.setChecked(hobbyInfo.getBoolean(ActivityControlCenter.KEY_BOOKS_OVERT, false));
		setHobbyInfoBooksOvertListener();

		item_movie_overt = (Switch) findViewById(R.id.hobby_info_movie_overt);
		item_movie_overt.setChecked(hobbyInfo.getBoolean(ActivityControlCenter.KEY_MOVIE_OVERT, false));
		setHobbyInfoMovieOvertListener();

		item_other_overt = (Switch) findViewById(R.id.hobby_info_other_overt);
		item_other_overt.setChecked(hobbyInfo.getBoolean(ActivityControlCenter.KEY_OTHER_OVERT, false));
		setHobbyInfoOtherOvertListener();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hobby_information, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			this.startActivity(new Intent(HobbyInfoSettings.this, SystemSettings.class));
			return true;
		}
		if (id == android.R.id.home){
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
