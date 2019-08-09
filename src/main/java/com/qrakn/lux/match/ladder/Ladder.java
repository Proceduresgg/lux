package com.qrakn.lux.match.ladder;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.kit.MatchKit;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
import com.qrakn.lux.match.queue.MatchQueue;
import com.qrakn.lux.mongo.MongoHandler;
import com.qrakn.lux.util.ItemStackUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@RequiredArgsConstructor
public class Ladder {

    private final MatchQueue queue = new MatchQueue(this);

    private final String name;

    private int priority = 0;

    private ItemStack icon = new ItemStack(Material.DIAMOND);

    private MatchKit defaultKit = new MatchKit(new ItemStack[]{}, new ItemStack[]{});

    @SneakyThrows
    public Ladder fromJSON(Document document) {
        if (document != null) {
            this.priority = document.getInteger("priority");
            this.icon = ItemStackUtils.fromBase64(document.getString("icon"))[0];
            this.defaultKit = new MatchKit(ItemStackUtils.fromBase64(document.getString("contents")), ItemStackUtils.fromBase64(document.getString("armor")));
        }

        return this;
    }

    private Document toJSON() {
        return new Document("name", name)
                .append("priority", priority)
                .append("contents", ItemStackUtils.toBase64(defaultKit.getContents()))
                .append("armor", ItemStackUtils.toBase64(defaultKit.getArmor()))
                .append("icon", ItemStackUtils.toBase64(new ItemStack[]{icon}));
    }

    public int getPlaying() {
        return (int) MatchHandler.INSTANCE.getMatches()
                .stream()
                .filter(match -> match.getLadder() == this)
                .count() * 2;
    }

    public int getQueuing() {
        return queue.getQueue().size();
    }

    public void save() {
        MongoHandler.INSTANCE.getDatabase().getCollection("ladders")
                .replaceOne(Filters.eq("name", this.name), toJSON(), new ReplaceOptions().upsert(true));
    }

    public void delete() {
        LadderHandler.INSTANCE.getLadders().remove(name);

        MongoHandler.INSTANCE.getDatabase().getCollection("ladders")
                .deleteOne(Filters.eq("name", this.name));
    }
}
