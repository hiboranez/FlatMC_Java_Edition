package Base;

import Element.Entity;
import EntityType.Item;
import EntityType.Player;
import InputDetection.*;
import Utils.GUI.*;
import Utils.OtherTool.*;
import Utils.TCP.Command;
import Utils.TCP.ServerLoad;
import Utils.TCP.TCPClient;
import Utils.WorldTool.WorldGenerate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static Utils.OtherTool.CraftIndex.updateCrafting;
import static java.lang.Math.max;
import static java.lang.Math.min;


public class GameFrame extends JFrame implements Runnable {
    // 储存世界文件根目录
    private static String pathWorld = System.getProperty("user.dir") + "/data/world/";
    // 储存服务器文件根目录
    private static String pathServer = System.getProperty("user.dir") + "/data/server/";
    // 定义窗口是否全屏
    boolean fullScreen = false;
    // 定义窗口线程
    private Thread thread = new Thread(this);
    // 定义TCP连接线程
    Thread enterServerThread;
    Thread timeOutThread;
    Thread LogTimerThread;
    // ★定义窗口离屏图像，用于双缓存
    private Image offScreenGameImage = null;
    private Image offScreenUiImage = null;
    // ★定义窗口玩家对象
    private Player player = null;
    // ★定义窗口玩家名
    private String playerName = null;
    // ★定义窗口要打开的世界名
    private String worldName = null;
    // ★定义窗口当前世界最终背景图片
    private BufferedImage worldCurrentFinalTexture = null;
    // ★定义窗口当前星星最终背景图片
    private BufferedImage starFinalTexture = null;
    // ★定义窗口菜单背景图片
    private BufferedImage menuTexture = null;
    // ★定义窗口当前世界
    private World worldCurrent = null;
    // 定义TCP世界传输状态
    private boolean[] linkState = new boolean[14];
    // 定义当前视野内方块光照强度
    private int[][] lightIntensity = null;
    // 定义当前视野内方块光照强度
    private int[][] lightIntensityNext = null;
    // 定义窗口窗口中心坐标
    private int xCenter = 450, yCenter = 300;
    // 定义窗口鼠标位置点
    private Point mouseLocation = new Point(0, 0);
    // 定义当前语言
    private String language = "English";
    // 定义根据当前渲染方块数
    private int xLeftVision = 0;
    private int xRightVision = 0;
    private int yUpVision = 0;
    private int yDownVision = 0;
    // 定义窗口当前玩家世界偏移量
    private int xPlayerRelative = 0;
    private int yPlayerRelative = 0;
    // 定义窗口当前的镜头偏移量
    private int xCameraRelative = 0;
    private int yCameraRelative = 0;
    // 定义窗口应有的世界偏移量
    private int xRelative = 0;
    private int yRelative = 0;
    // 定义窗口当前世界落后玩家偏移量
    private int xRelativeLag = 0;
    private int yRelativeLag = 0;
    // 定义窗口当前玩家落后应有偏移量
    private int xPlayerRelativeLag = 0;
    private int yPlayerRelativeLag = 0;
    // 定义世界背景透明度
    private double worldTimeAlphaDouble = 0;
    // 定义玩家上次中心坐标
    private int xPlayerLastCenter = 0;
    // 定义鼠标附属物品id
    private int itemAttachId = -1;
    // 定义鼠标附属物品数量
    private int itemAttachAmount = 0;
    // 定义弹出合成框物品计时器
    private int loseItemInCraftingTableTimer = 0;
    // 定义打开的物品栏页码
    private int inventoryPage = 0;
    // 定义玩家上次选中物品序号对应物品id
    private int itemLastChosenId = -1;
    // 定义选中物品姓名计时器
    private int itemChosenTimer = 0;
    // 定义游戏缩放比例
    private double zoomGameRatio = 1.0;
    // 定义GUI缩放比例
    private double zoomGuiRatio = 1.5;
    // 定义帧率
    private static long lastTime = System.nanoTime();
    private static int frameCount = 0;
    private static int frameRate = 0;
    // 定义窗口设备环境，用于实现全屏
    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice gd = ge.getDefaultScreenDevice();
    // 定义窗口播放的背景音乐列表
    private List<PlaySound> soundList = new CopyOnWriteArrayList<>();
    // 定义裂痕列表
    private List<Crack> crackList = new CopyOnWriteArrayList<>();
    // 定义窗口键盘检测类
    private SingleKey singleKeyDetection = new SingleKey();
    private DoubleKey doubleKeyDetection = new DoubleKey();
    // 定义窗口鼠标检测类
    private SingleMouse singleMouseDetector = new SingleMouse();
    private DoubleMouse doubleMouseDetector = new DoubleMouse();
    private MouseWheel mouseWheelDetector = new MouseWheel();
    private MouseFollow mouseFollower = new MouseFollow();
    // 定义窗口监听器类
    private WindowListen windowListener = new WindowListen();
    // 定义窗口GUI按钮列表
    private List<FlexButton> flexButtonList = new CopyOnWriteArrayList<>();
    // 定义窗口GUI文字列表
    private List<FlexText> flexTextList = new CopyOnWriteArrayList<>();
    private List<ChatText> chatTextList = new CopyOnWriteArrayList<>();
    // 定义窗口GUI文字列表
    private List<FlexImage> flexImageList = new CopyOnWriteArrayList<>();
    // 定义窗口GUI网格列表
    private List<FlexGrid> flexGridList = new CopyOnWriteArrayList<>();
    // 定义字符建造器
    StringBuilder inputStringBuilder = new StringBuilder();
    // 定义窗口玩家名是否可见
    private boolean nameOn = true;
    // 定义窗口玩家手中物品是否可见
    private boolean handItemOn = true;
    // 定义窗口GUI是否开启
    private boolean guiOn = true;
    // 定义窗口选中框是否开启
    private boolean showBlockSelection1 = true;
    private boolean showBlockSelection2 = true;
    // 定义窗口是否开启游戏音乐
    private boolean MusicOn = true;
    // 定义游戏窗口当前显示界面
    private int frameSort = 1;
    // 定义是否显示当前时间
    private boolean timeVisible = true;
    // 定义窗口是否进入背包界面
    private boolean backGui = false;
    // 定义是否显示实体血条
    private boolean healthBarVisible = true;
    // 定义是否按下鼠标
    private boolean mouseLeftPressed = false;
    private boolean mouseRightPressed = false;
    private boolean mouseMiddlePressed = false;
    // 定义游戏是否暂停
    private boolean gamePaused = false;
    // 定义是否打开物品栏
    private boolean inventoryOpened = false;
    // 定义是否合成
    private boolean crafted = false;
    // 定义是否全部合成
    private boolean allCrafted = false;
    // 定义是否打开工作台合成
    private boolean craftingTableOpened = false;
    // 定义是否按下shift
    private boolean shiftPressed = false;
    // 定义是否按下ctrl
    private boolean ctrlPressed = false;
    // 定义是否正在填装GUI
    private boolean guiLoading = false;
    // 定义是否是多人模式
    private boolean multiPlayerMode = false;
    // 定义是否查看玩家列表
    private boolean showOnlinePlayerList = false;
    // 定义是否连接被拒绝
    private boolean linkDeny = false;
    // 定义是否账户被封禁
    private boolean banned = false;
    // 定义是否IP被封禁
    private boolean baniped = false;
    // 定义是否打开聊天框
    private boolean terminalOn = false;
    private ChatText terminalText = new ChatText(this, null, "white", 0, true);
    private boolean infoOn = false;

    public GameFrame(String playerName, int width, int height, String language) {
        // 设置大小为900x600，18x12方块
        setSize(width, height);
        // 设置窗口位置居中
        setLocationRelativeTo(null);
        // 设置窗口可见
        setVisible(true);
        // 设置关闭按钮
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口可伸缩
        setResizable(true);
        // 设置语言
        this.language = language;
        // 设置窗口标题
        if (Objects.equals(language, "English")) {
            setTitle("FlatMC v0.4.0");
        } else if (Objects.equals(language, "Chinese")) {
            setTitle("我的世界2D版 v0.4.0");
        }
        // 初始化资源
        Resource.init();
        Command.setGame(this);
        for (int i = 0; i < linkState.length; i++)
            linkState[i] = false;
        // 设置菜单背景图
        menuTexture = Resource.getWorldTextureList().get(3);
        // 定义自己的玩家名
        this.playerName = playerName;
        // 向窗口对象添加键盘监听器
        addKeyListener(singleKeyDetection.startListening(this));
        addKeyListener(doubleKeyDetection.startListening(this));
        // 向窗口对象添加鼠标监听器
        // 鼠标跟随器不需额外添加
        addMouseListener(singleMouseDetector.startListening(this));
        addMouseListener(doubleMouseDetector.startListening(this));
        addMouseWheelListener(mouseWheelDetector.startListening(this));
        // 向窗口添加窗口监听器
        addWindowListener(windowListener.startListening(this));
        // 初始化屏幕背景
        offScreenGameImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        offScreenUiImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        menuTexture = (BufferedImage) ImageEditor.imageScale(Resource.getWorldTextureList().get(3), (int) getSize().getWidth(), (int) getSize().getHeight());
        // 开启游戏线程
        thread.start();
        showFrame1();
    }

    public static String getPathWorld() {
        return pathWorld;
    }

    public static void setPathWorld(String pathWorld) {
        GameFrame.pathWorld = pathWorld;
    }

    // 游戏暂停界面
    public void gamePause() {
        clearGUI();
        backGui = true;
        gamePaused = true;
        if (isMusicOn())
            switchMusicOnEsc();
        else switchMusicOffEsc();
        if (!multiPlayerMode)
            flexButtonList.add(new FlexButton(this, 0, 0.225, 0.5, 0.1, WordList.getWord(0, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    gamePaused = false;
                    clearGUI();
                    stopMusic();
                    worldCurrent.getWorldSaver().saveWorld(worldName, worldCurrent);
                    worldCurrent.threadInterrupt();
                    player = null;
                    worldCurrent = null;
                    mouseLeftPressed = false;
                    mouseRightPressed = false;
                    backGui = false;
                    showFrame1();
                    updateTextureSize();
                }
            });
        else
            flexButtonList.add(new FlexButton(this, 0, 0.225, 0.5, 0.1, WordList.getWord(44, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    gamePaused = false;
                    clearGUI();
                    chatTextList.clear();
                    stopMusic();
                    worldCurrent.threadInterrupt();
                    player = null;
                    worldCurrent = null;
                    mouseLeftPressed = false;
                    mouseRightPressed = false;
                    backGui = false;
                    TCPClient.closeTCP();
                    for (int i = 0; i < linkState.length; i++)
                        linkState[i] = false;
                    showFrame1();
                    updateTextureSize();
                }
            });
        if (Objects.equals(worldCurrent.getDifficulty(), "peaceful"))
            switchDifficultyPeacefulEsc();
        else if (Objects.equals(worldCurrent.getDifficulty(), "easy"))
            switchDifficultyEasyEsc();
        else if (Objects.equals(worldCurrent.getDifficulty(), "normal"))
            switchDifficultyNormalEsc();
        else if (Objects.equals(worldCurrent.getDifficulty(), "hard"))
            switchDifficultyHardEsc();

        if (player.isKeepInventory())
            switchKeepInventoryTrueEsc();
        else
            switchKeepInventoryFalseEsc();
    }

    // esc切换死亡不掉落KeepInventory: True
    public void switchKeepInventoryTrueEsc() {
        if (!multiPlayerMode)
            flexButtonList.add(new FlexButton(this, 0, -0.225, 0.5, 0.1, WordList.getWord(5, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    player.setKeepInventory(false);
                    Command.showText(playerName + WordList.getWord(49, language) + false, "green");
                    switchKeepInventoryFalseEsc();
                }
            });
        else
            flexButtonList.add(new FlexButton(this, 0, -0.225, 0.5, 0.1, WordList.getWord(5, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    TCPClient.sendMessageToServer("/keepinventory " + playerName + " false\n", playerName);
                }
            });
    }

    // esc切换死亡不掉落KeepInventory: False
    public void switchKeepInventoryFalseEsc() {
        if (!multiPlayerMode)
            flexButtonList.add(new FlexButton(this, 0, -0.225, 0.5, 0.1, WordList.getWord(6, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    player.setKeepInventory(true);
                    Command.showText(playerName + WordList.getWord(49, language) + true, "green");
                    switchKeepInventoryTrueEsc();
                }
            });
        else
            flexButtonList.add(new FlexButton(this, 0, -0.225, 0.5, 0.1, WordList.getWord(6, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    TCPClient.sendMessageToServer("/keepinventory " + playerName + " true\n", playerName);
                }
            });
    }

    // esc切换难度Difficulty: Peaceful
    public void switchDifficultyPeacefulEsc() {
        if (!multiPlayerMode)
            flexButtonList.add(new FlexButton(this, 0, -0.075, 0.5, 0.1, WordList.getWord(7, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    worldCurrent.setDifficulty("easy");
                    Command.showText(WordList.getWord(52, language) + "easy", "green");
                    switchDifficultyEasyEsc();
                }
            });
        else
            flexButtonList.add(new FlexButton(this, 0, -0.075, 0.5, 0.1, WordList.getWord(7, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    TCPClient.sendMessageToServer("/difficulty easy\n", playerName);
                }
            });
    }

    // esc切换难度Difficulty: Easy
    public void switchDifficultyEasyEsc() {
        if (!multiPlayerMode)
            flexButtonList.add(new FlexButton(this, 0, -0.075, 0.5, 0.1, WordList.getWord(8, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    worldCurrent.setDifficulty("normal");
                    Command.showText(WordList.getWord(52, language) + "normal", "green");
                    switchDifficultyNormalEsc();
                }
            });
        else
            flexButtonList.add(new FlexButton(this, 0, -0.075, 0.5, 0.1, WordList.getWord(8, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    TCPClient.sendMessageToServer("/difficulty normal\n", playerName);
                }
            });
    }

    // esc切换难度Difficulty: Normal
    public void switchDifficultyNormalEsc() {
        if (!multiPlayerMode)
            flexButtonList.add(new FlexButton(this, 0, -0.075, 0.5, 0.1, WordList.getWord(9, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    worldCurrent.setDifficulty("hard");
                    Command.showText(WordList.getWord(52, language) + "hard", "green");
                    switchDifficultyHardEsc();
                }
            });
        else
            flexButtonList.add(new FlexButton(this, 0, -0.075, 0.5, 0.1, WordList.getWord(9, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    TCPClient.sendMessageToServer("/difficulty hard\n", playerName);
                }
            });
    }

    // esc切换难度Difficulty: Hard
    public void switchDifficultyHardEsc() {
        if (!multiPlayerMode)
            flexButtonList.add(new FlexButton(this, 0, -0.075, 0.5, 0.1, WordList.getWord(10, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    worldCurrent.setDifficulty("peaceful");
                    Command.showText(WordList.getWord(52, language) + "peaceful", "green");
                    switchDifficultyPeacefulEsc();
                }
            });
        else
            flexButtonList.add(new FlexButton(this, 0, -0.075, 0.5, 0.1, WordList.getWord(10, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    TCPClient.sendMessageToServer("/difficulty peaceful\n", playerName);
                }
            });
    }

    // esc切换音乐Music: ON
    public void switchMusicOnEsc() {
        flexButtonList.add(new FlexButton(this, 0, 0.075, 0.5, 0.1, WordList.getWord(11, language), "white", true, 30) {
            @Override
            public void pressFunction() {
                setMusicOn(false);
                stopMusic();
                switchMusicOffEsc();
            }
        });
    }

    // esc切换音乐Music: OFF
    public void switchMusicOffEsc() {
        flexButtonList.add(new FlexButton(this, 0, 0.075, 0.5, 0.1, WordList.getWord(12, language), "white", true, 30) {
            @Override
            public void pressFunction() {
                setMusicOn(true);
                switchMusicOnEsc();
            }
        });
    }

    // 显示屏幕5 连接中断界面
    public void showFrame5(String cause) {
        if (LogTimerThread.isAlive()) LogTimerThread.interrupt();
        if (worldCurrent != null)
            worldCurrent.threadInterrupt();
        frameSort = 5;
        clearGUI();
        chatTextList.clear();
        gamePaused = false;
        stopMusic();
        if (worldCurrent != null && worldCurrent.getThread().isAlive())
            worldCurrent.threadInterrupt();
        player = null;
        worldCurrent = null;
        mouseLeftPressed = false;
        mouseRightPressed = false;
        backGui = false;
        updateTextureSize();
        if (cause.equals("LinkLost"))
            flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(42, language), "red", -1));
        else if (cause.equals("ServerStopped"))
            flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(55, language), "red", -1));
        else if (cause.equals("PlayerExisted"))
            flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(63, language), "red", -1));
        else if (cause.equals("LoginTimeOut"))
            flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(115, language), "red", -1));
        else if (cause.equals("Kicked"))
            flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(127, language), "red", -1));
        else if (cause.equals("Baniped"))
            flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(128, language), "red", -1));
        else if (cause.equals("Banned"))
            flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(129, language), "red", -1));
        flexButtonList.add(new FlexButton(this, 0, 0.08, 0.5, 0.1, WordList.getWord(41, language), "white", false, 30) {
            @Override
            public void pressFunction() {
                TCPClient.closeTCP();
                for (int i = 0; i < linkState.length; i++)
                    linkState[i] = false;
                enterServerThread.interrupt();
                timeOutThread.interrupt();
                showFrame3();
            }
        });
    }

    // 显示屏幕4 连接界面
    public void showFrame4() {
        frameSort = 4;
        clearGUI();
        chatTextList.clear();
        flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(38, language), "white", -1));
        flexButtonList.add(new FlexButton(this, 0, 0.08, 0.5, 0.1, WordList.getWord(41, language), "white", false, 30) {
            @Override
            public void pressFunction() {
                TCPClient.closeTCP();
                for (int i = 0; i < linkState.length; i++)
                    linkState[i] = false;
                enterServerThread.interrupt();
                if (timeOutThread != null)
                    timeOutThread.interrupt();
                showFrame3();
            }
        });
    }

    // 显示屏幕3 选择服务器界面
    public void showFrame3() {
        frameSort = 3;
        clearGUI();
        chatTextList.clear();
        loadServerFile();
        flexTextList.add(new FlexText(this, 0, 0.44, 0.07, 0.07, WordList.getWord(37, language), "white", -1));
    }

    // 显示屏幕2 选择世界界面
    public void showFrame2() {
        frameSort = 2;
        clearGUI();
        chatTextList.clear();
        loadWorldFile();
        flexTextList.add(new FlexText(this, 0, 0.44, 0.07, 0.07, WordList.getWord(13, language), "white", -1));
    }

    // 显示屏幕1 主界面
    public void showFrame1() {
        frameSort = 1;
        clearGUI();
        chatTextList.clear();
        flexButtonList.add(new FlexButton(this, 0, -0.05, 0.5, 0.1, WordList.getWord(14, language), "white", true, 30) {
            @Override
            public void pressFunction() {
                showFrame2();
            }
        });
        flexButtonList.add(new FlexButton(this, 0, 0.08, 0.5, 0.1, WordList.getWord(15, language), "white", false, 30) {
            @Override
            public void pressFunction() {
                showFrame3();
            }
        });
        if (Objects.equals(language, "English")) {
            switchLanguageEnglish();
        } else switchLanguageChinese();
        flexButtonList.add(new FlexButton(this, 0, 0.34, 0.5, 0.1, WordList.getWord(16, language), "white", false, 30) {
            @Override
            public void pressFunction() {
                mouseFollower = null;
                getGame().dispose();
                getGame().getThread().interrupt();
            }
        });
        guiLoading = true;
        flexTextList.add(new FlexText(this, 0, -0.3, 0.6, 0.25, WordList.getWord(17, language), "white", -1));
        flexTextList.add(new FlexText(this, 0, -0.2, 0.08, 0.08, WordList.getWord(18, language), "yellow", -1));
        guiLoading = false;
    }

    // 主菜单切换Language:English
    public void switchLanguageEnglish() {
        flexButtonList.add(new FlexButton(this, 0, 0.21, 0.5, 0.1, "Language: English", "white", true, 30) {
            @Override
            public void pressFunction() {
                language = "Chinese";
                clearGUI();
                showFrame1();
                switchLanguageChinese();
            }
        });
    }

    // 主菜单切换语言：中文
    public void switchLanguageChinese() {
        flexButtonList.add(new FlexButton(this, 0, 0.21, 0.5, 0.1, "语言：中文", "white", true, 30) {
            @Override
            public void pressFunction() {
                language = "English";
                clearGUI();
                showFrame1();
                switchLanguageEnglish();
            }
        });
    }

    // 世界已经存在提醒
    public void warningWorldAlreadyExist() {
        flexTextList.add(new FlexText(this, 0, -0.355, 0.06, 0.06, playerName + WordList.getWord(1, language) + WordList.getWord(19, language), "yellow", 500));
    }

    // 世界数目达上限提醒
    public void warningWorldMaxAmount() {
        flexTextList.add(new FlexText(this, 0, -0.355, 0.06, 0.06, WordList.getWord(20, language), "yellow", 200));
    }

    // 未开放提醒
    public void warningNotAvailable() {
        flexTextList.add(new FlexText(this, 0, -0.138, 0.08, 0.08, WordList.getWord(21, language), "yellow", 200));
    }

    // 清空GUI
    public void clearGUI() {
        flexButtonList.clear();
        flexTextList.clear();
        flexImageList.clear();
        flexGridList.clear();
    }

    // 读取世界名
    public void loadWorldFile() {
        // 获取文件夹中的所有文件
        File folder = new File(pathWorld);
        File[] worldFileList = folder.listFiles();
        // 存储文件名的数组
        List<String> worldNameList = new ArrayList<>();
        if (worldFileList != null) {
            for (File file : worldFileList) {
                // 判断是否为txt文件
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    // 获取文件名并添加到数组
                    worldNameList.add(file.getName().replace(".txt", ""));
                }
            }
        }
        // 遍历文件数组
        if (!worldNameList.isEmpty()) {
            int count = 0;
            guiLoading = true;
            for (String world : worldNameList) {
                flexTextList.add(new FlexText(this, 0, -0.4, 0.06, 0.2, WordList.getWord(22, language), "white", -1));
                if (count % 2 == 0)
                    flexButtonList.add(new FlexButton(this, -0.22, -0.27 + 0.1 * (count / 2), 0.4, 0.07, world, "white", true, 30) {
                        @Override
                        public void pressFunction() {
                            enterWorld(world, playerName);
                        }
                    });
                else
                    flexButtonList.add(new FlexButton(this, 0.22, -0.27 + 0.1 * (count / 2), 0.4, 0.07, world, "white", true, 30) {
                        @Override
                        public void pressFunction() {
                            enterWorld(world, playerName);
                        }
                    });
                count++;
            }
        } else {
            flexTextList.add(new FlexText(this, 0, -0.4, 0.06, 0.2, WordList.getWord(23, language), "white", -1));
        }
        guiLoading = false;
        flexButtonList.add(new FlexButton(this, 0.22, 0.36, 0.4, 0.07, WordList.getWord(24, language), "white", false, 30) {
            @Override
            public void pressFunction() {
                if (worldNameList.size() >= 12) {
                    warningWorldMaxAmount();
                    setTimeNoPress(30);
                } else if (worldNameList.contains(playerName + WordList.getWord(1, language))) {
                    warningWorldAlreadyExist();
                    setTimeNoPress(30);
                } else {
                    WorldGenerate.generateWorld(playerName + WordList.getWord(1, language), playerName, 1001, 501);
                    showFrame2();
                }
            }
        });
        flexButtonList.add(new FlexButton(this, -0.22, 0.36, 0.4, 0.07, WordList.getWord(25, language), "white", true, 30) {
            @Override
            public void pressFunction() {
                showFrame1();
            }
        });
    }

    // 读取服务器名
    public void loadServerFile() {
        // 获取文件夹中的所有文件
        File folder = new File(pathServer);
        File[] serverFileList = folder.listFiles();
        // 存储文件名的数组
        List<String> serverNameList = new ArrayList<>();
        if (serverFileList != null) {
            for (File file : serverFileList) {
                // 判断是否为txt文件
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    // 获取文件名并添加到数组
                    serverNameList.add(file.getName().replace(".txt", ""));
                }
            }
        }
        // 遍历文件数组
        if (!serverNameList.isEmpty()) {
            int count = 0;
            guiLoading = true;
            for (String server : serverNameList) {
                flexTextList.add(new FlexText(this, 0, -0.4, 0.06, 0.2, WordList.getWord(35, language), "white", -1));
                if (count % 2 == 0)
                    flexButtonList.add(new FlexButton(this, -0.22, -0.27 + 0.1 * (count / 2), 0.4, 0.07, server, "white", true, 30) {
                        @Override
                        public void pressFunction() {
                            showFrame4();
                            enterServerThread = new Thread(() -> {
                                enterServer(server, playerName);
                            });
                            enterServerThread.start();
                        }
                    });
                else
                    flexButtonList.add(new FlexButton(this, 0.22, -0.27 + 0.1 * (count / 2), 0.4, 0.07, server, "white", true, 30) {
                        @Override
                        public void pressFunction() {
                            showFrame4();
                            enterServerThread = new Thread(() -> {
                                enterServer(server, playerName);
                            });
                            enterServerThread.start();
                        }
                    });
                count++;
            }
        } else {
            flexTextList.add(new FlexText(this, 0, -0.4, 0.06, 0.2, WordList.getWord(36, language), "white", -1));
        }
        guiLoading = false;
        flexButtonList.add(new FlexButton(this, 0.22, 0.36, 0.4, 0.07, WordList.getWord(34, language), "white", false, 30) {
            @Override
            public void pressFunction() {
                showFrame3();
            }
        });
        flexButtonList.add(new FlexButton(this, -0.22, 0.36, 0.4, 0.07, WordList.getWord(25, language), "white", true, 30) {
            @Override
            public void pressFunction() {
                showFrame1();
            }
        });
    }

    public void enterWorld(String worldName, String playerName) {
        // 加载世界
        this.worldName = worldName;
        worldCurrent = new World(this.worldName);
        multiPlayerMode = false;
        // 加载世界
        worldCurrent.getWorldLoader().loadWorld(worldName, worldCurrent);
        worldCurrentFinalTexture = worldCurrent.getTextureCurrent();
        // 更新世界图片时间
        updateWorldTexture();
        // 建立玩家与世界的连接
        for (Entity entity : worldCurrent.getPlayerList()) {
            if (entity instanceof Player) {
                if (Objects.equals(((Player) entity).getName(), playerName)) {
                    player = (Player) entity;
                    worldCurrent.getEntityList().add(player);
                    player.setGame(this);
                }
            }
        }
        if (player == null) {
            player = new Player(this.playerName, worldCurrent, this);
            player.setGame(this);
        }
        // 初始化镜头偏移量
        player.updateCollisionLocation();
        player.updateCenterLocation();
        xPlayerRelative = player.getxCenter() - xCenter;
        yPlayerRelative = player.getyCenter() - yCenter;
        xCameraRelative = player.getxCenter() - xCenter;
        yCameraRelative = player.getyCenter() - yCenter;
        // 初始化光照
        lightIntensity = new int[worldCurrent.getHeight()][worldCurrent.getWidth()];
        xPlayerLastCenter = player.getxCenter();
        updateVision();
        updateVisionLightIntensity();
        // 开启世界线程
        worldCurrent.threadStart();
        // 清除GUI
        clearGUI();
        chatTextList.clear();
        // 切换画面
        frameSort = 0;
    }

    public void enterServer(String serverName, String playerName) {
        String[] address = ServerLoad.loadServer(serverName);
        boolean connected = TCPClient.startListeningToServer(address[0], Integer.parseInt(address[1]), this);
        if (frameSort == 4) {
            for (FlexText flexText : flexTextList)
                if (flexText.getyCenterRatio() == -0.15)
                    flexTextList.remove(flexText);
            if (connected) {
                flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(39, language), "green", -1));
                timeOutThread = new Thread(() -> {
                    int timer = 100;
                    while (true) {
                        if (timer <= 0) {
                            for (FlexText flexText : flexTextList)
                                if (flexText.getyCenterRatio() == -0.15)
                                    flexTextList.remove(flexText);
                            flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(43, language), "red", -1));
                            TCPClient.closeTCP();
                            for (int i = 0; i < linkState.length; i++)
                                linkState[i] = false;
                            timeOutThread.interrupt();
                            break;
                        }
                        timer--;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                timeOutThread.start();
                enterServerWorld(serverName, playerName);
            } else
                flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(40, language), "red", -1));
        }
    }

    // 更新GUI
    public void updateFlexGUI() {
        // 遍历按钮
        for (FlexButton flexButton : flexButtonList) {
            flexButton.updateButton();
            // 如果按钮被按过且可以删除
            if (flexButton.isCanDelete() && flexButton.isOncePress())
                // 删除按钮
                flexButtonList.remove(flexButton);
        }
        // 遍历文字
        for (FlexText flexText : flexTextList) {
            flexText.updateDrawText();
            // 如果可以删除
            if (flexText.isCanDelete() && !guiLoading)
                // 删除文字
                flexTextList.remove(flexText);
        }
        // 遍历图片
        for (FlexImage flexImage : flexImageList) {
            flexImage.updateImage();
            // 如果可以删除
            if (flexImage.isCanDelete() && !guiLoading)
                // 删除图片
                flexImageList.remove(flexImage);
        }
        // 遍历网格
        for (FlexGrid flexGrid : flexGridList) {
            flexGrid.updateGrid();
            // 如果可以删除
            if (flexGrid.isCanDelete() && !guiLoading)
                // 删除网格
                flexGridList.remove(flexGrid);
        }
    }

    public void enterServerWorld(String serverName, String playerName) {
        String cause = "null";
        // 加载世界
        this.worldName = serverName;
        worldCurrent = new World(this.worldName);
        linkState[0] = true;
        while (!linkState[1] || !linkState[2] || !linkState[3] || !linkState[4] || !linkState[5] || !linkState[6] || !linkState[7] || !linkState[8] || !linkState[9] || !linkState[10] || !linkState[11] || !linkState[12]) {
            if (linkState[1] && linkState[2]) {
                for (FlexText flexText : flexTextList)
                    if (flexText.getyCenterRatio() == -0.15)
                        flexTextList.remove(flexText);
                flexTextList.add(new FlexText(this, 0, -0.15, 0.1, 0.1, WordList.getWord(132, language), "green", -1));
                for (int y = 0; y < worldCurrent.getHeight(); y++) {
                    for (int x = 0; x < worldCurrent.getWidth(); x++) {
                        worldCurrent.getBlockIdList()[y][x] = -1;
                    }
                }
                linkState[12] = true;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        multiPlayerMode = true;
        worldCurrentFinalTexture = worldCurrent.getTextureCurrent();
        // 更新世界图片时间
        updateWorldTexture();
        // 建立玩家与世界的连接
        for (Entity entity : worldCurrent.getPlayerList()) {
            if (entity instanceof Player) {
                if (Objects.equals(((Player) entity).getName(), playerName)) {
                    player = (Player) entity;
                    worldCurrent.getEntityList().add(player);
                    player.setGame(this);
                }
            }
        }
        if (player == null) {
            player = new Player(this.playerName, worldCurrent, this);
            player.setGame(this);
        }
        TCPClient.sendMessageToServer("/updatePlayerLink " + playerName + "\n", playerName);
        while (!linkState[13]) {
            if (linkDeny) {
                linkDeny = false;
                cause = "sameName";
                break;
            } else if (banned) {
                banned = false;
                cause = "banned";
                break;
            } else if (baniped) {
                baniped = false;
                cause = "baniped";
                break;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        timeOutThread.interrupt();
        if (cause.equals("null")) {
            // 初始化镜头偏移量
            player.updateCollisionLocation();
            player.updateCenterLocation();
            xPlayerRelative = player.getxCenter() - xCenter;
            yPlayerRelative = player.getyCenter() - yCenter;
            xCameraRelative = player.getxCenter() - xCenter;
            yCameraRelative = player.getyCenter() - yCenter;
            // 初始化光照
            lightIntensity = new int[worldCurrent.getHeight()][worldCurrent.getWidth()];
            xPlayerLastCenter = player.getxCenter();
            updateVision();
            updateVisionLightIntensity();
            player.setHasGravity(false);
            player.setLogin(false);
            // 开启世界线程
            worldCurrent.threadStart();
            // 清除GUI
            clearGUI();
            // 切换画面
            frameSort = 0;
            Command.showText(playerName + WordList.getWord(47, language), "yellow");
            LogTimerThread = new Thread(() -> {
                int logTimer = 0;
                while (true) {
                    if (logTimer >= 50) {
                        for (int i = 0; i < linkState.length; i++)
                            linkState[i] = false;
                        TCPClient.closeTCP();
                        showFrame5("LoginTimeOut");
                        LogTimerThread.interrupt();
                    }
                    logTimer++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            LogTimerThread.start();
        } else if (cause.equals("sameName")) {
            TCPClient.closeTCP();
            for (int i = 0; i < linkState.length; i++)
                linkState[i] = false;
            showFrame5("PlayerExisted");
        } else if (cause.equals("banned")) {
            TCPClient.closeTCP();
            for (int i = 0; i < linkState.length; i++)
                linkState[i] = false;
            showFrame5("Banned");
        } else if (cause.equals("baniped")) {
            TCPClient.closeTCP();
            for (int i = 0; i < linkState.length; i++)
                linkState[i] = false;
            showFrame5("Baniped");
        }
    }

    // 开启/退出全屏
    public void controlFullScreen() {
        if (fullScreen) {
            if (gd.isFullScreenSupported())
                gd.setFullScreenWindow(this);
        } else
            gd.setFullScreenWindow(null);
    }

    // 打开物品栏
    public void openInventory() {
        if (Objects.equals(player.getGameMode(), "survival"))
            inventoryPage = 0;
        inventoryOpened = true;
        backGui = true;
        flexImageList.add(new FlexImage(this, 0, 0, 0.4, 0.7, Resource.getGuiTextureList().get(18)) {
            @Override
            public void pressOutFunction() {
                if (itemAttachId != -1) {
                    if (Objects.equals(player.getFaceTo(), "left"))
                        player.loseItem(itemAttachId, itemAttachAmount, -9, true);
                    else if (Objects.equals(player.getFaceTo(), "right"))
                        player.loseItem(itemAttachId, itemAttachAmount, 9, true);
                    itemAttachId = -1;
                    itemAttachAmount = 0;
                    if (multiPlayerMode)
                        worldCurrent.updatePlayerItemDataToServer(player);
                    setTimeNoPress(10);
                }
            }
        });
        for (int i = 0; i < 36; i++) {
            if (i / 9 == 0)
                flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, 0.27 - (i / 9) * 0.07, 0.04, 0.07, i, 30));
            else
                flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.03 + (i / 9) * 0.07, 0.04, 0.07, i, 30));
        }
        if (inventoryPage == 0) {
            flexGridList.add(new FlexGrid(this, -0.1600, -0.275, 0.04, 0.07, 36, 30));
            flexGridList.add(new FlexGrid(this, -0.1600, -0.205, 0.04, 0.07, 37, 30));
            flexGridList.add(new FlexGrid(this, -0.1600, -0.135, 0.04, 0.07, 38, 30));
            flexGridList.add(new FlexGrid(this, -0.1600, -0.065, 0.04, 0.07, 39, 30));
            flexGridList.add(new FlexGrid(this, 0.0100, -0.155, 0.04, 0.07, 41, 30));
            flexGridList.add(new FlexGrid(this, 0.0100, -0.225, 0.04, 0.07, 44, 30));
            flexGridList.add(new FlexGrid(this, 0.0500, -0.155, 0.04, 0.07, 42, 30));
            flexGridList.add(new FlexGrid(this, 0.0500, -0.225, 0.04, 0.07, 45, 30));
            flexGridList.add(new FlexGrid(this, 0.1400, -0.19, 0.04, 0.07, 50, 30));
            flexImageList.add(new FlexImage(this, 0.0960, -0.19, 0.035, 0.05, Resource.getGuiTextureList().get(20)));
            flexImageList.add(new FlexImage(this, -0.0890, -0.1703, 0.1, 0.28, Resource.getGuiTextureList().get(21)));
            flexImageList.add(new FlexImage(this, -0.0890, -0.1703, 0.06, 0.22, Resource.getGuiTextureList().get(22)));
        } else if (inventoryPage == 1) {
            List<Integer> pageItemList = new ArrayList<>();
            for (int i = 0; i <= 42; i++) {
                if (IDIndex.blockIdToType(i).equals("Blocks"))
                    pageItemList.add(i);
            }
            int size = pageItemList.size();
            if (size > 36) size = 36;
            for (int i = 0; i < size; i++) {
                flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.275 + (i / 9) * 0.07, 0.04, 0.07, -pageItemList.get(i) - 3, 30));
            }
            if (size < 36)
                for (int i = size; i < 36; i++) {
                    flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.275 + (i / 9) * 0.07, 0.04, 0.07, -2, 30));
                }
        } else if (inventoryPage == 2) {
            List<Integer> pageItemList = new ArrayList<>();
            for (int i = 0; i <= 42; i++) {
                if (IDIndex.blockIdToType(i).equals("Tools"))
                    pageItemList.add(i);
            }
            int size = pageItemList.size();
            if (size > 36) size = 36;
            for (int i = 0; i < size; i++) {
                flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.275 + (i / 9) * 0.07, 0.04, 0.07, -pageItemList.get(i) - 3, 30));
            }
            if (size < 36)
                for (int i = size; i < 36; i++) {
                    flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.275 + (i / 9) * 0.07, 0.04, 0.07, -2, 30));
                }
        } else if (inventoryPage == 3) {
            List<Integer> pageItemList = new ArrayList<>();
            for (int i = 0; i <= 42; i++) {
                if (IDIndex.blockIdToType(i).equals("Minerals"))
                    pageItemList.add(i);
            }
            int size = pageItemList.size();
            if (size > 36) size = 36;
            for (int i = 0; i < size; i++) {
                flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.275 + (i / 9) * 0.07, 0.04, 0.07, -pageItemList.get(i) - 3, 30));
            }
            if (size < 36)
                for (int i = size; i < 36; i++) {
                    flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.275 + (i / 9) * 0.07, 0.04, 0.07, -2, 30));
                }
        } else if (inventoryPage == 4) {
            List<Integer> pageItemList = new ArrayList<>();
            for (int i = 0; i <= 42; i++) {
                if (IDIndex.blockIdToType(i).equals("Others"))
                    pageItemList.add(i);
            }
            int size = pageItemList.size();
            if (size > 36) size = 36;
            for (int i = 0; i < size; i++) {
                flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.275 + (i / 9) * 0.07, 0.04, 0.07, -pageItemList.get(i) - 3, 30));
            }
            if (size < 36)
                for (int i = size; i < 36; i++) {
                    flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.275 + (i / 9) * 0.07, 0.04, 0.07, -2, 30));
                }
        }
        if (Objects.equals(player.getGameMode(), "creative")) {
            flexButtonList.add(new FlexButton(this, -0.15552, -0.372, 0.07776, 0.05, WordList.getWord(26, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    inventoryPage = 0;
                    setTimeNoPress(30);
                    clearGUI();
                    openInventory();
                }
            });
            flexButtonList.add(new FlexButton(this, -0.07776, -0.372, 0.0788, 0.05, WordList.getWord(27, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    inventoryPage = 1;
                    setTimeNoPress(30);
                    clearGUI();
                    openInventory();
                }
            });
            flexButtonList.add(new FlexButton(this, 0, -0.372, 0.0788, 0.05, WordList.getWord(28, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    inventoryPage = 2;
                    setTimeNoPress(30);
                    clearGUI();
                    openInventory();
                }
            });
            flexButtonList.add(new FlexButton(this, 0.07776, -0.372, 0.0788, 0.05, WordList.getWord(29, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    inventoryPage = 3;
                    setTimeNoPress(30);
                    clearGUI();
                    openInventory();
                }
            });
            flexButtonList.add(new FlexButton(this, 0.15552, -0.372, 0.0788, 0.05, WordList.getWord(30, language), "white", true, 30) {
                @Override
                public void pressFunction() {
                    inventoryPage = 4;
                    setTimeNoPress(30);
                    clearGUI();
                    openInventory();
                }
            });
        }
    }

    // 打开工作台
    public void openCraftingTable() {
        craftingTableOpened = true;
        backGui = true;
        flexImageList.add(new FlexImage(this, 0, 0, 0.4, 0.7, Resource.getGuiTextureList().get(18)) {
            @Override
            public void pressOutFunction() {
                if (itemAttachId != -1) {
                    if (Objects.equals(player.getFaceTo(), "left"))
                        player.loseItem(itemAttachId, itemAttachAmount, -9, true);
                    else if (Objects.equals(player.getFaceTo(), "right"))
                        player.loseItem(itemAttachId, itemAttachAmount, 9, true);
                    itemAttachId = -1;
                    itemAttachAmount = 0;
                    if (multiPlayerMode)
                        worldCurrent.updatePlayerItemDataToServer(player);
                    setTimeNoPress(10);
                }
            }
        });
        flexImageList.add(new FlexImage(this, 0.0425, -0.183, 0.05, 0.07, Resource.getGuiTextureList().get(20)));
        for (int i = 0; i < 36; i++) {
            if (i / 9 == 0)
                flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, 0.27 - (i / 9) * 0.07, 0.04, 0.07, i, 30));
            else
                flexGridList.add(new FlexGrid(this, -0.1600 + (i % 9) * 0.04, -0.03 + (i / 9) * 0.07, 0.04, 0.07, i, 30));
        }
        for (int i = 0; i < 9; i++) {
            flexGridList.add(new FlexGrid(this, -0.1110 + (i % 3) * 0.04, -0.113 - (i / 3) * 0.07, 0.04, 0.07, 41 + i, 30));
        }
        flexGridList.add(new FlexGrid(this, 0.114, -0.183, 0.04, 0.07, 50, 30));
    }

    // 绘制世界背景
    public void drawWorldTexture() {
        Graphics graphics = offScreenGameImage.getGraphics();
        if (frameSort == 0) {
            // 绘制世界背景
            graphics.drawImage(worldCurrentFinalTexture, 0, 0, this);
            // 绘制星星
            if (worldCurrent.getTime() >= 100000 && worldCurrent.getTime() <= 120000 || worldCurrent.getTime() >= 0 && worldCurrent.getTime() <= 40000)
                graphics.drawImage(starFinalTexture, 0, -getHeight() / 2, this);
        } else if (frameSort == 1) {
            graphics.drawImage(menuTexture, 0, 0, this);
        } else if (frameSort == 2 || frameSort == 3 || frameSort == 4 || frameSort == 5) {
            graphics.drawImage(menuTexture, 0, 0, this);
            graphics.setColor(new Color(0, 0, 0, 150));
            graphics.fillRect((int) (getWidth() * 0.06), (int) (getHeight() * 0.167), (int) (getWidth() * 0.88), (int) (getHeight() * 0.64));
        }
    }

    // 绘制游戏材质图像
    public void drawGameTextureImage(int xOffset, int yOffset) {
        Graphics graphics = offScreenGameImage.getGraphics();
        if (frameSort == 0) {
            // 绘制方块
            int xLeftVisionTemp = xLeftVision - 20;
            int xRightVisionTemp = xRightVision + 20;
            int yUpVisionTemp = yUpVision - 20;
            int yDownVisionTemp = yDownVision + 20;
            if (xLeftVisionTemp < 0) xLeftVisionTemp = 0;
            if (xRightVisionTemp > worldCurrent.getWidth() - 1) xRightVisionTemp = worldCurrent.getWidth() - 1;
            if (yUpVisionTemp < 0) yUpVisionTemp = 0;
            if (yDownVisionTemp > worldCurrent.getHeight() - 1) yDownVisionTemp = worldCurrent.getHeight() - 1;
            for (int i = xLeftVisionTemp; i <= xRightVisionTemp; i++) {
                for (int j = yUpVisionTemp; j <= yDownVisionTemp; j++) {
                    int blockId = worldCurrent.getBlockIdList()[j][i];
                    if (blockId != -1)
                        graphics.drawImage(Resource.getBlockTextureList().get(blockId), i * 50 - xOffset, j * 50 - yOffset, this);
                }
            }
            // 绘制实体
            Iterator<Entity> iteratorEntity = worldCurrent.getEntityList().iterator();
            // 遍历所有实体
            while (iteratorEntity.hasNext()) {
                Entity entity = iteratorEntity.next();
                // 因掉落物要悬浮，特殊处理
                if (entity instanceof Item) {
                    Item item = (Item) entity;
                    graphics.drawImage(item.getTextureCurrent(), item.getX() - xOffset, item.getY() + item.getSuspendY() - yOffset, this);
                }
                // 因玩家坐标会移动，为防止闪烁，特殊处理
                else if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (!player.isDead()) {
                        int blockIDChosen = player.getItemBarId()[player.getItemBarChosen()];
                        if (Objects.equals(player.getFaceTo(), "left")) {
                            if (blockIDChosen != -1 && handItemOn) {
                                // 如果玩家手拿棍棒型物品
                                if (IDIndex.blockIdToIsStickItem(blockIDChosen)) {
                                    Image image = ImageEditor.imageRotate(Resource.getBlockTextureList().get(blockIDChosen), -45 - player.getxHand());
                                    graphics.drawImage(image, player.getxCenter() - 50 + player.getxHand() - xCameraRelative, player.getyCenter() + player.getyHand() - 44 - yCameraRelative, this);
                                } else {
                                    Image image = ImageEditor.imageRotate(Resource.getItemTextureList().get(blockIDChosen), -45 - player.getxHand());
                                    graphics.drawImage(image, player.getxCenter() - 27 + player.getxHand() - xCameraRelative, player.getyCenter() + player.getyHand() - 10 - yCameraRelative, this);
                                }
                            }
                        }
                        updateRelativeLocation();
                        Image playerImage = player.getTextureCurrent();
                        if (player.getDamageTimer() != 0)
                            playerImage = ImageEditor.imageStain(playerImage, Color.RED, 100);
                        graphics.drawImage(playerImage, player.getX() - xCameraRelative, player.getY() - yCameraRelative, this);
                        if (Objects.equals(player.getFaceTo(), "right")) {
                            if (blockIDChosen != -1 && handItemOn) {
                                // 如果玩家手拿棍棒型物品
                                if (IDIndex.blockIdToIsStickItem(blockIDChosen)) {
                                    Image image = ImageEditor.imageRotate(Resource.getBlockTextureList().get(blockIDChosen), -45 + player.getxHand());
                                    image = ImageEditor.imageReverse(image, "horizontal");
                                    graphics.drawImage(image, player.getxCenter() - 20 + player.getxHand() - xCameraRelative, player.getyCenter() + player.getyHand() - 44 - yCameraRelative, this);
                                } else {
                                    Image image = ImageEditor.imageRotate(Resource.getItemTextureList().get(blockIDChosen), 45 - player.getxHand());
                                    int tmpX = (int) (player.getxHand() * 1.5);
                                    if (player.getxHand() == 20) tmpX = player.getxHand();
                                    graphics.drawImage(image, player.getxCenter() - 6 + tmpX - xCameraRelative, player.getyCenter() + player.getyHand() - 10 - yCameraRelative, this);
                                }
                            }
                        }
                    }
                }
                // 绘制其他实体，不包括玩家
                else {
                    Image entityImage = entity.getTextureCurrent();
                    if (entity.getDamageTimer() != 0)
                        entityImage = ImageEditor.imageStain(entityImage, Color.RED, 100);
                    graphics.drawImage(entityImage, entity.getX() - xOffset, entity.getY() - yOffset, this);
                    if (healthBarVisible) {
                        int barWidth = (int) (36 * ((double) entity.getHealth() / entity.getHealthMax()));
                        graphics.setColor(Color.GRAY);
                        graphics.fillRect(entity.getxCenter() - 18 - xCameraRelative, entity.getyUp() - 10 - yCameraRelative, 36, 3);
                        if (barWidth > 24 && barWidth <= 36) graphics.setColor(Color.GREEN);
                        else if (barWidth > 12 && barWidth <= 24) graphics.setColor(Color.ORANGE);
                        else if (barWidth >= 0 && barWidth <= 12) graphics.setColor(Color.RED);
                        graphics.fillRect(entity.getxCenter() - 18 - xCameraRelative, entity.getyUp() - 10 - yCameraRelative, barWidth, 3);
                    }
                }
            }
            // 绘制光照效果
            int leftSide = -20 + xLeftVision;
            int upSide = -20 + yUpVision;
            if (lightIntensity != null)
                for (int x = leftSide; x < leftSide + 40 + player.getxVision() / 25; x++) {
                    if (x < 0 || x >= worldCurrent.getWidth()) continue;
                    else if (x == 0 || x == worldCurrent.getWidth() - 1) {
                        for (int y = upSide; y < upSide + 40 + player.getyVision() / 25; y++) {
                            if (y < 0 || y > worldCurrent.getHeight() - 1) continue;
                            graphics.setColor(new Color(0, 0, 0, 150));
                            graphics.fillRect(x * 50 - xOffset, y * 50 - yOffset, 50, 50);
                        }
                        continue;
                    }
                    for (int y = upSide; y < upSide + 40 + player.getyVision() / 25; y++) {
                        if (y < 0 || y > worldCurrent.getHeight() - 1) continue;
                        else if (y == 0 || y == worldCurrent.getHeight() - 1) {
                            graphics.setColor(new Color(0, 0, 0, 150));
                            graphics.fillRect(x * 50 - xOffset, y * 50 - yOffset, 50, 50);
                            continue;
                        }
                        int darkLight = 255 - lightIntensity[y][x];
                        if (darkLight < 0) darkLight = 0;
                        graphics.setColor(new Color(0, 0, 0, darkLight));
                        graphics.fillRect(x * 50 - xOffset, y * 50 - yOffset, 50, 50);
                    }
                }
            // 绘制名字
            graphics.setFont(Resource.getFontList().get(0).deriveFont(16.0f));
            FontMetrics metrics = graphics.getFontMetrics();
            for (Entity entity : worldCurrent.getEntityList()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (player.getName() != null && guiOn && nameOn && !player.isDead()) {
                        if (player.isOperator()) {
                            int xName = player.getxCenter() - metrics.stringWidth("[OP] " + player.getName()) / 2;
                            graphics.setColor(new Color(0, 0, 0, 100));
                            graphics.fillRect(player.getxCenter() - metrics.stringWidth("[OP] " + player.getName()) / 2 - xCameraRelative - 5, player.getY() - 26 - yCameraRelative, metrics.stringWidth("[OP] " + player.getName()) + 8, 22);
                            graphics.setColor(new Color(230, 230, 0, 255));
                            updateRelativeLocation();
                            graphics.drawString("[OP] " + player.getName(), xName - xCameraRelative, player.getY() - 9 - yCameraRelative);
                        } else {
                            int xName = player.getxCenter() - metrics.stringWidth(player.getName()) / 2;
                            graphics.setColor(new Color(0, 0, 0, 100));
                            graphics.fillRect(player.getxCenter() - metrics.stringWidth(player.getName()) / 2 - xCameraRelative - 5, player.getY() - 26 - yCameraRelative, metrics.stringWidth(player.getName()) + 8, 22);
                            graphics.setColor(new Color(255, 255, 255, 230));
                            updateRelativeLocation();
                            graphics.drawString(player.getName(), xName - xCameraRelative, player.getY() - 9 - yCameraRelative);
                        }
                    }
                }
            }
            // 绘制破坏效果
            if (multiPlayerMode) {
                if (!crackList.isEmpty()) {
                    updateCrackList();
                    for (Crack crack : crackList) {
                        if (crack.getCrackSort() != 0) {
                            graphics.drawImage(Resource.getGuiTextureList().get(crack.getCrackSort() + 5), crack.getxBlock() * 50 - xCameraRelative, crack.getyBlock() * 50 - yCameraRelative, this);
                        }
                    }
                    updateCrackList();
                }
            } else if (player.getBlockCrackSort() != 0)
                graphics.drawImage(Resource.getGuiTextureList().get(player.getBlockCrackSort() + 5), player.getxBlockSelected() * 50 - xCameraRelative, player.getyBlockSelected() * 50 - yCameraRelative, this);
            if (guiOn) // 绘制选中方块
                if (!backGui && !player.isDead() && showBlockSelection1 && showBlockSelection2 && player.isCanReachBlockSelected())
                    graphics.drawImage(Resource.getGuiTextureList().get(5), player.getxBlockSelected() * 50 - xCameraRelative, player.getyBlockSelected() * 50 - yCameraRelative, this);
        }
    }

    // 绘制GUI图像
    public void drawGuiTextureImage() {
        Graphics graphics = offScreenUiImage.getGraphics();
        if (frameSort == 0) {
            // 如果开启了GUI
            if (guiOn) {
                // 绘制GUI
                graphics.drawImage(Resource.getGuiTextureList().get(0), xCenter - (int) (229 * zoomGuiRatio), getHeight() - (int) (63 * zoomGuiRatio), (int) (458 * zoomGuiRatio), (int) (56 * zoomGuiRatio), this);
                // 绘制背包内的物品
                for (int i = 0; i < 9; i++) {
                    if (player.getItemBarId()[i] != -1 && player.getItemBarAmount()[i] != 0)
                        graphics.drawImage(ImageEditor.imageScale(Resource.getItemBarBlockTexture().get(player.getItemBarId()[i]), (int) (35 * zoomGuiRatio), (int) (35 * zoomGuiRatio)), (int) (i * 50.43 * zoomGuiRatio) + xCenter - (int) (219 * zoomGuiRatio), (int) (getHeight() - 53 * zoomGuiRatio), this);
                }
                // 绘制物品栏选中框
                graphics.drawImage(ImageEditor.imageScale(Resource.getGuiTextureList().get(1), (int) (50 * zoomGuiRatio), (int) (50 * zoomGuiRatio)), (int) (player.getItemBarChosen() * 50.43 * zoomGuiRatio) + xCenter - (int) (226 * zoomGuiRatio), (int) (getHeight() - 60 * zoomGuiRatio), this);
                // 绘制物品数量
                for (int i = 0; i < 9; i++) {
                    if (player.getItemBarAmount()[i] == 0 || player.getItemBarAmount()[i] == 1) continue;
                    if (IDIndex.blockIdToIsTool(player.getItemBarId()[i])) {
                        int barWidth = (int) (33 * ((double) player.getItemBarAmount()[i] / IDIndex.blockIdToMaxAmount(player.getItemBarId()[i])));
                        graphics.setColor(Color.GRAY);
                        graphics.fillRect((int) (i * 50.43 * zoomGuiRatio) + xCenter - (int) ((182 + 36) * zoomGuiRatio), getHeight() - (int) (23 * zoomGuiRatio), (int) (33 * zoomGuiRatio), (int) (3 * zoomGuiRatio));
                        if (barWidth > 22 && barWidth <= 33) graphics.setColor(Color.GREEN);
                        else if (barWidth > 11 && barWidth <= 22) graphics.setColor(Color.ORANGE);
                        else if (barWidth >= 0 && barWidth <= 11) graphics.setColor(Color.RED);
                        graphics.fillRect((int) (i * 50.43 * zoomGuiRatio) + xCenter - (int) ((182 + 36) * zoomGuiRatio), getHeight() - (int) (23 * zoomGuiRatio), (int) (barWidth * zoomGuiRatio), (int) (3 * zoomGuiRatio));
                    } else {
                        graphics.setColor(Color.WHITE);
                        graphics.setFont(Resource.getFontList().get(0).deriveFont(14.0f * (float) zoomGuiRatio));
                        String amount = String.valueOf(player.getItemBarAmount()[i]);
                        graphics.drawString(amount, (int) (i * 50.43 * zoomGuiRatio) + xCenter + (int) ((-182 - amount.length() * 11) * zoomGuiRatio), getHeight() - (int) (19 * zoomGuiRatio));
                    }
                }
                if (Objects.equals(player.getGameMode(), "survival")) {
                    // 绘制生命值框
                    for (int i = 0; i < (player.getHealthMax() / 2); i++)
                        graphics.drawImage(Resource.getGuiTextureList().get(2), i * 30 + 22, 50, this);
                    if (player.getHealthMax() % 2 != 0)
                        graphics.drawImage(Resource.getGuiTextureList().get(2), (player.getHealthMax() / 2) * 30 + 22, 50, this);
                    if (player.getHeartFlashTimer() != 0)
                        for (int i = 0; i < (player.getHealthMax() / 2); i++)
                            graphics.drawImage(Resource.getGuiTextureList().get(17), i * 30 + 22, 50, this);
                    // 绘制生命值
                    for (int i = 0; i < player.getHealth() / 2; i++)
                        graphics.drawImage(Resource.getGuiTextureList().get(3), i * 30 + 25, 53, this);
                    if (player.getHealth() % 2 != 0)
                        graphics.drawImage(Resource.getGuiTextureList().get(4), (player.getHealth() / 2) * 30 + 25, 53, this);
                }
                // 绘制当前时间
                if (timeVisible) {
                    int widthTime = (int) (getWidth() * 0.023);
                    int heightTime = (int) (getHeight() * 0.023);
                    int sizeTimeMin = min(widthTime, heightTime);
                    graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (sizeTimeMin * zoomGuiRatio)));
                    FontMetrics metrics = graphics.getFontMetrics();
                    String timeCurrent = IDIndex.numberToTime(worldCurrent.getTime());
                    int xText = (getWidth() - metrics.stringWidth(timeCurrent)) / 2;
                    graphics.setColor(new Color(0, 0, 0, 100));
                    graphics.fillRect(xText - (int) (metrics.stringWidth(timeCurrent) * 0.08), (int) (50 - (sizeTimeMin * 0.16) * zoomGuiRatio), (int) (metrics.stringWidth(timeCurrent) * 1.12), (int) (sizeTimeMin * 1.4 * zoomGuiRatio));
                    graphics.setColor(new Color(255, 255, 255, 230));
                    graphics.drawString(timeCurrent, (getWidth() - metrics.stringWidth(timeCurrent)) / 2, (int) (50 + (sizeTimeMin) * zoomGuiRatio));
                }
                // 绘制游戏信息
                if (infoOn && player != null) {
                    int widthInfo = (int) (getWidth() * 0.023);
                    int heightInfo = (int) (getHeight() * 0.023);
                    int sizeTimeMin = min(widthInfo, heightInfo);
                    graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (sizeTimeMin * zoomGuiRatio * 0.8)));
                    FontMetrics metrics = graphics.getFontMetrics();
                    // 绘制坐标
                    String location = WordList.getWord(78, language) + "x=" + (player.getxCenter() / 50 - worldCurrent.getWidth() / 2) + " y=" + ((int) (-(player.getyCenter() + player.getHeight() * 0.25) / 50) + worldCurrent.getHeight() / 2);
                    int xInfo1 = getWidth() - metrics.stringWidth(location);
                    graphics.setColor(new Color(0, 0, 0, 100));
                    graphics.fillRect(xInfo1 - (int) (zoomGuiRatio * getWidth() * 0.012), (int) (50 + (sizeTimeMin * 1.04) * zoomGuiRatio), (int) (metrics.stringWidth(location) * 2), (int) (sizeTimeMin * 1.2 * zoomGuiRatio));
                    graphics.setColor(new Color(255, 255, 255, 230));
                    graphics.drawString(location, xInfo1 - (int) (sizeTimeMin * zoomGuiRatio * 0.65), (int) (50 + sizeTimeMin * zoomGuiRatio * 1.92));
                    // 绘制选中方块坐标
                    String locationSelect = WordList.getWord(79, language) + "x=" + (player.getxBlockSelected() - worldCurrent.getWidth() / 2) + " y=" + (-player.getyBlockSelected() + worldCurrent.getHeight() / 2);
                    int xInfo2 = getWidth() - metrics.stringWidth(locationSelect);
                    graphics.setColor(new Color(0, 0, 0, 100));
                    graphics.fillRect(xInfo2 - (int) (zoomGuiRatio * getWidth() * 0.012), (int) (50 - (sizeTimeMin * 0.16) * zoomGuiRatio), (int) (metrics.stringWidth(locationSelect) * 2), (int) (sizeTimeMin * 1.2 * zoomGuiRatio));
                    graphics.setColor(new Color(255, 255, 255, 230));
                    graphics.drawString(locationSelect, xInfo2 - (int) (sizeTimeMin * zoomGuiRatio * 0.65), (int) (50 + sizeTimeMin * zoomGuiRatio * 0.75));
                    // 绘制帧率
                    String fps = WordList.getWord(80, language) + getFrameRate(this);
                    int xInfo3 = getWidth() - metrics.stringWidth(fps);
                    graphics.setColor(new Color(0, 0, 0, 100));
                    graphics.fillRect(xInfo3 - (int) (zoomGuiRatio * getWidth() * 0.012), (int) (50 + (sizeTimeMin * 2.23) * zoomGuiRatio), (int) (metrics.stringWidth(fps) * 2), (int) (sizeTimeMin * 1.2 * zoomGuiRatio));
                    graphics.setColor(new Color(255, 255, 255, 230));
                    graphics.drawString(fps, xInfo3 - (int) (sizeTimeMin * zoomGuiRatio * 0.65), (int) (50 + sizeTimeMin * zoomGuiRatio * 3.18));
                }
                // 绘制服务器信息提示
                if (multiPlayerMode) {
                    // 绘制玩家列表
                    if (showOnlinePlayerList) {
                        graphics.setColor(new Color(0, 0, 0, 100));
                        graphics.fillRect((int) (0.275 * getWidth()), (int) (0.18 * getHeight()), (int) (0.45 * getWidth()), (int) (0.55 * getHeight()));
                        if (getWorldCurrent() != null) {
                            int onlineNum = 1;
                            int widthName = (int) (getWidth() * 0.023);
                            int heightName = (int) (getHeight() * 0.023);
                            int sizeNameMin = min(widthName, heightName);
                            graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (sizeNameMin * 1.5)));
                            graphics.setColor(Color.orange);
                            FontMetrics metrics = graphics.getFontMetrics();
                            int xText = (getWidth() - metrics.stringWidth(WordList.getWord(62, language))) / 2;
                            graphics.drawString(WordList.getWord(62, language), xText, (int) (0.207 * getHeight()) + sizeNameMin);
                            for (Entity entity : getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player playerOnline = (Player) entity;
                                    if (playerOnline.isOperator()) {
                                        graphics.setColor(new Color(230, 230, 0, 255));
                                        graphics.drawString("[OP] " + playerOnline.getName(), (int) (0.29 * getWidth()), (int) (0.207 * getHeight()) + sizeNameMin + (int) ((sizeNameMin + getHeight() * 0.038) * onlineNum));
                                    } else {
                                        graphics.setColor(new Color(255, 255, 255, 230));
                                        graphics.drawString(playerOnline.getName(), (int) (0.29 * getWidth()), (int) (0.207 * getHeight()) + sizeNameMin + (int) ((sizeNameMin + getHeight() * 0.038) * onlineNum));
                                    }
                                    onlineNum++;
                                }
                                if (onlineNum == 8) break;
                            }
                        }
                    }

                    for (FlexText flexText : flexTextList)
                        if (flexText.getyCenterRatio() == -0.3551) {
                            int widthText = (int) (getWidth() * 0.08);
                            int heightText = (int) (getHeight() * 0.08);
                            int sizeTimeMin = Math.min(widthText, heightText);
                            graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (sizeTimeMin * 0.48)));
                            FontMetrics metrics = graphics.getFontMetrics();
                            String text = flexText.getText();
                            graphics.setColor(new Color(0, 0, 0, 100));
                            graphics.fillRect(getxCenter() - (metrics.stringWidth(text) / 2) - 1, (int) (0.1449 * getyCenter() + 0.08 + sizeTimeMin * 0.59), (int) (metrics.stringWidth(text) * 1.0), (int) (sizeTimeMin * 0.65));
                        }
                }
                // 绘制选中物名称
                if (player.getItemBarId()[player.getItemBarChosen()] != -1 && (player.getItemBarId()[player.getItemBarChosen()] != itemLastChosenId || itemChosenTimer > 0)) {
                    if (player.getItemBarId()[player.getItemBarChosen()] != itemLastChosenId)
                        itemChosenTimer = 100;
                    if (itemChosenTimer > 0) itemChosenTimer--;
                    int widthName = (int) (getWidth() * 0.025);
                    int heightName = (int) (getHeight() * 0.025);
                    int sizeTimeMin = min(widthName, heightName);
                    int itemId = player.getItemBarId()[player.getItemBarChosen()];
                    graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (sizeTimeMin * zoomGuiRatio * 0.85)));
                    FontMetrics metrics = graphics.getFontMetrics();
                    if (itemId != -1) {
                        String itemName = IDIndex.blockIDToName(itemId, language);
                        itemName = itemName.replace("_", "  ");
                        int decline = (int) (46 * (itemChosenTimer - 5));
                        if (decline >= 0) decline = 0;
                        graphics.setColor(new Color(255, 255, 255, 230 + decline));
                        graphics.drawString(itemName, (getWidth() - metrics.stringWidth(itemName)) / 2, getHeight() - (int) ((50 + sizeTimeMin) * zoomGuiRatio));
                    }
                }
                itemLastChosenId = player.getItemBarId()[player.getItemBarChosen()];
                if (player.getAttackCD() != 0) {
                    ((Graphics2D) graphics).setStroke(new BasicStroke((int) (5 * zoomGuiRatio))); // 调整圆环的宽度
                    // 画整个灰色圆环
                    graphics.setColor(Color.GRAY);
                    graphics.drawArc(xCenter + (int) (237 * zoomGuiRatio), getHeight() - (int) (52 * zoomGuiRatio), (int) (2 * 15 * zoomGuiRatio), (int) (2 * 15 * zoomGuiRatio), 0, 360);
                    // 画橙色色圆环（部分填充）
                    double percentage = 1 - ((double) player.getAttackCD() / IDIndex.blockIdToAttackCD(player.getItemBarId()[player.getItemBarChosen()]));
                    int angle = -(int) (percentage * 360);
                    graphics.setColor(Color.ORANGE);
                    graphics.drawArc(xCenter + (int) (237 * zoomGuiRatio), getHeight() - (int) (52 * zoomGuiRatio), (int) (2 * 15 * zoomGuiRatio), (int) (2 * 15 * zoomGuiRatio), 80, angle);
                }
            }
            if (isBackGui()) {
                graphics.setColor(new Color(0, 0, 0, 150));
                graphics.fillRect(0, 0, getWidth(), getHeight());
            }
        }
        if (!guiLoading) {
            // 绘制FlexButton图案
            for (FlexButton flexButton : flexButtonList) {
                graphics.drawImage(flexButton.getTextureCurrent(), flexButton.getX(), flexButton.getY(), this);
            }
            // 绘制FlexImage图案
            for (int i = 0; i < flexImageList.size(); i++) {
                graphics.drawImage(flexImageList.get(i).getTexture(), flexImageList.get(i).getX(), flexImageList.get(i).getY(), this);
            }
            // 绘制FlexGrid图案
            for (FlexGrid flexGrid : flexGridList) {
                flexGrid.drawGrid();
            }
            // 更新FlexGUI文字部分，用于确保文字在图像上方
            drawFlexGuiTextPart();
            if (itemAttachId != -1)
                drawItemAttach();
        }
        // 绘制悬停文字
        for (FlexGrid flexGrid : flexGridList) {
            if (flexGrid.getChosenTimer() >= 85 && flexGrid.getId() != -1) {
                int widthTime = (int) (getWidth() * 0.02);
                int heightTime = (int) (getHeight() * 0.02);
                int sizeTimeMin = Math.min(widthTime, heightTime);
                int itemId = flexGrid.getId();
                graphics.setFont(Resource.getFontList().get(0).deriveFont((float) sizeTimeMin));
                FontMetrics metrics = graphics.getFontMetrics();
                if (itemId != -1) {
                    String itemName = IDIndex.blockIDToName(itemId, language);
                    itemName = itemName.replace("_", "  ");
                    graphics.setColor(new Color(0, 0, 0, 100));
                    graphics.fillRect((int) mouseLocation.getX() + 10, (int) mouseLocation.getY() + sizeTimeMin, (int) (metrics.stringWidth(itemName) * 1.17), (int) (sizeTimeMin * 1.4));
                    graphics.setColor(new Color(255, 255, 255, 230));
                    graphics.drawString(itemName, (int) mouseLocation.getX() + 10 + (int) (metrics.stringWidth(itemName) * 0.1), (int) mouseLocation.getY() + (int) (sizeTimeMin * 2.1));
                }
            }
        }
    }

    // 绘制鼠标附属物品
    public void drawItemAttach() {
        int tmpWidth = (int) (getWidth() * 0.04);
        int tmpHeight = (int) (getHeight() * 0.07);
        Graphics graphics = offScreenUiImage.getGraphics();
        // 绘制物品
        Image image = Resource.getItemBarBlockTexture().get(itemAttachId).getScaledInstance((int) (tmpWidth * 0.7), (int) (tmpHeight * 0.7), Image.SCALE_FAST);
        graphics.drawImage(image, (int) (mouseLocation.getX() - tmpWidth * 0.35), (int) (mouseLocation.getY() - tmpHeight * 0.3), this);
        // 绘制文字
        if (IDIndex.blockIdToIsTool(itemAttachId)) {
            int widthResized = (int) (tmpWidth * 0.7);
            int heightResized = (int) (tmpHeight * 0.06);
            int widthOffset = (int) (tmpWidth * 0.355);
            int heightOffset = (int) (tmpHeight * 0.64);
            int barWidth = (int) (widthResized * ((double) itemAttachAmount / IDIndex.blockIdToMaxAmount(itemAttachId)));
            graphics.setColor(Color.darkGray);
            graphics.fillRect((int) mouseLocation.getX() - widthOffset, (int) mouseLocation.getY() + tmpHeight - heightOffset, widthResized, heightResized);
            if (barWidth > (widthResized * 2) / 3 && barWidth <= widthResized) graphics.setColor(Color.GREEN);
            else if (barWidth > 11 && barWidth <= (widthResized * 2) / 3) graphics.setColor(Color.ORANGE);
            else if (barWidth >= 0 && barWidth <= widthResized / 3) graphics.setColor(Color.RED);
            graphics.fillRect((int) mouseLocation.getX() - widthOffset, (int) mouseLocation.getY() + tmpHeight - heightOffset, barWidth, heightResized);
        } else if (itemAttachAmount >= 2) {
            int minValue = Integer.min((int) (tmpWidth * 0.7), (int) (tmpHeight * 0.7));
            graphics.setColor(Color.WHITE);
            graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (minValue / 2.3)));
            FontMetrics metrics = graphics.getFontMetrics();
            graphics.drawString(Integer.toString(itemAttachAmount), (int) (mouseLocation.getX() + tmpWidth * 0.24) - metrics.stringWidth(Integer.toString(itemAttachAmount)) / 2, (int) (mouseLocation.getY() + tmpHeight * 0.25 + minValue / 6));
        }
    }

    // 绘制FlexGUI文字部分，用于确保文字在图像上方
    public void drawFlexGuiTextPart() {
        for (FlexText flexText : flexTextList) {
            if (flexText.getText() != null) flexText.updateDrawText();
            if (flexText.isCanDelete()) flexTextList.remove(flexText);
        }
        if (!backGui && frameSort == 0 && guiOn) {
            for (ChatText chatText : chatTextList) {
                if (chatText.getText() != null) chatText.updateDrawText();
            }
        }
        if (!backGui && frameSort == 0 && terminalOn) {
            for (ChatText chatText : chatTextList) {
                if (chatText.getText() != null) {
                    chatText.setDeleteTimer(5);
                }
            }
            terminalText.setText(inputStringBuilder.toString());
            terminalText.setDeleteTimer(20);
            terminalText.updateDrawText();
        }
        for (FlexButton flexButton : flexButtonList) {
            if (flexButton.getText() != null) flexButton.updateDrawText();
            if (flexButton.isCanDelete()) flexButtonList.remove(flexButton);
        }
        for (FlexGrid flexGrid : flexGridList) {
            flexGrid.updateDrawText();
            if (flexGrid.isCanDelete()) flexGridList.remove(flexGrid);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (offScreenGameImage == null)
            offScreenGameImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        if (offScreenUiImage == null)
            offScreenUiImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 绘制 offScreenGameImage
        drawWorldTexture();
        drawGameTextureImage(xCameraRelative, yCameraRelative);
        // 绘制 offScreenUiImage
        drawGuiTextureImage();
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHints(hints);
        // 创建缩放后的图像
        if (frameSort == 0)
            offScreenGameImage = offScreenGameImage.getScaledInstance((int) (getWidth() * zoomGameRatio), (int) (getHeight() * zoomGameRatio), Image.SCALE_FAST);
        // 创建合成的画布
        BufferedImage mergedCanvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics mergedGraphics = mergedCanvas.getGraphics();
        // 将 offScreenGameImage 绘制到合成画布上
        if (frameSort == 0)
            mergedGraphics.drawImage(offScreenGameImage, -(int) ((zoomGameRatio - 1) * getWidth() / 2), -(int) ((zoomGameRatio - 1) * getHeight() / 2), null);
        else
            mergedGraphics.drawImage(offScreenGameImage, 0, 0, null);
        // 将 offScreenUiImage 绘制到合成画布上
        mergedGraphics.drawImage(offScreenUiImage, 0, 0, null);
        // 将合成的画布发送至窗口
        g.drawImage(mergedCanvas, 0, 0, null);
        // 释放资源
        mergedGraphics.dispose();
        offScreenGameImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        offScreenUiImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    // 更新镜头标准位移
    public void updateRelativeLocation() {
        xRelative = player.getxCenter() - xCenter;
        yRelative = player.getyCenter() - yCenter;
        // 从窗口坐标转换到世界坐标+Relative
        // 从世界坐标转换到窗口坐标-Relative
    }

    // 更新镜头位移
    public void updateCameraLocation() {
        int tmpxRelative = xRelative;
        int tmpyRelative = yRelative;
        xRelativeLag = xPlayerRelative - xCameraRelative;
        yRelativeLag = yPlayerRelative - yCameraRelative;
        xPlayerRelativeLag = tmpxRelative - xPlayerRelative;
        yPlayerRelativeLag = tmpyRelative - yPlayerRelative;
        xCameraRelative += xRelativeLag / 18;
        yCameraRelative += yRelativeLag / 18;
        xPlayerRelative += xPlayerRelativeLag / 18;
        yPlayerRelative += yPlayerRelativeLag / 18;
    }

    // 更新中心坐标
    public void updateCenterLocation() {
        xCenter = getWidth() / 2;
        yCenter = getHeight() / 2;
    }

    // 更新输入信息
    public void updateKeyInput() {
        singleKeyDetection.refresh();
        doubleKeyDetection.refresh();
        singleMouseDetector.refresh();
    }

    // 更新选中框显示状态
    public void updateBlockSelectionVisible() {
        // 如果开启了GUI
        if (guiOn) {
            double x = mouseLocation.getX();
            double y = mouseLocation.getY();
            if (player.getGame().isGuiOn() && x >= (player.getGame().getxCenter() - 224 * zoomGuiRatio) && x <= player.getGame().getxCenter() + 224 * zoomGuiRatio && y >= player.getGame().getHeight() - 63 * zoomGuiRatio && y <= player.getGame().getHeight()) {
                showBlockSelection2 = false;
            } else if (player.isAttackMode()) {
                showBlockSelection2 = false;
            } else if (IDIndex.blockIDToName(player.getItemBarId()[player.getItemBarChosen()], "English").contains("sword")) {
                showBlockSelection2 = false;
            } else showBlockSelection2 = true;
        } else showBlockSelection2 = false;
    }

    // 更新背景音乐
    public void updateMusic() {
        // 清除播放完成的音乐
        for (PlaySound sound : soundList) {
            if (sound.isEnded()) {
                soundList.remove(sound);
                sound = null;
            }
        }

        // 如果音乐为空，添加新音乐
        if (MusicOn && soundList.isEmpty()) {
            Random random = new Random();
            // 使用随机数随机播放音乐
            soundList.add(new PlaySound("music", random.nextInt(12), 100));
        }

        // 如果音乐关闭，清除音乐
        if (!MusicOn) {
            stopMusic();
        }
    }

    public void updateCrackList() {
        // 清除无用裂痕
        for (Crack crack1 : crackList) {
            if (crack1.getCrackSort() == 0) {
                for (Crack crack2 : crackList) {
                    if (crack1 != crack2 && Objects.equals(crack1.getPlayerName(), crack2.getPlayerName()))
                        crackList.remove(crack2);
                }
                crackList.remove(crack1);
            }
            for (Crack crack2 : crackList) {
                if (Objects.equals(crack1.getPlayerName(), crack2.getPlayerName()))
                    if (crack1.getCrackSort() < crack2.getCrackSort()) {
                        crackList.remove(crack2);
                        crack1.setCrackSort(crack2.getCrackSort());
                    }
            }
        }
    }

    // 立即停止所有音乐
    public void stopMusic() {
        for (PlaySound sound : soundList) {
            sound.stopPlay();
        }
    }

    // 更新拉伸世界背景图片
    public void updateWorldTexture() {
        if (worldCurrent.getTime() >= 0 && worldCurrent.getTime() < 25000)
            worldTimeAlphaDouble = 1;
        else if (worldCurrent.getTime() >= 25000 && worldCurrent.getTime() < 40000)
            worldTimeAlphaDouble = 1 - ((worldCurrent.getTime() - 25000) / 15000.0);
        else if (worldCurrent.getTime() >= 40000 && worldCurrent.getTime() < 90000)
            worldTimeAlphaDouble = 0;
        else if (worldCurrent.getTime() >= 90000 && worldCurrent.getTime() <= 105000)
            worldTimeAlphaDouble = 1 - ((105000 - worldCurrent.getTime()) / 15000.0);
        else if (worldCurrent.getTime() >= 105000 && worldCurrent.getTime() <= 120000)
            worldTimeAlphaDouble = 1;
        worldCurrentFinalTexture = (BufferedImage) ImageEditor.imageStain(ImageEditor.imageScale(worldCurrent.getTextureCurrent(), (int) getSize().getWidth(), (int) getSize().getHeight()), Color.BLACK, (int) (worldTimeAlphaDouble * 255));
        starFinalTexture = (BufferedImage) ImageEditor.imageTransparent(ImageEditor.imageScale(Resource.getWorldTextureList().get(2), (int) getSize().getWidth(), (int) getSize().getHeight()), (int) (worldTimeAlphaDouble * 100));
    }

    // 更新当前渲染格数
    public void updateVision() {
        int tmp;
        tmp = (player.getxCenter() - player.getxVision()) / 50;
        if (tmp >= 0) xLeftVision = tmp;
        else xLeftVision = 0;
        tmp = (player.getxCenter() + player.getxVision()) / 50;
        if (tmp <= worldCurrent.getWidth() - 1) xRightVision = tmp;
        else xRightVision = worldCurrent.getWidth() - 1;
        tmp = (player.getyCenter() - player.getyVision()) / 50;
        if (tmp >= 0) yUpVision = tmp;
        else yUpVision = 0;
        tmp = (player.getyCenter() + player.getyVision()) / 50;
        if (tmp <= worldCurrent.getHeight() - 1) yDownVision = tmp;
        else yDownVision = worldCurrent.getHeight() - 1;
    }

    // 更新视野内光照强度
    public void updateVisionLightIntensity() {
        renderLightIntensity(-100 + xLeftVision, 100 + xRightVision);
    }

    // 渲染光照
    public void renderLightIntensity(int xLeftSide, int xRightSide) {
        lightIntensityNext = new int[worldCurrent.getHeight()][worldCurrent.getWidth()];
        for (int y = 0; y < worldCurrent.getHeight(); y++)
            for (int x = 0; x < worldCurrent.getWidth(); x++)
                lightIntensityNext[y][x] = lightIntensity[y][x];

        // 自然光初次渲染
        for (int x = xLeftSide; x <= xRightSide; x++) {
            if (x <= 0 || x >= worldCurrent.getWidth() - 1) continue;
            double worldTimeAlphaDoubleTmp = worldTimeAlphaDouble;
            double brightValue = 1 - worldCurrent.getGama();
            if (brightValue < 0) brightValue = 0;
            else if (brightValue > 1) brightValue = 1;
            if (worldTimeAlphaDoubleTmp >= brightValue)
                worldTimeAlphaDoubleTmp = brightValue;
            int lightBlocked = (int) (worldTimeAlphaDoubleTmp * 255);
            for (int y = 1; y <= player.getWorld().getHeight() - 2; y++) {
                lightIntensityNext[y][x] = 255 - lightBlocked;
                if (player.getWorld().getBlockIdList()[y][x] != -1) {
                    if (player.getWorld().getBlockIdList()[y][x] == 7)
                        lightBlocked += (int) (17 * (brightValue));
                    else if (player.getWorld().getBlockIdList()[y][x] != 17)
                        lightBlocked += (int) (50 * (brightValue));
                    if (lightBlocked > 255) lightBlocked = 255;
                    else if (lightBlocked < 0) lightBlocked = 0;
                }
            }
        }
        // 更新视野内光源
        for (int x = xLeftSide; x <= xRightSide; x++) {
            for (int y = 1; y <= player.getWorld().getHeight() - 2; y++) {
                if (x < 0 || x >= worldCurrent.getWidth()) continue;
                if (IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y][x]))
                    lightIntensityNext[y][x] = 1255;
            }
        }
        // 光线衍射
        for (int y = 1; y <= player.getWorld().getHeight() - 2; y++) {
            for (int x = xLeftSide; x <= xRightSide; x++) {
                if (x <= 0 || x >= worldCurrent.getWidth() - 1) continue;
                if (lightIntensityNext[y][x - 1] > lightIntensityNext[y][x] || lightIntensityNext[y][x + 1] > lightIntensityNext[y][x]) {
                    if (lightIntensityNext[y][x - 1] < 0) lightIntensityNext[y][x - 1] = 0;
                    if (lightIntensityNext[y][x + 1] < 0) lightIntensityNext[y][x + 1] = 0;
                    if (lightIntensityNext[y][x] < 0) lightIntensityNext[y][x] = 0;
                    int light1 = (int) (lightIntensityNext[y][x - 1] * 0.8);
                    int light2 = (int) (lightIntensityNext[y][x + 1] * 0.8);
                    if (worldCurrent.getBlockIdList()[y][x - 1] == 7)
                        light1 = (int) (lightIntensityNext[y][x - 1] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y][x - 1] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y][x - 1]))
                        light1 = (int) (lightIntensityNext[y][x - 1] * 0.3);
                    if (worldCurrent.getBlockIdList()[y][x + 1] == 7)
                        light2 = (int) (lightIntensityNext[y][x + 1] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y][x + 1] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y][x + 1]))
                        light2 = (int) (lightIntensityNext[y][x + 1] * 0.3);
                    int light3 = max(lightIntensityNext[y][x], max(light1, light2));
                    lightIntensityNext[y][x] = light3;
                }
            }
            for (int x = xRightSide; x >= xLeftSide; x--) {
                if (x <= 0 || x >= worldCurrent.getWidth() - 1) continue;
                if (lightIntensityNext[y][x - 1] > lightIntensityNext[y][x] || lightIntensityNext[y][x + 1] > lightIntensityNext[y][x]) {
                    if (lightIntensityNext[y][x - 1] < 0) lightIntensityNext[y][x - 1] = 0;
                    else if (lightIntensityNext[y][x + 1] < 0) lightIntensityNext[y][x + 1] = 0;
                    int light1 = (int) (lightIntensityNext[y][x - 1] * 0.8);
                    int light2 = (int) (lightIntensityNext[y][x + 1] * 0.8);
                    if (worldCurrent.getBlockIdList()[y][x - 1] == 7)
                        light1 = (int) (lightIntensityNext[y][x - 1] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y][x - 1] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y][x - 1]))
                        light1 = (int) (lightIntensityNext[y][x - 1] * 0.3);
                    if (worldCurrent.getBlockIdList()[y][x + 1] == 7)
                        light2 = (int) (lightIntensityNext[y][x + 1] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y][x + 1] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y][x + 1]))
                        light2 = (int) (lightIntensityNext[y][x + 1] * 0.3);
                    int light3 = max(lightIntensityNext[y][x], max(light1, light2));
                    lightIntensityNext[y][x] = light3;
                }
            }
        }
        for (int x = xLeftSide; x <= xRightSide; x++) {
            if (x < 0 || x >= worldCurrent.getWidth()) continue;
            for (int y = 1; y < player.getWorld().getHeight() - 1; y++) {
                if (lightIntensityNext[y - 1][x] > lightIntensityNext[y][x] || lightIntensityNext[y + 1][x] > lightIntensityNext[y][x]) {
                    if (lightIntensityNext[y - 1][x] < 0) lightIntensityNext[y - 1][x] = 0;
                    if (lightIntensityNext[y + 1][x] < 0) lightIntensityNext[y + 1][x] = 0;
                    if (lightIntensityNext[y][x] < 0) lightIntensityNext[y][x] = 0;
                    int light1 = (int) (lightIntensityNext[y - 1][x] * 0.8);
                    int light2 = (int) (lightIntensityNext[y + 1][x] * 0.8);
                    if (worldCurrent.getBlockIdList()[y - 1][x] == 7)
                        light1 = (int) (lightIntensityNext[y - 1][x] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y - 1][x] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y - 1][x]))
                        light1 = (int) (lightIntensityNext[y - 1][x] * 0.3);
                    if (worldCurrent.getBlockIdList()[y + 1][x] == 7)
                        light2 = (int) (lightIntensityNext[y + 1][x] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y + 1][x] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y + 1][x]))
                        light2 = (int) (lightIntensityNext[y + 1][x] * 0.3);
                    int light3 = max(lightIntensityNext[y][x], max(light1, light2));
                    lightIntensityNext[y][x] = light3;
                }
            }
            for (int y = player.getWorld().getHeight() - 2; y > 0; y--) {
                if (lightIntensityNext[y - 1][x] > lightIntensityNext[y][x] || lightIntensityNext[y + 1][x] > lightIntensityNext[y][x]) {
                    if (lightIntensityNext[y - 1][x] < 0) lightIntensityNext[y - 1][x] = 0;
                    if (lightIntensityNext[y + 1][x] < 0) lightIntensityNext[y + 1][x] = 0;
                    if (lightIntensityNext[y][x] < 0) lightIntensityNext[y][x] = 0;
                    int light1 = (int) (lightIntensityNext[y - 1][x] * 0.8);
                    int light2 = (int) (lightIntensityNext[y + 1][x] * 0.8);
                    if (worldCurrent.getBlockIdList()[y - 1][x] == 7)
                        light1 = (int) (lightIntensityNext[y - 1][x] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y - 1][x] != -1)
                        light1 = (int) (lightIntensityNext[y - 1][x] * 0.3);
                    if (worldCurrent.getBlockIdList()[y + 1][x] == 7)
                        light2 = (int) (lightIntensityNext[y + 1][x] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y + 1][x] != -1)
                        light2 = (int) (lightIntensityNext[y + 1][x] * 0.3);
                    int light3 = max(lightIntensityNext[y][x], max(light1, light2));
                    lightIntensityNext[y][x] = light3;
                }
            }
        }
        for (int y = 0; y < player.getWorld().getHeight(); y++) {
            for (int x = xLeftSide; x <= xRightSide; x++) {
                if (x <= 0 || x >= worldCurrent.getWidth() - 1) continue;
                if (lightIntensityNext[y][x - 1] > lightIntensityNext[y][x] || lightIntensityNext[y][x + 1] > lightIntensityNext[y][x]) {
                    if (lightIntensityNext[y][x - 1] < 0) lightIntensityNext[y][x - 1] = 0;
                    if (lightIntensityNext[y][x + 1] < 0) lightIntensityNext[y][x + 1] = 0;
                    if (lightIntensityNext[y][x] < 0) lightIntensityNext[y][x] = 0;
                    int light1 = (int) (lightIntensityNext[y][x - 1] * 0.8);
                    int light2 = (int) (lightIntensityNext[y][x + 1] * 0.8);
                    if (worldCurrent.getBlockIdList()[y][x - 1] == 7)
                        light1 = (int) (lightIntensityNext[y][x - 1] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y][x - 1] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y][x - 1]))
                        light1 = (int) (lightIntensityNext[y][x - 1] * 0.3);
                    if (worldCurrent.getBlockIdList()[y][x + 1] == 7)
                        light2 = (int) (lightIntensityNext[y][x + 1] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y][x + 1] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y][x + 1]))
                        light2 = (int) (lightIntensityNext[y][x + 1] * 0.3);
                    int light3 = max(lightIntensityNext[y][x], max(light1, light2));
                    lightIntensityNext[y][x] = light3;
                }
            }
            for (int x = xRightSide; x >= xLeftSide; x--) {
                if (x <= 0 || x >= worldCurrent.getWidth() - 1) continue;
                if (lightIntensityNext[y][x - 1] > lightIntensityNext[y][x] || lightIntensityNext[y][x + 1] > lightIntensityNext[y][x]) {
                    if (lightIntensityNext[y][x - 1] < 0) lightIntensityNext[y][x - 1] = 0;
                    else if (lightIntensityNext[y][x + 1] < 0) lightIntensityNext[y][x + 1] = 0;
                    int light1 = (int) (lightIntensityNext[y][x - 1] * 0.8);
                    int light2 = (int) (lightIntensityNext[y][x + 1] * 0.8);
                    if (worldCurrent.getBlockIdList()[y][x - 1] == 7)
                        light1 = (int) (lightIntensityNext[y][x - 1] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y][x - 1] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y][x - 1]))
                        light1 = (int) (lightIntensityNext[y][x - 1] * 0.3);
                    if (worldCurrent.getBlockIdList()[y][x + 1] == 7)
                        light2 = (int) (lightIntensityNext[y][x + 1] * 0.3);
                    else if (worldCurrent.getBlockIdList()[y][x + 1] != -1 && !IDIndex.blockIdToIsLightSource(worldCurrent.getBlockIdList()[y][x + 1]))
                        light2 = (int) (lightIntensityNext[y][x + 1] * 0.3);
                    int light3 = max(lightIntensityNext[y][x], max(light1, light2));
                    lightIntensityNext[y][x] = light3;
                }
            }
        }
        lightIntensity = lightIntensityNext;
    }

    // 更新视野边界光照强度
    public void updateSideLightIntensity() {
        int xPlayerCenterTmp = player.getxCenter();
        int leftSideLast = -20 + (xPlayerLastCenter - player.getxVision()) / 50;
        int leftSide = -20 + (xPlayerCenterTmp - player.getxVision()) / 50;
        int rightSideLast = 20 + (xPlayerLastCenter + player.getxVision()) / 50;
        int rightSide = 20 + (xPlayerCenterTmp + player.getxVision()) / 50;
        if (leftSide < leftSideLast)
            renderLightIntensity(leftSide, leftSideLast - 1);
        else if (rightSide > rightSideLast)
            renderLightIntensity(rightSideLast + 1, rightSide);
        xPlayerLastCenter = xPlayerCenterTmp;
    }

    // 更新游戏内背景图片信息
    public void updateWorldBackgroundTexture() {
        if (frameSort == 0) {
            if (xCenter != getWidth() / 2 || yCenter != getHeight() / 2) {
                // 更新中心坐标
                updateCenterLocation();
                // 更改离屏图像大小
                offScreenGameImage = createImage(getWidth(), getHeight());
                updateWorldTexture();
            }
            // 更新世界时间图片
            if ((worldCurrent.getTime() >= 25000 && worldCurrent.getTime() <= 40000) || (worldCurrent.getTime() >= 90000 && worldCurrent.getTime() <= 105000)) {
                if (worldCurrent.getTime() % 500 == 0) {
                    updateWorldTexture();
                    updateVisionLightIntensity();
                }
            }
        }
    }

    // 刷新屏幕大小
    public void updateTextureSize() {
        // 更改离屏图像大小
        Image tmpOffScreenImage = createImage(getWidth(), getHeight());
        BufferedImage tmpMenuTexture = null;
        if (frameSort == 1)
            tmpMenuTexture = (BufferedImage) ImageEditor.imageScale(Resource.getWorldTextureList().get(3), (int) getSize().getWidth(), (int) getSize().getHeight());
        if (frameSort == 2 || frameSort == 3 || frameSort == 4 || frameSort == 5)
            tmpMenuTexture = (BufferedImage) ImageEditor.imageScale(Resource.getWorldTextureList().get(3), (int) getSize().getWidth(), (int) getSize().getHeight());
        menuTexture = tmpMenuTexture;
        offScreenGameImage = tmpOffScreenImage;
    }

    // 更新游戏菜单背景图片信息
    public void updateMenuBackgroundTexture() {
        if (!(frameSort == 0)) {
            if (xCenter != getWidth() / 2 || yCenter != getHeight() / 2) {
                // 更新中心坐标
                updateCenterLocation();
                // 更改离屏图像大小
                updateTextureSize();
            }
        }
    }

    // 更新鼠标附属数量id
    public void updateItemAttach() {
        if (itemAttachAmount == 0) itemAttachId = -1;
        if (inventoryOpened) {
            for (int i = 0; i < 36; i++) {
                if (player.getItemBarId()[i] != -1) {
                    for (FlexGrid flexGrid : flexGridList) {
                        if (flexGrid.getSort() == i) {
                            flexGrid.setId(player.getItemBarId()[i]);
                            flexGrid.setAmount(player.getItemBarAmount()[i]);
                            if (multiPlayerMode)
                                worldCurrent.updatePlayerItemDataToServer(player);
                        }
                    }
                }
            }
        }
        if (!backGui) {
            if (itemAttachId != -1) {
                if (Objects.equals(player.getFaceTo(), "left"))
                    player.loseItem(itemAttachId, itemAttachAmount, -9, true);
                else if (Objects.equals(player.getFaceTo(), "right"))
                    player.loseItem(itemAttachId, itemAttachAmount, 9, true);
                itemAttachId = -1;
                itemAttachAmount = 0;
                if (multiPlayerMode)
                    worldCurrent.updatePlayerItemDataToServer(player);
                if (multiPlayerMode)
                    worldCurrent.updatePlayerItemDataToServer(player);
            }
        }
    }

    // 更新弹出合成框内物品计时器
    public void updateLoseItemInCraftingTableTimer() {
        if (loseItemInCraftingTableTimer > 0) loseItemInCraftingTableTimer--;
        else if (loseItemInCraftingTableTimer < 0) loseItemInCraftingTableTimer = 0;
    }

    // 弹出合成框内的物品
    public void loseItemInCraftingTable() {
        if (loseItemInCraftingTableTimer == 0) {
            loseItemInCraftingTableTimer = 5;
            List<Integer> loseId = new ArrayList<>();
            List<Integer> loseAmount = new ArrayList<>();
            for (FlexGrid flexGrid : flexGridList) {
                if (flexGrid.getSort() >= 41 && flexGrid.getSort() <= 49 && flexGrid.getId() != -1) {
                    if (loseId.contains(flexGrid.getId())) {
                        loseAmount.set(loseId.indexOf(flexGrid.getId()), loseAmount.get(loseId.indexOf(flexGrid.getId())) + flexGrid.getAmount());
                    } else {
                        loseId.add(flexGrid.getId());
                        loseAmount.add(flexGrid.getAmount());
                    }
                }
            }
            for (int i = 0; i < loseId.size(); i++) {
                if (Objects.equals(player.getFaceTo(), "left"))
                    player.loseItem(loseId.get(i), loseAmount.get(i), -9, true);
                else if (Objects.equals(player.getFaceTo(), "right"))
                    player.loseItem(loseId.get(i), loseAmount.get(i), 9, true);
            }
        }
    }

    public void serverClosed() {
        gamePaused = false;
        showFrame5("ServerStopped");
        player = null;
        worldCurrent = null;
        mouseLeftPressed = false;
        mouseRightPressed = false;
        backGui = false;
    }

    // 获取帧率
    public static int getFrameRate(GameFrame game) {
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTime;

        if (elapsedTime >= 1_000_000_000) {
            // 1 second has passed
            frameRate = (int) (frameCount / (elapsedTime / 1_000_000_000.0));
            frameCount = 0;
            lastTime = currentTime;
        }

        frameCount++;

        return frameRate;
    }

    // 更新聊天栏
    public void updateChatTextList() {
        if (chatTextList.size() >= 8) chatTextList.remove(0);
    }

    // 线程函数
    @Override
    public void run() {
        while (true) {
            // 更新屏幕
            repaint();
            // 更新鼠标相对屏幕位置
            if (mouseFollower != null)
                mouseLocation.setLocation(mouseFollower.getMouseX(this), mouseFollower.getMouseY(this));
            // 更新GUI
            updateFlexGUI();
            if (frameSort == 0) {
                // 更新聊天栏
                updateChatTextList();
                // 更新当前渲染格数
                if (player != null)
                    updateVision();
                // 更新背景音乐
                if (isMusicOn())
                    updateMusic();
                // 更新镜头标准位移
                updateRelativeLocation();
                // 更新镜头位移
                updateCameraLocation();
                // 更新方块选中框显示状态
                updateBlockSelectionVisible();
                // 更新传输键盘信息到游戏
                updateKeyInput();
                // 更新游戏内背景图片信息
                updateWorldBackgroundTexture();
                // 更新鼠标附属数量id
                updateItemAttach();
                if (player != null && multiPlayerMode) {
                    // 清除无用裂痕
                    updateCrackList();
                }
                if (backGui) {
                    // 更新合成配方
                    updateCrafting(this);
                }
            } else {
                // 更新菜单背景图片信息
                updateMenuBackgroundTexture();
            }
            // 更新中心坐标
            updateCenterLocation();
            // 更新弹出合成框内物品计时器
            updateLoseItemInCraftingTableTimer();
            // 定义线程每5ms执行一次
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Image getOffScreenGameImage() {
        return offScreenGameImage;
    }

    public void setOffScreenGameImage(Image offScreenGameImage) {
        this.offScreenGameImage = offScreenGameImage;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isNameOn() {
        return nameOn;
    }

    public void setNameOn(boolean nameOn) {
        this.nameOn = nameOn;
    }

    public boolean isGuiOn() {
        return guiOn;
    }

    public void setGuiOn(boolean guiOn) {
        this.guiOn = guiOn;
    }

    public boolean isShowBlockSelection1() {
        return showBlockSelection1;
    }

    public void setShowBlockSelection1(boolean showBlockSelection1) {
        this.showBlockSelection1 = showBlockSelection1;
    }

    public boolean isShowBlockSelection2() {
        return showBlockSelection2;
    }

    public void setShowBlockSelection2(boolean showBlockSelection2) {
        this.showBlockSelection2 = showBlockSelection2;
    }

    public boolean isMusicOn() {
        return MusicOn;
    }

    public void setMusicOn(boolean musicOn) {
        MusicOn = musicOn;
    }

    public List<PlaySound> getSoundList() {
        return soundList;
    }

    public void setSoundList(List<PlaySound> soundList) {
        this.soundList = soundList;
    }

    public SingleKey getSingleKeyDetection() {
        return singleKeyDetection;
    }

    public void setSingleKeyDetection(SingleKey singleKeyDetection) {
        this.singleKeyDetection = singleKeyDetection;
    }

    public DoubleKey getDoubleKeyDetection() {
        return doubleKeyDetection;
    }

    public void setDoubleKeyDetection(DoubleKey doubleKeyDetection) {
        this.doubleKeyDetection = doubleKeyDetection;
    }

    public SingleMouse getSingleClickDetection() {
        return singleMouseDetector;
    }

    public void setSingleClickDetection(SingleMouse singleMouseDetection) {
        this.singleMouseDetector = singleMouseDetection;
    }

    public DoubleMouse getDoubleClickDetection() {
        return doubleMouseDetector;
    }

    public void setDoubleClickDetection(DoubleMouse doubleMouseDetection) {
        this.doubleMouseDetector = doubleMouseDetection;
    }

    public MouseWheel getMouseWheelDetector() {
        return mouseWheelDetector;
    }

    public void setMouseWheelDetector(MouseWheel mouseWheelDetector) {
        this.mouseWheelDetector = mouseWheelDetector;
    }

    public MouseFollow getMouseFollower() {
        return mouseFollower;
    }

    public void setMouseFollower(MouseFollow mouseFollow) {
        this.mouseFollower = mouseFollow;
    }

    public Point getMouseLocation() {
        return mouseLocation;
    }

    public void setMouseLocation(Point mouseLocation) {
        this.mouseLocation = mouseLocation;
    }

    public int getFrameSort() {
        return frameSort;
    }

    public void setFrameSort(int frameSort) {
        this.frameSort = frameSort;
    }

    public SingleMouse getSingleMousePressDetection() {
        return singleMouseDetector;
    }

    public void setSingleMousePressDetection(SingleMouse singleMouseDetection) {
        this.singleMouseDetector = singleMouseDetection;
    }

    public DoubleMouse getDoubleMousePressDetection() {
        return doubleMouseDetector;
    }

    public void setDoubleMousePressDetection(DoubleMouse doubleMouseDetection) {
        this.doubleMouseDetector = doubleMouseDetection;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public BufferedImage getWorldCurrentFinalTexture() {
        return worldCurrentFinalTexture;
    }

    public void setWorldCurrentFinalTexture(BufferedImage worldCurrentFinalTexture) {
        this.worldCurrentFinalTexture = worldCurrentFinalTexture;
    }

    public GraphicsEnvironment getGe() {
        return ge;
    }

    public void setGe(GraphicsEnvironment ge) {
        this.ge = ge;
    }

    public GraphicsDevice getGd() {
        return gd;
    }

    public void setGd(GraphicsDevice gd) {
        this.gd = gd;
    }

    public int getxCenter() {
        return xCenter;
    }

    public void setxCenter(int xCenter) {
        this.xCenter = xCenter;
    }

    public int getyCenter() {
        return yCenter;
    }

    public void setyCenter(int yCenter) {
        this.yCenter = yCenter;
    }

    public int getxCameraRelative() {
        return xCameraRelative;
    }

    public void setxCameraRelative(int xCameraRelative) {
        this.xCameraRelative = xCameraRelative;
    }

    public int getyCameraRelative() {
        return yCameraRelative;
    }

    public void setyCameraRelative(int yCameraRelative) {
        this.yCameraRelative = yCameraRelative;
    }

    public World getWorldCurrent() {
        return worldCurrent;
    }

    public void setWorldCurrent(World worldCurrent) {
        this.worldCurrent = worldCurrent;
    }

    public int getxPlayerRelative() {
        return xPlayerRelative;
    }

    public void setxPlayerRelative(int xPlayerRelative) {
        this.xPlayerRelative = xPlayerRelative;
    }

    public int getyPlayerRelative() {
        return yPlayerRelative;
    }

    public void setyPlayerRelative(int yPlayerRelative) {
        this.yPlayerRelative = yPlayerRelative;
    }

    public int getxRelative() {
        return xRelative;
    }

    public void setxRelative(int xRelative) {
        this.xRelative = xRelative;
    }

    public int getyRelative() {
        return yRelative;
    }

    public void setyRelative(int yRelative) {
        this.yRelative = yRelative;
    }

    public int getxRelativeLag() {
        return xRelativeLag;
    }

    public void setxRelativeLag(int xRelativeLag) {
        this.xRelativeLag = xRelativeLag;
    }

    public int getyRelativeLag() {
        return yRelativeLag;
    }

    public void setyRelativeLag(int yRelativeLag) {
        this.yRelativeLag = yRelativeLag;
    }

    public int getxPlayerRelativeLag() {
        return xPlayerRelativeLag;
    }

    public void setxPlayerRelativeLag(int xPlayerRelativeLag) {
        this.xPlayerRelativeLag = xPlayerRelativeLag;
    }

    public int getyPlayerRelativeLag() {
        return yPlayerRelativeLag;
    }

    public void setyPlayerRelativeLag(int yPlayerRelativeLag) {
        this.yPlayerRelativeLag = yPlayerRelativeLag;
    }

    public boolean isBackGui() {
        return backGui;
    }

    public void setBackGui(boolean backGui) {
        this.backGui = backGui;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getxLeftVision() {
        return xLeftVision;
    }

    public void setxLeftVision(int xLeftVision) {
        this.xLeftVision = xLeftVision;
    }

    public int getxRightVision() {
        return xRightVision;
    }

    public void setxRightVision(int xRightVision) {
        this.xRightVision = xRightVision;
    }

    public int getyUpVision() {
        return yUpVision;
    }

    public void setyUpVision(int yUpVision) {
        this.yUpVision = yUpVision;
    }

    public int getyDownVision() {
        return yDownVision;
    }

    public void setyDownVision(int yDownVision) {
        this.yDownVision = yDownVision;
    }

    public SingleMouse getSingleMouseDetector() {
        return singleMouseDetector;
    }

    public void setSingleMouseDetector(SingleMouse singleMouseDetector) {
        this.singleMouseDetector = singleMouseDetector;
    }

    public DoubleMouse getDoubleMouseDetector() {
        return doubleMouseDetector;
    }

    public void setDoubleMouseDetector(DoubleMouse doubleMouseDetector) {
        this.doubleMouseDetector = doubleMouseDetector;
    }

    public WindowListen getWindowListener() {
        return windowListener;
    }

    public void setWindowListener(WindowListen windowListener) {
        this.windowListener = windowListener;
    }

    public List<FlexButton> getFlexButtonList() {
        return flexButtonList;
    }

    public void setFlexButtonList(List<FlexButton> flexButtonList) {
        this.flexButtonList = flexButtonList;
    }

    public List<FlexText> getFlexTextList() {
        return flexTextList;
    }

    public void setFlexTextList(List<FlexText> flexTextList) {
        this.flexTextList = flexTextList;
    }

    public boolean isHandItemOn() {
        return handItemOn;
    }

    public void setHandItemOn(boolean handItemOn) {
        this.handItemOn = handItemOn;
    }

    public BufferedImage getStarFinalTexture() {
        return starFinalTexture;
    }

    public void setStarFinalTexture(BufferedImage starFinalTexture) {
        this.starFinalTexture = starFinalTexture;
    }

    public boolean isTimeVisible() {
        return timeVisible;
    }

    public void setTimeVisible(boolean timeVisible) {
        this.timeVisible = timeVisible;
    }

    public double getWorldTimeAlphaDouble() {
        return worldTimeAlphaDouble;
    }

    public void setWorldTimeAlphaDouble(double worldTimeAlphaDouble) {
        this.worldTimeAlphaDouble = worldTimeAlphaDouble;
    }

    public int[][] getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(int[][] lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public int[][] getLightIntensityNext() {
        return lightIntensityNext;
    }

    public void setLightIntensityNext(int[][] lightIntensityNext) {
        this.lightIntensityNext = lightIntensityNext;
    }

    public int getxPlayerLastCenter() {
        return xPlayerLastCenter;
    }

    public void setxPlayerLastCenter(int xPlayerLastCenter) {
        this.xPlayerLastCenter = xPlayerLastCenter;
    }

    public boolean isHealthBarVisible() {
        return healthBarVisible;
    }

    public void setHealthBarVisible(boolean healthBarVisible) {
        this.healthBarVisible = healthBarVisible;
    }

    public boolean isMouseLeftPressed() {
        return mouseLeftPressed;
    }

    public void setMouseLeftPressed(boolean mouseLeftPressed) {
        this.mouseLeftPressed = mouseLeftPressed;
    }

    public boolean isMouseRightPressed() {
        return mouseRightPressed;
    }

    public void setMouseRightPressed(boolean mouseRightPressed) {
        this.mouseRightPressed = mouseRightPressed;
    }

    public BufferedImage getMenuTexture() {
        return menuTexture;
    }

    public void setMenuTexture(BufferedImage menuTexture) {
        this.menuTexture = menuTexture;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public List<FlexImage> getFlexImageList() {
        return flexImageList;
    }

    public void setFlexImageList(List<FlexImage> flexImageList) {
        this.flexImageList = flexImageList;
    }

    public boolean isInventoryOpened() {
        return inventoryOpened;
    }

    public void setInventoryOpened(boolean inventoryOpened) {
        this.inventoryOpened = inventoryOpened;
    }

    public int getItemAttachId() {
        return itemAttachId;
    }

    public void setItemAttachId(int itemAttachId) {
        this.itemAttachId = itemAttachId;
    }

    public int getItemAttachAmount() {
        return itemAttachAmount;
    }

    public void setItemAttachAmount(int itemAttachAmount) {
        this.itemAttachAmount = itemAttachAmount;
    }

    public List<FlexGrid> getFlexGridList() {
        return flexGridList;
    }

    public void setFlexGridList(List<FlexGrid> flexGridList) {
        this.flexGridList = flexGridList;
    }

    public boolean isCrafted() {
        return crafted;
    }

    public void setCrafted(boolean crafted) {
        this.crafted = crafted;
    }

    public boolean isCraftingTableOpened() {
        return craftingTableOpened;
    }

    public void setCraftingTableOpened(boolean craftingTableOpened) {
        this.craftingTableOpened = craftingTableOpened;
    }

    public int getLoseItemInCraftingTableTimer() {
        return loseItemInCraftingTableTimer;
    }

    public void setLoseItemInCraftingTableTimer(int loseItemInCraftingTableTimer) {
        this.loseItemInCraftingTableTimer = loseItemInCraftingTableTimer;
    }

    public int getInventoryPage() {
        return inventoryPage;
    }

    public void setInventoryPage(int inventoryPage) {
        this.inventoryPage = inventoryPage;
    }

    public boolean isShiftPressed() {
        return shiftPressed;
    }

    public void setShiftPressed(boolean shiftPressed) {
        this.shiftPressed = shiftPressed;
    }

    public boolean isMouseMiddlePressed() {
        return mouseMiddlePressed;
    }

    public void setMouseMiddlePressed(boolean mouseMiddlePressed) {
        this.mouseMiddlePressed = mouseMiddlePressed;
    }

    public boolean isCtrlPressed() {
        return ctrlPressed;
    }

    public void setCtrlPressed(boolean ctrlPressed) {
        this.ctrlPressed = ctrlPressed;
    }

    public boolean isAllCrafted() {
        return allCrafted;
    }

    public void setAllCrafted(boolean allCrafted) {
        this.allCrafted = allCrafted;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getItemChosenTimer() {
        return itemChosenTimer;
    }

    public void setItemChosenTimer(int itemChosenTimer) {
        this.itemChosenTimer = itemChosenTimer;
    }

    public int getItemLastChosenId() {
        return itemLastChosenId;
    }

    public void setItemLastChosenId(int itemLastChosenId) {
        this.itemLastChosenId = itemLastChosenId;
    }

    public double getZoomGameRatio() {
        return zoomGameRatio;
    }

    public void setZoomGameRatio(double zoomGameRatio) {
        this.zoomGameRatio = zoomGameRatio;
    }

    public boolean isGuiLoading() {
        return guiLoading;
    }

    public void setGuiLoading(boolean guiLoading) {
        this.guiLoading = guiLoading;
    }

    public Image getOffScreenUiImage() {
        return offScreenUiImage;
    }

    public void setOffScreenUiImage(Image offScreenUiImage) {
        this.offScreenUiImage = offScreenUiImage;
    }

    public double getZoomGuiRatio() {
        return zoomGuiRatio;
    }

    public void setZoomGuiRatio(double zoomGuiRatio) {
        this.zoomGuiRatio = zoomGuiRatio;
    }

    public static String getPathServer() {
        return pathServer;
    }

    public static void setPathServer(String pathServer) {
        GameFrame.pathServer = pathServer;
    }

    public Thread getEnterServerThread() {
        return enterServerThread;
    }

    public void setEnterServerThread(Thread enterServerThread) {
        this.enterServerThread = enterServerThread;
    }

    public boolean[] getLinkState() {
        return linkState;
    }

    public void setLinkState(boolean[] linkState) {
        this.linkState = linkState;
    }

    public Thread getTimeOutThread() {
        return timeOutThread;
    }

    public void setTimeOutThread(Thread timeOutThread) {
        this.timeOutThread = timeOutThread;
    }

    public boolean isMultiPlayerMode() {
        return multiPlayerMode;
    }

    public void setMultiPlayerMode(boolean multiPlayerMode) {
        this.multiPlayerMode = multiPlayerMode;
    }

    public List<Crack> getCrackList() {
        return crackList;
    }

    public void setCrackList(List<Crack> crackList) {
        this.crackList = crackList;
    }

    public boolean isShowOnlinePlayerList() {
        return showOnlinePlayerList;
    }

    public void setShowOnlinePlayerList(boolean showOnlinePlayerList) {
        this.showOnlinePlayerList = showOnlinePlayerList;
    }

    public boolean isLinkDeny() {
        return linkDeny;
    }

    public void setLinkDeny(boolean linkDeny) {
        this.linkDeny = linkDeny;
    }

    public List<ChatText> getChatTextList() {
        return chatTextList;
    }

    public void setChatTextList(List<ChatText> chatTextList) {
        this.chatTextList = chatTextList;
    }

    public boolean isTerminalOn() {
        return terminalOn;
    }

    public void setTerminalOn(boolean terminalOn) {
        this.terminalOn = terminalOn;
    }

    public StringBuilder getInputStringBuilder() {
        return inputStringBuilder;
    }

    public void setInputStringBuilder(StringBuilder inputStringBuilder) {
        this.inputStringBuilder = inputStringBuilder;
    }

    public ChatText getTerminalText() {
        return terminalText;
    }

    public void setTerminalText(ChatText terminalText) {
        this.terminalText = terminalText;
    }

    public boolean isInfoOn() {
        return infoOn;
    }

    public void setInfoOn(boolean infoOn) {
        this.infoOn = infoOn;
    }

    public Thread getLogTimerThread() {
        return LogTimerThread;
    }

    public void setLogTimerThread(Thread logTimerThread) {
        LogTimerThread = logTimerThread;
    }

    public static long getLastTime() {
        return lastTime;
    }

    public static void setLastTime(long lastTime) {
        GameFrame.lastTime = lastTime;
    }

    public static int getFrameCount() {
        return frameCount;
    }

    public static void setFrameCount(int frameCount) {
        GameFrame.frameCount = frameCount;
    }

    public static int getFrameRate() {
        return frameRate;
    }

    public static void setFrameRate(int frameRate) {
        GameFrame.frameRate = frameRate;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isBaniped() {
        return baniped;
    }

    public void setBaniped(boolean baniped) {
        this.baniped = baniped;
    }
}
