import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FeatureKeeperSharedModule } from 'app/shared/shared.module';
import { FeatureComponent } from './feature.component';
import { FeatureDetailComponent } from './feature-detail.component';
import { FeatureUpdateComponent } from './feature-update.component';
import { FeatureDeleteDialogComponent } from './feature-delete-dialog.component';
import { featureRoute } from './feature.route';

@NgModule({
  imports: [FeatureKeeperSharedModule, RouterModule.forChild(featureRoute)],
  declarations: [FeatureComponent, FeatureDetailComponent, FeatureUpdateComponent, FeatureDeleteDialogComponent],
  entryComponents: [FeatureDeleteDialogComponent],
})
export class FeatureKeeperFeatureModule {}
