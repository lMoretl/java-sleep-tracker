package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MaxDurationFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>(
                "Максимальная продолжительность сна (мин)",
                sessions.stream()
                        .mapToLong(SleepingSession::getDurationMinutes)
                        .max()
                        .orElse(0)
        );
    }
}
