package ca.uwaterloo.lab3_205_02;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class MagSensorEventListener implements SensorEventListener {

	private float [] mgravity;
    private float [] mgeomagnet;
    private TextView directionView;
    
	public MagSensorEventListener(TextView directionView) {
		this.directionView = directionView;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mgravity = event.values;

        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            mgeomagnet = event.values;
        }
        if(mgeomagnet != null && mgravity != null){
            
                float R[] = new float[9];
                float I[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, mgravity, mgeomagnet);
                if (success) {
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    float angle;
                    angle = (float) ((-(orientation[0]) * 360) / Math.PI);
                    directionView.setText("At:" + String.valueOf((int)angle) + (char) 0x00B0);
                
            }
    }
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
}
