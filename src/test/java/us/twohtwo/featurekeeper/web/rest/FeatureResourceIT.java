package us.twohtwo.featurekeeper.web.rest;

import us.twohtwo.featurekeeper.FeatureKeeperApp;
import us.twohtwo.featurekeeper.domain.Feature;
import us.twohtwo.featurekeeper.repository.FeatureRepository;
import us.twohtwo.featurekeeper.service.FeatureService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import us.twohtwo.featurekeeper.domain.enumeration.Client;
import us.twohtwo.featurekeeper.domain.enumeration.ProductArea;
/**
 * Integration tests for the {@link FeatureResource} REST controller.
 */
@SpringBootTest(classes = FeatureKeeperApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeatureResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Client DEFAULT_CLIENT = Client.Kitty;
    private static final Client UPDATED_CLIENT = Client.Hawk;

    private static final Long DEFAULT_CLIENT_PRIORITY = 1L;
    private static final Long UPDATED_CLIENT_PRIORITY = 2L;

    private static final LocalDate DEFAULT_TARGET_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARGET_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ProductArea DEFAULT_PRODUCT_AREA = ProductArea.Policies;
    private static final ProductArea UPDATED_PRODUCT_AREA = ProductArea.Billing;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeatureMockMvc;

    private Feature feature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feature createEntity(EntityManager em) {
        Feature feature = new Feature()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .client(DEFAULT_CLIENT)
            .clientPriority(DEFAULT_CLIENT_PRIORITY)
            .targetDate(DEFAULT_TARGET_DATE)
            .productArea(DEFAULT_PRODUCT_AREA);
        return feature;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feature createUpdatedEntity(EntityManager em) {
        Feature feature = new Feature()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .client(UPDATED_CLIENT)
            .clientPriority(UPDATED_CLIENT_PRIORITY)
            .targetDate(UPDATED_TARGET_DATE)
            .productArea(UPDATED_PRODUCT_AREA);
        return feature;
    }

    @BeforeEach
    public void initTest() {
        feature = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeature() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();
        // Create the Feature
        restFeatureMockMvc.perform(post("/api/features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isCreated());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate + 1);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFeature.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeature.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testFeature.getClientPriority()).isEqualTo(DEFAULT_CLIENT_PRIORITY);
        assertThat(testFeature.getTargetDate()).isEqualTo(DEFAULT_TARGET_DATE);
        assertThat(testFeature.getProductArea()).isEqualTo(DEFAULT_PRODUCT_AREA);
    }

    @Test
    @Transactional
    public void createFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // Create the Feature with an existing ID
        feature.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeatureMockMvc.perform(post("/api/features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFeatures() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get all the featureList
        restFeatureMockMvc.perform(get("/api/features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].clientPriority").value(hasItem(DEFAULT_CLIENT_PRIORITY.intValue())))
            .andExpect(jsonPath("$.[*].targetDate").value(hasItem(DEFAULT_TARGET_DATE.toString())))
            .andExpect(jsonPath("$.[*].productArea").value(hasItem(DEFAULT_PRODUCT_AREA.toString())));
    }
    
    @Test
    @Transactional
    public void getFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feature.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT.toString()))
            .andExpect(jsonPath("$.clientPriority").value(DEFAULT_CLIENT_PRIORITY.intValue()))
            .andExpect(jsonPath("$.targetDate").value(DEFAULT_TARGET_DATE.toString()))
            .andExpect(jsonPath("$.productArea").value(DEFAULT_PRODUCT_AREA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFeature() throws Exception {
        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeature() throws Exception {
        // Initialize the database
        featureService.save(feature);

        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Update the feature
        Feature updatedFeature = featureRepository.findById(feature.getId()).get();
        // Disconnect from session so that the updates on updatedFeature are not directly saved in db
        em.detach(updatedFeature);
        updatedFeature
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .client(UPDATED_CLIENT)
            .clientPriority(UPDATED_CLIENT_PRIORITY)
            .targetDate(UPDATED_TARGET_DATE)
            .productArea(UPDATED_PRODUCT_AREA);

        restFeatureMockMvc.perform(put("/api/features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeature)))
            .andExpect(status().isOk());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFeature.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeature.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testFeature.getClientPriority()).isEqualTo(UPDATED_CLIENT_PRIORITY);
        assertThat(testFeature.getTargetDate()).isEqualTo(UPDATED_TARGET_DATE);
        assertThat(testFeature.getProductArea()).isEqualTo(UPDATED_PRODUCT_AREA);
    }

    @Test
    @Transactional
    public void updateNonExistingFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeatureMockMvc.perform(put("/api/features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeature() throws Exception {
        // Initialize the database
        featureService.save(feature);

        int databaseSizeBeforeDelete = featureRepository.findAll().size();

        // Delete the feature
        restFeatureMockMvc.perform(delete("/api/features/{id}", feature.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
