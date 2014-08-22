package kalamies.dota;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class KvWriter {

    public static void SaveToKv(List<Unit> units, String filename, boolean towers) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("// Auto-generated, all changes will be overwritten.");
        units.forEach(unit -> {
            if (towers) {
                lines.addAll(TowerToKv(unit));
            } else {
                lines.addAll(UnitToKv(unit));
            }
        });
        if (Files.exists(Paths.get(filename))) {
            System.out.println("File " + filename + " exists, overwriting.");
        }
        Files.write(Paths.get(filename), lines);
    }

    public static List<String> UnitToKv(Unit unit) {
        List<String> lines = new ArrayList<>();
        lines.add("\"" + unit.getClassName() + "\"");
        lines.add("{");
        lines.add("    \"BaseClass\"                    \"" + unit.getBaseClass() + "\"");
        lines.add("    \"Model\"                        \"" + unit.getModel() + "\"");
        lines.add("    \"ModelScale\"                   \"" + unit.getModelScale() + "\"");
        lines.add("    \"BoundsHullName\"               \"" + unit.getBoundsHullName() + "\"");
        lines.add("    \"RingRadius\"                   \"" + unit.getRingRadius() + "\"");
        lines.add("    \"StatusHealth\"                 \"" + unit.getMaxHp() + "\"");
        lines.add("    \"AttackCapabilities\"           \"" + unit.getAttackCapabilities() + "\"");
        lines.add("    \"AttackDamageMin\"              \"" + unit.getAttackDamageMin() + "\"");
        lines.add("    \"AttackDamageMax\"              \"" + unit.getAttackDamageMax() + "\"");
//        lines.add("    \"AttackDamageType\"         \"" + unit.getAttackDamageType() + "\"");
        lines.add("    \"AttackRange\"                  \"" + unit.getAttackRange() + "\"");
        lines.add("    \"MovementSpeed\"                \"" + unit.getMovementSpeedMax() + "\"");
        lines.add("    \"MovementTurnRate\"             \"" + unit.getMovementTurnRate() + "\"");
        lines.add("    \"MovementCapabilities\"         \"" + unit.getMovementCapabilities() + "\"");
        lines.add("    \"BountyGoldMax\"                \"" + unit.getBountyGoldMax() + "\"");
        lines.add("    \"BountyGoldMin\"                \"" + unit.getBountyGoldMin() + "\"");
        lines.add("");
        lines.add("    \"vscripts\"                     \"ai/dude.lua\"");
        lines.add("");
        lines.add("    \"Ability1\"                     \"dude_passive\"");
        lines.add("");
        lines.add("    // Custom");
        lines.add("    \"UnitName\"                     \"" + unit.getName() + "\"");
        lines.add("}");
        lines.add("");
        lines.forEach(System.out::println);
        return lines;
    }

    public static List<String> TowerToKv(Unit unit) {
        List<String> lines = new ArrayList<>();
        lines.add("\"" + unit.getClassName() + "\"");
        lines.add("{");
        lines.add("    \"BaseClass\"                \"" + unit.getBaseClass() + "\"");
        lines.add("    \"Model\"                    \"" + unit.getModel() + "\"");
        lines.add("    \"ModelScale\"               \"" + unit.getModelScale() + "\"");
        lines.add("    \"BoundsHullName\"           \"" + unit.getBoundsHullName() + "\"");
        lines.add("    \"RingRadius\"               \"" + unit.getRingRadius() + "\"");
        lines.add("    \"StatusHealth\"               \"" + unit.getMaxHp() + "\"");
        lines.add("    \"AttackCapabilities\"       \"" + unit.getAttackCapabilities() + "\"");
        lines.add("    \"AttackDamageMin\"          \"" + unit.getAttackDamageMin() + "\"");
        lines.add("    \"AttackDamageMax\"          \"" + unit.getAttackDamageMax() + "\"");
//        lines.add("    \"AttackDamageType\"         \"" + unit.getAttackDamageType() + "\"");
        lines.add("    \"AttackRate\"               \"" + unit.getAttackRate() + "\"");
        lines.add("    \"AttackAnimationPoint\"     \"" + unit.getAttackAnimationPoint() + "\"");
        lines.add("    \"AttackAcquisitionRange\"   \"" + unit.getAttackRange() + "\"");
        lines.add("    \"AttackRange\"              \"" + unit.getAttackRange() + "\"");
        lines.add("    \"MovementTurnRate\"         \"" + unit.getMovementTurnRate() + "\"");
        lines.add("    \"ProjectileModel\"          \"" + unit.getProjectileModel() + "\"");
        lines.add("    \"ProjectileSpeed\"          \"" + unit.getProjectileSpeed() + "\"");
        lines.add("");
        lines.add("    \"Ability1\"                 \"tower_invulnerability\"");
        lines.add("    \"Ability2\"                 \"tower_attack_scripts\"");
        lines.add("");
        lines.add("    // Custom");
        lines.add("    \"UnitName\"                 \"" + unit.getName() + "\"");
        lines.add("    \"AttackTargets\"            \"" + unit.getAttackTargets() + "\"");

        if (unit.getCombinesTo() != null) {
            lines.add("    \"CombinesTo\"               \"" + unit.getCombinesTo() + "\"");
        }
        if (unit.getCombinedFrom() != null) {
            lines.add("    \"CombinedFrom\"");
            lines.add("     {");
            unit.getCombinedFrom().forEach(gem -> {
                lines.add("             \"" + gem + "\" \"1\"");
            });
            lines.add("     }");
        }

        lines.add("}");
        lines.add("");
        lines.forEach(System.out::println);
        return lines;
    }
}
