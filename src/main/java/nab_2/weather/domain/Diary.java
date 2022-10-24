package nab_2.weather.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String weather;
    private String icon;
    private double temperature;
    private String text;
    private LocalDate date;


    //다이어리 라는 클래스안에다가 input으로 받은  DateWeather dateWeather 객체안의값들을넣어줌
    public void setDateWeather(DateWeather dateWeather){
        this.date=dateWeather.getDate();
        this.weather=dateWeather.getWeather();
        this.icon=dateWeather.getIcon();
        this.temperature=dateWeather.getTemperature();


    }
}
