package com.example.bodima;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class houseRecyclerViewAdapter extends RecyclerView.Adapter<houseRecyclerViewAdapter.ViewHolder> {

    private ArrayList<House> houseArrayList;

    public houseRecyclerViewAdapter(ArrayList<House> houseArrayList){
       this.houseArrayList =houseArrayList;

    }

    @NonNull
    @Override
    public houseRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //instenciate the view //get the data and put in here
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.house_layout,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //load text
        ViewHolder viewHolder =  (ViewHolder) holder;

        House fetchHousesData = houseArrayList.get(position);

        viewHolder.Title.setText(fetchHousesData.getTitle());
        viewHolder.City.setText(fetchHousesData.getCity());
        viewHolder.Amount.setText( String.format("Rs. %s /month", fetchHousesData.getAmount()));
        viewHolder.BedsNo.setText(String.valueOf(fetchHousesData.getBeds()));
        viewHolder.BathsNo.setText(String.valueOf(fetchHousesData.getBaths()));


        //load image

    }

    @Override
    public int getItemCount() {
        return houseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        TextView Title,City,Amount,BedsNo,BathsNo,HouseSize,LandSize,Description,Name,Phone;
        //image add

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //image view
            Title=itemView.findViewById(R.id.houseTitle);
            City=itemView.findViewById(R.id.houseCity);
            Amount=itemView.findViewById(R.id.housePrice);
            BedsNo=itemView.findViewById(R.id.beds2);
            BathsNo=itemView.findViewById(R.id.baths2);



        }
    }

}
