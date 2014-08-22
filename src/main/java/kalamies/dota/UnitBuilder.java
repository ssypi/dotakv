package kalamies.dota;

public class UnitBuilder {
    private Unit unit;

    private UnitBuilder() {
        this.unit = new Unit();
        unit.setBaseClass("npc_dota_creep");
//        unit.setModel("models/buildings/building_racks_melee_reference.vmdl");
        unit.setModel("models/creeps/neutral_creeps/n_creep_gnoll/n_creep_gnoll_frost.vmdl");
        unit.setModelScale(0.5);
        unit.setSoundSet("Tower.Fire");
        unit.setVisionDayTime(9000);
        unit.setRingRadius(100);
        unit.setVisionNightTime(9000);
        unit.setAttackCapabilities("DOTA_UNIT_CAP_RANGED_ATTACK");
        unit.setAttackAnimationPoint(0.0);
        unit.setBoundsHullName("DOTA_HULL_SIZE_HERO");
//        unit.setVscripts("ai/dude.lua");
//        unit.setSoundSet("Creep_Good_Melee");
        unit.setMovementTurnRate(1.0);
    }

    public Unit build() {
        return unit;
    }

    public static UnitBuilder createBuilder() {
        return new UnitBuilder();
    }

    public UnitBuilder name(String name) {
        name = name.replace("|cff33ff33", "");
        name = name.replace("|cffff3300", "");
        name = name.replace("|r", "");
        unit.setName(name);
        return this;
    }

    public UnitBuilder level(double level) {
        unit.setLevel(level);
        return this;
    }

    public UnitBuilder bounty(int bounty) {
        unit.setBountyGoldMax(bounty);
        unit.setBountyGoldMin(bounty);
        return this;
    }

    public UnitBuilder model(String model) {
        unit.setModel(model);
        return this;
    }

    public UnitBuilder modelScale(double modelScale) {
        unit.setModelScale(modelScale);
        return this;
    }


    public UnitBuilder moveSpeedMaxMin(int maxSpeed, int minSpeed) {
        unit .setMovementSpeed(maxSpeed, minSpeed);
        return this;
    }


    public UnitBuilder maxHp(int hitPoints) {
        unit.setMaxHp(hitPoints);
        return this;
    }


    public UnitBuilder projectileModel(String projectileModel) {
        unit.setProjectileModel(projectileModel);
        return this;
    }

    public UnitBuilder projectileSpeed(int projectileSpeed) {
        unit.setProjectileSpeed(projectileSpeed);
        return this;
    }

    public UnitBuilder attackDamageMaxMin(int attackDamageMax, int attackDamageMin) {
        unit.setAttackDamageMax(attackDamageMax);
        unit.setAttackDamageMin(attackDamageMin);
        return this;
    }

    public UnitBuilder attackRange(int attackRange) {
        unit.setAttackRange(attackRange);
        return this;
    }

    public UnitBuilder baseClass(String baseClass) {
        unit.setBaseClass(baseClass);

        return this;
    }

    public UnitBuilder moveCapabilities(String movementCapabilities) {
        unit.setMovementCapabilities(movementCapabilities);
        return this;
    }

    public UnitBuilder turnRate(Double movementTurnRate) {
        unit.setMovementTurnRate(movementTurnRate);
        return this;
    }

    public UnitBuilder attackCapabilities(String attackCapabilities) {
        unit.setAttackCapabilities(attackCapabilities);
        return this;
    }

    public UnitBuilder vscripts(String vscripts) {
        unit.setVscripts(vscripts);
        return this;
    }

    public UnitBuilder attackRate(double attackRate) {
        if (String.valueOf(attackRate).length() > 3) {
            attackRate = Double.parseDouble(String.valueOf(attackRate).substring(0, 4));
        }
        unit.setAttackRate(attackRate);
        return this;
    }

    public UnitBuilder attackTargets(String attackTargets) {
        unit.setAttackTargets(attackTargets);
        return this;
    }

    public UnitBuilder attackType(String attackType) {
        unit.setAttackDamageType(attackType);
        return this;
    }
}
