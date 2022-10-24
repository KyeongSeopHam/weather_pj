package nab_2.weather.service;

import nab_2.weather.WeatherApplication;
import nab_2.weather.domain.DateWeather;
import nab_2.weather.domain.Diary;
import nab_2.weather.error.InvalidDate;
import nab_2.weather.repository.DateWeatherRepository;
import nab_2.weather.repository.DiaryRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true) // 이 다이어리서비스안에있는 모든 메소드들이 트랜잭션으로되고 다 readOnly만 가능하게된다.
public class DiaryService {

    @Value("${openweathermap.key}")
    private String apiKey;

    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository; // 특정시간에 캐싱처리하기위해서 레파지토리쓰려구

    private static final Logger logger = LoggerFactory.getLogger(WeatherApplication.class);


    // 다이어리서비스 빈이 생성될떄 다이어리 레파지토리를 가져올거
    public DiaryService(DiaryRepository diaryRepository, DateWeatherRepository dateWeatherRepository) {
        this.diaryRepository = diaryRepository;
        this.dateWeatherRepository = dateWeatherRepository;

    }


    @Transactional
    // 매익 특정 시간마다 날씨 데이터를 저장해주는 함수
    @Scheduled(cron = "0 0 2 * * *")   // 초 분 시 일 월  새벽2시마다 업데이트되게
    public void saveWeatherDate(){
        dateWeatherRepository.save(getWeatherFromApi());  // db안에 데이터웨덜 엔티티를 저장해야할거 날씨데이터를 가져와야하니까 그함수만들자
    }




@Transactional(isolation = Isolation.SERIALIZABLE)
    public void createDiary(LocalDate date, String text) {

        logger.info("다이어리 시작 로그");


        // 날씨 데이터 가져오기 (api에서가져오기 x db에 이미 저장된거가져오기 전날에저장한거)
//        DateWeather dateWeather = getDateWeather(date);  <- 이렇게하면 (ex 2000-10-10)입력해도 오늘날짜로가져옴
        DateWeather dateWeather = getDateWeather2(date);
        



        /**
         3. 파싱된 데이터 + 일기 값  내 db에 넣기
         // 데이터베이스에 어떤 데이터를 넣을떄 항상 했던 첫번쨰 작업
         도메인패키지 -> 보내고받기위한 클래스생성
         */

/**
   1. 매번 다이어리를 쓸때마다 날씨데이터를 가져오는 파싱하는 불필요한 작업을생략
   2. 매일매일 api에서 가져와서 db에 저장했기떄문에 시간에따라 데이터가 쌓이면  비용적측면에서 유리 / 캐싱
 */

        Diary nowDiary = new Diary();
       nowDiary.setDateWeather(dateWeather);
        nowDiary.setText(text);
//  서비스단에서 -> 디비에저장하기위해서 -> 레파지토리를 거쳐야함

        diaryRepository.save(nowDiary);


        logger.info("다이어리 로그 끝");
    }

    
    //getDateWeather2  특정날짜입력시 db에서가져오든 api에서가져오든 데이터가져오기
    private DateWeather getDateWeather2(LocalDate date) {

        List<DateWeather> allByDateListFormDB = dateWeatherRepository.findAllByDate(date);

        if(allByDateListFormDB.size()==0){
            // db 없으니까 api에서 가져와야함

            return getWeatherFromApi2(date);
        }else{
            return allByDateListFormDB.get(0);
        }


    }

    private DateWeather getWeatherFromApi2(LocalDate date) {

        /**
         1. open whather map에서 날씨 데이터 가져오기.
         */

        String weatherData = getWeatherString(); // 위에서 원했던 json값이 오는걸확인했으니 그값을 저장해보자.

        /**
         2. 받아온 날씨 json 파싱하기
         */
        Map<String, Object> parsedWeather = parseWeather(weatherData);
        DateWeather dateWeather = new DateWeather();

        dateWeather.setDate(date);

        dateWeather.setWeather(parsedWeather.get("main").toString());
        dateWeather.setIcon(parsedWeather.get("icon").toString());
        dateWeather.setTemperature((Double) parsedWeather.get("temp"));

        return dateWeather;


    }


    // 2020-05-05
    private DateWeather getDateWeather(LocalDate date) {
        // !!!일단  ex) 10/22일 값을 가져오고싶다면  10/22날씨 db가있는지 확인해보고(스캐쥴링적용하니까)

        List<DateWeather> dateWeatherListFromDB = dateWeatherRepository.findAllByDate(date);


        if(dateWeatherListFromDB.size()==0){
            // 새로 api에서 날씨정보를 가져와야한다.
            return getWeatherFromApi();
        }else { // 그날의저장된 날씨db가있다면?
                return dateWeatherListFromDB.get(0);
        }



    }

    // 그럼여기서는 다이어리 레파지토리에서 가져와야할것이다. 무엇을? -> 일기값을
    // 일기값을 가져오려면 db를조회해야하고 서비스입장에서는 db를 조회하려면 레파지토리를통해야할것이다.
    // 레파지토리에서 이 date라는 값을 기준으로  그날의 일기의 데이터를 가져오고싶은데...
    @Transactional(readOnly = true)
    public List<Diary> readDiary(LocalDate date){

//        if(date.isAfter(LocalDate.ofYearDay(3000,10))){
//            throw new InvalidDate();
//        }


        return diaryRepository.findAllByDate(date);
    }


    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate){
        return diaryRepository.findAllByDateBetween(startDate,endDate);
    }


    /**
    예로들어 ex)  10월21일에 쓴 일기가 2건이라 생각했을때 updateDiary() 2건다 수정해버리면 문제가생기니
     API에서 1번째것만 수정하는걸로 정의하는걸로하자
     그럼 첫번째 일기가 무엇인지 가져와야하는상황
     */

    public void updateDiary(LocalDate date, String text){
        Diary nowDiary = diaryRepository.getFirstByDate(date);
        //getFirstByDate(date) 이날짜에 있는 데이터 하나를 가져오는거 limit1
        nowDiary.setText(text);
        diaryRepository.save(nowDiary); // 덮어씌우자
    }
    /**
    삭제
     */
    public void deleteDiary(LocalDate date){
        diaryRepository.deleteAllByDate(date);
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




    // getWeatherFromApi() ->  open whather map에서 날씨 데이터 가져오기. -> 받아온 날씨 json 파싱하기 -> 그 결과물을 DateWeather객체의 형식에넣어서 반환
    private DateWeather getWeatherFromApi(){
        /**
         1. open whather map에서 날씨 데이터 가져오기.
         */
//        System.out.println(getWeatherString());    // 아래에서 반환된값을 출력해보자.

        String weatherData = getWeatherString(); // 위에서 원했던 json값이 오는걸확인했으니 그값을 저장해보자.

        /**
         2. 받아온 날씨 json 파싱하기
         */
        Map<String, Object> parsedWeather = parseWeather(weatherData);
        DateWeather dateWeather = new DateWeather();

        dateWeather.setDate(LocalDate.now());

        dateWeather.setWeather(parsedWeather.get("main").toString());
        dateWeather.setIcon(parsedWeather.get("icon").toString());
        dateWeather.setTemperature((Double) parsedWeather.get("temp"));

        return dateWeather;
    }




}
