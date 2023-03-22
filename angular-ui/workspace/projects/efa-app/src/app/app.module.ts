import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FinancialAccountingHomeComponent } from './pages/financial-accounting-home/financial-accounting-home.component';
import { SourceDocumentMainComponent } from './pages/source-document-main/source-document-main.component';
import { SourceDocumentHomeComponent } from './pages/source-document-main/source-document-home/source-document-home.component';
import { SourceDocumentBrowseComponent } from './pages/source-document-main/source-document-browse/source-document-browse.component';

@NgModule({
  declarations: [
    AppComponent,
    FinancialAccountingHomeComponent,
    SourceDocumentMainComponent,
    SourceDocumentHomeComponent,
    SourceDocumentBrowseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
