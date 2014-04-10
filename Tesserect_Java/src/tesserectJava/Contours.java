package tesserectJava;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Contours 
{
       public String FindContours(String name)
       {
    	   try
    	   {
    		   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    		   Mat source_img=Highgui.imread(name,CvType.CV_8UC1);
    		   Mat destination_img = new Mat(source_img.rows(),source_img.cols(),source_img.type());

   	        destination_img = source_img;  
    		   
    		List<MatOfPoint> contours=new ArrayList<MatOfPoint>();
    		Imgproc.findContours(source_img, contours, new Mat(), Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_NONE);
    		
    		for(int i=0;i<contours.size();i++)
    		{
    			System.out.println(Imgproc.contourArea(contours.get(i)));
    			//Rect rect=Imgproc.boundingRect(contours.get(i));// gets bounding rectangle for contours
    			//System.out.println(rect.height);
    			Imgproc.drawContours(destination_img, contours, i, new Scalar(255,255,255));
    			//Core.rectangle(destination_img, new Point(rect.x, rect.y), new Point(rect.x+rect.width,rect.y+ rect.height), new Scalar(0,  255,0));// select different argument method if wants to shift
    		}
    		
    		Highgui.imwrite("contours.jpg", destination_img);
	         name="contours.jpg";
    	   }
    	   catch(Exception e)
    	   {
    		   
    	   }
    	   
    	   
    	   return name;
       }
}
