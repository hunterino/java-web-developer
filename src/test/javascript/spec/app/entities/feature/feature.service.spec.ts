import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FeatureService } from 'app/entities/feature/feature.service';
import { IFeature, Feature } from 'app/shared/model/feature.model';
import { Client } from 'app/shared/model/enumerations/client.model';
import { ProductArea } from 'app/shared/model/enumerations/product-area.model';

describe('Service Tests', () => {
  describe('Feature Service', () => {
    let injector: TestBed;
    let service: FeatureService;
    let httpMock: HttpTestingController;
    let elemDefault: IFeature;
    let expectedResult: IFeature | IFeature[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FeatureService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Feature(0, 'AAAAAAA', 'AAAAAAA', Client.Kitty, 0, currentDate, ProductArea.Policies);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            targetDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Feature', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            targetDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            targetDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Feature()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Feature', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            client: 'BBBBBB',
            clientPriority: 1,
            targetDate: currentDate.format(DATE_FORMAT),
            productArea: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            targetDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Feature', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            client: 'BBBBBB',
            clientPriority: 1,
            targetDate: currentDate.format(DATE_FORMAT),
            productArea: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            targetDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Feature', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
