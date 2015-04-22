package rsos.framework.utils;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.util.ImageUtil;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class OneBarcodeUtil {
	public List<String> code = new ArrayList<String>();
	public String fileName;
	
    public OneBarcodeUtil() {
	}

	public OneBarcodeUtil(List<String> code, String fileName) {
		super();
		this.code = code;
		this.fileName = fileName;		
	}
       
    public void createBarcode(){
    	try{			
			String path = "C:\\barcodeImg\\" + fileName + "\\";
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();			
			}
			JBarcode localJBarcode = new JBarcode(Code39Encoder.getInstance(), 
					WideRatioCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
			localJBarcode.setShowText(true);
			localJBarcode.setShowCheckDigit(true);
			localJBarcode.setCheckDigit(false);
			
			for(String sigleCode:code){
				BufferedImage localBufferedImage = localJBarcode.createBarcode(sigleCode);
				saveToPNG(localBufferedImage,path + sigleCode+".png");				
			}			
			
			Rectangle pageSize = new Rectangle(189, 60);	//自定义纸张
			Document doc = new Document(pageSize,0,0,0,0);
			// 定义输出位置并把文档对象装入输出对象中
			PdfWriter.getInstance(doc, new FileOutputStream(path+"print.pdf"));
			// 打开文档对象
			doc.open();
			// 加入图片
			for(int i = 0; i < code.size(); i++){
				Image jpg = Image.getInstance(path + code.get(i)+".png");
				jpg.setAlignment(Image.ALIGN_CENTER);
				doc.add(jpg);
				if(i!=(code.size()-1)){
					doc.newPage();
				}else{
					break;
				}
			}
			// 关闭文档对象，释放资源
			doc.close();

//			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
//			// 查找所有的可用打印服务
//			PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
//			// 定位默认的打印服务
//			PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
//			// 显示打印对话框
//	        //PrintService service = ServiceUI.printDialog(null, 200, 200, printService, defaultService, flavor, pras);			
//			DocPrintJob job = defaultService.createPrintJob();// 创建打印作业
//			FileInputStream fis = new FileInputStream(file);// 构造待打印的文件流
//			DocAttributeSet das = new HashDocAttributeSet(); 
//			Doc doc = new SimpleDoc(fis, flavor, das);// 建立打印文件格式
//			pras.add(new Copies(this.sum));
//			job.print(doc, pras);// 进行文件的打印

        } catch (Exception e) {			
			e.printStackTrace();
		}
    }

	public static void main(String[] paramArrayOfString){    
		try {    
			OneBarcodeUtil obu = new OneBarcodeUtil();
			//生成. 欧洲商品条码(=European Article Number)    
			//这里我们用作图书条码    
			/*
			JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), 
				WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
			String str = "788515004012";    
			BufferedImage localBufferedImage = localJBarcode.createBarcode(str);    
			obu.saveToGIF(localBufferedImage, "C:\\barcodeImg\\EAN13.gif");    */
			
			JBarcode localJBarcode = new JBarcode(Code39Encoder.getInstance(), 
					WideRatioCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
			localJBarcode.setShowText(true);
			localJBarcode.setShowCheckDigit(true);
			localJBarcode.setCheckDigit(false);
      
			String str = "CMBC000040";    
			BufferedImage localBufferedImage = localJBarcode.createBarcode(str);    
			obu.saveToPNG(localBufferedImage, "C:\\barcodeImg\\CMBC000040.png");    
   
		}catch (Exception localException) {    
			localException.printStackTrace();    
		}    
 	}    
   
	public void saveToJPEG(BufferedImage paramBufferedImage, String paramString)    
	{    
		saveToFile(paramBufferedImage, paramString, "jpeg");    
	}    
   
	public void saveToPNG(BufferedImage paramBufferedImage, String paramString)    
	{    
		saveToFile(paramBufferedImage, paramString, "png");    
	}    
   
	public void saveToGIF(BufferedImage paramBufferedImage, String paramString)    
	{    
		saveToFile(paramBufferedImage, paramString, "gif");    
	}    
   
	public void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2)    
	{    
		try   
		{    
			FileOutputStream localFileOutputStream = new FileOutputStream(paramString1);    
			ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 96, 96);    
			localFileOutputStream.close();    
		} catch (Exception localException)    
		{    
			localException.printStackTrace();    
		}    
	}    
   
} 