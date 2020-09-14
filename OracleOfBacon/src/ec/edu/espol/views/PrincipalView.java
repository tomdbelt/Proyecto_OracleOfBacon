/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.views;

import ec.edu.espol.common.Actor;
import ec.edu.espol.constants.CONSTANTES;
import ec.edu.espol.main.MainOB;
import java.util.List;
import java.util.ListIterator;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    private ComboBox<Actor> cmbSrc;
    private ComboBox<Actor> cmbDst;
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
        lTitle.setId("lTitle");
        Label lGroup = new Label("G7");
        lGroup.setId("lTitle");
        VBox vTitle = new VBox();
        vTitle.getChildren().addAll(lTitle, lGroup);
        img = new ImageView(new Image(CONSTANTES.RUTA_IMAGEN, 100, 100, true, true));
        boxTitle.getChildren().addAll(vTitle,img);
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
            hResult.getChildren().clear();
            
            HBox boxDijkstra = new HBox();
            VBox vDijkstra = new VBox();
            
            HBox boxBFS = new HBox();  
            VBox vBFS = new VBox();
            
            HBox boxDFS = new HBox();
            VBox vDFS = new VBox();
            
            //SECCION DIJKSTRA
            Label lD = new Label("DIJKSTRA");
            lD.setId("txtTitleAlg");
            long sTimeD = System.nanoTime();
            int numD = MainOB.graphOfB.numEdgesDijkstra(cmbSrc.getValue(), cmbDst.getValue());
            long eTimeD = System.nanoTime()-sTimeD;
            
            if(numD!=Integer.MAX_VALUE){
                Label ltimeD = new Label("Tiempo: " + eTimeD + "ns");
                Label lnumD = new Label("Distancia: " + numD);
                vDijkstra.getChildren().addAll(lD, ltimeD, lnumD);
                List<String> camD = MainOB.graphOfB.getCaminoDijkstra(cmbSrc.getValue(), cmbDst.getValue());
                ListIterator<String> litD = camD.listIterator();
                VBox vCamDijkstra = new VBox();
                vCamDijkstra.setId("vResults");
                while(litD.hasNext()){
                    String s = litD.next();
                    if(litD.previousIndex()%2==0){
                        Label lAct = new Label(s);
                        lAct.setId("txtActor");
                        vCamDijkstra.getChildren().add(lAct);
                        if(litD.hasNext())
                            vCamDijkstra.getChildren().add(new Label("was in"));
                    }else{
                        Label lMovie = new Label(s);
                        lMovie.setId("txtMovie");
                        vCamDijkstra.getChildren().add(lMovie);
                        vCamDijkstra.getChildren().add(new Label("with"));
                    }
                }
                boxDijkstra.getChildren().add(vCamDijkstra);
                ScrollPane sP = new ScrollPane();
                sP.setContent(vCamDijkstra);
                sP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                sP.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                boxDijkstra.getChildren().add(sP);
                vDijkstra.getChildren().addAll(boxDijkstra);
            }else{
                boxDijkstra.getChildren().add(new Label("Lo sentimos. Resultado no Encontrado"));
                vDijkstra.getChildren().addAll(lD, boxDijkstra);
            }
                
            //SECCION BFS
            Label lBFS = new Label("BFS");
            lBFS.setId("txtTitleAlg");
            long sTimeBFS = System.nanoTime();
            int numBFS = MainOB.graphOfB.numEdgesBFS(cmbSrc.getValue(), cmbDst.getValue());
            long eTimeBFS = System.nanoTime()-sTimeBFS;
            
            if(numBFS!=Integer.MAX_VALUE){
                Label ltimeBFS = new Label("Tiempo: " + eTimeBFS + "ns");
                Label lnumBFS = new Label("Distancia: " + numBFS);
                vBFS.getChildren().addAll(lBFS, ltimeBFS, lnumBFS);
                List<String> camBFS = MainOB.graphOfB.getCaminoBFS(cmbSrc.getValue(), cmbDst.getValue());
                ListIterator<String> litBFS = camBFS.listIterator();
                VBox vCamBFS = new VBox();
                vCamBFS.setId("vResults");
                while(litBFS.hasNext()){
                    String s = litBFS.next();
                    if(litBFS.previousIndex()%2==0){
                        Label lAct = new Label(s);
                        lAct.setId("txtActor");
                        vCamBFS.getChildren().add(lAct);
                        if(litBFS.hasNext())
                            vCamBFS.getChildren().add(new Label("was in"));
                    }else{
                        Label lMovie = new Label(s);
                        lMovie.setId("txtMovie");
                        vCamBFS.getChildren().add(lMovie);
                        vCamBFS.getChildren().add(new Label("with"));
                    }
                }
                boxBFS.getChildren().add(vCamBFS);
                ScrollPane sP = new ScrollPane();
                sP.setContent(vCamBFS);
                sP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                sP.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                boxBFS.getChildren().add(sP);
                vBFS.getChildren().addAll(boxBFS);
            }else{
                boxBFS.getChildren().add(new Label("Lo sentimos. Resultado no Encontrado"));
                vBFS.getChildren().addAll(lBFS, boxBFS);
            }
            
            //SECCION DFS
            Label lDFS = new Label("DFS");
            lDFS.setId("txtTitleAlg");
            long sTimeDFS = System.nanoTime();
            int numDFS = MainOB.graphOfB.numEdgesDFS(cmbSrc.getValue(), cmbDst.getValue());
            long eTimeDFS = System.nanoTime()-sTimeDFS;
            
            if(numDFS!=Integer.MAX_VALUE){
                Label ltimeDFS = new Label("Tiempo: " + eTimeDFS + "ns");
                Label lnumDFS = new Label("Distancia: " + numDFS);
                vDFS.getChildren().addAll(lDFS, ltimeDFS, lnumDFS);
                List<String> camDFS = MainOB.graphOfB.getCaminoDFS(cmbSrc.getValue(), cmbDst.getValue());
                ListIterator<String> litDFS = camDFS.listIterator();
                VBox vCamDFS = new VBox();
                vCamDFS.setId("vResults");
                while(litDFS.hasNext()){
                    String s = litDFS.next();
                    if(litDFS.previousIndex()%2==0){
                        Label lAct = new Label(s);
                        lAct.setId("txtActor");
                        vCamDFS.getChildren().add(lAct);
                        if(litDFS.hasNext())
                            vCamDFS.getChildren().add(new Label("was in"));
                    }else{
                        Label lMovie = new Label(s);
                        lMovie.setId("txtMovie");
                        vCamDFS.getChildren().add(lMovie);
                        vCamDFS.getChildren().add(new Label("with"));
                    }
                }
                boxBFS.getChildren().add(vCamDFS);
                ScrollPane sP = new ScrollPane();
                sP.setContent(vCamDFS);
                sP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                sP.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                boxDFS.getChildren().add(sP);
                vDFS.getChildren().addAll(boxDFS);
            }else{
                boxDFS.getChildren().add(new Label("Lo sentimos. Resultado no Encontrado"));
                vDFS.getChildren().addAll(lDFS, boxDFS);
            }
            
            hResult.getChildren().addAll(vDijkstra, vBFS, vDFS);
            hResult.setVisible(true);
        });
        boxQuery.getChildren().addAll(cmbSrc, new Label("to"), cmbDst, bFindLink);
        vCenter.getChildren().addAll(boxQuery,hResult);
        root.setCenter(vCenter);
    }
}
