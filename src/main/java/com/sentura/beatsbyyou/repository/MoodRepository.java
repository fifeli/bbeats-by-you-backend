package com.sentura.beatsbyyou.repository;

import com.sentura.beatsbyyou.entity.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodRepository extends JpaRepository<Mood,Long> {

    Mood findFirstById(long id);

    List<Mood> findAllByActiveIsTrue();
}
