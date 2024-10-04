import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Career {
    ArrayList<Season> seasonList = new ArrayList<>();
    public Career(){

    }
    public Career(ArrayList<Season> s){
        seasonList = s;
    }
    public ArrayList<Season> getSeasonList(){
        return seasonList;
    }
    public int getLatestSeason(){
        String sN = seasonList.getLast().getSeasonName();
        return Integer.parseInt(sN.substring(3,5));
    }
    public Season getCurrentSeason(){
        return seasonList.getLast();
    }
    public void addSeason(Season s){
        seasonList.add(s);
    }
    public Career initialiseCareer(){
        ArrayList<Season> seasonList = new ArrayList<>();
        ArrayList<Match> matchList = new ArrayList<>();
        String seasonName = "23_24";
        Season season = new Season(matchList, seasonName);
        seasonList.add(season);
        Career career = new Career(seasonList);
        return career;
    }
    public static void createLatestSeason(Career career){
        ArrayList<Match> matchList = new ArrayList<>();
        String seasonName = String.valueOf(career.getLatestSeason()) + "_" + String.valueOf(career.getLatestSeason()+1);
        Season season = new Season(matchList, seasonName);
        career.getSeasonList().add(season);
        Main.menu(career);
    }
    public static void addSeason(Career career){
        Scanner scanner = new Scanner(System.in);
        Season.selectSeason(career);
        System.out.println("Do you still want to add another season? (y/n)");
        String input = scanner.nextLine();
        scanner.close();
        if(input.equals("y")){
            Career.createLatestSeason(career);
        }else{
            Main.menu(career);
        }
    }
    public void readSeasonFolder(){
        int i,j = 0;
        File folderName = new File("/workspaces/Clontarf/ClontarfRugby1.0/src/main/java/career/23_24/");
        String positions[] = folderName.list();
        String fileName, pathName;
        for(j = 0; j <= positions.length - 1; j++){
            for(i = 1; i <= 20; i++){
                fileName = "game" + String.valueOf(i);
                pathName = "/workspaces/Clontarf/ClontarfRugby1.0/src/main/java/career/23_24/" + positions[j] + "/" + fileName + ".csv";
                if(Files.exists(Paths.get(pathName))){
                    Match.readGameFile(fileName,positions[j], this);
                }
            }

        }

    }
    public void viewTeamLeaderboards(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("(1) View Leaderboard for particular PlayerStat");
        System.out.println("(2) View Leaderboard for all PlayerStats");
        System.out.println("(3) View Player Position in all Leaderboards");
        int input = Integer.parseInt(scanner.nextLine());
        scanner.close();
        switch (input){
            case 1:
                viewLeaderboardForIndividualStat();
            case 2:
                viewLeaderboardForAllStats();
            case 3:
                viewPlayerPositionInLeaderboards();
                break;
            default:
                Main.menu(this);
        }

        Main.menu(this);
    }
    public void viewLeaderboardForIndividualStat(){
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        displayStatNameList();
        int input = Integer.parseInt(scanner.nextLine()) - 1;
        scanner.close();
        String statName = getElementOfStatNameList(input);
        Season season = this.getCurrentSeason();
        ArrayList<String> playerNameList = Season.getNameList(season);
        ArrayList<Integer[]> playerStatTotalArray = new ArrayList<>();
        ArrayList<Double[]> playerStatAvgArray = new ArrayList<>();

        for(String playerName: playerNameList){
            playerStatTotalArray.add(Player.getStatTotals(season,playerName));
            playerStatAvgArray.add(Player.getStatAvg(season,playerName,playerStatTotalArray.get(i)));
            i += 1;
        }


        boolean isTotal = true;
        sortPlayerStatArrays(playerStatTotalArray,playerStatAvgArray,playerNameList,input, isTotal);

        System.out.println(statName);
        for(i = 0; i < playerStatTotalArray.size(); i ++){
            System.out.println("(" + String.valueOf(i+1) + ") " + playerNameList.get(i));
            System.out.println(playerStatTotalArray.get(i)[input]);
        }

        isTotal = false;
        sortPlayerStatArrays(playerStatTotalArray,playerStatAvgArray,playerNameList,input, isTotal);

        System.out.println(statName);
        for(i = 0; i < playerStatAvgArray.size(); i ++){
            System.out.println("(" + String.valueOf(i+1) + ") " + playerNameList.get(i));
            System.out.println(playerStatAvgArray.get(i)[input]);
        }



        Main.menu(this);
    }
    public static void sortPlayerStatArrays(ArrayList<Integer[]> playerStatTotalArray,ArrayList<Double[]> playerStatAvgArray, ArrayList<String> playerNameList, int input, boolean isTotal){
        boolean sorted = false;
        int i;
        while(!sorted){
            sorted = true;
            for(i = 0; i < playerStatTotalArray.size() - 1; i++){
                if (isTotal) {
                    Integer[] array1 = playerStatTotalArray.get(i);
                    Integer[] array2 = playerStatTotalArray.get(i+1);
                    if(array1[input] < array2[input]){
                        Collections.swap(playerStatTotalArray,i,i+1);
                        Collections.swap(playerStatAvgArray,i,i+1);
                        Collections.swap(playerNameList,i,i+1);
                        sorted = false;
                    }
                }else{
                    Double[] array1 = playerStatAvgArray.get(i);
                    Double[] array2 = playerStatAvgArray.get(i+1);
                    if(array1[input] < array2[input]){
                        Collections.swap(playerStatTotalArray,i,i+1);
                        Collections.swap(playerStatAvgArray,i,i+1);
                        Collections.swap(playerNameList,i,i+1);
                        sorted = false;
                    }
                }


            }
        }


    }




    public static ArrayList<String> getStatNameList(){
        ArrayList<String> statNameList = new ArrayList<>();
        for(PlayerStats stat: PlayerStats.values()){
            statNameList.add(stat.getName());
        }
        return statNameList;
    }
    public static String getElementOfStatNameList(int i){
        ArrayList<String> statNameList = getStatNameList();
        return statNameList.get(i);
    }
    public static void displayStatNameList(){
        int i = 1;
        ArrayList<String> statNameList = getStatNameList();
        System.out.println("Select PlayerStat:");
        for(String statName: statNameList){
            System.out.println("(" + String.valueOf(i) + ") " + statName);
            i += 1;
        }

    }
    public void viewLeaderboardForAllStats(){
        int i = 0,input = 0;
        Season season = this.getCurrentSeason();
        ArrayList<String> statNameList = getStatNameList();
        ArrayList<String> playerNameList = Season.getNameList(season);

        ArrayList<Integer[]> playerStatTotalArray = new ArrayList<>();
        ArrayList<Double[]> playerStatAvgArray = new ArrayList<>();

        for(String playerName: playerNameList){
            playerStatTotalArray.add(Player.getStatTotals(season,playerName));
            playerStatAvgArray.add(Player.getStatAvg(season,playerName,playerStatTotalArray.get(i)));
            i += 1;
        }

        for(input = 0; input < statNameList.size(); input++){

            String statName = getElementOfStatNameList(input);

            boolean isTotal = true;
            sortPlayerStatArrays(playerStatTotalArray,playerStatAvgArray,playerNameList,input, isTotal);
            System.out.println(statName);
            for(i = 0; i < playerStatTotalArray.size() - 1; i ++){
                System.out.println("(" + String.valueOf(i+1) + ") " + playerNameList.get(i));
                System.out.println(playerStatTotalArray.get(i)[input]);
            }

            isTotal = false;
            sortPlayerStatArrays(playerStatTotalArray,playerStatAvgArray,playerNameList,input, isTotal);
            System.out.println(statName);
            for(i = 0; i < playerStatTotalArray.size() - 1; i ++){
                System.out.println("(" + String.valueOf(i+1) + ") " + playerNameList.get(i));
                System.out.println(playerStatTotalArray.get(i)[input]);
            }


        }


        Main.menu(this);
    }
    public void viewPlayerPositionInLeaderboards() {
        int i;
        Season season = this.getCurrentSeason();

        ArrayList<String> playerNameList = Season.getNameList(season);
        String currentPlayer = Season.selectNameFromNameList(playerNameList);
        ArrayList<String> statNameList = getStatNameList();
        ArrayList<Integer[]> playerStatTotalArray = new ArrayList<>();
        ArrayList<Double[]> playerStatAvgArray = new ArrayList<>();

        // Populate stat arrays
        for (String playerName : playerNameList) {
            playerStatTotalArray.add(Player.getStatTotals(season, playerName));
            playerStatAvgArray.add(Player.getStatAvg(season, playerName, playerStatTotalArray.get(playerStatTotalArray.size() - 1)));
        }

        for (int input = 0; input < statNameList.size(); input++) {
            String statName = statNameList.get(input);

            // Sort by total stats
            boolean isTotal = true;
            sortPlayerStatArrays(playerStatTotalArray, playerStatAvgArray, playerNameList, input, isTotal);
            System.out.println(statName);
            for (i = 0; i < playerNameList.size(); i++) {
                if (playerNameList.get(i).equals(currentPlayer)) {
                    System.out.println("(" + (i + 1) + ") " + playerNameList.get(i));
                    System.out.println(playerStatTotalArray.get(i)[input]);
                }
            }
        }

        for(int input = 0; input < statNameList.size(); input++){
            String statName = statNameList.get(input);
            // Sort by average stats
            boolean isTotal = false;
            sortPlayerStatArrays(playerStatTotalArray,playerStatAvgArray, playerNameList, input, isTotal);
            System.out.println(statName);
            for (i = 0; i < playerNameList.size(); i++) {
                if (playerNameList.get(i).equals(currentPlayer)) {
                    System.out.println("(" + (i + 1) + ") " + playerNameList.get(i));
                    System.out.println(playerStatAvgArray.get(i)[input]);
                }
            }
        }

        Main.menu(this);
    }
}