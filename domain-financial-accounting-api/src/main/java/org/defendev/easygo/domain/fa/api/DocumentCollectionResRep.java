package org.defendev.easygo.domain.fa.api;

import org.defendev.common.domain.resrep.CollectionMeta;
import org.defendev.common.domain.resrep.ICollectionResRep;

import java.util.List;



public class DocumentCollectionResRep implements ICollectionResRep<DocumentMinDto> {

    private final CollectionMeta meta;

    private final List<DocumentMinDto> items;

    public DocumentCollectionResRep(CollectionMeta meta, List<DocumentMinDto> items) {
        this.meta = meta;
        this.items = items;
    }

    @Override
    public CollectionMeta getMeta() {
        return meta;
    }

    @Override
    public List<DocumentMinDto> getItems() {
        return items;
    }
}
