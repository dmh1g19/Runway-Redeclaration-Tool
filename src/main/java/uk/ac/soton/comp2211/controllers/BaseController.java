package uk.ac.soton.comp2211.controllers;

import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.BaseView;
import uk.ac.soton.comp2211.views.CalculationsView;
import uk.ac.soton.comp2211.views.MenuView;
import uk.ac.soton.comp2211.views.SelectionView;

public abstract class BaseController {

    //Model
    AirportModel model;

    //View
    BaseView view;

    public BaseController(BaseView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }

    public abstract void initialise();

    public void loadMenu() {
        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView, model);
        view.getView().getScene().setRoot(menuView.getView());
    }

    public void loadSelection() {
        SelectionView selectionView = new SelectionView();
        SelectionController selectionController = new SelectionController(selectionView, model);
        view.getView().getScene().setRoot(selectionView.getView());
    }

    public void loadCalculations() {
        CalculationsView calculationsView = new CalculationsView();
        CalculationsController calculationsController = new CalculationsController(calculationsView, model);
        view.getView().getScene().setRoot(calculationsView.getView());
    }

}
