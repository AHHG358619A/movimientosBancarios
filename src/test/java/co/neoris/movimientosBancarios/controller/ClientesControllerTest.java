package co.neoris.movimientosBancarios.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.neoris.movimientosBancarios.dto.ClientesDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureWebClient
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
class ClientesControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  void getListClients() throws Exception {

    mockMvc.perform(get("/api/v1/clientes")).andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("[0].id").value("1"))
        .andExpect(jsonPath("[1].id").value("2"));

  }

  @Test
  void createClient() throws Exception {

    ClientesDTO clienteDTO = new ClientesDTO();
    clienteDTO.setIdentificacion("1111155555777");
    clienteDTO.setNombres("usuario test");
    clienteDTO.setGenero("Masculino");
    clienteDTO.setEdad(19);
    clienteDTO.setDireccion("Carrera 77A Barrio Mira");
    clienteDTO.setTelefono(123123123);
    clienteDTO.setContrasenia("123123");
    clienteDTO.setEstado(true);

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(clienteDTO);

    mockMvc.perform(post("/api/v1/clientes").content(json).characterEncoding("utf-8")
            .contentType("application/json"))
        .andExpect(status().isCreated());

  }

  @Test
  void updateClientWithExistingClientId() throws Exception {

    ClientesDTO clienteDTO = new ClientesDTO();
    clienteDTO.setIdentificacion("4444555");
    clienteDTO.setNombres("usuario actualizado");
    clienteDTO.setGenero("Femenino");
    clienteDTO.setEdad(99);
    clienteDTO.setDireccion("Carrera 77A Barrio Mirador");
    clienteDTO.setTelefono(123123123);
    clienteDTO.setContrasenia("123123");
    clienteDTO.setEstado(true);

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(clienteDTO);

    mockMvc.perform(
            put("/api/v1/clientes/5").content(json).characterEncoding("utf-8")
                .contentType("application/json"))
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.nombres").value("usuario actualizado"))
        .andExpect(jsonPath("$.identificacion").value("4444555"));

  }


  @Test
  void updateClientWithNotExistingClientId() throws Exception {

    ClientesDTO clienteDTO = new ClientesDTO();
    clienteDTO.setIdentificacion("777888999");

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(clienteDTO);

    mockMvc.perform(put("/api/v1/clientes/99").content(json).characterEncoding("utf-8")
            .contentType("application/json"))
        .andExpect(status().isNotFound());

  }

  @Test
  void deleteClientWithExistingClientId() throws Exception {

    mockMvc.perform(delete("/api/v1/clientes/4"))
        .andExpect(status().isAccepted());
  }
}