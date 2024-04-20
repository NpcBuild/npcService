package com.npc.core.net;

/**
 * 显示下载速度
 * 需要了解SpringMVC中的接口ResponseExtractor
 */
public interface DisplayDownloadSpeed {
    void displaySpeed(String task, long contentLength);
}
