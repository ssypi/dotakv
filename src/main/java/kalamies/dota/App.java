package kalamies.dota;

import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
//        List<Unit> units = UnitDataReader.ReadUnits("units.w3u.json");
        List<Unit> towers = UnitDataReader.ReadTowers("units.w3u.json");
//        KvWriter.SaveToKv(units, "dudes.txt", false);
        KvWriter.SaveToKv(towers, "towers.txt", true);



//        KvReader reader = new KvReader();
//        reader.read("npc_units_custom.txt");
//        new KvMerger().MergeInFolder("", "units.txt");
    }
}
