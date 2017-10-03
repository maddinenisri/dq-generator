import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DqgeneratorSharedModule } from '../../shared';
import {
    TableDefinitionService,
    TableDefinitionPopupService,
    TableDefinitionComponent,
    TableDefinitionDetailComponent,
    TableDefinitionDialogComponent,
    TableDefinitionPopupComponent,
    TableDefinitionDeletePopupComponent,
    TableDefinitionDeleteDialogComponent,
    tableDefinitionRoute,
    tableDefinitionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tableDefinitionRoute,
    ...tableDefinitionPopupRoute,
];

@NgModule({
    imports: [
        DqgeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TableDefinitionComponent,
        TableDefinitionDetailComponent,
        TableDefinitionDialogComponent,
        TableDefinitionDeleteDialogComponent,
        TableDefinitionPopupComponent,
        TableDefinitionDeletePopupComponent,
    ],
    entryComponents: [
        TableDefinitionComponent,
        TableDefinitionDialogComponent,
        TableDefinitionPopupComponent,
        TableDefinitionDeleteDialogComponent,
        TableDefinitionDeletePopupComponent,
    ],
    providers: [
        TableDefinitionService,
        TableDefinitionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DqgeneratorTableDefinitionModule {}
