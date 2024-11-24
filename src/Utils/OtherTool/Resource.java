package Utils.OtherTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Resource {
    // 储存材质文件根目录
    private static String pathTexture = System.getProperty("user.dir") + "/data/resource/";
    // 储存音效文件路径
    private static String pathSound = System.getProperty("user.dir") + "/data/resource/sound/";
    // 储存世界材质
    private static List<BufferedImage> worldTextureList = new ArrayList<>();
    // 储存方块材质
    private static List<BufferedImage> blockTextureList = new ArrayList<>();
    // 储存玩家材质
    private static List<BufferedImage> playerTextureList = new ArrayList<>();
    // 储存GUI材质
    private static List<BufferedImage> guiTextureList = new ArrayList<>();
    // 储存物品栏方块材质
    private static List<BufferedImage> itemBarBlockTexture = new ArrayList<>();
    // 储存单个掉落物材质
    private static List<BufferedImage> itemTextureList = new ArrayList<>();
    // 储存多个掉落物材质
    private static List<BufferedImage> itemDoubleTextureList = new ArrayList<>();
    // 储存僵尸材质
    protected static List<BufferedImage> zombieTextureList = new ArrayList<>();
    // 储存字体文件
    private static List<Font> fontList = new ArrayList<>();

    public static void init() {
        loadGuiTexture();
        loadFont();
        loadItemBarBlockTexture();
        loadWorldTexture();
        loadBlockTexture();
        loadPlayerTexture();
        loadItemTexture();
        loadItemDoubleTexture();
        loadZombieTexture();
    }

    // 加载字体
    public static void loadFont() {
        try {
            fontList.add(Font.createFont(Font.TRUETYPE_FONT, new File(pathTexture + "font/MinecraftAE.ttf")));//0
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    // 加载世界材质
    public static void loadWorldTexture() {
        try {
            worldTextureList.add(ImageIO.read(new File(pathTexture + "/texture/background/" + "Author.png")));//0
            worldTextureList.add(ImageIO.read(new File(pathTexture + "/texture/background/" + "Sky.png")));//1
            worldTextureList.add(ImageIO.read(new File(pathTexture + "/texture/background/" + "Star.png")));//2
            worldTextureList.add(ImageIO.read(new File(pathTexture + "/texture/background/" + "Stone_Wall.png")));//3
            worldTextureList.add(ImageIO.read(new File(pathTexture + "/texture/background/" + "Dirt_Wall.png")));//4
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 加载方块材质
    public static void loadBlockTexture() {
        try {
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Dirt.png")));//0
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Grass_Block.png")));//1
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Stone.png")));//2
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Bedrock.png")));//3
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Cobble_Stone.png")));//4
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Oak.png")));//5
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Plank.png")));//6
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Leaves.png")));//7
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Crafting_Table.png")));//8
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Wooden_Sword.png")));//9
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Wooden_Pickaxe.png")));//10
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Wooden_Axe.png")));//11
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Wooden_Shovel.png")));//12
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Stone_Sword.png")));//13
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Stone_Pickaxe.png")));//14
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Stone_Axe.png")));//15
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Stone_Shovel.png")));//16
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Torch.png")));//17
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Stick.png")));//18
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Coal_Ore.png")));//19
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Iron_Ore.png")));//20
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Gold_Ore.png")));//21
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Diamond_Ore.png")));//22
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Coal.png")));//23
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Iron_Ingot.png")));//24
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Gold_Ingot.png")));//25
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Diamond.png")));//26
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Iron_Block.png")));//27
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Gold_Block.png")));//28
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Diamond_Block.png")));//29
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Iron_Sword.png")));//30
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Iron_Pickaxe.png")));//31
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Iron_Axe.png")));//32
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Iron_Shovel.png")));//33
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Golden_Sword.png")));//34
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Golden_Pickaxe.png")));//35
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Golden_Axe.png")));//36
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Golden_Shovel.png")));//37
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Diamond_Sword.png")));//38
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Diamond_Pickaxe.png")));//39
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Diamond_Axe.png")));//40
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Diamond_Shovel.png")));//41
            blockTextureList.add(ImageIO.read(new File(pathTexture + "/texture/block/" + "Rotten_Flesh.png")));//42
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 加载玩家材质
    public static void loadPlayerTexture() {
        try {
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_FaceLeft.png")));//0
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_FaceRight.png")));//1
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ReadyLeft1.png")));//2
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ReadyLeft2.png")));//3
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ReadyRight1.png")));//4
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ReadyRight2.png")));//5
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_WalkLeft1.png")));//6
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_WalkLeft2.png")));//7
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_WalkRight1.png")));//8
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_WalkRight2.png")));//9
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_RunLeft1.png")));//10
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_RunLeft2.png")));//11
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_RunRight1.png")));//12
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_RunRight2.png")));//13
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ClickLeft1.png")));//14
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ClickLeft2.png")));//15
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ClickLeft3.png")));//16
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ClickRight1.png")));//17
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ClickRight2.png")));//18
            playerTextureList.add(ImageIO.read(new File(pathTexture + "/texture/player/" + "/Steve_ClickRight3.png")));//19
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 加载GUI材质
    public static void loadGuiTexture() {
        try {
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Item_Bar.png")));//0
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Selected_Plot.png")));//0
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Empty_Heart.png")));//2
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Full_Heart.png")));//3
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Half_Heart.png")));//4
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Selected_Block.png")));//5
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Destroy1.png")));//6
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Destroy2.png")));//7
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Destroy3.png")));//8
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Destroy4.png")));//9
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Destroy5.png")));//10
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Destroy6.png")));//11
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Destroy7.png")));//12
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Destroy8.png")));//13
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Button_Normal.png")));//14
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Button_Chosen.png")));//15
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Button_NoPress.png")));//16
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Heart_Flash.png")));//17
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Panel.png")));//18
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Grid.png")));//19
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Arrow_Panel.png")));//20
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Black.png")));//21
            guiTextureList.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Steve_Front.png")));//22
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 加载物品栏方块材质
    public static void loadItemBarBlockTexture() {
        try {
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Dirt.png")));//0
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Grass_Block.png")));//1
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Stone.png")));//2
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "/Bedrock.png")));//3
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Cobble_Stone.png")));//4
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Oak.png")));//5
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Plank.png")));//6
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Leaves.png")));//7
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Crafting_Table.png")));//8
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Wooden_Sword.png")));//9
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Wooden_Pickaxe.png")));//10
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Wooden_Axe.png")));//11
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Wooden_Shovel.png")));//12
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Stone_Sword.png")));//13
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Stone_Pickaxe.png")));//14
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Stone_Axe.png")));//15
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Stone_Shovel.png")));//16
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Torch.png")));//17
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Stick.png")));//18
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Coal_Ore.png")));//19
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Iron_Ore.png")));//20
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Gold_Ore.png")));//21
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Diamond_Ore.png")));//22
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Coal.png")));//23
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Iron_Ingot.png")));//24
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Gold_Ingot.png")));//25
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Diamond.png")));//26
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Iron_Block.png")));//27
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Gold_Block.png")));//28
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Diamond_Block.png")));//29
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Iron_Sword.png")));//30
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Iron_Pickaxe.png")));//31
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Iron_Axe.png")));//32
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Iron_Shovel.png")));//33
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Golden_Sword.png")));//34
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Golden_Pickaxe.png")));//35
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Golden_Axe.png")));//36
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Golden_Shovel.png")));//37
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Diamond_Sword.png")));//38
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Diamond_Pickaxe.png")));//39
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Diamond_Axe.png")));//40
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Diamond_Shovel.png")));//41
            itemBarBlockTexture.add(ImageIO.read(new File(pathTexture + "/texture/gui/" + "Rotten_Flesh.png")));//42
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 加载单个掉落物材质
    public static void loadItemTexture() {
        try {
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "/Dirt.png")));//0
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "/Grass_Block.png")));//1
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "/Stone.png")));//2
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "/Bedrock.png")));//3
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Cobble_Stone.png")));//4
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Oak.png")));//5
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Plank.png")));//6
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Leaves.png")));//7
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Crafting_Table.png")));//8
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Wooden_Sword.png")));//9
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Wooden_Pickaxe.png")));//10
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Wooden_Axe.png")));//11
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Wooden_Shovel.png")));//12
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stone_Sword.png")));//13
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stone_Pickaxe.png")));//14
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stone_Axe.png")));//15
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stone_Shovel.png")));//16
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Torch.png")));//17
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stick.png")));//18
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Coal_Ore.png")));//19
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Ore.png")));//20
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Gold_Ore.png")));//21
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Ore.png")));//22
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Coal.png")));//23
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Ingot.png")));//24
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Gold_Ingot.png")));//25
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond.png")));//26
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Block.png")));//27
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Gold_Block.png")));//28
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Block.png")));//29
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Sword.png")));//30
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Pickaxe.png")));//31
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Axe.png")));//32
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Shovel.png")));//33
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Golden_Sword.png")));//34
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Golden_Pickaxe.png")));//35
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Golden_Axe.png")));//36
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Golden_Shovel.png")));//37
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Sword.png")));//38
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Pickaxe.png")));//39
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Axe.png")));//40
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Shovel.png")));//41
            itemTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Rotten_Flesh.png")));//42
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 加载多个掉落物材质
    public static void loadItemDoubleTexture() {
        try {
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "/Dirt_Double.png")));//0
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "/Grass_Block_Double.png")));//1
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "/Stone_Double.png")));//2
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "/Bedrock_Double.png")));//3
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Cobble_Stone_Double.png")));//4
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Oak_Double.png")));//5
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Plank_Double.png")));//6
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Leaves_Double.png")));//7
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Crafting_Table_Double.png")));//8
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Wooden_Sword.png")));//9
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Wooden_Pickaxe.png")));//10
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Wooden_Axe.png")));//11
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Wooden_Shovel.png")));//12
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stone_Sword.png")));//13
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stone_Pickaxe.png")));//14
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stone_Axe.png")));//15
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stone_Shovel.png")));//16
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Torch_Double.png")));//17
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Stick_Double.png")));//18
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Coal_Ore_Double.png")));//19
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Ore_Double.png")));//20
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Gold_Ore_Double.png")));//21
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Ore_Double.png")));//22
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Coal_Double.png")));//23
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Ingot_Double.png")));//24
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Gold_Ingot_Double.png")));//25
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Double.png")));//26
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Block_Double.png")));//27
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Gold_Block_Double.png")));//28
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Block_Double.png")));//29
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Sword.png")));//30
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Pickaxe.png")));//31
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Axe.png")));//32
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Iron_Shovel.png")));//33
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Golden_Sword.png")));//34
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Golden_Pickaxe.png")));//35
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Golden_Axe.png")));//36
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Golden_Shovel.png")));//37
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Sword.png")));//38
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Pickaxe.png")));//39
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Axe.png")));//40
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Diamond_Shovel.png")));//41
            itemDoubleTextureList.add(ImageIO.read(new File(pathTexture + "/texture/item/" + "Rotten_Flesh_Double.png")));//42
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 加载僵尸材质
    public static void loadZombieTexture() {
        try {
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_StandLeft.png")));//0
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_StandRight.png")));//1
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_ReadyLeft1.png")));//2
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_ReadyLeft2.png")));//3
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_ReadyRight1.png")));//4
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_ReadyRight2.png")));//5
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_WalkLeft1.png")));//6
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_WalkLeft2.png")));//7
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_WalkRight1.png")));//8
            zombieTextureList.add(ImageIO.read(new File(pathTexture + "/texture/zombie/" + "/Zombie_WalkRight2.png")));//9

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPathTexture() {
        return pathTexture;
    }

    public static void setPathTexture(String pathTexture) {
        Resource.pathTexture = pathTexture;
    }

    public static List<BufferedImage> getWorldTextureList() {
        return worldTextureList;
    }

    public static void setWorldTextureList(List<BufferedImage> worldTextureList) {
        Resource.worldTextureList = worldTextureList;
    }

    public static List<BufferedImage> getBlockTextureList() {
        return blockTextureList;
    }

    public static void setBlockTextureList(List<BufferedImage> blockTextureList) {
        Resource.blockTextureList = blockTextureList;
    }

    public static List<BufferedImage> getPlayerTextureList() {
        return playerTextureList;
    }

    public static void setPlayerTextureList(List<BufferedImage> playerTextureList) {
        Resource.playerTextureList = playerTextureList;
    }

    public static List<BufferedImage> getGuiTextureList() {
        return guiTextureList;
    }

    public static void setGuiTextureList(List<BufferedImage> guiTextureList) {
        Resource.guiTextureList = guiTextureList;
    }

    public static List<BufferedImage> getItemBarBlockTexture() {
        return itemBarBlockTexture;
    }

    public static void setItemBarBlockTexture(List<BufferedImage> itemBarBlockTexture) {
        Resource.itemBarBlockTexture = itemBarBlockTexture;
    }

    public static List<BufferedImage> getItemTextureList() {
        return itemTextureList;
    }

    public static void setItemTextureList(List<BufferedImage> itemTextureList) {
        Resource.itemTextureList = itemTextureList;
    }

    public static List<BufferedImage> getItemDoubleTextureList() {
        return itemDoubleTextureList;
    }

    public static void setItemDoubleTextureList(List<BufferedImage> itemDoubleTextureList) {
        Resource.itemDoubleTextureList = itemDoubleTextureList;
    }

    public static List<Font> getFontList() {
        return fontList;
    }

    public static void setFontList(List<Font> fontList) {
        Resource.fontList = fontList;
    }

    public static String getPathSound() {
        return pathSound;
    }

    public static void setPathSound(String pathSound) {
        Resource.pathSound = pathSound;
    }

    public static List<BufferedImage> getZombieTextureList() {
        return zombieTextureList;
    }

    public static void setZombieTextureList(List<BufferedImage> zombieTextureList) {
        Resource.zombieTextureList = zombieTextureList;
    }
}
