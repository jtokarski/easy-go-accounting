package org.defendev.easygo.domain.fa.api;

import org.defendev.common.domain.resrep.CollectionMeta;
import org.defendev.common.domain.resrep.ICollectionResRep;

import java.util.List;



public class SourceDocumentCollectionResRep implements ICollectionResRep<SourceDocumentMinDto> {

    private final CollectionMeta meta;

    private final List<SourceDocumentMinDto> items;

    public SourceDocumentCollectionResRep(CollectionMeta meta, List<SourceDocumentMinDto> items) {
        this.meta = meta;
        this.items = items;
    }

    @Override
    public CollectionMeta getMeta() {
        return meta;
    }

    @Override
    public List<SourceDocumentMinDto> getItems() {
        return items;
    }
}
