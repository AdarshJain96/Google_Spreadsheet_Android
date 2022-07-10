package com.example.googlespreadsheetconnect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListItem extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    SimpleAdapter adapter;
    ProgressDialog loading;
    EditText editSearchItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        listView = (ListView) findViewById(R.id.lv_items);
        editSearchItem = (EditText) findViewById(R.id.et_search);

        listView.setOnItemClickListener(this);

        getItems();

    }


    private void getItems() {

        loading = ProgressDialog.show(this, "Loading", "please wait", false, true);
        loading.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Utils.url+"?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                        System.out.print("date is"+response.indexOf(0));
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String date = jo.getString("date");
                String itemId = jo.getString("itemId");
                String phoneNo = jo.getString("phone");
                String itemName = jo.getString("itemName");
                String therapy = jo.getString("therapy");
                String duration = jo.getString("duration");
                String brand = jo.getString("brand");
                String startTime = jo.getString("startTime");
                String endTime = jo.getString("endTime");
                String mode = jo.getString("mode");
                String price = jo.getString("price");
                String comment1 = jo.getString("comment1");
                String comment2 = jo.getString("comment2");

                HashMap<String, String> item = new HashMap<>();
                item.put("date",date);
                item.put("itemId", itemId);
                item.put("phone",phoneNo);
                item.put("itemName", itemName);
                item.put("therapy",therapy);
                item.put("duration",duration);
                item.put("brand",brand);
                item.put("startTime",startTime);
                item.put("endTime",endTime);
                item.put("mode",mode);
                item.put("price",price);
                item.put("comment1",comment1);
                item.put("comment2",comment2);

                list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SimpleAdapter(this, list, R.layout.list_item_row,
                new String[]{"date", "phone", "itemName",  "therapy", "duration", "brand", "startTime", "endTime", "mode", "price", "comment1", "comment2","itemId"},
                new int[]{R.id.tv_date, R.id.tv_phone_no, R.id.tv_item_name, R.id.tv_therapy, R.id.tv_duration, R.id.tv_brand, R.id.startTime, R.id.endTime, R.id.mode, R.id.tv_price, R.id.tv_comment1, R.id.tv_comment2});


        listView.setAdapter(adapter);
        loading.dismiss();

        editSearchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ListItem.this.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ListItem.this, ItemDetails.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String date = map.get("date").toString();
        String itemId = map.get("itemId").toString();
        String phoneNo = map.get("phone").toString();
        String itemName = map.get("itemName").toString();
        String therapy = map.get("therapy").toString();
        String duration = map.get("duration").toString();
        String brand = map.get("brand").toString();
        String startTime = map.get("startTime").toString();
        String endTime = map.get("endTime").toString();
        String mode = map.get("mode").toString();
        String price = map.get("price").toString();
        String comment1 = map.get("comment1").toString();
        String comment2 = map.get("comment2").toString();

        intent.putExtra("date", date);
        intent.putExtra("itemId", itemId);
        intent.putExtra("phone",phoneNo);
        intent.putExtra("itemName", itemName);
        intent.putExtra("therapy",therapy);
        intent.putExtra("duration",duration);
        intent.putExtra("brand", brand);
        intent.putExtra("startTime",startTime);
        intent.putExtra("endTime",endTime);
        intent.putExtra("mode",mode);
        intent.putExtra("price", price);
        intent.putExtra("comment1",comment1);
        intent.putExtra("comment2",comment2);

        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}