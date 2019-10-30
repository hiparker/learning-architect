package com.edwin.smartdevelop.modules.sys.dict;

/**
 * Created Date by 2019/10/30.
 *
 * @author Edwin
 */

public enum MenuDict {

    DIRECTORY("目录","0"),
    MENU("菜单","0"),
    BUTTON("按钮","0");

    MenuDict(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(String value) {
        for (MenuDict c : MenuDict.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
