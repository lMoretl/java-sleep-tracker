package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MinDurationFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>(
                "Минимальная продолжительность сна (мин)",
                sessions.stream()
                        .mapToLong(SleepingSession::getDurationMinutes)
                        .min()
                        .orElse(0)
        );
    }
}
