package com.test.image;

import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.List;

/**
 * Created by Hyun Woo Son on 9/12/17.
 */
public class testImage {


    public static void main(String args[]) throws Exception
    {
        String text = "SUPER PRUEBA";

        PDFDocument document = new PDFDocument();
        document.load(new File("/Users/User/Downloads/PagareOrdenTasaFijaD201708020901086465.pdf"));

        SimpleRenderer renderer = new SimpleRenderer();


        // set resolution (in DPI)
        renderer.setResolution(300);

        List<Image> images = renderer.render(document);

        for (int i = 0; i < images.size(); i++) {

            Image image =    images.get(i);
            BufferedImage bufferedImage= addWaterMark(image,text);

            ImageIO.write(bufferedImage, "png", new File((i + 1) + ".png"));
        }




    }


    private static BufferedImage addWaterMark(Image image,String text){

        // create BufferedImage object of same width and height as of original image
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);

        Integer fontSize = 400;

        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.setColor(new Color(192,192,192,100));
        // set font for the watermark text
        graphics.setFont(rotateFont(fontSize));

        // add the watermark text
        int y= -100;
        int x= 0;
        graphics.drawString(text, x, y);
        graphics.drawString(text, x, y+=fontSize*2+50);
        graphics.drawString(text, x, y+=fontSize*2+50);
        graphics.drawString(text, x, y+=fontSize*2+50);
        graphics.dispose();

        return bufferedImage;

    }


    private static Font rotateFont(int fontSize){
        Font font = new Font("Arial", Font.BOLD, fontSize);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(45), 0, 0);
        return  font.deriveFont(affineTransform);

    }
}
