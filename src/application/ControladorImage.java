package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ControladorImage implements Initializable{

	private Image im;
	
	@FXML
	private ImageView imagen;
	@FXML
	private CheckBox cb;
	@FXML
	private Label lb;
	
	private ImageRGB imageAWT;
	
	private String tipo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	public void moverRaton(MouseEvent event) {
		
		lb.setText("PosX: " + (int) event.getX() + " PosY: " + (int) event.getY());
		
	}
	
	public void cargaImagen(Image imag, String tipo) {
		this.tipo = tipo;
		im = imag;
		imageAWT = new ImageRGB(im, tipo);
		imagen.setImage(imag);
	}
	
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public boolean select() {
		return cb.isSelected();
	}

	
	public ImageRGB getImageAWT() {
		return imageAWT;
	}


	public void setImageAWT(ImageRGB imageAWT) {
		this.imageAWT = imageAWT;
	}


	public void setOff() {
		this.cb.setSelected(false);;
	}
	

	public Image getIm() {
		return im;
	}


	public void setIm(Image im) {
		this.im = im;
	}
	
	
}
