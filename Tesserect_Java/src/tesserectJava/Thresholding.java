package tesserectJava;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



public class Thresholding 
{
   public String apply_threshold(String name)
   {
	   try{

	         System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	         Mat source = Highgui.imread(name,CvType.CV_8UC1);
	         Mat destination = new Mat(source.rows(),source.cols(),source.type());

	        destination = source;       
	        //Imgproc.Canny(source,destination , 80, 100);
	       
	        
	        // Imgproc.threshold(source,destination,100,255,Imgproc.THRESH_BINARY_INV&Imgproc.THRESH_OTSU);
	         //Imgproc.adaptiveThreshold(source, destination, 255,Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);
	        // Imgproc.medianBlur(source, destination, 1);
	         Imgproc.GaussianBlur(source, destination,new Size(3,3), 0);
	        Imgproc.threshold(source, destination, 100, 255, Imgproc.THRESH_BINARY_INV);
	         Highgui.imwrite("Threshold.jpg", destination);
	         name="Threshold.jpg";
	         }catch (Exception e) {
	            System.out.println("error: " + e.getMessage());
	         }
	   return name;
   }
}
