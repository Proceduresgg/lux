package com.qrakn.lux.match.ladder;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.qrakn.lux.match.handler.MatchHandler;
import com.qrakn.lux.match.kit.MatchKit;
import com.qrakn.lux.match.ladder.handler.LadderHandler;
import com.qrakn.lux.match.queue.MatchQueue;
import com.qrakn.lux.mongo.MongoHandler;
import com.qrakn.lux.util.ItemStackSerializerUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public class Ladder {

    private final MatchQueue queue = new MatchQueue(this);

    private final String name;

    private int priority = 0;

    private ItemStack icon = new ItemStack(Material.DIAMOND);

    private MatchKit defaultKit = new MatchKit(new ItemStack[]{}, new ItemStack[]{});

    public void save() {
        MongoHandler.INSTANCE.getDatabase().getCollection("ladders")
                .replaceOne(Filters.eq("name", this.name), toJSON(), new ReplaceOptions().upsert(true));
    }

    public void delete() {
        LadderHandler.INSTANCE.getLadders().remove(name);

        MongoHandler.INSTANCE.getDatabase().getCollection("ladders")
                .deleteOne(Filters.eq("name", this.name));
    }

    public Ladder fromJSON(Document document) {
        if (document != null) {
            this.priority = document.getInteger("priority");

            try {
                this.defaultKit = new MatchKit(ItemStackSerializerUtil.itemStackArrayFromBase64(document.getString("contents")),
                        ItemStackSerializerUtil.itemStackArrayFromBase64(document.getString("armor")));

                if (document.getString("icon") != null) {
                    this.icon =  ItemStackSerializerUtil.itemStackArrayFromBase64(document.getString("icon"))[0];
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        return this;
    }

    private Document toJSON() {
        return new Document("name", name)
                .append("priority", priority)
                .append("contents", ItemStackSerializerUtil.itemStackArrayToBase64(defaultKit.getContents()))
                .append("armor", ItemStackSerializerUtil.itemStackArrayToBase64(defaultKit.getArmor()))
                .append("icon", ItemStackSerializerUtil.itemStackArrayToBase64(new ItemStack[]{icon}));
    }

    public int getPlaying() {
        return MatchHandler.INSTANCE.getMatches()
                .stream()
                .filter(match -> match.getLadder() == this)
                .collect(Collectors.toList())
                .size();
    }

    public int getQueuing() {
        return queue.getQueue().size();
    }
}
