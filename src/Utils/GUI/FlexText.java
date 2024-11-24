package Utils.GUI;

import Base.GameFrame;
import Utils.OtherTool.Resource;

import java.awt.*;
import java.util.Objects;

import static java.lang.Integer.min;

public class FlexText {
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
    // 定义要显示的文字
    private String text;
    private String color;
    // 定义是否可以删除
    private boolean canDelete = false;
    // 定义自动消失时间，-1为不会自动消失
    private int deleteTimer = -1;

    public FlexText() {
    }

    public FlexText(GameFrame game, double xCenterRatio, double yCenterRatio, double widthRatio, double heightRatio, String text, String color, int deleteTimer) {
        this.game = game;
        this.text = text;
        this.color = color;
        this.deleteTimer = deleteTimer;
        this.x = (int) (game.getWidth() * xCenterRatio) + game.getxCenter() - (int) ((game.getWidth() * widthRatio) / 2);
        this.y = (int) (game.getHeight() * yCenterRatio) + game.getyCenter() - (int) ((game.getHeight() * heightRatio) / 2);
        this.xCenterRatio = xCenterRatio;
        this.yCenterRatio = yCenterRatio;
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
    }

    public void updateDrawText() {
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        // 绘制文字
        if (text != null) {
            String path = System.getProperty("user.dir") + "/data/resource/";
            Graphics graphics = game.getOffScreenUiImage().getGraphics();
            int minValue = min(tmpWidth, tmpHeight);
            if (Objects.equals(color, "white"))
                graphics.setColor(Color.WHITE);
            else if (Objects.equals(color, "yellow"))
                graphics.setColor(Color.YELLOW);
            else if (Objects.equals(color, "red"))
                graphics.setColor(new Color(220, 20, 60));
            else if (Objects.equals(color, "green"))
                graphics.setColor(Color.GREEN);
            graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (minValue / 2.3)));
            // 更新坐标位置
            x = (int) (game.getWidth() * xCenterRatio) + game.getxCenter() - tmpWidth / 2;
            y = (int) (game.getHeight() * yCenterRatio) + game.getyCenter() - tmpHeight / 2;
            FontMetrics metrics = graphics.getFontMetrics();
            graphics.drawString(text, x + tmpWidth / 2 - metrics.stringWidth(text) / 2, y + (int) (tmpHeight + minValue / 3) / 2);
        }
        if (deleteTimer != -1) {
            if (deleteTimer > 0) deleteTimer--;
            else if (deleteTimer == 0) {
                canDelete = true;
            }
        }
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

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public int getDeleteTimer() {
        return deleteTimer;
    }

    public void setDeleteTimer(int deleteTimer) {
        this.deleteTimer = deleteTimer;
    }
}
