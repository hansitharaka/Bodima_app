package com.example.bodima;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bodima.Model.Place;

import java.util.List;

public class myplaceRecyclerViewAdapter extends RecyclerView.Adapter<myplaceRecyclerViewAdapter.ViewHolder> {

    private List<Place> placeArrayList;

    public myplaceRecyclerViewAdapter(List<Place> placeArrayList) {
        this.placeArrayList = placeArrayList;
    }

    @NonNull
    @Override
    public myplaceRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //instantiate the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myplace_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //load text
        ViewHolder viewHolder =  (ViewHolder) holder;

        Place fecthPlacesData = placeArrayList.get(position);

        viewHolder.title.setText( fecthPlacesData.getTitle() );
        viewHolder.city.setText( fecthPlacesData.getCity() );
        viewHolder.nBeds.setText( String.valueOf(fecthPlacesData.getBeds()) );
        viewHolder.nBaths.setText( String.valueOf(fecthPlacesData.getBaths()) );
        viewHolder.price.setText( String.format("Rs. %s /month", (int)fecthPlacesData.getAmount()) );   //price is formatted
        viewHolder.date.setText( String.format("posted on %s ", fecthPlacesData.getDate()) );           //date is formatted


        //load image
    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            //Variables
            ImageView imageView;
            TextView title, city, nBeds, nBaths, price, date;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                //initialize
//                imageView = itemView.findViewById(R.id.imgPlace);
                title = (TextView) itemView.findViewById(R.id.title);
                city = (TextView) itemView.findViewById(R.id.city);
                nBeds =(TextView)  itemView.findViewById(R.id.cBeds);
                nBaths = (TextView) itemView.findViewById(R.id.cBaths);
                price = (TextView) itemView.findViewById(R.id.placePrice);
                date = (TextView) itemView.findViewById(R.id.date);

            }
        }


}
