import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Quiz implements Modifiers {
    public static void main(String[] args) {
        Scanner in = null;

        try {
            System.out.println("File Opened successfully");
            Scanner input = new Scanner(new File("quiz.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } finally {
            if (in != null) {
                if (in.nextInt() > 0)
                {
                    in.close();
                }
            }
        }
    }

}
