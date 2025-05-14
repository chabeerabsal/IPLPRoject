import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IplAnalyser {
    public static void main(String[] args) throws IOException {
        List<String[]> matches= loadCsv("Data/matches.csv");
        List<String[]> deliveries= loadCsv("Data/deliveries.csv");
        System.out.println("\n1. Matches played per year:");
        matchesPlayedPerYear(matches);
        System.out.println("\n2. Matches won by each team:");
        matchesWonPerTeam(matches);
    }

    private static void matchesWonPerTeam(List<String[]> matches) {
        Map<String,Integer> matchesWonPerTeam= new HashMap<>();
        for (String[] match : matches) {
            String winner=match[10];
            if(winner.equals(""))
                continue;
            matchesWonPerTeam.put(winner,matchesWonPerTeam.getOrDefault(winner,0)+1);
        }

        for(Map.Entry<String,Integer> winner:matchesWonPerTeam.entrySet()){
            System.out.println(winner.getKey()+": "+winner.getValue());
        }
    }

    private static void matchesPlayedPerYear(List<String[]> matches) {
        Map<String, Integer> matchesPerYear = new HashMap<>();
        for (String[] match : matches) {
            String year = match[1];
            matchesPerYear.put(year, matchesPerYear.getOrDefault(year, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : matchesPerYear.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    static List<String[]> loadCsv(String fileName) throws IOException {
       BufferedReader bf=new BufferedReader(new FileReader(fileName));
       String line=bf.readLine();
       List<String[]> data = new ArrayList<String[]>();
       while((line=bf.readLine())!=null){
           data.add(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1));
          }
          bf.close();
       return data;
        }
}
