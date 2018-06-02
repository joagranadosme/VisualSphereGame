package com.example.jonathan.processingtest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

import java.text.DecimalFormat;

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
    private int rotation;

    //Tap rotation variable.
    private boolean flag = false;

    //Frames for planets.
    private Frame universe, sun, earth, moon;

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

        //Initialize universe
        universe = new Frame();
        universe.translate(width/2, height/2);

        //Initialize sun
        sun = new Frame(universe, new Vector(0,0), new Quaternion());

        //Initialize earth
        earth = new Frame(sun, new Vector(400, 0), new Quaternion());

        //Initialize moon
        moon = new Frame(earth, new Vector(100, 0), new Quaternion());

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
        Scene.applyTransformation(this.g, universe);
        push();
        Scene.applyTransformation(this.g, sun);
        stroke(0);
        fill(255,255,0);
        planet(100);
        push();
        Scene.applyTransformation(this.g, earth);
        stroke(0);
        fill(0,0,255);
        planet(50);
        push();
        Scene.applyTransformation(this.g, moon);
        noStroke();
        fill(200, 200, 200);
        planet(10);
        pop();
        pop();
        pop();
        pop();

    }

    public void mouseDragged() {

        universe.setTranslation(mouseX, mouseY);

    }

    public void mousePressed() {

        flag = !flag;

    }

    public void updateFrames() {

        if(flag)
            rotation+=5;

        universe.setRotation(new Quaternion(new Vector(1.0F, 0.0F, 0.0F), radians(-20F * ax)));
        sun.setRotation(new Quaternion(new Vector(0.0F, 1.0F, 0.0F), radians(0.5F * rotation)));
        earth.setRotation(new Quaternion(new Vector(0.0F, 1.0F, 0.0F), radians(2*rotation)));
        moon.setRotation(new Quaternion(new Vector(0.0F, 1.0F, 0.0F), radians(rotation)));

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
            //ax =  ((float)((int)(sensorEvent.values[0]*10)))/10;
            ax = sensorEvent.values[0];
            ay = sensorEvent.values[1];
            az = sensorEvent.values[2];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    }

}
