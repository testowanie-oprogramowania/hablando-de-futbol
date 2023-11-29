import {Component, Input} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
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
        FormsModule,
        NgOptimizedImage,
    ],
    templateUrl: './show-page.component.html',
    styleUrl: './show-page.component.scss',
})
export class ShowPageComponent {
    @Input() isForm: boolean = false;
    @Input() onSubmitForm: () => void = () => {};

    @Input() title: string = '';
    @Input() subtitles: string[] = [];
    @Input() mainImageUrl: string = '';
    @Input() thumbnailImageUrl: string = '';
    @Input() descriptionForImageThumbnail: string = '';

}
