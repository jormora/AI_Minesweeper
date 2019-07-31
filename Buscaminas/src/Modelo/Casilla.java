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

public class Casilla {
    private int value;
    private boolean isMine;
    private boolean isDescubierta;
    private boolean isFlag;
    
    public Casilla(int value, boolean isMine){
        this.value = value;
        this.isMine = isMine;
        this.isDescubierta = false;
        this.isFlag = false;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public void setValue(int value){
        this.value = value;
    }
    
    public boolean isMine(){
        return this.isMine;
    }
    
    public void setIsMine(boolean isMine){
        this.isMine = isMine;
    }
    
    public boolean isDescubierta(){
        return this.isDescubierta;
    }
    
    public void setIsDescubierta(boolean isDescubierta){
        this.isDescubierta = isDescubierta;
    }
    
    public boolean isFlag(){
        return this.isFlag;
    }
    
    public void setIsFlag(boolean isFlag){
        this.isFlag = isFlag;
    }
}
