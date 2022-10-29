package nab_2.weather.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import nab_2.weather.domain.Diary;
import nab_2.weather.service.DiaryService;
import org.springframework.context.annotation.Description;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 컨트롤러라는건 생각해보면 클라이언트와 맞닿아있는 애인데
 * 내가만들 날씨일기어플에 어떤 api를 제공해줘야할지? 생각해보자.
 * <p>
 * 1. 날씨일기를 작성하는 api인데 api의 경로를 지정을해줘야할것이다.
 * <p>
 * 함수를 만들어보자 createDiary
 * <p>
 * 이 함수에 어떤 path를통해서 요청을 보냈을때 이 함수가 동작할것인가 명시해보자.
 * /create/diary 라는경로로 요청을 보냈을떄  createDiary()동작
 * 겟맵핑은:조회할떄 많이쓰고  포스트맵핑은 보통 : 저장할때 많이쓰임
 * 그런데 생각해보면 /create/diary 말고도 다이어리안에 쓴 텍스트값이라던가
 * 이 일기를 언제썻는지에 값이라던가 그 값들을
 * 파라미터와 리퀘스트바디로 받아보자.
 * <p>
 * mvc 패턴에따라서 만들어야했던
 * Diary  컨트롤러, 서비스, 레파지토리
 * <p>
 * 1. 클라이언트에서  앱측에서 일기를쓰고  ex) 10월21일 ~~~~~
 * 앱에서 -> 컨트롤러로 보내줘서 그 값을 POST로 받고 서비스까지 던저준작업을 했음
 * 이 함수에 어떤 path를통해서 요청을 보냈을때 이 함수가 동작할것인가 명시해보자.
 * /create/diary 라는경로로 요청을 보냈을떄  createDiary()동작
 * 겟맵핑은:조회할떄 많이쓰고  포스트맵핑은 보통 : 저장할때 많이쓰임
 * 그런데 생각해보면 /create/diary 말고도 다이어리안에 쓴 텍스트값이라던가
 * 이 일기를 언제썻는지에 값이라던가 그 값들을
 * 파라미터와 리퀘스트바디로 받아보자.
 * <p>
 * mvc 패턴에따라서 만들어야했던
 * Diary  컨트롤러, 서비스, 레파지토리
 * <p>
 * 1. 클라이언트에서  앱측에서 일기를쓰고  ex) 10월21일 ~~~~~
 * 앱에서 -> 컨트롤러로 보내줘서 그 값을 POST로 받고 서비스까지 던저준작업을 했음
 * 이 함수에 어떤 path를통해서 요청을 보냈을때 이 함수가 동작할것인가 명시해보자.
 * /create/diary 라는경로로 요청을 보냈을떄  createDiary()동작
 * 겟맵핑은:조회할떄 많이쓰고  포스트맵핑은 보통 : 저장할때 많이쓰임
 * 그런데 생각해보면 /create/diary 말고도 다이어리안에 쓴 텍스트값이라던가
 * 이 일기를 언제썻는지에 값이라던가 그 값들을
 * 파라미터와 리퀘스트바디로 받아보자.
 * <p>
 * mvc 패턴에따라서 만들어야했던
 * Diary  컨트롤러, 서비스, 레파지토리
 * <p>
 * 1. 클라이언트에서  앱측에서 일기를쓰고  ex) 10월21일 ~~~~~
 * 앱에서 -> 컨트롤러로 보내줘서 그 값을 POST로 받고 서비스까지 던저준작업을 했음
 * 이 함수에 어떤 path를통해서 요청을 보냈을때 이 함수가 동작할것인가 명시해보자.
 * /create/diary 라는경로로 요청을 보냈을떄  createDiary()동작
 * 겟맵핑은:조회할떄 많이쓰고  포스트맵핑은 보통 : 저장할때 많이쓰임
 * 그런데 생각해보면 /create/diary 말고도 다이어리안에 쓴 텍스트값이라던가
 * 이 일기를 언제썻는지에 값이라던가 그 값들을
 * 파라미터와 리퀘스트바디로 받아보자.
 * <p>
 * mvc 패턴에따라서 만들어야했던
 * Diary  컨트롤러, 서비스, 레파지토리
 * <p>
 * 1. 클라이언트에서  앱측에서 일기를쓰고  ex) 10월21일 ~~~~~
 * 앱에서 -> 컨트롤러로 보내줘서 그 값을 POST로 받고 서비스까지 던저준작업을 했음
 * 이 함수에 어떤 path를통해서 요청을 보냈을때 이 함수가 동작할것인가 명시해보자.
 * /create/diary 라는경로로 요청을 보냈을떄  createDiary()동작
 * 겟맵핑은:조회할떄 많이쓰고  포스트맵핑은 보통 : 저장할때 많이쓰임
 * 그런데 생각해보면 /create/diary 말고도 다이어리안에 쓴 텍스트값이라던가
 * 이 일기를 언제썻는지에 값이라던가 그 값들을
 * 파라미터와 리퀘스트바디로 받아보자.
 * <p>
 * mvc 패턴에따라서 만들어야했던
 * Diary  컨트롤러, 서비스, 레파지토리
 * <p>
 * 1. 클라이언트에서  앱측에서 일기를쓰고  ex) 10월21일 ~~~~~
 * 앱에서 -> 컨트롤러로 보내줘서 그 값을 POST로 받고 서비스까지 던저준작업을 했음
 * 이 함수에 어떤 path를통해서 요청을 보냈을때 이 함수가 동작할것인가 명시해보자.
 * /create/diary 라는경로로 요청을 보냈을떄  createDiary()동작
 * 겟맵핑은:조회할떄 많이쓰고  포스트맵핑은 보통 : 저장할때 많이쓰임
 * 그런데 생각해보면 /create/diary 말고도 다이어리안에 쓴 텍스트값이라던가
 * 이 일기를 언제썻는지에 값이라던가 그 값들을
 * 파라미터와 리퀘스트바디로 받아보자.
 * <p>
 * mvc 패턴에따라서 만들어야했던
 * Diary  컨트롤러, 서비스, 레파지토리
 * <p>
 * 1. 클라이언트에서  앱측에서 일기를쓰고  ex) 10월21일 ~~~~~
 * 앱에서 -> 컨트롤러로 보내줘서 그 값을 POST로 받고 서비스까지 던저준작업을 했음
 * 이 함수에 어떤 path를통해서 요청을 보냈을때 이 함수가 동작할것인가 명시해보자.
 * /create/diary 라는경로로 요청을 보냈을떄  createDiary()동작
 * 겟맵핑은:조회할떄 많이쓰고  포스트맵핑은 보통 : 저장할때 많이쓰임
 * 그런데 생각해보면 /create/diary 말고도 다이어리안에 쓴 텍스트값이라던가
 * 이 일기를 언제썻는지에 값이라던가 그 값들을
 * 파라미터와 리퀘스트바디로 받아보자.
 * <p>
 * mvc 패턴에따라서 만들어야했던
 * Diary  컨트롤러, 서비스, 레파지토리
 * <p>
 * 1. 클라이언트에서  앱측에서 일기를쓰고  ex) 10월21일 ~~~~~
 * 앱에서 -> 컨트롤러로 보내줘서 그 값을 POST로 받고 서비스까지 던저준작업을 했음
 */


/**이 함수에 어떤 path를통해서 요청을 보냈을때 이 함수가 동작할것인가 명시해보자.
 /create/diary 라는경로로 요청을 보냈을떄  createDiary()동작
 겟맵핑은:조회할떄 많이쓰고  포스트맵핑은 보통 : 저장할때 많이쓰임
 그런데 생각해보면 /create/diary 말고도 다이어리안에 쓴 텍스트값이라던가
 이 일기를 언제썻는지에 값이라던가 그 값들을
 파라미터와 리퀘스트바디로 받아보자.

 mvc 패턴에따라서 만들어야했던
 Diary  컨트롤러, 서비스, 레파지토리

 1. 클라이언트에서  앱측에서 일기를쓰고  ex) 10월21일 ~~~~~
 앱에서 -> 컨트롤러로 보내줘서 그 값을 POST로 받고 서비스까지 던저준작업을 했음

 */


//@Controller
// @RestController 쓰면 컨트롤러랑 차이가모냐? 어떤기능이추가되는가 Http응답을보낼때 200, 404 상태코드들을
// 이 컨트롤러에서 지정을해서 ex) 잘못보냈어 404 내려보낼수있게끔 해주는역할

/**브라우저에 그냥 테스트하는건 문제점들이있다.
 ex) 브루아저에다가 localhost8080:/create/diary 했을떄 브라우저입장에서는 get요청을 보내는걸로 브라우저가 인식을 합니다.
 그래서 post요청을 보내기에는 적합하지않습니다. 또 하나 브라우저는 기본적으로 캐싱을 합니다.
 그래서 브라우저는항상 받아온 웹사이트 데이터를 빨리빨리 서빙을해줘야하는데 (그려줘야하는데) 캐싱이라는 개념이 브라우저에 녹아져있기에
 api 테스트하기에는 지금 보내는 요청에대한 결과값을 정확히 보고싶어서 테스트하는건데 몬가 캐싱된 데이터의값의 영향을받아서
 다른 데이터값이 나올수있기떄문에  xxxxx => api 테스트를 간단히하고싶을떄 포스트맨이용해보자.
 */


@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // <C>
    @ApiOperation(value = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장")
    @PostMapping("/create/diary")
    // 이렇게 받은값들을 서비스에 전달을 해줘야하는데 그래서 다이어리서비스 만들어둔걸 사용하자(애한테 전달해줘야하니)
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     @ApiParam(value = "작성할 일기의 날짜를 입력해주세요.", example = "2022-10-29") LocalDate date,
                     @RequestBody
                     @ApiParam(value = "아래와 같이 일기를 작성해주세요.", example = "오늘 날씨가 매우맑았다. 오늘은 치킨을 시켰는데 너무 좋았다.")
                     String text) {

        diaryService.createDiary(date, text);

    }

    /**   <R>
     iso = DateTimeFormat.ISO.DATE  "yyyy-MM-dd"
     날씨 일기 조회 API 구현 ( 일기의 날짜값정도면 input으로 처리하면되니) 조회니까 GET맵핑이용하자
     */
    @ApiOperation(value = "선택한 날짜의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                          @ApiParam(value = "조회할 일기의 날짜를 입력해주세요.", example = "2022-10-29")
                          LocalDate date) {
        return diaryService.readDiary(date); // 클라이언트에서 이 date값의 일기좀 알려줘 -> 서비스한테 -> 그럼 서비스는 작업을처리해서 ->컨트롤러에게 다시알려줌
    }


    /**  A ~ B 기간 주어서 조회해보기   <R>
     2022-02-05 ~ 2022-02-28 까지 조회해보기
     */
    @ApiOperation(value = "선택한 기간중의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diaries")
    // 다수의날짜를 표현해봄 pathName으로    /  start-end로 구분
    List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "조회할 기간의 마지막날 입니다.", example = "2022-10-28") LocalDate startDate,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "조회할 기간의 첫번째날 입니다.", example = "2022-10-29")
                            LocalDate endDate
    ) {

        return diaryService.readDiaries(startDate, endDate);
    }

    /**
     다이어리 수정  < U  >
     어떤날의 일기를 수정할건지
     */
    @ApiOperation(value = "선택한 날짜중의 첫번째 일기 데이터를 수정합니다.")
    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     @ApiParam(value = "수정할 날짜를 입력해주세요.(날짜가 같을경우 최근일기 수정)", example = "2022-10-29") LocalDate date,
                     @RequestBody String text
    ) {
        diaryService.updateDiary(date, text);
    }


    @ApiOperation(value = "선택한 날짜에 작성한 일기 데이터를 수정합니다.")
    @PutMapping("/update/diary/{id}")
    void updateDiary2(@PathVariable @ApiParam(value = "아이디 값을 입력하세요. 먼저 조회를통해서 아이디값을 확인하세요", example = "3") int id,
                      @RequestBody @ApiParam(value = "수정할 내용을 작성하세요.", example = "오늘은 춥네요.") String text
    ) {
        diaryService.updateDiary2(id, text);
    }


    /**
     날씨 삭제 <D>
     */
    @ApiOperation(value = "선택한 날짜중의 첫번째 일기 데이터를 삭제합니다.")
    @DeleteMapping("/delete/diary")
    void deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     @ApiParam(value = "삭제할 날짜를 입력해주세요.", example = "2022-10-29") LocalDate date) {
        diaryService.deleteDiary(date);
    }


}
