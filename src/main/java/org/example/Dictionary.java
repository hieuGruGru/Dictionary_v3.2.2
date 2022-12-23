package org.example;

import java.util.ArrayList;

public class Dictionary {
    Trie trie = new Trie();
    ArrayList<Word> list = new ArrayList<>();

    public Dictionary() {
        this.trie = trie;
        this.list = list;
    }
}