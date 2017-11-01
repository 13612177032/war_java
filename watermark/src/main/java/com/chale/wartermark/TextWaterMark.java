package com.chale.wartermark;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class TextWaterMark {

	// 水印透明度
	private float alpha = 0.2f;
	private int fontSize = 100;
	// 水印文字颜色
	private static Color color = Color.red;


	public void createWaterMarkFromImage(String source, String result,String mark) {
		OutputStream os = null;
		try {
			// 1、源图片
			Image srcImg = ImageIO.read(new File(source));
			int width = srcImg.getWidth(null);
			int height = srcImg.getHeight(null);

			BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
			// 获取Graphics2D
			Graphics2D g2d = image.createGraphics();
			g2d.drawImage(srcImg.getScaledInstance(width,height, Image.SCALE_SMOOTH), 0, 0,null);
			g2d.dispose();

			g2d = image.createGraphics();
			Font font = new Font("微软雅黑", Font.BOLD, fontSize);
			g2d.setFont(font);
			g2d.setPaint(color);

			
			GlyphVector fontGV = font.createGlyphVector(g2d.getFontRenderContext(), mark);

			Rectangle size = fontGV.getPixelBounds(g2d.getFontRenderContext(),0, 0);

			Shape textShape = fontGV.getOutline();
			double textWidth = size.getWidth();
			double textHeight = size.getHeight();
			AffineTransform rotate45 = AffineTransform.getRotateInstance(Math.PI / 4d);
			Shape rotatedText = rotate45.createTransformedShape(textShape);
			// use a gradient that repeats 4 times
			g2d.setPaint(new GradientPaint(0, 0, color, image.getWidth() / 2,
					image.getHeight() / 2, color));

			// 透明度
			g2d.setStroke(new BasicStroke(alpha));
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));

			double yStep = Math.sqrt(textWidth * textWidth / 2) + 80;
			for (double x = -textHeight * 3; x < image.getWidth(); x += (textHeight * 3)) {
				double y = -yStep;
				for (; y < image.getHeight(); y += yStep) {
					g2d.fill(rotatedText);
					g2d.translate(0, yStep);
				}
				g2d.translate(textHeight * 3, -(y + yStep));
			}

			// 释放对象
			g2d.dispose();
			os = new FileOutputStream(result);
			ImageIO.write(image, "PNG", os);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public static void main(String[] args) {
		TextWaterMark  textWaterMark=new TextWaterMark();
		String source = "d://out//touming.png";
		String result = "d://out//background_default-mark.png";
		textWaterMark.createWaterMarkFromImage(source, result, "12345678");
	}

}
