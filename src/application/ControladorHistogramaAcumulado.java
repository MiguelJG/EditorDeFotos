package application;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class ControladorHistogramaAcumulado implements Initializable{
	
	@FXML
	private BarChart<String, Integer> barChartR;
	
	@FXML
	private CategoryAxis axisR;
	
	@FXML
	private Label tipo;
	
	@FXML
	private Label tamano;
	
	@FXML
	private Label rango;
	
	@FXML
	private Label Brillo;
	
	@FXML
	private Label entropia;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public void cargaHisto(ImageRGB im) {
		if(!im.isBandW()) {
			DecimalFormat df = new DecimalFormat("#.00");
			ArrayList<Long> r = im.getHistAcum(im.getHistRed());
			ArrayList<Long> a =im.getHistAcum(im.getHistBlue());
			ArrayList<Long> v = im.getHistAcum(im.getHistGreen());
			tipo.setText("El tipo de fichero es: " + im.getTipo());
			tamano.setText("El tamnio de la imagen es de " + im.getWidth() + "X" + im.getHeight());
			rango.setText("MinR[ " +im.min(r)+ " ], MaxR[ " +im.max(r)+ " ], MinG[ " +im.min(v)+ " ], MaxG[ " +im.max(v)+ " ], MinB[ " +im.max(v)+ " ], MaxB[ " +im.min(a)+ " ]" );
			Brillo.setText("Brillo    Red: " + df.format(im.getBrillo(im.getHistRed())) + " , Green: " + df.format(im.getBrillo(im.getHistGreen())) + " , Blue: " + df.format(im.getBrillo(im.getHistBlue()))+
					 "\nContraste Red: " + df.format(im.getContraste(im.getHistRed())) + " , Green: " + df.format(im.getContraste(im.getHistGreen())) + " , Blue: " + df.format(im.getContraste(im.getHistBlue())));
					entropia.setText("Entropia: " + im.getEntropia(r));
			XYChart.Series<String, Integer> rojos = new XYChart.Series<>();
			rojos.setName("Red");
			for(int i = 0; i <= 255; i++) {
				rojos.getData().add(new XYChart.Data<String, Integer>(i+"" , (int) (long) r.get(i)));
			}
			
			barChartR.getData().addAll(rojos);
			
			XYChart.Series<String, Integer> azules = new XYChart.Series<>();
			azules.setName("Blue");
			for(int i = 0; i <= 255; i++) {
				azules.getData().add(new XYChart.Data<String, Integer>(i+"", (int) (long) a.get(i)));
			}
			
			barChartR.getData().addAll(azules);
			XYChart.Series<String, Integer> verdes = new XYChart.Series<>();
			verdes.setName("Green");
			for(int i = 0; i <= 255; i++) {
				verdes.getData().add(new XYChart.Data<String, Integer>(i+"", (int) (long) v.get(i)));
			}
			
			barChartR.getData().addAll(verdes);
		}else {
			DecimalFormat df = new DecimalFormat("#.00");
			ArrayList<Long> r = im.getHistAcum(im.getHistRed());
			XYChart.Series<String, Integer> rojos = new XYChart.Series<>();
			rojos.setName("Grises");
			for(int i = 0; i <= 255; i++) {
				rojos.getData().add(new XYChart.Data<String, Integer>(i+"" , (int) (long) r.get(i)));
			}
			
			barChartR.getData().addAll(rojos);
			
			tipo.setText("El tipo de fichero es: " + im.getTipo());
			tamano.setText("El tama�o de la imagen es de " + im.getWidth() + "X" + im.getHeight());
			rango.setText("Min[ " +im.min(r)+ " ], Max[ " +im.max(r)+ " ]" );
			Brillo.setText("Brillo: " + df.format(im.getBrillo(im.getHistRed())));
			entropia.setText("Entropia: " + im.getEntropia(im.getHistRed()));
		}
		
	}
	
	
	
	
	
}
