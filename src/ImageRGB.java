import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

/** Esta clase consiste en la representacion que le damos a una imagen. Con este estandar podemos simplificar las operaciones a un solo formato
 * y simplificar la adicion de nuevas utilidades
 * @author Miguel Jim�nez Gomis
 * @date 26/10/2018
 *
 */
public class ImageRGB extends JFrame{
	String name;			//String que almacena el PATH a la imagen que queremos modificar
	Boolean blackAndWhite;  //Boolean que indica si la foto est� en blanco y negro (true) o no (false)
	BufferedImage image;	//Imagen cargada en un buffer
	
	
	JLabel etiqueta;
	
	/** Constructor que crea el objeto desde un fichero
	 * @param name
	 */
	public ImageRGB() {
		super("Spaghetti Code");
		setIconImage(new ImageIcon("A:\\Users\\Pablo\\EditorDeFotos\\Ficheros\\Logo.png").getImage());
		try {
			
			Image temp = ImageIO.read(abrirArchivo());	//Se carga una awt.image
			this.image = (BufferedImage) temp;				// Se convierte a Buffered image
			
		} catch (IOException error) {
			error.printStackTrace();
		}
		this.blackAndWhite = new Boolean(this.isBandW());	// se mira si la imagen est� en blanco y negro
		etiqueta = new JLabel(new ImageIcon(image));
		
		 
		getContentPane().add(etiqueta);
 
		this.setSize(500, 500);
	}
	
	
	private File abrirArchivo() {
		  
		  JFileChooser chooser = new JFileChooser();
		  
		  FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & GIF Images", "jpg", "gif", "raw");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       return chooser.getSelectedFile();
		    }
			return null;


		    
	  
		}
	
	/** Constructor que crea el objeto desde otra awt.Image
	 * @param image awt.Image
	 * @param name	String del nombre de la imagen
	 */
	public ImageRGB(Image image, String name) {
		super("Spaghetti Code");
		this.name = new String(name);
		this.image = (BufferedImage) image;			// Se convierte en buffered image
		this.blackAndWhite = new Boolean(this.isBandW());	// se mira si la imagen est� en blanco y negro
		etiqueta = new JLabel(new ImageIcon(image));
		getContentPane().add(etiqueta);
		 
		this.setSize(500, 500);
	}
	
	
	/** Funcion que comprueba que una imagen est� en blanco y negro
	 * @return
	 */
	
	
	public Boolean isBandW() {
		Boolean isBandW = new Boolean(true);
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
	    isBandW = true;
	    for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(image.getRGB(i, j));
	    		if(dummy.getBlue() != dummy.getRed() || dummy.getBlue() != dummy.getGreen()) {
	    			isBandW = false;
	    		}
	    	}
	    }
	    this.blackAndWhite = isBandW; // se actualiza el estado de la imagen
	    return isBandW;
	}
	
	
	
	
	/** Guarda en un fichero la imagen
	 * //TODO
	 * 
	 */
	public void saveImage(String fichero) {
		try{
			//	this.image = new BufferedImage(255, 255, BufferedImage.TYPE_INT_RGB )// TYPE_INT_RGB representa color en int rojo 16-23, verde 8-15 and azul in 0-7
				 File f = new File(fichero); //  no se si el asterisco es cualquier extension
				
	           ImageIO.write(this.image, "PNG", f);
	           ImageIO.write(this.image, "JPG", f);
	           ImageIO.write(this.image, "RAW", f);
			}
			 catch (IOException e) {
	        	e.printStackTrace();
	        }
	}
	
	/** Metodo que dado una tabla de asignacion de valores a puntos modifique una imagen
	 * 
	 */
	void applyPointTransformation(ConversionTable tabla) {
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
	    for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(this.image.getRGB(i, j));
	    		dummy = new Color(tabla.getR(dummy.getRed()), tabla.getG(dummy.getGreen()), tabla.getB(dummy.getBlue()));
	    		image.setRGB(i, j, dummy.getRGB());
	    	}
	    }
	}
	
	/**Este método transforma una imagen a blanco y negro usando la conversión NTSC
	 * 
	 */
	void transformToBlackAndWhite() {
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
		for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(this.image.getRGB(i, j));
	    		//NTSC -> 0.299 R + 0.587 G + 0.114 B
	    		int grey = new Double(0.299 * dummy.getRed() + 0.587 * dummy.getGreen() + 0.114 * dummy.getBlue()).intValue();
	    		dummy = new Color(grey, grey, grey);
	    		this.image.setRGB(i, j, dummy.getRGB());
	    	}
		}
		this.isBandW();
	}
	
	/** Retorna una lista con la cantidades de pixeles que tienen dicho tono para el canal rojo
	 * @return
	 */
	public ArrayList<Long> getHistRed(){
		ArrayList<Long> hist = new ArrayList<Long>();
		for (int i = 0; i <= 255; i++) {
			  hist.add(new Long(0));
		}
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
		for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(this.image.getRGB(i, j));
	    		Long temp = hist.get(dummy.getRed());
	    		temp +=1;
	    		hist.set(dummy.getRed(), temp);
	    	}
		}
		return hist;
	}
	
	/** Retorna una lista con la cantidades de pixeles que tienen dicho tono para el canal verde
	 * @return
	 */
	public ArrayList<Long> getHistGreen(){
		ArrayList<Long> hist = new ArrayList<Long>();
		for (int i = 0; i < 255; i++) {
			  hist.add(new Long(0));
		}
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
		for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(this.image.getRGB(i, j));
	    		Long temp = hist.get(dummy.getGreen());
	    		temp +=1;
	    		hist.set(dummy.getRed(), temp);
	    	}
		}
		return hist;
	}
	
	/** Calcula el contraste para el histograma de un canal dado (es decir, su media)
	 * @param hist
	 * @return
	 */
	public Double getBrillo(ArrayList<Long> hist) {
		Long dummy = new Long(0);
		for(int i = 0; i < hist.size(); ++i) {
			dummy += i * hist.get(i);
		}
		return  dummy.doubleValue() / (this.image.getHeight() * this.image.getWidth());
	}
	
	/** Retorna el contraste para un canal dado (es decir su desviación tipica)
	 * @param hist
	 * @return
	 */
	public Double getContraste(ArrayList<Long> hist) {
		Double media = this.getBrillo(hist);
		Double varianza = new Double(0);
		for(int i = 0; i < hist.size(); ++i) {
			varianza += (i * i) * hist.get(i).doubleValue(); // x^2*fx
		}
		varianza = varianza / (this.image.getHeight() * this.image.getWidth());
		varianza = varianza - (media * media);
		return Math.sqrt(varianza); //la raiz cuadrada de la varianza es la desviación tipica
	}
	
	/** Retorna la entropía de un canal dado
	 * @param hist
	 * @return
	 */
	public Double getEntropia(ArrayList<Long> hist) { // H = ∑ pi*log(1/pi) = -∑ pi log(pi)
		Double entropia = new Double(0);
		for(int i = 0; i < hist.size(); ++i) {
			entropia += -((i * hist.get(i).doubleValue()) * Math.log(i));
		}
		return entropia;
	}
	
	/** Retorna una lista con la cantidades de pixeles que tienen dicho tono para el canal azul
	 * @return
	 */
	public ArrayList<Long> getHistBlue(){
		ArrayList<Long> hist = new ArrayList<Long>();
		for (int i = 0; i < 255; i++) {
			  hist.add(new Long(0));
		}
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
		for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(this.image.getRGB(i, j));
	    		Long temp = hist.get(dummy.getBlue());
	    		temp +=1;
	    		hist.set(dummy.getRed(), temp);
	    	}
		}
		return hist;
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
