package com.example.googlespreadsheetconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

import static java.sql.DriverManager.println;

public class ItemDetails extends AppCompatActivity {

    TextView textViewItemName, textViewItemBrand, textViewItemPrice, textViewItemId;
    TextView textViewPhone, textViewtherapy, textViewDuration, textViewStartTime;
    TextView textViewEndTime, textViewMode, textViewDate, textComment1, textComment2;
    Button update, delete;
    String itemId, date, itemName, brand, price, mode;
    String phoneNo, therapy, duration, startTime, endTime, comment1, comment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        date = intent.getStringExtra("date");
        phoneNo = intent.getStringExtra("phone");
        brand = intent.getStringExtra("brand");
        price = intent.getStringExtra("price");
        itemName = intent.getStringExtra("itemName");
        therapy = intent.getStringExtra("therapy");
        duration = intent.getStringExtra("duration");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        mode = intent.getStringExtra("mode");
        comment1 = intent.getStringExtra("comment1");
        comment2 = intent.getStringExtra("comment2");


        textViewItemId = (TextView)findViewById(R.id.tv_item_id);
        textViewDate = (TextView)findViewById(R.id.tv_date);
        textViewItemPrice = (TextView)findViewById(R.id.tv_price);
        textViewItemBrand = (TextView)findViewById(R.id.tv_brand);
        textViewItemName = (TextView)findViewById(R.id.tv_item_name);
        textViewPhone = (TextView)findViewById(R.id.tv_phone_no);
        textViewtherapy = (TextView)findViewById(R.id.tv_therapy);
        textViewDuration = (TextView)findViewById(R.id.tv_duration);
        textViewStartTime = (TextView)findViewById(R.id.startTime);
        textViewEndTime = (TextView)findViewById(R.id.endTime);
        textViewMode = (TextView)findViewById(R.id.mode);
        textComment1 = (TextView)findViewById(R.id.comment1);
        textComment2 = (TextView)findViewById(R.id.comment2);

        update = (Button)findViewById(R.id.update);
        delete = (Button)findViewById(R.id.delete);

        textViewItemName.setText(itemName);
        textViewDate.setText(date);
        textViewItemBrand.setText(brand);
        textViewItemId.setText(itemId);
        textViewItemPrice.setText(price);
        textViewPhone.setText(phoneNo);
        textViewtherapy.setText(therapy);
        textViewDuration.setText(duration);
        textViewStartTime.setText(startTime);
        textViewEndTime.setText(endTime);
        textViewMode.setText(mode);
        textComment1.setText(comment1);
        textComment2.setText(comment2);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetails.this, UpdateItem.class);
                intent.putExtra("itemId", itemId);
                intent.putExtra("itemName", itemName);
                intent.putExtra("brand", brand);
                intent.putExtra("price", price);
                intent.putExtra("phone",phoneNo);
                intent.putExtra("therapy",therapy);
                intent.putExtra("duration",duration);
                intent.putExtra("startTime",startTime);
                intent.putExtra("endTime",endTime);
                intent.putExtra("mode",mode);
                intent.putExtra("comment1",comment1);
                intent.putExtra("comment2",comment2);

                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemFromSheet();
            }
        });
    }

    private void deleteItemFromSheet() {
        Log.v("<------------------->",""+itemId);
        final ProgressDialog show = ProgressDialog.show(this, "Deleting Item", "Please wait");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.url,
                new Listener<String>() {
            public void onResponse(String str) {
                show.dismiss();
                Toast.makeText(ItemDetails.this, str, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {
            /* Access modifiers changed, original: protected */
            public Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("action", "deleteItem");
                hashMap.put("itemId", itemId);
                return hashMap;
            }
        };
        int socketTimeOut = 50000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }
}