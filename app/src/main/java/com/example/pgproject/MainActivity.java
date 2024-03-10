package com.example.pgproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.pgList);
        String url = "http://192.168.56.1/pg/api.php";
        ArrayList<PGDetailsModel> pgList = new ArrayList<>();
        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        PgDetailsAdapter adapter = new PgDetailsAdapter(getApplicationContext(), pgList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                // Extract data from JSON object
                                int pgId = Integer.parseInt(jsonObject.getString("pgId"));
                                String pgName = jsonObject.getString("pgName");
                                String pgCollege = jsonObject.getString("pgCollege");
                                String pgUnivercity = jsonObject.getString("pgUnivercity");
                                String pgType = jsonObject.getString("pgType");
                                int pgPrice = Integer.parseInt(jsonObject.getString("pgPrice"));
                                long pgNumber = Long.parseLong(jsonObject.getString("pgNumber"));
                                int beds = Integer.parseInt(jsonObject.getString("beds"));
                                int persons = Integer.parseInt(jsonObject.getString("persons"));
                                boolean food = Boolean.parseBoolean(jsonObject.getString("food"));
                                boolean washingMachine = Boolean.parseBoolean(jsonObject.getString("washingMachine"));
                                boolean wifi = Boolean.parseBoolean(jsonObject.getString("wi-fi"));
                                boolean ac = Boolean.parseBoolean(jsonObject.getString("a.c."));
                                String image = jsonObject.getString("pgImage");

                                PGDetailsModel pgDetails = new PGDetailsModel(pgId, pgName, pgType, pgCollege, pgUnivercity, pgPrice, pgNumber, image, beds, persons, food, washingMachine, wifi, ac);
                                pgList.add(pgDetails);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.d("Fetch error", "Problem while fetching data " + error.getMessage());
                        Toast.makeText(MainActivity.this, "Error while fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the queue
        queue.add(request);
        recyclerView.setAdapter(adapter);

    }
}