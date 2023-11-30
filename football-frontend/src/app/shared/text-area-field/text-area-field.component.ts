import {Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";

@Component({
    selector: 'app-text-area-field',
    standalone: true,
    imports: [
        CommonModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
    ],
    templateUrl: './text-area-field.component.html',
    styleUrl: './text-area-field.component.scss',
})
export class TextAreaFieldComponent {
    @Input() label: string = '';
    @Input() control: FormControl = new FormControl();
    @Input() rowsNumber: number = 0;
}
