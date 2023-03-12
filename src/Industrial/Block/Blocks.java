
package Industrial.Block;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.gen.Call;
import mindustry.world.Block;

public class Blocks {
    //private static final Seq<SuperBlock> allBlocks = new Seq();
    //private static final Seq<Block> DY = new Seq();
    //private static final Seq<String> DN = new Seq();
    private static final ObjectMap<Block,Seq<SuperBlock>> allpair = new ObjectMap<>();
    public Blocks() {
    }

    public static void add(String name, SuperBlock b) {
        if (b != null && b.block != null && b.block != mindustry.content.Blocks.air && !name.equals("") && name != null) {
            //allBlocks.add(b);
            //DY.add(b.block);
            //if ()
            //DN.add(name);
            if (!allpair.containsKey(b.block))
                allpair.put(b.block,new Seq<>());
            allpair.get(b.block).add(b);
        } else {
            Call.sendMessage("发生注册错误"+b.name+"具体异常:"+b.getClass());
            throw new RuntimeException("发生注册错误" + b.name + ",class:" + b.getClass());

        }
    }
    public static void remove(SuperBlock b){
        if (b==null)
            return;
        //allBlocks.remove(b);
        allpair.get(b.block).remove(b);
        //DY.remove(b.block);
        //DN.remove(b.name);

    }


    public static Seq<SuperBlock> getAllBlock(Block b){
        if (!allpair.containsKey(b))
            allpair.put(b,new Seq<>());
        return allpair.get(b);
    }
}
