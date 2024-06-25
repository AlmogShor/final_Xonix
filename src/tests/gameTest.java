//package tests;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import java.util.*;
//
//import src.*;
//
//
//public class gameTest {
//    private GamePanel gamePanel;
//    private Player player;
//    private Rival rival;
//    private Monster monster;
//
//    @BeforeEach
//    public void setup() {
//        player = new Player(gamePanel);
//        rival = new Rival(gamePanel);
//        monster = new Monster(gamePanel, 5, 5);
//        List<Monster> monsters = new ArrayList<>();
//        monsters.add(monster);
//        gamePanel = new GamePanel(player, rival, monsters);
//    }
//
//    @Test
//    public void playerMovesIntoSafeZone() {
//        player.move();
//        assertTrue(player.isInSafeZone());
//    }
//
//    @Test
//    public void playerMovesIntoDangerZone() {
//        player.move();
//        assertFalse(player.isInSafeZone());
//    }
//
//    @Test
//    public void rivalMovesIntoSafeZone() {
//        rival.move();
//        assertTrue(gamePanel.isOccupied(rival.getX(), rival.getY()));
//    }
//
//    @Test
//    public void rivalMovesIntoDangerZone() {
//        rival.move();
//        assertFalse(gamePanel.isOccupied(rival.getX(), rival.getY()));
//    }
//
//    @Test
//    public void monsterMovesTowardsPlayer() {
//        int initialDistance = Math.abs(monster.getX() - player.getX()) + Math.abs(monster.getY() - player.getY());
//        monster.move();
//        int newDistance = Math.abs(monster.getX() - player.getX()) + Math.abs(monster.getY() - player.getY());
//        assertTrue(newDistance <= initialDistance);
//    }
//
//    @Test
//    public void monsterMovesTowardsRival() {
//        rival.setComputerControlled(true);
//        int initialDistance = Math.abs(monster.getX() - rival.getX()) + Math.abs(monster.getY() - rival.getY());
//        monster.move();
//        int newDistance = Math.abs(monster.getX() - rival.getX()) + Math.abs(monster.getY() - rival.getY());
//        assertTrue(newDistance <= initialDistance);
//    }
//}