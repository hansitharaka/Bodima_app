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

import java.util.ArrayList;
import java.util.List;

public class landRecyclerViewAdapter extends RecyclerView.Adapter<landRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Land> landArrayList;

    public landRecyclerViewAdapter(Context context,List<Land> landArrayList){
        this.mContext =context;
        this.landArrayList = landArrayList;

    }

    @NonNull
    @Override
    public landRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //instenciate the view //get the data and put in here
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.land_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //load text
        ViewHolder viewHolder =  (ViewHolder) holder;

        Land fetchLandData = landArrayList.get(position);
        //put the data into the layout
        viewHolder.Title.setText(fetchLandData.getTitle());
        viewHolder.City.setText(fetchLandData.getCity());
        viewHolder.Amount.setText( String.format("Rs. %s ", fetchLandData.getAmount()));
        viewHolder.LandSize.setText(fetchLandData.getLandSize());
        //load image

        Glide.with(mContext)
                .load(fetchLandData.getImgUrl())
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {

        return landArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        TextView Title,City,Amount,LandSize;
        //TODO:image add
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //image view
            imageView = itemView.findViewById(R.id.imgLandAd);
            Title=itemView.findViewById(R.id.landTitle);
            City=itemView.findViewById(R.id.landCity);
            Amount=itemView.findViewById(R.id.landPrice);
            LandSize=itemView.findViewById(R.id.landSize);

        }
    }
}
