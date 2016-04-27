package com.crimsonrgames.android.hablandode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.crimsonrgames.android.hablandode.model.HDPublication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private PublicationsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new PublicationsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                DataManager.getInstance(getApplicationContext()).getPublications(new DataManager.HDJSONArraytCallbackListener() {
                    @Override
                    public void onSuccess(JSONArray response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Snackbar.make(view, "Successfully got JSON", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(VolleyError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Snackbar.make(view, "Error getting JSON", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                    }
                });

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        retrievePublicationsFromService();
    }


    private void retrievePublicationsFromService(){
        DataManager.getInstance(getApplicationContext()).getPublications(new DataManager.HDJSONArraytCallbackListener() {
            @Override
            public void onSuccess(final JSONArray response) {
                final List<HDPublication> pubs = new ArrayList<HDPublication>();
                for (int i = 0; i < response.length() ; i++) {
                    try {
                        pubs.add(new HDPublication(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        Log.wtf("MainViewActivity", e.getLocalizedMessage());
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter.setPublications(pubs);
                        mAdapter.notifyDataSetChanged();

                    }
                });
            }

            @Override
            public void onFailure(VolleyError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    class PublicationsAdapter  extends RecyclerView.Adapter<PublicationsAdapter.ViewHolder> {
        private List<HDPublication> publications = new ArrayList<>();
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView mThumbnailImageView;
            public TextView mTitleTextView;
            public TextView mDescriptionTextView;
            public Button mReadButton;
            public ViewHolder(View v) {
                super(v);
                mDescriptionTextView = (TextView) v.findViewById(R.id.description);
                mTitleTextView = (TextView) v.findViewById(R.id.title);
                mThumbnailImageView = (ImageView) v.findViewById(R.id.thumbnail);
                mReadButton = (Button) v.findViewById(R.id.read_button);

            }
        }


        public void setPublications(List<HDPublication> publications) {
            this.publications = publications;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public PublicationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.publication_card_view, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            HDPublication publication = publications.get(position);
            holder.mTitleTextView.setText(publication.getTitle());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return publications.size();
        }
    }
}
