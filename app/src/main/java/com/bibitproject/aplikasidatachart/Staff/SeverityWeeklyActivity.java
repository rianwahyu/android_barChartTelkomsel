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
import com.bibitproject.aplikasidatachart.Staff.Model.ModelSeverityWeekly;
import com.bibitproject.aplikasidatachart.Utils.MyConfig;
import com.bibitproject.aplikasidatachart.Utils.NetworkState;
import com.bibitproject.aplikasidatachart.databinding.ActivitySeverityWeeklyBinding;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeverityWeeklyActivity extends AppCompatActivity {

    Context context = this;
    ActivitySeverityWeeklyBinding binding;

    int[] colorClassArray = new int[]{
            Color.LTGRAY, Color.BLUE, Color.CYAN, Color.DKGRAY
    };

    ArrayList<PieEntry> listSeverity = new ArrayList<PieEntry>();
    ArrayList<Integer> listColors = new ArrayList<Integer>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_severity_weekly);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_severity_weekly);

        progressDialog = new ProgressDialog(context);

        listSeverity = new ArrayList<>();
        listColors = new ArrayList<>();

        getSeverity();

        binding.toolbar.setTitle("Weekly Severity");
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

    }

    void getSeverity(){
        progressDialog.setMessage("Memuat Data ..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = NetworkState.getUrl()+"staff/getSeverityWeekly.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");
                    if (success == true){
                        String data = jsonObject.getString("data");
                        JSONArray jsonArray = new JSONArray(data.toString());

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject users = jsonArray.getJSONObject(i);

                            String severityName = users.getString("severityName");
                            String totSeverity = users.getString("totSeverity");

                            listSeverity.add(new PieEntry(Float.parseFloat(totSeverity), severityName));
                            listColors.add(randomColor());
                        }

                        PieDataSet pieDataSet = new PieDataSet(listSeverity, "Severity");
                        pieDataSet.setColors(listColors);

                        PieData pieData = new PieData(pieDataSet);
                        binding.pieChart.setData(pieData);
                        binding.pieChart.invalidate();
                        binding.pieChart.setDrawEntryLabels(true);
                        binding.pieChart.setUsePercentValues(false);
                        binding.pieChart.setCenterText("Weekly Severity");
                        binding.pieChart.setCenterTextSize(20);
                        binding.pieChart.setCenterTextRadiusPercent(80);
                        binding.pieChart.setHoleRadius(30);
                        binding.pieChart.setTransparentCircleRadius(40);
                        binding.pieChart.setTransparentCircleColor(Color.RED);
                        binding.pieChart.setTransparentCircleAlpha(20);

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

    int[] colorClassArray2 = new int[]{
            randomColor()
    };

    public int randomColor() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256),
                random.nextInt(256));

        return color;
    }

    private ArrayList<Integer> dataColors(){
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(randomColor());
        colors.add(randomColor());
        colors.add(randomColor());
        colors.add(randomColor());
        return colors;
    }

    /*private ArrayList<PieEntry> dataValues1(){
        ArrayList<PieEntry> dataVals = new ArrayList<>();
        dataVals.add(listSeverity);
        return dataVals;
    }*/



    @Override
    public void onBackPressed() {
        finish();
    }
}