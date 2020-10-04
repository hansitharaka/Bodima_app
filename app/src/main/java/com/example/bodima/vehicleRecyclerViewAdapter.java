package com.example.bodima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bodima.Model.Vehicle;

import java.util.List;

public class vehicleRecyclerViewAdapter extends RecyclerView.Adapter<vehicleRecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<Vehicle> vehicleArrayList;

    public vehicleRecyclerViewAdapter(Context context, List<Vehicle> vehicleArrayList){
        this.mContext =context;
        this.vehicleArrayList = vehicleArrayList;

    }

    @NonNull
    @Override
    public vehicleRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //instenciate the view //get the data and put in here
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //load text
        ViewHolder viewHolder = (ViewHolder) holder;

        Vehicle fetchVehicleData = vehicleArrayList.get(position);
        //put the data into the layout
        viewHolder.Title.setText(fetchVehicleData.getTitle());
        viewHolder.City.setText(fetchVehicleData.getCity());
        viewHolder.Amount.setText(String.format("Rs. %s ",fetchVehicleData.getAmount()));
        viewHolder.Type.setText(fetchVehicleData.getType());

        //load image
        Glide.with(mContext)
                .load(fetchVehicleData.getImgUrl())
                .into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return vehicleArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        TextView Title,City,Amount,Type;
        //image add
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //image view
            imageView = itemView.findViewById(R.id.imgVehicleAd);
            Title=itemView.findViewById(R.id.vehicleTitle);
            City=itemView.findViewById(R.id.vehicleCity);
            Type=itemView.findViewById(R.id.vehcileType);
            Amount=itemView.findViewById(R.id.VehiclePrice);


        }

    }


}
