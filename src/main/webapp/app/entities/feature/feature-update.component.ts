import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFeature, Feature } from 'app/shared/model/feature.model';
import { FeatureService } from './feature.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-feature-update',
  templateUrl: './feature-update.component.html',
})
export class FeatureUpdateComponent implements OnInit {
  isSaving = false;
  targetDateDp: any;

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    client: [],
    clientPriority: [],
    targetDate: [],
    productArea: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected featureService: FeatureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feature }) => {
      this.updateForm(feature);
    });
  }

  updateForm(feature: IFeature): void {
    this.editForm.patchValue({
      id: feature.id,
      title: feature.title,
      description: feature.description,
      client: feature.client,
      clientPriority: feature.clientPriority,
      targetDate: feature.targetDate,
      productArea: feature.productArea,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('featureKeeperApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feature = this.createFromForm();
    if (feature.id !== undefined) {
      this.subscribeToSaveResponse(this.featureService.update(feature));
    } else {
      this.subscribeToSaveResponse(this.featureService.create(feature));
    }
  }

  private createFromForm(): IFeature {
    return {
      ...new Feature(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      client: this.editForm.get(['client'])!.value,
      clientPriority: this.editForm.get(['clientPriority'])!.value,
      targetDate: this.editForm.get(['targetDate'])!.value,
      productArea: this.editForm.get(['productArea'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeature>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
