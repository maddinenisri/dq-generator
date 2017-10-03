package com.mdstech.dqgenerator.web.rest;

import com.mdstech.dqgenerator.DqgeneratorApp;

import com.mdstech.dqgenerator.domain.ColumnDefinition;
import com.mdstech.dqgenerator.repository.ColumnDefinitionRepository;
import com.mdstech.dqgenerator.service.ColumnDefinitionService;
import com.mdstech.dqgenerator.web.rest.errors.ExceptionTranslator;
import com.mdstech.dqgenerator.service.dto.ColumnDefinitionCriteria;
import com.mdstech.dqgenerator.service.ColumnDefinitionQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ColumnDefinitionResource REST controller.
 *
 * @see ColumnDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DqgeneratorApp.class)
public class ColumnDefinitionResourceIntTest {

    private static final String DEFAULT_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLUMN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PK_COLUMN_ORDER = 1;
    private static final Integer UPDATED_PK_COLUMN_ORDER = 2;

    @Autowired
    private ColumnDefinitionRepository columnDefinitionRepository;

    @Autowired
    private ColumnDefinitionService columnDefinitionService;

    @Autowired
    private ColumnDefinitionQueryService columnDefinitionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restColumnDefinitionMockMvc;

    private ColumnDefinition columnDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColumnDefinitionResource columnDefinitionResource = new ColumnDefinitionResource(columnDefinitionService, columnDefinitionQueryService);
        this.restColumnDefinitionMockMvc = MockMvcBuilders.standaloneSetup(columnDefinitionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ColumnDefinition createEntity(EntityManager em) {
        ColumnDefinition columnDefinition = new ColumnDefinition()
            .columnName(DEFAULT_COLUMN_NAME)
            .columnType(DEFAULT_COLUMN_TYPE)
            .pkColumnOrder(DEFAULT_PK_COLUMN_ORDER);
        return columnDefinition;
    }

    @Before
    public void initTest() {
        columnDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createColumnDefinition() throws Exception {
        int databaseSizeBeforeCreate = columnDefinitionRepository.findAll().size();

        // Create the ColumnDefinition
        restColumnDefinitionMockMvc.perform(post("/api/column-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnDefinition)))
            .andExpect(status().isCreated());

        // Validate the ColumnDefinition in the database
        List<ColumnDefinition> columnDefinitionList = columnDefinitionRepository.findAll();
        assertThat(columnDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        ColumnDefinition testColumnDefinition = columnDefinitionList.get(columnDefinitionList.size() - 1);
        assertThat(testColumnDefinition.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
        assertThat(testColumnDefinition.getColumnType()).isEqualTo(DEFAULT_COLUMN_TYPE);
        assertThat(testColumnDefinition.getPkColumnOrder()).isEqualTo(DEFAULT_PK_COLUMN_ORDER);
    }

    @Test
    @Transactional
    public void createColumnDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = columnDefinitionRepository.findAll().size();

        // Create the ColumnDefinition with an existing ID
        columnDefinition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColumnDefinitionMockMvc.perform(post("/api/column-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnDefinition)))
            .andExpect(status().isBadRequest());

        // Validate the ColumnDefinition in the database
        List<ColumnDefinition> columnDefinitionList = columnDefinitionRepository.findAll();
        assertThat(columnDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkColumnNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = columnDefinitionRepository.findAll().size();
        // set the field null
        columnDefinition.setColumnName(null);

        // Create the ColumnDefinition, which fails.

        restColumnDefinitionMockMvc.perform(post("/api/column-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnDefinition)))
            .andExpect(status().isBadRequest());

        List<ColumnDefinition> columnDefinitionList = columnDefinitionRepository.findAll();
        assertThat(columnDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColumnTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = columnDefinitionRepository.findAll().size();
        // set the field null
        columnDefinition.setColumnType(null);

        // Create the ColumnDefinition, which fails.

        restColumnDefinitionMockMvc.perform(post("/api/column-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnDefinition)))
            .andExpect(status().isBadRequest());

        List<ColumnDefinition> columnDefinitionList = columnDefinitionRepository.findAll();
        assertThat(columnDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColumnDefinitions() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList
        restColumnDefinitionMockMvc.perform(get("/api/column-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(columnDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME.toString())))
            .andExpect(jsonPath("$.[*].columnType").value(hasItem(DEFAULT_COLUMN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pkColumnOrder").value(hasItem(DEFAULT_PK_COLUMN_ORDER)));
    }

    @Test
    @Transactional
    public void getColumnDefinition() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get the columnDefinition
        restColumnDefinitionMockMvc.perform(get("/api/column-definitions/{id}", columnDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(columnDefinition.getId().intValue()))
            .andExpect(jsonPath("$.columnName").value(DEFAULT_COLUMN_NAME.toString()))
            .andExpect(jsonPath("$.columnType").value(DEFAULT_COLUMN_TYPE.toString()))
            .andExpect(jsonPath("$.pkColumnOrder").value(DEFAULT_PK_COLUMN_ORDER));
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByColumnNameIsEqualToSomething() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where columnName equals to DEFAULT_COLUMN_NAME
        defaultColumnDefinitionShouldBeFound("columnName.equals=" + DEFAULT_COLUMN_NAME);

        // Get all the columnDefinitionList where columnName equals to UPDATED_COLUMN_NAME
        defaultColumnDefinitionShouldNotBeFound("columnName.equals=" + UPDATED_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByColumnNameIsInShouldWork() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where columnName in DEFAULT_COLUMN_NAME or UPDATED_COLUMN_NAME
        defaultColumnDefinitionShouldBeFound("columnName.in=" + DEFAULT_COLUMN_NAME + "," + UPDATED_COLUMN_NAME);

        // Get all the columnDefinitionList where columnName equals to UPDATED_COLUMN_NAME
        defaultColumnDefinitionShouldNotBeFound("columnName.in=" + UPDATED_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByColumnNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where columnName is not null
        defaultColumnDefinitionShouldBeFound("columnName.specified=true");

        // Get all the columnDefinitionList where columnName is null
        defaultColumnDefinitionShouldNotBeFound("columnName.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByColumnTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where columnType equals to DEFAULT_COLUMN_TYPE
        defaultColumnDefinitionShouldBeFound("columnType.equals=" + DEFAULT_COLUMN_TYPE);

        // Get all the columnDefinitionList where columnType equals to UPDATED_COLUMN_TYPE
        defaultColumnDefinitionShouldNotBeFound("columnType.equals=" + UPDATED_COLUMN_TYPE);
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByColumnTypeIsInShouldWork() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where columnType in DEFAULT_COLUMN_TYPE or UPDATED_COLUMN_TYPE
        defaultColumnDefinitionShouldBeFound("columnType.in=" + DEFAULT_COLUMN_TYPE + "," + UPDATED_COLUMN_TYPE);

        // Get all the columnDefinitionList where columnType equals to UPDATED_COLUMN_TYPE
        defaultColumnDefinitionShouldNotBeFound("columnType.in=" + UPDATED_COLUMN_TYPE);
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByColumnTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where columnType is not null
        defaultColumnDefinitionShouldBeFound("columnType.specified=true");

        // Get all the columnDefinitionList where columnType is null
        defaultColumnDefinitionShouldNotBeFound("columnType.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByPkColumnOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where pkColumnOrder equals to DEFAULT_PK_COLUMN_ORDER
        defaultColumnDefinitionShouldBeFound("pkColumnOrder.equals=" + DEFAULT_PK_COLUMN_ORDER);

        // Get all the columnDefinitionList where pkColumnOrder equals to UPDATED_PK_COLUMN_ORDER
        defaultColumnDefinitionShouldNotBeFound("pkColumnOrder.equals=" + UPDATED_PK_COLUMN_ORDER);
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByPkColumnOrderIsInShouldWork() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where pkColumnOrder in DEFAULT_PK_COLUMN_ORDER or UPDATED_PK_COLUMN_ORDER
        defaultColumnDefinitionShouldBeFound("pkColumnOrder.in=" + DEFAULT_PK_COLUMN_ORDER + "," + UPDATED_PK_COLUMN_ORDER);

        // Get all the columnDefinitionList where pkColumnOrder equals to UPDATED_PK_COLUMN_ORDER
        defaultColumnDefinitionShouldNotBeFound("pkColumnOrder.in=" + UPDATED_PK_COLUMN_ORDER);
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByPkColumnOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where pkColumnOrder is not null
        defaultColumnDefinitionShouldBeFound("pkColumnOrder.specified=true");

        // Get all the columnDefinitionList where pkColumnOrder is null
        defaultColumnDefinitionShouldNotBeFound("pkColumnOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByPkColumnOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where pkColumnOrder greater than or equals to DEFAULT_PK_COLUMN_ORDER
        defaultColumnDefinitionShouldBeFound("pkColumnOrder.greaterOrEqualThan=" + DEFAULT_PK_COLUMN_ORDER);

        // Get all the columnDefinitionList where pkColumnOrder greater than or equals to UPDATED_PK_COLUMN_ORDER
        defaultColumnDefinitionShouldNotBeFound("pkColumnOrder.greaterOrEqualThan=" + UPDATED_PK_COLUMN_ORDER);
    }

    @Test
    @Transactional
    public void getAllColumnDefinitionsByPkColumnOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        columnDefinitionRepository.saveAndFlush(columnDefinition);

        // Get all the columnDefinitionList where pkColumnOrder less than or equals to DEFAULT_PK_COLUMN_ORDER
        defaultColumnDefinitionShouldNotBeFound("pkColumnOrder.lessThan=" + DEFAULT_PK_COLUMN_ORDER);

        // Get all the columnDefinitionList where pkColumnOrder less than or equals to UPDATED_PK_COLUMN_ORDER
        defaultColumnDefinitionShouldBeFound("pkColumnOrder.lessThan=" + UPDATED_PK_COLUMN_ORDER);
    }


    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultColumnDefinitionShouldBeFound(String filter) throws Exception {
        restColumnDefinitionMockMvc.perform(get("/api/column-definitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(columnDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME.toString())))
            .andExpect(jsonPath("$.[*].columnType").value(hasItem(DEFAULT_COLUMN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pkColumnOrder").value(hasItem(DEFAULT_PK_COLUMN_ORDER)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultColumnDefinitionShouldNotBeFound(String filter) throws Exception {
        restColumnDefinitionMockMvc.perform(get("/api/column-definitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingColumnDefinition() throws Exception {
        // Get the columnDefinition
        restColumnDefinitionMockMvc.perform(get("/api/column-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColumnDefinition() throws Exception {
        // Initialize the database
        columnDefinitionService.save(columnDefinition);

        int databaseSizeBeforeUpdate = columnDefinitionRepository.findAll().size();

        // Update the columnDefinition
        ColumnDefinition updatedColumnDefinition = columnDefinitionRepository.findOne(columnDefinition.getId());
        updatedColumnDefinition
            .columnName(UPDATED_COLUMN_NAME)
            .columnType(UPDATED_COLUMN_TYPE)
            .pkColumnOrder(UPDATED_PK_COLUMN_ORDER);

        restColumnDefinitionMockMvc.perform(put("/api/column-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedColumnDefinition)))
            .andExpect(status().isOk());

        // Validate the ColumnDefinition in the database
        List<ColumnDefinition> columnDefinitionList = columnDefinitionRepository.findAll();
        assertThat(columnDefinitionList).hasSize(databaseSizeBeforeUpdate);
        ColumnDefinition testColumnDefinition = columnDefinitionList.get(columnDefinitionList.size() - 1);
        assertThat(testColumnDefinition.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testColumnDefinition.getColumnType()).isEqualTo(UPDATED_COLUMN_TYPE);
        assertThat(testColumnDefinition.getPkColumnOrder()).isEqualTo(UPDATED_PK_COLUMN_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingColumnDefinition() throws Exception {
        int databaseSizeBeforeUpdate = columnDefinitionRepository.findAll().size();

        // Create the ColumnDefinition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restColumnDefinitionMockMvc.perform(put("/api/column-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnDefinition)))
            .andExpect(status().isCreated());

        // Validate the ColumnDefinition in the database
        List<ColumnDefinition> columnDefinitionList = columnDefinitionRepository.findAll();
        assertThat(columnDefinitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteColumnDefinition() throws Exception {
        // Initialize the database
        columnDefinitionService.save(columnDefinition);

        int databaseSizeBeforeDelete = columnDefinitionRepository.findAll().size();

        // Get the columnDefinition
        restColumnDefinitionMockMvc.perform(delete("/api/column-definitions/{id}", columnDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ColumnDefinition> columnDefinitionList = columnDefinitionRepository.findAll();
        assertThat(columnDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColumnDefinition.class);
        ColumnDefinition columnDefinition1 = new ColumnDefinition();
        columnDefinition1.setId(1L);
        ColumnDefinition columnDefinition2 = new ColumnDefinition();
        columnDefinition2.setId(columnDefinition1.getId());
        assertThat(columnDefinition1).isEqualTo(columnDefinition2);
        columnDefinition2.setId(2L);
        assertThat(columnDefinition1).isNotEqualTo(columnDefinition2);
        columnDefinition1.setId(null);
        assertThat(columnDefinition1).isNotEqualTo(columnDefinition2);
    }
}
