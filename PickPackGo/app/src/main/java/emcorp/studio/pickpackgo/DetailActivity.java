package emcorp.studio.pickpackgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import afu.org.checkerframework.checker.oigj.qual.O;
import emcorp.studio.pickpackgo.fragment.DialogPaymentSuccessFragment;
import emcorp.studio.pickpackgo.utils.Constant;
import emcorp.studio.pickpackgo.utils.SharedPrefManager;
import emcorp.studio.pickpackgo.utils.Tools;

public class DetailActivity extends AppCompatActivity {
    String so = "";
    String id_order = "";
    LinearLayout btnCetak;
    ImageButton btnScan1, btnScan2;
    boolean prod1 = false;
    boolean prod2 = false;
    private MenuItem itemDone;
    String orderTime = "";
    String pickingTime = "";
    String packingTime = "";
    String completeTime = "";
    String durationTime = "";
    TextView tvOrderTime, tvPickingTime, tvPackingTime, tvCompleteTime, tvDurationTime;
    Date c;
    private ProgressBar progress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            so = extras.getString("so");
            id_order = extras.getString("id_order");
        }

        c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yy kk:mm:ss", Locale.getDefault());
        orderTime = df.format(c);

        initToolbar();
        initComponent();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(so);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void showTime(){
        tvOrderTime.setText(orderTime);
        tvPickingTime.setText(pickingTime);
        tvPackingTime.setText(packingTime);
        tvCompleteTime.setText(completeTime);
        if(!completeTime.toString().equals("")){
            //Show duration
            tvDurationTime.setText(durationTime);
        }
    }

    private void initComponent() {
        btnCetak = findViewById(R.id.btnCetak);
        btnScan1 = findViewById(R.id.btnScan1);
        btnScan2 = findViewById(R.id.btnScan2);
        tvOrderTime = findViewById(R.id.tvOrderTime);
        tvPickingTime = findViewById(R.id.tvPickingTime);
        tvPackingTime = findViewById(R.id.tvPackingTime);
        tvCompleteTime = findViewById(R.id.tvCompleteTime);
        tvDurationTime = findViewById(R.id.tvDurationTime);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        showTime();
        btnScan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prod1 = true;
                btnScan1.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Picked",Toast.LENGTH_SHORT).show();
                if(prod2&&prod1){
                    c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yy kk:mm:ss", Locale.getDefault());
                    packingTime = df.format(c);
                    btnCetak.setVisibility(View.VISIBLE);
                    showTime();
                }
            }
        });

        btnScan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prod2 = true;
                btnScan2.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Picked",Toast.LENGTH_SHORT).show();
                if(prod2&&prod1){
                    c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yy kk:mm:ss", Locale.getDefault());
                    packingTime = df.format(c);
                    btnCetak.setVisibility(View.VISIBLE);
                    showTime();
                }
            }
        });

        btnCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(completeTime.toString().equals("")){
                    c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yy kk:mm:ss", Locale.getDefault());
                    completeTime = df.format(c);

//                    Toast.makeText(getApplicationContext(),"CETAK",Toast.LENGTH_SHORT).show();

                    SimpleDateFormat format = new SimpleDateFormat("dd MMM yy kk:mm:ss");
                    Date orderDate = null;
                    Date completeDate = null;
                    try {
                        orderDate = format.parse(orderTime);
                        completeDate = format.parse(completeTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Get msec from each, and subtract.
                    long diff = completeDate.getTime() - orderDate.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000);
                    //System.out.println("Time in seconds: " + diffSeconds + " seconds.");
                    //System.out.println("Time in minutes: " + diffMinutes + " minutes.");
                    //System.out.println("Time in hours: " + diffHours + " hours.");
                    durationTime = String.valueOf(diffSeconds)+" second";
                    SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_COMPLETETIME,completeTime);
                    SharedPrefManager.getInstance(getApplicationContext()).setReferences(Constant.PREFERENCES_DURATION,durationTime);
                    showTime();
                    submitAction();
                }else{
                    submitAction();
                }
            }
        });
    }

    private void submitAction() {
        showDialogPaymentSuccess();
//        progress_bar.setVisibility(View.VISIBLE);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                progress_bar.setVisibility(View.GONE);
//            }
//        }, 1000);
    }

    private void showDialogPaymentSuccess() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogPaymentSuccessFragment newFragment = new DialogPaymentSuccessFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        itemDone = menu.findItem(R.id.action_done);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(DetailActivity.this,OrdersActivity.class));
            finish();
        } else {
            if(item.getTitle().equals("Map")){
                showDialogImageFull();
            }else if(item.getTitle().equals("Done")){
                c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd MMM yy kk:mm:ss", Locale.getDefault());
                pickingTime = df.format(c);
                showTime();
                itemDone.setVisible(false);
                btnScan1.setVisibility(View.VISIBLE);
                btnScan2.setVisibility(View.VISIBLE);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogImageFull() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailActivity.this,OrdersActivity.class));
        finish();
    }
}