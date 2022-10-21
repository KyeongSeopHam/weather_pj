package nab_2.weather.repository;

import nab_2.weather.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
다이어리 레파지토리는 DB와 맞닿아있는 기능들을 구현하기위해서 레파지토리를 만듬

 */

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
}
