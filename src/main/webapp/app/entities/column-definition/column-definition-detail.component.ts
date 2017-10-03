import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ColumnDefinition } from './column-definition.model';
import { ColumnDefinitionService } from './column-definition.service';

@Component({
    selector: 'jhi-column-definition-detail',
    templateUrl: './column-definition-detail.component.html'
})
export class ColumnDefinitionDetailComponent implements OnInit, OnDestroy {

    columnDefinition: ColumnDefinition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private columnDefinitionService: ColumnDefinitionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInColumnDefinitions();
    }

    load(id) {
        this.columnDefinitionService.find(id).subscribe((columnDefinition) => {
            this.columnDefinition = columnDefinition;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInColumnDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'columnDefinitionListModification',
            (response) => this.load(this.columnDefinition.id)
        );
    }
}
