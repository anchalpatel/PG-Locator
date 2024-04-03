package com.example.pgproject;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class CustomDialog {
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    private static final String TAG = "Custom Dialog";
    private ArrayList<PGDetailsModel> pgList;

    // Constructor
    public CustomDialog() {
        pgList = new ArrayList<>();
    }

    public interface DialogListener {
        void applyData(String collegeName, int isBoys, int isGirls, int hasWiFi,
                       int hasWashingMachine, int hasFood, int hasAC);
    }

    public void showDialog(Context context, final DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(view);

        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        CheckBox checkBoxGirls = view.findViewById(R.id.checkBox_girls);
        CheckBox checkBoxBoys = view.findViewById(R.id.checkBox_boys);
        CheckBox checkBoxWiFi = view.findViewById(R.id.checkBox_wifi);
        CheckBox checkBoxWashingMachine = view.findViewById(R.id.checkBox_washingMachine);
        CheckBox checkBoxFood = view.findViewById(R.id.checkBox_food);
        CheckBox checkBoxAC = view.findViewById(R.id.checkBox_ac);

        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                autoCompleteTextView.showDropDown();
            }
        });

        // Call fetchCollegeNames inside showDialog
        fetchCollegeNames(context, autoCompleteTextView);

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            String collegeName = autoCompleteTextView.getText().toString();
            int isBoys = checkBoxBoys.isChecked() ? 1 : 0;
            int isGirls = checkBoxGirls.isChecked() ? 1 : 0;
            int hasWiFi = checkBoxWiFi.isChecked() ? 1 : 0;
            int hasWashingMachine = checkBoxWashingMachine.isChecked() ? 1 : 0;
            int hasFood = checkBoxFood.isChecked() ? 1 : 0;
            int hasAC = checkBoxAC.isChecked() ? 1 : 0;


            listener.applyData(collegeName, isBoys, isGirls, hasWiFi, hasWashingMachine, hasFood, hasAC);
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to fetch college names
    private void fetchCollegeNames(Context context, AutoCompleteTextView autoCompleteTextView) {
        cd = new ConnectionDetector(context);
        isInternetPresent = cd.isConnectingToInternet();
        pgList.clear(); // Clear the list first
        // check for Internet status
        if (isInternetPresent) {
            StringRequest strReq = new StringRequest(Request.Method.GET,
                    EndPoints.LOAD_COLLEGE, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //UPDATE PROPERTIES
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context.getApplicationContext(), android.R.layout.simple_dropdown_item_1line, collegeNames);
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
}
