package org.example;

public class Trie {

    public static class TrieNode{
        TrieNode[] children;
        private boolean endOfWord;
        private String target;
        private String target_Normalize;
        private String meaning1;
        private String meaning2;
        public TrieNode() {
            this.children = new TrieNode[26];
            this.endOfWord = false;
            this.target = "";
            this.meaning1 = "";
            this.meaning2 = "";
            this.target_Normalize = "";
            for(int i= 0; i < 26; i++) {
                this.children[i] = null;
            }
        }
        public String getTarget() {
            return target;
        }
        public String getMeaning1() {
            return meaning1;
        }
        public String getMeaning2() {
            return meaning2;
        }
        public String getTarget_Normalize() {
            return target_Normalize;
        }
        public boolean getEndOfWord() {
            return endOfWord;
        }
        public void setMeaning1(String meaning1) {
            this.meaning1 = meaning1;
        }
        public void setMeaning2(String meaning2) {
            this.meaning2 = meaning2;
        }
        public void setTarget(String target) {
            this.target = target;
        }
        public void setTarget_Normalize(String target_Normalize) {
            this.target_Normalize = target_Normalize;
        }
        public void setEndOfWord(boolean endOfWord) {
            this.endOfWord = endOfWord;
        }
    }

    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
}