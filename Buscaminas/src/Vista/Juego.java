/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import AI.Accion;
import AI.Agente;
import Modelo.Tablero;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author Jorge Morales
 */
public class Juego extends Application{
    
    private Timer timer;
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, InterruptedException, IOException, BasicPlayerException{
        File file = new File("persistencia.txt");
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter p = new BufferedWriter(fw);
        int filasTablero = 100, columnasTablero = 100;
        Tablero t = new Tablero(filasTablero, columnasTablero);
        Agente a = new Agente(filasTablero ,columnasTablero,t.getNumeroMinas());
        Pane layout  = new Pane();
        int pixels = 6;
        Canvas canvas = new Canvas(t.getColumnas()*pixels,t.getFilas()*pixels);
        layout.getChildren().add(canvas);
        Shape[][] shapes = new Shape[t.getFilas()][t.getColumnas()];
        Scene escena = new Scene(layout,t.getColumnas()*pixels,t.getFilas()*pixels, Color.TRANSPARENT);
        GraphicsContext lapiz = canvas.getGraphicsContext2D();
        Vista v = new Vista(escena, lapiz, t, pixels);
        
        String songName = "song.mp3";
        String pathToMp3 = System.getProperty("user.dir") + "/" + songName;
        BasicPlayer player = new BasicPlayer();
        try {
            player.open(new URL("file:///" + pathToMp3));
            player.play();
        } catch (BasicPlayerException | MalformedURLException e) {
            //e.printStackTrace();
        }
        
        int aux1 = 0, aux2 = 0;
        for (int i = 0; i < t.getFilas(); i++) {
            for (int j = 0; j < t.getColumnas(); j++) {
                if(a.getTablero()[i][j]==10){
                    t.llenarTablero(i, j);
                    a.getTablero()[i][j] = t.getCasillas()[i][j].getValue();
                    aux1 = i;
                    aux2 = j;
                    System.out.println(i+","+j+",D");
                    break;
                }
            }
        }
        final int iIni = aux1, jIni = aux2;
        
        for (int i = 0; i < t.getFilas(); i++) {
            for (int j = 0; j < t.getColumnas(); j++) {
                shapes[i][j] = new Rectangle(j*20, i*20, 20, 20);
                shapes[i][j].setOpacity(0);
                layout.getChildren().add(shapes[i][j]);
                shapes[i][j].setOnMouseClicked((MouseEvent event) -> {
                    int x = (int)(event.getY()/20);
                    int y = (int)(event.getX()/20);
                    if(!t.getCasillas()[x][y].isDescubierta()&&!t.getCasillas()[x][y].isFlag()&&event.getButton().toString().equals("PRIMARY")){
                        try {
                            v.descubrirCasilla(x, y);
                            t.getCasillas()[x][y].setIsDescubierta(true);
                        } catch (FileNotFoundException ex) {
                            System.out.println("Imagen no encontrada");
                        }
                        if(t.getCasillas()[x][y].isMine()){
                            t.setFinalizado(true);
                            try {
                                if(!t.getFinalizado()){
                                    v.lose();
                                }
                            } catch (FileNotFoundException ex) {
                                System.out.println("No se encontró el archivo .png");
                            }
                        }
                    }else if(event.getButton().toString().equals("SECONDARY")&&!t.getCasillas()[x][y].isDescubierta()){
                        try {
                            v.ponerBandera(x,y);
                            int minas = 0;
                            for (int k = 0; k < t.getFilas(); k++) {
                                for (int l = 0; l < t.getColumnas(); l++) {
                                    if(t.getCasillas()[k][l].getValue()==-1&&t.getCasillas()[k][l].isFlag()){
                                        minas++;
                                    }
                                }
                            }
                            if(t.getNumeroMinas()==minas){
                                System.out.println("You win!!!");
                                t.setFinalizado(true);
                            }
                        } catch (FileNotFoundException ex) {
                            System.out.println("No se encontró el archivo .png");
                        }
                    }else if(t.getCasillas()[x][y].isDescubierta()&&event.getButton().toString().equals("PRIMARY")){
                        try {
                            v.destaparVecinos(x, y);
                            t.getCasillas()[x][y].setIsDescubierta(true);
                        } catch (FileNotFoundException ex) {
                            System.out.println("No se encontró el archivo .png");
                        }
                    }
                    //System.out.println(x+" "+y);
                });
            }
        }
        primaryStage.setTitle("Buscaminas");
        v.Show(primaryStage);
        v.draw();
        
        /*while(!t.getFinalizado()){
            
        }*/
        TimerTask time = new TimerTask() {
            @Override
            public void run() {
                try {
                    ArrayList<Accion> acciones;
                    if(!t.getCasillas()[iIni][jIni].isDescubierta()){
                        t.getCasillas()[iIni][jIni].setIsDescubierta(true);
                        v.descubrirCasilla(iIni, jIni);
                        compartirTablero(t, a);
                    }else{
                        acciones = a.realizarAccion();
                        for (int i = 0; i < acciones.size(); i++) {
                            if(acciones.get(i).isPutFlag()){
                                v.ponerBandera(acciones.get(i).getFila(), acciones.get(i).getColumna());
                                t.getCasillas()[acciones.get(i).getFila()][acciones.get(i).getColumna()].setIsFlag(true);
                                t.getCasillas()[acciones.get(i).getFila()][acciones.get(i).getColumna()].setIsDescubierta(false);
                                a.getTablero()[acciones.get(i).getFila()][acciones.get(i).getColumna()] = -1;
                            }else if(acciones.get(i).isDescubrirCasilla()){
                                v.descubrirCasilla(acciones.get(i).getFila(), acciones.get(i).getColumna());
                            }else if(acciones.get(i).isDestaparVecinos()){
                                v.destaparVecinos(acciones.get(i).getFila(), acciones.get(i).getColumna());
                            }
                            compartirTablero(t, a);
                        }
                    }
                    boolean aux = true;
                    for (int i = 0; i < t.getColumnas(); i++) {
                        for (int j = 0; j < t.getFilas(); j++) {
                            if(t.getCasillas()[i][j].isMine()&&!t.getCasillas()[i][j].isFlag()){
                                aux = false;
                                break;
                            }
                        }
                        if(!aux){
                            break;
                        }
                    }
                    if(aux){
                        System.out.println("You win!");
                        for (int i = 0; i < t.getFilas(); i++) {
                            for (int j = 0; j < t.getColumnas(); j++) {
                                if(!t.getCasillas()[i][j].isDescubierta()&&!t.getCasillas()[i][j].isFlag()){
                                    v.descubrirCasilla(i, j);
                                    a.getTablero()[i][j] = t.getCasillas()[i][j].getValue();
                                }
                            }
                        }
                        t.setFinalizado(true);
                        Date d = new Date();
                        p.write("Usuario: jomorales\n");
                        p.write("Fecha: "+d.getDate()+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900)+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"\n");
                        p.write("Número de minas: "+t.getNumeroMinas()+"\n");
                        for (int i = 0; i < t.getFilas(); i++) {
                            for (int j = 0; j < t.getColumnas(); j++) {
                                p.write(a.getTablero()[i][j]+"\t");
                            }
                            p.write("\n");
                        }
                        int t = 0;
                        for (Accion accion : a.getAcciones()) {
                            p.write(accion.getFila()+" - "+accion.getColumna()+" ");
                            if(accion.isPutFlag()){
                                p.write("B ");
                                if(accion.isIsMove50()){
                                    p.write("(<- Move 50) ");
                                }else if(accion.isIsMove121()){
                                    p.write("(<- Move 121), ");
                                }
                            }else if(accion.isDestaparVecinos()){
                                p.write("DV ");
                                if(accion.isIsMove50()){
                                    p.write("(<- Move 50) ");
                                }else if(accion.isIsMove121()){
                                    p.write("(<- Move 121) ");
                                }
                            }else if(accion.isDescubrirCasilla()){
                                p.write("D ");
                                if(accion.isIsMove50()){
                                    p.write("(<- Move 50) ");
                                }else if(accion.isIsMove121()){
                                    p.write("(<- Move 121) ");
                                }
                            }
                            t++;
                            if(t%10==0){
                                p.write("\n");
                            }
                        }
                        p.write("\n");
                        p.flush();
                        p.close();
                    }
                    if(t.getFinalizado()){
                        a.setFinalizado(true);
                        cancel();
                        timer.cancel();
                        timer.purge();
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("Imagen no encontrada");
                } catch (IOException ex) {
                    Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(time, 1000, 20);
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            try {
                t.setFinalizado(true);
                player.stop();
            } catch (BasicPlayerException ex) {
                System.out.println("Error");
            }
        });
    }
    
    public void compartirTablero(Tablero t, Agente a){
        for (int i = 0; i < t.getFilas(); i++) {
            for (int j = 0; j < t.getColumnas(); j++) {
                if(t.getCasillas()[i][j].isDescubierta()){
                    a.getTablero()[i][j] = t.getCasillas()[i][j].getValue();
                }
                //System.out.print(a.getTablero()[i][j] + "\t");
            }
            //System.out.println("");
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        Application.launch(args);
    }
}
