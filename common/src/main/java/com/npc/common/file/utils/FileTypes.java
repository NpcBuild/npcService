package com.npc.common.file.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件	文件头
 * JPEG (jpg)	FFD8FF
 * PNG (png)	89504E47
 * GIF (gif)	47494638
 * TIFF (tif)	49492A00
 * Windows Bitmap (bmp)	424D
 * CAD (dwg)	41433130
 * Adobe Photoshop (psd)	38425053
 * Rich Text Format (rtf)	7B5C727466
 * XML (xml)	3C3F786D6C
 * HTML (html)	68746D6C3E
 * Email [thorough only] (eml)	44656C69766572792D646174653A
 * Outlook Express (dbx)	CFAD12FEC5FD746F
 * Outlook (pst)	2142444E
 * MS Word/Excel (xls.or.doc)	D0CF11E0
 * MS Access (mdb)	5374616E64617264204A
 * WordPerfect (wpd)	FF575043
 * Postscript (eps.or.ps)	252150532D41646F6265
 * Adobe Acrobat (pdf)	255044462D312E
 * Quicken (qdf)	AC9EBD8F
 * Windows Password (pwl)	E3828596
 * ZIP Archive (zip)	504B0304
 * RAR Archive (rar)	52617221
 * Wave (wav)	57415645
 * AVI (avi)	41564920
 * Real Audio (ram)	2E7261FD
 * Real Media (rm)	2E524D46
 * MPEG (mpg)	000001BA
 * MPEG (mpg)	000001B3
 * Quicktime (mov)	6D6F6F76
 * Windows Media (asf)	3026B2758E66CF11
 * MIDI (mid)	4D546864
 */

/**
 * @author NPC
 * @description 依据文件头获取文件类型
 * @create 2023/3/9 23:34
 * 性能可能比较差，但是安全，后期可以结合后缀一起判断
 */
public class FileTypes {
    private static final Map<FileTypeName, String> FILE_TYPE_MAP = new HashMap<>();

    static {
        // 初始化文件类型信息
        initAllFileType();
    }

    private FileTypes() {
    }

    /**
     * 文件类型名称枚举
     * <br/>
     *
     * @author ：NPC
     * @date ：2023/3/9 23:34
     */
    public enum FileTypeName {

        JPG("jpg"),

        PNG("png"),

        GIF("gif"),

        HTML("html"),

        PDF("pdf"),

        ZIP("zip"),

        RAR("rar"),

        DOC("doc"),

        // ....根据自己需要添加更多
        ;

        private String fileTypeName;

        FileTypeName(String fileTypeName) {
            this.fileTypeName = fileTypeName;
        }

        @Override
        public String toString() {
            return fileTypeName;
        }
    }

    /**
     * 验证文件类型
     * <br/>
     * create by: NPC
     * <br/>
     * create time: 2023/3/9 23:34
     *
     * @param fileTypeName 文件类型名称枚举
     * @param file         文件
     * @return if 文件类型 = 文件类型名称枚举 return true , else return false
     */
    public static Boolean checkType(FileTypeName fileTypeName, File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] b = new byte[10];
            // 读取文件前10个字节
            int read = fileInputStream.read(b, 0, b.length);
            if (read != -1) {
                // 将字节转换为16进制字符串
                String fileCode = bytesToHexString(b).toUpperCase();
                // 获取对应文件类型的文件头
                String fileTypeHead = FILE_TYPE_MAP.get(fileTypeName);
                return fileCode.startsWith(fileTypeHead) || fileTypeHead.startsWith(fileCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取文件类型
     *
     * @param file 文件
     * @return 文件类型，if 获取不到类型 return "-1"
     */
    public static String getType(File file) {
        try {
            FileInputStream is = new FileInputStream(file);
            byte[] b = new byte[10];
            int read = is.read(b, 0, b.length);
            if (read != -1) {
                String fileCode = bytesToHexString(b).toUpperCase();
                for (Map.Entry<FileTypeName, String> next : FILE_TYPE_MAP.entrySet()) {
                    String fileTypeHead = next.getValue();
                    if (fileTypeHead.startsWith(fileCode) || fileCode.startsWith(fileTypeHead)) {
                        return next.getKey().toString();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 初始化常见文件头信息
     * <br/>
     * create by: NPC
     * <br/>
     * create time: 2023/3/9 23:34
     */
    private static void initAllFileType() {
        // JPEG (jpg)
        FILE_TYPE_MAP.put(FileTypeName.JPG, "FFD8FF");

        // PNG (png)
        FILE_TYPE_MAP.put(FileTypeName.PNG, "89504E47");

        // GIF (gif)
        FILE_TYPE_MAP.put(FileTypeName.GIF, "47494638");

        // HTML (html)
        FILE_TYPE_MAP.put(FileTypeName.HTML, "68746D6C3E");

        // Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put(FileTypeName.PDF, "255044462D312E");

        // ZIP Archive (zip)
        FILE_TYPE_MAP.put(FileTypeName.ZIP, "504B0304");

        // RAR Archive (rar)
        FILE_TYPE_MAP.put(FileTypeName.RAR, "52617221");

        // MS Word/Excel (xls.or.doc)
        FILE_TYPE_MAP.put(FileTypeName.DOC, "D0CF11E0");

        // ....根据自己需要添加更多， 文件头编码请用大写
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param bytes 待转换字节数组
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据文件名获取文件类型
     * 不一定完全正确，是根据文件名的扩展名来判断文件类型
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        String extension = getFileExtension(fileName);
        if (extension == null) {
            return null;
        }
        extension = extension.toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
                return "image";
            case "mp4":
            case "avi":
            case "mov":
            case "wmv":
            case "mkv":
            case "rmvb":
                return "video";
            case "zip":
            case "tar":
            case "jar":
            case "gz":
                return "zip";
            case "mp3":
            case "wav":
            case "flac":
                return "audio";
            case "pdf":
                return "pdf";
            case "doc":
            case "docx":
                return "word";
            case "txt":
                return "txt";
            case "xls":
            case "xlsx":
                return "excel";
            case "ppt":
            case "pptx":
                return "ppt";
            case "json":
                return "json";
            case "exe":
            case "msi":
                return "exe";
            case "url":
                return "url";
            case "ini":
                return "set";
            case "iso":
                return "windows";
            case "dll":
                return "dll";
            default:
                return "unknown";
        }
    }
    private static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return null;
        }
    }

    /**
     * 鉴定文件是图片还是视频
     * 在苹果手机的IOS系统中存在一种视频格式是 MOV 的，这种格式的视频是无法通过此种方式进行校验的
     * @param fileName
     * @return
     */
    public static int isPicOrVideo(String fileName) {
        int i = 0;
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        //下面是进一判断是视频和图片的区别
        if (contentTypeFor != null) {// 当是图片时不为空，是视频时为空
            i = 1;
        }
        return i;
    }

}
