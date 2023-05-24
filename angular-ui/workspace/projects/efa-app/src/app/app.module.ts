import { NgModule, APP_INITIALIZER } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AgGridModule } from '@ag-grid-community/angular';
import { ModuleRegistry } from '@ag-grid-community/core';
import { InfiniteRowModelModule } from '@ag-grid-community/infinite-row-model';

import { SharedModule } from '@/shared/shared.module';

import { SecurityContextDiscoveryService, discoverSecurityContextFactory } from '@/security/security-context-discovery.service';

import { FinancialAccountingHomeComponent } from './pages/financial-accounting-home/financial-accounting-home.component';
import { SourceDocumentMainComponent } from './pages/source-document-main/source-document-main.component';
import { SourceDocumentHomeComponent } from './pages/source-document-main/source-document-home/source-document-home.component';
import { SourceDocumentBrowseComponent } from './pages/source-document-main/source-document-browse/source-document-browse.component';
import { SourceDocumentStatisticsComponent } from './pages/source-document-main/source-document-statistics/source-document-statistics.component';
import { SourceDocumentDetailsComponent } from './pages/source-document-main/source-document-details/source-document-details.component';
import { SourceDocumentRowActionsComponent } from './pages/source-document-main/source-document-browse/source-document-row-actions/source-document-row-actions.component';



ModuleRegistry.registerModules([
  InfiniteRowModelModule,
]);

@NgModule({
  declarations: [
    AppComponent,
    FinancialAccountingHomeComponent,
    SourceDocumentMainComponent,
    SourceDocumentHomeComponent,
    SourceDocumentBrowseComponent,
    SourceDocumentStatisticsComponent,
    SourceDocumentDetailsComponent,
    SourceDocumentRowActionsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    AgGridModule,
    SharedModule,
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: discoverSecurityContextFactory,
      deps: [SecurityContextDiscoveryService],
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
