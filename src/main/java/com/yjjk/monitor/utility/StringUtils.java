/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: StringUtils
 * Author:   CentreS
 * Date:     2019-06-19 17:29
 * Description: 字符串工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.utility;


import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author CentreS
 * @Description: 字符串工具
 * @create 2019-06-19
 */
public class StringUtils {

    private static final int PHONE_LENGTH = 11;
    private static final int MACHINE_NUM_LENGTH = 12;


    private static Pattern pattern_replaceBlank = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * 手机号验证
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNumber(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (phone.length() != PHONE_LENGTH) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (isMatch) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param param
     * @return
     */
    public static boolean isNullorEmpty(Object param) {
        if (param == null) {
            return true;
        }
        if (param instanceof Integer) {
            if ((Integer) param == 0) {
                return true;
            }
        } else if (param instanceof String) {
            String str = (String) param;
            if (str.length() <= 0) {
                return true;
            }
        } else if (param instanceof List) {
            List list = (List) param;
            if (list.size() == 0) {
                return true;
            }
        } else if (param instanceof Set) {
            Set set = (Set) param;
            if (set.size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Matcher m = pattern_replaceBlank.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 工具类：判断list是否为空
     *
     * @param list
     * @return
     */
    public static boolean listIsNullOrEmpty(List list) {
        if (list == null) {
            return true;
        } else {
            if (list.size() == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 匹配设备SN序列号
     *
     * @param machineNum
     * @return
     */
    public static boolean checkMachineNum(String machineNum) {
        String regex = "\\w\\d{2}/\\d{8}";
        if (machineNum.length() != MACHINE_NUM_LENGTH) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(machineNum);
            boolean isMatch = m.matches();
            if (isMatch) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 设备编号匹配规则
     * @param machineNo
     * @return
     */
    public static boolean checkMachineNo(String machineNo) {
        if (machineNo.length() > 6) {
            return false;
        }
        return true;
    }

    /**
     * 姓名加密
     * eg:
     * 名字: 海
     * 名字: 贼*
     * 名字: 海*王
     * 名字: 大**王
     * 名字: 大*******王
     *
     * @param name
     * @return
     */
    public static String replaceNameX(String name) {
        if (name == null || name.length() <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        name = name.trim();
        builder.append(name.charAt(0));
        if (name.length() > 2) {
            for (int i = 0; i < name.length() - 2; i++) {
                builder.append("☆");
            }
            builder.append(name.charAt(name.length() - 1));
        } else if (name.length() == 2) {
            builder.append("☆");
        }
        return builder.toString();
    }

    //    public static void main(String[] args) {
//        System.out.println(isPhoneNumber("1307450d898"));
//        List list = new ArrayList();
//        list.add("fds");
//        System.out.println(isNullorEmpty(list));
//        System.out.println(checkMachineNum("B33/00044267"));
//    }
    public static void main(String[] args) {
        System.out.println("名字: " + replaceNameX("海"));
        System.out.println("名字: " + replaceNameX("贼王"));
        System.out.println("名字: " + replaceNameX("海贼王"));
        System.out.println("名字: " + replaceNameX("大海贼王"));
        System.out.println("名字: " + replaceNameX("大海发生大发大贼王"));
    }
}
