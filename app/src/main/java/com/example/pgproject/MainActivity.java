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
import com.android.volley.toolbox.JsonArrayRequest;
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
        String url = "http://192.168.43.42/pg/api.php";
        ArrayList<PGDetailsModel> pgList = new ArrayList<>();

        // Create a request queue


        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Toast.makeText(MainActivity.this, "Response received", Toast.LENGTH_SHORT).show();
                            // Parse the JSON response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Extract data from JSON object
                                int pgId = jsonObject.getInt("id");
                                String pgName = jsonObject.getString("pgName");
                                String pgType = jsonObject.getString("pgType");
                                String pgCollege = jsonObject.getString("pgCollege");
                                String pgUniversity = jsonObject.getString("pgUniversity");

                                // Parse pgPrice as String and extract numeric part
                                String pgPriceString = jsonObject.getString("pgPrice");
                                // Remove currency symbol and commas, then parse as int
                                int pgPrice = Integer.parseInt(pgPriceString.replaceAll("[^0-9]", ""));

                                long pgNumber = jsonObject.getLong("pgNumber");
                                int beds = jsonObject.getInt("beds");
                                int persons = jsonObject.getInt("person");
                                boolean food = jsonObject.getInt("food") == 1;
                                boolean washingMachine = jsonObject.getInt("washingMachine") == 1;
                                boolean wifi = jsonObject.getInt("wifi") == 1;
                                boolean ac = jsonObject.getInt("ac") == 1;
                                String image = jsonObject.getString("pImage");
                                // Create PGDetailsModel object and add to pgList
                                PGDetailsModel pgDetails = new PGDetailsModel(pgId, pgName, pgType, pgCollege, pgUniversity, pgPrice, pgNumber, image, beds, persons, food, washingMachine, wifi, ac);
                                pgList.add(pgDetails);
                            }

                            // Notify adapter or update UI here with the new data

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

        queue.add(request);
        PgDetailsAdapter adapter = new PgDetailsAdapter(getApplicationContext(), pgList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
