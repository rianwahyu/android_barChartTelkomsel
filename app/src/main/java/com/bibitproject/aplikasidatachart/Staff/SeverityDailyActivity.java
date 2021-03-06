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
import com.bibitproject.aplikasidatachart.Admin.Model.ModelRegistrationUser;
import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.Utils.MyConfig;
import com.bibitproject.aplikasidatachart.Utils.NetworkState;
import com.bibitproject.aplikasidatachart.databinding.ActivitySeverityDailyBinding;
import com.bibitproject.aplikasidatachart.databinding.ActivitySeverityMainBinding;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SeverityDailyActivity extends AppCompatActivity {

    Context context = this;
    ActivitySeverityDailyBinding binding;

    ProgressDialog progressDialog;

    float groupSpace = 0.08f;
    float barSpace = 0.02f;
    float barWidth = 0.45f;
    float tahunAwal = 20f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_severity_daily);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_severity_daily);

        progressDialog = new ProgressDialog(context);

        binding.toolbar.setTitle("Severity Daily");
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




        // Data-data yang akan ditampilkan di Chart


        // Diubah menjadi integer, kemudian dijadikan String
        // Ini berfungsi untuk menghilankan koma, dan tanda ribuah pada tahun
        /*xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });*/

        //Menghilangkan sumbu Y yang ada di sebelah kanan


        // Set data ke Chart
        // Tambahkan invalidate setiap kali mengubah data chart

        getSeverity();

        /*int[] numArr = {309, 1, 2418, 14015};
        List<BarEntry> entries = new ArrayList<BarEntry>();

        int plus=1;
        for (int num : numArr) {

            entries.add(new BarEntry(1f * plus, num));
            plus ++;
        }
        BarDataSet dataSet = new BarDataSet(entries, "Severity");
        BarData data = new BarData(dataSet);

        //ValueFormatter xAxisFormatter = new DayAxisValueFormatter(binding.barChart);
        String[] xLabels = {"1 normal", "4 minor", "5 major", "6 Critical"};
        //binding.barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xLabels));
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        //xAxis.setLabelCount(7);
        //xAxis.setValueFormatter(xAxisFormatter);
        binding.barChart.getXAxis().setValueFormatter(new LabelFormatter(xLabels));

        binding.barChart.setData(data);
        binding.barChart.invalidate();*/

    }

    public class LabelFormatter implements IAxisValueFormatter {
        private final String[] mLabels;

        public LabelFormatter(String[] labels) {
            mLabels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mLabels[(int) value];
        }
    }


    public class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> chart;
        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }
        @Override
        public String getFormattedValue(float value) {
            return "your text " + value;
        }
    }

    void getSeverity(){
        progressDialog.setMessage("Memuat Data ..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = NetworkState.getUrl()+"staff/getSeverityDaily.php";
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

                            String severityName = users.getString("severityName");
                            String totSeverity = users.getString("totSeverity");

                            xLabel.add(severityName);

                            dataSeverity.add(new BarEntry(1f * i, Float.parseFloat(totSeverity)));

                        }

                        String[] simpleArray = new String[ xLabel.size() ];
                        //xLabel.toArray( simpleArray );


                        BarDataSet dataSet1 = new BarDataSet(dataSeverity, "Severity");
                        dataSet1.setColor(ColorTemplate.COLORFUL_COLORS[0]);
                        BarData barData = new BarData();
                        barData.addDataSet(dataSet1);

                        binding.barChart.setData(barData);
                        //binding.barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xLabel));
                        XAxis xAxis = binding.barChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setGranularity(1f);
                        xAxis.setTextSize(10f);
                        xAxis.setTextColor(Color.RED);
                        xAxis.setDrawAxisLine(true);
                        xAxis.setDrawGridLines(false);
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabel));
                        //xAxis.setValueFormatter(new LabelFormatter(simpleArray));

                        binding.barChart.invalidate();

                        //xAxis.setDrawAxisLine(false);
                        /*xAxis.setValueFormatter(new IndexAxisValueFormatter(){
                            @Override
                            public String getFormattedValue(float value, AxisBase axisBase) {
                                return xLabel.get((int) value);
                            }
                        });*/


                        /*binding.barChart.getBarData().setBarWidth(barWidth);
                        binding.barChart.getXAxis().setAxisMinimum(tahunAwal);
                        binding.barChart.getXAxis().setAxisMaximum(tahunAwal + binding.barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 4);
                        binding.barChart.groupBars(tahunAwal, groupSpace, barSpace);
                        binding.barChart.setDragEnabled(true);*/


                        //binding.barChart.getAxisRight().setEnabled(false);

                        // Menghilankan deskripsi pada Chart
                        //binding.barChart.getDescription().setEnabled(false);

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