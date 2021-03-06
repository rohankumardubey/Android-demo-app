package com.paad.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class Compass extends Activity {
    
	float pitch = 0;
	float roll = 0;
	float heading = 0;

	CompassView compassView;
	SensorManager sensorManager;
	
	@Override
	public void onCreate(Bundle icicle) {
	  super.onCreate(icicle); 
	  setContentView(R.layout.main);

	  compassView = (CompassView)this.findViewById(R.id.compassView);
	  sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	  updateOrientation(0, 0, 0);
	}

	private final SensorListener sensorListener = new SensorListener() {
	  public void onSensorChanged(int sensor, float[] values) {
	    updateOrientation(values[SensorManager.DATA_X], 
	                      values[SensorManager.DATA_Y], 
	                      values[SensorManager.DATA_Z]);
	  }

	  public void onAccuracyChanged(int sensor, int accuracy) {}
	};

  private void updateOrientation(float _roll, float _pitch, float _heading) {
	  heading = _heading;
	  pitch = _pitch;
	  roll = _roll;

	  if (compassView!= null) {
		  compassView.setBearing(heading);
	    compassView.setPitch(pitch);
	    compassView.setRoll(roll);
	    compassView.invalidate();
	  }
	}

  @Override
  protected void onResume()
  {
    super.onResume();
    sensorManager.registerListener(sensorListener, SensorManager.SENSOR_ORIENTATION, SensorManager.SENSOR_DELAY_FASTEST);
  }

  @Override
  protected void onStop()
  {
    sensorManager.unregisterListener(sensorListener);
    super.onStop();
  }
}