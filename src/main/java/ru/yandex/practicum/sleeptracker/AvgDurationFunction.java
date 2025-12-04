package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class AvgDurationFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long avg = Math.round(
                sessions.stream()
                        .mapToLong(SleepingSession::getDurationMinutes)
                        .average()
                        .orElse(0)
        );

        return new SleepAnalysisResult<>("Средняя продолжительность сна (мин)", avg);
    }
}
