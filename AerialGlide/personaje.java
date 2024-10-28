	package juegojava;
	import java.awt.Graphics;
	import java.awt.Image;
	import java.awt.Rectangle;
	import java.io.File;
	import java.io.IOException;
	import javax.imageio.ImageIO;
	
	public class personaje {
	    // Posición del pájaro
	    private int x, y;
	    // Dimensiones del pájaro
	    private int ancho, largo;
	    // Velocidad de movimiento vertical
	    private int velocidad;
	    // Imagen del pájaro
	    private Image pjimagen;
	    
	    public personaje(int x, int y) {
	        // Inicializar la posición del pájaro
	        this.x = x;
	        this.y = y;
	        // Configurar el tamaño del pájaro
	        this.ancho = 90;
	        this.largo = 90;
	        // Configurar la velocidad inicial (en reposo)
	        this.velocidad = 10;
	        // Cargar la imagen del pájaro
	        try {
	            File file = new File("C:/pajaro.png");
	            System.out.println("Ruta absoluta: " + file.getAbsolutePath());
	            pjimagen = ImageIO.read(file); 
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Error al cargar la imagen del personaje.");
	        }

	
	    }
	
	    // Método para actualizar la posición del pájaro
	    public void update() {
	        // Mover el pájaro hacia abajo según la velocidad
	        y += velocidad;
	        // Aumentar la velocidad para simular la gravedad
	        velocidad += 1;
	        // Evitar que el pájaro salga por el suelo
	        if (y > 811 - ancho) {
	            y = 811 - ancho;
	            velocidad = 0;
	        }
	        // Evitar que el pájaro salga por la parte superior de la pantalla
	        if (y < 0) { // El borde superior es '0' en la coordenada 'y'
	            y = 0;
	            velocidad = 0;
	        }
	    }
	
	    // Método para hacer que el pájaro salte
	    public void jump() {
	        // Configurar la velocidad hacia arriba
	        velocidad = -10;
	    }
	
	    // Dibujar el pájaro en el juego
	    public void draw(Graphics g) {
	        if (pjimagen != null) {
	            g.drawImage(pjimagen, x, y, ancho, largo, null);
	        } else {
	            // En caso de que no se cargue la imagen, dibujar un rectángulo como reserva
	            g.fillRect(x, y, ancho, largo);
	        }
	    }
	
	    // Obtener el rectángulo de colisión del pájaro
	    public Rectangle getBounds() {
	        return new Rectangle(x, y, ancho, largo);
	    }
	}
