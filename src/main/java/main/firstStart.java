package main;

import util.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class firstStart extends Thread {

    Scanner sc =  new Scanner(System.in);
    String token = "";
    String owner = "";
    String yesno = "";
    String prefix = "";
    String black = "";
    List<String> blacklist = new ArrayList<>();
    String music = "";
    List<String> musicrole = new ArrayList<>();
    String support = "";
    List<String> supportrole = new ArrayList<>();

    public firstStart() {

    }

    public void run() {

        System.out.println("Distrolution-Bot Setup v1.0.0");
        System.out.println("__________________________________________________");
        System.out.println("Du wirst jetzt durch ein Setup geführt um deinen Bot einzurichten.\nDrücke Enter zum Fortfahren!");

        sc.nextLine();

        while (token == "") {

            System.out.println("__________________________________________________");
            System.out.println("Bitte gib jetzt den Token des Bots ein.\n" +
                    "Diesen bekommst du indem du dich auf der Webseite von Discord anmeldest, und dann auf Mehr/Entwickler gehts.\n" +
                    "Dort kannst du unter MyApps einen neuen Bot anlegen, gibst ihm einen Namen, eine Beschreibung und ein Profilbild.\n" +
                    "Dann speicherst du ihn und erstellst im nächsten Fenster einen Bot-User. Das Token bekommst du nun indem du auf \"click to reveal\" bei Client-Secret klickst.\n" +
                    "Den Code den du dann bekommst gibst du dann in der Einstellungsdatei ein. Gib diesen Code niemals weiter denn damit haben andere Leute zugriff auf deinen Bot." +
                    "Du kannst den Token jederzeit in der Bot-Daten datei ändern");
            token = sc.nextLine();

        }

        main.statics.setToken(token);

        while (owner == "") {

            System.out.println("__________________________________________________");
            System.out.println("Gib nun die User-ID des Owners des Bots an. Die User-ID bekommst du bei aktiviertem" +
                    "Developer-Modus mit Rechtsklick auf einen User und ID kopieren. Diese musst du dann hier einfügen:");
            owner = sc.nextLine();

        }

        main.statics.setOwner(owner);

        while (yesno == "") {

            System.out.println("__________________________________________________");
            System.out.println("Die Grundkonfiguration ist nun abgeschlossen. Möchtest du noch die Blacklist, den Command-Prefix und die Permissions einstellen? Bei \"nein\" werden Standard-Einstellungen geladen. (y/n)");
            yesno = sc.nextLine();

        }

        if (yesno.equalsIgnoreCase("y") || yesno.equalsIgnoreCase("yes")) {

            while (prefix == "") {

                System.out.println("__________________________________________________");
                System.out.println("Bitte gib das Prefix ein, dass vor jeden Command geschrieben werden muss. (Beispiele: \"/\",\"-\"\".\")");
                prefix = sc.nextLine();

            }

            main.statics.setPrefix(prefix);

                System.out.println("__________________________________________________");
                System.out.println("Bitte gib nun Wörter oder Ausdrücke ein, die auf die Blacklist (Liste mit Wörtern oder Ausdrücken die im Chat zensiert werden) geschrieben werden sollen. Setze zwischen jeden Ausdruck/jedes Wort ein Semikolon (;)\n" +
                        "Beispiel: Wort1;Ausdruck 1;Ausdruck 2;Wort2     (Die Reihenfolge ist egal)");
                black = sc.nextLine();
                if (!black.equals("")) {
                    for (String s : black.split(";")) {
                        blacklist.add(s);
                    }
                }
                main.statics.setBlacklist(blacklist);

            while (music == "") {

                System.out.println("__________________________________________________");
                System.out.println("Gib nun die Rollen ein die auf deinem Server die Erlaubnis haben sollen den Music-Command auszuführen (Wie vorher getrennt mit einem ;):");
                music = sc.nextLine();
                if (music != "") {
                    for (String s:music.split(";")) {
                        musicrole.add(s);
                    }
                    main.statics.setAllowedRolesMusic(musicrole);
                }

            }

            while (support == "") {

                System.out.println("__________________________________________________");
                System.out.println("Gib nun die Rollen ein die auf deinem Server die Supportfälle annehmen, verwalten und löschen dürfen (Getrennt mit ;):");
                support = sc.nextLine();
                if (support != "") {
                    for (String s:support.split(";")) {
                        supportrole.add(s);
                    }
                    main.statics.setAllowedRolesSupport(supportrole);
                }

            }

            settings.loadSettings(false);

        } else {

            settings.loadSettings(true);

        }

    }

}
