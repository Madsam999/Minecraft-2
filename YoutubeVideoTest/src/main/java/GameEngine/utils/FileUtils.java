package GameEngine.utils;

import org.example.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {
    public static  String loadAsString(String path) {
        StringBuilder result = new StringBuilder();

        try {

            Object t = Main.class.getResourceAsStream(path);
            if(t == null) {
                System.out.println(Main.class.getResource("."));
            }
            else {
                System.out.println(t.toString());
            }


            BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(path)));

            String line = "";
            while((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        catch (IOException e) {
            System.err.println("Couldn't find the file at " + path);
        }

        return result.toString();
    }
}
