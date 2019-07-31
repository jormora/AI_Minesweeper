/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

/**
 *
 * @author Jorge Morales
 */
public class Accion {
    private int fila;
    private int columna;
    private boolean putFlag;
    private boolean descubrirCasilla;
    private boolean destaparVecinos;
    private boolean isMove50;
    private boolean isMove121;
    
    public Accion(int fila, int columna, String accion){
        this.fila = fila;
        this.columna = columna;
        if(accion.equals("B")){
            this.putFlag = true;
            this.descubrirCasilla = false;
            this.destaparVecinos = false;
        }else if(accion.equals("D")){
            this.putFlag = false;
            this.descubrirCasilla = true;
            this.destaparVecinos = false;
        }else if(accion.equals("DV")){
            this.putFlag = false;
            this.descubrirCasilla = false;
            this.destaparVecinos = true;
        }
        this.isMove50 = false;
        this.isMove121 = false;
    }
    
    public Accion(int fila, int columna, String accion, int move){
        this.fila = fila;
        this.columna = columna;
        if(accion.equals("B")){
            this.putFlag = true;
            this.descubrirCasilla = false;
            this.destaparVecinos = false;
        }else if(accion.equals("D")){
            this.putFlag = false;
            this.descubrirCasilla = true;
            this.destaparVecinos = false;
        }else if(accion.equals("DV")){
            this.putFlag = false;
            this.descubrirCasilla = false;
            this.destaparVecinos = true;
        }
        if(move==1){
            this.isMove50 = true;
        }else if(move==2){
            this.isMove121 = true;
        }
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean isPutFlag() {
        return putFlag;
    }

    public void setPutFlag(boolean putFlag) {
        this.putFlag = putFlag;
    }

    public boolean isDescubrirCasilla() {
        return descubrirCasilla;
    }

    public void setDescubrirCasilla(boolean descubrirCasilla) {
        this.descubrirCasilla = descubrirCasilla;
    }

    public boolean isDestaparVecinos() {
        return destaparVecinos;
    }

    public void setDestaparVecinos(boolean destaparVecinos) {
        this.destaparVecinos = destaparVecinos;
    }

    public boolean isIsMove50() {
        return isMove50;
    }

    public void setIsMove50(boolean isMove50) {
        this.isMove50 = isMove50;
    }

    public boolean isIsMove121() {
        return isMove121;
    }

    public void setIsMove121(boolean isMove121) {
        this.isMove121 = isMove121;
    }
    
}
