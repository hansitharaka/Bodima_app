package com.example.bodima;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class houseRecyclerViewAdapter extends RecyclerView.Adapter<houseRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<House> houseArrayList;
    private OnItemClickListener mListener;

    public houseRecyclerViewAdapter(Context context, List<House> houseArrayList) {
        this.mContext = context;
        this.houseArrayList = houseArrayList;

    }

    @NonNull
    @Override
    public houseRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //instenciate the view //get the data and put in here
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.house_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //load text
        ViewHolder viewHolder = (ViewHolder) holder;

        House fetchHousesData = houseArrayList.get(position);

        viewHolder.Title.setText(fetchHousesData.getTitle());
        viewHolder.City.setText(fetchHousesData.getCity());
        viewHolder.Amount.setText(String.format("Rs. %s /month", fetchHousesData.getAmount()));
        viewHolder.BedsNo.setText(String.valueOf(fetchHousesData.getBeds()));
        viewHolder.BathsNo.setText(String.valueOf(fetchHousesData.getBaths()));


        //load image
        Glide.with(mContext)
                .load(fetchHousesData.getImgUrl())
                .into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return houseArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        //widgets
        TextView Title, City, Amount, BedsNo, BathsNo;
        //image add
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //image view
            imageView = itemView.findViewById(R.id.imgHouseAd);
            Title = itemView.findViewById(R.id.houseTitle);
            City = itemView.findViewById(R.id.houseCity);
            Amount = itemView.findViewById(R.id.housePrice);
            BedsNo = itemView.findViewById(R.id.beds2);
            BathsNo = itemView.findViewById(R.id.baths2);

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
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onEditClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
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
