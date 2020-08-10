package com.azuxdario.spacexlaunches;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    private static final String DATE_PRECISION_HALF = "half";
    private static final String DATE_PRECISION_QUARTER = "quarter";
    private static final String DATE_PRECISION_YEAR = "year";
    private static final String DATE_PRECISION_MONTH = "month";
    private static final String DATE_PRECISION_DAY = "day";
    private static final String DATE_PRECISION_HOUR = "hour";

    public static String getParsedDate(String dateToParse, String precision) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String result = null;
        try {
            Date date = format.parse(dateToParse);

            if (date != null) {
                switch (precision) {
                    case DATE_PRECISION_HALF:
                        break;
                    case DATE_PRECISION_QUARTER:
                        break;
                    case DATE_PRECISION_YEAR:
                        SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");
                        result = yearDateFormat.format(date);
                        break;
                    case DATE_PRECISION_MONTH:
                        SimpleDateFormat monthDateFormat = new SimpleDateFormat("MM-yyyy");
                        result = monthDateFormat.format(date);
                        break;
                    case DATE_PRECISION_DAY:
                        SimpleDateFormat dayDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        result = dayDateFormat.format(date);
                        break;
                    case DATE_PRECISION_HOUR:
                        SimpleDateFormat hourDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                        result = hourDateFormat.format(date);
                        break;
                    default:
                        break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}