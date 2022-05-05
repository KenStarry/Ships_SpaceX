package com.example.shipsspacex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ShipsRecyclerInterface{

    private RecyclerView recyclerView;
    private TextView shipTotal;

    private ShipsRecyclerAdapter shipsRecyclerAdapter;
    private ArrayList<ShipsModalClass> shipsModalClassArrayList;

    //  url for querying our data
    String url = "https://api.spacexdata.com/v3/ships";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Setting default tooblar
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        shipsModalClassArrayList = new ArrayList<>();

        getData();
    }

    //  calling oncreate option menu to inflate our menu file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  Menu inflater
        MenuInflater inflater = getMenuInflater();

        //  Inflating our menu file in the inflater
        inflater.inflate(R.menu.search_view_menu, menu);

        //  Getting our menu item
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        //  Getting search view of our menu item
        SearchView searchView = (SearchView) searchItem.getActionView();

        //  Call set onquery text listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  Here we are calling a method to filter our recyclerview
                filterRecycler(newText);
                return false;
            }
        });
        return true;
    }

    private void filterRecycler(String text) {
        //  Creating a new Arraylist to filter our data
        ArrayList<ShipsModalClass> filteredList = new ArrayList<>();

        //  For loop to compare the elements
        for (ShipsModalClass item : shipsModalClassArrayList) {

            //  Check if entered string matches with our recyclerview
            if(item.getShip_name().toLowerCase().contains(text.toLowerCase())) {
                //  If the item matches, add it to our filtered list
                filteredList.add(item);
            }

            //  If filtered list is empty, display a toast
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
            } else {
                //  Now pass the filtered list to the adapter so it can be displayed in the recycler view
                shipsRecyclerAdapter.filterList(filteredList);
            }
        }
    }

    public void getData() {
        //  New variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        //  Since we are requesting an array, we need to make an arrayRequest and extract an object from it
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //  Remove the progress bar after a response has been gotten
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                for (int i = 0; i < response.length(); i++) {

                    //  Creating a new JSON Object and getting each object from our JSONArray
                    try {

                        JSONObject response_obj = response.getJSONObject(i);

                        //  Now we can get our response from the API in JSON Object Format.
                        //  We will be extracting a string with its key value from our JSON Object.
                        String ship_status_api = response_obj.getString("status");
                        String ship_name_api = response_obj.getString("ship_name");
                        String ship_type_api = response_obj.getString("ship_type");
                        String ship_image_api = response_obj.getString("image");

                        //  Add all this data to the model then add it all to the arraylist
                        shipsModalClassArrayList.add(
                                //  Adding data from the modal class
                                new ShipsModalClass(ship_status_api, ship_name_api, ship_type_api, ship_image_api)
                        );

                        //  Set the total number of ships after getting the data
                        shipTotal = findViewById(R.id.shipTotal);
                        shipTotal.setText(new StringBuilder().append("Total Ships : ").append(shipsModalClassArrayList.size()));

                        //  Building our recycler view
                        buildRecyclerView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //  Add the request to the queue
        queue.add(jsonArrayRequest);
    }

    public void buildRecyclerView() {
        //  Initializing our adapter class
        shipsRecyclerAdapter = new ShipsRecyclerAdapter(shipsModalClassArrayList, MainActivity.this, this);

        //  Adding layout Manager to our recyclerview
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        //  Finally set the layoutmanager and adapter
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(shipsRecyclerAdapter);
    }

    @Override
    public void onItemClick(int position) {

        ShipDetailsBottomSheet bottomSheet = new ShipDetailsBottomSheet();


        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject clicked_ship = response.getJSONObject(position);

                    String ship_name_api = clicked_ship.getString("ship_name");
                    String ship_id_api = clicked_ship.getString("ship_id");
                    String ship_model_api = clicked_ship.getString("ship_model");
                    String ship_type_api = clicked_ship.getString("ship_type");
                    boolean active_api = clicked_ship.getBoolean("active");
//                    int imo_api = clicked_ship.getInt("imo");
//                    int mmsi_api = clicked_ship.getInt("mmsi");
//                    int abs_api = clicked_ship.getInt("abs");
//                    int class_api = clicked_ship.getInt("class");
                    String weight_lbs_api = clicked_ship.getString("weight_lbs");
                    String weight_kg_api = clicked_ship.getString("weight_kg");
//                    int year_api = clicked_ship.getInt("year");
                    String home_port_api = clicked_ship.getString("home_port");
                    String status_api = clicked_ship.getString("status");
                    String speed_kn_api = clicked_ship.getString("speed_kn");
                    String course_deg_api = clicked_ship.getString("course_deg");
                    String ship_image_api = clicked_ship.getString("image");

                    //  Sending Data to Bottom Sheet
                    Bundle data = new Bundle();

                    data.putString("ShipNameData", ship_name_api);
                    data.putString("ShipIDData", ship_id_api);
                    data.putString("ShipModelData", ship_model_api);
                    data.putString("ShipTypeData", ship_type_api);
                    data.putBoolean("ActiveData", active_api);
//                    data.putInt("ImoData", imo_api);
//                    data.putInt("MmsiData", mmsi_api);
//                    data.putInt("AbsData", abs_api);
//                    data.putInt("ClassData", class_api);
                    data.putString("WeightLbsData", weight_lbs_api);
                    data.putString("WeightKgData", weight_kg_api);
//                    data.putInt("YearData", year_api);
                    data.putString("HomePortData", home_port_api);
                    data.putString("StatusData", status_api);
                    data.putString("SpeedKnData", speed_kn_api);
                    data.putString("CourseDegData", course_deg_api);
                    data.putString("ImageData", ship_image_api);

                    bottomSheet.setArguments(data);

                    bottomSheet.show(getSupportFragmentManager(), "Ship Details BottomSheet");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}