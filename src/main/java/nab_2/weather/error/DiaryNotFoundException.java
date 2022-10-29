package nab_2.weather.error;

public class DiaryNotFoundException extends RuntimeException {
    private static final String MESSAGE = "다이어리를 못찾았습니다.";

    public DiaryNotFoundException() {
        super(MESSAGE);
    }


}
