/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.main;

import ec.edu.espol.common.Actor;
import ec.edu.espol.constants.CONSTANTES;
import ec.edu.espol.util.GraphLA;
import ec.edu.espol.util.Reader;
import ec.edu.espol.views.PrincipalView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author TBeltran
 */
public class MainOB extends Application{
    public static Scene mainScene;
    public static Stage mainStage;
    public static PrincipalView mainView;
    public static GraphLA<Actor> graphOfB;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        graphOfB = Reader.cargarGraph();
        mainStage = primaryStage;
        mainView = new PrincipalView();
        mainScene = new Scene(mainView.getRoot(), 800, 650);
        mainScene.getStylesheets().add(CONSTANTES.RUTA_STYLE_SHEET);
        mainStage.setTitle("The Oracle of Bacon G7");
        mainStage.setScene(mainScene);
        mainStage.show();
    }
    
}
