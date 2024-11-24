package Base;

import Element.Entity;
import EntityType.Item;
import EntityType.Player;
import EntityType.Zombie;
import Utils.OtherTool.IDIndex;
import Utils.OtherTool.PlaySound;
import Utils.OtherTool.Resource;
import Utils.OtherTool.StringConversion;
import Utils.TCP.TCPClient;
import Utils.WorldTool.WorldLoad;
import Utils.WorldTool.WorldSave;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static Utils.OtherTool.IDIndex.blockIdToItemId;
import static Utils.OtherTool.IDIndex.blockIdToResistance;
import static java.lang.Math.abs;

public class World implements Runnable {
    // 定义世界大小(方块数)
    int width = 200;
    int height = 100;
    int voidSize = 10;
    // 定义方块大小
    int blockSize = 50;
    // 定义亮度值
    double gama = 0.25;
    // 定义世界出生点
    int xSpawn = 50;
    int ySpawn = (height - 5) * 50;
    // 定义世界重力加速度
    double gravity = 0.5;
    // 定义世界空气阻力
    double airResistance = 0.5;
    // ★定义世界材质包
    private List<BufferedImage> textureList = null;
    // ★定义世界当前材质
    private BufferedImage textureCurrent = null;
    // 定义世界线程
    private Thread thread = new Thread(this);
    // 定义世界存储器
    private WorldLoad worldLoader = new WorldLoad();
    private WorldSave worldSaver = new WorldSave();
    // 定义世界难度
    // peaceful easy normal hard
    private String difficulty = "normal";
    // 定义世界怪物数量上限
    private int mobAmountMax = 10;
    // 定义世界当前怪物数量
    private int mobAmount = 0;
    // 定义世界时间(0~120000)
    private int time = 60000;
    // 定义在线状态计时器
    private int onlineTimer = 1200;
    // 定义世界实体列表
    private List<Entity> entityList = new CopyOnWriteArrayList<>();
    // 定义世界玩家列表(包含离线玩家)
    private List<Player> playerList = new CopyOnWriteArrayList<>();
    // 定义世界音效列表
    private List<PlaySound> soundList = new CopyOnWriteArrayList<>();
    // 定义世界所有方块ID列表，第一格为y，第二格为x
    private int[][] blockIdList = new int[height][width];

    public World(String name) {
        // 设置世界材质包
        textureList = Resource.getWorldTextureList();
        // 设置世界当前材质
        textureCurrent = textureList.get(1);
    }

    // 计算整型欧氏距离
    public static double calculateIntDistance(int x1, int y1, int x2, int y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        // 使用欧几里德距离公式计算距离
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance;
    }

    // 计算浮点型欧氏距离
    public static double calculateDoubleDistance(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        // 使用欧几里德距离公式计算距离
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance;
    }

    // 开启世界线程
    public void threadStart() {
        thread.start();
    }

    // 关闭世界线程
    public void threadInterrupt() {
        thread.interrupt();
    }

    // 世界添加方块
    public void setBlock(int id, int x, int y) {
        blockIdList[y][x] = id;
    }

    // 世界移除方块
    public void removeBlock(int x, int y) {
        blockIdList[y][x] = -1;
    }

    // 世界中键后操作方法，执行一次操作一次
    public void mouseMiddlePress() {
        for (Entity entity : entityList) {
            if (entity instanceof Player && !((Player) entity).isDummyPlayer()) {
                Player player = (Player) entity;
                // 如果玩家是创造模式
                if (Objects.equals(player.getGameMode(), "creative") && !player.getGame().isBackGui()) {
                    // 遍历世界中的所有方块
                    int blockId = blockIdList[player.getyBlockSelected()][player.getxBlockSelected()];
                    // 如果选中方块处存在方块
                    if (blockId != -1) {
                        // 获取方块id
                        boolean itemExist = false;
                        // 遍历玩家物品栏
                        for (int i = 0; i < 9; i++) {
                            // 如果存在该物品
                            if (player.getItemBarId()[i] == blockId) {
                                // 将物品栏选中框切换至该物品处
                                player.setItemBarChosen(i);
                                if (player.getGame().isMultiPlayerMode())
                                    updatePlayerItemDataToServer(player);
                                itemExist = true;
                                break;
                            }
                        }
                        if (!itemExist) {
                            // 获取物品
                            int left = player.getItem(blockId, 1, 9, true);
                            // 如果获取成功，将物品栏选中框切换至该物品处
                            if (left == 0)
                                for (int i = 0; i < 9; i++) {
                                    if (player.getItemBarId()[i] == blockId) {
                                        player.setItemBarChosen(i);
                                        break;
                                    }
                                }
                                // 如果获取失败，证明背包已满
                            else if (left == 1) {
                                // 直接替换该选中框物品
                                player.setItemBarAmountSingle(player.getItemBarChosen(), 1);
                                player.setItemBarIdSingle(player.getItemBarChosen(), blockId);
                            }
                            if (player.getGame().isMultiPlayerMode())
                                updatePlayerItemDataToServer(player);
                        }
                    }
                } else if (player.isCanReachBlockSelected()) {
                    // 遍历世界中的所有方块
                    int blockId = blockIdList[player.getyBlockSelected()][player.getxBlockSelected()];
                    // 如果选中方块处存在方块
                    if (blockId != -1) {
                        // 遍历玩家物品栏
                        for (int i = 0; i < 9; i++) {
                            // 如果存在该物品
                            if (player.getItemBarId()[i] == blockId) {
                                // 将物品栏选中框切换至该物品处
                                player.setItemBarChosen(i);
                                if (player.getGame().isMultiPlayerMode())
                                    updatePlayerItemDataToServer(player);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    // 检测某个火把周围是否有方块
    public boolean isTorchSolid(int x, int y) {
        // if (x >= 1 && blockIdList[y][x - 1] != -1) return true;
        // if (x <= width - 2 && blockIdList[y][x + 1] != -1) return true;
        // if (y >= 1 && blockIdList[y - 1][x] != -1) return true;
        if (y <= height - 2 && blockIdList[y + 1][x] != -1) return true;
        return false;
    }

    // 更新火把掉落
    public void updateTorchFall(Player player) {
        if (player.getxBlockSelected() >= 1 && IDIndex.blockIdToIsTorchLike(blockIdList[player.getyBlockSelected()][player.getxBlockSelected() - 1])) {
            if (!isTorchSolid(player.getxBlockSelected() - 1, player.getyBlockSelected())) {
                if (!player.getGame().isMultiPlayerMode()) {
                    // 添加掉落物实体
                    entityList.add(new Item((player.getxBlockSelected() - 1) * 50 + 13, player.getyBlockSelected() * 50 + 25, blockIdList[player.getyBlockSelected()][player.getxBlockSelected() - 1], 1, this, 0));
                    // 移除方块
                    blockIdList[player.getyBlockSelected()][player.getxBlockSelected() - 1] = -1;
                } else {
                    TCPClient.sendMessageToServer("/updateSummonItem " + ((player.getxBlockSelected() - 1) * 50 + 13) + " " + (player.getyBlockSelected() * 50 + 25) + " " + blockIdList[player.getyBlockSelected()][player.getxBlockSelected() - 1] + " " + 1 + " " + 0 + "\n", player.getName());
                    TCPClient.sendMessageToServer("/updateBlockIdListSingle " + (player.getxBlockSelected() - 1) + " " + player.getyBlockSelected() + " " + -1 + "\n", player.getName());
                }
            }
        } else if (player.getxBlockSelected() <= width - 2 && IDIndex.blockIdToIsTorchLike(blockIdList[player.getyBlockSelected()][player.getxBlockSelected() + 1])) {
            if (!isTorchSolid(player.getxBlockSelected() + 1, player.getyBlockSelected())) {
                if (!player.getGame().isMultiPlayerMode()) {
                    // 添加掉落物实体
                    entityList.add(new Item((player.getxBlockSelected() + 1) * 50 + 13, player.getyBlockSelected() * 50 + 25, blockIdList[player.getyBlockSelected()][player.getxBlockSelected() + 1], 1, this, 0));
                    // 移除方块
                    blockIdList[player.getyBlockSelected()][player.getxBlockSelected() + 1] = -1;
                } else {
                    TCPClient.sendMessageToServer("/updateSummonItem " + ((player.getxBlockSelected() + 1) * 50 + 13) + " " + (player.getyBlockSelected() * 50 + 25) + " " + blockIdList[player.getyBlockSelected()][player.getxBlockSelected() + 1] + " " + 1 + " " + 0 + "\n", player.getName());
                    TCPClient.sendMessageToServer("/updateBlockIdListSingle " + (player.getxBlockSelected() + 1) + " " + player.getyBlockSelected() + " " + -1 + "\n", player.getName());
                }
            }
        } else if (player.getyBlockSelected() >= 1 && IDIndex.blockIdToIsTorchLike(blockIdList[player.getyBlockSelected() - 1][player.getxBlockSelected()])) {
            if (!isTorchSolid(player.getxBlockSelected(), player.getyBlockSelected() - 1)) {
                if (!player.getGame().isMultiPlayerMode()) {
                    // 添加掉落物实体
                    entityList.add(new Item(player.getxBlockSelected() * 50 + 13, (player.getyBlockSelected() - 1) * 50 + 25, blockIdList[player.getyBlockSelected() - 1][player.getxBlockSelected()], 1, this, 0));
                    // 移除方块
                    blockIdList[player.getyBlockSelected() - 1][player.getxBlockSelected()] = -1;
                } else {
                    TCPClient.sendMessageToServer("/updateSummonItem " + (player.getxBlockSelected() * 50 + 13) + " " + ((player.getyBlockSelected() - 1) * 50 + 25) + " " + blockIdList[player.getyBlockSelected() - 1][player.getxBlockSelected()] + " " + 1 + " " + 0 + "\n", player.getName());
                    TCPClient.sendMessageToServer("/updateBlockIdListSingle " + player.getxBlockSelected() + " " + (player.getyBlockSelected() - 1) + " " + -1 + "\n", player.getName());
                }
            }
        } else if (player.getyBlockSelected() <= height - 2 && IDIndex.blockIdToIsTorchLike(blockIdList[player.getyBlockSelected() + 1][player.getxBlockSelected()])) {
            if (!isTorchSolid(player.getxBlockSelected(), player.getyBlockSelected() + 1)) {
                if (!player.getGame().isMultiPlayerMode()) {
                    // 添加掉落物实体
                    entityList.add(new Item(player.getxBlockSelected() * 50 + 13, (player.getyBlockSelected() + 1) * 50 + 25, blockIdList[player.getyBlockSelected() + 1][player.getxBlockSelected()], 1, this, 0));
                    // 移除方块
                    blockIdList[player.getyBlockSelected() + 1][player.getxBlockSelected()] = -1;
                } else {
                    TCPClient.sendMessageToServer("/updateSummonItem " + (player.getxBlockSelected() * 50 + 13) + " " + ((player.getyBlockSelected() + 1) * 50 + 25) + " " + blockIdList[player.getyBlockSelected() + 1][player.getxBlockSelected()] + " " + 1 + " " + 0 + "\n", player.getName());
                    TCPClient.sendMessageToServer("/updateBlockIdListSingle " + player.getxBlockSelected() + " " + (player.getyBlockSelected() + 1) + " " + -1 + "\n", player.getName());
                }
            }
        }
    }

    // 更新世界左键后操作方法，执行一次操作一次，长按连续操作
    public void updateMouseLeftPress() {
        for (Entity entity : entityList) {
            if (entity instanceof Player && !((Player) entity).isDummyPlayer()) {
                Player player = (Player) entity;
                if (player.getRespawnPauseTimer() == 0 && player.isMouseLeftPress() && !player.getGame().isBackGui()) {
                    // 更新玩家鼠标在游戏窗口的位置
                    double x = player.getGame().getMouseLocation().getX();
                    double y = player.getGame().getMouseLocation().getY();
                    double zoomGuiRatio = player.getGame().getZoomGuiRatio();
                    // 如果GUI开启且点到了物品栏
                    if (player.getGame().isGuiOn() && x >= (player.getGame().getxCenter() - 224 * zoomGuiRatio) && x <= player.getGame().getxCenter() + 224 * zoomGuiRatio && y >= player.getGame().getHeight() - 63 * zoomGuiRatio && y <= player.getGame().getHeight()) {
                        player.setItemBarChosen((int) ((x - (player.getGame().getxCenter() - 224 * zoomGuiRatio)) / (50 * zoomGuiRatio)));
                        if (player.getGame().isMultiPlayerMode())
                            updatePlayerItemDataToServer(player);
                    }
                    // 否则如果处于攻击模式或手拿武器，攻击
                    else if (player.isAttackMode() || player.getItemBarId()[player.getItemBarChosen()] == 9 || player.getItemBarId()[player.getItemBarChosen()] == 13 || player.getItemBarId()[player.getItemBarChosen()] == 30 || player.getItemBarId()[player.getItemBarChosen()] == 34 || player.getItemBarId()[player.getItemBarChosen()] == 38) {
                        player.attack();
                    }
                    // 否则如果在互动范围内
                    else if (player.isCanReachBlockSelected()) {
                        // 否则如果玩家是生存模式
                        if (Objects.equals(player.getGameMode(), "survival")) {
                            // 如果玩家选中的方块相比上一刻没有变化
                            if (player.getxBlockSelected() == player.getxLastBlockSeleted() && player.getyBlockSelected() == player.getyLastBlockSeleted()) {
                                // 玩家破坏时间加一
                                player.setTimeDestroyingBlock(player.getTimeDestroyingBlock() + 1);
                                // 如果选中方块处存在方块
                                int blockId = blockIdList[player.getyBlockSelected()][player.getxBlockSelected()];
                                if (blockId != -1) {
                                    if (player.getClickTimer() <= 0) {
                                        // 玩家破坏动作
                                        player.setClickTimer(23);
                                    }
                                    if (blockIdToResistance(blockId, player.getToolState()) == player.getTimeDestroyingBlock()) {
                                        // 获取玩家破坏方块ID
                                        int itemID = blockIdToItemId(blockId, player.getToolState());
                                        if (!player.getGame().isMultiPlayerMode()) {
                                            // 掉落物ID不为-1
                                            if (itemID != -1)
                                                // 添加掉落物实体
                                                entityList.add(new Item(player.getxBlockSelected() * 50 + 13, player.getyBlockSelected() * 50 + 25, itemID, 1, this, 0));
                                            // 移除方块
                                            blockIdList[player.getyBlockSelected()][player.getxBlockSelected()] = -1;
                                            // 更新火把掉落
                                            updateTorchFall(player);
                                        } else {
                                            TCPClient.sendMessageToServer("/updateSummonItem " + (player.getxBlockSelected() * 50 + 13) + " " + (player.getyBlockSelected() * 50 + 25) + " " + itemID + " " + 1 + " " + 0 + "\n", player.getName());
                                            TCPClient.sendMessageToServer("/updateBlockIdListSingle " + player.getxBlockSelected() + " " + player.getyBlockSelected() + " " + -1 + "\n", player.getName());
                                        }
                                        if (player.getGame().isMultiPlayerMode())
                                            updateSingleBlockDataToServer(player.getxBlockSelected(), player.getyBlockSelected(), -1, player);
                                        // 玩家破坏时间归零
                                        player.setTimeDestroyingBlock(0);
                                        // 如果使用工具，扣除工具耐久
                                        if (IDIndex.blockIdToIsTool(player.getItemBarId()[player.getItemBarChosen()])) {
                                            player.getItemBarAmount()[player.getItemBarChosen()]--;
                                            if (player.getItemBarAmount()[player.getItemBarChosen()] <= 0) {
                                                if (!player.getGame().isMultiPlayerMode())
                                                    soundList.add(new PlaySound("damage", 3, 100));
                                                else
                                                    TCPClient.sendMessageToServer("/updateSound " + player.getxCenter() + " " + player.getyCenter() + " toolBreak 0\n", player.getName());
                                            }
                                            if (player.getItemBarAmount()[player.getItemBarChosen()] == 0)
                                                player.getItemBarId()[player.getItemBarChosen()] = -1;
                                            if (player.getGame().isMultiPlayerMode())
                                                updatePlayerItemDataToServer(player);
                                        }
                                        // 更新光照
                                        player.getGame().updateVisionLightIntensity();
                                        // 播放破坏音效
                                        if (!player.getGame().isMultiPlayerMode())
                                            soundList.add(new PlaySound("dig", blockId, 100));
                                    }
                                } else {
                                    player.attack();
                                }
                            } else {
                                // 否则破坏时间归零
                                player.setTimeDestroyingBlock(0);
                            }
                        }
                        // 否则如果玩家是创造模式
                        else if (Objects.equals(player.getGameMode(), "creative")) {
                            // 遍历世界中所有方块
                            int blockId = blockIdList[player.getyBlockSelected()][player.getxBlockSelected()];
                            if (blockId != -1) {
                                // 玩家破坏动作
                                player.setClickTimer(23);
                                // 移除方块
                                if (!player.getGame().isMultiPlayerMode()) {
                                    blockIdList[player.getyBlockSelected()][player.getxBlockSelected()] = -1;
                                    // 更新火把掉落
                                    updateTorchFall(player);
                                } else {
                                    TCPClient.sendMessageToServer("/updateBlockIdListSingle " + player.getxBlockSelected() + " " + player.getyBlockSelected() + " " + -1 + "\n", player.getName());
                                }
                                if (player.getGame().isMultiPlayerMode())
                                    updateSingleBlockDataToServer(player.getxBlockSelected(), player.getyBlockSelected(), -1, player);
                                // 更新光照
                                player.getGame().updateVisionLightIntensity();
                                // 播放破坏音效
                                if (!player.getGame().isMultiPlayerMode())
                                    soundList.add(new PlaySound("dig", blockId, 100));
                            }
                        }
                    } else {
                        // 破坏时间归零
                        player.setBlockCrackSort(0);
                        player.setTimeDestroyingBlock(0);
                        player.attack();
                    }
                }
                // 如果松开按键，破坏时间归零
                else {
                    player.setBlockCrackSort(0);
                    player.setTimeDestroyingBlock(0);
                }
                // 更新上一刻方块
                player.updateLastBlockSelected();
            }
        }
    }

    // 更新世界中右键后操作方法，执行一次操作一次，长按连续操作
    public void updateMouseRightPress() {
        for (Entity entity : entityList) {
            if (entity instanceof Player && !((Player) entity).isDummyPlayer()) {
                Player player = (Player) entity;
                if (player.getNoRightPressTimer() == 0 && player.getRespawnPauseTimer() == 0 && !player.isAttackMode() && player.isMouseRightPress() && !player.getGame().isBackGui()) {
                    // 更新玩家碰撞坐标
                    player.updateCollisionLocation();
                    // 更新操控该玩家游戏窗口鼠标位置
                    int x = (int) player.getGame().getMouseLocation().getX();
                    int y = (int) player.getGame().getMouseLocation().getY();
                    double zoomGuiRatio = player.getGame().getZoomGuiRatio();
                    if (player.getGame().isGuiOn() && x >= (player.getGame().getxCenter() - 224 * zoomGuiRatio) && x <= player.getGame().getxCenter() + 224 * zoomGuiRatio && y >= player.getGame().getHeight() - 63 * zoomGuiRatio && y <= player.getGame().getHeight()) {
                        player.setItemBarChosen((int) ((x - (player.getGame().getxCenter() - 224 * zoomGuiRatio)) / (50 * zoomGuiRatio)));
                        if (player.getGame().isMultiPlayerMode())
                            updatePlayerItemDataToServer(player);
                    }
                    // 否则如果在互动范围内
                    else if (player.isCanReachBlockSelected()) {
                        int xSet = player.getxBlockSelected();
                        int ySet = player.getyBlockSelected();
                        // 如果点击的是工作台
                        if (blockIdList[ySet][xSet] == 8) {
                            player.getGame().openCraftingTable();
                        } // 如果玩家手中方块可以放置，放置方块
                        else if (IDIndex.blockIdToCanPlace(player.getItemBarId()[player.getItemBarChosen()])) {
                            // 遍历判断该位置是否存在方块
                            if (blockIdList[ySet][xSet] == -1)
                                // 遍历判断该位置是否周围有方块，创造模式可不判断
                                if (Objects.equals(player.getGameMode(), "creative") || blockIdList[ySet + 1][xSet] != -1 || blockIdList[ySet - 1][xSet] != -1 || blockIdList[ySet][xSet + 1] != -1 || blockIdList[ySet][xSet - 1] != -1)
                                    if (!IDIndex.blockIdToIsTorchLike(player.getItemBarId()[player.getItemBarChosen()]) || IDIndex.blockIdToIsTorchLike(player.getItemBarId()[player.getItemBarChosen()]) && player.getyBlockSelected() + 1 <= height - 1 && blockIdList[player.getyBlockSelected() + 1][player.getxBlockSelected()] != -1 && !IDIndex.blockIdToIsTorchLike(blockIdList[player.getyBlockSelected() + 1][player.getxBlockSelected()]))
                                        // 判断选物品栏位置是否有方块
                                        if (player.getItemBarAmount()[player.getItemBarChosen()] != 0) {
                                            // 判断放置位置是否有掉落物以外的实体
                                            boolean canSetBlock = true;
                                            for (Entity entityOther : entityList) {
                                                if (!(entityOther instanceof Item)) {
                                                    if (!(xSet < entityOther.getxLeft() / 50 || xSet > (entityOther.getxRight() - 1) / 50 || ySet < entityOther.getyUp() / 50 || ySet >= (entityOther.getyDown() + 49) / 50))
                                                        canSetBlock = false;
                                                }
                                            }
                                            if (canSetBlock || IDIndex.blockIdToIsTorchLike(player.getItemBarId()[player.getItemBarChosen()])) {
                                                // 如果方块不存在，则添加指定方块
                                                if (!player.getGame().isMultiPlayerMode()) {
                                                    setBlock(player.getItemBarId()[player.getItemBarChosen()], xSet, ySet);
                                                    // 更新火把掉落
                                                    updateTorchFall(player);
                                                } else {
                                                    TCPClient.sendMessageToServer("/updatePlaceBlock " + xSet + " " + ySet + " " + player.getItemBarId()[player.getItemBarChosen()] + "\n", player.getName());
                                                }
                                                if (player.getItemBarId()[player.getItemBarChosen()] == 8) {
                                                    player.setNoRightPressTimer(20);
                                                }
                                                // 玩家破坏动作
                                                player.setClickTimer(23);
                                                // 更新光照
                                                player.getGame().updateVisionLightIntensity();
                                                // 播放放置方块音效
                                                if (!player.getGame().isMultiPlayerMode())
                                                    soundList.add(new PlaySound("dig", player.getItemBarId()[player.getItemBarChosen()], 100));
                                                // 如果玩家不是创造模式
                                                if (!Objects.equals(player.getGameMode(), "creative")) {
                                                    if (!player.getGame().isMultiPlayerMode()) {
                                                        // 扣除方块数量
                                                        player.setItemBarAmountSingle(player.getItemBarChosen(), player.getItemBarAmount()[player.getItemBarChosen()] - 1);
                                                        if (player.getItemBarAmount()[player.getItemBarChosen()] == 0)
                                                            player.getItemBarId()[player.getItemBarChosen()] = -1;
                                                    }
                                                }
                                            }
                                        }
                        }
                    }
                }
            }
        }
    }

    // 世界清除播放完成的音效
    public void clearEndedSound() {
        for (PlaySound sound : soundList) {
            if (sound.isEnded()) {
                soundList.remove(sound);
                sound = null;
            }
        }
    }

    // 世界中玩家吸附掉落物
    public void playerAttractItem() {
        // 遍历所有玩家
        for (Entity entityPlayer : entityList) {
            if (entityPlayer instanceof Player) {
                Player player = (Player) entityPlayer;
                if (player.getGame() != null && !player.getGame().isMultiPlayerMode()) {// 遍历所有掉落物
                    for (Entity entityItem : entityList) {
                        if (entityItem instanceof Item) {
                            Item item = (Item) entityItem;
                            if (item.getTimeNoCollect() == 0 && !player.isDead()) {
                                // 计算掉落物与玩家欧氏距离
                                double itemCenterX = item.getxCenter();
                                double playerCenterX = player.getxCenter();
                                double distance = abs(itemCenterX - playerCenterX);
                                if (item.getyDown() <= player.getyDown() + 5 && item.getyUp() >= player.getyUp() && distance < 80) {
                                    item.setAttractSpeedX(1);
                                    if (distance < 2) item.setAttractSpeedX(0);
                                    else item.setAttractSpeedX(item.getAttractSpeedX() + 1 / (distance * 0.5));
                                    if (item.isCanRight() && itemCenterX < playerCenterX)
                                        item.setX(item.getX() + (int) item.getAttractSpeedX());
                                    else if (item.isCanLeft()) item.setX(item.getX() - (int) item.getAttractSpeedX());
                                    else item.setAttractSpeedX(0);
                                } else {
                                    item.setAttractSpeedX(0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 世界中玩家拾取掉落物
    public void playerCollectItem() {
        boolean multiPlayerMode = false;
        for (Entity entityPlayer : entityList) {
            if (entityPlayer instanceof Player && !entityPlayer.isDead()) {
                Player player = (Player) entityPlayer;
                if (player.getGame() != null && player.getGame().isMultiPlayerMode())
                    multiPlayerMode = true;
            }
        }
        if (!multiPlayerMode) {
            // 遍历所有玩家
            for (Entity entityPlayer : entityList) {
                if (entityPlayer instanceof Player && !entityPlayer.isDead()) {
                    Player player = (Player) entityPlayer;
                    if (!player.isDummyPlayer() && player.isLogin()) {
                        // 遍历所有掉落物
                        for (Entity entityItem : entityList) {
                            if (entityItem instanceof Item) {
                                Item item = (Item) entityItem;
                                if (item.getTimeNoCollect() == 0) {
                                    // 计算掉落物与玩家欧氏距离
                                    double itemCenterX = item.getxCenter();
                                    double itemCenterY = item.getyCenter();
                                    double playerCenterX = player.getxCenter();
                                    double playerCenterY = player.getyDown() - player.getHeight() * 0.25;
                                    if (calculateDoubleDistance(itemCenterX, itemCenterY, playerCenterX, playerCenterY) < 25) {
                                        if (player.canLoadItem(item.getId())) {
                                            int amountLeft = player.getItem(item.getId(), item.getAmount(), 36, true);
                                            if (amountLeft != 0) {
                                                if (!player.getGame().isMultiPlayerMode())
                                                    entityList.add(new Item(item.getX(), item.getY(), item.getId(), amountLeft, this, 0));
                                                else {
                                                    TCPClient.sendMessageToServer("/updateSummonItem " + item.getX() + " " + item.getY() + " " + item.getId() + " " + amountLeft + " " + 0 + "\n", player.getName());
                                                }
                                            }
                                            if (player.getGame().isMultiPlayerMode()) {
                                                TCPClient.sendMessageToServer("/updateRemoveItem " + item.getIdCode() + "\n", player.getName());
                                            }
                                            entityList.remove(item);
                                            item = null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 世界中合并掉落物
    public void assembleItem() {
        // 定义当前掉落物列表
        List<Item> itemList = new ArrayList<>();
        // 遍历所有掉落物，生成当前掉落物列表

        for (Entity entityItem : entityList) {
            if (entityItem instanceof Item) {
                Item item = (Item) entityItem;
                itemList.add(item);
            }
        }
        // 遍历当前所有掉落物
        for (int i = 0; i < itemList.size(); i++) {
            Item item1 = itemList.get(i);
            // 避免重复遍历掉落物
            for (int j = i + 1; j < itemList.size(); j++) {
                Item item2 = itemList.get(j);
                // 如果掉落物有相同id
                if (item1 != null && item1 != item2 && item1.getId() == item2.getId()) {
                    // 计算欧式距离
                    double item2CenterX = item1.getxCenter();
                    double item2CenterY = item1.getyCenter();
                    double item3CenterX = item2.getxCenter();
                    double item3CenterY = item2.getyCenter();
                    // 如果欧氏距离小于30且堆叠后数量小于最大堆叠数
                    if (calculateDoubleDistance(item2CenterX, item2CenterY, item3CenterX, item3CenterY) < 30 && item1.getAmount() + item2.getAmount() <= IDIndex.blockIdToMaxAmount(item1.getId())) {
                        // 生成新堆叠掉落物
                        Boolean mutiPlayer = false;
                        Boolean onePlayer = true;
                        String playerName = null;
                        for (Entity entity : entityList) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (player.getGame() != null) {
                                    if (player.getGame().isMultiPlayerMode()) {
                                        mutiPlayer = true;
                                        playerName = player.getName();
                                    }
                                }
                                if (player.isDummyPlayer()) onePlayer = false;
                            }
                        }
                        if (mutiPlayer && onePlayer) {
                            TCPClient.sendMessageToServer("/updateRemoveItem " + item1.getIdCode() + "\n", playerName);
                            TCPClient.sendMessageToServer("/updateRemoveItem " + item2.getIdCode() + "\n", playerName);
                        }
                        Item newItem = null;
                        if (onePlayer) {
                            if (!mutiPlayer)
                                newItem = new Item((item1.getX() + item2.getX()) / 2, (item1.getY() + item2.getY()) / 2, item1.getId(), item1.getAmount() + item2.getAmount(), this, (item1.getTimeNoCollect() + item2.getTimeNoCollect()) / 2);
                            else {
                                TCPClient.sendMessageToServer("/updateSummonItemWithXSpeed " + ((item1.getX() + item2.getX()) / 2) + " " + ((item1.getY() + item2.getY()) / 2) + " " + item1.getId() + " " + (item1.getAmount() + item2.getAmount()) + " " + ((item1.getTimeNoCollect() + item2.getTimeNoCollect()) / 2) + " " + ((item1.getxSpeed() + item2.getxSpeed()) / 2) + "\n", playerName);
                            }
                        }
                        // 更新实体信息
                        if (newItem != null) {
                            newItem.updateEntityData();
                            if ((item1.getxSpeed() + item2.getxSpeed()) / 2 > 0 && abs(newItem.getxRight() - newItem.getEntityRightWallX()) >= 15) {
                                newItem.setxSpeed((item1.getxSpeed() + item2.getxSpeed()) / 2);
                            } else if ((item1.getxSpeed() + item2.getxSpeed()) / 2 < 0 && abs(newItem.getxLeft() - newItem.getEntityLeftWallX()) >= 15)
                                newItem.setxSpeed((item1.getxSpeed() + item2.getxSpeed()) / 2);
                            newItem.setySpeed((item1.getySpeed() + item2.getySpeed()) / 2);
                            newItem.setTimeNoCollect((item1.getTimeNoCollect() + item2.getTimeNoCollect()) / 2);
                            entityList.add(newItem);
                        }
                        if (onePlayer) { // 移除旧掉落物
                            entityList.remove(item1);
                            entityList.remove(item2);
                        }
                        item1 = null;
                        item2 = null;
                        break;
                    }
                }
            }
        }
    }

    // 更新世界实体各参数
    public void updateWorldEntityData() {
        // 遍历所有实体
        for (Entity entity : entityList) {
            // 更新实体数据
            entity.updateEntityData();
            // 更新玩家实体特殊数据
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.updatePlayerData();
                if (!player.isDummyPlayer())
                    player.getGame().updateSideLightIntensity();
            }
            // 更新掉落物实体特殊数据
            if (entity instanceof Item) {
                Item item = (Item) entity;
                item.updateItemData();
            }
            // 更新僵尸实体特殊数据
            if (entity instanceof Zombie) {
                Zombie zombie = (Zombie) entity;
                zombie.updateZombieData();
            }
        }
    }

    // 更新播放周围实体声音
    public void playNearbyEntitySound() {
        for (Entity entityPlayer : entityList) {
            if (entityPlayer instanceof Player) {
                Player player = (Player) entityPlayer;
                for (Entity entity : entityList) {
                    if (entity != player) {
                        if (entity instanceof Zombie && player.getGame() != null) {
                            Random random = new Random();
                            int randomNumber = random.nextInt(500);
                            double R1 = calculateIntDistance(0, 0, player.getGame().getWidth() / 2, player.getGame().getHeight() / 2);
                            double r1 = calculateIntDistance(player.getxCenter(), player.getyCenter(), entity.getxCenter(), entity.getyCenter());
                            if (randomNumber == 0 && R1 >= r1) {
                                entity.getSoundList().add(new PlaySound("zombie", IDIndex.soundNameToID("zombie", "say"), (int) (1 - (r1 / R1)) * 100));
                            }
                        }
                    }
                }
            }
        }
    }

    // 更新世界时间
    public void updateTime() {
        time++;
        if (time >= 120000) time = 0;
    }

    // 更新世界怪物数量
    public void updateMobAmount() {
        mobAmount = 0;
        for (Entity entity : entityList) {
            if (entity instanceof Zombie) mobAmount++;
        }
    }

    // 更新世界怪物
    public void updateMobs() {
        if (Objects.equals(difficulty, "peaceful")) {
            for (Entity entity : entityList) {
                if (entity instanceof Zombie)
                    entityList.remove(entity);
            }
        } else {
            if (time >= 105000 || time <= 25000 && mobAmount < IDIndex.difficultyToMobAmountMax(difficulty)) {
                for (Entity entity : entityList) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        Random random = new Random();
                        int randomNumber = random.nextInt(IDIndex.difficultyToRandom(difficulty));
                        if (randomNumber == 0) {
                            int x = random.nextInt((1000 + player.getxVision()) / 25) - (1000 + player.getxVision()) / 50;
                            int locationY = player.getyCenter() / 50;
                            int locationX = player.getxCenter() / 50 + x;
                            if (locationX <= 0) locationX = 1;
                            else if (locationX >= width - 1) locationX = width - 2;
                            for (int y = 20; y < height; y++) {
                                if (!(blockIdList[y][locationX] == -1 || IDIndex.blockIdToIsUnTouchable(blockIdList[y][locationX]))) {
                                    locationY = y - 2;
                                    break;
                                }
                            }
                            if (player.getGame().getLightIntensity()[locationY][locationX] <= 150)
                                entityList.add(new Zombie(locationX * 50, locationY * 50, this));
                        }
                    }
                }
            }
        }
    }

    public void updateOnlineTimer(Player player) {
        if (onlineTimer <= 0) {
            player.getGame().showFrame5("LinkLost");
            thread.interrupt();
        }
        onlineTimer--;
    }

    public void updateSingleBlockDataToServer(int x, int y, int id, Player player) {
        TCPClient.sendMessageToServer("/updateBlockIdListSingle " + x + " " + y + " " + id + "\n", player.getName());
    }

    public void updateEntitydataToServer(Player player) {
        TCPClient.sendMessageToServer("/updateEntityData player " + player.getName() + " " + player.getHealth() + " " + player.isDead() + "\n", player.getName());
    }

    public void updateBlockDataToServer(Player player) {
        if (!player.isDummyPlayer()) {
            int x1 = (player.getxCenter() / blockSize) - 200;
            int y1 = (player.getyCenter() / blockSize) - 200;
            int x2 = (player.getxCenter() / blockSize) + 200;
            int y2 = (player.getyCenter() / blockSize) + 200;
            int[][] blockIdListRange = new int[x2 - x1 + 1][y2 - y1 + 1];
            int j = 0;
            for (int y = y1; y < y2; y++) {
                int i = 0;
                for (int x = x1; x < x2; x++) {
                    blockIdListRange[j][i] = blockIdList[y][x];
                    i++;
                }
                j++;
            }
            TCPClient.sendMessageToServer("/updateBlockIdListRange " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + StringConversion.intDoubleArrayToString(blockIdListRange) + "\n", player.getName());
        }
    }

    public void updatePlayerMoveDataToServer(Player player) {
        TCPClient.sendMessageToServer("/updateLocation player " + player.getName() + " " + player.getX() + " " + player.getY() + "\n", player.getName());
        TCPClient.sendMessageToServer("/updateTimer player " + player.getName() + " " + player.getWalkTimer() + " " + player.getRunTimer() + " " + player.getClickTimer() + "\n", player.getName());
        TCPClient.sendMessageToServer("/updateState player " + player.getName() + " " + player.getFaceTo() + " " + player.getMoveState() + "\n", player.getName());
    }

    public void updatePlayerItemDataToServer(Player player) {
        TCPClient.sendMessageToServer("/updateItemBarChosen " + player.getName() + " " + player.getItemBarChosen() + "\n", player.getName());
        TCPClient.sendMessageToServer("/updateItemBarAmount " + player.getName() + " " + StringConversion.intArrayToString(player.getItemBarAmount()) + "\n", player.getName());
        TCPClient.sendMessageToServer("/updateItemBarId " + player.getName() + " " + StringConversion.intArrayToString(player.getItemBarId()) + "\n", player.getName());
    }

    public void updateIsOp(Player host) {
        for (Entity entity : entityList) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                TCPClient.sendMessageToServer("/updateIsOp " + player.getName() + " \n", host.getName());
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            // 更新世界实体各参数
            updateWorldEntityData();
            // 玩家吸附掉落物
            playerAttractItem();
            // 玩家拾取掉落物
            playerCollectItem();
            // 合并掉落物
            assembleItem();
            // 清除世界播放完的音效
            clearEndedSound();
            // 更新玩家鼠标操作，中键不用更新，点一次执行一次
            updateMouseLeftPress();
            updateMouseRightPress();
            // 更新播放周围实体声音
            playNearbyEntitySound();
            for (Entity entity : entityList) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (!player.isDummyPlayer()) {
                        if (!player.getGame().isMultiPlayerMode()) {
                            // 更新世界怪物
                            updateMobs();
                            // 更新世界怪物数量
                            updateMobAmount();
                            // 更新世界时间
                            updateTime();
                        } else {
                            // 更新是否掉线
                            if (onlineTimer >= 0 && player.getGame() != null && player.getGame().getFrameSort() == 0)
                                updateOnlineTimer(player);
                            // 更新是否是OP
                            updateIsOp(player);
                            // 更新上传玩家运动状态
                            if (!player.getMoveState().equals("stand") || !player.isOnGround() || player.getClickTimer() != 0 || player.getxSpeed() != 0 || player.getySpeed() != 0)
                                updatePlayerMoveDataToServer(player);
                        }
                    }
                }
            }
            // 定义线程每10ms执行一次
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public List<BufferedImage> getTextureList() {
        return textureList;
    }

    public void setTextureList(List<BufferedImage> textureList) {
        this.textureList = textureList;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<PlaySound> getSoundList() {
        return soundList;
    }

    public void setSoundList(List<PlaySound> soundList) {
        this.soundList = soundList;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public int getVoidSize() {
        return voidSize;
    }

    public void setVoidSize(int voidSize) {
        this.voidSize = voidSize;
    }

    public int getxSpawn() {
        return xSpawn;
    }

    public void setxSpawn(int xSpawn) {
        this.xSpawn = xSpawn;
    }

    public int getySpawn() {
        return ySpawn;
    }

    public void setySpawn(int ySpawn) {
        this.ySpawn = ySpawn;
    }

    public double getAirResistance() {
        return airResistance;
    }

    public void setAirResistance(double airResistance) {
        this.airResistance = airResistance;
    }

    public int[][] getBlockIdList() {
        return blockIdList;
    }

    public void setBlockIdList(int[][] blockIdList) {
        this.blockIdList = blockIdList;
    }

    public void setBlockIdListSingle(int x, int y, int id) {
        this.blockIdList[y][x] = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public BufferedImage getTextureCurrent() {
        return textureCurrent;
    }

    public void setTextureCurrent(BufferedImage textureCurrent) {
        this.textureCurrent = textureCurrent;
    }

    public WorldLoad getWorldLoader() {
        return worldLoader;
    }

    public void setWorldLoader(WorldLoad worldLoader) {
        this.worldLoader = worldLoader;
    }

    public WorldSave getWorldSaver() {
        return worldSaver;
    }

    public void setWorldSaver(WorldSave worldSaver) {
        this.worldSaver = worldSaver;
    }

    public double getGama() {
        return gama;
    }

    public void setGama(double gama) {
        this.gama = gama;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getMobAmountMax() {
        return mobAmountMax;
    }

    public void setMobAmountMax(int mobAmountMax) {
        this.mobAmountMax = mobAmountMax;
    }

    public int getMobAmount() {
        return mobAmount;
    }

    public void setMobAmount(int mobAmount) {
        this.mobAmount = mobAmount;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public int getOnlineTimer() {
        return onlineTimer;
    }

    public void setOnlineTimer(int onlineTimer) {
        this.onlineTimer = onlineTimer;
    }
}