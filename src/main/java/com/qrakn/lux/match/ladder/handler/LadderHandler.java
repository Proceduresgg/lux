package com.qrakn.lux.match.ladder.handler;

        import com.qrakn.lux.match.ladder.Ladder;
        import lombok.Getter;

        import java.util.HashMap;
        import java.util.Map;

@Getter
public enum LadderHandler {

    INSTANCE;

    private final Map<String, Ladder> ladders = new HashMap<>();

    public void init() {

    }

    public void save() {
        ladders.values().forEach(Ladder::save);
    }

    public Ladder createLadder(String name) {
        Ladder ladder = new Ladder(name);

        ladders.put(name, ladder);

        return ladder;
    }
}
