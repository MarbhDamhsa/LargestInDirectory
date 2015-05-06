package directory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    static File file;
    static Scanner sc = new Scanner(System.in);
    static String dirPath;
    static final List<Path> files = new ArrayList<>();
    
    

    private static Long getSizeofLargestFile(Path path) {
        Long aLargestFileSize = 0L;
        File file = new File(dirPath);
        for (File aFile : file.listFiles()) {
            if (aLargestFileSize < aFile.length()) {
                aLargestFileSize = aFile.length();
            }
        }
        return aLargestFileSize;
    }

    static void listFiles(Path path) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path p : stream) {
                if (Files.isDirectory(p)) {
                    listFiles(p);
                }
                files.add(p);
            }
        }
    }
    
    public static void main(String[] args) {
    for(;;){
        System.out.println("Please enter a directory path: ");

        Path path;
        dirPath = sc.nextLine();
        path = Paths.get(dirPath);
        file = path.toFile();

        while (Files.notExists(path) == true) {
            System.out.println("Directory does not exist");
            System.out.println("Please enter a directory path");
            dirPath = sc.nextLine();
            path = Paths.get(dirPath);

        }

        if (Files.exists(path) && Files.isDirectory(path)) {
            System.out.println("Directory: "
                    + path.toAbsolutePath());

            System.out.println("Your Largest File(s): ");

            try {
                DirectoryStream<Path> stream;
                stream = Files.newDirectoryStream(path);
                for (Path p : stream) {
                    //files.add(p);
                }
                listFiles(path);
                stream.close();

                for (Path p : files) {
                    
                    while (Files.isDirectory(path) && file.list().length == 0){
                        System.out.println("Directory has no files!");
                        System.out.println("Please enter a directory path: ");
                        dirPath = sc.nextLine();
                        path = Paths.get(dirPath);
                    }
                        
                    if ((Files.isRegularFile(p)) && (Files.size(p) == getSizeofLargestFile(path))) {
                        System.out.println("     "
                                + p.getFileName() + "    " + p.getParent() + "   size: "
                                + Files.size(p) + "    " + Files.getLastModifiedTime(p));

                    }
                }
            } catch (IOException e) {
                System.out.println("I/O Exception");
                e.printStackTrace();
            }
        }
    }
    }
}//MainApp
