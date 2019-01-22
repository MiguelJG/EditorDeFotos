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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GetTextW implements Initializable{

	@FXML
	private TextArea puntos;
	
	@FXML
	Button coger;
	
	Image im;
	
	String str;
	
	int opcion;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void cargar(Image image, String string, int choice) {
		
		im = image;
		str = string;
		opcion = choice;
		
	}
	
	public void cogerPuntos() throws IOException {
		
		ImageRGB dummy = new ImageRGB(im, str);
		
		String[] puntos =  this.puntos.getText().split("-");
		
		switch(opcion) {
			case 1: dummy.umbralizar(Integer.parseInt(puntos[0]), Integer.parseInt(puntos[1]),  Integer.parseInt(puntos[2]));
					break;
			case 2: dummy.ajustar_brillo(Double.parseDouble(puntos[0]), Double.parseDouble(puntos[1]));
					break;
			case 3: dummy.ajusteGamma(Double.parseDouble(puntos[0]));
					break;
			case 4: dummy.escaladoInterpolacion(Integer.parseInt(puntos[0]), Integer.parseInt(puntos[1]));
					break;
			case 5: dummy.escaladoVecinoProximo(Integer.parseInt(puntos[0]), Integer.parseInt(puntos[1]));
					break;
			case 6: dummy.rotation(Integer.parseInt(puntos[0]));
					break;
		}
		
		
		
		
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Image_view.fxml"));
		Parent root = loader.load();
		ControladorImage con = loader.getController();
		con.cargaImagen(dummy.getImageFX(), dummy.getTipo());
		ImageControl.addimage(con);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		Stage cerrar = (Stage) coger.getScene().getWindow();
		cerrar.close();
		
		
	}
	
}
