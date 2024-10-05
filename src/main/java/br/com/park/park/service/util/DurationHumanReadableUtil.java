package br.com.park.park.service.util;

import java.time.Duration;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class DurationHumanReadableUtil {

    public static String formatDuration(Duration duration) {
        if (duration == null)
            return null;
        long hours = duration.toHours();
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);

        long seconds = duration.getSeconds();

        StringBuilder humanReadable = new StringBuilder();

        if (hours > 0) {
            humanReadable.append(hours).append(" hora").append(hours > 1 ? "s " : " ");
        }
        if (minutes > 0) {
            humanReadable.append(minutes).append(" minuto").append(minutes > 1 ? "s " : " ");
        }
        if (seconds > 0) {
            humanReadable.append(seconds).append(" segundo").append(seconds > 1 ? "s" : "");
        }
        String durationHumanReadable = humanReadable.toString().trim();

        return isBlank(durationHumanReadable) ? null : durationHumanReadable;
    }

}
