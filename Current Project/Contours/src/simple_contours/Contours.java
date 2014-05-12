package simple_contours;
import org.opencv.core.Rect;

public class Contours 
{
	double Height,Width,Area,diff,perc_variation,height_perc,width_perc;
	
   public double[] find_big4(Rect[] rect_arr,double[] L_rects,double[] big2,int cont_size, int[] cont_index, int[] big2_index)
	{
		L_rects[0]=rect_arr[0].area();
		cont_index[0]=0;
		
	   for(int i=0;i<cont_size;i++)
	   {
		   if(rect_arr[i].area()>L_rects[0])
		   {
			   L_rects[1]=L_rects[0];
			   L_rects[0]=rect_arr[i].area();
			   cont_index[1]=cont_index[0];
			   cont_index[0]=i;
			   
		   }
		    if((rect_arr[i].area()>L_rects[1])&&(rect_arr[i].area()<L_rects[0]))
		   {
			   L_rects[2]=L_rects[1];
			   L_rects[1]=rect_arr[i].area();
			   cont_index[2]=cont_index[1];
			   cont_index[1]=i;
		   }
		    if((rect_arr[i].area()>L_rects[2])&&(rect_arr[i].area()<L_rects[0])&&(rect_arr[i].area()<=L_rects[1]))
		   {
			   L_rects[3]=L_rects[2];
			   L_rects[2]=rect_arr[i].area();
			   cont_index[3]=cont_index[2];
			   cont_index[2]=i;
		   }
		    if((rect_arr[i].area()>L_rects[3])&&(rect_arr[i].area()<L_rects[0])&&(rect_arr[i].area()<L_rects[1])&&(rect_arr[i].area()<L_rects[2]))
		   {
			   
			   L_rects[3]=rect_arr[i].area();
			   cont_index[3]=i;
		   }
	   }
	   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	   
	   if(in_range(rect_arr[cont_index[0]].height,rect_arr[cont_index[1]].height, 25)&&in_range(rect_arr[cont_index[0]].width,rect_arr[cont_index[1]].width, 75))
	   {
		   big2[0]=L_rects[0];
		   big2[1]=L_rects[1];
		   big2_index[0]=cont_index[0];
		   big2_index[1]=cont_index[1];
		   
	   }
	   else
	   {
		   if(in_range(rect_arr[cont_index[1]].height,rect_arr[cont_index[2]].height, 25)&&in_range(rect_arr[cont_index[1]].width,rect_arr[cont_index[2]].width, 75))
		   {
			   big2[0]=L_rects[1];
			   big2[1]=L_rects[2];
			   big2_index[0]=cont_index[1];
			   big2_index[1]=cont_index[2];
			      
		   }
		   else
		   {
			   big2[0]=L_rects[2];
			   big2[1]=L_rects[3];
			   big2_index[0]=cont_index[2];
			   big2_index[1]=cont_index[3];
		   }
		   
		   return big2;
	   }
	   
	return big2;
	}
   
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
