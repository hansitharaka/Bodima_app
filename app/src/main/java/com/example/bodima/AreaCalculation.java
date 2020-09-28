package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AreaCalculation extends AppCompatActivity {

    EditText AreaInput;
    Button convertBtn;
    TextView result1;
    TextView result;
    TextView result2;

    double  inputValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_calculation);

        AreaInput = findViewById(R.id.Input_Area);
        convertBtn = findViewById(R.id.convertBtn);
        result1 = findViewById(R.id.result1);
        result = findViewById(R.id.result);
        result2 = findViewById(R.id.result2);





    }

    @Override
    protected void onStart() {
        super.onStart();
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValue=Double.parseDouble(AreaInput.getText().toString());
                convertIntoPurchus(inputValue);
                convertIntoArches(inputValue);
                convertIntoSqr(inputValue);
            }


        });

    }

    private void convertIntoSqr(double inputValue) {
        double Val=1*inputValue;
        result.setText("Sqr "+String.valueOf(Val));
    }

    public  void convertIntoPurchus(double inputValue){

        double Val=0.00367*inputValue;

        result2.setText("Purches "+String.valueOf(Val));

    }
    public void convertIntoArches(double inputValue) {
        double Val=0.00002296*inputValue;

        result1.setText("Arches "+String.valueOf(Val));
    }




}