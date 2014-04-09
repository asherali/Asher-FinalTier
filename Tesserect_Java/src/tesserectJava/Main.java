package tesserectJava;
import java.io.*;

import net.sourceforge.tess4j.*;;

public class Main {

	public static void main(String[] args) 
	{
		String name="test.jpg";
		
		GreyScale greyscale=new GreyScale();
		name=greyscale.convert2Grascale(name);
	
		Thresholding threshold=new Thresholding();
		name=threshold.apply_threshold(name);
	
		Morpholgy_Operations morph_op=new Morpholgy_Operations();
	
		name=morph_op.Apply_Dilation(name);
		name=morph_op.Apply_Erosion(name);
		
		File Filename=new File(name);
		Tesseract tesseract=Tesseract.getInstance();
		
		try
		{	
			String Result=tesseract.doOCR(Filename);
			System.out.println(Result);
		}
		catch(TesseractException e)
		{
			System.err.println(e.getMessage());
		}
		
	}

}
