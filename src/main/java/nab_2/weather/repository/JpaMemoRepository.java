package nab_2.weather.repository;

import nab_2.weather.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 JPA는 자바의 표준 ORM 명세
 자바에서 ORM 개념을 활용할때  쓸 함수들을 JpaRepository<>라는곳에 이미 저장이 다 되어있다.
 이걸 가져와서 쓰기만하면된다.  < class, 이클래스의형식의 타입 여이선 Integer >
 */


@Repository
public interface JpaMemoRepository extends JpaRepository<Memo, Integer> {
}
