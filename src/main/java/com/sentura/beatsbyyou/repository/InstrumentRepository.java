package com.sentura.beatsbyyou.repository;

import com.sentura.beatsbyyou.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentRepository extends JpaRepository<Instrument,Long> {

    Instrument findFirstById(long id);

    List<Instrument> findAllByActiveIsTrue();
}
