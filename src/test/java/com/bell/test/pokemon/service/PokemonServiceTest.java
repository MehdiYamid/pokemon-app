package com.bell.test.pokemon.service;

import com.bell.test.pokemon.domain.Pokemon;
import com.bell.test.pokemon.domain.PokemonListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PokemonServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonService pokemonService;

    private static final String POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon?limit=50";

    @Test
    public void getPokemonListTest() {
        List<Pokemon> expectedPokemonList = new ArrayList<>();
        expectedPokemonList.add(new Pokemon("Mewtwo", 100, 114));
        expectedPokemonList.add(new Pokemon("Miaouss", 20, 13));

        PokemonListResponse pokemonListResponse = new PokemonListResponse();
        pokemonListResponse.setResults(expectedPokemonList);

        ResponseEntity<PokemonListResponse> response = new ResponseEntity<>(pokemonListResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(POKEAPI_URL, PokemonListResponse.class)).thenReturn(response);
        List<Pokemon> actualPokemonList = pokemonService.getPokemonList();

        assertEquals(expectedPokemonList, actualPokemonList);
    }

    @Test
    public void getPokemonListKOTest() {
        ResponseEntity<PokemonListResponse> response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        when(restTemplate.getForEntity(POKEAPI_URL, PokemonListResponse.class)).thenReturn(response);
        List<Pokemon> actualPokemonList = pokemonService.getPokemonList();
        assertNull(actualPokemonList);
    }

}

