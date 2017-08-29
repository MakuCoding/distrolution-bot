package util;

import main.main;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class settings {

    public static void loadSettings() {
        Path path = Paths.get(System.getProperty("user.home") + "\\Desktop\\DistrolutionBot Daten.csv");

        File file = new File(path.toString());

        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                BufferedReader br = new BufferedReader(fr);
                main.statics.setPrefix(br.readLine().split(";")[1]);
                String[] content1 = br.readLine().split(";");
                List<String> allowed1 = new ArrayList<>();
                for (int i = 1; i < content1.length; i++) {allowed1.add(content1[i]);}
                main.statics.setAllowedRolesMusic(allowed1);
                String[] content2 = br.readLine().split(";");
                List<String> allowed2 = new ArrayList<>();
                for (int i = 1;i < content2.length;i++) {allowed2.add(content2[i]);}
                main.statics.setAllowedRolesSupport(allowed2);
                fr.close();
            } catch (Exception e) {
                System.out.println("Fehler beim Einlesen");
                e.printStackTrace();
            }
        } else {
            try (FileWriter fw = new FileWriter(path.toString())) {
                main.statics.setStandard();
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("PREFIX;" + main.statics.getPrefix());
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

}
