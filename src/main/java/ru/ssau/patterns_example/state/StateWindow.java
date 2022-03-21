package ru.ssau.patterns_example.state;

import ru.ssau.patterns_example.observer.LeftEye;
import ru.ssau.patterns_example.observer.Mouth;
import ru.ssau.patterns_example.observer.Nose;
import ru.ssau.patterns_example.observer.RightEye;
import ru.ssau.patterns_example.state.state_impls.InitialState;
import ru.ssau.patterns_example.state.state_impls.SemesterState;
import ru.ssau.patterns_example.state.state_impls.SessionState;
import ru.ssau.patterns_example.state.state_impls.VacationState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StateWindow {

    public final StudentContextHolder studentContextHolder = new StudentContextHolder(new InitialState());

    public static final String FILE_NAME = "Vladik.png";

    private BufferedImage mainPicture;

    private JFrame jFrame;
    private JLabel picLabel;


    public StateWindow() {
        init();
    }

    public void readMaimPicture() {
        try {
            mainPicture = studentContextHolder.getStateDependentImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StateWindow();
    }

    public void init() {
        readMaimPicture();

        picLabel = new JLabel(new ImageIcon(mainPicture));

        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);

        JButton semesterButton = new JButton("Семестр");
        JButton sessionButton = new JButton("Сессия");
        JButton vacationButton = new JButton("Каникулы");

        semesterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                studentContextHolder.changeState(SemesterState.getInstance());
                redraw();
            }
        });

        sessionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                studentContextHolder.changeState(SessionState.getInstance());
                redraw();
            }
        });
        vacationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                studentContextHolder.changeState(VacationState.getInstance());
                redraw();
            }
        });

        final Panel panel = new Panel();
        panel.add(semesterButton);
        panel.add(sessionButton);
        panel.add(vacationButton);

        jPanel.add(panel);

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
        picLabel.setIcon(new ImageIcon(mainPicture));
        jFrame.repaint();
    }
}
