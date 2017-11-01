package com.chale.wartermark.util;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class DFTUtil {
	private static final Double MOD = 88000d;
	public static Mat transformImageWithMark(Mat imgWithMark, Mat image) {
//		writeMatToFile("E:\\watermark\\20161111\\源图.txt", image.dump());
//		writeMatToFile("E:\\watermark\\20161111\\带水印图.txt", imgWithMark.dump());
		List<Mat> planesWithMark = new ArrayList<Mat>();
		Mat complexImageWithMark = new Mat();
		// optimize the dimension of the loaded image
		Mat paddedWithMark = optimizeImageDim(imgWithMark);
		// Mat padded = image;
		paddedWithMark.convertTo(paddedWithMark, CvType.CV_32F);
		// prepare the image planes to obtain the complex image
		planesWithMark.add(paddedWithMark);
		planesWithMark.add(Mat.zeros(paddedWithMark.size(), CvType.CV_32F));
		// prepare a complex image for performing the dft
		Core.merge(planesWithMark, complexImageWithMark);
		// dft
		Core.dft(complexImageWithMark, complexImageWithMark);

		// 实部和虚部
		List<Mat> complexWithMarkMat = new ArrayList<Mat>();
		Core.split(complexImageWithMark, complexWithMarkMat);

		Mat reWithMarkPart = complexWithMarkMat.get(0);
		Mat imWithMarkPart = complexWithMarkMat.get(1);
//
//		writeMatToFile("E:\\watermark\\20161111\\带水印图实部.txt", reWithMarkPart.dump());
//		writeMatToFile("E:\\watermark\\20161111\\带水印图虚部.txt", imWithMarkPart.dump());
		// 原图片进行傅里叶变换并求出实部和虚部

		List<Mat> planes = new ArrayList<Mat>();
		Mat complexImage = new Mat();
		// optimize the dimension of the loaded image
		Mat padded = optimizeImageDim(image);
		// Mat padded = image;
		padded.convertTo(padded, CvType.CV_32F);
		// prepare the image planes to obtain the complex image
		planes.add(padded);
		planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
		// prepare a complex image for performing the dft
		Core.merge(planes, complexImage);
		// dft
		Core.dft(complexImage, complexImage);

		// 实部和虚部
		List<Mat> complexMat = new ArrayList<Mat>();
		Core.split(complexImage, complexMat);

		Mat rePart = complexMat.get(0);
		Mat imPart = complexMat.get(1);

		Mat reSub = new Mat();
		Mat imSub = new Mat();
		Core.subtract(reWithMarkPart, rePart, reSub);
		Core.subtract(imWithMarkPart, imPart, imSub);


		Mat complexMark = new Mat();
		complexMat.set(0, reSub);
		complexMat.set(1, imSub);
		Core.merge(complexMat, complexMark);
		// optimize the image resulting from the dft operation
		Mat magnitude = createOptimizedMagnitude(complexMark);
		return magnitude;
	}
	/**
	 * Optimize the magnitude of the complex image obtained from the DFT, to
	 * improve its visualization
	 *
	 * @param complexImage
	 *            the complex image obtained from the DFT
	 * @return the optimized image
	 */
	private static Mat createOptimizedMagnitude(Mat complexImage) {
		// init
		List<Mat> newPlanes = new ArrayList<Mat>();
		Mat mag = new Mat();
		// split the comples image in two planes
		Core.split(complexImage, newPlanes);
		// compute the magnitude
		Core.magnitude(newPlanes.get(0), newPlanes.get(1), mag);
//		writeMatToFile("E:\\watermark\\20161111\\差归一化前.txt", mag.dump());

		double coef = Core.minMaxLoc(mag).maxVal;
		System.out.println(coef);
		// JPEG压缩后，较PNG格式的图片频谱有变化，需要再进行一次归一化即可，如果直接转换则都是255白色，已经在lena验证
		for (int i = 0; i < mag.rows(); i++) {
			for (int j = 0; j < mag.cols(); j++) {
				mag.put(i, j, mag.get(i, j)[0] / MOD * 255);
			}
		}
		// move to a logarithmic scale
//		Core.add(Mat.ones(mag.size(), CvType.CV_32F), mag, mag);
//		Core.log(mag, mag);
		// optionally reorder the 4 quadrants of the magnitude image
		//shiftDFT(mag);
		// normalize the magnitude image for the visualization since both JavaFX
		// and OpenCV need images with value between 0 and 255
		//writeMatToFile("E:\\test\\差归一化前.txt", mag.dump());
		// convert back to CV_8UC1
		//writeMatToFile("E:\\watermark\\20161109\\log.txt", mag.dump());
		mag.convertTo(mag, CvType.CV_8UC1);
		//Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);

//		for (int i = 0; i < mag.rows(); i++) {
//			for (int j = 0; j < mag.cols(); j++) {
//				if(mag.get(i, j)[0] < 255)
//				mag.put(i, j, 0);
//			}
//		}
//		writeMatToFile("E:\\watermark\\20161109\\差.txt", mag.dump());
		return mag;
	}

	public static Mat transformImageWithText(Mat image, String watermarkText, Point point, Double fontSize, Scalar scalar) {
		List<Mat> planes = new ArrayList<Mat>();
		Mat complexImage = new Mat();
		// optimize the dimension of the loaded image
		//Mat padded = this.optimizeImageDim(image);
		Mat padded = image;
		padded.convertTo(padded, CvType.CV_32F);
		// prepare the image planes to obtain the complex image
		planes.add(padded);
		planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
		// prepare a complex image for performing the dft
		Core.merge(planes, complexImage);
		// dft
		Core.dft(complexImage, complexImage);		
		// 频谱图上添加文本
		Core.putText(complexImage, watermarkText, point, Core.FONT_HERSHEY_DUPLEX, fontSize, scalar);
		Core.flip(complexImage, complexImage, -1);
		Core.putText(complexImage, watermarkText, point, Core.FONT_HERSHEY_DUPLEX, fontSize, scalar);
		Core.flip(complexImage, complexImage, -1);

		return complexImage;
	}

	public static Mat antitransformImage(Mat complexImage) {
		Mat invDFT = new Mat();
		Core.idft(complexImage, invDFT, Core.DFT_SCALE | Core.DFT_REAL_OUTPUT, 0);
		Mat restoredImage = new Mat();
		invDFT.convertTo(restoredImage, CvType.CV_8U);
		return restoredImage;
	}

	public static Mat transformImageWithImg(Mat image, Mat mark, int coef) {
		// 水印图片由8位变为32位
		mark.convertTo(mark, CvType.CV_32F);
		List<Mat> planes = new ArrayList<Mat>();
		Mat complexImage = new Mat();
		// optimize the dimension of the loaded image
		//Mat padded = optimizeImageDim(image);
		Mat padded = image;
		padded.convertTo(padded, CvType.CV_32F);
		// prepare the image planes to obtain the complex image
		planes.add(padded);
		planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
		// prepare a complex image for performing the dft
		Core.merge(planes, complexImage);
		// dft
		Core.dft(complexImage, complexImage);

		// 实部和虚部
		List<Mat> complexMat = new ArrayList<Mat>();
		Core.split(complexImage, complexMat);
		Mat rePart = complexMat.get(0);
		Mat imPart = complexMat.get(1);
		// 计算下文中水印图片等比例放大的系数
		double reCoef = Core.minMaxLoc(rePart).maxVal / 255 / coef;
		double imCoef = Core.minMaxLoc(imPart).maxVal / 255 / coef;
		//相位角
		Mat angle = new Mat();
		Core.phase(rePart, imPart, angle);
		//相位角的余弦值
		Mat angleCos = Mat.zeros(angle.rows(), angle.cols(), angle.type());
		for(int i = 0; i < angleCos.rows(); i ++){
			for(int j = 0; j < angleCos.cols(); j ++){
				angleCos.put(i, j, Math.cos(angle.get(i, j)[0]));
			}
		}
		//相位角的正弦值
		Mat angleSin = Mat.zeros(angle.rows(), angle.cols(), angle.type());
		for(int i = 0; i < angleSin.rows(); i ++){
			for(int j = 0; j < angleSin.cols(); j ++){
				angleSin.put(i, j, Math.sin(angle.get(i, j)[0]));
			}
		}
		// 水印图片频谱图矩阵 x 源图片相位角的余弦值 = 水印图片频谱图实部
		Mat reMark = new Mat();
		Core.multiply(angleCos, mark, reMark);
		// 水印图片频谱图矩阵 x 源图片相位角的正弦值 = 水印图片频谱图虚部
		Mat imMark = new Mat();
		Core.multiply(angleSin, mark, imMark);
		// 将水印图片频谱实部按照比例等比放大
		for(int i = 0; i < reMark.rows(); i ++){
			for(int j = 0; j < reMark.cols(); j++){
				reMark.put(i, j, reMark.get(i, j)[0] * reCoef);
			}
		}
		// 将水印图片频谱虚部按照比例等比放大
		for(int i = 0; i < imMark.rows(); i ++){
			for(int j = 0; j < imMark.cols(); j++){
				imMark.put(i, j, imMark.get(i, j)[0] * imCoef);
			}
		}
		// 源图片实部与等比放大的水印图片的实部相加
		Core.add(reMark, rePart, rePart);
		// 源图片虚部与等比放大的水印图片的虚部相加
		Core.add(imMark, imPart, imPart);
		// 将加水印的图片的实部和虚部合并组成双通道复频域矩阵
		complexMat.set(0, rePart);
		complexMat.set(1, imPart);
		Core.merge(complexMat, complexImage);
		return complexImage;
	}

	/**
	 * 为加快傅里叶变换的速度，对要处理的图片尺寸进行优化
	 *
	 * @param image
	 *            the {@link Mat} to optimize
	 * @return the image whose dimensions have been optimized
	 */
	private static Mat optimizeImageDim(Mat image) {
		// init
		Mat padded = new Mat();
		// get the optimal rows size for dft
		int addPixelRows = Core.getOptimalDFTSize(image.rows());
		// get the optimal cols size for dft
		int addPixelCols = Core.getOptimalDFTSize(image.cols());
		// apply the optimal cols and rows size to the image
		Imgproc.copyMakeBorder(image, padded, 0, addPixelRows - image.rows(), 0, addPixelCols - image.cols(),
				Imgproc.BORDER_CONSTANT, Scalar.all(0));

		return padded;
	}
}
