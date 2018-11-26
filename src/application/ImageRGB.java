package application;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.ArrayList;

/** Esta clase consiste en la representacion que le damos a una imagen. Con este estandar podemos simplificar las operaciones a un solo formato
 * y simplificar la adicion de nuevas utilidades
 * @author Miguel Jim�nez Gomis
 * @date 26/10/2018
 *
 */
public class ImageRGB{
	private Boolean blackAndWhite;  //Boolean que indica si la foto est� en blanco y negro (true) o no (false)
	private BufferedImage image;	//Imagen cargada en un buffer
	private String tipo = new String();	
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/** Constructor que crea el objeto desde un fichero
	 * @param name
	 */
	public ImageRGB() {		
	}
	
	/** Retorna el valor maximo de un histograma dado
	 * @param hist
	 * @return
	 */
	public int max(ArrayList<Long> hist) {		
		int max = 0;
		for(int i = 0; i <= 255 ; i++) {
			if(hist.get(i) >= hist.get(max))
				max = i;
		}		
		return max;		
	}
	
	/** Retorna el valor minimo de un histograma dado
	 * @param hist
	 * @return
	 */
	public int min(ArrayList<Long> hist) {
		int min = 0;
		for(int i = 0; i <= 255 ; i++) {
			if(hist.get(i) <= hist.get(min))
				min = i;
		}		
		return min;
	}
	
	/** Convierte la imagenAWT en una imagenFX
	 * @return
	 */
	public WritableImage getImageFX() {
		return SwingFXUtils.toFXImage(image, null);
	}

	/** Constructor que crea el objeto desde otra awt.Image
	 * @param image awt.Image
	 * @param name	String del nombre de la imagen
	 */
	public ImageRGB(Image image) {		
		this.image = (BufferedImage) image;			// Se convierte en buffered image
		this.blackAndWhite = new Boolean(this.isBandW());	// se mira si la imagen est� en blanco y negro		
	}
	
	/** Constructor que crea el objeto desde una FXImage
	 * @param image FXImage
	 * @param name	String del nombre de la imagen
	 */
	public ImageRGB(javafx.scene.image.Image image, String tipo) {
		this.tipo = tipo;
		this.image = SwingFXUtils.fromFXImage(image,null);			// Se convierte en buffered image
		this.blackAndWhite = new Boolean(this.isBandW());	// se mira si la imagen est� en blanco y negro		
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
	    return isBandW;
	}
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
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
	}

	
	/** Retorna el histograma del canal azul de la imagen
	 * @return
	 */
	public ArrayList<Long> getHistBlue(){
		ArrayList<Long> hist = new ArrayList<Long>();
		for (int i = 0; i <= 255; i++) {
			  hist.add(new Long(0));
		}
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
		for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(this.image.getRGB(i, j));
	    		Long temp = hist.get(dummy.getBlue());
	    		temp +=1;
	    		hist.set(dummy.getBlue(), temp);
	    	}
		}
		return hist;
	}

	/** Retorna el histograma del canal verde de la imagen
	 * @return
	 */
	public ArrayList<Long> getHistGreen(){
		ArrayList<Long> hist = new ArrayList<Long>();
		for (int i = 0; i <= 255; i++) {
			  hist.add(new Long(0));
		}
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
		for(int i = 0; i < width; i++) {
	    	for(int j = 0; j < height; j++) {
	    		Color dummy = new Color(this.image.getRGB(i, j));
	    		Long temp = hist.get(dummy.getGreen());
	    		temp +=1;
	    		hist.set(dummy.getGreen(), temp);
	    	}
		}
		return hist;
	}
	
	
	/** Retorna el histograma del canal rojo de la imagen
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
	
	/** Este metodo retorna el histograma acumulado de un histograma dado
	 * @param hist
	 * @return
	 */
	public ArrayList<Long> getHistAcum(ArrayList<Long> hist){
		ArrayList<Long> acum = new ArrayList<Long>();
		for (int i = 0; i <= 255; i++) {
			  acum.add(new Long(0));
		}
		acum.set(0,hist.get(0));
		for(int i = 1; i <= 255; i++) {
		//	System.out.println(hist.get(i - 1));
			acum.set(i, hist.get(i) + acum.get(i - 1));
		}
		return acum;
	}
	
	
	/** Este metodo retorna el brillo de un histograma dados
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
	
	
	/** Este metodo retorna el contraste de un histograma dado
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
	
	/** Retorna la entropia de un histograma dado
	 * @param hist
	 * @return
	 */
	public Double getEntropia(ArrayList<Long> hist) {
		//H = ∑ pi log(1/pi) = -∑ pi log(pi)
		Double entropia = new Double(0);
		for(int i = 0; i < hist.size(); i ++) {
			Double probabilidad = new Double(new Double(hist.get(i)) / this.tamanio());
			//para log en base 2 (Math.log(num) / Math.log(2))
			if(probabilidad != 0)
				entropia += probabilidad * (Math.log(1 / probabilidad) / Math.log(2));
		}
		return entropia;
	}
	
	public Boolean getBlackAndWhite() {
		return blackAndWhite;
	}

	public void setBlackAndWhite(Boolean blackAndWhite) {
		this.blackAndWhite = blackAndWhite;
	}	
	
	
	
	/** Esta funcion recibe un string de puntos y numero de intervalos y aplica una transformacion lineal por tramos con dichos puntos
	 * @param puntos
	 */
	public void transformacionLinealPorTramo(String inPuntos) {
		String[] dummy = inPuntos.split("-");
		int numTramos = Integer.parseInt(dummy[0]);
		if(numTramos < 0 || numTramos >= 255) {
			throw(new Error("Numero de tramos no valido en operacion de transformación lineal por puntos"));
		}
		if(dummy.length - 1 < (numTramos + 1) * 2) {
			throw(new Error("Numero de puntos introducidos para definir el numero de tramos no valido"));
		}
		Integer puntos[] = new Integer[dummy.length - 1];
		for(int i = 1; i < dummy.length; i++) { //array de puntos ya formateados
			puntos[i - 1] = Integer.parseInt(dummy[i]);
		}
		ConversionTable tabla = new ConversionTable();
		for(int i = 0; i < numTramos; i ++) {
			for(int j = puntos[i*2]; j <= puntos[i * 2 +2]; j++) {
				int value = 0;
				try {
					value = Funciones.fX(j, puntos[i*2], puntos[i*2 + 1], puntos[i*2 + 2], puntos[i*2 + 3]);
				} catch(Error err) {
					System.out.println(err);
				}
				tabla.setPos(j, value, value, value); // se ponen todos los valores al mismo ya que no estamos trabajando con imágenes a color
			}
		}
		this.applyPointTransformation(tabla);
	}
	
	
	
	/** Dados 2 histogramas aplica sobre la imagen
	 * @param histInit histograma inicial de la imagen
	 * @param histTarget histograma final de la im�gen
	 */
	void transformacionEspHistograma(ArrayList<Long> histInit, ArrayList<Long> histTarget) {
		ConversionTable tabla = new ConversionTable();
		for(int i = 0; i < histInit.size(); i++) {
			for(int j = 0; j < histTarget.size(); j++) {				
				if(j + 1 < histTarget.size()) {					
					if(histInit.get(i) < histTarget.get(j + 1)) {
						tabla.setPos(i, j, j, j);
						break;
					}
				} else {
					tabla.setPos(i, j, j, j);
				}
			}
		}
		this.applyPointTransformation(tabla);
	}
	
	/** Ecualiza el histograma a una recta con origen 0,0 y fin en 255,255
	 * 
	 */
	void equalizarHistograma() {
		ArrayList<Long> histTarget = new ArrayList<Long>();
		for(Long i = new Long(0); i < 256; i++) {
			histTarget.add(i * this.tamanio() / 255); // se crea el histograma objetivo ya equalizado
		}
		this.transformacionEspHistograma(this.getHistAcum(this.getHistRed()), histTarget);// se aplica sobre el histograma del rojo suponiendo que la im�gen es en blanco y negro
	}
	
	/** Retorna el numero de pixeles de la imagen
	 * @return
	 */
	public Long tamanio() {
		return new Long(this.image.getHeight() * this.image.getWidth());
	}
	
	/** Retorna el color del pixel indicado en formato de cadena
	 * @param x
	 * @param y
	 * @return
	 */
	public String pixel(int x, int y) {
		Color dummy = new Color(this.image.getRGB(x, y));
		if(this.isBandW()) {
			return new Integer(dummy.getRed()).toString();
		} else {
			return new Integer(dummy.getRed()).toString() + " " + new Integer(dummy.getGreen()).toString() + " " + new Integer(dummy.getBlue()).toString();
		}
	}
	
	/** Retorna la subimagen comprendida en el rectangulo indicado por los puntos superior izquierdo e inferior derecho
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public ImageRGB getSubImage(int x1, int y1, int x2, int y2) {
		int WIDTH = x2 - x1 + 1;
		int HEIGHT = y2 - y1 + 1;
		BufferedImage dummy = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		for(int i = x1; i <= x2; i++) {
			for(int j = y1; j <= y2; j++) {
				Color colorPixel = new Color(this.image.getRGB(i, j)); 
				dummy.setRGB(colorPixel.getRed(), colorPixel.getGreen(), colorPixel.getBlue());
			}
		}
		return new ImageRGB(dummy);
	}
	
	/** Binariza la imagen a los 2 colores dados  con respecto al umbral seleccionado
	 * @param color1
	 * @param umbral
	 * @param color2
	 */
	public void umbralizar(int color1, int umbral, int color2) {
		String tramos = new String();
		tramos += "3-0-" + color1 + "-" + umbral + "-" + color1 + "-" + (umbral+1) + "-" + color2 + "-255-" + color2;
		this.transformacionLinealPorTramo(tramos);
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
		for (int i = 0; i <= 255; i++) {
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


/**Esta clase encapsula funciones estaticas para hacer calculos sobre rectas dados 2 puntos
*
*/
class Funciones{		
	/** Retorna el valor de la Y para un punto X sobre la recta formada por los puntos x1,y1/x2,y2
	 * @param x
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static Integer fX(Integer x, Integer x1, Integer y1, Integer x2, Integer y2){
		if((x1 == 0 && x2 == 0)) {
			throw new Error("Valores de los puntos de la funcion incorrectos");
		}
		return Math.round  ((((x - x1) * (y2 - y1))/(x2 - x1)) + y1);
	}
	
	/** Retorna el valor de la x para un punto Y sobre la recta formada por los puntos x1,y1/x2,y2
	 * @param y
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static Integer fY(Integer y, Integer x1, Integer y1, Integer x2, Integer y2){
		if((y1 == 0 && y2 == 0)) {
			throw new Error("Valores de los puntos de la funcion incorrectos");
		}
		return Math.round  ((((y - y1) * (x2 - x1))/(y2 - y1)) + x1);
	}
	
	/** Dados 2 puntos x1,y1/x2,y2 retorna la pendiente de la recta que conforman
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static Double pendiente(Integer x1, Integer y1, Integer x2, Integer y2) {
		return new Double(y2 - y1)/new Double(x2 - x1);
	}
	
}
