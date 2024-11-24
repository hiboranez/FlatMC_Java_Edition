package InputDetection;

import javax.swing.*;
import java.awt.*;

public class MouseFollow {
    public MouseFollow() {

    }

    // 获取相对于 JFrame 左上角的鼠标X坐标
    public int getMouseX(JFrame frame) {
        if (frame != null) {
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            Point frameLocation = frame.getLocationOnScreen();
            return (int) (mouseLocation.getX() - frameLocation.getX());
        }
        return 0;
    }

    // 获取相对于 JFrame 左上角的鼠标Y坐标
    public int getMouseY(JFrame frame) {
        if (frame != null) {
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            Point frameLocation = frame.getLocationOnScreen();
            return (int) (mouseLocation.getY() - frameLocation.getY());
        }
        return 0;
    }
}