package support;

import listeners.messageListener;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class supportAccept extends Thread {

    private Member supporter;
    private supportCase scase;
    private boolean success = false;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public supportAccept(Member supporter, supportCase sc) {
        this.supporter = supporter;
        this.scase = sc;
    }

    public void run() {
        messageListener.accept.put(supporter.getUser(), this);
        PrivateChannel pc = supporter.getUser().openPrivateChannel().complete();
        pc.sendMessage("Willst du den Supportfall mit der ID " + scase.getId() + " von " + scase.getUser().getAsMention() +  " wirklich annehmen? (Noch 30 Sekunden) [ja/nein]").queue();
        for (int i = 30;i > 0;i--) {
            Message msg = pc.getHistory().retrievePast(1).complete().get(0);
            if (msg.getAuthor() == msg.getJDA().getSelfUser()) {
                pc.editMessageById(msg.getId(), "Willst du den Supportfall mit der ID " +
                        scase.getId() + " von " + scase.getUser().getAsMention() +
                        " wirklich annehmen? (Du hast noch " + i + " Sekunden) [ja/nein]").queue();
            }
            if (success) break;
            try {Thread.sleep(1000);} catch (InterruptedException e) {}
        }
        messageListener.accept.remove(supporter.getUser(), this);
        if (success) {
            try {
                int index = support.list.indexOf(scase);
                support.list.remove(index);
                scase.addSupporter(supporter.getUser());
                support.list.add(index, scase);
            } catch (Exception e) {return;}
            pc.sendMessage("Bitte kontaktiere jetzt den User " + supporter.getAsMention() + " und hilf ihm sein Problem zu lösen!").queue();
            scase.getUser().openPrivateChannel().complete().sendMessage("Der " + supporter.getRoles().get(0).getName() + " " + supporter.getAsMention() + " hat deinen Supportfall angenommen und wird dich gleich anschreiben").queue();
        }
    }

}
