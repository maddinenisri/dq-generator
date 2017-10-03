import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TableDefinition } from './table-definition.model';
import { TableDefinitionService } from './table-definition.service';

@Component({
    selector: 'jhi-table-definition-detail',
    templateUrl: './table-definition-detail.component.html'
})
export class TableDefinitionDetailComponent implements OnInit, OnDestroy {

    tableDefinition: TableDefinition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tableDefinitionService: TableDefinitionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTableDefinitions();
    }

    load(id) {
        this.tableDefinitionService.find(id).subscribe((tableDefinition) => {
            this.tableDefinition = tableDefinition;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTableDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tableDefinitionListModification',
            (response) => this.load(this.tableDefinition.id)
        );
    }
}
