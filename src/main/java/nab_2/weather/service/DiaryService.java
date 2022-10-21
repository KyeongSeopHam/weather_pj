package nab_2.weather.service;

import nab_2.weather.domain.Diary;
import nab_2.weather.repository.DiaryRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DiaryService {

    @Value("${openweathermap.key}")
    private String apiKey;

    private final DiaryRepository diaryRepository;

    // 다이어리서비스 빈이 생성될떄 다이어리 레파지토리를 가져올거
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(LocalDate date, String text) {
/**
 1. open whather map에서 날씨 데이터 가져오기.
 */
        System.out.println(getWeatherString());    // 아래에서 반환된값을 출력해보자.
        String weatherData = getWeatherString(); // 위에서 원했던 json값이 오는걸확인했으니 그값을 저장해보자.

        /**
         2. 받아온 날씨 json 파싱하기
         */
        Map<String, Object> parsedWeather = parseWeather(weatherData);
        // test log
        for (Map.Entry<String, Object> entrySet : parsedWeather.entrySet()) {
            System.out.println(entrySet.getKey() + " :" + entrySet.getValue());
        }

        /**
         3. 파싱된 데이터 + 일기 값  내 db에 넣기
         // 데이터베이스에 어떤 데이터를 넣을떄 항상 했던 첫번쨰 작업
         도메인패키지 -> 보내고받기위한 클래스생성
         */

        Diary nowDiary = new Diary();
        nowDiary.setWeather(parsedWeather.get("main").toString());
        nowDiary.setIcon(parsedWeather.get("icon").toString());
        nowDiary.setTemperature((Double) parsedWeather.get("temp"));
        nowDiary.setText(text);
        nowDiary.setDate(date);
//  서비스단에서 -> 디비에저장하기위해서 -> 레파지토리를 거쳐야함

        diaryRepository.save(nowDiary);


    }

    private String getWeatherString() {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey;
        System.out.println(apiUrl);
        try {

            URL url = new URL(apiUrl); // URL 이라는것이 URL을만들고 url을 호출하고 결과값을 받아오는걸 하는작업 오류발생할수있으니 try/catch
            //HttpURLConnection connectio이 모냐?  apiUrl을 Http형식으로 연결을 시킨거
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //httpUrl커넥션을 만들어서 이 요청을 보낼수있는 http커넥션을열었음
            connection.setRequestMethod("GET"); // api를 호출할떄 GET으로 부를거기에 setRequestMethod는 GET으로 하겠다 / 그다음에 겟 요청을보냈다
            int responseCode = connection.getResponseCode(); //커넥션이열렸고 받아온 응답결과의 코드를  받아올수있다. ex)200,400,500 / 그다음에 응답코드를 받았음
            // 200 ok(정상)
            BufferedReader br;  // 버퍼드리드 br객체에 apiUrl에 요청을보낸다음에 그 응답결과의 인풋스트림 또는 에러스트림이 뜰거   / 그다음에 응답코드에따라서
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            } else {   //문제가있음 200이아니라면
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = br.readLine()) != null) {  // 버퍼드리드에 넣어두었던걸 하나하나 읽으면서
                response.append(inputLine);   // 리스폰스라는 스트링빌더에다가 그 결과값을 쌓는과정
            }
            br.close();


            return response.toString(); // 결과적으로 리스폰스라는객체에 api를 호출하고 받은결과값이  모두쌓였을거임 -> 리스폰스를 스트링화해서 이클래스에서 반환하자
        } catch (Exception e) {
            return "failed to get response _kyeongseop";
        }

    }

    /**
     * 받아온 json 스트링을 넣어줄거 /  제이슨  스트링을 받아와서 -> 제이슨형태로 변환해주는게 목적
     * parseWeather(String jsonString) 메소드
     * 1.날씨받아온걸 String 값으로 반환을했는데 그 값을 받아다가 JSONParse를이용해서 파싱을하고
     * 그 안에있는 원하는 값들을 값을 해쉬맵형태로 반환한 형태다.
     */
    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        // json파싱작업중 ex{ ]예외처리
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString); //jsonObject안에 담겨있음 <= (구글에서나온 jsonParser를써서 파싱을했음)
        } catch (ParseException e) {
            throw new RuntimeException(e); // 실행도중에 예외를던지는거 / 예외를 처리해서 없애는게아니라 그냥 예외가발생했다.
        }
        Map<String, Object> resultMap = new HashMap<String, Object>(); // 해시맵만들어주자 <String, Object>
        // "main":{"temp":290.21,"feels_like":289.33,"temp_min":287.81,"temp_max":290.91,"pressure":1026,"humidity":52} 이런형태로 담겨있는데
        JSONObject mainData = (JSONObject) jsonObject.get("main"); //  (jsonObject) 형변환해서 오브젝트 형식으로 인식함
        //내가필요한건 메인안에 일부분이니까 또 뽑자
        resultMap.put("temp", mainData.get("temp"));  // 최종적으로 파싱한값 => 온도값을 해시맵에넣자

        /** 꼭 알아두자
         "weather":[{"id":721,"main":"Haze","description":"haze","icon":"50d"}],
         중괄호가아니라 대괄호로 시작하는게 키포인트  제이슨오브젝트가아니라 제이슨어레이라는뜻
         weather같은경우 보면 [ { 인걸 확인할수있다. 바로 object타입이아닌 리스트형태인걸 확인할수있다 이작업을 처리해줘야함 안그래서 에러가뜬거
         */
        JSONArray wetherArray = (JSONArray) jsonObject.get("weather");  // json어레이를 -> json오브젝트로 형변환
        // "weather":[{"id":721,"main":"Haze","description":"haze","icon":"50d"}],
        JSONObject weatherData = (JSONObject) wetherArray.get(0); //데이터가 특이한게 리스트안에는 들어있기는한데 리스트안에 객체가 단한개뿐 그래서0번째객체를
        //가져와서 그걸 JsonObejct로 만들어주면됨

        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;
    }


}
