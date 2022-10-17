package nab_2.weather.repository;

import nab_2.weather.domain.Memo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * DB작업을 하기위해서 Memo라는 객체를 저장도할수있어야할거같고, 조회도 할수있어야할거같다.
 * 이 레파지토리안에서 그런 함수들을 만들어보자.
 */

@Repository
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired // 알아서 application.properties에서 dataSource를 가져옴
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * domain/Memo라는 클래스를 만들었는데 이 클래스의 값을 저장하면
     * 마리아디비에다가 Memo라는클래스값이 저장이되고 반환값은 Memo를반환
     * JDBC의 특징 : 쿼리를 직접써야한다.
     */
    public Memo save(Memo memo) {
        String sql = "insert into memo values(?,?)"; // 메모라는 테이블에다가 insert를 쿼리를 직접작성
        jdbcTemplate.update(sql, memo.getId(), memo.getText()); // 업데이트할 쿼리입력 , (?,?) 에 대한부분 객체입력
        return memo;
    }


    /**
     * 위에서 저장했으니 조회하는 함수 ( 그전에 RowMapper를만들자)
     * RowMapper란 : jdbc를통해서 maraidb에서 데이터를 가져오면 그 가져온값은 "ResultSet" 이라는 형식의 데이터값입니다.
     * "ResultSet은 어떤형태냐면 { id=1,text='this is memo~'} 이런형식을 말한다. => ResultSet이라는 형식으로 DB에서 데이터를가져오게됩니다.
     * 그런데 이런형식을 결국은 스프링부트에 만들어둔 Memo라는 클래스에 대입을 시켜야하는데 ResultSet에 Memo라는 형식으로 매핑해주는걸 RowMapper라고 합니다.
     */
    private RowMapper<Memo> memoRowMapper() {
        //rs는 ResultSet을 줄여서 말한거,
        return ((rs, rowNum) -> new Memo(
                rs.getInt("id"),
                rs.getString("text")
        ));

    }


    /**
     jdbc탬플릿이. (마리아)디비에가서 위,sql데이터를 던지고
     던졌을떄 반환되는 객체들을 ResultSet형태로 가지고있는다헀는데 ResultSet 형태의 데이터를 미리 명시해둔
     Memo로 memoRowMapper()를 이용해서 Memo객체로 가져왔다 라고 생각하면된다.
     */

    public List<Memo> findAll(){
        String sql = "select * from memo";
        return jdbcTemplate.query(sql,memoRowMapper());
    }



 // findByid()를통해서 메모객체를 찾았을때 혹시 id가 3인객체를 찾겠다고 했는데 3인객체가 없다면 Optional이라는 객체로래핑해줘서
    // 혹시모를 null 값을 처리하기 쉽게 해주는 함수
   public Optional<Memo> findById(int id){
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql,memoRowMapper(),id).stream().findFirst();

   }





}
