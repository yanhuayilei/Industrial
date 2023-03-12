package Industrial.json;

import Industrial.Block.SuperBlock;
import Industrial.item.Item;
import Industrial.item.Items;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.serialization.Json;
import arc.util.serialization.Jval;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.gen.Call;
import mindustry.io.JsonIO;
import mindustry.world.Block;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class Jsonfactory {
    public final static Jval jval;
    public final static Seq<SuperBlock> jsonBlock = new Seq<>();
    public static void enrollBlock(Jval jval1)throws Exception{
        if (jval1.get("type").asString().equals("factory")){
            Jval.JsonMap item = jval1.get("item").asObject();
            Block bl = Vars.content.block(jval1.getString("block"));
            String string = jval1.getString("name");
            Log.info("已注册方块:"+jval1.get("name").asString());
            Call.sendMessage("已注册方块:"+jval1.get("name").asString());
            jsonBlock.add(new factory(bl,string){{
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
            }});
        }
    }
    public static void enrollItem(Jval jval1)throws Exception{
        if (jval1.get("type").asString().equals("item")){
            new Item(jval1.get("name").asString());
            Log.info("已注册物品:"+jval1.get("name").asString());
            Call.sendMessage("已注册物品:"+jval1.get("name").asString());
        }
    }

    public static Jval.JsonArray all;
    static {
        try {
        jval = Jval.read(new FileReader("demoAdd.json"));
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
        all = jval.asArray();
        //Log.info(all.first());
        try {
            for (Jval jval1 : all.list()) {//item
                enrollItem(jval1);
            }
            for (Jval jval1 : all.list()) {
                enrollBlock(jval1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.info("自定义物品已加载");
    }

    public static void load() {

    }
}
