package InputDetection;

import Base.GameFrame;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheel {
    private GameFrame game = null;

    public MouseWheelListener startListening(GameFrame game) {
        this.game = game;
        return new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (game.getFrameSort() == 0 && !game.isBackGui() && !game.isTerminalOn()) {
                    if (!game.getPlayer().isDead()) {
                        if (!game.isCtrlPressed()) {
                            if (e.getWheelRotation() == -1) {
                                if (game.getPlayer().getItemBarChosen() > 0)
                                    game.getPlayer().setItemBarChosen(game.getPlayer().getItemBarChosen() - 1);
                                if (game.getPlayer().getItemBarChosen() == -1)
                                    game.getPlayer().setItemBarChosen(8);
                                if (game.isMultiPlayerMode())
                                    game.getWorldCurrent().updatePlayerItemDataToServer(game.getPlayer());
                            }
                            if (e.getWheelRotation() == 1) {
                                if (game.getPlayer().getItemBarChosen() < 8)
                                    game.getPlayer().setItemBarChosen(game.getPlayer().getItemBarChosen() + 1);
                                if (game.getPlayer().getItemBarChosen() == 9)
                                    game.getPlayer().setItemBarChosen(0);
                                if (game.isMultiPlayerMode())
                                    game.getWorldCurrent().updatePlayerItemDataToServer(game.getPlayer());
                            }
                        } else if (!game.isShiftPressed()) {
                            if (e.getWheelRotation() == 1 && game.getZoomGameRatio() >= 1.1)
                                game.setZoomGameRatio(game.getZoomGameRatio() - 0.1);
                            if (e.getWheelRotation() == -1 && game.getZoomGameRatio() <= 2.4)
                                game.setZoomGameRatio(game.getZoomGameRatio() + 0.1);
                        } else {
                            if (e.getWheelRotation() == 1 && game.getZoomGuiRatio() >= 1.1)
                                game.setZoomGuiRatio(game.getZoomGuiRatio() - 0.1);
                            if (e.getWheelRotation() == -1 && game.getZoomGuiRatio() <= 2.4)
                                game.setZoomGuiRatio(game.getZoomGuiRatio() + 0.1);
                        }
                    }
                }
            }
        };
    }
}
