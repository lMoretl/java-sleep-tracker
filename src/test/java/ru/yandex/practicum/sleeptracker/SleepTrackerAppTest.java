package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {

    private List<SleepingSession> mockSessions() {
        return List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:00"),
                        LocalDateTime.parse("2025-10-02T08:00"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-02T23:30"),
                        LocalDateTime.parse("2025-10-03T06:00"), SleepQuality.BAD)
        );
    }

    @Test
    void testCountSessions() {
        CountSessionsFunction f = new CountSessionsFunction();
        assertEquals(2, f.apply(mockSessions()).getValue());
    }

    @Test
    void testMinDuration() {
        MinDurationFunction f = new MinDurationFunction();
        long value = f.apply(mockSessions()).getValue();
        assertTrue(value > 0);
    }

    @Test
    void testInsomniaNights() {
        InsomniaNightsFunction f = new InsomniaNightsFunction();
        int value = f.apply(mockSessions()).getValue();
        assertEquals(0, value);
    }

    @Test
    void testChronotype() {
        ChronotypeFunction f = new ChronotypeFunction();
        Chronotype type = f.apply(mockSessions()).getValue();
        assertEquals(Chronotype.DOVE, type);
    }
}
