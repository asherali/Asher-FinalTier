package tesserectJava;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Morpholgy_Operations 
{

	public String Apply_Erosion(String name)
	{
		try
		{
			  System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		         Mat source = Highgui.imread(name);
		         Mat destination =new Mat(source.rows(),source.cols(),source.type());
		         destination = source;

		         int erosion_size = 1;
		         Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, 
		         new Size(erosion_size , erosion_size));
		         Imgproc.erode(source, destination, element);
		         Highgui.imwrite("erosion.jpg", destination);
		         name="erosion.jpg";

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		return name;
	}
	
	public String Apply_Dilation(String name)
	{
		try
		{
			  System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
			 Mat source = Highgui.imread(name);
		    Mat destination =new Mat(source.rows(),source.cols(),source.type());
	        destination = source;
	        int dilation_size =2;
			Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
		    new Size(dilation_size, dilation_size));
			Imgproc.dilate(source, destination, element1);
			Highgui.imwrite("dilation.jpg", destination);
			name="dilation.jpg";	        

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		return name;
	}
}
