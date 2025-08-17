package com.sentura.beatsbyyou.repository;

import com.sentura.beatsbyyou.entity.Tempo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempoRepository extends JpaRepository<Tempo,Long> {

    Tempo findFirstById(long id);

    List<Tempo> findAllByTempoValue(int value);

    Page<Tempo> findAllByTempoValue(int value,Pageable pageable);

    Page<Tempo> findAll(Pageable pageable);
}
