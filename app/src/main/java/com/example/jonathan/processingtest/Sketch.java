package com.example.jonathan.processingtest;

import processing.core.PApplet;

import frames.input.*;
import frames.primitives.*;
import frames.core.*;
import frames.processing.*;

public class Sketch extends PApplet {

    private Scene scene;

    public void settings() {
        fullScreen();
    }

    public void setup() {
        //scene = new Scene(this);
        background(175);
    }

    public void draw() {
        background(175);
        fill(255, 0, 0);
        stroke(255, 0, 255);
        if(mousePressed) {
            ellipse(mouseX, mouseY, 50, 50);
        }
    }

}
