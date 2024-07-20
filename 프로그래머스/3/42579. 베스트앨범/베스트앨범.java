import java.util.*;
import java.util.stream.*;

class Solution {
    public class Song {
        String genre;
        int play;
        int id;
        
        public Song(String genre, int play, int id) {
            this.genre = genre;
            this.play = play;
            this.id = id;
        }
    }
    
    public class Genre {
        String name;
        int play;
        
        public Genre(String name, int play) {
            this.name = name;
            this.play = play;
        }
    }
    
    public int[] solution(String[] genres, int[] plays) {
        List<Integer> answer = new ArrayList<>();
        Map<String, List<Song>> map = new HashMap<>();
        List<Genre> list = new ArrayList<>();
        
        for (int i = 0; i < genres.length; i++) {
            if (!map.containsKey(genres[i])) map.put(genres[i], new ArrayList<>());
            map.get(genres[i])
                .add(new Song(genres[i], plays[i], i));
        }
        
        for (String key : map.keySet()) {
            int totalPlay = map.get(key)
                .stream()
                .map(x -> x.play)
                .mapToInt(i -> i)
                .sum();
            list.add(new Genre(key, totalPlay));
            
            map.put(key, map.get(key)
                .stream()
                .sorted(Comparator.comparing(x -> -x.play))
                .collect(Collectors.toList()));
        }
        list.sort(Comparator.comparing(x -> -x.play));
        
        for (Genre genre : list) {
            List<Song> value = map.get(genre.name);
            for (int i = 0; i < Math.min(2, value.size()); i++) {
                answer.add(value.get(i).id);
            }
        }
            
        return answer.stream()
            .mapToInt(i -> i)
            .toArray();
    }
}