package com.example.bodima;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bodima.Model.ExpenseAdapter;
import com.example.bodima.Model.ExpenseData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ExpensesHistory extends AppCompatActivity {


//    private List<ExpenseData> expenseData;
//    private RecyclerView rv;
//    private ExpenseAdapter adapter;

    List<ExpenseData> expenseData;
    RecyclerView recyclerView;
    ExpenseAdapter adapter;

    DatabaseReference databaseReference;


    TextView AmountView;
    TextView TypeView;
    TextView DateView;

    String Amount;
    String Type_;
    String Date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_history);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        Intent intent = getIntent();
//
//        AmountView = findViewById(R.id.amount_ex_text);
//        TypeView = findViewById(R.id.Type1);
//        DateView = findViewById(R.id.date_expense);
//        get from previous intent
//        AmountView.setText(intent.getStringExtra("AmountEnter"));
//        TypeView.setText(intent.getStringExtra("Type_in"));
//        DateView.setText(intent.getStringExtra("DateAndTime"));
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////







    }

    @Override
    protected void onResume() {
        super.onResume();
        Loaddatafromdatabase();
    }

    //load data from firebase
    public void Loaddatafromdatabase(){

        setContentView(R.layout.activity_expenses_history);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        expenseData=new ArrayList<>();

        databaseReference=FirebaseDatabase.getInstance().getReference("ExpenseManeger");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ExpenseData data = ds.getValue(ExpenseData.class);
                    expenseData .add(data);
                    adapter = new ExpenseAdapter(expenseData);
                    recyclerView.setAdapter(adapter);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}