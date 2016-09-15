package com.example.aemacc13.accelerameter;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    //constants
    private static final String X= "X", Y= "Y", Z="Z";
    private static final String LAT= "LATITUDE", LON= "LONGITUDE";

    //views
    TextView x_accel_view= null;
    TextView y_accel_view= null;
    TextView z_accel_view= null;
    TextView latitude_view= null;
    TextView longitude_view= null;

    //handlers
    AccelerometerHandler ah= null;
    LocationHandler lh= null;

    //accelerations
    float x= 0, y= 0, z= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //accelerometer views
        this.x_accel_view= (TextView)findViewById(R.id.x_accel_view);
        this.y_accel_view= (TextView)findViewById(R.id.y_accel_view);
        this.z_accel_view= (TextView)findViewById(R.id.z_accel_view);

        //location views
        this.latitude_view= (TextView)findViewById(R.id.latitude_view);
        this.longitude_view= (TextView)findViewById(R.id.longitude_view);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferences(MODE_PRIVATE).edit().putFloat(X, x).apply();
        getPreferences(MODE_PRIVATE).edit().putFloat(Y, y).apply();
        getPreferences(MODE_PRIVATE).edit().putFloat(Z, z).apply();
        getPreferences(MODE_PRIVATE).edit().putFloat(LAT, (float)lh.getLatitude()).apply();
        getPreferences(MODE_PRIVATE).edit().putFloat(LON, (float)lh.getLongitude()).apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.ah= new AccelerometerHandler(this);
        this.ah.addObserver(this);

        this.lh= new LocationHandler(this);
        this.lh.addObserver(this);

        this.x_accel_view.setText(Float.toString(getPreferences(MODE_PRIVATE).getFloat(X, 0)));
        this.y_accel_view.setText(Float.toString(getPreferences(MODE_PRIVATE).getFloat(Y, 0)));
        this.z_accel_view.setText(Float.toString(getPreferences(MODE_PRIVATE).getFloat(Z, 0)));
        this.latitude_view.setText(Float.toString(getPreferences(MODE_PRIVATE).getFloat(LAT, 0)));
        this.longitude_view.setText(Float.toString(getPreferences(MODE_PRIVATE).getFloat(LON, 0)));
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof AccelerometerHandler){
            float[] xyz= (float[]) o;
            x= xyz[0]; y= xyz[1]; z= xyz[2];
            this.x_accel_view.setText(Float.toString(x));
            this.y_accel_view.setText(Float.toString(y));
            this.z_accel_view.setText(Float.toString(z));
        } else if (observable instanceof LocationHandler) {
            this.latitude_view.setText(Double.toString(lh.getLatitude()));
            this.longitude_view.setText(Double.toString(lh.getLongitude()));
        }
    }
}
