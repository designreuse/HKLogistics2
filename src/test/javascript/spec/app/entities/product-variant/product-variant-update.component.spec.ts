/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { ProductVariantUpdateComponent } from 'app/entities/product-variant/product-variant-update.component';
import { ProductVariantService } from 'app/entities/product-variant/product-variant.service';
import { ProductVariant } from 'app/shared/model/product-variant.model';

describe('Component Tests', () => {
    describe('ProductVariant Management Update Component', () => {
        let comp: ProductVariantUpdateComponent;
        let fixture: ComponentFixture<ProductVariantUpdateComponent>;
        let service: ProductVariantService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ProductVariantUpdateComponent]
            })
                .overrideTemplate(ProductVariantUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductVariantUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductVariantService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductVariant(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productVariant = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductVariant();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productVariant = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
