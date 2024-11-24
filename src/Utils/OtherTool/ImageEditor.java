package Utils.OtherTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageEditor {
    public static Image imageScale(Image image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return scaledImage;
    }

    public static Image imageRotate(Image image, int angle) {
        double radian = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radian));
        double cos = Math.abs(Math.cos(radian));
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        int newWidth = (int) Math.floor(width * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.translate((newWidth - width) / 2, (newHeight - height) / 2);
        g2d.rotate(radian, width / 2, height / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotatedImage;
    }

    public static Image imageReverse(Image image, String type) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage reversedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = reversedImage.createGraphics();
        if (type.equals("horizontal")) {
            g2d.scale(-1, 1);
            g2d.drawImage(image, -width, 0, null);
        } else if (type.equals("vertical")) {
            g2d.scale(1, -1);
            g2d.drawImage(image, 0, -height, null);
        }
        g2d.dispose();
        return reversedImage;
    }

    public static Image imageStain(Image image, Color color, int level) {
        BufferedImage stainedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = stainedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), level));
        g2d.fillRect(0, 0, stainedImage.getWidth(), stainedImage.getHeight());
        g2d.dispose();
        return stainedImage;
    }

    public static Image imageTransparent(Image image, int alpha) {
        BufferedImage transparentImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = transparentImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        for (int x = 0; x < transparentImage.getWidth(); x++) {
            for (int y = 0; y < transparentImage.getHeight(); y++) {
                int argb = transparentImage.getRGB(x, y);
                int alphaChannel = (argb >> 24) & 0xFF;
                if (alphaChannel != 0) {
                    int newArgb = (alpha << 24) | (argb & 0x00FFFFFF);
                    transparentImage.setRGB(x, y, newArgb);
                }
            }
        }
        g2d.dispose();
        return transparentImage;
    }
}