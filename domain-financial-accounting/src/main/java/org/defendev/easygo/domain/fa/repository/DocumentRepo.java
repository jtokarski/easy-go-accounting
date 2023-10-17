package org.defendev.easygo.domain.fa.repository;

import org.defendev.easygo.domain.fa.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DocumentRepo extends JpaRepository<Document, Long> {

    Page<Document> findAll(Specification<Document> spec, Pageable pageable);

}
