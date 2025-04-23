package fest.hack.baylortype.service;

import fest.hack.baylortype.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaderboardService {
    private final ScoreRepository scoreRepository;

    @Autowired
    public LeaderboardService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }
}
