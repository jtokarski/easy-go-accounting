import { NgModule, provideAppInitializer, EnvironmentProviders, FactoryProvider, inject } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClient, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

import { firstValueFrom } from 'rxjs';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AgGridModule } from '@ag-grid-community/angular';
import { ModuleRegistry } from '@ag-grid-community/core';
import { InfiniteRowModelModule } from '@ag-grid-community/infinite-row-model';

import { SecurityContextDiscoveryService } from '@defendev/common-angular';

import { SharedModule } from '@/shared/shared.module';

import { FinancialAccountingHomeComponent } from './pages/financial-accounting-home/financial-accounting-home.component';
import { DocumentMainComponent } from './pages/document-main/document-main.component';
import { DocumentHomeComponent } from './pages/document-main/document-home/document-home.component';
import { DocumentBrowseComponent } from './pages/document-main/document-browse/document-browse.component';
import { DocumentStatisticsComponent } from './pages/document-main/document-statistics/document-statistics.component';
import { DocumentDetailsComponent } from './pages/document-main/document-details/document-details.component';
import { DocumentRowActionsComponent } from './pages/document-main/document-browse/document-row-actions/document-row-actions.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';



const buildSecurityContextDiscoveryService =
  (httpClient: HttpClient) => new SecurityContextDiscoveryService('api/security-context', httpClient);

export function discoverSecurityContextFactory(service: SecurityContextDiscoveryService) {
  return () => firstValueFrom(service.discover());
}

ModuleRegistry.registerModules([
  InfiniteRowModelModule,
]);

@NgModule({
  declarations: [
    AppComponent,
    FinancialAccountingHomeComponent,
    DocumentMainComponent,
    DocumentHomeComponent,
    DocumentBrowseComponent,
    DocumentStatisticsComponent,
    DocumentDetailsComponent,
    DocumentRowActionsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NgbModule,
    AgGridModule,
    SharedModule,
  ],
  providers: [
    {
      provide: SecurityContextDiscoveryService,
      useFactory: buildSecurityContextDiscoveryService,
      deps: [HttpClient],
    } as FactoryProvider,
    provideAppInitializer( () => {
      const fn = discoverSecurityContextFactory(inject(SecurityContextDiscoveryService));
      return fn();
    }) as EnvironmentProviders,
    provideHttpClient(withInterceptorsFromDi()) as EnvironmentProviders,
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
