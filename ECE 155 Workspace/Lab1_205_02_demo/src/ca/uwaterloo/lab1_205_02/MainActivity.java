    // -------------------------------------------------------------------
    // Department of Electrical and Computer Engineering
    // University of Waterloo
    //
	// Group Number:205-02
    // Student Names: Lingyi Li, Bill Schoen, James Fong
    // User-ids:l352li, wschoen, jnkfong
    //
    // Assignment: <Lab1>
    // Submission Date: May 16th, 2015
    //
    // We declare that, other than the acknowledgements listed below,
    // this program is my original work.
    //
    // Acknowledgements:
    // Except for the materials provided in the assignment requirements. 
    // All the code is our own work.
    // -------------------------------------------------------------------
package ca.uwaterloo.lab1_205_02;

import java.util.Arrays;

import android.support.v7.app.ActionBarActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SensorEventListener{
	
	private LineGraphView graph;
    
	private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accelerometer;
    private Sensor rotational;
    private Sensor magnetic;
    
    private Button btnReset;
 	private TextView textView;
    
    //accelerometer, magnetic field, rotational Sensor
    private float recordX [];
    private float recordY [];
    private float recordZ [];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//creates layout for the accelerometer graph
    	LinearLayout layGraph;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        recordX = new float [] {-100,-100,-100};
        recordY = new float [] {-100,-100,-100};
        recordZ = new float [] {-100,-100,-100};
        
        layGraph = (LinearLayout)findViewById(R.id.mainLayout);
        graph = new LineGraphView(getApplicationContext(),100, Arrays.asList("x", "y", "z"));
        graph.setVisibility(View.VISIBLE);
        layGraph.addView(graph, 0);

      //initializes the sensor manager and each individual sensor
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        rotational = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
       
       //creates button that resets the graph values,
       //since the values will never reach -100, the next sensor value will be read.
        btnReset = (Button)findViewById(R.id.btnResetMax);
        btnReset.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				for (int x = 0; x <= 2; x++)
				{
				recordX [x] = -100f; 
		        recordY [x] = -100f;
		        recordZ [x] = -100f;
		        }
			}
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    //when the app is resumed from pause, re-initialize the sensors
    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, rotational, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //when the app is paused, de-initialize sensors
    protected void onPause(){

        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public  void onSensorChanged(SensorEvent event){
    	//adds points to the graph based on current accelerometer readings and updates the text view.
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            graph.addPoint(event.values);
            textView = (TextView)findViewById(R.id.txtaccx);
            textView.setText("X: " + event.values[0]);
            textView = (TextView)findViewById(R.id.txtaccy);
            textView.setText("Y: " + event.values[1]);
            textView = (TextView)findViewById(R.id.txtaccz);
            textView.setText("Z: " + event.values[2]);
            
            if(event.values[0] > recordX[0])
            {
            	textView = (TextView)findViewById(R.id.txtMaxAccX);
            	recordX[0] = event.values[0];
            	textView.setText("X: " + recordX[0]);
            }
            if(event.values[1] > recordY[0])
            {
            	textView = (TextView)findViewById(R.id.txtMaxAccY);
            	recordY[0] = event.values[1];
            	textView.setText("Y: " + recordY[0]);
            }
            if(event.values[2] > recordZ[0])
            {
            	textView = (TextView)findViewById(R.id.txtMaxAccZ);
            	recordZ[0] = event.values[2];
            	textView.setText("Z: " + recordZ[0]);
            }  
        }
        //updates text view for light sensor readings
        else if(event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
        	textView = (TextView)findViewById(R.id.txtLightResult);
        	textView.setText("Lighting: " + event.values[0] + " lx");
        }
        //updates text view for rotational sensor values
        else if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {	
        	textView = (TextView)findViewById(R.id.txtRotX);
            textView.setText("X: " + event.values[0]);
            textView = (TextView)findViewById(R.id.txtRotY);
            textView.setText("Y: " + event.values[1]);
            textView = (TextView)findViewById(R.id.txtRotZ);
            textView.setText("Z: " + event.values[2]);
        	
        	if(event.values[0] > recordX[2])
            {
            	textView = (TextView)findViewById(R.id.txtMaxRotX);
            	recordX[2] = event.values[0];
            	textView.setText("X: " + recordX[2]);
            }
            if(event.values[1] > recordY[2])
            {
            	textView = (TextView)findViewById(R.id.txtMaxRotY);
            	recordY[2] = event.values[1];
            	textView.setText("Y: " + recordY[2]);
            }
            if(event.values[2] > recordZ[2])
            {
            	textView = (TextView)findViewById(R.id.txtMaxRotZ);
            	recordZ[2] = event.values[2];
            	textView.setText("Z: " + recordZ[2]);
            }        	
        }
        //updates text view for magnetic field sensor values
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
        	textView = (TextView)findViewById(R.id.txtMagX);
            textView.setText("X: " + event.values[0]);
            textView = (TextView)findViewById(R.id.txtMagY);
            textView.setText("Y: " + event.values[1]);
            textView = (TextView)findViewById(R.id.txtMagZ);
            textView.setText("Z: " + event.values[2]);
        	
        	if(event.values[0] > recordX[1])
            {
            	textView = (TextView)findViewById(R.id.txtMaxMagX);
            	recordX[1] = event.values[0];
            	textView.setText("X: " + recordX[1]);
            }
            if(event.values[1] > recordY[1])
            {
            	textView = (TextView)findViewById(R.id.txtMaxMagY);
            	recordY[1] = event.values[1];
            	textView.setText("Y: " + recordY[1]);
            }
            if(event.values[2] > recordZ[1])
            {
            	textView = (TextView)findViewById(R.id.txtMaxMagZ);
            	recordZ[1] = event.values[2];
            	textView.setText("Z: " + recordZ[1]);
            }	
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    
}
