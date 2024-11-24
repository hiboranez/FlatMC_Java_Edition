package Utils.TCP;

import Base.GameFrame;
import Base.World;
import Element.Entity;
import EntityType.Item;
import EntityType.Player;
import EntityType.Zombie;
import Utils.GUI.ChatText;
import Utils.GUI.Crack;
import Utils.GUI.FlexButton;
import Utils.OtherTool.*;

import java.awt.*;
import java.util.Objects;

import static java.lang.Integer.min;

public class Command {
    public static final boolean printInformation = true;
    public static final boolean showConnectInformation = true;
    private static GameFrame game;

    public static void setGame(GameFrame game) {
        Command.game = game;
    }

    public static String tpaPlayerName = null;

    public static void ReceivedFromServerCommand(String command) {
        String[] parts = command.split(" ");
        // 连接命令(客户端专属，服务器不具有)
        if (Objects.equals(parts[0], "/widthWorld")) {
            waitLinkState();
            game.getWorldCurrent().setWidth(Integer.parseInt(parts[1]));
            game.getLinkState()[1] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/heightWorld")) {
            waitLinkState();
            game.getWorldCurrent().setHeight(Integer.parseInt(parts[1]));
            game.getWorldCurrent().setBlockIdList(new int[game.getWorldCurrent().getHeight()][game.getWorldCurrent().getWidth()]);
            game.getLinkState()[2] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/voidSizeWorld")) {
            waitLinkState();
            game.getWorldCurrent().setVoidSize(Integer.parseInt(parts[1]));
            game.getLinkState()[3] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/gamaWorld")) {
            waitLinkState();
            game.getWorldCurrent().setGama(Double.parseDouble(parts[1]));
            game.getLinkState()[4] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/difficultyWorld")) {
            waitLinkState();
            game.getWorldCurrent().setDifficulty(parts[1]);
            game.getLinkState()[5] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/xSpawnWorld")) {
            waitLinkState();
            game.getWorldCurrent().setxSpawn(Integer.parseInt(parts[1]));
            game.getLinkState()[6] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/ySpawnWorld")) {
            waitLinkState();
            game.getWorldCurrent().setySpawn(Integer.parseInt(parts[1]));
            game.getLinkState()[7] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/blockSizeWorld")) {
            waitLinkState();
            game.getWorldCurrent().setBlockSize(Integer.parseInt(parts[1]));
            game.getLinkState()[8] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/gravityWorld")) {
            waitLinkState();
            game.getWorldCurrent().setGravity(Double.parseDouble(parts[1]));
            game.getLinkState()[9] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/airResistanceWorld")) {
            waitLinkState();
            game.getWorldCurrent().setAirResistance(Double.parseDouble(parts[1]));
            game.getLinkState()[10] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/timeWorld")) {
            waitLinkState();
            game.getWorldCurrent().setTime(Integer.parseInt(parts[1]));
            game.getLinkState()[11] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/blockIdListWorld")) {
            waitLinkState();
            int[][] tmpWorldIdList = StringConversion.stringToIntDoubleArray(parts[1]);
            for (int y = 0; y < game.getWorldCurrent().getHeight(); y++)
                for (int x = 0; x < game.getWorldCurrent().getWidth(); x++) {
                    game.getWorldCurrent().getBlockIdList()[y][x] = tmpWorldIdList[y][x];
                }
            game.getLinkState()[12] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + tmpWorldIdList.length + " " + tmpWorldIdList[0].length);
        } else if (Objects.equals(parts[0], "/xPlayer")) {
            waitLinkState();
            game.getPlayer().setX(Integer.parseInt(parts[1]));
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/yPlayer")) {
            waitLinkState();
            game.getPlayer().setY(Integer.parseInt(parts[1]));
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/xSpawnPlayer")) {
            waitLinkState();
            game.getPlayer().setxSpawn(Integer.parseInt(parts[1]));
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/ySpawnPlayer")) {
            waitLinkState();
            game.getPlayer().setySpawn(Integer.parseInt(parts[1]));
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/gameModePlayer")) {
            waitLinkState();
            game.getPlayer().setGameMode(parts[1]);
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/healthPlayer")) {
            waitLinkState();
            game.getPlayer().setHealth(Integer.parseInt(parts[1]));
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/flyingPlayer")) {
            waitLinkState();
            game.getPlayer().setFlying(Boolean.parseBoolean(parts[1]));
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/autoJumpPlayer")) {
            waitLinkState();
            game.getPlayer().setAutoJump(Boolean.parseBoolean(parts[1]));
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/keepInventoryPlayer")) {
            waitLinkState();
            game.getPlayer().setKeepInventory(Boolean.parseBoolean(parts[1]));
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/ItemBarAmountPlayer")) {
            waitLinkState();
            int[] itemBarAmountTmp = StringConversion.stringToIntArray(parts[1]);
            game.getPlayer().setItemBarAmount(itemBarAmountTmp);
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + itemBarAmountTmp.length);
        } else if (Objects.equals(parts[0], "/ItemBarIdPlayer")) {
            waitLinkState();
            int[] itemBarIdTmp = StringConversion.stringToIntArray(parts[1]);
            game.getPlayer().setItemBarId(itemBarIdTmp);
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + itemBarIdTmp.length);
        }
        // 状态命令
        else if (Objects.equals(parts[0], "/updatePlayerLinkDeny")) {
            waitLinkState();
            game.setLinkDeny(true);
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/updatePlayerLinkBan")) {
            waitLinkState();
            game.setBanned(true);
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/updatePlayerLinkBanIp")) {
            waitLinkState();
            game.setBaniped(true);
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/updatePlayerLink")) {
            // 特殊：初始化连接最后一步
            waitLinkState();
            game.getLinkState()[13] = true;
            if (printInformation && showConnectInformation)
                System.out.println(parts[0] + " " + parts[1]);
        } else if (Objects.equals(parts[0], "/updateDenied")) {
            if (Objects.equals(parts[1], "/unknownCommand")) {
                showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(65, game.getLanguage()), "orange");
                System.out.println("Error: Unknown command");
            } else {
                if (Objects.equals(parts[1], "/keepinventory")) {
                    if (game.isGamePaused()) {
                        if (game.getPlayer().isKeepInventory())
                            game.switchKeepInventoryTrueEsc();
                        else game.switchKeepInventoryFalseEsc();
                    }
                } else if (Objects.equals(parts[1], "/difficulty")) {
                    if (game.getWorldCurrent().getDifficulty().equals("peaceful"))
                        game.switchDifficultyPeacefulEsc();
                    else if (game.getWorldCurrent().getDifficulty().equals("easy"))
                        game.switchDifficultyEasyEsc();
                    else if (game.getWorldCurrent().getDifficulty().equals("normal"))
                        game.switchDifficultyNormalEsc();
                    else if (game.getWorldCurrent().getDifficulty().equals("hard"))
                        game.switchDifficultyHardEsc();
                }
                showText(WordList.getWord(45, game.getLanguage()), "orange");
                if (printInformation) System.out.println("Failed permission: " + parts[1]);
            }
        } else if (Objects.equals(parts[0], "/updateUsage")) {
            if (parts[1].equals("/help")) {
                if (printInformation) System.out.println("Usage: /help page");
                showText(WordList.getWord(75, game.getLanguage()) + "/help page", "orange");
            } else if (parts[1].equals("/time")) {
                if (printInformation) System.out.println("Usage: /time set newTime or /time");
                showText(WordList.getWord(75, game.getLanguage()) + "/time set newTime or /time", "orange");
            } else if (parts[1].equals("/tp")) {
                if (printInformation)
                    System.out.println("Usage: /tp playerName xBlock yBlock or /tp playerSent playerReceive");
                showText("Usage: /tp playerName xBlock yBlock or /tp playerSent playerReceive", "orange");
            } else if (parts[1].equals("/keepinventory")) {
                if (printInformation) System.out.println("Usage: /keepinventory playerName boolean");
                showText(WordList.getWord(75, game.getLanguage()) + "/keepinventory playerName boolean", "orange");
            } else if (parts[1].equals("/gamemode")) {
                if (printInformation) System.out.println("Usage: /gamemode playerName mode");
                showText(WordList.getWord(75, game.getLanguage()) + "/gamemode playerName mode", "orange");
            } else if (parts[1].equals("/difficulty")) {
                if (printInformation) System.out.println("Usage: /difficulty level");
                showText(WordList.getWord(75, game.getLanguage()) + "/difficulty level", "orange");
            } else if (parts[1].equals("/save")) {
                if (printInformation) System.out.println("Usage: /save");
                showText(WordList.getWord(75, game.getLanguage()) + "/save", "orange");
            } else if (parts[1].equals("/stop")) {
                if (printInformation) System.out.println("Usage: /stop");
                showText(WordList.getWord(75, game.getLanguage()) + "/stop", "orange");
            } else if (parts[1].equals("/kill")) {
                if (printInformation) System.out.println("Usage: /kill playerName");
                showText(WordList.getWord(75, game.getLanguage()) + "/kill playerName", "orange");
            } else if (parts[1].equals("/op")) {
                if (printInformation) System.out.println("Usage: /op playerName");
                showText(WordList.getWord(75, game.getLanguage()) + "/op playerName", "orange");
            } else if (parts[1].equals("/deop")) {
                if (printInformation) System.out.println("Usage: /deop playerName");
                showText(WordList.getWord(75, game.getLanguage()) + "/deop playerName", "orange");
            } else if (parts[1].equals("/setblock")) {
                if (printInformation) System.out.println("Usage: /setblock xBlock yBlock blockId|Name");
                showText(WordList.getWord(75, game.getLanguage()) + "/setblock xBlock yBlock blockId|Name", "orange");
            } else if (parts[1].equals("/give")) {
                if (printInformation) System.out.println("Usage: /give playerName itemId / Name amount");
                showText(WordList.getWord(75, game.getLanguage()) + "/give playerName itemId / Name amount", "orange");
            } else if (parts[1].equals("/clear")) {
                if (printInformation) System.out.println("Usage: /clear playerName itemId / Name amount");
                showText(WordList.getWord(75, game.getLanguage()) + "/clear playerName itemId / Name amount", "orange");
            } else if (parts[1].equals("/summon")) {
                if (printInformation) System.out.println("Usage: /summon entityName xBlock yBlock");
                showText(WordList.getWord(75, game.getLanguage()) + "/summon entityName xBlock yBlock", "orange");
            } else if (parts[1].equals("/gama")) {
                if (printInformation) System.out.println("Usage: /gama set value or /game");
                showText(WordList.getWord(75, game.getLanguage()) + "/gama set value or /game", "orange");
            } else if (parts[1].equals("/gravity")) {
                if (printInformation) System.out.println("Usage: /gravity set value or /gravity");
                showText(WordList.getWord(75, game.getLanguage()) + "/gravity set value or /gravity", "orange");
            } else if (parts[1].equals("/resistance")) {
                if (printInformation) System.out.println("Usage: /resistance object set value or /resistance object");
                showText(WordList.getWord(75, game.getLanguage()) + "/resistance object set value or /resistance object", "orange");
            } else if (parts[1].equals("/spawnpoint")) {
                if (printInformation) System.out.println("Usage: /spawnpoint playerName xBlock yBlock");
                showText(WordList.getWord(75, game.getLanguage()) + "/spawnpoint playerName xBlock yBlock", "orange");
            } else if (parts[1].equals("/spawnworld")) {
                if (printInformation) System.out.println("Usage: /spawnworld xBlock yBlock");
                showText(WordList.getWord(75, game.getLanguage()) + "/spawnworld xBlock yBlock", "orange");
            } else if (parts[1].equals("/spawn")) {
                if (printInformation) System.out.println("Usage: /spawn");
                showText(WordList.getWord(75, game.getLanguage()) + "/spawn", "orange");
            } else if (parts[1].equals("/tpa")) {
                if (printInformation) System.out.println("Usage: /tpa playerName");
                showText(WordList.getWord(75, game.getLanguage()) + "/tpa playerName", "orange");
            } else if (parts[1].equals("/tpaccept")) {
                if (printInformation) System.out.println("Usage: /tpaccept");
                showText(WordList.getWord(75, game.getLanguage()) + "/tpaccept", "orange");
            } else if (parts[1].equals("/tpdeny")) {
                if (printInformation) System.out.println("Usage: /tpdeny");
                showText(WordList.getWord(75, game.getLanguage()) + "/tpdeny", "orange");
            } else if (parts[1].equals("/register")) {
                if (printInformation) System.out.println("Usage: /register password password");
                showText(WordList.getWord(75, game.getLanguage()) + "/register password password", "orange");
            } else if (parts[1].equals("/login")) {
                if (printInformation) System.out.println("Usage: /login password");
                showText(WordList.getWord(75, game.getLanguage()) + "/login password", "orange");
            } else if (parts[1].equals("/sethome")) {
                if (printInformation) System.out.println("Usage: /sethome");
                showText(WordList.getWord(75, game.getLanguage()) + "/sethome", "orange");
            } else if (parts[1].equals("/home")) {
                if (printInformation) System.out.println("Usage: /home");
                showText(WordList.getWord(75, game.getLanguage()) + "/home", "orange");
            } else if (parts[1].equals("/msg")) {
                if (printInformation) System.out.println("Usage: /msg playerName message");
                showText(WordList.getWord(75, game.getLanguage()) + "/msg playerName message", "orange");
            } else if (parts[1].equals("/kick")) {
                if (printInformation) System.out.println("Usage: /kick playerName");
                showText(WordList.getWord(75, game.getLanguage()) + "/kick playerName", "orange");
            } else if (parts[1].equals("/ban")) {
                if (printInformation) System.out.println("Usage: /ban playerName");
                showText(WordList.getWord(75, game.getLanguage()) + "/ban playerName", "orange");
            } else if (parts[1].equals("/banip")) {
                if (printInformation) System.out.println("Usage: /banip playerName");
                showText(WordList.getWord(75, game.getLanguage()) + "/banip playerName", "orange");
            } else if (parts[1].equals("/unban")) {
                if (printInformation) System.out.println("Usage: /unban playerName");
                showText(WordList.getWord(75, game.getLanguage()) + "/unban playerName", "orange");
            }
        } else if (Objects.equals(parts[0], "/updateAlive")) {
            if (game.getWorldCurrent() != null)
                game.getWorldCurrent().setOnlineTimer(Integer.parseInt(parts[1]));
        } else if (Objects.equals(parts[0], "/updatePlayerJoin")) {
            showText(parts[1] + WordList.getWord(47, game.getLanguage()), "yellow");
        } else if (Objects.equals(parts[0], "/updatePlayerExit")) {
            showText(parts[1] + WordList.getWord(46, game.getLanguage()), "yellow");
            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (player.getName().equals(parts[1]))
                        game.getWorldCurrent().getEntityList().remove(entity);
                }
            }
        } else if (Objects.equals(parts[0], "/updateNewPlayer")) {
            if (game.getWorldCurrent() != null) {
                Player player = new Player(parts[1], game.getWorldCurrent(), true);
                player.setX(Integer.parseInt(parts[2]));
                player.setY(Integer.parseInt(parts[3]));
                game.getWorldCurrent().getEntityList().add(player);
                game.getWorldCurrent().getPlayerList().add(player);
            }
        } else if (Objects.equals(parts[0], "/updateTime")) {
            if (game.getWorldCurrent() != null)
                game.getWorldCurrent().setTime(Integer.parseInt(parts[1]));
        } else if (Objects.equals(parts[0], "/updateBlockIdListRange")) {
            waitLinkState12();
            int[][] blockIdListRange = StringConversion.stringToIntDoubleArray(parts[5]);
            int j = 0;
            for (int y = Integer.parseInt(parts[2]); y < Integer.parseInt(parts[4]); y++) {
                int i = 0;
                for (int x = Integer.parseInt(parts[1]); x < Integer.parseInt(parts[3]); x++) {
                    game.getWorldCurrent().getBlockIdList()[y][x] = blockIdListRange[j][i];
                    i++;
                }
                j++;
            }
            if (game.getFrameSort() == 0) {
                game.renderLightIntensity(Integer.parseInt(parts[1]), Integer.parseInt(parts[3]));
                game.updateVisionLightIntensity();
            }
        } else if (Objects.equals(parts[0], "/updateBlockIdListSingle")) {
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int id = Integer.parseInt(parts[3]);
            if (game.getWorldCurrent().getBlockIdList()[y][x] != id) {
                double R1 = World.calculateIntDistance(0, 0, game.getPlayer().getGame().getWidth() / 2, game.getPlayer().getGame().getHeight() / 2);
                double r1 = World.calculateIntDistance(game.getPlayer().getxCenter(), game.getPlayer().getyCenter(), x * 50, y * 50);
                if (R1 >= r1) {
                    if (id == -1) {
                        game.getWorldCurrent().getSoundList().add(new PlaySound("dig", game.getWorldCurrent().getBlockIdList()[y][x], (int) (1 - (r1 / R1)) * 100));
                    } else {
                        game.getWorldCurrent().getSoundList().add(new PlaySound("dig", id, (int) (1 - (r1 / R1)) * 100));
                    }
                }
                game.getWorldCurrent().getBlockIdList()[y][x] = id;
                game.getWorldCurrent().updateTorchFall(game.getPlayer());
                game.updateVisionLightIntensity();
            }
        } else if (Objects.equals(parts[0], "/updateBlockIdListSingleNoSound")) {
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int id = Integer.parseInt(parts[3]);
            if (game.getWorldCurrent().getBlockIdList()[y][x] != id) {
                game.getWorldCurrent().getBlockIdList()[y][x] = id;
                game.getWorldCurrent().updateTorchFall(game.getPlayer());
                game.updateVisionLightIntensity();
            }
        } else if (Objects.equals(parts[0], "/updateLocation")) {
            if (parts[1].equals("player")) {
                if (game.getWorldCurrent() != null) {
                    for (Entity entity : game.getWorldCurrent().getEntityList())
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            if (player.getName().equals(parts[2])) {
                                player.setX(Integer.parseInt(parts[3]));
                                player.setY(Integer.parseInt(parts[4]));
                            }
                        }
                }
            } else if (parts[1].equals("other")) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity.getIdCode() == Integer.parseInt(parts[2])) {
                        entity.setX(Integer.parseInt(parts[3]));
                        entity.setY(Integer.parseInt(parts[4]));
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateTimer")) {
            if (parts[1].equals("player")) {
                for (Entity entity : game.getWorldCurrent().getEntityList())
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.getName().equals(parts[2])) {
                            player.setWalkTimer(Integer.parseInt(parts[3]));
                            player.setRunTimer(Integer.parseInt(parts[4]));
                            player.setClickTimer(Integer.parseInt(parts[5]));
                        }
                    }
            } else if (parts[1].equals("other")) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity.getIdCode() == Integer.parseInt(parts[2])) {
                        if (entity instanceof Zombie) {
                            Zombie zombie = (Zombie) entity;
                            zombie.setWalkTimer(Integer.parseInt(parts[3]));
                            if (Integer.parseInt(parts[4]) == 1)
                                zombie.setTextureCurrent(zombie.getTextureList().get(0));
                            else if (Integer.parseInt(parts[4]) == 2)
                                zombie.setTextureCurrent(zombie.getTextureList().get(1));
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateState")) {
            if (parts[1].equals("player")) {
                for (Entity entity : game.getWorldCurrent().getEntityList())
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.getName().equals(parts[2])) {
                            if (parts[3].equals("left")) player.setFaceTo("left");
                            else if (parts[3].equals("right")) player.setFaceTo("right");
                            if (parts[4].equals("stand")) player.setMoveState("stand");
                            else if (parts[4].equals("walk")) player.setMoveState("walk");
                            else if (parts[4].equals("run")) player.setMoveState("run");
                        }
                    }
            } else if (parts[1].equals("other")) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity.getIdCode() == Integer.parseInt(parts[2])) {
                        if (entity instanceof Zombie) {
                            Zombie zombie = (Zombie) entity;
                            if (parts[3].equals("left")) zombie.setFaceTo("left");
                            else if (parts[3].equals("right")) zombie.setFaceTo("right");
                            if (parts[4].equals("stand")) zombie.setMoveState("stand");
                            else if (parts[4].equals("walk")) zombie.setMoveState("walk");
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateEntityData")) {
            if (parts[1].equals("player")) {
                if (game.getWorldCurrent() != null) {
                    for (Entity entity : game.getWorldCurrent().getEntityList())
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            if (player.getName().equals(parts[2])) {
                                player.setHealth(Integer.parseInt(parts[3]));
                                player.setLastHealth(Integer.parseInt(parts[3]));
                                player.setDead(Boolean.parseBoolean(parts[4]));
                            }
                        }
                }
            } else if (parts[1].equals("other")) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity.getIdCode() == Integer.parseInt(parts[2])) {
                        if (entity instanceof Zombie) {
                            entity.setHealth(Integer.parseInt(parts[3]));
                            entity.setDead(Boolean.parseBoolean(parts[4]));
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateAttack")) {
            if (parts[1].equals("player")) {
                for (Entity entity : game.getWorldCurrent().getEntityList())
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.getName().equals(parts[2])) {
                            player.setHealth(player.getHealth() - Integer.parseInt(parts[4]));
                            if (parts.length == 6 && parts[5].equals("zombie")) {
                                if (!player.isDead() && player.getHealth() <= 0) {
                                    player.setDead(true);
                                    TCPClient.sendMessageToServer(
                                            "/updateEntityData player " + player.getName() + " " + player.getHealth() + " " + player.isDead() + "\n", player.getName());
                                    if (!player.isDummyPlayer())
                                        player.kill();
                                    TCPClient.sendMessageToServer("/updateDeathInfo zombie " + player.getName() + "\n", player.getName());
                                }
                            }
                            player.hurt(Integer.parseInt(parts[4]));
                            if (Objects.equals(parts[3], "left")) {
                                player.stopMoveX();
                                player.setInParalysis(true);
                                player.setParalysisTimer(30);
                                player.setxSpeed(-8);
                            } else if (Objects.equals(parts[3], "right")) {
                                player.stopMoveX();
                                player.setInParalysis(true);
                                player.setParalysisTimer(30);
                                player.setxSpeed(8);
                            }
                        }
                    }
            } else if (parts[1].equals("other")) {
                Entity entity = game.getWorldCurrent().getEntityList().get(Integer.parseInt(parts[2]));
                if (entity != null) {
                    entity.hurt(Integer.parseInt(parts[3]));
                    if (Objects.equals(game.getPlayer().getFaceTo(), "left")) {
                        entity.stopMoveX();
                        entity.setInParalysis(true);
                        entity.setParalysisTimer(30);
                        entity.setxSpeed(-8);
                    } else if (Objects.equals(game.getPlayer().getFaceTo(), "right")) {
                        entity.stopMoveX();
                        entity.setInParalysis(true);
                        entity.setParalysisTimer(30);
                        entity.setxSpeed(8);
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updatePlayerMode")) {
            for (Entity entity : game.getWorldCurrent().getEntityList())
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (player.getName().equals(parts[1])) {
                        if (parts[2].equals("creative")) player.setGameMode("creative");
                        else if (parts[2].equals("survival")) player.setGameMode("survival");
                        player.setFlying(Boolean.parseBoolean(parts[3]));
                        player.setKeepInventory(Boolean.parseBoolean(parts[4]));
                    }
                }
        } else if (Objects.equals(parts[0], "/updateItemBarChosen")) {
            for (Entity entity : game.getWorldCurrent().getEntityList())
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (player.getName().equals(parts[1])) {
                        player.setItemBarChosen(Integer.parseInt(parts[2]));
                    }
                }
        } else if (Objects.equals(parts[0], "/updateItemBarAmount")) {
            for (Entity entity : game.getWorldCurrent().getEntityList())
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (player.getName().equals(parts[1])) {
                        int[] itemBarAmountTmp = StringConversion.stringToIntArray(parts[2]);
                        player.setItemBarAmount(itemBarAmountTmp);
                    }
                }
        } else if (Objects.equals(parts[0], "/updateItemBarId")) {
            for (Entity entity : game.getWorldCurrent().getEntityList())
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (player.getName().equals(parts[1])) {
                        int[] itemBarIdTmp = StringConversion.stringToIntArray(parts[2]);
                        player.setItemBarId(itemBarIdTmp);
                    }
                }
        } else if (Objects.equals(parts[0], "/updateSummonItem")) {
            if (game.getWorldCurrent() != null) {
                if (Integer.parseInt(parts[4]) != -1)
                    game.getWorldCurrent().getEntityList().add(new Item(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), game.getWorldCurrent(), Integer.parseInt(parts[6])));
            }
        } else if (Objects.equals(parts[0], "/updateRemoveItem")) {
            if (game.getWorldCurrent() != null) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity instanceof Item) {
                        Item item = (Item) entity;
                        if (item.getIdCode() == Integer.parseInt(parts[1]))
                            game.getWorldCurrent().getEntityList().remove(entity);
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateSummonItemWithXSpeed")) {
            if (game.getWorldCurrent() != null && Integer.parseInt(parts[4]) != -1) {
                Item item = new Item(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), game.getWorldCurrent(), Integer.parseInt(parts[6]));
                item.setxSpeed(Double.parseDouble(parts[7]));
                game.getWorldCurrent().getEntityList().add(item);
            }
        } else if (Objects.equals(parts[0], "/updateIsOp")) {
            if (game.getWorldCurrent() != null) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.getName().equals(parts[1])) {
                            if (parts[2].equals("Yes"))
                                player.setOperator(true);
                            else if (parts[2].equals("No"))
                                player.setOperator(false);
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateSound")) {
            if (game.getWorldCurrent() != null) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.getGame() != null) {
                            Double r1 = World.calculateIntDistance(player.getxCenter(), player.getyCenter(), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                            Double R1 = World.calculateIntDistance(0, 0, player.getGame().getxCenter(), player.getGame().getyCenter());
                            if (r1 <= R1) {
                                if (parts[3].equals("toolBreak"))
                                    player.getWorld().getSoundList().add(new PlaySound("damage", 3, 100));
                                else if (parts[3].equals("pop"))
                                    player.getWorld().getSoundList().add(new PlaySound("player", IDIndex.soundNameToID("player", "pop"), 100));
                                else if (parts[3].equals("damageHit"))
                                    player.getWorld().getSoundList().add(new PlaySound("damage", IDIndex.soundNameToID("damage", "hit"), 100));
                                else if (parts[3].equals("playerHurt"))
                                    player.getWorld().getSoundList().add(new PlaySound("player", IDIndex.soundNameToID("player", "hurt"), 100));
                                else if (parts[3].equals("zombieHurt"))
                                    player.getWorld().getSoundList().add(new PlaySound("zombie", IDIndex.soundNameToID("zombie", "hurt"), 100));
                                else if (parts[3].equals("zombieDeath"))
                                    player.getWorld().getSoundList().add(new PlaySound("zombie", IDIndex.soundNameToID("zombie", "death"), 100));
                                else if (parts[3].equals("fallSmall"))
                                    player.getWorld().getSoundList().add(new PlaySound("damage", IDIndex.soundNameToID("damage", "fallsmall"), 100));
                                else if (parts[3].equals("fallBig"))
                                    player.getWorld().getSoundList().add(new PlaySound("damage", IDIndex.soundNameToID("damage", "fallbig"), 100));
                                else if (parts[3].equals("step"))
                                    player.getWorld().getSoundList().add(new PlaySound("step", Integer.parseInt(parts[4]), 100));
                            }
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateCrack")) {
            if (game.getWorldCurrent() != null) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.getGame() != null) {
                            player.getGame().getCrackList().add(new Crack(parts[4], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateChat")) {
            showText("[" + parts[1] + "] " + parts[2], "white");
        } else if (Objects.equals(parts[0], "/updateText")) {
            if (parts.length >= 2) {
                StringBuilder string = new StringBuilder();
                for (int i = 1; i < parts.length; i++) {
                    string.append(parts[i]);
                    if (i == parts.length - 1) break;
                    string.append(" ");
                }
                showText(string.toString(), "white");
                if (Command.printInformation) System.out.println(string.toString());
                if (string.toString().equals("Unknown command"))
                    if (printInformation) System.out.println("Unknown command");
            }
        } else if (Objects.equals(parts[0], "/updateDeathInfo")) {
            if (parts[2].equals("kill"))
                showText(parts[1] + WordList.getWord(58, game.getLanguage()) + parts[3], "blue");
            else if (parts[2].equals("fall"))
                showText(parts[3] + WordList.getWord(59, game.getLanguage()), "blue");
            else if (parts[2].equals("choke"))
                showText(parts[3] + WordList.getWord(60, game.getLanguage()), "blue");
            else if (parts[2].equals("zombie"))
                showText(parts[3] + WordList.getWord(61, game.getLanguage()), "blue");
            else if (parts[2].equals("void"))
                showText(parts[3] + WordList.getWord(64, game.getLanguage()), "blue");
        } else if (Objects.equals(parts[0], "/updateDamageTimer")) {
            if (game.getWorldCurrent() != null) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.isDummyPlayer() && player.getName().equals(parts[1])) {
                            player.setDamageTimer(Integer.parseInt(parts[2]));
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateSummonZombie")) {
            if (game.getWorldCurrent() != null) {
                game.getWorldCurrent().getEntityList().add(new Zombie(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), game.getWorldCurrent()));
            }
        } else if (Objects.equals(parts[0], "/updateRemoveZombie")) {
            if (game.getWorldCurrent() != null) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity instanceof Zombie) {
                        Zombie zombie = (Zombie) entity;
                        if (zombie.getIdCode() == Integer.parseInt(parts[1]))
                            game.getWorldCurrent().getEntityList().remove(entity);
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateHitZombie")) {
            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                if (entity.getIdCode() == Integer.parseInt(parts[1])) {
                    if (entity instanceof Zombie) {
                        Zombie zombie = (Zombie) entity;
                        if (Objects.equals(parts[2], "left")) {
                            zombie.stopMoveX();
                            zombie.setInParalysis(true);
                            zombie.setParalysisTimer(30);
                            zombie.setxSpeed(-8);
                            zombie.setHealth(Integer.parseInt(parts[3]));
                        } else if (Objects.equals(parts[2], "right")) {
                            zombie.stopMoveX();
                            zombie.setInParalysis(true);
                            zombie.setParalysisTimer(30);
                            zombie.setxSpeed(8);
                            zombie.setHealth(Integer.parseInt(parts[3]));
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateHealth")) {
            if (parts[1].equals("player")) {
                if (game.getWorldCurrent() != null) {
                    for (Entity entity : game.getWorldCurrent().getEntityList())
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            if (player.getName().equals(parts[2])) {
                                player.setHealth(Integer.parseInt(parts[3]));
                            }
                        }
                }
            } else if (parts[1].equals("other")) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity.getIdCode() == Integer.parseInt(parts[2])) {
                        if (entity instanceof Zombie) {
                            entity.setHealth(Integer.parseInt(parts[3]));
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateTpaNoRequest")) {
            showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(101, game.getLanguage()), "orange");
            if (printInformation)
                System.out.println("Error: You don 't have any teleport request to solve");
        } else if (Objects.equals(parts[0], "/updateTpaSelf")) {
            showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(105, game.getLanguage()), "orange");
            if (printInformation) System.out.println("Error: You cannot teleport to yourself");
        } else if (Objects.equals(parts[0], "/updateTpaNoPlayer")) {
            showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
            if (printInformation) System.out.println("Error: " + parts[1] + " player not exist or online");
        } else if (Objects.equals(parts[0], "/updateTpaTimeOut")) {
            if (printInformation) System.out.println(parts[1] + " " + parts[2]);
            if (game.getPlayer().getName().equals(parts[1])) {
                showText(WordList.getWord(102, game.getLanguage()) + parts[2] + WordList.getWord(104, game.getLanguage()), "orange");
                if (printInformation) System.out.println("Your teleport request to " + parts[2] + " has been canceled");
                tpaPlayerName = null;
            } else if (game.getPlayer().getName().equals(parts[2])) {
                showText(WordList.getWord(103, game.getLanguage()) + parts[1] + WordList.getWord(104, game.getLanguage()), "orange");
                if (printInformation)
                    System.out.println("The teleport request from " + parts[1] + " has been canceled");
            }
        } else if (Objects.equals(parts[0], "/updateTpaWithdraw")) {
            if (printInformation) System.out.println(parts[1] + " " + parts[2]);
            if (game.getPlayer().getName().equals(parts[1])) {
                showText(WordList.getWord(102, game.getLanguage()) + parts[2] + WordList.getWord(108, game.getLanguage()), "orange");
                if (printInformation) System.out.println("Your teleport request to " + parts[2] + " has been canceled");
                tpaPlayerName = null;
            } else if (game.getPlayer().getName().equals(parts[2])) {
                showText(WordList.getWord(103, game.getLanguage()) + parts[1] + WordList.getWord(108, game.getLanguage()), "orange");
                if (printInformation)
                    System.out.println("The teleport request from " + parts[1] + " has been canceled");
            }
        } else if (Objects.equals(parts[0], "/updateLoginTimeOut")) {
            for (int i = 0; i < game.getLinkState().length; i++)
                game.getLinkState()[i] = false;
            TCPClient.closeTCP();
            game.showFrame5("LoginTimeOut");
            game.getLogTimerThread().interrupt();
            if (printInformation) System.out.println("Log in time out");
        } else if (Objects.equals(parts[0], "/updateRegisterInfo")) {
            showText(WordList.getWord(116, game.getLanguage()), "orange");
            if (printInformation) System.out.println("Please register by using /register password passwordAgain");
        } else if (Objects.equals(parts[0], "/updateLoginInfo")) {
            showText(WordList.getWord(117, game.getLanguage()), "orange");
            if (printInformation) System.out.println("Please logging in by using /login password");
        } else if (Objects.equals(parts[0], "/updateDropItemWithXSpeed")) {
            if (game.getWorldCurrent() != null && Integer.parseInt(parts[4]) != -1) {
                Item item = new Item(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), game.getWorldCurrent(), Integer.parseInt(parts[6]));
                game.getWorldCurrent().getEntityList().add(item);
            }
        } else if (Objects.equals(parts[0], "/updateItemState")) {
            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                if (entity instanceof Item) {
                    Item item = (Item) entity;
                    if (item.getIdCode() == Integer.parseInt(parts[1])) {
                        item.setSuspendY(Integer.parseInt(parts[2]));
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateCollectItem")) {
            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (!player.isDummyPlayer() && player.getName().equals(parts[1])) {
                        player.getItem(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), 36, true);
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/updateAreaProtected")) {
            showText(WordList.getWord(131, game.getLanguage()), "orange");
        }
        // 其它命令
        else if (Objects.equals(parts[0], "/help")) {
            if (isIntNumber(parts[1])) {
                if (isIntNumber(parts[1])) {
                    if (Boolean.parseBoolean(parts[2])) {
                        if (Integer.parseInt(parts[1]) == 1) {
                            showText("/help", "orange");
                            showText("/time", "orange");
                            showText("/keepinventory", "orange");
                            showText("/gamemode", "orange");
                            showText("/difficulty", "orange");
                            showText("/kill", "orange");
                            showText("page 1/6", "orange");
                            if (printInformation) System.out.println("Helped: " + parts[1]);
                        } else if (Integer.parseInt(parts[1]) == 2) {
                            showText("/setblock", "orange");
                            showText("/give", "orange");
                            showText("/tp", "orange");
                            showText("/op", "orange");
                            showText("/deop", "orange");
                            showText("/clear", "orange");
                            showText("page 2/6", "orange");
                            if (printInformation) System.out.println("Helped: " + parts[1]);
                        } else if (Integer.parseInt(parts[1]) == 3) {
                            showText("/summon", "orange");
                            showText("/gama", "orange");
                            showText("/gravity", "orange");
                            showText("/spawnpoint", "orange");
                            showText("/spawnworld", "orange");
                            showText("/resistance", "orange");
                            showText("page 3/6", "orange");
                            if (printInformation) System.out.println("Helped: " + parts[1]);
                        } else if (Integer.parseInt(parts[1]) == 4) {
                            showText("/spawn", "orange");
                            showText("/tpa", "orange");
                            showText("/tpaccept", "orange");
                            showText("/tpdeny", "orange");
                            showText("/register", "orange");
                            showText("/login", "orange");
                            showText("page 4/6", "orange");
                            if (printInformation) System.out.println("Helped: " + parts[1]);
                        } else if (Integer.parseInt(parts[1]) == 5) {
                            showText("/sethome", "orange");
                            showText("/home", "orange");
                            showText("/msg", "orange");
                            showText("/kick", "orange");
                            showText("/ban", "orange");
                            showText("/banip", "orange");
                            showText("page 5/6", "orange");
                            if (printInformation) System.out.println("Helped: " + parts[1]);
                        } else if (Integer.parseInt(parts[1]) == 6) {
                            showText("/unban", "orange");
                            showText("page 6/6", "orange");
                            if (printInformation) System.out.println("Helped: " + parts[1]);
                        } else {
                            showText("Error:  page must be 1~6", "orange");
                            if (printInformation) System.out.println("Error: page must be 1~6");
                        }
                    } else {
                        if (Integer.parseInt(parts[1]) == 1) {
                            showText("/help", "orange");
                            showText("/spawn", "orange");
                            showText("/tpa", "orange");
                            showText("/tpaccept", "orange");
                            showText("/tpdeny", "orange");
                            showText("/register", "orange");
                            showText("page 1/2", "orange");
                            if (printInformation) System.out.println("Helped: " + parts[1]);
                        } else if (Integer.parseInt(parts[1]) == 2) {
                            showText("/login", "orange");
                            showText("/sethome", "orange");
                            showText("/home", "orange");
                            showText("/msg", "orange");
                            showText("page 2/2", "orange");
                            if (printInformation) System.out.println("Helped: " + parts[1]);
                        } else {
                            showText("Error:  page must be 1~2", "orange");
                            if (printInformation) System.out.println("Error: page must be 1~2");
                        }
                    }
                } else {
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available number");
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(73, game.getLanguage()), "orange");
                }
            } else {
                showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(73, game.getLanguage()), "orange");
                System.out.println("Error: " + parts[1] + " is not an available number");
            }
        } else if (Objects.equals(parts[0], "/time")) {
            if (parts.length == 3) {
                if (game.getWorldCurrent() != null) {
                    if (isIntNumber(parts[2])) {
                        game.getWorldCurrent().setTime(Integer.parseInt(parts[2]));
                        game.updateWorldTexture();
                        game.updateVisionLightIntensity();
                        showText(WordList.getWord(50, game.getLanguage()) + parts[2], "green");
                        if (printInformation) System.out.println("Time set: " + parts[2]);
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[2] + " is not an available number");
                    }
                }
            } else if (parts.length == 1) {
                if (game.getWorldCurrent() != null) {
                    int timeNow = game.getWorldCurrent().getTime();
                    showText(WordList.getWord(66, game.getLanguage()) + timeNow, "green");
                    if (printInformation) System.out.println("Time: " + timeNow);
                }
            }
        } else if (Objects.equals(parts[0], "/tp")) {
            if (parts.length == 4) {
                if (isPlayerOnline(parts[1])) {
                    int xBlock = -2;
                    int yBlock = -2;
                    if (isIntNumber(parts[2]))
                        xBlock = (Integer.parseInt(parts[2]) + game.getWorldCurrent().getWidth() / 2);
                    if (isIntNumber(parts[3]))
                        yBlock = (-Integer.parseInt(parts[3]) + game.getWorldCurrent().getHeight() / 2);
                    if (xBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error:" + parts[2] + " is not an available number");
                    } else if (yBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error:" + parts[3] + " is not an available number");
                    } else {
                        if (game.getWorldCurrent() != null)
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[1])) {
                                        player.setX(xBlock * 50 - player.getWidth() / 2);
                                        player.setY(yBlock * 50 - player.getHeight() / 2);
                                    }
                                }
                            }
                        showText(parts[1] + WordList.getWord(48, game.getLanguage()) + parts[2] + " " + parts[3], "green");
                        if (printInformation)
                            System.out.println("Teleported: " + parts[1] + " " + parts[2] + " " + parts[3]);
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                }
            } else if (parts.length == 3) {
                if (game.getWorldCurrent() != null) {
                    if (isPlayerOnline(parts[1]) && isPlayerOnline(parts[2])) {
                        for (Entity entity1 : game.getWorldCurrent().getEntityList()) {
                            if (entity1 instanceof Player) {
                                Player player1 = (Player) entity1;
                                if (player1.getName().equals(parts[1])) {
                                    for (Entity entity2 : game.getWorldCurrent().getEntityList()) {
                                        if (entity2 instanceof Player) {
                                            Player player2 = (Player) entity2;
                                            if (player2.getName().equals(parts[2])) {
                                                player1.setX(player2.getX());
                                                player1.setY(player2.getY());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        showText(parts[1] + WordList.getWord(48, game.getLanguage()) + parts[2], "green");
                        System.out.println("Teleported: " + parts[1] + " " + parts[2]);
                    } else if (!isPlayerOnline(parts[1])) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[1] + " player not exist or online");
                    } else if (!isPlayerOnline(parts[2])) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[2] + " player not exist or online");
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/keepinventory")) {
            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                if (isPlayerOnline(parts[1])) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.getName().equals(parts[1])) {
                            if (isBooleanString(parts[2])) {
                                boolean bool = Boolean.parseBoolean(parts[2]);
                                if (parts[2].equals("1")) bool = true;
                                else if (parts[2].equals("0")) bool = false;
                                player.setKeepInventory(bool);
                                if (parts[2].equals("1")) {
                                    showText(parts[1] + WordList.getWord(49, game.getLanguage()) + "true", "green");
                                    if (printInformation) System.out.println("KeepInventoried: " + parts[1] + " true");
                                } else if (parts[2].equals("0")) {
                                    showText(parts[1] + WordList.getWord(49, game.getLanguage()) + "false", "green");
                                    if (printInformation) System.out.println("KeepInventoried: " + parts[1] + " false");
                                } else {
                                    showText(parts[1] + WordList.getWord(49, game.getLanguage()) + parts[2], "green");
                                    if (printInformation)
                                        System.out.println("KeepInventoried: " + parts[1] + " " + parts[2]);
                                }
                                if (player.equals(game.getPlayer()) && game.isGamePaused()) {
                                    for (FlexButton flexButton : game.getFlexButtonList()) {
                                        if (flexButton.getText().equals(WordList.getWord(5, game.getLanguage())))
                                            game.getFlexButtonList().remove(flexButton);
                                        if (flexButton.getText().equals(WordList.getWord(6, game.getLanguage())))
                                            game.getFlexButtonList().remove(flexButton);
                                    }
                                    if (player.isKeepInventory())
                                        game.switchKeepInventoryTrueEsc();
                                    else game.switchKeepInventoryFalseEsc();
                                }
                            } else {
                                showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(74, game.getLanguage()), "orange");
                                if (printInformation)
                                    System.out.println("Error: " + parts[2] + " is not an available boolean");
                            }
                        }
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                }
            }
        } else if (Objects.equals(parts[0], "/gamemode")) {
            if (isPlayerOnline(parts[1])) {
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.getName().equals(parts[1])) {
                            if (parts[2].equals("creative")) {
                                player.setGameMode("creative");
                                showText(parts[1] + WordList.getWord(51, game.getLanguage()) + "creative", "green");
                                if (printInformation) System.out.println("Gamemoded: " + parts[1] + " " + "creative");
                            } else if (parts[2].equals("survival")) {
                                player.setGameMode("survival");
                                showText(parts[1] + WordList.getWord(51, game.getLanguage()) + "survival", "green");
                                if (printInformation) System.out.println("Gamemoded: " + parts[1] + " " + "survival");
                            } else if (isIntNumber(parts[2]) && Integer.parseInt(parts[2]) == 1) {
                                player.setGameMode("creative");
                                showText(parts[1] + WordList.getWord(51, game.getLanguage()) + "creative", "green");
                                if (printInformation) System.out.println("Gamemoded: " + parts[1] + " " + "creative");
                            } else if (isIntNumber(parts[2]) && Integer.parseInt(parts[2]) == 0) {
                                player.setGameMode("survival");
                                showText(parts[1] + WordList.getWord(51, game.getLanguage()) + "survival", "green");
                                if (printInformation) System.out.println("Gamemoded: " + parts[1] + " " + "survival");
                            } else {
                                showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(70, game.getLanguage()), "orange");
                                if (printInformation)
                                    System.out.println("Error: " + parts[1] + " is not available number or mode");
                            }
                        }
                    }
                }
            } else {
                showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                if (printInformation)
                    System.out.println("Error: " + parts[1] + " player not exist or online");
            }
        } else if (Objects.equals(parts[0], "/difficulty")) {
            if (game.getWorldCurrent() != null) {
                if (parts[1].equals("peaceful")) {
                    game.getWorldCurrent().setDifficulty("peaceful");
                    showText(WordList.getWord(52, game.getLanguage()) + parts[1], "green");
                    if (printInformation) System.out.println("Difficultied: " + parts[1]);
                } else if (parts[1].equals("easy")) {
                    game.getWorldCurrent().setDifficulty("easy");
                    showText(WordList.getWord(52, game.getLanguage()) + parts[1], "green");
                    if (printInformation) System.out.println("Difficultied: " + parts[1]);
                } else if (parts[1].equals("normal")) {
                    game.getWorldCurrent().setDifficulty("normal");
                    showText(WordList.getWord(52, game.getLanguage()) + parts[1], "green");
                    if (printInformation) System.out.println("Difficultied: " + parts[1]);
                } else if (parts[1].equals("hard")) {
                    game.getWorldCurrent().setDifficulty("hard");
                    showText(WordList.getWord(52, game.getLanguage()) + parts[1], "green");
                    if (printInformation) System.out.println("Difficultied: " + parts[1]);
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(71, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available difficulty level");
                }
                for (Entity entity : game.getWorldCurrent().getEntityList()) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.equals(game.getPlayer()) && game.isGamePaused()) {
                            for (FlexButton flexButton : game.getFlexButtonList()) {
                                if (flexButton.getText().equals(WordList.getWord(7, game.getLanguage())))
                                    game.getFlexButtonList().remove(flexButton);
                                if (flexButton.getText().equals(WordList.getWord(8, game.getLanguage())))
                                    game.getFlexButtonList().remove(flexButton);
                                if (flexButton.getText().equals(WordList.getWord(9, game.getLanguage())))
                                    game.getFlexButtonList().remove(flexButton);
                                if (flexButton.getText().equals(WordList.getWord(10, game.getLanguage())))
                                    game.getFlexButtonList().remove(flexButton);
                            }
                            if (game.getWorldCurrent().getDifficulty().equals("peaceful"))
                                game.switchDifficultyPeacefulEsc();
                            else if (game.getWorldCurrent().getDifficulty().equals("easy"))
                                game.switchDifficultyEasyEsc();
                            else if (game.getWorldCurrent().getDifficulty().equals("normal"))
                                game.switchDifficultyNormalEsc();
                            else if (game.getWorldCurrent().getDifficulty().equals("hard"))
                                game.switchDifficultyHardEsc();
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/save")) {
            showText(WordList.getWord(53, game.getLanguage()), "green");
            if (printInformation) System.out.println("World saved");
        } else if (Objects.equals(parts[0], "/stop")) {
            game.showFrame5("ServerStopped");
            if (printInformation) System.out.println("Server stopped");
        } else if (Objects.equals(parts[0], "/kill")) {
            if (isPlayerOnline(parts[1])) {
                if (game.getWorldCurrent() != null) {
                    for (Entity entity : game.getWorldCurrent().getEntityList()) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            if (!player.isDummyPlayer() && player.getName().equals(parts[1])) {
                                player.kill();
                                TCPClient.sendMessageToServer("/updateSound " + player.getxCenter() + " " + player.getyCenter() + " " + "playerHurt 0\n", player.getName());
                            }
                        }
                    }
                    showText(parts[1] + WordList.getWord(54, game.getLanguage()), "green");
                    if (printInformation) System.out.println("Killed: " + parts[1]);
                }
            } else {
                showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                if (printInformation)
                    System.out.println("Error: " + parts[1] + " player not exist or online");
            }
        } else if (Objects.equals(parts[0], "/op")) {
            showText(parts[1] + WordList.getWord(56, game.getLanguage()), "green");
            if (printInformation) System.out.println("Oped: " + parts[1]);
        } else if (Objects.equals(parts[0], "/deop")) {
            showText(parts[1] + WordList.getWord(57, game.getLanguage()), "green");
            if (printInformation) System.out.println("Deoped: " + parts[1]);
        } else if (Objects.equals(parts[0], "/setblock")) {
            if (game.getWorldCurrent() != null) {
                int xBlock = -2;
                if (isIntNumber(parts[1])) xBlock = Integer.parseInt(parts[1]) + game.getWorldCurrent().getWidth() / 2;
                int yBlock = -2;
                if (isIntNumber(parts[2]))
                    yBlock = -Integer.parseInt(parts[2]) + game.getWorldCurrent().getHeight() / 2;
                int id = -2;
                if (xBlock == -2) {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available number");
                } else if (yBlock == -2) {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[2] + " is not an available number");
                } else if (Math.abs(Integer.parseInt(parts[1])) <= game.getWorldCurrent().getWidth() / 2 && Math.abs(Integer.parseInt(parts[2])) <= game.getWorldCurrent().getHeight() / 2) {
                    if (isIntNumber(parts[3]) && IDIndex.blockIDToName(Integer.parseInt(parts[3]), game.getLanguage()) != null)
                        id = Integer.parseInt(parts[3]);
                    else if (IDIndex.blockNameToID(parts[3]) != -2)
                        id = IDIndex.blockNameToID(parts[3]);
                    if (id != -2) {
                        game.getWorldCurrent().getBlockIdList()[yBlock][xBlock] = id;
                        showText(parts[1] + " " + parts[2] + WordList.getWord(67, game.getLanguage()) + IDIndex.blockIDToName(id, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Setblocked: " + parts[1] + " " + parts[2] + " " + IDIndex.blockIDToName(id, "English"));
                    } else {
                        if (printInformation)
                            System.out.println("Error: " + parts[3] + " is not an available number or name");
                        showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(69, game.getLanguage()), "orange");
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(72, game.getLanguage()), "orange");
                    if (printInformation) System.out.println("Error: Location is out of the world's size");
                }
            }
        } else if (Objects.equals(parts[0], "/give")) {
            if (game.getWorldCurrent() != null) {
                if (isPlayerOnline(parts[1])) {
                    int id = -2;
                    int amount = -2;
                    if (isIntNumber(parts[2]) && !IDIndex.blockIDToName(Integer.parseInt(parts[2]), "English").equals("null"))
                        id = Integer.parseInt(parts[2]);
                    else if (IDIndex.blockNameToID(parts[2]) != -2) id = IDIndex.blockNameToID(parts[2]);
                    if (isIntNumber(parts[3])) amount = Integer.parseInt(parts[3]);
                    if (id == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(69, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[2] + " is not an available number or name");
                    } else if (amount == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[3] + " is not an available number");
                    } else {
                        int amountLeft = 0;
                        for (Entity entity : game.getWorldCurrent().getEntityList()) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (player.getName().equals(parts[1])) {
                                    amountLeft = player.getItem(id, amount, 36, true);
                                }
                            }
                        }
                        showText(WordList.getWord(68, game.getLanguage()) + parts[1] + " " + IDIndex.blockIDToName(id, game.getLanguage()) + " * " + (amount - amountLeft), "orange");
                        if (printInformation)
                            System.out.println("Given: " + parts[1] + " " + IDIndex.blockIDToName(id, "English") + " * " + (amount - amountLeft));
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                }
            }
        } else if (Objects.equals(parts[0], "/clear")) {
            if (parts.length == 2) {
                if (game.getWorldCurrent() != null) {
                    if (isPlayerOnline(parts[1])) {
                        for (Entity entity : game.getWorldCurrent().getEntityList()) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (player.getName().equals(parts[1])) {
                                    for (int i = 0; i < 36; i++) {
                                        player.getItemBarAmount()[i] = 0;
                                        player.getItemBarId()[i] = -1;
                                    }
                                }
                            }
                        }
                        showText(parts[1] + WordList.getWord(81, game.getLanguage()), "green");
                        if (printInformation)
                            System.out.println("Cleared: " + parts[1]);
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[1] + " player not exist or online");
                    }
                }
            } else if (parts.length == 3) {
                if (game.getWorldCurrent() != null) {
                    if (isPlayerOnline(parts[1])) {
                        int id = -2;
                        if (isIntNumber(parts[2]) && !IDIndex.blockIDToName(Integer.parseInt(parts[2]), "English").equals("null"))
                            id = Integer.parseInt(parts[2]);
                        else if (IDIndex.blockNameToID(parts[2]) != -2) id = IDIndex.blockNameToID(parts[2]);
                        if (id == -2) {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(69, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[2] + " is not an available number or name");
                        } else {
                            int amountCleared = 0;
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[1])) {
                                        for (int i = 0; i < 36; i++) {
                                            if (player.getItemBarId()[i] == id) {
                                                amountCleared += player.getItemBarAmount()[i];
                                                player.getItemBarAmount()[i] = 0;
                                                player.getItemBarId()[i] = -1;
                                            }
                                        }
                                    }
                                }
                            }
                            showText(parts[1] + " " + WordList.getWord(82, game.getLanguage()) + IDIndex.blockIDToName(id, game.getLanguage()) + " * " + amountCleared, "green");
                            if (printInformation)
                                System.out.println("Cleared: " + parts[1] + " " + IDIndex.blockIDToName(id, "English") + " * " + amountCleared);
                        }
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[1] + " player not exist or online");
                    }
                }
            } else if (parts.length == 4) {
                if (game.getWorldCurrent() != null) {
                    if (isPlayerOnline(parts[1])) {
                        int id = -2;
                        int amount = -2;
                        if (isIntNumber(parts[2]) && !IDIndex.blockIDToName(Integer.parseInt(parts[2]), "English").equals("null"))
                            id = Integer.parseInt(parts[2]);
                        else if (IDIndex.blockNameToID(parts[2]) != -2) id = IDIndex.blockNameToID(parts[2]);
                        if (isIntNumber(parts[3])) amount = Integer.parseInt(parts[3]);
                        if (id == -2) {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(69, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[2] + " is not an available number or name");
                        } else if (amount == -2) {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[3] + " is not an available number");
                        } else {
                            int amountCleared = 0;
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[1])) {
                                        for (int i = 0; i < 36; i++) {
                                            if (player.getItemBarId()[i] == id) {
                                                while (player.getItemBarAmount()[i] > 0) {
                                                    if (amountCleared >= amount) break;
                                                    amountCleared += 1;
                                                    player.getItemBarAmount()[i] -= 1;
                                                }
                                            }
                                            if (amountCleared >= amount) break;
                                        }
                                    }
                                }
                            }
                            showText(parts[1] + " " + WordList.getWord(82, game.getLanguage()) + IDIndex.blockIDToName(id, game.getLanguage()) + " * " + amountCleared, "green");
                            if (printInformation)
                                System.out.println("Cleared: " + parts[1] + " " + IDIndex.blockIDToName(id, "English") + " * " + amountCleared);
                        }
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[1] + " player not exist or online");
                    }
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/clear playerName itemId / Name amount", "orange");
                if (printInformation) System.out.println("Usage: /clear playerName itemId / Name amount");
            }
        } else if (Objects.equals(parts[0], "/summon")) {
            if (parts.length == 4) {
                if (IDIndex.nameToIsEntity(parts[1])) {
                    int xBlock = -2;
                    int yBlock = -2;
                    if (isIntNumber(parts[2]))
                        xBlock = (Integer.parseInt(parts[2]) + game.getWorldCurrent().getWidth() / 2);
                    if (isIntNumber(parts[3]))
                        yBlock = (-Integer.parseInt(parts[3]) + game.getWorldCurrent().getHeight() / 2);
                    if (xBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error:" + parts[2] + " is not an available number");
                    } else if (yBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error:" + parts[3] + " is not an available number");
                    } else {
                        if (game.getWorldCurrent() != null)
                            if (parts[1].equals("zombie")) {
                                showText(IDIndex.entityNameToName(parts[1], game.getLanguage()) + WordList.getWord(84, game.getLanguage()) + parts[2] + " " + parts[3], "green");
                                if (printInformation)
                                    System.out.println("Summoned: " + IDIndex.entityNameToName(parts[1], "English") + " " + parts[2] + " " + parts[3]);
                            }
                    }
                } else {
                    showText(parts[1] + " " + WordList.getWord(83, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available entity name");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/summon entityName xBlock yBlock", "orange");
                if (printInformation) System.out.println("Usage: /summon entityName xBlock yBlock");
            }
        } else if (Objects.equals(parts[0], "/gama")) {
            if (parts.length == 3 && parts[1].equals("set")) {
                if (isDoubleNumber(parts[2])) {
                    game.getWorldCurrent().setGama(Double.parseDouble(parts[2]));
                    if (printInformation) System.out.println("Gama set: " + parts[2]);
                    game.updateWorldTexture();
                    game.updateVisionLightIntensity();
                    showText(WordList.getWord(85, game.getLanguage()) + parts[2], "green");
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[2] + " is not an available number");
                }
            } else if (parts.length == 1) {
                double gama = game.getWorldCurrent().getGama();
                if (printInformation) System.out.println("gama: " + gama);
                showText(WordList.getWord(86, game.getLanguage()) + gama, "green");
            } else {
                if (printInformation) System.out.println("Usage: /gama set newGama or /gama");
                showText(WordList.getWord(75, game.getLanguage()) + "/gama set newGama or /gama", "orange");
            }
        } else if (Objects.equals(parts[0], "/gravity")) {
            if (parts.length == 3 && parts[1].equals("set")) {
                if (isDoubleNumber(parts[2])) {
                    game.getWorldCurrent().setGravity(Double.parseDouble(parts[2]));
                    if (printInformation) System.out.println("Gravity set: " + parts[2]);
                    game.updateWorldTexture();
                    game.updateVisionLightIntensity();
                    showText(WordList.getWord(87, game.getLanguage()) + parts[2], "green");
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[2] + " is not an available number");
                }
            } else if (parts.length == 1) {
                double gravity = game.getWorldCurrent().getGravity();
                if (printInformation) System.out.println("Gravity: " + gravity);
                showText(WordList.getWord(88, game.getLanguage()) + gravity, "green");
            } else {
                if (printInformation) System.out.println("Usage: /gravity set newGravity or /gravity");
                showText(WordList.getWord(75, game.getLanguage()) + "/gravity set newGravity or /gravity", "orange");
            }
        } else if (Objects.equals(parts[0], "/resistance")) {
            if (parts.length >= 2) {
                if (parts[1].equals("air")) {
                    if (parts.length == 4 && parts[2].equals("set")) {
                        if (isDoubleNumber(parts[3])) {
                            game.getWorldCurrent().setAirResistance(Double.parseDouble(parts[3]));
                            if (printInformation) System.out.println("Air resistance set: " + parts[3]);
                            game.updateWorldTexture();
                            game.updateVisionLightIntensity();
                            showText(WordList.getWord(90, game.getLanguage()) + parts[3], "green");
                        } else {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[3] + " is not an available number");
                        }
                    } else if (parts.length == 2) {
                        double airResistance = game.getWorldCurrent().getAirResistance();
                        if (printInformation) System.out.println("Air resistance: " + airResistance);
                        showText(WordList.getWord(91, game.getLanguage()) + airResistance, "green");
                    } else {
                        if (printInformation)
                            System.out.println("Usage: /resistance object set value or /gravity object");
                        showText(WordList.getWord(75, game.getLanguage()) + "/resistance object set value or /gravity object", "orange");
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(89, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available object");
                }
            } else {
                if (printInformation)
                    System.out.println("Usage: /resistance object set value or /gravity object");
                showText(WordList.getWord(75, game.getLanguage()) + "/resistance object set value or /gravity object", "orange");
            }
        } else if (Objects.equals(parts[0], "/spawnpoint")) {
            if (parts.length == 4) {
                if (isPlayerOnline(parts[1])) {
                    int xBlock = -2;
                    int yBlock = -2;
                    if (isIntNumber(parts[2]))
                        xBlock = (Integer.parseInt(parts[2]) + (game.getWorldCurrent().getWidth() / 2));
                    if (isIntNumber(parts[3]))
                        yBlock = (-Integer.parseInt(parts[3]) + (game.getWorldCurrent().getHeight() / 2));
                    if (xBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[2] + " is not an available number");
                    } else if (yBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[3] + " is not an available number");

                    } else {
                        if (game.getWorldCurrent() != null)
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[1])) {
                                        player.setxSpawn(xBlock * 50 - player.getWidth() / 2);
                                        player.setySpawn(yBlock * 50 - player.getHeight() / 2);
                                    }
                                }
                            }
                        showText(parts[1] + WordList.getWord(92, game.getLanguage()) + parts[2] + " " + parts[3], "green");
                        if (printInformation)
                            System.out.println("Spawnpointed: " + parts[1] + " " + parts[2] + " " + parts[3]);
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/spawnpoint playerName xBlock yBlock", "orange");
                if (printInformation)
                    System.out.println("Usage: /spawnpoint playerName xBlock yBlock");
            }
        } else if (Objects.equals(parts[0], "/spawnworld")) {
            if (parts.length == 3) {
                int xBlock = -2;
                int yBlock = -2;
                if (isIntNumber(parts[1]))
                    xBlock = (Integer.parseInt(parts[1]) + (game.getWorldCurrent().getWidth() / 2));
                if (isIntNumber(parts[2]))
                    yBlock = (-Integer.parseInt(parts[2]) + (game.getWorldCurrent().getHeight() / 2));
                if (xBlock == -2) {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available number");
                } else if (yBlock == -2) {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[2] + " is not an available number");
                } else {
                    game.getWorldCurrent().setxSpawn(xBlock * 50 - 20 / 2);
                    game.getWorldCurrent().setySpawn(yBlock * 50 - 95 / 2);
                    showText(WordList.getWord(93, game.getLanguage()) + parts[1] + " " + parts[2], "green");
                    if (printInformation)
                        System.out.println("Spawnworlded: " + parts[1] + " " + parts[2]);
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/spawnworld xBlock yBlock", "orange");
                if (printInformation)
                    System.out.println("Usage: /spawnworld xBlock yBlock");
            }
        } else if (Objects.equals(parts[0], "/spawn")) {
            showText(WordList.getWord(94, game.getLanguage()), "green");
            if (printInformation) System.out.println("You have been teleported to world spawn point");
            if (game.getWorldCurrent() != null) {
                game.getPlayer().setX(game.getWorldCurrent().getxSpawn());
                game.getPlayer().setY(game.getWorldCurrent().getySpawn());
            }
        } else if (Objects.equals(parts[0], "/tpa")) {
            if (parts.length == 3) {
                if (game.getWorldCurrent() != null) {
                    if (game.getPlayer().getName().equals(parts[1])) {
                        if (isPlayerOnline(parts[2])) {
                            showText(WordList.getWord(98, game.getLanguage()) + parts[2], "green");
                            System.out.println("Your teleport request has been sent to " + parts[2]);
                            tpaPlayerName = parts[2];
                        }
                    } else if (game.getPlayer().getName().equals(parts[2])) {
                        showText(parts[1] + WordList.getWord(95, game.getLanguage()), "orange");
                        showText(WordList.getWord(96, game.getLanguage()), "orange");
                        showText(WordList.getWord(97, game.getLanguage()), "orange");
                        System.out.println(parts[1] + " wants to teleport to your position");
                        System.out.println("Accept: /tpaccept");
                        System.out.println("Deny: /tpdeny");
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/tpaccept")) {
            if (parts.length == 3) {
                if (game.getWorldCurrent() != null) {
                    if (tpaPlayerName != null && tpaPlayerName.equals(parts[2])) {
                        if (isPlayerOnline(parts[2])) {
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[2])) {
                                        game.getPlayer().setX(player.getX());
                                        game.getPlayer().setY(player.getY());
                                        tpaPlayerName = null;
                                    }
                                }
                            }
                            showText(WordList.getWord(99, game.getLanguage()), "green");
                            System.out.println("Your teleport request has been accepted");
                        }
                    }
                    if (game.getPlayer().getName().equals(parts[2])) {
                        for (Entity entity : game.getWorldCurrent().getEntityList()) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (player.getName().equals(parts[1])) {
                                    game.getPlayer().setX(game.getPlayer().getX());
                                    game.getPlayer().setY(game.getPlayer().getY());
                                    tpaPlayerName = null;
                                }
                            }
                        }
                        showText(WordList.getWord(106, game.getLanguage()), "green");
                        System.out.println("You have accepted the teleport request");
                    }
                    for (Entity entity1 : game.getWorldCurrent().getEntityList()) {
                        if (entity1 instanceof Player) {
                            Player player1 = (Player) entity1;
                            if (player1.getName().equals(parts[1])) {
                                for (Entity entity2 : game.getWorldCurrent().getEntityList()) {
                                    if (entity2 instanceof Player) {
                                        Player player2 = (Player) entity2;
                                        if (player2.getName().equals(parts[2])) {
                                            player1.setX(player2.getX());
                                            player1.setY(player2.getY());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/tpdeny")) {
            if (parts.length == 3) {
                if (game.getWorldCurrent() != null) {
                    if (tpaPlayerName != null && tpaPlayerName.equals(parts[2])) {
                        showText(WordList.getWord(100, game.getLanguage()), "orange");
                        System.out.println("Your teleport request has been denied");
                        tpaPlayerName = null;
                    } else if (game.getPlayer().getName().equals(parts[2])) {
                        showText(WordList.getWord(107, game.getLanguage()), "green");
                        System.out.println("You have denied the teleport request");
                    }
                }
            }
        } else if (Objects.equals(parts[0], "/register")) {
            if (parts.length == 2) {
                if (parts[1].equals("success")) {
                    game.getLogTimerThread().interrupt();
                    showText(WordList.getWord(110, game.getLanguage()), "orange");
                    game.getPlayer().setHasGravity(true);
                    game.getPlayer().setLogin(true);
                    if (printInformation) System.out.println("Register success");
                } else if (parts.length == 2 && parts[1].equals("differ")) {
                    if (printInformation) System.out.println("Error: Entered passwords differ");
                    showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(109, game.getLanguage()), "orange");
                } else if (parts.length == 2 && parts[1].equals("null")) {
                    if (printInformation)
                        System.out.println("Error: You can't use null as your password, change another one");
                    showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(111, game.getLanguage()), "orange");
                } else if (parts.length == 2 && parts[1].equals("registered")) {
                    if (printInformation)
                        System.out.println("Error: You have already registered");
                    showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(122, game.getLanguage()), "orange");
                }
            }
        } else if (Objects.equals(parts[0], "/login")) {
            if (parts.length == 2) {
                if (parts[1].equals("success")) {
                    game.getLogTimerThread().interrupt();
                    showText(WordList.getWord(112, game.getLanguage()), "orange");
                    game.getPlayer().setHasGravity(true);
                    game.getPlayer().setLogin(true);
                    if (printInformation) System.out.println("Login success");
                } else if (parts.length == 2 && parts[1].equals("wrong")) {
                    if (printInformation) System.out.println("Error: Wrong password");
                    showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(113, game.getLanguage()), "orange");
                } else if (parts.length == 2 && parts[1].equals("noPassword")) {
                    if (printInformation)
                        System.out.println("Error: You still don't have a password, use /register to create one first");
                    showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(114, game.getLanguage()), "orange");
                } else if (parts.length == 2 && parts[1].equals("logined")) {
                    if (printInformation)
                        System.out.println("Error: You have already logged in");
                    showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(121, game.getLanguage()), "orange");
                }
            }
        } else if (Objects.equals(parts[0], "/sethome")) {
            showText(WordList.getWord(118, game.getLanguage()), "green");
            if (printInformation) System.out.println("Home set successfully");
        } else if (Objects.equals(parts[0], "/home")) {
            showText(WordList.getWord(119, game.getLanguage()), "green");
            if (printInformation) System.out.println("You have returned home");
            if (game.getWorldCurrent() != null) {
                game.getPlayer().setX(Integer.parseInt(parts[1]));
                game.getPlayer().setY(Integer.parseInt(parts[2]));
            }
        } else if (Objects.equals(parts[0], "/msg")) {
            if (game.getPlayer() != null && game.getPlayerName().equals(parts[1])) {
                if (isPlayerOnline(parts[2])) {
                    showText(WordList.getWord(123, game.getLanguage()) + parts[2] + WordList.getWord(124, game.getLanguage()) + parts[3], "orange");
                    if (printInformation) System.out.println("You whispered to " + parts[2] + ": " + parts[3]);
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation) System.out.println("Error: " + parts[2] + " player not exist or online");
                }
            } else if (game.getPlayer() != null && game.getPlayerName().equals(parts[2])) {
                showText(parts[1] + WordList.getWord(120, game.getLanguage()) + parts[3], "orange");
                if (printInformation) System.out.println(parts[1] + " whispered to you: " + parts[3]);
            }
        } else if (Objects.equals(parts[0], "/kick")) {
            if (game.getPlayer() != null) {
                if (!game.getPlayerName().equals(parts[1])) {
                    if (isPlayerOnline(parts[1])) {
                        for (Entity entity : game.getWorldCurrent().getEntityList()) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (player.getName().equals(parts[1])) {
                                    game.getWorldCurrent().getEntityList().remove(player);
                                }
                            }
                        }
                        showText(parts[1] + WordList.getWord(125, game.getLanguage()), "orange");
                        System.out.println("Kicked: " + parts[1]);
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                        System.out.println("Failed usage: /kick");
                    }
                } else {
                    game.setGamePaused(false);
                    game.showFrame5("Kicked");
                    game.setPlayer(null);
                    game.setWorldCurrent(null);
                    game.setMouseLeftPressed(false);
                    game.setMouseRightPressed(false);
                    game.setBackGui(false);
                }
            }
        } else if (Objects.equals(parts[0], "/banip")) {
            if (game.getPlayer() != null) {
                if (!game.getPlayerName().equals(parts[1])) {
                    if (isPlayerOnline(parts[1])) {
                        for (Entity entity : game.getWorldCurrent().getEntityList()) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (player.getName().equals(parts[1])) {
                                    game.getWorldCurrent().getEntityList().remove(player);
                                }
                            }
                        }
                        showText(parts[1] + WordList.getWord(126, game.getLanguage()), "orange");
                        System.out.println("Baniped: " + parts[1]);
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                        System.out.println("Failed usage: /banip");
                    }
                } else {
                    game.setGamePaused(false);
                    game.showFrame5("Banned");
                    game.setPlayer(null);
                    game.setWorldCurrent(null);
                    game.setMouseLeftPressed(false);
                    game.setMouseRightPressed(false);
                    game.setBackGui(false);
                }
            }
        } else if (Objects.equals(parts[0], "/ban")) {
            if (game.getPlayer() != null) {
                if (!game.getPlayerName().equals(parts[1])) {
                    System.out.println("Banned: " + parts[1]);
                    showText(parts[1] + WordList.getWord(126, game.getLanguage()), "orange");
                    for (Entity entity : game.getWorldCurrent().getEntityList()) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            if (player.getName().equals(parts[1])) {
                                game.getWorldCurrent().getEntityList().remove(player);
                            }
                        }
                    }
                } else {
                    game.setGamePaused(false);
                    game.showFrame5("Banned");
                    game.setPlayer(null);
                    game.setWorldCurrent(null);
                    game.setMouseLeftPressed(false);
                    game.setMouseRightPressed(false);
                    game.setBackGui(false);
                }
            }
        } else if (Objects.equals(parts[0], "/unban")) {
            if (game.getWorldCurrent() != null) {
                System.out.println("Unbanned: " + parts[1]);
                showText(parts[1] + WordList.getWord(130, game.getLanguage()), "orange");
            }
        } else {
            showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(65, game.getLanguage()), "orange");
            if (printInformation) System.out.println("Error: Unknown command");
        }
    }

    public static void ReceivedFromClientCommand(String command) {
        String[] parts = command.split(" ");
        if (Objects.equals(parts[0], "/help")) {
            if (parts.length == 2) {
                if (isIntNumber(parts[1])) {
                    if (Integer.parseInt(parts[1]) == 1) {
                        showText("/help", "orange");
                        showText("/time", "orange");
                        showText("/keepinventory", "orange");
                        showText("/gamemode", "orange");
                        showText("/difficulty", "orange");
                        showText("/kill", "orange");
                        showText("page 1/3", "orange");
                        if (printInformation) System.out.println("Helped: " + parts[1]);
                    } else if (Integer.parseInt(parts[1]) == 2) {
                        showText("/setblock", "orange");
                        showText("/give", "orange");
                        showText("/tp", "orange");
                        showText("/clear", "orange");
                        showText("/summon", "orange");
                        showText("/gama", "orange");
                        showText("page 2/3", "orange");
                        if (printInformation) System.out.println("Helped: " + parts[1]);
                    } else if (Integer.parseInt(parts[1]) == 3) {
                        showText("/gravity", "orange");
                        showText("/spawnpoint", "orange");
                        showText("/spawnworld", "orange");
                        showText("/resistance", "orange");
                        showText("page 3/3", "orange");
                        if (printInformation) System.out.println("Helped: " + parts[1]);
                    } else {
                        showText("Error:  page must be 1~3", "orange");
                        if (printInformation)
                            System.out.println(WordList.getWord(76, game.getLanguage()) + "page must be 1~3");
                    }
                } else {
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available number");
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(73, game.getLanguage()), "orange");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/help page", "orange");
                if (printInformation) System.out.println("Usage: /help page");
            }
        } else if (Objects.equals(parts[0], "/time")) {
            if (parts.length == 3 && parts[1].equals("set")) {
                if (isIntNumber(parts[2])) {
                    game.getWorldCurrent().setTime(Integer.parseInt(parts[2]));
                    if (printInformation) System.out.println("Time set: " + parts[2]);
                    game.updateWorldTexture();
                    game.updateVisionLightIntensity();
                    showText(WordList.getWord(50, game.getLanguage()) + parts[2], "green");
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[2] + " is not an available number");
                }
            } else if (parts.length == 1) {
                int time = game.getWorldCurrent().getTime();
                if (printInformation) System.out.println("Time: " + time);
                showText(WordList.getWord(66, game.getLanguage()) + time, "green");
            } else {
                if (printInformation) System.out.println("Usage: /time set newTime or /time");
                showText(WordList.getWord(75, game.getLanguage()) + "/time set newTime or /time", "orange");
            }
        } else if (Objects.equals(parts[0], "/tp")) {
            if (parts.length == 4) {
                if (isPlayerOnline(parts[1])) {
                    int xBlock = -2;
                    int yBlock = -2;
                    if (isIntNumber(parts[2]))
                        xBlock = (Integer.parseInt(parts[2]) + (game.getWorldCurrent().getWidth() / 2));
                    if (isIntNumber(parts[3]))
                        yBlock = (-Integer.parseInt(parts[3]) + (game.getWorldCurrent().getHeight() / 2));
                    if (xBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[2] + " is not an available number");
                    } else if (yBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[3] + " is not an available number");

                    } else {
                        if (game.getWorldCurrent() != null)
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[1])) {
                                        player.setX(xBlock * 50 - player.getWidth() / 2);
                                        player.setY(yBlock * 50 - player.getHeight() / 2);
                                    }
                                }
                            }
                        showText(parts[1] + WordList.getWord(48, game.getLanguage()) + parts[2] + " " + parts[3], "green");
                        if (printInformation)
                            System.out.println("Teleported: " + parts[1] + " " + parts[2] + " " + parts[3]);
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/tp playerName xBlock yBlock", "orange");
                if (printInformation)
                    System.out.println("Usage: /tp playerName xBlock yBlock");
            }
        } else if (Objects.equals(parts[0], "/keepinventory")) {
            if (parts.length == 3) {
                if (isPlayerOnline(parts[1])) {
                    if (isBooleanString(parts[2])) {
                        for (Entity entity : game.getWorldCurrent().getEntityList()) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (player.getName().equals(parts[1])) {
                                    boolean bool = Boolean.parseBoolean(parts[2]);
                                    if (parts[2].equals("1")) bool = true;
                                    else if (parts[2].equals("0")) bool = false;
                                    player.setKeepInventory(bool);
                                }
                            }
                        }
                        if (parts[2].equals("1")) {
                            showText(parts[1] + WordList.getWord(49, game.getLanguage()) + "true", "green");
                            if (printInformation) System.out.println("KeepInventoried: " + parts[1] + " true");
                        } else if (parts[2].equals("0")) {
                            showText(parts[1] + WordList.getWord(49, game.getLanguage()) + "false", "green");
                            if (printInformation) System.out.println("KeepInventoried: " + parts[1] + " false");
                        } else {
                            showText(parts[1] + WordList.getWord(49, game.getLanguage()) + parts[2], "green");
                            if (printInformation) System.out.println("KeepInventoried: " + parts[1] + " " + parts[2]);
                        }
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(74, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[2] + " is not an available boolean");
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/keepinventory playerName boolean", "orange");
                if (printInformation) System.out.println("Usage: /keepinventory playerName boolean");
            }
        } else if (Objects.equals(parts[0], "/gamemode")) {
            if (parts.length == 3) {
                if (isPlayerOnline(parts[1])) {
                    for (Entity entity : game.getWorldCurrent().getEntityList()) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            if (player.getName().equals(parts[1])) {
                                if (parts[2].equals("creative")) {
                                    player.setGameMode("creative");
                                    showText(parts[1] + WordList.getWord(51, game.getLanguage()) + "creative", "green");
                                    if (printInformation)
                                        System.out.println("Gamemoded: " + parts[1] + " " + "creative");
                                } else if (parts[2].equals("survival")) {
                                    player.setGameMode("survival");
                                    showText(parts[1] + WordList.getWord(51, game.getLanguage()) + "survival", "green");
                                    if (printInformation)
                                        System.out.println("Gamemoded: " + parts[1] + " " + "survival");
                                } else if (isIntNumber(parts[2]) && Integer.parseInt(parts[2]) == 1) {
                                    player.setGameMode("creative");
                                    showText(parts[1] + WordList.getWord(51, game.getLanguage()) + "creative", "green");
                                    if (printInformation)
                                        System.out.println("Gamemoded: " + parts[1] + " " + "creative");
                                } else if (isIntNumber(parts[2]) && Integer.parseInt(parts[2]) == 0) {
                                    player.setGameMode("survival");
                                    showText(parts[1] + WordList.getWord(51, game.getLanguage()) + "survival", "green");
                                    if (printInformation)
                                        System.out.println("Gamemoded: " + parts[1] + " " + "survival");
                                } else {
                                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(70, game.getLanguage()), "orange");
                                    if (printInformation)
                                        System.out.println("Error: " + parts[2] + " is not available number or mode");
                                }
                            }
                        }
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/gamemode playerName mode", "orange");
                if (printInformation) System.out.println("Usage: /gamemode playerName mode");
            }
        } else if (Objects.equals(parts[0], "/difficulty")) {
            if (parts.length == 2) {
                if (parts[1].equals("peaceful")) {
                    game.getWorldCurrent().setDifficulty("peaceful");
                    showText(WordList.getWord(52, game.getLanguage()) + parts[1], "green");
                    if (printInformation) System.out.println("Difficultied: " + parts[1]);
                } else if (parts[1].equals("easy")) {
                    game.getWorldCurrent().setDifficulty("easy");
                    showText(WordList.getWord(52, game.getLanguage()) + parts[1], "green");
                    if (printInformation) System.out.println("Difficultied: " + parts[1]);
                } else if (parts[1].equals("normal")) {
                    game.getWorldCurrent().setDifficulty("normal");
                    showText(WordList.getWord(52, game.getLanguage()) + parts[1], "green");
                    if (printInformation) System.out.println("Difficultied: " + parts[1]);
                } else if (parts[1].equals("hard")) {
                    game.getWorldCurrent().setDifficulty("hard");
                    showText(WordList.getWord(52, game.getLanguage()) + parts[1], "green");
                    if (printInformation) System.out.println("Difficultied: " + parts[1]);
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(71, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available difficulty level");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/difficulty level", "orange");
                if (printInformation) System.out.println("Usage: /difficulty level");
            }
        } else if (Objects.equals(parts[0], "/kill")) {
            if (parts.length == 2) {
                if (isPlayerOnline(parts[1])) {
                    for (Entity entity : game.getWorldCurrent().getEntityList()) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            if (player.getName().equals(parts[1])) {
                                player.setDead(true);
                                player.kill();
                            }
                        }
                    }
                    showText(parts[1] + WordList.getWord(54, game.getLanguage()), "green");
                    if (printInformation) System.out.println("Killed: " + parts[1]);
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/kill playerName", "orange");
                if (printInformation) System.out.println("Usage: /kill playerName");
            }
        } else if (Objects.equals(parts[0], "/setblock")) {
            if (parts.length == 4) {
                if (game.getWorldCurrent() != null) {
                    int xBlock = -2;
                    if (isIntNumber(parts[1]))
                        xBlock = Integer.parseInt(parts[1]) + game.getWorldCurrent().getWidth() / 2;
                    int yBlock = -2;
                    if (isIntNumber(parts[2]))
                        yBlock = -Integer.parseInt(parts[2]) + game.getWorldCurrent().getHeight() / 2;
                    int id = -2;
                    if (xBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[1] + " is not an available number");
                    } else if (yBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[2] + " is not an available number");
                    } else if (Math.abs(Integer.parseInt(parts[1])) <= game.getWorldCurrent().getWidth() / 2 && Math.abs(Integer.parseInt(parts[2])) <= game.getWorldCurrent().getHeight() / 2) {
                        if (isIntNumber(parts[3]) && IDIndex.blockIDToName(Integer.parseInt(parts[3]), game.getLanguage()) != null)
                            id = Integer.parseInt(parts[3]);
                        else if (IDIndex.blockNameToID(parts[3]) != -2)
                            id = IDIndex.blockNameToID(parts[3]);
                        if (id != -2) {
                            game.getWorldCurrent().getBlockIdList()[yBlock][xBlock] = id;
                            if (id != -1) {
                                showText(parts[1] + " " + parts[2] + WordList.getWord(67, game.getLanguage()) + IDIndex.blockIDToName(id, game.getLanguage()), "green");
                                if (printInformation)
                                    System.out.println("Setblocked: " + parts[1] + " " + parts[2] + " " + IDIndex.blockIDToName(id, "English"));
                            }
                        } else {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(69, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[3] + " is not an available number or name");
                        }
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(72, game.getLanguage()), "orange");
                        if (printInformation) System.out.println("Error: Location is out of the world's size");
                    }
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/setblock xBlock yBlock blockId", "orange");
                if (printInformation) System.out.println("Usage: /setblock xBlock yBlock blockId");
            }
        } else if (Objects.equals(parts[0], "/give")) {
            if (parts.length == 4) {
                if (game.getWorldCurrent() != null) {
                    if (isPlayerOnline(parts[1])) {
                        int id = -2;
                        int amount = -2;
                        if (isIntNumber(parts[2]) && !IDIndex.blockIDToName(Integer.parseInt(parts[2]), "English").equals("null"))
                            id = Integer.parseInt(parts[2]);
                        else if (IDIndex.blockNameToID(parts[2]) != -2) id = IDIndex.blockNameToID(parts[2]);
                        if (isIntNumber(parts[3])) amount = Integer.parseInt(parts[3]);
                        if (id == -2) {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(69, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[2] + " is not an available number or name");
                        } else if (amount == -2) {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[3] + " is not an available number");
                        } else {
                            int amountLeft = 0;
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[1])) {
                                        amountLeft = player.getItem(id, amount, 36, true);
                                    }
                                }
                            }
                            showText(WordList.getWord(68, game.getLanguage()) + parts[1] + " " + IDIndex.blockIDToName(id, game.getLanguage()) + " * " + (amount - amountLeft), "green");
                            if (printInformation)
                                System.out.println("Given: " + parts[1] + " " + IDIndex.blockIDToName(id, "English") + " * " + (amount - amountLeft));
                        }
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[1] + " player not exist or online");
                    }
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/give playerName itemId / Name amount", "orange");
                if (printInformation) System.out.println("Usage: /give playerName itemId / Name amount");
            }
        } else if (Objects.equals(parts[0], "/clear")) {
            if (parts.length == 2) {
                if (game.getWorldCurrent() != null) {
                    if (isPlayerOnline(parts[1])) {
                        for (Entity entity : game.getWorldCurrent().getEntityList()) {
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (player.getName().equals(parts[1])) {
                                    for (int i = 0; i < 36; i++) {
                                        player.getItemBarAmount()[i] = 0;
                                        player.getItemBarId()[i] = -1;
                                    }
                                }
                            }
                        }
                        showText(parts[1] + WordList.getWord(81, game.getLanguage()), "green");
                        if (printInformation)
                            System.out.println("Cleared: " + parts[1]);
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[1] + " player not exist or online");
                    }
                }
            } else if (parts.length == 3) {
                if (game.getWorldCurrent() != null) {
                    if (isPlayerOnline(parts[1])) {
                        int id = -2;
                        if (isIntNumber(parts[2]) && !IDIndex.blockIDToName(Integer.parseInt(parts[2]), "English").equals("null"))
                            id = Integer.parseInt(parts[2]);
                        else if (IDIndex.blockNameToID(parts[2]) != -2) id = IDIndex.blockNameToID(parts[2]);
                        if (id == -2) {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(69, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[2] + " is not an available number or name");
                        } else {
                            int amountCleared = 0;
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[1])) {
                                        for (int i = 0; i < 36; i++) {
                                            if (player.getItemBarId()[i] == id) {
                                                amountCleared += player.getItemBarAmount()[i];
                                                player.getItemBarAmount()[i] = 0;
                                                player.getItemBarId()[i] = -1;
                                            }
                                        }
                                    }
                                }
                            }
                            showText(parts[1] + " " + WordList.getWord(82, game.getLanguage()) + IDIndex.blockIDToName(id, game.getLanguage()) + " * " + amountCleared, "green");
                            if (printInformation)
                                System.out.println("Cleared: " + parts[1] + " " + IDIndex.blockIDToName(id, "English") + " * " + amountCleared);
                        }
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[1] + " player not exist or online");
                    }
                }
            } else if (parts.length == 4) {
                if (game.getWorldCurrent() != null) {
                    if (isPlayerOnline(parts[1])) {
                        int id = -2;
                        int amount = -2;
                        if (isIntNumber(parts[2]) && !IDIndex.blockIDToName(Integer.parseInt(parts[2]), "English").equals("null"))
                            id = Integer.parseInt(parts[2]);
                        else if (IDIndex.blockNameToID(parts[2]) != -2) id = IDIndex.blockNameToID(parts[2]);
                        if (isIntNumber(parts[3])) amount = Integer.parseInt(parts[3]);
                        if (id == -2) {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(69, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[2] + " is not an available number or name");
                        } else if (amount == -2) {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[3] + " is not an available number");
                        } else {
                            int amountCleared = 0;
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[1])) {
                                        for (int i = 0; i < 36; i++) {
                                            if (player.getItemBarId()[i] == id) {
                                                while (player.getItemBarAmount()[i] > 0) {
                                                    if (amountCleared >= amount) break;
                                                    amountCleared += 1;
                                                    player.getItemBarAmount()[i] -= 1;
                                                }
                                            }
                                            if (amountCleared >= amount) break;
                                        }
                                    }
                                }
                            }
                            showText(parts[1] + " " + WordList.getWord(82, game.getLanguage()) + IDIndex.blockIDToName(id, game.getLanguage()) + " * " + amountCleared, "green");
                            if (printInformation)
                                System.out.println("Cleared: " + parts[1] + " " + IDIndex.blockIDToName(id, "English") + " * " + amountCleared);
                        }
                    } else {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[1] + " player not exist or online");
                    }
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/clear playerName itemId / Name amount", "orange");
                if (printInformation) System.out.println("Usage: /clear playerName itemId / Name amount");
            }
        } else if (Objects.equals(parts[0], "/summon")) {
            if (parts.length == 4) {
                if (IDIndex.nameToIsEntity(parts[1])) {
                    int xBlock = -2;
                    int yBlock = -2;
                    if (isIntNumber(parts[2]))
                        xBlock = (Integer.parseInt(parts[2]) + game.getWorldCurrent().getWidth() / 2);
                    if (isIntNumber(parts[3]))
                        yBlock = (-Integer.parseInt(parts[3]) + game.getWorldCurrent().getHeight() / 2);
                    if (xBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error:" + parts[2] + " is not an available number");
                    } else if (yBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error:" + parts[3] + " is not an available number");
                    } else {
                        if (game.getWorldCurrent() != null)
                            if (parts[1].equals("zombie")) {
                                game.getWorldCurrent().getEntityList().add(new Zombie(xBlock * 50 - 20 / 2, yBlock * 50 - 95 / 2, game.getWorldCurrent()));
                                showText(IDIndex.entityNameToName(parts[1], game.getLanguage()) + WordList.getWord(84, game.getLanguage()) + parts[2] + " " + parts[3], "green");
                                if (printInformation)
                                    System.out.println("Summoned: " + IDIndex.entityNameToName(parts[1], "English") + " " + parts[2] + " " + parts[3]);
                            }
                    }
                } else {
                    showText(parts[1] + " " + WordList.getWord(83, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available entity name");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/summon entityName xBlock yBlock", "orange");
                if (printInformation) System.out.println("Usage: /summon entityName xBlock yBlock");
            }
        } else if (Objects.equals(parts[0], "/gama")) {
            if (parts.length == 3 && parts[1].equals("set")) {
                if (isDoubleNumber(parts[2])) {
                    game.getWorldCurrent().setGama(Double.parseDouble(parts[2]));
                    if (printInformation) System.out.println("Gama set: " + parts[2]);
                    game.updateWorldTexture();
                    game.updateVisionLightIntensity();
                    showText(WordList.getWord(85, game.getLanguage()) + parts[2], "green");
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[2] + " is not an available number");
                }
            } else if (parts.length == 1) {
                double gama = game.getWorldCurrent().getGama();
                if (printInformation) System.out.println("Gama: " + gama);
                showText(WordList.getWord(86, game.getLanguage()) + gama, "green");
            } else {
                if (printInformation) System.out.println("Usage: /gama set newGama or /gama");
                showText(WordList.getWord(75, game.getLanguage()) + "/gama set newGama or /gama", "orange");
            }
        } else if (Objects.equals(parts[0], "/gravity")) {
            if (parts.length == 3 && parts[1].equals("set")) {
                if (isDoubleNumber(parts[2])) {
                    game.getWorldCurrent().setGravity(Double.parseDouble(parts[2]));
                    if (printInformation) System.out.println("Gravity set: " + parts[2]);
                    game.updateWorldTexture();
                    game.updateVisionLightIntensity();
                    showText(WordList.getWord(87, game.getLanguage()) + parts[2], "green");
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[2] + " is not an available number");
                }
            } else if (parts.length == 1) {
                double gravity = game.getWorldCurrent().getGravity();
                if (printInformation) System.out.println("Gravity: " + gravity);
                showText(WordList.getWord(88, game.getLanguage()) + gravity, "green");
            } else {
                if (printInformation) System.out.println("Usage: /gravity set newGravity or /gravity");
                showText(WordList.getWord(75, game.getLanguage()) + "/gravity set newGravity or /gravity", "orange");
            }
        } else if (Objects.equals(parts[0], "/resistance")) {
            if (parts.length >= 2) {
                if (parts[1].equals("air")) {
                    if (parts.length == 4 && parts[2].equals("set")) {
                        if (isDoubleNumber(parts[3])) {
                            game.getWorldCurrent().setAirResistance(Double.parseDouble(parts[3]));
                            if (printInformation) System.out.println("Air resistance set: " + parts[3]);
                            game.updateWorldTexture();
                            game.updateVisionLightIntensity();
                            showText(WordList.getWord(90, game.getLanguage()) + parts[3], "green");
                        } else {
                            showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                            if (printInformation)
                                System.out.println("Error: " + parts[3] + " is not an available number");
                        }
                    } else if (parts.length == 2) {
                        double airResistance = game.getWorldCurrent().getAirResistance();
                        if (printInformation) System.out.println("Air resistance: " + airResistance);
                        showText(WordList.getWord(91, game.getLanguage()) + airResistance, "green");
                    } else {
                        if (printInformation)
                            System.out.println("Usage: /resistance object set value or /gravity object");
                        showText(WordList.getWord(75, game.getLanguage()) + "/resistance object set value or /gravity object", "orange");
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(89, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available object");
                }
            } else {
                if (printInformation)
                    System.out.println("Usage: /resistance object set value or /gravity object");
                showText(WordList.getWord(75, game.getLanguage()) + "/resistance object set value or /gravity object", "orange");
            }
        } else if (Objects.equals(parts[0], "/spawnpoint")) {
            if (parts.length == 4) {
                if (isPlayerOnline(parts[1])) {
                    int xBlock = -2;
                    int yBlock = -2;
                    if (isIntNumber(parts[2]))
                        xBlock = (Integer.parseInt(parts[2]) + (game.getWorldCurrent().getWidth() / 2));
                    if (isIntNumber(parts[3]))
                        yBlock = (-Integer.parseInt(parts[3]) + (game.getWorldCurrent().getHeight() / 2));
                    if (xBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[2] + " is not an available number");
                    } else if (yBlock == -2) {
                        showText(WordList.getWord(76, game.getLanguage()) + parts[3] + WordList.getWord(73, game.getLanguage()), "orange");
                        if (printInformation)
                            System.out.println("Error: " + parts[3] + " is not an available number");

                    } else {
                        if (game.getWorldCurrent() != null)
                            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                                if (entity instanceof Player) {
                                    Player player = (Player) entity;
                                    if (player.getName().equals(parts[1])) {
                                        player.setxSpawn(xBlock * 50 - player.getWidth() / 2);
                                        player.setySpawn(yBlock * 50 - player.getHeight() / 2);
                                    }
                                }
                            }
                        showText(parts[1] + WordList.getWord(92, game.getLanguage()) + parts[2] + " " + parts[3], "green");
                        if (printInformation)
                            System.out.println("Spawnpointed: " + parts[1] + " " + parts[2] + " " + parts[3]);
                    }
                } else {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + " " + WordList.getWord(77, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " player not exist or online");
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/spawnpoint playerName xBlock yBlock", "orange");
                if (printInformation)
                    System.out.println("Usage: /spawnpoint playerName xBlock yBlock");
            }
        } else if (Objects.equals(parts[0], "/spawnworld")) {
            if (parts.length == 3) {
                int xBlock = -2;
                int yBlock = -2;
                if (isIntNumber(parts[1]))
                    xBlock = (Integer.parseInt(parts[1]) + (game.getWorldCurrent().getWidth() / 2));
                if (isIntNumber(parts[2]))
                    yBlock = (-Integer.parseInt(parts[2]) + (game.getWorldCurrent().getHeight() / 2));
                if (xBlock == -2) {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[1] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[1] + " is not an available number");
                } else if (yBlock == -2) {
                    showText(WordList.getWord(76, game.getLanguage()) + parts[2] + WordList.getWord(73, game.getLanguage()), "orange");
                    if (printInformation)
                        System.out.println("Error: " + parts[2] + " is not an available number");
                } else {
                    game.getWorldCurrent().setxSpawn(xBlock * 50 - 20 / 2);
                    game.getWorldCurrent().setySpawn(yBlock * 50 - 95 / 2);
                    showText(WordList.getWord(93, game.getLanguage()) + parts[1] + " " + parts[2], "green");
                    if (printInformation)
                        System.out.println("Spawnworlded: " + parts[1] + " " + parts[2]);
                }
            } else {
                showText(WordList.getWord(75, game.getLanguage()) + "/spawnworld xBlock yBlock", "orange");
                if (printInformation)
                    System.out.println("Usage: /spawnworld xBlock yBlock");
            }
        } else {
            showText(WordList.getWord(76, game.getLanguage()) + WordList.getWord(65, game.getLanguage()), "orange");
            if (printInformation) System.out.println("Error: Unknown command");
        }
    }

    public static void waitLinkState() {
        while (!game.getLinkState()[0]) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void waitLinkState12() {
        while (!game.getLinkState()[12]) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void showText(String text, String color) {
        if (game.getFrameSort() == 0) {
            int tmpWidth = (int) (game.getWidth() * 0.06);
            int tmpHeight = (int) (game.getHeight() * 0.06);
            int minValue = min(tmpWidth, tmpHeight);
            Graphics graphics = game.getOffScreenUiImage().getGraphics();
            graphics.setFont(Resource.getFontList().get(0).deriveFont((float) (minValue / 2.3)));
            FontMetrics metrics = graphics.getFontMetrics();
            int i = 0;
            while (i < text.length()) {
                int j = i + 1;
                while (metrics.stringWidth(text.substring(i, j)) < 0.38 * game.getWidth()) {
                    if (text.length() == j) break;
                    j++;
                }
                game.getChatTextList().add(new ChatText(game, text.substring(i, j), color, 600, false));
                i = j;
            }
        }
    }

    public static boolean isIntNumber(String string) {
        int intValue;
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public static boolean isDoubleNumber(String string) {
        double value;
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            value = Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public static boolean isBooleanString(String string) {
        if (string.equals("true") || string.equals(("True")))
            return true;
        else if (string.equals("false") || string.equals(("False")))
            return true;
        else if (string.equals("1"))
            return true;
        else if (string.equals("0"))
            return true;
        return false;
    }

    public static boolean isPlayerOnline(String name) {
        if (game.getWorldCurrent() != null) {
            for (Entity entity : game.getWorldCurrent().getEntityList()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (player.getName().equals(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
