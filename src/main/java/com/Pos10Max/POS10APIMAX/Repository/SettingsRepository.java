package com.Pos10Max.POS10APIMAX.Repository;

import com.Pos10Max.POS10APIMAX.Entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity,Integer> {





}
