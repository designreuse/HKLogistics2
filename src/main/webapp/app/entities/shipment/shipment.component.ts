import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from 'app/core';
import { ShipmentService } from './shipment.service';
import { Warehouse, Courier } from 'app/shared/model/shipment.model';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

@Component({
    selector: 'jhi-shipment',
    templateUrl: './shipment.component.html'
})
export class ShipmentComponent implements OnInit, OnDestroy {

    public formGroup1: FormGroup;
    public formGroup2: FormGroup;
    constructor(
        private shipmentService: ShipmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private formBuilder: FormBuilder
    ) {

    }


    ngOnInit() {

        this.formGroup1 = this.formBuilder.group({
            inputPinCode: new FormControl('', Validators.required),
            sourceWarehouse: new FormControl(null, Validators.required),
            inputWeight: '',
            inputAmount: '',
            getCost: ''
        });

        this.formGroup2 = this.formBuilder.group({
            soOrderId: new FormControl('', Validators.required),
            courierGroup: new FormControl(null, Validators.required),
            overrideCost: '',
            weightkg: '',
            sdateTime: null,
            edateTime: null
        });
    }
    ngOnDestroy() {

    }

    public couriers: Courier[] = [
        { displayName: 'courier1', id: 1 },
        { displayName: 'courier2', id: 2 },
        { displayName: 'courier3', id: 3 }
    ];

    public warehouses: Warehouse[] = [
        { displayName: 'warehouse1', id: 1 },
        { displayName: 'warehouse2', id: 2 },
        { displayName: 'warehouse3', id: 3 }
    ];

    validateForm(f: FormGroup) {
        return f.valid
    }

    public getShipmentCost() {
        if (this.validateForm(this.formGroup1)) {
            this.shipmentService.getShipmentCost(this.formGroup1);
        }

    }

    public calculateShipmentCost() {
        if (this.validateForm(this.formGroup2)) {
            this.shipmentService.calculateShipmentCost(this.formGroup2);
        }
    }

    public saveShipingCostByDate() {
        if (this.validateForm(this.formGroup2)) {
            this.shipmentService.saveShipingCostByDate(this.formGroup2);
        }
    }

    public saveShipingCostSingleSO() {
        if (this.validateForm(this.formGroup2)) {
            this.shipmentService.saveShipingCostSingleSO(this.formGroup2);
        }
    }

}
