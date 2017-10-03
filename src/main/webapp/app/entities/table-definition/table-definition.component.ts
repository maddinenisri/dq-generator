import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { TableDefinition } from './table-definition.model';
import { TableDefinitionService } from './table-definition.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-table-definition',
    templateUrl: './table-definition.component.html'
})
export class TableDefinitionComponent implements OnInit, OnDestroy {
tableDefinitions: TableDefinition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tableDefinitionService: TableDefinitionService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tableDefinitionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.tableDefinitions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTableDefinitions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TableDefinition) {
        return item.id;
    }
    registerChangeInTableDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe('tableDefinitionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
