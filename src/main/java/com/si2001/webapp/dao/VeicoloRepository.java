package com.si2001.webapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.si2001.webapp.entities.Veicolo;

public interface VeicoloRepository extends JpaRepository<Veicolo,Long> {

	Veicolo findByVeicoloId(Long veicoloId);
	

}
