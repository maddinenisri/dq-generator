import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { ColumnDefinition } from './column-definition.model';
import { ColumnDefinitionService } from './column-definition.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-column-definition',
    templateUrl: './column-definition.component.html'
})
export class ColumnDefinitionComponent implements OnInit, OnDestroy {
columnDefinitions: ColumnDefinition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private columnDefinitionService: ColumnDefinitionService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.columnDefinitionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.columnDefinitions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInColumnDefinitions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ColumnDefinition) {
        return item.id;
    }
    registerChangeInColumnDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe('columnDefinitionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
