import {Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";

@Component({
    selector: 'app-date-picker-field',
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatDatepickerModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
    ],
    templateUrl: './date-picker-field.component.html',
    styleUrl: './date-picker-field.component.scss',
})
export class DatePickerFieldComponent {
    @Input() label: string = '';
    @Input() control: FormControl = new FormControl();
}
