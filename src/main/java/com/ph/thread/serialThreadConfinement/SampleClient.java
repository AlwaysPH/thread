package com.ph.thread.serialThreadConfinement;

public class SampleClient {

    private static final MessageFileDownloader DOWNLOADER;

    static {
        DOWNLOADER = new MessageFileDownloader("", "", "", "");
        DOWNLOADER.init();
    }

    public static void main(String[] args) {
        DOWNLOADER.downFile("");
    }
}
