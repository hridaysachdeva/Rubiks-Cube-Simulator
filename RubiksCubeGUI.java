package com.RubiksCubeSimulator;

import javax.swing.*;
import java.awt.*;

public class RubiksCubeGUI extends JFrame {
    private RubiksCube cube;
    private JPanel[][][] facePanels; // [face][row][col]
    private static final String[] faceOrder = {"up", "left", "front", "right", "back", "down"};
    private static final Color[] faceColors = {
            Color.BLUE, Color.ORANGE, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN
    };

    public RubiksCubeGUI() {
        cube = new RubiksCube();
        facePanels = new JPanel[6][3][3];

        setTitle("Rubik's Cube Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel cubePanel = new JPanel();
        cubePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // Add UP face
        gbc.gridx = 1; gbc.gridy = 0;
        cubePanel.add(createFacePanel(0), gbc);

        // Add LEFT, FRONT, RIGHT, BACK faces
        gbc.gridy = 1;
        for (int i = 1; i <= 4; i++) {
            gbc.gridx = i - 1;
            cubePanel.add(createFacePanel(i), gbc);
        }

        // Add DOWN face
        gbc.gridx = 1; gbc.gridy = 2;
        cubePanel.add(createFacePanel(5), gbc);

        add(cubePanel, BorderLayout.CENTER);

        // === Controls ===
        JPanel controlPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        String[] moves = {"F", "Fp", "R", "Rp", "U", "Up", "L", "Lp", "B", "Bp", "D", "Dp"};

        for (String move : moves) {
            JButton btn = new JButton(move);
            btn.addActionListener(e -> {
                applyMove(move);
                repaintCube();
            });
            controlPanel.add(btn);
        }

        // === Reset and Pattern Panel ===
        JPanel topPanel = new JPanel(new FlowLayout());

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            cube = new RubiksCube();
            repaintCube();
        });
        topPanel.add(resetButton);

        JButton checkerboardButton = new JButton("Checkerboard");
        checkerboardButton.addActionListener(e -> {
            applyMoveSequence("F F B B U U D D L L R R");
            repaintCube();
        });
        topPanel.add(checkerboardButton);

        JButton cubeInCubeButton = new JButton("Cube in a Cube");
        cubeInCubeButton.addActionListener(e -> {
            applyMoveSequence("F L F Up R U F F L L Up Lp B Dp Bp L L U");
            repaintCube();
        });
        topPanel.add(cubeInCubeButton);

        add(controlPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        repaintCube(); // Draw initial state
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createFacePanel(int faceIndex) {
        JPanel panel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(40, 40));
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                facePanels[faceIndex][i][j] = cell;
                panel.add(cell);
            }
        }
        return panel;
    }

    private void repaintCube() {
        for (int f = 0; f < 6; f++) {
            char[][] face = cube.getFace(faceOrder[f]);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    facePanels[f][i][j].setBackground(colorFromChar(face[i][j]));
                }
            }
        }
    }

    private Color colorFromChar(char c) {
        return switch (c) {
            case 'W' -> Color.WHITE;
            case 'Y' -> Color.YELLOW;
            case 'B' -> Color.BLUE;
            case 'G' -> Color.GREEN;
            case 'R' -> Color.RED;
            case 'O' -> Color.ORANGE;
            default -> Color.GRAY;
        };
    }

    private void applyMove(String move) {
        switch (move.toLowerCase()) {
            case "f" -> cube.F();
            case "fp" -> cube.Fp();
            case "r" -> cube.R();
            case "rp" -> cube.Rp();
            case "u" -> cube.U();
            case "up" -> cube.Up();
            case "l" -> cube.L();
            case "lp" -> cube.Lp();
            case "b" -> cube.B();
            case "bp" -> cube.Bp();
            case "d" -> cube.D();
            case "dp" -> cube.Dp();
        }
    }

    private void applyMoveSequence(String sequence) {
        String[] moves = sequence.split(" ");
        for (String move : moves) {
            applyMove(move);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RubiksCubeGUI::new);
    }
}
