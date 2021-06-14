package com.bibitproject.aplikasidatachart.Staff.ui;

import android.app.ProgressDialog;
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
import com.bibitproject.aplikasidatachart.Staff.AdapterMonitoring;
import com.bibitproject.aplikasidatachart.Staff.Model.Monitoring;
import com.bibitproject.aplikasidatachart.Utils.MyConfig;
import com.bibitproject.aplikasidatachart.Utils.NetworkState;
import com.bibitproject.aplikasidatachart.databinding.FragmentHomeMonitoringBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeMonitoringFragment extends Fragment {

    FragmentHomeMonitoringBinding binding;
    AdapterMonitoring adapterMonitoring;
    List<Monitoring> listMonitoring = new ArrayList<Monitoring>();
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_monitoring, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_monitoring, container, false);
        progressDialog = new ProgressDialog(getContext());

        listMonitoring = new ArrayList<>();
        binding.rcMonitoring.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcMonitoring.setLayoutManager(linearLayoutManager);
        adapterMonitoring = new AdapterMonitoring(getContext(), listMonitoring);
        binding.rcMonitoring.setAdapter(adapterMonitoring);

        getMonitoring();

        return binding.getRoot();
    }

    private void getMonitoring() {
        progressDialog.setTitle("Load data");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        listMonitoring.clear();
        //OnShimmer();
        String url = NetworkState.getUrl()+"staff/getMonitoring.php";
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

                            binding.textJumCritical.setText(object.getString("cri"));
                            binding.textJumMajor.setText(object.getString("maj"));
                            binding.textJumMinor.setText(object.getString("min"));
                        }

                        String data = jsonObject.getString("data");
                        JSONArray jsonArray = new JSONArray(data.toString());
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String reg = object.getString("reg");
                            String cri = object.getString("cri");
                            String maj = object.getString("maj");
                            String min = object.getString("min");

                            Monitoring monitoring = new Monitoring(reg, cri, maj, min);
                            listMonitoring.add(monitoring);
                        }
                        adapterMonitoring.notifyDataSetChanged();
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
}