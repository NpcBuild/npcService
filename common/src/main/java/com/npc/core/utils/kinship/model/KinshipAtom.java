package com.npc.core.utils.kinship.model;

/**
 * @program: npcService
 * @description
 * @author: feiyang
 * @create: 2026/01/27 22:42
 **/
public class KinshipAtom {

    public GenerationOffset generation;
    public Gender gender;
    public Lineage lineage;

    public KinshipAtom(GenerationOffset generation, Gender gender, Lineage lineage) {
        this.generation = generation;
        this.gender = gender;
        this.lineage = lineage;
    }
}
