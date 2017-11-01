package com.chale.wartermark;

import java.io.IOException;

import com.chale.wartermark.exception.WaterMarkException;
import com.chale.wartermark.service.WaterMarkService;
import com.chale.wartermark.service.impl.WaterMarkServiceImpl;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
	private static Logger log = LoggerFactory.getLogger(Main.class);



	public static void main(String[] args) throws WaterMarkException {
		WaterMarkService waterMarkService=new WaterMarkServiceImpl("D:\\opencv","opencv_java2413.dll");
//		for (int i = 0; i <1000 ; i++) {
//			System.out.println(i);
//			waterMarkService.joinWaterMark("D:\\opencv\\539219310300598966.jpg","1361217703"+i,"D:\\opencv\\shazi2"+i+".jpg");
//		}

		waterMarkService.parseWatermarkForRedScale("D:\\\\opencv\\595355e8N98bfcfd5.png","D:\\\\opencv\\aaaaa.jpg");

	}



}
