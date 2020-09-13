/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.views;

import ec.edu.espol.common.Actor;
import ec.edu.espol.main.MainOB;
import java.util.List;
import java.util.ListIterator;
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
    private VBox vCenter;
    private HBox hResult;
    
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
        vCenter = new VBox();
        hResult = new HBox();
        hResult.setVisible(false);
        
        HBox boxQuery = new HBox();
        txtSrc = new TextField();
        txtDst = new TextField();
        bFindLink = new Button("Find link");
        bFindLink.setOnAction(e->{
            //System.out.println(txtSrc.getText().equals(""));
            hResult.getChildren().clear();
            VBox vDijkstra = new VBox();
            VBox vBFS = new VBox();
            VBox vDFS = new VBox();
            
            //SECCION DIJKSTRA
            Label lD = new Label("DIJKSTRA");
            long sTimeD = System.nanoTime();
            int numD = MainOB.graphOfB.numEdgesDijkstra(new Actor(txtSrc.getText()), new Actor(txtDst.getText()));
            long eTimeD = System.nanoTime()-sTimeD;
            Label ltimeD = new Label("Tiempo: " + eTimeD + "ns");
            Label lnumD = new Label("Distancia: " + numD);
            vDijkstra.getChildren().addAll(lD, ltimeD, lnumD);
            List<String> camD = MainOB.graphOfB.getCaminoDijkstra(new Actor(txtSrc.getText()), new Actor(txtDst.getText()));
            ListIterator<String> litD = camD.listIterator();
            while(litD.hasNext()){
                String s = litD.next();
                if(litD.previousIndex()%2==0){
                    Label lAct = new Label(s);
                    vDijkstra.getChildren().add(lAct);
                    if(litD.hasNext())
                        vDijkstra.getChildren().add(new Label("was in"));
                }else{
                    Label lMovie = new Label(s);
                    vDijkstra.getChildren().add(lMovie);
                    vDijkstra.getChildren().add(new Label("with"));
                }
            }
            
            //SECCION BFS
            Label lBFS = new Label("BFS");
            long sTimeBFS = System.nanoTime();
            int numBFS = MainOB.graphOfB.numEdgesBFS(new Actor(txtSrc.getText()), new Actor(txtDst.getText()));
            long eTimeBFS = System.nanoTime()-sTimeBFS;
            Label ltimeBFS = new Label("Tiempo: " + eTimeBFS + "ns");
            Label lnumBFS = new Label("Distancia: " + numBFS);
            vBFS.getChildren().addAll(lBFS, ltimeBFS, lnumBFS);
            List<String> camBFS = MainOB.graphOfB.getCaminoBFS(new Actor(txtSrc.getText()), new Actor(txtDst.getText()));
            ListIterator<String> litBFS = camBFS.listIterator();
            while(litBFS.hasNext()){
                String s = litBFS.next();
                if(litBFS.previousIndex()%2==0){
                    Label lAct = new Label(s);
                    vBFS.getChildren().add(lAct);
                    if(litBFS.hasNext())
                        vBFS.getChildren().add(new Label("was in"));
                }else{
                    Label lMovie = new Label(s);
                    vBFS.getChildren().add(lMovie);
                    vBFS.getChildren().add(new Label("with"));
                }
            }
            
            //SECCION DFS
            Label lDFS = new Label("DFS");
            long sTimeDFS = System.nanoTime();
            int numDFS = MainOB.graphOfB.numEdgesDFS(new Actor(txtSrc.getText()), new Actor(txtDst.getText()));
            long eTimeDFS = System.nanoTime()-sTimeDFS;
            Label ltimeDFS = new Label("Tiempo: " + eTimeDFS + "ns");
            Label lnumDFS = new Label("Distancia: " + numDFS);
            vDFS.getChildren().addAll(lDFS, ltimeDFS, lnumDFS);
            List<String> camDFS = MainOB.graphOfB.getCaminoDFS(new Actor(txtSrc.getText()), new Actor(txtDst.getText()));
            ListIterator<String> litDFS = camDFS.listIterator();
            while(litDFS.hasNext()){
                String s = litDFS.next();
                if(litDFS.previousIndex()%2==0){
                    Label lAct = new Label(s);
                    vDFS.getChildren().add(lAct);
                    if(litDFS.hasNext())
                        vDFS.getChildren().add(new Label("was in"));
                }else{
                    Label lMovie = new Label(s);
                    vDFS.getChildren().add(lMovie);
                    vDFS.getChildren().add(new Label("with"));
                }
            }
            
            hResult.getChildren().addAll(vDijkstra, vBFS, vDFS);
            hResult.setVisible(true);
        });
        boxQuery.getChildren().addAll(txtSrc, new Label("to"), txtDst, bFindLink);
        vCenter.getChildren().addAll(boxQuery,hResult);
        root.setCenter(vCenter);
    }
    
    //private void create
    
}
