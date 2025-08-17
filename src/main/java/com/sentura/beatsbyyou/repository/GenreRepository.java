package com.sentura.beatsbyyou.repository;

import com.sentura.beatsbyyou.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre,Long> {

    Genre findFirstById(long id);

    List<Genre> findAllByActiveIsTrue();
}
