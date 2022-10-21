package nab_2.weather.repository;

import nab_2.weather.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/*
다이어리 레파지토리는 DB와 맞닿아있는 기능들을 구현하기위해서 레파지토리를 만듬

 */

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    List<Diary> findAllByDate(LocalDate date); // jpa에서 알아서 findAllDate 이러한 함수로 알아서 쿼리생성, 데이터베이스에서값을가져와주는거
    List<Diary> findAllByDateBetween(LocalDate starDate , LocalDate endDate);

    //getFirstByDate : limit1 느낌
    Diary getFirstByDate(LocalDate date);

    //일기 삭제



@Transactional  // 애를 안붙이면 안지워지네..?
    void deleteAllByDate(LocalDate date);


}
