package Utils.GUI;

import Base.GameFrame;
import Utils.OtherTool.ImageEditor;

import java.awt.image.BufferedImage;

public class FlexImage {
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
    private BufferedImage texture;
    private boolean canDelete = false;
    // 定义不可被按下时间
    private int timeNoPress = 0;

    public FlexImage() {
    }

    public FlexImage(GameFrame game, double xCenterRatio, double yCenterRatio, double widthRatio, double heightRatio, BufferedImage texture) {
        this.game = game;
        this.x = (int) (game.getWidth() * xCenterRatio) + game.getxCenter() - (int) ((game.getWidth() * widthRatio) / 2);
        this.y = (int) (game.getHeight() * yCenterRatio) + game.getyCenter() - (int) ((game.getHeight() * heightRatio) / 2);
        this.xCenterRatio = xCenterRatio;
        this.yCenterRatio = yCenterRatio;
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
        this.texture = texture;
        updateImage();
    }

    public void updateImage() {
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        // 更新坐标位置
        x = (int) (game.getWidth() * xCenterRatio) + game.getxCenter() - tmpWidth / 2;
        y = (int) (game.getHeight() * yCenterRatio) + game.getyCenter() - tmpHeight / 2;
        // 更新材质大小
        texture = (BufferedImage) ImageEditor.imageScale(texture, tmpWidth, tmpHeight);
        if (timeNoPress > 0) {
            timeNoPress--;
        } else if (game.isMouseLeftPressed()) {
            if (game.getMouseLocation().getX() > x && game.getMouseLocation().getX() < x + tmpWidth && game.getMouseLocation().getY() > y && game.getMouseLocation().getY() < y + tmpHeight)
                pressFunction();
            else pressOutFunction();
        }
    }

    public void pressFunction() {

    }

    public void pressOutFunction() {

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

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public int getTimeNoPress() {
        return timeNoPress;
    }

    public void setTimeNoPress(int timeNoPress) {
        this.timeNoPress = timeNoPress;
    }
}
