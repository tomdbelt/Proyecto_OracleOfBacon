/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author TBeltran
 */
public class PrincipalView {
    private BorderPane root;
    private Label lTitle;
    private ImageView img;
    private TextField txtSrc;
    private TextField txtDst;
    private Button bFindLink;
    private VBox vResult;
    
    public PrincipalView(){
        root = new BorderPane();
        createTopSection();
        createCenterSection();
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
    
    private void createTopSection(){
        HBox boxTitle = new HBox();
        lTitle = new Label("THE ORACLE OF BACON");
        img = new ImageView(new Image("/ec/edu/espol/resources/img_kbacon.png", 100, 100, true, true));
        boxTitle.getChildren().addAll(lTitle,img);
        root.setTop(boxTitle);
    }
    
    private void createCenterSection(){
        HBox boxCenter = new HBox();
        txtSrc = new TextField();
        txtDst = new TextField();
        bFindLink = new Button("Find link");
        boxCenter.getChildren().addAll(txtSrc, new Label("to"), txtDst, bFindLink);
        root.setCenter(boxCenter);
    }
    
}
