package juegojava;

import java.awt.Component;

import javax.swing.JFrame;

public class ejecucion {
    public static void main(String[] args) {
        // Crear la ventana del juego
        JFrame frame = new JFrame();
        // Crear el panel del juego
        juegoPanel juego = new juegoPanel(); // Asegúrate de que esta clase extienda JPanel

        // Agregar el panel del juego a la ventana
        frame.add(juego);
        // Configurar el título de la ventana
        frame.setTitle("Aerial Glide");
        // Configurar para cerrar la aplicación al salir
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Evitar que la ventana sea redimensionable
        frame.setResizable(false);
        // Establecer el tamaño de la ventana
        frame.setSize(400, 600);
        // Centrar la ventana en la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar la ventana
        frame.setVisible(true);
    }
}
