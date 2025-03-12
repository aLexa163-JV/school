package ru.hogwarts.school.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(FacultyService.class)
@WebMvcTest(FacultyController.class)
public class FacultyWebMvcTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private FacultyRepository facultyRepository;

    @Test
    public void testGetFacultyById() throws Exception {
        Faculty faculty = new Faculty("Гриффиндор", "красный");

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty("Гриффиндор", "красный");

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(post("/faculty")
                .contentType("application/json")
                .content("{\"name\":\"Гриффиндор\",\"color\":\"красный\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Faculty faculty = new Faculty("Гриффиндор", "красный");

        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(delete("/faculty/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testEditFaculty() throws Exception {
        Faculty faculty = new Faculty("Гриффиндор", "красный");

        when(facultyRepository.save(any())).thenReturn(faculty);
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/1")
                        .content(objectMapper.writeValueAsString(new Faculty()))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testFindFacultiesByNameOrColor() throws Exception {
        Faculty faculty = new Faculty("Гриффиндор", "красный");

        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty?name=Гриффиндор"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
