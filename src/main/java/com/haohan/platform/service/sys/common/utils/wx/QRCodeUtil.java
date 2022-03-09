package com.haohan.platform.service.sys.common.utils.wx;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {

    public static int FOREGROUND = Color.BLACK.getRGB();
    public static int BACKGROUND = Color.WHITE.getRGB();
    public static int WIDTH = 300;
    public static int HEIGHT = 300;
    public static String IMG_FORMAT = "png";

    /***
     * 输出二维码到文件
     *
     * @param content
     *            二维码内容
     * @param file
     *            目标文件位置
     */
    public static void encodeQrcode(String content, String file) {
        if (StringUtils.isBlank(content))
            return;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            try {
                ImageIO.write(image, IMG_FORMAT, new File(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    /***
     * 输出二维码到文件
     *
     * @param content
     *            二维码内容
     * @param file
     *            目标文件位置
     */
    public static void encodeQrcode(String content, File file) {
        if (StringUtils.isBlank(content))
            return;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            try {
                ImageIO.write(image, IMG_FORMAT, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    /***
     * 输出二维码到文件
     *
     * @param content
     *            二维码内容
     * @param file
     *            目标文件位置
     */
    public static void encodeQrcode(String content, File file, int width, int height) {
        if (StringUtils.isBlank(content))
            return;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            try {
                ImageIO.write(image, IMG_FORMAT, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    /***
     * 生成二维码 直接用于输出
     *
     * @param content
     */
    public static void encodeQrcode(String content, OutputStream os) {
        if (StringUtils.isBlank(content))
            return;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            // 输出二维码图片流
            try {
                ImageIO.write(image, IMG_FORMAT, os);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    /***
     * 生成二维码 直接用于输出
     *
     * @param content
     */
    public static void encodeQrcode(String content, OutputStream os, InputStream input) {
        if (StringUtils.isBlank(content))
            return;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
        hints.put(EncodeHintType.MARGIN, 0);

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            // 输出二维码图片流
            try {
                setLogo(image, input);
                ImageIO.write(image, IMG_FORMAT, os);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    /***
     * 二维码添加logo
     *
     * @param image
     */
    private static void setLogo(BufferedImage image, InputStream input) {
        try {
            Graphics2D g = image.createGraphics();
            BufferedImage logo = ImageIO.read(input);
            int widthLogo = logo.getWidth();
            int heightLogo = logo.getHeight();

            // 计算图片放置位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - logo.getHeight()) / 2;

            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            g.setStroke(new BasicStroke(2));
            g.setColor(new Color(FOREGROUND));
            g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 生成带logo的二维码
     *
     * @param content
     *            二维码内容
     * @param file
     *            输出文件位置
     *            二维码中间logo
     */
    public static void encodeQrcode(String content, String file, InputStream input) {
        if (StringUtils.isBlank(content))
            return;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
        hints.put(EncodeHintType.MARGIN, 0);

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            try {
                setLogo(image, input);
                ImageIO.write(image, IMG_FORMAT, new File(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? FOREGROUND : BACKGROUND);
            }
        }
        return image;
    }

    /***
     * 解析二维码
     *
     * @param file
     *            二维码文件
     * @return
     * @throws Exception
     */
    public static Map<String, String> decode(File file) throws Exception {
        // BufferedImage image = ImageIO.read(url);
        BufferedImage image = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);
        Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
        Map<String, String> res = new HashMap<>();
        res.put("result", result.toString());
        res.put("content", result.getText());
        // System.out.println("解析结果 = " + result.toString());
        // System.out.println("二维码格式类型 = " + result.getBarcodeFormat());
        // System.out.println("二维码文本内容 = " + result.getText());
        return res;
    }
}
