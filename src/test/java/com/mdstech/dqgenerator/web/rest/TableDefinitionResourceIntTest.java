package com.mdstech.dqgenerator.web.rest;

import com.mdstech.dqgenerator.DqgeneratorApp;

import com.mdstech.dqgenerator.domain.TableDefinition;
import com.mdstech.dqgenerator.repository.TableDefinitionRepository;
import com.mdstech.dqgenerator.service.TableDefinitionService;
import com.mdstech.dqgenerator.web.rest.errors.ExceptionTranslator;
import com.mdstech.dqgenerator.service.dto.TableDefinitionCriteria;
import com.mdstech.dqgenerator.service.TableDefinitionQueryService;

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
 * Test class for the TableDefinitionResource REST controller.
 *
 * @see TableDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DqgeneratorApp.class)
public class TableDefinitionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_NAME = "BBBBBBBBBB";

    @Autowired
    private TableDefinitionRepository tableDefinitionRepository;

    @Autowired
    private TableDefinitionService tableDefinitionService;

    @Autowired
    private TableDefinitionQueryService tableDefinitionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTableDefinitionMockMvc;

    private TableDefinition tableDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TableDefinitionResource tableDefinitionResource = new TableDefinitionResource(tableDefinitionService, tableDefinitionQueryService);
        this.restTableDefinitionMockMvc = MockMvcBuilders.standaloneSetup(tableDefinitionResource)
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
    public static TableDefinition createEntity(EntityManager em) {
        TableDefinition tableDefinition = new TableDefinition()
            .name(DEFAULT_NAME)
            .schemaName(DEFAULT_SCHEMA_NAME);
        return tableDefinition;
    }

    @Before
    public void initTest() {
        tableDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createTableDefinition() throws Exception {
        int databaseSizeBeforeCreate = tableDefinitionRepository.findAll().size();

        // Create the TableDefinition
        restTableDefinitionMockMvc.perform(post("/api/table-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableDefinition)))
            .andExpect(status().isCreated());

        // Validate the TableDefinition in the database
        List<TableDefinition> tableDefinitionList = tableDefinitionRepository.findAll();
        assertThat(tableDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        TableDefinition testTableDefinition = tableDefinitionList.get(tableDefinitionList.size() - 1);
        assertThat(testTableDefinition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTableDefinition.getSchemaName()).isEqualTo(DEFAULT_SCHEMA_NAME);
    }

    @Test
    @Transactional
    public void createTableDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tableDefinitionRepository.findAll().size();

        // Create the TableDefinition with an existing ID
        tableDefinition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTableDefinitionMockMvc.perform(post("/api/table-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableDefinition)))
            .andExpect(status().isBadRequest());

        // Validate the TableDefinition in the database
        List<TableDefinition> tableDefinitionList = tableDefinitionRepository.findAll();
        assertThat(tableDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tableDefinitionRepository.findAll().size();
        // set the field null
        tableDefinition.setName(null);

        // Create the TableDefinition, which fails.

        restTableDefinitionMockMvc.perform(post("/api/table-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableDefinition)))
            .andExpect(status().isBadRequest());

        List<TableDefinition> tableDefinitionList = tableDefinitionRepository.findAll();
        assertThat(tableDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTableDefinitions() throws Exception {
        // Initialize the database
        tableDefinitionRepository.saveAndFlush(tableDefinition);

        // Get all the tableDefinitionList
        restTableDefinitionMockMvc.perform(get("/api/table-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tableDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].schemaName").value(hasItem(DEFAULT_SCHEMA_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTableDefinition() throws Exception {
        // Initialize the database
        tableDefinitionRepository.saveAndFlush(tableDefinition);

        // Get the tableDefinition
        restTableDefinitionMockMvc.perform(get("/api/table-definitions/{id}", tableDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tableDefinition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.schemaName").value(DEFAULT_SCHEMA_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllTableDefinitionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tableDefinitionRepository.saveAndFlush(tableDefinition);

        // Get all the tableDefinitionList where name equals to DEFAULT_NAME
        defaultTableDefinitionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tableDefinitionList where name equals to UPDATED_NAME
        defaultTableDefinitionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTableDefinitionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tableDefinitionRepository.saveAndFlush(tableDefinition);

        // Get all the tableDefinitionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTableDefinitionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tableDefinitionList where name equals to UPDATED_NAME
        defaultTableDefinitionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTableDefinitionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tableDefinitionRepository.saveAndFlush(tableDefinition);

        // Get all the tableDefinitionList where name is not null
        defaultTableDefinitionShouldBeFound("name.specified=true");

        // Get all the tableDefinitionList where name is null
        defaultTableDefinitionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTableDefinitionsBySchemaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tableDefinitionRepository.saveAndFlush(tableDefinition);

        // Get all the tableDefinitionList where schemaName equals to DEFAULT_SCHEMA_NAME
        defaultTableDefinitionShouldBeFound("schemaName.equals=" + DEFAULT_SCHEMA_NAME);

        // Get all the tableDefinitionList where schemaName equals to UPDATED_SCHEMA_NAME
        defaultTableDefinitionShouldNotBeFound("schemaName.equals=" + UPDATED_SCHEMA_NAME);
    }

    @Test
    @Transactional
    public void getAllTableDefinitionsBySchemaNameIsInShouldWork() throws Exception {
        // Initialize the database
        tableDefinitionRepository.saveAndFlush(tableDefinition);

        // Get all the tableDefinitionList where schemaName in DEFAULT_SCHEMA_NAME or UPDATED_SCHEMA_NAME
        defaultTableDefinitionShouldBeFound("schemaName.in=" + DEFAULT_SCHEMA_NAME + "," + UPDATED_SCHEMA_NAME);

        // Get all the tableDefinitionList where schemaName equals to UPDATED_SCHEMA_NAME
        defaultTableDefinitionShouldNotBeFound("schemaName.in=" + UPDATED_SCHEMA_NAME);
    }

    @Test
    @Transactional
    public void getAllTableDefinitionsBySchemaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tableDefinitionRepository.saveAndFlush(tableDefinition);

        // Get all the tableDefinitionList where schemaName is not null
        defaultTableDefinitionShouldBeFound("schemaName.specified=true");

        // Get all the tableDefinitionList where schemaName is null
        defaultTableDefinitionShouldNotBeFound("schemaName.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTableDefinitionShouldBeFound(String filter) throws Exception {
        restTableDefinitionMockMvc.perform(get("/api/table-definitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tableDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].schemaName").value(hasItem(DEFAULT_SCHEMA_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTableDefinitionShouldNotBeFound(String filter) throws Exception {
        restTableDefinitionMockMvc.perform(get("/api/table-definitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTableDefinition() throws Exception {
        // Get the tableDefinition
        restTableDefinitionMockMvc.perform(get("/api/table-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTableDefinition() throws Exception {
        // Initialize the database
        tableDefinitionService.save(tableDefinition);

        int databaseSizeBeforeUpdate = tableDefinitionRepository.findAll().size();

        // Update the tableDefinition
        TableDefinition updatedTableDefinition = tableDefinitionRepository.findOne(tableDefinition.getId());
        updatedTableDefinition
            .name(UPDATED_NAME)
            .schemaName(UPDATED_SCHEMA_NAME);

        restTableDefinitionMockMvc.perform(put("/api/table-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTableDefinition)))
            .andExpect(status().isOk());

        // Validate the TableDefinition in the database
        List<TableDefinition> tableDefinitionList = tableDefinitionRepository.findAll();
        assertThat(tableDefinitionList).hasSize(databaseSizeBeforeUpdate);
        TableDefinition testTableDefinition = tableDefinitionList.get(tableDefinitionList.size() - 1);
        assertThat(testTableDefinition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTableDefinition.getSchemaName()).isEqualTo(UPDATED_SCHEMA_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTableDefinition() throws Exception {
        int databaseSizeBeforeUpdate = tableDefinitionRepository.findAll().size();

        // Create the TableDefinition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTableDefinitionMockMvc.perform(put("/api/table-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tableDefinition)))
            .andExpect(status().isCreated());

        // Validate the TableDefinition in the database
        List<TableDefinition> tableDefinitionList = tableDefinitionRepository.findAll();
        assertThat(tableDefinitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTableDefinition() throws Exception {
        // Initialize the database
        tableDefinitionService.save(tableDefinition);

        int databaseSizeBeforeDelete = tableDefinitionRepository.findAll().size();

        // Get the tableDefinition
        restTableDefinitionMockMvc.perform(delete("/api/table-definitions/{id}", tableDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TableDefinition> tableDefinitionList = tableDefinitionRepository.findAll();
        assertThat(tableDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableDefinition.class);
        TableDefinition tableDefinition1 = new TableDefinition();
        tableDefinition1.setId(1L);
        TableDefinition tableDefinition2 = new TableDefinition();
        tableDefinition2.setId(tableDefinition1.getId());
        assertThat(tableDefinition1).isEqualTo(tableDefinition2);
        tableDefinition2.setId(2L);
        assertThat(tableDefinition1).isNotEqualTo(tableDefinition2);
        tableDefinition1.setId(null);
        assertThat(tableDefinition1).isNotEqualTo(tableDefinition2);
    }
}
