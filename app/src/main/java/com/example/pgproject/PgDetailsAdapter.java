package com.example.pgproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PgDetailsAdapter extends RecyclerView.Adapter<PgDetailsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PGDetailsModel> pgList;
    private OnItemClickListener listener;

    public  interface OnItemClickListener{
        void onItemClick(int position);
    }
    public PgDetailsAdapter(Context context, ArrayList<PGDetailsModel> pgList) {
        this.context = context;
        this.pgList = pgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pgcard, parent, false);
        int height = (int) 800/* set your desired height */;
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if(position!=RecyclerView.NO_POSITION && listener!=null){
                    listener.onItemClick(position);
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PGDetailsModel pgDetails = pgList.get(position);
        holder.pgName.setText(pgDetails.getpName());
        holder.pgPrice.setText(String.valueOf(pgDetails.getPgPrice()) + " ₹");
        holder.pgAddress.setText(pgDetails.getPgCollege());
        holder.beds.setText(String.valueOf(pgDetails.getBeds()) + " Beds");
        Glide.with(context)
                .load(pgDetails.getImage())
                .into(holder.pgImage);

//        Picasso.get().load(pgDetails.getImage()).into(holder.pgImage, new Callback() {
//            @Override
//            public void onSuccess() {
//                // Image loaded successfully
//                Log.d("Picasso", "Image loaded successfully");
//            }
//
//            @Override
//            public void onError(Exception e) {
//                // Handle error
//                Log.e("Picasso", "Error loading image: " + e.getMessage());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return pgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pgImage;
        TextView pgName;
        TextView pgPrice;
        TextView beds;
        TextView pgAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pgImage = itemView.findViewById(R.id.img);
            pgName = itemView.findViewById(R.id.pName);
            pgPrice = itemView.findViewById(R.id.pPrice);
            beds = itemView.findViewById(R.id.pBed);
            pgAddress = itemView.findViewById(R.id.pAddress);
        }
    }
}
