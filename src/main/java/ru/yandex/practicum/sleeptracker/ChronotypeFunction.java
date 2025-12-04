package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;

public class ChronotypeFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Chronotype> apply(List<SleepingSession> sessions) {

        LocalTime wakeBorder = LocalTime.of(9, 0);
        LocalTime sleepBorder = LocalTime.of(23, 0);
        LocalTime earlySleepBorder = LocalTime.of(22, 0);
        LocalTime earlyWakeBorder = LocalTime.of(7, 0);

        long owls = sessions.stream()
                .filter(this::isNightSession)
                .filter(s -> s.getStart().toLocalTime().isAfter(sleepBorder)
                        && s.getEnd().toLocalTime().isAfter(wakeBorder))
                .count();

        long larks = sessions.stream()
                .filter(this::isNightSession)
                .filter(s -> s.getStart().toLocalTime().isBefore(earlySleepBorder)
                        && s.getEnd().toLocalTime().isBefore(earlyWakeBorder))
                .count();

        long doves = sessions.stream()
                .filter(this::isNightSession)
                .count() - owls - larks;

        Chronotype result;
        if (owls > larks && owls > doves) result = Chronotype.OWL;
        else if (larks > owls && larks > doves) result = Chronotype.LARK;
        else result = Chronotype.DOVE;

        return new SleepAnalysisResult<>("Хронотип пользователя", result);
    }

    private boolean isNightSession(SleepingSession s) {
        LocalTime t = s.getStart().toLocalTime();
        return t.isAfter(LocalTime.of(18, 0)) || t.isBefore(LocalTime.of(6, 0));
    }
}
