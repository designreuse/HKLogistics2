import { Component, OnInit, OnDestroy } from '@angular/core';
import { CourierWHMapping } from './courier-wh-mapping.model';
import { CourierWHMappingService } from './courier-wh-mapping.service';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

@Component({
    selector: 'jhi-courier-wh-mapping.component',
    templateUrl: './courier-wh-mapping.component.html'
})
export class CourierWHMappingComponent implements OnInit, OnDestroy {

    public formGroup: FormGroup;
    public courierWHMapping: CourierWHMapping;
    constructor(
        private courierWHMappingService: CourierWHMappingService,
        private formBuilder: FormBuilder
    ) {

    }


    ngOnInit() {
        this.formGroup = this.formBuilder.group({
            warehouse: new FormControl('', Validators.required),
            courierChannel: new FormControl(null, Validators.required),
        });
    }
    ngOnDestroy() {

    }

    validateForm(f: FormGroup) {
        return f.valid
    }

    public createNew() {
        let courierWHMapping = new CourierWHMapping();
        courierWHMapping = this.formGroup.value;
        if (this.validateForm(this.formGroup)) {
            this.courierWHMappingService.createNew(courierWHMapping);
        }
    }

}
