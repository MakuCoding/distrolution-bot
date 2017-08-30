package support;

import listeners.messageListener;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;

public class supportRefuse extends Thread {

    private supportCase scase;
    private User user;
    boolean success = false;

    public supportRefuse(supportCase scase, User user) {
        this.scase = scase;
        this.user = user;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void run() {
        messageListener.refuse.put(user, this);
        PrivateChannel pc = user.openPrivateChannel().complete();
        pc.sendMessage("Willst du den Supportfall mit der ID " + scase.getId() + " von " + (scase.getUser() == user ? "dir" : scase.getUser().getAsMention()) +  " wirklich löschen? (Noch 30 Sekunden) [ja/nein]").queue();
        for (int i = 30;i > 0;i--) {
            Message msg = pc.getHistory().retrievePast(1).complete().get(0);
            if (msg.getAuthor() == msg.getJDA().getSelfUser()) {
                pc.editMessageById(msg.getId(), "Willst du den Supportfall mit der ID " +
                        scase.getId() + " von " + (scase.getUser() == user ? "dir" : scase.getUser().getAsMention()) +
                        " wirklich löschen (Du hast noch " + i + " Sekunden) [ja/nein]").queue();
            }
            if (success) break;
            try {Thread.sleep(1000);} catch (InterruptedException e) {}
        }
        messageListener.refuse.remove(user, this);
        if (success) {
            support.list.remove(scase);
            pc.sendMessage(":white_check_mark: Supportfall gelöscht!").queue();
        }
    }

}
