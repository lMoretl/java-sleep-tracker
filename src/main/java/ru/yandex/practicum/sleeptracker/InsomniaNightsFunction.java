package ru.yandex.practicum.sleeptracker;

import java.time.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

public class InsomniaNightsFunction implements SleepAnalysisFunction {

    private static final LocalTime NIGHT_START = LocalTime.MIDNIGHT;
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);
    private static final LocalTime EVENING = LocalTime.of(18, 0);

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sessions) {

        if (sessions == null || sessions.isEmpty()) {
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
        LocalTime start = s.getStart().toLocalTime();
        LocalTime end = s.getEnd().toLocalTime();

        return start.isBefore(NIGHT_END)
                || (start.isAfter(EVENING) && end.isAfter(NIGHT_START));
    }
}
