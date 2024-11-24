package InputDetection;

import Base.GameFrame;
import Utils.TCP.TCPClient;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowListen {
    public WindowListener startListening(GameFrame game) {
        return new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (game.getFrameSort() == 0 && !game.isMultiPlayerMode()) {
                    game.getWorldCurrent().getWorldSaver().saveWorld(game.getWorldName(), game.getWorldCurrent());
                } else if (game.isMultiPlayerMode()) {
                    game.setGamePaused(false);
                    game.clearGUI();
                    game.stopMusic();
                    if (game.getWorldCurrent() != null)
                        game.getWorldCurrent().threadInterrupt();
                    game.setPlayer(null);
                    game.setWorldCurrent(null);
                    game.setMouseLeftPressed(false);
                    game.setMouseRightPressed(false);
                    game.setBackGui(false);
                    TCPClient.closeTCP();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };
    }
}
