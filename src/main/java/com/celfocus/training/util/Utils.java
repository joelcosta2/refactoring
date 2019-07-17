package com.celfocus.training.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.Exception;
import java.text.SimpleDateFormat;
import java.util.*;

import com.celfocus.training.view.TypeFile;
import org.apache.commons.codec.binary.Hex;

import static com.celfocus.training.util.constant.ConstantStrings.FORMAT_DATE;

public final class Utils {

    private Utils() {}

    static MessageDigest sha256;
    
    static {
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
                throw new ArgumentNullException(e);
        }
    }
    
    public static String toHexStringSHA256(String source, Charset charset) {
        return Hex.encodeHexString(toSHA256(source.getBytes(charset)));
    }
    
    public static byte[] toSHA256(byte[] bytes) {
        return sha256.digest(bytes);
    }

    public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
    }
    
    public static Map<String, String> parseHTTPHeaderMap(String headers) {
        String value = headers.substring(1, headers.length() - 1);
        String[] keyValuePairs = value.split(",");
        Map<String, String> map = new HashMap<>();

        for (String pair : keyValuePairs)
        {
            String[] entry = pair.split("=", 2);

            if (entry.length > 1) {

                map.put(entry[0].trim(), entry[1].trim());
            }

        }
        return map;
    }

    public static String toString(Date date, String format) {
        return toString(date, new SimpleDateFormat(format));
    }

    public static String toString(Date date, DateFormat format) {
        Objects.requireNonNull(date);
        Objects.requireNonNull(format);
        return format.format(date);
    }

    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    @SafeVarargs
    public static <T> java.util.List<T> createListFromArray(T... ts) {
        if (ts == null) {
            return new ArrayList<>(0);
        }
        java.util.List<T> list = new ArrayList<>(ts.length);
        for (T t : ts) {
            list.add(t);
        }
        return list;
    }

    @SafeVarargs
    public static <K, V> Map<K, V> createMapFromArray(Object... ts) {
        if (ts == null) {
            return new HashMap<>(0);
        }
        if (ts.length % 2 != 0) {
            throw new IllegalArgumentException("Length should be pair");
        }
        int length = ts.length / 2;
        Map<K, V> map = new HashMap<>(length);
        for (int index = 0; index <= length; index+=2) {
            map.put((K) ts[index], (V) ts[index + 1]);
        }
        return map;
    }

    public static Date parseToDate(String year, String month, String day) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(year)
                .append("/")
                .append(month)
                .append("/")
                .append(day);

        return parseStringToDate(stringBuilder.toString());
    }

    public static int getAgeFromDate(Date birthDate) {
        Calendar calendarNow = getCalendar(new Date(System.currentTimeMillis()));
        Calendar calendarBirthDate = getCalendar(birthDate);

        return calendarNow.get(Calendar.YEAR) - calendarBirthDate.get(Calendar.YEAR);

    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static Date parseStringToDate(String dateToParse){
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
        Date date = null;

        try {
            date = dateFormat.parse(dateToParse);
        } catch (Exception e) {
            LOGGER.log("ERROR ",e);
        }

        return date;
    }

    public static String dispatchView(TypeFile typeFile) {
        String file = null;
        switch (typeFile) {
            case HTML:

                break;
            case XML:

                break;
            default:
                file = "";
        }
        return file;
    }
}