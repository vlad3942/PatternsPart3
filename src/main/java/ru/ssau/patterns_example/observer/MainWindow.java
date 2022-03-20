package ru.ssau.patterns_example.observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MainWindow {

    public static final String FILE_NAME = "Vladik.png";

    private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();
    private BufferedImage mainPicture;

    private JFrame jFrame;
    private JLabel picLabel;

    private final LeftEye leftEyeListener = new LeftEye(this);
    private final RightEye rightEyeListener = new RightEye(this);
    private final Nose noseListener = new Nose(this);
    private final Mouth mouthListener = new Mouth(this);

    public MainWindow() {
        init();
    }

    public void readMaimPicture() {
        try {
            mainPicture = ImageIO.read(Objects.requireNonNull(CLASS_LOADER.getResource(FILE_NAME)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    public void init() {
        readMaimPicture();

        picLabel = new JLabel(new ImageIcon(mainPicture));
        picLabel.addMouseListener(leftEyeListener);
        picLabel.addMouseListener(rightEyeListener);
        picLabel.addMouseListener(noseListener);
        picLabel.addMouseListener(mouthListener);

        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);

        jFrame = new JFrame();
        jFrame.add(jPanel);
        jFrame.revalidate();
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public void redraw() {
        readMaimPicture();
        Graphics2D gr = (Graphics2D) mainPicture.getGraphics();
        if (!leftEyeListener.isOpened()) changeImage(gr, LeftEye.FILE_NAME);
        if (!rightEyeListener.isOpened()) changeImage(gr, RightEye.FILE_NAME);
        if (!mouthListener.isNormal()) changeImage(gr, Mouth.FILE_NAME);
        if (!noseListener.isNormal()) changeImage(gr, Nose.FILE_NAME);

        picLabel.setIcon(new ImageIcon(mainPicture));

        jFrame.repaint();
    }

    private static void changeImage(final Graphics2D gr, final String fileName) {
        try {
            BufferedImage nose = ImageIO.read(Objects.requireNonNull(CLASS_LOADER.getResource(fileName)));
            gr.drawImage(nose, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
