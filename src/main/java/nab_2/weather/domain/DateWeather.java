package nab_2.weather.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

/*
  create table date_weather(
    date DATE not null primary key ,
    weather varchar(50) not null ,
    icon varchar(50) not null,
    temperature DOUBLE NOT NULL
);
 */
@Getter
@Setter
@Entity(name = "date_weather")
@NoArgsConstructor
@AllArgsConstructor
public class DateWeather {
    @Id
    private LocalDate date;
    private String weather;
    private String icon;
    private Double temperature;
}
