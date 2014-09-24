package com.maryamq.imageclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.maryamq.imageclient.R;
import com.maryamq.imageclient.model.ImageResult;
import com.maryamq.imageclient.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends Activity {

	private ShareActionProvider miShareAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_image_result);

		ImageView ivImage = (ImageView) this.findViewById(R.id.ivImage);

		TextView tvTitle = (TextView) this.findViewById(R.id.tvTitle);
		ImageResult result = (ImageResult) this.getIntent()
				.getSerializableExtra(SearchActivity.IMAGE_RESULT);
		Picasso.with(this).load(result.fullUrl).into(ivImage, new Callback() {

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				setupShareIntent();
			}

		});
		tvTitle.setText(Html.fromHtml(result.title));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share);
		// Fetch reference to the share action provider
		miShareAction = (ShareActionProvider) item.getActionProvider();
		// Return true to display menu
		return true;
	}

	// Gets the image URI and setup the associated share intent to hook into the
	// provider
	public void setupShareIntent() {
		// Fetch Bitmap Uri locally
		ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
		Uri bmpUri = Utils.getLocalBitmapUri(ivImage); // see previous remote
														// images section
		// Create share intent as described above
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
		shareIntent.setType("image/*");
		// Attach share event to the menu item provider
		miShareAction.setShareIntent(shareIntent);
	}

}
