import { Injectable, InjectionToken } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SecurityContextDto } from '@defendev/common-angular';
import * as _ from 'lodash';
import { Observable, timer, map, firstValueFrom, tap } from 'rxjs';
import { OBSERVE_RESPONSE_JSON } from '@/shared/observe-response-json';



export function discoverSecurityContextFactory(service: SecurityContextDiscoveryService) {
  return () => firstValueFrom(service.discover());
}


@Injectable({
  providedIn: 'root'
})
export class SecurityContextDiscoveryService {

  private securityContext!: SecurityContextDto;

  public get context(): SecurityContextDto {
    return _.cloneDeep(this.securityContext);
  }

  constructor(private httpClient: HttpClient) { }

  public discover(): Observable<SecurityContextDto> {
    const url = '/api/security-context';
    return this.httpClient.get<SecurityContextDto>(url, OBSERVE_RESPONSE_JSON).pipe(
      map((response: HttpResponse<SecurityContextDto>) => {
          if (null == response.body) {
            /*
             * todo: handle properly...
             */
            return { authentication: { principal: { username: 'failedFailed' } } } as SecurityContextDto;
          }
          return response.body;
      }),
      tap((dto) => this.securityContext = dto),
    );
  }

}
