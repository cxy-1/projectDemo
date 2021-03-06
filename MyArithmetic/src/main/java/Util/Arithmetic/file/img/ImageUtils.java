/**
 *
 */
package Util.Arithmetic.file.img;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author lsx
 * @date 2017年10月30日 下午5:01:27
 */
public class ImageUtils {
    /**
     * 添加字体的属性设置
     */
    private Font font = new Font("微软雅黑", Font.PLAIN, 30);
    private Graphics2D g = null;
    private int fontsize = 0;
    private int x = 0;
    private int y = 0;

    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public BufferedImage loadImageUrl(String imgName) {
        try {
            System.out.println(imgName);
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(File outputfile, BufferedImage img) {
        if (outputfile != null && img != null) {
            try {
                ImageIO.write(img, "png", outputfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设定文字的字体等
     */
    public void setFont(String fontStyle, int fontSize) {
        this.fontsize = fontSize;
        this.font = new Font(fontStyle, Font.PLAIN, fontSize);
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    public BufferedImage modifyImage(BufferedImage img, Object content, int x, int y, Font font,
                                     Color color) {
        try {
            g = img.createGraphics();
            // 设置字体颜色
            g.setColor(color);
            if (this.font != null) {
                g.setFont(font);
            }
            // 验证输出位置的纵坐标和横坐标
            this.x = x;
            this.y = y;
            if (content != null) {
                g.drawString(content.toString(), this.x, this.y);
            }
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出
     */
    public BufferedImage modifyImage(BufferedImage img, Object[] contentArr, int x, int y,
                                     boolean xory) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.RED);
            if (this.font != null) {
                g.setFont(this.font);
            }
            // 验证输出位置的纵坐标和横坐标
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (contentArr != null) {
                int arrlen = contentArr.length;
                if (xory) {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.x += contentArr[i].toString().length()
                                // 重新计算文本输出位置
                                * this.fontsize / 2 + 5;
                    }
                } else {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        // 重新计算文本输出位置
                        this.y += this.fontsize + 2;
                    }
                }
            }
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     * <p>
     * 时间:2007-10-8
     *
     * @param img
     * @return
     */
    public BufferedImage modifyImageYe(BufferedImage img) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            // 设置字体颜色
            g.setColor(Color.blue);
            if (this.font != null) {
                g.setFont(this.font);
            }
            g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * 图片整合（图片放到图片中）
     *
     * @param b
     * @param d
     * @return
     * @author lsx
     * @date 2017年10月30日 下午4:55:51
     */
    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d, int x, int y, int w,
                                            int h) {
        try {
            g = d.createGraphics();
            g.drawImage(b, x, y, w, h, null);
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static BufferedImage resize(String filepath, int targetW, int targetH) throws Exception {
        return resize(filepath, targetW, targetH, true);
    }

    public static BufferedImage resize(String filepath, int targetW, int targetH, boolean w)
            throws Exception {
        BufferedImage source = ImageIO.read(new FileInputStream(filepath));
        return resize(source, targetW, targetH, w);
    }

    public static BufferedImage resize(BufferedImage source, int targetW, int targetH, boolean w) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        // 以宽度为准等比缩放
        if (w) {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        } else {// 以高度为准等比缩放
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        }
        /*
         * if (sx < sy) { sx = sy; targetW = (int) (sx * source.getWidth()); } else { sy
         * = sx; targetH = (int) (sy * source.getHeight()); }
         */
        // handmade
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
