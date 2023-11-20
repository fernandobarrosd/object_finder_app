package br.ifsul.objectfinder_ifsul.design_patterns.factories;

import androidx.annotation.NonNull;
import java.util.Calendar;
import br.ifsul.objectfinder_ifsul.classes.Date;

public abstract class DateFactory {
    public static Date fromCalendar(@NonNull Calendar calendar) {
        return new Date.DateBuilder()
                .setDay(calendar.get(Calendar.DAY_OF_MONTH))
                .setMonth(calendar.get(Calendar.MONTH))
                .setYear(calendar.get(Calendar.YEAR))
                .build();
    }
}
