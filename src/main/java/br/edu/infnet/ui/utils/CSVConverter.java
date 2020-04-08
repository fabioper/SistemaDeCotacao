package br.edu.infnet.ui.utils;

import java.util.List;
import java.util.StringJoiner;

public class CSVConverter<T> {
    public final String convert(List<? extends T> elements, ConverterCallback<? super T> cb) {
        StringJoiner joiner = new StringJoiner("\n");

        for (T element : elements) {
            String format = cb.format(element);
            joiner.add(format);
        }

        return joiner.toString();
    }

    public interface ConverterCallback<T> {
        String format(T element);
    }
}
