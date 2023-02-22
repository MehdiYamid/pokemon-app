package com.bell.test.pokemon.service;

import com.bell.test.pokemon.domain.*;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PokemonServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonService pokemonService;

    private static final String POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon";

    @Test
    public void getPokemonListTest() {
        List<Pokemon> expectedPokemonList = new ArrayList<>();
        expectedPokemonList.add(new Pokemon("Mewtwo", 100, 114));
        expectedPokemonList.add(new Pokemon("Miaouss", 20, 13));
        PokemonListResponse pokemonListResponse = new PokemonListResponse();
        pokemonListResponse.setResults(expectedPokemonList);
        ResponseEntity<PokemonListResponse> response = new ResponseEntity<>(pokemonListResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(POKEAPI_URL + "?limit=50", PokemonListResponse.class)).thenReturn(response);
        List<Pokemon> actualPokemonList = pokemonService.getPokemonList();

        Assertions.assertEquals(expectedPokemonList, actualPokemonList);
    }

    @Test
    public void getPokemonListKOTest() {
        ResponseEntity<PokemonListResponse> response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        when(restTemplate.getForEntity(POKEAPI_URL + "?limit=50", PokemonListResponse.class)).thenReturn(response);
        List<Pokemon> actualPokemonList = pokemonService.getPokemonList();

        Assertions.assertNull(actualPokemonList);
    }

    @Test
    public void initBattleTest() {
        Pokemon pokemon = new Pokemon("Mewtwo", 100, 114);
        ResponseEntity<Pokemon> response = new ResponseEntity<>(pokemon, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(response);

        Battle battle = pokemonService.initBattle("Mewtwo", "Mewtwo");

        Assertions.assertNotNull(battle);
        Assertions.assertNotNull(battle.getPlayer1());
        Assertions.assertNotNull(battle.getPlayer2());
        Assertions.assertEquals("Mewtwo", battle.getPlayer1().getPokemon().getName());
        Assertions.assertEquals("Mewtwo", battle.getPlayer2().getPokemon().getName());
    }

    @Test
    public void initBattleKoTest() {
        ResponseEntity<Pokemon> response = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(response);

        Battle battle = pokemonService.initBattle("Mewtwo", "Mewtwo");

        Assertions.assertNotNull(battle);
        Assertions.assertNotNull(battle.getPlayer1());
        Assertions.assertNotNull(battle.getPlayer2());
        Assertions.assertNull(battle.getPlayer1().getPokemon());
        Assertions.assertNull(battle.getPlayer2().getPokemon());
    }

    @Test
    void evaluateBattleTest() {
        Player player1 = new Player(new Pokemon("Mewtwo", 100, 114));
        Player player2 = new Player(new Pokemon("Miaouss", 20, 13));
        Battle battle = new Battle(player1, player2);
        int hpPokemon1BeforeAttack = player1.getPokemon().getHp();
        int hpPokemon2BeforeAttack = player2.getPokemon().getHp();
        pokemonService.evaluateBattle(battle, "1", "2");
        int hpPokemon1AfterAttack = player2.getPokemon().getHp();
        int hpPokemon2AfterAttack = player1.getPokemon().getHp();

        Assertions.assertTrue(hpPokemon1BeforeAttack > hpPokemon1AfterAttack);
        Assertions.assertTrue(hpPokemon2BeforeAttack > hpPokemon2AfterAttack);
        Assertions.assertTrue(player1.isAttackAllow());
        Assertions.assertFalse(player2.isAttackAllow());
    }
}

