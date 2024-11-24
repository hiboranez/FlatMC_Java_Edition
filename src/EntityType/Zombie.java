package EntityType;

import Base.World;
import Element.Entity;
import Utils.OtherTool.IDIndex;
import Utils.OtherTool.PlaySound;
import Utils.OtherTool.Resource;
import Utils.OtherTool.WordList;
import Utils.TCP.Command;
import Utils.TCP.TCPClient;

import java.util.Objects;
import java.util.Random;

import static java.lang.Math.*;

public class Zombie extends Entity {
    // 定义僵尸朝向
    // left right
    private String faceTo = "left";
    // 定义僵尸当前运动状态
    // stand    walk
    private String moveState = "stand";
    // 定义僵尸锁定的玩家
    private Player player = null;
    // 定义僵尸移动计时器，用于播放走路声音
    private int moveTimer = 0;
    // 定义僵尸走路计时器，用于播放运动动画
    private int walkTimer = 0;
    // 定义僵尸移动频率
    private int moveFrequency = 20;
    // 定义僵尸攻击冷却时间
    private int attackCD = 100;
    // 定义僵尸攻击范围
    private int attackRange = 50;
    // 定义僵尸移动目的地
    private int moveToX = 0;
    // 定义僵尸是否需要移动
    private boolean onMoving = false;

    public Zombie(int x, int y, World world) {
        // 设置实体类型
        setType("zombie");
        // 设置坐标
        setX(x);
        setY(y);
        // 设置所处世界
        setWorld(world);
        // 设置大小
        setWidth(20);
        setHeight(95);
        // 设置实体大小修正值
        setxLeftRevision((70 - getWidth()) / 2);
        setxRightRevision((70 - getWidth()) / 2 + getWidth());
        setyUpRevision(0);
        setyDownRevision(getHeight());
        // 设置材质包
        setTextureList(Resource.getZombieTextureList());
        // 设置材质
        setTextureCurrent(getTextureList().get(0));
    }

    public Zombie(int idCode, int x, int y, int health, World world) {
        setIdCode(idCode);
        setHealth(health);
        // 设置实体类型
        setType("zombie");
        // 设置坐标
        setX(x);
        setY(y);
        // 设置所处世界
        setWorld(world);
        // 设置大小
        setWidth(20);
        setHeight(95);
        // 设置实体大小修正值
        setxLeftRevision((70 - getWidth()) / 2);
        setxRightRevision((70 - getWidth()) / 2 + getWidth());
        setyUpRevision(0);
        setyDownRevision(getHeight());
        // 设置材质包
        setTextureList(Resource.getZombieTextureList());
        // 设置材质
        setTextureCurrent(getTextureList().get(0));
    }

    public Zombie() {
        // 设置实体类型
        setType("zombie");
        // 设置大小
        setWidth(20);
        setHeight(95);
        // 设置实体大小修正值
        setxLeftRevision((70 - getWidth()) / 2);
        setxRightRevision((70 - getWidth()) / 2 + getWidth());
        setyUpRevision(0);
        setyDownRevision(getHeight());
        // 设置材质包
        setTextureList(Resource.getZombieTextureList());
        // 设置材质
        setTextureCurrent(getTextureList().get(0));
    }

    // 僵尸向左走
    public void walkLeft() {
        if (isCanLeft() && !isInParalysis()) {
            // 改变速度
            setxSpeed(-1);
            // 改变朝向
            faceTo = "left";
            // 改变运动状态
            moveState = "walk";
            // 改变移动频率
            moveFrequency = 50;
        }
    }

    // 僵尸向右走
    public void walkRight() {
        if (isCanRight() && !isInParalysis()) {
            // 改变速度
            setxSpeed(1);
            // 改变朝向
            faceTo = "right";
            // 改变运动状态
            moveState = "walk";
            // 改变移动频率
            moveFrequency = 50;
        }
    }

    // 僵尸跳跃
    public void jump() {
        // 判断是否站在方块上
        if (isOnGround() && !isUnderCeil()) {
            // 更改上升速度
            setJumpSpeed(-8.0);
        }
    }

    // 僵尸攻击
    public void attack() {
        if (player != null && attackCD == 0 && !isInParalysis()) {
            if (player.getyCenter() >= getyCenter() - 50 && player.getyCenter() <= getyCenter() + 50 && ((Objects.equals(faceTo, "left") && player.getxCenter() <= getxCenter() && player.getxCenter() >= getxCenter() - attackRange) || (Objects.equals(faceTo, "right") && player.getxCenter() >= getxCenter() && player.getxCenter() <= getxCenter() + attackRange))) {
                int xMin = min(getxCenter() / 50, player.getxCenter() / 50);
                int xMax = max(getxCenter() / 50, player.getxCenter() / 50);
                boolean canAttack = true;
                for (int x = xMin; x <= xMax; x++) {
                    if (!(getWorld().getBlockIdList()[(getyCenter() - 20) / 50][x] == -1 || IDIndex.blockIdToIsTorchLike(getWorld().getBlockIdList()[(getyCenter() - 25) / 50][x])))
                        canAttack = false;
                }
                if (canAttack && !Objects.equals(player.getGameMode(), "creative") && !player.isDead()) {
                    Boolean multiPlayerMode = false;
                    for (Entity entity : getWorld().getEntityList()) {
                        if (entity instanceof Player && (!((Player) entity).isDummyPlayer())) {
                            if (((Player) entity).getGame().isMultiPlayerMode()) {
                                multiPlayerMode = true;
                                if (!player.getGameMode().equals("creative")) {
                                    // 播放受伤音效
                                    if (player.getHealth() > 3) {
                                        TCPClient.sendMessageToServer("/updateSound " + getxCenter() + " " + getyCenter() + " " + "damageHit 0\n", ((Player) entity).getName());
                                    } else
                                        TCPClient.sendMessageToServer("/updateSound " + getxCenter() + " " + getyCenter() + " " + "playerHurt 0\n", ((Player) entity).getName());
                                    player.setDamageTimer(20);
                                    TCPClient.sendMessageToServer("/updateAttack player " + player.getName() + " " + faceTo + " " + 3 + "\n", ((Player) entity).getName());
                                    if (player.getHealth() <= 3) {
                                        TCPClient.sendMessageToServer("/updateDeathInfo zombie " + player.getName() + "\n", ((Player) entity).getName());
                                    }
                                }
                            }
                        }
                    }
                    if (!multiPlayerMode) {
                        player.hurt(3);
                        if (player.getHealth() <= 0) {
                            player.setDead(true);
                            player.kill();
                            Command.showText(player.getName() + WordList.getWord(61, player.getGame().getLanguage()), "blue");
                        }

                        if (Objects.equals(faceTo, "left")) {
                            player.setInParalysis(true);
                            player.setParalysisTimer(30);
                            player.setxSpeed(-8);
                        } else if (Objects.equals(faceTo, "right")) {
                            player.setInParalysis(true);
                            player.setParalysisTimer(30);
                            player.setxSpeed(8);
                        }
                    }
                    attackCD = 100;
                }

            }
        }
    }


    // 更新僵尸攻击CD
    public void updateAttackCD() {
        if (attackCD != 0) attackCD--;
        else if (attackCD < 0) attackCD = 0;
    }

    // 更新麻痹状态停止运动
    public void updateParalysisAction() {
        if (isInParalysis()) {
            if (Objects.equals(faceTo, "left")) {
                setTextureCurrent(getTextureList().get(0));
            } else if (Objects.equals(faceTo, "right")) {
                setTextureCurrent(getTextureList().get(1));
            }
        }
    }

    // 重写实体停止水平移动
    @Override
    public void stopMoveX() {
        // 改变僵尸水平速度
        setxSpeed(0);
        // 改变僵尸运动状态
        moveState = "stand";
        // 改变僵尸材质
        if (Objects.equals(faceTo, "left"))
            setTextureCurrent(getTextureList().get(0));
        else if (Objects.equals(faceTo, "right"))
            setTextureCurrent(getTextureList().get(1));
    }

    // 重写实体死亡
    @Override
    public void kill() {
        Random random = new Random();
        int randomNumber1 = random.nextInt(100);
        int randomNumber2 = random.nextInt(100);
        randomNumber1++;
        randomNumber2++;
        getWorld().getEntityList().remove(this);
        for (Entity entity : getWorld().getEntityList()) {
            if (entity instanceof Player) {
                Player host = (Player) entity;
                if (!host.isDummyPlayer()) {
                    if (host.getGame().isMultiPlayerMode()) {
                        getWorld().updateEntitydataToServer(host);
                        TCPClient.sendMessageToServer("/updateRemoveZombie " + getIdCode() + "\n", host.getName());
                        if (randomNumber1 <= 80) {
                            if (randomNumber2 <= 20)
                                TCPClient.sendMessageToServer("/updateSummonItem " + getxCenter() + " " + getyCenter() + " " + 42 + " " + 2 + " " + 0 + "\n", host.getName());
                            else
                                TCPClient.sendMessageToServer("/updateSummonItem " + getxCenter() + " " + getyCenter() + " " + 42 + " " + 1 + " " + 0 + "\n", host.getName());
                        }
                    } else {
                        if (randomNumber1 <= 80) {
                            if (randomNumber2 <= 20)
                                getWorld().getEntityList().add(new Item(getxCenter(), getyCenter(), 42, 2, getWorld(), 10));
                            else
                                getWorld().getEntityList().add(new Item(getxCenter(), getyCenter(), 42, 1, getWorld(), 10));
                        }
                    }
                }
            }
        }
    }

    // 更新僵尸材质
    public void updateTexture() {
        if (Objects.equals(moveState, "walk") && Objects.equals(faceTo, "left")) {
            if (walkTimer >= 0 && walkTimer < 10) {
                setTextureCurrent(getTextureList().get(0));
            } else if (walkTimer >= 10 && walkTimer < 20) {
                setTextureCurrent(getTextureList().get(2));
            } else if (walkTimer >= 20 && walkTimer < 40) {
                setTextureCurrent(getTextureList().get(6));
            } else if (walkTimer >= 40 && walkTimer < 50) {
                setTextureCurrent(getTextureList().get(2));
            } else if (walkTimer >= 50 && walkTimer < 60) {
                setTextureCurrent(getTextureList().get(0));
            } else if (walkTimer >= 60 && walkTimer < 70) {
                setTextureCurrent(getTextureList().get(3));
            } else if (walkTimer >= 70 && walkTimer < 90) {
                setTextureCurrent(getTextureList().get(7));
            } else if (walkTimer >= 90 && walkTimer < 100) {
                setTextureCurrent(getTextureList().get(3));
            } else if (walkTimer >= 100) walkTimer = 0;
            walkTimer++;
        } else if (Objects.equals(moveState, "walk") && Objects.equals(faceTo, "right")) {
            if (walkTimer >= 0 && walkTimer < 10) {
                setTextureCurrent(getTextureList().get(1));
            } else if (walkTimer >= 10 && walkTimer < 20) {
                setTextureCurrent(getTextureList().get(4));
            } else if (walkTimer >= 20 && walkTimer < 40) {
                setTextureCurrent(getTextureList().get(8));
            } else if (walkTimer >= 40 && walkTimer < 50) {
                setTextureCurrent(getTextureList().get(4));
            } else if (walkTimer >= 50 && walkTimer < 60) {
                setTextureCurrent(getTextureList().get(1));
            } else if (walkTimer >= 60 && walkTimer < 70) {
                setTextureCurrent(getTextureList().get(5));
            } else if (walkTimer >= 70 && walkTimer < 90) {
                setTextureCurrent(getTextureList().get(9));
            } else if (walkTimer >= 90 && walkTimer < 100) {
                setTextureCurrent(getTextureList().get(5));
            } else if (walkTimer >= 100) walkTimer = 0;
            walkTimer++;
        }
    }

    // 更新僵尸移动声音
    public void playWalkSound() {
        // 如果不处于静止状态且已经落地
        if (!Objects.equals(moveState, "stand") && isOnGround()) {
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
                if (moveTimer <= moveFrequency) moveTimer++;
                else if (player != null && player.getGame() != null) {
                    double R1 = World.calculateIntDistance(0, 0, player.getGame().getWidth() / 2, player.getGame().getHeight() / 2);
                    double r1 = World.calculateIntDistance(player.getxCenter(), player.getyCenter(), getxCenter(), getyCenter());
                    if (R1 >= r1)
                        getSoundList().add(new PlaySound("step", id, (int) (1 - (r1 / R1)) * 100));
                    // 移动计时器归零
                    moveTimer = 0;
                }
        }
    }

    // 更新僵尸选中玩家
    public void updatePlayerSelected() {
        double distanceMin = getWorld().getWidth() + getWorld().getHeight();
        for (Entity entity : getWorld().getEntityList()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (abs(player.getxCenter() - getxCenter()) <= player.getxVision())
                    if (!Objects.equals(player.getGameMode(), "creative") && !player.isDead()) {
                        double distance = World.calculateIntDistance(getxCenter(), getyCenter(), player.getxCenter(), player.getyCenter());
                        if (distance < distanceMin) {
                            this.player = player;
                            distanceMin = distance;
                        }
                    } else if (this.player == player) {
                        this.player = null;
                        stopMoveX();
                        moveTimer = 0;
                        walkTimer = 0;
                    }
            }
        }
    }

    // 更新僵尸移动到指定位置
    public void updateZombieMoveToX(int xPurpose) {
        if (getxCenter() < xPurpose) {
            walkRight();
            if (!isCanRight())
                if (!updateCanJumpWall()) stopMoveX();
        } else if (getxCenter() > xPurpose) {
            walkLeft();
            if (!isCanLeft())
                if (!updateCanJumpWall()) stopMoveX();
        } else if (getxCenter() == xPurpose) {
            stopMoveX();
            onMoving = false;
        }
    }

    // 更新僵尸移动策略
    public void updateZombieMoveStrategy() {
        if (player != null) {
            moveToX = player.getxCenter();
            onMoving = true;
        } else {
            Random random = new Random();
            int randomNumber = random.nextInt(500);
            if (randomNumber == 0) {
                moveToX = getxCenter() + random.nextInt(500) - 250;
                onMoving = true;
            }
        }
    }

    // 更新僵尸能否往上跳
    public boolean updateCanJumpWall() {
        if ((getyCenter() - 25) / 50 - 1 >= 0 && (getyCenter() - 25) / 50 <= getWorld().getHeight() - 1) {
            if (!isCanLeft() && getxCenter() / 50 - 1 >= 0 && getxCenter() / 50 <= getWorld().getWidth() - 1)
                if (IDIndex.blockIdToIsUnTouchable(getWorld().getBlockIdList()[(getyCenter() - 25) / 50][getxCenter() / 50 - 1]) && IDIndex.blockIdToIsUnTouchable(getWorld().getBlockIdList()[(getyCenter() - 25) / 50 - 1][getxCenter() / 50 - 1]) && IDIndex.blockIdToIsUnTouchable(getWorld().getBlockIdList()[(getyCenter() - 25) / 50 - 1][getxCenter() / 50])) {
                    jump();
                    return true;
                }
            if (!isCanRight() && getxCenter() / 50 + 1 <= getWorld().getWidth() - 1 && getxCenter() / 50 >= 0)
                if (IDIndex.blockIdToIsUnTouchable(getWorld().getBlockIdList()[(getyCenter() - 25) / 50][getxCenter() / 50 + 1]) && IDIndex.blockIdToIsUnTouchable(getWorld().getBlockIdList()[(getyCenter() - 25) / 50 - 1][getxCenter() / 50 + 1]) && IDIndex.blockIdToIsUnTouchable(getWorld().getBlockIdList()[(getyCenter() - 25) / 50 - 1][getxCenter() / 50])) {
                    jump();
                    return true;
                }
        }
        return false;
    }

    // 更新僵尸数据
    public void updateZombieData() {
        // 更新僵尸材质
        updateTexture();
        for (Entity entity : getWorld().getEntityList()) {
            if (entity instanceof Player && !((Player) entity).isDummyPlayer()) {
                if (!((Player) entity).getGame().isMultiPlayerMode()) {
                    {
                        if (getWorld().getTime() % 100 == 0)
                            // 更新僵尸选中玩家
                            updatePlayerSelected();
                        // 更新僵尸移动策略
                        updateZombieMoveStrategy();
                        // 如果在移动，播放移动音效
                        playWalkSound();
                        // 更新僵尸攻击CD
                        updateAttackCD();
                        // 僵尸攻击
                        attack();
                        // 更新僵尸运动目的地
                        if (onMoving)
                            updateZombieMoveToX(moveToX);
                        // 更新麻痹状态停止运动
                        updateParalysisAction();
                    }
                }
            }
        }
    }

    public void updateDataToServer() {
        for (Entity entity : getWorld().getEntityList()) {
            if (entity instanceof Player && !((Player) entity).isDummyPlayer()) {
                Player player = (Player) entity;
                if (((Player) entity).getGame().isMultiPlayerMode()) {
                    TCPClient.sendMessageToServer("/updateLocation other " + getIdCode() + " " + getX() + " " + getY() + "\n", player.getName());
                    TCPClient.sendMessageToServer("/updateTimer other " + getIdCode() + " " + getWalkTimer() + " 0 0\n", player.getName());
                    TCPClient.sendMessageToServer("/updateState other " + getIdCode() + " " + getFaceTo() + " " + getMoveState() + "\n", player.getName());
                }
            }
        }
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMoveTimer() {
        return moveTimer;
    }

    public void setMoveTimer(int moveTimer) {
        this.moveTimer = moveTimer;
    }

    public int getWalkTimer() {
        return walkTimer;
    }

    public void setWalkTimer(int walkTimer) {
        this.walkTimer = walkTimer;
    }

    public int getMoveFrequency() {
        return moveFrequency;
    }

    public void setMoveFrequency(int moveFrequency) {
        this.moveFrequency = moveFrequency;
    }

    public int getAttackCD() {
        return attackCD;
    }

    public void setAttackCD(int attackCD) {
        this.attackCD = attackCD;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }
}
