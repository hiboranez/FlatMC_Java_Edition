package EntityType;

import Base.World;
import Element.Entity;
import Utils.OtherTool.Resource;
import Utils.TCP.TCPClient;

import static java.lang.Math.abs;

public class Item extends Entity {
    // **定义掉落物ID
    private int id;
    // 定义掉落物物品堆叠数量
    private int amount = 1;
    // 定义掉落物悬浮时间
    private int timeSuspend = 0;
    // 定义掉落物悬浮偏差值(-5 + -3~3)
    private int suspendY = -10;
    // 定义掉落物被拾取范围
    private int collectRange = 10;
    // 定义掉落物被吸附速度
    private double attractSpeedX = 0;
    private double attractSpeedY = 0;
    // 定义掉落物不可被拾取时间
    private int timeNoCollect = 500;
    private boolean onGroundLast = false;

    public Item() {
        // 设置实体类型为掉落物
        setType("item");
        // 设置掉落物大小
        setWidth(20);
        setHeight(20);
        // 设置实体大小修正值
        setxLeftRevision(3);
        setxRightRevision(22);
        setyUpRevision(0);
        setyDownRevision(getHeight());
        // 设置掉落物无敌
        setCanHurt(false);
    }

    public Item(int x, int y, int id, int amount, World world, int timeNoCollect) {
        // 设置实体类型为掉落物
        setType("item");
        // 设置掉落物大小
        setWidth(20);
        setHeight(20);
        // 设置实体大小修正值
        setxLeftRevision(3);
        setxRightRevision(22);
        setyUpRevision(0);
        setyDownRevision(getHeight());
        // 设置掉落物位置
        setX(x);
        setY(y);
        // 设置掉落物id
        this.id = id;
        // 设置掉落物数量
        this.amount = amount;
        // 设置掉落物所处世界
        setWorld(world);
        // 设置掉落物材质包
        getTextureList().add(Resource.getItemTextureList().get(id));
        getTextureList().add(Resource.getItemDoubleTextureList().get(id));
        // 设置掉落物当前材质
        if (amount == 1) setTextureCurrent(getTextureList().get(0));
        if (amount > 1) setTextureCurrent(getTextureList().get(1));
        // 设置掉落物无敌
        setCanHurt(false);
        // 设置掉落物不可被拾取时间
        this.timeNoCollect = timeNoCollect;
    }

    public Item(int idCode, int x, int y, int id, int amount, World world, int timeNoCollect) {
        // 设置实体编号
        setIdCode(idCode);
        // 设置实体类型为掉落物
        setType("item");
        // 设置掉落物大小
        setWidth(20);
        setHeight(20);
        // 设置实体大小修正值
        setxLeftRevision(3);
        setxRightRevision(22);
        setyUpRevision(0);
        setyDownRevision(getHeight());
        // 设置掉落物位置
        setX(x);
        setY(y);
        // 设置掉落物id
        this.id = id;
        // 设置掉落物数量
        this.amount = amount;
        // 设置掉落物所处世界
        setWorld(world);
        // 设置掉落物材质包
        if (id >= 0) {
            getTextureList().add(Resource.getItemTextureList().get(id));
            getTextureList().add(Resource.getItemDoubleTextureList().get(id));
        }
        // 设置掉落物当前材质
        if (amount == 1) setTextureCurrent(getTextureList().get(0));
        if (amount > 1) setTextureCurrent(getTextureList().get(1));
        // 设置掉落物无敌
        setCanHurt(false);
        // 设置掉落物不可被拾取时间
        this.timeNoCollect = timeNoCollect;
    }

    // 重写更新实体纵坐标
    @Override
    public void updateY() {
        Boolean multiPlayerMode = false;
        for (Entity entity : getWorld().getEntityList()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (player.getGame() != null && player.getGame().isMultiPlayerMode())
                    multiPlayerMode = true;
            }
        }
        if (!multiPlayerMode) {
            // 如果在方块内
            if (isInBlock()) {
                setY(getY() - 25);
                for (Entity entity : getWorld().getEntityList()) {
                    if (entity instanceof Player && !((Player) entity).isDummyPlayer()) {
                        Player player = (Player) entity;
                        if (((Player) entity).getGame().isMultiPlayerMode()) {
                            TCPClient.sendMessageToServer("/updateLocation other " + getIdCode() + " " + getX() + " " + getY() + "\n", player.getName());
                        }
                    }
                }
            } else if (isHasGravity() && !isOnGround()) {
                // 如果受重力影响且没有落地
                // 未在方块上，下落
                moveY(getyDown(), getEntityDownGroundY());
                setFallSpeed(abs(getySpeed()));
                if (abs(getySpeed()) + getWorld().getGravity() < getSpeedMax())
                    setySpeed(getySpeed() + getWorld().getGravity());
                // 悬浮偏差值设为-10
                suspendY = -10;
            }
            // 否则悬浮
            else if (getxSpeed() == 0) suspend();
        }
    }

    // 掉落物悬浮
    public void suspend() {
        suspendY = (int) (Math.sin(Math.toRadians(timeSuspend)) * 8) - 10;
        timeSuspend += 3;
    }

    // 更新掉落物数据
    public void updateItemData() {
        // 更新不可被拾取时间
        if (timeNoCollect > 0) {
            timeNoCollect--;
        }
    }

    public void updateDataToServer() {
        for (Entity entity : getWorld().getEntityList()) {
            if (entity instanceof Player && !((Player) entity).isDummyPlayer()) {
                Player player = (Player) entity;
                if (((Player) entity).getGame().isMultiPlayerMode()) {
                    TCPClient.sendMessageToServer("/updateLocation other " + getIdCode() + " " + getX() + " " + getY() + "\n", player.getName());
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTimeSuspend() {
        return timeSuspend;
    }

    public void setTimeSuspend(int timeSuspend) {
        this.timeSuspend = timeSuspend;
    }

    public int getSuspendY() {
        return suspendY;
    }

    public void setSuspendY(int suspendY) {
        this.suspendY = suspendY;
    }

    public int getCollectRange() {
        return collectRange;
    }

    public void setCollectRange(int collectRange) {
        this.collectRange = collectRange;
    }

    public double getAttractSpeedX() {
        return attractSpeedX;
    }

    public void setAttractSpeedX(double attractSpeedX) {
        this.attractSpeedX = attractSpeedX;
    }

    public double getAttractSpeedY() {
        return attractSpeedY;
    }

    public void setAttractSpeedY(double attractSpeedY) {
        this.attractSpeedY = attractSpeedY;
    }

    public int getTimeNoCollect() {
        return timeNoCollect;
    }

    public void setTimeNoCollect(int timeNoCollect) {
        this.timeNoCollect = timeNoCollect;
    }
}
