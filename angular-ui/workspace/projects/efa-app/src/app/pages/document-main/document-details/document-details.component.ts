import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { HttpClient, HttpResponse } from "@angular/common/http";
import { BehaviorSubject, Observable, Subject, map, mergeMap, take, takeUntil } from 'rxjs';
import { OBSERVE_RESPONSE_JSON } from '@/shared/observe-response-json';
import { DocumentFullDto } from "@/shared/dto/document";



@Component({
  selector: 'ea-document-details',
  templateUrl: './document-details.component.html',
  styleUrls: ['./document-details.component.scss']
})
export class DocumentDetailsComponent implements OnInit, OnDestroy {

  private destroyedSubject: Subject<void> = new Subject<void>();

  public externalId: string | null = null;

  public dto?: DocumentFullDto;

  public constructor(
    private route: ActivatedRoute,
    private httpClient: HttpClient,
  ) { }

  public ngOnInit(): void {
    this.route.paramMap.pipe(
      takeUntil(this.destroyedSubject),
      take(1),
      map( (params: ParamMap) => params.get('externalId') as string ),
      mergeMap( (externalId: string) => {
        this.externalId = externalId;
        const url = `api/document/${externalId}`;
        const dto$: Observable<HttpResponse<DocumentFullDto>> = this.httpClient.get<DocumentFullDto>(url,
          OBSERVE_RESPONSE_JSON);
        return dto$;
      }),
    ).subscribe({
      next: (response: HttpResponse<DocumentFullDto>) => {
        if (response.body) {
          this.dto = response.body;
        }
      }
    });
  }

  public ngOnDestroy(): void {
    this.destroyedSubject.next();
    this.destroyedSubject.complete();
  }

}
