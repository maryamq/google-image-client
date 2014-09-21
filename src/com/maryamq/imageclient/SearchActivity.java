package com.maryamq.imageclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
    
    private boolean triggerSearch(String input) {
    	if (input == null || input.isEmpty()) {
    		return false;
    	}
    	
    	return true;
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.search_menu, menu);
    	MenuItem searchItem = menu.findItem(R.id.action_search);
    	SearchView searchView = (SearchView) searchItem.getActionView();
    	searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return triggerSearch(query);
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return true;
			}
    		
    	});
    	return super.onCreateOptionsMenu(menu);
    }
}
