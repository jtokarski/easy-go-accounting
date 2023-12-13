import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { ICellRendererAngularComp } from "@ag-grid-community/angular";
import { ICellRendererParams } from "@ag-grid-community/core";
import { DocumentMinDto } from '@/shared/dto/document';



@Component({
  selector: 'ea-document-row-actions',
  templateUrl: './document-row-actions.component.html',
  styleUrls: ['./document-row-actions.component.scss']
})
export class DocumentRowActionsComponent implements ICellRendererAngularComp {

  private documentMinDto?: DocumentMinDto;

  public constructor(
    private router: Router,
  ) { }

  public agInit(params: ICellRendererParams<DocumentMinDto>): void {
    this.documentMinDto = params.data;
  }

  public refresh(params: ICellRendererParams<any>): boolean {
    this.documentMinDto = params.data;
    return true;
  }

  public goToDocumentDetails() {
    this.router.navigate(['document/mgmt/details', this.documentMinDto?.externalId]);
  }
}
