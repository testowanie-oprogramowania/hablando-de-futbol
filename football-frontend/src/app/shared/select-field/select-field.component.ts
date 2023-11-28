import {Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {Observable, of} from "rxjs";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";

@Component({
    selector: 'app-select-field',
    standalone: true,
    imports: [
        CommonModule,
        MatFormFieldModule,
        MatOptionModule,
        MatSelectModule,
        ReactiveFormsModule,
    ],
    templateUrl: './select-field.component.html',
    styleUrl: './select-field.component.scss',
})
export class SelectFieldComponent<T> {
    @Input() label: string = '';
    @Input() control: FormControl = new FormControl();
    @Input() data$: Observable<T[]> = of();
    @Input() dataFormToShow: (type: T) => string = (type: T) => '';
}
