import java.util.Arrays;
import java.util.Scanner;
public class Main {
    public static void viewPlayerStats(Career career){

        Scanner scanner = new Scanner(System.in);
        System.out.println("(1) View Player Stats from individual Match");
        System.out.println("(2) View Player Totals and Averages across Season");
        System.out.println("(3) View All 100 Scores from individual Match");
        System.out.println("(4) View Player 100 Scores from across Season");
        System.out.println("(5) Back to Menu");
        int option = Integer.parseInt(scanner.nextLine());
        switch(option){
            case 1:
                Player.viewPlayerStatsFromMatch(career);
            case 2:
                Season.playerStatsAcrossSeason(career);
            case 3:
                Match.outputMatch100Scores(career);
            case 4:
                Season.player100ScoreAcrossSeason(career);
            case 5:
                menu(career);
                break;
            default:
                viewPlayerStats(career);
        }

        menu(career);
    }
    public static void viewMatchStats(Career career){
        Match match = Match.selectMatch(career);
        System.out.println(match.getFileName());

        for(Player player: match.getPlayerList()){
            System.out.println(player.getName());
            System.out.println(player.getHundredScore());
            System.out.println(Arrays.toString(player.getPlayerStats()));
        }

        menu(career);
    }

    public static void viewTeamStats(Career career){
        Scanner scanner = new Scanner(System.in);
        System.out.println("(1) View Team Overview");
        System.out.println("(2) View Team Leaderboards");
        int option = Integer.parseInt(scanner.nextLine());
        switch (option){
            case 1:
                System.out.println("Team Mean 100 Score Across Season: " + Season.calculateTeamMean100Score(career));
                Season.calculateTeamStatTotals(career);
                menu(career);
            case 2:
                career.viewTeamLeaderboards();
                menu(career);
            break;
            default:
                menu(career);
        }



    }
    public static void main(String[] args) {
        //initialises empty 23/24 season
        Career career = new Career().initialiseCareer();
        //

        //loops through every file in 23_24 folder and reads in all player data
        career.readSeasonFolder();
        //


        menu(career);
    }

    public static void menu(Career career){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Hello and welcome!");
            System.out.println("(1) View Player Stats Totals and 100 score");
            System.out.println("(2) View Team Stat Totals");
            System.out.println("(3) View Match Stats");
            System.out.println("(4) View/Add Seasons");
            System.out.println("(5) Exit");
            int input = Integer.parseInt(scanner.nextLine());
            switch(input){
                case 1:
                    viewPlayerStats(career);
                case 2:
                    viewTeamStats(career);
                case 3:
                    viewMatchStats(career);
                case 4:
                    Career.addSeason(career);
                case 5:
                    System.exit(0);
                    break;
                default:
                    menu(career);
            }
    }
}