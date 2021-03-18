package com.bibitproject.aplikasidatachart.Staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.Utils.MyConfig;
import com.bibitproject.aplikasidatachart.Utils.NetworkState;
import com.bibitproject.aplikasidatachart.databinding.ActivityDeviceAlarmBinding;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeviceAlarmActivity extends AppCompatActivity {

    Context context = this;

    ActivityDeviceAlarmBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_device_alarm);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device_alarm);

        progressDialog = new ProgressDialog(context);

        binding.toolbar.setTitle("Device Alarm");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_white);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getDeviceAlarm();
    }

    void getDeviceAlarm(){
        progressDialog.setMessage("Memuat Data ..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = NetworkState.getUrl()+"staff/getDeviceAlarm.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                List<BarEntry> dataSeverity = new ArrayList<BarEntry>();


                List<String> xLabel = new ArrayList<String>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");
                    if (success == true){
                        String data = jsonObject.getString("data");
                        JSONArray jsonArray = new JSONArray(data.toString());

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject users = jsonArray.getJSONObject(i);

                            String Device_Alias = users.getString("Device_Alias");
                            String totDevice = users.getString("totDevice");

                            xLabel.add(Device_Alias);

                            dataSeverity.add(new BarEntry(1f * i, Float.parseFloat(totDevice)));

                        }


                        BarDataSet dataSet1 = new BarDataSet(dataSeverity, "Severity");
                        dataSet1.setColor(ColorTemplate.COLORFUL_COLORS[0]);
                        BarData barData = new BarData();
                        barData.addDataSet(dataSet1);

                        binding.barChart.setData(barData);
                        XAxis xAxis = binding.barChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setGranularity(1f);
                        xAxis.setTextSize(8f);
                        xAxis.setTextColor(Color.RED);
                        xAxis.setDrawAxisLine(true);
                        xAxis.setDrawGridLines(false);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabel));

                        binding.barChart.invalidate();
                    }else{
                        MyConfig.showToast(context, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                MyConfig.showToast(context, error.getMessage().toString());
            }
        })
        {
            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("parent",idJabatan);
                params.put("level","2");
                return params;
            }*/
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}