import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Season {
    ArrayList<Match> matchList = new ArrayList<>();
    public String seasonName;
    public Season(ArrayList<Match> mL, String sN){
        seasonName = sN;
        matchList = mL;
    }
    public ArrayList<Match> getMatchList(){return matchList;}
    public String getSeasonName(){return seasonName;}

    public ArrayList<String> getMatchFileNameList(){
        ArrayList<String> matchFileNameList = new ArrayList<>();
        for(Match match: this.matchList){
            matchFileNameList.add(match.getFileName());
        }
        return matchFileNameList;
    }
    public static ArrayList<String> getNameList(Season season){
        ;
        ArrayList<Match> matchList = season.getMatchList();
        ArrayList<String> playerNameList = new ArrayList<>();

        for(Match match: matchList){
            for(Player player : match.getPlayerList()){
                String currentPlayerName = player.getName();
                if(!playerNameList.contains(currentPlayerName)){
                    playerNameList.add(currentPlayerName);
                }
            }
        }

        return playerNameList;
    }
    public static Season selectSeason(Career career){
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        System.out.println("Select a Season");
        for(Season season: career.getSeasonList()){
            System.out.println("(" + String.valueOf(i) + ")" + season.getSeasonName());
            i += 1;
        }
        int seasonNum = Integer.parseInt(scanner.nextLine());
        scanner.close();
        return career.getSeasonList().get(seasonNum - 1);
    }
    public static void playerStatsAcrossSeason(Career career){
        Season currentSeason = selectSeason(career);
        ArrayList<String> playerNameList = getNameList(currentSeason);
        String playerName = selectNameFromNameList(playerNameList);
        Integer[] playerStatTotals = new Integer[30];
        playerStatTotals = Player.getStatTotals(currentSeason,playerName);
        Double[] playerStatAvg = new Double[30];
        playerStatAvg = Player.getStatAvg(currentSeason,playerName,playerStatTotals);


        System.out.println("Player Name: " + playerName);
        System.out.println("Player Total Stats: " + Arrays.toString(playerStatTotals));
        System.out.println("Player Average Stats: " + Arrays.toString(playerStatAvg));

        Main.menu(career);
    }
    public static String selectNameFromNameList(ArrayList<String> playerNameList){
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        System.out.println("Select Player");
        for(String playerName: playerNameList){
            System.out.println("(" + String.valueOf(i) + ") " + playerName);
            i += 1;
        }
        int input = Integer.parseInt(scanner.nextLine());
        scanner.close();
        return playerNameList.get(input - 1);

    }
    public static void player100ScoreAcrossSeason(Career career){
        ArrayList<String> playerNameList = getNameList(career.getCurrentSeason());
        String currentPlayerName = selectNameFromNameList(playerNameList);
        ArrayList<Double> hundredScoreList = new ArrayList<>();
        ArrayList<Integer> whichGamesPlayed = new ArrayList<>();
        double hundredScoreAvg = 0;
        int gamesPlayed = 0;


        for(Match match: career.getCurrentSeason().getMatchList()){
            for(Player player: match.getPlayerList()){
                if(currentPlayerName.equals(player.getName())){
                    hundredScoreList.add(player.getHundredScore());
                    gamesPlayed += 1;
                    whichGamesPlayed.add(gamesPlayed);
                    hundredScoreAvg += player.getHundredScore();
                }
            }
        }
        hundredScoreAvg = hundredScoreAvg / gamesPlayed;

        System.out.println(currentPlayerName + " 100 Scores:");
        System.out.println(whichGamesPlayed.toString());
        System.out.println(hundredScoreList.toString());
        System.out.println("Number of Games Played: " + gamesPlayed);
        System.out.println("Hundred Score Average: " + hundredScoreAvg);

        Main.menu(career);
    }
    public static Double calculateTeamMean100Score(Career career){
        int gamesPlayed = 0;
        double total100Score = 0.0, avg100Score = 0.0;


        for(Season season: career.getSeasonList()){
            for(Match match: season.getMatchList()){
                for(Player player: match.getPlayerList()){
                    total100Score += player.getHundredScore();
                    gamesPlayed += 1;
                }
            }
        }

        avg100Score = total100Score / gamesPlayed;

        return avg100Score;
    }
    public static void calculateTeamStatTotals(Career career){
        Integer[] teamStatTotals = new Integer[30];
        Double[] teamStatAvg = new Double[30];
        int i = 0;
        int gamesPlayed = 0;
        for(i = 0; i < 30; i++){
            teamStatTotals[i] = 0;
            teamStatAvg[i] = 0.0;
        }

        for(Season season: career.getSeasonList()){
            for(Match match: season.getMatchList()){
                for(Player player: match.getPlayerList()){
                    for(i = 0; i < 30; i++){
                       teamStatTotals[i] += player.getPlayerStats()[i];
                    }
                }
                gamesPlayed += 1;
            }
        }

        for(i = 0; i < 30; i++){
            teamStatAvg[i] = (double)  Math.round((teamStatTotals[i] * 10) / gamesPlayed) / 10;
        }

        System.out.println("Games Played: " + gamesPlayed);
        System.out.println("Team Totals: " + Arrays.toString(teamStatTotals));
        System.out.println("Team Avg: " + Arrays.toString(teamStatAvg));
    }
}