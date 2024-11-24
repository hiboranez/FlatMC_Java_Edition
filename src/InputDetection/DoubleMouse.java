package InputDetection;

import Base.GameFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DoubleMouse {
    private long lastPressTime = 0;
    private boolean mouseReleased = false;

    public DoubleMouse() {
    }

    public MouseListener startListening(GameFrame game) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastPressTime <= 100 && mouseReleased) {
                    // 在300毫秒内检测到两次按键，认为是双击
                }
                lastPressTime = currentTime;
                mouseReleased = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleased = true;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }

                ;
    }

    public long getLastPressTime() {
        return lastPressTime;
    }

    public void setLastPressTime(long lastPressTime) {
        this.lastPressTime = lastPressTime;
    }

    public boolean isMouseReleased() {
        return mouseReleased;
    }

    public void setMouseReleased(boolean mouseReleased) {
        this.mouseReleased = mouseReleased;
    }
}
