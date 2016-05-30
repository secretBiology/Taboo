package com.secretbiology.taboo.words;

public class AllWords {

    public String[][] getAllWords(){

        String[][] List1 = new WordSet1().words();
        String[][] List2 = new WordSet2().words();
        int fulllength = List1.length + List2.length;
        String[][] all = new String[fulllength][6];
        System.arraycopy(List1, 0, all, 0, List1.length);
        System.arraycopy(List2, 0, all, List1.length, List2.length);
        return all;
    }
}
