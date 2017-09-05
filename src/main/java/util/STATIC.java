package util;

import java.util.ArrayList;
import java.util.List;

public class STATIC {

    private final String version;
    private String TOKEN;
    private String OWNER;
    private String PREFIX;
    private boolean ONCE_STARTED;
    private List<String> BLACKLIST = new ArrayList<>();
    private List<String> ALLOWED_ROLES_MUSIC = new ArrayList<>();
    private List<String> ALLOWED_ROLES_SUPPORT = new ArrayList<>();
    private int supportId;

    public STATIC() {
        this.version = "1.0.0";
        this.supportId = 0;
        TOKEN = "";
        OWNER = "";
        ONCE_STARTED = false;
    }

    public void setStandard() {
        PREFIX = "-";
        BLACKLIST.add("missgeburt");
        BLACKLIST.add("hurensohn");
        BLACKLIST.add("arschloch");
        ALLOWED_ROLES_MUSIC.add("Owner");
        ALLOWED_ROLES_MUSIC.add("Admin");
        ALLOWED_ROLES_SUPPORT.add("Owner");
        ALLOWED_ROLES_SUPPORT.add("Admin");
        ALLOWED_ROLES_SUPPORT.add("Supporter");
    }

    public String getToken() {
        return this.TOKEN;
    }

    public String getOwner() {
        return this.OWNER;
    }

    public String getPrefix() {
        return this.PREFIX;
    }

    public boolean getOnceStarted() {
        return this.ONCE_STARTED;
    }

    public List<String> getBlacklist() {
        return this.BLACKLIST;
    }

    public List<String> getAllowedRolesMusic() {
        return this.ALLOWED_ROLES_MUSIC;
    }

    public List<String> getAllowedRolesSupport() {
        return this.ALLOWED_ROLES_SUPPORT;
    }

    public int getSupportId() {
        if (supportId < 1000) {
            supportId++;
        } else {
            supportId = 1;
        }
        return this.supportId;
    }

    public void setToken(String token) {
        this.TOKEN = token;
    }

    public void setOwner(String ownerID) {
        this.OWNER = ownerID;
    }

    public void setPrefix(String prefix) {
        this.PREFIX = prefix;
    }

    public void setOnceStarted(boolean firstStart) {
        this.ONCE_STARTED = firstStart;
    }

    public void setBlacklist(List<String> blacklist) {
        this.BLACKLIST = blacklist;
    }

    public void setAllowedRolesMusic(List<String> allowed) {
        this.ALLOWED_ROLES_MUSIC = allowed;
    }

    public void setAllowedRolesSupport(List<String> allowed) {
        this.ALLOWED_ROLES_SUPPORT = allowed;
    }

}