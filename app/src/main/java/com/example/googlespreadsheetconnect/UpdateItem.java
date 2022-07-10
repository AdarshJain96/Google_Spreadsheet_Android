package com.example.googlespreadsheetconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateItem extends AppCompatActivity {

    Button buttonUpdateItem;
    EditText editTextBrand;
    EditText editTextItemName;
    EditText editTextPrice;
    EditText editPhoneNumber,edittherapy,editDuration;
    EditText editStartTime, editEndTime, editComment1, editComment2; /*editMode*/;

    TextInputLayout name_layout, therapy_layout;
    TextInputLayout phone_layout, brand_layout, price_layout;
    TextInputLayout duration_layout, start_time_layout, end_time_layout, comment1_layout, comment2_layout;

    CheckBox ch1, ch2, ch3;

    String msg="";

    String itemId, itemName, brand, price, mode;
    String phoneNo, therapy, duration, startTime, endTime, comment1, comment2;

    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        itemName = intent.getStringExtra("itemName");
        brand = intent.getStringExtra("brand");
        price = intent.getStringExtra("price");
        phoneNo = intent.getStringExtra("phone");
        therapy = intent.getStringExtra("therapy");
        duration = intent.getStringExtra("duration");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        mode = intent.getStringExtra("mode");
        comment1 = intent.getStringExtra("comment1");
        comment2 = intent.getStringExtra("comment2");

        name_layout = (TextInputLayout) findViewById(R.id.name_layout);
        therapy_layout = (TextInputLayout) findViewById(R.id.therapy_layout);
        phone_layout = (TextInputLayout) findViewById(R.id.phone_layout);
        brand_layout = (TextInputLayout) findViewById(R.id.brand_layout);
        price_layout = (TextInputLayout) findViewById(R.id.price_layout);
        duration_layout = (TextInputLayout) findViewById(R.id.duration_layout);
        start_time_layout = (TextInputLayout) findViewById(R.id.start_time_layout);
        end_time_layout = (TextInputLayout) findViewById(R.id.end_time_layout);
        comment1_layout = (TextInputLayout) findViewById(R.id.comment1_layout);
        comment2_layout = (TextInputLayout) findViewById(R.id.comment2_layout);

        editTextItemName = (EditText) findViewById(R.id.et_item_name);
        editTextBrand = (EditText) findViewById(R.id.et_brand);
        editTextPrice = (EditText) findViewById(R.id.et_price);
        editPhoneNumber = (EditText)findViewById(R.id.et_phone);
        edittherapy = (EditText)findViewById(R.id.et_therapy);
        editDuration = (EditText)findViewById(R.id.et_duration);
        editStartTime = (EditText)findViewById(R.id.et_start_time);
        editEndTime = (EditText)findViewById(R.id.et_end_time);
        editComment1 = (EditText)findViewById(R.id.et_comment1);
        editComment2 = (EditText)findViewById(R.id.et_comment2);
        //editMode = (EditText)findViewById(R.id.et_payment);

        ch1=(CheckBox)findViewById(R.id.checkBox1);
        ch2=(CheckBox)findViewById(R.id.checkBox2);
        ch3=(CheckBox)findViewById(R.id.checkBox3);

        editTextItemName.setText(itemName);
        editTextBrand.setText(brand);
        editTextPrice.setText(price);
        editPhoneNumber.setText(phoneNo);
        edittherapy.setText(therapy);
        editDuration.setText(duration);
        editStartTime.setText(startTime);
        editEndTime.setText(endTime);
        editComment1.setText(comment1);
        editComment2.setText(comment2);
        msg = mode;

        switch (msg) {
            case "Cash": ch1.setChecked(true);
            break;
            case "Card": ch2.setChecked(true);
            break;
            case "Google": ch3.setChecked(true);
            break;
            case "Cash, Card":
                ch1.setChecked(true);
                ch2.setChecked(true);
            break;
            case "Card, Google":
                ch2.setChecked(true);
                ch3.setChecked(true);
            break;
            case "Cash, Google":
                ch1.setChecked(true);
                ch3.setChecked(true);
            break;
            case "Cash, Card, Google":
                ch1.setChecked(true);
                ch2.setChecked(true);
                ch3.setChecked(true);
        }
        //editMode.setText(mode);

        buttonUpdateItem = (Button) findViewById(R.id.btn_update_item);

        editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateItem.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view1, int hourOfDay, int minute1) {
                                //editEndTime.setText(hourOfDay + ":" + minute1 + " " + format);
                                hour   = hourOfDay;
                                minute = minute1;

                                updateStartTime(hour,minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateItem.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view1, int hourOfDay, int minute1) {
                                //editEndTime.setText(hourOfDay + ":" + minute1 + " " + format);
                                hour   = hourOfDay;
                                minute = minute1;

                                updateEndTime(hour,minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        buttonUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ch1.isChecked())
                    msg = "Cash";
                if(ch2.isChecked())
                    msg = "Card";
                if(ch3.isChecked())
                    msg = "Google";
                if(ch1.isChecked() && ch2.isChecked())
                    msg = "Cash, Card";
                if(ch2.isChecked() && ch3.isChecked())
                    msg = "Card, Google";
                if(ch1.isChecked() && ch3.isChecked())
                    msg = "Cash, Google";
                if(ch1.isChecked() && ch2.isChecked() && ch3.isChecked())
                    msg = "Cash, Card, Google";

                if(editTextItemName.getText().toString().equals("")){
                    name_layout.setError("Client name should not be empty");
                }else if(edittherapy.getText().toString().equals("")) {
                    therapy_layout.setError("Therapy should not be empty");
                }else if(editTextBrand.getText().toString().equals("")) {
                    brand_layout.setError("Therapist name should not be empty");
                }/*else if(editTextPrice.getText().toString().equals("")) {
                    price_layout.setError("Price should not be empty");
                }*/else if(editPhoneNumber.getText().toString().equals("")) {
                    phone_layout.setError("Phone number should not be empty");
                }else if(editDuration.getText().toString().equals("")) {
                    duration_layout.setError("Duration should not be empty");
                }else if(editStartTime.getText().toString().equals("")) {
                    start_time_layout.setError("Start time should not be empty");
                }else if(editEndTime.getText().toString().equals("")) {
                    end_time_layout.setError("End time should not be empty");
                }else if(ch1.isChecked() || ch2.isChecked() || ch3.isChecked()){
                    updateItemToSheet();
                }else{
                    ch1.setError("select mode of payment");
                    ch2.setError("select mode of payment");
                    ch3.setError("select mode of payment");
                }
            }
        });
    }

    private void updateItemToSheet() {
        final ProgressDialog show = ProgressDialog.show(this, "Updating Item", "Please wait");
        final String name = editTextItemName.getText().toString().trim();
        final String brand = editTextBrand.getText().toString().trim();
        final String price = editTextPrice.getText().toString().trim();
        final String phoneNo = editPhoneNumber.getText().toString().trim();
        final String therapy = edittherapy.getText().toString().trim();
        final String duration = editDuration.getText().toString().trim();
        final String startTime = editStartTime.getText().toString().trim();
        final String endTime = editEndTime.getText().toString().trim();
        final String comment1 = editComment1.getText().toString().trim();
        final String comment2 = editComment2.getText().toString().trim();
        final String mode = msg.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.url,
                new Listener<String>() {
            public void onResponse(String str) {
                show.dismiss();
                Toast.makeText(UpdateItem.this, str, Toast.LENGTH_SHORT).show();
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
                hashMap.put("action", "updateItem");
                hashMap.put("itemId", itemId);
                hashMap.put("phone",phoneNo);
                hashMap.put("itemName", name);
                hashMap.put("therapy",therapy);
                hashMap.put("duration",duration);
                hashMap.put("brand", brand);
                hashMap.put("startTime",startTime);
                hashMap.put("endTime",endTime);
                hashMap.put("mode",mode);
                hashMap.put("price", price);
                hashMap.put("comment1", comment1);
                hashMap.put("comment2", comment2);

                return hashMap;
            }
        };

        int socketTimeOut = 50000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }

    private void updateStartTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        editStartTime.setText(aTime);
    }

    private void updateEndTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        editEndTime.setText(aTime);
    }


}