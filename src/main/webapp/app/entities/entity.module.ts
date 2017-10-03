import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DqgeneratorTableDefinitionModule } from './table-definition/table-definition.module';
import { DqgeneratorColumnDefinitionModule } from './column-definition/column-definition.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        DqgeneratorTableDefinitionModule,
        DqgeneratorColumnDefinitionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DqgeneratorEntityModule {}
