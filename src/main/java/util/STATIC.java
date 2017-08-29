package util;

import java.util.ArrayList;
import java.util.List;

public class STATIC {

    private final String version;
    private String PREFIX;
    private List<String> ALLOWED_ROLES_MUSIC = new ArrayList<>();
    private List<String> ALLOWED_ROLES_SUPPORT = new ArrayList<>();
    private List<String> BLACKLIST = new ArrayList<>();
    private int supportId;

    public STATIC() {
        this.version = "1.0.0";
        this.supportId = 0;
        this.BLACKLIST.add("missgeburt");
        this.BLACKLIST.add("hurensohn");
        this.BLACKLIST.add("arschloch");
    }

    public void setStandard() {
        PREFIX = "-";
        ALLOWED_ROLES_MUSIC.add("Owner");
        ALLOWED_ROLES_MUSIC.add("Admin");
        ALLOWED_ROLES_SUPPORT.add("Owner");
        ALLOWED_ROLES_SUPPORT.add("Admin");
        ALLOWED_ROLES_SUPPORT.add("Supporter");
    }

    public String getPrefix() {
        return this.PREFIX;
    }

    public List<String> getAllowedRolesMusic() {
        return this.ALLOWED_ROLES_MUSIC;
    }

    public List<String> getAllowedRolesSupport() {
        return this.ALLOWED_ROLES_SUPPORT;
    }

    public List<String> getBlacklist() {
        return this.BLACKLIST;
    }

    public int getSupportId() {
        if (supportId < 1000) {
            supportId++;
        } else {
            supportId = 1;
        }
        return this.supportId;
    }

    public void setPrefix(String prefix) {
        this.PREFIX = prefix;
    }

    public void setAllowedRolesMusic(List<String> allowed) {
        this.ALLOWED_ROLES_MUSIC = allowed;
    }

    public void setAllowedRolesSupport(List<String> allowed) {
        this.ALLOWED_ROLES_SUPPORT = allowed;
    }

    public void setBlacklist(List<String> blacklist) {
        this.BLACKLIST = blacklist;
    }

}