package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

public class ImageControl {

	static List<ControladorImage> images = new ArrayList<ControladorImage>();
	
	
	public static void addimage(ControladorImage con) {
		images.add(con);
	}
	
	public ImageControl() {
		
	}
	
	
	public void chargeImage(ActionEvent event) throws IOException {
		
		String dir = abrirArchivo().toURI().toString();
		String[] dummy = dir.split(",");
		String dummy2 = "";
		for(int i = 0; i < dummy.length; i++)
			if(dummy[i].equals("gif") || dummy[i].equals("raw") || dummy[i].equals("jpg")) {
				dummy2 = dummy[i];
			}
		
		crearVentanaImagen(new Image(dir), dummy2);
	}
	
	
	
	
	private void crearVentanaImagen(Image im, String tipo) throws IOException {
		
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Image_view.fxml"));
		Parent root = loader.load();
		ControladorImage con = loader.getController();
		con.cargaImagen(im, tipo);
		images.add(con);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
	}
	
	
	public void deseleccionar() {
		
		for(ControladorImage cn: images) {
			if(cn.select()) {
				cn.setOff();
				
			}
		}
		
	}
	
	public void crearPorPuntos() throws IOException {
		
		
		for(ControladorImage cn: images) {
			if(cn.select()) {
				dummy(cn.getImageAWT());
				deseleccionar();
				break;
				
				
				
			}
		}
		
	}
	
	public void dummy(ImageRGB cn) throws IOException {
		FXMLLoader loader =new FXMLLoader(getClass().getResource("PorPuntosTramos.fxml"));
		Parent root = loader.load();
		PorPuntos con = loader.getController();
		con.cargar(cn.getImageFX(),cn.getTipo());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}
	

	
	private void crearVentanaHistograma(ImageRGB im) throws IOException {
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Histograma.fxml"));
		Parent root = loader.load();
		ControladorHistograma con = loader.getController();
		con.cargaHisto(im);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	private void crearVentanaHistogramaAcumulado(ImageRGB im) throws IOException {
		FXMLLoader loader =new FXMLLoader(getClass().getResource("HistogramaAcumulado.fxml"));
		Parent root = loader.load();
		ControladorHistograma con = loader.getController();
		con.cargaHisto(im);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public void duplicar() throws IOException {
		
		for(ControladorImage cn : images) {
			if(cn.select()) {
				crearVentanaImagen(cn.getIm(), cn.getTipo());
				break;
			}
		}
		deseleccionar();
	}
	
	public void histograma() throws IOException {
		
		for(ControladorImage cn : images) {
			if(cn.select()) {
				crearVentanaHistograma(cn.getImageAWT());
				break;
			}
		}
		deseleccionar();
		
	}
	public void histogramaAcumulado() throws IOException {
		
		for(ControladorImage cn : images) {
			if(cn.select()) {
				crearVentanaHistogramaAcumulado(cn.getImageAWT());
				break;
			}
		}
		deseleccionar();
		
	}
	
	public void ecualizar() throws IOException {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				ImageRGB dummy = new ImageRGB(cn.getIm(), cn.getTipo());
				dummy.equalizarHistograma();
				crearVentanaImagen(dummy.getImageFX(), dummy.getTipo());
				break;
			}
		}
		deseleccionar();
	}
	
	
	
	public void blancoNegro(ActionEvent event) throws IOException {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				ImageRGB dummy = new ImageRGB(cn.getIm(), cn.getTipo());
				dummy.transformToBlackAndWhite();
				crearVentanaImagen(dummy.getImageFX(), dummy.getTipo());
				break;
			}
		}
		deseleccionar();
	}
	
	private File abrirArchivo() {
		  
		  JFileChooser chooser = new JFileChooser();
		  
		  FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "PNG & GIF & RAW Images", "pnf", "gif", "raw");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {

		       return chooser.getSelectedFile();
		    }
			return null;


		}
	
	
	
}