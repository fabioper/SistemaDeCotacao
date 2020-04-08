package br.edu.infnet.ui.utils;

import javafx.scene.control.TextFormatter;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class CurrencyFilter implements UnaryOperator<TextFormatter.Change> {
    private NumberFormat formatter;
    public final String NOT_ALPHANUMERIC_CHARS = "[^0-9]";


    public CurrencyFilter() {
        var locale = new Locale("pt", "br");
        formatter = NumberFormat.getCurrencyInstance(locale);
    }

    @Override
    public final TextFormatter.Change apply(TextFormatter.Change valueChanged) {
        var controlText = valueChanged.getControlText();

        if (valueChanged.isReplaced() && valueChanged.getText().matches(NOT_ALPHANUMERIC_CHARS)) {
            var rangeStart = valueChanged.getRangeStart();
            var rangeEnd = valueChanged.getRangeEnd();
            var substring = controlText.substring(rangeStart, rangeEnd);
            valueChanged.setText(substring);
        }

        if (valueChanged.isAdded()) {
            if (valueChanged.getControlText().contains(",")) {
                if (valueChanged.getText().matches(NOT_ALPHANUMERIC_CHARS)) {
                    valueChanged.setText("");
                }
            } else if (valueChanged.getText().matches("[^0-9,]")) {
                valueChanged.setText("");
            }
        }

        return valueChanged;
    }
}
