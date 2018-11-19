import javax.swing.ImageIcon;

public class Main {

	public static void main(String[] args) {
		ImageRGB im = new ImageRGB();
		//ImageRGB im2 = new ImageRGB();
		System.out.println(im.isBandW());
		//System.out.println(im2.blackAndWhite);
		im.transformToBlackAndWhite();
		System.out.println(im.blackAndWhite);
		System.out.println(im.getHistRed());
		//System.out.println(im2.blackAndWhite);
		im.show();	
		//im2.show();
	}

}
