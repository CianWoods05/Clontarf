import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Match {
    ArrayList<Player> playerList = new ArrayList<>();
    public boolean wasHome;
    public int homeScore;
    public int awayScore;
    public String refereeName;
    public String fileName;

    public Match(String fN,int hS, int aS, boolean wH, String rN, ArrayList<Player> pL){
        fileName = fN;
        homeScore = hS;
        awayScore = aS;
        wasHome = wH;
        refereeName = rN;
        playerList = pL;
    }
    public Match(String fN, ArrayList<Player> pL){
        fileName = fN;
        homeScore = 0;
        awayScore = 0;
        wasHome = false;
        refereeName = "";
        playerList = pL;
    }
    public String getFileName(){return fileName;}
    public int getHomeScore(){return homeScore;}
    public int getAwayScore(){return awayScore;}
    public boolean getWasHome(){return wasHome;}
    public String getRefereeName(){return refereeName;}
    public ArrayList<Player> getPlayerList(){return playerList;}

    public void addToPlayerList(Player player){
        this.playerList.add(player);
    }

    public static void readGameFile(String fileName,String position, Career career) {
        int i;
        ArrayList<Integer[]> statArray = new ArrayList<>();
        String pathName = "src/main/java/career/23_24/" + position + "/" + fileName + ".csv";
        String testRow;
        String[] nameList;

        int numPlayers = 0;

        try {
            // Open and read the file
            BufferedReader br = new BufferedReader(new FileReader(pathName));

            nameList = br.readLine().split(",");
            numPlayers = nameList.length;
            Integer[] statRow = new Integer[numPlayers];


            while ((testRow = br.readLine()) != null) {
                String[] line = testRow.split(",");
                for(i = 0; i < numPlayers ; i++){
                    statRow[i] = Integer.parseInt(line[i]);
                }
                statArray.add(statRow);
                statRow = new Integer[numPlayers];

            }
            
            br.close();
            addStatsToMatch(career, fileName,position, statArray, nameList);
            System.out.printf(fileName);
            System.out.printf(" has been added to season ");
            System.out.printf(career.getCurrentSeason().getSeasonName());
            System.out.println();
        } catch (IOException e) {
            System.out.println("ERROR: Could not read " + fileName);
        }

        


    }
    public static void addStatsToMatch(Career career, String fileName,String position, ArrayList<Integer[]> statArray, String[] nameList){

        int i,j = 0;
        Integer[] statColumn;
        ArrayList<Integer[]> playerStats = new ArrayList<>();
        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<Match> matchList = career.getCurrentSeason().getMatchList();
        Match currentMatch;
        Player player;


        for(i = 0; i < nameList.length; i++){
            statColumn = new Integer[30];
            for(j = 0; j < statArray.size(); j++) {
                statColumn[j] = statArray.get(j)[i];
            }
            playerStats.add(statColumn);
            player = new Player(nameList[i],position, playerStats.get(i));
            player.setHundredScore();
            playerList.add(player);

        }
        ArrayList<String> matchFileNameList = career.getCurrentSeason().getMatchFileNameList();

        if(matchFileNameList.contains(fileName)){
            Match match = career.getCurrentSeason().getMatchList().get(matchFileNameList.indexOf(fileName)); // this is shit but it works
            for(Player currentPlayer: playerList){
                match.addToPlayerList(currentPlayer);
            }
        }else{
            currentMatch = new Match(fileName, playerList);
            matchList.add(currentMatch);
        }
    }

    public static void outputMatch100Scores(Career career){
        Match currentMatch = selectMatch(career);

        System.out.println(currentMatch.getFileName());
        for(Player player : currentMatch.getPlayerList()){
            System.out.println(player.getName() + ", " + player.getHundredScore() );
        }
        Main.menu(career);
    }

    public static Match selectMatch(Career career){
        Season currentSeason = Season.selectSeason(career);
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        System.out.println("Select a Match");
        for(Match match: currentSeason.getMatchList()){
            System.out.println("(" + String.valueOf(i) + ")" + match.getFileName());
            i += 1;
        }
        int matchNum = Integer.parseInt(scanner.nextLine());
        scanner.close();
        return currentSeason.getMatchList().get(matchNum - 1);
    }

    public ArrayList<String> getMatchNameList(Career career){
        Match currentMatch = this;
        ArrayList<String> matchNameList = new ArrayList<>();
        for(Player player: currentMatch.getPlayerList()){
            matchNameList.add(player.getName());
        }
        return matchNameList;
    }
}
