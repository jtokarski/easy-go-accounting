import { Injectable, InjectionToken } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SecurityContextDto } from '@defendev/common-angular';
import * as _ from 'lodash';
import { Observable, timer, map, firstValueFrom, tap } from 'rxjs';
import { OBSERVE_RESPONSE_JSON } from '@/shared/observe-response-json';


export const X_CSRF_TOKEN = 'X-CSRF-TOKEN';

export function discoverSecurityContextFactory(service: SecurityContextDiscoveryService) {
  return () => firstValueFrom(service.discover());
}


@Injectable({
  providedIn: 'root'
})
export class SecurityContextDiscoveryService {

  private _securityContext!: SecurityContextDto;

  private _csrfToken!: string;

  public get context(): SecurityContextDto {
    return _.cloneDeep(this._securityContext);
  }

  public get isAuthenticated(): boolean {
    return this._securityContext.authentication.authenticated;
  }

  public get csrfToken(): string {
    return this._csrfToken;
  }

  constructor(private httpClient: HttpClient) { }

  public discover(): Observable<void> {
    const url = 'api/security-context';
    return this.httpClient.get<SecurityContextDto>(url, OBSERVE_RESPONSE_JSON).pipe(
      tap((response: HttpResponse<SecurityContextDto>) => {
          if (null == response.body) {
            /*
             * todo: handle properly...
             */
            return { authentication: { principal: { username: 'failedFailed' } } } as SecurityContextDto;
          }
        this._csrfToken = response.headers.get(X_CSRF_TOKEN) || '';
        this._securityContext = response.body;
        return response;
      }),
      map(_ => {}),
    );
  }

}
