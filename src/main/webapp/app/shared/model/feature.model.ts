import { Moment } from 'moment';
import { Client } from 'app/shared/model/enumerations/client.model';
import { ProductArea } from 'app/shared/model/enumerations/product-area.model';

export interface IFeature {
  id?: number;
  title?: string;
  description?: any;
  client?: Client;
  clientPriority?: number;
  targetDate?: Moment;
  productArea?: ProductArea;
}

export class Feature implements IFeature {
  constructor(
    public id?: number,
    public title?: string,
    public description?: any,
    public client?: Client,
    public clientPriority?: number,
    public targetDate?: Moment,
    public productArea?: ProductArea
  ) {}
}
