package com.skillbox.fibonacci;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class FibonacciControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FibonacciService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenValidIndex_whenHttpGet_thenReturnNumber() throws Exception {
        FibonacciNumber number = new FibonacciNumber(12, 144);
        when(service.fibonacciNumber(12)).thenReturn(number);

        mockMvc.perform(get("/fibonacci/12"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.index").value(12))
               .andExpect(jsonPath("$.value").value(144));

        verify(service).fibonacciNumber(12);
    }

    @Test
    void givenInvalidIndex_whenHttpGet_thenReturnBadRequest() throws Exception {
        when(service.fibonacciNumber(-1)).thenThrow(new IllegalArgumentException("Index should be greater or equal to 1"));

        mockMvc.perform(get("/fibonacci/-1"))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Index should be greater or equal to 1"));

        verify(service).fibonacciNumber(-1);
    }
}
