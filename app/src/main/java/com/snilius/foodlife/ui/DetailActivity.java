package com.snilius.foodlife.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.snilius.foodlife.App;
import com.snilius.foodlife.R;
import com.snilius.foodlife.data.CupboardDbHelper;
import com.snilius.foodlife.model.Food;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class DetailActivity extends AppCompatActivity {

    public static final String FOOD_EXTRA = "FOOD_EXTRA";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.category)
    TextView mCategory;
    @Bind(R.id.carbs)
    TextView mCarbs;
    @Bind(R.id.fat)
    TextView mFat;
    @Bind(R.id.protein)
    TextView mProtein;
    @Bind(R.id.calories)
    TextView mCalories;

    @Inject
    CupboardDbHelper mDhHelper;
    private Food mFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        ((App) getApplication()).getComponent().inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (!intent.hasExtra(FOOD_EXTRA)) {
            Timber.d("nope nothing here");
            finish();
        }

        mFood = Parcels.unwrap(intent.getParcelableExtra(FOOD_EXTRA));
        getSupportActionBar().setTitle(mFood.getTitle());
        getSupportActionBar().setSubtitle(mFood.getBrand());

        mCategory.setText(mFood.getCategory());
        mCarbs.setText(getString(R.string.carbs, mFood.getCarbohydrates()));
        mFat.setText(getString(R.string.fat, mFood.getFat()));
        mProtein.setText(getString(R.string.protein, mFood.getProtein()));
        mCalories.setText(getString(R.string.calories, mFood.getCalories()));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (cupboard().withDatabase(mDhHelper.getWritableDatabase()).get(Food.class, mFood.getId()) == null) {
            getMenuInflater().inflate(R.menu.menu_detail_save, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_detail_detele, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                cupboard().withDatabase(mDhHelper.getWritableDatabase()).put(mFood);
                Toast.makeText(this, R.string.food_saved, Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
                return true;
            case R.id.action_delete:
                cupboard().withDatabase(mDhHelper.getWritableDatabase()).delete(Food.class, mFood.getId());
                Toast.makeText(this, R.string.food_droped, Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
