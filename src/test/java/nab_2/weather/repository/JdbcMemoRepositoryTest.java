package nab_2.weather.repository;

import nab_2.weather.domain.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // db를 테스트할떄 테스트코드떄문에 데이터베이스안에있는 정보가 변경이되면 안되기떄문에 그걸 막기위한 어노테이션이다.
class JdbcMemoRepositoryTest {

    @Autowired
    JdbcMemoRepository jdbcMemoRepository;

    @Test
    void insertMemoTest() {
        // given (주어진것)
        Memo newMemo = new Memo(2, "insertMemoTest");

        // when ( ~~을 했을떄)  // jdbcMemoRepository를 활용해서 save했을떄
        jdbcMemoRepository.save(newMemo);

        //then (그러고나면 이럴것이다.~ 보통은 assert문이들어감) then에서는 newMemo를 save했기때문에
        Optional<Memo> result =jdbcMemoRepository.findById(2);
        assertEquals(result.get().getText(),"insertMemoTest");

    }

    @Test
    void findAllMemoTest(){
        List<Memo> memoList = jdbcMemoRepository.findAll();
        System.out.println(memoList); // log [nab_2.weather.domain.Memo@fabef2e]
        assertNotNull(memoList);
    }
}