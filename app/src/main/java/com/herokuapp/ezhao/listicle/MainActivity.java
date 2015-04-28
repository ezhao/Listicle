package com.herokuapp.ezhao.listicle;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.fragNavDrawer) FragmentNavigationDrawer fragNavDrawer;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.lvDrawer) ListView lvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        // Hook up the drawer
        setSupportActionBar(toolbar);
        fragNavDrawer.setupDrawerConfiguration(lvDrawer, toolbar, R.layout.drawer_listicle_item, R.id.flFragment);

        // Add nav items
        fragNavDrawer.addNavItem("Test", "Test one", ListicleFragment.class);
        fragNavDrawer.addNavItem("Test2", "Test two", ListicleFragment.class);
        fragNavDrawer.addNavItem("Test3", "Test three", ListicleFragment.class);
        if (savedInstanceState == null) {
            fragNavDrawer.selectDrawerItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (fragNavDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        fragNavDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        fragNavDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }
}
