/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.util.ArrayList;

/**
 *
 * @author Jorge Morales
 */
public class Agente {
    private ArrayList<Accion> acciones;
    private int[][] tablero;
    private int filas;
    private int columnas;
    private int minas;
    private boolean finalizado;
    
    public Agente(int filas, int columnas, int minas){
        this.acciones = new ArrayList<>();
        this.tablero = new int[filas][columnas];
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                this.tablero[i][j] = -2;
            }
        }
        // -2 -> Desconocido -1 -> Bandera 10 -> Destapar, valor desconocido
        int x = (int)(Math.random()*this.filas);
        int y = (int)(Math.random()*this.columnas);
        if(x==10){
            x--;
        }
        if(y==10){
            y--;
        }
        this.tablero[x][y] = 10;
    }
    
    public ArrayList<Accion> realizarAccion(){
        ArrayList<Accion> act = new ArrayList<>();
        String accion;
        boolean aux = true;
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                if(this.tablero[i][j]!=-2&&this.tablero[i][j]!=-1&&this.tablero[i][j]!=0&&contarVecinosT(i,j)>0){
                    accion = comprobarCasilla(i,j);
                    if(accion.contains("B")){
                        String[] split = accion.split(",");
                        System.out.println(accion);
                        for (int k = 0; k < split.length-2; k+=2) {
                            if(!split[k].equals("B")){
                                this.acciones.add(new Accion(Integer.parseInt(split[k]), Integer.parseInt(split[k+1]), "B"));
                                act.add(new Accion(Integer.parseInt(split[k]), Integer.parseInt(split[k+1]), "B"));
                            }else{
                                this.acciones.add(new Accion(Integer.parseInt(split[k+1]), Integer.parseInt(split[k+2]), "DV"));
                                act.add(new Accion(Integer.parseInt(split[k+1]), Integer.parseInt(split[k+2]), "DV"));
                                aux = false;
                            }
                        }
                    }else if(accion.contains("DV")){
                        System.out.println(accion);
                        String[] split = accion.split(",");
                        this.acciones.add(new Accion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), "DV"));
                        act.add(new Accion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), "DV"));
                        aux = false;
                    }
                }
                if(!aux){
                    break;
                }
            }
            if(!aux){
                break;
            }
        }
        if(aux&&comprobarWin()){
            int xAxis = -1, yAxis = -1;
            boolean bo = true;
            for (int i = 0; i < this.filas; i++) {
                for (int j = 0; j < this.columnas; j++) {
                    if(this.tablero[i][j]==-2){
                        xAxis = i;
                        yAxis = j;
                        bo = false;
                        break;
                    }
                }
                if(!bo){
                    break;
                }
            }
            System.out.println(xAxis+","+yAxis+",B(LM=LC)");
            act.add(new Accion(xAxis, yAxis, "B"));
            this.acciones.add(new Accion(xAxis, yAxis, "B"));
        }else if(aux&&casilla121().contains("B")){
            System.out.print("Move121 ");
            String[] split = casilla121().split(",");
            for (int i = 0; i < split.length; i+=2) {
                if(!split[i].equals("B")){
                    this.acciones.add(new Accion(Integer.parseInt(split[i]), Integer.parseInt(split[i+1]), "B", 2));
                    act.add(new Accion(Integer.parseInt(split[i]), Integer.parseInt(split[i+1]), "B", 2));
                    System.out.print(split[i]+","+split[i+1]+",");
                }else{
                    System.out.println("B");
                    break;
                }
            }
        }else if(aux&&!move50Percent().equals("")){
            System.out.print("Move50 ");
            String[] split = move50Percent().split(",");
            for (int i = 0; i < split.length; i+=2) {
                if(!split[i].equals("B")){
                    this.acciones.add(new Accion(Integer.parseInt(split[i]), Integer.parseInt(split[i+1]), "B", 1));
                    act.add(new Accion(Integer.parseInt(split[i]), Integer.parseInt(split[i+1]), "B", 1));
                }else{
                    this.acciones.add(new Accion(Integer.parseInt(split[i+1]), Integer.parseInt(split[i+2]), "DV", 1));
                    act.add(new Accion(Integer.parseInt(split[i+1]), Integer.parseInt(split[i+2]), "DV", 1));
                    break;
                }
            }
            for (int i = 0; i < split.length; i++) {
                if(i==split.length-1){
                    System.out.print(split[i]);
                }else{
                    System.out.print(split[i]+",");
                }
            }
            System.out.println("");
        }else if(aux){
            String[] split = descubrirAleatorio().split(",");
            for (int i = 0; i < split.length; i++) {
                if(i==split.length-1){
                    System.out.print(split[i]);
                }else{
                    System.out.print(split[i]+",");
                }
            }
            System.out.println("");
            this.acciones.add(new Accion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), "D"));
            act.add(new Accion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), "D"));
        }
        
        /*boolean aux = true;
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                if(this.tablero[i][j]!=-2&&this.tablero[i][j]!=-1&&this.tablero[i][j]!=0&&contarVecinosT(i,j)>0){
                    accion = comprobarCasilla(i,j);
                    if(accion.contains("B")&&accion.contains("DV")){
                        String[] split = accion.split(",");
                        for (int k = 0; k < split.length; k+=2) {
                            if(!split[k].equals("B")){
                                act.add(new Accion(Integer.parseInt(split[k]), Integer.parseInt(split[k+1]), "B"));
                                aux = false;
                            }else {
                                act.add(new Accion(Integer.parseInt(split[k+1]), Integer.parseInt(split[k+2]), "DV"));
                                aux = false;
                                break;
                            }
                        }
                    }else if(accion.contains("DV")&&this.tablero[i][j]!=0&&contarVecinosT(i,j)>0){
                        String[] split = accion.split(",");
                        act.add(new Accion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), "DV"));
                        aux = false;
                        break;
                    }
                    break;
                }
            }
        }
        if(aux){
            String[] split = descubrirAleatorio().split(",");
            act.add(new Accion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), "D"));
        }*/
        return act;
    }
    
    public String comprobarCasilla(int x, int y){
        int cont = 0;
        String res = "";
        if(x==0&&y==0){
            if(this.getTablero()[x][y+1]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y+1]==-1){
                cont++;
            }
            if(cont==this.tablero[x][y]&&contarVecinosT(x,y)>0){
                return x + "," + y + "," + "DV";
            }
            cont = 0;
            if(this.getTablero()[x][y+1]==-2){
                res += x + "," + (y+1) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y+1]==-2){
                res += (x+1) + "," + (y+1) + ",";
                cont++;
            }
            if(cont==this.tablero[x][y]-contarBanderasCasilla(x,y)){
                return res + "B" + "," + x + "," + y + "," + "DV";
            }/*else if(cont==this.tablero[x][y]+1){
                int c = (int)(Math.random()*2+1);
                if(c==1&&this.tablero[x][y+1]==-2){
                    res = res.replace(x + "," + (y+1) + ",", "");
                }else if(c==2&&this.tablero[x+1][y]==-2){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else{
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }
                return res + "B" + "," + x + "," + y + "," + "DV";
            }*/
            return "";
        }else if(x==0&&y==this.columnas-1){
            if(this.getTablero()[x][y-1]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y-1]==-1){
                cont++;
            }
            if(cont==this.tablero[x][y]&&contarVecinosT(x,y)>0){
                return x + "," + y + "," + "DV";
            }
            cont = 0;
            if(this.getTablero()[x][y-1]==-2){
                res += x + "," + (y-1) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y-1]==-2){
                res += (x+1) + "," + (y-1) + ",";
                cont++;
            }
            if(cont==this.tablero[x][y]-contarBanderasCasilla(x,y)){
                return res + "B" + "," + x + "," + y + "," + "DV";
            }/*else if(cont==this.tablero[x][y]+1){
                int c = (int)(Math.random()*2+1);
                if(c==1&&this.tablero[x][y-1]==-2){
                    res = res.replace(x + "," + (y-1) + ",", "");
                }else if(c==2&&this.tablero[x+1][y]==-2){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else{
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }
                return res + "B" + "," + x + "," + y + "," + "DV";
            }*/
            return "";
        }else if(x==this.filas-1&&y==0){
            if(this.getTablero()[x][y+1]==-1){
                cont++;
            }
            if(this.getTablero()[x-1][y]==-1){
                cont++;
            }
            if(this.getTablero()[x-1][y+1]==-1){
                cont++;
            }
            if(cont==this.tablero[x][y]&&contarVecinosT(x,y)>0){
                return x + "," + y + "," + "DV";
            }
            cont = 0;
            if(this.getTablero()[x][y+1]==-2){
                res += x + "," + (y+1) + ",";
                cont++;
            }
            if(this.getTablero()[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
                cont++;
            }
            if(this.getTablero()[x-1][y+1]==-2){
                res += (x-1) + "," + (y+1) + ",";
                cont++;
            }
            if(cont==this.tablero[x][y]-contarBanderasCasilla(x,y)){
                return res + "B" + "," + x + "," + y + "," + "DV";
            }/*else if(cont==this.tablero[x][y]+1){
                int c = (int)(Math.random()*2+1);
                if(c==1&&this.tablero[x][y+1]==-2){
                    res = res.replace(x + "," + (y+1) + ",", "");
                }else if(c==2&&this.tablero[x-1][y]==-2){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else{
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }
                return res + "B" + "," + x + "," + y + "," + "DV";
            }*/
            return "";
        }else if(x==this.filas-1&&y==this.columnas-1){
            if(this.getTablero()[x][y-1]==-1){
                cont++;
            }
            if(this.getTablero()[x-1][y]==-1){
                cont++;
            }
            if(this.getTablero()[x-1][y-1]==-1){
                cont++;
            }
            if(cont==this.tablero[x][y]&&contarVecinosT(x,y)>0){
                return x + "," + y + "," + "DV";
            }
            cont = 0;
            if(this.getTablero()[x][y-1]==-2){
                res += x + "," + (y-1) + ",";
                cont++;
            }
            if(this.getTablero()[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
                cont++;
            }
            if(this.getTablero()[x-1][y-1]==-2){
                res += (x-1) + "," + (y-1) + ",";
                cont++;
            }
            if(cont==this.tablero[x][y]-contarBanderasCasilla(x,y)){
                return res + "B" + "," + x + "," + y + "," + "DV";
            }/*else if(cont==this.tablero[x][y]+1){
                int c = (int)(Math.random()*2+1);
                if(c==1&&this.tablero[x][y-1]==-2){
                    res = res.replace(x + "," + (y-1) + ",", "");
                }else if(c==2&&this.tablero[x+1][y]==-2){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else{
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }
                return res + "B" + "," + x + "," + y + "," + "DV";
            }*/
            return "";
        }else if(x==0){
            if(this.getTablero()[x][y-1]==-1){
                cont++;
            }
            if(this.getTablero()[x][y+1]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y-1]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y+1]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y]==-1){
                cont++;
            }
            if(cont==this.tablero[x][y]&&contarVecinosT(x,y)>0){
                return x + "," + y + "," + "DV";
            }
            cont = 0;
            if(this.getTablero()[x][y-1]==-2){
                res += x + "," + (y-1) + ",";
                cont++;
            }
            if(this.getTablero()[x][y+1]==-2){
                res += (x) + "," + (y+1) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y-1]==-2){
                res += (x+1) + "," + (y-1) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y+1]==-2){
                res += (x+1) + "," + (y+1) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
                cont++;
            }
            if(cont==this.tablero[x][y]-contarBanderasCasilla(x,y)){
                return res + "B" + "," + x + "," + y + "," + "DV";
            }/*else if(cont==this.tablero[x][y]+1){
                int c = (int)(Math.random()*4+1);
                if(c==1&&this.tablero[x][y-1]==-2){
                    res = res.replace(x + "," + (y-1) + ",", "");
                }else if(c==2&&this.tablero[x][y+1]==-2){
                    res = res.replace((x) + "," + (y+1) + ",", "");
                }else if(c==3&&this.tablero[x+1][y-1]==-2){
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }else if(c==4&&this.tablero[x+1][y+1]==-2){
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }else {
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }
                return res + "B" + "," + x + "," + y + "," + "DV";
            }*/
            return "";
        }else if(y==this.columnas-1){
            if(this.getTablero()[x-1][y]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y]==-1){
                cont++;
            }
            if(this.getTablero()[x-1][y-1]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y-1]==-1){
                cont++;
            }
            if(this.getTablero()[x][y-1]==-1){
                cont++;
            }
            if(cont==this.tablero[x][y]&&contarVecinosT(x,y)>0){
                return x + "," + y + "," + "DV";
            }
            cont = 0;
            if(this.getTablero()[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
                cont++;
            }
            if(this.getTablero()[x-1][y-1]==-2){
                res += (x-1) + "," + (y-1) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y-1]==-2){
                res += (x+1) + "," + (y-1) + ",";
                cont++;
            }
            if(this.getTablero()[x][y-1]==-2){
                res += (x) + "," + (y-1) + ",";
                cont++;
            }
            if(cont==this.tablero[x][y]-contarBanderasCasilla(x,y)){
                return res + "B" + "," + x + "," + y + "," + "DV";
            }/*else if(cont==this.tablero[x][y]+1){
                int c = (int)(Math.random()*4+1);
                if(c==1&&this.tablero[x-1][y]==-2){
                    res = res.replace(x + "," + (y-1) + ",", "");
                }else if(c==2&&this.tablero[x+1][y]==-2){
                    res = res.replace((x) + "," + (y+1) + ",", "");
                }else if(c==3&&this.tablero[x-1][y-1]==-2){
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }else if(c==4&&this.tablero[x+1][y-1]==-2){
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }else {
                    res = res.replace((x) + "," + (y-1) + ",", "");
                }
                return res + "B" + "," + x + "," + y + "," + "DV";
            }*/
            return "";
        }else if(x==this.filas-1){
            if(this.getTablero()[x][y-1]==-1){
                cont++;
            }
            if(this.getTablero()[x][y+1]==-1){
                cont++;
            }
            if(this.getTablero()[x-1][y-1]==-1){
                cont++;
            }
            if(this.getTablero()[x-1][y+1]==-1){
                cont++;
            }
            if(this.getTablero()[x-1][y]==-1){
                cont++;
            }
            if(cont==this.tablero[x][y]&&contarVecinosT(x,y)>0){
                return x + "," + y + "," + "DV";
            }
            cont = 0;
            if(this.getTablero()[x][y-1]==-2){
                res += x + "," + (y-1) + ",";
                cont++;
            }
            if(this.getTablero()[x][y+1]==-2){
                res += (x) + "," + (y+1) + ",";
                cont++;
            }
            if(this.getTablero()[x-1][y-1]==-2){
                res += (x-1) + "," + (y-1) + ",";
                cont++;
            }
            if(this.getTablero()[x-1][y+1]==-2){
                res += (x-1) + "," + (y+1) + ",";
                cont++;
            }
            if(this.getTablero()[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
                cont++;
            }
            if(cont==this.tablero[x][y]-contarBanderasCasilla(x,y)){
                return res + "B" + "," + x + "," + y + "," + "DV";
            }/*else if(cont==this.tablero[x][y]+1){
                int c = (int)(Math.random()*4+1);
                if(c==1&&this.tablero[x][y-1]==-2){
                    res = res.replace(x + "," + (y-1) + ",", "");
                }else if(c==2&&this.tablero[x][y+1]==-2){
                    res = res.replace((x) + "," + (y+1) + ",", "");
                }else if(c==3&&this.tablero[x-1][y-1]==-2){
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }else if(c==4&&this.tablero[x-1][y+1]==-2){
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }else {
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }
                return res + "B" + "," + x + "," + y + "," + "DV";
            }*/
            return "";
        }else if(y==0){
            if(this.getTablero()[x-1][y]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y]==-1){
                cont++;
            }
            if(this.getTablero()[x-1][y+1]==-1){
                cont++;
            }
            if(this.getTablero()[x+1][y+1]==-1){
                cont++;
            }
            if(this.getTablero()[x][y+1]==-1){
                cont++;
            }
            if(cont==this.tablero[x][y]&&contarVecinosT(x,y)>0){
                return x + "," + y + "," + "DV";
            }
            cont = 0;
            if(this.getTablero()[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
                cont++;
            }
            if(this.getTablero()[x-1][y+1]==-2){
                res += (x-1) + "," + (y+1) + ",";
                cont++;
            }
            if(this.getTablero()[x+1][y+1]==-2){
                res += (x+1) + "," + (y+1) + ",";
                cont++;
            }
            if(this.getTablero()[x][y+1]==-2){
                res += (x) + "," + (y+1) + ",";
                cont++;
            }
            if(cont==this.tablero[x][y]-contarBanderasCasilla(x,y)){
                return res + "B" + "," + x + "," + y + "," + "DV";
            }/*else if(cont==this.tablero[x][y]+1){
                int c = (int)(Math.random()*4+1);
                if(c==1&&this.tablero[x-1][y]==-2){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else if(c==2&&this.tablero[x+1][y]==-2){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(c==3&&this.tablero[x-1][y+1]==-2){
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }else if(c==4&&this.tablero[x+1][y+1]==-2){
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }else {
                    res = res.replace((x) + "," + (y+1) + ",", "");
                }
                return res + "B" + "," + x + "," + y + "," + "DV";
            }*/
            return "";
        }else{
            for (int i = x-1; i < x+2; i++) {
                for (int j = y-1; j < y+2; j++) {
                    if(this.tablero[i][j]==-1){
                        cont++;
                    }
                }
            }
            if(cont==this.tablero[x][y]&&contarVecinosT(x,y)>0){
                return x + "," + y + "," + "DV";
            }
            cont = 0;
            for (int i = x-1; i < x+2; i++) {
                for (int j = y-1; j < y+2; j++) {
                    if(this.tablero[i][j]==-2){
                        res += (i) + "," + (j) + ",";
                        cont++;
                    }
                }
            }
            if(cont==this.tablero[x][y]-contarBanderasCasilla(x,y)){
                return res + "B" + "," + x + "," + y + "," + "DV";
            }/*else if(cont==this.tablero[x][y]+1){
                int c = (int)(Math.random()*7+1);
                if(c==1&&this.tablero[x-1][y-1]==-2){
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }else if(c==2&&this.tablero[x-1][y]==-2){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else if(c==3&&this.tablero[x-1][y+1]==-2){
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }else if(c==4&&this.tablero[x][y-1]==-2){
                    res = res.replace((x) + "," + (y-1) + ",", "");
                }else if(c==5&&this.tablero[x][y+1]==-2){
                    res = res.replace((x) + "," + (y+1) + ",", "");
                }else if(c==6&&this.tablero[x+1][y-1]==-2){
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }else if(c==7&&this.tablero[x+1][y]==-2){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else {
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }
                return res + "B" + "," + x + "," + y + "," + "DV";
            }*/
            return "";
        }
    }
    
    public String move50Percent(){
        int x = -1, y = -1;
        String res = "";
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                if(contarVecinosT(i,j)==2&&this.tablero[i][j]-contarBanderasCasilla(i,j)==1&&contarBanderasCasilla(i, j)>0){
                    x = i;
                    y = j;
                    break;
                }
            }
            if(x==i){
                break;
            }
        }
        if(x==-1&&y==-1){
            return "";
        }
        if(x==0&&y==0){
            int a = (int)(Math.random()*2+1);
            if(this.tablero[x][y+1]==-2){
                res += x + "," + (y+1) + ",";
            }
            if(this.tablero[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
            }
            if(this.tablero[x+1][y+1]==-2){
                res += (x+1) + "," + (y+1) + ",";
            }
            if(a==1){
                if(res.contains(x + "," + (y+1) + ",")){
                    res = res.replace(x + "," + (y+1) + ",", "");
                }else if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(res.contains((x+1) + "," + (y+1) + ",")){
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }
            }else{
                if(res.contains((x+1) + "," + (y+1) + ",")){
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }else if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(res.contains(x + "," + (y+1) + ",")){
                    res = res.replace(x + "," + (y+1) + ",", "");
                }
            }
            return res + "B" + "," + x + "," + y + "," + "DV";
        }else if(x==0&&y==this.columnas-1){
            int a = (int)(Math.random()*2+1);
            if(this.tablero[x][y-1]==-2){
                res += x + "," + (y-1) + ",";
            }
            if(this.tablero[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
            }
            if(this.tablero[x+1][y-1]==-2){
                res += (x+1) + "," + (y-1) + ",";
            }
            if(a==1){
                if(res.contains(x + "," + (y-1) + ",")){
                    res = res.replace(x + "," + (y-1) + ",", "");
                }else if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(res.contains((x+1) + "," + (y-1) + ",")){
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }
            }else{
                if(res.contains((x+1) + "," + (y-1) + ",")){
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }else if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(res.contains(x + "," + (y-1) + ",")){
                    res = res.replace(x + "," + (y-1) + ",", "");
                }
            }
            return res + "B" + "," + x + "," + y + "," + "DV";
        }else if(x==this.filas-1&&y==this.columnas-1){
            int a = (int)(Math.random()*2+1);
            if(this.tablero[x][y-1]==-2){
                res += x + "," + (y-1) + ",";
            }
            if(this.tablero[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
            }
            if(this.tablero[x-1][y-1]==-2){
                res += (x-1) + "," + (y-1) + ",";
            }
            if(a==1){
                if(res.contains(x + "," + (y-1) + ",")){
                    res = res.replace(x + "," + (y-1) + ",", "");
                }else if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else if(res.contains((x-1) + "," + (y-1) + ",")){
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }
            }else{
                if(res.contains((x-1) + "," + (y-1) + ",")){
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }else if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else if(res.contains(x + "," + (y-1) + ",")){
                    res = res.replace(x + "," + (y-1) + ",", "");
                }
            }
            return res + "B" + "," + x + "," + y + "," + "DV";
        }else if(x==this.filas-1&&y==0){
            int a = (int)(Math.random()*2+1);
            if(this.tablero[x][y+1]==-2){
                res += x + "," + (y+1) + ",";
            }
            if(this.tablero[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
            }
            if(this.tablero[x-1][y+1]==-2){
                res += (x-1) + "," + (y+1) + ",";
            }
            if(a==1){
                if(res.contains(x + "," + (y+1) + ",")){
                    res = res.replace(x + "," + (y+1) + ",", "");
                }else if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else if(res.contains((x-1) + "," + (y+1) + ",")){
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }
            }else{
                if(res.contains((x-1) + "," + (y+1) + ",")){
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }else if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else if(res.contains(x + "," + (y+1) + ",")){
                    res = res.replace(x + "," + (y+1) + ",", "");
                }
            }
            return res + "B" + "," + x + "," + y + "," + "DV";
        }else if(x==0){
            int a = (int)(Math.random()*2+1);
            if(this.tablero[x][y+1]==-2){
                res += x + "," + (y+1) + ",";
            }
            if(this.tablero[x][y-1]==-2){
                res += (x) + "," + (y-1) + ",";
            }
            if(this.tablero[x+1][y+1]==-2){
                res += (x+1) + "," + (y+1) + ",";
            }
            if(this.tablero[x+1][y-1]==-2){
                res += (x+1) + "," + (y-1) + ",";
            }
            if(this.tablero[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
            }
            if(a==1){
                if(res.contains(x + "," + (y+1) + ",")){
                    res = res.replace(x + "," + (y+1) + ",", "");
                }else if(res.contains((x) + "," + (y-1) + ",")){
                    res = res.replace((x) + "," + (y-1) + ",", "");
                }else if(res.contains((x+1) + "," + (y+1) + ",")){
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }else if(res.contains((x+1) + "," + (y-1) + ",")){
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }else if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }
            }else{
                if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(res.contains((x+1) + "," + (y-1) + ",")){
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }else if(res.contains((x+1) + "," + (y+1) + ",")){
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }else if(res.contains((x) + "," + (y-1) + ",")){
                    res = res.replace((x) + "," + (y-1) + ",", "");
                }else if(res.contains((x) + "," + (y+1) + ",")){
                    res = res.replace((x) + "," + (y+1) + ",", "");
                }
            }
            return res + "B" + "," + x + "," + y + "," + "DV";
        }else if(y==this.columnas-1){
            int a = (int)(Math.random()*2+1);
            if(this.tablero[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
            }
            if(this.tablero[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
            }
            if(this.tablero[x-1][y-1]==-2){
                res += (x-1) + "," + (y-1) + ",";
            }
            if(this.tablero[x+1][y-1]==-2){
                res += (x+1) + "," + (y-1) + ",";
            }
            if(this.tablero[x][y-1]==-2){
                res += (x) + "," + (y-1) + ",";
            }
            if(a==1){
                if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(res.contains((x-1) + "," + (y-1) + ",")){
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }else if(res.contains((x+1) + "," + (y-1) + ",")){
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }else if(res.contains((x) + "," + (y-1) + ",")){
                    res = res.replace((x) + "," + (y-1) + ",", "");
                }
            }else{
                if(res.contains((x) + "," + (y-1) + ",")){
                    res = res.replace((x) + "," + (y-1) + ",", "");
                }else if(res.contains((x+1) + "," + (y-1) + ",")){
                    res = res.replace((x+1) + "," + (y-1) + ",", "");
                }else if(res.contains((x-1) + "," + (y-1) + ",")){
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }else if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }
            }
            return res + "B" + "," + x + "," + y + "," + "DV";
        }else if(x==this.filas-1){
            int a = (int)(Math.random()*2+1);
            if(this.tablero[x][y+1]==-2){
                res += x + "," + (y+1) + ",";
            }
            if(this.tablero[x][y-1]==-2){
                res += (x) + "," + (y-1) + ",";
            }
            if(this.tablero[x-1][y+1]==-2){
                res += (x-1) + "," + (y+1) + ",";
            }
            if(this.tablero[x-1][y-1]==-2){
                res += (x-1) + "," + (y-1) + ",";
            }
            if(this.tablero[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
            }
            if(a==1){
                if(res.contains(x + "," + (y+1) + ",")){
                    res = res.replace(x + "," + (y+1) + ",", "");
                }else if(res.contains((x) + "," + (y-1) + ",")){
                    res = res.replace((x) + "," + (y-1) + ",", "");
                }else if(res.contains((x-1) + "," + (y+1) + ",")){
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }else if(res.contains((x-1) + "," + (y-1) + ",")){
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }else if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }
            }else{
                if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else if(res.contains((x-1) + "," + (y-1) + ",")){
                    res = res.replace((x-1) + "," + (y-1) + ",", "");
                }else if(res.contains((x-1) + "," + (y+1) + ",")){
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }else if(res.contains((x) + "," + (y-1) + ",")){
                    res = res.replace((x) + "," + (y-1) + ",", "");
                }else if(res.contains((x) + "," + (y+1) + ",")){
                    res = res.replace((x) + "," + (y+1) + ",", "");
                }
            }
            return res + "B" + "," + x + "," + y + "," + "DV";
        }else if(y==0){
            int a = (int)(Math.random()*2+1);
            if(this.tablero[x-1][y]==-2){
                res += (x-1) + "," + (y) + ",";
            }
            if(this.tablero[x+1][y]==-2){
                res += (x+1) + "," + (y) + ",";
            }
            if(this.tablero[x-1][y+1]==-2){
                res += (x-1) + "," + (y+1) + ",";
            }
            if(this.tablero[x+1][y+1]==-2){
                res += (x+1) + "," + (y+1) + ",";
            }
            if(this.tablero[x][y+1]==-2){
                res += (x) + "," + (y+1) + ",";
            }
            if(a==1){
                if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }else if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(res.contains((x-1) + "," + (y+1) + ",")){
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }else if(res.contains((x+1) + "," + (y+1) + ",")){
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }else if(res.contains((x) + "," + (y+1) + ",")){
                    res = res.replace((x) + "," + (y+1) + ",", "");
                }
            }else{
                if(res.contains((x) + "," + (y+1) + ",")){
                    res = res.replace((x) + "," + (y+1) + ",", "");
                }else if(res.contains((x+1) + "," + (y+1) + ",")){
                    res = res.replace((x+1) + "," + (y+1) + ",", "");
                }else if(res.contains((x-1) + "," + (y+1) + ",")){
                    res = res.replace((x-1) + "," + (y+1) + ",", "");
                }else if(res.contains((x+1) + "," + (y) + ",")){
                    res = res.replace((x+1) + "," + (y) + ",", "");
                }else if(res.contains((x-1) + "," + (y) + ",")){
                    res = res.replace((x-1) + "," + (y) + ",", "");
                }
            }
            return res + "B" + "," + x + "," + y + "," + "DV";
        }else{
            boolean aux;
            int a = (int)(Math.random()*2+1);
            aux = a != 1;
            for (int i = x-1; i < x+2; i++) {
                for (int j = y-1; j < y+2; j++) {
                    if(this.tablero[i][j]==-2&&aux){
                        res += i + "," + j + ",";
                    }
                    aux = !aux;
                }
            }
            return res + "B" + "," + x + "," + y + "," + "DV";
        }
    }
    
    public boolean comprobarWin(){
        int Flags = 0;
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                if(this.tablero[i][j]==-1){
                    Flags++;
                }
            }
        }
        int casillasT = 0;
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                if(this.tablero[i][j]==-2){
                    casillasT++;
                }
            }
        }
        return (Flags==this.minas-1)&&(casillasT==1);
    }
    
    public String casilla121(){
        boolean aux = true;
        int[] ind = new int[4];
        for (int i = 0; i < 4; i++) {
            ind[i] = -1;
        }
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.filas; j++) {
                if(this.tablero[i][j]==2&&contarVecinosT(i,j)==3){
                    ind = comprobarRecta(i,j);
                    //System.out.println(contarVecinosT(i,j));
                    if(ind[0]!=-1){
                        aux = false;
                        break;
                    }
                }
            }
            if(!aux){
                break;
            }
        }
        if(ind[0]!=-1){
            return ind[0]+","+ind[1]+","+ind[2]+","+ind[3]+",B";
        }else{
            return "";
        }
        
    }
    
    public int[] comprobarRecta(int i,int j){
        int[] res = new int[4];
        for (int k = 0; k < 4; k++) {
            res[k] = -1;
        }
        if(i==0&&j!=0&&j!=this.columnas-1){
            if(this.tablero[i][j-1]==1&&this.tablero[i][j+1]==1){
                res[0] = i+1;
                res[1] = j-1;
                res[2] = i+1;
                res[3] = j+1;
            }
        }else if(j==this.columnas-1&&i!=0&&i!=this.filas-1){
            if(this.tablero[i-1][j]==1&&this.tablero[i+1][j]==1){
                res[0] = i-1;
                res[1] = j-1;
                res[2] = i+1;
                res[3] = j-1;
            }
        }else if(i==this.filas-1&&j!=0&&j!=this.columnas-1){
            if(this.tablero[i][j-1]==1&&this.tablero[i][j+1]==1){
                res[0] = i-1;
                res[1] = j-1;
                res[2] = i-1;
                res[3] = j+1;
            }
        }
        else if(j==0&&i!=0&&i!=this.filas-1){
            if(this.tablero[i-1][j]==1&&this.tablero[i+1][j]==1){
                res[0] = i-1;
                res[1] = j+1;
                res[2] = i+1;
                res[3] = j+1;
            }
        }else if((i!=0&&j!=0)&&(i!=0&&j!=this.columnas-1)&&(i!=this.filas-1&&j!=this.columnas-1)&&(i!=this.filas-1&&j!=0)){
            if(this.tablero[i][j-1]==1&&this.tablero[i][j+1]==1){
                if(this.tablero[i-1][j-1]==-2&&this.tablero[i-1][j+1]==-2){
                    res[0] = i-1;
                    res[1] = j-1;
                    res[2] = i-1;
                    res[3] = j+1;
                }else if(this.tablero[i+1][j-1]==-2&&this.tablero[i+1][j+1]==-2){
                    res[0] = i+1;
                    res[1] = j-1;
                    res[2] = i+1;
                    res[3] = j+1;
                }
            }else if(this.tablero[i-1][j]==1&&this.tablero[i+1][j]==1){
                if(this.tablero[i-1][j-1]==-2&&this.tablero[i+1][j-1]==-2){
                    res[0] = i-1;
                    res[1] = j-1;
                    res[2] = i+1;
                    res[3] = j-1;
                }else if(this.tablero[i-1][j+1]==-2&&this.tablero[i+1][j+1]==-2){
                    res[0] = i-1;
                    res[1] = j+1;
                    res[2] = i+1;
                    res[3] = j+1;
                }
            }
        }
        return res;
    }
    
    public String Move1(){
        boolean aux = true;
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                if(this.tablero[i][j]==1&&contarVecinosT(i,j)==3){
                    ////////////////////////////////////
                }
            }
        }
        return "";
    }
    
    public boolean comprobarRectaT(int i, int j){
        boolean res = true;
        /////////////////////////////////////
        return res;
    }
    
    public String descubrirAleatorio(){
        int r1 = (int)(Math.random()*this.filas);
        int r2 = (int)(Math.random()*this.columnas);
        if(r1==10){
            r1--;
        }
        if(r2==10){
            r2--;
        }
        while((this.tablero[r1][r2]!=-2)){
            r1 = (int)(Math.random()*this.filas);
            r2 = (int)(Math.random()*this.columnas);
            if(r1==10){
                r1--;
            }
            if(r2==10){
                r2--;
            }
        }
        return r1 + "," + r2 + "," + "D";
    }
    
    public int contarVecinosT(int x, int y){
        int cont = 0;
        if(x==0&&y==0){
            if(this.tablero[x][y+1]==-2){
                cont++;
            }
            if(this.tablero[x+1][y]==-2){
                cont++;
            }
            if(this.tablero[x+1][y+1]==-2){
                cont++;
            }
            return cont;
        }else if(x==0&&y==this.columnas-1){
            if(this.tablero[x][y-1]==-2){
                cont++;
            }
            if(this.tablero[x+1][y]==-2){
                cont++;
            }
            if(this.tablero[x+1][y-1]==-2){
                cont++;
            }
            return cont;
        }else if(x==this.filas-1&&y==this.columnas-1){
            if(this.tablero[x][y-1]==-2){
                cont++;
            }
            if(this.tablero[x-1][y]==-2){
                cont++;
            }
            if(this.tablero[x-1][y-1]==-2){
                cont++;
            }
            return cont;
        }else if(x==this.filas-1&&y==0){
            if(this.tablero[x][y+1]==-2){
                cont++;
            }
            if(this.tablero[x-1][y]==-2){
                cont++;
            }
            if(this.tablero[x-1][y+1]==-2){
                cont++;
            }
            return cont;
        }else if(x==0){
            if(this.tablero[x][y+1]==-2){
                cont++;
            }
            if(this.tablero[x][y-1]==-2){
                cont++;
            }
            if(this.tablero[x+1][y+1]==-2){
                cont++;
            }
            if(this.tablero[x+1][y-1]==-2){
                cont++;
            }
            if(this.tablero[x+1][y]==-2){
                cont++;
            }
            return cont;
        }else if(y==this.columnas-1){
            if(this.tablero[x+1][y]==-2){
                cont++;
            }
            if(this.tablero[x-1][y]==-2){
                cont++;
            }
            if(this.tablero[x+1][y-1]==-2){
                cont++;
            }
            if(this.tablero[x-1][y-1]==-2){
                cont++;
            }
            if(this.tablero[x][y-1]==-2){
                cont++;
            }
            return cont;
        }else if(x==this.filas-1){
            if(this.tablero[x][y+1]==-2){
                cont++;
            }
            if(this.tablero[x][y-1]==-2){
                cont++;
            }
            if(this.tablero[x-1][y+1]==-2){
                cont++;
            }
            if(this.tablero[x-1][y-1]==-2){
                cont++;
            }
            if(this.tablero[x-1][y]==-2){
                cont++;
            }
            return cont;
        }else if(y==0){
            if(this.tablero[x-1][y]==-2){
                cont++;
            }
            if(this.tablero[x+1][y]==-2){
                cont++;
            }
            if(this.tablero[x-1][y+1]==-2){
                cont++;
            }
            if(this.tablero[x+1][y+1]==-2){
                cont++;
            }
            if(this.tablero[x][y+1]==-2){
                cont++;
            }
            return cont;
        }else{
            for (int i = x-1; i < x+2; i++) {
                for (int j = y-1; j < y+2; j++) {
                    if(this.tablero[i][j]==-2){
                        cont++;
                    }
                }
            }
            return cont;
        }
    }
    
    public int contarBanderasCasilla(int x, int y){
        int cont = 0;
        if(x==0&&y==0){
            if(this.tablero[x][y+1]==-1){
                cont++;
            }
            if(this.tablero[x+1][y]==-1){
                cont++;
            }
            if(this.tablero[x+1][y+1]==-1){
                cont++;
            }
            return cont;
        }else if(x==0&&y==this.columnas-1){
            if(this.tablero[x][y-1]==-1){
                cont++;
            }
            if(this.tablero[x+1][y]==-1){
                cont++;
            }
            if(this.tablero[x+1][y-1]==-1){
                cont++;
            }
            return cont;
        }else if(x==this.filas-1&&y==this.columnas-1){
            if(this.tablero[x][y-1]==-1){
                cont++;
            }
            if(this.tablero[x-1][y]==-1){
                cont++;
            }
            if(this.tablero[x-1][y-1]==-1){
                cont++;
            }
            return cont;
        }else if(x==this.filas-1&&y==0){
            if(this.tablero[x][y+1]==-1){
                cont++;
            }
            if(this.tablero[x-1][y]==-1){
                cont++;
            }
            if(this.tablero[x-1][y+1]==-1){
                cont++;
            }
            return cont;
        }else if(x==0){
            if(this.tablero[x][y+1]==-1){
                cont++;
            }
            if(this.tablero[x][y-1]==-1){
                cont++;
            }
            if(this.tablero[x+1][y+1]==-1){
                cont++;
            }
            if(this.tablero[x+1][y-1]==-1){
                cont++;
            }
            if(this.tablero[x+1][y]==-1){
                cont++;
            }
            return cont;
        }else if(y==this.columnas-1){
            if(this.tablero[x+1][y]==-1){
                cont++;
            }
            if(this.tablero[x-1][y]==-1){
                cont++;
            }
            if(this.tablero[x+1][y-1]==-1){
                cont++;
            }
            if(this.tablero[x-1][y-1]==-1){
                cont++;
            }
            if(this.tablero[x][y-1]==-1){
                cont++;
            }
            return cont;
        }else if(x==this.filas-1){
            if(this.tablero[x][y+1]==-1){
                cont++;
            }
            if(this.tablero[x][y-1]==-1){
                cont++;
            }
            if(this.tablero[x-1][y+1]==-1){
                cont++;
            }
            if(this.tablero[x-1][y-1]==-1){
                cont++;
            }
            if(this.tablero[x-1][y]==-1){
                cont++;
            }
            return cont;
        }else if(y==0){
            if(this.tablero[x-1][y]==-1){
                cont++;
            }
            if(this.tablero[x+1][y]==-1){
                cont++;
            }
            if(this.tablero[x-1][y+1]==-1){
                cont++;
            }
            if(this.tablero[x+1][y+1]==-1){
                cont++;
            }
            if(this.tablero[x][y+1]==-1){
                cont++;
            }
            return cont;
        }else{
            for (int i = x-1; i < x+2; i++) {
                for (int j = y-1; j < y+2; j++) {
                    if(this.tablero[i][j]==-1){
                        cont++;
                    }
                }
            }
            return cont;
        }
    }

    public ArrayList<Accion> getAcciones() {
        return acciones;
    }

    public void setAccion(Accion accion) {
        this.acciones.add(accion);
    }

    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public int getMinas() {
        return minas;
    }

    public void setMinas(int minas) {
        this.minas = minas;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
    
    
    
}
