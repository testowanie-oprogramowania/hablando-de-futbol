import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';

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
    @Input() hasInitialValue: boolean = false;
    @Input() data$: Observable<T[]> = of();
    @Input() compareObjects: (o1: T, o2: T) => boolean = (o1: T, o2: T) =>
        false;
    @Input() dataFormToShow: (type: T) => string = (type: T) => '';
}
