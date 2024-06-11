package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import src.*;


public class gameTest {
    private gamePanel gamePanel;
    private player player;
    private rival rival;
    private monster monster;

    @BeforeEach
    public void setup() {
        player = new player(gamePanel);
        rival = new rival(gamePanel);
        monster = new monster(gamePanel, 5, 5);
        List<monster> monsters = new ArrayList<>();
        monsters.add(monster);
        gamePanel = new gamePanel(player, rival, monsters);
    }

    @Test
    public void playerMovesIntoSafeZone() {
        player.move(1, 0);
        assertTrue(player.isInSafeZone());
    }

    @Test
    public void playerMovesIntoDangerZone() {
        player.move(5, 5);
        assertFalse(player.isInSafeZone());
    }

    @Test
    public void rivalMovesIntoSafeZone() {
        rival.move(1, 0);
        assertTrue(gamePanel.isOccupied(rival.getX(), rival.getY()));
    }

    @Test
    public void rivalMovesIntoDangerZone() {
        rival.move(5, 5);
        assertFalse(gamePanel.isOccupied(rival.getX(), rival.getY()));
    }

    @Test
    public void monsterMovesTowardsPlayer() {
        int initialDistance = Math.abs(monster.getX() - player.getX()) + Math.abs(monster.getY() - player.getY());
        monster.move();
        int newDistance = Math.abs(monster.getX() - player.getX()) + Math.abs(monster.getY() - player.getY());
        assertTrue(newDistance <= initialDistance);
    }

    @Test
    public void monsterMovesTowardsRival() {
        rival.setComputerControlled(true);
        int initialDistance = Math.abs(monster.getX() - rival.getX()) + Math.abs(monster.getY() - rival.getY());
        monster.move();
        int newDistance = Math.abs(monster.getX() - rival.getX()) + Math.abs(monster.getY() - rival.getY());
        assertTrue(newDistance <= initialDistance);
    }
}