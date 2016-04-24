package fr.nicolasgodefroy.android.ohmybeer.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import fr.nicolasgodefroy.android.ohmybeer.R;
import fr.nicolasgodefroy.android.ohmybeer.fragment.CountryFragment;
import fr.nicolasgodefroy.android.ohmybeer.fragment.ResultFragment;
import fr.nicolasgodefroy.android.ohmybeer.fragment.SearchFragment;
import fr.nicolasgodefroy.android.ohmybeer.listener.CountryListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            onNavigationItemSelected(R.id.nav_country);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStackImmediate();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        return onNavigationItemSelected(id);
    }

    public boolean onNavigationItemSelected(int id) {
        Fragment currentFragment = null;
        if (id == R.id.nav_country) {
            currentFragment = new CountryFragment();
        } else if (id == R.id.nav_search) {
            currentFragment = new SearchFragment();
        } else if (id == R.id.selected_country) {
            currentFragment = new CountryFragment();
            Fragment oldFragment = getFragmentManager().findFragmentById(R.id.container);
            ((CountryFragment) currentFragment).setListener((CountryListener) oldFragment);
        } else if (id == R.id.search_button) {

            currentFragment = new ResultFragment();
            Fragment oldFragment = getFragmentManager().findFragmentById(R.id.container);
            SearchFragment searchFragment = (SearchFragment) oldFragment;
            ((ResultFragment) currentFragment).setBeerList(searchFragment.getBeers());
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.container, currentFragment)
                .addToBackStack(null)
                .commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
