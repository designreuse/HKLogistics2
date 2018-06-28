/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeComponent } from 'app/entities/pincode/pincode.component';
import { PincodeService } from 'app/entities/pincode/pincode.service';
import { Pincode } from 'app/shared/model/pincode.model';

describe('Component Tests', () => {
    describe('Pincode Management Component', () => {
        let comp: PincodeComponent;
        let fixture: ComponentFixture<PincodeComponent>;
        let service: PincodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeComponent],
                providers: []
            })
                .overrideTemplate(PincodeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PincodeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PincodeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Pincode(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pincodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
