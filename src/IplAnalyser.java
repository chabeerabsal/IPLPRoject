import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IplAnalyser {
    public static void main(String[] args) throws IOException {
        List<String[]> matches= loadCsv("Data/matches.csv");
       List<String[]> deliveries= loadCsv("Data/deliveries.csv");
        System.out.println("\n1. Matches played per year:");
        matchesPlayedPerYear(matches);
        System.out.println("\n2. Matches won by each team:");
        matchesWonPerTeam(matches);
        System.out.println("\n3 For the year 2016 get the extra runs conceded per team");
        extraRunConcededPerTeam(matches,deliveries);

        System.out.println("\n4 For the year 2015 get the top economical bowlers.");
        topEconomicalbowler(matches,deliveries);

        System.out.println("\n5  player of the match in 2017. ");
        playerOfTheMatch(matches);

        System.out.println("\n6 Top 5 batsman of the winning team. ");
        topFiveBatsman(matches,deliveries);
    }

    private static void topFiveBatsman(List<String[]> matches, List<String[]> deliveries) {
        Map<String,String> records=new HashMap<>();
        for(String[] record:matches){
            records.put(record[0],record[10]);
        }
        Map<String,Integer> topRuns=new HashMap<>();
        for(String[] record:deliveries){
            if(records.get(record[0]).equals(record[2]))
            {
                Integer totalRuns=Integer.parseInt(record[17]);
                String player= record[6];
                topRuns.put(player,topRuns.getOrDefault(player,0)+totalRuns);
            }
        }

        List<Map.Entry<String,Integer>> topFiveBatsman=new ArrayList<>(topRuns.entrySet());
        topFiveBatsman.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        int count=0;
        for(Map.Entry<String,Integer> entry:topFiveBatsman){
            if(count==5)
                break;
            System.out.println(entry.getKey()+" "+entry.getValue());
            count++;

        }


    }

    private static void playerOfTheMatch(List<String[]> matches) {
        Map<String,Integer> pom= new HashMap<>();
        for (String[] match : matches) {
            String year=match[1];
            if(year.equals("2016"))
            {
                String player=match[13];
                pom.put(player, pom.getOrDefault(player,0)+1);
            }
        }
        List<Map.Entry<String,Integer>> list= new ArrayList<>(pom.entrySet());
        list.sort(Map.Entry.comparingByValue());
        for (Map.Entry<String,Integer> entry : list) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<String,Integer> entry : list) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }

    private static void topEconomicalbowler(List<String[]> matches, List<String[]> deliveries) {
        HashSet<String> id=new HashSet<>();
        for (String[] match : matches) {
            if(match[1].equals("2015"))
                id.add(match[0]);
        }
        Map<String,Integer> totalRuns=new HashMap<>();
        for(String[]deliverie: deliveries) {
            if(id.contains(deliverie[0])){
                String bowler=deliverie[8];
                int totalRun=Integer.parseInt(deliverie[17]);
                totalRuns.put(bowler, totalRuns.getOrDefault(bowler, 0) + totalRun);
            }
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(totalRuns.entrySet());
        list.sort(Map.Entry.comparingByValue());
        System.out.println("Top Economical Bowlers: "+list.get(0).getKey());
        Set<String> matchIds2015 = new HashSet<>();
        for (String[] row : matches) {
            if (row[1].equals("2015")) {
                matchIds2015.add(row[0]);
            }
        }

        Map<String, Integer> runs = new HashMap<>();
        Map<String, Integer> balls = new HashMap<>();

        for (String[] row : deliveries) {
            if (matchIds2015.contains(row[0])) {
                String bowler = row[8];
                int run = Integer.parseInt(row[17]); // total_runs
                runs.put(bowler, runs.getOrDefault(bowler, 0) + run);
                balls.put(bowler, balls.getOrDefault(bowler, 0) + 1);
            }
        }

        Map<String, Double> economy = new HashMap<>();
        for (String bowler : runs.keySet()) {
            if (balls.get(bowler) >= 60) {
                double eco = runs.get(bowler) / (balls.get(bowler) / 6.0);
                economy.put(bowler, eco);
            }
        }

        economy.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .forEach(e -> System.out.println(e.getKey() + ": " + String.format("%.2f", e.getValue())));

          List<Map.Entry<String, Double>> list2 = new ArrayList<>(economy.entrySet());
        list2.sort(Map.Entry.comparingByValue());
        int count=0;
        for(Map.Entry<String, Double> entry: list2) {
            if(count==5)
                break;
            System.out.println(entry.getKey()+": "+entry.getValue());
            count++;
        }

    }

    private static void extraRunConcededPerTeam(List<String[]> matches, List<String[]> deliveries) {
        HashSet<String> id=new HashSet<>();
        for (String[] match : matches) {
            if(match[1].equals("2016"))
                id.add(match[0]);
        }
        Map<String,Integer> extraRuns=new HashMap<>();
        for(String[]deliverie: deliveries) {
            if(id.contains(deliverie[0])){
                String bowlingTeam=deliverie[3];
                int extraRun=Integer.parseInt(deliverie[16]);
                extraRuns.put(bowlingTeam, extraRuns.getOrDefault(bowlingTeam, 0) + extraRun);
            }
        }
        for(Map.Entry<String,Integer> entry: extraRuns.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }


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
