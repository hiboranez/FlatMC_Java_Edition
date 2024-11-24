package Utils.OtherTool;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Random;

public class PlaySound {
    // 定义播放器
    private AdvancedPlayer player;
    // 定义是否结束
    private boolean Ended = false;
    // 定义音效文件路径
    private static String path = System.getProperty("user.dir") + "/data/resource/sound/";

    public PlaySound(String type, String fileName, int volume) {
        startPlay(type, fileName, volume);
    }

    // 停止播放音效
    public void stopPlay() {
        if (player != null) {
            player.close();
            Ended = true;
        }
    }

    // 播放音效
    public PlaySound(String type, int sort, int volume) {
        //音效类型为music
        if (Objects.equals(type, "music")) {
            if (sort == 0) startPlay("music", "calm1", volume);//calm1
            else if (sort == 1) startPlay("music", "calm2", volume);//calm2
            else if (sort == 2) startPlay("music", "calm3", volume);//calm3
            else if (sort == 3) startPlay("music", "hal1", volume);//hal1
            else if (sort == 4) startPlay("music", "hal2", volume);//hal2
            else if (sort == 5) startPlay("music", "hal3", volume);//hal3
            else if (sort == 6) startPlay("music", "hal4", volume);//hal4
            else if (sort == 7) startPlay("music", "nuance1", volume);//nuance1
            else if (sort == 8) startPlay("music", "nuance2", volume);//nuance2
            else if (sort == 9) startPlay("music", "piano1", volume);//piano1
            else if (sort == 10) startPlay("music", "WetHands", volume);//WetHands
            else if (sort == 11) startPlay("music", "MoogCity2", volume);//MoogCity2
        }

        //音效类型为dig
        if (Objects.equals(type, "dig")) {
            if (sort == 0) digGravel(volume);//gravel
            else if (sort == 1) digGrass(volume);//grass
            else if (sort == 2) digStone(volume);//stone
            else if (sort == 3) digStone(volume);//stone
            else if (sort == 4) digStone(volume);//stone
            else if (sort == 5) digWood(volume);//wood
            else if (sort == 6) digWood(volume);//wood
            else if (sort == 7) digGrass(volume);//grass
            else if (sort == 8) digWood(volume);//wood
            else if (sort == 9) digStone(volume);//stone
            else if (sort == 10) digStone(volume);//stone
            else if (sort == 11) digStone(volume);//stone
            else if (sort == 12) digStone(volume);//stone
            else if (sort == 13) digStone(volume);//stone
            else if (sort == 14) digStone(volume);//stone
            else if (sort == 15) digStone(volume);//stone
            else if (sort == 16) digStone(volume);//stone
            else if (sort == 17) digWood(volume);//wood
            else if (sort == 18) digWood(volume);//wood
            else if (sort == 19) digStone(volume);//stone
            else if (sort == 20) digStone(volume);//stone
            else if (sort == 21) digStone(volume);//stone
            else if (sort == 22) digStone(volume);//stone
            else if (sort == 23) digStone(volume);//stone
            else if (sort == 24) digStone(volume);//stone
            else if (sort == 25) digStone(volume);//stone
            else if (sort == 26) digStone(volume);//stone
            else if (sort == 27) digStone(volume);//stone
            else if (sort == 28) digStone(volume);//stone
            else if (sort == 29) digStone(volume);//stone
            else if (sort == 30) digStone(volume);//stone
            else if (sort == 31) digStone(volume);//stone
            else if (sort == 32) digStone(volume);//stone
            else if (sort == 33) digStone(volume);//stone
            else if (sort == 34) digStone(volume);//stone
            else if (sort == 35) digStone(volume);//stone
            else if (sort == 36) digStone(volume);//stone
            else if (sort == 37) digStone(volume);//stone
            else if (sort == 38) digStone(volume);//stone
            else if (sort == 39) digStone(volume);//stone
            else if (sort == 40) digStone(volume);//stone
            else if (sort == 41) digStone(volume);//stone
            else if (sort == 42) digStone(volume);//stone
        }

        //音效类型为step
        if (Objects.equals(type, "step")) {
            if (sort == 0) stepGravel(volume);//gravel
            else if (sort == 1) stepGrass(volume);//grass
            else if (sort == 2) stepStone(volume);//stone
            else if (sort == 3) stepStone(volume);//stone
            else if (sort == 4) stepStone(volume);//stone
            else if (sort == 5) stepWood(volume);//wood
            else if (sort == 6) stepWood(volume);//wood
            else if (sort == 7) stepGrass(volume);//grass
            else if (sort == 8) stepWood(volume);//wood
            else if (sort == 9) stepStone(volume);//stone
            else if (sort == 10) stepStone(volume);//stone
            else if (sort == 11) stepStone(volume);//stone
            else if (sort == 12) stepStone(volume);//stone
            else if (sort == 13) stepStone(volume);//stone
            else if (sort == 14) stepStone(volume);//stone
            else if (sort == 15) stepStone(volume);//stone
            else if (sort == 16) stepStone(volume);//stone
            else if (sort == 17) stepWood(volume);//wood
            else if (sort == 18) stepWood(volume);//wood
            else if (sort == 19) stepStone(volume);//stone
            else if (sort == 20) stepStone(volume);//stone
            else if (sort == 21) stepStone(volume);//stone
            else if (sort == 22) stepStone(volume);//stone
            else if (sort == 23) stepStone(volume);//stone
            else if (sort == 24) stepStone(volume);//stone
            else if (sort == 25) stepStone(volume);//stone
            else if (sort == 26) stepStone(volume);//stone
            else if (sort == 27) stepStone(volume);//stone
            else if (sort == 28) stepStone(volume);//stone
            else if (sort == 29) stepStone(volume);//stone
            else if (sort == 30) stepStone(volume);//stone
            else if (sort == 31) stepStone(volume);//stone
            else if (sort == 32) stepStone(volume);//stone
            else if (sort == 33) stepStone(volume);//stone
            else if (sort == 34) stepStone(volume);//stone
            else if (sort == 35) stepStone(volume);//stone
            else if (sort == 36) stepStone(volume);//stone
            else if (sort == 37) stepStone(volume);//stone
            else if (sort == 38) stepStone(volume);//stone
            else if (sort == 39) stepStone(volume);//stone
            else if (sort == 40) stepStone(volume);//stone
            else if (sort == 41) stepStone(volume);//stone
            else if (sort == 42) stepStone(volume);//stone
        }

        //音效类型为player
        if (Objects.equals(type, "player")) {
            if (sort == 0) startPlay("player", "pop", volume);//pop
            if (sort == 1) startPlay("player", "hurt", volume);//hurt
            if (sort == 2) eat(volume);//eat
            if (sort == 3) startPlay("player", "hiccup", volume);//hiccup
        }

        //音效类型为damage
        if (Objects.equals(type, "damage")) {
            if (sort == 0) hit(volume);//hit
            if (sort == 1) startPlay("damage", "fallbig", volume);//fallbig
            if (sort == 2) startPlay("damage", "fallsmall", volume);//fallsmall
            if (sort == 3) startPlay("damage", "toolbreak", volume);//fallsmall
        }

        //音效类型为gui
        if (Objects.equals(type, "gui")) {
            if (sort == 0) startPlay("gui", "click", volume);//click
        }

        //音效类型为zombie
        if (Objects.equals(type, "zombie")) {
            if (sort == 0) zombieHurt(volume);//hurt
            if (sort == 1) startPlay("zombie", "death", volume);//death
            if (sort == 2) zombieSay(volume);//say
        }
    }

    public void startPlay(String type, String fileName, int volume) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + type + "/" + fileName + ".mp3");
            player = new AdvancedPlayer(fileInputStream);

            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    player.close();
                    Ended = true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 定义播放线程
        Thread playerThread = new Thread(() -> {
            try {
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 开启线程
        playerThread.start();
    }

    // 随机化挖掘gravel音效
    public void digGravel(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        startPlay("dig", "gravel" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化挖掘grass音效
    public void digGrass(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        startPlay("dig", "grass" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化挖掘stone音效
    public void digStone(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        startPlay("dig", "stone" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化挖掘wood音效
    public void digWood(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        startPlay("dig", "wood" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化踩踏gravel音效
    public void stepGravel(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        startPlay("step", "gravel" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化踩踏grass音效
    public void stepGrass(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(6);
        startPlay("step", "grass" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化踩踏stone音效
    public void stepStone(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(6);
        startPlay("step", "stone" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化踩踏wood音效
    public void stepWood(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(6);
        startPlay("step", "wood" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化击打音效
    public void hit(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        startPlay("damage", "hit" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化僵尸受伤音效
    public void zombieHurt(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        startPlay("zombie", "hurt" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化僵尸说话音效
    public void zombieSay(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        startPlay("zombie", "say" + String.valueOf(randomNumber + 1), volume);
    }

    // 随机化吃东西音效
    public void eat(int volume) {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        startPlay("player", "eat" + String.valueOf(randomNumber + 1), volume);
    }

    public AdvancedPlayer getPlayer() {
        return player;
    }

    public void setPlayer(AdvancedPlayer player) {
        this.player = player;
    }

    public boolean isEnded() {
        return Ended;
    }

    public void setEnded(boolean ended) {
        Ended = ended;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        PlaySound.path = path;
    }

}
