import java.util.Arrays;
import java.util.Scanner;
public class Player {
    public String name;
    public double hundredScore;
    public String position;
    public Integer[] playerStats;

    public Player(String n, String p, double hS, Integer[] pS) {
        name = n;
        position = p;
        hundredScore = hS;
        playerStats = pS;
    }

    public Player(String n,String p, Integer[] pS) {
        name = n;
        position = p;
        hundredScore = 0;
        playerStats = pS;
    }

    public static Integer[] getStatTotals(Season season,String playerName){
        Integer[] currentPlayerStatTotals = new Integer[30];
        int i = 0;

        for(i = 0; i < 30; i++){
            currentPlayerStatTotals[i] = 0;
        }

        for(Match match: season.getMatchList()){
            for(Player player: match.getPlayerList()){

                if(playerName.equals(player.getName())){
                    Integer[] playerStats = player.getPlayerStats();

                    for(i = 0; i < 30; i ++){
                        currentPlayerStatTotals[i] += playerStats[i];
                    }
                }
            }
        }
        return currentPlayerStatTotals;
    }

    public static Double[] getStatAvg(Season season,String playerName, Integer[] playerStatTotals){
        int i = 0;
        Double[] currentPlayerStatAvg = new Double[30];

        for(i = 0; i < 30; i++){
            currentPlayerStatAvg[i] = 0.0;
        }

        int numGamesPlayed = 0;
        for(Match match: season.getMatchList()){
            for(Player player: match.getPlayerList()){
                if(playerName.equals(player.getName())){
                    numGamesPlayed += 1;
                }
            }
        }

        for(i = 0; i < 30; i++){

            currentPlayerStatAvg[i] = (double) playerStatTotals[i] / numGamesPlayed;

        }


        return currentPlayerStatAvg;
    }

    public Double getHundredScore(){
        return hundredScore;
    }
    public void setHundredScore(){
        Integer[] currentPlayerStats = this.getPlayerStats();
        this.hundredScore = 0;
        int i = 0;

        for(PlayerStats value: PlayerStats.values()){
            this.hundredScore += (currentPlayerStats[i] * value.getValue());
            i += 1;
        }

        this.hundredScore = (double) Math.round(this.hundredScore * 10) / 10;
    }
    public String getName() {
        return name;
    }

    public Integer[] getPlayerStats() {
        return playerStats;
    }

    public String getPosition() {
        return position;
    }


    public static void viewPlayerStatsFromMatch(Career career){
        Match currentMatch = Match.selectMatch(career);
        Player currentPlayer = selectPlayer(currentMatch);
        displayPlayerStats(currentPlayer);
        Main.menu(career);
    }

    public static Player selectPlayer(Match currentMatch){
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        System.out.println("Select a Player");
        for(Player currentPlayer: currentMatch.getPlayerList()){
            System.out.println("(" + String.valueOf(i) + ")" + currentPlayer.getName());
            i += 1;
        }
        int input = Integer.parseInt(scanner.nextLine());
        Player selectedPlayer = currentMatch.getPlayerList().get(input - 1);
        return selectedPlayer;

    }

    public static void displayPlayerStats(Player currentPlayer){
        System.out.println("Player Name: " + currentPlayer.getName());
        System.out.println("100 score: " + currentPlayer.getHundredScore());
        System.out.println("Player Stats: " + Arrays.toString(currentPlayer.getPlayerStats()));

    }



}
