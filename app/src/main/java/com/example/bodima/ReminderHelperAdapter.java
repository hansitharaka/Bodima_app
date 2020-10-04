package com.example.bodima;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bodima.Model.Reminders;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static androidx.core.content.ContextCompat.getSystemService;


public class ReminderHelperAdapter extends RecyclerView.Adapter {

    List<Reminders> remindersList;
    private OnItemClickListener itemClick;


    public ReminderHelperAdapter(List<Reminders> remindersList){
        this.remindersList = remindersList;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_layout, parent, false);
        viewHolderClass viewHolderClass = new viewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        viewHolderClass viewHolderClass = (ReminderHelperAdapter.viewHolderClass)holder;
        Reminders reminders = remindersList.get(position);
        Reminders fetchData = remindersList.get(position);

        viewHolderClass.description.setText(fetchData.getDescription());
        viewHolderClass.day.setText(fetchData.getDay());
        viewHolderClass.month.setText(fetchData.getMonth());
        viewHolderClass.amount.setText(fetchData.getAmount());
    }


    @Override
    public int getItemCount() {
        return remindersList.size();
    }

    public class viewHolderClass extends RecyclerView.ViewHolder
        implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener
    {

        TextView description;
        TextView month;
        TextView day;
        TextView amount;

        public viewHolderClass(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.desclabel);
            day = itemView.findViewById(R.id.day);
            month = itemView.findViewById(R.id.month);
            amount = itemView.findViewById(R.id.amount);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");

            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(itemClick != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    itemClick.onDeleteClick(position);
                    return true;
                }
            }
            return  false;
        }
    }

    public  interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        itemClick = listener;
    }
}
