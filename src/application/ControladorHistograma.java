package application;

import java.net.URL;
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

public class ControladorHistograma implements Initializable{
	
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
			ArrayList<Long> r = im.getHistRed();
			ArrayList<Long> a =im.getHistBlue();
			ArrayList<Long> v = im.getHistGreen();
			tipo.setText("El tipo de fichero es: " + im.getTipo());
			tamano.setText("El tama�o de la imagen es de " + im.getWidth() + "X" + im.getHeight());
			rango.setText("MinR[ " +im.min(r)+ " ], MaxR[ " +im.max(r)+ " ], MinG[ " +im.min(v)+ " ], MaxG[ " +im.max(v)+ " ], MinB[ " +im.max(v)+ " ], MaxB[ " +im.min(a)+ " ]" );
			Brillo.setText("Brillo Red: " + im.getBrillo(r) + " , brillo Green: " + im.getBrillo(v) + " brillo Blue: " + im.getBrillo(a));
			entropia.setText("Entro y pia");
			XYChart.Series<String, Integer> rojos = new XYChart.Series<>();
			rojos.setName("Red");
			XYChart.Series<String, Integer> verdes = new XYChart.Series<>();
			verdes.setName("Green");
			XYChart.Series<String, Integer> azules = new XYChart.Series<>();
			azules.setName("Blue");
			for(int i = 0; i <= 255; i++) {
				rojos.getData().add(new XYChart.Data<String, Integer>(i+"" , (int) (long) r.get(i)));
				azules.getData().add(new XYChart.Data<String, Integer>(i+"", (int) (long) a.get(i)));
				verdes.getData().add(new XYChart.Data<String, Integer>(i+"", (int) (long) v.get(i)));
			}
			
			barChartR.getData().addAll(rojos);
			barChartR.getData().addAll(azules);	
			barChartR.getData().addAll(verdes);
		}else {
			ArrayList<Long> r = im.getHistRed();
			XYChart.Series<String, Integer> rojos = new XYChart.Series<>();
			rojos.setName("Grises");
			for(int i = 0; i <= 255; i++) {
				rojos.getData().add(new XYChart.Data<String, Integer>(i+"" , (int) (long) r.get(i)));
			}
			
			barChartR.getData().addAll(rojos);
			
			tipo.setText("El tipo de fichero es: " + im.getTipo());
			tamano.setText("El tama�o de la imagen es de " + im.getWidth() + "X" + im.getHeight());
			rango.setText("Min[ " +im.min(r)+ " ], Max[ " +im.max(r)+ " ]" );
			Brillo.setText("Brillo: " + im.getBrillo(r));
			entropia.setText("Entropia: " + im.getEntropia(r));
		}
		
	}
	
	
	
	
	
}
