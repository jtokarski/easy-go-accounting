import { Injectable, InjectionToken } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SecurityContextDto } from '@defendev/common-angular';
import * as _ from 'lodash';
import { Observable, timer, map, firstValueFrom, tap } from 'rxjs';



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
    return timer(3000).pipe(
      map((_) => ({ authentication: { principal: { username: 'anguM0ck' } } } as SecurityContextDto)),
      tap((dto) => this.securityContext = dto),
    );
  }

}
