package com.chale.wartermark.service.impl;

import com.chale.wartermark.exception.WaterMarkException;
import com.chale.wartermark.model.MarkConfig;
import com.chale.wartermark.service.WaterMarkService;
import com.chale.wartermark.util.DFTUtil;
import com.chale.wartermark.util.ImageUtil;
import com.chale.wartermark.util.OpencvInit;
import com.chale.wartermark.util.WatermarkUtil;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangchaolei on 2017/6/17.
 */
public class WaterMarkServiceImpl implements WaterMarkService{

    private MarkConfig markConfig=MarkConfig.getInitConfig();
    private String path;
    private String fileName;

    public WaterMarkServiceImpl(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    @Override
    public void parseWatermarkForRedScale(String in, String out) {
        OpencvInit.init(path,fileName);

        Mat imageWithMark = Highgui.imread(in);
        List<Mat> planesWithMark = new ArrayList<Mat>();
        Core.split(imageWithMark, planesWithMark);
        Mat redImgWithMark = planesWithMark.get(1);

        Mat image = WatermarkUtil.generateImage(255, 255, 255, imageWithMark.cols(), imageWithMark.rows());
        List<Mat> planes = new ArrayList<Mat>();
        Core.split(image, planes);
        Mat redImg = planes.get(1);

        Mat dftWatermarkImg = DFTUtil.transformImageWithMark(redImgWithMark, redImg);
        Highgui.imwrite(out, dftWatermarkImg);
    }

    @Override
    public void joinWaterMark(String input,String mark, String output) throws WaterMarkException {
        OpencvInit.init(path,fileName);


        if(input==null) throw new WaterMarkException("输入文件地址为空");
        if(mark==null) throw new WaterMarkException("水印为空");
        if(output==null) throw new WaterMarkException("输出文件地址为空");

        byte[] inputBytes;
        try {
            inputBytes = ImageUtil.image2byte(input);
        } catch (Exception e) {
            throw new WaterMarkException("获取水印字节数组失败");
        }
        Mat sourceMat = WatermarkUtil.bytes2RGBMat(inputBytes);
        Mat markMat;
        Mat resultMat;

        try {
            markMat = WatermarkUtil.generateWatermarkImage(mark,
                    Integer.parseInt(markConfig.getFontSize()), Integer.parseInt(markConfig.getTextLeft()),
                    Integer.parseInt(markConfig.getTextTop()), sourceMat.cols(), sourceMat.rows());
        } catch (IOException e) {
            throw new WaterMarkException("生成水印图片失败");
        }
        Highgui.imwrite(output.replace(".jpg","-mark.jpg"), markMat);
        try {
            resultMat = WatermarkUtil.addWatermarkImg(sourceMat, markMat, 1000);
        } catch (IOException e) {
            throw new WaterMarkException("水印叠加失败");
        }

        Highgui.imwrite(output, resultMat);

    }


    public void setPath(String path) {
        this.path = path;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMarkConfig(MarkConfig markConfig) {
        this.markConfig = markConfig;
    }
}
