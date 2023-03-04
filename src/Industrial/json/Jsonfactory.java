package Industrial.json;

import Industrial.item.Item;
import Industrial.item.Items;
import arc.util.Log;
import arc.util.serialization.Json;
import arc.util.serialization.Jval;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.io.JsonIO;
import mindustry.world.Block;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class Jsonfactory {
    public final static Jval jval;


    public static Jval.JsonArray all;
    static {
        try {
        jval = Jval.read(new FileReader("demoAdd.json"));
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
        all = jval.asArray();
        //Log.info(all.first());
        for (Jval jval1:all.list()){//item
            if (jval1.get("type").asString().equals("item")){
                new Item(jval1.get("name").asString());
                Log.info("已注册物品:"+jval1.get("name").asString());
            }
        }
        for (Jval jval1:all.list()){
            if (jval1.get("type").asString().equals("factory")){
                Jval.JsonMap item = jval1.get("item").asObject();
                Block bl = Vars.content.block(jval1.getString("block"));
                String string = jval1.getString("name");
                new factory(bl,string){{
                    time = item.get("time").asInt();
                    outisoriginal = item.get("out").get("isoriginal").asBool();
                    out = outisoriginal?null: Items.get(item.get("out").get("item").asString());
                    out1 = outisoriginal?Vars.content.item(item.get("out").get("item").asString()):null;
                    outam = item.get("out").get("amount").asInt();
                    maxItem = jval1.get("maxItem").asInt();
                    inpisoriginal = item.get("inp").get("isoriginal").asBool();
                    inp = inpisoriginal?null:Items.get(item.get("inp").get("item").asString());
                    inp1 = inpisoriginal?Vars.content.item(item.get("inp").get("item").asString()):null;
                    inpam = item.get("inp").get("amount").asInt();
                    hasitem = true;
                }};
            }
        }

        Log.info("自定义物品已加载");
    }

    public static void load() {

    }
}
