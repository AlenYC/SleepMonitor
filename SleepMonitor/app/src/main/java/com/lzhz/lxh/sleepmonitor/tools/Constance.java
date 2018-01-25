package com.lzhz.lxh.sleepmonitor.tools;

import java.util.UUID;

/**
 * Created by dk on 2017/5/26.
 */

public interface Constance {
    String APP_NAME = "219-LZ";
    String KEY_TEL = "tel";
    String KEY_PSW = "psw";
    String UUID_CHAR_SERVICE = "0000ffe5-0000-1000-8000-00805f9b34fb";
    String UUID_CHAR_SERVICE2 = "0000ffe0-0000-1000-8000-00805f9b34fb";
    String UUID_CHAR_SERVICE3 = "00001623-1212-efde-1523-785feabcd123";
    String UUID_CHAR_PILLOW_WR = "00001625-1212-efde-1523-785feabcd123";
    String UUID_CHAR_WRITE = "0000ffe9-0000-1000-8000-00805f9b34fb";
    String UUID_CHAR_READ = "0000ffe4-0000-1000-8000-00805f9b34fb";
    UUID UUID_DESCRIPTOR_DEF = UUID.fromString("00002902-0000-1000-8000-00805F9B34FB");
    String REG_TEL = "^1(3|4|5|7|8)\\d{9}$";
    String HTTP_GET_YZM = "http://219.api.zxcapp.com/user/captcha?";
    String HTTP_REGISTER = "http://219.api.zxcapp.com/user/signup";
    String HTTP_LOGIN = "http://219.api.zxcapp.com/user/login";
    String HTTP_RESET_PSW = "http://219.api.zxcapp.com/user/resetpwd";
    String HTTP_GET_INFO = "http://219.api.zxcapp.com/user/view?";
    String HTTP_UPDATE_INFO = "http://219.api.zxcapp.com/user/update";
    String HTTP_UPDATE_AVATOR = "http://219.api.zxcapp.com/user/upload-image";
}
