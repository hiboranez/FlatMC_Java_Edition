package Utils.WorldTool;

import Base.World;
import EntityType.Item;
import EntityType.Player;
import EntityType.Zombie;
import Utils.OtherTool.Resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WorldLoad {
    public void loadWorld(String name, World world) {
        String path = System.getProperty("user.dir") + "/data/world/" + name + ".txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("width = ")) {
                    world.setWidth(Integer.parseInt(line.substring(8)));
                } else if (line.startsWith("height = ")) {
                    world.setHeight(Integer.parseInt(line.substring(9)));
                } else if (line.startsWith("voidSize = ")) {
                    world.setVoidSize(Integer.parseInt(line.substring(11)));
                } else if (line.startsWith("gama = ")) {
                    world.setGama(Double.parseDouble(line.substring(7)));
                } else if (line.startsWith("difficulty = ")) {
                    world.setDifficulty(line.substring(13));
                } else if (line.startsWith("xSpawn = ")) {
                    world.setxSpawn(Integer.parseInt(line.substring(9)));
                } else if (line.startsWith("ySpawn = ")) {
                    world.setySpawn(Integer.parseInt(line.substring(9)));
                } else if (line.startsWith("blockSize = ")) {
                    world.setBlockSize(Integer.parseInt(line.substring(12)));
                } else if (line.startsWith("gravity = ")) {
                    world.setGravity(Double.parseDouble(line.substring(10)));
                } else if (line.startsWith("airResistance = ")) {
                    world.setAirResistance(Double.parseDouble(line.substring(16)));
                } else if (line.startsWith("time = ")) {
                    world.setTime(Integer.parseInt(line.substring(7)));
                } else if (line.startsWith("player = {")) {
                    Player player = new Player();
                    world.getPlayerList().add(player);
                    player.setWorld(world);
                    while (!(line = reader.readLine()).equals("}")) {
                        if (line.startsWith("name = "))
                            player.setName(line.substring(7));
                        else if (line.startsWith("x = "))
                            player.setX(Integer.parseInt(line.substring(4)));
                        else if (line.startsWith("y = "))
                            player.setY(Integer.parseInt(line.substring(4)));
                        else if (line.startsWith("xSpawn = "))
                            player.setxSpawn(Integer.parseInt(line.substring(9)));
                        else if (line.startsWith("ySpawn = "))
                            player.setySpawn(Integer.parseInt(line.substring(9)));
                        else if (line.startsWith("gameMode = "))
                            player.setGameMode(line.substring(11));
                        else if (line.startsWith("health = "))
                            player.setHealth(Integer.parseInt(line.substring(9)));
                        else if (line.startsWith("flying = "))
                            player.setFlying(Boolean.parseBoolean(line.substring(9)));
                        else if (line.startsWith("autoJump = "))
                            player.setAutoJump(Boolean.parseBoolean(line.substring(11)));
                        else if (line.startsWith("keepInventory = "))
                            player.setKeepInventory(Boolean.parseBoolean(line.substring(16)));
                        else if (line.startsWith("ItemBarAmount =")) {
                            String[] values = reader.readLine().replace("[", "").replace("]", "").split(",");
                            for (int i = 0; i < values.length; i++) {
                                player.setItemBarAmountSingle(i, Integer.parseInt(values[i]));
                            }
                        } else if (line.startsWith("ItemBarId =")) {
                            String[] values = reader.readLine().replace("[", "").replace("]", "").split(",");
                            for (int i = 0; i < values.length; i++) {
                                player.setItemBarIdSingle(i, Integer.parseInt(values[i]));
                            }
                        }
                    }
                } else if (line.startsWith("Item = {")) {
                    Item item = new Item();
                    item.setWorld(world);
                    while (!(line = reader.readLine()).equals("}")) {
                        if (line.startsWith("x = "))
                            item.setX(Integer.parseInt(line.substring(4)));
                        else if (line.startsWith("y = "))
                            item.setY(Integer.parseInt(line.substring(4)));
                        else if (line.startsWith("id = "))
                            item.setId(Integer.parseInt((line.substring(5))));
                        else if (line.startsWith("amount = "))
                            item.setAmount(Integer.parseInt(line.substring(9)));
                        else if (line.startsWith("timeNoCollect = "))
                            item.setTimeNoCollect(Integer.parseInt(line.substring(16)));
                    }
                    item.getTextureList().add(Resource.getItemTextureList().get(item.getId()));
                    item.getTextureList().add(Resource.getItemDoubleTextureList().get(item.getId()));
                    if (item.getAmount() == 1) item.setTextureCurrent(item.getTextureList().get(0));
                    if (item.getAmount() > 1) item.setTextureCurrent(item.getTextureList().get(1));
                    world.getEntityList().add(item);
                } else if (line.startsWith("Zombie = {")) {
                    Zombie zombie = new Zombie();
                    zombie.setWorld(world);
                    while (!(line = reader.readLine()).equals("}")) {
                        if (line.startsWith("x = "))
                            zombie.setX(Integer.parseInt(line.substring(4)));
                        else if (line.startsWith("y = "))
                            zombie.setY(Integer.parseInt(line.substring(4)));
                        else if (line.startsWith("health = "))
                            zombie.setHealth(Integer.parseInt((line.substring(9))));
                    }
                    world.getEntityList().add(zombie);
                } else if (line.startsWith("blockIdList = {")) {
                    int y = 0;
                    world.setBlockIdList(new int[world.getHeight()][world.getWidth()]);
                    world.setxSpawn(50);
                    world.setySpawn((world.getHeight() - 5) * 50);
                    while (!(line = reader.readLine()).equals("}")) {
                        String[] values = line.replace("[", "").replace("]", "").split(",");
                        for (int x = 0; x < values.length; x++) {
                            world.setBlockIdListSingle(x, y, Integer.parseInt(values[x]));
                        }
                        y++;
                    }
                }
                line = null;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close(); // 在finally块中确保关闭文件流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
