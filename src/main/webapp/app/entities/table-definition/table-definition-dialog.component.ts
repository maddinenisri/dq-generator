import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TableDefinition } from './table-definition.model';
import { TableDefinitionPopupService } from './table-definition-popup.service';
import { TableDefinitionService } from './table-definition.service';

@Component({
    selector: 'jhi-table-definition-dialog',
    templateUrl: './table-definition-dialog.component.html'
})
export class TableDefinitionDialogComponent implements OnInit {

    tableDefinition: TableDefinition;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tableDefinitionService: TableDefinitionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tableDefinition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tableDefinitionService.update(this.tableDefinition));
        } else {
            this.subscribeToSaveResponse(
                this.tableDefinitionService.create(this.tableDefinition));
        }
    }

    private subscribeToSaveResponse(result: Observable<TableDefinition>) {
        result.subscribe((res: TableDefinition) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TableDefinition) {
        this.eventManager.broadcast({ name: 'tableDefinitionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-table-definition-popup',
    template: ''
})
export class TableDefinitionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tableDefinitionPopupService: TableDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tableDefinitionPopupService
                    .open(TableDefinitionDialogComponent as Component, params['id']);
            } else {
                this.tableDefinitionPopupService
                    .open(TableDefinitionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
