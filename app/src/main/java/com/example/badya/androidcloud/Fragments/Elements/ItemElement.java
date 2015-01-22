package com.example.badya.androidcloud.Fragments.Elements;

import java.security.PublicKey;

/**
 * Created by Badya on 11/12/14.
 */
public class ItemElement {
    private int icon;
    private String name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    private boolean isGroupHeader = false;

    public ItemElement(int icon, String name, boolean isGroupHeader) {
        super();
        this.icon = icon;
        this.name = name;
        this.isGroupHeader = isGroupHeader;
    }

    public ItemElement(int icon, String name, String token, boolean isGroupHeader) {
        super();
        this.icon = icon;
        this.name = name;
        this.token = token;
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
