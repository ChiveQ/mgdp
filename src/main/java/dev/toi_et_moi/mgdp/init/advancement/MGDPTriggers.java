package dev.toi_et_moi.mgdp.init.advancement;

import dev.toi_et_moi.mgdp.init.MGDP;

public class MGDPTriggers {

    public static void register() {}

    public static final GolemKillTrigger GOLEM_KILL = new GolemKillTrigger(MGDP.loc("golem_kill"));

}
