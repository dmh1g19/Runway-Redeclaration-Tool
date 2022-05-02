package uk.ac.soton.comp2211.views;

import javafx.scene.Parent;

public abstract class BaseView {

    //View
    Parent view;


    public abstract Parent createView();

    public Parent getView() {
        return view;
    }

}
