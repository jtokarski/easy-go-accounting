import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient, HttpResponse } from "@angular/common/http";
import { Observable, map, Subject, takeUntil } from "rxjs";
import { OBSERVE_RESPONSE_JSON } from "@/shared/observe-response-json";
import { SourceDocumentFullDto } from "@/shared/dto/source-document";
import { LastVisitedDto } from '@/shared/dto/last-visited';



@Component({
  selector: 'ea-financial-accounting-home',
  templateUrl: './financial-accounting-home.component.html',
  styleUrls: ['./financial-accounting-home.component.scss']
})
export class FinancialAccountingHomeComponent implements OnInit, OnDestroy {

  private destroyedSubject: Subject<void> = new Subject<void>();

  public dto$!: Observable<LastVisitedDto | null>;

  public constructor(private httpClient: HttpClient) { }

  public ngOnInit(): void {
    const url = `api/last-visited`;
    this.dto$ = this.httpClient.get<LastVisitedDto>(url, OBSERVE_RESPONSE_JSON)
      .pipe(
        takeUntil(this.destroyedSubject),
        map((response: HttpResponse<LastVisitedDto>) => response.body)
      );
  }

  public ngOnDestroy(): void {
    this.destroyedSubject.next();
    this.destroyedSubject.complete();
  }

}
