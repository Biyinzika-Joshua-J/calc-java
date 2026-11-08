public class Calc {
    private String display = "0";
    private double savedNumber = 0;
    private String operator = "";
    private boolean newNumber = true;

    public String press(String key) {
        if (key.equals("AC")) {
            display = "0";
            savedNumber = 0;
            operator = "";
            newNumber = true;
        } else if (key.equals("±")) {
            if (!display.equals("0") && !display.equals("Error")) {
                display = display.startsWith("-") ? display.substring(1) : "-" + display;
            }
        } else if (key.equals("%")) {
            if (!display.equals("Error")) {
                display = format(Double.parseDouble(display) / 100);
            }
        } else if (key.equals(".")) {
            if (newNumber || display.equals("Error")) {
                display = "0.";
                newNumber = false;
            } else if (!display.contains(".")) {
                display += ".";
            }
        } else if (key.equals("+") || key.equals("−") || key.equals("×") || key.equals("÷")) {
            if (!display.equals("Error")) {
                if (!operator.isEmpty() && !newNumber) {
                    calculate();
                }
                if (!display.equals("Error")) {
                    savedNumber = Double.parseDouble(display);
                    operator = key;
                    newNumber = true;
                }
            }
        } else if (key.equals("=")) {
            if (!operator.isEmpty() && !newNumber) {
                calculate();
                operator = "";
                newNumber = true;
            }
        } else {
            // A digit
            if (newNumber || display.equals("0") || display.equals("Error")) {
                display = key;
                newNumber = false;
            } else {
                display += key;
            }
        }
        return display;
    }

    private void calculate() {
        double currentNumber = Double.parseDouble(display);
        double result = savedNumber;

        if (operator.equals("+")) result += currentNumber;
        if (operator.equals("−")) result -= currentNumber;
        if (operator.equals("×")) result *= currentNumber;
        if (operator.equals("÷")) result /= currentNumber;

        display = format(result);
        if (display.equals("Error")) operator = "";
    }

    private String format(double number) {
        if (!Double.isFinite(number)) return "Error";
        String text = Double.toString(number);
        return text.endsWith(".0") ? text.substring(0, text.length() - 2) : text;
    }
}
