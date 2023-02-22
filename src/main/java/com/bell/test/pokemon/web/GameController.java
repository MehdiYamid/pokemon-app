package com.bell.test.pokemon.web;

import com.bell.test.pokemon.domain.Battle;
import com.bell.test.pokemon.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * The controller for the game
 */
@Controller
@SessionAttributes("battle")
public class GameController {

    @Autowired
    private PokemonService pokemonService;

    /**
     * First page for Pokemon selection
     *
     * @param model The model
     * @return The Pokemons selection form
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pokemonList", pokemonService.getPokemonList());
        return "index";
    }

    /**
     * The page of the battle
     *
     * @param pokemonSelected1 The player's 1 Pokemon
     * @param pokemonSelected2 The player's 2 Pokemon
     * @param model The model
     * @return The battle form
     */
    @PostMapping("/battle")
    public String battle(@RequestParam("pokemonSelected1") String pokemonSelected1, @RequestParam("pokemonSelected2") String pokemonSelected2, Model model) {
        Battle battle = pokemonService.initBattle(pokemonSelected1, pokemonSelected2);
        model.addAttribute("battle", battle);
        return "battle";
    }

    /**
     * The attach action
     * @param battle The current battle
     * @param attackType1 The attack type for player 1 (normal or special)
     * @param attackType2 The attack type for player 2 (normal or special)
     * @param model The model
     * @return The battle form
     */
    @PostMapping("/attack")
    public String attack(@ModelAttribute("battle") Battle battle,
                         @RequestParam(name = "attackType1", required = false) String attackType1,
                         @RequestParam(name = "attackType2", required = false) String attackType2,
                         Model model) {
        model.addAttribute("battle", pokemonService.evaluateBattle(battle, attackType1, attackType2));
        return "battle";
    }
}
