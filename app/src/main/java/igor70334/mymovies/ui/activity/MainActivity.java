package igor70334.mymovies.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import igor70334.mymovies.R;

public class MainActivity extends BaseActivity {

    //TODO: save state of Y position when rotating

    SearchView mSearchView;
    MenuItem mMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mMenu = menu.findItem(R.id.search);

        mSearchView = (SearchView) mMenu.getActionView();

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setQueryHint(getResources().getString(R.string.search_hint));

        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //TODO: find a better way to do this
        //reseting SearchView (for when the user press the home button)
        if(mSearchView != null) {
            mSearchView.setQuery("", false);
            //Collapse the action view
            mSearchView.onActionViewCollapsed();
        }
        if(mMenu != null) {
            //Collapse the search widget
            mMenu.collapseActionView();
        }
    }


}
