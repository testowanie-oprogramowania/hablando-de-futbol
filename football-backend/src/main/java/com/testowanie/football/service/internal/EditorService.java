package com.testowanie.football.service.internal;

import com.testowanie.football.dto.request.CreateEditorRequest;
import com.testowanie.football.dto.request.UpdateEditorRequest;
import com.testowanie.football.dto.resource.EditorResource;
import com.testowanie.football.exception.EntityNotFoundException;
import com.testowanie.football.mapper.EditorMapper;
import com.testowanie.football.model.Editor;
import com.testowanie.football.repository.EditorRepository;
import com.testowanie.football.service.EditorUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class EditorService implements EditorUseCases {
    private static final String EDITOR_NOT_FOUND = "Editor not found";
    private final EditorMapper editorMapper;
    private final EditorRepository editorRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EditorResource> getEditors(Pageable pageable) {
        return editorRepository.findAll(pageable).map(editorMapper::toEditorResource);
    }

    @Override
    @Transactional(readOnly = true)
    public EditorResource getEditorById(Long id) {
        return editorRepository.findById(id)
                .map(editorMapper::toEditorResource)
                .orElseThrow(() -> new EntityNotFoundException(EDITOR_NOT_FOUND));
    }

    @Override
    @Transactional
    public void createEditor(CreateEditorRequest createEditorRequest) {
        Editor editor = editorMapper.fromCreateEditorRequest(createEditorRequest);
        editorRepository.save(editor);
    }

    @Override
    @Transactional
    public void updateEditor(Long id, UpdateEditorRequest updateEditorRequest) {
        Editor editor = editorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EDITOR_NOT_FOUND));
        editorMapper.updateEditorFromUpdateEditorRequest(updateEditorRequest, editor);
        editorRepository.save(editor);
    }

    @Override
    @Transactional
    public void deleteEditor(Long id) {
        Editor editor = editorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EDITOR_NOT_FOUND));
        editorRepository.delete(editor);
    }
}
