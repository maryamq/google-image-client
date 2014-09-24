package com.maryamq.imageclient.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.maryamq.imageclient.R;
import com.maryamq.imageclient.adapters.ImageResultsAdapter;
import com.maryamq.imageclient.dialog.SettingsDialog;
import com.maryamq.imageclient.dialog.SettingsDialog.SettingsDialogListener;
import com.maryamq.imageclient.handlers.EndlessScrollListener;
import com.maryamq.imageclient.model.ImageResult;
import com.maryamq.imageclient.model.UrlMetaData;
import com.maryamq.imageclient.utils.Utils;

public class SearchActivity extends FragmentActivity implements
		SettingsDialogListener {

	public static final String IMAGE_RESULT = "Image_Result";
	AsyncHttpClient client;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private GridView gvResults;
	private int lastCusorPosition;
	private String currentQuery;
	private UrlMetaData urlMetaData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		client = new AsyncHttpClient();
		// Setup adapter
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		urlMetaData = new UrlMetaData();
		this.setupViews();
	}

	private void setupViews() {
		gvResults = (GridView) this.findViewById(R.id.gdResults);
		gvResults.setAdapter(aImageResults);
		gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if (lastCusorPosition == 0) {
					aImageResults.clear();
				}
				if (lastCusorPosition != totalItemsCount) {
					triggerSearch(currentQuery, totalItemsCount);

				}
				lastCusorPosition = totalItemsCount;
			}

		});
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(SearchActivity.this,
						ImageDisplayActivity.class);
				ImageResult result = imageResults.get(position);
				i.putExtra(IMAGE_RESULT, result);
				startActivity(i);
			}
		});
	}

	private boolean triggerSearch(String input, int page) {
		if (input == null || input.isEmpty()) {
			return false;
		}

		// check the internet.
		if (!Utils.isNetworkAvailable(this)) {
			Toast.makeText(SearchActivity.this, "No Internet connection",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		this.currentQuery = input;
		String url = this.urlMetaData.getUrl(input, page);
		Log.d("debug", "URL for load more " + url);
		client.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				JSONArray imageResultsJson = null;
				try {
					if (response.isNull("responseData")) {
						Toast.makeText(SearchActivity.this, "No More Images!",
								Toast.LENGTH_SHORT).show();
						return;
					}
					imageResultsJson = response.getJSONObject("responseData")
							.getJSONArray("results");
					aImageResults.addAll(ImageResult
							.fromJSONArray(imageResultsJson));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_menu, menu);
		MenuItem settingsItem = menu.findItem(R.id.itSettings);
		settingsItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				FragmentManager fm = getSupportFragmentManager();
				SettingsDialog dialog = new SettingsDialog(urlMetaData);
				dialog.show(fm, "fragment_edit_settings");
				return true;
			}

		});
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				aImageResults.clear();
				Log.i("Debug", "List cleared");
				return triggerSearch(query, 0);
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return true;
			}

		});
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onFinishSettingsDialog(UrlMetaData urlData) {
		this.urlMetaData = urlData;
		aImageResults.clear();
		triggerSearch(this.currentQuery, 0);

	}

}
