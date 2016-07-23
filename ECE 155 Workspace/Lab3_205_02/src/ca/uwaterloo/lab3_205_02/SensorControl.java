package ca.uwaterloo.lab3_205_02;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.widget.TextView;

public class SensorControl implements SensorEventListener {
	
	private double pts, prev, endTime;
	float lowThresh, highThresh, rotaty, rotatx;
	float [] smoothedAccel, smoothedMag;
	int c, state, steps; 
	TextView output, stepData, txtLowThresh, txtHighThresh, txtDirection;
	SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor accelerometer2;
    private Sensor rotation;
    //private Sensor magnetometer;
    private long startTime;
    private double angle;
    double xTotal;
    double yTotal;
	LineGraphView graph;
	public SensorControl(SensorManager sensorManager, TextView outputView,TextView stepDataView,TextView txtLowThresh, 
			TextView txtHighThresh, TextView txtDirection, LineGraphView graph)
	{
		//Set general TextViews and needed values
		output = outputView;
		stepData = stepDataView;
		this.txtHighThresh = txtHighThresh;
		this.txtLowThresh = txtLowThresh;
		this.graph = graph;
		this.smoothedAccel = new float[] {0,0,0};
		this.smoothedMag = new float[] {0,0,0};
		this.lowThresh= 0.1f;
		this.highThresh= 0.2f;
		this.txtDirection = txtDirection;
		this.c = 1;
		this.steps = 0;
		this.state = 0;
		this.rotaty = 0;
		this.rotatx = 0;
		this.angle = 0.0;
		this.xTotal = 0;
		this.yTotal = 0;
		
		this.sensorManager = sensorManager;
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        //magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_FASTEST);

        accelerometer2 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, rotation, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, accelerometer2,SensorManager.SENSOR_DELAY_FASTEST);
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		//Check if the event passing through contains linear acceleration values
		if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
		{
			//Set and smooth out z values according to the accuracy level set
			smoothedAccel[2] += ((event.values[2] - smoothedAccel[2]) / c);
			pts = event.values[2];
			//Set and smooth out y values according to the accuracy level set
			this.rotaty += (event.values[1] - this.rotaty)/ c;
			smoothedAccel[1] = (float) this.rotaty;	
			this.rotatx += (event.values[0] - this.rotatx)/ c;
			smoothedAccel[0] = (float) this.rotatx;		
			//Add smoothened value points to graph 
			this.graph.addPoint(smoothedAccel);
			
			//Display accuracy level
			output.setText("Accuracy:" + c);
			//Display text value of thresholds set
			this.txtLowThresh.setText("Low Threshold: " + this.lowThresh);
			this.txtHighThresh.setText("High Threshold: " + this.highThresh);
			//Finite State Machine
					//Start at approximately 0 State: 0
					if(Math.round(pts) == 0 && state != 4)
					{
						state = 1;
						//startTime = System.nanoTime();
						prev = pts;
					}
					//Check if values are increasing and under set threshold State: 1
					if(prev < pts && pts < this.lowThresh && (state==1 || state==2))
					{
						prev = pts;
						state = 2;
					}
					//Check if values are within set threshold (Peak) State: 2
					if(pts > this.lowThresh && pts < this.highThresh && (state==2 || state==3))
					{
						state = 3;
						prev = pts;
					}
					
					//Check if values goes beyond high threshold, if so, State: 0 
					if(pts > this.highThresh)
					{
						state = 0;
					}
					//Check if values are decreasing and below low threshold (decreasing from peak) State: 3
					if(prev > pts && pts < this.lowThresh && 0 < pts && (state==3 || state==4))
					{
						prev = pts;
						state = 4;
					}
					//Check if values are below negative values State: 4
					if(pts < 0 && (state==4 || state==5))
					{
						prev = pts;
						state = 5;
					}
					//Check if values is beyond lower threshold (Too low) State: 0
					if(pts < -4.0){
						state = 0;
					}
					//Check if rotation is applied State: 0
					if(this.rotaty > 3.5 || this.rotaty < -3.5)
					{
						state = 0;
					}
					//Check if state has reached state 5 and values have returned back to 0 State:5 -> State:0 
					if(state==5 && Math.round(pts) == 0)
					{
						
						//xTotal +=0.762*Math.round(Math.sin(angle)*100)/100;
						//yTotal +=0.762*Math.round(Math.cos(angle)*100)/100;
						xTotal +=Math.sin(angle);
						yTotal +=Math.cos(angle);
						//endTime += 0.5*Math.abs(Math.pow((System.nanoTime()-startTime)*0.00000001,2)*event.values[0]);
						txtDirection.setText("Displacement East:" + xTotal + "\n Displacement North:" + yTotal + "\n" + Math.toDegrees(angle));
						//startTime = System.nanoTime();
						addStep();
						state = 0;
					}	
		}
		/*if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
		{	
			smoothedMag = event.values; 
		}
		float R[] = new float[9];
        float I[] = new float[9];
        boolean success = SensorManager.getRotationMatrix(R, I, smoothedAccel, smoothedMag);
        if (success) {
            float orientation[] = new float[3];
            SensorManager.getOrientation(R, orientation);
            float angle = (float) ((-(orientation[0]) * 360) / Math.PI);
            //txtDirection.setText("At:" + String.valueOf((int)angle) + (char) 0x00B0);
        }*/
		if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
		{
			angle = Math.asin((double)event.values[2])*2;
			txtDirection.setText("Displacement East:" + String.format("%.2f",xTotal) + "\n Displacement North:" + String.format("%.2f",yTotal) + "\n" + Math.toDegrees(angle));
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	//Method to change accuracy level (c)
	public void changeFilter(int level){
		this.c = level;
	}
	//Method to change low threshold
	public void changeLowThresh(float lowThresh){
		this.lowThresh = lowThresh;
	}
	//Method to change high threshold
	public void changeHighThresh(float highThresh){
		this.highThresh = highThresh;
	}
	//Method to add step and change step textView value
	private void addStep()
	{
		ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION,100);
		tg.startTone(ToneGenerator.TONE_PROP_BEEP);
		this.steps++;
		stepData.setText("Steps: " + this.steps);
	}
	//Method to reset step back to 0 and change TextView value
	public void resetStep()
	{
		this.steps = 0;
		this.endTime = 0;
		this.xTotal = 0;
		this.yTotal = 0;
		stepData.setText("Steps: " + steps);
		txtDirection.setText("Displacement East:" + xTotal + "\n Displacement North:" + yTotal + "\n" + Math.toDegrees(angle));
	}
}
