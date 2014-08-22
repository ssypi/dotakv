package kalamies.dota;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class KvReader {

    private boolean hasCorrectBrackets(String content) {
        int openBrackets = StringUtils.countMatches(content, "{"); // TODO: check if the line is commented or bracket inside quotes
        int closeBrackets = StringUtils.countMatches(content, "}");
        if (openBrackets != closeBrackets) {
            System.out.println("Too many open/close brackets " + openBrackets + "/" + closeBrackets);
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidLine(String line, String nextLine) {
        if (line.trim().startsWith("\"")) {
            int quotes = StringUtils.countMatches(line, "\"");
            if (quotes == 2) {
                if (!nextLine.trim().startsWith("{")) {
                    System.out.println("Invalid line:");
                    System.out.println(line);
                    System.out.println(nextLine);
                    return false;
                }
            } else if (quotes == 4) {
                if (nextLine.trim().startsWith("{")) {
                    System.out.println("Invalid line:");
                    System.out.println(line);
                    System.out.println(nextLine);
                    return false;
                }
            } else {
                System.out.println("Invalid line:");
                System.out.println(line);
                System.out.println(nextLine);
                return false;
            }
        }
        return true;
    }

    public void read(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        hasCorrectBrackets(content);

        try (Stream<String> filteredLines = Files.lines(Paths.get(path))
                .onClose(() -> System.out.println("File closed."))
                .filter(line -> !line.trim().startsWith("//"))) {
            List<String> lines = filteredLines.collect(Collectors.toList());

            for (int i = 0; i < lines.size() - 1; i++) {
                isValidLine(lines.get(i), lines.get(i + 1));
            }
            Map<String, Object> map = KvToMap(lines, 0);
            map.forEach((k,v) -> System.out.println(k));
            List<Unit> units = new ArrayList<>();
            ((Map<String, Object>)map.get("DOTAUnits")).forEach((k, v) -> {
                if (!k.equals("parent") && v instanceof HashMap) {
                    units.add(UnitFromMap(k, (Map) v));
                }
            });
            units.forEach(KvWriter::UnitToKv);
        }
    }

    private String getString(Map map, String key) {
        Object value = map.get(key);
        if (value instanceof String) {
            String string = ((String) value).replace("\"", "");
            return string;
        }
        return "";
    }

    private int getInt(Map map, String key) {
        Object value = map.get(key);
        if (value instanceof String) {
            String s = String.valueOf(value);
            s = s.replace("\"", "");
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing integer " + key + " = " + value);
                return 0;
            }
        }
        return 0;
    }

    private double getDouble(Map map, String key) {
        Object value = map.get(key);
        if (value instanceof String) {
            String s = String.valueOf(value);
            s = s.replace("\"", "");
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing double " + key + " = " + value);
                return 0;
            }
        }
        return 0;
    }

    private Unit UnitFromMap(String name, Map map) {
        Unit unit = UnitBuilder.createBuilder()
                .name(name)
                .baseClass(getString(map, "BaseClass"))
                .model(getString(map, "Model"))
                .modelScale(getDouble(map, "ModelScale"))
                .maxHp(getInt(map, "StatusHealth"))
                .bounty(getInt(map, "BountyGoldMax"))
                .projectileModel(getString(map, "ProjectileModel"))
                .projectileSpeed(getInt(map, "ProjectileSpeed"))
                .moveSpeedMaxMin(getInt(map, "MovementSpeed"), getInt(map, "MovementSpeedMin"))
                .moveCapabilities(getString(map, "MovementCapabilities"))
                .turnRate(getDouble(map, "MovementTurnRate"))
                .attackCapabilities(getString(map, "AttackCapabilities"))
                .vscripts(getString(map, "vscripts"))
                .attackDamageMaxMin(getInt(map, "AttackDamageMax"), getInt(map, "AttackDamageMin"))
                .attackRange(getInt(map, "AttackRange"))
                .build();

        unit.setAttackCapabilities(getString(map, "AttackCapabilities"));
        return unit;
    }

    private Map KvToMap(List<String> lines, int index) {
        Map<String, Object> all = new HashMap<>();
        Map<String, Object> cur = all;
        Map<String, Object> prev = null;
        for (int i = index; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            Pattern kv = Pattern.compile("\"([^\"]+)\"[\\s]*(\"[^\"]*\")?\"?[\\s]*(//.*)?");
            Matcher matcher = kv.matcher(line);
            if (matcher.matches()) {
                String key = matcher.group(1);
                MatchResult result = matcher.toMatchResult();
//                System.out.println(result.groupCount());
                if (matcher.group(2) != null) {
                    String value = matcher.group(2);
//                    value = value.replace("\"", "");
//                    System.out.println(value);
                    if (cur.containsKey(key)) {
                        System.out.println("Error: Duplicate key on line#" + i);
                        System.out.println(line);
                    }
                    cur.put(key, value);
                } else {
//                    System.out.println(line);
//                    System.out.println(i);
                    prev = cur;
                    cur = new HashMap<>();
                    cur.put("parent", prev);
                    prev.put(key, cur);
                }
//                if (!matcher.group(2).isEmpty()) {
//                    String value = matcher.group(2);
////                    System.out.println(key + ":" + value);
//                    cur.put(key, value);
//                } else if (!matcher.group(1).trim().isEmpty()) {
//                    prev = cur;
//                    cur = new HashMap<>();
//                    prev.put(key, cur);
//                } else {
//                    System.out.println("error?");
//
//                }
            } else {
                if (line.equals("}")) {
                    cur = (Map) cur.get("parent");
                }
            }
        }
        return all;
    }


    public Unit ParseUnit(String unitKv) {
        Unit unit = UnitBuilder.createBuilder().build();
        return unit;
    }
}
