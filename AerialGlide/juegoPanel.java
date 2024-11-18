package juegojava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;

public class JuegoPanel extends JPanel implements ActionListener, KeyListener {
    private Timer tiempo;
    private Image fondo;
    private Personaje pajaro;
    private ArrayList<Obstaculos> obstaculos;
    private int contadorTiempo;
    private int contadorSaltos;
    private static final int ESPACIO_ENTRE_OBSTACULOS = 200;
    private static final int ANCHO_OBSTACULO = 120;
    
    // Componentes para mostrar mensaje de perder
    private JLabel mensajePerder;
    private JButton botonReiniciar;

    // Reproductor de música
    private Clip musicaFondo;
    private Clip efectoSalto;
    
    private boolean musicaActivada;  // Variable para controlar el estado de la música

    public JuegoPanel(boolean musicaActivada) {
        this.musicaActivada = musicaActivada;
        fondo = new ImageIcon("C:/JAVA/juegojava/src/Resources/fondo juego.jpg").getImage();
        pajaro = new Personaje(100, 300);
        obstaculos = new ArrayList<>();
        tiempo = new Timer(20, this);
        tiempo.start();
        
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
        
        // Inicialización de los componentes del mensaje de perder
        mensajePerder = new JLabel("¡Perdiste! Tu puntuación es: 0");
        mensajePerder.setFont(new Font("Arial", Font.BOLD, 60));
        mensajePerder.setForeground(Color.RED);
        mensajePerder.setHorizontalAlignment(SwingConstants.CENTER);
        mensajePerder.setVisible(false);
        
        botonReiniciar = new JButton("Reiniciar Juego");
        botonReiniciar.setFont(new Font("Arial", Font.BOLD, 40));
        botonReiniciar.setBackground(Color.WHITE);
        botonReiniciar.setForeground(Color.BLACK);
        botonReiniciar.addActionListener(e -> reiniciarJuego());
        botonReiniciar.setVisible(false);
        
        // Añadir el mensaje y el botón al panel
        setLayout(new BorderLayout());
        add(mensajePerder, BorderLayout.CENTER);
        add(botonReiniciar, BorderLayout.SOUTH);
        
        // Inicializar contador de saltos
        contadorSaltos = 0;
        
        // Cargar la música de fondo si está activada
        if (musicaActivada) {
            cargarMusicaFondo();
        }
    }
    private void cargarMusicaFondo() {
        try {
            // Ruta de la música de fondo
            File musicaArchivo = new File("C:/JAVA/juegojava/src/Resources/Juego 35.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicaArchivo);
            musicaFondo = AudioSystem.getClip();
            musicaFondo.open(audioStream);
            musicaFondo.loop(Clip.LOOP_CONTINUOUSLY); // Reproducir en bucle
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        pajaro.draw(g);

        // Dibujar obstáculos
        for (Obstaculos obstaculo : obstaculos) {
            obstaculo.dibujar(g);
        }

        // Dibujar la puntuación en la esquina superior izquierda (basada en saltos)
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.WHITE);
        g.drawString("Puntuación: " + contadorSaltos, 20, 40);  // Mostrar la puntuación basada en saltos
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pajaro.update(getHeight()); // Pasar la altura del panel al personaje

        // Mover obstáculos y eliminar los que salen de la pantalla
        for (Obstaculos obstaculo : obstaculos) {
            obstaculo.mover(8); // Velocidad de movimiento
        }
        obstaculos.removeIf(Obstaculos::fueraDePantalla);

        // Generar nuevos obstáculos cada cierto tiempo
        contadorTiempo++;
        if (contadorTiempo % 80 == 0) { // Ajusta para cambiar la frecuencia
            generarObstaculos();
        }

        // Detectar colisiones
        for (Obstaculos obstaculo : obstaculos) {
            if (pajaro.getBounds().intersects(obstaculo.getBounds())) {
                perder();
                return;
            }
        }

        repaint();
    }

    private void generarObstaculos() {
        int alturaVentana = getHeight();
        int espacioVertical = 200; // Espacio entre los dos obstáculos
        int alturaObstaculoSuperior = (int) (Math.random() * (alturaVentana - espacioVertical - 100)) + 60;

        // Obstáculo superior
        obstaculos.add(new Obstaculos(
            getWidth(), 
            0, 
            ANCHO_OBSTACULO, 
            alturaObstaculoSuperior, 
            "C:/JAVA/juegojava/src/Resources/obstacle - copia.png"
        ));

        // Obstáculo inferior
        obstaculos.add(new Obstaculos(
            getWidth(), 
            alturaObstaculoSuperior + espacioVertical, 
            ANCHO_OBSTACULO, 
            alturaVentana - alturaObstaculoSuperior - espacioVertical, 
            "C:/JAVA/juegojava/src/Resources/obstacle.png"
        ));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pajaro.jump(); // Saltar cuando se presiona la barra espaciadora
            contadorSaltos++; // Incrementar el contador de saltos
            saltar(); // Reproducir el efecto de salto
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void saltar() {
        if (efectoSalto != null) {
            efectoSalto.setFramePosition(0); // Reiniciar el sonido
            efectoSalto.start(); // Reproducir sonido de salto
        }
    }

    private void perder() {
        tiempo.stop();
        mensajePerder.setText("¡Perdiste! Tu puntuación es: " + contadorSaltos); // Mostrar la puntuación al perder
        mensajePerder.setVisible(true);
        botonReiniciar.setVisible(true); // Hacer visible el botón de reiniciar

        // Detener la música de fondo cuando se pierde, si está activada
        if (musicaFondo != null && musicaFondo.isRunning()) {
            musicaFondo.stop();
        }
    }

    private void reiniciarJuego() {
        // Restablecer la puntuación y otros elementos
        contadorTiempo = 0;
        contadorSaltos = 0;
        obstaculos.clear();
        pajaro = new Personaje(100, 300);

        // Ocultar el mensaje de perder y el botón de reiniciar
        mensajePerder.setVisible(false);
        botonReiniciar.setVisible(false);

        // Iniciar nuevamente el juego
        tiempo.start();
        repaint();

        // Si la música está activada, detenerla y reiniciarla
        if (musicaActivada) {
            // Detener la música y restablecer el clip si está inicializado
            if (musicaFondo != null && musicaFondo.isRunning()) {
                musicaFondo.stop();
            }
            // Reinstalar el clip y reiniciar la música
            try {
                File musicaArchivo = new File("C:/JAVA/juegojava/src/Resources/Juego 35.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicaArchivo);
                musicaFondo = AudioSystem.getClip();
                musicaFondo.open(audioStream);
                musicaFondo.loop(Clip.LOOP_CONTINUOUSLY);  // Reproducir música en bucle
                musicaFondo.start();  // Reproducir música
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

