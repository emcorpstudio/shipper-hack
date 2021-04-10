package emcorp.studio.pickpackgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnLogin;
    private ProgressDialog progressDialog;
    TextView tvRegister,tvClient,tvForgot,tvGuest;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View parent_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvClient = (TextView) findViewById(R.id.tvClient);
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        tvGuest = (TextView) findViewById(R.id.tvGuest);
        parent_view = findViewById(android.R.id.content);
        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

//        if (Build.VERSION.SDK_INT >= 23){
//            if (checkPermission()){
//                //Permission already granted
//            } else {
//                requestPermission(); // Code for permission
//            }
//        }

//        if(SharedPrefManager.getInstance(getApplicationContext()).getReferences(Constant.PREFERENCES_LOGIN)!=null){
//            if(SharedPrefManager.getInstance(getApplicationContext()).getReferences(Constant.PREFERENCES_LOGIN).equals("1")){
//                startActivity(new Intent(emcorp.studio.e_report.LoginActivity.this,MainActivity.class));
//                finish();
//            }
//        }
//
//        tvForgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(Tools.sendWA(Constant.WA_NUMBER,"Halo, saya lupa password"));
//            }
//        });
//
//        tvGuest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_ROLE,"GUEST");
//                Intent i = new Intent(emcorp.studio.e_report.LoginActivity.this, ProdukActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(edtEmail.getText().toString().equals("")){
//                    edtEmail.setError("Email/username belum diisi!");
//                }else{
//                    if(edtPassword.getText().toString().equals("")){
//                        edtPassword.setError("Password belum diisi!");
//                    }else{
//                        LoginProcess();
//                    }
//                }
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();

            }
        });
//
//        tvRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(emcorp.studio.e_report.LoginActivity.this, RegisterActivity.class));
//                finish();
//            }
//        });
    }

//    private boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(emcorp.studio.e_report.LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (result == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        } else {
//            return false;
//        }
//    }

//    private void requestPermission() {
//
//        if (ActivityCompat.shouldShowRequestPermissionRationale(emcorp.studio.e_report.LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Toast.makeText(emcorp.studio.e_report.LoginActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(emcorp.studio.e_report.LoginActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.e("value", "Permission Granted, Now you can use local drive .");
//                } else {
//                    Log.e("value", "Permission Denied, You cannot use local drive .");
//                }
//                break;
//        }
//    }
//
//
//    public void LoginProcess(){
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constant.ROOT_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        Log.d("CETAK", Constant.ROOT_URL+" "+response);
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            JSONObject userDetails = obj.getJSONObject("hasil");
//                            String success = userDetails.getString("success");
//                            String message = userDetails.getString("message");
//
//                            if(success.equals("1")){
//                                //Succes
//                                String id = userDetails.getString("id_user");
//                                String nama = userDetails.getString("nama");
//                                String email = userDetails.getString("email");
//                                String nik = userDetails.getString("nik");
//                                String role = userDetails.getString("role");
//                                String hp = userDetails.getString("hp");
//                                String jabatan = userDetails.getString("jabatan");
//                                String foto = userDetails.getString("foto");
//
//                                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_ID,id);
//                                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_EMAIL,email);
//                                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_NAMA,nama);
//                                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_NIK,nik);
//                                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_ROLE,role);
//                                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_HP,hp);
//                                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_FOTO,foto);
//                                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_JABATAN,jabatan);
//                                SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_LOGIN,"1");
//                                startActivity(new Intent(emcorp.studio.e_report.LoginActivity.this,MainActivity.class));
//                                finish();
//                            }
//                            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("function", Constant.FUNCTION_LOGIN);
//                params.put("key", Constant.KEY);
//                params.put("nik", edtEmail.getText().toString());
//                params.put("password", edtPassword.getText().toString());
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

}
