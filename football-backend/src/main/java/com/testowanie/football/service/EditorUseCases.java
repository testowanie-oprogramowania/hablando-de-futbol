package com.testowanie.football.service;

import com.testowanie.football.dto.request.CreateEditorRequest;
import com.testowanie.football.dto.request.UpdateEditorRequest;
import com.testowanie.football.dto.resource.EditorResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EditorUseCases {

    Page<EditorResource> getEditors(Pageable pageable);

    EditorResource getEditorById(Long id);

    void createEditor(CreateEditorRequest createEditorRequest);

    void updateEditor(Long id, UpdateEditorRequest updateEditorRequest);

    void deleteEditor(Long id);
}
