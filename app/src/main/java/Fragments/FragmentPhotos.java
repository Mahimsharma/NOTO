package Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Assignment3.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import Adapters.ImageAdapter;
import Models.ImageEntity;
import Utils.ImageDao;
import Utils.ImageDatabase;
import Utils.LogInHandler;
import Utils.NetworkUtility;

public class FragmentPhotos extends Fragment {

    private ActionBar actionBar;
    private List<ImageEntity> imageList;
    private ImageAdapter adapter;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    public static int layoutSpan;
    private String currentQuery;
    private int currentPage;
    private  String per_page="20";
    private ProgressBar progressBar;
    private ImageDao imageDao;
    private LogInHandler logInHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageDao = ImageDatabase.getInstance(getContext()).getImageDao();
        imageList = imageDao.getAll();
        currentQuery = "random";
        logInHandler = LogInHandler.getInstance(getContext());
        layoutSpan = logInHandler.getLayoutSpan();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photos, container, false);
        Toolbar toolbar =  v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Photos");
        setHasOptionsMenu(true);

        progressBar = v.findViewById(R.id.progressBar);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), layoutSpan));
        requestQueue = Volley.newRequestQueue(getContext());
        if(NetworkUtility.getConnectivityStatus(getContext())==0)
        {
            Toast.makeText(getContext(),"No Connection!",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            adapter = new ImageAdapter(getContext(),imageList);
            recyclerView.setAdapter(adapter);
        }
        else{
        searchFlickr(currentQuery);
        }

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int dy = scrollY - oldScrollY;
                if (dy > 0) { // only when scrolling up

                    final int visibleThreshold = 2;

                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int currentTotalCount = layoutManager.getItemCount();

                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(NetworkUtility.getConnectivityStatus(getContext()) != 0)
                                    searchFlickr(currentQuery);
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(),"No Connection!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 1000);

                    }
                }
            }
        });
        return v;

    }

    @Override
    public void onResume() {

        layoutSpan = logInHandler.getLayoutSpan();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),layoutSpan));
        recyclerView.setAdapter(adapter);
        super.onResume();
    }

    private void searchFlickr(String tag)
    {
        if(tag.equals(currentQuery)) {
            currentPage++;}
        else{
            currentQuery = tag;
            currentPage = 1;
            imageDao.delete();
            imageList.clear();
        }

        String url = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=e44c5252a0f90974cff57177110fdc32&tags="+tag+"&per_page="+per_page+"&page="+String.valueOf(currentPage)+"&format=json&nojsoncallback=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("photos");
                    int totalPages = response.getInt("pages");
                    if (currentPage <= totalPages) {
                        JSONArray jsonArray = response.getJSONArray("photo");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject hit = jsonArray.getJSONObject(i);
                            String serverID = hit.getString("server");
                            String photoID = hit.getString("id");
                            String secret = hit.getString("secret");
                            String title = hit.getString("title");
                            String imgURL = "https://live.staticflickr.com/" + serverID + "/" + photoID + "_" + secret + "_w.jpg";
                            imageList.add(new ImageEntity(imgURL, title));
                        }
                        progressBar.setVisibility(View.GONE);
                        imageDao.insert(imageList);
                        if(currentPage==1)
                        {
                            adapter = new ImageAdapter(getContext(),imageList);
                            recyclerView.setAdapter(adapter);
                        }
                        else{
                            adapter.addCards(imageList);
                        }
                    }
                    else{
                        Toast.makeText(getContext(),"Couldn't find more images! ",Toast.LENGTH_SHORT);
                    }
                }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
                requestQueue.add(request);
    }



    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pop_menu, menu);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                return true;
            }
        };

        MenuItem searchMenu =  menu.findItem(R.id.search);
        searchMenu.setOnActionExpandListener(onActionExpandListener);
        SearchView searchView =(SearchView) searchMenu.getActionView();
        searchView.setQueryHint("Search Query");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(NetworkUtility.getConnectivityStatus(getContext()) != 0)
                {
                    searchFlickr(query);
                Toast.makeText(getActivity(),"Searching for "+ query,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"No Connection!",Toast.LENGTH_SHORT).show();

                }
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                searchMenu.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.one :
                layoutSpan=1;
                break;
            case R.id.two :
                layoutSpan=2;
                break;
            case R.id.three :
                layoutSpan=3;
                break;
            case R.id.four :
                layoutSpan=4;
                break;
            default:
                break;
        }
        logInHandler.setLayoutSpan(layoutSpan);
        onResume();
        return true;

    }

}