package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class CountSessionsFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>(
                "Количество сессий сна",
                sessions.size()
        );
    }
}
