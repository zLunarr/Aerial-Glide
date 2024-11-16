	package juegojava;
	
	import java.awt.Graphics;
	import java.awt.Image;
	import javax.swing.ImageIcon;
	import java.awt.Rectangle;
	
	public class Personaje {
	    private int x, y, velocidad;
	    private final int ancho = 90, alto = 90;
	    private final Image imagen;
	    private boolean enSuelo;  // Variable para saber si el personaje tocó el suelo
	
	    public Personaje(int x, int y) {
	        this.x = x;
	        this.y = y;
	        this.velocidad = 0;
	        this.imagen = new ImageIcon("C:/JAVA/juegojava/src/Resources/bird.png").getImage();
	        this.enSuelo = false;  // Inicialmente, no está en el suelo
	    }
	
	    public void update() {
	        y += velocidad;
	        velocidad += 1; // Gravedad

	        if (y + alto > 800) { // Límite inferior
	            y = 800 - alto;
	            velocidad = 0;
	        }
	        if (y < 0) { // Límite superior
	            y = 0;
	            velocidad = 0;
	        }
	    }

	    public void jump() {
	        velocidad = -15;
	    }

	    public void draw(Graphics g) {
	        g.drawImage(imagen, x, y, ancho, alto, null);
	    }

	    public Rectangle getBounds() {
	        return new Rectangle(x, y, ancho, alto);
	    }
	}
