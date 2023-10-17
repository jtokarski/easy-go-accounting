import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FinancialAccountingHomeComponent } from '@/pages/financial-accounting-home/financial-accounting-home.component';
import { DocumentMainComponent } from '@/pages/document-main/document-main.component';
import { DocumentHomeComponent } from '@/pages/document-main/document-home/document-home.component';
import { DocumentBrowseComponent } from '@/pages/document-main/document-browse/document-browse.component';
import { DocumentDetailsComponent } from '@/pages/document-main/document-details/document-details.component';
import { DocumentStatisticsComponent } from '@/pages/document-main/document-statistics/document-statistics.component';





const routes: Routes = [
  {
    path: '',
    component: FinancialAccountingHomeComponent,
  },
  {
    path: 'document',
    component: DocumentMainComponent,
    children: [
      {
        path: '',
        component: DocumentHomeComponent,
      },
      {
        path: 'mgmt/browse',
        component: DocumentBrowseComponent,
      },
      {
        path: 'mgmt/details/:externalId',
        component: DocumentDetailsComponent,
      },
      {
        path: 'stats',
        component: DocumentStatisticsComponent,
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
