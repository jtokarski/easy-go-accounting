import { IBaseDto } from '@defendev/common-angular';

export interface DocumentMinDto extends IBaseDto {
  controlNumber: string;
}

export interface DocumentFullDto extends IBaseDto {
  controlNumber: string;
  description: string;
}

