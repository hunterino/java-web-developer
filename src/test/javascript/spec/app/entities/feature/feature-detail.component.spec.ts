import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { FeatureKeeperTestModule } from '../../../test.module';
import { FeatureDetailComponent } from 'app/entities/feature/feature-detail.component';
import { Feature } from 'app/shared/model/feature.model';

describe('Component Tests', () => {
  describe('Feature Management Detail Component', () => {
    let comp: FeatureDetailComponent;
    let fixture: ComponentFixture<FeatureDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ feature: new Feature(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FeatureKeeperTestModule],
        declarations: [FeatureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FeatureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeatureDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load feature on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feature).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
