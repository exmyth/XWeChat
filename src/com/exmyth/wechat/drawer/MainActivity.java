package com.exmyth.wechat.drawer;

import java.util.ArrayList;

import com.exmyth.wechat.R;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ArrayList<String> menuList;
	private ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_drawaer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

		drawerList = (ListView) findViewById(R.id.leftdrawer);
		menuList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {

			menuList.add("Hello" + i);
		}

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuList);

		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 动态插入一个frament
				drawerList.setItemChecked(position, true);

				setTitle(menuList.get(position));

				Fragment content = new ContentFragment();
				Bundle args = new Bundle();
				args.putString("text", menuList.get(position));
				content.setArguments(args);
				//
				FragmentManager manager = getSupportFragmentManager();
				manager.beginTransaction().replace(R.id.frameLayout, content).commit();
				drawerLayout.closeDrawer(drawerList);
			}
		});

		/*
		// set a custom shadow that overlays the main content when the drawer opens
		// drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close)
        {

            //Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view)
            {

                invalidateOptionsMenu(); // creates call to
                                            // onPrepareOptionsMenu()
            }

            // Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView)
            {

                invalidateOptionsMenu(); // creates call to
                                            // onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // getActionBar().setHomeButtonEnabled(true);
        // Note: getActionBar() Added in API level 11
        */
	}
	
	/*
	@Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
    */
}
