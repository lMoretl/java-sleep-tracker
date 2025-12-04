package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SleepLogLoader {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static List<SleepingSession> load(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .filter(line -> !line.isBlank())
                .map(SleepLogLoader::parseLine)
                .toList();
    }

    private static SleepingSession parseLine(String line) {
        String[] parts = line.split(";");
        LocalDateTime start = LocalDateTime.parse(parts[0], FORMATTER);
        LocalDateTime end = LocalDateTime.parse(parts[1], FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(parts[2]);

        return new SleepingSession(start, end, quality);
    }
}
