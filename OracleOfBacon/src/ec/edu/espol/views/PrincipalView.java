/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.views;

import ec.edu.espol.common.Actor;
import ec.edu.espol.main.MainOB;
import java.awt.Color;
import java.util.List;
import java.util.ListIterator;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 *
 * @author TBeltran
 */
public class PrincipalView {
    private BorderPane root;
    private Label lTitle;
    private ImageView img;
    private ComboBox<Actor> cmbSrc;
    private ComboBox<Actor> cmbDst;
    private Button bFindLink;
    private VBox vCenter;
    private HBox hResult;
    
    public PrincipalView(){
        root = new BorderPane();
        createTopSection();
        createCenterSection();
        instanciarIDs();
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
        cmbSrc = new ComboBox<>();
        cmbDst = new ComboBox<>();
        cmbSrc.setItems(FXCollections.observableList(MainOB.graphOfB.getVertexes()));
        cmbSrc.getSelectionModel().select(new Actor("Kevin Bacon"));
        cmbDst.setItems(FXCollections.observableList(MainOB.graphOfB.getVertexes()));
        bFindLink = new Button("Find link");
        bFindLink.setOnAction(e->{
            //System.out.println(txtSrc.getText().equals(""));
            
            hResult.getChildren().clear();
            HBox boxDijkstra = new HBox();
            VBox vDijkstra = new VBox();
            
            HBox boxBFS = new HBox();  
            VBox vBFS = new VBox();
            
            HBox boxDFS = new HBox();
            VBox vDFS = new VBox();
            
            vDijkstra.setId("vResults");
            vBFS.setId("vResults");
            vDFS.setId("vResults");
            //SECCION DIJKSTRA
            Label lD = new Label("DIJKSTRA");
            long sTimeD = System.nanoTime();
            int numD = MainOB.graphOfB.numEdgesDijkstra(cmbSrc.getValue(), cmbDst.getValue());
            long eTimeD = System.nanoTime()-sTimeD;
            Label ltimeD = new Label("Tiempo: " + eTimeD + "ns");
            Label lnumD = new Label("Distancia: " + numD);
            vDijkstra.getChildren().addAll(lD, ltimeD, lnumD);
            List<String> camD = MainOB.graphOfB.getCaminoDijkstra(cmbSrc.getValue(), cmbDst.getValue());
            ListIterator<String> litD = camD.listIterator();
            while(litD.hasNext()){
                String s = litD.next();
                if(litD.previousIndex()%2==0){
                    Label lAct = new Label(s);
                    lAct.setId("txtActor");
                    vDijkstra.getChildren().add(lAct);
                    if(litD.hasNext())
                        vDijkstra.getChildren().add(new Label("was in"));
                }else{
                    Label lMovie = new Label(s);
                    lMovie.setId("txtMovie");
                    vDijkstra.getChildren().add(lMovie);
                    vDijkstra.getChildren().add(new Label("with"));
                }
            }
            boxDijkstra.getChildren().add(vDijkstra);
            ScrollPane sP = new ScrollPane();
            sP.setContent(vDijkstra);
            sP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            sP.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            boxDijkstra.getChildren().add(sP);
            
            //SECCION BFS
            Label lBFS = new Label("BFS");
            long sTimeBFS = System.nanoTime();
            int numBFS = MainOB.graphOfB.numEdgesBFS(cmbSrc.getValue(), cmbDst.getValue());
            long eTimeBFS = System.nanoTime()-sTimeBFS;
            Label ltimeBFS = new Label("Tiempo: " + eTimeBFS + "ns");
            Label lnumBFS = new Label("Distancia: " + numBFS);
            vBFS.getChildren().addAll(lBFS, ltimeBFS, lnumBFS);
            List<String> camBFS = MainOB.graphOfB.getCaminoBFS(cmbSrc.getValue(), cmbDst.getValue());
            ListIterator<String> litBFS = camBFS.listIterator();
            while(litBFS.hasNext()){
                String s = litBFS.next();
                if(litBFS.previousIndex()%2==0){
                    Label lAct = new Label(s);
                    lAct.setId("txtActor");
                    vBFS.getChildren().add(lAct);
                    if(litBFS.hasNext())
                        vBFS.getChildren().add(new Label("was in"));
                }else{
                    Label lMovie = new Label(s);
                    lMovie.setId("txtMovie");
                    vBFS.getChildren().add(lMovie);
                    vBFS.getChildren().add(new Label("with"));
                }
            }
            boxBFS.getChildren().add(vBFS);
            ScrollPane sP2 = new ScrollPane();
            sP2.setContent(vBFS);
            sP2.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            sP2.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            boxBFS.getChildren().add(sP2);
            
            //SECCION DFS
            Label lDFS = new Label("DFS");
            long sTimeDFS = System.nanoTime();
            int numDFS = MainOB.graphOfB.numEdgesDFS(cmbSrc.getValue(), cmbDst.getValue());
            long eTimeDFS = System.nanoTime()-sTimeDFS;
            Label ltimeDFS = new Label("Tiempo: " + eTimeDFS + "ns");
            Label lnumDFS = new Label("Distancia: " + numDFS);
            vDFS.getChildren().addAll(lDFS, ltimeDFS, lnumDFS);
            List<String> camDFS = MainOB.graphOfB.getCaminoDFS(cmbSrc.getValue(), cmbDst.getValue());
            ListIterator<String> litDFS = camDFS.listIterator();
            while(litDFS.hasNext()){
                String s = litDFS.next();
                if(litDFS.previousIndex()%2==0){
                    Label lAct = new Label(s);
                    lAct.setId("txtActor");
                    vDFS.getChildren().add(lAct);
                    if(litDFS.hasNext())
                        vDFS.getChildren().add(new Label("was in"));
                }else{
                    Label lMovie = new Label(s);
                    lMovie.setId("txtMovie");
                    vDFS.getChildren().add(lMovie);
                    vDFS.getChildren().add(new Label("with"));
                }
            }
            boxDFS.getChildren().add(vDFS);
            ScrollPane sP3 = new ScrollPane();
            sP3.setContent(vDFS);
            sP3.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            sP3.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            boxDFS.getChildren().add(sP3);
            
            hResult.getChildren().addAll(boxDijkstra, boxBFS, boxDFS);
            hResult.setVisible(true);
        });
        boxQuery.getChildren().addAll(cmbSrc, new Label("to"), cmbDst, bFindLink);
        vCenter.getChildren().addAll(boxQuery,hResult);
        root.setCenter(vCenter);
    }
    
    private void instanciarIDs(){
        lTitle.setId("lTitle");
    }
}
