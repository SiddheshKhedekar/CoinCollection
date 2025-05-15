package com.siddhesh.coincollection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddhesh.coincollection.model.Currency;
import com.siddhesh.coincollection.model.Denomination;
import com.siddhesh.coincollection.service.DenominationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DenominationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DenominationService denominationService;

    @InjectMocks
    private DenominationController denominationController;

    private ObjectMapper objectMapper;

    private Denomination sampleDenomination;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(denominationController).build();
        objectMapper = new ObjectMapper();

        Currency currency = new Currency();
        currency.setId(1L);
        sampleDenomination = new Denomination(1L, "Series-A", 5.0, "url", currency);
    }

    @Test
    public void testCreateDenomination() throws Exception {
        when(denominationService.createDenomination(any())).thenReturn(sampleDenomination);

        mockMvc.perform(post("/api/denominations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDenomination)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateDenomination() throws Exception {
        when(denominationService.updateDenomination(any(), any())).thenReturn(sampleDenomination);

        mockMvc.perform(put("/api/denominations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDenomination)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteDenomination() throws Exception {
        doNothing().when(denominationService).deleteDenomination(1L);

        mockMvc.perform(delete("/api/denominations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetDenominationById_Found() throws Exception {
        when(denominationService.getDenominationById(1L)).thenReturn(Optional.of(sampleDenomination));

        mockMvc.perform(get("/api/denominations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.series").value("Series-A"));
    }

    @Test
    public void testGetDenominationById_NotFound() throws Exception {
        when(denominationService.getDenominationById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/denominations/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetByValue() throws Exception {
        when(denominationService.getByValue(5.0)).thenReturn(List.of(sampleDenomination));

        mockMvc.perform(get("/api/denominations/value/5.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].value").value(5.0));
    }

    @Test
    public void testGetBySeries() throws Exception {
        when(denominationService.getBySeries("Series-A")).thenReturn(List.of(sampleDenomination));

        mockMvc.perform(get("/api/denominations/series/Series-A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].series").value("Series-A"));
    }

    @Test
    public void testGetByValueAndSeries() throws Exception {
        when(denominationService.getByValueAndSeries(5.0, "Series-A")).thenReturn(List.of(sampleDenomination));

        mockMvc.perform(get("/api/denominations/filter")
                        .param("value", "5.0")
                        .param("series", "Series-A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].series").value("Series-A"));
    }

    @Test
    public void testGetAllDenominations() throws Exception {
        when(denominationService.getAllDenominations()).thenReturn(Arrays.asList(sampleDenomination));

        mockMvc.perform(get("/api/denominations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
