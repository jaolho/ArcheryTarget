package jaolho.archery;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.*;
import javax.swing.*;

public class TargetAnalyzer {

	class ImageBackgroundPanel extends JPanel {
	    TargetAnalyzer targetAnalyzer;
	 
	    ImageBackgroundPanel(TargetAnalyzer targetAnalyzer) {
	        this.targetAnalyzer = targetAnalyzer;
	    }
	 
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(targetAnalyzer.image, 0, 0, this.getWidth(), this.getHeight(), this);
	        
	    }
	}
	
	BufferedImage image;
	ImageBackgroundPanel panel;
	float[] hsbArray = new float[3];
	int[] rgbArray = new int[3];
	
	public TargetAnalyzer(String filename) {
		try {
			image = ImageIO.read(new File(filename));
			panel = new ImageBackgroundPanel(this);
			//ColorManipulator.reduce(image, 5);
			//ColorManipulator.categorize(image);
			
			//ArrayList<Point> dp = findDarkPoints();
			//dp.for
			Point center = findCenter();
			Graphics2D g = (Graphics2D) image.getGraphics();
			g.setColor(Color.green);
	        Point c = findCenter();
	        g.fillOval(c.x, c.y, 10, 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseRGB(int rgb) {
		rgbArray[0] = (rgb >> 16) & 0xFF;
		rgbArray[1] = (rgb >> 8) & 0xFF;
		rgbArray[2] = (rgb & 0xFF);
	}
	
	private void parseHSB(int rgb) {
		parseRGB(rgb);
		Color.RGBtoHSB(rgbArray[0], rgbArray[1], rgbArray[2], hsbArray);
	}
	
	private ArrayList<Point> findYellowPoints() {
		ArrayList<Point> result = new ArrayList<Point>();
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int rgb = image.getRGB(x, y);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);
				double rgBalance = (double)r / (double) g;
				double rbBalance = (double) r / (double) b;
				if (!(rgBalance < 0.7 || rgBalance > 1.3 || rbBalance < 2))
					result.add(new Point(x, y));
			}
		}
		return result;
	}
	
	private ArrayList<Point> findYellowPoints2() {
		Point p = new Point();
		int maxR = image.getWidth() / 4;
		Point center = new Point(image.getWidth() / 2, image.getHeight() / 2);
		ArrayList<Point> result = new ArrayList<Point>();
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				p.x = x;
				p.y = y;
				if (center.distance(p) > maxR) { 
					//image.setRGB(x, y, 0xffff);
					continue;
				}
				parseHSB(image.getRGB(x, y));
				if (hsbArray[0] > 40/360.0 && hsbArray[0] < 80/360.0 && hsbArray[1] > 0.5) {
					result.add(new Point(x, y));
				}
				else {
					image.setRGB(x, y, 0);
				}
				
			}
		}
		return result;
	}
	
	private ArrayList<Point> findDarkPoints() {
		ArrayList<Point> result = new ArrayList<Point>();
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int rgb = image.getRGB(x, y);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);
				if (r < 70 && g < 70 && b < 70) {
					result.add(new Point(x, y));
					image.setRGB(x, y, Color.PINK.getRGB());	
				}
				else {
					//image.setRGB(x, y, Color. //255, 0, 0);
				}
			}
		}
		return result;
	}
	
	private Point findCenter() {
		ArrayList<Point> yellowPoints = findYellowPoints2();
		long xTot = 0;
		long yTot = 0;
		for (Point point : yellowPoints) {
			xTot += point.x;
			yTot += point.y;
		}
		return new Point((int)(xTot / yellowPoints.size()), (int)(yTot / yellowPoints.size()));
	}
	
	private ArrayList<Point> findArrowLikeObject(ArrayList<Point> slice, int startX) {
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TargetAnalyzer t = new TargetAnalyzer("images/taulu3.png");
		TargetAnalyzer t = new TargetAnalyzer("images/20130530_143304.jpg");
		
		//1. Create the frame.
		JFrame frame = new JFrame("FrameDemo");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//3. Create components and put them in the frame.
		//...create emptyLabel...
		frame.getContentPane().add(t.panel);

		//4. Size the frame.
		frame.setSize(300, 300);
		//frame.pack();

		//5. Show it.
		frame.setVisible(true);
	}

}
