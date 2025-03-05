package ru.hogwarts.school;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(FacultyService.class)
@WebMvcTest(FacultyController.class)
public class FacultyWebMvcTest {

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
        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                .contentType("application/json")
                .content("{\"name\":\"Гриффиндор\",\"color\":\"красный\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testEditFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/1")
                .contentType("application/json")
                .content("{\"name\":\"Гриффиндор\",\"color\":\"красный\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindFacultiesByNameOrColor() throws Exception {
        mockMvc.perform(get("/faculty?name=Гриффиндор"))
                .andExpect(status().isOk());
    }

}
