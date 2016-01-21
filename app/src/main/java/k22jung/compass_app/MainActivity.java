package k22jung.compass_app;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity implements SensorEventListener {

    private ImageView pointer;
    private float currentDegree = 0f;
    private SensorManager sm;
    TextView heading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heading = (TextView) findViewById(R.id.heading);
        pointer = (ImageView) findViewById(R.id.pointer);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    protected void onResume() {
        super.onResume();

        sm.registerListener(this,
                sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);

        RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(210);
        ra.setFillAfter(true);
        pointer.startAnimation(ra);

        heading.setText("Heading: " + degree + " °");
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Figure 8 calibration code
    }

}
