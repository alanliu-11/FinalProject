package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Leaderboard {
    static TreeMap<Integer, TreeSet<String>> leaderBoard = new TreeMap<>(Collections.reverseOrder());
    static HashSet<String> names = new HashSet<>();
    public void initializeLeaderboard() throws IOException {
        File file = new File("leaderboard.txt");
        file.createNewFile();
        Scanner in = new Scanner(file);
        if (!in.hasNextInt()){
            return;
        }
        int differentScores = in.nextInt();
        for (int i = 0; i < differentScores; i++){
            int first = in.nextInt();
            TreeSet<String> temp = new TreeSet<>();
            int second = in.nextInt();
            for (int j = 0; j < first; j++){
                temp.add(in.next());
                leaderBoard.put(second, temp);
            }
        }
        in.close();
    }
    public void saveLeaderboard() throws IOException {
        File leaderboard = new File("leaderboard.txt");
        if (!leaderboard.exists()){
            leaderboard.createNewFile();
        }

        PrintWriter pw = new PrintWriter(leaderboard);
        pw.write(leaderBoard.size() + " ");
        for (int i : leaderBoard.keySet()){
            pw.write(leaderBoard.get(i).size() + " " + i + " ");
            for (String j : leaderBoard.get(i)){
                pw.write(j + " ");
            }
        }
        pw.close();
    }
    public void printLeaderboard(Graphics g){
        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.drawString("Leaderboard", GameConstant.SCREEN_MAX_WIDTH/2 - 350, 100);
        int printed = 0;
        int yPrint = 200;
        while (printed < Math.min(5, leaderBoard.size())){
            for (int score : leaderBoard.keySet()) {
                for (String player : leaderBoard.get(score)) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 30));
                    g.drawString(score + " " + player, GameConstant.SCREEN_MAX_WIDTH / 2 - 350, yPrint);
                    yPrint += 50;
                    printed++;
                }
            }
        }
    }
    public void updateLeaderboard(int score){
        String input = JOptionPane.showInputDialog(null, "Enter your username:");
        while (input == null || input.isEmpty()){
            input = JOptionPane.showInputDialog(null, "You have to pick a name:");
            if (input.isEmpty()){
                break;
            }
        }
        if (names.contains(input)){
            int originalScore = 0;
            for (int lbScore : leaderBoard.keySet()){
                for (String person : leaderBoard.get(lbScore)){
                    if (person.equals(input)){
                        originalScore = lbScore;
                        break;
                    }
                }
            }
            if (originalScore < score){
                leaderBoard.get(originalScore).remove(input); //removes person from that old score if they previously got a higher one
            }
        }
        if (leaderBoard.containsKey(score)){
            leaderBoard.get(score).add(input);
        }
        else{
            TreeSet<String> tempSet = new TreeSet<>();
            tempSet.add(input);
            leaderBoard.put(score, tempSet);
        }
    }
}
