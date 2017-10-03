import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ColumnDefinition } from './column-definition.model';
import { ColumnDefinitionPopupService } from './column-definition-popup.service';
import { ColumnDefinitionService } from './column-definition.service';
import { TableDefinition, TableDefinitionService } from '../table-definition';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-column-definition-dialog',
    templateUrl: './column-definition-dialog.component.html'
})
export class ColumnDefinitionDialogComponent implements OnInit {

    columnDefinition: ColumnDefinition;
    isSaving: boolean;

    tabledefinitions: TableDefinition[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private columnDefinitionService: ColumnDefinitionService,
        private tableDefinitionService: TableDefinitionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tableDefinitionService.query()
            .subscribe((res: ResponseWrapper) => { this.tabledefinitions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.columnDefinition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.columnDefinitionService.update(this.columnDefinition));
        } else {
            this.subscribeToSaveResponse(
                this.columnDefinitionService.create(this.columnDefinition));
        }
    }

    private subscribeToSaveResponse(result: Observable<ColumnDefinition>) {
        result.subscribe((res: ColumnDefinition) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ColumnDefinition) {
        this.eventManager.broadcast({ name: 'columnDefinitionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackTableDefinitionById(index: number, item: TableDefinition) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-column-definition-popup',
    template: ''
})
export class ColumnDefinitionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private columnDefinitionPopupService: ColumnDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.columnDefinitionPopupService
                    .open(ColumnDefinitionDialogComponent as Component, params['id']);
            } else {
                this.columnDefinitionPopupService
                    .open(ColumnDefinitionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
