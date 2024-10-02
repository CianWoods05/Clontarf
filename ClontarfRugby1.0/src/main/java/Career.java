import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Career {
    ArrayList<Season> seasonList = new ArrayList<>();

    public Career(){

    }
    public Career(ArrayList s){
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
        if(input.equals("y")){
            Career.createLatestSeason(career);
        }else{
            Main.menu(career);
        }
    }

    public void readSeasonFolder(){
        int i,j = 0;
        File folderName = new File("src/main/java/career/23_24");
        String positions[] = folderName.list();
        String fileName, pathName;
        for(j = 0; j <= positions.length - 1; j++){
            for(i = 1; i <= 20; i++){
                fileName = "game" + String.valueOf(i);
                pathName = "src/main/java/career/23_24/" + positions[j] + "/" + fileName + ".csv";
                if(Files.exists(Paths.get(pathName))){
                    Match.readGameFile(fileName,positions[j], this);
                }
            }

        }

    }

}
