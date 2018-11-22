import javax.swing.ImageIcon;

public class Main {

	public static void main(String[] args) {
		ImageRGB im = new ImageRGB();
		//ImageRGB im2 = new ImageRGB();
		System.out.println(im.isBandW());
		//System.out.println(im2.blackAndWhite);
		im.transformToBlackAndWhite();
		System.out.println(im.blackAndWhite);
		System.out.println(im.getBrillo(im.getHistRed()));
		System.out.println(im.getContraste(im.getHistRed()));
		System.out.println(im.getHistRed());
		System.out.println(im.getHistAcum(im.getHistRed()));
		try {
		//im.transformacionLinealPorTramo("1-0-255-255-0");
			im.equalizarHistograma();
		}catch(Error e) {
			System.out.println(e);
		}
		//System.out.println(im.getHistRed());
		System.out.println(im.getHistAcum(im.getHistRed()));
		im.show();	
	}

}
