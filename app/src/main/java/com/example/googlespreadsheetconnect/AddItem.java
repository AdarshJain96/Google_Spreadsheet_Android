package com.example.googlespreadsheetconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddItem extends AppCompatActivity {

    EditText editTextItemName,editTextBrand,editTextPrice;
    EditText editPhoneNumber,edittherapy,editDuration;
    EditText editStartTime, editEndTime, editComment1, editComment2; /*editMode*/;

    TextInputLayout name_layout, therapy_layout;
    TextInputLayout phone_layout, brand_layout, price_layout;
    TextInputLayout duration_layout, start_time_layout, end_time_layout, comment1_layout, comment2_layout;

    CheckBox ch1, ch2, ch3;

    Button buttonAddItem;

    String msg="";

    private String format = "";

    //Locale local = new Locale("en", "IN");

    final Calendar c = Calendar.getInstance(/*local*/);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

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

        editTextItemName = (EditText)findViewById(R.id.et_item_name);
        editTextBrand = (EditText)findViewById(R.id.et_brand);
        editTextPrice = (EditText)findViewById(R.id.et_price);
        editPhoneNumber = (EditText)findViewById(R.id.et_phone);
        edittherapy = (EditText)findViewById(R.id.et_therapy);
        editDuration = (EditText)findViewById(R.id.et_duration);
        editStartTime = (EditText)findViewById(R.id.et_start_time);
        editEndTime = (EditText)findViewById(R.id.et_end_time);
        editComment1 = (EditText)findViewById(R.id.et_comment1);
        editComment2 = (EditText)findViewById(R.id.et_comment2);
        /*editMode = (EditText)findViewById(R.id.et_payment);*/

        ch1=(CheckBox)findViewById(R.id.checkBox1);
        ch2=(CheckBox)findViewById(R.id.checkBox2);
        ch3=(CheckBox)findViewById(R.id.checkBox3);

        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        buttonAddItem = (Button)findViewById(R.id.btn_add_item);

//        editStartTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(AddItem.this,
//                        new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker view1, int hourOfDay, int minute1) {
//                                //editEndTime.setText(hourOfDay + ":" + minute1 + " " + format);
//                                hour   = hourOfDay;
//                                minute = minute1;
//
//                                updateStartTime(hour,minute);
//                            }
//                        }, hour, minute, false);
//                timePickerDialog.show();
//            }
//        });
//
//        editEndTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(AddItem.this,
//                        new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker view1, int hourOfDay, int minute1) {
//                                //editEndTime.setText(hourOfDay + ":" + minute1 + " " + format);
//                                hour   = hourOfDay;
//                                minute = minute1;
//
//                                updateEndTime(hour,minute);
//                            }
//                        }, hour, minute, false);
//                timePickerDialog.show();
//            }
//        });

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
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
                }else if(editTextPrice.getText().toString().equals("")) {
                    price_layout.setError("Price should not be empty");
                }/*else if(editPhoneNumber.getText().toString().equals("")) {
                    phone_layout.setError("Phone number should not be empty");
                }*/else if(editDuration.getText().toString().equals("")) {
                    duration_layout.setError("Duration should not be empty");
                }else if(editStartTime.getText().toString().equals("")) {
                    start_time_layout.setError("Start time should not be empty");
                }else if(editEndTime.getText().toString().equals("")) {
                    end_time_layout.setError("End time should not be empty");
                }else if(ch1.isChecked() || ch2.isChecked() || ch3.isChecked()){
                    addItemToSheet();
                }else{
                    ch1.setError("select mode of payment");
                    ch2.setError("select mode of payment");
                    ch3.setError("select mode of payment");
                }
            }
        });
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

    private void addItemToSheet() {

        name_layout.setError("");
        therapy_layout.setError("");
        brand_layout.setError("");
        price_layout.setError("");
        phone_layout.setError("");
        duration_layout.setError("");
        start_time_layout.setError("");
        end_time_layout.setError("");

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
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
        final String mode = msg.trim();//editMode.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(AddItem.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("phone",phoneNo);
                parmas.put("itemName",name);
                parmas.put("therapy",therapy);
                parmas.put("duration",duration);
                parmas.put("brand",brand);
                parmas.put("startTime",startTime);
                parmas.put("endTime",endTime);
                parmas.put("mode",mode);
                parmas.put("price",price);
                parmas.put("comment1",comment1);
                parmas.put("comment2",comment2);
                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }

}