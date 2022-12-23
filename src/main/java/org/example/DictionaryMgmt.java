package org.example;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryMgmt {
    public static Dictionary dict1;

    public static void init(InputStream inputPath) {//get data from text file, init Trie
        dict1 = new Dictionary();
        Scanner sc = null;
        sc = new Scanner(inputPath);
        while (sc.hasNextLine()) {
            String currentLine = sc.nextLine();
            int indexOfTab = currentLine.indexOf("\t");
            int indexOfComma = currentLine.indexOf(",");

            String word = currentLine.substring(0, indexOfTab).trim();
            String meaning1 = currentLine.substring(indexOfTab + 1, indexOfComma).trim();
            String meaning2 = currentLine.substring(indexOfComma + 1).trim();

            if (validateString(word)) {
                String target_n = Normalize.remove(word);
                Trie.TrieNode current = dict1.trie.root;
                for (int i = 0; i < target_n.length(); i ++) {
                    int index = target_n.charAt(i) - 'a';
                    if (current.children[index] == null) {
                        Trie.TrieNode node = new Trie.TrieNode();
                        String temp = current.getTarget_Normalize();
                        current.children[index] = node;
                        current = node;
                        current.setTarget_Normalize(temp + target_n.charAt(i));
                    } else {
                        current = current.children[index];
                    }
                }

                current.setMeaning1(meaning1);
                current.setMeaning2(meaning2);
                current.setEndOfWord(true);
                current.setTarget(word);
            } else {
            }
        }
    }

    public static boolean validateString(String word) {
        String word_n = Normalize.remove(word);
        if (word_n.length() == 0) {
            return false;
        } else {
            for (int i = 0; i < word_n.length(); i ++) {
                int index = word_n.charAt(i) - 'a';
                if (index < 0 || index > 25) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void loadToList(Trie.TrieNode node, ArrayList<Word> list) {
        Trie.TrieNode current = node;
        if (current != null) {
            if (current.getEndOfWord()) {
                Word word = new Word();
                word.setTarget(current.getTarget());
                word.setMeaning1(current.getMeaning1());
                word.setMeaning2(current.getMeaning2());
                list.add(word);
            }
            for (int i = 0; i < 26; i++) {
                if (current.children[i] != null) {
                    loadToList(current.children[i], list);
                }
            }
        }
    }

    public static boolean isWord(String word) {
        if ((search(word) != null) && (search(word).getEndOfWord() == true)) {
            return true;
        }
        return false;
    }

    public static Trie.TrieNode search(String word) {
        Trie.TrieNode current = dict1.trie.root;
        String target_n = Normalize.remove(word);
        for (int i = 0; i < target_n.length(); i ++) {
            int index = target_n.charAt(i) - 'a';
            if (current.children[index] != null) {
                current = current.children[index];
            } else {
                return null;
            }
        }
        return current;
    }

    public static void insert(String word, String meaning1, String meaning2) {

        Trie.TrieNode current = dict1.trie.root;
        String target_n = Normalize.remove(word);
        if (validateString(target_n) && validateString(meaning1)) {
            for (int i = 0; i < target_n.length(); i ++) {
                int index = target_n.charAt(i) - 'a';
                if (current.children[index] == null) {
                    Trie.TrieNode node = new Trie.TrieNode();
                    String temp = current.getTarget_Normalize();
                    current.children[index] = node;
                    current = node;
                    current.setTarget_Normalize(temp + target_n.charAt(i));
                } else {
                    current = current.children[index];
                }
            }
            current.setMeaning1(meaning1.trim());
            current.setMeaning2(meaning2.trim());
            current.setEndOfWord(true);
            current.setTarget(word.trim());
        }
    }

    public static void delete(String word) throws IllegalAccessException {
        Trie.TrieNode node = search(word);
        node.setEndOfWord(false);
        node.setTarget("");
        node.setMeaning1("");
        node.setMeaning2("");
    }

    public static void export(String path) throws IOException {
        BufferedWriter bWrite = new BufferedWriter(new FileWriter(new File
                (path)));
        int index = dict1.list.size();
        for (int i = 0; i < index; i ++) {
            String word = dict1.list.get(i).getTarget();
            String meaning1 = dict1.list.get(i).getMeaning1();
            String meaning2 = dict1.list.get(i).getMeaning2();
            bWrite.write(word + "\t" + meaning1 + "," + meaning2 + "\n");
        }
        bWrite.close();
    }
}