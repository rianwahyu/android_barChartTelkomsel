package com.bibitproject.aplikasidatachart.Staff.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.Staff.Model.Monitoring;
import com.bibitproject.aplikasidatachart.Utils.MyConfig;
import com.bibitproject.aplikasidatachart.Utils.NetworkState;
import com.bibitproject.aplikasidatachart.databinding.FragmentHomeFaultBinding;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeFaultFragment extends Fragment {

    FragmentHomeFaultBinding binding;

    List<Monitoring> listMonitoring = new ArrayList<Monitoring>();
    LinearLayoutManager linearLayoutManager;

    List<String> xLabel = new ArrayList<String>();
    List<BarEntry> dataCritical = new ArrayList<BarEntry>();
    List<BarEntry> dataMjor = new ArrayList<BarEntry>();
    List<BarEntry> dataMinor = new ArrayList<BarEntry>();

    ArrayList<PieEntry> listFaultHistogram = new ArrayList<PieEntry>();
    ArrayList<Integer> listColors = new ArrayList<Integer>();

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_fault, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_fault, container, false);

        progressDialog = new ProgressDialog(getContext());
        listMonitoring = new ArrayList<>();

        listFaultHistogram = new ArrayList<>();
        listColors = new ArrayList<>();
        /*BarDataSet barDataSet1 = new BarDataSet(barEntries1(), "DataSet 1");
        barDataSet1.setColor(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(barEntries2(), "DataSet 2");
        barDataSet2.setColor(Color.BLUE);
        BarDataSet barDataSet3 = new BarDataSet(barEntries3(), "DataSet 3");
        barDataSet3.setColor(Color.GREEN);
        BarDataSet barDataSet4 = new BarDataSet(barEntries4(), "DataSet 4");
        barDataSet4.setColor(Color.YELLOW);

        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3, barDataSet4);
        binding.barFault.setData(data);

        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        XAxis xAxis = binding.barFault.getXAxis();

        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        binding.barFault.setDragEnabled(true);
        binding.barFault.setVisibleXRangeMaximum(3);

        float barSpace = 0.08f;
        float groupSpace = 0.44f;
        data.setBarWidth(0.10f);

        binding.barFault.getXAxis().setAxisMinimum(0);
//        binding.barFault.getXAxis().setAxisMinimum(0 +
//                binding.barFault.getBarData().getGroupWidth(groupSpace, barSpace)*7);
        binding.barFault.getAxisLeft().setAxisMinimum(0);

        binding.barFault.groupBars(0, groupSpace, barSpace);


        binding.barFault.invalidate();

        Legend l = binding.barFault.getLegend();
        l.setFormSize(10f);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);*/

        getFaultDaily();

        return binding.getRoot();
    }

    private void getFaultDaily() {
        progressDialog.setTitle("Load data");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        listMonitoring.clear();
        //OnShimmer();
        String url = NetworkState.getUrl()+"staff/getFaultDaily.php";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("response", response);
                try {
                    //menangkap respon JSON dari server
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");
                    //cek status login dari jsonserver
                    if (success==true){
                        //OnFoundData();
                        String data = jsonObject.getString("data");
                        JSONArray jsonArray = new JSONArray(data.toString());
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String reg = object.getString("reg");
                            String cri = object.getString("cri");
                            String maj = object.getString("maj");
                            String min = object.getString("min");

                            xLabel.add(reg);

                            dataCritical.add(new BarEntry(1f * i, Float.parseFloat(cri)));
                            dataMjor.add(new BarEntry(1f * i, Float.parseFloat(maj)));
                            dataMinor.add(new BarEntry(1f * i, Float.parseFloat(min)));
                        }

                        BarDataSet barCritical = new BarDataSet(dataCritical, "Critical");
                        barCritical.setColor(Color.RED);

                        BarDataSet barMajor = new BarDataSet(dataMjor, "Major");
                        barMajor.setColor(Color.YELLOW);

                        BarDataSet barMinor = new BarDataSet(dataMinor, "Minor");
                        barMinor.setColor(Color.GREEN);

                        BarData barData = new BarData(barCritical, barMajor, barMinor);
                        binding.barFault.setData(barData);

                        XAxis xAxis = binding.barFault.getXAxis();

                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabel));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setGranularity(1f);
                        xAxis.setTextSize(10f);
                        xAxis.setTextColor(Color.RED);
                        xAxis.setDrawAxisLine(true);
                        xAxis.setDrawGridLines(false);

                        binding.barFault.setDragEnabled(true);
                        binding.barFault.setVisibleXRangeMaximum(7);

                        float barSpace = 0.08f;
                        float groupSpace = 0.44f;
                        barData.setBarWidth(0.10f);

                        binding.barFault.getXAxis().setAxisMinimum(0);
//        binding.barFault.getXAxis().setAxisMinimum(0 +
//                binding.barFault.getBarData().getGroupWidth(groupSpace, barSpace)*7);
                        binding.barFault.getAxisLeft().setAxisMinimum(0);

                        binding.barFault.groupBars(0, groupSpace, barSpace);


                        binding.barFault.invalidate();

                        Legend l = binding.barFault.getLegend();
                        l.setFormSize(10f);
                        l.setForm(Legend.LegendForm.CIRCLE);
                        l.setTextSize(12f);
                        l.setTextColor(Color.BLACK);

                        getFaultHistogram();

                    }else{
                        MyConfig.showToast(getContext(), message);
                        //OnEmpty();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //OnEmpty();
                Log.d("error", error.getMessage().toString());
                MyConfig.showToast(getContext(), error.getMessage().toString());
            }
        }){
            //parameter untuk login yang akan di tangkap api server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                /*params.put("userIdPelayan",new SessionManager(getContext()).getIdUser());
                params.put("stats","0");*/
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        mRequestQueue.add(stringRequest);
    }

    private void getFaultHistogram() {
        progressDialog.setTitle("Load data");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        listMonitoring.clear();
        //OnShimmer();
        String url = NetworkState.getUrl()+"staff/getFaultHistogram.php";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("response", response);
                try {
                    //menangkap respon JSON dari server
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");
                    //cek status login dari jsonserver
                    if (success==true){
                        //OnFoundData();

                        String dataTotal = jsonObject.getString("dataTotal");

                        JSONArray jsonArray1 = new JSONArray(dataTotal.toString());

                        for (int j=0; j<jsonArray1.length(); j++){
                            JSONObject object = jsonArray1.getJSONObject(j);

//                            binding.textJumCritical.setText(object.getString("cri"));
//                            binding.textJumMajor.setText(object.getString("maj"));
//                            binding.textJumMinor.setText(object.getString("min"));
                            /*DecimalFormat df = new DecimalFormat();
                            df.setMaximumFractionDigits(2);

                            Float percentFloat = Float.valueOf(df.format(object.getString("percent")));*/

                            /*listFaultHistogram.add(new PieEntry(parseFloat(object.getString("percent")),
                                    object.getString("Severity")));*/
                            listFaultHistogram.add(new PieEntry(Float.parseFloat(object.getString("percent")),
                                    object.getString("Severity")));
                            if (object.getString("Severity").equals("critical")){
                                listColors.add(Color.RED);
                            }else if (object.getString("Severity").equals("major")){
                                listColors.add(Color.YELLOW);
                            }else if (object.getString("Severity").equals("minor")){
                                listColors.add(Color.GREEN);
                            }
                            //listColors.add(randomColor());
                        }

                        PieDataSet pieDataSet = new PieDataSet(listFaultHistogram, "");
                        pieDataSet.setColors(listColors);
                        pieDataSet.setValueTextColor(Color.BLACK);


                        PieData pieData = new PieData(pieDataSet);
                        binding.pieChart.setData(pieData);
                        binding.pieChart.invalidate();
                        binding.pieChart.setDrawEntryLabels(true);
                        binding.pieChart.setUsePercentValues(false);
                        binding.pieChart.setCenterText("Fault");
                        binding.pieChart.setCenterTextSize(20);
                        binding.pieChart.setCenterTextColor(Color.BLACK);

                        binding.pieChart.setCenterTextRadiusPercent(80);
                        binding.pieChart.setHoleRadius(30);
                        binding.pieChart.setTransparentCircleRadius(40);
                        binding.pieChart.setTransparentCircleColor(Color.RED);
                        binding.pieChart.setTransparentCircleAlpha(20);


                        Legend l = binding.pieChart.getLegend();

                        binding.pieChart.setEntryLabelColor(Color.BLACK);

                    }else{
                        MyConfig.showToast(getContext(), message);
                        //OnEmpty();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //OnEmpty();
                progressDialog.dismiss();
                Log.d("error", error.getMessage().toString());
                MyConfig.showToast(getContext(), error.getMessage().toString());
            }
        }){
            //parameter untuk login yang akan di tangkap api server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                /*params.put("userIdPelayan",new SessionManager(getContext()).getIdUser());
                params.put("stats","0");*/
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        mRequestQueue.add(stringRequest);
    }

    private ArrayList<BarEntry> barEntries1(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 2000));
        barEntries.add(new BarEntry(2, 1652));
        barEntries.add(new BarEntry(3, 1312));
        barEntries.add(new BarEntry(4, 2019));
        barEntries.add(new BarEntry(5, 2113));
        barEntries.add(new BarEntry(6, 4311));
        barEntries.add(new BarEntry(7, 1231));

        return barEntries;
    }

    private ArrayList<BarEntry> barEntries2(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 2000));
        barEntries.add(new BarEntry(2, 1652));
        barEntries.add(new BarEntry(3, 1312));
        barEntries.add(new BarEntry(4, 2019));
        barEntries.add(new BarEntry(5, 2113));
        barEntries.add(new BarEntry(6, 4311));
        barEntries.add(new BarEntry(7, 1231));

        return barEntries;
    }


    private ArrayList<BarEntry> barEntries3(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 987));
        barEntries.add(new BarEntry(2, 1652));
        barEntries.add(new BarEntry(3, 5634));
        barEntries.add(new BarEntry(4, 5623));
        barEntries.add(new BarEntry(5, 2113));
        barEntries.add(new BarEntry(6, 431));
        barEntries.add(new BarEntry(7, 2442));

        return barEntries;
    }


    private ArrayList<BarEntry> barEntries4(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 1232));
        barEntries.add(new BarEntry(2, 6242));
        barEntries.add(new BarEntry(3, 13123));
        barEntries.add(new BarEntry(4, 624));
        barEntries.add(new BarEntry(5, 8676));
        barEntries.add(new BarEntry(6, 1909));
        barEntries.add(new BarEntry(7, 23234));

        return barEntries;
    }

    public int randomColor() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256),
                random.nextInt(256));

        return color;
    }

    String roundOffTo2DecPlaces(float val)
    {
        return String.format("%.4f", val);
    }

    float parseFloat(String avg){
        DecimalFormat df = new DecimalFormat("#.000");
        String average = df.format(avg);

        return Float.parseFloat(average);
    }
}