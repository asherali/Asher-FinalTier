package simple_contours;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
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
		
		 
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src_img,src_grey,src_blur,src_thresh,src_dilate,dest_img; 
		src_img=Highgui.imread("test5j.jpg",Imgproc.COLOR_BGR2GRAY);
		
		
		src_grey=new Mat(src_img.size(), Core.DEPTH_MASK_8U);
		src_blur=new Mat(src_img.size(), Core.DEPTH_MASK_8U);
		src_thresh=new Mat(src_img.size(), Core.DEPTH_MASK_8U);
		src_dilate=new Mat(src_img.size(), Core.DEPTH_MASK_8U);
		dest_img=Mat.zeros(src_img.size(), CvType.CV_8UC3);
		//Mat mask=new Mat(); 
		//Mat floodfill=Mat.zeros(src_img.size(), CvType.CV_8UC1);
		
		Imgproc.cvtColor(src_img, src_grey, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(src_grey, src_blur, new Size(3, 3), 0);
		Imgproc.threshold(src_blur, src_thresh, 80, 255, Imgproc.THRESH_BINARY_INV);
		Imgproc.dilate(src_thresh, src_dilate, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
		
		//Core.inRange(src_dilate,new Scalar(255, 255, 255),new Scalar(255, 255, 255),mask);
		//int Pcount=Core.countNonZero(mask);
		
		//Core.bitwise_not(src_dilate, src_dilate);
		//currently finding contours for black bg and white fg
		
		
		Highgui.imwrite("Threshold.jpg", src_thresh);
		Highgui.imwrite("Dilate.jpg", src_dilate);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
         //Imgproc.Canny(src_dilate, dest_img, 35, 90);
         //Imgproc.dilate(dest_img, dest_img, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
 		 //Imgproc.copyMakeBorder(dest_img, dest_img, 1, 1, 1, 1, Imgproc.BORDER_REPLICATE);
         
         //Point seed=new Point(15, 25);
         //Imgproc.floodFill(floodfill, dest_img, seed, new Scalar(255));
         //Highgui.imwrite("floodfill.jpg", floodfill);
         Highgui.imwrite("dest.jpg", dest_img);
	      List<MatOfPoint> contours = new ArrayList<MatOfPoint>();  
	      Mat heirarchy= new Mat();
	      Point shift=new Point(0,0);
	      Imgproc.findContours(src_dilate, contours,heirarchy, Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_NONE,shift);
	      //double[] cont_area =new double[contours.size()]; 
	      int count=3;
	      double height_perc= 0.50;
	       int cont_size=contours.size();
	      Rect[] rect_arr=new Rect[cont_size];
	      double[] L_rects= new double[4];
	      double[] big2=new double[2];
	      int[] cont_index=new int[4];
	      int[] big2_index=new int[2];
	      Rect rect;
	      
	         for(int i=0; i<cont_size;i++)
	         { 
	        	 rect_arr[i]=Imgproc.boundingRect(contours.get(i));
	        	 System.out.println("rect AREA: "+rect_arr[i].area());
	         }
	         
	         Contours contObj= new Contours();
	          big2=contObj.find_big4(rect_arr,L_rects,big2,cont_size,cont_index,big2_index);
	         for(int i=0;i<4;i++)
	  	   {
	  		   System.out.println(L_rects[i]);
	  		   System.out.println(cont_index[i]);
	  	   }
	         for(int i=0;i<2;i++)
		  	   {
		  		   System.out.println(big2[i]);
		  		   System.out.println(big2_index[i]);
		  	   }
	         
	         for(int i=0; i<cont_size;i++)
	         { 
	        	  rect = Imgproc.boundingRect(contours.get(i));
		           // cont_area[i]=Imgproc.contourArea(contours.get(i));
		            //System.out.println("Hight: "+rect.height);
		            //System.out.println("WIDTH: "+rect.width);
		            //System.out.println("Cont AREA: "+cont_area[i]);
		            //System.out.println(rect.x +","+rect.y+","+rect.height+","+rect.width);
		              //System.out.println(rect_arr[i].x +","+rect_arr[i].y+","+rect_arr[i].height+","+rect_arr[i].width);
		            
	        	 int[] iBuff=new int[(int)heirarchy.total()*heirarchy.channels()];
	        	 heirarchy.get(0, 0,iBuff);
	        	 //if(rect.height>17)
	        	 if(iBuff[count]==-1)
	        	 {
	        		 if(contObj.in_range(rect_arr[big2_index[1]].height,rect_arr[i].height, height_perc))
	        		 {
	        			 
		              Core.rectangle(src_img, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,0,255));  	 
		              Imgproc.drawContours(dest_img, contours, i, new Scalar(255,255,255),-1,8,heirarchy,2,shift);
		             
		              //Core.rectangle(dest_img, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,255,0));
	        	 }
	        	 }
		              count+=4; 
	         }
	         
	         
              // Imgproc.erode(dest_img, dest_img, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
 	         Highgui.imwrite("Final.jpg", dest_img);
	         Highgui.imwrite("Original.jpg", src_img);
	         
	         BufferedImage bi;
	        Deskew deskew=new Deskew();
	         File Filename=new File("FinalOCR.jpg");
	         Tesseract tesseract=Tesseract.getInstance();
	   		
	   		try
	   		{	
	   			bi=deskew.ApplyDeskew();
	   			String Result=tesseract.doOCR(bi);
	   			System.out.println(Result);
	   			ImageIO.write(bi, "jpg", Filename);
	   			
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
	
	
//////////////////////////////////////////////////////////////////////////////////////////
	
	
	

}
