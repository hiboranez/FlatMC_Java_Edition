package InputDetection;

import Base.GameFrame;
import EntityType.Player;
import Utils.GUI.FlexGrid;
import Utils.OtherTool.IDIndex;
import Utils.OtherTool.WordList;
import Utils.TCP.Command;
import Utils.TCP.TCPClient;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class SingleKey {
    // ★定义游戏
    GameFrame game = null;
    // ★定义玩家
    Player player = null;
    // 定义q键计时器，防止过快
    int qTimer = 0;
    // 定义键入顺序列表
    List<String> adList = new ArrayList<>();
    // 定义是否按下w键
    boolean wPressed = false;
    // 定义是否按下q键
    boolean qPressed = false;
    // 定义是否暂停
    boolean isPaused = true;
    // 定义键盘监听器
    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (!isPaused) {
                char keyChar = e.getKeyChar();

                if (keyChar == KeyEvent.VK_ESCAPE) {
                    game.removeKeyListener(keyListener);
                    game.getInputStringBuilder().setLength(0);
                    game.getTerminalText().setText("");
                    game.setTerminalOn(false);
                    isPaused = true;
                } else if (keyChar == KeyEvent.VK_BACK_SPACE) {
                    // 按下退格键，删除最后一个字符
                    if (game.getInputStringBuilder().length() > 0) {
                        game.getInputStringBuilder().deleteCharAt(game.getInputStringBuilder().length() - 1);
                    }
                } else if (keyChar == KeyEvent.VK_ENTER) {
                    // 按下回车键，发送消息
                    if (game.getInputStringBuilder().length() > 0) {
                        game.removeKeyListener(keyListener);
                        String string = game.getInputStringBuilder().toString();
                        string = "[" + player.getName() + "] " + string;
                        if (game.isMultiPlayerMode()) {
                            TCPClient.sendMessageToServer("/updateText " + string + "\n", player.getName());
                        } else {
                            String[] parts = string.split(" ");
                            if (parts.length >= 2 && parts[1].charAt(0) == '/') {
                                System.out.println(string.substring(("[" + player.getName() + "] ").length()));
                                Command.ReceivedFromClientCommand(string.substring(("[" + player.getName() + "] ").length()));
                            } else {
                                Command.showText(string, "white");
                                if (Command.printInformation) System.out.println(string);
                            }
                        }
                        game.getInputStringBuilder().setLength(0);
                        game.getTerminalText().setText(null);
                        game.setTerminalOn(false);
                        isPaused = true;
                    } else {
                        game.removeKeyListener(keyListener);
                        game.getInputStringBuilder().setLength(0);
                        game.getTerminalText().setText("");
                        game.setTerminalOn(false);
                        isPaused = true;
                    }
                } else {
                    // 追加字符到字符串并更新显示
                    game.getInputStringBuilder().append(keyChar);
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // 不需要处理
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // 不需要处理
        }
    };

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
                    if (!game.isBackGui() && !player.isInParalysis() && player.isLogin()) {
                        // 向左移动
                        if ((e.getKeyChar() == 'a' || e.getKeyChar() == 'A') && !adList.contains("a"))
                            adList.add("a");

                        // 向右移动
                        if ((e.getKeyChar() == 'd' || e.getKeyChar() == 'D') && !adList.contains("d"))
                            adList.add("d");

                        // 跳跃
                        if ((e.getKeyChar() == 'w' || e.getKeyChar() == 'W')) {
                            wPressed = true;
                            game.getPlayer().setwPressed(true);
                        }

                        // 开始向下飞
                        if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
                            game.getPlayer().setsPressed(true);
                        }
                    }

                    if (!player.isDead() && !player.isInParalysis()) {
                        // 丢弃物品
                        if ((e.getKeyChar() == 'q' || e.getKeyChar() == 'Q')) {
                            qPressed = true;
                        }
                    }

                    if (!game.isBackGui()) {
                        // 开启/关闭攻击模式
                        if ((e.getKeyChar() == 'x' || e.getKeyChar() == 'X')) {
                            player.setAttackMode(!player.isAttackMode());
                        }

                        // 开启/关闭终端
                        if ((e.getKeyChar() == 't' || e.getKeyChar() == 'T')) {
                            game.setTerminalOn(!game.isTerminalOn());
                            if (game.isTerminalOn()) {
                                game.addKeyListener(keyListener);
                                Timer timer = new Timer();
                                TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        isPaused = false;
                                        // 取消计时器
                                        timer.cancel();
                                    }
                                };
                                timer.schedule(task, 5);
                            }
                        }

                        // 开启/关闭终端
                        if (e.getKeyChar() == '/') {
                            game.setTerminalOn(!game.isTerminalOn());
                            if (game.isTerminalOn()) {
                                game.addKeyListener(keyListener);
                                Timer timer = new Timer();
                                TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        isPaused = false;
                                        game.getInputStringBuilder().append('/');
                                        // 取消计时器
                                        timer.cancel();
                                    }
                                };
                                timer.schedule(task, 5);
                            }
                        }

                        // 开闭GUI
                        if (e.getKeyCode() == 112 && !player.isDead())
                            if (!game.getPlayer().isDead())
                                game.setGuiOn(!game.isGuiOn());

                        // 更改玩家物品栏
                        if (e.getKeyCode() >= 49 && e.getKeyCode() <= 57 && !player.isDead())
                            game.getPlayer().setItemBarChosen(e.getKeyCode() - 49);

                        // 更新选中框显示状态
                        if (e.getKeyCode() == 113 && !player.isDead())
                            game.setShowBlockSelection1(!game.isShowBlockSelection1());

                        // 显示/隐藏游戏信息
                        if (e.getKeyCode() == 114 && !player.isDead())
                            game.setInfoOn(!game.isInfoOn());

                        // 更改模式
                        if (e.getKeyCode() == 115 && !player.isDead()) {
                            if (Objects.equals(game.getPlayer().getGameMode(), "creative")) {
                                if (!game.isMultiPlayerMode()) {
                                    game.getPlayer().setGameMode("survival");
                                    Command.showText(player.getName() + WordList.getWord(51, game.getLanguage()) + "survival", "green");
                                } else {
                                    TCPClient.sendMessageToServer("/gamemode " + player.getName() + " survival\n", player.getName());
                                }
                            } else if (Objects.equals(game.getPlayer().getGameMode(), "survival"))
                                if (!game.isMultiPlayerMode()) {
                                    game.getPlayer().setGameMode("creative");
                                    Command.showText(player.getName() + WordList.getWord(51, game.getLanguage()) + "creative", "green");
                                } else {
                                    TCPClient.sendMessageToServer("/gamemode " + player.getName() + " creative\n", player.getName());
                                }
                        }

                        // 清屏
                        if (e.getKeyCode() == 116 && !player.isDead())
                            game.getChatTextList().clear();
                        // 显示/隐藏当前时间
                        if (e.getKeyCode() == 117 && !player.isDead())
                            game.setTimeVisible(!game.isTimeVisible());
                        // 显示/隐藏实体血条
                        if (e.getKeyCode() == 118 && !player.isDead())
                            game.setHealthBarVisible(!game.isHealthBarVisible());
                        // 恢复缩放
                        if (e.getKeyCode() == 119 && !player.isDead()) {
                            game.setZoomGameRatio(1.0);
                            game.setZoomGuiRatio(1.5);
                        }

                        // 显示/隐藏名称
                        if (e.getKeyCode() == 120 && !player.isDead())
                            game.setNameOn(!game.isNameOn());
                    }

                    // 按下Esc游戏暂停
                    if (e.getKeyCode() == 27) {
                        if (!player.isDead()) {
                            if (game.isCraftingTableOpened()) {
                                game.loseItemInCraftingTable();
                                game.clearGUI();
                                game.setCraftingTableOpened(false);
                                game.setBackGui(false);
                            } else if (game.isInventoryOpened()) {
                                game.loseItemInCraftingTable();
                                game.clearGUI();
                                game.setInventoryOpened(false);
                                game.setBackGui(false);
                            } else if (!game.isGamePaused())
                                game.gamePause();
                            else {
                                game.clearGUI();
                                game.setGamePaused(false);
                                game.setBackGui(false);
                            }
                        }
                    }

                    // 按下e打开物品栏
                    if (e.getKeyChar() == 'e' || e.getKeyChar() == 'E') {
                        if (!player.isDead() && !game.isGamePaused()) {
                            if (game.isCraftingTableOpened()) {
                                game.loseItemInCraftingTable();
                                game.clearGUI();
                                game.setCraftingTableOpened(false);
                                game.setBackGui(false);
                            } else if (!game.isInventoryOpened()) {
                                game.openInventory();
                            } else {
                                game.loseItemInCraftingTable();
                                game.clearGUI();
                                game.setInventoryOpened(false);
                                game.setBackGui(false);
                            }
                        }
                    }

                    // 按shift
                    if (e.getKeyCode() == 16) {
                        game.setShiftPressed(true);
                    }

                    // 按ctrl
                    if (e.getKeyCode() == 17) {
                        game.setCtrlPressed(true);
                    }

                    // 按~
                    if (e.getKeyCode() == 192) {
                        game.setShowOnlinePlayerList(true);
                    }
                }

                // 全屏
                if (e.getKeyCode() == 122) {
                    if (!game.isFullScreen()) {
                        game.setFullScreen(true);
                        game.controlFullScreen();
                    } else {
                        game.setFullScreen(false);
                        game.controlFullScreen();
                    }
                }

                // 刷新玩家行为
                refresh();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (game.getFrameSort() == 0) {
                    // 停止向左
                    if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') adList.remove("a");

                    // 停止向右
                    if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') adList.remove("d");

                    // 停止跳跃
                    if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
                        wPressed = false;
                        game.getPlayer().setwPressed(false);
                    }

                    // 停止丢弃物品
                    if ((e.getKeyChar() == 'q' || e.getKeyChar() == 'Q')) {
                        qPressed = false;
                    }

                    // 停止向下飞
                    if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
                        game.getPlayer().setsPressed(false);
                    }

                    // 按shift
                    if (e.getKeyCode() == 16) {
                        game.setShiftPressed(false);
                    }

                    // 按ctrl
                    if (e.getKeyCode() == 17) {
                        game.setCtrlPressed(false);
                    }

                    // 按~
                    if (e.getKeyCode() == 192) {
                        game.setShowOnlinePlayerList(false);
                    }
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
                wPressed = false;
                player.setMoveState("stand");
            }
            if (!player.isDead()) {
                // 如果列表非空且玩家状态不为奔跑
                if (adList.size() - 1 >= 0 && !adList.isEmpty() && !Objects.equals(player.getMoveState(), "run")) {
                    // 如果玩家最后按下的是"a"
                    if (Objects.equals(adList.get(adList.size() - 1), "a")) player.walkLeft();
                        // 如果玩家最后按下的是"d"
                    else if (Objects.equals(adList.get(adList.size() - 1), "d")) player.walkRight();
                }
                // 否则如果玩家处于走路状态
                else if (!game.isBackGui() && !player.isInParalysis() && Objects.equals(player.getMoveState(), "walk"))
                    player.stopMoveX();
                // 跳跃
                if (wPressed) player.jump();
                // 丢弃物品
                if (game.isBackGui()) {
                    int id = -1;
                    int sort = -1;
                    int amount = 0;
                    for (FlexGrid flexGrid : game.getFlexGridList()) {
                        if (flexGrid.isChosen()) {
                            id = flexGrid.getId();
                            sort = flexGrid.getSort();
                            amount = flexGrid.getAmount();
                            break;
                        }
                    }
                    if (id != -1) {
                        if (qPressed) qTimer++;
                        else if (qTimer != 0 && qTimer <= 100) {
                            if (Objects.equals(player.getFaceTo(), "left"))
                                if (IDIndex.blockIdToIsTool(id)) {
                                    player.loseItem(id, amount, -9, true);
                                    for (FlexGrid flexGrid : game.getFlexGridList()) {
                                        if (flexGrid.getSort() == sort) {
                                            flexGrid.setAmount(0);
                                            flexGrid.setId(-1);
                                            flexGrid.updateItemBar();
                                            break;
                                        }
                                    }
                                } else {
                                    player.loseItem(id, 1, -9, true);
                                    for (FlexGrid flexGrid : game.getFlexGridList()) {
                                        if (flexGrid.getSort() == sort) {
                                            flexGrid.setAmount(flexGrid.getAmount() - 1);
                                            if (flexGrid.getAmount() == 0) flexGrid.setId(-1);
                                            flexGrid.updateItemBar();
                                            break;
                                        }
                                    }
                                }
                            else if (Objects.equals(player.getFaceTo(), "right"))
                                if (IDIndex.blockIdToIsTool(id)) {
                                    player.loseItem(id, amount, 9, true);
                                    for (FlexGrid flexGrid : game.getFlexGridList()) {
                                        if (flexGrid.getSort() == sort) {
                                            flexGrid.setAmount(0);
                                            flexGrid.setId(-1);
                                            flexGrid.updateItemBar();
                                            break;
                                        }
                                    }
                                } else {
                                    player.loseItem(id, 1, 9, true);
                                    for (FlexGrid flexGrid : game.getFlexGridList()) {
                                        if (flexGrid.getSort() == sort) {
                                            flexGrid.setAmount(flexGrid.getAmount() - 1);
                                            if (flexGrid.getAmount() == 0) flexGrid.setId(-1);
                                            flexGrid.updateItemBar();
                                            break;
                                        }
                                    }
                                }
                            qTimer = 0;
                        }
                        if (qTimer != 0 && qTimer > 100) {
                            if (Objects.equals(player.getFaceTo(), "left"))
                                player.loseItem(id, amount, -9, true);
                            else if (Objects.equals(player.getFaceTo(), "right"))
                                player.loseItem(id, amount, 9, true);
                            qTimer = 0;
                            for (FlexGrid flexGrid : game.getFlexGridList()) {
                                if (flexGrid.getSort() == sort) {
                                    flexGrid.setAmount(0);
                                    flexGrid.setId(-1);
                                    flexGrid.updateItemBar();
                                    break;
                                }
                            }
                        }
                    }
                } else if (player.getItemBarId()[player.getItemBarChosen()] != -1) {
                    if (qPressed) qTimer++;
                    else {
                        if (qTimer != 0 && qTimer <= 100) {
                            if (Objects.equals(player.getFaceTo(), "left"))
                                if (IDIndex.blockIdToIsTool(player.getItemBarId()[player.getItemBarChosen()]))
                                    player.dropItem(player.getItemBarChosen(), player.getItemBarAmount()[player.getItemBarChosen()], -9, true);
                                else player.dropItem(player.getItemBarChosen(), 1, -9, true);
                            else if (Objects.equals(player.getFaceTo(), "right"))
                                if (IDIndex.blockIdToIsTool(player.getItemBarId()[player.getItemBarChosen()]))
                                    player.dropItem(player.getItemBarChosen(), player.getItemBarAmount()[player.getItemBarChosen()], 9, true);
                                else player.dropItem(player.getItemBarChosen(), 1, 9, true);
                            qTimer = 0;
                        }
                    }
                    if (qTimer != 0 && qTimer > 100) {
                        if (Objects.equals(player.getFaceTo(), "left"))
                            player.dropItem(player.getItemBarChosen(), player.getItemBarAmount()[player.getItemBarChosen()], -9, true);
                        else if (Objects.equals(player.getFaceTo(), "right"))
                            player.dropItem(player.getItemBarChosen(), player.getItemBarAmount()[player.getItemBarChosen()], 9, true);
                        qTimer = 0;
                    }
                } else qTimer = 0;
            }
        }
    }
}
