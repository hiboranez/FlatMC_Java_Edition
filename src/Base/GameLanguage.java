package Base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameLanguage {
    // 储存材质文件根目录
    private static String pathTexture = System.getProperty("user.dir") + "/data/resource/";
    private String language = "English";

    public GameLanguage() {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("Language Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());

        // 布局GUI
        placeComponents(frame);
    }

    private void placeComponents(JFrame frame) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(pathTexture + "font/MinecraftAE.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 10, 30, 30));
        JLabel nameLabel = new JLabel("Choose your language:");
        JButton launchButton1 = new JButton("English");
        JButton launchButton2 = new JButton("中文");

        if (font != null) {
            nameLabel.setFont(font.deriveFont((float) (25)));
            launchButton1.setFont(font.deriveFont((float) (35)));
            launchButton2.setFont(font.deriveFont((float) (35)));
        }

        launchButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                language = "English";
                new GameLaunch(language);
                ((Window) SwingUtilities.getWindowAncestor(mainPanel)).dispose();
            }
        });

        launchButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                language = "Chinese";
                new GameLaunch(language);
                ((Window) SwingUtilities.getWindowAncestor(mainPanel)).dispose();
            }
        });

        mainPanel.add(new JLabel(""));
        mainPanel.add(nameLabel);
        mainPanel.add(launchButton1);
        mainPanel.add(launchButton2);
        frame.add(mainPanel);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 传递行和列的参数
                new GameLanguage();
            }
        });
    }
}
