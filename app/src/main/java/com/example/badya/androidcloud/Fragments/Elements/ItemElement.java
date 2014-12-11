package com.example.badya.androidcloud.Fragments.Elements;

/**
 * Created by Badya on 11/12/14.
 */
public class ItemElement {
    private int icon;
    private String name;

    private boolean isGroupHeader = false;

    public ItemElement(int icon, String name, boolean isGroupHeader) {
        super();
        this.icon = icon;
        this.name = name;
        this.isGroupHeader = isGroupHeader;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGroupHeader() {
        return isGroupHeader;
    }

    public void setGroupHeader(boolean isGroupHeader) {
        this.isGroupHeader = isGroupHeader;
    }
}
