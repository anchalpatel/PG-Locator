package com.example.pgproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgproject.PGDetailsModel;
import com.example.pgproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PgDetailsAdapter extends RecyclerView.Adapter<PgDetailsAdapter.ViewHolder>{

    Context context;
    ArrayList<PGDetailsModel> pgList;

    PgDetailsAdapter(Context context, ArrayList<PGDetailsModel>pgData){
        this.context = context;
        this.pgList = pgData;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pgcard, parent, false);
        int height = (int) 600/* set your desired height */;
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        // Initialize and return the ViewHolder instance
        ViewHolder vHolder = new ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(pgList.get(position).getImage()).into(holder.pgImage);
        holder.pgName.setText(pgList.get(position).pName);
        holder.pgPrice.setText(pgList.get(position).pgPrice + "₹");
        holder.pgAddress.setText(pgList.get(position).pgCollege);
        holder.beds.setText(pgList.get(position).beds + " Beds");
    }



    @Override
    public int getItemCount() {
        return pgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView pgName;
        TextView pgPrice;
        TextView beds;
        TextView pgAddress;
        ImageView pgImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pgImage =itemView.findViewById(R.id.img);
            pgName =itemView.findViewById(R.id.pName);
            pgPrice =itemView.findViewById(R.id.pPrice);
            beds =itemView.findViewById(R.id.pBed);
            pgAddress =itemView.findViewById(R.id.pAddress);
        }
    }
}
