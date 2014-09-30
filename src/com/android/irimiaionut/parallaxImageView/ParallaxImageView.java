package com.android.irimiaionut.parallaxImageView;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ParallaxImageView extends ImageView implements SensorEventListener {
	float[] rotMat = new float[16];
	float[] vals = new float[3];
	//sensor parallax effect
	private SensorManager senSensorManager;
	private Sensor senAccelerometer;
	private int sideVerticalMargin, sideHorizontalMargin;
    public ParallaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
		senSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	    senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
	    senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_FASTEST);
    }

    public ParallaxImageView(Context context) {
        super(context);
    }
    
    public void setMargins(int VM, int HM){
    	this.sideVerticalMargin = VM;
    	this.sideHorizontalMargin = HM;
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
	    Sensor mySensor = event.sensor;
	    
	    if (mySensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
	    	
	    	long actualTime = System.currentTimeMillis();

        	RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)this.getLayoutParams();
            
         // Convert the rotation-vector to a 4x4 matrix.
        	try {
        		SensorManager.getRotationMatrixFromVector(rotMat, event.values);
        		} catch (IllegalArgumentException e) {
        		if (event.values.length > 3) {  
        		// Note 3 bug
        		float[] newVector = new float[] {
        				event.values[0],
        				event.values[1],
        				event.values[2]
        		};
        		SensorManager.getRotationMatrixFromVector(rotMat, newVector); 
        		}
        	}
            SensorManager.remapCoordinateSystem(rotMat,
                        SensorManager.AXIS_Y, SensorManager.AXIS_X,
                        rotMat);
            
            SensorManager.getOrientation(rotMat, vals);

            vals[0] = (float) Math.toDegrees(vals[0]);
            vals[1] = (float) Math.toDegrees(vals[1]);
            vals[2] = (float) Math.toDegrees(vals[2]);
            
            int leftfloat = (int) (-160-(vals[1]*1.7));
            int rightfloat = (int) (-160+(vals[1]*1.7));
            
            int topfloat;
			int bottomfloat;
			
			if(vals[2]>0){
	            topfloat=(int) (-240+(vals[2]*1.5));
	            bottomfloat=(int) (-240-(vals[2]*1.5));
            }else{
	            topfloat=(int) (-240-(vals[2]*1.5));
	            bottomfloat=(int) (-240+(vals[2]*1.5));
            }
			
	        params.setMargins(leftfloat, topfloat, rightfloat, bottomfloat);
	        this.setLayoutParams(params);
	    }
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	public void onPause() {
	    senSensorManager.unregisterListener(this);
	}
	public void onResume() {
	    senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
	}

}
