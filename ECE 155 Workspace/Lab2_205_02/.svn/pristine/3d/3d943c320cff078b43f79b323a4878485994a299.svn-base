package ca.uwaterloo.lab2_205_02;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.widget.TextView;

public class AccSensorEventListener implements SensorEventListener {
	double pts;
	float lowThresh, highThresh, rotatZ;
	float [] smoothedAccel;
	int c, state, steps; 
	double prev;
	TextView output, stepData, txtLowThresh, txtHighThresh;

	LineGraphView graph;
	public AccSensorEventListener(TextView outputView,TextView stepDataView,TextView txtLowThresh, 
			TextView txtHighThresh, LineGraphView graph)
	{
		//Set general TextViews and needed values
		output = outputView;
		stepData = stepDataView;
		this.txtHighThresh = txtHighThresh;
		this.txtLowThresh = txtLowThresh;
		this.graph = graph;
		this.smoothedAccel = new float[] {0,0};
		this.lowThresh= 0.1f;
		this.highThresh= 0.2f;
		this.c = 1;
		this.steps = 0;
		this.state = 0;
		this.rotatZ = 0;
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		//Check if the event passing through contains linear acceleration values
		if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
		{
			//Set and smooth out y values according to the accuracy level set
			pts = event.values[1];
			smoothedAccel[0] += (pts - smoothedAccel[0]) / c;
			//Set and smooth out z values according to the accuracy level set
			this.rotatZ += (event.values[2] - this.rotatZ)/ c;
			smoothedAccel[1] = (float) pts;		
			//Add y and smoothened y points to graph 
			this.graph.addPoint(smoothedAccel);
			
			//Display accuracy level
			output.setText("Accuracy:" + c);
			//Display text value of thresholds set
			this.txtLowThresh.setText("Low Threshold: " + this.lowThresh);
			this.txtHighThresh.setText("High Threshold: " + this.highThresh);
			//Finite State Machine
					//Start at approximately 0 State: 0
					if(Math.round(pts) == 0 && state != 4 && state != 6)
					{
						state = 1;
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
					if(this.rotatZ > 2.5 || this.rotatZ < -2.5)
					{
						state = 0;
					}
					//Check if state has reached state 5 and values have returned back to 0 State:5 -> State:0 
					if(state ==5 && Math.round(pts) == 0)
					{
						addStep();
						state = 0;
					}	
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
		stepData.setText("Steps: " + steps);
	}
}
