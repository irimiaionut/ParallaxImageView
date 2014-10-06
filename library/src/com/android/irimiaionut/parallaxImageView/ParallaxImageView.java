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
	private float verticalMultiplier=1, horizontalMultiplier=1;
	
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
    	this.sideVerticalMargin = -VM;
    	this.sideHorizontalMargin = -HM;
	    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)this.getLayoutParams();
	    params.setMargins(-HM, -VM, -HM, -VM);
	    this.setLayoutParams(params);
    }

    public void setMultipliers(float Vertical, float Horizontal){
    	this.verticalMultiplier = Vertical;
    	this.horizontalMultiplier = Horizontal;
    }
    
	@Override
	public void onSensorChanged(SensorEvent event) {
	    Sensor mySensor = event.sensor;
	    
	    if (mySensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            
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
            
            int leftfloat = (int) (this.sideHorizontalMargin-(vals[1]*this.horizontalMultiplier));
            
            int topfloat;
			
			if(vals[2]>0){
	            topfloat=(int) (this.sideVerticalMargin+(vals[2]*this.verticalMultiplier));
            }else{
	            topfloat=(int) (this.sideVerticalMargin-(vals[2]*this.verticalMultiplier));
            }
			
			this.setX(leftfloat);
			this.setY(topfloat);
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
