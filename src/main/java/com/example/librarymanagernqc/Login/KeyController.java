package com.example.librarymanagernqc.Login;

import java.util.ArrayList;
import java.util.Collections;

public class KeyController {
    private static final ArrayList<String> recoveryKeys = new ArrayList<>();
    private static final int KEY_COUNT = 8;

    public static ArrayList<String> getShuffleKeys() {
        Collections.shuffle(recoveryKeys);
        return recoveryKeys;
    }

    public static void setRecoveryKeys () {}
}
