package jaolho.archery;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.util.ArrayList;

public class ColorManipulator {
	public static void reduce(BufferedImage image, int colorCount) {
		int divider = 3 * 255 / colorCount;
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int rgb = image.getRGB(x, y);
				int red = ( rgb >> 16 ) & 0xFF;
		        int green = ( rgb >> 8 ) & 0xFF;
		        int blue = ( rgb & 0xFF );
		        red = red - ( red % divider );
		        green = green - ( green % divider );
		        blue = blue - ( blue % divider );
		        image.setRGB(x, y, 0xFF000000 | ( red << 16 ) | ( green << 8 ) | ( blue ));
			}
		}
	}
	
	public static void categorize(BufferedImage image) {
		double threshold = 1.15;
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int rgb = image.getRGB(x, y);
				
				int red = ( rgb >> 16 ) & 0xFF;
		        int green = ( rgb >> 8 ) & 0xFF;
		        int blue = ( rgb & 0xFF );
		        
		        
		        // red
		        if (red / (double) green > threshold && red / (double) blue > threshold) {
		        	image.setRGB(x, y, 0xFFFF0000); 
		        	continue;
		        }
		        // blue
		        if (blue / (double) green > threshold && blue / (double) red > threshold) {
		        	image.setRGB(x, y, 0xFF0000FF); 
		        	continue;
		        }
		        // green
		        if (green / (double) red > threshold && green / (double) blue > threshold) {
		        	image.setRGB(x, y, 0xFF0000FF); 
		        	continue;
		        }
		        // yellow
		        if (red / (double) blue > threshold && green / (double) blue > threshold) {
			        image.setRGB(x, y, 0xFFFFFF00);  
		        	continue;
		        }
		        // white
		        if (red > 230 && green > 230 && blue > 230) {
		        	image.setRGB(x, y, 0xFFFFFFFF); 
		        	continue;
		        }
		        // black
		        //if (red < 100 && green < 100 && blue < 100) {
		        //	image.setRGB(x, y, 0xFF000000); 
		        //	continue;
		        //}
		        image.setRGB(x, y, 0xFF000000); 
			}
		}
	}
}
