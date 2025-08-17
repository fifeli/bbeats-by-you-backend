package com.sentura.beatsbyyou.repository;

import com.sentura.beatsbyyou.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate,Long> {

    EmailTemplate findFirstByEmailTypeAndActiveTrue(@Param("type")String type);
}
