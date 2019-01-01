package com.froobworld.frooblib.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class TimeUtils {

    public static String getDaysHoursMinutes(long l) {
        boolean added = false;
        String s = "";
        long left = l;
        long days = (left / 1000 / 60 / 60 / 24);
        left -= (days * 24 * 60 * 60 * 1000);

        long hours = (left / 1000 / 60 / 60);
        left -= (hours * 60 * 60 * 1000);

        long minutes = (left / 1000 / 60);
        left -= (minutes * 60 * 1000);

        if (days > 0) {
            s += (int) days + (days == 1 ? " day" : " days");
            added = true;
        }
        if (hours > 0) {
            s += (added ? " " : "") + (int) hours + (hours == 1 ? " hour" : " hours");
            added = true;
        }
        if (minutes > 0) {
            s += (added ? " " : "") + (int) minutes + (minutes == 1 ? " minute" : " minutes");
            added = true;
        }

        return added ? s : "less than a minute";
    }

    public static String timeToDateString(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMMM yyyy");

        return sdf.format(new Date(l));
    }

    public static Long parseTime(String string) {
        long length = 0;
        string = string.toLowerCase();
        try {
            for (String split : string.split(":")) {
                if (split.contains("d")) {
                    long t = Integer.valueOf(split.replaceAll("d", ""));
                    length += t * 24 * 3600 * 1000;
                    continue;
                }
                if (split.contains("h")) {
                    long t = Integer.valueOf(split.replaceAll("h", ""));
                    length += t * 3600 * 1000;
                    continue;
                }
                if (split.contains("m")) {
                    long t = Integer.valueOf(split.replaceAll("m", ""));
                    length += t * 60 * 1000;
                    continue;
                }
                if (split.contains("s")) {
                    long t = Integer.valueOf(split.replaceAll("s", ""));
                    length += t * 1000;
                    continue;
                }
                return null;
            }
        } catch (NumberFormatException ex) {
            return null;
        }

        return length;
    }

}
