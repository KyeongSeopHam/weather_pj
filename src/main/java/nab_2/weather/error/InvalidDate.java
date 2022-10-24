package nab_2.weather.error;

public class InvalidDate extends RuntimeException {
    private static final String MESSAGE = "너무 과거나 미래 날짜임 예외처리";

    public InvalidDate() {
        super(MESSAGE);
    }


}
