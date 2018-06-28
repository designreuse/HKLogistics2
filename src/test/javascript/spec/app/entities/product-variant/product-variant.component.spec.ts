/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { ProductVariantComponent } from 'app/entities/product-variant/product-variant.component';
import { ProductVariantService } from 'app/entities/product-variant/product-variant.service';
import { ProductVariant } from 'app/shared/model/product-variant.model';

describe('Component Tests', () => {
    describe('ProductVariant Management Component', () => {
        let comp: ProductVariantComponent;
        let fixture: ComponentFixture<ProductVariantComponent>;
        let service: ProductVariantService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ProductVariantComponent],
                providers: []
            })
                .overrideTemplate(ProductVariantComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductVariantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductVariantService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductVariant(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productVariants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
