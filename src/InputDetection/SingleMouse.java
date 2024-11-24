package InputDetection;

import Base.GameFrame;
import EntityType.Player;
import Utils.OtherTool.IDIndex;
import Utils.OtherTool.PlaySound;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SingleMouse {
    private boolean mouseMiddlePress = false;
    private boolean mouseMiddleRelease = true;
    private boolean mouseMiddleClicked = false;
    private boolean mouseRightPress = false;
    // 定义右键键计时器
    int rightPressTimer = 0;
    int rightPressTimerTotal = 0;

    private GameFrame game = null;
    private Player player = null;

    public MouseListener startListening(GameFrame game) {
        this.game = game;
        this.player = game.getPlayer();
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!game.isTerminalOn()) {
                    int x = e.getX();
                    int y = e.getY();
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (game.getFrameSort() == 0) {
                            game.getPlayer().setMouseLeftPress(true);
                        }
                        game.setMouseLeftPressed(true);
                    }
                    if (e.getButton() == MouseEvent.BUTTON2) {
                        mouseMiddleRelease = false;
                        mouseMiddlePress = true;
                        game.setMouseMiddlePressed(true);
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        if (game.getFrameSort() == 0) {
                            game.getPlayer().setMouseRightPress(true);
                            mouseRightPress = true;
                        }
                        game.setMouseRightPressed(true);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (game.getFrameSort() == 0) {
                        game.getPlayer().setMouseLeftPress(false);
                    }
                    game.setMouseLeftPressed(false);
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    mouseMiddlePress = false;
                    mouseMiddleRelease = true;
                    game.setMouseMiddlePressed(false);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (game.getFrameSort() == 0) {
                        game.getPlayer().setMouseRightPress(false);
                        mouseRightPress = false;
                    }
                    game.setMouseRightPressed(false);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public void refresh() {
        if (mouseMiddlePress && !mouseMiddleClicked && !game.isTerminalOn()) {
            if (game.getFrameSort() == 0) {
                player = game.getPlayer();
                player.getWorld().mouseMiddlePress();
            }
            if (!game.isBackGui() && !player.isDead())
                mouseMiddleClicked = true;
        }
        if (mouseMiddleRelease && !game.isTerminalOn()) mouseMiddleClicked = false;
        if (game.getFrameSort() == 0 && !game.isTerminalOn()) {
            player = game.getPlayer();
            if (player != null && IDIndex.blockIdToIsFood(player.getItemBarId()[player.getItemBarChosen()]) && player.getHealth() < player.getHealthMax()) {
                if (mouseRightPress && rightPressTimer == 35) {
                    player.getSoundList().add(new PlaySound("player", IDIndex.soundNameToID("player", "eat"), 100));
                    rightPressTimer = 0;
                    rightPressTimerTotal += 35;
                } else if (mouseRightPress) {
                    rightPressTimer++;
                } else {
                    rightPressTimer = 0;
                    rightPressTimerTotal = 0;
                }
                if (rightPressTimerTotal >= 300) {
                    player.eat();
                    rightPressTimer = 0;
                    rightPressTimerTotal = 0;
                }
            }
        }
    }

    public boolean isMouseMiddlePress() {
        return mouseMiddlePress;
    }

    public void setMouseMiddlePress(boolean mouseMiddlePress) {
        this.mouseMiddlePress = mouseMiddlePress;
    }

    public boolean isMouseMiddleRelease() {
        return mouseMiddleRelease;
    }

    public void setMouseMiddleRelease(boolean mouseMiddleRelease) {
        this.mouseMiddleRelease = mouseMiddleRelease;
    }

    public boolean isMouseMiddleClicked() {
        return mouseMiddleClicked;
    }

    public void setMouseMiddleClicked(boolean mouseMiddleClicked) {
        this.mouseMiddleClicked = mouseMiddleClicked;
    }

    public GameFrame getGame() {
        return game;
    }

    public void setGame(GameFrame game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}