package com.example.pgproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private AutoCompleteTextView autoCompleteTextView;
    private PgDetailsAdapter adapter;
    private ArrayList<PGDetailsModel> pgList;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private boolean isScrolling = false;
    private int currentPage = 0;
    private int itemsPerPage = 3;

    private int isGirls = 0;
    private int isBoys = 0;
    private String collegeName;
    CheckBox isBoysLay;
    CheckBox isGirlsLay;
    ImageView search;
    TextView centerTextView;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    TextView noData;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initViews();
        context = this;
        fetchDataFromServer(currentPage, "$", "$", "$", "$", "$", "$", "$");
        fetchCollegeNames();
    }

    private void initViews() {
//        progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);
        search = findViewById(R.id.search);
        centerTextView = findViewById(R.id.centerTextView);
        //noData = findViewById(R.id.noData);

        recyclerView = findViewById(R.id.pgList);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        pgList = new ArrayList<>();
        adapter = new PgDetailsAdapter(this, pgList, position -> {
            PGDetailsModel pgDetails = pgList.get(position);
            Gson gson = new Gson();
            String pgDetailsJson = gson.toJson(pgDetails);
            Intent pgDescription = new Intent(MainActivity.this, PgDescriptionActivity.class);
            pgDescription.putExtra("pgModelJson", pgDetailsJson);
            startActivity(pgDescription);
        });

        autoCompleteTextView.setOnClickListener(v -> autoCompleteTextView.showDropDown());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    isScrolling = true;
                }
            }

         @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();


            }
        });
//        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                autoCompleteTextView.showDropDown();
//            }
//        });
//
//        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
//            String selectedCollege = (String) parent.getItemAtPosition(position);
//            isBoysLay = findViewById(R.id.checkBoxBoys);
//            isGirlsLay = findViewById(R.id.checkBoxGirls);
//
//            isBoys = isBoysLay.isChecked() ? 1 : 0;
//            isGirls = isGirlsLay.isChecked() ? 1 : 0;
//
//            try {
//                fetchDataForCollege(selectedCollege,String.valueOf(isBoys), String.valueOf(isGirls));
//            } catch (UnsupportedEncodingException e) {
//                throw new RuntimeException(e);
//            }
//        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Called", Toast.LENGTH_SHORT).show();
                isBoysLay = findViewById(R.id.checkBoxBoys);
                isGirlsLay = findViewById(R.id.checkBoxGirls);

//                isBoys = isBoysLay.isChecked() ? 1 : 0;
//                isGirls = isGirlsLay.isChecked() ? 1 : 0;
//                String searchText = autoCompleteTextView.getText().toString();
//                collegeName = searchText;
//                if (!TextUtils.isEmpty(searchText)) {
//                    try {
//                        fetchDataForCollege(searchText,String.valueOf(isBoys), String.valueOf(isGirls));
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                        Toast.makeText(MainActivity.this, "Error in search query", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
//                }
                CustomDialog dialog = new CustomDialog();
                dialog.showDialog(MainActivity.this, new CustomDialog.DialogListener() {
                    @Override
                    public void applyData(String collegeName, int isBoys, int isGirls, int hasWiFi,
                                          int hasWashingMachine, int hasFood, int hasAC) {
                        String isBoysStr = isBoys == 1 ? "1" : "0";
                        String isGirlsStr = isGirls == 1 ? "1" : "0";
                        String isWifi = hasWiFi == 1 ? "1" : "0";
                        String isWashingMachine = hasWashingMachine == 1 ? "1" : "0";
                        String isAc = hasAC == 1 ? "1" : "0";
                        String isFood = hasFood == 1 ? "1" : "0";
                        fetchDataFromServer(1, collegeName, isBoysStr, isGirlsStr, isWifi, isWashingMachine, isAc, isFood);
                        // Use the data as needed
                        //Log.d(TAG, "collegeName" + collegeName + "isBoys" + isBoys + "isGirls" + isGirls + "isWifi" + hasWiFi + "isAC" + hasAC + "isWM" + hasWashingMachine + "isFood" + hasFood);
                    }
                });

            }
        });

    }

    private void fetchDataFromServer(int page, String collegeName, String isBoys, String isGirl, String hasWifi, String hasWashingMachine, String hasAcm, String hasFood) {
        cd = new ConnectionDetector(context);
        isInternetPresent = cd.isConnectingToInternet();
        if(!isInternetPresent){
            noData.setText(R.string.noInternet);
            noData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        pgList.clear(); // Clear the list first
        // check for Internet status
        if (isInternetPresent) {
            StringRequest strReq = new StringRequest(Request.Method.GET,
                    EndPoints.LOAD_DATA + "/" + collegeName + "/" + isBoys + "/" + isGirls, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //UPDATE PROPETIES
                    try {

                        JSONObject obj = new JSONObject(response);
                        if(obj.length()==0){
                            noData.setText(R.string.noDataFound);
                            noData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            pgList.clear();
                        }
                        else {
                            // check for error flag
                            if (obj.getBoolean("error") == false) {
                                //Get random_users And show it in Top Horizontal View
                                JSONArray random_usersarray = obj.getJSONArray("random_users");


                                for (int i = 0; i < random_usersarray.length(); i++) {
                                    JSONObject randomuserObj = (JSONObject) random_usersarray.get(i);
                                    PGDetailsModel pgDetails = parseJsonToPGDetailsModel(randomuserObj);
                                    pgList.add(pgDetails);
                                }
                                adapter.notifyDataSetChanged();


                            } else {
                                // error in fetching chat rooms
                                Toast.makeText(context, "Check Internet Connection.#1", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (JSONException e)
                    {
                        Toast.makeText(context, "Check Internet Connection.#2" + e.toString(), Toast.LENGTH_SHORT).show();
                    }


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    Log.v("Error",""+ error.toString());
                    Toast.makeText(context, "Check Internet Connection.#3" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }

                ;
            };
            // disabling retry policy so that it won't make
            // multiple http calls
            int socketTimeout = 0;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

            strReq.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
            //Adding request to request queue
            MyApplication.getInstance().addToRequestQueue(strReq);

        } else {
            Toast.makeText(context, "Check Internet connection & Retry.", Toast.LENGTH_SHORT).show();
        }
    }



    private PGDetailsModel parseJsonToPGDetailsModel(JSONObject jsonObject) throws JSONException {
        String pgPriceString = jsonObject.getString("pgPrice");

        return new PGDetailsModel(
                jsonObject.getString("pgId"),
                jsonObject.getString("pgName"),
                jsonObject.getString("forBoys"),
                jsonObject.getString("forGirls"),
                jsonObject.getString("pgCollege"),
                jsonObject.getString("pgUniversity"),
                jsonObject.getString("pgPrice"),
                jsonObject.getString("pgAddress"),
                jsonObject.getString("pgNumber"),
                jsonObject.getString("pImage"),
                jsonObject.getString("person"),
                jsonObject.getString("Food"),
                jsonObject.getString("washingMachine") ,
                jsonObject.getString("ac"),
                jsonObject.getString("wifi"),
                jsonObject.getString("singleBed"),
                jsonObject.getString("doubleBed"),
                jsonObject.getString("multipleBed")
        );
    }

    private void fetchCollegeNames() {
        cd = new ConnectionDetector(context);
        isInternetPresent = cd.isConnectingToInternet();
        pgList.clear(); // Clear the list first
        // check for Internet status
        if (isInternetPresent) {
            StringRequest strReq = new StringRequest(Request.Method.GET,
                    EndPoints.LOAD_COLLEGE, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //UPDATE PROPETIES
                    try {
                        JSONObject obj = new JSONObject(response);
                        // check for error flag
                        if (!obj.getBoolean("error")) {
                            //Get random_users And show it in Top Horizontal View
                            JSONArray random_usersarray = obj.getJSONArray("random_users");

                            List<String> collegeNames = new ArrayList<>();
                            for (int i = 0; i < random_usersarray.length(); i++) {
                                JSONObject randomuserObj = random_usersarray.getJSONObject(i);
                                String collegeName = randomuserObj.getString("collegeName");
                                collegeNames.add(collegeName);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, collegeNames);
                            autoCompleteTextView.setAdapter(adapter);
                        } else {
                            // error in fetching chat rooms
                            Toast.makeText(context, "Error: " + obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(context, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error fetching college names: " + error.getMessage());
                    Toast.makeText(context, "Error fetching college names: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            //Adding request to request queue
            MyApplication.getInstance().addToRequestQueue(strReq);
        } else {
            Toast.makeText(context, "Check Internet connection & Retry.", Toast.LENGTH_SHORT).show();
        }
    }


    private List<String> parseCollegeNames(String response) {

        List<String> collegeNames = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(response);

            for (int i = 0; i < array.length(); i++) {
                JSONObject collegeDetail = array.getJSONObject(i);
                String collegeName = collegeDetail.getString("collegeName");
                collegeNames.add(collegeName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("College Fetch Error", e.getMessage());
        }

        return collegeNames;
    }

//    private void fetchDataForCollege(String collegeName, String isBoys, String isGirls) throws UnsupportedEncodingException {
//        fetchDataFromServer(currentPage, collegeName,  isBoys,  isGirls);
//    }
}