package Utils.GUI;

public class Crack {
    private String playerName;
    private int xBlock;
    private int yBlock;
    private int crackSort;

    public Crack(String playerName, int xBlock, int yBlock, int crackSort) {
        this.playerName = playerName;
        this.xBlock = xBlock;
        this.yBlock = yBlock;
        this.crackSort = crackSort;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getxBlock() {
        return xBlock;
    }

    public void setxBlock(int xBlock) {
        this.xBlock = xBlock;
    }

    public int getyBlock() {
        return yBlock;
    }

    public void setyBlock(int yBlock) {
        this.yBlock = yBlock;
    }

    public int getCrackSort() {
        return crackSort;
    }

    public void setCrackSort(int crackSort) {
        this.crackSort = crackSort;
    }
}
