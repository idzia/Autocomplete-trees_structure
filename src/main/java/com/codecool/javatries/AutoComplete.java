package com.codecool.javatries;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class AutoComplete {

    private TrieDataNode root;

    private static final char ROOT = '-';
    private static final char END = '*';

    /**
     * Starts a new Trie with dummy root data "-"
     */
    public AutoComplete() {
        root = new TrieDataNode(ROOT);
    }

    /**
     * Adds a word to the Trie.
     */
    public void addWord(String wordToAdd) {
        int len = wordToAdd.length();
        TrieDataNode current = root;

        for (int i=0; i<len; i++) {
            char letter = wordToAdd.charAt(i);
            current.addChild(letter);
            current = current.getChild(letter);
        }
        current.addChild(END);
    }

    /**
     * Returns the possible completions of baseChars String from the Trie.
     * @param baseChars The String to autocomplete.
     * @return possible completions. An empty list if there are none.
     */
    public List<String> autoComplete(String baseChars) {
        List<String> wordsList = new ArrayList<>();
        StringBuilder baseWord = new StringBuilder();

        TrieDataNode current = root;

        for (int i=0; i<baseChars.length(); i++) {
            char letter = baseChars.charAt(i);
            current = current.getChild(letter);
            if (current == null) {
                return wordsList;
            }
            baseWord.append(current.getData());
        }
        wordsList = current.matchingWordsList(baseWord.toString());
        sort(wordsList);

        return wordsList;
    }


    /**
     * Removes a word from the Trie
     * @return true if the removal was successful
     */
    public boolean removeWord(String wordToRemove) {

        TrieDataNode[] nodeArray = wordNodeArray(wordToRemove);

        if (nodeArray == null) {
            return false;
        }
        int lastIndex = nodeArray.length-1;

        TrieDataNode lastNode = nodeArray[lastIndex];

        while (lastNode.hasAChild() == false) {
            lastIndex--;
            TrieDataNode parent = nodeArray[lastIndex];
            parent.getChildrenMap().remove(lastNode.getData());
            lastNode = nodeArray[lastIndex];
        }
        return true;
    }


    public TrieDataNode[] wordNodeArray (String wordToRemove) {

        TrieDataNode[] nodeArray = new TrieDataNode[wordToRemove.length()+1];
        TrieDataNode current = root;

        for (int i=0; i<nodeArray.length-1; i++) {
            char letter = wordToRemove.charAt(i);

            if (current.getChild(letter)==null) {
                return null;
            } else {

                nodeArray[i] = current.getChild(letter);
                current = current.getChild(letter);
            }
        }

        nodeArray[wordToRemove.length()] = current.getChild(END);

        return nodeArray;
    }

}
