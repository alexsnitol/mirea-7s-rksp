package org.example;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.System.err;
import static java.lang.System.out;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * При помощи WatchService реализовать наблюдение за каталогом:
 * 1) При создании нового файла в этом каталоге вывести его название;
 * 2) При изменении файла вывести список изменений(добавленных и удаленных строк);
 * 3) При удалении файла вывести его размер и контрольную сумму(использовать реализацию из задания 3).
 * Если реализовать пункт 3 не представляется возможным – докажите это.
 */

public class Opt4 {

    private static Map<String, List<String>> filesCache = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get("opt4", "src", "main", "resources");

        initFilesCache(path);

        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(
                watchService,
                ENTRY_CREATE,
                ENTRY_MODIFY,
                ENTRY_DELETE
        );

        WatchKey key;
        boolean fileChanged = false;

        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                String fileName = event.context().toString();

                // обработка временных файлов
                if (fileName.endsWith("~")) {
                    fileChanged = true;
                    continue;
                }

                if (event.kind().equals(ENTRY_CREATE)) {
                    out.println(fileName + " has been created");
                    filesCache.put(
                            fileName,
                            getFileContents(getFilePathFromResourcesByName(fileName))
                    );
                } else if (event.kind().equals(ENTRY_MODIFY)) {
                    List<String> changedFileContents = getFileContents(getFilePathFromResourcesByName(fileName));
                    Patch<String> patch = DiffUtils.diff(
                            filesCache.get(fileName),
                            changedFileContents
                    );
                    List<Delta<String>> deltaList = patch.getDeltas();

                    if (fileChanged || deltaList.stream().anyMatch(d -> d.getType().equals(Delta.TYPE.CHANGE))) {
                        out.println(fileName + " has been modified");
                        for (Delta delta : deltaList) {
                            out.println(delta);
                        }
                        filesCache.replace(fileName, changedFileContents);
                    }
                } else if (event.kind().equals(ENTRY_DELETE)) {
                    long sizeOfFile = getSizeOfFile(fileName);
                    filesCache.remove(fileName);
                    out.println(fileName + " with size " + sizeOfFile + " bytes has been deleted");
                }
            }
            key.reset();
        }
    }

    private static void initFilesCache(Path path) throws IOException {
        Files.list(path).forEach(f -> filesCache.put(f.toFile().getName(), getFileContents(f.toAbsolutePath())));
    }

    private static long getSizeOfFile(String fileName) {
        long sizeOfFile = 0;
        for (String line : filesCache.get(fileName)) {
            sizeOfFile += line.getBytes().length;
        }
        return sizeOfFile;
    }

    private static List<String> getFileContents(Path path) {
        List<String> contents = new LinkedList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
        } catch (Exception e) {
            err.println("error in time reading file " + path.toFile().getName());
        }
        return contents;
    }

    private static Path getFilePathFromResourcesByName(String fileName) {
        return Paths.get("opt4", "src", "main", "resources", fileName).toAbsolutePath();
    }
}