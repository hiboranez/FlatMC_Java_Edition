package EntityType;

import Base.GameFrame;
import Base.World;
import Element.Entity;
import Utils.GUI.FlexButton;
import Utils.GUI.FlexGrid;
import Utils.GUI.FlexText;
import Utils.OtherTool.IDIndex;
import Utils.OtherTool.PlaySound;
import Utils.OtherTool.Resource;
import Utils.OtherTool.WordList;
import Utils.TCP.Command;
import Utils.TCP.TCPClient;

import java.util.Objects;
import java.util.Random;

import static java.lang.Math.*;

public class Player extends Entity {
    // 定义玩家破坏方块的时间
    int timeDestroyingBlock = 0;
    // ★定义玩家匹配的游戏窗口
    private GameFrame game = null;
    // ★定义玩家名
    private String name = null;
    // 定义玩家模式
    // survival creative
    private String gameMode = "survival";
    // 定义玩家朝向
    // left right
    private String faceTo = "left";
    // 定义玩家当前运动状态
    // stand walk run
    private String moveState = "stand";
    // 定义玩家当前工具状态
    // hand pickaxe axe shovel
    private String toolState = "hand";
    // 定义玩家物品栏
    private int[] itemBarId = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    // 定义玩家物品栏数量
    private int[] itemBarAmount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0};
    // 定义玩家选中的物品栏
    private int itemBarChosen = 0;
    // 定义玩家选中的方块坐标
    private int xBlockSelected = 0;
    private int yBlockSelected = 0;
    // 定义玩家上一刻选中的方块坐标，用于判断是否切换方块
    private int xLastBlockSeleted = 0;
    private int yLastBlockSeleted = 0;
    // 定义玩家方块裂痕序号(0~7)
    private int blockCrackSort = 0;
    private int blockCrackSortLast = 0;
    // 定义玩家攻击力
    private int attackValue = 1;
    // 定义玩家渲染距离(相对于窗口中心)
    private int xVision = 1500;
    private int yVision = 1500;
    // 定义玩家互动范围
    private int interactRange = 160;
    // 定义玩家手部相对坐标
    private int xHand = 0;
    private int yHand = 0;
    // 定义玩家移动计时器，用于播放走路声音
    private int moveTimer = 0;
    // 定义走路跑步计时器，用于播放运动动画
    private int walkTimer = 0;
    private int runTimer = 0;
    // 定义点击计时器，用于播放手部动画
    private int clickTimer = 0;
    // 定义玩家移动频率
    private int moveFrequency = 20;
    // 定义玩家攻击冷却时间
    private int attackCD = 50;
    // 定义玩家攻击范围
    private int attackRange = 100;
    // 定义玩家鼠标所在世界的坐标
    private int xMouseInWorld = 0, yMouseInWorld = 0;
    // 定义生命值闪烁计时器
    private int heartFlashTimer = 0;
    // 定义死亡重置计时器
    private int respawnPauseTimer = 0;
    // 定义玩家不可右键时间
    private int noRightPressTimer = 0;
    // 定义玩家是否处于攻击模式
    private boolean attackMode = false;
    // 定义玩家是否按下鼠标
    private boolean mouseLeftPress = false;
    private boolean mouseRightPress = false;
    // 定义能否继续手臂动作
    private boolean canContinueHand = true;
    // 定义玩家是否按下"w"键
    private boolean wPressed = false;
    // 定义玩家是否按下"s"键
    private boolean sPressed = false;
    // 定义玩家选中方的块是否能被触及到
    private boolean canReachBlockSelected = true;
    // 定义玩家是否死亡物品不掉落
    private boolean keepInventory = false;
    // 定义玩家是否开启自动跳跃
    private boolean autoJump = true;
    // 定义是否为假人
    private boolean dummyPlayer = false;
    // 定义是否为管理员
    private boolean operator = false;
    // 定义是否登陆成功
    private boolean login = true;

    public Player() {
        setIdCode(-1);
        // 设置实体类型为玩家
        setType("player");
        // 设置玩家可以复活
        setCanRespawn(true);
        // 设置玩家材质包
        setTextureList(Resource.getPlayerTextureList());
        // 设置玩家材质
        setTextureCurrent(getTextureList().get(0));
        // 设置玩家大小
        setWidth(20);
        setHeight(95);
        // 设置实体大小修正值
        setxLeftRevision((70 - getWidth()) / 2);
        setxRightRevision((70 - getWidth()) / 2 + getWidth());
        setyUpRevision(0);
        setyDownRevision(getHeight());
    }

    public Player(String name, World world, Boolean dummyPlayer) {
        setIdCode(-1);
        // 设置实体类型为玩家
        setType("player");
        // 设置玩家名称
        this.name = name;
        // 设置为假人
        this.dummyPlayer = dummyPlayer;
        if (dummyPlayer)
            setHasGravity(false);
        // 设置玩家所处世界
        setWorld(world);
        // 设置玩家可以复活
        setCanRespawn(true);
        // 设置玩家材质包
        setTextureList(Resource.getPlayerTextureList());
        // 设置玩家材质
        setTextureCurrent(getTextureList().get(0));
        // 设置玩家大小
        setWidth(20);
        setHeight(95);
        // 设置实体大小修正值
        setxLeftRevision((70 - getWidth()) / 2);
        setxRightRevision((70 - getWidth()) / 2 + getWidth());
        setyUpRevision(0);
        setyDownRevision(getHeight());
    }

    public Player(String name, World world, GameFrame game) {
        setIdCode(-1);
        // 设置玩家匹配的游戏窗口
        this.game = game;
        // 设置玩家名
        this.name = name;
        // 设置玩家所处世界
        setWorld(world);
        world.getEntityList().add(this);
        world.getPlayerList().add(this);
        // 设置玩家重生点
        setxSpawn(world.getxSpawn());
        setySpawn(world.getySpawn());
        // 设置玩家坐标
        setX(world.getxSpawn());
        setY(world.getySpawn());
        // 设置实体类型为玩家
        setType("player");
        // 设置玩家可以复活
        setCanRespawn(true);
        // 设置玩家材质包
        setTextureList(Resource.getPlayerTextureList());
        // 设置玩家材质
        setTextureCurrent(getTextureList().get(0));
        // 设置玩家大小
        setWidth(20);
        setHeight(95);
        // 设置实体大小修正值
        setxLeftRevision((70 - getWidth()) / 2);
        setxRightRevision((70 - getWidth()) / 2 + getWidth());
        setyUpRevision(0);
        setyDownRevision(getHeight());
    }

    // 玩家拾取掉落物，返回未被拾取的数量
    public int getItem(int id, int amount, int searchSize, boolean soundOn) {
        // 定义还未被捡完的掉落物数量
        int amountLeft = amount;
        // 如果不是工具
        if (!IDIndex.blockIdToIsTool(id)) {
            // 搜索背包内是否已经存在该物品
            for (int i = 0; i < searchSize; i++)
                // 如果存在
                if (itemBarId[i] == id) {
                    // 如果物品数量小于最大堆叠数
                    if (itemBarAmount[i] < IDIndex.blockIdToMaxAmount(id)) {
                        // 如果物品数量加上全部物品多于最大堆叠数
                        if (itemBarAmount[i] + amountLeft > IDIndex.blockIdToMaxAmount(id)) {
                            // 掉落物剩余数量扣除已经捡走的数量
                            amountLeft -= (IDIndex.blockIdToMaxAmount(id) - itemBarAmount[i]);
                            // 该物品堆叠达到上限，设为最大堆叠数
                            itemBarAmount[i] = IDIndex.blockIdToMaxAmount(id);
                        } else {
                            // 否则该物品直接堆叠全部掉落物数量
                            itemBarAmount[i] += amountLeft;
                            // 掉落物剩余数量扣除已经捡走的数量
                            amountLeft = 0;
                            // 退出循环
                            break;
                        }
                    }
                }
        }
        // 如果掉落物数量还有剩余
        if (amountLeft > 0)
            // 搜寻背包内第一个空位
            for (int i = 0; i < searchSize; i++)
                // 如果搜索到了
                if (itemBarId[i] == -1) {
                    // 如果物品剩余数量小于等于最大堆叠数
                    if (amountLeft <= IDIndex.blockIdToMaxAmount(id)) {
                        // 该物品栏直接堆叠剩余数量
                        itemBarAmount[i] += amountLeft;
                        // 设置此物品栏存在该物品
                        itemBarId[i] = id;
                        // 掉落物剩余数量扣除已经捡走的数量
                        amountLeft = 0;
                        // 退出循环
                        break;
                    } else {
                        // 否则堆叠达到上限，设为最大堆叠数
                        itemBarAmount[i] = IDIndex.blockIdToMaxAmount(id);
                        // 设置此物品栏存在该物品
                        itemBarId[i] = id;
                        // 掉落物剩余数量扣除最大堆叠数
                        amountLeft -= IDIndex.blockIdToMaxAmount(id);
                    }
                }
        // 如果得到了东西，播放pop音效
        if (amountLeft < amount && soundOn) {
            if (game != null && !game.isMultiPlayerMode())
                getSoundList().add(new PlaySound("player", IDIndex.soundNameToID("player", "pop"), 100));
            else {
                TCPClient.sendMessageToServer("/updateSound " + getxCenter() + " " + getyCenter() + " " + "pop 0\n",
                        name);
            }
        }
        if (!dummyPlayer) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                flexGrid.updateItemGrid();
            }
            if (itemBarAmount[itemBarChosen] == 0)
                itemBarId[itemBarChosen] = -1;
            if (game.isMultiPlayerMode())
                getWorld().updatePlayerItemDataToServer(this);
        } else {
            getWorld().updatePlayerItemDataToServer(this);
        }
        // 返回剩余数量
        return amountLeft;
    }

    // 玩家丢失物品为掉落物
    public void loseItem(int id, int amount, int xSpeed, boolean soundOn) {
        int newItemSpeed = xSpeed;
        int newItemX = getxCenter() - 12;
        int newItemY = getyUp() + 15;
        updateEntityBlockRelation();
        if (abs(getEntityLeftWallX() - getxCenter()) <= 25 && Objects.equals(faceTo, "left"))
            newItemX = getxCenter() - 12 + (25 - abs(getEntityLeftWallX() - getxCenter()));
        else if (abs(getEntityRightWallX() - getxCenter()) <= 25 && Objects.equals(faceTo, "right")) {
            newItemX = getxCenter() - 12 - (25 - abs(getEntityRightWallX() - getxCenter()));
        }
        if (!game.isMultiPlayerMode()) {
            Item item = new Item(newItemX, newItemY, id, amount, getWorld(), 100);
            item.setxSpeed(newItemSpeed);
            getWorld().getEntityList().add(item);
        } else {
            TCPClient.sendMessageToServer("/updateSummonItemWithXSpeed " + newItemX + " " + newItemY + " " + id + " "
                    + amount + " " + 100 + " " + newItemSpeed + "\n", name);
        }
        if (soundOn)
            getSoundList().add(new PlaySound("player", IDIndex.soundNameToID("player", "pop"), 100));
    }

    // 玩家丢弃物品为掉落物，返回是否成功
    public boolean dropItem(int barId, int amount, int xSpeed, boolean soundOn) {
        if (itemBarId[barId] != -1) {
            if (itemBarAmount[barId] >= amount) {
                itemBarAmount[barId] -= amount;
                int newItemSpeed = xSpeed;
                int newItemX = getxCenter() - 12;
                int newItemY = getyUp() + 15;
                updateEntityBlockRelation();
                if (abs(getEntityLeftWallX() - getxCenter()) <= 25 && Objects.equals(faceTo, "left"))
                    newItemX = getxCenter() - 12 + (25 - abs(getEntityLeftWallX() - getxCenter()));
                else if (abs(getEntityRightWallX() - getxCenter()) <= 25 && Objects.equals(faceTo, "right")) {
                    newItemX = getxCenter() - 12 - (25 - abs(getEntityRightWallX() - getxCenter()));
                }
                if (!game.isMultiPlayerMode()) {
                    Item item = new Item(newItemX, newItemY, itemBarId[barId], amount, getWorld(), 100);
                    item.setxSpeed(newItemSpeed);
                    getWorld().getEntityList().add(item);
                } else {
                    TCPClient.sendMessageToServer("/updateDropItemWithXSpeed " + newItemX + " " + newItemY + " "
                            + itemBarId[barId] + " " + amount + " " + 100 + " " + newItemSpeed + "\n", name);
                }
                if (soundOn)
                    getSoundList().add(new PlaySound("player", IDIndex.soundNameToID("player", "pop"), 100));
                if (itemBarAmount[itemBarChosen] == 0)
                    itemBarId[itemBarChosen] = -1;
                if (game.isMultiPlayerMode()) {
                    getWorld().updatePlayerItemDataToServer(this);
                    TCPClient.sendMessageToServer("/updateSound " + getxCenter() + " " + getyCenter() + " " + "pop 0\n",
                            name);
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // 玩家吃东西
    public void eat() {
        if (IDIndex.blockIdToIsFood(itemBarId[itemBarChosen])) {
            // 如果未达最大血量
            if (getHealth() + 4 < getHealthMax())
                setHealth(getHealth() + 4);
            else
                setHealth(getHealthMax());
            itemBarAmount[itemBarChosen]--;
            if (itemBarAmount[itemBarChosen] == 0)
                itemBarId[itemBarChosen] = -1;
            if (game.isMultiPlayerMode())
                getWorld().updatePlayerItemDataToServer(this);
            getSoundList().add(new PlaySound("player", IDIndex.soundNameToID("player", "hiccup"), 100));
        }
    }

    // 玩家向左走
    public void walkLeft() {
        if (isCanLeft() && !isInParalysis()) {
            // 改变速度
            setxSpeed(-3);
            // 改变朝向
            faceTo = "left";
            // 改变运动状态
            moveState = "walk";
            // 改变移动频率
            moveFrequency = 50;
        }
        if (autoJump)
            updateCanJumpWall();
    }

    // 玩家向右走
    public void walkRight() {
        if (isCanRight() && !isInParalysis()) {
            // 改变速度
            setxSpeed(3);
            // 改变朝向
            faceTo = "right";
            // 改变运动状态
            moveState = "walk";
            // 改变移动频率
            moveFrequency = 50;
        }
        if (autoJump)
            updateCanJumpWall();
    }

    // 玩家跳跃
    public void jump() {
        // 判断是否站在方块上
        if (isOnGround() && !isUnderCeil() && !isInParalysis()) {
            // 更改上升速度
            setJumpSpeed(-8.0);
        }
    }

    // 玩家向左跑
    public void runLeft() {
        if (this.isCanLeft() && !isInParalysis()) {
            // 改变速度
            this.setxSpeed(-6);
            // 改变朝向
            this.setFaceTo("left");
            // 改变运动状态
            this.setMoveState("run");
            // 改变移动频率
            moveFrequency = 35;
        }
        if (autoJump)
            updateCanJumpWall();
    }

    // 玩家向右跑
    public void runRight() {
        if (this.isCanRight() && !isInParalysis()) {
            // 改变速度
            this.setxSpeed(6);
            // 改变朝向
            this.setFaceTo("right");
            // 改变运动状态
            this.setMoveState("run");
            // 改变移动频率
            moveFrequency = 35;
        }
        if (autoJump)
            updateCanJumpWall();
    }

    // 玩家攻击
    public void attack() {
        if (attackCD == 0 && !isInParalysis() && login) {
            double distanceMin = getWorld().getHeight() + getWorld().getWidth();
            Entity entityAttacked = null;
            for (Entity entity : getWorld().getEntityList()) {
                if (!entity.isDead() && !(entity instanceof Item) && entity != this
                        && entity.getyCenter() >= getyCenter() - 50 && entity.getyCenter() <= getyCenter() + 50
                        && ((Objects.equals(faceTo, "left") && entity.getxCenter() <= getxCenter()
                        && entity.getxCenter() >= getxCenter() - attackRange)
                        || (Objects.equals(faceTo, "right") && entity.getxCenter() >= getxCenter()
                        && entity.getxCenter() <= getxCenter() + attackRange))) {
                    int xMin = min(getxCenter() / 50, entity.getxCenter() / 50);
                    int xMax = max(getxCenter() / 50, entity.getxCenter() / 50);
                    boolean canAttack = true;
                    for (int x = xMin; x <= xMax; x++) {
                        if (!(getWorld().getBlockIdList()[(getyCenter() - 20) / 50][x] == -1 || IDIndex
                                .blockIdToIsTorchLike(getWorld().getBlockIdList()[(getyCenter() - 25) / 50][x])))
                            canAttack = false;
                    }
                    if (canAttack) {
                        double distance = World.calculateIntDistance(getxCenter(), getyCenter(), entity.getxCenter(),
                                entity.getyCenter());
                        if (distance < distanceMin) {
                            distanceMin = distance;
                            entityAttacked = entity;
                        }
                        if (entityAttacked != null) {
                            Boolean canMove = true;
                            Boolean canHurt = true;
                            if (entityAttacked instanceof Player && !entityAttacked.isDead()) {
                                Player playerAttacked = (Player) entityAttacked;
                                canHurt = false;
                                canMove = false;
                                if (!playerAttacked.getGameMode().equals("creative")) {
                                    // 播放受伤音效
                                    if (playerAttacked.getHealth() > attackValue) {
                                        TCPClient.sendMessageToServer("/updateSound " + getxCenter() + " "
                                                + getyCenter() + " " + "damageHit 0\n", name);
                                    } else
                                        TCPClient.sendMessageToServer("/updateSound " + getxCenter() + " "
                                                + getyCenter() + " " + "playerHurt 0\n", name);
                                    playerAttacked.setDamageTimer(20);
                                    if (IDIndex.blockIdToIsTool(getItemBarId()[getItemBarChosen()])) {
                                        getItemBarAmount()[getItemBarChosen()]--;
                                        if (getItemBarAmount()[getItemBarChosen()] <= 0) {
                                            if (!getGame().isMultiPlayerMode())
                                                getWorld().getSoundList().add(new PlaySound("damage", 3, 100));
                                            else {
                                                TCPClient.sendMessageToServer("/updateSound " + getxCenter() + " "
                                                        + getyCenter() + " toolBreak 0\n", getName());
                                            }
                                        }
                                        if (getItemBarAmount()[getItemBarChosen()] == 0)
                                            getItemBarId()[getItemBarChosen()] = -1;
                                        if (getGame().isMultiPlayerMode())
                                            getWorld().updatePlayerItemDataToServer(this);
                                    }
                                    TCPClient.sendMessageToServer("/updateAttack player " + playerAttacked.getName()
                                            + " " + faceTo + " " + attackValue + "\n", name);
                                    if (playerAttacked.getHealth() <= attackValue) {
                                        TCPClient.sendMessageToServer(
                                                "/updateDeathInfo kill " + playerAttacked.getName() + "\n", name);
                                    }
                                }
                            }
                            if (canHurt) {
                                entityAttacked.hurt(attackValue);
                                if (game.isMultiPlayerMode())
                                    TCPClient.sendMessageToServer("/updateHitZombie " + entityAttacked.getIdCode() + " "
                                            + faceTo + " " + entityAttacked.getHealth() + "\n", name);
                                if (IDIndex.blockIdToIsTool(getItemBarId()[getItemBarChosen()])) {
                                    getItemBarAmount()[getItemBarChosen()]--;
                                    if (getItemBarAmount()[getItemBarChosen()] <= 0) {
                                        if (!getGame().isMultiPlayerMode())
                                            getWorld().getSoundList().add(new PlaySound("damage", 3, 100));
                                        else {
                                            TCPClient.sendMessageToServer("/updateSound " + getxCenter() + " "
                                                    + getyCenter() + " toolBreak 0\n", getName());
                                        }
                                    }
                                    if (getItemBarAmount()[getItemBarChosen()] == 0)
                                        getItemBarId()[getItemBarChosen()] = -1;
                                    if (getGame().isMultiPlayerMode())
                                        getWorld().updatePlayerItemDataToServer(this);
                                }
                            }
                            if (canMove) {
                                if (Objects.equals(faceTo, "left")) {
                                    entityAttacked.stopMoveX();
                                    entityAttacked.setInParalysis(true);
                                    entityAttacked.setParalysisTimer(30);
                                    entityAttacked.setxSpeed(-8);
                                } else if (Objects.equals(faceTo, "right")) {
                                    entityAttacked.stopMoveX();
                                    entityAttacked.setInParalysis(true);
                                    entityAttacked.setParalysisTimer(30);
                                    entityAttacked.setxSpeed(8);
                                }
                            }
                            if (clickTimer <= 0) {
                                // 玩家破坏动作
                                clickTimer = 23;
                            }
                            attackCD = IDIndex.blockIdToAttackCD(itemBarId[itemBarChosen]);
                        }
                    }
                }
            }
        }
    }

    // 重写实体重生
    @Override
    public void respawn() {
        respawnPauseTimer = 100;
        setX(getxSpawn());
        setY(getySpawn());
        setHealth(getHealthMax());
        attackCD = 50;
        setDead(false);
        setCanJump(true);
        game.setBackGui(false);
        game.getFlexTextList()
                .removeIf(flexText -> flexText.getxCenterRatio() == 0 && flexText.getyCenterRatio() == -0.12);
        setxSpeed(0);
        setySpeed(0);
        setFallSpeed(0);
        setJumpSpeed(0);
        setHealth(getHealthMax());
        getGame().updateVision();
        getGame().updateVisionLightIntensity();
        if (game.isMultiPlayerMode()) {
            getWorld().updateEntitydataToServer(this);
            getWorld().updatePlayerMoveDataToServer(this);
            getWorld().updatePlayerItemDataToServer(this);
        }
    }

    // 重写更新实体死亡
    @Override
    public void updateKill() {
        if (!isDummyPlayer()) {
            if (game.isMultiPlayerMode()) {
                if (getHealth() <= 0 && !isDead())
                    kill();
                if (getxLeft() < 0 && !isDead()) {
                    kill();
                    TCPClient.sendMessageToServer("/updateDeathInfo void " + name + "\n", name);
                    TCPClient.sendMessageToServer(
                            "/updateSound " + getxCenter() + " " + getyCenter() + " " + "playerHurt 0\n", name);
                }
                if (getxRight() > getWorld().getWidth() * 50 && !isDead()) {
                    kill();
                    TCPClient.sendMessageToServer("/updateDeathInfo void " + name + "\n", name);
                    TCPClient.sendMessageToServer(
                            "/updateSound " + getxCenter() + " " + getyCenter() + " " + "playerHurt 0\n", name);
                }
                if (getyDown() > (getWorld().getHeight() + getWorld().getVoidSize()) * 50 && !isDead()) {
                    kill();
                    TCPClient.sendMessageToServer("/updateDeathInfo void " + name + "\n", name);
                    TCPClient.sendMessageToServer(
                            "/updateSound " + getxCenter() + " " + getyCenter() + " " + "playerHurt 0\n", name);
                }
            } else {
                if (getHealth() <= 0 && !isDead())
                    kill();
                if (getxLeft() < 0 && !isDead()) {
                    kill();
                    Command.showText(name + WordList.getWord(64, game.getLanguage()), "blue");
                }
                if (getxRight() > getWorld().getWidth() * 50 && !isDead()) {
                    kill();
                    Command.showText(name + WordList.getWord(64, game.getLanguage()), "blue");
                }
                if (getyDown() > (getWorld().getHeight() + getWorld().getVoidSize()) * 50 && !isDead()) {
                    kill();
                    Command.showText(name + WordList.getWord(64, game.getLanguage()), "blue");
                }
            }
        } else {
            if (getHealth() <= 0 && !isDead())
                setDead(true);
            if (getxLeft() < 0 && !isDead())
                setDead(true);
            if (getxRight() > getWorld().getWidth() * 50 && !isDead())
                setDead(true);
            if (getyDown() > (getWorld().getHeight() + getWorld().getVoidSize()) * 50 && !isDead())
                setDead(true);
        }
    }

    // 重写实体死亡
    @Override
    public void kill() {
        setDead(true);
        if (game.isMultiPlayerMode()) {
            TCPClient.sendMessageToServer(
                    "/updateEntityData player " + name + " " + getHealth() + " " + isDead() + "\n", name);
        }
        game.setGamePaused(false);
        game.clearGUI();
        game.setBackGui(true);
        game.getFlexTextList()
                .add(new FlexText(game, 0, -0.25, 0.6, 0.25, WordList.getWord(31, game.getLanguage()), "white", -1));
        game.getFlexButtonList().add(
                new FlexButton(game, 0, 0.02, 0.5, 0.1, WordList.getWord(32, game.getLanguage()), "white", true, 150) {
                    @Override
                    public void pressFunction() {
                        respawn();
                        game.clearGUI();
                    }
                });
        if (!game.isMultiPlayerMode())
            game.getFlexButtonList().add(new FlexButton(game, 0, 0.17, 0.5, 0.1,
                    WordList.getWord(33, game.getLanguage()), "white", true, 150) {
                @Override
                public void pressFunction() {
                    game.setGamePaused(false);
                    game.setBackGui(false);
                    game.clearGUI();
                    game.stopMusic();
                    game.getPlayer().respawn();
                    getWorld().getWorldSaver().saveWorld(game.getWorldName(), game.getWorldCurrent());
                    getWorld().threadInterrupt();
                    game.setPlayer(null);
                    game.setWorldCurrent(null);
                    game.setMouseLeftPressed(false);
                    game.setMouseRightPressed(false);
                    game.showFrame1();
                    game.updateTextureSize();
                }
            });
        else
            game.getFlexButtonList().add(new FlexButton(game, 0, 0.17, 0.5, 0.1,
                    WordList.getWord(44, game.getLanguage()), "white", true, 150) {
                @Override
                public void pressFunction() {
                    respawn();
                    game.setGamePaused(false);
                    game.clearGUI();
                    game.stopMusic();
                    game.getWorldCurrent().threadInterrupt();
                    game.setPlayer(null);
                    game.setWorldCurrent(null);
                    game.setMouseLeftPressed(false);
                    game.setMouseRightPressed(false);
                    game.setBackGui(false);
                    TCPClient.closeTCP();
                    for (int i = 0; i < game.getLinkState().length; i++)
                        game.getLinkState()[i] = false;
                    game.showFrame1();
                    game.updateTextureSize();
                }
            });
        if (!keepInventory) {
            for (int i = 0; i < 36; i++) {
                if (itemBarId[i] != -1) {
                    if (abs(getEntityLeftWallX() - getxLeft()) < 5) {
                        Random random = new Random();
                        int randomNumber = random.nextInt(8);
                        loseItem(itemBarId[i], itemBarAmount[i], randomNumber + 2, false);
                        itemBarAmount[i] = 0;
                        itemBarId[i] = -1;
                    } else if (abs(getEntityRightWallX() - getxRight()) < 5) {
                        Random random = new Random();
                        int randomNumber = random.nextInt(8);
                        loseItem(itemBarId[i], itemBarAmount[i], -randomNumber - 2, false);
                        itemBarAmount[i] = 0;
                        itemBarId[i] = -1;
                    } else {
                        Random random = new Random();
                        int randomNumber = random.nextInt(19);
                        loseItem(itemBarId[i], itemBarAmount[i], randomNumber - 9, false);
                        itemBarAmount[i] = 0;
                        itemBarId[i] = -1;
                    }
                    if (game.isMultiPlayerMode())
                        getWorld().updatePlayerItemDataToServer(this);
                }
            }
        }
        if (game.isMultiPlayerMode()) {
            getWorld().updatePlayerItemDataToServer(this);
            getWorld().updateEntitydataToServer(this);
        }
        if (game != null && !game.isMultiPlayerMode())
            getSoundList().add(new PlaySound("damage", IDIndex.soundNameToID("damage", "hit"), 100));
    }

    // 重写更新实体垂直坐标
    @Override
    public void updateY() {
        // 如果正在飞行
        if (isFlying()) {
            if (!isUnderCeil() && wPressed) {
                setySpeed(-7);
                moveY(getyUp(), getEntityUpCeilY());
            } else if (!isOnGround() && sPressed) {
                moveY(getyDown(), getEntityDownGroundY());
                setySpeed(7);
                setFallSpeed(7);
            } else if (!wPressed && !sPressed) {
                setJumpSpeed(0);
                setySpeed(0);
            }
        } else {
            // 如果可以跳跃且跳跃速度不为0
            if (isCanJump() && getJumpSpeed() != 0) {
                // 如果顶到方块，终止跳跃状态
                if (isUnderCeil()) {
                    setJumpSpeed(0);
                    setySpeed(0);
                } else {
                    // 如果处于跳跃状态，上升
                    setJumpSpeed(getJumpSpeed() + getWorld().getGravity());
                    if (getJumpSpeed() > 0)
                        setJumpSpeed(0);
                    setySpeed(getJumpSpeed());
                    moveY(getyUp(), getEntityUpCeilY());
                }
            }
            // 否则如果受重力影响且没有落地
            else if (isHasGravity() && !isOnGround()) {
                // 未在方块上，下落
                moveY(getyDown(), getEntityDownGroundY());
                if (abs(getySpeed()) + getWorld().getGravity() < getSpeedMax())
                    setySpeed(getySpeed() + getWorld().getGravity());
                setFallSpeed(abs(getySpeed()));
            } else if (!dummyPlayer && !Objects.equals(gameMode, "creative") && isInBlock() && !isDead()) {
                if (getChokeTimer() >= 180) {
                    hurt(2);
                    if (game.isMultiPlayerMode()) {
                        TCPClient.sendMessageToServer("/updateDamageTimer " + name + " " + 20 + "\n", name);
                        if (getHealth() <= 0)
                            TCPClient.sendMessageToServer("/updateDeathInfo choke " + name + "\n", name);
                    } else if (getHealth() <= 0) {
                        Command.showText(name + WordList.getWord(60, game.getLanguage()), "blue");
                    }
                    setChokeTimer(0);
                } else
                    setChokeTimer(getChokeTimer() + 1);
            }
        }
    }

    // 重写实体扣血
    @Override
    public void hurt(int damage) {
        if (login) {
            // 如果生命值能够承受伤害
            if (getHealth() >= damage)
                // 生命值直接减去伤害值
                setHealth(getHealth() - damage);
            else
                // 否则生命值直接归零
                setHealth(0);
            // 播放受伤音效
            playHurtSound();

            // 回血等待时间归零
            setHealWaitingTime(0);
            for (Entity entity : getWorld().getEntityList()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (!player.isDummyPlayer() && player.getGame().isMultiPlayerMode()) {
                        getWorld().updateEntitydataToServer(player);
                    }
                }
            }
        }
    }

    // 重写更新实体是否在方块内
    @Override
    public void updateInBlock() {
        boolean tmpInBlock = false;
        for (int i = getxLeftCollision(); i < getxRightCollision(); i++) {
            for (int j = getyUpCollision(); j < getyDownCollision(); j++) {
                if (getWorld().getBlockIdList()[j][i] != -1
                        && !IDIndex.blockIdToIsTool(getWorld().getBlockIdList()[j][i])
                        && !IDIndex.blockIdToIsTorchLike(getWorld().getBlockIdList()[j][i])) {
                    if (getyUp() + 5 >= j * 50 && getyUp() + 30 <= j * 50 + getWorld().getBlockSize()) {
                        if (getxLeft() < i * 50 + getWorld().getBlockSize() && getxRight() > i * 50
                                || getxRight() > i * 50 && getxLeft() < i * 50 + getWorld().getBlockSize())
                            tmpInBlock = true;
                    }
                    if (getxLeft() >= i * 50 && getxRight() <= i * 50 + getWorld().getBlockSize()) {
                        if (getyUp() + 5 < j * 50 + getWorld().getBlockSize() && getyUp() + 30 > j * 50
                                || getyUp() + 5 > j * 50 && getyUp() + 30 < j * 50 + getWorld().getBlockSize())
                            tmpInBlock = true;
                    }
                }
            }
        }
        if (tmpInBlock)
            setInBlock(true);
        else
            setInBlock(false);
    }

    // 重写实体停止水平移动
    @Override
    public void stopMoveX() {
        // 改变水平速度
        setxSpeed(0);
        // 改变运动状态
        moveState = "stand";
        // 改变Player材质
        if (Objects.equals(faceTo, "left")) {
            setTextureCurrent(getTextureList().get(0));
            xHand = 0;
            yHand = 0;
        } else if (Objects.equals(faceTo, "right")) {
            setTextureCurrent(getTextureList().get(1));
            xHand = 0;
            yHand = 0;
        }
        if (!isDummyPlayer() && game.isMultiPlayerMode())
            getWorld().updatePlayerMoveDataToServer(this);
    }

    // 更新麻痹状态停止运动
    public void updateParalysisAction() {
        if (getGame().isBackGui() || isInParalysis()) {
            if (Objects.equals(faceTo, "left")) {
                setTextureCurrent(getTextureList().get(0));
                xHand = 0;
                yHand = 0;
            } else if (Objects.equals(faceTo, "right")) {
                setTextureCurrent(getTextureList().get(1));
                xHand = 0;
                yHand = 0;
            }
        }
    }

    // 更新玩家背包是否能继续装下某个物品
    public boolean canLoadItem(int blockId) {
        for (int i = 0; i < 36; i++) {
            if (itemBarId[i] == blockId) {
                if (itemBarAmount[i] < IDIndex.blockIdToMaxAmount(blockId))
                    return true;
            }
        }
        for (int i = 0; i < 36; i++) {
            if (itemBarId[i] == -1)
                return true;
        }
        return false;
    }

    // 更新玩家能否往上跳
    public void updateCanJumpWall() {
        if ((getyCenter() - 25) / 50 - 1 >= 0 && (getyCenter() - 25) / 50 <= getWorld().getHeight() - 1) {
            if (!isCanLeft() && getxCenter() / 50 - 1 >= 0 && getxCenter() / 50 <= getWorld().getWidth() - 1)
                if (IDIndex.blockIdToIsUnTouchable(
                        getWorld().getBlockIdList()[(getyCenter() - 25) / 50][getxCenter() / 50 - 1])
                        && IDIndex.blockIdToIsUnTouchable(
                        getWorld().getBlockIdList()[(getyCenter() - 25) / 50 - 1][getxCenter() / 50 - 1])
                        && IDIndex.blockIdToIsUnTouchable(
                        getWorld().getBlockIdList()[(getyCenter() - 25) / 50 - 1][getxCenter() / 50]))
                    jump();
            if (!isCanRight() && getxCenter() / 50 + 1 <= getWorld().getWidth() - 1 && getxCenter() / 50 >= 0)
                if (IDIndex.blockIdToIsUnTouchable(
                        getWorld().getBlockIdList()[(getyCenter() - 25) / 50][getxCenter() / 50 + 1])
                        && IDIndex.blockIdToIsUnTouchable(
                        getWorld().getBlockIdList()[(getyCenter() - 25) / 50 - 1][getxCenter() / 50 + 1])
                        && IDIndex.blockIdToIsUnTouchable(
                        getWorld().getBlockIdList()[(getyCenter() - 25) / 50 - 1][getxCenter() / 50]))
                    jump();
        }
    }

    // 更新玩家选中方块坐标
    public void updateBlockSelected() {
        int tempXBlockSelected = (int) (game.getMouseLocation().getX() + game.getxCameraRelative()) / 50;
        int tempYBlockSelected = (int) (game.getMouseLocation().getY() + game.getyCameraRelative()) / 50;
        // 如果选中的方块出界
        if (tempXBlockSelected < 0) {
            xBlockSelected = 0;
            if (tempYBlockSelected >= 0 && tempYBlockSelected < getWorld().getHeight())
                yBlockSelected = tempYBlockSelected;
            else
                yBlockSelected = getWorld().getHeight() - 1;
        } else if (tempXBlockSelected >= getWorld().getWidth()) {
            xBlockSelected = getWorld().getWidth() - 1;
            if (tempYBlockSelected >= 0 && tempYBlockSelected < getWorld().getHeight())
                yBlockSelected = tempYBlockSelected;
            else
                yBlockSelected = getWorld().getHeight() - 1;
        } else if (tempYBlockSelected < 0) {
            if (tempXBlockSelected < getWorld().getWidth())
                xBlockSelected = tempXBlockSelected;
            else
                xBlockSelected = getWorld().getWidth() - 1;
            yBlockSelected = 0;
        } else if (tempYBlockSelected >= getWorld().getHeight()) {
            if (tempXBlockSelected < getWorld().getWidth())
                xBlockSelected = tempXBlockSelected;
            else
                xBlockSelected = getWorld().getWidth() - 1;
            yBlockSelected = getWorld().getHeight() - 1;
        }
        // 否则直接更新
        else {
            xBlockSelected = (int) (game.getMouseLocation().getX() / game.getZoomGameRatio()
                    + (int) (((game.getZoomGameRatio() - 1) * game.getWidth()) / 2 / game.getZoomGameRatio())
                    + game.getxCameraRelative()) / 50;
            yBlockSelected = (int) (game.getMouseLocation().getY() / game.getZoomGameRatio()
                    + (int) (((game.getZoomGameRatio() - 1) * game.getHeight()) / 2 / game.getZoomGameRatio())
                    + game.getyCameraRelative()) / 50;
        }
    }

    // 更新玩家鼠标在世界的坐标
    public void updateMouseInWorldLocation() {
        xMouseInWorld = (int) game.getMouseLocation().getX() - game.getxCameraRelative();
        xMouseInWorld = (int) game.getMouseLocation().getY() - game.getyCameraRelative();
    }

    // 更新玩家移动声音
    public void playWalkSound() {
        // 如果不处于静止状态且已经落地
        if (!Objects.equals(moveState, "stand") && isOnGround() && getyDown() / 50 <= getWorld().getHeight() - 1 && getyDown() / 50 >= 0) {
            // 初始化脚下方块id
            int id = -1;
            for (int i = getxLeftCollision(); i <= getxRightCollision(); i++) {
                // 如果方块处于脚下高度
                if (getWorld().getBlockIdList()[getyDown() / 50][i] != -1)
                    // 如果方块在玩家宽度内
                    if (i * 50 + getWorld().getBlockSize() > getxLeft() && i * 50 < getxRight()) {
                        // 设置脚下方块id
                        id = getWorld().getBlockIdList()[getyDown() / 50][i];
                        break;
                    }
            }
            if (id != -1)
                // 如果移动计时器未达到移动频率，计时器加一
                if (moveTimer <= moveFrequency)
                    moveTimer++;
                else {
                    if (dummyPlayer) {
                        for (Entity entity : getWorld().getEntityList()) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (player.getGame() != null) {
                                    double r1 = World.calculateIntDistance(getxCenter(), getyCenter(),
                                            player.getxCenter(), player.getyCenter());
                                    double R1 = World.calculateIntDistance(0, 0, player.getGame().getxCenter(),
                                            player.getGame().getyCenter());
                                    if (r1 <= R1)
                                        getSoundList().add(new PlaySound("step", id, 100));
                                }
                            }
                        }
                    } else
                        // 否则播放走路音效
                        getSoundList().add(new PlaySound("step", id, 100));
                    // 移动计时器归零
                    moveTimer = 0;
                }
        }
    }

    // 更新玩家选中方块是否可触及
    public void updateCanReachBlockSelected() {
        double distance = World.calculateIntDistance(xBlockSelected * 50 + 25, yBlockSelected * 50 + 25,
                (getxLeft() + getxRight()) / 2, (getyUp() + getyDown()) / 2);
        if (distance <= interactRange)
            canReachBlockSelected = true;
        else
            canReachBlockSelected = false;
        if (Objects.equals(gameMode, "creative"))
            canReachBlockSelected = true;
    }

    // 更新玩家上次选中的方块
    public void updateLastBlockSelected() {
        xLastBlockSeleted = xBlockSelected;
        yLastBlockSeleted = yBlockSelected;
    }

    // 更新玩家方块裂痕序号
    public void updateBlockCrackSort() {
        if (xBlockSelected >= 0 && xBlockSelected <= getWorld().getWidth() && yBlockSelected >= 0 && yBlockSelected <= getWorld().getHeight())
            if (getWorld().getBlockIdList()[yBlockSelected][xBlockSelected] != -1) {
                // 根据方块耐久度更新裂痕序号
                int tmpResistance = IDIndex
                        .blockIdToResistance(getWorld().getBlockIdList()[yBlockSelected][xBlockSelected], toolState);
                if (tmpResistance > 0) {
                    blockCrackSort = (int) (timeDestroyingBlock / (tmpResistance / 8.0));
                    if (blockCrackSort != blockCrackSortLast)
                        if (!isDummyPlayer() && game.isMultiPlayerMode()) {
                            TCPClient.sendMessageToServer("/updateCrack " + xBlockSelected + " " + yBlockSelected + " "
                                    + blockCrackSort + "\n", name);
                        }
                    blockCrackSortLast = blockCrackSort;
                } else {
                    timeDestroyingBlock = 0;
                    blockCrackSort = 0;
                    if (blockCrackSort != blockCrackSortLast)
                        if (!isDummyPlayer() && game.isMultiPlayerMode()) {
                            TCPClient.sendMessageToServer("/updateCrack " + xBlockSelected + " " + yBlockSelected + " "
                                    + blockCrackSort + "\n", name);
                        }
                    blockCrackSortLast = blockCrackSort;
                }
            }
        // 防止裂痕序号超过8
        if (blockCrackSort >= 8) {
            blockCrackSort = 0;
            if (blockCrackSort != blockCrackSortLast)
                if (!isDummyPlayer() && game.isMultiPlayerMode()) {
                    TCPClient.sendMessageToServer(
                            "/updateCrack " + xBlockSelected + " " + yBlockSelected + " " + blockCrackSort + "\n",
                            name);
                }
            blockCrackSortLast = blockCrackSort;
        }

        // 如果选中框内不存在方块
        if (getWorld().getBlockIdList()[yBlockSelected][xBlockSelected] == -1) {
            // 裂痕序号归零
            blockCrackSort = 0;
            if (blockCrackSort != blockCrackSortLast)
                if (!isDummyPlayer() && game.isMultiPlayerMode()) {
                    TCPClient.sendMessageToServer(
                            "/updateCrack " + xBlockSelected + " " + yBlockSelected + " " + blockCrackSort + "\n",
                            name);
                }
            blockCrackSortLast = blockCrackSort;
        }
    }

    // 更新玩家物品栏内物品ID
    public void updateItemBarId() {
        for (int i = 0; i < 9; i++)
            if (itemBarAmount[i] == 0)
                itemBarId[i] = -1;
    }

    // 更新玩家不可右键时间
    public void updateNoRightPressTimer() {
        if (noRightPressTimer > 0)
            noRightPressTimer--;
        else if (noRightPressTimer < 0)
            noRightPressTimer = 0;
    }

    // 更新玩家渲染距离内草坪蔓延消亡
    public void updateGrassSpread() {
        int xLeftVision = (getxCenter() - xVision) / 50;
        int xRightVision = (getxCenter() + xVision) / 50;
        int yUpVision = (getyCenter() - yVision) / 50;
        int yDownVision = (getyCenter() + yVision) / 50;
        if (xLeftVision < 0)
            xLeftVision = 0;
        if (xRightVision >= getWorld().getWidth())
            xRightVision = getWorld().getWidth() - 1;
        if (yUpVision < 0)
            yUpVision = 0;
        if (yDownVision >= getWorld().getHeight())
            yDownVision = getWorld().getHeight() - 1;
        for (int i = xLeftVision; i <= xRightVision; i++) {
            for (int j = yUpVision; j <= yDownVision; j++) {
                boolean canSpread = false;
                boolean canDisappear = false;
                if (j - 1 >= 0 && getWorld().getBlockIdList()[j][i] == 0
                        && (getWorld().getBlockIdList()[j - 1][i] == -1
                        || IDIndex.blockIdToIsTool(getWorld().getBlockIdList()[j - 1][i])
                        || IDIndex.blockIdToIsTorchLike(getWorld().getBlockIdList()[j - 1][i]))) {
                    for (int k = -1; k <= 1; k++) {
                        if (j + k < 0 || j + k > getWorld().getHeight() - 1)
                            continue;
                        if (i - 1 >= 0 && getWorld().getBlockIdList()[j + k][i - 1] == 1)
                            canSpread = true;
                        else if (i + 1 <= getWorld().getWidth() - 1 && getWorld().getBlockIdList()[j + k][i + 1] == 1)
                            canSpread = true;
                    }
                } else if (j >= 1 && getWorld().getBlockIdList()[j][i] == 1 && getWorld().getBlockIdList()[j - 1][i] != -1
                        && !IDIndex.blockIdToIsTool(getWorld().getBlockIdList()[j - 1][i])
                        && !IDIndex.blockIdToIsTorchLike(getWorld().getBlockIdList()[j - 1][i])) {
                    canDisappear = true;
                }
                if (getWorld().getBlockIdList()[j][i] == 0 && canSpread) {
                    Random random = new Random();
                    int randomNumber = random.nextInt(1000);
                    if (randomNumber == 0)
                        getWorld().getBlockIdList()[j][i] = 1;
                }
                if (getWorld().getBlockIdList()[j][i] == 1 && canDisappear) {
                    Random random = new Random();
                    int randomNumber = random.nextInt(1000);
                    if (randomNumber == 0)
                        getWorld().getBlockIdList()[j][i] = 0;
                }
            }
        }
    }

    // 更新玩家无敌状态
    public void updateCanHurt() {
        if (Objects.equals(gameMode, "creative"))
            setCanHurt(false);
        else
            setCanHurt(true);
    }

    // 更新玩家飞行状态
    public void updateFlyingState() {
        if (!Objects.equals(gameMode, "creative"))
            setFlying(false);
    }

    // 更新玩家工具状态
    public void updateToolState() {
        if (itemBarId[itemBarChosen] == 9)
            toolState = "wooden_sword";
        else if (itemBarId[itemBarChosen] == 13)
            toolState = "stone_sword";
        else if (itemBarId[itemBarChosen] == 10)
            toolState = "wooden_pickaxe";
        else if (itemBarId[itemBarChosen] == 14)
            toolState = "stone_pickaxe";
        else if (itemBarId[itemBarChosen] == 11)
            toolState = "wooden_axe";
        else if (itemBarId[itemBarChosen] == 15)
            toolState = "stone_axe";
        else if (itemBarId[itemBarChosen] == 12)
            toolState = "wooden_shovel";
        else if (itemBarId[itemBarChosen] == 30)
            toolState = "iron_sword";
        else if (itemBarId[itemBarChosen] == 31)
            toolState = "iron_pickaxe";
        else if (itemBarId[itemBarChosen] == 32)
            toolState = "iron_axe";
        else if (itemBarId[itemBarChosen] == 33)
            toolState = "iron_shovel";
        else if (itemBarId[itemBarChosen] == 34)
            toolState = "golden_sword";
        else if (itemBarId[itemBarChosen] == 35)
            toolState = "golden_pickaxe";
        else if (itemBarId[itemBarChosen] == 36)
            toolState = "golden_axe";
        else if (itemBarId[itemBarChosen] == 37)
            toolState = "golden_shovel";
        else if (itemBarId[itemBarChosen] == 38)
            toolState = "diamond_sword";
        else if (itemBarId[itemBarChosen] == 39)
            toolState = "diamond_pickaxe";
        else if (itemBarId[itemBarChosen] == 40)
            toolState = "diamond_axe";
        else if (itemBarId[itemBarChosen] == 41)
            toolState = "diamond_shovel";
        else
            toolState = "hand";
    }

    // 更新玩家攻击力
    public void updateAttackValue() {
        attackValue = IDIndex.blockIdToAttackValue(itemBarId[itemBarChosen]);
    }

    // 更新死亡重置计时器
    public void updateRespawnPauseTimer() {
        if (respawnPauseTimer > 0)
            respawnPauseTimer--;
        else if (respawnPauseTimer <= 0)
            respawnPauseTimer = 0;
    }

    // 更新玩家材质
    public void updateTexture() {
        if (Objects.equals(moveState, "walk") && Objects.equals(faceTo, "left")) {
            if (walkTimer >= 0 && walkTimer < 10) {
                setTextureCurrent(getTextureList().get(0));
                xHand = 0;
                yHand = 0;
            } else if (walkTimer >= 10 && walkTimer < 20) {
                setTextureCurrent(getTextureList().get(2));
                xHand = 4;
                yHand = -2;
            } else if (walkTimer >= 20 && walkTimer < 40) {
                setTextureCurrent(getTextureList().get(6));
                xHand = 9;
                yHand = -2;
            } else if (walkTimer >= 40 && walkTimer < 50) {
                setTextureCurrent(getTextureList().get(2));
                xHand = 4;
                yHand = -2;
            } else if (walkTimer >= 50 && walkTimer < 60) {
                setTextureCurrent(getTextureList().get(0));
                xHand = 0;
                yHand = 0;
            } else if (walkTimer >= 60 && walkTimer < 70) {
                setTextureCurrent(getTextureList().get(3));
                xHand = -4;
                yHand = -2;
            } else if (walkTimer >= 70 && walkTimer < 90) {
                setTextureCurrent(getTextureList().get(7));
                xHand = -9;
                yHand = -2;
            } else if (walkTimer >= 90 && walkTimer < 100) {
                setTextureCurrent(getTextureList().get(3));
                xHand = -4;
                yHand = -2;
            } else if (walkTimer >= 100)
                walkTimer = 0;
            walkTimer++;
        } else if (Objects.equals(moveState, "walk") && Objects.equals(faceTo, "right")) {
            if (walkTimer >= 0 && walkTimer < 10) {
                setTextureCurrent(getTextureList().get(1));
                xHand = 0;
                yHand = 0;
            } else if (walkTimer >= 10 && walkTimer < 20) {
                setTextureCurrent(getTextureList().get(4));
                xHand = 6;
                yHand = -2;
            } else if (walkTimer >= 20 && walkTimer < 40) {
                setTextureCurrent(getTextureList().get(8));
                xHand = 9;
                yHand = -2;
            } else if (walkTimer >= 40 && walkTimer < 50) {
                setTextureCurrent(getTextureList().get(4));
                xHand = 6;
                yHand = -2;
            } else if (walkTimer >= 50 && walkTimer < 60) {
                setTextureCurrent(getTextureList().get(1));
                xHand = 0;
                yHand = 0;
            } else if (walkTimer >= 60 && walkTimer < 70) {
                setTextureCurrent(getTextureList().get(5));
                xHand = -6;
                yHand = -2;
            } else if (walkTimer >= 70 && walkTimer < 90) {
                setTextureCurrent(getTextureList().get(9));
                xHand = -9;
                yHand = -2;
            } else if (walkTimer >= 90 && walkTimer < 100) {
                setTextureCurrent(getTextureList().get(5));
                xHand = -6;
                yHand = -2;
            } else if (walkTimer >= 100)
                walkTimer = 0;
            walkTimer++;
        } else if (Objects.equals(moveState, "run") && Objects.equals(faceTo, "left")) {
            if (runTimer >= 0 && runTimer < 5) {
                setTextureCurrent(getTextureList().get(0));
                xHand = 0;
                yHand = 0;
            } else if (runTimer >= 5 && runTimer < 10) {
                setTextureCurrent(getTextureList().get(2));
                xHand = 4;
                yHand = -2;
            } else if (runTimer >= 10 && runTimer < 15) {
                setTextureCurrent(getTextureList().get(6));
                xHand = 9;
                yHand = -2;
            } else if (runTimer >= 15 && runTimer < 25) {
                setTextureCurrent(getTextureList().get(10));
                xHand = 17;
                yHand = -7;
            } else if (runTimer >= 25 && runTimer < 30) {
                setTextureCurrent(getTextureList().get(6));
                xHand = 9;
                yHand = -2;
            } else if (runTimer >= 30 && runTimer < 35) {
                setTextureCurrent(getTextureList().get(2));
                xHand = 4;
                yHand = -2;
            } else if (runTimer >= 35 && runTimer < 40) {
                setTextureCurrent(getTextureList().get(0));
                xHand = 0;
                yHand = 0;
            } else if (runTimer >= 40 && runTimer < 45) {
                setTextureCurrent(getTextureList().get(3));
                xHand = -4;
                yHand = -2;
            } else if (runTimer >= 45 && runTimer < 50) {
                setTextureCurrent(getTextureList().get(7));
                xHand = -9;
                yHand = -2;
            } else if (runTimer >= 50 && runTimer < 60) {
                setTextureCurrent(getTextureList().get(11));
                xHand = -17;
                yHand = -11;
            } else if (runTimer >= 60 && runTimer < 65) {
                setTextureCurrent(getTextureList().get(7));
                xHand = -9;
                yHand = -2;
            } else if (runTimer >= 65 && runTimer < 70) {
                setTextureCurrent(getTextureList().get(3));
                xHand = -4;
                yHand = -2;
            } else if (runTimer >= 70)
                runTimer = 0;
            runTimer++;
        } else if (Objects.equals(moveState, "run") && Objects.equals(faceTo, "right")) {
            if (runTimer >= 0 && runTimer < 5) {
                setTextureCurrent(getTextureList().get(1));
                xHand = 0;
                yHand = 0;
            } else if (runTimer >= 5 && runTimer < 10) {
                setTextureCurrent(getTextureList().get(4));
                xHand = 4;
                yHand = -2;
            } else if (runTimer >= 10 && runTimer < 15) {
                setTextureCurrent(getTextureList().get(8));
                xHand = 9;
                yHand = -2;
            } else if (runTimer >= 15 && runTimer < 25) {
                setTextureCurrent(getTextureList().get(12));
                xHand = 20;
                yHand = -11;
            } else if (runTimer >= 25 && runTimer < 30) {
                setTextureCurrent(getTextureList().get(8));
                xHand = 9;
                yHand = -2;
            } else if (runTimer >= 30 && runTimer < 35) {
                setTextureCurrent(getTextureList().get(4));
                xHand = 4;
                yHand = -2;
            } else if (runTimer >= 35 && runTimer < 40) {
                setTextureCurrent(getTextureList().get(1));
                xHand = 0;
                yHand = 0;
            } else if (runTimer >= 40 && runTimer < 45) {
                setTextureCurrent(getTextureList().get(5));
                xHand = -4;
                yHand = -2;
            } else if (runTimer >= 45 && runTimer < 50) {
                setTextureCurrent(getTextureList().get(9));
                xHand = -9;
                yHand = -2;
            } else if (runTimer >= 50 && runTimer < 60) {
                setTextureCurrent(getTextureList().get(13));
                xHand = -15;
                yHand = -6;
            } else if (runTimer >= 60 && runTimer < 65) {
                setTextureCurrent(getTextureList().get(9));
                xHand = -9;
                yHand = -2;
            } else if (runTimer >= 65 && runTimer < 70) {
                setTextureCurrent(getTextureList().get(5));
                xHand = -4;
                yHand = -2;
            } else if (runTimer >= 70)
                runTimer = 0;
            runTimer++;
        } else if (Objects.equals(moveState, "stand") && Objects.equals(faceTo, "left")) {
            if (clickTimer > 0) {
                if (clickTimer <= 23 && clickTimer > 15) {
                    setTextureCurrent(getTextureList().get(16));
                    xHand = -12;
                    yHand = -4;
                } else if (clickTimer <= 15 && clickTimer > 10) {
                    setTextureCurrent(getTextureList().get(15));
                    xHand = -9;
                    yHand = -2;
                } else if (clickTimer <= 10 && clickTimer > 5) {
                    setTextureCurrent(getTextureList().get(14));
                    xHand = -4;
                    yHand = -2;
                } else if (clickTimer <= 5) {
                    setTextureCurrent(getTextureList().get(0));
                    xHand = 0;
                    yHand = 0;
                }
            } else {
                setTextureCurrent(getTextureList().get(0));
                xHand = 0;
                yHand = 0;
            }
        } else if (Objects.equals(moveState, "stand") && Objects.equals(faceTo, "right")) {
            if (clickTimer > 0) {
                if (clickTimer <= 23 && clickTimer > 15) {
                    setTextureCurrent(getTextureList().get(19));
                    xHand = 12;
                    yHand = -4;
                } else if (clickTimer <= 15 && clickTimer > 10) {
                    setTextureCurrent(getTextureList().get(18));
                    xHand = 9;
                    yHand = -2;
                } else if (clickTimer <= 10 && clickTimer > 5) {
                    setTextureCurrent(getTextureList().get(17));
                    xHand = 4;
                    yHand = -2;
                } else if (clickTimer <= 5) {
                    setTextureCurrent(getTextureList().get(1));
                    xHand = 0;
                    yHand = 0;
                }
            } else {
                setTextureCurrent(getTextureList().get(1));
                xHand = 0;
                yHand = 0;
            }
        }
        if (canContinueHand && (mouseLeftPress || mouseRightPress)) {
            if (game != null && !game.isBackGui())
                clickTimer = 23;
            else if (game == null)
                clickTimer = 23;
            canContinueHand = false;
        } else if (!(mouseLeftPress || mouseRightPress))
            canContinueHand = true;
        if (clickTimer > 0)
            clickTimer--;
        else
            clickTimer = 0;
    }

    // 更新玩家物品栏选中序号
    public void updateItemBarChosen() {
        if (itemBarChosen < 0)
            itemBarChosen = 0;
        if (itemBarChosen > 8)
            itemBarChosen = 8;
    }

    // 更新玩家攻击CD
    public void updateAttackCD() {
        if (attackCD != 0)
            attackCD--;
        else if (attackCD < 0)
            attackCD = 0;
    }

    // 重写更新闪烁计时器
    @Override
    public void updateFlashTimer() {
        if (getHealth() != getLastHealth()) {
            if (getHealth() < getLastHealth())
                setDamageTimer(20);
            heartFlashTimer = 20;
            if (!dummyPlayer && game.isMultiPlayerMode()) {
                getWorld().updateEntitydataToServer(this);
            }
            setLastHealth(getHealth());
            if (game != null && game.isMultiPlayerMode()) {
                TCPClient.sendMessageToServer("/updateHealth player " + name + " " + getHealth() + "\n", name);
            }
        } else {
            if (heartFlashTimer > 0)
                heartFlashTimer--;
            else if (heartFlashTimer < 0)
                heartFlashTimer = 0;
            if (getDamageTimer() > 0)
                setDamageTimer(getDamageTimer() - 1);
            else if (getDamageTimer() < 0)
                setDamageTimer(0);
        }
    }

    // 更新玩家数据
    public void updatePlayerData() {
        if (!dummyPlayer) {
            // 设置玩家所在世界
            setWorld(game.getWorldCurrent());
            // 更新玩家物品栏内物品id
            updateItemBarId();
            // 更新玩家选中方块
            updateBlockSelected();
            // 更新方块裂痕序号
            updateBlockCrackSort();
            // 更新玩家选中方块是否可触及
            updateCanReachBlockSelected();
            // 更新玩家鼠标在世界的位置
            updateMouseInWorldLocation();
            // 更新玩家物品栏选中序号
            updateItemBarChosen();
            // 更新玩家攻击CD
            updateAttackCD();
            // 更新死亡重置计时器
            updateRespawnPauseTimer();
            // 更新玩家不可右键时间
            updateNoRightPressTimer();
            // 更新麻痹状态停止运动
            updateParalysisAction();
            if (!game.isMultiPlayerMode()) {
                // 更新玩家渲染距离内草坪蔓延消亡
                updateGrassSpread();
            }
        } else {
            setHealWaitingTime(0);
        }
        // 更新玩家材质
        updateTexture();
        // 如果在移动，播放玩家移动音效
        playWalkSound();
        // 更新玩家无敌状态
        updateCanHurt();
        // 更新玩家飞行状态
        updateFlyingState();
        // 更新玩家工具状态
        updateToolState();
        // 更新玩家攻击力
        updateAttackValue();
    }

    public GameFrame getGame() {
        return game;
    }

    public void setGame(GameFrame game) {
        this.game = game;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaceTo() {
        return faceTo;
    }

    public void setFaceTo(String faceTo) {
        this.faceTo = faceTo;
    }

    public String getMoveState() {
        return moveState;
    }

    public void setMoveState(String moveState) {
        this.moveState = moveState;
    }

    public int[] getItemBarId() {
        return itemBarId;
    }

    public void setItemBarId(int[] itemBarId) {
        this.itemBarId = itemBarId;
    }

    public void setItemBarIdSingle(int i, int itemBarId) {
        this.itemBarId[i] = itemBarId;
    }

    public int[] getItemBarAmount() {
        return itemBarAmount;
    }

    public void setItemBarAmount(int[] itemBarAmount) {
        this.itemBarAmount = itemBarAmount;
    }

    public void setItemBarAmountSingle(int i, int itemBarAmount) {
        this.itemBarAmount[i] = itemBarAmount;
    }

    public int getItemBarChosen() {
        return itemBarChosen;
    }

    public void setItemBarChosen(int itemBarChosen) {
        this.itemBarChosen = itemBarChosen;
    }

    public int getMoveTimer() {
        return moveTimer;
    }

    public void setMoveTimer(int moveTimer) {
        this.moveTimer = moveTimer;
    }

    public int getMoveFrequency() {
        return moveFrequency;
    }

    public void setMoveFrequency(int moveFrequency) {
        this.moveFrequency = moveFrequency;
    }

    public int getxBlockSelected() {
        return xBlockSelected;
    }

    public void setxBlockSelected(int xBlockSelected) {
        this.xBlockSelected = xBlockSelected;
    }

    public int getyBlockSelected() {
        return yBlockSelected;
    }

    public void setyBlockSelected(int yBlockSelected) {
        this.yBlockSelected = yBlockSelected;
    }

    public int getxLastBlockSeleted() {
        return xLastBlockSeleted;
    }

    public void setxLastBlockSeleted(int xLastBlockSeleted) {
        this.xLastBlockSeleted = xLastBlockSeleted;
    }

    public int getyLastBlockSeleted() {
        return yLastBlockSeleted;
    }

    public void setyLastBlockSeleted(int yLastBlockSeleted) {
        this.yLastBlockSeleted = yLastBlockSeleted;
    }

    public boolean isMouseLeftPress() {
        return mouseLeftPress;
    }

    public void setMouseLeftPress(boolean mouseLeftPress) {
        this.mouseLeftPress = mouseLeftPress;
    }

    public boolean isMouseRightPress() {
        return mouseRightPress;
    }

    public void setMouseRightPress(boolean mouseRightPress) {
        this.mouseRightPress = mouseRightPress;
    }

    public int getTimeDestroyingBlock() {
        return timeDestroyingBlock;
    }

    public void setTimeDestroyingBlock(int timeDestroyingBlock) {
        this.timeDestroyingBlock = timeDestroyingBlock;
    }

    public int getBlockCrackSort() {
        return blockCrackSort;
    }

    public void setBlockCrackSort(int blockCrackSort) {
        this.blockCrackSort = blockCrackSort;
    }

    public int getInteractRange() {
        return interactRange;
    }

    public void setInteractRange(int interactRange) {
        this.interactRange = interactRange;
    }

    public boolean isCanReachBlockSelected() {
        return canReachBlockSelected;
    }

    public void setCanReachBlockSelected(boolean canReachBlockSelected) {
        this.canReachBlockSelected = canReachBlockSelected;
    }

    public int getxMouseInWorld() {
        return xMouseInWorld;
    }

    public void setxMouseInWorld(int xMouseInWorld) {
        this.xMouseInWorld = xMouseInWorld;
    }

    public int getyMouseInWorld() {
        return yMouseInWorld;
    }

    public void setyMouseInWorld(int yMouseInWorld) {
        this.yMouseInWorld = yMouseInWorld;
    }

    public boolean iswPressed() {
        return wPressed;
    }

    public void setwPressed(boolean wPressed) {
        this.wPressed = wPressed;
    }

    public boolean issPressed() {
        return sPressed;
    }

    public void setsPressed(boolean sPressed) {
        this.sPressed = sPressed;
    }

    public boolean isKeepInventory() {
        return keepInventory;
    }

    public void setKeepInventory(boolean keepInventory) {
        this.keepInventory = keepInventory;
    }

    public int getxVision() {
        return xVision;
    }

    public void setxVision(int xVision) {
        this.xVision = xVision;
    }

    public int getyVision() {
        return yVision;
    }

    public void setyVision(int yVision) {
        this.yVision = yVision;
    }

    public int getxHand() {
        return xHand;
    }

    public void setxHand(int xHand) {
        this.xHand = xHand;
    }

    public int getyHand() {
        return yHand;
    }

    public void setyHand(int yHand) {
        this.yHand = yHand;
    }

    public int getWalkTimer() {
        return walkTimer;
    }

    public void setWalkTimer(int walkTimer) {
        this.walkTimer = walkTimer;
    }

    public int getRunTimer() {
        return runTimer;
    }

    public void setRunTimer(int runTimer) {
        this.runTimer = runTimer;
    }

    public String getToolState() {
        return toolState;
    }

    public void setToolState(String toolState) {
        this.toolState = toolState;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getClickTimer() {
        return clickTimer;
    }

    public void setClickTimer(int clickTimer) {
        this.clickTimer = clickTimer;
    }

    public boolean isCanContinueHand() {
        return canContinueHand;
    }

    public void setCanContinueHand(boolean canContinueHand) {
        this.canContinueHand = canContinueHand;
    }

    public boolean isAutoJump() {
        return autoJump;
    }

    public void setAutoJump(boolean autoJump) {
        this.autoJump = autoJump;
    }

    public int getAttackCD() {
        return attackCD;
    }

    public void setAttackCD(int attackCD) {
        this.attackCD = attackCD;
    }

    public int getHeartFlashTimer() {
        return heartFlashTimer;
    }

    public void setHeartFlashTimer(int heartFlashTimer) {
        this.heartFlashTimer = heartFlashTimer;
    }

    public boolean isAttackMode() {
        return attackMode;
    }

    public void setAttackMode(boolean attackMode) {
        this.attackMode = attackMode;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getRespawnPauseTimer() {
        return respawnPauseTimer;
    }

    public void setRespawnPauseTimer(int respawnPauseTimer) {
        this.respawnPauseTimer = respawnPauseTimer;
    }

    public int getNoRightPressTimer() {
        return noRightPressTimer;
    }

    public void setNoRightPressTimer(int noRightPressTimer) {
        this.noRightPressTimer = noRightPressTimer;
    }

    public boolean isDummyPlayer() {
        return dummyPlayer;
    }

    public void setDummyPlayer(boolean dummyPlayer) {
        this.dummyPlayer = dummyPlayer;
    }

    public boolean isOperator() {
        return operator;
    }

    public void setOperator(boolean operator) {
        this.operator = operator;
    }

    public int getBlockCrackSortLast() {
        return blockCrackSortLast;
    }

    public void setBlockCrackSortLast(int blockCrackSortLast) {
        this.blockCrackSortLast = blockCrackSortLast;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}