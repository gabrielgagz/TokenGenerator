package com.gabrielgagz.tokengenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateTokenActivity extends AppCompatActivity implements OnItemSelectedListener {

    private TextView result;
    private String tokenResultValue;
    private Spinner spn;
    private DataBaseHelper helper = new DataBaseHelper();
    private static final String SALTCHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_token);

        result = (TextView) findViewById(R.id.tokenResult);
        spn = (Spinner) findViewById(R.id.spinner);
        List<String> spnItems =  new ArrayList<String>();
        spn.setOnItemSelectedListener(this);
        // Call to button listener
        addListenerOnButton(spn);
        // Full-fill Spinner
        getAndFullFill (spnItems, spn);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        result.setText(getFromDB(String.valueOf(spn.getSelectedItem())));
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        result.setText(null);
    }

    private void addListenerOnButton(Spinner spn) {
        Button btn = (Button) findViewById(R.id.button_generate_token);
        btn.setOnClickListener(v -> {
            result.setText(storeInDB(String.valueOf(spn.getSelectedItem())));
        });
    }

    private void getAndFullFill (List spnItems, Spinner spn) {
        try {
            Cursor queryResult = helper.returnFullFillData(this, spnItems);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnItems);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn.setAdapter(adapter);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String generateToken(String spinnerText) {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 52) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        byte[] encrypt = spinnerText.getBytes(StandardCharsets.UTF_8);
        String generatedString = Base64.encodeToString(encrypt, Base64.DEFAULT);
        String fullFillToken = salt.toString();
        tokenResultValue = generatedString.substring(0, 12) + fullFillToken;
        return tokenResultValue;
    }

    private String getFromDB ( String tokenId ) {
        try {
            return helper.retrieveTokenCodeFromDb(this,tokenId);
        } catch (Exception e) {
            return getString(R.string.no_token);
        }
    }

    private String storeInDB ( String tokenId ) {
        // Generate token with given ID
        String tokenName = generateToken(tokenId);
        try {
            helper.storeTokenAndCodeInDb(this, tokenId, tokenName);
            return tokenName;
        } catch (Exception e) {
            return getString(R.string.error) + e.getMessage();
        }
    }

}