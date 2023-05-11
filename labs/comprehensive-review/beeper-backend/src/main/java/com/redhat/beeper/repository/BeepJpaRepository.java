package com.redhat.beeper.repository;

import com.redhat.beeper.repository.model.Beep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeepJpaRepository extends JpaRepository<Beep, Long> {
}
