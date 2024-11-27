package com.example.librarymanagernqc.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class KeyController {
    private static final ArrayList<String> recoveryKeys = new ArrayList<>(Arrays.asList(
            "apple", "banana", "chair", "desk", "earth", "flower", "globe", "happy", "island",
            "jump", "key", "lamp", "moon", "north", "orange", "peace", "queen", "river", "stone",
            "tiger", "umbrella", "victory", "whale", "yellow", "zebra", "airplane", "balloon", "cloud",
            "dream", "echo", "fountain", "guitar", "horizon", "infinite", "jungle", "kettle", "lightning",
            "mountain", "night", "ocean", "piano", "quiz", "rocket", "sunshine", "tree", "unity", "vortex",
            "wind", "xylophone", "yarn", "zigzag", "adjust", "breeze", "crystal", "diamond", "edge",
            "fortune", "galaxy", "harmony", "inspire", "jewel", "keyhole", "lighthouse", "mystic", "nova",
            "open", "pulse", "quest", "rose", "silence", "treasure", "vivid", "wander",
            "xenon", "yellowstone", "zodiac"
    ));
    private static final int KEY_COUNT = 8;

    public static ArrayList<String> getShuffleKeys() {
        Collections.shuffle(recoveryKeys);
        return recoveryKeys;
    }
}
