package InputDetection;

import Base.GameFrame;
import EntityType.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DoubleKey {
    // 定义双击检测时间变量
    private long aLastPressTime = 0;
    private long dLastPressTime = 0;
    private long wLastPressTime = 0;
    private boolean aReleased = false;
    private boolean dReleased = false;
    private boolean wReleased = false;
    // 定义键入顺序列表
    List<String> adList = new ArrayList<>();
    // 定义游戏
    GameFrame game = null;
    // 定义玩家
    Player player = null;

    public DoubleKey() {
    }

    public KeyListener startListening(GameFrame game) {
        this.game = game;
        this.player = game.getPlayer();
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (game.getFrameSort() == 0 && !game.isTerminalOn()) {
                    // 判断是否正在跑
                    if (Objects.equals(game.getPlayer().getMoveState(), "run")) {
                        if (e.getKeyChar() == 'a') {
                            if (!adList.contains("a")) adList.add("a");
                        } else if (e.getKeyChar() == 'd') {
                            if (!adList.contains("d")) adList.add("d");
                        }
                    }

                    if (!game.isBackGui() && !player.isInParalysis() && player.isLogin()) {
                        // 判断a是否双击
                        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - aLastPressTime <= 300 && aReleased) {
                                // 在300毫秒内检测到两次按键，认为是双击
                                if (!adList.contains("a")) adList.add("a");
                            }
                            aLastPressTime = currentTime;
                            aReleased = false;
                        }
                        // 判断d是否双击
                        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - dLastPressTime <= 300 && dReleased) {
                                // 在300毫秒内检测到两次按键，认为是双击
                                if (!adList.contains("d")) adList.add("d");
                            }
                            dLastPressTime = currentTime;
                            dReleased = false;
                        }
                        // 判断w是否双击
                        if (!player.isDead() && e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - wLastPressTime <= 300 && wReleased) {
                                // 在300毫秒内检测到两次按键，认为是双击
                                if (Objects.equals(player.getGameMode(), "creative"))
                                    player.setFlying(!player.isFlying());
                            }
                            wLastPressTime = currentTime;
                            wReleased = false;
                        }
                    }
                }

                // 刷新玩家行为
                refresh();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (game.getFrameSort() == 0) {
                    // 停止向左跑
                    if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
                        aReleased = true;
                        adList.remove("a");
                    }
                    // 停止向右跑
                    if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                        dReleased = true;
                        adList.remove("d");
                    }
                    // 松开w
                    if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
                        wReleased = true;
                }

                // 刷新玩家行为
                refresh();
            }
        };
    }

    // 根据按键执行player行为
    public void refresh() {
        if (game.getFrameSort() == 0) {
            player = game.getPlayer();
            // 麻痹状态停止运动
            if (!adList.isEmpty() && (game.isBackGui() || player.isInParalysis())) {
                adList.remove("a");
                adList.remove("d");
                wReleased = true;
                player.setMoveState("stand");
            }
            // 如果列表非空
            if (!game.isBackGui() && !player.isDead() && !adList.isEmpty()) {
                // 如果玩家最后按下的是"a"
                if (Objects.equals(adList.get(adList.size() - 1), "a")) player.runLeft();
                    // 如果玩家最后按下的是"d"
                else if (Objects.equals(adList.get(adList.size() - 1), "d")) player.runRight();
            }
            // 否则停止移动
            else if (!game.isBackGui() && !player.isInParalysis() && Objects.equals(player.getMoveState(), "run")) {
                player.stopMoveX();
            }
        }
    }

    public long getaLastPressTime() {
        return aLastPressTime;
    }

    public void setaLastPressTime(long aLastPressTime) {
        this.aLastPressTime = aLastPressTime;
    }

    public long getdLastPressTime() {
        return dLastPressTime;
    }

    public void setdLastPressTime(long dLastPressTime) {
        this.dLastPressTime = dLastPressTime;
    }

    public boolean isaReleased() {
        return aReleased;
    }

    public void setaReleased(boolean aReleased) {
        this.aReleased = aReleased;
    }

    public boolean isdReleased() {
        return dReleased;
    }

    public void setdReleased(boolean dReleased) {
        this.dReleased = dReleased;
    }
}
