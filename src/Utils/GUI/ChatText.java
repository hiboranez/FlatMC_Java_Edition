package Utils.GUI;

import Base.GameFrame;
import Utils.OtherTool.Resource;

import java.awt.*;
import java.util.Objects;

import static java.lang.Integer.min;

public class ChatText {
    private GameFrame game;
    // 定义绘制坐标
    private int x;
    private int y;
    // 定义中心坐标，相对窗口中心
    private double xLeftSideRatio = -0.49;
    private double yUpSideRatio = -0.47;
    // 定义宽高比率，相比于屏幕大小
    private double widthRatio = 0.06;
    private double heightRatio = 0.06;
    // 定义要显示的文字
    private String text;
    private String color;
    // 定义是否可以删除
    private boolean canDelete = false;
    // 定义自动消失时间，-1为不会自动消失
    private int deleteTimer = -1;
    private int alpha = 230;
    // 定义是否是输入端
    private boolean terminalText = false;

    public ChatText() {
    }

    public ChatText(GameFrame game, String text, String color, int deleteTimer, Boolean terminalText) {
        this.game = game;
        this.text = text;
        this.color = color;
        this.deleteTimer = deleteTimer;
        this.x = (int) (game.getWidth() * xLeftSideRatio) + game.getxCenter() - (int) ((game.getWidth() * widthRatio) / 2);
        this.y = (int) (game.getHeight() * yUpSideRatio) + game.getyCenter() - (int) ((game.getHeight() * heightRatio) / 2);
        this.terminalText = terminalText;
    }

    public void updateDrawText() {
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        int decline = (int) (46 * (deleteTimer - 5));
        if (decline >= 0) decline = 0;
        alpha = 230 + decline;
        // 绘制文字
        if (text != null || terminalText) {
            String path = System.getProperty("user.dir") + "/data/resource/";
            Graphics graphics = game.getOffScreenUiImage().getGraphics();
            int id = -1;
            x = 22;
            int size = game.getChatTextList().size();
            for (int i = 0; i < size; i++) {
                if (game.getChatTextList().get(i).equals(this) && size - i <= 7) {
                    id = i;
                    y = (int) (game.getHeight() * 0.04 * (8 - size + i)) + 77;
                    break;
                }
            }
            if (terminalText) {
                id = 7;
                y = (int) (game.getHeight() * 0.04 * 8) + 77;
            }
            if (terminalText) {
                int declineBack = (int) (26 * (deleteTimer - 5));
                if (declineBack >= 0) declineBack = 0;
                int alphaBack = 130 + declineBack;
                graphics.setColor(new Color(0, 0, 0, alphaBack));
            } else {
                int declineBack = (int) (20 * (deleteTimer - 5));
                if (declineBack >= 0) declineBack = 0;
                int alphaBack = 100 + declineBack;
                graphics.setColor(new Color(0, 0, 0, alphaBack));
            }
            int minValue = min(tmpWidth, tmpHeight);
            graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (minValue / 2.3)));
            FontMetrics metrics = graphics.getFontMetrics();
            if (id != -1 && deleteTimer > 0) {
                graphics.fillRect(x - 6, y - (int) (minValue / 1.45 * 0.74), (int) (0.4 * game.getWidth()), (int) (minValue / 1.45));
            }
            if (Objects.equals(color, "white"))
                graphics.setColor(new Color(255, 255, 255, alpha));
            else if (Objects.equals(color, "yellow"))
                graphics.setColor(new Color(255, 255, 0, alpha));
            else if (Objects.equals(color, "red"))
                graphics.setColor(new Color(255, 20, 60, alpha));
            else if (Objects.equals(color, "green"))
                graphics.setColor(new Color(0, 255, 0, alpha));
            else if (Objects.equals(color, "orange"))
                graphics.setColor(new Color(255, 165, 0, alpha));
            else if (Objects.equals(color, "blue"))
                graphics.setColor(new Color(0, 200, 255, alpha));
            if (terminalText && !text.isEmpty()) {
                int i = 1;
                while (metrics.stringWidth(text.substring(0, i)) < 0.38 * game.getWidth()) {
                    if (text.length() == i) break;
                    i++;
                }
                graphics.drawString(text.substring(text.length() - i, text.length()), x, y);
            }
            // 更新坐标位置
            for (int i = 0; i < size; i++) {
                if (text.isEmpty()) continue;
                int textLength = 1;
                while (metrics.stringWidth(text.substring(0, textLength)) < 0.38 * game.getWidth()) {
                    if (text.length() == textLength) break;
                    textLength++;
                }
                if (game.getChatTextList().get(i).equals(this) && size - i <= 7) {
                    graphics.drawString(text.substring(0, textLength), x, y);
                }
            }
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

    public double getxLeftSideRatio() {
        return xLeftSideRatio;
    }

    public void setxLeftSideRatio(double xLeftSideRatio) {
        this.xLeftSideRatio = xLeftSideRatio;
    }

    public double getyUpSideRatio() {
        return yUpSideRatio;
    }

    public void setyUpSideRatio(double yUpSideRatio) {
        this.yUpSideRatio = yUpSideRatio;
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

    public boolean isTerminalText() {
        return terminalText;
    }

    public void setTerminalText(boolean terminalText) {
        this.terminalText = terminalText;
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

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}
