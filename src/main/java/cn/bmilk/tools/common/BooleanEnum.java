package cn.bmilk.tools.common;

public enum BooleanEnum {

    TRUE("true"),
    FALSE("false");

    private String value;

    BooleanEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
