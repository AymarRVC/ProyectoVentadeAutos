/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


public class ClsVentas {
    int id;
    String fecha;
    double precios;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getFecha(){
        return fecha;
    }
    public void setFecha (String fecha){
        this.fecha = fecha;
    }
    public double getPrecios(){
        return precios;
    }
    public void setPrecios (double precios){
        this.precios = precios;
    }
    
    @Override
    public String toString() {
        return getFecha()+" - "+getPrecios();
    }
    
}
