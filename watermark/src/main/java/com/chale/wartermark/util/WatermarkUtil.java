package com.chale.wartermark.util;


import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WatermarkUtil {
	private static Logger log = LoggerFactory.getLogger(WatermarkUtil.class);

	/**
	 * 生成指定尺寸图片（非水印图）
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static Mat generateImage(int red, int green, int blue, int width, int height){
		Mat img_b = new Mat(height,width,CvType.CV_8UC1,new Scalar(blue));
		Mat img_r = new Mat(height,width,CvType.CV_8UC1,new Scalar(red));
		Mat img_g = new Mat(height,width,CvType.CV_8UC1,new Scalar(green));
		List<Mat> planes = new ArrayList<Mat>();
		planes.add(img_b);
		planes.add(img_g);
		planes.add(img_r);
		Mat image = new Mat();
		Core.merge(planes, image);
		return image;
	}
	/**
	 * 三通道都加文字水印
	 *
	 * @param image
	 * @param watermarkText
	 * @throws IOException
	 */
	public static Mat addWatermark(Mat image, String watermarkText, int width) throws IOException {
		List<Mat> planes = new ArrayList<Mat>();
		Core.split(image, planes);
		// 对字符进行utf编码
		watermarkText = URLEncoder.encode(watermarkText, "utf-8");
		int length = watermarkText.length();
		double fontSize = calculateFontSize(width, length);
		for (int i = 0; i < planes.size(); i++) {
			planes.set(i, DFTUtil.antitransformImage(DFTUtil.transformImageWithText(planes.get(i),
					watermarkText, new Point(4, 12), fontSize, new Scalar(255,255,255))));
		}
		Mat watermarkImg = new Mat();
		log.info("######## opencv-Core.merge前的通道数为 : " + planes.size() + "########");
		if(!planes.isEmpty()){
			Core.merge(planes, watermarkImg);
		}
		return watermarkImg;
	}

	/**
	 * 三通道都加图片水印
	 * @param image 源图片
	 * @param mark 水印图片
	 * @return
	 * @throws IOException
	 */
	public static Mat addWatermarkImg(Mat image, Mat mark, int coef) throws IOException {
		List<Mat> planes = new ArrayList<Mat>();
		Core.split(image, planes);
		// 对三通道都加水印
//		for (int i = 0; i < planes.size(); i++) {
//			planes.set(i, DFTUtil.antitransformImage(DFTUtil.transformImageWithImg(planes.get(i), mark, coef)));
//		}
		// 只对第二个通道加水印
		planes.set(1, DFTUtil.antitransformImage(DFTUtil.transformImageWithImg(planes.get(1), mark, coef)));

		Mat watermarkImg = new Mat();
		Core.merge(planes, watermarkImg);
		return watermarkImg;
	}

	/**
	 * 根据手机屏幕尺寸和pin的长度计算水印字体的大小
	 * @param width
	 * @param length
	 * @return
	 */
	private static double calculateFontSize(int width, int length){
		int capacity = width / 10;
		double fontSize = 0.6;
		if(length > capacity){
			capacity = width / 8;
			fontSize = 0.5;
			if (length > capacity){
				fontSize = 0.4;
				capacity = width / 7;
				if(length > capacity){
					fontSize = 0.3;
				}
			}
		}
		return fontSize;
	}
	/**
	 * 矩阵转换为字节流
	 * @param img
	 * @return
	 */
	public static byte[] mat2Bytes(Mat img){
		MatOfByte buf = new MatOfByte();
		Highgui.imencode(".png", img, buf);
		return buf.toArray();
	}

	/**
	 * 字节流转换为矩阵
	 * @param imgStream
	 * @return
	 */
	public static Mat bytes2Mat(byte[] imgStream){
		MatOfByte buf = new MatOfByte();
		buf.fromArray(imgStream);
		return Highgui.imdecode(buf, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
	}

	/**
	 * 字节流转换为三通道矩阵
	 * @param imgStream
	 * @return
	 */
	public static Mat bytes2RGBMat(byte[] imgStream){
		MatOfByte buf = new MatOfByte();
		buf.fromArray(imgStream);
		return Highgui.imdecode(buf,Highgui.CV_LOAD_IMAGE_UNCHANGED);
	}

	/**
	 * 生成单通道水印图
	 * @param watermarkText 水印文字
	 * @param width 生成图片的宽度
	 * @param height 生成图片的高度
	 * @return
	 */
	public static Mat generateWatermarkImage(String watermarkText, int width, int height) throws IOException {
		// 对字符进行utf编码
		watermarkText = URLEncoder.encode(watermarkText, "utf-8");
		double fontSize = calculateFontSize(width, watermarkText.length());
		Mat image = new Mat(height,width,CvType.CV_8UC1,new Scalar(0));
		Core.putText(image, watermarkText, new Point(8,15), Core.FONT_HERSHEY_TRIPLEX, fontSize, new Scalar(255),2);
		Core.flip(image, image, -1);
		Core.putText(image, watermarkText, new Point(8,15), Core.FONT_HERSHEY_TRIPLEX, fontSize, new Scalar(255),2);
		Core.flip(image, image, -1);
		return image;
	}

	/**
	 * 生成单通道水印图
	 * @param watermarkText 水印文字
	 * @param width 生成图片的宽度
	 * @param height 生成图片的高度
	 * @return
	 */
	public static Mat generateWatermarkImage(String watermarkText, int fontSize, int left, int top, int width, int height) throws IOException {
		// 创建BufferedImage对象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// 获取Graphics2D
		Graphics2D g2d = image.createGraphics();
		// 画图
		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, width, height);

		Color color = Color.WHITE;
		//Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		Font font = new Font("微软雅黑", Font.BOLD, fontSize);
		g2d.setFont(font);

		g2d.setPaint(color);

		new G2DWriteString(g2d,watermarkText,fontSize,width,height).write();
//		int step = 0;
//		while(step < (height + top)){
//			g2d.drawString(watermarkText, left, top + step);
//
//			step = step + fontSize;
//		}
//		while(step < (height + top)){
//			g2d.drawString(watermarkText, left, top + step);
//
//			step = step + fontSize;
//		}
		
		// 释放对象
		g2d.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 保存文件
		ImageIO.write(image, "png", baos);
		Mat mark = bytes2Mat(baos.toByteArray());
		return mark;
	}

	static class G2DWriteString{
		Graphics2D g2d;
		String str;
		int fontSize; int width; int height;
		int x=fontSize;int y=fontSize;

		int nowChar=0;

		public void write(){
			while (x<width){
				while(y<height){
					g2d.drawString(getChar(),x,y);
					y+=fontSize;
				}
				y=fontSize;
				x+=fontSize;
			}
		}

		public G2DWriteString(Graphics2D g2d, String str, int fontSize, int width, int height) {
			this.g2d = g2d;
			this.str = str+"##";
			this.fontSize = fontSize;
			this.width = width;
			this.height = height;
		}

		public String getChar() {
			if(nowChar>=str.length()) {
				nowChar=0;
			}
			String s = String.valueOf(str.charAt(nowChar));
			nowChar++;
			return s;
		}
	}
}
