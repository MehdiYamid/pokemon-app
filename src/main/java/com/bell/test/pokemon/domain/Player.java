package com.bell.test.pokemon.domain;


public class Player {

    private int roundWon;
    private Pokemon pokemon;
    private boolean attackAllow;

    private boolean winner;

    public Player(Pokemon pokemonSelected) {
        this.pokemon = pokemonSelected;
        this.roundWon = 0;
        this.attackAllow = true;
        this.winner = false;
    }

    public int getRoundWon() {
        return roundWon;
    }

    public void setRoundWon(int roundWon) {
        this.roundWon = roundWon;
    }

    public void incrementRoundWon() {
        this.roundWon ++;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public boolean isAttackAllow() {
        return attackAllow;
    }

    public void setAttackAllow(boolean attackAllow) {
        this.attackAllow = attackAllow;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
