package com.maryamq.imageclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.maryamq.imageclient.R;
import com.maryamq.imageclient.Utils;
import com.maryamq.imageclient.model.ImageResult;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		getActionBar().hide();

		ImageView ivImage = (ImageView) this
				.findViewById(R.id.ivFullScreenImage);
		ImageResult result = (ImageResult) this.getIntent()
				.getSerializableExtra(SearchActivity.IMAGE_RESULT);
		Picasso.with(this).load(result.fullUrl).into(ivImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
