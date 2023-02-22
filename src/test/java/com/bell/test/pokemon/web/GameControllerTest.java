package com.bell.test.pokemon.web;

import com.bell.test.pokemon.domain.Battle;
import com.bell.test.pokemon.domain.Player;
import com.bell.test.pokemon.domain.Pokemon;
import com.bell.test.pokemon.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class GameControllerTest {

    @Mock
    private PokemonService pokemonService;

    @Mock
    private Model model;

    @InjectMocks
    private GameController gameController;

    @Test
    public void indexTest() {
        List<Pokemon> expectedPokemonList = new ArrayList<>();
        expectedPokemonList.add(new Pokemon("Mewtwo", 100, 114));
        expectedPokemonList.add(new Pokemon("Miaouss", 20, 13));
        when(pokemonService.getPokemonList()).thenReturn(expectedPokemonList);

        String viewName = gameController.index(model);

        assertEquals("index", viewName);
        verify(model).addAttribute("pokemonList", expectedPokemonList);
    }

    @Test
    public void battleTest() {
        Battle battle = new Battle(new Player(new Pokemon("Mewtwo", 100, 114)), new Player(new Pokemon("Miaouss", 20, 13)));
        when(pokemonService.initBattle("Mewtwo", "Miaouss")).thenReturn(battle);

        String viewName = gameController.battle("Mewtwo", "Miaouss", model);

        assertEquals("battle", viewName);
        verify(model).addAttribute("battle", battle);
    }

    @Test
    public void attackTest() {
        Battle battle = new Battle(new Player(new Pokemon("Mewtwo", 100, 114)), new Player(new Pokemon("Miaouss", 20, 13)));
        when(pokemonService.evaluateBattle(battle, "Mewtwo", "Miaouss")).thenReturn(battle);

        String viewName = gameController.attack(battle, "Mewtwo", "Miaouss", model);

        assertEquals("battle", viewName);
        verify(model).addAttribute("battle", battle);
    }
}