import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from "@angular/common/http";
import { Observable, Subject } from 'rxjs';
import { IDatasource, IGetRowsParams, SortModelItem } from '@ag-grid-community/core';
import { BaseAgDatasource, SortOrder, Filter, Query, QueryPageable, QuerySearchPhrase, QuerySort, QueryFilter,
  QueryOwnedBy, ICollectionResRep } from '@defendev/common-angular';
import { OBSERVE_RESPONSE_JSON } from '@/shared/observe-response-json';
import { SourceDocumentMinDto } from '@/shared/dto/source-document';
import { SecurityContextDiscoveryService, X_CSRF_TOKEN } from '@/security/security-context-discovery.service';

export interface SourceDocumentQuery extends Query, QueryPageable, QuerySearchPhrase, QuerySort, QueryFilter,
  QueryOwnedBy {}




@Injectable({
  providedIn: 'root'
})
export class SourceDocumentAgDatasourceService extends BaseAgDatasource implements IDatasource {

  constructor(
    private httpClient: HttpClient,
    private securityService: SecurityContextDiscoveryService,
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

    const url = 'api/source-document/_browse';
    const query = {
      pageNumber: pageNumber,
      pageSize: pageSize,
      sortOrders: sortOrders,
      filter: filter,
      resolveOwnershipUnitsForRequestingUser: true,
    } as SourceDocumentQuery;

    const rows$: Observable<HttpResponse<ICollectionResRep<SourceDocumentMinDto>>> = this.httpClient
      .post<ICollectionResRep<SourceDocumentMinDto>>(url, query, {
        headers: new HttpHeaders({ [X_CSRF_TOKEN]: this.securityService.csrfToken }),
        ...OBSERVE_RESPONSE_JSON,
      });

    rows$.subscribe((response: HttpResponse<ICollectionResRep<SourceDocumentMinDto>>) => {
      if (null === response.body) {
        return;
      }
      const body: ICollectionResRep<SourceDocumentMinDto> | null = response.body;
      params.successCallback(body?.items, body?.meta.totalElements);
    });
  }


}
