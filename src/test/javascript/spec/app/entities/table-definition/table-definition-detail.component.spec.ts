/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DqgeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TableDefinitionDetailComponent } from '../../../../../../main/webapp/app/entities/table-definition/table-definition-detail.component';
import { TableDefinitionService } from '../../../../../../main/webapp/app/entities/table-definition/table-definition.service';
import { TableDefinition } from '../../../../../../main/webapp/app/entities/table-definition/table-definition.model';

describe('Component Tests', () => {

    describe('TableDefinition Management Detail Component', () => {
        let comp: TableDefinitionDetailComponent;
        let fixture: ComponentFixture<TableDefinitionDetailComponent>;
        let service: TableDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DqgeneratorTestModule],
                declarations: [TableDefinitionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TableDefinitionService,
                    JhiEventManager
                ]
            }).overrideTemplate(TableDefinitionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TableDefinitionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TableDefinitionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TableDefinition(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tableDefinition).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
