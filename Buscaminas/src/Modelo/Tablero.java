/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Jorge Morales
 */
public class Tablero {
    private int filas;
    private int columnas;
    private int numeroMinas;
    private boolean finalizado;
    private Casilla[][] casillas;
    
    public Tablero(int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
        this.numeroMinas = (int)(this.filas*this.columnas*0.16);
        //this.numeroMinas = 4;
        this.finalizado = false;
        this.casillas = new Casilla[filas][columnas];
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                this.casillas[i][j] = new Casilla(0, false);
            }
        }
    }
    
    public void llenarTablero(int f, int c){
        System.out.println("Minas: "+this.numeroMinas);
        for (int i = 0; i < this.numeroMinas; i++) {
            int x = (int)(Math.random()*this.filas);
            int y = (int)(Math.random()*this.columnas);
            if(x==10){
                x--;
            }
            if(y==10){
                y--;
            }
            while(this.casillas[x][y].isMine()||(x==f&&y==c)){
                x = (int)(Math.random()*this.filas);
                y = (int)(Math.random()*this.columnas);
            }
            this.casillas[x][y].setValue(-1);
            this.casillas[x][y].setIsMine(true);
        }
        /*this.casillas[this.columnas-2][0].setValue(-1);
        this.casillas[this.columnas-2][0].setIsMine(true);
        this.casillas[this.columnas-2][1].setValue(-1);
        this.casillas[this.columnas-2][1].setIsMine(true);
        this.casillas[this.columnas-1][1].setValue(-1);
        this.casillas[this.columnas-1][1].setIsMine(true);
        this.casillas[this.columnas-1][0].setValue(-1);
        this.casillas[this.columnas-1][0].setIsMine(true);*/
        /*this.casillas[5][5].setValue(-1);
        this.casillas[5][5].setIsMine(true);
        this.casillas[5][6].setValue(-1);
        this.casillas[5][6].setIsMine(true);
        this.casillas[5][7].setValue(-1);
        this.casillas[5][7].setIsMine(true);
        this.casillas[6][5].setValue(-1);
        this.casillas[6][5].setIsMine(true);
        this.casillas[6][7].setValue(-1);
        this.casillas[6][7].setIsMine(true);
        this.casillas[7][5].setValue(-1);
        this.casillas[7][5].setIsMine(true);
        this.casillas[7][6].setValue(-1);
        this.casillas[7][6].setIsMine(true);
        this.casillas[7][7].setValue(-1);
        this.casillas[7][7].setIsMine(true);*/
        
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                int minasAr = 0;
                if(!this.casillas[i][j].isMine()){
                    if((i==0)&&(j==0)){
                        minasAr += (this.casillas[i][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j+1].isMine())? 1:0;
                        this.casillas[i][j].setValue(minasAr);
                    }else if((i==filas-1)&&(j==columnas-1)){
                        minasAr += (this.casillas[i-1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i-1][j-1].isMine())? 1:0;
                        this.casillas[i][j].setValue(minasAr);
                    }else if((i==0)&&(j==columnas-1)){
                        minasAr += (this.casillas[i][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j-1].isMine())? 1:0;
                        this.casillas[i][j].setValue(minasAr);
                    }else if((i==filas-1)&&(j==0)){
                        minasAr += (this.casillas[i-1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i-1][j+1].isMine())? 1:0;
                        this.casillas[i][j].setValue(minasAr);
                    }else if(i==0){
                        minasAr += (this.casillas[i][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j].isMine())? 1:0;
                        this.casillas[i][j].setValue(minasAr);
                    }else if(j==columnas-1){
                        minasAr += (this.casillas[i-1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i-1][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i][j-1].isMine())? 1:0;
                        this.casillas[i][j].setValue(minasAr);
                    }else if(i==filas-1){
                        minasAr += (this.casillas[i][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i-1][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i-1][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i-1][j].isMine())? 1:0;
                        this.casillas[i][j].setValue(minasAr);
                    }else if(j==0){
                        minasAr += (this.casillas[i-1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i-1][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i][j+1].isMine())? 1:0;
                        this.casillas[i][j].setValue(minasAr);
                    }else{
                        minasAr += (this.casillas[i-1][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i-1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i-1][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i][j+1].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j-1].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j].isMine())? 1:0;
                        minasAr += (this.casillas[i+1][j+1].isMine())? 1:0;
                        this.casillas[i][j].setValue(minasAr);
                    }
                    minasAr = 0;
                }
            }
        }
    }
    
    public int getFilas(){
        return this.filas;
    }
    
    public void setFilas(int filas){
        this.filas = filas;
    }
    
    public int getColumnas(){
        return this.columnas;
    }
    
    public void setColumnas(int columnas){
        this.columnas = columnas;
    }
    
    public boolean getFinalizado(){
        return this.finalizado;
    }
    
    public void setFinalizado(boolean finalizado){
        this.finalizado = finalizado;
    }
    
    public Casilla[][] getCasillas(){
        return this.casillas;
    }
    
    public void setCasillas(Casilla[][] casillas){
        this.casillas = casillas;
    }

    public int getNumeroMinas() {
        return numeroMinas;
    }

    public void setNumeroMinas(int numeroMinas) {
        this.numeroMinas = numeroMinas;
    }

}
