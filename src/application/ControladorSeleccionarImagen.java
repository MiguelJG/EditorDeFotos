package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControladorSeleccionarImagen implements Initializable{
	
	int opcion;
	
	ImageRGB im;
	
	@FXML
	Button coger;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void cargar(int choice, ImageRGB image) {
		
		opcion = choice;
		im = image;
		
	}

	public void cogerImagen() throws IOException {
		ImageRGB image2 = null ;
		for(ControladorImage cn : ImageControl.getImages()) {
			if(cn.select()) {
				image2 = new ImageRGB(cn.getIm(), "gif");
				ImageControl.deseleccionar();
				break;
			}
		}
		
		switch(opcion) {
			case 1: crearVentana(im.diferencias(image2.getImage()));
					break;
			case 2: ImageRGB dummy = new ImageRGB(im.getImageFX(), im.getTipo());
					dummy.transformacionEspHistograma(dummy.getHistAcum(dummy.getHistRed()), image2.getHistAcum(image2.getHistRed()));
					crearVentana(dummy);
					break;
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
		
		Stage cerrar = (Stage) coger.getScene().getWindow();
		cerrar.close();
	}
	
	


}
