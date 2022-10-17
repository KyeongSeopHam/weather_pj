package nab_2.weather.repository;

import nab_2.weather.domain.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class JpaMemoRepositoryTest {

    @Autowired   // JPA를 활용한 Memo데이터를 전송할수있는 jpaMemoRepository를 불러왔음
    JpaMemoRepository jpaMemoRepository;

    @Test
    void insertMemoTest(){
        // given (주어진것)
        Memo newMemo = new Memo(10,"this is jpa memo~~~!!!");

        // when ( ~~을 했을떄)
        jpaMemoRepository.save(newMemo);

        //then (그러고나면 이럴것이다.~ 보통은 assert문이들어감)

        List<Memo> memoList = jpaMemoRepository.findAll(); // List<T> findAll();
        assertTrue(memoList.size()>0);
    }

    @Test
    void findByIdTest(){
        //given
        Memo newMemo = new Memo(11,"JPA");


        //when
       Memo memo = jpaMemoRepository.save(newMemo); // 세이브하고나면 리턴값이 메모일거임
        System.out.println(memo.getId());  //아이디값출력

        /** 아래에서 설명
        Hibernate: insert into memo (text) values (?)
                9
         */


        //then
        // findById함수를 활용해서 ID의11인 애를찾는거 그러면 딱 new Memo(11,"JPA") 이 메모객체가 나올텐데 저객체가 돌아와야정상
//        Optional<Memo> result = jpaMemoRepository.findById(11); //객체를 담고 옵셔널 처리를해주자 이렇게돌리면에러가날거임 왜???
        /**
         Hibernate: insert into memo (text) values (?)
          9
          우린 처음에 마리아디비에 11로 키값을 넣어서 보냈지만
          이 출력된 값을 보면  9로 출력된걸 확인할수 있다.
        이  Memo newMemo = new Memo(11,"JPA"); 가 들어있는 메모 객체가
         마리아디비에가서 id값이 오토인크리먼트 되면서 9라는 id값을 얻게된것이다.
         그렇기때문에 save 하고나서 반환된 메모의 아이디를 아래와 같이 넣어줘야 "테스트코드"가 성공할것이다.
         findById()값에넣어주자

         */

        Optional<Memo> result = jpaMemoRepository.findById(memo.getId()); //객체를 담고 옵셔널 처리를해주자 이떄주의해야할것이있음 파라미터는


        // assertEquals(result.get().getText(),"jpa"); 테스트실패
         assertEquals(result.get().getText(),"JPA"); //테스트성공

    }

    /**

     정리하자면
     1.JPA를 활용해서 이 쿼리문들을 직접작성하거나 불필요한 코드를 작성하는거없이
     save,findById, findAll 등  이런함수들을 다 쓸수있다.
     2. 메모라는 객체라는 안에다가 id를 11로 지정했음에도 불구하고 마리아디비는 id라는 컬럼이
     오토인크리먼트 정책을가지고있기떄문에 본인원하는대로 값을 ex) 9  로 만들어줬다.
     그럼 여기서 얻을수있는 힌트는 메모객체에다가 id값을 null값을 넣어서 보내도되겠구나 라는걸 확인할수있다. (id를 매번 굳이넣을필요가없겠다)
     */

}