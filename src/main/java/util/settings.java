package util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import main.main;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class settings {

    public static void loadSettings(boolean standard) {
        Path path = Paths.get(System.getProperty("user.dir") + "\\Discord-Bot Einstellungen.txt");


        File file = new File(path.toString());

        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                BufferedReader br = new BufferedReader(fr);
                main.statics.setToken(br.readLine().split(";")[1]);

                main.statics.setOwner(br.readLine().split(";")[1]);

                main.statics.setPrefix(br.readLine().split(";")[1]);

                main.statics.setOnceStarted(Boolean.parseBoolean(br.readLine().split(";")[1]));

                String[] black = br.readLine().split(";");
                List<String> blacklist = new ArrayList<>();
                for (int i = 1;i < black.length;i++) {blacklist.add(black[i]);}
                main.statics.setBlacklist(blacklist);

                br.readLine();
                br.readLine();

                String[] roles = br.readLine().split(";");
                List<String> allowed1 = new ArrayList<>();
                for (int i = 1; i < roles.length; i++) {allowed1.add(roles[i]);}
                main.statics.setAllowedRolesMusic(allowed1);

                roles = br.readLine().split(";");
                List<String> allowed2 = new ArrayList<>();
                for (int i = 1;i < roles.length;i++) {allowed2.add(roles[i]);}
                main.statics.setAllowedRolesSupport(allowed2);

                br.close();
                fr.close();
            } catch (Exception e) {
                System.out.println("Fehler beim Einlesen");
                e.printStackTrace();
            }
        } else {
            try (FileWriter fw = new FileWriter(path.toString())) {
                if (standard) main.statics.setStandard();
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("TOKEN;" + main.statics.getToken());
                bw.newLine();

                bw.write("OWNER_USER_ID;" + main.statics.getOwner());
                bw.newLine();

                bw.write("PREFIX;" + main.statics.getPrefix());
                bw.newLine();

                bw.write("ONCE_STARTED;" + main.statics.getOnceStarted());
                bw.newLine();

                String black = "";
                for (int i = 0; i < main.statics.getBlacklist().size(); i++) {
                    black += ";" + main.statics.getBlacklist().get(i);
                }
                bw.write("CHAT_BLACKLIST" + black);
                bw.newLine();

                bw.write("PERMISSIONS:");
                bw.newLine();
                bw.write("Hier die Rollen eures Servers eingeben!");
                bw.newLine();

                String roles = "";
                for (int i = 0; i < main.statics.getAllowedRolesMusic().size(); i++) {
                    roles += ";" + main.statics.getAllowedRolesMusic().get(i);
                }
                bw.write("ALLOWED_ROLES_MUSIC" + roles);
                bw.newLine();

                roles = "";
                for (int i = 0;i < main.statics.getAllowedRolesSupport().size();i++) {
                    roles += ";" + main.statics.getAllowedRolesSupport().get(i);
                }
                bw.write("ALLOWED_ROLES_SUPPORT" + roles);
                bw.flush();
                bw.close();
            } catch (IOException ex) {}
        }
    }

    public static void setSettings() {

        Path path = Paths.get(System.getProperty("user.dir") + "\\Discord-Bot Einstellungen.txt");

        File file = new File(path.toString());

        if (file.exists()) {
            try {
                FileWriter fw = new FileWriter(path.toString());
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("TOKEN;" + main.statics.getToken());
                bw.newLine();

                bw.write("OWNER_USER_ID;" + main.statics.getOwner());
                bw.newLine();

                bw.write("PREFIX;" + main.statics.getPrefix());
                bw.newLine();

                bw.write("ONCE_STARTED;" + main.statics.getOnceStarted());
                bw.newLine();

                String black = "";
                for (int i = 0; i < main.statics.getBlacklist().size(); i++) {
                    black += ";" + main.statics.getBlacklist().get(i);
                }
                bw.write("CHAT_BLACKLIST" + black);
                bw.newLine();

                bw.write("PERMISSIONS:");
                bw.newLine();
                bw.write("Hier die Rollen eures Servers eingeben!");
                bw.newLine();

                String roles = "";
                for (int i = 0; i < main.statics.getAllowedRolesMusic().size(); i++) {
                    roles += ";" + main.statics.getAllowedRolesMusic().get(i);
                }
                bw.write("ALLOWED_ROLES_MUSIC" + roles);
                bw.newLine();

                roles = "";
                for (int i = 0;i < main.statics.getAllowedRolesSupport().size();i++) {
                    roles += ";" + main.statics.getAllowedRolesSupport().get(i);
                }
                bw.write("ALLOWED_ROLES_SUPPORT" + roles);
                bw.flush();
                bw.close();
            } catch (Exception e) {}
        }

    }

}
