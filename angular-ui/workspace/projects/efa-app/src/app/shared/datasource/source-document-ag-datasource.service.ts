import { Injectable } from '@angular/core';

import { IDatasource, IGetRowsParams, SortModelItem } from '@ag-grid-community/core';

import {
  BaseAgDatasource, SortOrder,
  Operator, NumberPropertyFilter, DateTimePropertyFilter, Filter,
  Query, QueryPageable, QuerySearchPhrase, QuerySort, QueryFilter,
  expandUriVariables, ICollectionResRep, IBaseDto
} from '@defendev/common-angular';


import { Observable, Subject } from 'rxjs';
import { HttpClient, HttpResponse } from "@angular/common/http";


const OBSERVE_RESPONSE_JSON: {
  observe: 'response';
  responseType: 'json';
} = {
  observe: 'response',
  responseType: 'json',
};

export interface SourceDocumentQuery extends Query, QueryPageable, QuerySearchPhrase, QuerySort, QueryFilter {};

export interface SourceDocumentMinDto extends IBaseDto {
  controlNumber: string;
  // ...

};


@Injectable({
  providedIn: 'root'
})
export class SourceDocumentAgDatasourceService extends BaseAgDatasource implements IDatasource {

  constructor(
    private httpClient: HttpClient,
  ) {
    super();
  }

  private destroyedSubject: Subject<void> = new Subject<void>();

  public rowCount?: number;

  public destroy(): void {
    this.destroyedSubject.next();
    this.destroyedSubject.complete();
  }

  public getRows(params: IGetRowsParams): void {
    const startRow = params.startRow;
    const endRow = params.endRow;
    if (false === this.validPaginationParams(startRow, endRow, params.failCallback)) {
      return;
    }
    const pageSize = endRow - startRow;
    const pageNumber = startRow / pageSize;

    const sortOrders: Array<SortOrder> = params.sortModel.map((sortModelItem: SortModelItem) => {
      return {
        direction: sortModelItem.sort,
        property: sortModelItem.colId,
      } as SortOrder;
    });

    const filter: Filter = {
      numberPropertyFilters: [],
      dateTimePropertyFilters: [],
      textPropertyFilters: [],
    };

    const url = '/api/source-document/_browse';
    const query = {
      pageNumber: pageNumber,
      pageSize: pageSize,
      sortOrders: sortOrders,
      filter: filter,
    } as SourceDocumentQuery;

    const rows$: Observable<HttpResponse<ICollectionResRep<SourceDocumentMinDto>>> = this.httpClient
      .post<ICollectionResRep<SourceDocumentMinDto>>(url, query, OBSERVE_RESPONSE_JSON);

    rows$.subscribe((response: HttpResponse<ICollectionResRep<SourceDocumentMinDto>>) => {
      if (null === response.body) {
        return;
      }
      const body: ICollectionResRep<SourceDocumentMinDto> | null = response.body;
      params.successCallback(body?.items, body?.meta.totalElements);
    });
  }


}
