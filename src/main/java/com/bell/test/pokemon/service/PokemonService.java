package com.bell.test.pokemon.service;

import com.bell.test.pokemon.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
public class PokemonService {

    private static final String POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon";
    private static final int HP_INIT = 20;
    private static final int ROUND_MAX = 2;

    private final Random random = new Random();

    @Autowired
    private RestTemplate restTemplate;

    public List<Pokemon> getPokemonList() {
        ResponseEntity<PokemonListResponse> response = restTemplate.getForEntity(POKEAPI_URL + "?limit=50", PokemonListResponse.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getResults();
        }
        return null;
    }

    public Battle initBattle(String pokemonSelected1, String pokemonSelected2) {
        return new Battle(setupPlayer(pokemonSelected1), setupPlayer(pokemonSelected2));
    }

    public Battle evaluateBattle(Battle battle, String attackType1, String attackType2) {
        battle.getPlayer1().getPokemon().decreaseHp(calculateDamage(attackType2));
        battle.getPlayer2().getPokemon().decreaseHp(calculateDamage(attackType1));

        battle.getPlayer1().setAttackAllow(attackType1 == null || !(Integer.parseInt(attackType1)==AttackTypeEnum.SPECIAL_ATTACK.getValue()));
        battle.getPlayer2().setAttackAllow(attackType2 == null || !(Integer.parseInt(attackType2)==AttackTypeEnum.SPECIAL_ATTACK.getValue()));

        int player1Hp = battle.getPlayer1().getPokemon().getHp();
        int player2Hp = battle.getPlayer2().getPokemon().getHp();

        if (player1Hp <= 0) {
            battle.getPlayer2().incrementRoundWon();
            if (battle.getPlayer2().getRoundWon()==ROUND_MAX) {
                battle.getPlayer2().setWinner(true);
            }
        } else if (player2Hp <= 0) {
            battle.getPlayer1().incrementRoundWon();
            if (battle.getPlayer1().getRoundWon()==ROUND_MAX) {
                battle.getPlayer1().setWinner(true);
            }
        }
        if (player1Hp <= 0 || player2Hp <= 0) {
            battle.incrementRound();
            battle.getPlayer1().getPokemon().setHp(HP_INIT);
            battle.getPlayer2().getPokemon().setHp(HP_INIT);
        }
        return battle;
    }

    private Pokemon getPokemonByName(String pokemonName) {
        ResponseEntity<Pokemon> response = restTemplate.getForEntity(POKEAPI_URL + "/" + pokemonName, Pokemon.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    private Player setupPlayer(String pokemonSelected) {
        return new Player(getPokemonByName(pokemonSelected));
    }

    private int calculateDamage(String attackType) {
        int damage = 0;
        if (attackType != null) {
            if (attackType.equals("1")) {
                return random.nextInt(10) + 1;
            } else if (attackType.equals("2")) {
                return random.nextInt(11) + 5;
            }
        }
        return damage;
    }
}
