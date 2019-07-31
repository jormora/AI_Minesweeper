/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Tablero;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Morales
 */
public class Vista{
    private Scene scene;
    private Tablero t;
    private final GraphicsContext lapiz;
    private int pixels;
    private FileInputStream cer;
    private Image cero;
    private FileInputStream un;
    private Image uno;
    private FileInputStream d;
    private Image dos;
    private FileInputStream tre;
    private Image tres;
    private FileInputStream cuatr;
    private Image cuatro;
    private FileInputStream cinc;
    private Image cinco;
    private FileInputStream sei;
    private Image seis;
    private FileInputStream siet;
    private Image siete;
    private FileInputStream och;
    private Image ocho;
    private FileInputStream min;
    private Image mina;
    private FileInputStream cas;
    private Image casilla;
    private FileInputStream b;
    private Image ban;

    public Vista(Scene scene, GraphicsContext lapiz, Tablero t, int pixels) throws FileNotFoundException {
        this.scene = scene;
        this.lapiz = lapiz;
        this.t = t;
        this.pixels = pixels;
        this.cer = new FileInputStream(new File("imagenes\\Minesweeper_0.svg.png"));
        this.cero = new Image(this.cer);
        this.un = new FileInputStream(new File("imagenes\\Minesweeper_1.svg.png"));
        this.uno = new Image(this.un);
        this.d = new FileInputStream(new File("imagenes\\Minesweeper_2.svg.png"));
        this.dos = new Image(this.d);
        this.tre = new FileInputStream(new File("imagenes\\Minesweeper_3.svg.png"));
        this.tres = new Image(this.tre);
        this.cuatr = new FileInputStream(new File("imagenes\\Minesweeper_4.svg.png"));
        this.cuatro = new Image(this.cuatr);
        this.cinc = new FileInputStream(new File("imagenes\\Minesweeper_5.svg.png"));
        this.cinco = new Image(this.cinc);
        this.sei = new FileInputStream(new File("imagenes\\Minesweeper_6.svg.png"));
        this.seis = new Image(this.sei);
        this.siet = new FileInputStream(new File("imagenes\\Minesweeper_7.svg.png"));
        this.siete = new Image(this.siet);
        this.och = new FileInputStream(new File("imagenes\\Minesweeper_8.svg.png"));
        this.ocho = new Image(this.och);
        this.min = new FileInputStream(new File("imagenes\\mina2.png"));
        this.mina = new Image(this.min);
        this.cas = new FileInputStream(new File("imagenes\\Minesweeper_unopened_square.svg.png"));
        this.casilla = new Image(this.cas);
        this.b = new FileInputStream(new File("imagenes\\Minesweeper_flag.svg.png"));
        this.ban = new Image(this.b);
    }
    
    public void Show(Stage stage){
        stage.setScene(this.scene);
        stage.show();
    }
    
    public void draw() throws FileNotFoundException{
        if(this.t.getFinalizado()){
            System.out.println("You win!");
        }
        for (int i = 0; i < this.t.getFilas(); i++) {
            for (int j = 0; j < this.t.getColumnas(); j++) {
                if(this.t.getCasillas()[i][j].isDescubierta()){
                    descubrirCasilla(i,j);
                }else{
                    lapiz.drawImage(this.casilla, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                }
            }
        }
    }
    
    public void descubrirCasilla(int i, int j) throws FileNotFoundException{
        switch (this.t.getCasillas()[i][j].getValue()) {
            case 0:
                this.lapiz.drawImage(this.cero, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                destaparCero(i,j);
                break;
            case 1:
                this.lapiz.drawImage(this.uno, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                break;
            case 2:
                this.lapiz.drawImage(this.dos, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                break;
            case 3:
                this.lapiz.drawImage(this.tres, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                break;
            case 4:
                this.lapiz.drawImage(this.cuatro, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                break;
            case 5:
                this.lapiz.drawImage(this.cinco, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                break;
            case 6:
                this.lapiz.drawImage(this.seis, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                break;
            case 7:
                this.lapiz.drawImage(this.siete, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                break;
            case 8:
                this.lapiz.drawImage(this.ocho, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                break;
            case -1:
                this.lapiz.drawImage(this.mina, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                if(!this.t.getFinalizado()){
                    lose();
                }
                break;
            default:
                break;
        }
        this.t.getCasillas()[i][j].setIsDescubierta(true);
    }
    
    public void lose() throws FileNotFoundException{
        this.t.setFinalizado(true);
        System.out.println("Game Over!");
        for (int i = 0; i < this.t.getFilas(); i++) {
            for (int j = 0; j < this.t.getColumnas(); j++) {
                if(!this.t.getCasillas()[i][j].isDescubierta()&&!this.t.getCasillas()[i][j].isFlag()){
                    this.t.getCasillas()[i][j].setIsDescubierta(true);
                    switch (this.t.getCasillas()[i][j].getValue()) {
                        case 0:
                            this.lapiz.drawImage(this.cero, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            break;
                        case 1:
                            this.lapiz.drawImage(this.uno, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            break;
                        case 2:
                            this.lapiz.drawImage(this.dos, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            break;
                        case 3:
                            this.lapiz.drawImage(this.tres, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            break;
                        case 4:
                            this.lapiz.drawImage(this.cuatro, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            break;
                        case 5:
                            this.lapiz.drawImage(this.cinco, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            break;
                        case 6:
                            this.lapiz.drawImage(this.seis, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            break;
                        case 7:
                            this.lapiz.drawImage(this.siete, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            break;
                        case 8:
                            this.lapiz.drawImage(this.ocho, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            break;
                        case -1:
                            if(!this.t.getCasillas()[i][j].isFlag()){
                                this.lapiz.drawImage(this.mina, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void ponerBandera(int x, int y) throws FileNotFoundException {
        if(this.t.getCasillas()[x][y].isFlag()){
            this.lapiz.drawImage(this.casilla, y*this.pixels, x*this.pixels, this.pixels, this.pixels);
            this.t.getCasillas()[x][y].setIsFlag(false);
            this.t.getCasillas()[x][y].setIsDescubierta(false);
        }else{
            this.lapiz.drawImage(this.ban, y*this.pixels, x*this.pixels, this.pixels, this.pixels);
            this.t.getCasillas()[x][y].setIsFlag(true);
            this.t.getCasillas()[x][y].setIsDescubierta(false);
        }
    }
    
    public void destaparVecinos(int i, int j) throws FileNotFoundException{
        int banderas = 0;
        if((i==0)&&(j==0)){
            banderas += (this.t.getCasillas()[i][j+1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j+1].isFlag())? 1:0;
            if(banderas==this.t.getCasillas()[i][j].getValue()){
                if(!this.t.getCasillas()[i][j+1].isFlag()&&!this.t.getCasillas()[i][j+1].isDescubierta()){
                    descubrirCasilla(i,j+1);
                    this.t.getCasillas()[i][j+1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j].isFlag()&&!this.t.getCasillas()[i+1][j].isDescubierta()){
                    descubrirCasilla(i+1,j);
                    this.t.getCasillas()[i+1][j].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j+1].isFlag()&&!this.t.getCasillas()[i+1][j+1].isDescubierta()){
                    descubrirCasilla(i+1,j+1);
                    this.t.getCasillas()[i+1][j+1].setIsDescubierta(true);
                }
            }
        }else if((i==this.t.getFilas()-1)&&(j==this.t.getColumnas()-1)){
            banderas += (this.t.getCasillas()[i-1][j].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i][j-1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i-1][j-1].isFlag())? 1:0;
            if(banderas==this.t.getCasillas()[i][j].getValue()){
                if(!this.t.getCasillas()[i-1][j].isFlag()&&!this.t.getCasillas()[i-1][j].isDescubierta()){
                    descubrirCasilla(i-1,j);
                    this.t.getCasillas()[i-1][j].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i][j-1].isFlag()&&!this.t.getCasillas()[i][j-1].isDescubierta()){
                    descubrirCasilla(i,j-1);
                    this.t.getCasillas()[i][j-1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i-1][j-1].isFlag()&&!this.t.getCasillas()[i-1][j-1].isDescubierta()){
                    descubrirCasilla(i-1,j-1);
                    this.t.getCasillas()[i-1][j-1].setIsDescubierta(true);
                }
            }
        }else if((i==0)&&(j==this.t.getColumnas()-1)){
            banderas += (this.t.getCasillas()[i][j-1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j-1].isFlag())? 1:0;
            if(banderas==this.t.getCasillas()[i][j].getValue()){
                if(!this.t.getCasillas()[i][j-1].isFlag()&&!this.t.getCasillas()[i][j-1].isDescubierta()){
                    descubrirCasilla(i,j-1);
                    this.t.getCasillas()[i][j-1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j].isFlag()&&!this.t.getCasillas()[i+1][j].isDescubierta()){
                    descubrirCasilla(i+1,j);
                    this.t.getCasillas()[i+1][j].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j-1].isFlag()&&!this.t.getCasillas()[i+1][j-1].isDescubierta()){
                    descubrirCasilla(i+1,j-1);
                    this.t.getCasillas()[i+1][j-1].setIsDescubierta(true);
                }
            }
        }else if((i==this.t.getFilas()-1)&&(j==0)){
            banderas += (this.t.getCasillas()[i-1][j].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i][j+1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i-1][j+1].isFlag())? 1:0;
            if(banderas==this.t.getCasillas()[i][j].getValue()){
                if(!this.t.getCasillas()[i-1][j].isFlag()&&!this.t.getCasillas()[i-1][j].isDescubierta()){
                    descubrirCasilla(i-1,j);
                    this.t.getCasillas()[i-1][j].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i][j+1].isFlag()&&!this.t.getCasillas()[i][j+1].isDescubierta()){
                    descubrirCasilla(i,j+1);
                    this.t.getCasillas()[i][j+1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i-1][j+1].isFlag()&&!this.t.getCasillas()[i-1][j+1].isDescubierta()){
                    descubrirCasilla(i-1,j+1);
                    this.t.getCasillas()[i-1][j+1].setIsDescubierta(true);
                }
            }
        }else if(i==0){
            banderas += (this.t.getCasillas()[i][j-1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i][j+1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j-1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j+1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j].isFlag())? 1:0;
            if(banderas==this.t.getCasillas()[i][j].getValue()){
                if(!this.t.getCasillas()[i][j-1].isFlag()&&!this.t.getCasillas()[i][j-1].isDescubierta()){
                    descubrirCasilla(i,j-1);
                    this.t.getCasillas()[i][j-1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i][j+1].isFlag()&&!this.t.getCasillas()[i][j+1].isDescubierta()){
                    descubrirCasilla(i,j+1);
                    this.t.getCasillas()[i][j+1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j-1].isFlag()&&!this.t.getCasillas()[i+1][j-1].isDescubierta()){
                    descubrirCasilla(i+1,j-1);
                    this.t.getCasillas()[i+1][j-1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j+1].isFlag()&&!this.t.getCasillas()[i+1][j+1].isDescubierta()){
                    descubrirCasilla(i+1,j+1);
                    this.t.getCasillas()[i+1][j+1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j].isFlag()&&!this.t.getCasillas()[i+1][j].isDescubierta()){
                    descubrirCasilla(i+1,j);
                    this.t.getCasillas()[i+1][j].setIsDescubierta(true);
                }
            }
        }else if(j==this.t.getColumnas()-1){
            banderas += (this.t.getCasillas()[i-1][j].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i-1][j-1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j-1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i][j-1].isFlag())? 1:0;
            if(banderas==this.t.getCasillas()[i][j].getValue()){
                if(!this.t.getCasillas()[i-1][j].isFlag()&&!this.t.getCasillas()[i-1][j].isDescubierta()){
                    descubrirCasilla(i-1,j);
                    this.t.getCasillas()[i-1][j].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j].isFlag()&&!this.t.getCasillas()[i+1][j].isDescubierta()){
                    descubrirCasilla(i+1,j);
                    this.t.getCasillas()[i+1][j].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i-1][j-1].isFlag()&&!this.t.getCasillas()[i-1][j-1].isDescubierta()){
                    descubrirCasilla(i-1,j-1);
                    this.t.getCasillas()[i-1][j-1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j-1].isFlag()&&!this.t.getCasillas()[i+1][j-1].isDescubierta()){
                    descubrirCasilla(i+1,j-1);
                    this.t.getCasillas()[i+1][j-1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i][j-1].isFlag()&&!this.t.getCasillas()[i][j-1].isDescubierta()){
                    descubrirCasilla(i,j-1);
                    this.t.getCasillas()[i][j-1].setIsDescubierta(true);
                }
            }
        }else if(i==this.t.getFilas()-1){
            banderas += (this.t.getCasillas()[i][j-1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i][j+1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i-1][j-1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i-1][j+1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i-1][j].isFlag())? 1:0;
            if(banderas==this.t.getCasillas()[i][j].getValue()){
                if(!this.t.getCasillas()[i][j-1].isFlag()&&!this.t.getCasillas()[i][j-1].isDescubierta()){
                    descubrirCasilla(i,j-1);
                    this.t.getCasillas()[i][j-1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i][j+1].isFlag()&&!this.t.getCasillas()[i][j+1].isDescubierta()){
                    descubrirCasilla(i,j+1);
                    this.t.getCasillas()[i][j+1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i-1][j-1].isFlag()&&!this.t.getCasillas()[i-1][j-1].isDescubierta()){
                    descubrirCasilla(i-1,j-1);
                    this.t.getCasillas()[i-1][j-1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i-1][j+1].isFlag()&&!this.t.getCasillas()[i-1][j+1].isDescubierta()){
                    descubrirCasilla(i-1,j+1);
                    this.t.getCasillas()[i-1][j+1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i-1][j].isFlag()&&!this.t.getCasillas()[i-1][j].isDescubierta()){
                    descubrirCasilla(i-1,j);
                    this.t.getCasillas()[i-1][j].setIsDescubierta(true);
                }
            }
        }else if(j==0){
            banderas += (this.t.getCasillas()[i-1][j].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i-1][j+1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i+1][j+1].isFlag())? 1:0;
            banderas += (this.t.getCasillas()[i][j+1].isFlag())? 1:0;
            if(banderas==this.t.getCasillas()[i][j].getValue()){
                if(!this.t.getCasillas()[i-1][j].isFlag()&&!this.t.getCasillas()[i-1][j].isDescubierta()){
                    descubrirCasilla(i-1,j);
                    this.t.getCasillas()[i-1][j].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j].isFlag()&&!this.t.getCasillas()[i+1][j].isDescubierta()){
                    descubrirCasilla(i+1,j);
                    this.t.getCasillas()[i+1][j].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i-1][j+1].isFlag()&&!this.t.getCasillas()[i-1][j+1].isDescubierta()){
                    descubrirCasilla(i-1,j+1);
                    this.t.getCasillas()[i-1][j+1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i+1][j+1].isFlag()&&!this.t.getCasillas()[i+1][j+1].isDescubierta()){
                    descubrirCasilla(i+1,j+1);
                    this.t.getCasillas()[i+1][j+1].setIsDescubierta(true);
                }
                if(!this.t.getCasillas()[i][j+1].isFlag()&&!this.t.getCasillas()[i][j+1].isDescubierta()){
                    descubrirCasilla(i,j+1);
                    this.t.getCasillas()[i][j+1].setIsDescubierta(true);
                }
            }
        }else {
            for (int k = i-1; k < i+2; k++) {
                for (int l = j-1; l < j+2; l++) {
                    if(this.t.getCasillas()[k][l].isFlag()){
                        banderas++;
                    }
                }
            }
            if(banderas==this.t.getCasillas()[i][j].getValue()){
                for (int k = i-1; k < i+2; k++) {
                    for (int l = j-1; l < j+2; l++) {
                        if(!this.t.getCasillas()[k][l].isFlag()&&!this.t.getCasillas()[k][l].isDescubierta()){
                            descubrirCasilla(k,l);
                            this.t.getCasillas()[k][l].setIsDescubierta(true);
                        }
                    }
                }
            }
        }
    }
    
    public void destaparCero(int i, int j) throws FileNotFoundException{
        this.lapiz.drawImage(this.cero, j*this.pixels, i*this.pixels, this.pixels, this.pixels);
        this.t.getCasillas()[i][j].setIsDescubierta(true);
        destaparVecinos(i,j);
    }
    
}
