package com.testowanie.football.mapper;

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
}
