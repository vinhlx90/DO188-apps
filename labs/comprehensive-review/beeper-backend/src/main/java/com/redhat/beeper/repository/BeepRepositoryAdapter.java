package com.redhat.beeper.repository;

import com.redhat.beeper.application.BeepRepository;
import com.redhat.beeper.application.model.Beep;
import com.redhat.beeper.application.model.BeepNotFoundException;
import com.redhat.beeper.repository.model.BeepEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class BeepRepositoryAdapter implements BeepRepository {

    private BeepJpaRepository repo;
    private BeepEntityMapper mapper;

    public BeepRepositoryAdapter(BeepJpaRepository repo, BeepEntityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<Beep> findAll() {
        return repo.findAll().stream()
                .map(mapper::toModel)
                .collect(toList());
    }

    @Override
    public Beep save(Beep beep) {
        return mapper.toModel(repo.save(mapper.toEntity(beep)));
    }

    @Override
    public Beep findById(Long id) {

        return repo.findById(id).map(mapper::toModel)
                .orElseThrow(() -> new BeepNotFoundException());
    }

    @Override
    public void delete(Long id) {
        findById(id);
        this.repo.deleteById(id);
    }
}
