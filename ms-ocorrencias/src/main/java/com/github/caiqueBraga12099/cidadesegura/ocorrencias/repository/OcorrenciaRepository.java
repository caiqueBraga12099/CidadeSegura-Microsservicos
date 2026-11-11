package com.github.caiqueBraga12099.cidadesegura.ocorrencias.repository;

import com.github.caiqueBraga12099.cidadesegura.ocorrencias.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
}
