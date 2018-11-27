package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
	
	private Boolean roi;
	
	private int rx1;
	private int ry1;
	private int rx2;
	private int ry2;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		roi = false;
		
	}
	
	public void moverRaton(MouseEvent event) {
		
		lb.setText("PosX: " + (int) event.getX() + " PosY: " + (int) event.getY() + " Color: "+ imageAWT.pixel((int)event.getX(), (int)event.getY()));
		
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
	
	public void activarRoi() {
		roi = true;
	}
	
	public void roiP(MouseEvent event) {
		
		rx1 = (int) event.getX();
		ry1 = (int) event.getY();
		
	}
	
	public void roiR(MouseEvent event) throws IOException {
	
		rx2 = (int) event.getX();
		ry2 = (int) event.getY();
		if(roi) {
			ImageRGB dummy = new ImageRGB(imageAWT.getSubImage(rx1, ry1, rx2, ry2).getImageFX(), imageAWT.getTipo());
			crearVentana(dummy);
			roi = false;
		}
		
	}
	
	public void crearVentana(ImageRGB ima) throws IOException {
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Image_view.fxml"));
		Parent root = loader.load();
		ControladorImage con = loader.getController();
		con.cargaImagen(ima.getImageFX(), "gif");
		ImageControl.addimage(con);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	
}
