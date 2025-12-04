package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class SleepTrackerApp {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Укажите путь к файлу sleep_log.txt");
            return;
        }

        try {
            List<SleepingSession> sessions = SleepLogLoader.load(args[0]);

            List<SleepAnalysisFunction> functions = List.of(
                    new CountSessionsFunction(),
                    new MinDurationFunction(),
                    new MaxDurationFunction(),
                    new AvgDurationFunction(),
                    new CountBadSessionsFunction(),
                    new InsomniaNightsFunction(),
                    new ChronotypeFunction()
            );

            functions.forEach(f -> {
                SleepAnalysisResult<?> result = f.apply(sessions);
                System.out.println(result.getDescription() + ": " + result.getValue());
            });

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
