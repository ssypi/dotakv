package kalamies.dota;

import org.json.JSONString;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.json.stream.JsonParser.Event;

public class UnitDataReader {
    public JsonObject unit;

    private static Map<String, String> combinesTo;
    private static Map<String, List<String>> combinedFrom;

    public UnitDataReader(JsonValue unit) {
        this.unit = (JsonObject) unit;
    }



    public static List<Unit> ReadTowers(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
//        System.out.println(content);

        Recipes recipes = new Recipes("gems.json");
        combinesTo = recipes.getCombinesTo();
        combinedFrom = recipes.getCombinedFrom();

        try (JsonReader jsonReader = Json.createReader(new StringReader(content))) {
            JsonObject object = jsonReader.readObject();
            JsonArray array = object.getJsonArray("customInfo");
            Stream<JsonValue> stream = array.stream()
                    .filter(UnitDataReader::IsTower)
                    .sorted(UnitDataReader::CompareUnitLevel);
//            List list = stream.collect(Collectors.toList());
            List<Unit> units = new ArrayList<>();
            stream.forEach(unit -> {
                UnitDataReader reader = new UnitDataReader(unit);
                reader.AddToTowerList(units);
            });
//            units.forEach(unit -> System.out.println(unit.toString()));
            return units;
        }
    }


    public static List<Unit> ReadUnits(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
//        System.out.println(content);

        try (JsonReader jsonReader = Json.createReader(new StringReader(content))) {
            JsonObject object = jsonReader.readObject();
            JsonArray array = object.getJsonArray("customInfo");
            Stream<JsonValue> stream = array.stream()
                    .filter(UnitDataReader::IsUnit)
                    .sorted(UnitDataReader::CompareUnitLevel);
//            List list = stream.collect(Collectors.toList());
            List<Unit> units = new ArrayList<>();
            stream.forEach(unit -> {
                UnitDataReader reader = new UnitDataReader(unit);
                reader.AddToUnitList(units);
            });
//            units.forEach(unit -> System.out.println(unit.toString()));
            return units;
        }
    }

    private String GetAttackTargets() {
        String targets = GetKeyValue("ua1g").toLowerCase();
        if (!targets.contains("ground")) {
            return "GEM_ATTACK_AIR";
        } else if (!targets.contains("air")) {
            return "GEM_ATTACK_GROUND";
        } else {
            return "GEM_ATTACK_BOTH";
        }
    }

    private String getAttackType() {
        String type = GetKeyValue("ua1t");
        if (type.isEmpty() || type.equals("0")) {
            type = GetKeyValue("ua2t");
        }
        return type;
    }

    private int GetMaxDamage(JsonValue unit) {
        JsonObject obj = (JsonObject) unit;
        int base = Integer.parseInt(GetKeyValue("ua1b"));
        int dice = Integer.parseInt(GetKeyValue("ua1d"));
        int sides = Integer.parseInt(GetKeyValue("ua1s"));
        int max = base + dice*sides;
        return max;
    }

    private void AddToTowerList(List<Unit> units) {
        Unit newUnit = UnitBuilder.createBuilder()
                .baseClass("npc_dota_tower")
                .model("models/buildings/building_racks_melee_reference.vmdl")
                .modelScale(0.5)
                .maxHp(50)
                .attackCapabilities("DOTA_UNIT_CAP_RANGED_ATTACK")
                .attackDamageMaxMin(Integer.parseInt(GetKeyValue("ua1b")) + 1, GetMaxDamage(unit))
                .attackRate(Double.parseDouble(GetKeyValue("ua1c")))
                .attackRange(GetAttackRange())
                .attackTargets(GetAttackTargets())
                .attackType(getAttackType())
                .projectileSpeed(getProjectileSpeed())
                .projectileModel("particles/base_attacks/generic_projectile.vpcf")
                .moveCapabilities("DOTA_UNIT_CAP_MOVE_NONE")
                .name(GetUnitName())
                .build();

        newUnit.setCombinesTo(combinesTo.get(newUnit.getClassName()));
        newUnit.setCombinedFrom(combinedFrom.get(newUnit.getClassName()));

        units.add(newUnit);
    }

    private void AddToUnitList(List<Unit> units) {
        Unit newUnit = UnitBuilder.createBuilder()
                .baseClass("npc_dota_creature")
                .bounty(GetGoldBounty())
                .maxHp(GetMaxHp())
                .attackCapabilities("DOTA_UNIT_CAP_NO_ATTACK")
                .attackDamageMaxMin(Integer.parseInt(GetKeyValue("ua1b")) + 1, GetMaxDamage(unit))
                .attackRate(Double.parseDouble(GetKeyValue("ua1c")))
                .attackRange(Integer.parseInt(GetKeyValue("ua1r")))
                .attackTargets(GetAttackTargets())
                .attackType(getAttackType())
                .projectileSpeed(Integer.parseInt(GetKeyValue("ua1z")))
                .moveCapabilities(GetUnitMoveType())
                .moveSpeedMaxMin(GetMoveSpeed(), GetMoveSpeedMin())
                .name(GetUnitName())
                .level(Double.parseDouble(GetUnitLevel()))
                .build();
        units.add(newUnit);
    }


    public String GetUnitName() {
        String name = String.valueOf(GetUnitMod("unam"));
        return name;
    }

    public int GetAttackRange() {
        int range = Integer.parseInt(GetKeyValue("ua1r"));
        if (range == 0) {
            range = Integer.parseInt(GetKeyValue("ua2r"));
        }
        return range;
    }

    public int getProjectileSpeed() {
        int speed = Integer.parseInt(GetKeyValue("ua1z"));
        if (speed == 0) {
            speed = Integer.parseInt(GetKeyValue("ua2z"));
        }
        if (speed == 0) {
            speed = 711;
        }
        return speed;
    }

    public String GetUnitMoveType() {
        String moveType = GetKeyValue("umvt");
        if (moveType.equals("fly")) {
            return "DOTA_UNIT_CAP_MOVE_FLY";
        } else {
            return "DOTA_UNIT_CAP_MOVE_GROUND";
        }
    }

    public String GetUnitDefenseType(JsonValue unit) {
        return String.valueOf(GetUnitMod("udty"));
    }

    public String GetUnitDefenseBonus(JsonValue unit) {
        return String.valueOf(GetUnitMod("udef"));
    }

    public String GetUnitDefenseUpgrade(JsonValue unit) {
        return String.valueOf(GetUnitMod("udup"));
    }

    public int GetGoldBounty() {
        return 1 + Integer.parseInt(String.valueOf(GetUnitMod("ubba")));
    }

    public int GetMaxHp() {
        return Integer.parseInt(String.valueOf(GetUnitMod("uhpm")));
    }


    public int GetMoveSpeed() {
        return Integer.parseInt(String.valueOf(GetUnitMod("umvs")));
    }

    public int GetMoveSpeedMin() {
        return Integer.parseInt(String.valueOf(GetUnitMod("umis")));
    }

    public String GetKeyValue(String key) {
        JsonArray mods = unit.getJsonArray("mods");
        for (JsonValue val : mods) {
            JsonObject object = (JsonObject) val;
            if (object.getString("ID").contains(key)) {
                if (object.getValueType() == JsonValue.ValueType.STRING) {
                    return object.getString("value");
                } else {
                    try {
                        return "" + object.getJsonNumber("value");
                    } catch (ClassCastException e) {
                        return object.getString("value");
                    }

                }
            }
        }
        return "0";
    }

    public Object GetUnitMod(String mod) {
        JsonArray mods = unit.getJsonArray("mods");
        for (JsonValue val : mods) {
            JsonObject object = (JsonObject) val;
            if (object.getString("ID").contains(mod)) {
                if (object.getValueType() == JsonValue.ValueType.STRING) {
                    return object.getString("value");
                } else {
                    try {
                        return object.getInt("value");
                    } catch (ClassCastException e) {
                        return object.getString("value");
                    }

                }
            }
        }
        return "0";
    }

    public String GetUnitLevel() {
        JsonArray mods = unit.getJsonArray("mods");
        for (JsonValue val : mods) {
            JsonObject object = (JsonObject) val;
            if (object.getString("ID").contains("unsf")) {
                if (object.getString("value") == null) {
                    System.out.println("IS NULL");
                } else {
                    if (!object.getString("value").contains("Race")) {
                        return object.getString("value");
                    }
                }
            }
        }
        return "0";
    }

    public static int CompareUnitLevel(JsonValue u1, JsonValue u2) {
        JsonObject unit1 = (JsonObject) u1;
        JsonObject unit2 = (JsonObject) u2;

        UnitDataReader reader1 = new UnitDataReader(u1);
        UnitDataReader reader2 = new UnitDataReader(u2);

        String unit1Level = reader1.GetUnitLevel();
        String unit2Level = reader2.GetUnitLevel();

        return Float.compare(Float.parseFloat(unit1Level), Float.parseFloat(unit2Level));
    }

    public static boolean IsUnit(JsonValue value) {
        final boolean[] containsKey = new boolean[1];
        containsKey[0] = false;
        if (value.getValueType() == JsonValue.ValueType.OBJECT) {
            JsonArray array = ((JsonObject) value).getJsonArray("mods");
            for (JsonValue val : array) {
                JsonObject object = (JsonObject) val;
                if (object.getString("ID").contains("umvs")) {
                    if (object.getInt("value") != 0) {
                        containsKey[0] = true;
                    }
                }
            }
//            array.forEach(x -> containsKey[0] = ((JsonObject) x).getString("ID").contains("umvs"));
//            System.out.println(containsKey[0]);
//            if (containsKey[0]) {
//                System.out.println(("found umvs"));
//            }
//            return containsKey[0];
        } else {
            System.out.println("not object");
        }
        return containsKey[0];
    }

    public static boolean IsTower(JsonValue value) {
        final boolean[] containsKey = new boolean[1];
        containsKey[0] = true;
        if (value.getValueType() == JsonValue.ValueType.OBJECT) {
            JsonArray array = ((JsonObject) value).getJsonArray("mods");
            for (JsonValue val : array) {
                JsonObject object = (JsonObject) val;
                if (object.getString("ID").contains("umvs")) {
                    if (object.getInt("value") != 0) {
                        containsKey[0] = false;
                    }
                }
            }
//            array.forEach(x -> containsKey[0] = ((JsonObject) x).getString("ID").contains("umvs"));
//            System.out.println(containsKey[0]);
//            if (containsKey[0]) {
//                System.out.println(("found umvs"));
//            }
//            return containsKey[0];
        } else {
            System.out.println("not object");
        }
        return containsKey[0];
    }
}
