package com.example.googlespreadsheetconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonAddItem;
    Button btn_list_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        btn_list_item = (Button)findViewById(R.id.btn_list_item);

        buttonAddItem.setOnClickListener(view -> {
          Intent intent = new Intent(getApplicationContext(),AddItem.class);
          startActivity(intent);
        });
        btn_list_item.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),ListItem.class);
            startActivity(intent);
        });

    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}