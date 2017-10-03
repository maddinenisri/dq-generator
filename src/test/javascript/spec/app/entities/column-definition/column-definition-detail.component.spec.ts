/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DqgeneratorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ColumnDefinitionDetailComponent } from '../../../../../../main/webapp/app/entities/column-definition/column-definition-detail.component';
import { ColumnDefinitionService } from '../../../../../../main/webapp/app/entities/column-definition/column-definition.service';
import { ColumnDefinition } from '../../../../../../main/webapp/app/entities/column-definition/column-definition.model';

describe('Component Tests', () => {

    describe('ColumnDefinition Management Detail Component', () => {
        let comp: ColumnDefinitionDetailComponent;
        let fixture: ComponentFixture<ColumnDefinitionDetailComponent>;
        let service: ColumnDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DqgeneratorTestModule],
                declarations: [ColumnDefinitionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ColumnDefinitionService,
                    JhiEventManager
                ]
            }).overrideTemplate(ColumnDefinitionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ColumnDefinitionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ColumnDefinitionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ColumnDefinition(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.columnDefinition).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
