import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		// print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = scaled(tinypic, 3, 5);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		int [] colors = in.readAllInts();
		int index = 0;

		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				
				int iCount = 0;
				int[] rgbVal = new int[3];

				while (iCount < 3) {
					rgbVal[iCount] = colors[index];
					index++;
					iCount++;
				}

				image[i][j] = new Color(rgbVal[0], rgbVal[1], rgbVal[2]);
			}
		}

		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
		String rgbs = "";
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				Color currRGB = image[i][j];
				if (j == image[0].length - 1){
					rgbs += String.format("( %3s, %3s, %3s)%n", currRGB.getRed(), currRGB.getGreen(), currRGB.getBlue());
				}
				else {
					rgbs += String.format("( %3s, %3s, %3s) ", currRGB.getRed(), currRGB.getGreen(), currRGB.getBlue());
				}		
			}
		}
		System.out.println(rgbs);
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		//// Replace the following statement with your code
		Color[][] hFlipImage = new Color[image.length][image[0].length];
		int fRow = 0;
		int lRow = image[0].length - 1;
		while (fRow <= lRow) {
			for (int i = 0; i < image.length; i++) {
				hFlipImage[i][fRow] = image[i][lRow];
				hFlipImage[i][lRow] = image[i][fRow];
			}
			fRow++;
			lRow--;
		}
		
		return hFlipImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		//// Replace the following statement with your code
		Color[][] vFlipImage = new Color[image.length][image[0].length];
		int fCol = 0;
		int lCol = image.length - 1;
		while (fCol <= lCol) {
			for (int i = 0; i < image[0].length; i++) {
				vFlipImage[fCol][i] = image[lCol][i];
				vFlipImage[lCol][i] = image[fCol][i];
			}
			fCol++;
			lCol--;
		}
		
		return vFlipImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		//// Replace the following statement with your code
		int lum = (int)(0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
		return new Color(lum, lum, lum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		//// Replace the following statement with your code
		Color[][] grayImage = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				grayImage[i][j] = luminance(image[i][j]);
			}
		}
		return grayImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		//// Replace the following statement with your code
		Color[][] scaledImage = new Color[height][width];
		float wScale = (float)width;
		float hScale = (float)height;
		float iWScale = (float)image[0].length;
		float iHScale = (float)image.length;


		for (int i = 0; i < scaledImage.length; i++) {
			for(int j = 0; j < scaledImage[0].length; j++) {
				float newI = i * (iHScale / hScale);
				float newJ = j * (iWScale / wScale);

				scaledImage[i][j] = image[(int)newI][(int)newJ];
			}

		}
		return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		//// Replace the following statement with your code
		double oAlpha = 1 - alpha;
		int rb = (int)(alpha * c1.getRed() + oAlpha * c2.getRed());
		int gb = (int)(alpha * c1.getGreen() + oAlpha * c2.getGreen());
		int bb = (int)(alpha * c1.getBlue() + oAlpha * c2.getBlue());

		return new Color(rb, gb, bb);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		//// Replace the following statement with your code
		Color[][] blenImage = new Color[image1.length][image1[0].length];

		for (int i = 0; i < blenImage.length; i++) {
			for (int j = 0; j < blenImage[0].length; j++) {
				blenImage[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
			

		}
		return blenImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		//// Replace this comment with your code
		Color[][] scaledTarget = scaled(target, source[0].length, source.length);


		for (int i = 0; i <=n; i++) {
			String finalOutput = String.format("%s/%s", n - i, n);
			Color[][] blendImage = blend(source, scaledTarget, (double)(n - i)/n);
			if (i == 0) {
				finalOutput += " (yielding the source image)";
			} 
			else if (i == n) {
				finalOutput += " (yielding the target image)";
			}
			System.out.println(String.format("step %s: Blend the two images using α = %s", i, finalOutput));
			display(blendImage);
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

