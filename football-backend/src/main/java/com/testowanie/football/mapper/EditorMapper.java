package com.testowanie.football.mapper;

import com.testowanie.football.dto.request.CreateEditorRequest;
import com.testowanie.football.dto.request.UpdateEditorRequest;
import com.testowanie.football.dto.resource.EditorResource;
import com.testowanie.football.model.Editor;
import org.springframework.stereotype.Component;

@Component
public class EditorMapper {
    public EditorResource toEditorResource(Editor editor) {
        return EditorResource.builder()
                .id(editor.getId())
                .name(editor.getName())
                .surname(editor.getSurname())
                .image(editor.getPhotoUrl())
                .build();
    }

    public Editor fromCreateEditorRequest(CreateEditorRequest createEditorRequest) {
        return Editor.builder()
                .name(createEditorRequest.name())
                .surname(createEditorRequest.surname())
                .photoUrl(createEditorRequest.photoUrl())
                .build();
    }

    public void updateEditorFromUpdateEditorRequest(UpdateEditorRequest updateEditorRequest, Editor editor) {
        editor.setName(updateEditorRequest.name());
        editor.setSurname(updateEditorRequest.surname());
        editor.setPhotoUrl(updateEditorRequest.photoUrl());
    }
}
