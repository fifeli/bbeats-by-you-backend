package com.sentura.beatsbyyou.repository;

import com.sentura.beatsbyyou.entity.BeatPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BeatPriceRepository extends JpaRepository<BeatPrice,Long> {

    BeatPrice findFirstById(long id);

    @Query(nativeQuery = true,value = "SELECT * FROM BEAT_PRICE WHERE :seconds BETWEEN START_TIME_SECONDS AND END_TIME_SECONDS ORDER BY ID DESC LIMIT 1")
    BeatPrice findBeatPriceByTimeRange(@Param("seconds") int seconds);

    @Query(nativeQuery = true,value = "SELECT * FROM BEAT_PRICE WHERE START_TIME_SECONDS=:startSeconds AND END_TIME_SECONDS=:endSeconds ORDER BY ID DESC LIMIT 1")
    BeatPrice findBeatPriceByStartAndEndTimes(@Param("startSeconds") int startSeconds,@Param("endSeconds") int endSeconds);
}
