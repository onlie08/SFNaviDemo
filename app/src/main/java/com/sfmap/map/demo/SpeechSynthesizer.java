package com.sfmap.map.demo;

public interface SpeechSynthesizer {
    boolean startSpeaking(String text);
    boolean stop();
    void destroy();
}
