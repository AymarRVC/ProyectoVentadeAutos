/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FondoPanel extends JPanel {

    private Image imagen;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // ← Esta es la llamada correcta, al método de JPanel
        imagen = new ImageIcon(getClass().getResource("/IMG1.1/fondoimg.png")).getImage();
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}
