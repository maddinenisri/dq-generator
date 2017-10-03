import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ColumnDefinition } from './column-definition.model';
import { ColumnDefinitionPopupService } from './column-definition-popup.service';
import { ColumnDefinitionService } from './column-definition.service';

@Component({
    selector: 'jhi-column-definition-delete-dialog',
    templateUrl: './column-definition-delete-dialog.component.html'
})
export class ColumnDefinitionDeleteDialogComponent {

    columnDefinition: ColumnDefinition;

    constructor(
        private columnDefinitionService: ColumnDefinitionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.columnDefinitionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'columnDefinitionListModification',
                content: 'Deleted an columnDefinition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-column-definition-delete-popup',
    template: ''
})
export class ColumnDefinitionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private columnDefinitionPopupService: ColumnDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.columnDefinitionPopupService
                .open(ColumnDefinitionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
