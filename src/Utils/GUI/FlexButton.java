package Utils.GUI;

import Base.GameFrame;
import Utils.OtherTool.IDIndex;
import Utils.OtherTool.ImageEditor;
import Utils.OtherTool.PlaySound;
import Utils.OtherTool.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static java.lang.Integer.min;

public class FlexButton {
    private GameFrame game;
    // 定义绘制坐标
    private int x;
    private int y;
    // 定义中心坐标，相对窗口中心
    private double xCenterRatio;
    private double yCenterRatio;
    // 定义宽高比率，相比于屏幕大小
    private double widthRatio;
    private double heightRatio;
    private BufferedImage textureNormal = Resource.getGuiTextureList().get(14);
    private BufferedImage textureChosen = Resource.getGuiTextureList().get(15);
    private BufferedImage textureNoPress = Resource.getGuiTextureList().get(16);
    private BufferedImage textureCurrent;
    private boolean chosen = false;
    private boolean canDelete = false;
    private boolean oncePress = false;
    // 定义不可被按下时间
    private int timeNoPress = 500;
    // 定义要显示的文字
    private String text;
    private String color;

    public FlexButton() {
    }

    public FlexButton(GameFrame game, double xCenterRatio, double yCenterRatio, double widthRatio, double heightRatio, String text, String color, boolean oncePress, int timeNoPress) {
        this.game = game;
        this.text = text;
        this.color = color;
        this.timeNoPress = timeNoPress;
        this.x = (int) (game.getWidth() * xCenterRatio) + game.getxCenter() - (int) ((game.getWidth() * widthRatio) / 2);
        this.y = (int) (game.getHeight() * yCenterRatio) + game.getyCenter() - (int) ((game.getHeight() * heightRatio) / 2);
        this.xCenterRatio = xCenterRatio;
        this.yCenterRatio = yCenterRatio;
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
        this.oncePress = oncePress;
        textureNormal = (BufferedImage) ImageEditor.imageScale(textureNormal, (int) (game.getWidth() * widthRatio), (int) (game.getHeight() * heightRatio));
        textureChosen = (BufferedImage) ImageEditor.imageScale(textureChosen, (int) (game.getWidth() * widthRatio), (int) (game.getHeight() * heightRatio));
        textureNoPress = (BufferedImage) ImageEditor.imageScale(textureNoPress, (int) (game.getWidth() * widthRatio), (int) (game.getHeight() * heightRatio));
        if (timeNoPress > 0) textureCurrent = textureNoPress;
        else textureCurrent = textureNormal;
    }

    // 更新文字
    public void updateDrawText() {
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        // 绘制文字
        if (text != null) {
            Graphics graphics = game.getOffScreenUiImage().getGraphics();
            int minValue = min(tmpWidth, tmpHeight);
            if (Objects.equals(color, "white"))
                graphics.setColor(Color.WHITE);
            graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (minValue / 2.3)));
            FontMetrics metrics = graphics.getFontMetrics();
            graphics.drawString(text, x + tmpWidth / 2 - metrics.stringWidth(text) / 2, y + (int) (tmpHeight + minValue / 3) / 2);
        }
    }

    public void updateButton() {
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        // 更新坐标位置
        x = (int) (game.getWidth() * xCenterRatio) + game.getxCenter() - tmpWidth / 2;
        y = (int) (game.getHeight() * yCenterRatio) + game.getyCenter() - tmpHeight / 2;
        // 更新材质大小
        textureNormal = (BufferedImage) ImageEditor.imageScale(textureNormal, tmpWidth, tmpHeight);
        textureChosen = (BufferedImage) ImageEditor.imageScale(textureChosen, tmpWidth, tmpHeight);
        textureNoPress = (BufferedImage) ImageEditor.imageScale(textureNoPress, tmpWidth, tmpHeight);
        // 更新按钮判断
        if (timeNoPress > 0) {
            textureCurrent = textureNoPress;
            timeNoPress--;
        } else {
            if (game.getMouseLocation().getX() > x && game.getMouseLocation().getX() < x + tmpWidth && game.getMouseLocation().getY() > y && game.getMouseLocation().getY() < y + tmpHeight) {
                textureCurrent = textureChosen;
                chosen = true;
            } else {
                textureCurrent = textureNormal;
                chosen = false;
            }
            if (chosen && game.isMouseLeftPressed()) {
                pressFunction();
                // 播放音效
                game.getSoundList().add(new PlaySound("gui", IDIndex.soundNameToID("gui", "click"), 0));
                // 点击按钮后
                timeNoPress = 30;
                if (oncePress)
                    canDelete = true;
            }
        }
    }

    public void pressFunction() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getWidthRatio() {
        return widthRatio;
    }

    public void setWidthRatio(int widthRatio) {
        this.widthRatio = widthRatio;
    }

    public double getHeightRatio() {
        return heightRatio;
    }

    public void setHeightRatio(int heightRatio) {
        this.heightRatio = heightRatio;
    }

    public GameFrame getGame() {
        return game;
    }

    public void setGame(GameFrame game) {
        this.game = game;
    }

    public BufferedImage getTextureNormal() {
        return textureNormal;
    }

    public void setTextureNormal(BufferedImage textureNormal) {
        this.textureNormal = textureNormal;
    }

    public BufferedImage getTextureChosen() {
        return textureChosen;
    }

    public void setTextureChosen(BufferedImage textureChosen) {
        this.textureChosen = textureChosen;
    }

    public BufferedImage getTextureCurrent() {
        return textureCurrent;
    }

    public void setTextureCurrent(BufferedImage textureCurrent) {
        this.textureCurrent = textureCurrent;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isOncePress() {
        return oncePress;
    }

    public void setOncePress(boolean oncePress) {
        this.oncePress = oncePress;
    }

    public double getxCenterRatio() {
        return xCenterRatio;
    }

    public void setxCenterRatio(double xCenterRatio) {
        this.xCenterRatio = xCenterRatio;
    }

    public double getyCenterRatio() {
        return yCenterRatio;
    }

    public void setyCenterRatio(double yCenterRatio) {
        this.yCenterRatio = yCenterRatio;
    }

    public void setWidthRatio(double widthRatio) {
        this.widthRatio = widthRatio;
    }

    public void setHeightRatio(double heightRatio) {
        this.heightRatio = heightRatio;
    }

    public BufferedImage getTextureNoPress() {
        return textureNoPress;
    }

    public void setTextureNoPress(BufferedImage textureNoPress) {
        this.textureNoPress = textureNoPress;
    }

    public int getTimeNoPress() {
        return timeNoPress;
    }

    public void setTimeNoPress(int timeNoPress) {
        this.timeNoPress = timeNoPress;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
