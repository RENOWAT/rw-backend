package com.tfm.backend.data.daos;

import com.tfm.backend.data.entities.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Integer > {


}
