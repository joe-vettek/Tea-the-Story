package com.teamtea.teastory.entity.ai;

import java.util.function.Predicate;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class AvoidCommonEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final TargetingConditions avoidEntityTargeting;

    public AvoidCommonEntityGoal(PathfinderMob pMob, Class<T> pEntityClassToAvoid, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier) {
        this(pMob, pEntityClassToAvoid, p_25052_ -> true, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    /**
     * Goal that helps mobs avoid mobs of a specific class
     */
    public AvoidCommonEntityGoal(
            PathfinderMob pMob,
            Class<T> pEntityClassToAvoid,
            Predicate<LivingEntity> pAvoidPredicate,
            float pMaxDistance,
            double pWalkSpeedModifier,
            double pSprintSpeedModifier,
            Predicate<LivingEntity> pPredicateOnAvoidEntity
    ) {
        super(pMob, pEntityClassToAvoid, pAvoidPredicate, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier, pPredicateOnAvoidEntity);
        this.avoidEntityTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().range(pMaxDistance).selector(pPredicateOnAvoidEntity.and(pAvoidPredicate));
    }

    public AvoidCommonEntityGoal(PathfinderMob pMob, Class<T> pEntityClassToAvoid, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier, Predicate<LivingEntity> pPredicateOnAvoidEntity) {
        this(pMob, pEntityClassToAvoid, p_25049_ -> true, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier, pPredicateOnAvoidEntity);

    }

    @Override
    public boolean canUse() {
        this.toAvoid = this.mob
                .level()
                .getNearestEntity(
                        this.mob
                                .level()
                                .getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate(this.maxDist, 3.0, this.maxDist), t -> true),
                        this.avoidEntityTargeting,
                        this.mob,
                        this.mob.getX(),
                        this.mob.getY(),
                        this.mob.getZ()
                );
        var cAvoid =  this.mob
                .level()
                .getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate(this.maxDist, 3.0, this.maxDist));
        if (!cAvoid.isEmpty()){
            this.toAvoid=cAvoid.getFirst();
        }
        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
            vec3=new Vec3(toAvoid.getX()+mob.getRandom().nextInt(10)-5,toAvoid.getY()+1,toAvoid.getZ()+mob.getRandom().nextInt(10)-5);
            if (vec3 == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(this.mob)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                return this.path != null;
            }
        }
    }

}
