import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ColumnDefinitionComponent } from './column-definition.component';
import { ColumnDefinitionDetailComponent } from './column-definition-detail.component';
import { ColumnDefinitionPopupComponent } from './column-definition-dialog.component';
import { ColumnDefinitionDeletePopupComponent } from './column-definition-delete-dialog.component';

export const columnDefinitionRoute: Routes = [
    {
        path: 'column-definition',
        component: ColumnDefinitionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ColumnDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'column-definition/:id',
        component: ColumnDefinitionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ColumnDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const columnDefinitionPopupRoute: Routes = [
    {
        path: 'column-definition-new',
        component: ColumnDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ColumnDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'column-definition/:id/edit',
        component: ColumnDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ColumnDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'column-definition/:id/delete',
        component: ColumnDefinitionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ColumnDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
