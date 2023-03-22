import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FinancialAccountingHomeComponent } from '@/pages/financial-accounting-home/financial-accounting-home.component';
import { SourceDocumentMainComponent } from '@/pages/source-document-main/source-document-main.component';
import { SourceDocumentHomeComponent } from '@/pages/source-document-main/source-document-home/source-document-home.component';
import { SourceDocumentBrowseComponent } from '@/pages/source-document-main/source-document-browse/source-document-browse.component';



const routes: Routes = [
  {
    path: '',
    component: FinancialAccountingHomeComponent,
  },
  {
    path: 'srcdoc',
    component: SourceDocumentMainComponent,
    children: [
      {
        path: '',
        component: SourceDocumentHomeComponent,
      },
      {
        path: 'mgmt/browse',
        component: SourceDocumentBrowseComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
