package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;

public class ChronotypeFunction implements SleepAnalysisFunction {

    private static final LocalTime WAKE_BORDER = LocalTime.of(9, 0);
    private static final LocalTime SLEEP_BORDER = LocalTime.of(23, 0);
    private static final LocalTime EARLY_SLEEP_BORDER = LocalTime.of(22, 0);
    private static final LocalTime EARLY_WAKE_BORDER = LocalTime.of(7, 0);
    private static final LocalTime NIGHT_START = LocalTime.of(18, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult<Chronotype> apply(List<SleepingSession> sessions) {

        long owls = sessions.stream()
                .filter(this::isNightSession)
                .filter(s -> s.getStart().toLocalTime().isAfter(SLEEP_BORDER)
                        && s.getEnd().toLocalTime().isAfter(WAKE_BORDER))
                .count();

        long larks = sessions.stream()
                .filter(this::isNightSession)
                .filter(s -> s.getStart().toLocalTime().isBefore(EARLY_SLEEP_BORDER)
                        && s.getEnd().toLocalTime().isBefore(EARLY_WAKE_BORDER))
                .count();

        long doves = sessions.stream()
                .filter(this::isNightSession)
                .count() - owls - larks;

        Chronotype result;

        if (owls > larks && owls > doves) {
            result = Chronotype.OWL;
        } else if (larks > owls && larks > doves) {
            result = Chronotype.LARK;
        } else {
            result = Chronotype.DOVE;
        }

        return new SleepAnalysisResult<>("Хронотип пользователя", result);
    }

    private boolean isNightSession(SleepingSession s) {
        LocalTime t = s.getStart().toLocalTime();
        return t.isAfter(NIGHT_START) || t.isBefore(NIGHT_END);
    }
}
