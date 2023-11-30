export class EditorRequest {
    constructor(
        public name: string,
        public surname: string,
        public photoUrl: string
    ) {}

    public static fromForm(
        editorRequestRawFormValue: EditorRequestRawFormValue
    ): EditorRequest {
        return new EditorRequest(
            editorRequestRawFormValue.name,
            editorRequestRawFormValue.surname,
            editorRequestRawFormValue.photoUrl
        );
    }
}

export interface EditorRequestRawFormValue {
    name: string;
    surname: string;
    photoUrl: string;
}
