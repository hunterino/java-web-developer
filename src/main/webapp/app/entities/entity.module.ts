import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'feature',
        loadChildren: () => import('./feature/feature.module').then(m => m.FeatureKeeperFeatureModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class FeatureKeeperEntityModule {}
