package emcorp.studio.pickpackgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import emcorp.studio.pickpackgo.model.Simulation;
import emcorp.studio.pickpackgo.model.Social;
import emcorp.studio.pickpackgo.utils.Tools;

public class SimulationActivity extends AppCompatActivity {
    EditText edtRackA, edtOpenA, edtRackB, edtOpenB, edtRackNewOrder, edtBeratOrderBaru;
    Button bt_submit;
    TextView tvPicker,tvScore;
    //Config
    double maxWeight = 10000;
    String picker = "";
    String score = "";
    String[] picker_name = new String[]{"A","B"};
    String[][] kordinat_rack = new String[][]{
            //Name , X , Y
            {"1A","1","1"},
            {"2A","1","2"},
            {"3A","1","3"},
            {"4A","1","4"},
            {"5A","1","5"},
            {"1B","2","1"},
            {"2B","2","2"},
            {"3B","2","3"},
            {"4B","2","4"},
            {"5B","2","5"},
            {"1C","3","1"},
            {"2C","3","2"},
            {"3C","3","3"},
            {"4C","3","4"},
            {"5C","3","5"},
            {"1D","4","1"},
            {"2D","4","2"},
            {"3D","4","3"},
            {"4D","4","4"},
            {"5D","4","5"},
    };
    List<String> pickingA = new ArrayList<String>();
    List<String> pickingB = new ArrayList<String>();
    List<String> openPickingA = new ArrayList<String>();
    List<String> openPickingB = new ArrayList<String>();
    List<String> rackOrderBaru = new ArrayList<String>();
    List<String> weightOrderBaru = new ArrayList<String>();
    List<Simulation> calculationSimulation = new ArrayList<Simulation>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        initToolbar();
        initComponent();
    }

    private void initComponent() {
        edtRackA = findViewById(R.id.edtRackA);
        edtOpenA = findViewById(R.id.edtOpenA);
        edtRackB = findViewById(R.id.edtRackB);
        edtOpenB = findViewById(R.id.edtOpenB);
        edtRackNewOrder = findViewById(R.id.edtRackNewOrder);
        edtBeratOrderBaru = findViewById(R.id.edtBeratOrderBaru);
        bt_submit = findViewById(R.id.bt_submit);
        tvPicker = findViewById(R.id.tvPicker);
        tvScore = findViewById(R.id.tvScore);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentCalculation();
            }
        });
    }

    private void assignmentCalculation(){
        pickingA = new ArrayList<String>(Arrays.asList(edtRackA.getText().toString().split(",")));
        pickingB = new ArrayList<String>(Arrays.asList(edtRackB.getText().toString().split(",")));
        openPickingA = new ArrayList<String>(Arrays.asList(edtOpenA.getText().toString().split(",")));
        openPickingB = new ArrayList<String>(Arrays.asList(edtOpenB.getText().toString().split(",")));
        rackOrderBaru = new ArrayList<String>(Arrays.asList(edtRackNewOrder.getText().toString().split(",")));
        weightOrderBaru = new ArrayList<String>(Arrays.asList(edtBeratOrderBaru.getText().toString().split(",")));
        int loadA = Integer.valueOf(edtOpenA.getText().toString());
        int loadB = Integer.valueOf(edtOpenB.getText().toString());

        //Cek apakah ada orderan yang beratnya lebih dari maksimal berat?
        picker = "";
        score = "";
        boolean weight_limit = false;
        for(int i=0;i<weightOrderBaru.size();i++){
            Double weight = Double.valueOf(weightOrderBaru.get(i));
            if(weight > maxWeight){
                weight_limit = true;
                break;
            }
        }

        if(weight_limit){
            //Ada orderan yang melebihi limit
            if(loadA==loadB){
                int rnd = new Random().nextInt(picker_name.length);
                picker = picker_name[rnd];
                score = "Random";
            }else{
                //Jika picking open sama, maka pilih yang paling kecil
                if(loadA<loadB){
                    picker = picker_name[0];
                }else if(loadB<loadA){
                    picker = picker_name[1];
                }
                score = "Smallest workload";
            }
        }else{
            //Tidak ada orderan yang melebihi limit
            calculationSimulation.clear();
            //Add si A
            for(int i=0;i<pickingA.size();i++){
                for(int j=0;j<rackOrderBaru.size();j++){
                    Simulation simulation = new Simulation();
                    simulation.picker = "A";
                    simulation.rackPicker = pickingA.get(i);
                    simulation.rackOrder = rackOrderBaru.get(j);
                    simulation.xRackPicker = findCordinat(pickingA.get(i),"x");
                    simulation.yRackPicker = findCordinat(pickingA.get(i),"y");
                    simulation.xRackOrder = findCordinat(rackOrderBaru.get(j),"x");
                    simulation.yRackOrder = findCordinat(rackOrderBaru.get(j),"y");
                    simulation.calculation = Double.valueOf(0);
                    calculationSimulation.add(simulation);
                }
            }

            //Add si B
            for(int i=0;i<pickingB.size();i++){
                for(int j=0;j<rackOrderBaru.size();j++){
                    Simulation simulation = new Simulation();
                    simulation.picker = "B";
                    simulation.rackPicker = pickingB.get(i);
                    simulation.rackOrder = rackOrderBaru.get(j);
                    simulation.xRackPicker = findCordinat(pickingB.get(i),"x");
                    simulation.yRackPicker = findCordinat(pickingB.get(i),"y");
                    simulation.xRackOrder = findCordinat(rackOrderBaru.get(j),"x");
                    simulation.yRackOrder = findCordinat(rackOrderBaru.get(j),"y");
                    simulation.yRackOrder = findCordinat(rackOrderBaru.get(j),"y");
                    simulation.calculation = Double.valueOf(0);
                    calculationSimulation.add(simulation);
                }
            }

            //Menghitung Jarak
            double totalA = 0;
            double totalB = 0;
            for(int i=0;i<calculationSimulation.size();i++){
                int x1 = Integer.valueOf(calculationSimulation.get(i).xRackPicker);
                int y1 = Integer.valueOf(calculationSimulation.get(i).yRackPicker);
                int x2 = Integer.valueOf(calculationSimulation.get(i).xRackOrder);
                int y2 = Integer.valueOf(calculationSimulation.get(i).yRackOrder);
                double x = Math.pow(x1-x2,2);
                double y = Math.pow(y1-y2,2);
                double hasil = Math.sqrt(x+y);
                calculationSimulation.get(i).calculation = hasil;
                if(calculationSimulation.get(i).picker.toString().equals("A")){
                    totalA = totalA + hasil;
                }
                if(calculationSimulation.get(i).picker.toString().equals("B")){
                    totalB = totalB + hasil;
                }
            }

            for(int i=0;i<calculationSimulation.size();i++){
                Log.d("CETAK",
                        calculationSimulation.get(i).picker+" - "+
                            calculationSimulation.get(i).rackPicker+" - "+
                            calculationSimulation.get(i).rackOrder+" - "+
                            calculationSimulation.get(i).xRackPicker+" - "+
                            calculationSimulation.get(i).yRackPicker+" - "+
                            calculationSimulation.get(i).xRackOrder+" - "+
                            calculationSimulation.get(i).yRackOrder+" = "+
                            String.valueOf(calculationSimulation.get(i).calculation)
                );
            }
            Log.d("CETAK","TOTAL A : "+String.valueOf(totalA));
            Log.d("CETAK","TOTAL B : "+String.valueOf(totalB));

            if(totalA==totalB){
                int rnd = new Random().nextInt(picker_name.length);
                picker = picker_name[rnd];
                score = "Random\nScore A : "+String.format("%.3f", totalA)+"\n"+"Score B : "+String.format("%.3f", totalB);
            }else{
                //Pilih yang paling dekat
                if(totalA<totalB){
                    picker = picker_name[0];
                }else if(totalB<totalA){
                    picker = picker_name[1];
                }
                score = "Choose smallest distance\nScore A : "+String.format("%.3f", totalA)+"\n"+"Score B : "+String.format("%.3f", totalB);
            }
        }

        tvPicker.setText("Assign to staff "+picker);
        tvScore.setText(score);

    }

    private String findCordinat(String name, String xy){
        String kordinat = "";
        for(int i=0;i<kordinat_rack.length;i++){
            if(kordinat_rack[i][0].equals(name)){
                if(xy.equals("x")){
                    kordinat = kordinat_rack[i][1];
                }else if(xy.equals("y")){
                    kordinat = kordinat_rack[i][2];
                }
                break;
            }
        }
        return kordinat;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assignment Simulation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(SimulationActivity.this,MainActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SimulationActivity.this,MainActivity.class));
        finish();
    }
}