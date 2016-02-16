package com.snilius.foodlife.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.snilius.foodlife.App;
import com.snilius.foodlife.R;
import com.snilius.foodlife.data.CupboardDbHelper;
import com.snilius.foodlife.data.FoodAdapter;
import com.snilius.foodlife.data.FoodService;
import com.snilius.foodlife.model.Food;
import com.snilius.foodlife.model.FoodNotFound;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity implements FoodAdapter.OnClickListener {

    public static final String BUNDLE_FOOD_LIST = "FOOD_LIST";
    public static final String BUNDLE_QUERY = "QUERY";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    FoodService mFoodService;
    @Inject
    CupboardDbHelper mDhHelper;

    private List<Food> mList;
    private FoodAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String mQuery;
    private SearchView mSearchView;
    private boolean mIsSearch = false;

    private ActionMode mActionMode;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((App) getApplication()).getComponent().inject(this);
        setSupportActionBar(mToolbar);

        mLayoutManager = new LinearLayoutManager(this);
        mList = new ArrayList<>();
        mAdapter = new FoodAdapter(mList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null && mIsSearch) {
            if (savedInstanceState.containsKey(BUNDLE_FOOD_LIST)) {
                Timber.d("restoring state");
                mList.addAll(Parcels.unwrap(savedInstanceState.getParcelable(BUNDLE_FOOD_LIST)));
                mAdapter.notifyDataSetChanged();
                mIsSearch = true;
            }

            if (savedInstanceState.containsKey(BUNDLE_QUERY)) {
                mQuery = savedInstanceState.getString(BUNDLE_QUERY);
            }
        } else {
            new LoadLocalFoodTask().execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsSearch) {
            new LoadLocalFoodTask().execute();
        }
    }

    @Override
    public void itemClick(int position) {
        // toggle selection with one tap if in context mode
        if (mActionMode != null) {
            mAdapter.toggleSelect(position);
            toggleSelect();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.FOOD_EXTRA, Parcels.wrap(mList.get(position)));
            startActivity(intent);
        }
    }

    @Override
    public void itemLongClick(int position, boolean selected) {
        Timber.d("long press");
        toggleSelect();
    }

    private void toggleSelect() {
        if (mActionMode == null) {
            mActionMode = startSupportActionMode(actionModeCallback);
        }

        int selectedCount = mAdapter.getSelected().size();
        if (selectedCount < 1) {
            mActionMode.finish();
        } else {
            mActionMode.setTitle(getString(R.string.selected_items, selectedCount));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (mQuery != null) {
            MenuItemCompat.expandActionView(searchItem);
            mSearchView.setQuery(mQuery, false);
        }

        RxSearchView.queryTextChangeEvents(mSearchView)
                .filter(event ->
                        // manual submit
                        event.isSubmitted() && event.queryText().length() > 0
                        // continuous
                        || event.queryText().length() > 2
                )
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(event -> {
                    mQuery = event.queryText().toString();
                    mFoodService.search(mQuery)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                mIsSearch = true;
                                mList.clear();
                                if (response.code() == 200) {
                                    mList.addAll(response.body().food);
                                } else {
                                    Timber.d(response.message());
                                    mList.add(new FoodNotFound(getString(R.string.no_such_food)));
                                }
                                mAdapter.notifyDataSetChanged();
                            }, err -> {
                                Timber.d(err, err.getMessage());

                                mList.clear();
                                mList.add(new FoodNotFound(getString(R.string.no_such_food)));
                                mAdapter.notifyDataSetChanged();
                            });
                });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                new LoadLocalFoodTask().execute();
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Timber.d("onSaveInstanceState");
        outState.putParcelable(BUNDLE_FOOD_LIST, Parcels.wrap(mList));
        outState.putString(BUNDLE_QUERY, mQuery);
        super.onSaveInstanceState(outState);
    }

    /**
     * Handler for context toolbar
     */
    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            if (mIsSearch) mode.getMenuInflater().inflate (R.menu.menu_main_context, menu);
            else mode.getMenuInflater().inflate (R.menu.menu_main_local_context, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.action_save) {
                Timber.d("save");
                SparseIntArray selected = mAdapter.getSelected();
                SQLiteDatabase database = mDhHelper.getWritableDatabase();
                for (int i = 0; i < selected.size(); i++) {
                    int foodIndex = selected.valueAt(i);
                    Timber.d("saving food index "+foodIndex);
                    cupboard().withDatabase(database).put(mList.get(foodIndex));
                }
                Toast.makeText(MainActivity.this, R.string.selection_save, Toast.LENGTH_SHORT).show();
                mAdapter.clearSelection();
                mActionMode.finish();
                return true;
            } else if (itemId == R.id.action_delete) {
                SparseIntArray selected = mAdapter.getSelected();
                SQLiteDatabase database = mDhHelper.getWritableDatabase();

                List<Food> toRemove = new ArrayList<>(selected.size());
                for (int i = 0; i < selected.size(); i++) {
                    int foodIndex = selected.valueAt(i);
                    Timber.d("drop food index " + foodIndex);
                    toRemove.add(mList.get(foodIndex));
                    cupboard().withDatabase(database).delete(Food.class, mList.get(foodIndex).getId());
                }

                mList.removeAll(toRemove);
                mAdapter.notifyDataSetChanged();
                mAdapter.clearSelection();
                mActionMode.finish();

                Toast.makeText(MainActivity.this, R.string.selection_delete, Toast.LENGTH_SHORT).show();
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            mAdapter.clearSelection();
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Local content loader
     */
    private class LoadLocalFoodTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            SQLiteDatabase database = mDhHelper.getReadableDatabase();
            List<Food> list = cupboard().withDatabase(database).query(Food.class).orderBy("title asc").list();
            mList.clear();
            mList.addAll(list);
            mIsSearch = false;
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
