package com.RubiksCubeSimulator;

import java.util.HashMap;
import java.util.Map;

public class RubiksCube {
    private static final char W = 'W';
    private static final char Y = 'Y';
    private static final char B = 'B';
    private static final char G = 'G';
    private static final char R = 'R';
    private static final char O = 'O';

    private final Map<String, char[][]> faces = new HashMap<>();

    public RubiksCube() {
        faces.put("up", createFace(B));
        faces.put("down", createFace(G));
        faces.put("left", createFace(O));
        faces.put("right", createFace(R));
        faces.put("front", createFace(W));
        faces.put("back", createFace(Y));
    }

    private char[][] createFace(char color) {
        char[][] face = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                face[i][j] = color;
        return face;
    }

    public char[][] getFace(String name) {
        return faces.get(name);
    }

    private void rotateFaceClockwise(char[][] face) {
        char[][] temp = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                temp[i][j] = face[2 - j][i];
        for (int i = 0; i < 3; i++)
            System.arraycopy(temp[i], 0, face[i], 0, 3);
    }

    public void F() {
        rotateFaceClockwise(faces.get("front"));
        char[] temp = faces.get("up")[2].clone();
        for (int i = 0; i < 3; i++) faces.get("up")[2][i] = faces.get("left")[2 - i][2];
        for (int i = 0; i < 3; i++) faces.get("left")[i][2] = faces.get("down")[0][i];
        for (int i = 0; i < 3; i++) faces.get("down")[0][i] = faces.get("right")[2 - i][0];
        for (int i = 0; i < 3; i++) faces.get("right")[i][0] = temp[i];
    }

    public void B() {
        rotateFaceClockwise(faces.get("back"));
        char[] temp = faces.get("up")[0].clone();
        for (int i = 0; i < 3; i++) faces.get("up")[0][i] = faces.get("right")[i][2];
        for (int i = 0; i < 3; i++) faces.get("right")[i][2] = faces.get("down")[2][2 - i];
        for (int i = 0; i < 3; i++) faces.get("down")[2][i] = faces.get("left")[i][0];
        for (int i = 0; i < 3; i++) faces.get("left")[2 - i][0] = temp[i];
    }

    public void U() {
        rotateFaceClockwise(faces.get("up"));
        char[] temp = faces.get("front")[0].clone();
        faces.get("front")[0] = faces.get("right")[0];
        faces.get("right")[0] = faces.get("back")[0];
        faces.get("back")[0] = faces.get("left")[0];
        faces.get("left")[0] = temp;
    }

    public void D() {
        rotateFaceClockwise(faces.get("down"));
        char[] temp = faces.get("front")[2].clone();
        faces.get("front")[2] = faces.get("left")[2];
        faces.get("left")[2] = faces.get("back")[2];
        faces.get("back")[2] = faces.get("right")[2];
        faces.get("right")[2] = temp;
    }

    public void L() {
        rotateFaceClockwise(faces.get("left"));
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) temp[i] = faces.get("up")[i][0];
        for (int i = 0; i < 3; i++) faces.get("up")[i][0] = faces.get("back")[2 - i][2];
        for (int i = 0; i < 3; i++) faces.get("back")[i][2] = faces.get("down")[2 - i][0];
        for (int i = 0; i < 3; i++) faces.get("down")[i][0] = faces.get("front")[i][0];
        for (int i = 0; i < 3; i++) faces.get("front")[i][0] = temp[i];
    }

    public void R() {
        rotateFaceClockwise(faces.get("right"));
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) temp[i] = faces.get("up")[i][2];
        for (int i = 0; i < 3; i++) faces.get("up")[i][2] = faces.get("front")[i][2];
        for (int i = 0; i < 3; i++) faces.get("front")[i][2] = faces.get("down")[i][2];
        for (int i = 0; i < 3; i++) faces.get("down")[i][2] = faces.get("back")[2 - i][0];
        for (int i = 0; i < 3; i++) faces.get("back")[i][0] = temp[2 - i];
    }

    public void Fp() { F(); F(); F(); }
    public void Bp() { B(); B(); B(); }
    public void Up() { U(); U(); U(); }
    public void Dp() { D(); D(); D(); }
    public void Lp() { L(); L(); L(); }
    public void Rp() { R(); R(); R(); }
}
