package simple_contours;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import net.sourceforge.tess4j.TessAPI;
import net.sourceforge.tess4j.TessAPI.TessBaseAPI;
import net.sourceforge.tess4j.TessAPI1;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



public class Main {

	public static void main(String[] args) {
		
		Boolean whiteBg=false; 
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src_img,proc_img,output; 
		src_img=Highgui.imread("test10.jpg",Imgproc.COLOR_BGR2GRAY);
		
		
	
	
		proc_img=Mat.zeros(src_img.size(), CvType.CV_8UC3);
		output=Mat.zeros(src_img.size(), CvType.CV_8UC3);
	
		
		Imgproc.cvtColor(src_img, proc_img, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(proc_img, proc_img, new Size(3, 3), 0);
		Imgproc.threshold(proc_img, proc_img, 80, 255, Imgproc.THRESH_BINARY_INV);
		Imgproc.dilate(proc_img, proc_img, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
				
         Highgui.imwrite("Proccessed Image.jpg", proc_img);
	      List<MatOfPoint> contours = new ArrayList<MatOfPoint>();  
	      Mat heirarchy= new Mat();
	      Imgproc.findContours(proc_img.clone(), contours,heirarchy, Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_NONE,new Point(0,0));
	      int count=3;
	      double height_perc= 0.50;
	       int cont_size=contours.size();
	      Rect[] rect_arr=new Rect[cont_size];
	      double[] L_rects= new double[4];
	      double[] big2=new double[2];
	      int[] cont_index=new int[4];
	      int[] big2_index=new int[2];
	      Rect rect;
	      double rectArea;
	      double halfImgArea;
	      
	         for(int i=0; i<cont_size;i++)
	         { 
	        	 rect_arr[i]=Imgproc.boundingRect(contours.get(i));
	        	 rectArea=rect_arr[i].area();
    			  halfImgArea=(src_img.width()*src_img.height())/2;
    			 
    			  if(rectArea>=halfImgArea)
    			 {
    				  Core.bitwise_not(proc_img, proc_img);
    				  whiteBg=true;
    				  Highgui.imwrite("Processed Image.jpg", proc_img);
    			      contours.clear();
    			      Imgproc.findContours(proc_img.clone(), contours,heirarchy, Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_NONE,new Point(0,0));
    			      cont_size=contours.size();
    			      rect_arr=new Rect[cont_size];
    			      break;
    			 }
    		}
	         
	       if(whiteBg)
	       {
	    	   for(int i=0; i<cont_size;i++)
	        	 rect_arr[i]=Imgproc.boundingRect(contours.get(i));       	 
	       }
	         
	         Contours contObj= new Contours();
	          big2=contObj.find_big4(rect_arr,L_rects,big2,cont_size,cont_index,big2_index);
	         
	         Mat[] images= new Mat[contours.size()];
	         for(int i=0,j=0; i<cont_size;i++)
	         { 
	        	 images[i]=Mat.zeros(src_img.size(),CvType.CV_8UC3);
	        	  rect = Imgproc.boundingRect(contours.get(i));
		          int[] iBuff=new int[(int)heirarchy.total()*heirarchy.channels()];
	        	 heirarchy.get(0, 0,iBuff);
	        	 if(iBuff[count]==-1)
	        	 {
	        		 if(contObj.in_range(rect_arr[big2_index[0]].height,rect_arr[i].height, height_perc))
	        		 {
	        			
		              Core.rectangle(src_img, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,0,255));  	 
		              Core.rectangle(images[j], new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,255,0));
		              Imgproc.drawContours(output, contours, i, new Scalar(255,255,255),-1,8,heirarchy,2,new Point(0,0));
		              Imgproc.drawContours(images[j], contours, i, new Scalar(255,255,255),-1,8,heirarchy,1,new Point(0,0));
		              Highgui.imwrite("image"+j+".jpg", images[j]); 
		              j++;
	        		 }
	        	 }
		              count+=4; 
	         }
	         
	         Imgproc.copyMakeBorder(output, output, 10, 10, 10, 10, Imgproc.BORDER_REPLICATE);
	         Highgui.imwrite("Filtered.jpg", output);
	         Highgui.imwrite("Original.jpg", src_img);
  
	      	try
	   		{	
	   			BufferedImage bi;
	   			Deskew deskew=new Deskew();
	   			Tesseract tesseract=Tesseract.getInstance();
	  		   tesseract.setLanguage("eng");
	  		    tesseract.setPageSegMode(8);
	  		    tesseract.setTessVariable("tessedit_char_whitelist", "0123456789abcdefghjklmnopqrstuvwxyzABCDEFGHJKLNMPQRSTUVWXYZ");
	   		    
	  		    {
	   				bi=deskew.ApplyDeskew("Filtered.jpg");
		   			String Result=tesseract.doOCR(bi);
		   			File Filename=new File("Ready2OCR.jpg");
		   			ImageIO.write(bi, "jpg", Filename);
		   			System.out.println(Result); 	
	   		    }
	   		}
	   		catch(TesseractException e)
	   		{
	   			System.err.println(e.getMessage());
	   		}
	   		catch(IOException e)
	   		{
	   			System.err.println(e.getMessage());
	   		}
	}
}
