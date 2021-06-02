package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import main.MenuAdminMain;

public class AdminReportController
{
    @FXML
    private Label fxMainLabel;

    public AdminReportController()
    {
    }

    @FXML
    public void initialize()
    {
        fxMainLabel.setText("ABAB\nBABA");
    }

    // Logout worker and return to login screen
    public void Back(ActionEvent event)
    {
        System.out.println("BACK");
        // back to worker menu
        MenuAdminMain menuAdminMain = new MenuAdminMain(640,480);
        menuAdminMain.show();
        // hide this window.
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
