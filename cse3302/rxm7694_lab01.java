//Rodrigo Munoz
//ID: 1001847694
//macos 13.3.1 (a) (22E772610a)
//java 18.0.1.1 2022-04-22

import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

public class rxm7694_lab01{
    public static void main(String[] args) throws IOException{
      long dirSize = getDirSize("./");

      System.out.println("Dir size = " + dirSize);
    }

    public static long getDirSize(String path) throws IOException {
        long size = 0;
        File[] dir = new File(path).listFiles();

        for(File file : dir){
            if(file.isFile()){
                size += file.length();
            }
            else if(file.isDirectory() && file.getName() != "." && file.getName() != ".."){
                size = size + getDirSize(file.getPath());
            }
        }

        return size;
    }
}

