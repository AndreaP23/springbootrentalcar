package com.si2001.webapp.entities;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "RUOLO")
@Data
public class Ruolo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name = "ruolo_id")
    private Long ruoloId;

    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
}
