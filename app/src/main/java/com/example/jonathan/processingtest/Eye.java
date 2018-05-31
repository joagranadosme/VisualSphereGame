package com.example.jonathan.processingtest;

import frames.core.Graph;
import frames.core.Node;
import processing.event.Event;

/**
 * Created by jonathan on 31/05/18.
 */

public class Eye extends Node {

    public Eye(Graph graph) {
        super(graph);
    }

    protected Eye(Graph otherGraph, Eye otherNode) {
        super(otherGraph, otherNode);
    }

    @Override
    public Eye get(){
        return new Eye(graph(), this);
    }

    @Override
    public void interact(frames.input.Event event) {

    }
}
