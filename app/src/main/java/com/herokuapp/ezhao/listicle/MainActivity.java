package com.herokuapp.ezhao.listicle;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.activeandroid.Model;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity implements EditTextDialog.EditTextDialogListener {
    private final String ADD_TITLE = "Create new Listicle";
    private final String EDIT_TITLE = "Edit this Listicle's name";
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
        // TODO(emily) figure out why selecting just the listName column failed
        List<Listicle> listicles = new Select().from(Listicle.class).execute();
        ArrayList<String> alreadyAdded = new ArrayList<>();
        for (Listicle listicle : listicles) {
            String listicleName = listicle.getListName();
            if (!alreadyAdded.contains(listicleName)) {
                alreadyAdded.add(listicleName);
                newListicle(fragNavDrawer, listicleName);
            }
        }
        if (listicles.size() == 0) {
            newListicle(fragNavDrawer, "First list");
        }

        if (savedInstanceState == null) {
            fragNavDrawer.selectDrawerItem(0);
        }
    }

    private int newListicle(FragmentNavigationDrawer fragNavDrawer, String listicleName) {
        Bundle args = new Bundle();
        args.putString(ListicleFragment.LISTICLE_NAME_KEY, listicleName);
        return fragNavDrawer.addNavItem(listicleName, listicleName, ListicleFragment.class, args);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (fragNavDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        if (id == R.id.action_add) {
            EditTextDialog addListicleDialog = EditTextDialog.newInstance(ADD_TITLE);
            addListicleDialog.show(fm, "fragment_add_listicle");
            return true;
        } else if (id == R.id.action_edit) {
            EditTextDialog addListicleDialog = EditTextDialog.newInstance(EDIT_TITLE);
            addListicleDialog.show(fm, "fragment_edit_listicle");
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

    @Override
    public void onFinishEditText(String title, String inputText) {
        if (title.equals(ADD_TITLE)) {
            Log.i("EMILY", "adding: " + inputText);
            int newPosition = newListicle(fragNavDrawer, inputText);
            fragNavDrawer.selectDrawerItem(newPosition);
        } else if (title.equals(EDIT_TITLE)) {
            // TODO(emily) this doesn't do anything yet oops
            Log.i("EMILY", "editing: " + inputText);
        }
    }
}
