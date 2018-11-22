package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PorPuntos implements Initializable{

	@FXML
	private TextArea puntos;
	
	Image im;
	
	String str;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void cargar(Image image, String string) {
		
		im = image;
		str = string;
		
	}
	
	public void cogerPuntos() throws IOException {
		
		
		ImageRGB dummy = new ImageRGB(im, str);
		dummy.transformacionLinealPorTramo(puntos.getText());
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Image_view.fxml"));
		Parent root = loader.load();
		ControladorImage con = loader.getController();
		con.cargaImagen(dummy.getImageFX(), dummy.getTipo());
		ImageControl.addimage(con);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
		
	}
	
	
	

}
