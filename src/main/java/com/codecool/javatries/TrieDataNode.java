package com.codecool.javatries;

import java.util.*;

public class TrieDataNode {

    private char data;
    private  HashMap<Character, TrieDataNode> children = new HashMap<>();

    public TrieDataNode(char data) {
        this.data = data;
    }

    public char getData() {
        return data;
    }

    @Override
    public String toString() {
        return Character.toString(data);
    }

    public void addChild(char letter) {
        char key = Character.toLowerCase(letter);
        if (children.get(key) == null) {
            TrieDataNode nextNode = new TrieDataNode(letter);
            children.put(key, nextNode);
        }
    }

    public TrieDataNode getChild (char letter) {
        char key = Character.toLowerCase(letter);
        return children.get(key);
    }

    public List<Character> getKeysList () {
        Set keys = children.keySet();
        List<Character> keysList = new ArrayList<Character>(keys);
        return keysList;
    }

    public List<String> matchingWordsList (String baseChars) {
        List<Character> keysList = getKeysList();
        List<String> nodesWordList = new ArrayList<>();


        for (Character key : keysList) {
            if (key == '*') {
                nodesWordList.add(baseChars);
            } else {
                StringBuilder word = new StringBuilder();
                word.append(baseChars);
                char extend = children.get(key).data;
                word.append(extend);
                List<String> childWordList = children.get(key).matchingWordsList(word.toString());

                nodesWordList.addAll(childWordList);
            }
        }
        return nodesWordList;
    }

}
