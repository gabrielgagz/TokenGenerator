package com.gabrielgagz.tokengenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

public class GenerateTokenActivity extends AppCompatActivity implements OnItemSelectedListener {

    private TextView result;
    private Boolean buttonClicked = false;
    private String tokenResultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_token);

        result = (TextView) findViewById(R.id.tokenResult);

        Spinner spn = (Spinner) findViewById(R.id.spinner);
        spn.setOnItemSelectedListener(this);

        // Call to button listener
        addListenerOnButton(spn);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        buttonClicked = false;
        result.setText(null);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        buttonClicked = false;
        result.setText(null);
    }

    public void addListenerOnButton(Spinner spn) {
        Button btn = (Button) findViewById(R.id.button_generate_token);
        btn.setOnClickListener(v -> {
            result.setText(generateToken(String.valueOf(spn.getSelectedItem())));
        });
    }

    public  String generateToken(String spinnerText) {

        if (buttonClicked) return tokenResultValue;

        byte[] encrypt = spinnerText.getBytes(StandardCharsets.UTF_8);
        String generatedString = Base64.encodeToString(encrypt, Base64.DEFAULT);
        String fullFillToken = getSaltString();
        buttonClicked = true;
        tokenResultValue = generatedString.substring(0, 12) + fullFillToken;
        return tokenResultValue;

    }

    protected static String getSaltString() {
        String SALTCHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 52) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

}