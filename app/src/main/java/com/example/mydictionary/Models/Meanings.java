package com.example.mydictionary.Models;

import java.util.List;

public class Meanings {
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Definitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definitions> definitions) {
        this.definitions = definitions;
    }

    String partOfSpeech="";
    List<Definitions>definitions=null;
}
