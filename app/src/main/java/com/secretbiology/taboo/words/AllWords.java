package com.secretbiology.taboo.words;

public class AllWords {

    public String[][] getAllWords(){

        String[][] List1 = new WordSet1().words();
        String[][] List2 = new WordSet2().words();
        String[][] List3 = new WordSet3().words();
        String[][] List4 = new WordSet4().words();
        int fulllength = List1.length + List2.length + List3.length + List4.length;
        String[][] all = new String[fulllength][6];
        System.arraycopy(List1, 0, all, 0, List1.length);
        System.arraycopy(List2, 0, all, List1.length, List2.length);
        System.arraycopy(List3, 0, all, (List1.length + List2.length), List3.length);
        System.arraycopy(List4, 0, all, (List1.length + List2.length + List3.length), List4.length);
        return all;
    }
}
