import { NgModule } from '@angular/core';
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

import { FinancialAccountingHomeComponent } from './pages/financial-accounting-home/financial-accounting-home.component';
import { SourceDocumentMainComponent } from './pages/source-document-main/source-document-main.component';
import { SourceDocumentHomeComponent } from './pages/source-document-main/source-document-home/source-document-home.component';
import { SourceDocumentBrowseComponent } from './pages/source-document-main/source-document-browse/source-document-browse.component';
import { SourceDocumentStatisticsComponent } from './pages/source-document-main/source-document-statistics/source-document-statistics.component';



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
    SourceDocumentStatisticsComponent
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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
