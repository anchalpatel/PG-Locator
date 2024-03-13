package com.example.pgproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView search;
    PgDetailsAdapter adapter;
    ArrayList<PGDetailsModel> pgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.pgList);
        search = findViewById(R.id.search);

        // Initialize pgList
        pgList = new ArrayList<>();

        // Initialize adapter
        adapter = new PgDetailsAdapter(getApplicationContext(), pgList, new PgDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click
                PGDetailsModel pgDetails = pgList.get(position);
                Gson gson = new Gson();
                String pgDetailsJson = gson.toJson(pgDetails);

                // Start PgDescriptionActivity and pass the JSON string as an extra
                Intent pgDescription = new Intent(MainActivity.this, PgDescriptionActivity.class);
                pgDescription.putExtra("pgModelJson", pgDetailsJson);
                startActivity(pgDescription);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Fetch data from server
        fetchDataFromServer();
    }

    private void fetchDataFromServer() {
        String url = "http://192.168.51.36/pg/api.php";

        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Toast.makeText(MainActivity.this, "Response received", Toast.LENGTH_SHORT).show();
                            // Clear previous data
                            pgList.clear();
                            // Parse the JSON response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Extract data from JSON object and add to pgList
                                // (Assuming PGDetailsModel constructor and setters are defined appropriately)
                                //Log.d("Image url :", jsonObject.getString("pImage"));
                                PGDetailsModel pgDetails = new PGDetailsModel(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("pgName"),
                                        jsonObject.getString("pgType"),
                                        jsonObject.getString("pgCollege"),
                                        jsonObject.getString("pgUniversity"),
                                        Integer.parseInt(jsonObject.getString("pgPrice").replaceAll("[^0-9]", "")),
                                        jsonObject.getLong("pgNumber"),
                                        jsonObject.getString("pImage"),
                                        jsonObject.getInt("beds"),
                                        jsonObject.getInt("person"),
                                        jsonObject.getInt("food") == 1,
                                        jsonObject.getInt("washingMachine") == 1,
                                        jsonObject.getInt("wifi") == 1,
                                        jsonObject.getInt("ac") == 1
                                );
                                pgList.add(pgDetails);
                            }
                            // Notify adapter of data change
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("Fetch error", "Problem while fetching data " + error);
                        Toast.makeText(MainActivity.this, "Error while fetching data" + error, Toast.LENGTH_SHORT).show();
                    }
                });

        // Add request to the queue
        queue.add(request);
    }
}
