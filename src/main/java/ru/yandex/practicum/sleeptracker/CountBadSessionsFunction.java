package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class CountBadSessionsFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>(
                "Количество плохих ночей (BAD)",
                sessions.stream()
                        .filter(s -> s.getQuality() == SleepQuality.BAD)
                        .count()
        );
    }
}
