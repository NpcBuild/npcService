package com.npc.utils;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.nio.charset.Charset;

/**
 * @author NPC
 * @description Windows系统API工具类
 * //todo https://mp.weixin.qq.com/s/91mZOfa_rIDYYfjDeMZCkw
 * @create 2025/1/3 21:18
 */
public class Win32ApiUtils {

    private static final int WM_GETTEXT = 0x000D;
    private static final int WM_GETTEXTLENGTH = 0x000E;

    // 定义FindWindow函数
    public static native WinDef.HWND FindWindow(String lpClassName, String lpWindowName);

    // 定义EnumChildWindows函数
    public static native int EnumChildWindows(WinDef.HWND hWndParent, WinUser.WNDENUMPROC lpfn, int lParam);

    // 定义GetClassName函数
    public static native int GetClassName(WinDef.HWND hWnd, byte[] lpClassName, int nMaxCount);

    // 定义IsWindowVisible函数
    public static native boolean IsWindowVisible(WinDef.HWND hWnd);

    // 定义SendMessage函数(用于发送获取文本长度的消息)
    public static native int SendMessage(WinDef.HWND hWnd, int wMsg, int wParam, int lParam);

    // 定义SendMessage函数(用于发送获取文本内容的消息)
    public static native int SendMessage(WinDef.HWND hWnd, int wMsg, int wParam, byte[] lParam);

    static {
        Native.register("user32.dll");
    }

    // 定义回调函数类型，用于EnumChildWindows函数
    public interface WNDENUMPROC extends WinUser.WNDENUMPROC{
        boolean callback(WinDef.HWND hWnd, Pointer arg1);
    }

    // 获取指定窗口的文本内容
    public static String getWindowText(String windowTitle) {
        String content = "";
        // 查找窗口
        WinDef.HWND mainHandle = FindWindow(null, windowTitle);
        if (mainHandle != null) {
            // 枚举子窗口，查找控件句柄
            EnumChildWindows(mainHandle, new WNDENUMPROC() {
                @Override
                public boolean callback(WinDef.HWND hWnd, Pointer arg1) {
                    // 获取窗口类名
                    byte[] className = new byte[256];
                    GetClassName(hWnd, className, className.length);
                    String classname = new String(className, Charset.forName("UTF-8")).trim();
                    if ("Edit".equals(classname)) {
                        // 判断是否为文本框或编辑框
                        if (IsWindowVisible(hWnd)) {
                            // 获取文本长度
                            int length = SendMessage(hWnd, WM_GETTEXTLENGTH, 0, 0);
                            // 发送获取文本内容的消息
                            byte[] buffer = new byte[length + 1];
                            SendMessage(hWnd, WM_GETTEXT, length + 1, buffer);
//                            content = new String(buffer, Charset.forName("UTF-8")).trim();
                        }
                    }
                    return true;
                }
            }, 0);
        }
            return content;
    }
}
