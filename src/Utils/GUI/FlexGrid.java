package Utils.GUI;

import Base.GameFrame;
import Utils.OtherTool.IDIndex;
import Utils.OtherTool.ImageEditor;
import Utils.OtherTool.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static java.lang.Integer.min;

public class FlexGrid {
    private GameFrame game;
    // 定义绘制坐标
    private int x;
    private int y;
    // 定义中心坐标，相对窗口中心
    private double xCenterRatio;
    private double yCenterRatio;
    // 定义宽高比率，相比于屏幕大小
    private double widthRatio;
    private double heightRatio;
    private BufferedImage textureNormal = Resource.getGuiTextureList().get(19);
    private BufferedImage textureChosen = (BufferedImage) ImageEditor.imageStain(Resource.getGuiTextureList().get(19), Color.white, 100);
    private BufferedImage textureCurrent;
    private boolean chosen = false;
    private boolean canDelete = false;
    // 定义不可被按下时间
    private int timeNoPress = 500;
    // 定义当前网格物品id
    private int id = -1;
    // 定义当前网格物品数量
    private int amount = 0;
    // 定义当前网格序号
    private int sort = 0;
    // 定义选中计时器
    private int chosenTimer = 0;

    public FlexGrid() {
    }

    public FlexGrid(GameFrame game, double xCenterRatio, double yCenterRatio, double widthRatio, double heightRatio, int sort, int timeNoPress) {
        this.game = game;
        this.timeNoPress = timeNoPress;
        this.sort = sort;
        this.x = (int) (game.getWidth() * xCenterRatio) + game.getxCenter() - (int) ((game.getWidth() * widthRatio) / 2);
        this.y = (int) (game.getHeight() * yCenterRatio) + game.getyCenter() - (int) ((game.getHeight() * heightRatio) / 2);
        this.xCenterRatio = xCenterRatio;
        this.yCenterRatio = yCenterRatio;
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        textureNormal = (BufferedImage) ImageEditor.imageScale(textureNormal, tmpWidth, tmpHeight);
        textureChosen = (BufferedImage) ImageEditor.imageScale(textureChosen, tmpWidth, tmpHeight);
        textureCurrent = textureNormal;
        updateItemGrid();
    }

    public FlexGrid(GameFrame game, double xCenterRatio, double yCenterRatio, double widthRatio, double heightRatio, int id, int amount, int timeNoPress) {
        this.game = game;
        this.timeNoPress = timeNoPress;
        this.sort = sort;
        this.id = id;
        this.amount = amount;
        this.x = (int) (game.getWidth() * xCenterRatio) + game.getxCenter() - (int) ((game.getWidth() * widthRatio) / 2);
        this.y = (int) (game.getHeight() * yCenterRatio) + game.getyCenter() - (int) ((game.getHeight() * heightRatio) / 2);
        this.xCenterRatio = xCenterRatio;
        this.yCenterRatio = yCenterRatio;
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        textureNormal = (BufferedImage) ImageEditor.imageScale(textureNormal, tmpWidth, tmpHeight);
        textureChosen = (BufferedImage) ImageEditor.imageScale(textureChosen, tmpWidth, tmpHeight);
        textureCurrent = textureNormal;
    }

    // 绘制文字
    public void updateDrawText() {
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        // 绘制文字
        Graphics graphics = game.getOffScreenUiImage().getGraphics();
        if (IDIndex.blockIdToIsTool(id)) {
            int widthResized = (int) (tmpWidth * 0.7);
            int heightResized = (int) (tmpHeight * 0.06);
            int widthOffset = (int) (tmpWidth * 0.15);
            int heightOffset = (int) (tmpHeight * 0.2);
            int barWidth = (int) (widthResized * ((double) amount / IDIndex.blockIdToMaxAmount(id)));
            graphics.setColor(Color.darkGray);
            graphics.fillRect(x + widthOffset, y + tmpHeight - heightOffset, widthResized, heightResized);
            if (barWidth > (widthResized * 2) / 3 && barWidth <= widthResized) graphics.setColor(Color.GREEN);
            else if (barWidth > 11 && barWidth <= (widthResized * 2) / 3) graphics.setColor(Color.ORANGE);
            else if (barWidth >= 0 && barWidth <= widthResized / 3) graphics.setColor(Color.RED);
            graphics.fillRect(x + widthOffset, y + tmpHeight - heightOffset, barWidth, heightResized);
        } else if (amount >= 2) {
            int minValue = min((int) (tmpWidth * 0.7), (int) (tmpHeight * 0.7));
            graphics.setColor(Color.WHITE);
            graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (minValue / 2.3)));
            FontMetrics metrics = graphics.getFontMetrics();
            graphics.drawString(Integer.toString(amount), x + (int) (tmpWidth * 0.74) - metrics.stringWidth(Integer.toString(amount)) / 2, y + (int) (tmpHeight * 0.7 + minValue / 6));
        }
    }

    public void drawGrid() {
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        Graphics graphics = game.getOffScreenUiImage().getGraphics();
        graphics.drawImage(textureCurrent, x, y, game);
        if (id != -1)
            graphics.drawImage(ImageEditor.imageScale(Resource.getItemBarBlockTexture().get(id), (int) (tmpWidth * 0.7), (int) (tmpHeight * 0.7)), x + (int) (tmpWidth * 0.15), y + (int) (tmpHeight * 0.15), game);
    }

    public void updateGrid() {
        int tmpWidth = (int) (game.getWidth() * widthRatio);
        int tmpHeight = (int) (game.getHeight() * heightRatio);
        // 更新坐标位置
        x = (int) (game.getWidth() * xCenterRatio) + game.getxCenter() - tmpWidth / 2;
        y = (int) (game.getHeight() * yCenterRatio) + game.getyCenter() - tmpHeight / 2;
        // 更新材质大小
        textureNormal = (BufferedImage) ImageEditor.imageScale(textureNormal, tmpWidth, tmpHeight);
        textureChosen = (BufferedImage) ImageEditor.imageScale(textureChosen, tmpWidth, tmpHeight);
        // 更新数量
        if (amount == 0) id = -1;
        // 更新按钮判断
        if (timeNoPress > 0) {
            timeNoPress--;
            textureCurrent = textureNormal;
            chosen = false;
        } else {
            if (game.getMouseLocation().getX() > x && game.getMouseLocation().getX() < x + tmpWidth && game.getMouseLocation().getY() > y && game.getMouseLocation().getY() < y + tmpHeight) {
                textureCurrent = textureChosen;
                chosen = true;
            } else {
                textureCurrent = textureNormal;
                chosen = false;
            }
            if (chosen && game.isMouseLeftPressed()) {
                pressLeftFunction();
            } else if (chosen && game.isMouseRightPressed()) {
                pressRightFunction();
            } else if (chosen && game.isMouseMiddlePressed()) {
                pressMiddleFunction();
            }
            if (chosen)
                chosenTimer++;
            else chosenTimer = 0;
        }
        // 如果是负sort
        if (sort <= -3) {
            id = -sort - 3;
            if (IDIndex.blockIdToIsTool(id))
                amount = IDIndex.blockIdToMaxAmount(id);
            else amount = 1;
        }
    }

    public void updateItemBar() {
        if (sort >= 0 && sort <= 35) {
            game.getPlayer().getItemBarAmount()[sort] = amount;
            game.getPlayer().getItemBarId()[sort] = id;
        }
    }

    public void updateItemGrid() {
        if (sort >= 0 && sort <= 35) {
            id = game.getPlayer().getItemBarId()[sort];
            amount = game.getPlayer().getItemBarAmount()[sort];
        }
    }

    public void switchItem() {
        int idTmp = id;
        int amountTmp = amount;
        amount = game.getItemAttachAmount();
        id = game.getItemAttachId();
        game.setItemAttachAmount(amountTmp);
        game.setItemAttachId(idTmp);
        updateItemBar();
        if (game.isMultiPlayerMode())
            game.getWorldCurrent().updatePlayerItemDataToServer(game.getPlayer());
    }

    public void pressMiddleFunction() {
        if (Objects.equals(game.getPlayer().getGameMode(), "creative")) {
            if (id != -1 && game.getItemAttachId() == -1) {
                game.setItemAttachAmount(IDIndex.blockIdToMaxAmount(id));
                game.setItemAttachId(id);
            }
        }
    }

    public void pressLeftFunction() {
        for (FlexGrid flexGrid : game.getFlexGridList()) {
            flexGrid.setTimeNoPress(20);
        }
        if (sort >= -1) {
            if (!game.isShiftPressed() && !game.isCtrlPressed()) {
                if ((sort >= 36 && sort <= 39 && IDIndex.blockIdToIsArmor(game.getItemAttachId())) || sort < 36 || (sort > 40 && sort != 50)) {
                    if (game.getItemAttachId() != id) {
                        switchItem();
                    } else if (sort >= -1 && id != -1 && !IDIndex.blockIdToIsTool(id) && !IDIndex.blockIdToIsArmor(id)) {
                        if (game.getItemAttachAmount() + amount <= IDIndex.blockIdToMaxAmount(id)) {
                            amount += game.getItemAttachAmount();
                            game.setItemAttachAmount(0);
                            game.setItemAttachId(-1);
                            updateItemBar();
                        } else {
                            game.setItemAttachAmount(game.getItemAttachAmount() - (IDIndex.blockIdToMaxAmount(id) - amount));
                            amount = IDIndex.blockIdToMaxAmount(id);
                            updateItemBar();
                        }
                    }
                } else if (sort == 50) {
                    if (!game.isShiftPressed()) {
                        if (game.getItemAttachId() == -1 && id != -1) {
                            game.setItemAttachAmount(amount);
                            game.setItemAttachId(id);
                            amount = 0;
                            id = -1;
                            game.setCrafted(true);
                        } else if (id != -1 && game.getItemAttachId() == id) {
                            if (game.getItemAttachAmount() + amount <= IDIndex.blockIdToMaxAmount(id)) {
                                game.setItemAttachAmount(game.getItemAttachAmount() + amount);
                                amount = 0;
                                id = -1;
                                game.setCrafted(true);
                            }
                        }
                    }
                }
            } else if (game.isShiftPressed() && !game.isCtrlPressed() && id != -1) {
                if (sort >= 0 && sort <= 8) {
                    // 定义还未被捡完的掉落物数量
                    int amountLeft = amount;
                    // 如果不是工具
                    if (!IDIndex.blockIdToIsTool(id)) {
                        // 搜索背包内是否已经存在该物品
                        for (int i = 9; i < 36; i++)
                            // 如果存在
                            if (game.getPlayer().getItemBarId()[i] == id) {
                                // 如果物品数量小于最大堆叠数
                                if (game.getPlayer().getItemBarAmount()[i] < IDIndex.blockIdToMaxAmount(id)) {
                                    // 如果物品数量加上全部物品多于最大堆叠数
                                    if (game.getPlayer().getItemBarAmount()[i] + amountLeft > IDIndex.blockIdToMaxAmount(id)) {
                                        // 掉落物剩余数量扣除已经捡走的数量
                                        amountLeft -= (IDIndex.blockIdToMaxAmount(id) - game.getPlayer().getItemBarAmount()[i]);
                                        // 该物品堆叠达到上限，设为最大堆叠数
                                        game.getPlayer().getItemBarAmount()[i] = IDIndex.blockIdToMaxAmount(id);
                                    } else {
                                        // 否则该物品直接堆叠全部掉落物数量
                                        game.getPlayer().getItemBarAmount()[i] += amountLeft;
                                        // 掉落物剩余数量扣除已经捡走的数量
                                        amountLeft = 0;
                                        // 退出循环
                                        break;
                                    }
                                }
                            }
                        if (amountLeft == 0) {
                            id = -1;
                            amount = 0;
                            game.getPlayer().getItemBarId()[sort] = -1;
                            game.getPlayer().getItemBarAmount()[sort] = 0;
                        } else {
                            amount = amountLeft;
                            for (int j = 9; j < 36; j++)
                                if (game.getPlayer().getItemBarId()[j] == -1) {
                                    game.getPlayer().getItemBarId()[j] = id;
                                    game.getPlayer().getItemBarAmount()[j] = amount;
                                    game.getPlayer().getItemBarId()[sort] = -1;
                                    game.getPlayer().getItemBarAmount()[sort] = 0;
                                    break;
                                }
                        }
                    } else {
                        for (int i = 9; i < 36; i++)
                            if (game.getPlayer().getItemBarId()[i] == -1) {
                                game.getPlayer().getItemBarId()[i] = id;
                                game.getPlayer().getItemBarAmount()[i] = amount;
                                game.getPlayer().getItemBarId()[sort] = -1;
                                game.getPlayer().getItemBarAmount()[sort] = 0;
                                break;
                            }
                    }
                    for (FlexGrid flexGrid : game.getFlexGridList()) {
                        flexGrid.updateItemGrid();
                    }
                } else if (sort >= 9 && sort <= 35) {
                    // 定义还未被捡完的掉落物数量
                    int amountLeft = amount;
                    // 如果不是工具
                    if (!IDIndex.blockIdToIsTool(id)) {
                        // 搜索背包内是否已经存在该物品
                        for (int i = 0; i < 9; i++)
                            // 如果存在
                            if (game.getPlayer().getItemBarId()[i] == id) {
                                // 如果物品数量小于最大堆叠数
                                if (game.getPlayer().getItemBarAmount()[i] < IDIndex.blockIdToMaxAmount(id)) {
                                    // 如果物品数量加上全部物品多于最大堆叠数
                                    if (game.getPlayer().getItemBarAmount()[i] + amountLeft > IDIndex.blockIdToMaxAmount(id)) {
                                        // 掉落物剩余数量扣除已经捡走的数量
                                        amountLeft -= (IDIndex.blockIdToMaxAmount(id) - game.getPlayer().getItemBarAmount()[i]);
                                        // 该物品堆叠达到上限，设为最大堆叠数
                                        game.getPlayer().getItemBarAmount()[i] = IDIndex.blockIdToMaxAmount(id);
                                    } else {
                                        // 否则该物品直接堆叠全部掉落物数量
                                        game.getPlayer().getItemBarAmount()[i] += amountLeft;
                                        // 掉落物剩余数量扣除已经捡走的数量
                                        amountLeft = 0;
                                        // 退出循环
                                        break;
                                    }
                                }
                            }
                        if (amountLeft == 0) {
                            id = -1;
                            amount = 0;
                            game.getPlayer().getItemBarId()[sort] = -1;
                            game.getPlayer().getItemBarAmount()[sort] = 0;
                        } else {
                            amount = amountLeft;
                            for (int j = 0; j < 9; j++)
                                if (game.getPlayer().getItemBarId()[j] == -1) {
                                    game.getPlayer().getItemBarId()[j] = id;
                                    game.getPlayer().getItemBarAmount()[j] = amount;
                                    game.getPlayer().getItemBarId()[sort] = -1;
                                    game.getPlayer().getItemBarAmount()[sort] = 0;
                                    break;
                                }
                        }
                    } else {
                        for (int i = 0; i < 9; i++)
                            if (game.getPlayer().getItemBarId()[i] == -1) {
                                game.getPlayer().getItemBarId()[i] = id;
                                game.getPlayer().getItemBarAmount()[i] = amount;
                                game.getPlayer().getItemBarId()[sort] = -1;
                                game.getPlayer().getItemBarAmount()[sort] = 0;
                                break;
                            }
                    }
                    for (FlexGrid flexGrid : game.getFlexGridList()) {
                        flexGrid.updateItemGrid();
                    }
                } else if (sort >= 41 && sort <= 49) {
                    // 定义还未被捡完的掉落物数量
                    int amountLeft = amount;
                    // 如果不是工具
                    if (!IDIndex.blockIdToIsTool(id)) {
                        // 搜索背包内是否已经存在该物品
                        for (int i = 9; i < 36; i++)
                            // 如果存在
                            if (game.getPlayer().getItemBarId()[i] == id) {
                                // 如果物品数量小于最大堆叠数
                                if (game.getPlayer().getItemBarAmount()[i] < IDIndex.blockIdToMaxAmount(id)) {
                                    // 如果物品数量加上全部物品多于最大堆叠数
                                    if (game.getPlayer().getItemBarAmount()[i] + amountLeft > IDIndex.blockIdToMaxAmount(id)) {
                                        // 掉落物剩余数量扣除已经捡走的数量
                                        amountLeft -= (IDIndex.blockIdToMaxAmount(id) - game.getPlayer().getItemBarAmount()[i]);
                                        // 该物品堆叠达到上限，设为最大堆叠数
                                        game.getPlayer().getItemBarAmount()[i] = IDIndex.blockIdToMaxAmount(id);
                                    } else {
                                        // 否则该物品直接堆叠全部掉落物数量
                                        game.getPlayer().getItemBarAmount()[i] += amountLeft;
                                        // 掉落物剩余数量扣除已经捡走的数量
                                        amountLeft = 0;
                                        // 退出循环
                                        break;
                                    }
                                }
                            }
                        if (amountLeft != 0)
                            for (int i = 0; i < 9; i++)
                                // 如果存在
                                if (game.getPlayer().getItemBarId()[i] == id) {
                                    // 如果物品数量小于最大堆叠数
                                    if (game.getPlayer().getItemBarAmount()[i] < IDIndex.blockIdToMaxAmount(id)) {
                                        // 如果物品数量加上全部物品多于最大堆叠数
                                        if (game.getPlayer().getItemBarAmount()[i] + amountLeft > IDIndex.blockIdToMaxAmount(id)) {
                                            // 掉落物剩余数量扣除已经捡走的数量
                                            amountLeft -= (IDIndex.blockIdToMaxAmount(id) - game.getPlayer().getItemBarAmount()[i]);
                                            // 该物品堆叠达到上限，设为最大堆叠数
                                            game.getPlayer().getItemBarAmount()[i] = IDIndex.blockIdToMaxAmount(id);
                                        } else {
                                            // 否则该物品直接堆叠全部掉落物数量
                                            game.getPlayer().getItemBarAmount()[i] += amountLeft;
                                            // 掉落物剩余数量扣除已经捡走的数量
                                            amountLeft = 0;
                                            // 退出循环
                                            break;
                                        }
                                    }
                                }
                        if (amountLeft == 0) {
                            id = -1;
                            amount = 0;
                        } else {
                            boolean finished = false;
                            for (int j = 9; j < 36; j++)
                                if (game.getPlayer().getItemBarId()[j] == -1) {
                                    game.getPlayer().getItemBarId()[j] = id;
                                    game.getPlayer().getItemBarAmount()[j] = amountLeft;
                                    finished = true;
                                    break;
                                }
                            if (!finished)
                                for (int j = 0; j < 9; j++)
                                    if (game.getPlayer().getItemBarId()[j] == -1) {
                                        game.getPlayer().getItemBarId()[j] = id;
                                        game.getPlayer().getItemBarAmount()[j] = amountLeft;
                                        break;
                                    }
                            amount = 0;
                            id = -1;
                        }
                    } else {
                        boolean finished = false;
                        for (int i = 0; i < 36; i++)
                            if (game.getPlayer().getItemBarId()[i] == -1) {
                                game.getPlayer().getItemBarId()[i] = id;
                                game.getPlayer().getItemBarAmount()[i] = amount;
                                finished = true;
                                break;
                            }
                        if (!finished)
                            for (int j = 0; j < 9; j++)
                                if (game.getPlayer().getItemBarId()[j] == -1) {
                                    game.getPlayer().getItemBarId()[j] = id;
                                    game.getPlayer().getItemBarAmount()[j] = amountLeft;
                                    break;
                                }
                        amount = 0;
                        id = -1;
                    }
                    for (FlexGrid flexGrid : game.getFlexGridList()) {
                        flexGrid.updateItemGrid();
                    }
                } else if (sort == 50) {
                    game.setAllCrafted(true);
                }

            } else if (game.isCtrlPressed()) {
                if (sort >= 0 && sort <= 35) {
                    if (id != -1) {
                        // 如果不是工具
                        if (!IDIndex.blockIdToIsTool(id)) {
                            // 搜索背包内是否已经存在该物品
                            for (int i = 35; i >= 0; i--) {
                                if (i == sort) continue;
                                // 如果存在
                                if (game.getPlayer().getItemBarId()[i] == id) {
                                    if (game.getPlayer().getItemBarAmount()[i] == IDIndex.blockIdToMaxAmount(id))
                                        continue;
                                    // 如果物品数量小于最大堆叠数
                                    if (game.getPlayer().getItemBarAmount()[i] + game.getPlayer().getItemBarAmount()[sort] <
                                            IDIndex.blockIdToMaxAmount(id)) {
                                        game.getPlayer().getItemBarAmount()[sort] += game.getPlayer().getItemBarAmount()[i];
                                        game.getPlayer().getItemBarAmount()[i] = 0;
                                        game.getPlayer().getItemBarId()[i] = -1;
                                    } else {
                                        game.getPlayer().getItemBarAmount()[sort] = IDIndex.blockIdToMaxAmount(id);
                                        game.getPlayer().getItemBarAmount()[i] -= IDIndex.blockIdToMaxAmount(id) - game.getPlayer().getItemBarAmount()[sort];
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    for (FlexGrid flexGrid : game.getFlexGridList()) {
                        flexGrid.updateItemGrid();
                    }
                } else if (sort >= 41 && sort <= 49) {
                    if (id != -1) {
                        // 如果不是工具
                        if (!IDIndex.blockIdToIsTool(id)) {
                            // 搜索合成框内是否已经存在该物品
                            for (FlexGrid flexGrid : game.getFlexGridList()) {
                                if (flexGrid.getSort() == sort || !(flexGrid.getSort() >= 41 && flexGrid.getSort() <= 49))
                                    continue;
                                // 如果存在
                                if (flexGrid.getId() == id) {
                                    if (flexGrid.getAmount() == IDIndex.blockIdToMaxAmount(id))
                                        continue;
                                    // 如果物品数量小于最大堆叠数
                                    if (flexGrid.getAmount() + amount < IDIndex.blockIdToMaxAmount(id)) {
                                        amount += flexGrid.getAmount();
                                        flexGrid.setAmount(0);
                                        flexGrid.setId(-1);
                                    } else {
                                        flexGrid.setAmount(flexGrid.getAmount() - (IDIndex.blockIdToMaxAmount(id) - amount));
                                        amount = IDIndex.blockIdToMaxAmount(id);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (sort == -2) {
                game.setItemAttachAmount(0);
                game.setItemAttachId(-1);
            } else if (!game.isShiftPressed()) {
                if (game.getItemAttachId() == -1) {
                    if (IDIndex.blockIdToIsTool(id))
                        game.setItemAttachAmount(IDIndex.blockIdToMaxAmount(id));
                    else game.setItemAttachAmount(1);
                    game.setItemAttachId(id);
                } else if (game.getItemAttachId() == id) {
                    if (IDIndex.blockIdToIsTool(id))
                        game.setItemAttachAmount(IDIndex.blockIdToMaxAmount(id));
                    else if (game.getItemAttachAmount() + 1 <= IDIndex.blockIdToMaxAmount(id))
                        game.setItemAttachAmount(game.getItemAttachAmount() + 1);
                } else {
                    game.setItemAttachAmount(0);
                    game.setItemAttachId(-1);
                }
            } else {
                // 定义还未被捡完的掉落物数量
                int amountLeft = IDIndex.blockIdToMaxAmount(id);
                boolean itemExist = false;
                // 如果不是工具
                if (!IDIndex.blockIdToIsTool(id)) {
                    // 搜索背包内是否已经存在该物品
                    for (int i = 0; i < 36; i++)
                        // 如果存在
                        if (game.getPlayer().getItemBarId()[i] == id) {
                            // 如果物品数量小于最大堆叠数
                            if (game.getPlayer().getItemBarAmount()[i] < IDIndex.blockIdToMaxAmount(id)) {
                                itemExist = true;
                                // 如果物品数量加上全部物品多于最大堆叠数
                                if (game.getPlayer().getItemBarAmount()[i] + amountLeft > IDIndex.blockIdToMaxAmount(id)) {
                                    // 掉落物剩余数量扣除已经捡走的数量
                                    amountLeft -= (IDIndex.blockIdToMaxAmount(id) - game.getPlayer().getItemBarAmount()[i]);
                                    // 该物品堆叠达到上限，设为最大堆叠数
                                    game.getPlayer().getItemBarAmount()[i] = IDIndex.blockIdToMaxAmount(id);
                                } else {
                                    // 否则该物品直接堆叠全部掉落物数量
                                    game.getPlayer().getItemBarAmount()[i] += amountLeft;
                                    // 退出循环
                                    break;
                                }
                            }
                        }
                    if (!itemExist) {
                        for (int j = 0; j < 36; j++)
                            if (game.getPlayer().getItemBarId()[j] == -1) {
                                game.getPlayer().getItemBarId()[j] = id;
                                game.getPlayer().getItemBarAmount()[j] = IDIndex.blockIdToMaxAmount(id);
                                break;
                            }
                    }
                } else {
                    for (int i = 0; i < 36; i++)
                        if (game.getPlayer().getItemBarId()[i] == -1) {
                            game.getPlayer().getItemBarId()[i] = id;
                            game.getPlayer().getItemBarAmount()[i] = IDIndex.blockIdToMaxAmount(id);
                            break;
                        }
                }
                for (FlexGrid flexGrid : game.getFlexGridList()) {
                    flexGrid.updateItemGrid();
                }
            }
        }
    }

    public void pressRightFunction() {
        for (FlexGrid flexGrid : game.getFlexGridList()) {
            flexGrid.setTimeNoPress(20);
        }
        if (sort >= -1) {
            if ((sort >= 36 && sort <= 39 && IDIndex.blockIdToIsArmor(game.getItemAttachId())) || sort < 36 || (sort > 40 && sort != 50)) {
                if (game.getItemAttachId() != id) {
                    if (game.getItemAttachId() == -1 && id != -1) {
                        if (!IDIndex.blockIdToIsTool(id) && !IDIndex.blockIdToIsArmor(id)) {
                            int amountTmp = amount - amount / 2;
                            amount -= amountTmp;
                            game.setItemAttachAmount(amountTmp);
                            game.setItemAttachId(id);
                            if (amount == 0) id = -1;
                            updateItemBar();
                        } else switchItem();
                    } else if (game.getItemAttachId() != -1 && id == -1) {
                        if (!IDIndex.blockIdToIsTool(game.getItemAttachId()) && !IDIndex.blockIdToIsArmor(id)) {
                            if (game.getItemAttachAmount() >= 2 && amount < IDIndex.blockIdToMaxAmount(game.getItemAttachId())) {
                                amount += 1;
                                if (amount == 1)
                                    id = game.getItemAttachId();
                                game.setItemAttachAmount(game.getItemAttachAmount() - 1);
                                updateItemBar();
                            } else if (game.getItemAttachAmount() == 1) {
                                amount = 1;
                                id = game.getItemAttachId();
                                game.setItemAttachAmount(0);
                                game.setItemAttachId(-1);
                                updateItemBar();
                            }
                        } else switchItem();
                    } else switchItem();
                } else if (id != -1 && !IDIndex.blockIdToIsTool(id) && !IDIndex.blockIdToIsArmor(id)) {
                    if (amount + 1 <= IDIndex.blockIdToMaxAmount(id)) {
                        amount++;
                        game.setItemAttachAmount(game.getItemAttachAmount() - 1);
                        updateItemBar();
                    }
                }
            } else if (sort == 50) {
                if (game.getItemAttachId() == -1 && id != -1) {
                    game.setItemAttachAmount(amount);
                    game.setItemAttachId(id);
                    amount = 0;
                    id = -1;
                    game.setCrafted(true);
                } else if (id != -1 && game.getItemAttachId() == id) {
                    if (game.getItemAttachAmount() + amount <= IDIndex.blockIdToMaxAmount(id)) {
                        game.setItemAttachAmount(game.getItemAttachAmount() + amount);
                        amount = 0;
                        id = -1;
                        game.setCrafted(true);
                    }
                }
            }
        } else {
            if (sort == -2) {
                game.setItemAttachAmount(0);
                game.setItemAttachId(-1);
            } else {
                if (game.getItemAttachId() == -1) {
                    if (IDIndex.blockIdToIsTool(id))
                        game.setItemAttachAmount(IDIndex.blockIdToMaxAmount(id));
                    else game.setItemAttachAmount(1);
                    game.setItemAttachId(id);
                } else if (game.getItemAttachId() == id) {
                    if (IDIndex.blockIdToIsTool(id))
                        game.setItemAttachAmount(IDIndex.blockIdToMaxAmount(id));
                    else if (game.getItemAttachAmount() + 1 <= IDIndex.blockIdToMaxAmount(id))
                        game.setItemAttachAmount(game.getItemAttachAmount() + 1);
                } else {
                    game.setItemAttachAmount(0);
                    game.setItemAttachId(-1);
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getWidthRatio() {
        return widthRatio;
    }

    public void setWidthRatio(int widthRatio) {
        this.widthRatio = widthRatio;
    }

    public double getHeightRatio() {
        return heightRatio;
    }

    public void setHeightRatio(int heightRatio) {
        this.heightRatio = heightRatio;
    }

    public GameFrame getGame() {
        return game;
    }

    public void setGame(GameFrame game) {
        this.game = game;
    }

    public BufferedImage getTextureNormal() {
        return textureNormal;
    }

    public void setTextureNormal(BufferedImage textureNormal) {
        this.textureNormal = textureNormal;
    }

    public BufferedImage getTextureChosen() {
        return textureChosen;
    }

    public void setTextureChosen(BufferedImage textureChosen) {
        this.textureChosen = textureChosen;
    }

    public BufferedImage getTextureCurrent() {
        return textureCurrent;
    }

    public void setTextureCurrent(BufferedImage textureCurrent) {
        this.textureCurrent = textureCurrent;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public double getxCenterRatio() {
        return xCenterRatio;
    }

    public void setxCenterRatio(double xCenterRatio) {
        this.xCenterRatio = xCenterRatio;
    }

    public double getyCenterRatio() {
        return yCenterRatio;
    }

    public void setyCenterRatio(double yCenterRatio) {
        this.yCenterRatio = yCenterRatio;
    }

    public void setWidthRatio(double widthRatio) {
        this.widthRatio = widthRatio;
    }

    public void setHeightRatio(double heightRatio) {
        this.heightRatio = heightRatio;
    }

    public int getTimeNoPress() {
        return timeNoPress;
    }

    public void setTimeNoPress(int timeNoPress) {
        this.timeNoPress = timeNoPress;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getChosenTimer() {
        return chosenTimer;
    }

    public void setChosenTimer(int chosenTimer) {
        this.chosenTimer = chosenTimer;
    }
}
