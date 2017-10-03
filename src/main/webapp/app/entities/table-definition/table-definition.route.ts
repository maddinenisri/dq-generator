import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TableDefinitionComponent } from './table-definition.component';
import { TableDefinitionDetailComponent } from './table-definition-detail.component';
import { TableDefinitionPopupComponent } from './table-definition-dialog.component';
import { TableDefinitionDeletePopupComponent } from './table-definition-delete-dialog.component';

export const tableDefinitionRoute: Routes = [
    {
        path: 'table-definition',
        component: TableDefinitionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'table-definition/:id',
        component: TableDefinitionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tableDefinitionPopupRoute: Routes = [
    {
        path: 'table-definition-new',
        component: TableDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'table-definition/:id/edit',
        component: TableDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'table-definition/:id/delete',
        component: TableDefinitionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TableDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
