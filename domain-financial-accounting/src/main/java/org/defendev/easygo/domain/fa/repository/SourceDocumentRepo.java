package org.defendev.easygo.domain.fa.repository;

import org.defendev.easygo.domain.fa.model.SourceDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SourceDocumentRepo extends JpaRepository<SourceDocument, Long> {

    Page<SourceDocument> findAll(Specification<SourceDocument> spec, Pageable pageable);

}
