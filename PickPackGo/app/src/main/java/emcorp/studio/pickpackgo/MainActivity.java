package emcorp.studio.pickpackgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import afu.org.checkerframework.checker.oigj.qual.O;
import emcorp.studio.pickpackgo.utils.Constant;
import emcorp.studio.pickpackgo.utils.Tools;

public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Toolbar toolbar;
    private LinearLayout btnDashboard,btnHelp,btnOrders,btnProcess,btnPick,btnPack,btnAnalytics,btnElearning,btnSecurity,btnProfile,btnSeeMore;
    private ImageButton btnLogout;
    private TextView tvOrder1,tvDate1,tvOrder2,tvDate2,tvOrder3,tvDate3,tvOrder4,tvDate4,tvOrder5,tvDate5,tvOrder6,tvDate6;
//    private ProgressDialog progressDialog;
    private ImageButton btnRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initNavigationMenu();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Dashboard");
        Tools.setSystemBarColor(this);
    }

    private void initNavigationMenu() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        tvOrder1 = findViewById(R.id.tvOrder1);
        tvDate1 = findViewById(R.id.tvDate1);
        tvOrder2 = findViewById(R.id.tvOrder2);
        tvDate2 = findViewById(R.id.tvDate2);
        tvOrder3 = findViewById(R.id.tvOrder3);
        tvDate3 = findViewById(R.id.tvDate3);
        tvOrder4 = findViewById(R.id.tvOrder4);
        tvDate4 = findViewById(R.id.tvDate4);
        tvOrder5 = findViewById(R.id.tvOrder5);
        tvDate5 = findViewById(R.id.tvDate5);
        tvOrder6 = findViewById(R.id.tvOrder6);
        tvDate6 = findViewById(R.id.tvDate6);
        btnSeeMore = findViewById(R.id.btnSeeMore);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnLogout = findViewById(R.id.btnLogout);
        btnDashboard = findViewById(R.id.btnDashboard);
        btnHelp = findViewById(R.id.btnHelp);
        btnOrders = findViewById(R.id.btnOrders);
        btnProcess = findViewById(R.id.btnProcess);
        btnPick = findViewById(R.id.btnPick);
        btnPack = findViewById(R.id.btnPack);
        btnAnalytics = findViewById(R.id.btnAnalytics);
        btnElearning = findViewById(R.id.btnElearning);
        btnSecurity = findViewById(R.id.btnSecurity);
        btnProfile = findViewById(R.id.btnProfile);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HelpActivity.class));
                finish();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OrdersActivity.class));
                finish();
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OrdersActivity.class));
                finish();
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AnalyticsActivity.class));
                finish();
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnElearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LearningActivity.class));
                finish();
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SimulationActivity.class));
                finish();
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                finish();
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadProcess();
            }
        });
        LoadProcess();
    }

    public void LoadProcess(){
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CETAK",response);
//                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){

                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String id_order = isiArray.getString("id_order");
                                    String warehouse_code = isiArray.getString("warehouse_code");
                                    String tenant_code = isiArray.getString("tenant_code");
                                    String so = isiArray.getString("so");
                                    String status = isiArray.getString("status");
                                    String courier = isiArray.getString("courier");
                                    String mcode = isiArray.getString("mcode");
                                    String description = isiArray.getString("description");
                                    String bin = isiArray.getString("bin");
                                    String id_user = isiArray.getString("id_user");
                                    String order_qty = isiArray.getString("order_qty");
                                    String picked_qty = isiArray.getString("picked_qty");
                                    String order_time = isiArray.getString("order_time");
                                    String picking_time = isiArray.getString("picking_time");
                                    String packing_time = isiArray.getString("packing_time");
                                    String complete_time = isiArray.getString("complete_time");

                                    switch (i){
                                        case 0 :
                                            tvOrder1.setText(so);
                                            tvDate1.setText(order_time);
                                            break;
                                        case 1 :
                                            tvOrder2.setText(so);
                                            tvDate2.setText(order_time);
                                            break;
                                        case 2 :
                                            tvOrder3.setText(so);
                                            tvDate3.setText(order_time);
                                            break;
                                        case 3 :
                                            tvOrder4.setText(so);
                                            tvDate4.setText(order_time);
                                            break;
                                        case 4 :
                                            tvOrder5.setText(so);
                                            tvDate5.setText(order_time);
                                            break;
                                        case 5 :
                                            tvOrder6.setText(so);
                                            tvDate6.setText(order_time);
                                            break;
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", Constant.FUNCTION_LISTORDERS);
                params.put("key", Constant.KEY);
                params.put("id_user", "1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}