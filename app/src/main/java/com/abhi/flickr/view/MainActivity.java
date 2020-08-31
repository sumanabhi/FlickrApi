package com.abhi.flickr.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.abhi.flickr.R;
import com.abhi.flickr.repository.model.FlikrModel;
import com.abhi.flickr.utils.Flickrutils;
import com.abhi.flickr.view.recview.Divider;
import com.abhi.flickr.view.recview.FlikrListAdapter;
import com.abhi.flickr.view.vvmcontract.MainScreen;
import com.abhi.flickr.viewmodel.FlikrViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainScreen {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int DATA_FETCHING_INTERVAL = 5 * 1000; //5 seconds
    private static final String SEARCH_KEY = "search-key";
    private RecyclerView recView;
    private FlikrListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FlikrViewModel mViewModel;
    private long mLastFetchedDataTimeStamp;
    private final Observer<List<FlikrModel>> dataObserver = flikrModels -> updateData(flikrModels);

    private final Observer<String> errorObserver = errorMsg -> setError(errorMsg);
    private boolean isLoading;
    private int page = 0;
    private String searchText = Flickrutils.DEFAULT_QUERY;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        // if you saved something on outState you can recover them here
        if (savedInstanceState != null) {
            if (savedInstanceState.getString(SEARCH_KEY) != null)
                searchText = savedInstanceState.getString(SEARCH_KEY);
        }
        mViewModel = ViewModelProviders.of(this).get(FlikrViewModel.class);
        mViewModel.getFlikerModels().observe(this, dataObserver);
        mViewModel.getErrorUpdates().observe(this, errorObserver);
        mViewModel.clearData();
        mViewModel.fetchData(searchText, page++);
    }

    private void bindViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        recView = findViewById(R.id.recView);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (System.currentTimeMillis() - mLastFetchedDataTimeStamp < DATA_FETCHING_INTERVAL) {
                Log.d(TAG, "\tNot fetching from network because interval didn't reach");
                mSwipeRefreshLayout.setRefreshing(false);
                return;
            }
            mViewModel.fetchData(searchText, page);
        });
        mAdapter = new FlikrListAdapter(getApplication());
        GridLayoutManager lm = new GridLayoutManager(this, 3);
        recView.setLayoutManager(lm);
        recView.setAdapter(mAdapter);
        recView.addItemDecoration(new Divider(this));
        recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            isLoading = true;
                            Log.d(TAG, "fetchData called for page:" + page);
                            //Do pagination.. i.e. fetch new data
                            mViewModel.fetchData(searchText, page++);
                        }
                    }
                }
            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (searchView != null) {
            searchText = searchView.getQuery().toString();
            outState.putString(SEARCH_KEY, searchText);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        if (searchText != null && !searchText.isEmpty()) {
            myActionMenuItem.expandActionView();
            searchView.setQuery(searchText, true);
            searchView.clearFocus();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                page = 1;
                searchText = query;
                mAdapter.setItems(null);
                mViewModel.clearData();
                mViewModel.fetchData(query, page++);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });

        return true;
    }


    private void showErrorToast(String error) {
        Toast.makeText(this, "Error:" + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateData(List<FlikrModel> data) {
        isLoading = false;
        mLastFetchedDataTimeStamp = System.currentTimeMillis();
        mAdapter.setItems(data);

        Log.d(TAG, "Thread->" + Thread.currentThread().getName() + "\nData Size:" +
                data.size() + "\nAdapter Data Size:" + mAdapter.getItemCount());
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setError(String msg) {
        showErrorToast(msg);
    }
}
