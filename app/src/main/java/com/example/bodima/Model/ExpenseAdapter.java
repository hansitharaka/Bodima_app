package com.example.bodima.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bodima.ExpensesHistory;
import com.example.bodima.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;


public class ExpenseAdapter<databaseReference> extends RecyclerView.Adapter<ExpenseAdapter.ViewHolderClass> {


    private List<ExpenseData> listdata;


    public ExpenseAdapter(List<ExpenseData> listdata) {
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ex_data_card, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);

        ///////////////////

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;

        ExpenseData fetchdata = listdata.get(position);

        viewHolderClass.amount_ex_text1.setText(fetchdata.getAmount());
        viewHolderClass.date_expense1.setText(fetchdata.getDate());
        viewHolderClass.Type123.setText(fetchdata.getType());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }


    //1

    public static class ViewHolderClass extends RecyclerView.ViewHolder {


        TextView amount_ex_text1, Type123, date_expense1;


        public ViewHolderClass(View itemView) {
            super(itemView);

            amount_ex_text1 = (TextView) itemView.findViewById(R.id.amount_ex_text);
            Type123 = (TextView) itemView.findViewById(R.id.Type1);
            date_expense1 = (TextView) itemView.findViewById(R.id.date_expense);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mclicklistner.OnItemLongClick(view,getAdapterPosition());
                    return false;
                }
            });





        }
        private ViewHolderClass.clickListener mclicklistner;
        ///1
        public interface  clickListener{
            void OnItemLongClick(View view,int position);
        }
        //2
        public void setOnclickListner(ViewHolderClass.clickListener clickListener){
            mclicklistner = clickListener;
        }





    }

//    public void showDeletedetails(String id) {
//        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case DialogInterface.BUTTON_POSITIVE:
//
//                        //Do your Yes progress
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        //Do your No progress
//                        break;
//                }
//            }
//        };
//
//
//    }

    ////////////////////////////////////////////////////////////////////


}
