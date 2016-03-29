package edu.pw.elka.andromote.functionalityframework.functions;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Klasa abstrakcyjna, która służy do definiowania funkcji opartych na wykorzystywaniu sensorów urządzenia Android
 * @author Sebastian Luczak
 *
 */
public abstract class BaseSensorFunction extends BaseFunction implements SensorEventListener {
	protected SensorManager sensorManager;
	protected Sensor sensor;

	public BaseSensorFunction(Context context, int sensorType) {
		super(context);
		if(sensorType != 0) {
			sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
			sensor = sensorManager.getDefaultSensor(sensorType);
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	@Override
	public void cleanup() {
		sensorManager.unregisterListener(this, sensor);
		logger.info("Cleanning up");
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {}
}
