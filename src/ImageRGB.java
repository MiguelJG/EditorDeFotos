import java.util.*;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/** Esta clase consiste en la representacion que le damos a una imagen. Con este estandar podemos simplificar las operaciones a un solo formato
 * y simplificar la adicion de nuevas utilidades
 * @author Miguel Jiménez Gomis
 * @date 26/10/2018
 *
 */
public class ImageRGB {
	String name;			//String que almacena el PATH a la imagen que queremos modificar
	Boolean blackAndWhite;  //Boolean que indica si la foto está en blanco y negro (true) o no (false)
	BufferedImage image;	//Imagen cargada en un buffer
	
	/** Constructor que crea el objeto desde un fichero
	 * @param name
	 */
	public ImageRGB(String name) {
		try {
			this.name = new String(name);
			Image temp = ImageIO.read(new File(name));	//Se carga una awt.image
			this.image = (BufferedImage) temp;				// Se convierte a Buffered image
			
		} catch (IOException error) {
			error.printStackTrace();
		}
		this.blackAndWhite = new Boolean(this.isBandW());	// se mira si la imagen está en blanco y negro
	}
	
	/** Constructor que crea el objeto desde otra awt.Image
	 * @param image awt.Image
	 * @param name	String del nombre de la imagen
	 */
	public ImageRGB(Image image, String name) {
		this.name = new String(name);
		this.image = (BufferedImage) image;			// Se convierte en buffered image
		this.blackAndWhite = new Boolean(this.isBandW());	// se mira si la imagen está en blanco y negro
	}
	
	/** Funcion que comprueba que una imagen esté en blanco y negro
	 * @return
	 */
	public Boolean isBandW() {
		Boolean isBandW = new Boolean(true);
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
	    for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(image.getRGB(i, j));
	    		if(dummy.getBlue() != dummy.getRed() || dummy.getBlue() != dummy.getGreen()) {
	    			isBandW = false;
	    		}
	    	}
	    }
	    return isBandW;
	}
	
	/** Guarda en un fichero la imagen
	 * //TODO
	 * 
	 */
	public void saveImage() {
		
	}
	
	/** Metodo que dado una tabla de asignacion de valores a puntos modifique una imagen
	 * 
	 */
	void applyPointTransformation(ConversionTable tabla) {
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
	    for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(image.getRGB(i, j));
	    		dummy = new Color(tabla.getR(dummy.getRed()), tabla.getG(dummy.getGreen()), tabla.getB(dummy.getBlue()));
	    		image.setRGB(i, j, dummy.getRGB());
	    	}
	    }
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getBlackAndWhite() {
		return blackAndWhite;
	}

	public void setBlackAndWhite(Boolean blackAndWhite) {
		this.blackAndWhite = blackAndWhite;
	}	
}

/** Clase que implementa una tabla de asignacion de valores a los pixeles 
 * (la tabla que usamos para no aplicar las transformaciones mas veces de las necesarias)
 * @author Miguel
 *
 */
class ConversionTable {
	ArrayList<RGB> tabla = new ArrayList<RGB>();
	
	public ConversionTable() {
		for (int i = 0; i < 255; i++) {
			tabla.add(new RGB(0, 0, 0));
		}
	}
	
	public void setPos(int i, int r, int g, int b) {
		RGB temp = new RGB(r,g,b);
		this.tabla.set(i, temp);
	}
	
	public int getR(int i) {
		return tabla.get(i).getRed();
	}
	public int getG(int i) {
		return tabla.get(i).getBlue();
	}
	public int getB(int i) {
		return tabla.get(i).getGreen();
	}
}

/** Clase auxiliar para la de ConversionTable para almacenar un valor rgb
 * @author Miguel
 *
 */
class RGB{
	Integer red;
	Integer green;
	Integer blue;
	
	public RGB(Integer r, Integer g, Integer b) {
		red = new Integer(r);
		green = new Integer(g);
		blue = new Integer(b);
	}

	public Integer getRed() {
		return red;
	}

	public void setRed(Integer red) {
		this.red = red;
	}

	public Integer getGreen() {
		return green;
	}

	public void setGreen(Integer green) {
		this.green = green;
	}

	public Integer getBlue() {
		return blue;
	}

	public void setBlue(Integer blue) {
		this.blue = blue;
	}	
}
