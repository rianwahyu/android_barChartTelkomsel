package com.bibitproject.aplikasidatachart.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bibitproject.aplikasidatachart.Admin.Adapter.AdapterRegistrationUser;
import com.bibitproject.aplikasidatachart.Admin.Model.ModelRegistrationUser;
import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.Utils.MyConfig;
import com.bibitproject.aplikasidatachart.Utils.NetworkState;
import com.bibitproject.aplikasidatachart.databinding.ActivityRegistrationUserBinding;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.button.MaterialButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationUserActivity extends AppCompatActivity {

    Context context = this;

    ActivityRegistrationUserBinding binding;

    ProgressDialog progressDialog ;
    AdapterRegistrationUser adapterRegistrationUser;
    List<ModelRegistrationUser> listRegistration = new ArrayList<ModelRegistrationUser>();
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_registration_user);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration_user);

        progressDialog = new ProgressDialog(context);
        listRegistration = new ArrayList<>();

        binding.toolbar.setTitle("Registration User");
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


        binding.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddUser();
            }
        });

        binding.rcRegistrationUser.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcRegistrationUser.setLayoutManager(linearLayoutManager);
        adapterRegistrationUser = new AdapterRegistrationUser(context, listRegistration);
        binding.rcRegistrationUser.setAdapter(adapterRegistrationUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsers();
    }

    private void getUsers() {
        progressDialog.setMessage("Memuat Data ..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = NetworkState.getUrl()+"admin/getUser.php";
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

                            String idUser = users.getString("idUser");
                            String name = users.getString("name");
                            String username = users.getString("username");
                            String password = users.getString("password");
                            String role = users.getString("role");

                            ModelRegistrationUser modelJabatanCustom = new ModelRegistrationUser(idUser, name, username, password, role);
                            listRegistration.add(modelJabatanCustom);
                        }
                        adapterRegistrationUser.notifyDataSetChanged();
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

    private void dialogAddUser() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.create();

        MaterialEditText etNama = dialogView.findViewById(R.id.etNama);
        MaterialEditText etUsername = dialogView.findViewById(R.id.etUsername);
        MaterialEditText etPassword = dialogView.findViewById(R.id.etPassword);
        MaterialButton buttonTambah = dialogView.findViewById(R.id.btnTambah);


        SpinKitView spinKitView = dialogView.findViewById(R.id.loading);
        spinKitView.setVisibility(View.GONE);

        final AlertDialog dialogs = dialog.create();

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNama.getText().toString().isEmpty()){
                    etNama.setError("Mohon memasukkan nama");
                } else if (etUsername.getText().toString().isEmpty()){
                    etUsername.setError("Mohon memasukkan username");
                } else if (etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Mohon memasukkan password");
                }else {
                    prosesTambahUser();
                    spinKitView.setVisibility(View.VISIBLE);
                }
            }

            private void prosesTambahUser() {
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = NetworkState.getUrl()+"admin/addUser.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            String message = jsonObject.getString("message");
                            if (status == true){
                                MyConfig.showToast(context, message);
                                dialogs.dismiss();
                            }else{
                                spinKitView.setVisibility(View.GONE);
                                etUsername.setError(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.getMessage().toString());
                        MyConfig.showToast(context, error.getMessage().toString());
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //memasukkan parameter yang dibutuhkan pada server API untuk menambah jabatan
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", etNama.getText().toString().trim());
                        params.put("username", etUsername.getText().toString().trim());
                        params.put("password", etPassword.getText().toString().trim());
                        return params;
                    }
                };

                int socketTimeout = 30000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                mRequestQueue.add(stringRequest);
            }
        });

        dialogs.show();
    }
}