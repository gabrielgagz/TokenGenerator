package com.gabrielgagz.tokengenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainTokenActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_token_generator);

        Button btnGenerate = findViewById(R.id.generateTokenMain);
        btnGenerate.setOnClickListener(this);
        Button btnAddNew = findViewById(R.id.newTokenMain);
        btnAddNew.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ( v.getId() == R.id.generateTokenMain ) {
            Intent generateIntent = new Intent (this, GenerateTokenActivity.class);
            startActivity(generateIntent);
        } else if ( v.getId() == R.id.newTokenMain ) {
            Intent addNewIntent = new Intent (v.getContext(), AddNewTokenActivity.class);
            startActivityForResult(addNewIntent, 0);
        }
    }
}