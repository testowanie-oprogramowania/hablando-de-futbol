import {Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {SelectFieldComponent} from "../select-field/select-field.component";
import {TextAreaFieldComponent} from "../text-area-field/text-area-field.component";
import {TextInputFieldComponent} from "../text-input-field/text-input-field.component";

@Component({
    selector: 'app-show-page',
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatCardModule,
        ReactiveFormsModule,
        SelectFieldComponent,
        TextAreaFieldComponent,
        TextInputFieldComponent,
    ],
    templateUrl: './show-page.component.html',
    styleUrl: './show-page.component.scss',
})
export class ShowPageComponent {
    @Input() formGroupA: FormGroup = new FormGroup({})
    @Input() onSubmitForm: () => void = () => {};
}
