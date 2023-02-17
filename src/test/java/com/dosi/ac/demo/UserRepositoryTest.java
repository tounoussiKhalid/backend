package com.dosi.ac.demo;

import com.dosi.ac.demo.entities.User;
import com.dosi.ac.demo.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.sql.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    final String BIRTHDATE = "1999-06-30";

    @Test
    public void testCreateUser() throws Exception {
        Optional<User> optionalUser = userRepository.findByFirstNameAndLastName("John", "Doe");
        System.out.println(optionalUser);
        if( optionalUser.isPresent())
        {
            userRepository.delete(optionalUser.get());
            return;
        }
        User user = new User();
        user.setFirst_name("John");
        user.setLast_name("Doe");
        user.setBirthdate(Date.valueOf(BIRTHDATE));
        user.setCity("New York");

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andReturn();

        String location = result.getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value(user.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(user.getLast_name()))
                .andExpect(jsonPath("$.birthdate").value(user.getBirthdate().toString()))
                .andExpect(jsonPath("$.city").value(user.getCity()));
    }

    @Test
    public void testGetUserById() throws Exception {
        Optional<User> optionalUser = userRepository.findByFirstNameAndLastName("John", "Doe");
        System.out.println(optionalUser);
        if( optionalUser.isPresent())
        {
            userRepository.delete(optionalUser.get());
            return;
        }
        User user = userRepository.save(User.builder()
                .first_name("John")
                .last_name("Doe")
                .city("New York")
                .birthdate(Date.valueOf(BIRTHDATE))
                .build());

        mockMvc.perform(get("/users/" + user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value(user.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(user.getLast_name()))
                .andExpect(jsonPath("$.birthdate").value(user.getBirthdate().toString()))
                .andExpect(jsonPath("$.city").value(user.getCity()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        Optional<User> optionalUser = userRepository.findByFirstNameAndLastName("John", "Doe");
        System.out.println(optionalUser);
        if( optionalUser.isPresent())
        {
            userRepository.delete(optionalUser.get());
            return;
        }
        User user = new User();
        user.setFirst_name("John");
        user.setLast_name("Doe");
        user.setBirthdate(Date.valueOf(BIRTHDATE));
        user.setCity("New York");
        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andReturn();

        String[] parts =  result.getResponse().getHeader("Location").split("/");
        long id = Long.parseLong(parts[parts.length - 1]);

        user.setId(id);
        user.setFirst_name("Jane");
        user.setLast_name("Doe");
        user.setCity("Los Angeles");

        MvcResult resultUpdate = mockMvc.perform(put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();
        String location = resultUpdate.getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value(user.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(user.getLast_name()))
                .andExpect(jsonPath("$.birthdate").value(user.getBirthdate().toString()))
                .andExpect(jsonPath("$.city").value(user.getCity()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = userRepository.save( User.builder()
                .first_name("John")
                .last_name("Doe")
                .city("New York")
                .birthdate(Date.valueOf(BIRTHDATE))
                .build());

        mockMvc.perform(delete("/users/" + user.getId()))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.findById(user.getId()).isPresent());
    }
}