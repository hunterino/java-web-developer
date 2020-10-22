package us.twohtwo.featurekeeper.service.impl;

import us.twohtwo.featurekeeper.service.FeatureService;
import us.twohtwo.featurekeeper.domain.Feature;
import us.twohtwo.featurekeeper.repository.FeatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Feature}.
 */
@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {

    private final Logger log = LoggerFactory.getLogger(FeatureServiceImpl.class);

    private final FeatureRepository featureRepository;

    public FeatureServiceImpl(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public Feature save(Feature feature) {
        log.debug("Request to save Feature : {}", feature);
        return featureRepository.save(feature);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Feature> findAll(Pageable pageable) {
        log.debug("Request to get all Features");
        return featureRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Feature> findOne(Long id) {
        log.debug("Request to get Feature : {}", id);
        return featureRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Feature : {}", id);
        featureRepository.deleteById(id);
    }
}
