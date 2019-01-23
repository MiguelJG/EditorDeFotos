package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImageControl {

	static List<ControladorImage> images = new ArrayList<ControladorImage>();
	
	
	public static void addimage(ControladorImage con) {
		images.add(con);
	}
	
	public ImageControl() {
	}
	
	public static void deseleccionar() {
		for(ControladorImage cn: images) {
			if(cn.select()) {
				cn.setOff();
			}
		}
	}
	
	//Botones業業業業業業業業業業業業業業業業業業業業業業業業業業業業業業業業業業業v
	
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
	
	public void crearPorPuntos() throws IOException {
		
		
		for(ControladorImage cn: images) {
			if(cn.select()) {
				PorPuntos(cn.getImageAWT());
				deseleccionar();
				break;
				
			}
		}
		
	}
	public void diferencia() throws IOException {
		for(ControladorImage cn: images) {
			if(cn.select()) {
				crearSeleccionarImagen(1, cn.getImageAWT());
				deseleccionar();
				break;
				
			}
		}
		
	}
	
	public void histogramaiguala() throws IOException {
		for(ControladorImage cn: images) {
			if(cn.select()) {
				crearSeleccionarImagen(2, cn.getImageAWT());
				deseleccionar();
				break;
				
			}
		}
		
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
	
	public void espejoHorizontal(ActionEvent event) throws IOException {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				ImageRGB dummy = cn.getImageAWT().escpejoHorizontal();
				crearVentanaImagen(dummy.getImageFX(), dummy.getTipo());
				break;
			}
		}
		deseleccionar();
	}
	public void espejoVertical(ActionEvent event) throws IOException {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				ImageRGB dummy = cn.getImageAWT().escpejoVertical();
				crearVentanaImagen(dummy.getImageFX(), dummy.getTipo());
				break;
			}
		}
		deseleccionar();
	}
	public void traspuesta(ActionEvent event) throws IOException {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				ImageRGB dummy = cn.getImageAWT().traspuesta();
				crearVentanaImagen(dummy.getImageFX(), dummy.getTipo());
				break;
			}
		}
		deseleccionar();
	}
	
	public void SaveImageB(ActionEvent event) throws IOException {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				ImageRGB dummy = new ImageRGB(cn.getIm(), cn.getTipo());
				saveImage(dummy);
				break;
			}
		}
		deseleccionar();
	}
	
	public void umbralB() {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				try {
					getTextW(cn.getImageAWT(), 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		deseleccionar();
	}
	
	public void cambiarBC() {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				try {
					getTextW(cn.getImageAWT(), 2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		deseleccionar();
	}
	
	
	public void gamma() {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				try {
					getTextW(cn.getImageAWT(), 3);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		deseleccionar();
	}
	public void escaladoInterpolacional() {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				try {
					getTextW(cn.getImageAWT(), 4);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		deseleccionar();
	}
	
	public void escaladoVecinoProximo() {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				try {
					getTextW(cn.getImageAWT(), 5);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		deseleccionar();
	}
	
	public void rotado() {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				try {
					getTextW(cn.getImageAWT(), 6);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		deseleccionar();
	}
	
	public void rotadoMalo() {
		for(ControladorImage cn : images) {
			if(cn.select()) {
				try {
					getTextW(cn.getImageAWT(), 7);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		deseleccionar();
	}
	
	
	//Cradores de ventanas 業業業業業業業業業業業業業業業業業業業業業業業業業業業業業業業
	
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
		ControladorHistogramaAcumulado con = loader.getController();
		con.cargaHisto(im);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
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
	
	private void saveImage(ImageRGB pic) {
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(pic.getImageFX(),
                    null), "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
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
	
	
	
	public void PorPuntos(ImageRGB cn) throws IOException {
		FXMLLoader loader =new FXMLLoader(getClass().getResource("PorPuntosTramos.fxml"));
		Parent root = loader.load();
		PorPuntos con = loader.getController();
		con.cargar(cn.getImageFX(),cn.getTipo());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	private void crearSeleccionarImagen(int i, ImageRGB im) throws IOException {
		
		FXMLLoader loader =new FXMLLoader(getClass().getResource("SeleccionarImagen.fxml"));
		Parent root = loader.load();
		ControladorSeleccionarImagen con = loader.getController();
		con.cargar(i, im);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
	}
	
	public void getTextW(ImageRGB im, int i) throws IOException {
		FXMLLoader loader =new FXMLLoader(getClass().getResource("GetText.fxml"));
		Parent root = loader.load();
		GetTextW con = loader.getController();
		con.cargar(im.getImageFX(),im.getTipo(),i);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}
	

	public static List<ControladorImage> getImages() {
		return images;
	}


	
	
	
}
