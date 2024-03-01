import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class program {
    public static void main(String[] args) throws IOException {
        String path = args[0];
        ArrayList<Monster> monsters = new ArrayList<>();
        try {
            Path file = Path.of(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(",");
                monsters.add(new Monster(split[0], split[1], split[2], split[3], split[4]));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> types = new ArrayList<String>();

        int index = 1;
        for (Monster monster : monsters) {
            if (!types.contains(monster.Type)) {
                types.add(monster.Type);
                System.out.println(index + ". " + monster.Type + '\n');
                index++;
            }
        }
        System.out.println("Enter the number of the type of monster you want to see: ");
        Scanner scanner = new Scanner(System.in);
        int chosen = scanner.nextInt();
        chosen--;
        String type = types.get(chosen);
        System.out.println("\n");
        PrintWriter writer = new PrintWriter("filtered_monsters.csv");
        for (Monster monster : monsters) {
            if (monster.Type.equals(type)) {
                writer.println(monster.toString());
            }
        }
        writer.close();
        for (Monster monster : monsters) {
            if (monster.Type.equals(type)) {
                System.out.println(monster);
            }
        }
        scanner.close();
    }

    /**
     * Creates a Monsters array
     */
    // public static ArrayList<Monster> createWeapons(String filename) throws
    // IOException {
    // // TODO
    // String delimiter = ",";
    // ArrayList<Monster> Monsters = new ArrayList<Monster>();
    // Scanner in = new Scanner(Path.of(filename));
    // while (in.hasNextLine()) {
    // // Read the first line and split it into tokens
    // String line = in.nextLine();
    // String[] tokens = line.split(delimiter);
    // // Create a new weapon object and add it to the array
    // Monsters.add(new Monster(tokens[0], tokens[1], tokens[2], tokens[3],
    // tokens[4])); // create infinite
    // }
    // return Monsters;
    // }
}