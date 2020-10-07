import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<String> logMessages = new ArrayList<>(); // лист для записи лог сообщений
        String OSType = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (OSType.contains("win")) {
            System.out.println("Программа будет установлена в папку C:Games. Убедитесь что она создана.");
        } else {
            System.out.println("Программа работает только под системой Windows. Запустите программу под Windows");
        }
        ArrayList<String> paths = new ArrayList<>();    // Лист для папок
        boolean result;
        paths.add("C://Games//");
        paths.add("C://Games//src//");
        paths.add("C://Games//res//");
        paths.add("C://Games//savegames//");
        paths.add("C://Games//temp//");
        paths.add("C://Games//temp//temp.txt");
        paths.add("C://Games//src//main//");
        paths.add("C://Games//src//main//main.java");
        paths.add("C://Games//src//main//Utils.java");
        paths.add("C://Games//src//test//");
        paths.add("C://Games//res//drawables//");
        paths.add("C://Games//res//vectors//");
        paths.add("C://Games//res//icons//");
        for (int i = 0; i < paths.size(); i++) {
            try {
                File dir = new File(paths.get(i));
                if (i == 0) {   // проверка создания директории для установки
                    if (!dir.isDirectory() && dir.exists()) {
                        System.out.println("Папка для установки не создана");
                        System.out.println(("Пожалуйста создайте папку для установки C:Games"));
                        break;
                    } else {
                        continue;
                    }
                }
                if (paths.get(i).contains(".")) {   // Для файлов используется другой метод, здесь вычленяем файлы
                    result = dir.createNewFile();
                } else {
                    result = dir.mkdir();
                }
                logMessages.add("Результат создания " + paths.get(i) + " = " + result);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                logMessages.add(e.getMessage());
            }

        }
        StringBuilder builder = new StringBuilder();
        for (String k : logMessages) {
            System.out.println(k);
            builder.append(k + "\n");
        }

        FileWriter temp = new FileWriter("C://Games//temp//temp.txt");
        temp.write(builder.toString());
        temp.close();
        System.out.println("\nЛог файл доступен здесь: C:/Games/temp/temp.txt");
        // задание 2
        GameProgress game1 = new GameProgress(100, 8, 80, 843);
        GameProgress game2 = new GameProgress(89, 7, 80, 967);
        GameProgress game3 = new GameProgress(77, 5, 80, 9331);
        saveGame(paths.get(3) + "game1.dat", game1); // Сохранение игры1
        saveGame(paths.get(3) + "game2.dat", game2); // Сохранение игры2
        saveGame(paths.get(3) + "game3.dat", game3); // Сохранение игры3
        zipFile("C://Games//savegames//game.zip", new String[]{paths.get(3) + "game1.dat", paths.get(3) + "game2.dat", paths.get(3) + "game3.dat"});
        File file1 = new File(paths.get(3) + "game1.dat");
        File file2 = new File(paths.get(3) + "game2.dat");
        File file3 = new File(paths.get(3) + "game3.dat");
        file1.delete();
        file2.delete();
        file3.delete();

    }

    public static void saveGame(String path, GameProgress game) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        }
    }

    public static void zipFile(String zipPath, String[] filesPaths) throws IOException {
        ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(zipPath));
        for(int i = 0; i < filesPaths.length; i++) {
            try (FileInputStream fis = new FileInputStream(filesPaths[i])) {
                int index = filesPaths[i].lastIndexOf("/");
                String zipFileName = filesPaths[i].substring(index + 1);
                String entryName = zipFileName;
                ZipEntry entry1 = new ZipEntry(entryName);
                zOut.putNextEntry(entry1);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zOut.write(buffer);
                zOut.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        zOut.close();
    }
}


