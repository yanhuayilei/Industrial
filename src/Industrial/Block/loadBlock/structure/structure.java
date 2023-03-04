
package Industrial.Block.loadBlock.structure;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.Block.loadBlock.structure.loadBlock.conv.container;
import Industrial.table.Menudispose;
import Industrial.table.PlayerInfo;
import Industrial.table.WorldTable;
import Industrial.time.ifBuilding;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.type.Item;
import mindustry.world.Block;

import java.util.ArrayList;
import java.util.Set;

public class structure extends SuperBlock {


    public structure(Block wall, String tesT) {
        super(wall,tesT);
    }

    public SuperBuild create(Building building) {
        return new structurecB(this, building, this);
    }













    public class structurecB extends SuperBuild {
        public Integer rotation;
        public int click = 0;
        public Building[] buildings;
        public Boolean[] isallmax;
        public ObjectMap<Item, Integer> storage;
        public int maxStorage;
        public ifBuilding task;

        public structurecB(structure this$0, Building build, SuperBlock b) {
            super(build, b);
            this.rotation = 0;
            this.buildings = new Building[]{null, null, null, null};
            this.isallmax = new Boolean[]{false, false, false, false};
            this.storage = new ObjectMap();
            this.task = new ifBuilding(() -> {
                this.buildings[this.rotation] = this.build.nearby(this.rotation);
                if (this.buildings[this.rotation] != null && this.buildings[this.rotation].items != null) {
                    this.isallmax[this.rotation] = this.buildings[this.rotation].items() != null && this.buildings[this.rotation].items().get(Items.copper) < this.buildings[this.rotation].block().itemCapacity && this.buildings[this.rotation].acceptItem(this.buildings[this.rotation], Items.copper);
                } else {
                    this.isallmax[this.rotation] = false;
                }

                if (!this.isallmax[0] && !this.isallmax[1] && !this.isallmax[2] && !this.isallmax[3]) {
                    if (this.storage.get(Items.copper) < this.maxStorage) {
                        this.storage.put(Items.copper, this.storage.get(Items.copper) + 1);
                        addIems(null);
                    } else {
                        this.maxlabel();
                    }
                } else if (this.isallmax[0]) {
                    this.addIems(this.buildings[0]);
                } else if (this.isallmax[1]) {
                    this.addIems(this.buildings[1]);
                } else if (this.isallmax[2]) {
                    this.addIems(this.buildings[2]);
                } else {
                    if (!this.isallmax[3]) {
                        this.rotation = 0;
                        return;
                    }

                    this.addIems(this.buildings[3]);
                }

                Integer var1 = this.rotation;
                Integer var2 = this.rotation = this.rotation + 1;
                if (this.rotation >= 4) {
                    this.rotation = 0;
                }

            }, 1, this);
            this.maxStorage = 10;
            Vars.content.items().each((item) -> {
                Integer var10000 = this.storage.put(item, 0);
            });
        }

        public void addIems(Building building) {
            //Log.info(neighborhood().size());
            Call.label("正在生产...", 1.05F, this.build.x, this.build.y);
            addFwItem(Industrial.item.Items.getSeq().first(), 1,0,false);
            if (building != null) {
                int originalItems = building.items().get(Items.copper);
                Call.setItem(building, Items.copper, originalItems + 1);
            }
        }

        public void maxlabel() {
            Call.label("东西已满", 1.05F, this.build.x, this.build.y);
        }

        public void run() {
            SuperBuild.addTask(this.task);
        }

        public void dead() {
            Log.info("死亡1");
        }


        @Override
        public void click(PlayerInfo player) {
            Menudispose menudispose = new Menudispose<SuperBuild>(this);
            addMenu(player,menudispose);
            Call.menu(player.player.con(),SuperBuild.menuid,"Test","Test",new String[][]{{"ok"}});
        }

        @Override
        public void clickProcess(PlayerInfo player, int option) {
            click++;
            player.player.sendMessage("第"+click+"次点"+"ok");
        }
    }

}
