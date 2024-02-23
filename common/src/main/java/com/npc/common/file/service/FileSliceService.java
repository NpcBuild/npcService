package com.npc.common.file.service;

import com.npc.common.file.entity.FileSlice;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public interface FileSliceService {

    long saveChunk(MultipartFile file, FileSlice fileInfo) throws IOException;

    void mergeChunks(FileSlice fileInfo) throws IOException;

    long getUploadedChunks(String fileMd5);

    long getTotalChunks(String fileMd5);
}
