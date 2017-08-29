package support;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.List;

public class supportCase {

    private int id;
    private User user;
    private Guild guild;
    private String reason;
    private List<User> supporter;
    private boolean needsHelp;

    public supportCase() {
        this.id = 0;
    }

    public supportCase(int id, User user, Guild guild, String reason) {
        this.id = id;
        this.user = user;
        this.guild = guild;
        this.reason = reason;
        this.supporter = new ArrayList<>();
        this.needsHelp = false;
    }

    public supportCase(int id, User user, Guild guild) {
        this.id = id;
        this.user = user;
        this.guild = guild;
        this.reason = "";
        this.supporter = new ArrayList<>();
        this.needsHelp = false;
    }

    public int getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public String getReason() {
        return this.reason;
    }

    public List<User> getSupporter() {
        return this.supporter;
    }

    public boolean needsHelp() {
        return this.needsHelp;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void addSupporter(User supporter) {
        this.supporter.add(supporter);
    }

    public void setNeedsHelp(boolean needsHelp) {
        this.needsHelp = needsHelp;
    }

}
