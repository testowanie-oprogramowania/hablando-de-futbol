import {Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {AbstractControl, FormControl, ReactiveFormsModule} from "@angular/forms";

@Component({
    selector: 'app-text-input-field',
    standalone: true,
    imports: [
        CommonModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
    ],
    templateUrl: './text-input-field.component.html',
    styleUrl: './text-input-field.component.scss',
})
export class TextInputFieldComponent {
    @Input() label: string = '';
    @Input() control: FormControl = new FormControl();
}
