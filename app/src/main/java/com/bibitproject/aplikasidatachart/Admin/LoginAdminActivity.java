package com.bibitproject.aplikasidatachart.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
import com.bibitproject.aplikasidatachart.Utils.MyConfig;
import com.bibitproject.aplikasidatachart.Utils.NetworkState;
import com.bibitproject.aplikasidatachart.Utils.SessionManager;
import com.bibitproject.aplikasidatachart.databinding.ActivityLoginAdminBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginAdminActivity extends AppCompatActivity {

    Context context = this;
    ActivityLoginAdminBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login_admin);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_admin);

        progressDialog = new ProgressDialog(context);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekLogin();
            }
        });

    }

    private void cekLogin() {
        if (binding.etUsername.getText().toString().trim().isEmpty()){
            MyConfig.showToast(context, "Mohon mengisi username");
        }else if (binding.etPassword.getText().toString().trim().isEmpty()){
            MyConfig.showToast(context, "Mohon mengisi password");
        }else {
            prosesLogin(binding.etUsername.getText().toString().trim(), binding.etPassword.getText().toString().trim() );
        }
    }

    private void prosesLogin(String username, String password) {
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = NetworkState.getUrl()+"admin/login.php";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    //menangkap respon JSON dari server
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");
                    //cek status login dari jsonserver
                    if (success==true){

                        String access = jsonObject.getString("access");
                        String id = jsonObject.getString("id");
                        //cek status akses user berasarkan hak akses
                        new SessionManager(context).login(id, access);
                        MyConfig.showToast(context, message);
                        startActivity(new Intent(context, HomeAdminActivity.class));
                        finish();
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
                Log.d("error", error.getMessage().toString());
                MyConfig.showToast(context, error.getMessage().toString());
            }
        }){
            //parameter untuk login yang akan di tangkap api server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        int socketTimeout = 30000;
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