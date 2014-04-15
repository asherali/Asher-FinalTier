package simple_contours;
import org.opencv.core.Rect;

public class Contours 
{
	double Height,Width,Area,diff,perc_variation,height_perc,width_perc;
    double L1_area,L2_area,L3_area;
	int L1_index,L2_index,L3_index;
    
   public Contours()
   {
	   L1_area=0;L2_area=0;L3_area=0;
	   height_perc=0.25;
	   width_perc=0.75;
   }
}
 class Biggest_contours extends Contours
{
	 Contours cont_L1=new Contours();
	 	Contours cont_L2=new Contours();
	 	Contours cont_L3=new Contours();
    int counter;
	 public void calc_big_3(Rect[] rect_arr,int conts_length)
	    {
	    	for(int i=0;i<conts_length;i++)
	    	{
	    		counter=i;
	    		if(rect_arr[i].area()>L1_area)
	    		{
	    			if(cont_L1.Height<rect_arr[i].height||cont_L1.Width<rect_arr[i].width)
	    			{
	    				if((in_range(rect_arr[i].height, cont_L1.Height,height_perc))&&in_range(rect_arr[i].height, cont_L1.Height, width_perc))
	    				{
	    					L2_area=L1_area;
	    					L2_index=L1_index;
	    					cont_L2.Area=cont_L1.Area;
	    					cont_L2.Height=cont_L1.Height;
	    					cont_L2.Width=cont_L2.Width;
	    					
	    					L1_area=rect_arr[i].area();
	    					L1_index=i;
	    					cont_L1.Area=rect_arr[i].area();
	    					cont_L1.Height=rect_arr[i].height;
	    					cont_L1.Width=rect_arr[i].width;
	    					
	    					
	    				}
	    					    
	    			}
	    	    		
	    		}
	    		else if(rect_arr[i].area()<L1_area)
	    		{
	    			
	    		}
	    	}
	    }
	 
	 
	/* public void compare_two(Contours cont1,Contours cont2,Rect[] rect,double Largest_area)
	 {
	 }*/
	 
	 public boolean in_range(double big,double small,double perc)
	 {
		 diff=big-small;
		 perc_variation=big*perc;
		 if(diff<perc_variation)
		 return true;
		 else
			 return false;
	 }
}