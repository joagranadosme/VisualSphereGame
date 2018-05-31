package com.example.jonathan.processingtest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

import processing.core.PApplet;

import frames.primitives.*;
import frames.processing.*;

public class Sketch extends PApplet {

    //Context variables.
    private Context context;

    //Sensor variables.
    private Sensor sensor;
    private SensorManager manager;
    private AccelerometerListener listener;

    //Movement variables.
    private float ax, ay, az;
    private int translation, rotation;

    private Frame sun; //Frames for planets.

    /*
    TODO QUESTIONS:
        1) Why scene isn't working on Android.

    */

    public void settings() {

        fullScreen(P3D); //Set fullScreen and 3D

    }

    public void setup() {

        orientation(LANDSCAPE); //Set horizontal orientation

        context = getActivity(); //Get context

        //Initialize sensor
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new AccelerometerListener();
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        textFont(createFont("SansSerif", 10 * displayDensity));

        //Initialize sun
        sun = new Frame();
        sun.translate(width/2, height/2);

    }

    public void draw() {

        //Set Background
        background(0);

        //Display sensor values
        text("X: " + ax + "\nY: " + ay + "\nZ: " + az, 0, 0, width, height);

        //Update all frames
        updateFrames();

        //Draw all frames
        push();
        Scene.applyTransformation(this.g, sun);
        noStroke();
        fill(255, 255, 0);
        planet(300);
        pop();

    }

    void updateFrames() {

        rotation += ay;
        sun.setRotation(new Quaternion(new Vector(0.0F, 1.0F, 0.0F), radians(rotation)));

    }

    public void planet(float r) {
        sphere(r);
    }

    public void push() {
        pushStyle();
        pushMatrix();
    }

    public void pop() {
        popStyle();
        popMatrix();
    }

    //Resume collecting data from sensor
    public void onResume() {
        super.onResume();
        if(manager != null) {
            manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    //Stop collecting data from sensor
    public void onPause() {
        super.onPause();
        if(manager != null) {
            manager.unregisterListener(listener);
        }
    }


    //Class for receive the events from Accelerometer
    class AccelerometerListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            ax =  sensorEvent.values[0];
            ay =  sensorEvent.values[1];
            az =  sensorEvent.values[2];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    }




}
