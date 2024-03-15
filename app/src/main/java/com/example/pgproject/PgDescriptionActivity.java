package com.example.pgproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

            TextView price = findViewById(R.id.pg_rent);
            price.setText(String.valueOf(pgDetails.getPgPrice()));

            TextView pgAddress = findViewById(R.id.pg_add);
            pgAddress.setText(pgDetails.getPgAddress());

            // Handle visibility of TextViews based on pgDetails properties

            if (!pgDetails.getWifi().equals("1")) {
                LinearLayout wiContainer = findViewById(R.id.wifiContainer);
                wiContainer.setVisibility(View.GONE); // Set visibility to GONE to remove the view from the layout
            }

            if (!pgDetails.getAc().equals("1")) {
                LinearLayout acContainer = findViewById(R.id.acContainer);
                acContainer.setVisibility(View.GONE);
            }

            if (!pgDetails.getFood().equals("1")) {
                LinearLayout tiffinContainer = findViewById(R.id.foodContainer);
                tiffinContainer.setVisibility(View.GONE);
            }

            if (!pgDetails.getWashingMachine().equals("1")) {
                LinearLayout laundryContainer = findViewById(R.id.laundryContainer);
                laundryContainer.setVisibility(View.GONE);
            }
            if (pgDetails.getForBoys().equals("1")) {
                LinearLayout forBoys = findViewById(R.id.forBoys);
                forBoys.setVisibility(View.GONE);
            }

            if (pgDetails.getForGirls().equals("1")) {
                LinearLayout forGirls = findViewById(R.id.forGirls);
                forGirls.setVisibility(View.GONE);
            }

            if (pgDetails.getSingleBed().equals("1")) {
                CardView singleBed = findViewById(R.id.single);
                singleBed.setVisibility(View.GONE);
            }

            if (pgDetails.getForGirls().equals("1")) {
                CardView goubleBed = findViewById(R.id.double_sharing);
                goubleBed.setVisibility(View.GONE);
            }

            if (pgDetails.getMultipleBed().equals("1")) {
                CardView multipleBed = findViewById(R.id.four_sharing);
                multipleBed.setVisibility(View.GONE);
            }


            TextView phoneNumber = findViewById(R.id.phone_no);
            phoneNumber.setText(String.valueOf(pgDetails.getPgNumber()));

            TextView address = findViewById(R.id.pgAddress);
            address.setText(pgDetails.getPgAddress());

            Button call = findViewById(R.id.call);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + String.valueOf(pgDetails.getPgNumber())));
                    startActivity(intent);
                }
            });
        }
    }
}