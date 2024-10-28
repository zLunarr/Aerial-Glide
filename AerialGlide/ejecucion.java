package juegojava;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ejecucion {
    public static void main(String[] args) {
        // Crear la ventana del juego
        JFrame frame = new JFrame();
        // Crear el panel del juego
        juegoPanel juego = new juegoPanel(); 

        // Agregar el panel del juego a la ventana
        frame.add(juego);
        juego.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        frame.pack(); // Ajusta el tamaño de la ventana a la del panel
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Título de la ventana
        frame.setTitle("Aerial Glide");
        // Cerrar la aplicación al salir
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Evitar que la ventana sea redimensionable
        frame.setResizable(false);
        // Tamaño de la ventana
        frame.setSize(2000, 1800);
        // Centrar la ventana en la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar la ventana
        frame.setVisible(true);
    }
}
