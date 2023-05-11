import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, AbstractControl, FormControl, FormGroup, ValidatorFn, ValidationErrors } from "@angular/forms";
import { Subject, takeUntil, map, mergeMap, of, timer, forkJoin, startWith, distinctUntilChanged,
  filter } from "rxjs";
import * as _ from 'lodash';
import { SourceDocumentAgDatasourceService } from "@/shared/datasource/source-document-ag-datasource.service";
import { GetRowIdParams, GridApi, GridOptions, GridReadyEvent, IDatasource, PaginationChangedEvent }
  from "@ag-grid-community/core";
import { toStringNullSafe } from "@defendev/common-angular";
import { GlobalLoadingService } from "@/shared/global-loading/global-loading.service";
import { SourceDocumentMinDto } from '@/shared/dto/source-document';
import { SourceDocumentRowActionsComponent } from
  '@/pages/source-document-main/source-document-browse/source-document-row-actions/source-document-row-actions.component';




const PAGE_SIZE_FG_KEY = 'pageSize';
const CURRENT_PAGE_FG_KEY = 'currentPage';

interface ExternalPaginationFg {
  [PAGE_SIZE_FG_KEY]: any;
  [CURRENT_PAGE_FG_KEY]: any;
}

const INITIAL_PAGE_SIZE = 10;


@Component({
  selector: 'ea-source-document-browse',
  templateUrl: './source-document-browse.component.html',
  styleUrls: ['./source-document-browse.component.scss'],
  providers: [
    {
      provide: SourceDocumentAgDatasourceService,
      useClass: SourceDocumentAgDatasourceService,
    }
  ],
})
export class SourceDocumentBrowseComponent implements OnInit, OnDestroy {

  private destroyedSubject: Subject<void> = new Subject<void>();


  public gridOptions :GridOptions<SourceDocumentMinDto> = {};

  private gridApi!: GridApi<SourceDocumentMinDto>;

  public gridDisplayReloading: boolean = false;

  public get gridDisplayEverInitialized(): boolean {
    return !!(this.gridOptions.columnDefs);
  };

  public get gridDisplayActive(): boolean {
    return (this.gridDisplayEverInitialized && !this.gridDisplayReloading);
  }

  public externalPaginationFg: FormGroup;

  public get currentPageFc() {
    return this.externalPaginationFg.controls[CURRENT_PAGE_FG_KEY] as FormControl;
  }

  public get pageSizeFc() {
    return this.externalPaginationFg.controls[PAGE_SIZE_FG_KEY] as FormControl;
  }

  public paginationGoToPage() {
    const toGoPage = parseInt(this.currentPageFc.value);
    this.gridApi.paginationGoToPage(toGoPage - 1);
  }

  public paginationSetPageSize(newSize: number) {
    this.gridApi.paginationSetPageSize(newSize);
  }

  public paginationPageOnFocus(event: FocusEvent) {
    const targetInput = event.target as HTMLInputElement;
    targetInput.select();
  }

  private totalPages: number = 0;

  public constructor(
    private fb: FormBuilder,
    private globalLoading: GlobalLoadingService,
    private agDatasource: SourceDocumentAgDatasourceService,
  ) {
    this.externalPaginationFg = this.fb.group({
      pageSize: this.fb.control(INITIAL_PAGE_SIZE),
      currentPage: this.fb.control(null, [this.validateToGoPage]),
    } as ExternalPaginationFg);
  }



  public ngOnDestroy(): void {
    this.destroyedSubject.next();
    this.destroyedSubject.complete();
  }

  public ngOnInit(): void {
    this.pageSizeGridReInitSubscription();
  }

  private validateToGoPage: (() => ValidatorFn) = () => (control: AbstractControl) => {
    const toGoPage = parseInt(control.value, 10);
    if (! Number.isInteger(toGoPage)) {
      return {
        'nonIntegerValue': {
          value: control.value,
        },
      } as ValidationErrors;
    }
    if (!_.inRange(toGoPage, 1, this.totalPages + 1)) {
      return {
        'outOfBound': {
          value: control.value,
        },
      } as ValidationErrors;
    }
    return {} as ValidationErrors;
  }

  private onGridReady = (event: GridReadyEvent<SourceDocumentMinDto>) => {
    this.gridApi = event.api;
  };

  private onPaginationChanged = (event: PaginationChangedEvent<SourceDocumentMinDto>) => {
    const gridApi: GridApi<SourceDocumentMinDto> = event.api;
    const paginationPageSize: number = gridApi.paginationGetPageSize();
    const paginationCurrentPage: number = gridApi.paginationGetCurrentPage() + 1;
    this.totalPages = gridApi.paginationGetTotalPages();
    this.externalPaginationFg.setValue({
      pageSize: paginationPageSize,
      currentPage: paginationCurrentPage,
    } as ExternalPaginationFg);
  };

  private buildGridOptions(
    agDatasource: IDatasource,
    pageSize: number,
    onGridReady: (event: GridReadyEvent<SourceDocumentMinDto>) => void,
    onPaginationChanged: (event: PaginationChangedEvent<SourceDocumentMinDto>) => void,
  ): GridOptions {
    return {
      rowModelType: 'infinite',
      datasource: agDatasource,
      defaultColDef: {
        wrapHeaderText: true,
        autoHeaderHeight: true,
      },
      columnDefs: [
        {
          colId: 'externalId',
          headerName: 'Id',
          valueGetter: (params) => params.data?.externalId,
          flex: 1,
        },
        {
          colId:'controlNumber',
          headerName: 'Control Number',
          valueGetter: (params) => params.data?.controlNumber,
          sortable: true,
          flex: 10,
        },
        {
          colId: 'rowActions',
          headerName: '',
          sortable: false,
          cellRenderer: SourceDocumentRowActionsComponent,
          flex: 1,
        }
      ],
      getRowId: (params: GetRowIdParams<SourceDocumentMinDto>) => (toStringNullSafe(params.data.externalId)),
      pagination: true,
      paginationPageSize: pageSize,
      cacheBlockSize: pageSize,
      onGridReady: onGridReady,
      onPaginationChanged: onPaginationChanged,
    } as GridOptions<SourceDocumentMinDto>;
  }

  private pageSizeGridReInitSubscription() {
    this.pageSizeFc.valueChanges.pipe(
      startWith(this.pageSizeFc.value as number),
      takeUntil(this.destroyedSubject.asObservable()),
      filter(() => !(this.gridDisplayEverInitialized && this.gridDisplayReloading)),
      distinctUntilChanged(),
      mergeMap(
        (pageSize: number) => {
          const taskId = this.globalLoading.tracker.sourceDocumentMain.sourceDocumentBrowse._.registerTask();
          this.gridDisplayReloading = true;
          return forkJoin([
            of(pageSize),
            of(taskId),
            timer(0),
          ]);
        }
      ),
      mergeMap(
        ([pageSize, taskId, _]: [number, number, any]) => {
          this.gridOptions = this.buildGridOptions(this.agDatasource, pageSize, this.onGridReady,
            this.onPaginationChanged);
          this.gridDisplayReloading = false;
          return forkJoin([
            of(taskId),
            timer(0),
          ]);
        }
      ),
      map(
        ([taskId, _]: [number, any]) => {
          this.globalLoading.tracker.sourceDocumentMain.sourceDocumentBrowse._.clearTask(taskId);
          return;
        }
      ),
    ).subscribe();
  }

}
