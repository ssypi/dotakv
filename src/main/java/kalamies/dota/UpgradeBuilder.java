package kalamies.dota;

import quintinity.kvlib.KVException;
import quintinity.kvlib.KVLib;
import quintinity.kvlib.data.KVArray;
import quintinity.kvlib.data.KVElement;
import quintinity.kvlib.data.KVString;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UpgradeBuilder {
    public void ReadFromKV(String path) throws KVException {
        Map<String, KVArray> upgradeMap = new HashMap<>();
        File file = new File(path);
        KVArray data = KVLib.ReadKVFile(file);
        data.values().forEach(unit -> {
            if (unit.is("array")) {
                KVArray array = (KVArray) unit;
                array.get("Custom");
            }
        });
        data.getEntries().forEach((entry) -> {
            String name = entry.getKey();
            KVElement value = entry.getValue();
            if (value.is("array")) {
                KVArray custom = ((KVArray) value).getArray("Custom");
                if (custom != null) {
                    KVArray up = custom.getArray("Upgrade");
                    if (up != null) {
                        KVString upgradesTo = up.getString("UnitName");
                        KVString goldCost = up.getString("GoldCost");
                        KVArray upgradeArray = new KVArray();
                        upgradeArray.set("UnitName", upgradesTo);
                        upgradeArray.set("GoldCost", goldCost);
                        upgradeMap.put(name, upgradeArray);
                    }
                }
            }
        });
    }
}
