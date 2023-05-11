import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { ICellRendererAngularComp } from "@ag-grid-community/angular";
import { ICellRendererParams } from "@ag-grid-community/core";



import { SourceDocumentMinDto } from '@/shared/dto/source-document';




@Component({
  selector: 'ea-source-document-row-actions',
  templateUrl: './source-document-row-actions.component.html',
  styleUrls: ['./source-document-row-actions.component.scss']
})
export class SourceDocumentRowActionsComponent implements ICellRendererAngularComp {

  private sourceDocumentMinDto?: SourceDocumentMinDto;

  public constructor(private router: Router) { }

  public agInit(params: ICellRendererParams<SourceDocumentMinDto>): void {
    this.sourceDocumentMinDto = params.data;
  }

  public refresh(params: ICellRendererParams<any>): boolean {
    this.sourceDocumentMinDto = params.data;
    return true;
  }

  public goToSourceDocumentDetails() {
    this.router.navigate(['srcdoc/mgmt/details', this.sourceDocumentMinDto?.externalId]);
  }
}
