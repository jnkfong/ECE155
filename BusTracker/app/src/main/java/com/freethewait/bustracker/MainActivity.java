package com.freethewait.bustracker;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    TextView tvBusTime;
    Spinner spnBusRoute;
    BufferedReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnBusRoute = (Spinner)findViewById(R.id.SpnBusRoute);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,this.getBusRoute());
        spnBusRoute.setAdapter(adapter_state);

        tvBusTime = (TextView)findViewById(R.id.txtBusTime);

        spnBusRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvBusTime.setText(spnBusRoute.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public String [] getBusRoute()
    {
        String busRouteWNum;
        String [] busRouteOnly;
        ArrayList <String> busRoutes = new ArrayList <> ();
        try{
            reader = new BufferedReader(new InputStreamReader(this.getAssets().open("busroute.csv")));
            while ((busRouteWNum = reader.readLine()) != null)
            {
                busRouteOnly = busRouteWNum.split(",");
                busRoutes.add(busRouteOnly[0]);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        busRouteOnly = new String [busRoutes.size()];
        for (int x = 0; x< busRoutes.size(); x++)
        {
            busRouteOnly [x] = String.valueOf(busRoutes.get(x));
        }
        return busRouteOnly;
    }
    public String getBusStops (String busRouteNum, String busDay){
        String work = "";
        try {
            String temp;
            String [] parts;

            reader = new BufferedReader (new InputStreamReader(this.getAssets().open((busDay + busRouteNum +".csv").toLowerCase())));
            while ((temp = reader.readLine()) != null) {
                parts = temp.split(",");
                work += parts[0] + "\n";
            }
            reader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return work;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
