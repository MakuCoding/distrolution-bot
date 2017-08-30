package commands;

import main.permissions;
import main.main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import support.supportCase;
import support.support;
import support.supportAccept;
import support.supportRefuse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class cmdSupport implements Command {

    private supportCase getSupportCase(int id) {
        for (supportCase sc:support.list) {
            if (sc.getId() == id) {
                return sc;
            }
        }
        return new supportCase();
    }

    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {

        if (args.length == 0) {

                int anzahl = 0;
                for (supportCase s : support.list) {
                    if (s.getUser() == event.getAuthor()) {
                        anzahl++;
                    }
                }
                if (anzahl <= 5) {
                    supportCase sc = new supportCase(main.statics.getSupportId(), event.getAuthor(), event.getGuild());
                    support.list.add(sc);
                    //support.notifySupporter(sc);
                    event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.GREEN).setDescription("Deine Anfrage wurde registriert. Bitte habe ein wenig Geduld, einer unserer Supporter wird dich bald anschreiben.").build()).queue();
                    event.getMessage().delete().queue();
                    return;
                } else {
                    event.getChannel().sendMessage(new EmbedBuilder()
                            .setColor(Color.RED)
                            .setDescription(":warning: Um Spam zu vermeiden kannst du nicht mehr als fünf Support-Anfragen stellen!")
                            .build()).queue();
                    event.getMessage().delete().queue();
                    return;
                }

        } else {

            switch (args[0].toLowerCase()) {

                case "reason":

                    if (args.length > 1) {
                        int anzahl = 0;
                        for (supportCase s : support.list) {
                            if (s.getUser() == event.getAuthor()) {
                                anzahl++;
                            }
                        }
                        if (anzahl <= 5) {
                            supportCase sc = new supportCase(main.statics.getSupportId(), event.getAuthor(), event.getGuild(), Arrays.stream(args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1));
                            support.list.add(sc);
                            //support.notifySupporter(sc);
                            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.GREEN).setDescription("Deine Anfrage wurde registriert. Bitte habe ein wenig Geduld, einer unserer Supporter wird dich bald anschreiben.").build()).queue();
                            event.getMessage().delete().queue();
                            return;
                        } else {
                            event.getChannel().sendMessage(new EmbedBuilder()
                                    .setColor(Color.RED)
                                    .setDescription(":warning: Um Spam zu vermeiden kannst du nicht mehr als fünf Support-Anfragen stellen!")
                                    .build()).queue();
                            event.getMessage().delete().queue();
                            return;
                        }
                    }

                    break;

                case "list":

                    if (permissions.checkSupportPermission(event)) {

                        EmbedBuilder listeOut = new EmbedBuilder().setColor(Color.BLUE).setDescription("**==========SUPPORTLISTE==========**\n");
                        String liste = "";
                        for (int i = 0; i < support.list.size(); i++) {
                            supportCase s = support.list.get(i);
                            if (s.getGuild() == event.getGuild() && (s.getSupporter().size() == 0 || s.getSupporter().size() > 0 && s.needsHelp())) {

                                String list = s.getId() + " - " + s.getUser().getName() + " - " + (s.getReason() == "" ? "Support benötigt!" : s.getReason()) + (s.needsHelp() ? " - Supporter braucht Unterstützung!" : "");
                                if (list.length() < 32) {
                                    StringBuilder sblist = new StringBuilder();
                                    for (int in = 0; in < (32 - list.length() / 2); in++) {
                                        sblist.append(" ");
                                    }
                                    sblist.append(list);
                                    while (sblist.length() < 32) {
                                        sblist.append(" ");
                                    }
                                    liste += "\n" + sblist.toString() + "\n";
                                } else {
                                    liste += "\n" + list + "\n";
                                }
                            }

                        }

                        if (liste == "")
                            listeOut.appendDescription("\nDie Supportliste ist zurzeit leer\n");
                        listeOut.appendDescription(liste + "\n================================");
                        event.getChannel().sendMessage(listeOut.build()).queue();
                        event.getMessage().delete().queue();
                        return;

                    } else {
                        EmbedBuilder listeOut = new EmbedBuilder().setColor(Color.BLUE).setDescription("**==========DEINE SUPPORTFÄLLE==========**\n");
                        String liste = "";
                        for (int i = 0; i < support.list.size(); i++) {
                            supportCase s = support.list.get(i);
                            if (s.getGuild() == event.getGuild() && (s.getSupporter().size() == 0 || s.getSupporter().size() > 0 && s.needsHelp()) && s.getUser() == event.getAuthor()) {

                                String list = s.getId() + " - " + s.getUser().getName() + " - " + (s.getReason() == "" ? "Support benötigt!" : s.getReason()) + (s.needsHelp() ? " - Supporter braucht Unterstützung!" : "");
                                if (list.length() < 38) {
                                    StringBuilder sblist = new StringBuilder();
                                    for (int in = 0; in < (38 - list.length() / 2); in++) {
                                        sblist.append(" ");
                                    }
                                    sblist.append(list);
                                    while (sblist.length() < 38) {
                                        sblist.append(" ");
                                    }
                                    liste += "\n" + sblist.toString() + "\n";
                                } else {
                                    liste += "\n" + list + "\n";
                                }
                            }

                        }

                        if (liste == "")
                            listeOut.appendDescription("\nDu hast keine Support-Anfrage gestellt\n");
                        listeOut.appendDescription(liste + "\n================================");
                        event.getChannel().sendMessage(listeOut.build()).queue();
                        event.getMessage().delete().queue();
                        return;
                    }

                case "accept":

                    if (permissions.checkSupportPermission(event)) {
                        if (args.length == 2) {
                            int id = 0;
                            try {
                                id = Integer.parseInt(args[1]);
                            } catch (Exception e) {
                                return;
                            }
                            supportCase scase = getSupportCase(id);
                            if (scase.getId() != 0 && scase.getSupporter().size() == 0) {
                                supportAccept sa = new supportAccept(event.getMember(), scase);
                                sa.start();
                                event.getMessage().delete().queue();
                                return;
                            } else {
                                event.getChannel().sendMessage(new EmbedBuilder()
                                        .setColor(Color.RED)
                                        .setDescription(":warning: Es wurde kein Supportfall mit dieser ID gefunden!")
                                        .build()).queue();
                                event.getMessage().delete().queue();
                                return;
                            }
                        }
                    } else {
                        event.getChannel().sendMessage(new EmbedBuilder()
                                .setColor(Color.RED)
                                .setDescription(":warning: Du hast nicht die Berechtigung dazu " + event.getMember().getAsMention() + ":exclamation:")
                                .build()).queue();
                        event.getMessage().delete().queue();
                        return;
                    }

                    break;

                case "solved":

                    List<supportCase> sclist = new ArrayList<>();
                    for (supportCase s : support.list) {
                        if (s.getUser() == event.getAuthor()) {
                            sclist.add(s);
                        }
                    }
                    if (args.length == 1) {
                        if (sclist.size() == 1) {
                            sclist.get(0).getSupporter().forEach(u -> u.openPrivateChannel().complete().sendMessage(event.getAuthor().getAsMention() + "'s Supportfall ist gelöst!").queue());
                            support.list.remove(sclist.get(0));
                            event.getMessage().delete().queue();
                            event.getChannel().sendMessage(new EmbedBuilder()
                                    .setColor(Color.GREEN)
                                    .setDescription("Dein Supportfall ist gelöst und wurde gelöscht!:thumbsup:")
                                    .build()).queue();
                            return;
                        } else if (sclist.size() == 0) {
                            event.getMessage().delete().queue();
                            event.getChannel().sendMessage(new EmbedBuilder()
                                    .setColor(Color.RED)
                                    .setDescription(":warning: Du hast unseren Support nicht in Anspruch genommen!")
                                    .build()).queue();
                            return;
                        } else {
                            event.getMessage().delete().queue();
                            event.getChannel().sendMessage(new EmbedBuilder()
                                    .setColor(Color.RED)
                                    .setDescription(":warning: Du hast mehrere Supportfälle. Bitte gib die ID des Supportfalls an den du als gelöst markieren willst!")
                                    .build()).queue();
                            return;
                        }
                    } else if (args.length == 2) {
                        int id = 0;
                        try {
                            id = Integer.parseInt(args[1]);
                        } catch (Exception e) {
                            return;
                        }
                        supportCase scase = getSupportCase(id);
                        if (event.getAuthor() == scase.getUser()) {
                            scase.getSupporter().forEach(u -> u.openPrivateChannel().complete().sendMessage(event.getAuthor().getAsMention() + "'s Supportfall ist gelöst!").queue());
                            support.list.remove(scase);
                            event.getMessage().delete().queue();
                            event.getChannel().sendMessage(new EmbedBuilder()
                                    .setColor(Color.GREEN)
                                    .setDescription("Dein Supportfall ist gelöst und wurde gelöscht!:thumbsup:")
                                    .build()).queue();
                            return;
                        } else {
                            event.getMessage().delete().queue();
                            event.getChannel().sendMessage(new EmbedBuilder()
                                    .setColor(Color.RED)
                                    .setDescription(":warning: Du bist nicht der Ersteller dieser Support-Anfrage und kannst ihn deshalb nicht als gelöst markieren!")
                                    .build()).queue();
                            return;
                        }
                    }

                    break;

                case "refuse":

                    if (permissions.checkSupportPermission(event)) {
                        if (args.length == 2) {
                            int id = 0;
                            try {
                                id = Integer.parseInt(args[1]);
                            } catch (Exception e) {
                                return;
                            }
                            supportCase scase = getSupportCase(id);
                            if (!(scase == new supportCase())) {
                                supportRefuse refuse = new supportRefuse(scase, event.getAuthor());
                                refuse.start();
                            }
                        }
                    } else {
                        event.getChannel().sendMessage(new EmbedBuilder()
                                .setColor(Color.RED)
                                .setDescription(":warning: Du hast nicht die Berechtigung dazu " + event.getMember().getAsMention() + ":exclamation:")
                                .build()).queue();
                        event.getMessage().delete().queue();
                        return;
                    }

                    break;

                case "needhelp":

                    if (permissions.checkSupportPermission(event)) {
                        System.out.println("1");
                        if (args.length == 2) {
                            System.out.println("2");
                            int id = 0;
                            try {
                                id = Integer.parseInt(args[1]);
                            } catch (Exception e) {
                                return;
                            }
                            System.out.println(id);
                            supportCase scase = getSupportCase(id);
                            id = support.list.indexOf(scase);
                            if (scase.getId() != 0) {
                                System.out.println("4");
                                if (scase.needsHelp()) {
                                    support.list.remove(id);
                                    scase.setNeedsHelp(false);
                                    support.list.add(id, scase);
                                } else {
                                    System.out.println("5");
                                    support.list.remove(id);
                                    scase.setNeedsHelp(true);
                                    support.list.add(id, scase);
                                    support.notifySupporter(scase);
                                }
                                event.getMessage().delete().queue();
                                return;
                            }
                        } else if (args.length == 3) {
                            int id = 0;
                            try {
                                id = Integer.parseInt(args[1]);
                            } catch (Exception e) {
                                break;
                            }
                            supportCase scase = getSupportCase(id);
                            if (scase.getId() != 0) {
                                if (args[2].equalsIgnoreCase("false") || args[2].equalsIgnoreCase("true")) {
                                    support.list.remove(id);
                                    scase.setNeedsHelp(Boolean.getBoolean(args[2].toLowerCase()));
                                    support.list.add(id, scase);
                                    event.getMessage().delete().queue();
                                    return;
                                }
                            }
                        }
                    } else {
                        event.getChannel().sendMessage(new EmbedBuilder()
                                .setColor(Color.RED)
                                .setDescription(":warning: Du hast nicht die Berechtigung dazu " + event.getMember().getAsMention() + ":exclamation:")
                                .build()).queue();
                        event.getMessage().delete().queue();
                        return;
                    }

                    break;

                case "helpsup":

                    if (permissions.checkSupportPermission(event)) {
                        if (args.length == 2) {
                            int id = 0;
                            Member memb = null;
                            try {
                                id = Integer.parseInt(args[1]);
                            } catch (Exception e) {
                                try {
                                    memb = event.getGuild().getMembersByEffectiveName(args[0], true).get(0);
                                } catch (Exception ex) {
                                    return;
                                }
                            }
                            supportCase scase = null;
                            if (id != 0) {
                                scase = getSupportCase(id);
                            } else if (memb != null) {
                                for (supportCase s : support.list) {
                                    if (s.getSupporter().get(0) == memb.getUser()) {
                                        scase = s;
                                        break;
                                    }
                                }
                            }
                            if (scase != null) {
                                if (!(scase.getSupporter().contains(event.getAuthor()))) {
                                    id = support.list.indexOf(scase);
                                    support.list.remove(scase);
                                    scase.addSupporter(event.getAuthor());
                                    support.list.add(id, scase);
                                    event.getMessage().delete().queue();
                                    return;
                                }
                            }
                        }
                    } else {
                        event.getChannel().sendMessage(new EmbedBuilder()
                                .setColor(Color.RED)
                                .setDescription(":warning: Du hast nicht die Berechtigung dazu " + event.getMember().getAsMention() + ":exclamation:")
                                .build()).queue();
                        event.getMessage().delete().queue();
                        return;
                    }

                    break;

            }

            event.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(Color.RED)
                    .build()).queue();
            event.getMessage().delete().queue();

        }

    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
