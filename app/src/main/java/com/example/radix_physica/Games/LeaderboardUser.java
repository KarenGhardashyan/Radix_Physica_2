package com.example.radix_physica.Games;

public class LeaderboardUser {
    private String username;
    private int score;

    public LeaderboardUser() {
    }

    public LeaderboardUser(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
