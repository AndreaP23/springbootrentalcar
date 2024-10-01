package com.si2001.webapp.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

	private static final long serialVersionUID = 149790289743555628L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)     @Column(name = "user_id")
	private Long userId;
	@Column(name="nome")
	private String nome;
	@Column(name="cognome")
	private String cognome;

	@Column(name="email", unique = true)  
	private String email;

	@Column(name = "phone")  
	private String telefono;
	@Column(name="password")
	private String password;

	@Column(name = "data_nascita")  
	private LocalDate dataNascita;

	@Column(name = "ruolo_id")  
	private Integer ruolo;
	
	@OneToMany(mappedBy= "user", fetch = FetchType.LAZY)
	private List<Prenotazione> prenotazioni;
}
