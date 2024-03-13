package com.example.pgproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class PgDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_description);

        String pgDetailsJson = getIntent().getStringExtra("pgModelJson");

        // Check if pgDetailsJson is not null
        if (pgDetailsJson != null) {
            // Deserialize the JSON string back to PGDetailsModel object using Gson
            Gson gson = new Gson();
            PGDetailsModel pgDetails = gson.fromJson(pgDetailsJson, PGDetailsModel.class);

            TextView pgNameTextView = findViewById(R.id.pgname);
            pgNameTextView.setText(pgDetails.getpName());

           ImageView pgImage = findViewById(R.id.pgimage);
            Glide.with(this)
                    .load(pgDetails.getImage())
                    .into(pgImage);

            TextView pgPrice = findViewById(R.id.pg_rent);
            pgPrice.setText(pgDetails.getPgPrice());
//
//            TextView pgAddress = findViewById(R.id.pg_add);
//            pgAddress.setText(pgDetails.getPgAddress());

//            TextView wifi = findViewById(R.id.wifi);
//            if(!pgDetails.isWifi()){
//                wifi.setVisibility(View.INVISIBLE);
//                wifi.setVisibility(View.GONE);
//            }
//
//            TextView ac = findViewById(R.id.AC);
//            if(!pgDetails.isAc()){
//                ac.setVisibility(View.INVISIBLE);
//                ac.setVisibility(View.GONE);
//            }
//
//            TextView tiffin = findViewById(R.id.tiffin);
//            if(!pgDetails.isFood()){
//                tiffin.setVisibility(View.INVISIBLE);
//                tiffin.setVisibility(View.GONE);
//            }
//
//            TextView laundry = findViewById(R.id.laundry);
//            if(!pgDetails.isWashingMachine()){
//                laundry.setVisibility(View.INVISIBLE);
//                laundry.setVisibility(View.GONE);
//            }
//
//            TextView phoneNumber = findViewById(R.id.phone_no);
//            phoneNumber.setText((int) pgDetails.getPgNumber());



        }
}
}