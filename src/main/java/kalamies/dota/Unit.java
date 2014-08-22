package kalamies.dota;

import java.util.ArrayList;
import java.util.List;

public class Unit {
    private String name;
    private double level;
    private int maxHp;
    private int movementSpeedMax = 300;
    private int movementSpeedMin = 100;
    private Double movementTurnRate = 1.0;
    private String baseClass;
    private String model;
    private double modelScale = 1.0;
    private String soundSet;
    private String attackCapabilities = "DOTA_UNIT_CAP_NO_ATTACK";
    private String moveCapabilities = "DOTA_UNIT_CAP_MOVE_GROUND";
    private String boundsHullName = "DOTA_HULL_SIZE_HERO";
    private int bountyGoldMin;
    private int bountyGoldMax;
    private List<String> abilities;
    private String vscripts = "ai/dude.lua";
    private int ringRadius;
    private int attackDamageMin;
    private int attackDamageMax;
    private String attackDamageType;
    private Double attackRate;
    private Double attackAnimationPoint;
    private int attackAcquisitionRange;
    private int attackRange;
    private String projectileModel;
    private int projectileSpeed;
    private String movementCapabilities;
    private String attackTargets;
    private String className = null;
    private String combinesTo;
    private List<String> combinedFrom;

    @Override
    public String toString() {
        return "#" + ((Double) level).intValue() + " " + name;
    }

    private int visionDayTime = 9000;
    private int visionNightTime = 9000;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMovementSpeedMax() {
        return movementSpeedMax;
    }

    public int getMovementSpeedMin() {
        return movementSpeedMin;
    }

    public void setMovementSpeed(int movementSpeed, int moveSpeedMin) {
        this.movementSpeedMax = movementSpeed;
        this.movementSpeedMin = moveSpeedMin;
    }

    public Double getMovementTurnRate() {
        return movementTurnRate;
    }

    public void setMovementTurnRate(Double movementTurnRate) {
        this.movementTurnRate = movementTurnRate;
    }

    public String getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(String baseClass) {
        this.baseClass = baseClass;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getModelScale() {
        return modelScale;
    }

    public void setModelScale(double modelScale) {
        this.modelScale = modelScale;
    }

    public String getSoundSet() {
        return soundSet;
    }

    public void setSoundSet(String soundSet) {
        this.soundSet = soundSet;
    }

    public String getAttackCapabilities() {
        return attackCapabilities;
    }

    public void setAttackCapabilities(String attackCapabilities) {
        this.attackCapabilities = attackCapabilities;
    }

    public String getBoundsHullName() {
        return boundsHullName;
    }

    public void setBoundsHullName(String boundsHullName) {
        this.boundsHullName = boundsHullName;
    }

    public int getBountyGoldMin() {
        return bountyGoldMin;
    }

    public void setBountyGoldMin(int bountyGoldMin) {
        this.bountyGoldMin = bountyGoldMin;
    }

    public int getBountyGoldMax() {
        return bountyGoldMax;
    }

    public void setBountyGoldMax(int bountyGoldMax) {
        this.bountyGoldMax = bountyGoldMax;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public String getVscripts() {
        return vscripts;
    }

    public void setVscripts(String vscripts) {
        this.vscripts = vscripts;
    }

    public int getVisionDayTime() {
        return visionDayTime;
    }

    public void setVisionDayTime(int visionDayTime) {
        this.visionDayTime = visionDayTime;
    }

    public int getVisionNightTime() {
        return visionNightTime;
    }

    public void setVisionNightTime(int visionNightTime) {
        this.visionNightTime = visionNightTime;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public int getRingRadius() {
        return ringRadius;
    }

    public void setRingRadius(int ringRadius) {
        this.ringRadius = ringRadius;
    }

    public int getAttackDamageMin() {
        return attackDamageMin;
    }

    public void setAttackDamageMin(int attackDamageMin) {
        this.attackDamageMin = attackDamageMin;
    }

    public int getAttackDamageMax() {
        return attackDamageMax;
    }

    public void setAttackDamageMax(int attackDamageMax) {
        this.attackDamageMax = attackDamageMax;
    }

    public String getAttackDamageType() {
        return attackDamageType;
    }

    public void setAttackDamageType(String attackDamageType) {
        this.attackDamageType = attackDamageType;
    }

    public Double getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(Double attackRate) {
        this.attackRate = attackRate;
    }

    public Double getAttackAnimationPoint() {
        return attackAnimationPoint;
    }

    public void setAttackAnimationPoint(Double attackAnimationPoint) {
        this.attackAnimationPoint = attackAnimationPoint;
    }


    public int getAttackAcquisitionRange() {
        return attackAcquisitionRange;
    }

    public void setAttackAcquisitionRange(int attackAcquisitionRange) {
        this.attackAcquisitionRange = attackAcquisitionRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public String getProjectileModel() {
        return projectileModel;
    }

    public void setProjectileModel(String projectileModel) {
        this.projectileModel = projectileModel;
    }

    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public void setMovementCapabilities(String movementCapabilities) {
        this.movementCapabilities = movementCapabilities;
    }

    public String getMovementCapabilities() {
        return movementCapabilities;
    }

    public void setAttackTargets(String attackTargets) {
        this.attackTargets = attackTargets;
    }

    public String getAttackTargets() {
        return attackTargets;
    }

    private boolean isSpecial() {
        List<String> qualities = new ArrayList<>();
        qualities.add("chipped");
        qualities.add("flawed");
        qualities.add("normal");
        qualities.add("flawless");
        qualities.add("perfect");
        qualities.add("great");
        final boolean[] isSpecial = {true};
        qualities.forEach(q -> {
            if (this.name.toLowerCase().contains(q)) {
                isSpecial[0] = false;
            }
        });
        return isSpecial[0];
    }

    public String getClassName() {
        if (this.className == null) {
            if (baseClass.contains("tower")) {
                if (isSpecial()) {
                    this.className = "gem_special_" + this.name.replace(" ", "_").toLowerCase();
                } else {
                    this.className = "gem_" + this.name.replace(" ", "_").toLowerCase();
                }
            } else {
                this.className = "dude" + ((Double) level).intValue();
                if (this.name.toLowerCase().contains("extreme")) {
                    this.className += "x";
                }
            }
        }
        return this.className;
    }

    public void setCombinesTo(String combinesTo) {
        this.combinesTo = combinesTo;
    }

    public String getCombinesTo() {
        return combinesTo;
    }

    public void setCombinedFrom(List<String> combinedFrom) {
        this.combinedFrom = combinedFrom;
    }

    public List<String> getCombinedFrom() {
        return combinedFrom;
    }
}
