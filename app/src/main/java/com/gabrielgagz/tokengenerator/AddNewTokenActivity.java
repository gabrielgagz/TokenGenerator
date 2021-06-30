package com.gabrielgagz.tokengenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewTokenActivity extends AppCompatActivity {

    private EditText newToken;
    private  DataBaseHelper helper = new DataBaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_token_name);

        newToken = (EditText) findViewById(R.id.addNewTokenName);

        // Call to button listener
        addListenerOnButton();
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void addListenerOnButton() {

        Button btn = (Button) findViewById(R.id.newTokenButton);
        btn.setOnClickListener(v -> {
            try {
                String newName = newToken.getText().toString();
                if (!newName.isEmpty()) {
                    helper.storeNewTokenInDb(this, newName);
                    newToken.setText(null);
                    showToastMessage(getString(R.string.token_stored));
                } else {
                    showToastMessage(getString(R.string.token_empty));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

}