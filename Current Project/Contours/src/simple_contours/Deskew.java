package simple_contours;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.recognition.software.jdeskew.ImageDeskew;
import net.sourceforge.vietocr.ImageHelper;


public class Deskew {
	
	final double MINIMUM_DESKEW_THRESHOLD = 0.05d;
	
	
	public BufferedImage ApplyDeskew(String image) throws IOException
	{
	 File imageFile = new File(image);
     BufferedImage buff_img=ImageIO.read(imageFile);
     ImageDeskew Img_Deskew=new ImageDeskew(buff_img);

     double SkewAngle= Img_Deskew.getSkewAngle();
     if((SkewAngle>MINIMUM_DESKEW_THRESHOLD)||(SkewAngle< -MINIMUM_DESKEW_THRESHOLD))
     {
    	 buff_img=ImageHelper.rotateImage(buff_img, -SkewAngle);
     }
     
     
     return buff_img;
	}
	
	
}
