package ru.yandex.practicum.sleeptracker;

import java.time.*;
import java.util.List;

public class InsomniaNightsFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Количество бессонных ночей", 0);
        }

        LocalDateTime firstStart = sessions.get(0).getStart();
        LocalDate firstNight = firstStart.toLocalDate();
        if (firstStart.getHour() >= 12) {
            firstNight = firstNight.plusDays(1);
        }

        LocalDate lastNight = sessions.get(sessions.size() - 1).getEnd().toLocalDate();

        int totalNights = Period.between(firstNight, lastNight.plusDays(1)).getDays();

        int nightsWithSleep = (int) sessions.stream()
                .filter(this::isNightSleep)
                .map(s -> s.getEnd().toLocalDate())
                .distinct()
                .count();

        int insomniaNights = totalNights - nightsWithSleep;

        return new SleepAnalysisResult<>("Количество бессонных ночей", insomniaNights);
    }

    private boolean isNightSleep(SleepingSession s) {
        LocalTime nightStart = LocalTime.MIDNIGHT;
        LocalTime nightEnd = LocalTime.of(6, 0);

        return
                (s.getStart().toLocalTime().isBefore(nightEnd))
                        ||
                        (s.getStart().toLocalTime().isAfter(LocalTime.of(18, 0))
                                && s.getEnd().toLocalTime().isAfter(nightStart));
    }
}
