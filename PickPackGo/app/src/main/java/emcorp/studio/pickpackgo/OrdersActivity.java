package emcorp.studio.pickpackgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emcorp.studio.pickpackgo.adapter.AdapterListOrders;
import emcorp.studio.pickpackgo.data.DataGenerator;
import emcorp.studio.pickpackgo.model.Social;
import emcorp.studio.pickpackgo.utils.Constant;
import emcorp.studio.pickpackgo.utils.Tools;
import emcorp.studio.pickpackgo.widget.LineItemDecoration;

public class OrdersActivity extends AppCompatActivity {
    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterListOrders mAdapter;
    private ProgressDialog progressDialog;
    List<Social> items = new ArrayList<Social>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        LoadProcess();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        mAdapter = new AdapterListOrders(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListOrders.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Social obj, int position) {
                Intent i = new Intent(OrdersActivity.this,DetailActivity.class);
                i.putExtra("id_order",items.get(position).id);
                i.putExtra("so",items.get(position).name);
                startActivity(i);
//                Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    public void LoadProcess(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CETAK",response);
                        progressDialog.dismiss();
                        items.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                recyclerView.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_SHORT).show();
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
                                    String bin_name = isiArray.getString("bin_name");

                                    Social social = new Social();
                                    social.name = so;
                                    social.bin_name = bin_name;
                                    social.id = id_order;
                                    items.add(social);
                                }

                                initComponent();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(OrdersActivity.this,MainActivity.class));
            finish();
        } else {
//            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OrdersActivity.this,MainActivity.class));
        finish();
    }
}