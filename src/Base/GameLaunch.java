package Base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GameLaunch {
    // 储存材质文件根目录
    private static String pathTexture = System.getProperty("user.dir") + "/data/resource/";
    private int width = 900;
    private int height = 600;

    public GameLaunch(String language) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame();
        if (Objects.equals(language, "English")) {
            frame.setTitle("FlatMC Launcher");
        } else if (Objects.equals(language, "Chinese")) {
            frame.setTitle("我的世界2D版 启动器");
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());

        // 布局GUI
        placeComponents(frame, language);
    }

    private void placeComponents(JFrame frame, String language) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(pathTexture + "font/MinecraftAE.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 10, 30, 30));
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0)); // 使用FlowLayout，水平排列，居中对齐
        JLabel nameLabel = new JLabel();
        JTextField nameText = new JTextField();
        JLabel resolutionLabel = new JLabel();
        JTextField widthText = new JTextField("1760");
        JTextField heightText = new JTextField("990");
        widthText.setColumns(7);
        heightText.setColumns(7);
        JLabel xText = new JLabel("x");
        JButton launchButton = new JButton();
        if (Objects.equals(language, "English")) {
            nameLabel.setText("Please enter player name:");
            resolutionLabel.setText("Please enter window resolution:");
            launchButton.setText("Game Start");
            nameText.setText("boyanez");
        } else if (Objects.equals(language, "Chinese")) {
            nameLabel.setText("请输入玩家名：");
            resolutionLabel.setText("请输入窗口分辨率：");
            launchButton.setText("开始游戏");
            nameText.setText("薄言");
        }

        if (font != null) {
            nameLabel.setFont(font.deriveFont((float) (25)));
            nameText.setFont(font.deriveFont((float) (25)));
            resolutionLabel.setFont(font.deriveFont((float) (25)));
            widthText.setFont(font.deriveFont((float) (25)));
            heightText.setFont(font.deriveFont((float) (25)));
            launchButton.setFont(font.deriveFont((float) (35)));
            xText.setFont(font.deriveFont((float) (25)));
        }

        inputPanel.add(widthText);
        inputPanel.add(xText);
        inputPanel.add(heightText);

        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameText.getText();
                width = Integer.parseInt(widthText.getText());
                height = Integer.parseInt(heightText.getText());
                new GameFrame(playerName, width, height, language);
                ((Window) SwingUtilities.getWindowAncestor(inputPanel)).dispose();
            }
        });

        mainPanel.add(new JLabel(""));
        mainPanel.add(nameLabel);
        mainPanel.add(nameText);
        mainPanel.add(resolutionLabel);
        mainPanel.add(inputPanel);
        mainPanel.add(launchButton);
        frame.add(mainPanel);
    }
}
