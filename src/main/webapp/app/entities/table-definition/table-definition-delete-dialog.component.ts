import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TableDefinition } from './table-definition.model';
import { TableDefinitionPopupService } from './table-definition-popup.service';
import { TableDefinitionService } from './table-definition.service';

@Component({
    selector: 'jhi-table-definition-delete-dialog',
    templateUrl: './table-definition-delete-dialog.component.html'
})
export class TableDefinitionDeleteDialogComponent {

    tableDefinition: TableDefinition;

    constructor(
        private tableDefinitionService: TableDefinitionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tableDefinitionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tableDefinitionListModification',
                content: 'Deleted an tableDefinition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-table-definition-delete-popup',
    template: ''
})
export class TableDefinitionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tableDefinitionPopupService: TableDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tableDefinitionPopupService
                .open(TableDefinitionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
