import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DqgeneratorSharedModule } from '../../shared';
import {
    ColumnDefinitionService,
    ColumnDefinitionPopupService,
    ColumnDefinitionComponent,
    ColumnDefinitionDetailComponent,
    ColumnDefinitionDialogComponent,
    ColumnDefinitionPopupComponent,
    ColumnDefinitionDeletePopupComponent,
    ColumnDefinitionDeleteDialogComponent,
    columnDefinitionRoute,
    columnDefinitionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...columnDefinitionRoute,
    ...columnDefinitionPopupRoute,
];

@NgModule({
    imports: [
        DqgeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ColumnDefinitionComponent,
        ColumnDefinitionDetailComponent,
        ColumnDefinitionDialogComponent,
        ColumnDefinitionDeleteDialogComponent,
        ColumnDefinitionPopupComponent,
        ColumnDefinitionDeletePopupComponent,
    ],
    entryComponents: [
        ColumnDefinitionComponent,
        ColumnDefinitionDialogComponent,
        ColumnDefinitionPopupComponent,
        ColumnDefinitionDeleteDialogComponent,
        ColumnDefinitionDeletePopupComponent,
    ],
    providers: [
        ColumnDefinitionService,
        ColumnDefinitionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DqgeneratorColumnDefinitionModule {}
