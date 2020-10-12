package com.example.bodima;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.bodima.Model.Place;

import java.util.List;

public class myplaceRecyclerViewAdapter extends RecyclerView.Adapter<myplaceRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Place> placeArrayList;
    private OnItemClickListener mListener;
    private String user;

    public myplaceRecyclerViewAdapter(Context context, List<Place> placeArrayList) {
        this.mContext = context;
        this.placeArrayList = placeArrayList;
    }

    //to retrieve logged in user type
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
        ViewHolder viewHolder = (ViewHolder) holder;

        Place fecthPlacesData = placeArrayList.get(position);

        viewHolder.title.setText(fecthPlacesData.getTitle());
        viewHolder.city.setText(fecthPlacesData.getCity());
        viewHolder.nBeds.setText(fecthPlacesData.getBeds());
        viewHolder.nBaths.setText(fecthPlacesData.getBaths());
        viewHolder.price.setText(String.format("Rs. %s /month", fecthPlacesData.getAmount()));   //price is formatted
        viewHolder.date.setText(String.format("posted on %s ", fecthPlacesData.getDate()));      //date is formatted

        //load image
        Glide.with(mContext)
                .load(fecthPlacesData.getImgUrl())
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        //Variables
        ImageView imageView;
        TextView title, city, nBeds, nBaths, price, date;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //initialize
            imageView = itemView.findViewById(R.id.imgPlace);
            title = (TextView) itemView.findViewById(R.id.title);
            city = (TextView) itemView.findViewById(R.id.city);
            nBeds = (TextView) itemView.findViewById(R.id.cBeds);
            nBaths = (TextView) itemView.findViewById(R.id.cBaths);
            price = (TextView) itemView.findViewById(R.id.placePrice);
            date = (TextView) itemView.findViewById(R.id.date);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

            edit.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }

            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {

                    //only sellers get access to menu options
                    if(user.equals("seller") ) {
                        switch (item.getItemId()) {
                            case 1:
                                mListener.onEditClick(position);
                                return true;
                            case 2:
                                mListener.onDeleteClick(position);
                                return true;
                        }
                    } else {
                        switch ( (item.getItemId()) ) {
                            case 1:
                            case 2:
                                item.setEnabled(false);
                                
                                return false;
                        }
                    }

                }
            }
            return false;
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


}