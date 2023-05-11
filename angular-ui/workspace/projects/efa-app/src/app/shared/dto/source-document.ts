import { IBaseDto } from '@defendev/common-angular';

export interface SourceDocumentMinDto extends IBaseDto {
  controlNumber: string;
}

export interface SourceDocumentFullDto extends IBaseDto {
  controlNumber: string;
  description: string;
}

