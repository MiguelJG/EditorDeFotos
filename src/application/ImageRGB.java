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
	
	public BufferedImage getImage() {
		return image;
	}


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
			if(hist.get(i) > 0)
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
		for(int i = 255; i >=0 ; i--) {
			if(hist.get(i) > 0)
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
				//dummy.setRGB(colorPixel.getRed(), colorPixel.getGreen(), colorPixel.getBlue());
				dummy.setRGB(i-x1, j-y1, colorPixel.getRGB());
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
	
	public ImageRGB diferencias(BufferedImage image2) {
		Integer width = this.image.getWidth();
	    Integer height = this.image.getHeight();
	    Integer width2 = image2.getWidth();
	    Integer height2 = image2.getHeight();
	    if((width != width2) ||(height != height2)) {
	    	System.err.println("Error, Los imagenes tiene tama�o diferentes");
	    }
	    //variable para almacenar el nuevo imagen de salida 
	    BufferedImage dummy = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
	    
	    for(int i = 0; i < width ; i++) {
	    	for(int j = 0; j < height ; j++) {
	    		Color color_image2 = new Color(image2.getRGB(i,j));
	    		Color color_image1 = new Color(this.image.getRGB(i,j));
	    		//Resta de valor absoluto
	    		int diferencia_roja = Math.abs(color_image1.getRed() - color_image2.getRed()); 
	    		int diferencia_verde = Math.abs(color_image1.getGreen() - color_image2.getGreen());
	    		int diferencia_azul = Math.abs(color_image1.getBlue() - color_image2.getBlue());
	    		
	    		//setter de la imagen
	    		dummy.setRGB(i, j, new Color(diferencia_roja,diferencia_verde,diferencia_azul).getRGB());
	    	}
	    }
	    //
	    return new ImageRGB(dummy);
	}


	/** Ajusta linealmente el brillo y el contraste de la imagen a los valores dados (suponiendo una imagen en BYN)
	 * @param brillo
	 * @param contraste
	 */
	public void ajustar_brillo(Double brillo, Double contraste) {
	    ConversionTable table = new ConversionTable();
	    
	    Double contrasteV = getContraste(getHistRed());
	    Double brilloV = getBrillo(getHistRed());
	    
	    Double a = contraste/contrasteV;
	    Double b = ((brillo*contrasteV) - (contraste*brilloV))/contrasteV;
	    
	    for(int i = 0; i < table.tabla.size() ; i++) {
	    		// Vout = A* Vin + B
	    		Double v_out = a * i + b;
	    		if(v_out>255)
	    			v_out=(double) 255;
	    		table.setPos(i, v_out.intValue(), v_out.intValue(), v_out.intValue());
	    		/*como meter elemento en la talba?*/
	    		
	    }
	    this.applyPointTransformation(table);
	}
	
	/** Metodo que aplica sobre la im�gen una correccion gamma
	 * @param gamma
	 */
	public void ajusteGamma(Double gamma) {
		ConversionTable table = new ConversionTable();
		for(int i = 0; i < table.tabla.size() ; i++) {
			Double valor = new Double(0);
			try {
				valor = Funciones.exponencial(new Double((double)i / 255), gamma);
			} catch (Error err) {
				System.out.println(err);
			}
    		int valorInt = new Double ((double)valor * 255).intValue();
    		table.setPos(i, valorInt, valorInt, valorInt);    		
    	}
		System.out.println("Transformacion :" + table.tabla.toString());
		this.applyPointTransformation(table);
	}
	
	
	/** Esta funcion reescala la a un nuevo tamaño pasado por parámetro usando la seleccion del veciono más próximo
	 * @param newWidth
	 * @param newHeigth
	 */
	public ImageRGB escaladoVecinoProximo(int newWidth, int newHeigth) {
		double facW =  newWidth / this.image.getWidth(); //FACTOR DE ESCALADO DE LA ANCHURA
		double facH =  newHeigth / this.image.getHeight(); //FACTOR DE ESCALADO DE LA ALTURA
		BufferedImage EscImage = new BufferedImage(newWidth, newHeigth, BufferedImage.TYPE_INT_RGB); 		
	    for(int i = 0; i < EscImage.getWidth(); i++) {
	    	for(int j = 0; j < EscImage.getHeight(); j++) {
	    		double x =  Math.round(i / facW);
	    		double y = Math.round(j / facH);
	    		Color dummy = new Color(image.getRGB((int)x, (int)y));	
	    		EscImage.setRGB(i, j, dummy.getRGB());
	    	}
	    }
	    return new ImageRGB(EscImage);
	}
	
	
	/** Esta funcion reescala la a un nuevo tamaño pasado por parámetro usandouna interpolacion de los colores a usar
	 * @param newWidth
	 * @param newHeigth
	 */
	public ImageRGB escaladoInterpolacion(int newWidth, int newHeigth) {
		double facW =  newWidth / this.image.getWidth(); //FACTOR DE ESCALADO DE LA ANCHURA
		double facH =  newHeigth / this.image.getHeight(); //FACTOR DE ESCALADO DE LA ALTURA
		BufferedImage EscImage = new BufferedImage(newWidth, newHeigth, BufferedImage.TYPE_INT_RGB); 		
	    for(int i = 0; i < EscImage.getWidth(); i++) {
	    	for(int j = 0; j < EscImage.getHeight(); j++) {
	    		double x =  i / facW; // valor
	    		double y = j / facH;
	    		double X =  (int)(i / facW); // truncamos la operacion para obtener los valores x e y para calculas p y q
	    		double Y = (int)(j / facH);
	    		double p = x - X;	//se calculan la p y la q
	    		double q = y - Y;
	    		Color A = new Color(image.getRGB((int)X, (int)Y + 1)); //Se realizan las operaciones para ver que color es
	    		Color B = new Color(image.getRGB((int)X + 1, (int)Y + 1));	
	    		Color C = new Color(image.getRGB((int)X, (int)Y));	
	    		Color D = new Color(image.getRGB((int)X + 1, (int)Y));	
	    		// C+(D-C)p+(A-C)q+(B+C-A-D)pq	    		
	    		Color CDCp = colorAddition(C, colorScale(colorSub(D, C), p));	
	    		Color ACq = colorScale(colorSub(A, C), q);
	    		Color BCADpq = colorScale(colorScale(colorAddition(B, colorSub(C, colorSub(A, D))), p), q);
	    		Color dummy = colorAddition(colorAddition(CDCp, ACq), BCADpq);
	    		EscImage.setRGB(i, j, dummy.getRGB());
	    	}
	    }
	    return new ImageRGB(EscImage);
	}
	
	private static Color colorScale(Color c, double scale) {
	    int r = Math.min(255, (int) (c.getRed() * scale));
	    int g = Math.min(255, (int) (c.getGreen() * scale));
	    int b = Math.min(255, (int) (c.getBlue() * scale));
	    return new Color(r,g,b);
	}
	
	private static Color colorAddition(Color c1, Color c2) {
	    int r = Math.min(255, c1.getRed() + c2.getRed());
	    int g = Math.min(255, c1.getGreen() + c2.getGreen());
	    int b = Math.min(255, c1.getBlue() + c2.getBlue());
	    return new Color(r,g,b);
	}
	
	private static Color colorSub(Color c1, Color c2) {
	    int r = Math.max(0, c1.getRed() - c2.getRed());
	    int g = Math.max(0, c1.getGreen() - c2.getGreen());
	    int b = Math.max(0, c1.getBlue() - c2.getBlue());
	    return new Color(r,g,b);
	}
	
	/** Este metodo retorna la imagen espejo Horizontal
	 * @return
	 */
	public ImageRGB escpejoHorizontal() {
		BufferedImage EscImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB); 		
	    for(int i = 0; i < EscImage.getWidth(); i++) {
	    	for(int j = 0; j < EscImage.getHeight(); j++) {
	    		Color dummy = new Color(image.getRGB(image.getWidth() - 1 - i, j));	
	    		EscImage.setRGB(i, j, dummy.getRGB());
	    	}
	    }
	    return new ImageRGB(EscImage);
	}
	
	
	/** Este metodo retorna la imagen espejo vertical
	 * @return
	 */
	public ImageRGB escpejoVertical() {
		BufferedImage EscImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB); 		
	    for(int i = 0; i < EscImage.getWidth(); i++) {
	    	for(int j = 0; j < EscImage.getHeight(); j++) {
	    		Color dummy = new Color(image.getRGB(i, image.getHeight() - 1 - j));	
	    		EscImage.setRGB(i, j, dummy.getRGB());
	    	}
	    }
	    return new ImageRGB(EscImage);
	}
	
	/** Este método retorna la transpuesta de una imagen
	 * @return
	 */
	public ImageRGB traspuesta() {
		BufferedImage EscImage = new BufferedImage(image.getHeight(), image.getWidth(), BufferedImage.TYPE_INT_RGB); 
		for(int i = 0; i < EscImage.getWidth(); i++) {
	    	for(int j = 0; j < EscImage.getHeight(); j++) {
	    		Color dummy = new Color(image.getRGB(j,i));	
	    		EscImage.setRGB(i, j, dummy.getRGB());
	    	}
	    }
		return new ImageRGB(EscImage);
	}
	
	public ImageRGB rotation(BufferedImage imagen,int grado) {

        double angulo = Math.toRadians(grado); //parsedouble convertir el int en double
        double sin = Math.sin(angulo);
        double cos = Math.cos(angulo);
        int w = this.image.getWidth();
        int h = this.image.getHeight();
        //https://stackoverflow.com/questions/5789239/calculate-largest-rectangle-in-a-rotated-rectangle
        //
        //Represents an image with 8-bit RGBA color components packed into integer pixels.
        //"deformar" los gráficos -> AffineTransform
        //AffineTransform affi = new AffineTransform();
        double x_rotate = 0.5 * (w - 1);
        double h_rotate = 0.5 * (h - 1);        
        int newW = (int) Math.floor(w * cos - h * sin + x_rotate);
        int newH = (int) Math.floor(h * cos + w * sin + h_rotate);
        BufferedImage imangen_rotado = new BufferedImage(newW , newH ,BufferedImage.TYPE_INT_ARGB);        
        for(int i=0 ; i < newW; i++) {
            for(int j=0 ; j < newH; j++) {
                double a = i - x_rotate;
                double b = j - h_rotate;
                int newx = (int) Math.floor(a * cos - h * sin + x_rotate);
                int newy = (int) Math.floor(b * cos + w * sin + h_rotate);
                if(newx >= 0 && newx < newW && newy < newH && newy >= 0) {
                	Color colorPixel = new Color(this.image.getRGB(newx, newy));
                 	imangen_rotado.setRGB(newx, newy, colorPixel.getRGB());
                } else {
                	imangen_rotado.setRGB(newx, newy, Color.BLACK.getRGB());
                }
             }
          }
         return new ImageRGB(imangen_rotado);
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
	
	public static Double exponencial(Double base, Double potencia) {
		if(potencia < 0) {
			throw new Error("Exponente menor que 0");
		}
		return Math.pow(base, potencia);
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
