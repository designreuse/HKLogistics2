/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { ProductVariantDetailComponent } from 'app/entities/product-variant/product-variant-detail.component';
import { ProductVariant } from 'app/shared/model/product-variant.model';

describe('Component Tests', () => {
    describe('ProductVariant Management Detail Component', () => {
        let comp: ProductVariantDetailComponent;
        let fixture: ComponentFixture<ProductVariantDetailComponent>;
        const route = ({ data: of({ productVariant: new ProductVariant(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ProductVariantDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductVariantDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductVariantDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productVariant).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
