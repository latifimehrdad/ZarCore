package com.zar.core.view.picker.date.utils;

public class PersianCalendarUtils {
    static int oldMonth = -1, oldDay = 0, oldYear = 0;
    public static long persianToJulian(long year, int month, int day) {
        return 365L *
                ((ceil(year - 474L, 2820D) + 474L) - 1L) +
                ((long) Math.floor((682L * (ceil(year - 474L, 2820D) + 474L) - 110L) / 2816D)) +
                (PersianCalendarConstants.PERSIAN_EPOCH - 1L) + 1029983L *
                ((long) Math.floor((year - 474L) / 2820D)) +
                (month < 7 ? 31 * month : 30 * month + 6) + day;
    }

    public static boolean isPersianLeapYear(int persianYear) {
        int[] leapYears = {
                1301, 1305, 1309, 1313, 1317, 1322, 1326, 1330, 1334, 1338,
                1342, 1346, 1350, 1354, 1358, 1363, 1367, 1371, 1375, 1379,
                1383, 1387, 1391, 1395, 1399, 1403, 1408, 1412, 1416, 1420,
                1424, 1428, 1432, 1436, 1440, 1445, 1449, 1453, 1457, 1461,
                1465, 1469, 1473, 1477, 1481, 1486, 1490, 1494, 1498
        };
        for (int year : leapYears) {
            if (year == persianYear) {
                return true;
            }
        }
        return false;
    }

    public static long julianToPersian(long julianDate) {
        long depoch = julianDate - persianToJulian(475L, 0, 1);
        long cycle = depoch / 1029983L;
        long cyear = depoch % 1029983L;

        long ycycle;

        if (cyear == 1029982L) {
            ycycle = 2820L;
        } else {
            long aux1 = cyear / 366L;
            long aux2 = cyear % 366L;
            ycycle = (long) Math.floor((2134D * aux1 + 2816D * aux2 + 2815D) / 1028522D) + aux1 + 1;
        }

        long year = ycycle + 2820L * cycle + 474L;
        if (oldMonth == 11 && oldDay == 29)
            if (isPersianLeapYear(oldYear))
                year = oldYear;
        if (year <= 0) year--;

        long newYearJulian = persianToJulian(year, 0, 1);
        long yday = julianDate - newYearJulian + 1;
        if (oldMonth == 11 && oldDay == 30)
            yday = yday - 1;

        if (yday == 0)
            yday = 1;

        int month, day;

        if (yday <= 186) {
            month = (int) ((yday - 1) / 31);
            day = (int) ((yday - 1) % 31) + 1;
        } else if (yday <= 336) {
            month = (int) ((yday - 187) / 30) + 6;
            day = (int) ((yday - 187) % 30) + 1;
        } else {
            // حالا بررسی می‌کنیم آیا سال کبیسه‌س یا نه
            if (isPersianLeapYear((int) year)) {
                if (yday == 366) {
                    month = 11;
                    day = 30;
                } else {
                    month = 11;
                    day = (int) (yday - 336);
                }
            } else {
                // در سال غیرکبیسه نباید بیشتر از 365 باشه
                month = 11;
                day = (int) (yday - 336);
            }
        }

        oldDay = day;
        oldMonth = month;
        oldYear = (int)year;
        return (year << 16) | (month << 8) | day;
    }



    public static long ceil(double double1, double double2) {
        return (long) (double1 - double2 * Math.floor(double1 / double2));
    }
}
